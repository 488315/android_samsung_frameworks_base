package com.samsung.android.sume.solution;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.types.OptionBase;

/* loaded from: classes6.dex */
public class Option extends Graph.Option {
    public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() { // from class: com.samsung.android.sume.solution.Option.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Option createFromParcel(Parcel parcel) {
            return new Option(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Option[] newArray(int i) {
            return new Option[i];
        }
    };
    public static final int OPTION_AUDIO_BITRATE = 100;
    public static final int OPTION_CUSTOM_BASE = 10000;
    public static final int OPTION_FILTER_THRESHOLD = 103;
    public static final int OPTION_SCALE_FACTOR = 102;
    public static final int OPTION_UPSCALER_FACTOR = 104;
    public static final int OPTION_VIDEO_BITRATE = 101;

    public static int makeCustomOption(int i) {
        return i + 10000;
    }

    public Option() {
    }

    protected Option(Parcel parcel) {
        super(parcel);
    }

    public int getAudioBitrate() {
        return ((Integer) get(100, 0)).intValue();
    }

    public Option setAudioBitrate(int i) {
        getAll().put(100, Integer.valueOf(i));
        return this;
    }

    public int getVideoBitrate() {
        return ((Integer) get(101, 0)).intValue();
    }

    public Option setVideoBitrate(int i) {
        getAll().put(101, Integer.valueOf(i));
        return this;
    }

    public float getScale() {
        return ((Float) get(102, Float.valueOf(0.0f))).floatValue();
    }

    public Option setScale(float f) {
        getAll().put(102, Float.valueOf(f));
        return this;
    }

    public int getUpscaler() {
        return ((Integer) get(104, 4)).intValue();
    }

    public Option setUpscaler(int i) {
        getAll().put(104, Integer.valueOf(i));
        return this;
    }

    public Float getFilterThreshold() {
        return (Float) get(103, Float.valueOf(0.0f));
    }

    public Option setFilterThreshold(float f) {
        getAll().put(103, Float.valueOf(f));
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.Graph.Option, com.samsung.android.sume.core.types.OptionBase
    public OptionBase set(int i) {
        getAll().put(Integer.valueOf(i), null);
        return this;
    }

    @Override // com.samsung.android.sume.core.graph.Graph.Option, com.samsung.android.sume.core.types.OptionBase
    public OptionBase set(int i, Object obj) {
        getAll().put(Integer.valueOf(i), obj);
        return this;
    }
}
