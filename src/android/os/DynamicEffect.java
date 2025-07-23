package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class DynamicEffect extends VibrationEffect implements Parcelable {
    public static final Parcelable.Creator<DynamicEffect> CREATOR = new Parcelable.Creator<DynamicEffect>() { // from class: android.os.DynamicEffect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DynamicEffect createFromParcel(Parcel parcel) {
            return new DynamicEffect(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DynamicEffect[] newArray(int i) {
            return new DynamicEffect[i];
        }
    };
    private String mJson;

    @Override // android.os.VibrationEffect
    public DynamicEffect applyAdaptiveScale(float f) {
        return this;
    }

    @Override // android.os.VibrationEffect
    public DynamicEffect applyEffectStrength(int i) {
        return this;
    }

    @Override // android.os.VibrationEffect
    public VibrationEffect applyRepeatingIndefinitely(boolean z, int i) {
        return null;
    }

    @Override // android.os.VibrationEffect
    public boolean areVibrationFeaturesSupported(VibratorInfo vibratorInfo) {
        return false;
    }

    @Override // android.os.VibrationEffect
    public VibrationEffect cropToLengthOrNull(int i) {
        return null;
    }

    @Override // android.os.VibrationEffect, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.VibrationEffect
    public long getDuration() {
        return 0L;
    }

    @Override // android.os.VibrationEffect
    public DynamicEffect resolve(int i) {
        return this;
    }

    @Override // android.os.VibrationEffect
    public DynamicEffect scale(float f) {
        return this;
    }

    @Override // android.os.VibrationEffect
    public String toDebugString() {
        return null;
    }

    @Override // android.os.VibrationEffect
    public void validate() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static DynamicEffect create(String str) {
        return new DynamicEffect(str);
    }

    DynamicEffect(Parcel parcel) {
    }

    public DynamicEffect(String str) {
        this.mJson = str;
    }

    public String getEffectInfo() {
        return this.mJson;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DynamicEffect) {
            return ((DynamicEffect) obj).mJson.equals(this.mJson);
        }
        return false;
    }

    public int hashCode() {
        return this.mJson.hashCode();
    }

    public String toString() {
        return "DynamicEffect{mJson=" + this.mJson + "}";
    }

    @Override // android.os.VibrationEffect
    public long[] computeCreateWaveformOffOnTimingsOrNull() {
        return new long[0];
    }
}
