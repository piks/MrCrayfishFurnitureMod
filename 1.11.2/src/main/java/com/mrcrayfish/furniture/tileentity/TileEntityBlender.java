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
package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityBlender extends TileEntity implements ITickable, ISimpleInventory
{
	public ItemStack[] ingredients = new ItemStack[4];

	private boolean blending = false;
	public int progress = 0;
	public int drinkCount = 0;

	public String drinkName = "";
	public int healAmount;
	public int currentRed;
	public int currentGreen;
	public int currentBlue;

	public void addIngredient(ItemStack ingredient)
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			if (ingredients[i] == null)
			{
				ingredients[i] = ingredient.copy();
				break;
			}
		}
	}

	public void removeIngredient()
	{
		for (int i = ingredients.length - 1; i >= 0; i--)
		{
			if (ingredients[i] != null)
			{
				if (!world.isRemote)
				{
					EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0D, pos.getZ() + 0.5, ingredients[i]);
					world.spawnEntity(entityItem);
				}
				ingredients[i] = null;
				break;
			}
		}
		TileEntityUtil.markBlockForUpdate(world, pos);
	}

	public boolean isFull()
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			if (ingredients[i] == null)
			{
				return false;
			}
		}
		return true;
	}

	public ItemStack[] getIngredients()
	{
		return ingredients;
	}

	public boolean hasValidIngredients()
	{
		RecipeData data = RecipeAPI.getBlenderRecipeDataFromIngredients(ingredients);
		if (data == null)
		{
			return false;
		}
		drinkName = data.getDrinkName();
		healAmount = data.getHealAmount();
		currentRed = data.getRed();
		currentGreen = data.getGreen();
		currentBlue = data.getBlue();
		return true;
	}

	public void startBlending()
	{
		blending = true;
	}

	public boolean isBlending()
	{
		return blending;
	}

	public boolean hasDrink()
	{
		if (this.drinkCount > 0)
		{
			return true;
		}
		return false;
	}

	public ItemStack getDrink()
	{
		ItemStack cup = new ItemStack(FurnitureItems.itemDrink);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setIntArray("Colour", new int[] { currentRed, currentGreen, currentBlue });
		nbt.setInteger("HealAmount", healAmount);
		cup.setTagCompound(nbt);
		cup.setStackDisplayName(new String(drinkName));
		return cup;
	}

	int timer = 0;

	@Override
	public void update()
	{
		if (blending)
		{
			progress++;
			if (progress == 200)
			{
				clearIngredients();
				drinkCount = 6;
				progress = 0;
				blending = false;
				world.updateComparatorOutputLevel(pos, blockType);
			}

			if (timer == 20)
			{
				timer = 0;
			}
			if (timer == 0)
			{
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.blender, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
			}
			timer++;
		}
	}

	public void clearIngredients()
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			if (ingredients[i] != null)
			{
				if (ingredients[i].getItem().hasContainerItem(ingredients[i]))
				{
					if (!world.isRemote)
					{
						EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5, new ItemStack(ingredients[i].getItem().getContainerItem()));
						world.spawnEntity(entityItem);
					}
				}
				ingredients[i] = null;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		if (tagCompound.hasKey("Items"))
		{
			NBTTagList tagList = (NBTTagList) tagCompound.getTag("Items");
			this.ingredients = new ItemStack[4];

			for (int i = 0; i < tagList.tagCount(); ++i)
			{
				NBTTagCompound itemTag = (NBTTagCompound) tagList.getCompoundTagAt(i);
				byte slot = itemTag.getByte("Slot");

				if (slot >= 0 && slot < this.ingredients.length)
				{
					this.ingredients[slot] = new ItemStack(itemTag);
				}
			}
		}

		this.blending = tagCompound.getBoolean("Blending");
		this.progress = tagCompound.getInteger("Progress");
		this.drinkCount = tagCompound.getInteger("DrinkCount");
		this.drinkName = tagCompound.getString("DrinkName");
		this.healAmount = tagCompound.getInteger("HealAmount");
		this.currentRed = tagCompound.getInteger("CurrentRed");
		this.currentGreen = tagCompound.getInteger("CurrentGreen");
		this.currentBlue = tagCompound.getInteger("CurrentBlue");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.ingredients.length; ++i)
		{
			if (this.ingredients[i] != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("Slot", (byte) i);
				this.ingredients[i].writeToNBT(itemTag);
				tagList.appendTag(itemTag);
			}
		}
		tagCompound.setTag("Items", tagList);
		tagCompound.setBoolean("Blending", blending);
		tagCompound.setInteger("Progress", progress);
		tagCompound.setString("DrinkName", drinkName);
		tagCompound.setInteger("DrinkCount", drinkCount);
		tagCompound.setInteger("HealAmount", healAmount);
		tagCompound.setInteger("CurrentRed", currentRed);
		tagCompound.setInteger("CurrentGreen", currentGreen);
		tagCompound.setInteger("CurrentBlue", currentBlue);
		return tagCompound;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public NBTTagCompound getUpdateTag() 
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public int getSize()
	{
		return ingredients.length;
	}

	@Override
	public ItemStack getItem(int i)
	{
		return ingredients[i];
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < ingredients.length; i++)
		{
			ingredients[i] = null;
		}
	}
}
