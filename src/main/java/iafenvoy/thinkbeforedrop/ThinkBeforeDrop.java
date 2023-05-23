package iafenvoy.thinkbeforedrop;

import iafenvoy.thinkbeforedrop.ThinkBeforeDrop;
import iafenvoy.thinkbeforedrop.config.Configs;
import iafenvoy.thinkbeforedrop.registry.KeyBind;
import fi.dy.masa.malilib.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class ThinkBeforeDrop implements ClientModInitializer {
    public static final String MOD_ID = "thinkbeforedrop";
    public static final String MOD_NAME = "Think Before Drop";

    @Override
    public void onInitializeClient() {
        KeyBind.register();
        ConfigManager.getInstance().registerConfigHandler(MOD_ID, Configs.INSTANCE);
    }
}
