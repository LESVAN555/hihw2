package com.hihw2.client;

import net.minecraft.client.MinecraftClient;

public final class ToggleSprintHandler {

    private static boolean sprintToggled = false;
    private static boolean wasPressed = false;

    private ToggleSprintHandler() {}

    public static void tick(MinecraftClient client) {
        if (HihW2Client.toggleSprintKey == null || client.player == null) return;

        boolean pressed = HihW2Client.toggleSprintKey.isPressed();
        if (pressed && !wasPressed) {
            sprintToggled = !sprintToggled;
        }
        wasPressed = pressed;

        if (sprintToggled && !client.player.isSprinting() && client.player.forwardSpeed > 0)
