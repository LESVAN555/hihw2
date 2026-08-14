package com.hihw2.client;

import net.minecraft.client.MinecraftClient;

public final class ZoomHandler {

    private static double savedFov = -1;
    private static final double ZOOM_FOV = 15.0;

    private ZoomHandler() {}

    public static void tick(MinecraftClient client) {
        if (HihW2Client.zoomKey == null || client.options == null) return;

        boolean held = HihW2Client.zoomKey.isPressed();

        if (held && saved
