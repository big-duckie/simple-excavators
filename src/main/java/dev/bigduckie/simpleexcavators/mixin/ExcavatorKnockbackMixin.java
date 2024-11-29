package dev.bigduckie.simpleexcavators.mixin;

import dev.bigduckie.simpleexcavators.item.ExtendedExcavatorItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class ExcavatorKnockbackMixin {

    private static final int SLIME_KNOCKBACK_MODIFIER = 3;

    @Inject(at = @At("HEAD"), method = "getKnockback", cancellable = true)
    private static void getKnockback(LivingEntity livingEntity, CallbackInfoReturnable<Integer> info) {
        ItemStack heldStack = livingEntity.getMainHandStack();
        if(heldStack.getItem() instanceof ExtendedExcavatorItem excavatorItem) {
            // check data associated with excavator item for extra kb
            if(excavatorItem.getData().hasExtraKnockback()) {
                info.setReturnValue(SLIME_KNOCKBACK_MODIFIER);
                info.cancel();
            }
        }
    }
}