package com.hihw2.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class InventorySorter {

    private static boolean wasPressed = false;

    private InventorySorter() {}

    public static void handleKey(MinecraftClient client) {
        if (HihW2Client.sortInventoryKey == null || client.player == null) return;

        boolean pressed = HihW2Client.sortInventoryKey.isPressed();
        if (pressed && !wasPressed) {
            sort(client);
        }
        wasPressed = pressed;
    }

    private static void sort(MinecraftClient client) {
        PlayerInventory inv = client.player.getInventory();

        List<ItemStack> items = new ArrayList<>();
        for (int i = 9; i < 36; i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                items.add(stack.copy());
                inv.setStack(i, ItemStack.EMPTY);
            }
        }

        items.sort(Comparator.comparing(s -> s.getName().getString()));

        for (int i = 0; i < items.size() && (9 + i) < 36; i++) {
            inv.setStack(9 + i, items.get(i));
        }

        if (client.player != null) {
            client.player.sendMessage(Text.literal("§b[HiH w2] §fИнвентарь отсортирован (локально)."), true);
        }
    }
}
