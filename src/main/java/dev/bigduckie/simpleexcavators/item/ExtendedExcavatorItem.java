package dev.bigduckie.simpleexcavators.item;

import dev.bigduckie.simpleexcavators.data.ExcavatorData;
import dev.draylar.magna.api.BlockProcessor;
import dev.draylar.magna.item.ExcavatorItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class ExtendedExcavatorItem extends ExcavatorItem {

    private final ExcavatorData data;

    public ExtendedExcavatorItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int breakRadius, ExcavatorData data) {
        super(toolMaterial, attackDamage, attackSpeed, settings, breakRadius);
        this.data = data;
    }

    public ExcavatorData getData() {
        return data;
    }

    @Override
    public BlockProcessor getProcessor(World world, PlayerEntity player, BlockPos pos, ItemStack heldStack) {
        if (data.canSmelt()) {
            return (tool, input) -> {
                Optional<SmeltingRecipe> cooked = world.getRecipeManager().getFirstMatch(
                        RecipeType.SMELTING,
                        new SimpleInventory(input),
                        world
                );

                if (cooked.isPresent()) {
                    return cooked.get().getOutput(world.getRegistryManager()).copy();
                }

                return input;
            };
        } else {
            return super.getProcessor(world, player, pos, heldStack);
        }
    }
}
