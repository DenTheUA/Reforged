package org.silvercatcher.reforged.api;

import java.util.*;

import org.silvercatcher.reforged.ReforgedMod;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public abstract class AReloadable extends ItemBow implements ItemExtension {
	
	private Item ammo;
	
	public AReloadable(String name) {
		setMaxStackSize(1);
		setMaxDamage(100);
		setUnlocalizedName(name);
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	byte empty		= 0;
	byte loading	= 1;
	byte loaded		= 2;
	
	Map<EntityPlayer, Integer> clientServerTransfer = new HashMap<EntityPlayer, Integer>();
	
	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		LanguageRegistry lr = LanguageRegistry.instance();
		
		tooltip.add(lr.getStringLocalization("item.musket.loadstate") + ": " + (loadState == empty ? 
				lr.getStringLocalization("item.musket.loadstate.empty")
				: (loadState == loaded ? lr.getStringLocalization("item.musket.loadstate.loaded") : 
					lr.getStringLocalization("item.musket.loadstate.loading"))));
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) return EnumAction.BLOCK;
		if(loadState == loaded) return EnumAction.BOW;
		return EnumAction.NONE;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) return 40;

		return super.getMaxItemUseDuration(stack);
	}
	
	protected void setAmmo(Item ammo) {
		this.ammo = ammo;
	}
	
	private Item getAmmo() {
		return ammo;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound compound = giveCompound(itemStackIn);
		
		byte loadState = compound.getByte(CompoundTags.AMMUNITION);
		
		if(loadState == empty) {
			if(playerIn.capabilities.isCreativeMode || playerIn.inventory.consumeInventoryItem(getAmmo())) {
				
				loadState = loading;
				if(compound.getByte(CompoundTags.AMMUNITION) == empty) {
					if(worldIn.isRemote) {
						compound.setInteger(CompoundTags.STARTED, playerIn.ticksExisted + getReloadTotal());
						clientServerTransfer.put(playerIn, playerIn.ticksExisted + getReloadTotal());					
					} else {
						compound.setInteger(CompoundTags.STARTED, clientServerTransfer.get(playerIn));
					}
				}
			} else {
				
				worldIn.playSoundAtEntity(playerIn, "item.fireCharge.use", 1.0f, 0.7f);
			}
		}
		
		compound.setByte(CompoundTags.AMMUNITION, loadState);
		
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		
		return itemStackIn;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
		if(!worldIn.isRemote) {
			NBTTagCompound compound = giveCompound(stack);
			
			byte loadState = compound.getByte(CompoundTags.AMMUNITION);
			
			if(loadState == loaded) {
				
				worldIn.playSoundAtEntity(playerIn, "ambient.weather.thunder", 1f, 1f);
				
				shoot(worldIn, playerIn, stack);
				if(!playerIn.capabilities.isCreativeMode && stack.getItem().isDamageable() && stack.attemptDamageItem(5, itemRand)) {
					playerIn.renderBrokenItemStack(stack);
					playerIn.destroyCurrentEquippedItem();
				}
				
				compound.setByte(CompoundTags.AMMUNITION, empty);
				compound.setInteger(CompoundTags.STARTED, -1);
			}
		}
	}
	
	public abstract void shoot(World worldIn, EntityLivingBase playerIn, ItemStack stack);
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		byte loadState = giveCompound(stack).getByte(CompoundTags.AMMUNITION);
		
		if(loadState == loading) {
			loadState = loaded;
		}
		giveCompound(stack).setByte(CompoundTags.AMMUNITION, loadState);
		return stack;
	}
	
	public abstract int getReloadTotal();
	
	public NBTTagCompound giveCompound(ItemStack stack) {
		
		NBTTagCompound compound = CompoundTags.giveCompound(stack);
		
		if(!compound.hasKey(CompoundTags.AMMUNITION)) {
			
			compound.setByte(CompoundTags.AMMUNITION, empty);
		}
		return compound;
	}
	
	public int getReloadStarted(ItemStack stack) {
		return giveCompound(stack).getInteger(CompoundTags.STARTED);
	}
	
	public int getReloadLeft(ItemStack stack, EntityPlayer player) {
		return (getReloadStarted(stack) - player.ticksExisted);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
}