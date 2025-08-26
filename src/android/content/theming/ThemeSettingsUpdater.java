package android.content.theming;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes.dex */
public class ThemeSettingsUpdater implements Parcelable {
    public static final Parcelable.Creator<ThemeSettingsUpdater> CREATOR = new Parcelable.Creator<ThemeSettingsUpdater>() { // from class: android.content.theming.ThemeSettingsUpdater.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThemeSettingsUpdater createFromParcel(Parcel parcel) {
            return new ThemeSettingsUpdater(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ThemeSettingsUpdater[] newArray(int i) {
            return new ThemeSettingsUpdater[i];
        }
    };
    private Integer mAccentColor;
    private Boolean mColorBoth;
    private Integer mColorIndex;
    private String mColorSource;
    private Integer mSystemPalette;
    private Integer mThemeStyle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ThemeSettingsUpdater(Integer num, Integer num2, Integer num3, String str, Integer num4, Boolean bool) {
        this.mAccentColor = num3;
        this.mColorBoth = bool;
        this.mColorIndex = num;
        this.mColorSource = str;
        this.mSystemPalette = num2;
        this.mThemeStyle = num4;
    }

    ThemeSettingsUpdater() {
    }

    protected ThemeSettingsUpdater(Parcel parcel) {
        this.mAccentColor = (Integer) parcel.readValue(null);
        this.mColorBoth = (Boolean) parcel.readValue(null);
        this.mColorIndex = (Integer) parcel.readValue(null);
        this.mColorSource = (String) parcel.readValue(null);
        this.mSystemPalette = (Integer) parcel.readValue(null);
        this.mThemeStyle = (Integer) parcel.readValue(null);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeValue(this.mAccentColor);
        parcel.writeValue(this.mColorBoth);
        parcel.writeValue(this.mColorIndex);
        parcel.writeValue(this.mColorSource);
        parcel.writeValue(this.mSystemPalette);
        parcel.writeValue(this.mThemeStyle);
    }

    public ThemeSettingsUpdater colorIndex(int i) {
        this.mColorIndex = Integer.valueOf(i);
        return this;
    }

    public Integer getColorIndex() {
        return this.mColorIndex;
    }

    public ThemeSettingsUpdater systemPalette(int i) {
        this.mSystemPalette = Integer.valueOf(i);
        return this;
    }

    public Integer getSystemPalette() {
        return this.mSystemPalette;
    }

    public ThemeSettingsUpdater accentColor(int i) {
        this.mAccentColor = Integer.valueOf(i);
        return this;
    }

    public Integer getAccentColor() {
        return this.mAccentColor;
    }

    public ThemeSettingsUpdater colorSource(String str) {
        this.mColorSource = str;
        return this;
    }

    public Integer getThemeStyle() {
        return this.mThemeStyle;
    }

    public ThemeSettingsUpdater themeStyle(int i) {
        this.mThemeStyle = Integer.valueOf(i);
        return this;
    }

    public String getColorSource() {
        return this.mColorSource;
    }

    public ThemeSettingsUpdater colorBoth(boolean z) {
        this.mColorBoth = Boolean.valueOf(z);
        return this;
    }

    public Boolean getColorBoth() {
        return this.mColorBoth;
    }

    public ThemeSettings toThemeSettings(ThemeSettings themeSettings) {
        return new ThemeSettings(((Integer) Objects.requireNonNullElse(this.mColorIndex, themeSettings.colorIndex())).intValue(), ((Integer) Objects.requireNonNullElse(this.mSystemPalette, themeSettings.systemPalette())).intValue(), ((Integer) Objects.requireNonNullElse(this.mAccentColor, themeSettings.accentColor())).intValue(), (String) Objects.requireNonNullElse(this.mColorSource, themeSettings.colorSource()), ((Integer) Objects.requireNonNullElse(this.mThemeStyle, themeSettings.themeStyle())).intValue(), ((Boolean) Objects.requireNonNullElse(this.mColorBoth, themeSettings.colorBoth())).booleanValue());
    }
}
