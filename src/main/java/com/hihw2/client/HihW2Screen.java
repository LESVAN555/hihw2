private void buildHudTab(int centerX) {
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD Overlay: " + (HudManager.hudEnabled ? "ON" : "OFF")),
                button -> {
                    HudManager.hudEnabled = !HudManager.hudEnabled;
                    button.setMessage(Text.literal("HUD Overlay: " + (HudManager.hudEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, 60, 200, 20).build());
    }

    private void buildPlayerTab(int centerX) {
        MinecraftClient client = MinecraftClient.getInstance();
        String name = client.player != null ? client.player.getName().getString() : "Unknown";
        int ping = 0;
        if (client.getNetworkHandler() != null && client.player != null) {
            var entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            if (entry != null) ping = entry.getLatency();
        }
        String health = client.player != null ? String.format("%.1f / %.1f", clienprivate void buildHudTab(int centerX) {
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD Overlay: " + (HudManager.hudEnabled ? "ON" : "OFF")),
                button -> {
                    HudManager.hudEnabled = !HudManager.hudEnabled;
                    button.setMessage(Text.literal("HUD Overlay: " + (HudManager.hudEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, 60, 200, 20).build());
    }

    private void buildPlayerTab(int centerX) {
        MinecraftClient client = MinecraftClient.getInstance();
        String name = client.player != null ? client.player.getName().getString() : "Unknown";
        int ping = 0;
        if (client.getNetworkHandler() != null && client.player != null) {
            var entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            if (entry != null) ping = entry.getLatency();
        }
        String health = client.player != null ? String.format("%.1f / %.1f", client.player.getHealth(), client.player.getMaxHealth()) : "N/A";
        String gamemode = client.interactionManager != null ? client.interactionManager.getCurrentGameMode().getTranslatableName().getString() : "N/A";

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Name: " + name),
                button -> {}
        ).dimensions(centerX - 100, 60, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Ping: " + ping + "ms"),
                button -> {}
        ).dimensions(centerX - 100, 85, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Health: " + health),
                button -> {}
        ).dimensions(centerX - 100, 110, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Mode: " + gamemode),
                button -> {}
        ).dimensions(centerX - 100, 135, 200, 20).build());
    }

    private void buildCrosshairTab(int centerX) {
        String[] names = {"Vanilla", "Dot", "Circle", "Cross"};
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Style: " + names[CrosshairManager.style]),
                button -> {
                    CrosshairManager.style = (CrosshairManager.style + 1) % names.length;
                    button.setMessage(Text.literal("Style: " + names[CrosshairManager.style]));
                }
        ).dimensions(centerX - 100, 60, 200, 20).build());
    }

    private void buildAboutTab(int centerX) {
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("User me w2 - v1.0.0"),
                button -> {}
        ).dimensions(centerX - 100, 60, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fabric client mod"),
                button -> {}
        ).dimensions(centerX - 100, 85, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
                                                                                                           }
