package android.content.theming;

import android.app.blob.XmlTags;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ThemeSettings implements Parcelable {
    public static final Parcelable.Creator<ThemeSettings> CREATOR = new Parcelable.Creator<ThemeSettings>() { // from class: android.content.theming.ThemeSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThemeSettings createFromParcel(Parcel parcel) {
            return new ThemeSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThemeSettings[] newArray(int i) {
            return new ThemeSettings[i];
        }
    };
    private final int mAccentColor;
    private final boolean mColorBoth;
    private final int mColorIndex;
    private final String mColorSource;
    private final int mSystemPalette;
    private final int mThemeStyle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ThemeSettings(int i, int i2, int i3, String str, int i4, boolean z) {
        this.mAccentColor = i3;
        this.mColorBoth = z;
        this.mColorIndex = i;
        this.mColorSource = str;
        this.mSystemPalette = i2;
        this.mThemeStyle = i4;
    }

    ThemeSettings(Parcel parcel) {
        this.mAccentColor = parcel.readInt();
        this.mColorBoth = parcel.readBoolean();
        this.mColorIndex = parcel.readInt();
        this.mColorSource = (String) Objects.requireNonNullElse(parcel.readString8(), XmlTags.TAG_SESSION);
        this.mSystemPalette = parcel.readInt();
        this.mThemeStyle = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAccentColor);
        parcel.writeBoolean(this.mColorBoth);
        parcel.writeInt(this.mColorIndex);
        parcel.writeString8(this.mColorSource);
        parcel.writeInt(this.mSystemPalette);
        parcel.writeInt(this.mThemeStyle);
    }

    public Integer colorIndex() {
        return Integer.valueOf(this.mColorIndex);
    }

    public Integer systemPalette() {
        return Integer.valueOf(this.mSystemPalette);
    }

    public Integer accentColor() {
        return Integer.valueOf(this.mAccentColor);
    }

    public String colorSource() {
        return this.mColorSource;
    }

    public Integer themeStyle() {
        return Integer.valueOf(this.mThemeStyle);
    }

    public Boolean colorBoth() {
        return Boolean.valueOf(this.mColorBoth);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ThemeSettings)) {
            return false;
        }
        ThemeSettings themeSettings = (ThemeSettings) obj;
        return this.mColorIndex == themeSettings.mColorIndex && this.mSystemPalette == themeSettings.mSystemPalette && this.mAccentColor == themeSettings.mAccentColor && this.mColorSource.equals(themeSettings.mColorSource) && this.mThemeStyle == themeSettings.mThemeStyle && this.mColorBoth == themeSettings.mColorBoth;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mColorIndex), Integer.valueOf(this.mSystemPalette), Integer.valueOf(this.mAccentColor), this.mColorSource, Integer.valueOf(this.mThemeStyle), Boolean.valueOf(this.mColorBoth));
    }

    public static ThemeSettingsUpdater updater() {
        return new ThemeSettingsUpdater();
    }
}
