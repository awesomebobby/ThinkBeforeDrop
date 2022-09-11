package iafenvoy.thinkbeforedrop;

import iafenvoy.thinkbeforedrop.config.Configs;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class DropManager {
    private static long lastDropTime = 0;
    private static int lastSlot = -1;
    private static boolean dropped = false;

    private static boolean shouldHandleDrop(ItemStack stack) {
        if (!Configs.INSTANCE.open.getBooleanValue()) return false;
        Item item = stack.getItem();
        Block block = null;
        if (item instanceof BlockItem)
            block = ((BlockItem) item).getBlock();
        String name = Registry.ITEM.getId(item).getPath();
        if (Configs.INSTANCE.excludeItems.getStrings().contains(name))
            return false;
        if (Configs.INSTANCE.weapon.getBooleanValue())
            if (item instanceof SwordItem || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem || item instanceof ArrowItem)
                return true;
        if (Configs.INSTANCE.tool.getBooleanValue())
            if (item instanceof AxeItem || item instanceof PickaxeItem || item instanceof ShovelItem || item instanceof HoeItem)
                return true;
        if (Configs.INSTANCE.shulker_box.getBooleanValue())
            if (block != null)
                if (block instanceof ShulkerBoxBlock)
                    return true;
        if (Configs.INSTANCE.armor.getBooleanValue())
            if (item instanceof ArmorItem || item instanceof ElytraItem)
                return true;
        if (Configs.INSTANCE.ore.getBooleanValue())
            if (block != null)
                if (block instanceof OreBlock)
                    return true;
        if (Configs.INSTANCE.disc.getBooleanValue())
            if (item instanceof MusicDiscItem)
                return true;
        if (Configs.INSTANCE.uncommon.getBooleanValue())
            if (item.getRarity(stack) == Rarity.UNCOMMON)
                return true;
        if (Configs.INSTANCE.rare.getBooleanValue())
            if (item.getRarity(stack) == Rarity.RARE)
                return true;
        if (Configs.INSTANCE.epic.getBooleanValue())
            if (item.getRarity(stack) == Rarity.EPIC)
                return true;
        if (Configs.INSTANCE.enchanted.getBooleanValue()) {
            if (stack.hasEnchantments())
                return true;
        }
        if (Configs.INSTANCE.has_nbt.getBooleanValue()) {
            NbtCompound tag = stack.getTag();
            assert tag != null;
            if (tag.contains("display") || tag.getBoolean("Unbreakable") || tag.contains("CanDestroy") || tag.contains("CanPlaceOn") || tag.contains("StoredEnchantments") || tag.contains("AttributeModifiers"))
                return true;
        }
        if (Configs.INSTANCE.enchanted_book.getBooleanValue())
            if (item instanceof EnchantedBookItem)
                return true;
        if (Configs.INSTANCE.book.getBooleanValue())
            if (item instanceof WritableBookItem || item instanceof WrittenBookItem)
                return true;
        return Configs.INSTANCE.customItems.getStrings().contains(name);
    }

    public static boolean shouldThrow(ItemStack stack, int slot) {
        if (slot != lastSlot) {
            lastDropTime = 0;
            dropped = false;
        }
        if (!shouldHandleDrop(stack) || dropped) return true;
        long now = System.currentTimeMillis();
        if (now - lastDropTime >= Configs.INSTANCE.minSecond.getDoubleValue() * 1000 && now - lastDropTime <= Configs.INSTANCE.maxSecond.getDoubleValue() * 1000) {
            if (stack.getCount() != 1)
                dropped = true;
            lastDropTime = 0;
            return true;
        }
        lastDropTime = now;
        lastSlot = slot;
        return false;
    }

    public static Text getWarningText() {
        return new TranslatableText("tbt.warning");
    }
}
