package android.service.notification;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public final class ZenDeviceEffects implements Parcelable {
    public static final Parcelable.Creator<ZenDeviceEffects> CREATOR = new Parcelable.Creator<ZenDeviceEffects>() { // from class: android.service.notification.ZenDeviceEffects.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenDeviceEffects createFromParcel(Parcel parcel) {
            return new ZenDeviceEffects(parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), Set.of(parcel.readArray(String.class.getClassLoader(), String.class)));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenDeviceEffects[] newArray(int i) {
            return new ZenDeviceEffects[i];
        }
    };
    public static final int FIELD_DIM_WALLPAPER = 4;
    public static final int FIELD_DISABLE_AUTO_BRIGHTNESS = 16;
    public static final int FIELD_DISABLE_TAP_TO_WAKE = 32;
    public static final int FIELD_DISABLE_TILT_TO_WAKE = 64;
    public static final int FIELD_DISABLE_TOUCH = 128;
    public static final int FIELD_EXTRA_EFFECTS = 1024;
    public static final int FIELD_GRAYSCALE = 1;
    public static final int FIELD_MAXIMIZE_DOZE = 512;
    public static final int FIELD_MINIMIZE_RADIO_USAGE = 256;
    public static final int FIELD_NIGHT_LIGHT = 2048;
    public static final int FIELD_NIGHT_MODE = 8;
    public static final int FIELD_SUPPRESS_AMBIENT_DISPLAY = 2;
    private static final int MAX_EFFECTS_LENGTH = 2000;
    private final boolean mDimWallpaper;
    private final boolean mDisableAutoBrightness;
    private final boolean mDisableTapToWake;
    private final boolean mDisableTiltToWake;
    private final boolean mDisableTouch;
    private final Set<String> mExtraEffects;
    private final boolean mGrayscale;
    private final boolean mMaximizeDoze;
    private final boolean mMinimizeRadioUsage;
    private final boolean mNightLight;
    private final boolean mNightMode;
    private final boolean mSuppressAmbientDisplay;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ZenDeviceEffects(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, Set<String> set) {
        this.mGrayscale = z;
        this.mSuppressAmbientDisplay = z2;
        this.mDimWallpaper = z3;
        this.mNightMode = z4;
        this.mDisableAutoBrightness = z5;
        this.mDisableTapToWake = z6;
        this.mDisableTiltToWake = z7;
        this.mDisableTouch = z8;
        this.mMinimizeRadioUsage = z9;
        this.mMaximizeDoze = z10;
        this.mNightLight = z11;
        this.mExtraEffects = Collections.unmodifiableSet(set);
    }

    public void validate() {
        Iterator<String> it = this.mExtraEffects.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().length();
        }
        if (i > 2000) {
            throw new IllegalArgumentException("Total size of extra effects must be at most 2000 characters");
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof ZenDeviceEffects) {
            ZenDeviceEffects zenDeviceEffects = (ZenDeviceEffects) obj;
            if (obj == this) {
                return true;
            }
            if (this.mGrayscale == zenDeviceEffects.mGrayscale && this.mSuppressAmbientDisplay == zenDeviceEffects.mSuppressAmbientDisplay && this.mDimWallpaper == zenDeviceEffects.mDimWallpaper && this.mNightMode == zenDeviceEffects.mNightMode && this.mDisableAutoBrightness == zenDeviceEffects.mDisableAutoBrightness && this.mDisableTapToWake == zenDeviceEffects.mDisableTapToWake && this.mDisableTiltToWake == zenDeviceEffects.mDisableTiltToWake && this.mDisableTouch == zenDeviceEffects.mDisableTouch && this.mMinimizeRadioUsage == zenDeviceEffects.mMinimizeRadioUsage && this.mMaximizeDoze == zenDeviceEffects.mMaximizeDoze && this.mNightLight == zenDeviceEffects.mNightLight && Objects.equals(this.mExtraEffects, zenDeviceEffects.mExtraEffects)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mGrayscale), Boolean.valueOf(this.mSuppressAmbientDisplay), Boolean.valueOf(this.mDimWallpaper), Boolean.valueOf(this.mNightMode), Boolean.valueOf(this.mDisableAutoBrightness), Boolean.valueOf(this.mDisableTapToWake), Boolean.valueOf(this.mDisableTiltToWake), Boolean.valueOf(this.mDisableTouch), Boolean.valueOf(this.mMinimizeRadioUsage), Boolean.valueOf(this.mMaximizeDoze), Boolean.valueOf(this.mNightLight), this.mExtraEffects);
    }

    public String toString() {
        ArrayList arrayList = new ArrayList(11);
        if (this.mGrayscale) {
            arrayList.add("grayscale");
        }
        if (this.mSuppressAmbientDisplay) {
            arrayList.add("suppressAmbientDisplay");
        }
        if (this.mDimWallpaper) {
            arrayList.add("dimWallpaper");
        }
        if (this.mNightMode) {
            arrayList.add("nightMode");
        }
        if (this.mDisableAutoBrightness) {
            arrayList.add("disableAutoBrightness");
        }
        if (this.mDisableTapToWake) {
            arrayList.add("disableTapToWake");
        }
        if (this.mDisableTiltToWake) {
            arrayList.add("disableTiltToWake");
        }
        if (this.mDisableTouch) {
            arrayList.add("disableTouch");
        }
        if (this.mMinimizeRadioUsage) {
            arrayList.add("minimizeRadioUsage");
        }
        if (this.mMaximizeDoze) {
            arrayList.add("maximizeDoze");
        }
        if (this.mNightLight) {
            arrayList.add("nightLight");
        }
        if (this.mExtraEffects.size() > 0) {
            arrayList.add("extraEffects=[" + String.join(",", this.mExtraEffects) + NavigationBarInflaterView.SIZE_MOD_END);
        }
        return NavigationBarInflaterView.SIZE_MOD_START + String.join(", ", arrayList) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static String fieldsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("FIELD_GRAYSCALE");
        }
        if ((i & 2) != 0) {
            arrayList.add("FIELD_SUPPRESS_AMBIENT_DISPLAY");
        }
        if ((i & 4) != 0) {
            arrayList.add("FIELD_DIM_WALLPAPER");
        }
        if ((i & 8) != 0) {
            arrayList.add("FIELD_NIGHT_MODE");
        }
        if ((i & 16) != 0) {
            arrayList.add("FIELD_DISABLE_AUTO_BRIGHTNESS");
        }
        if ((i & 32) != 0) {
            arrayList.add("FIELD_DISABLE_TAP_TO_WAKE");
        }
        if ((i & 64) != 0) {
            arrayList.add("FIELD_DISABLE_TILT_TO_WAKE");
        }
        if ((i & 128) != 0) {
            arrayList.add("FIELD_DISABLE_TOUCH");
        }
        if ((i & 256) != 0) {
            arrayList.add("FIELD_MINIMIZE_RADIO_USAGE");
        }
        if ((i & 512) != 0) {
            arrayList.add("FIELD_MAXIMIZE_DOZE");
        }
        if ((i & 2048) != 0) {
            arrayList.add("FIELD_NIGHT_LIGHT");
        }
        if ((i & 1024) != 0) {
            arrayList.add("FIELD_EXTRA_EFFECTS");
        }
        return "{" + String.join(",", arrayList) + "}";
    }

    public boolean shouldDisplayGrayscale() {
        return this.mGrayscale;
    }

    public boolean shouldSuppressAmbientDisplay() {
        return this.mSuppressAmbientDisplay;
    }

    public boolean shouldDimWallpaper() {
        return this.mDimWallpaper;
    }

    public boolean shouldUseNightMode() {
        return this.mNightMode;
    }

    public boolean shouldDisableAutoBrightness() {
        return this.mDisableAutoBrightness;
    }

    public boolean shouldDisableTapToWake() {
        return this.mDisableTapToWake;
    }

    public boolean shouldDisableTiltToWake() {
        return this.mDisableTiltToWake;
    }

    public boolean shouldDisableTouch() {
        return this.mDisableTouch;
    }

    public boolean shouldMinimizeRadioUsage() {
        return this.mMinimizeRadioUsage;
    }

    public boolean shouldMaximizeDoze() {
        return this.mMaximizeDoze;
    }

    public boolean shouldUseNightLight() {
        return this.mNightLight;
    }

    public Set<String> getExtraEffects() {
        return this.mExtraEffects;
    }

    public boolean hasEffects() {
        return this.mGrayscale || this.mSuppressAmbientDisplay || this.mDimWallpaper || this.mNightMode || this.mDisableAutoBrightness || this.mDisableTapToWake || this.mDisableTiltToWake || this.mDisableTouch || this.mMinimizeRadioUsage || this.mMaximizeDoze || this.mNightLight || this.mExtraEffects.size() > 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mGrayscale);
        parcel.writeBoolean(this.mSuppressAmbientDisplay);
        parcel.writeBoolean(this.mDimWallpaper);
        parcel.writeBoolean(this.mNightMode);
        parcel.writeBoolean(this.mDisableAutoBrightness);
        parcel.writeBoolean(this.mDisableTapToWake);
        parcel.writeBoolean(this.mDisableTiltToWake);
        parcel.writeBoolean(this.mDisableTouch);
        parcel.writeBoolean(this.mMinimizeRadioUsage);
        parcel.writeBoolean(this.mMaximizeDoze);
        parcel.writeBoolean(this.mNightLight);
        parcel.writeArray(this.mExtraEffects.toArray(new String[0]));
    }

    public static final class Builder {
        private boolean mDimWallpaper;
        private boolean mDisableAutoBrightness;
        private boolean mDisableTapToWake;
        private boolean mDisableTiltToWake;
        private boolean mDisableTouch;
        private final HashSet<String> mExtraEffects;
        private boolean mGrayscale;
        private boolean mMaximizeDoze;
        private boolean mMinimizeRadioUsage;
        private boolean mNightLight;
        private boolean mNightMode;
        private boolean mSuppressAmbientDisplay;

        public Builder() {
            this.mExtraEffects = new HashSet<>();
        }

        public Builder(ZenDeviceEffects zenDeviceEffects) {
            HashSet<String> hashSet = new HashSet<>();
            this.mExtraEffects = hashSet;
            this.mGrayscale = zenDeviceEffects.shouldDisplayGrayscale();
            this.mSuppressAmbientDisplay = zenDeviceEffects.shouldSuppressAmbientDisplay();
            this.mDimWallpaper = zenDeviceEffects.shouldDimWallpaper();
            this.mNightMode = zenDeviceEffects.shouldUseNightMode();
            this.mDisableAutoBrightness = zenDeviceEffects.shouldDisableAutoBrightness();
            this.mDisableTapToWake = zenDeviceEffects.shouldDisableTapToWake();
            this.mDisableTiltToWake = zenDeviceEffects.shouldDisableTiltToWake();
            this.mDisableTouch = zenDeviceEffects.shouldDisableTouch();
            this.mMinimizeRadioUsage = zenDeviceEffects.shouldMinimizeRadioUsage();
            this.mMaximizeDoze = zenDeviceEffects.shouldMaximizeDoze();
            this.mNightLight = zenDeviceEffects.shouldUseNightLight();
            hashSet.addAll(zenDeviceEffects.getExtraEffects());
        }

        public Builder setShouldDisplayGrayscale(boolean z) {
            this.mGrayscale = z;
            return this;
        }

        public Builder setShouldSuppressAmbientDisplay(boolean z) {
            this.mSuppressAmbientDisplay = z;
            return this;
        }

        public Builder setShouldDimWallpaper(boolean z) {
            this.mDimWallpaper = z;
            return this;
        }

        public Builder setShouldUseNightMode(boolean z) {
            this.mNightMode = z;
            return this;
        }

        public Builder setShouldDisableAutoBrightness(boolean z) {
            this.mDisableAutoBrightness = z;
            return this;
        }

        public Builder setShouldDisableTapToWake(boolean z) {
            this.mDisableTapToWake = z;
            return this;
        }

        public Builder setShouldDisableTiltToWake(boolean z) {
            this.mDisableTiltToWake = z;
            return this;
        }

        public Builder setShouldDisableTouch(boolean z) {
            this.mDisableTouch = z;
            return this;
        }

        public Builder setShouldMinimizeRadioUsage(boolean z) {
            this.mMinimizeRadioUsage = z;
            return this;
        }

        public Builder setShouldMaximizeDoze(boolean z) {
            this.mMaximizeDoze = z;
            return this;
        }

        public Builder setShouldUseNightLight(boolean z) {
            this.mNightLight = z;
            return this;
        }

        public Builder setExtraEffects(Set<String> set) {
            Objects.requireNonNull(set);
            this.mExtraEffects.clear();
            this.mExtraEffects.addAll(set);
            return this;
        }

        public Builder addExtraEffects(Set<String> set) {
            this.mExtraEffects.addAll((Collection) Objects.requireNonNull(set));
            return this;
        }

        public Builder addExtraEffect(String str) {
            this.mExtraEffects.add((String) Objects.requireNonNull(str));
            return this;
        }

        public Builder add(ZenDeviceEffects zenDeviceEffects) {
            if (zenDeviceEffects == null) {
                return this;
            }
            if (zenDeviceEffects.shouldDisplayGrayscale()) {
                setShouldDisplayGrayscale(true);
            }
            if (zenDeviceEffects.shouldSuppressAmbientDisplay()) {
                setShouldSuppressAmbientDisplay(true);
            }
            if (zenDeviceEffects.shouldDimWallpaper()) {
                setShouldDimWallpaper(true);
            }
            if (zenDeviceEffects.shouldUseNightMode()) {
                setShouldUseNightMode(true);
            }
            if (zenDeviceEffects.shouldDisableAutoBrightness()) {
                setShouldDisableAutoBrightness(true);
            }
            if (zenDeviceEffects.shouldDisableTapToWake()) {
                setShouldDisableTapToWake(true);
            }
            if (zenDeviceEffects.shouldDisableTiltToWake()) {
                setShouldDisableTiltToWake(true);
            }
            if (zenDeviceEffects.shouldDisableTouch()) {
                setShouldDisableTouch(true);
            }
            if (zenDeviceEffects.shouldMinimizeRadioUsage()) {
                setShouldMinimizeRadioUsage(true);
            }
            if (zenDeviceEffects.shouldMaximizeDoze()) {
                setShouldMaximizeDoze(true);
            }
            if (zenDeviceEffects.shouldUseNightLight()) {
                setShouldUseNightLight(true);
            }
            addExtraEffects(zenDeviceEffects.getExtraEffects());
            return this;
        }

        public ZenDeviceEffects build() {
            return new ZenDeviceEffects(this.mGrayscale, this.mSuppressAmbientDisplay, this.mDimWallpaper, this.mNightMode, this.mDisableAutoBrightness, this.mDisableTapToWake, this.mDisableTiltToWake, this.mDisableTouch, this.mMinimizeRadioUsage, this.mMaximizeDoze, this.mNightLight, this.mExtraEffects);
        }
    }
}
