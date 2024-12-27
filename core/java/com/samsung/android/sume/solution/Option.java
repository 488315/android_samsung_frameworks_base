package com.samsung.android.sume.solution;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.types.OptionBase;

/* loaded from: classes6.dex */
public class Option extends Graph.Option {
    public static final Parcelable.Creator<Option> CREATOR =
            new Parcelable.Creator<
                    Option>() { // from class: com.samsung.android.sume.solution.Option.1
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
    public static final int OPTION_AUDIO_BITRATE = 100;
    public static final int OPTION_CUSTOM_BASE = 10000;
    public static final int OPTION_FILTER_THRESHOLD = 103;
    public static final int OPTION_SCALE_FACTOR = 102;
    public static final int OPTION_UPSCALER_FACTOR = 104;
    public static final int OPTION_VIDEO_BITRATE = 101;

    public Option() {}

    protected Option(Parcel in) {
        super(in);
    }

    public int getAudioBitrate() {
        return ((Integer) get(100, 0)).intValue();
    }

    public Option setAudioBitrate(int audioBitrate) {
        getAll().put(100, Integer.valueOf(audioBitrate));
        return this;
    }

    public int getVideoBitrate() {
        return ((Integer) get(101, 0)).intValue();
    }

    public Option setVideoBitrate(int videoBitrate) {
        getAll().put(101, Integer.valueOf(videoBitrate));
        return this;
    }

    public float getScale() {
        return ((Float) get(102, Float.valueOf(0.0f))).floatValue();
    }

    public Option setScale(float scale) {
        getAll().put(102, Float.valueOf(scale));
        return this;
    }

    public int getUpscaler() {
        return ((Integer) get(104, 4)).intValue();
    }

    public Option setUpscaler(int scale) {
        getAll().put(104, Integer.valueOf(scale));
        return this;
    }

    public Float getFilterThreshold() {
        return (Float) get(103, Float.valueOf(0.0f));
    }

    public Option setFilterThreshold(float filterThreshold) {
        getAll().put(103, Float.valueOf(filterThreshold));
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.Graph.Option,
              // com.samsung.android.sume.core.types.OptionBase
    public OptionBase set(int option) {
        getAll().put(Integer.valueOf(option), null);
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.Graph.Option,
              // com.samsung.android.sume.core.types.OptionBase
    public OptionBase set(int option, Object data) {
        getAll().put(Integer.valueOf(option), data);
        return this;
    }

    public static int makeCustomOption(int optionIndex) {
        return optionIndex + 10000;
    }
}
