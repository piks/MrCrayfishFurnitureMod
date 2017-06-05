/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.init;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.items.ItemCup;
import com.mrcrayfish.furniture.items.ItemEnvelope;
import com.mrcrayfish.furniture.items.ItemEnvelopeSigned;
import com.mrcrayfish.furniture.items.ItemGeneric;
import com.mrcrayfish.furniture.items.ItemKnife;
import com.mrcrayfish.furniture.items.ItemLog;
import com.mrcrayfish.furniture.items.ItemPackage;
import com.mrcrayfish.furniture.items.ItemPackageSigned;
import com.mrcrayfish.furniture.items.ItemRecipeBook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnitureItems
{
	/** Initial Furniture */
	public static Item itemFlesh, itemCookedFlesh, itemCoolPack;

	/** Garden Update */
	public static Item itemHammer;
	public static Item itemEnvelope, itemEnvelopeSigned;
	public static Item itemPackage, itemPackageSigned;

	/** Electronic Update */
	public static Item itemInkCartridge;
	public static Item itemSuperInkCartridge;
	/** Bathroom Update */

	/** Kitchen Update */
	public static Item itemKnife, itemCup, itemDrink, itemSoap, itemSoapyWater, itemSuperSoapyWater;

	/** Christmas Update */
	public static Item itemLog;

	/** Outdoor Update */
	public static Item itemSpatula, itemSausage, itemSausageCooked, itemKebab, itemKebabCooked, itemCrowBar;

	/** Food */
	public static Item itemBreadSlice, itemToast;

	/** Misc */
	public static Item itemRecipeBook;
	public static Item itemCrayfish;

	public static void init()
	{
		itemCoolPack = new ItemGeneric().setUnlocalizedName("item_cool_pack").setRegistryName("item_cool_pack");
		itemInkCartridge = new ItemGeneric().setUnlocalizedName("item_ink_cartridge").setRegistryName("item_ink_cartridge");
		itemSuperInkCartridge = new ItemGeneric().setUnlocalizedName("item_super_ink_cartridge").setRegistryName("item_super_ink_cartridge");
		itemFlesh = new ItemFood(1, 2, false).setUnlocalizedName("item_flesh").setRegistryName("item_flesh").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCookedFlesh = new ItemFood(4, 4, false).setUnlocalizedName("item_flesh_cooked").setRegistryName("item_flesh_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemEnvelope = new ItemEnvelope().setUnlocalizedName("item_envelope").setRegistryName("item_envelope");
		itemEnvelopeSigned = new ItemEnvelopeSigned().setUnlocalizedName("item_envelope_signed").setRegistryName("item_envelope_signed");
		itemPackage = new ItemPackage().setUnlocalizedName("item_package").setRegistryName("item_package");
		itemPackageSigned = new ItemPackageSigned().setUnlocalizedName("item_package_signed").setRegistryName("item_package_signed");
		itemHammer = new Item().setUnlocalizedName("item_hammer").setRegistryName("item_hammer");
		itemBreadSlice = new ItemFood(2, false).setUnlocalizedName("item_bread_slice").setRegistryName("item_bread_slice").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemToast = new ItemFood(4, false).setUnlocalizedName("item_toast").setRegistryName("item_toast").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKnife = new ItemKnife().setMaxDamage(100).setUnlocalizedName("item_knife").setRegistryName("item_knife");
		itemCup = new ItemCup(false).setUnlocalizedName("item_cup").setRegistryName("item_cup");
		itemDrink = new ItemCup(true).setUnlocalizedName("item_drink").setRegistryName("item_drink");
		itemSoap = new ItemGeneric().setUnlocalizedName("item_soap").setRegistryName("item_soap");
		itemSoapyWater = new ItemGeneric().setUnlocalizedName("item_soap_water").setRegistryName("item_soap_water").setContainerItem(Items.BUCKET).setMaxStackSize(1);
		itemSuperSoapyWater = new ItemGeneric().setUnlocalizedName("item_super_soap_water").setRegistryName("item_super_soap_water").setContainerItem(Items.BUCKET).setMaxStackSize(1);
		itemRecipeBook = new ItemRecipeBook().setUnlocalizedName("item_recipe_book").setRegistryName("item_recipe_book");
		itemCrayfish = new Item().setUnlocalizedName("item_crayfish").setRegistryName("item_crayfish").setMaxStackSize(1);
		itemLog = new ItemLog(FurnitureBlocks.fire_pit_off).setUnlocalizedName("item_log").setRegistryName("item_log").setMaxStackSize(16);
		itemSpatula = new Item().setUnlocalizedName("item_spatula").setRegistryName("item_spatula").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSausage = new ItemFood(1, false).setUnlocalizedName("item_sausage").setRegistryName("item_sausage").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemSausageCooked = new ItemFood(4, false).setUnlocalizedName("item_sausage_cooked").setRegistryName("item_sausage_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKebab = new ItemFood(1, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0), 0.3F).setUnlocalizedName("item_kebab").setRegistryName("item_kebab").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemKebabCooked = new ItemFood(4, false).setUnlocalizedName("item_kebab_cooked").setRegistryName("item_kebab_cooked").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
		itemCrowBar = new Item().setUnlocalizedName("item_crow_bar").setRegistryName("item_crow_bar").setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	public static void registerItems()
	{
		GameRegistry.register(itemFlesh);
		GameRegistry.register(itemCookedFlesh);
		GameRegistry.register(itemCoolPack);
		GameRegistry.register(itemHammer);
		GameRegistry.register(itemEnvelope);
		GameRegistry.register(itemEnvelopeSigned);
		GameRegistry.register(itemPackage);
		GameRegistry.register(itemPackageSigned);
		GameRegistry.register(itemInkCartridge);
		GameRegistry.register(itemSuperInkCartridge);
		GameRegistry.register(itemBreadSlice);
		GameRegistry.register(itemToast);
		GameRegistry.register(itemKnife);
		GameRegistry.register(itemCup);
		GameRegistry.register(itemDrink);
		GameRegistry.register(itemSoap);
		GameRegistry.register(itemSoapyWater);
		GameRegistry.register(itemSuperSoapyWater);
		GameRegistry.register(itemRecipeBook);
		GameRegistry.register(itemCrayfish);
		GameRegistry.register(itemLog);
		GameRegistry.register(itemSpatula);
		GameRegistry.register(itemSausage);
		GameRegistry.register(itemSausageCooked);
		GameRegistry.register(itemKebab);
		GameRegistry.register(itemKebabCooked);
		GameRegistry.register(itemCrowBar);
	}

	public static void registerRenders()
	{
		registerRender(itemFlesh);
		registerRender(itemCookedFlesh);
		registerRender(itemCoolPack);
		registerRender(itemHammer);
		registerRender(itemEnvelope);
		registerRender(itemEnvelopeSigned);
		registerRender(itemPackage);
		registerRender(itemPackageSigned);
		registerRender(itemInkCartridge);
		registerRender(itemSuperInkCartridge);
		registerRender(itemBreadSlice);
		registerRender(itemToast);
		registerRender(itemKnife);
		registerRender(itemCup);
		registerRender(itemDrink);
		registerRender(itemSoap);
		registerRender(itemSoapyWater);
		registerRender(itemSuperSoapyWater);
		registerRender(itemRecipeBook);
		registerRender(itemCrayfish);
		registerRender(itemLog);
		registerRender(itemSpatula);
		registerRender(itemSausage);
		registerRender(itemSausageCooked);
		registerRender(itemKebab);
		registerRender(itemKebabCooked);
		registerRender(itemCrowBar);
	}

	private static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	private static void registerRender(Item item, int maxMeta)
	{
		for (int i = 0; i < maxMeta; i++)
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
}
