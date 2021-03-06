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

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.blockartistry.mod.ThermalRecycling.data.CompostIngredient;
import org.blockartistry.mod.ThermalRecycling.data.ItemData;
import org.blockartistry.mod.ThermalRecycling.data.ScrapHandler;
import org.blockartistry.mod.ThermalRecycling.data.ScrapValue;
import org.blockartistry.mod.ThermalRecycling.support.handlers.ForestryFarmScrapHandler;
import org.blockartistry.mod.ThermalRecycling.support.recipe.RecipeDecomposition;
import org.blockartistry.mod.ThermalRecycling.util.ItemStackHelper;

public final class ModForestry extends ModPlugin {

	static final String[] recipeIgnoreList = new String[] { "log1:*", "log2:*",
			"log3:*", "log4:*", "log5:*", "log6:*", "log7:*", "log8:*",
			"fireproofLog1:*", "fireproofLog2:*", "fireproofLog3:*",
			"fireproofLog4:*", "fireproofLog5:*", "fireproofLog6:*",
			"fireproofLog7:*", "fireproofLog8:*", "planks:*", "planks2:*",
			"fireproofPlanks1:*", "fireproofPlanks2:*", "slabs1:*", "slabs2:*",
			"slabs3:*", "slabs4:*", "fences:*", "fences2:*", "stairs:*",
			"stamps:*", "letters", "crate", "waxCast", "apiculture:*",
			"arboriculture:*", "lepidopterology:*", "soil:*", "honeyedSlice",
			"beeCombs:*", "apatite", "fertilizerCompound",
			"fertilizerBio", "carton", "pipette", "scoop",
			"catalogue", "soil:*", "core:1", "mulch", };

	static final String[] scrapValuesNone = new String[] { "log1:*", "log2:*",
			"log3:*", "log4:*", "log5:*", "log6:*", "log7:*", "log8:*",
			"fireproofLog1:*", "fireproofLog2:*", "fireproofLog3:*",
			"fireproofLog4:*", "fireproofLog5:*", "fireproofLog6:*",
			"fireproofLog7:*", "fireproofLog8:*", "waxCapsule",
			"refractoryEmpty", "beeDroneGE:*", "propolis:*", "sapling:*",
			"phosphor", "beeswax", "refractoryWax", "fruits:*",
			"honeyDrop:*", "honeydew", "royalJelly", "waxCast",
			"beeCombs:*", "woodPulp", "oakStick", "carton", "planks:*",
			"planks2:*", "fireproofPlanks1:*", "fireproofPlanks2:*",
			"slabs1:*", "slabs2:*", "slabs3:*", "slabs4:*", "fences:*",
			"fences2:*", "stairs:*", "stamps:*", "letters", "crate",
			"leaves:*", "stained:*", "fertilizerCompound",
			"fertilizerBio", "pipette", "scoop", "catalogue",
			"honeyedSlice", "soil:*", "stump:*", "mushroom:*", "saplingGE:*",
			"apiculture:2", "ash", "mulch", "peat", "brokenBronzeShovel",
			"brokenBronzePickaxe", "item.PipeItemsPropolis:0"

	};

	static final String[] scrapValuesPoor = new String[] { "beeLarvaeGE:*",
			"pollen:*", "apatite", };

	static final String[] scrapValuesStandard = new String[] { "butterflyGE:*",
			"beePrincessGE:*", "beeQueenGE:*", };

	static final String[] scrapValuesSuperior = new String[] { "core:0",
			"hardenedMachine", "treealyzer", "beealyzer", "flutterlyzer",
			"frameProven" };

	public ModForestry() {
		super(SupportedMod.FORESTRY);
	}

	protected void registerForestryRecipes(final Map<Object[], Object[]> entry) {
		for (final Entry<Object[], Object[]> e : entry.entrySet()) {
			if (e.getValue().length == 1
					&& e.getValue()[0] instanceof ItemStack) {
				final ItemStack stack = (ItemStack) e.getValue()[0];
				if (!ItemData.isRecipeIgnored(stack))
					recycler.input(stack)
							.useRecipe(
									RecipeDecomposition.decomposeForestry(
											stack, e.getKey())).save();
			}
		}
	}

	@Override
	public void apply() {

		registerRecipesToIgnore(recipeIgnoreList);
		registerScrapValues(ScrapValue.NONE, scrapValuesNone);
		registerScrapValues(ScrapValue.POOR, scrapValuesPoor);
		registerScrapValues(ScrapValue.STANDARD, scrapValuesStandard);
		registerScrapValues(ScrapValue.SUPERIOR, scrapValuesSuperior);

		registerRecycleToWoodDust(1, "log1:*", "log2:*", "log3:*", "log4:*",
				"log5:*", "log6:*", "log7:*", "log8:*", "fireproofLog2:*",
				"fireproofLog3:*", "fireproofLog4:*", "fireproofLog5:*",
				"fireproofLog6:*", "fireproofLog7:*", "fireproofLog8:*");
		registerRecycleToWoodDust(2, "planks:*", "planks2:*",
				"fireproofPlanks1:*", "fireproofPlanks2:*");
		registerRecycleToWoodDust(8, "sapling", "saplingGE");
		registerRecycleToWoodDust(16, "fruits:*");
		registerRecycleToWoodDust(32, "propolis:*");

		registerCompostIngredient(CompostIngredient.BROWN, "sapling",
				"saplingGE", "leaves:*", "soil:*", "ash", "mulch");
		registerCompostIngredient(CompostIngredient.GREEN, "fruits:*");

		registerPulverizeToDirt("sapling", 0, 0);

		// Hook for farm blocks
		final ForestryFarmScrapHandler handler = new ForestryFarmScrapHandler();
		ScrapHandler.registerHandler(
				ItemStackHelper.getItemStack("Forestry:ffarm:*"), handler);

		// Scan the item registry looking for "crated" things - we want
		// to blacklist recipes and set scrap value to POOR. Should
		// get something for the effort of making these crates.
		for (final Object o : Item.itemRegistry.getKeys()) {
			final String itemName = (String) o;
			if (itemName.startsWith("Forestry:crated")) {
				final ItemStack stack = ItemStackHelper.getItemStack(itemName);
				final ItemData data = ItemData.get(stack);
				data.setIgnoreRecipe(true);
				data.setScrubFromOutput(true);
				data.setValue(ScrapValue.POOR);
				ItemData.put(stack, data);
			}
		}

		// Dig into the Forestry crafting data and extract additional recipes
		registerForestryRecipes(forestry.api.recipes.RecipeManagers.carpenterManager
				.getRecipes());
		registerForestryRecipes(forestry.api.recipes.RecipeManagers.fabricatorManager
				.getRecipes());

		pulverizer.setEnergy(1200).append("Forestry:saplingGE", 8)
				.output(Blocks.dirt).save();

		pulverizer.setEnergy(1200).append("Forestry:sapling", 8)
				.output(Blocks.dirt).save();

		// Machine casings
		sawmill.append("Forestry:impregnatedCasing").output(Blocks.planks, 32)
				.save();
		pulverizer.append("Forestry:sturdyMachine").output("ingotBronze", 8)
				.save();
		pulverizer.append("Forestry:hardenedMachine").output("ingotBronze", 8)
				.secondaryOutput(Items.diamond, 4).save();

		// Survivalist tools
		pulverizer.append("Forestry:bronzePickaxe", "Forestry:kitPickaxe")
				.output("dustBronze", 3).save();
		pulverizer.append("Forestry:bronzeShovel", "Forestry:kitShovel")
				.output("dustBronze").save();

		// Misc
		pulverizer.setEnergy(200).append("Forestry:canEmpty")
				.output("nuggetTin", 2).secondaryOutput("nuggetTin").chance(10)
				.save();

		// Glass
		pulverizer.setEnergy(3200)
				.appendSubtypeRange("Forestry:stained", 0, 15)
				.output(Blocks.sand).save();
		
		// Pile of Rubble - add apatite, empty can, and scoop
		registerPileOfRubbleDrop(1, 3, 5, "apatite");
		registerPileOfRubbleDrop(1, 2, 3, "canEmpty");
		registerPileOfRubbleDrop(1, 1, 2, "scoop");
	}
}