package dev.bigduckie.simpleexcavators.data;

import dev.bigduckie.simpleexcavators.item.ExtendedExcavatorItem;
import dev.bigduckie.simpleexcavators.material.CustomToolMaterial;
import dev.bigduckie.staticcontent.api.ContentData;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStackSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Set;

public class ExcavatorData implements ContentData {

    private final String id;
    private final int miningLevel;
    private final int durability;
    private final float blockBreakSpeed;
    private final float attackDamage;
    private final float attackSpeed;
    private final int enchantability;
    private final Identifier repairIngredient;
    private final boolean isFireImmune;
    private final boolean smelts;
    private final int breakRadius;
    private final boolean isExtra;
    private final int burnTime;
    private final boolean hasExtraKnockback;

    public static final Set<ItemStack> ENTRY_SET = ItemStackSet.create();

    public ExcavatorData(String id, int miningLevel, int durability, float blockBreakSpeed, float attackDamage, float attackSpeed, int enchantability, Identifier repairIngredient, boolean isFireImmune, boolean smelts, int breakRadius, boolean isExtra, int burnTime, boolean hasExtraKnockback, String group) {
        this.id = id;
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.blockBreakSpeed = blockBreakSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.isFireImmune = isFireImmune;
        this.smelts = smelts;
        this.breakRadius = breakRadius;
        this.isExtra = isExtra;
        this.burnTime = burnTime;
        this.hasExtraKnockback = hasExtraKnockback;
    }

    @Override
    public void register(Identifier identifier) {
        // only register if the excavator is not extra (wood -> diamond), or if it is extra (lapis, emerald, ...) and the option is enabled
        if (!isExtra() || isExtra() && dev.bigduckie.simpleexcavators.SimpleExcavators.CONFIG.enableExtraMaterials) {

            // setup settings
            Item.Settings settings = new Item.Settings();
            if (isFireImmune()) {
                settings.fireproof();
            }

            // check for excavator autosmelt
            // create excavator with settings
            ExtendedExcavatorItem excavatorItem = new ExtendedExcavatorItem(
                    CustomToolMaterial.from(this),
                    0,
                    getAttackSpeed() == 0 ? -2.4f : getAttackSpeed(),
                    settings,
                    getBreakRadius() == 0 ? 1 : getBreakRadius(),
                    this
            );

            // burn time for furnace fuel
            if (getBurnTime() > 0) {
                FuelRegistry.INSTANCE.add(excavatorItem, getBurnTime());
            }

            // add excavator to tag
            String path = getId() + "_excavator";
            Identifier registryID = path.contains(":") ? new Identifier(path) : dev.bigduckie.simpleexcavators.SimpleExcavators.id(path);
            Registry.register(Registries.ITEM, registryID, excavatorItem);

            // add excavator to item group
            ENTRY_SET.add(excavatorItem.getDefaultStack());
        }
    }

    public boolean hasExtraKnockback() {
        return hasExtraKnockback;
    }

    public boolean canSmelt() {
        return smelts;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public String getId() {
        return id;
    }

    public int getBreakRadius() {
        return breakRadius;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getDurability() {
        return durability;
    }

    public float getBlockBreakSpeed() {
        return blockBreakSpeed * (float) dev.bigduckie.simpleexcavators.SimpleExcavators.CONFIG.breakSpeedMultiplier;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public Identifier getRepairIngredient() {
        return repairIngredient;
    }

    public boolean isFireImmune() {
        return isFireImmune;
    }
}
