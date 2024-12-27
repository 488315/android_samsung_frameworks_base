package com.samsung.android.sume.solution.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.filter.ImgpFilter;
import com.samsung.android.sume.core.filter.factory.MediaFilterFactory;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import com.samsung.android.sume.core.plugin.PluginStore;
import com.samsung.android.sume.core.plugin.SimgpPlugin;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.ImgpType;
import com.samsung.android.sume.core.types.OptionBase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* loaded from: classes6.dex */
public class UniImgp {
    public static final int INTER_AREA = 3;
    public static final int INTER_CUBIC = 2;
    public static final int INTER_LANCZOS4 = 4;
    public static final int INTER_LINEAR = 1;
    public static final int INTER_LINEAR_EXACT = 5;
    public static final int INTER_MAX = 7;
    public static final int INTER_NEAREST = 0;
    public static final int OPTION_IMGP_TYPE = 2010;
    public static final int OPTION_IMGP_TYPE_NAME = 2011;
    public static final int OPTION_LATEST_PLUGIN_ORDER = 2001;
    public static final int OPTION_PERSISTENT_INPUT_FORMAT = 2003;
    public static final int OPTION_PERSISTENT_OUTPUT_FORMAT = 2004;
    public static final int OPTION_PLUGIN_ORDER = 2002;
    public static final int OPTION_PREFERRED_COLOR_FORMAT = 2005;
    public static final int OPTION_USE_PERSISTENT_FORMAT = 2000;
    public static final int PSNR = 0;
    public static final int SSIM = 1;
    private static volatile PluginStore globalStore;
    private final MediaFilterFactory factory;
    private Option option;

    @Retention(RetentionPolicy.SOURCE)
    public @interface InterpolationType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface OptionType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface QualityMetricType {}

    public static class Option extends OptionBase {
        public static final Parcelable.Creator<Option> CREATOR =
                new Parcelable.Creator<
                        Option>() { // from class:
                                    // com.samsung.android.sume.solution.filter.UniImgp.Option.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Option createFromParcel(Parcel in) {
                        return new Option(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Option[] newArray(int size) {
                        return new Option[size];
                    }
                };
        private transient PluginStore pluginStore;

        public Option() {}

        public Option(Parcel in) {
            super(in);
        }

        public boolean isUsePersistentFormat() {
            return ((Boolean) get(2000, false)).booleanValue();
        }

        public Option setUsePersistentFormat(boolean usePersistentFormat) {
            set(2000, Boolean.valueOf(usePersistentFormat));
            return this;
        }

        public MediaFormat getPersistentInputFormat() {
            return (MediaFormat) get(2003);
        }

        public Option setPersistentInputFormat(MediaFormat mediaFormat) {
            set(2003, mediaFormat);
            return this;
        }

        public MediaFormat getPersistentOutputFormat() {
            return (MediaFormat) get(2004);
        }

        public Option setPersistentOutputFormat(MediaFormat mediaFormat) {
            set(2004, mediaFormat);
            return this;
        }

        public ColorFormat getPreferredColorFormat() {
            return (ColorFormat) get(2005);
        }

        public Option setPreferredColorFormat(ColorFormat colorFormat) {
            set(2005, colorFormat);
            return this;
        }

        public Option latestPluginsOrder() {
            set(2001);
            return this;
        }

        public boolean isLatestPluginsOrder() {
            return contains(2001);
        }

        public Option setPluginOrder(ImgpPlugin.Type... pluginOrder) {
            Map<ImgpType, List<ImgpPlugin.Type>> pluginOrderMap =
                    contains(2002) ? (Map) get(2002) : new HashMap<>();
            pluginOrderMap.put(ImgpType.ANY, Arrays.asList(pluginOrder));
            return (Option) set(2002, pluginOrderMap);
        }

        public Option setPluginOrder(ImgpType type, ImgpPlugin.Type... pluginOrder) {
            Map<ImgpType, List<ImgpPlugin.Type>> pluginOrderMap =
                    contains(2002) ? (Map) get(2002) : new HashMap<>();
            pluginOrderMap.put(type, Arrays.asList(pluginOrder));
            return (Option) set(2002, pluginOrderMap);
        }

        public Map<ImgpType, List<ImgpPlugin.Type>> getPluginOrderMap() {
            return (Map) get(2002);
        }

        public Option setPluginStore(PluginStore pluginStore) {
            this.pluginStore = pluginStore;
            return this;
        }

        public PluginStore getPluginStore() {
            return this.pluginStore;
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public OptionBase set(int option) {
            return super.set(option);
        }

        @Override // com.samsung.android.sume.core.types.OptionBase
        public OptionBase set(int option, Object data) {
            return super.set(option, data);
        }
    }

    public static PluginStore getGlobalStore() {
        if (globalStore == null) {
            synchronized (PluginStore.class) {
                if (globalStore == null) {
                    globalStore = PluginStore.of();
                    globalStore.add(new SimgpPlugin());
                }
            }
        }
        return globalStore;
    }

    private UniImgp() {
        this.option = null;
        this.factory =
                new MediaFilterFactory.Builder()
                        .addPluginStore(getGlobalStore())
                        .addDefaultCreators()
                        .build();
    }

    private UniImgp(Option option) {
        this.option = null;
        MediaFilterFactory.Builder builder = new MediaFilterFactory.Builder();
        builder.addPluginStore(
                        (PluginStore)
                                Optional.ofNullable(option.getPluginStore())
                                        .orElse(getGlobalStore()))
                .addDefaultCreators();
        this.option = option;
        this.factory = builder.build();
    }

    private void configDescriptorByOption(ImgpDescriptor descriptor) {
        if (this.option != null) {
            descriptor.setUsePersistentFormat(this.option.isUsePersistentFormat());
            descriptor.setLatestPluginsOrder(this.option.isLatestPluginsOrder());
        }
    }

    public Operator newOperator() {
        if (!this.option.contains(2004)) {
            throw new IllegalArgumentException("persistent output format should be given");
        }
        MediaFormat persistentInputFormat = this.option.getPersistentInputFormat();
        MediaFormat persistentOutputFormat = this.option.getPersistentOutputFormat();
        ColorFormat preferredColorFormat = this.option.getPreferredColorFormat();
        return new NativeImgpFilterAdapter(
                persistentInputFormat, persistentOutputFormat, preferredColorFormat);
    }

    public <T extends ImgpType> Operator newOperator(T type) {
        ImgpDescriptor descriptor = new ImgpDescriptor(type);
        configDescriptorByOption(descriptor);
        return new ImgpFilterAdapter((ImgpFilter) this.factory.newFilter(descriptor));
    }

    public static UniImgp of() {
        return new UniImgp();
    }

    public static UniImgp of(Option option) {
        return new UniImgp(option);
    }

    public static Operator ofResize() {
        return new UniImgp().newOperator(ImgpType.RESIZE);
    }

    public static Operator ofResize(Option option) {
        return new UniImgp(option).newOperator(ImgpType.RESIZE);
    }

    public static Operator ofCvtColor() {
        return new UniImgp().newOperator(ImgpType.CVT_COLOR);
    }

    public static Operator ofCvtColor(Option option) {
        return new UniImgp(option).newOperator(ImgpType.CVT_COLOR);
    }

    public static Operator ofCvtData() {
        return new UniImgp().newOperator(ImgpType.CVT_DATA);
    }

    public static Operator ofCvtData(Option option) {
        return new UniImgp(option).newOperator(ImgpType.CVT_DATA);
    }

    public static Operator ofCvtGamut() {
        return new UniImgp().newOperator(ImgpType.CVT_GAMUT);
    }

    public static Operator ofCvtGamut(Option option) {
        return new UniImgp(option).newOperator(ImgpType.CVT_GAMUT);
    }

    public static Operator ofRotate() {
        return new UniImgp().newOperator(ImgpType.ROTATE);
    }

    public static Operator ofRotate(Option option) {
        return new UniImgp(option).newOperator(ImgpType.ROTATE);
    }

    public static Operator ofFlip() {
        return new UniImgp().newOperator(ImgpType.FLIP);
    }

    public static Operator ofFlip(Option option) {
        return new UniImgp(option).newOperator(ImgpType.FLIP);
    }

    public static Operator ofCrop() {
        return new UniImgp().newOperator(ImgpType.CROP);
    }

    public static Operator ofCrop(Option option) {
        return new UniImgp(option).newOperator(ImgpType.CROP);
    }

    public static Operator ofSplit() {
        return new UniImgp().newOperator(ImgpType.SPLIT);
    }

    public static Operator ofSplit(Option option) {
        return new UniImgp(option).newOperator(ImgpType.SPLIT);
    }

    public static Operator ofMerge() {
        return new UniImgp().newOperator(ImgpType.MERGE);
    }

    public static Operator ofMerge(Option option) {
        return new UniImgp(option).newOperator(ImgpType.MERGE);
    }

    public static Operator ofUnified() {
        return new UniImgp().newOperator(ImgpType.ANY);
    }

    public static Operator ofUnified(Option option) {
        return new UniImgp(option).newOperator(ImgpType.ANY);
    }

    public static Operator ofQuality() {
        return new UniImgp().newOperator(ImgpType.QUALITY_MEASURE);
    }

    public static Operator ofQuality(Option option) {
        return new UniImgp(option).newOperator(ImgpType.QUALITY_MEASURE);
    }

    public static Operator ofDecode() {
        return new UniImgp().newOperator(ImgpType.DECODE);
    }

    public static Operator ofDecode(Option option) {
        return new UniImgp(option).newOperator(ImgpType.DECODE);
    }

    public static Operator ofEncode() {
        return new UniImgp().newOperator(ImgpType.ENCODE);
    }

    public static Operator ofEncode(Option option) {
        return new UniImgp(option).newOperator(ImgpType.ENCODE);
    }

    public static Operator ofEncodeHDR() {
        return new UniImgp().newOperator(ImgpType.ENCODE_HDR);
    }

    public static Operator ofEncodeHDR(Option option) {
        return new UniImgp(option).newOperator(ImgpType.ENCODE_HDR);
    }

    public static Operator ofCreateGainmap() {
        return new UniImgp().newOperator(ImgpType.CREATE_GAINMAP);
    }

    public static Operator ofCreateGainmap(Option option) {
        return new UniImgp(option).newOperator(ImgpType.CREATE_GAINMAP);
    }
}
