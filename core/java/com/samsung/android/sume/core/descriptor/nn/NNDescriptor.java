package com.samsung.android.sume.core.descriptor.nn;

import android.util.Pair;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.descriptor.PluginDescriptor;
import com.samsung.android.sume.core.filter.NNFilter;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.functional.ModelSelector;
import com.samsung.android.sume.core.types.HwUnit;
import com.samsung.android.sume.core.types.LoadType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.core.types.nn.NNFW;
import com.samsung.android.sume.core.types.nn.NNFileDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class NNDescriptor extends PluginDescriptor {
    private static final String TAG = Def.tagOf((Class<?>) NNDescriptor.class);
    private transient Supplier<String> modelIdSupplier;
    private transient ModelSelector modelSelector;
    private transient List<NNFileDescriptor> nnFileDescriptor;
    private final List<NNFWProfile> nnfwProfiles = new ArrayList();

    public NNDescriptor() {
    }

    public NNDescriptor(Enum<?> modelId) {
        setPluginId(modelId);
    }

    public NNDescriptor(Enum<?> modelId, NNFW fw, HwUnit hw, int units) {
        this.nnfwProfiles.add(new NNFWProfile(fw, hw, units));
        setPluginId(modelId);
    }

    public MediaType getMediaType() {
        return (MediaType) getAll().get(Integer.valueOf(PLUGIN_MEDIA_TYPE));
    }

    public void setMediaType(MediaType mediaType) {
        getAll().put(Integer.valueOf(PLUGIN_MEDIA_TYPE), mediaType);
    }

    public List<NNFileDescriptor> getNnFileDescriptors() {
        return this.nnFileDescriptor;
    }

    public void setNNFileDescriptors(List<NNFileDescriptor> nnFileDescriptor) {
        this.nnFileDescriptor = nnFileDescriptor;
    }

    public List<NNFWProfile> getNNFWProfiles() {
        return this.nnfwProfiles;
    }

    public void setNNFWProfile(NNFW fw, HwUnit hw, int units) {
        this.nnfwProfiles.add(new NNFWProfile(fw, hw, units));
    }

    @Override // com.samsung.android.sume.core.descriptor.PluginDescriptor, com.samsung.android.sume.core.descriptor.MFDescriptorBase, com.samsung.android.sume.core.descriptor.MFDescriptor
    public Class<?> getFilterType() {
        return NNFilter.class;
    }

    public MutableMediaFormat getInputFormat() {
        return (MutableMediaFormat) getAll().get(Integer.valueOf(PLUGIN_INPUT_FORMAT));
    }

    public MutableMediaFormat getOutputFormat() {
        return (MutableMediaFormat) getAll().get(Integer.valueOf(PLUGIN_OUTPUT_FORMAT));
    }

    public MutableMediaFormat getTargetFormat() {
        return (MutableMediaFormat) getAll().get(Integer.valueOf(PLUGIN_TARGET_FORMAT));
    }

    public NNDescriptor setInputFormat(MutableMediaFormat inputFormat) {
        getAll().put(Integer.valueOf(PLUGIN_INPUT_FORMAT), inputFormat);
        return this;
    }

    public NNDescriptor setOutputFormat(MutableMediaFormat outputFormat) {
        getAll().put(Integer.valueOf(PLUGIN_OUTPUT_FORMAT), outputFormat);
        if (outputFormat.checkTypeOf("scale", Float.class)) {
            Float scale = (Float) outputFormat.get("scale");
            outputFormat.setShape(getInputFormat().getShape().toMutableShape().scale(scale));
        } else if (outputFormat.checkTypeOf("scale", Pair.class)) {
            Pair<Float, Float> scale2 = (Pair) outputFormat.get("scale");
            outputFormat.setShape(getInputFormat().getShape().toMutableShape().scale((Pair<Float, Float>) Objects.requireNonNull(scale2)));
        }
        return this;
    }

    public NNDescriptor setTargetFormat(MutableMediaFormat targetFormat) {
        getAll().put(Integer.valueOf(PLUGIN_TARGET_FORMAT), targetFormat);
        return this;
    }

    public void setModelIdSupplier(Supplier<String> modelIdSupplier) {
        this.modelIdSupplier = modelIdSupplier;
    }

    public String getModelId() {
        return (String) Optional.ofNullable(this.modelIdSupplier).map(new NNDescriptor$$ExternalSyntheticLambda0()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.descriptor.nn.NNDescriptor$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return NNDescriptor.this.m9132x6e850223();
            }
        });
    }

    /* renamed from: lambda$getModelId$0$com-samsung-android-sume-core-descriptor-nn-NNDescriptor, reason: not valid java name */
    /* synthetic */ String m9132x6e850223() {
        return super.getFilterId();
    }

    public LoadType getLoadingType() {
        return (LoadType) getAll().get(Integer.valueOf(PLUGIN_LOADING_TYPE));
    }

    public void setLoadingType(LoadType loadType) {
        getAll().put(Integer.valueOf(PLUGIN_LOADING_TYPE), loadType);
    }

    public void setModelSelector(ModelSelector modelSelector) {
        this.modelSelector = modelSelector;
    }

    public ModelSelector getModelSelector() {
        return this.modelSelector;
    }
}
