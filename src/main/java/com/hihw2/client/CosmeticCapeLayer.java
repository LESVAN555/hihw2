package com.hihw2.client;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CosmeticCapeLayer extends FeatureRenderer<PlayerEntityRenderState, net.minecraft.client.render.entity.model.PlayerEntityModel> {

    private static final Identifier TEXTURE = Identifier.of("hihw2", "textures/cape/simple_cape.png");

    public CosmeticCapeLayer(FeatureRendererContext<PlayerEntityRenderState, net.minecraft.client.render.entity.model.PlayerEntityModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        if (!CosmeticManager.capeEnabled) return;

        matrices.push();
        matrices.translate(0.0, 0.0, 0.125);
        matrices.scale(1.0f, 1.0f, 1.0f);

        var vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        var matrixEntry = matrices.peek();

        float x1 = -4f / 16f, x2 = 4f / 16f;
        float y1 = 0f, y2 = -10f / 16f;
        float z = 0f;

        addVertex(vertexConsumer, matrixEntry, x1, y1, z, 0, 0, light);
        addVertex(vertexConsumer, matrixEntry, x2, y1, z, 1, 0, light);
        addVertex(vertexConsumer, matrixEntry, x2, y2, z, 1, 1, light);
        addVertex(vertexConsumer, matrixEntry, x1, y2, z, 0, 1, light);

        matrices.pop();
    }

    private void addVertex(net.minecraft.client.render.VertexConsumer vc, MatrixStack.Entry entry, float x, float y, float z, float u, float v, int light) {
        vc.vertex(entry, x, y, z)
                .color(255, 255, 255, 255)
                .texture(u, v)
                .overlay(net.minecraft.client.render.OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, 0, 0, 1);
    }
}
