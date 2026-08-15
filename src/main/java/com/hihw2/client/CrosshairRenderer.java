package com.hihw2.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public final class CrosshairRenderer {

    private CrosshairRenderer() {}

    public static void render(DrawContext ctx) {
        if (CrosshairManager.style == 0) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) return;

        int centerX = ctx.getScaledWindowWidth() / 2;
        int centerY = ctx.getScaledWindowHeight() / 2;
        int color = 0xFFFFFFFF;

        switch (CrosshairManager.style) {
            case 1:
                ctx.fill(centerX - 1, centerY - 1, centerX + 1, centerY + 1, color);
                break;
            case 2:
                drawCircle(ctx, centerX, centerY, 4, color);
                break;
            case 3:
                ctx.fill(centerX - 6, centerY - 1, centerX + 6, centerY + 1, color);
                ctx.fill(centerX - 1, centerY - 6, centerX + 1, centerY + 6, color);
                break;
            default:
                break;
        }
    }

    private static void drawCircle(DrawContext ctx, int cx, int cy, int radius, int color) {
        for (int angle = 0; angle < 360; angle += 10) {
            double rad = Math.toRadians(angle);
            int px = cx + (int) (radius * Math.cos(rad));
            int py = cy + (int) (radius * Math.sin(rad));
            ctx.fill(px, py, px + 1, py + 1, color);
        }
    }
}
