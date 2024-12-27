package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import com.samsung.android.sume.core.types.ImgpType;

/* loaded from: classes6.dex */
public class ImgpDescriptor extends PluginDescriptor {
    private static final String TAG = Def.tagOf((Class<?>) ImgpDescriptor.class);

    public ImgpDescriptor() {
        this(ImgpPlugin.Type.ANY, ImgpType.ANY);
    }

    public ImgpDescriptor(ImgpPlugin.Type pluginType) {
        this(pluginType, ImgpType.ANY);
    }

    public ImgpDescriptor(ImgpType imgpType) {
        this(ImgpPlugin.Type.ANY, imgpType);
    }

    public ImgpDescriptor(ImgpPlugin.Type pluginType, Enum<?> imgpType) {
        Def.require(pluginType == ImgpPlugin.Type.CUSTOM || (imgpType instanceof ImgpType), "For pre-defined plugin types, should set ImgpType as 2nd argument", new Object[0]);
        setPluginId(pluginType);
        getAll().put(2010, imgpType);
    }

    public ImgpDescriptor(String pluginClassName, String imgpTypeName) {
        Def.require(pluginClassName.startsWith("com.samsung.android."), "pluginClassName should be follow sec package naming rule: com.samsung.android.{}", new Object[0]);
        setPluginId(ImgpPlugin.Type.CUSTOM);
        getAll().put(2011, imgpTypeName);
        setPluginClassName(pluginClassName);
    }

    public <T extends Enum<?>> T getImgpType() {
        return (T) get(2010);
    }

    public String getImgpTypeName() {
        return (String) get(2011);
    }

    public ImgpDescriptor setFormat(MutableMediaFormat format) {
        getAll().put(Integer.valueOf(PLUGIN_INPUT_FORMAT), format);
        return this;
    }

    public MutableMediaFormat getFormat() {
        return (MutableMediaFormat) getAll().get(Integer.valueOf(PLUGIN_INPUT_FORMAT));
    }

    public boolean isUsePersistentFormat() {
        return ((Boolean) get(2000, false)).booleanValue();
    }

    public void setUsePersistentFormat(boolean usePersistentFormat) {
        getAll().put(2000, Boolean.valueOf(usePersistentFormat));
    }

    public boolean isLatestPluginsOrder() {
        return ((Boolean) get(2001, false)).booleanValue();
    }

    public void setLatestPluginsOrder(boolean latestOrder) {
        getAll().put(2001, Boolean.valueOf(latestOrder));
    }
}
