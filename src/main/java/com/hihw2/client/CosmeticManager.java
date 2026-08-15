package com.hihw2.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public final class CosmeticManager {

    public static boolean capeEnabled = false;

    private static final Identifier TEXTURE = Identifier.of("hihw2", "textures/cape/simple_cape.png");

    private CosmeticManager() {}

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (!capeEnabled) return;

            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;
            if (player == null) return;

            MatrixStack matrices = context.matrixStack();
            VertexConsumerProvider.Immediate immediate =
                    client.getBufferBuilders().getEntityVertexConsumers();

            Vec3d camPos = context.camera().getPos();
            double px = player.getX() - camPos.x;
            double py = player.getY() - camPos.y;
            double pz = player.getZ() - camPos.z;

            matrices.push();
            matrices.translate(px, py + 1.5, pz);
            matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(-player.getYaw()));
            matrices.translate(0, 0, 0.15);

            var vc = immediate.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
            var entry = matrices.peek();

            float x1 = -0.25f, x2 = 0.25f;
            float y1 = 0f, y2 = -0.6f;

            addVertex(vc, entry, x1, y1, 0, 0, 0);
            addVertex(vc, entry, x2, y1, 0, 1, 0);
            addVertex(vc, entry, x2, y2, 0, 1, 1);
            addVertex(vc, entry, x1, y2, 0, 0, 1);

            matrices.pop();
            immediate.draw();
        });
    }

    private static void addVertex(net.minecraft.client.render.VertexConsumer vc, MatrixStack.Entry entry, float x, float y, float z, float u, float v) {
        vc.vertex(entry, x, y, z)
                .color(255, 255, 255, 255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(15728880)
                .normal(entry, 0, 0, 1);
    }
              }
