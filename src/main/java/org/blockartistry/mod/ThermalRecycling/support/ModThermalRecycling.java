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

package org.blockartistry.mod.ThermalRecycling.support;

import org.blockartistry.mod.ThermalRecycling.ItemManager;
import org.blockartistry.mod.ThermalRecycling.data.ItemInfo;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ModThermalRecycling extends ModPlugin {

	public ModThermalRecycling() {
		super(SupportedMod.THERMAL_RECYCLING);
	}

	@Override
	public void apply() {
		
		//////////////////////
		//
		// Add recipe blacklist items first
		// before processing!
		//
		//////////////////////
		
		ItemInfo.setRecipeIgnored(ItemManager.recyclingScrapBox, true);
		
		//////////////////////
		//
		// Process the recipes
		//
		//////////////////////
		
		// Process all registered recipes
		for(Object o: CraftingManager.getInstance().getRecipeList()) {
			
			IRecipe recipe = (IRecipe)o;
			ItemStack stack = recipe.getRecipeOutput();
			
			// Check to see if this item should have a recipe in
			// the list.  This does not mean that something later
			// on can't add one - just means by default it will
			// not be included.
			if(stack != null) {
				if(!ItemInfo.isRecipeIgnored(stack)) {
					recycler.useRecipe(stack).save();
				}
			}
		}
	}
}