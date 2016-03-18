package org.silvercatcher.reforged.items.weapons;

import org.silvercatcher.reforged.ReforgedRegistry;
import org.silvercatcher.reforged.entities.EntityBoomerang;
import org.silvercatcher.reforged.items.ExtendedItem;
import org.silvercatcher.reforged.items.recipes.BoomerangEnchRecipe;
import org.silvercatcher.reforged.material.MaterialDefinition;
import org.silvercatcher.reforged.material.MaterialManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class ItemBoomerang extends ExtendedItem {
	
	protected final MaterialDefinition materialDefinition;
	protected final boolean unbreakable;
	
	public ItemBoomerang(ToolMaterial material) {
		this(material, false);
	}
	
	public ItemBoomerang(ToolMaterial material, boolean unbreakable) {
		super();
		this.unbreakable = unbreakable;
		setMaxStackSize(1);
		materialDefinition = MaterialManager.getMaterialDefinition(material);
		setMaxDamage((int) (materialDefinition.getMaxUses() * 0.8f));
		setUnlocalizedName(materialDefinition.getPrefixedName("boomerang"));
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
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer) {
	   
		// import, otherwise references will cause chaos!
		ItemStack throwStack = par1ItemStack.copy();
		
		if(par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(this))
	    {
	        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        	
	        if (!par2World.isRemote) {
	        	
	        	EntityBoomerang boomerang = new EntityBoomerang(par2World, par3EntityPlayer, throwStack);
	        	par2World.spawnEntityInWorld(boomerang);
	        }
	    }
	    return par1ItemStack;
	}

	@Override
	public void registerRecipes() {
		
			GameRegistry.addRecipe(new ItemStack(this),
					"xww",
					"  w",
					"  x",
					'x', materialDefinition.getRepairMaterial(),
					'w', Items.stick);
			ReforgedRegistry.registerIRecipe("EnchantBoomerang", new BoomerangEnchRecipe(), BoomerangEnchRecipe.class, Category.SHAPELESS);
	}
	
	/**
	 * this is weak melee combat damage!
	 * for ranged combat damage, see {@link EntityBoomerang#getImpactDamage}
	 */
	@Override
	public float getHitDamage() {
		
		return Math.max(1f, (0.5f + materialDefinition.getDamageVsEntity() * 0.5f));
	}
	
	public ToolMaterial getMaterial() {
		
		return materialDefinition.getMaterial();
	}
	
	public MaterialDefinition getMaterialDefinition() {
		
		return materialDefinition;
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return materialDefinition.getEnchantability();
	}
}