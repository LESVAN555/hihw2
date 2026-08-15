package com.hihw2.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;

public final class HudManager {

    public static boolean hudEnabled = true;

    private static final Deque<Long> clickTimestamps = new ArrayDeque<>();
    private static boolean lastAttackState = false;

    private static long sessionStartMillis = -1;

    private HudManager() {}

    public static void tick(MinecraftClient client) {
        if (client.player == null) {
            sessionStartMillis = -1;
            return;
        }
        if (sessionStartMillis < 0) {
            sessionStartMillis = System.currentTimeMillis();
        }

        boolean attackDown = InputUtil.isKeyPressed(
                client.getWindow().getHandle(),
                GLFW.GLFW_MOUSE_BUTTON_LEFT
        );
        long now = System.currentTimeMillis();
        if (attackDown && !lastAttackState) {
            clickTimestamps.addLast(now);
        }
        lastAttackState = attackDown;
        while (!clickTimestamps.isEmpty() && now - clickTimestamps.peekFirst() > 1000) {
            clickTimestamps.pollFirst();
        }
    }public static void render(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!hudEnabled || client.player == null || client.options.hudHidden) return;

        int x = 6;
        int y = 6;
        int lineHeight = 10;
        int color = 0xFFFFFF;

        y = drawLine(ctx, "FPS: " + client.getCurrentFps(), x, y, lineHeight, color);
        y = drawLine(ctx, formatCoords(client.player), x, y, lineHeight, color);
        y = drawLine(ctx, "Time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), x, y, lineHeight, color);
        y = drawLine(ctx, "CPS: " + clickTimestamps.size(), x, y, lineHeight, color);
        y = drawLine(ctx, "Session: " + formatSession(), x, y, lineHeight, color);
        y = drawArmor(ctx, client.player, x, y, lineHeight, color);
        y = drawPotions(ctx, client.player, x, y, lineHeight, color);

        drawKeystrokes(ctx, client);
    }

    private static int drawLine(DrawContext ctx, String text, int x, int y, int lineHeight, int color) {
        ctx.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal(text), x, y, color);
        return y + lineHeight;
    }

    private static String formatCoords(PlayerEntity player) {
        return String.format("XYZ: %.1f / %.1f / %.1f", player.getX(), player.getY(), player.getZ());
    }private static String formatSession() {
        if (sessionStartMillis < 0) return "00:00";
        long seconds = (System.currentTimeMillis() - sessionStartMillis) / 1000;
        long m = seconds / 60;
        long s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    private static int drawArmor(DrawContext ctx, PlayerEntity player, int x, int y, int lineHeight, int color) {
        int durabilitySum = 0;
        int maxSum = 0;
        int pieces = 0;
        for (ItemStack stack : player.getArmorItems()) {
            if (!stack.isEmpty()) {
                pieces++;
                maxSum += stack.getMaxDamage();
                durabilitySum += (stack.getMaxDamage() - stack.getDamage());
            }
        }
        String line = pieces == 0
                ? "Armor: none"
                : "Armor: " + (maxSum == 0 ? 100 : (durabilitySum * 100 / Math.max(maxSum, 1))) + "%";
        return drawLine(ctx, line, x, y, lineHeight, color);
    }

    private static int drawPotions(DrawContext ctx, PlayerEntity player, int x, int y, int lineHeight, int color) {
        for (StatusEffectInstance effect : player.getStatusEffects()) {
            String name = Text.translatable(effect.getTranslationKey()).getString();
            int seconds = effect.getDuration() / 20;
            y = drawLine(ctx, name + " " + (effect.getAmplifier() + 1) + " - " + seconds + "s", x, y, lineHeight, color);
        }
        return y;
    }

    private static void drawKeystrokes(DrawContext ctx, MinecraftClient client) {
        GameOptions o = client.options;
        int size = 20;
        int gap = 2;
        int baseX = ctx.getScaledWindowWidth() - (size * 3 + gap * 2) - 10;
        int baseY = ctx.getScaledWindowHeight() - (size * 2 + gap) - 10;

        drawKey(ctx, "W", baseX + size + gap, baseY, size, o.forwardKey.isPressed());
        drawKey(ctx, "A", baseX, baseY + size + gap, size, o.leftKey.isPressed());
        drawKey(ctx, "S", baseX + size + gap, baseY + size + gap, size, o.backKey.isPressed());
        drawKey(ctx, "D", baseX + (size + gap) * 2, baseY + size + gap, size, o.rightKey.isPressed());
    }

    private static void drawKey(DrawContext ctx, String label, int x, int y, int size, boolean active) {
        int bg = active ? 0xAA3D7BFF : 0x66000000;
        ctx.fill(x, y, x + size, y + size, bg);
        int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(label);
        ctx.drawTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.literal(label),
                x + (size - textWidth) / 2,
                y + (size - 8) / 2,
                0xFFFFFF
        );
    }
                  }
