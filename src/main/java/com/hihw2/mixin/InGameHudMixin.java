package com.hihw2.mixin;

import com.hihw2.client.CrosshairManager;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void hihw2$hideVanillaCrosshair(CallbackInfo ci) {
        if (CrosshairManager.style != 0) {
            ci.cancel();
        }
    }
}
