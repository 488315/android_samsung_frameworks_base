package com.samsung.android.sume.core.descriptor.nn;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.descriptor.MFDescriptorBase;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.types.HwUnit;
import com.samsung.android.sume.core.types.LoadType;
import com.samsung.android.sume.core.types.nn.Model;
import com.samsung.android.sume.core.types.nn.NNFW;
import com.samsung.android.sume.core.types.nn.NNFileDescriptor;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class NNFWDescriptor extends MFDescriptorBase implements Cloneable {
    private static final String TAG = Def.tagOf((Class<?>) NNFWDescriptor.class);
    private final NNFW fw;
    private final HwUnit hw;
    private MutableMediaFormat inputFormat;
    private transient Supplier<LoadType> loadTypeSupplier;
    private Model model;
    private transient WeakReference<NNDescriptor> nnDescriptor;
    private NNFileDescriptor nnFileDescriptor;
    private MutableMediaFormat outputFormat;
    private transient MutableMediaFormat targetFormat;

    public NNFWDescriptor(NNFW fw, HwUnit hw) {
        this.fw = fw;
        this.hw = hw;
    }

    public NNFWDescriptor(NNFW fw, HwUnit hw, NNDescriptor nnDescriptor) {
        this(fw, hw);
        setNNDescriptor(nnDescriptor);
    }

    public NNFWDescriptor(NNFW fw, HwUnit hw, final LoadType loadType, NNDescriptor nnDescriptor) {
        this(fw, hw, nnDescriptor);
        this.loadTypeSupplier = new Supplier() { // from class: com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return NNFWDescriptor.lambda$new$0(LoadType.this);
            }
        };
    }

    static /* synthetic */ LoadType lambda$new$0(LoadType loadType) {
        return loadType;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase, com.samsung.android.sume.core.descriptor.MFDescriptor
    public String getFilterId() {
        return this.fw.name();
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase, com.samsung.android.sume.core.descriptor.MFDescriptor
    public Class<?> getFilterType() {
        return NNFW.class;
    }

    public NNFW getNNFramework() {
        return this.fw;
    }

    public NNFileDescriptor getNNFileDescriptor() {
        return this.nnFileDescriptor;
    }

    public void setNNFileDescriptor(NNFileDescriptor nnFileDescriptor) {
        this.nnFileDescriptor = nnFileDescriptor;
    }

    public NNDescriptor getNNDescriptor() {
        return this.nnDescriptor.get();
    }

    public void setNNDescriptor(final NNDescriptor nnDescriptor) {
        this.nnDescriptor = new WeakReference<>(nnDescriptor);
        this.model = (Model) nnDescriptor.getPluginId();
        this.inputFormat = nnDescriptor.getInputFormat();
        this.outputFormat = nnDescriptor.getOutputFormat();
        this.targetFormat = nnDescriptor.getTargetFormat();
        Objects.requireNonNull(nnDescriptor);
        this.loadTypeSupplier = new Supplier() { // from class: com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return NNDescriptor.this.getLoadingType();
            }
        };
    }

    public NNFW getFw() {
        return this.fw;
    }

    public HwUnit getHw() {
        return this.hw;
    }

    public LoadType getLoadType() {
        return this.loadTypeSupplier.get();
    }

    public boolean isLazy() {
        return getLoadType() == LoadType.LAZY;
    }

    public boolean isInstant() {
        return getLoadType() == LoadType.INSTANT;
    }

    public MutableMediaFormat getInputFormat() {
        return this.inputFormat;
    }

    public MutableMediaFormat getOutputFormat() {
        return this.outputFormat;
    }

    public MutableMediaFormat getTargetFormat() {
        return this.targetFormat;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase, com.samsung.android.sume.core.descriptor.MFDescriptor
    public MediaFilter.Option getOption() {
        return (MediaFilter.Option) Optional.ofNullable(this.nnDescriptor.get()).map(new Function() { // from class: com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((NNDescriptor) obj).getOption();
            }
        }).orElse(null);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public NNFWDescriptor m9133clone() {
        try {
            NNFWDescriptor clone = (NNFWDescriptor) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("fail to clone NNFWDescriptor");
        }
    }

    @Override // com.samsung.android.sume.core.types.OptionBase
    public String toString() {
        return Def.tagOf(this) + Def.contentToString("model=" + ((String) Optional.ofNullable(this.nnFileDescriptor).map(new NNFWDescriptor$$ExternalSyntheticLambda1()).orElse("n/a")), NavigationBarInflaterView.SIZE_MOD_START + this.model.name() + NavigationBarInflaterView.SIZE_MOD_END, "fw=" + this.fw.name(), "hw=" + this.hw.name(), "input=" + this.inputFormat, "output=" + this.outputFormat);
    }
}
