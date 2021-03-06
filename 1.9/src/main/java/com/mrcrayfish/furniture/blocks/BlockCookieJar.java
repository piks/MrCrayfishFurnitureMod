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
package com.mrcrayfish.furniture.blocks;

import java.util.List;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCookieJar;
import com.mrcrayfish.furniture.util.TileEntityUtil;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCookieJar extends Block implements ITileEntityProvider
{
	public static final PropertyInteger COOKIE_COUNT = PropertyInteger.create("cookie_count", 0, 6);
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(4 * 0.0625, 0.0, 4 * 0.0625, 12 * 0.0625, 0.65, 12 * 0.0625);

	public BlockCookieJar(Material material)
	{
		super(material);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.GLASS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COOKIE_COUNT, Integer.valueOf(0)));
		this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			for (int i = 0; i < getMetaFromState(state); i++)
			{
				EntityItem cookie = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, new ItemStack(Items.COOKIE));
				world.spawnEntityInWorld(cookie);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		int metadata = getMetaFromState(state);
		if (heldItem != null)
		{
			if (heldItem.getItem() == Items.COOKIE && metadata < 6)
			{
				worldIn.setBlockState(pos, state.withProperty(COOKIE_COUNT, metadata + 1), 2);
				heldItem.stackSize--;
				worldIn.updateComparatorOutputLevel(pos, this);
				return true;
			}
		}
		if (metadata > 0)
		{
			worldIn.setBlockState(pos, state.withProperty(COOKIE_COUNT, metadata - 1), 2);
			if (!worldIn.isRemote)
			{
				EntityItem var14 = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, new ItemStack(Items.COOKIE));
				worldIn.spawnEntityInWorld(var14);
			}
			TileEntityUtil.markBlockForUpdate(worldIn, pos);
			return true;
		}
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return BOUNDING_BOX;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB bounds, List<AxisAlignedBB> list, Entity collidingEntity) 
	{
		super.addCollisionBoxToList(pos, bounds, list, BOUNDING_BOX);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCookieJar();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(COOKIE_COUNT, 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Integer) state.getValue(COOKIE_COUNT)).intValue();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(COOKIE_COUNT, meta);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { COOKIE_COUNT });
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) 
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) 
	{
		TileEntityCookieJar jar = (TileEntityCookieJar) world.getTileEntity(pos);
		return jar.getSize();
	}
}
