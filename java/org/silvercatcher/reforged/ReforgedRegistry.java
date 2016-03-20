package org.silvercatcher.reforged;

import java.util.ArrayList;
import java.util.List;

import org.silvercatcher.reforged.ReforgedReferences.GlobalValues;
import org.silvercatcher.reforged.api.ReforgedAdditions;
import org.silvercatcher.reforged.items.ItemExtension;
import org.silvercatcher.reforged.items.others.ItemArrowBundle;
import org.silvercatcher.reforged.items.others.ItemBulletBlunderbuss;
import org.silvercatcher.reforged.items.others.ItemBulletMusket;
import org.silvercatcher.reforged.items.others.ItemDart;
import org.silvercatcher.reforged.items.weapons.ItemBattleAxe;
import org.silvercatcher.reforged.items.weapons.ItemBlowGun;
import org.silvercatcher.reforged.items.weapons.ItemBlunderbuss;
import org.silvercatcher.reforged.items.weapons.ItemBoomerang;
import org.silvercatcher.reforged.items.weapons.ItemFireRod;
import org.silvercatcher.reforged.items.weapons.ItemHolyCross;
import org.silvercatcher.reforged.items.weapons.ItemJavelin;
import org.silvercatcher.reforged.items.weapons.ItemKatana;
import org.silvercatcher.reforged.items.weapons.ItemKnife;
import org.silvercatcher.reforged.items.weapons.ItemMusket;
import org.silvercatcher.reforged.items.weapons.ItemMusketWithBayonet;
import org.silvercatcher.reforged.items.weapons.ItemNestOfBees;
import org.silvercatcher.reforged.items.weapons.ItemSaber;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ReforgedRegistry {
	
	//Integers
	public static int counterEntities = 0;
	
	//Hashmaps
	/**Every item on that list gets registered*/
	public static List<Item> registrationList = new ArrayList<Item>();
	
	//Registry
	/**Adds all items to the registrationList*/
	public static void createItems() {

		if(GlobalValues.NEST_OF_BEES) {
			registrationList.add(ReforgedAdditions.ARROW_BUNDLE = new ItemArrowBundle());
			registrationList.add(ReforgedAdditions.NEST_OF_BEES = new ItemNestOfBees());
		}
		
		if(GlobalValues.HOLY_CROSS) {
			registrationList.add(ReforgedAdditions.HOLY_CROSS = new ItemHolyCross());
		}
		
		if(GlobalValues.FIREROD) {
			registrationList.add(ReforgedAdditions.FIREROD = new ItemFireRod());
		}
		
		if(GlobalValues.MUSKET) {
			registrationList.add(ReforgedAdditions.MUSKET_BARREL = 
					new Item().setUnlocalizedName("musket_barrel").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.BLUNDERBUSS_BARREL = 
					new Item().setUnlocalizedName("blunderbuss_barrel").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.GUN_STOCK = 
					new Item().setUnlocalizedName("gun_stock").setCreativeTab(ReforgedMod.tabReforged));
			registrationList.add(ReforgedAdditions.MUSKET = new ItemMusket());
			registrationList.add(ReforgedAdditions.WOODEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_BAYONET_MUSKET = new ItemMusketWithBayonet(ToolMaterial.DIAMOND));
			registrationList.add(ReforgedAdditions.MUSKET_BULLET = new ItemBulletMusket());
			registrationList.add(ReforgedAdditions.BLUNDERBUSS = new ItemBlunderbuss());
			registrationList.add(ReforgedAdditions.BLUNDERBUSS_SHOT = new ItemBulletBlunderbuss());
		}
		
		if(GlobalValues.BATTLEAXE) {
			registrationList.add(ReforgedAdditions.WOODEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.GOLD));
		}
			//This has to be registered! Else, the Creative Tab would be broken!
			registrationList.add(ReforgedAdditions.IRON_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.IRON));
		if(GlobalValues.BATTLEAXE) {
			registrationList.add(ReforgedAdditions.DIAMOND_BATTLE_AXE = new ItemBattleAxe(ToolMaterial.DIAMOND));
		}
		
		if(GlobalValues.BOOMERANG) {
			registrationList.add(ReforgedAdditions.WOODEN_BOOMERANG = new ItemBoomerang(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_BOOMERANG = new ItemBoomerang(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_BOOMERANG = new ItemBoomerang(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_BOOMERANG = new ItemBoomerang(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_BOOMERANG = new ItemBoomerang(ToolMaterial.DIAMOND));
		}
		
		if(GlobalValues.SABRE) {
			registrationList.add(ReforgedAdditions.WOODEN_SABER = new ItemSaber(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_SABER = new ItemSaber(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_SABER = new ItemSaber(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_SABER = new ItemSaber(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_SABER = new ItemSaber(ToolMaterial.DIAMOND));
		}
		
		if(GlobalValues.KNIFE) {
			registrationList.add(ReforgedAdditions.WOODEN_KNIFE = new ItemKnife(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_KNIFE = new ItemKnife(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_KNIFE = new ItemKnife(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_KNIFE = new ItemKnife(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_KNIFE = new ItemKnife(ToolMaterial.DIAMOND));
		}
		
		if(GlobalValues.KATANA) {
			registrationList.add(ReforgedAdditions.WOODEN_KATANA = new ItemKatana(ToolMaterial.WOOD));
			registrationList.add(ReforgedAdditions.STONE_KATANA = new ItemKatana(ToolMaterial.STONE));
			registrationList.add(ReforgedAdditions.GOLDEN_KATANA = new ItemKatana(ToolMaterial.GOLD));
			registrationList.add(ReforgedAdditions.IRON_KATANA = new ItemKatana(ToolMaterial.IRON));
			registrationList.add(ReforgedAdditions.DIAMOND_KATANA = new ItemKatana(ToolMaterial.DIAMOND));
		}
		
		if(GlobalValues.JAVELIN) {
			registrationList.add(ReforgedAdditions.JAVELIN = new ItemJavelin());
		}
		
		if(GlobalValues.BLOWGUN) {
			registrationList.add(ReforgedAdditions.DART_NORMAL = new ItemDart("normal"));
			registrationList.add(ReforgedAdditions.DART_HUNGER = new ItemDart("hunger"));
			registrationList.add(ReforgedAdditions.DART_POISON = new ItemDart("poison"));
			registrationList.add(ReforgedAdditions.DART_POISON_STRONG = new ItemDart("poison_strong"));
			registrationList.add(ReforgedAdditions.DART_SLOW = new ItemDart("slowness"));
			registrationList.add(ReforgedAdditions.DART_WITHER = new ItemDart("wither"));
			registrationList.add(ReforgedAdditions.BLOWGUN = new ItemBlowGun());
		}
	}
	
	/**Registers all items out of the registrationList*/
	public static void registerItems() {
		
		for(Item item : registrationList) {
			GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		}
	}
	
	/**Registers all recipes of the registered items*/
	public static void registerRecipes() {
		
		for(Item item : registrationList) {
			if(item instanceof ItemExtension) {
				((ItemExtension) (item)).registerRecipes();
			}
		}
		
		if(GlobalValues.MUSKET) {
			
			GameRegistry.addRecipe(new ItemStack(ReforgedAdditions.GUN_STOCK),
					"   ",
					"ssp",
					"   ",
					's', Items.stick,
					'p', Blocks.planks);
		
			GameRegistry.addRecipe(new ItemStack(ReforgedAdditions.MUSKET_BARREL),
					"   ",
					"iif",
					"  i",
					'i', Items.iron_ingot,
					'f', Items.flint_and_steel);
		
			GameRegistry.addRecipe(new ItemStack(ReforgedAdditions.BLUNDERBUSS_BARREL),
					"i  ",
					" if",
					"i i",
					'i', Items.iron_ingot,
					'f', Items.flint_and_steel);
		}
	}
	
	/**Helper method for registering an Entity
	 * @param c The class of the Entity
	 * @param name The name for the Entity*/
	public static void registerEntity(Class c, String name) {
		EntityRegistry.registerModEntity(c, name, ++counterEntities, ReforgedMod.instance, 80, 3, true);
	}
	
	/**Helper method for binding a renderclass to a entity
	 * @param entityclass The class of the Entity
	 * @param renderclass The class of the Renderer*/
	public static void registerEntityRenderer(Class entityclass, IRenderFactory renderclass) {
		RenderingRegistry.registerEntityRenderingHandler(entityclass, renderclass);
	}
	
	/**Helper method for registering our EventHandler
	 * @param ReforgedEvents The instance of our EventHandler*/
	public static void registerEventHandler(ReforgedEvents eventclass) {
	    MinecraftForge.EVENT_BUS.register(eventclass);
	}
}