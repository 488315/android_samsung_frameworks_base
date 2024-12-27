package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.filter.MediaFilter;
import java.util.Optional;

/* loaded from: classes6.dex */
public abstract class MFDescriptorBase extends MediaFilter.Option implements MFDescriptor {
    protected static int PLUGIN_CLASS = 1000;
    protected static int PLUGIN_ID = 1001;
    protected static int PLUGIN_VERSION = 1002;
    protected static int PLUGIN_MEDIA_TYPE = 1003;
    protected static int PLUGIN_INPUT_FORMAT = 1004;
    protected static int PLUGIN_OUTPUT_FORMAT = 1005;
    protected static int PLUGIN_LOADING_TYPE = 1006;
    protected static int PLUGIN_CLASS_NAME = 1007;
    protected static int PLUGIN_TARGET_FORMAT = 1008;
    protected static int FILTER_ID = 1010;
    protected static int FILTER_TYPE = 1011;

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptor
    public MediaFilter.Option getOption() {
        return this;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptor
    public void setOption(MediaFilter.Option option) {
        option.copyTo(this);
    }

    public String getFilterId() {
        return (String) get(FILTER_ID);
    }

    public MFDescriptorBase setFilterId(String filterId) {
        getAll().put(Integer.valueOf(FILTER_ID), filterId);
        return this;
    }

    public MFDescriptorBase setFilterType(Class<?> clazz) {
        getAll().put(Integer.valueOf(FILTER_TYPE), clazz);
        return this;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptor
    public Class<?> getFilterType() {
        return (Class) Optional.ofNullable(getAll().get(Integer.valueOf(FILTER_TYPE))).orElse(MediaFilter.class);
    }
}
