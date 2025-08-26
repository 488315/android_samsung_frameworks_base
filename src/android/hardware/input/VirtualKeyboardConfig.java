package android.hardware.input;

import android.annotation.SystemApi;
import android.hardware.input.VirtualInputDeviceConfig;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class VirtualKeyboardConfig extends VirtualInputDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualKeyboardConfig> CREATOR = new Parcelable.Creator<VirtualKeyboardConfig>() { // from class: android.hardware.input.VirtualKeyboardConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualKeyboardConfig createFromParcel(Parcel parcel) {
            return new VirtualKeyboardConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualKeyboardConfig[] newArray(int i) {
            return new VirtualKeyboardConfig[i];
        }
    };
    public static final String DEFAULT_LANGUAGE_TAG = "en-Latn-US";
    public static final String DEFAULT_LAYOUT_TYPE = "qwerty";
    private final String mLanguageTag;
    private final String mLayoutType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualKeyboardConfig(Builder builder) {
        super(builder);
        this.mLanguageTag = builder.mLanguageTag;
        this.mLayoutType = builder.mLayoutType;
    }

    private VirtualKeyboardConfig(Parcel parcel) {
        super(parcel);
        this.mLanguageTag = parcel.readString8();
        this.mLayoutType = parcel.readString8();
    }

    public String getLanguageTag() {
        return this.mLanguageTag;
    }

    public String getLayoutType() {
        return this.mLayoutType;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.mLanguageTag);
        parcel.writeString8(this.mLayoutType);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    String additionalFieldsToString() {
        return " languageTag=" + this.mLanguageTag + " layoutType=" + this.mLayoutType;
    }

    public static final class Builder extends VirtualInputDeviceConfig.Builder<Builder> {
        private String mLanguageTag = VirtualKeyboardConfig.DEFAULT_LANGUAGE_TAG;
        private String mLayoutType = "qwerty";

        public Builder setLanguageTag(String str) {
            Objects.requireNonNull(str, "languageTag cannot be null");
            ULocale uLocaleForLanguageTag = ULocale.forLanguageTag(str);
            if (uLocaleForLanguageTag.getLanguage().isEmpty()) {
                throw new IllegalArgumentException("The language tag is not valid.");
            }
            this.mLanguageTag = ULocale.createCanonical(uLocaleForLanguageTag).toLanguageTag();
            return this;
        }

        public Builder setLayoutType(String str) {
            Objects.requireNonNull(str, "layoutType cannot be null");
            this.mLayoutType = str;
            return this;
        }

        public VirtualKeyboardConfig build() {
            return new VirtualKeyboardConfig(this);
        }
    }
}
