package dev.bigduckie.simpleexcavators;

import dev.bigduckie.simpleexcavators.config.SimpleExcavatorsConfig;
import dev.bigduckie.simpleexcavators.data.ExcavatorData;
import dev.bigduckie.simpleexcavators.item.ExtendedExcavatorItem;
import dev.bigduckie.staticcontent.StaticContent;
import dev.draylar.magna.api.optional.MagnaOptionals;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class SimpleExcavators implements ModInitializer {
    public static final String MOD_ID = "simple-excavators";
    public static SimpleExcavatorsConfig CONFIG = OmegaConfig.register(SimpleExcavatorsConfig.class);
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, id("group"));

    @Override
    public void onInitialize() {
        MagnaOptionals.optInForCurse();
        StaticContent.load(id("excavators"), ExcavatorData.class);
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder().displayName(Text.translatable("itemGroup.simple-excavators.group")).icon(() -> new ItemStack(Registries.ITEM.get(id("diamond_excavator")))).entries((context, entries) -> entries.addAll(ExcavatorData.ENTRY_SET)).build());
        registerCallbackHandlers();
    }

    private void registerCallbackHandlers() {
        AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            ItemStack handStack = playerEntity.getMainHandStack();
            if (handStack.getItem() instanceof ExtendedExcavatorItem extendedExcavatorItem) {
                // set entity on fire if this excavator smelts blocks
                if (extendedExcavatorItem.getData().canSmelt()) {
                    entity.setOnFireFor(4);
                }
            }

            return ActionResult.PASS;
        });
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}