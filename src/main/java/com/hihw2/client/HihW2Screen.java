package com.hihw2.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class HihW2Screen extends Screen {

    public HihW2Screen() {
        super(Text.literal("User me w2"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD: " + (HudManager.hudEnabled ? "ON" : "OFF")),
                button -> {
                    HudManager.hudEnabled = !HudManager.hudEnabled;
                    button.setMessage(Text.literal("HUD: " + (HudManager.hudEnabled ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 75, startY, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Done"),
                button -> this.close()
        ).dimensions(centerX - 75, startY + 100, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
