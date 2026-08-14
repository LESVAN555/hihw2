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
    }

    public static void render(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!hudEnabled || client.player == null || client.options.hudHidden) return;

        int x = 6;
        int y = 6;
        int lineHeight = 10;
        int color = 0xFFFFFF;

        y = drawLine(ctx, "FPS: " + client.getCurrentFps(), x, y, lineHeight, color);
        y = drawLine(ctx, formatCoords(client.player), x, y, lineHeight, color);
        y = drawLine(ctx, "Время: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), x, y, lineHeight, color);
        y = drawLine(ctx, "CPS: " + clickTimestamps.size(), x, y, lineHeight, color);
        y = drawLine(ctx, "Сессия: " + formatSession(), x, y, lineHeight, color);
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
    }

    private static String formatSession() {
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
