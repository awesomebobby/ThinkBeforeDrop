package iafenvoy.thinkbeforedrop.config;

import iafenvoy.thinkbeforedrop.ThinkBeforeDrop;
import fi.dy.masa.malilib.gui.GuiConfigsBase;

import java.util.List;

public class ConfigGui extends GuiConfigsBase {

    public ConfigGui() {
        super(10, 40, ThinkBeforeDrop.MOD_ID, null, "config." + ThinkBeforeDrop.MOD_ID + ".title");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

//        this.onSettingsChanged();
//        // reload the GUI when tab button is clicked
//        this.reCreateListWidget();
//        assert this.getListWidget() != null;
//        this.getListWidget().resetScrollbarPosition();
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(Configs.INSTANCE.getConfigs());
    }

}
