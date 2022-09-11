package iafenvoy.thinkbeforedrop.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.util.JsonUtils;
import iafenvoy.thinkbeforedrop.ThinkBeforeDrop;
import net.minecraft.client.resource.language.I18n;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Configs implements IConfigHandler {
    public static final Configs INSTANCE = new Configs();
    private static final String MOD_ID = ThinkBeforeDrop.MOD_ID;
    private static final String FILE_PATH = "./config/" + MOD_ID + ".json";
    private static final File CONFIG_DIR = new File("./config");
    private final List<IConfigBase> configs = new ArrayList<>();

    public final NBoolean open = new NBoolean("open", true);//是否启用
    //设置两次按下Q的允许间隔
    public final NDouble minSecond = new NDouble("minSecond", 0.5);
    public final NDouble maxSecond = new NDouble("maxSecond", 5);
    //配置表
    public final NBoolean weapon = new NBoolean("weapon", false);//所有武器
    public final NBoolean tool = new NBoolean("tool", false);//所有工具
    public final NBoolean shulker_box = new NBoolean("skulker_box", false);//所有潜影盒
    public final NBoolean armor = new NBoolean("armor", false);//所有装备
    public final NBoolean ore = new NBoolean("ore", false);//所有矿石
    public final NBoolean disc = new NBoolean("disc", false);//所有唱片
    public final NBoolean uncommon = new NBoolean("uncommon", false);//所有黄名物品
    public final NBoolean rare = new NBoolean("rare", false);//所有蓝名物品
    public final NBoolean epic = new NBoolean("epic", false);//所有紫名物品
    public final NBoolean enchanted = new NBoolean("enchanted", false);//所有有附魔的物品，包括物品有的附魔（只能指令拿到的那种）
    public final NBoolean has_nbt = new NBoolean("has_nbt", false);//所有有特殊nbt的（不包括耐久、附魔惩罚等）
    public final NBoolean enchanted_book = new NBoolean("enchanted_book", false);//所有附魔书
    public final NBoolean book = new NBoolean("books", false);//所有成书
    public final NStringList customItems=new NStringList("customItems",ImmutableList.of());//自定义
    public final NStringList excludeItems=new NStringList("excludeItems",ImmutableList.of());//排除物品

    private Configs() {
        this.load();
    }

    @Override
    public void load() {
        File settingFile = new File(FILE_PATH);
        if (settingFile.isFile() && settingFile.exists()) {
            JsonElement jsonElement = JsonUtils.parseJsonFile(settingFile);
            if (jsonElement instanceof JsonObject)
                ConfigUtils.readConfigBase((JsonObject) jsonElement, MOD_ID, configs);
        }
    }

    @Override
    public void save() {
        if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs()) {
            JsonObject configRoot = new JsonObject();
            ConfigUtils.writeConfigBase(configRoot, MOD_ID, configs);
            JsonUtils.writeJsonToFile(configRoot, new File(FILE_PATH));
        }
    }

    public List<IConfigBase> getConfigs() {
        return configs;
    }

    public class NBoolean extends ConfigBoolean {
        public NBoolean(String nameKey, boolean defaultValue) {
            super(I18n.translate("config." + MOD_ID + "." + nameKey), defaultValue, I18n.translate("config." + MOD_ID + "." + nameKey + ".tip"));
            configs.add(this);
        }
    }

    public class NDouble extends ConfigDouble {
        public NDouble(String nameKey, double defaultValue) {
            super(I18n.translate("config." + MOD_ID + "." + nameKey), defaultValue, I18n.translate("config." + MOD_ID + "." + nameKey + ".tip"));
            configs.add(this);
        }

        @Override
        public boolean shouldUseSlider() {
            return false;
        }
    }

    public class NStringList extends ConfigStringList {

        public NStringList(String nameKey, ImmutableList<String> defaultValue) {
            super(I18n.translate("config." + MOD_ID + "." + nameKey), defaultValue, I18n.translate("config." + MOD_ID + "." + nameKey + ".tip"));
            configs.add(this);
        }
    }
}
