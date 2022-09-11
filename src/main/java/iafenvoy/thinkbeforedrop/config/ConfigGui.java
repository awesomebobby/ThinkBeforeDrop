package iafenvoy.thinkbeforedrop.config;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import iafenvoy.thinkbeforedrop.ThinkBeforeDrop;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

public class ConfigGui extends GuiConfigsBase {
    public ConfigGui(Screen parent) {
        super(10, 20, ThinkBeforeDrop.MOD_ID, null, "config." + ThinkBeforeDrop.MOD_ID + ".title");
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        this.onSettingsChanged();
        // reload the GUI when tab button is clicked
        this.reCreateListWidget();
        assert this.getListWidget() != null;
        this.getListWidget().resetScrollbarPosition();
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(Configs.INSTANCE.getConfigs());
    }
}
