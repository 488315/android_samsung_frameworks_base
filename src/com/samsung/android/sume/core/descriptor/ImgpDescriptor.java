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

    public ImgpDescriptor(ImgpPlugin.Type type) {
        this(type, ImgpType.ANY);
    }

    public ImgpDescriptor(ImgpType imgpType) {
        this(ImgpPlugin.Type.ANY, imgpType);
    }

    public ImgpDescriptor(ImgpPlugin.Type type, Enum<?> r5) {
        Def.require(type == ImgpPlugin.Type.CUSTOM || (r5 instanceof ImgpType), "For pre-defined plugin types, should set ImgpType as 2nd argument", new Object[0]);
        setPluginId(type);
        getAll().put(2010, r5);
    }

    public ImgpDescriptor(String str, String str2) {
        Def.require(str.startsWith("com.samsung.android."), "pluginClassName should be follow sec package naming rule: com.samsung.android.{}", new Object[0]);
        setPluginId(ImgpPlugin.Type.CUSTOM);
        getAll().put(2011, str2);
        setPluginClassName(str);
    }

    public <T extends Enum<?>> T getImgpType() {
        return (T) get(2010);
    }

    public String getImgpTypeName() {
        return (String) get(2011);
    }

    public ImgpDescriptor setFormat(MutableMediaFormat mutableMediaFormat) {
        getAll().put(Integer.valueOf(PLUGIN_INPUT_FORMAT), mutableMediaFormat);
        return this;
    }

    public MutableMediaFormat getFormat() {
        return (MutableMediaFormat) getAll().get(Integer.valueOf(PLUGIN_INPUT_FORMAT));
    }

    public boolean isUsePersistentFormat() {
        return ((Boolean) get(2000, false)).booleanValue();
    }

    public void setUsePersistentFormat(boolean z) {
        getAll().put(2000, Boolean.valueOf(z));
    }

    public boolean isLatestPluginsOrder() {
        return ((Boolean) get(2001, false)).booleanValue();
    }

    public void setLatestPluginsOrder(boolean z) {
        getAll().put(2001, Boolean.valueOf(z));
    }
}
