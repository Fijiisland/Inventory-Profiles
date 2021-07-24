/*
package org.anti_ad.mc.ipnext.mixin;

import net.minecraft.world.entity.player.Inventory; //net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.item.Item; //net.minecraft.world.item.ItemStack //net.minecraft.item.ItemStack;
import net.minecraft.core.NonNullList; //net.minecraft.util.NonNullList;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class MixinPlayerInventory {


    @Shadow @Final public NonNullList<ItemStack> items;

    @Inject(at = @At(value = "HEAD", target = "Lnet.minecraft.world.entity.player.Inventory //net.minecraft.entity.player.PlayerInventory;getFreeSlot()I"),
            method = "getFreeSlot",
            cancellable = true)
    public void getEmptySlot(CallbackInfoReturnable<Integer> info) {
        for(int i = 0; i < this.items.size(); ++i) {
            if (!LockSlotsHandler.INSTANCE.isSlotLocked(i)) {
                if (((ItemStack) this.items.get(i)).isEmpty()) {
                    info.setReturnValue(i);
                    return;
                }
            }
        }
        info.setReturnValue(-1);
    }
}
*/