package android.text;

import android.annotation.SystemApi;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.icu.util.ULocale;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class FontConfig implements Parcelable {
    public static final Parcelable.Creator<FontConfig> CREATOR = new Parcelable.Creator<FontConfig>() { // from class: android.text.FontConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FontConfig createFromParcel(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            parcel.readTypedList(arrayList, FontFamily.CREATOR);
            ArrayList arrayList2 = new ArrayList();
            parcel.readTypedList(arrayList2, Alias.CREATOR);
            ArrayList arrayList3 = new ArrayList();
            parcel.readTypedList(arrayList3, NamedFamilyList.CREATOR);
            return new FontConfig(arrayList, arrayList2, arrayList3, Collections.EMPTY_LIST, parcel.readLong(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FontConfig[] newArray(int i) {
            return new FontConfig[i];
        }
    };
    private final List<Alias> mAliases;
    private final int mConfigVersion;
    private final List<FontFamily> mFamilies;
    private final long mLastModifiedTimeMillis;
    private final List<Customization.LocaleFallback> mLocaleFallbackCustomizations;
    private final List<NamedFamilyList> mNamedFamilyLists;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FontConfig(List<FontFamily> list, List<Alias> list2, List<NamedFamilyList> list3, List<Customization.LocaleFallback> list4, long j, int i) {
        this.mFamilies = list;
        this.mAliases = list2;
        this.mNamedFamilyLists = list3;
        this.mLocaleFallbackCustomizations = list4;
        this.mLastModifiedTimeMillis = j;
        this.mConfigVersion = i;
    }

    public FontConfig(List<FontFamily> list, List<Alias> list2, long j, int i) {
        this(list, list2, Collections.EMPTY_LIST, Collections.EMPTY_LIST, j, i);
    }

    public List<FontFamily> getFontFamilies() {
        return this.mFamilies;
    }

    public List<Alias> getAliases() {
        return this.mAliases;
    }

    public List<NamedFamilyList> getNamedFamilyLists() {
        return this.mNamedFamilyLists;
    }

    public List<Customization.LocaleFallback> getLocaleFallbackCustomizations() {
        return this.mLocaleFallbackCustomizations;
    }

    public long getLastModifiedTimeMillis() {
        return this.mLastModifiedTimeMillis;
    }

    public int getConfigVersion() {
        return this.mConfigVersion;
    }

    @Deprecated
    public FontFamily[] getFamilies() {
        return (FontFamily[]) this.mFamilies.toArray(new FontFamily[0]);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mFamilies, i);
        parcel.writeTypedList(this.mAliases, i);
        parcel.writeTypedList(this.mNamedFamilyLists, i);
        parcel.writeLong(this.mLastModifiedTimeMillis);
        parcel.writeInt(this.mConfigVersion);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            FontConfig fontConfig = (FontConfig) obj;
            if (this.mLastModifiedTimeMillis == fontConfig.mLastModifiedTimeMillis && this.mConfigVersion == fontConfig.mConfigVersion && Objects.equals(this.mFamilies, fontConfig.mFamilies) && Objects.equals(this.mAliases, fontConfig.mAliases)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mFamilies, this.mAliases, Long.valueOf(this.mLastModifiedTimeMillis), Integer.valueOf(this.mConfigVersion));
    }

    public String toString() {
        return "FontConfig{mFamilies=" + this.mFamilies + ", mAliases=" + this.mAliases + ", mLastModifiedTimeMillis=" + this.mLastModifiedTimeMillis + ", mConfigVersion=" + this.mConfigVersion + '}';
    }

    public static final class Font implements Parcelable {
        public static final Parcelable.Creator<Font> CREATOR = new Parcelable.Creator<Font>() { // from class: android.text.FontConfig.Font.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Font createFromParcel(Parcel parcel) {
                File file = new File(parcel.readString8());
                String string8 = parcel.readString8();
                return new Font(file, string8 == null ? null : new File(string8), parcel.readString8(), new FontStyle(parcel.readInt(), parcel.readInt()), parcel.readInt(), parcel.readString8(), parcel.readString8(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Font[] newArray(int i) {
                return new Font[i];
            }
        };
        public static final int VAR_TYPE_AXES_ITAL = 2;
        public static final int VAR_TYPE_AXES_NONE = 0;
        public static final int VAR_TYPE_AXES_WGHT = 1;
        private final File mFile;
        private final String mFontFamilyName;
        private final String mFontVariationSettings;
        private final int mIndex;
        private final File mOriginalFile;
        private final String mPostScriptName;
        private final FontStyle mStyle;
        private final int mVarTypeAxes;

        @Retention(RetentionPolicy.SOURCE)
        public @interface VarTypeAxes {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Font(File file, File file2, String str, FontStyle fontStyle, int i, String str2, String str3, int i2) {
            this.mFile = file;
            this.mOriginalFile = file2;
            this.mPostScriptName = str;
            this.mStyle = fontStyle;
            this.mIndex = i;
            this.mFontVariationSettings = str2;
            this.mFontFamilyName = str3;
            this.mVarTypeAxes = i2;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mFile.getAbsolutePath());
            File file = this.mOriginalFile;
            parcel.writeString8(file == null ? null : file.getAbsolutePath());
            parcel.writeString8(this.mPostScriptName);
            parcel.writeInt(this.mStyle.getWeight());
            parcel.writeInt(this.mStyle.getSlant());
            parcel.writeInt(this.mIndex);
            parcel.writeString8(this.mFontVariationSettings);
            parcel.writeString8(this.mFontFamilyName);
            parcel.writeInt(this.mVarTypeAxes);
        }

        public File getFile() {
            return this.mFile;
        }

        public File getOriginalFile() {
            return this.mOriginalFile;
        }

        public FontStyle getStyle() {
            return this.mStyle;
        }

        public String getFontVariationSettings() {
            return this.mFontVariationSettings;
        }

        public String getFontFamilyName() {
            return this.mFontFamilyName;
        }

        public int getTtcIndex() {
            return this.mIndex;
        }

        public String getPostScriptName() {
            return this.mPostScriptName;
        }

        public int getVarTypeAxes() {
            return this.mVarTypeAxes;
        }

        @Deprecated
        public FontVariationAxis[] getAxes() {
            return FontVariationAxis.fromFontVariationSettings(this.mFontVariationSettings);
        }

        @Deprecated
        public int getWeight() {
            return getStyle().getWeight();
        }

        @Deprecated
        public boolean isItalic() {
            return getStyle().getSlant() == 1;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Font font = (Font) obj;
                if (this.mIndex == font.mIndex && Objects.equals(this.mFile, font.mFile) && Objects.equals(this.mOriginalFile, font.mOriginalFile) && Objects.equals(this.mStyle, font.mStyle) && Objects.equals(this.mFontVariationSettings, font.mFontVariationSettings) && Objects.equals(this.mFontFamilyName, font.mFontFamilyName) && this.mVarTypeAxes == font.mVarTypeAxes) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mFile, this.mOriginalFile, this.mStyle, Integer.valueOf(this.mIndex), this.mFontVariationSettings, this.mFontFamilyName, Integer.valueOf(this.mVarTypeAxes));
        }

        public String toString() {
            return "Font{mFile=" + this.mFile + ", mOriginalFile=" + this.mOriginalFile + ", mStyle=" + this.mStyle + ", mIndex=" + this.mIndex + ", mFontVariationSettings='" + this.mFontVariationSettings + "', mFontFamilyName='" + this.mFontFamilyName + "', mVarTypeAxes='" + this.mVarTypeAxes + "'}";
        }
    }

    public static final class Alias implements Parcelable {
        public static final Parcelable.Creator<Alias> CREATOR = new Parcelable.Creator<Alias>() { // from class: android.text.FontConfig.Alias.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Alias createFromParcel(Parcel parcel) {
                return new Alias(parcel.readString8(), parcel.readString8(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Alias[] newArray(int i) {
                return new Alias[i];
            }
        };
        private final String mName;
        private final String mOriginal;
        private final int mWeight;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Alias(String str, String str2, int i) {
            this.mName = str;
            this.mOriginal = str2;
            this.mWeight = i;
        }

        public String getName() {
            return this.mName;
        }

        public String getOriginal() {
            return this.mOriginal;
        }

        public int getWeight() {
            return this.mWeight;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mName);
            parcel.writeString8(this.mOriginal);
            parcel.writeInt(this.mWeight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Alias alias = (Alias) obj;
                if (this.mWeight == alias.mWeight && Objects.equals(this.mName, alias.mName) && Objects.equals(this.mOriginal, alias.mOriginal)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mName, this.mOriginal, Integer.valueOf(this.mWeight));
        }

        public String toString() {
            return "Alias{mName='" + this.mName + "', mOriginal='" + this.mOriginal + "', mWeight=" + this.mWeight + '}';
        }
    }

    public static final class FontFamily implements Parcelable {
        public static final Parcelable.Creator<FontFamily> CREATOR = new Parcelable.Creator<FontFamily>() { // from class: android.text.FontConfig.FontFamily.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FontFamily createFromParcel(Parcel parcel) {
                ArrayList arrayList = new ArrayList();
                parcel.readTypedList(arrayList, Font.CREATOR);
                String string8 = parcel.readString8();
                return new FontFamily(arrayList, LocaleList.forLanguageTags(string8), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FontFamily[] newArray(int i) {
                return new FontFamily[i];
            }
        };
        public static final int VARIANT_COMPACT = 1;
        public static final int VARIANT_DEFAULT = 0;
        public static final int VARIANT_ELEGANT = 2;
        private final List<Font> mFonts;
        private final LocaleList mLocaleList;
        private final int mVariant;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Variant {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Deprecated
        public String getName() {
            return null;
        }

        public FontFamily(List<Font> list, LocaleList localeList, int i) {
            this.mFonts = list;
            this.mLocaleList = localeList;
            this.mVariant = i;
        }

        public List<Font> getFontList() {
            return this.mFonts;
        }

        public LocaleList getLocaleList() {
            return this.mLocaleList;
        }

        public int getVariant() {
            return this.mVariant;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(this.mFonts, i);
            parcel.writeString8(this.mLocaleList.toLanguageTags());
            parcel.writeInt(this.mVariant);
        }

        @Deprecated
        public Font[] getFonts() {
            return (Font[]) this.mFonts.toArray(new Font[0]);
        }

        @Deprecated
        public String getLanguages() {
            return this.mLocaleList.toLanguageTags();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                FontFamily fontFamily = (FontFamily) obj;
                if (this.mVariant == fontFamily.mVariant && Objects.equals(this.mFonts, fontFamily.mFonts) && Objects.equals(this.mLocaleList, fontFamily.mLocaleList)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mFonts, this.mLocaleList, Integer.valueOf(this.mVariant));
        }

        public String toString() {
            return "FontFamily{mFonts=" + this.mFonts + ", mLocaleList=" + this.mLocaleList + ", mVariant=" + this.mVariant + '}';
        }
    }

    public static final class NamedFamilyList implements Parcelable {
        public static final Parcelable.Creator<NamedFamilyList> CREATOR = new Parcelable.Creator<NamedFamilyList>() { // from class: android.text.FontConfig.NamedFamilyList.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NamedFamilyList createFromParcel(Parcel parcel) {
                ArrayList arrayList = new ArrayList();
                parcel.readTypedList(arrayList, FontFamily.CREATOR);
                return new NamedFamilyList(arrayList, parcel.readString8());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NamedFamilyList[] newArray(int i) {
                return new NamedFamilyList[i];
            }
        };
        private final List<FontFamily> mFamilies;
        private final String mName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public NamedFamilyList(List<FontFamily> list, String str) {
            this.mFamilies = list;
            this.mName = str;
        }

        public NamedFamilyList(FontFamily fontFamily) {
            ArrayList arrayList = new ArrayList();
            this.mFamilies = arrayList;
            arrayList.add(fontFamily);
            this.mName = fontFamily.getName();
        }

        public List<FontFamily> getFamilies() {
            return this.mFamilies;
        }

        public String getName() {
            return this.mName;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(this.mFamilies, i);
            parcel.writeString8(this.mName);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                NamedFamilyList namedFamilyList = (NamedFamilyList) obj;
                if (Objects.equals(this.mFamilies, namedFamilyList.mFamilies) && Objects.equals(this.mName, namedFamilyList.mName)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mFamilies, this.mName);
        }

        public String toString() {
            return "NamedFamilyList{mFamilies=" + this.mFamilies + ", mName='" + this.mName + "'}";
        }
    }

    public static class Customization {
        private Customization() {
        }

        public static class LocaleFallback {
            public static final int OPERATION_APPEND = 1;
            public static final int OPERATION_PREPEND = 0;
            public static final int OPERATION_REPLACE = 2;
            private final FontFamily mFamily;
            private final Locale mLocale;
            private final int mOperation;
            private final String mScript;

            @Retention(RetentionPolicy.SOURCE)
            public @interface Operation {
            }

            public LocaleFallback(Locale locale, int i, FontFamily fontFamily) {
                this.mLocale = locale;
                this.mOperation = i;
                this.mFamily = fontFamily;
                this.mScript = FontConfig.resolveScript(locale);
            }

            public Locale getLocale() {
                return this.mLocale;
            }

            public int getOperation() {
                return this.mOperation;
            }

            public FontFamily getFamily() {
                return this.mFamily;
            }

            public String getScript() {
                return this.mScript;
            }

            public String toString() {
                return "LocaleFallback{mLocale=" + this.mLocale + ", mOperation=" + this.mOperation + ", mFamily=" + this.mFamily + '}';
            }
        }
    }

    public static String resolveScript(Locale locale) {
        String script = locale.getScript();
        return (script == null || script.isEmpty()) ? ULocale.addLikelySubtags(ULocale.forLocale(locale)).getScript() : script;
    }
}
