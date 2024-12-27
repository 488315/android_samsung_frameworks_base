package com.samsung.android.sume.core.plugin;

import android.content.Context;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.filter.ContentFilterRegister;
import com.samsung.android.sume.core.functional.DescriptorLoader;
import com.samsung.android.sume.core.functional.DescriptorStreamLoader;

import java.util.function.Consumer;

public abstract class PluginFixture<T extends PluginFixture<?>> {
    protected ContentFilterRegister contentFilterRegister;
    private Runnable deInitializer;
    private DescriptorLoader descriptorLoader;
    private DescriptorStreamLoader descriptorStreamLoader;
    private Consumer<Context> initializer;

    public abstract boolean validate();

    public PluginFixture(Plugin<T> plugin) {
        plugin.bindToFixture(this);
        Def.require(validate(), "fail to check validation of plugin", new Object[0]);
    }

    public void init(Context context) {
        if (this.initializer != null) {
            this.initializer.accept(context);
        }
    }

    public void release() {
        if (this.deInitializer != null) {
            this.deInitializer.run();
        }
    }

    public DescriptorLoader getDescriptorLoader() {
        return this.descriptorLoader;
    }

    public void setDescriptorLoader(DescriptorLoader descriptorLoader) {
        this.descriptorLoader = descriptorLoader;
    }

    public DescriptorStreamLoader getDescriptorStreamLoader() {
        return this.descriptorStreamLoader;
    }

    public void setDescriptorStreamLoader(DescriptorStreamLoader descriptorStreamLoader) {
        this.descriptorStreamLoader = descriptorStreamLoader;
    }

    public void setInitializer(Consumer<Context> initializer) {
        this.initializer = initializer;
    }

    public void setDeInitializer(Runnable deInitializer) {
        this.deInitializer = deInitializer;
    }

    public ContentFilterRegister getContentFilterRegister() {
        return this.contentFilterRegister;
    }

    public void setContentFilterRegister(ContentFilterRegister contentFilterRegister) {
        this.contentFilterRegister = contentFilterRegister;
    }
}
