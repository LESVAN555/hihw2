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

        if (currentTab == 0) {
            buildHudTab(centerX);
        } else if (currentTab == 1) {
            buildPlayerTab(centerX);
        } else if (currentTab == 2) {
            buildCrosshairTab(centerX);
        } else {
            buildAboutTab(centerX);
        }

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"),
                button -> this.close()
        ).dimensions(centerX - 75, this.height - 30, 150, 20).build());
    }
