package com.hihw2.mixin;

import com.hihw2.client.CosmeticCapeLayer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void hihw2$addCosmeticLayer(net.minecraft.client.render.entity.EntityRendererFactory.Context context, boolean slim, CallbackInfo ci) {
        ((PlayerEntityRenderer) (Object) this).addFeature(new CosmeticCapeLayer((PlayerEntityRenderer) (Object) this));
    }
}
