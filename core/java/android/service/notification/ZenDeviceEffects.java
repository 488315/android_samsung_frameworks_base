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
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public final class ZenDeviceEffects implements Parcelable {
    public static final Parcelable.Creator<ZenDeviceEffects> CREATOR = new Parcelable.Creator<ZenDeviceEffects>() { // from class: android.service.notification.ZenDeviceEffects.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenDeviceEffects createFromParcel(Parcel in) {
            return new ZenDeviceEffects(in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), in.readBoolean(), Set.of(in.readArray(String.class.getClassLoader(), String.class)));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenDeviceEffects[] newArray(int size) {
            return new ZenDeviceEffects[size];
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
    private final boolean mNightMode;
    private final boolean mSuppressAmbientDisplay;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    private ZenDeviceEffects(boolean grayscale, boolean suppressAmbientDisplay, boolean dimWallpaper, boolean nightMode, boolean disableAutoBrightness, boolean disableTapToWake, boolean disableTiltToWake, boolean disableTouch, boolean minimizeRadioUsage, boolean maximizeDoze, Set<String> extraEffects) {
        this.mGrayscale = grayscale;
        this.mSuppressAmbientDisplay = suppressAmbientDisplay;
        this.mDimWallpaper = dimWallpaper;
        this.mNightMode = nightMode;
        this.mDisableAutoBrightness = disableAutoBrightness;
        this.mDisableTapToWake = disableTapToWake;
        this.mDisableTiltToWake = disableTiltToWake;
        this.mDisableTouch = disableTouch;
        this.mMinimizeRadioUsage = minimizeRadioUsage;
        this.mMaximizeDoze = maximizeDoze;
        this.mExtraEffects = Collections.unmodifiableSet(extraEffects);
    }

    public void validate() {
        int extraEffectsLength = 0;
        for (String extraEffect : this.mExtraEffects) {
            extraEffectsLength += extraEffect.length();
        }
        if (extraEffectsLength > 2000) {
            throw new IllegalArgumentException("Total size of extra effects must be at most 2000 characters");
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ZenDeviceEffects)) {
            return false;
        }
        ZenDeviceEffects that = (ZenDeviceEffects) obj;
        if (obj == this) {
            return true;
        }
        return this.mGrayscale == that.mGrayscale && this.mSuppressAmbientDisplay == that.mSuppressAmbientDisplay && this.mDimWallpaper == that.mDimWallpaper && this.mNightMode == that.mNightMode && this.mDisableAutoBrightness == that.mDisableAutoBrightness && this.mDisableTapToWake == that.mDisableTapToWake && this.mDisableTiltToWake == that.mDisableTiltToWake && this.mDisableTouch == that.mDisableTouch && this.mMinimizeRadioUsage == that.mMinimizeRadioUsage && this.mMaximizeDoze == that.mMaximizeDoze && Objects.equals(this.mExtraEffects, that.mExtraEffects);
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mGrayscale), Boolean.valueOf(this.mSuppressAmbientDisplay), Boolean.valueOf(this.mDimWallpaper), Boolean.valueOf(this.mNightMode), Boolean.valueOf(this.mDisableAutoBrightness), Boolean.valueOf(this.mDisableTapToWake), Boolean.valueOf(this.mDisableTiltToWake), Boolean.valueOf(this.mDisableTouch), Boolean.valueOf(this.mMinimizeRadioUsage), Boolean.valueOf(this.mMaximizeDoze), this.mExtraEffects);
    }

    public String toString() {
        ArrayList<String> effects = new ArrayList<>(11);
        if (this.mGrayscale) {
            effects.add("grayscale");
        }
        if (this.mSuppressAmbientDisplay) {
            effects.add("suppressAmbientDisplay");
        }
        if (this.mDimWallpaper) {
            effects.add("dimWallpaper");
        }
        if (this.mNightMode) {
            effects.add("nightMode");
        }
        if (this.mDisableAutoBrightness) {
            effects.add("disableAutoBrightness");
        }
        if (this.mDisableTapToWake) {
            effects.add("disableTapToWake");
        }
        if (this.mDisableTiltToWake) {
            effects.add("disableTiltToWake");
        }
        if (this.mDisableTouch) {
            effects.add("disableTouch");
        }
        if (this.mMinimizeRadioUsage) {
            effects.add("minimizeRadioUsage");
        }
        if (this.mMaximizeDoze) {
            effects.add("maximizeDoze");
        }
        if (this.mExtraEffects.size() > 0) {
            effects.add("extraEffects=[" + String.join(",", this.mExtraEffects) + NavigationBarInflaterView.SIZE_MOD_END);
        }
        return NavigationBarInflaterView.SIZE_MOD_START + String.join(", ", effects) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static String fieldsToString(int bitmask) {
        ArrayList<String> modified = new ArrayList<>();
        if ((bitmask & 1) != 0) {
            modified.add("FIELD_GRAYSCALE");
        }
        if ((bitmask & 2) != 0) {
            modified.add("FIELD_SUPPRESS_AMBIENT_DISPLAY");
        }
        if ((bitmask & 4) != 0) {
            modified.add("FIELD_DIM_WALLPAPER");
        }
        if ((bitmask & 8) != 0) {
            modified.add("FIELD_NIGHT_MODE");
        }
        if ((bitmask & 16) != 0) {
            modified.add("FIELD_DISABLE_AUTO_BRIGHTNESS");
        }
        if ((bitmask & 32) != 0) {
            modified.add("FIELD_DISABLE_TAP_TO_WAKE");
        }
        if ((bitmask & 64) != 0) {
            modified.add("FIELD_DISABLE_TILT_TO_WAKE");
        }
        if ((bitmask & 128) != 0) {
            modified.add("FIELD_DISABLE_TOUCH");
        }
        if ((bitmask & 256) != 0) {
            modified.add("FIELD_MINIMIZE_RADIO_USAGE");
        }
        if ((bitmask & 512) != 0) {
            modified.add("FIELD_MAXIMIZE_DOZE");
        }
        if ((bitmask & 1024) != 0) {
            modified.add("FIELD_EXTRA_EFFECTS");
        }
        return "{" + String.join(",", modified) + "}";
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

    public Set<String> getExtraEffects() {
        return this.mExtraEffects;
    }

    public boolean hasEffects() {
        return this.mGrayscale || this.mSuppressAmbientDisplay || this.mDimWallpaper || this.mNightMode || this.mDisableAutoBrightness || this.mDisableTapToWake || this.mDisableTiltToWake || this.mDisableTouch || this.mMinimizeRadioUsage || this.mMaximizeDoze || this.mExtraEffects.size() > 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mGrayscale);
        dest.writeBoolean(this.mSuppressAmbientDisplay);
        dest.writeBoolean(this.mDimWallpaper);
        dest.writeBoolean(this.mNightMode);
        dest.writeBoolean(this.mDisableAutoBrightness);
        dest.writeBoolean(this.mDisableTapToWake);
        dest.writeBoolean(this.mDisableTiltToWake);
        dest.writeBoolean(this.mDisableTouch);
        dest.writeBoolean(this.mMinimizeRadioUsage);
        dest.writeBoolean(this.mMaximizeDoze);
        dest.writeArray(this.mExtraEffects.toArray(new String[0]));
    }

    public static final class Builder {
        private boolean mDimWallpaper;
        private boolean mDisableAutoBrightness;
        private boolean mDisableTapToWake;
        private boolean mDisableTiltToWake;
        private boolean mDisableTouch;
        private final HashSet<String> mExtraEffects = new HashSet<>();
        private boolean mGrayscale;
        private boolean mMaximizeDoze;
        private boolean mMinimizeRadioUsage;
        private boolean mNightMode;
        private boolean mSuppressAmbientDisplay;

        public Builder() {
        }

        public Builder(ZenDeviceEffects zenDeviceEffects) {
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
            this.mExtraEffects.addAll(zenDeviceEffects.getExtraEffects());
        }

        public Builder setShouldDisplayGrayscale(boolean grayscale) {
            this.mGrayscale = grayscale;
            return this;
        }

        public Builder setShouldSuppressAmbientDisplay(boolean suppressAmbientDisplay) {
            this.mSuppressAmbientDisplay = suppressAmbientDisplay;
            return this;
        }

        public Builder setShouldDimWallpaper(boolean dimWallpaper) {
            this.mDimWallpaper = dimWallpaper;
            return this;
        }

        public Builder setShouldUseNightMode(boolean nightMode) {
            this.mNightMode = nightMode;
            return this;
        }

        public Builder setShouldDisableAutoBrightness(boolean disableAutoBrightness) {
            this.mDisableAutoBrightness = disableAutoBrightness;
            return this;
        }

        public Builder setShouldDisableTapToWake(boolean disableTapToWake) {
            this.mDisableTapToWake = disableTapToWake;
            return this;
        }

        public Builder setShouldDisableTiltToWake(boolean disableTiltToWake) {
            this.mDisableTiltToWake = disableTiltToWake;
            return this;
        }

        public Builder setShouldDisableTouch(boolean disableTouch) {
            this.mDisableTouch = disableTouch;
            return this;
        }

        public Builder setShouldMinimizeRadioUsage(boolean minimizeRadioUsage) {
            this.mMinimizeRadioUsage = minimizeRadioUsage;
            return this;
        }

        public Builder setShouldMaximizeDoze(boolean maximizeDoze) {
            this.mMaximizeDoze = maximizeDoze;
            return this;
        }

        public Builder setExtraEffects(Set<String> extraEffects) {
            Objects.requireNonNull(extraEffects);
            this.mExtraEffects.clear();
            this.mExtraEffects.addAll(extraEffects);
            return this;
        }

        public Builder addExtraEffects(Set<String> extraEffects) {
            this.mExtraEffects.addAll((Collection) Objects.requireNonNull(extraEffects));
            return this;
        }

        public Builder addExtraEffect(String extraEffect) {
            this.mExtraEffects.add((String) Objects.requireNonNull(extraEffect));
            return this;
        }

        public Builder add(ZenDeviceEffects effects) {
            if (effects == null) {
                return this;
            }
            if (effects.shouldDisplayGrayscale()) {
                setShouldDisplayGrayscale(true);
            }
            if (effects.shouldSuppressAmbientDisplay()) {
                setShouldSuppressAmbientDisplay(true);
            }
            if (effects.shouldDimWallpaper()) {
                setShouldDimWallpaper(true);
            }
            if (effects.shouldUseNightMode()) {
                setShouldUseNightMode(true);
            }
            if (effects.shouldDisableAutoBrightness()) {
                setShouldDisableAutoBrightness(true);
            }
            if (effects.shouldDisableTapToWake()) {
                setShouldDisableTapToWake(true);
            }
            if (effects.shouldDisableTiltToWake()) {
                setShouldDisableTiltToWake(true);
            }
            if (effects.shouldDisableTouch()) {
                setShouldDisableTouch(true);
            }
            if (effects.shouldMinimizeRadioUsage()) {
                setShouldMinimizeRadioUsage(true);
            }
            if (effects.shouldMaximizeDoze()) {
                setShouldMaximizeDoze(true);
            }
            addExtraEffects(effects.getExtraEffects());
            return this;
        }

        public ZenDeviceEffects build() {
            return new ZenDeviceEffects(this.mGrayscale, this.mSuppressAmbientDisplay, this.mDimWallpaper, this.mNightMode, this.mDisableAutoBrightness, this.mDisableTapToWake, this.mDisableTiltToWake, this.mDisableTouch, this.mMinimizeRadioUsage, this.mMaximizeDoze, this.mExtraEffects);
        }
    }
}
