/*
 * This file is part of ThermalRecycling, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.ThermalRecycling.blocks;

import java.util.Random;

import org.blockartistry.mod.ThermalRecycling.CreativeTabManager;
import org.blockartistry.mod.ThermalRecycling.ModOptions;
import org.blockartistry.mod.ThermalRecycling.ThermalRecycling;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackHelper;
import org.blockartistry.mod.ThermalRecycling.util.XorShiftRandom;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public final class PileOfRubble extends Block {

	private static final Random random = XorShiftRandom.shared;
	private static final int ITEM_DAMAGE = 20;
	private static final String CHEST_PILE_OF_RUBBLE = "pileOfRubble";
	private static final ChestGenHooks rubbleContent = ChestGenHooks
			.getInfo(CHEST_PILE_OF_RUBBLE);
	
	public static void addRubbleDrop(final ItemStack stack, final int min, final int max, final int weight) {
		rubbleContent.addItem(new WeightedRandomChestContent(stack, min, max, weight));
	}
	
	public static void addRubbleDrop(final Item item, final int min, final int max, final int weight) {
		rubbleContent.addItem(new WeightedRandomChestContent(item, 0, min, max, weight));
	}

	public static void addRubbleDrop(final Block block, final int min, final int max, final int weight) {
		rubbleContent.addItem(new WeightedRandomChestContent(Item.getItemFromBlock(block), 0, min, max, weight));
	}

	static {

		addRubbleDrop(Blocks.cobblestone, 1, 4, 12);
		addRubbleDrop(Blocks.stone, 1, 2, 9);
		addRubbleDrop(Items.coal, 1, 3, 8);
		addRubbleDrop(Blocks.gravel, 1, 2, 9);
		addRubbleDrop(Blocks.sand, 1, 2, 8);
		addRubbleDrop(Blocks.dirt, 1, 3, 10);
		addRubbleDrop(Blocks.clay, 1, 1, 7);

		addRubbleDrop(Items.bread, 1, 3, 8);
		addRubbleDrop(Items.cooked_beef, 1, 3, 6);
		addRubbleDrop(Items.rotten_flesh, 1, 2, 5);
		addRubbleDrop(Items.bone, 1, 2, 5);
		addRubbleDrop(Blocks.torch, 1, 8, 8);
		addRubbleDrop(Blocks.iron_ore, 1, 3, 5);
		addRubbleDrop(Blocks.gold_ore, 1, 2, 3);
		addRubbleDrop(Items.redstone, 1, 2, 3);
		addRubbleDrop(Items.diamond, 1, 1, 1);
		addRubbleDrop(Items.emerald, 1, 1, 1);
		addRubbleDrop(Blocks.tnt, 1, 1, 4);
		
		addRubbleDrop(new ItemStack(Items.iron_pickaxe, 1, ITEM_DAMAGE), 1, 1, 4);
		addRubbleDrop(new ItemStack(Items.iron_helmet, 1, ITEM_DAMAGE), 1, 1, 3);
		addRubbleDrop(new ItemStack(Items.iron_sword, 1, ITEM_DAMAGE), 1, 1, 3);
	}

	@SideOnly(Side.CLIENT)
	protected IIcon icon;

	public PileOfRubble() {
		super(Material.rock);

		setBlockName("PileOfRubble");
		setCreativeTab(CreativeTabManager.tab);

		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(soundTypeStone);

		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.375F, 0.9375F);

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		// http://www.minecraftforum.net/forums/archive/tutorials/928513-creating-mods-mcp-getrendertype
		return 6; // Crops
	}

	@Override
	protected boolean canSilkHarvest() {
		// No silk touch - gotta break it
		return false;
	}

	@Override
	public int quantityDropped(final Random random) {
		// Prevent the Pile of Scrap from dropping
		return 0;
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block,
			final int meta) {

		if (!world.isRemote) {
			final int dropCount = ModOptions.getRubblePileDropCount();
			for (int i = 0; i < dropCount; i++) {
				final ItemStack stack = rubbleContent.getOneItem(random);
				if (stack != null) {
					ItemStackHelper.spawnIntoWorld(world, stack, x, y, z);
				}

			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(final IIconRegister iconRegister) {
		icon = iconRegister.registerIcon(ThermalRecycling.MOD_ID
				+ ":pileOfRubble");
	}

	// http://www.minecraftforge.net/forum/index.php/topic,13626.0.html

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(final int side, final int metadata) {
		return icon;
	}

	public boolean canBlockStay(final World world, final int x, final int y, final int z) {
		// Make sure the block underneath is solid
		return world.getBlock(x, y - 1, z).getMaterial().isSolid();
	}

	public void register() {
		GameRegistry.registerBlock(this, getUnlocalizedName());
	}
}
