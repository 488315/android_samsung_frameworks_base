package android.text;

import android.annotation.SystemApi;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.icu.util.ULocale;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SystemApi
public final class FontConfig implements Parcelable {
    public static final Parcelable.Creator<FontConfig> CREATOR =
            new Parcelable.Creator<FontConfig>() { // from class: android.text.FontConfig.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FontConfig createFromParcel(Parcel source) {
                    ArrayList arrayList = new ArrayList();
                    source.readTypedList(arrayList, FontFamily.CREATOR);
                    ArrayList arrayList2 = new ArrayList();
                    source.readTypedList(arrayList2, Alias.CREATOR);
                    ArrayList arrayList3 = new ArrayList();
                    source.readTypedList(arrayList3, NamedFamilyList.CREATOR);
                    long lastModifiedDate = source.readLong();
                    int configVersion = source.readInt();
                    return new FontConfig(
                            arrayList,
                            arrayList2,
                            arrayList3,
                            Collections.emptyList(),
                            lastModifiedDate,
                            configVersion);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FontConfig[] newArray(int size) {
                    return new FontConfig[size];
                }
            };
    private final List<Alias> mAliases;
    private final int mConfigVersion;
    private final List<FontFamily> mFamilies;
    private final long mLastModifiedTimeMillis;
    private final List<Customization.LocaleFallback> mLocaleFallbackCustomizations;
    private final List<NamedFamilyList> mNamedFamilyLists;

    public FontConfig(
            List<FontFamily> families,
            List<Alias> aliases,
            List<NamedFamilyList> namedFamilyLists,
            List<Customization.LocaleFallback> localeFallbackCustomizations,
            long lastModifiedTimeMillis,
            int configVersion) {
        this.mFamilies = families;
        this.mAliases = aliases;
        this.mNamedFamilyLists = namedFamilyLists;
        this.mLocaleFallbackCustomizations = localeFallbackCustomizations;
        this.mLastModifiedTimeMillis = lastModifiedTimeMillis;
        this.mConfigVersion = configVersion;
    }

    public FontConfig(
            List<FontFamily> families,
            List<Alias> aliases,
            long lastModifiedTimeMillis,
            int configVersion) {
        this(
                families,
                aliases,
                Collections.emptyList(),
                Collections.emptyList(),
                lastModifiedTimeMillis,
                configVersion);
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mFamilies, flags);
        dest.writeTypedList(this.mAliases, flags);
        dest.writeTypedList(this.mNamedFamilyLists, flags);
        dest.writeLong(this.mLastModifiedTimeMillis);
        dest.writeInt(this.mConfigVersion);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FontConfig that = (FontConfig) o;
        if (this.mLastModifiedTimeMillis == that.mLastModifiedTimeMillis
                && this.mConfigVersion == that.mConfigVersion
                && Objects.equals(this.mFamilies, that.mFamilies)
                && Objects.equals(this.mAliases, that.mAliases)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                this.mFamilies,
                this.mAliases,
                Long.valueOf(this.mLastModifiedTimeMillis),
                Integer.valueOf(this.mConfigVersion));
    }

    public String toString() {
        return "FontConfig{mFamilies="
                + this.mFamilies
                + ", mAliases="
                + this.mAliases
                + ", mLastModifiedTimeMillis="
                + this.mLastModifiedTimeMillis
                + ", mConfigVersion="
                + this.mConfigVersion
                + '}';
    }

    public static final class Font implements Parcelable {
        public static final Parcelable.Creator<Font> CREATOR =
                new Parcelable.Creator<Font>() { // from class: android.text.FontConfig.Font.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Font createFromParcel(Parcel source) {
                        File path = new File(source.readString8());
                        String originalPathStr = source.readString8();
                        File originalPath =
                                originalPathStr == null ? null : new File(originalPathStr);
                        String postScriptName = source.readString8();
                        int weight = source.readInt();
                        int slant = source.readInt();
                        int index = source.readInt();
                        String varSettings = source.readString8();
                        String fallback = source.readString8();
                        int varTypeAxes = source.readInt();
                        return new Font(
                                path,
                                originalPath,
                                postScriptName,
                                new FontStyle(weight, slant),
                                index,
                                varSettings,
                                fallback,
                                varTypeAxes);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Font[] newArray(int size) {
                        return new Font[size];
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
        public @interface VarTypeAxes {}

        public Font(
                File file,
                File originalFile,
                String postScriptName,
                FontStyle style,
                int index,
                String fontVariationSettings,
                String fontFamilyName,
                int varTypeAxes) {
            this.mFile = file;
            this.mOriginalFile = originalFile;
            this.mPostScriptName = postScriptName;
            this.mStyle = style;
            this.mIndex = index;
            this.mFontVariationSettings = fontVariationSettings;
            this.mFontFamilyName = fontFamilyName;
            this.mVarTypeAxes = varTypeAxes;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString8(this.mFile.getAbsolutePath());
            dest.writeString8(
                    this.mOriginalFile == null ? null : this.mOriginalFile.getAbsolutePath());
            dest.writeString8(this.mPostScriptName);
            dest.writeInt(this.mStyle.getWeight());
            dest.writeInt(this.mStyle.getSlant());
            dest.writeInt(this.mIndex);
            dest.writeString8(this.mFontVariationSettings);
            dest.writeString8(this.mFontFamilyName);
            dest.writeInt(this.mVarTypeAxes);
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

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Font font = (Font) o;
            if (this.mIndex == font.mIndex
                    && Objects.equals(this.mFile, font.mFile)
                    && Objects.equals(this.mOriginalFile, font.mOriginalFile)
                    && Objects.equals(this.mStyle, font.mStyle)
                    && Objects.equals(this.mFontVariationSettings, font.mFontVariationSettings)
                    && Objects.equals(this.mFontFamilyName, font.mFontFamilyName)
                    && this.mVarTypeAxes == font.mVarTypeAxes) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(
                    this.mFile,
                    this.mOriginalFile,
                    this.mStyle,
                    Integer.valueOf(this.mIndex),
                    this.mFontVariationSettings,
                    this.mFontFamilyName,
                    Integer.valueOf(this.mVarTypeAxes));
        }

        public String toString() {
            return "Font{mFile="
                    + this.mFile
                    + ", mOriginalFile="
                    + this.mOriginalFile
                    + ", mStyle="
                    + this.mStyle
                    + ", mIndex="
                    + this.mIndex
                    + ", mFontVariationSettings='"
                    + this.mFontVariationSettings
                    + DateFormat.QUOTE
                    + ", mFontFamilyName='"
                    + this.mFontFamilyName
                    + DateFormat.QUOTE
                    + ", mVarTypeAxes='"
                    + this.mVarTypeAxes
                    + DateFormat.QUOTE
                    + '}';
        }
    }

    public static final class Alias implements Parcelable {
        public static final Parcelable.Creator<Alias> CREATOR =
                new Parcelable.Creator<Alias>() { // from class: android.text.FontConfig.Alias.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Alias createFromParcel(Parcel source) {
                        String alias = source.readString8();
                        String referName = source.readString8();
                        int weight = source.readInt();
                        return new Alias(alias, referName, weight);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public Alias[] newArray(int size) {
                        return new Alias[size];
                    }
                };
        private final String mName;
        private final String mOriginal;
        private final int mWeight;

        public Alias(String name, String original, int weight) {
            this.mName = name;
            this.mOriginal = original;
            this.mWeight = weight;
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
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString8(this.mName);
            dest.writeString8(this.mOriginal);
            dest.writeInt(this.mWeight);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Alias alias = (Alias) o;
            if (this.mWeight == alias.mWeight
                    && Objects.equals(this.mName, alias.mName)
                    && Objects.equals(this.mOriginal, alias.mOriginal)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mName, this.mOriginal, Integer.valueOf(this.mWeight));
        }

        public String toString() {
            return "Alias{mName='"
                    + this.mName
                    + DateFormat.QUOTE
                    + ", mOriginal='"
                    + this.mOriginal
                    + DateFormat.QUOTE
                    + ", mWeight="
                    + this.mWeight
                    + '}';
        }
    }

    public static final class FontFamily implements Parcelable {
        public static final Parcelable.Creator<FontFamily> CREATOR =
                new Parcelable.Creator<
                        FontFamily>() { // from class: android.text.FontConfig.FontFamily.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FontFamily createFromParcel(Parcel source) {
                        ArrayList arrayList = new ArrayList();
                        source.readTypedList(arrayList, Font.CREATOR);
                        String langTags = source.readString8();
                        int variant = source.readInt();
                        return new FontFamily(
                                arrayList, LocaleList.forLanguageTags(langTags), variant);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public FontFamily[] newArray(int size) {
                        return new FontFamily[size];
                    }
                };
        public static final int VARIANT_COMPACT = 1;
        public static final int VARIANT_DEFAULT = 0;
        public static final int VARIANT_ELEGANT = 2;
        private final List<Font> mFonts;
        private final LocaleList mLocaleList;
        private final int mVariant;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Variant {}

        public FontFamily(List<Font> fonts, LocaleList localeList, int variant) {
            this.mFonts = fonts;
            this.mLocaleList = localeList;
            this.mVariant = variant;
        }

        public List<Font> getFontList() {
            return this.mFonts;
        }

        @Deprecated
        public String getName() {
            return null;
        }

        public LocaleList getLocaleList() {
            return this.mLocaleList;
        }

        public int getVariant() {
            return this.mVariant;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.mFonts, flags);
            dest.writeString8(this.mLocaleList.toLanguageTags());
            dest.writeInt(this.mVariant);
        }

        @Deprecated
        public Font[] getFonts() {
            return (Font[]) this.mFonts.toArray(new Font[0]);
        }

        @Deprecated
        public String getLanguages() {
            return this.mLocaleList.toLanguageTags();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FontFamily that = (FontFamily) o;
            if (this.mVariant == that.mVariant
                    && Objects.equals(this.mFonts, that.mFonts)
                    && Objects.equals(this.mLocaleList, that.mLocaleList)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mFonts, this.mLocaleList, Integer.valueOf(this.mVariant));
        }

        public String toString() {
            return "FontFamily{mFonts="
                    + this.mFonts
                    + ", mLocaleList="
                    + this.mLocaleList
                    + ", mVariant="
                    + this.mVariant
                    + '}';
        }
    }

    public static final class NamedFamilyList implements Parcelable {
        public static final Parcelable.Creator<NamedFamilyList> CREATOR =
                new Parcelable.Creator<
                        NamedFamilyList>() { // from class:
                                             // android.text.FontConfig.NamedFamilyList.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public NamedFamilyList createFromParcel(Parcel source) {
                        ArrayList arrayList = new ArrayList();
                        source.readTypedList(arrayList, FontFamily.CREATOR);
                        String name = source.readString8();
                        return new NamedFamilyList(arrayList, name);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public NamedFamilyList[] newArray(int size) {
                        return new NamedFamilyList[size];
                    }
                };
        private final List<FontFamily> mFamilies;
        private final String mName;

        public NamedFamilyList(List<FontFamily> families, String name) {
            this.mFamilies = families;
            this.mName = name;
        }

        public NamedFamilyList(FontFamily family) {
            this.mFamilies = new ArrayList();
            this.mFamilies.add(family);
            this.mName = family.getName();
        }

        public List<FontFamily> getFamilies() {
            return this.mFamilies;
        }

        public String getName() {
            return this.mName;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.mFamilies, flags);
            dest.writeString8(this.mName);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            NamedFamilyList that = (NamedFamilyList) o;
            if (Objects.equals(this.mFamilies, that.mFamilies)
                    && Objects.equals(this.mName, that.mName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mFamilies, this.mName);
        }

        public String toString() {
            return "NamedFamilyList{mFamilies="
                    + this.mFamilies
                    + ", mName='"
                    + this.mName
                    + DateFormat.QUOTE
                    + '}';
        }
    }

    public static class Customization {
        private Customization() {}

        public static class LocaleFallback {
            public static final int OPERATION_APPEND = 1;
            public static final int OPERATION_PREPEND = 0;
            public static final int OPERATION_REPLACE = 2;
            private final FontFamily mFamily;
            private final Locale mLocale;
            private final int mOperation;
            private final String mScript;

            @Retention(RetentionPolicy.SOURCE)
            public @interface Operation {}

            public LocaleFallback(Locale locale, int operation, FontFamily family) {
                this.mLocale = locale;
                this.mOperation = operation;
                this.mFamily = family;
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
                return "LocaleFallback{mLocale="
                        + this.mLocale
                        + ", mOperation="
                        + this.mOperation
                        + ", mFamily="
                        + this.mFamily
                        + '}';
            }
        }
    }

    public static String resolveScript(Locale locale) {
        String script = locale.getScript();
        if (script != null && !script.isEmpty()) {
            return script;
        }
        return ULocale.addLikelySubtags(ULocale.forLocale(locale)).getScript();
    }
}
