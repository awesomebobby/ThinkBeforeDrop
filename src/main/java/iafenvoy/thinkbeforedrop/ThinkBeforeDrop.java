package iafenvoy.thinkbeforedrop;

import fi.dy.masa.malilib.config.ConfigManager;
import iafenvoy.thinkbeforedrop.config.Configs;
import iafenvoy.thinkbeforedrop.registry.KeyBind;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThinkBeforeDrop implements ClientModInitializer {
    public static final String MOD_ID = "thinkbeforedrop";
    public static final String MOD_NAME = "Think Before Drop";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        KeyBind.register();
        ConfigManager.getInstance().registerConfigHandler(MOD_ID, Configs.INSTANCE);
    }
}