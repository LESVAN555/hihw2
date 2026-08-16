package com.hihw2.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class HihW2Screen extends Screen {

    private int currentTab = 0;
    private static final String[] TABS = {"HUD", "Player", "Crosshair", "About"};

    public HihW2Screen() {
        super(Text.literal("User me w2"));
    }

    @Override
    protected void init() {
        int tabWidth = 80;
        int totalWidth = tabWidth * TABS.length;
        int startX = this.width / 2 - totalWidth / 2;

        for (int i = 0; i < TABS.length; i++) {
            final int tabIndex = i;
            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal(TABS[i]),
                    button -> {
                        this.currentTab = tabIndex;
                        this.clearAndInit();
                    }
            ).dimensions(startX + i * tabWidth, 25, tabWidth - 4, 20).build());
        }

        int centerX = this.width / 2;
        showTab(centerX);

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"),
                button -> this.close()
        ).dimensions(centerX - 75, this.height - 30, 150, 20).build());
    }

    private void showTab(int centerX) {
        if (currentTab == 0) {
            buildHudTab(centerX);
        } else if (currentTab == 1) {
            buildPlayerTab(centerX);
        } else if (currentTab == 2) {
            buildCrosshairTab(centerX);
        } else {
            buildAboutTab(centerX);
        }
    }

    private void buildHudTab(int centerX) {
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD Overlay: " + (HudManager.hudEnabled ? "ON" : "OFF")),
                button -> {
                    HudManager.hudEnabled = !HudManager.hudEnabled;
                    button.setMessage(Text.literal("HUD Overlay: " + (HudManager.hudEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, 60, 200, 20).build());
    this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Cape: " + (CosmeticManager.capeEnabled ? "ON" : "OFF")),
                button -> {
                    CosmeticManager.capeEnabled = !CosmeticManager.capeEnabled;
                    button.setMessage(Text.literal("Cape: " + (CosmeticManager.capeEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, 85, 200, 20).build());}

    private void buildPlayerTab(int centerX) {
        MinecraftClient client = MinecraftClient.getInstance();
        String name = client.player != null ? client.player.getName().getString() : "Unknown";
        int ping = 0;
        if (client.getNetworkHandler() != null && client.player != null) {
            var entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
            if (entry != null) ping = entry.getLatency();
        }
        String health = "N/A";
        if (client.player != null) {
            health = String.format("%.1f / %.1f", client.player.getHealth(), client.player.getMaxHealth());
        }
        String gamemode = "N/A";
        if (client.interactionManager != null) {
            gamemode = client.interactionManager.getCurrentGameMode().getTranslatableName().getString();
        }

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
    
        private static final net.minecraft.util.Identifier CAT_TEXTURE =
 net.minecraft.util.Identifier.of("hihw2", "foni-papik-pro-0c7d-p-kartinki-kot-iz-mainkrafta-na-prozrachnom-2.png");

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int imgSize = 200;
        int x = this.width / 2 - imgSize / 2;
        int y = this.height / 2 - imgSize / 2;
        context.drawTexture(CAT_TEXTURE, x, y, 0, 0, imgSize, imgSize, imgSize, imgSize);

        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
  }


    
    @Override
    public boolean shouldPause() {
        return false;
    }
          }
