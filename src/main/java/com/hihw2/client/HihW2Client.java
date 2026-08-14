package com.hihw2.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HihW2Client implements ClientModInitializer {

    public static KeyBinding toggleHudKey;
    public static KeyBinding toggleSprintKey;
    public static KeyBinding zoomKey;
    public static KeyBinding sortInventoryKey;

    @Override
    public void onInitializeClient() {
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hihw2.toggle_hud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.hihw2"
        ));

        toggleSprintKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hihw2.toggle_sprint",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.hihw2"
        ));

        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hihw2.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.hihw2"
        ));

        sortInventoryKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hihw2.sort_inventory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_APOSTROPHE,
                "category.hihw2"
        ));

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            HudManager.render(drawContext, tickCounter);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            HudManager.tick(client);
            InventorySorter.handleKey(client);
            ZoomHandler.tick(client);
            ToggleSprintHandler.tick(client);
        });

        MinecraftClient.getInstance();
    }
}
