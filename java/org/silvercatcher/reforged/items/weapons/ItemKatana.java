package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedMod;
import org.silvercatcher.reforged.items.ItemExtension;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemKatana extends ItemSword implements ItemExtension {

	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;
	
	public ItemKatana(ToolMaterial material) {
		this(material, false);
	}
	
	public ItemKatana(ToolMaterial material, boolean unbreakable) {
		super(material);
		
		this.unbreakable = unbreakable;
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		
		setUnlocalizedName(materialDefinition.getPrefixedName("katana"));
		setMaxDamage(materialDefinition.getMaxUses());
		setMaxStackSize(1);
		setCreativeTab(ReforgedMod.tabReforged);
	}
	
	@Override
	public boolean isDamageable() {
		if(unbreakable) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		if(!super.onLeftClickEntity(stack, player, entity)) {
			
			if(entity instanceof EntityLivingBase) {
				
				EntityLivingBase target = (EntityLivingBase) entity;
				
				int armorvalue = 0;

				for(int i = 1; i < 4; i++) {
					
					ItemStack armorStack = target.getEquipmentInSlot(i);
					if(armorStack != null && armorStack.getItem() instanceof ItemArmor) {
						armorvalue += ((ItemArmor) armorStack.getItem()).damageReduceAmount;
					}
				}

				float damage = getHitDamage() + getEnchantmentBonus(stack, player, entity);
				
				if(armorvalue < 12) {
					
					damage *= 1.5f;
					target.hurtResistantTime = 0;
				}
				
				if(armorvalue > 6) {
					
					stack.damageItem(1, target);
				}
				
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
			}
		}
		
		return true;
	}
	
	@Override
	public void registerRecipes() {
		
		GameRegistry.addRecipe(new ItemStack(this),
				"  m",
				" m ",
				"s  ",
				'm', materialDefinition.getRepairMaterial(),
				's', Items.stick);
	}

	@Override
	public float getHitDamage() {
		return materialDefinition.getDamageVsEntity() + 2f;
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		return ItemExtension.super.getAttributeModifiers(stack);
	}
	
	public ToolMaterial getMaterial() {
		
		return materialDefinition.getMaterial();
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}
}
