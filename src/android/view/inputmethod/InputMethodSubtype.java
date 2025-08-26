package android.view.inputmethod;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.icu.text.DisplayContext;
import android.icu.text.LocaleDisplayNames;
import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Printer;
import android.util.Slog;
import com.android.internal.inputmethod.SubtypeLocaleUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class InputMethodSubtype implements Parcelable {
    public static final Parcelable.Creator<InputMethodSubtype> CREATOR = new Parcelable.Creator<InputMethodSubtype>() { // from class: android.view.inputmethod.InputMethodSubtype.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodSubtype createFromParcel(Parcel parcel) {
            return new InputMethodSubtype(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMethodSubtype[] newArray(int i) {
            return new InputMethodSubtype[i];
        }
    };
    private static final String EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME = "UntranslatableReplacementStringInSubtypeName";
    private static final String EXTRA_VALUE_KEY_VALUE_SEPARATOR = "=";
    private static final String EXTRA_VALUE_PAIR_SEPARATOR = ",";
    private static final String LANGUAGE_TAG_NONE = "";
    public static final int SUBTYPE_ID_NONE = 0;
    private static final String SUBTYPE_MODE_KEYBOARD = "keyboard";
    private static final String TAG = "InputMethodSubtype";
    private static final String UNDEFINED_LANGUAGE_TAG = "und";
    private volatile String mCachedCanonicalizedLanguageTag;
    private volatile Locale mCachedLocaleObj;
    private volatile HashMap<String, String> mExtraValueHashMapCache;
    private final boolean mIsAsciiCapable;
    private final boolean mIsAuxiliary;
    private final CharSequence mLayoutLabelNonLocalized;
    private final int mLayoutLabelResId;
    private final Object mLock;
    private final boolean mOverridesImplicitlyEnabledSubtype;
    private final String mPkLanguageTag;
    private final String mPkLayoutType;
    private final String mSubtypeExtraValue;
    private final int mSubtypeHashCode;
    private final int mSubtypeIconResId;
    private final int mSubtypeId;
    private final String mSubtypeLanguageTag;
    private final String mSubtypeLocale;
    private final String mSubtypeMode;
    private final CharSequence mSubtypeNameOverride;
    private final int mSubtypeNameResId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class InputMethodSubtypeBuilder {
        private boolean mIsAuxiliary = false;
        private boolean mOverridesImplicitlyEnabledSubtype = false;
        private boolean mIsAsciiCapable = false;
        private int mSubtypeIconResId = 0;
        private int mSubtypeNameResId = 0;
        private CharSequence mSubtypeNameOverride = "";
        private int mLayoutLabelResId = 0;
        private CharSequence mLayoutLabelNonLocalized = "";
        private String mPkLanguageTag = "";
        private String mPkLayoutType = "";
        private int mSubtypeId = 0;
        private String mSubtypeLocale = "";
        private String mSubtypeLanguageTag = "";
        private String mSubtypeMode = "";
        private String mSubtypeExtraValue = "";

        public InputMethodSubtypeBuilder setIsAuxiliary(boolean z) {
            this.mIsAuxiliary = z;
            return this;
        }

        public InputMethodSubtypeBuilder setOverridesImplicitlyEnabledSubtype(boolean z) {
            this.mOverridesImplicitlyEnabledSubtype = z;
            return this;
        }

        public InputMethodSubtypeBuilder setIsAsciiCapable(boolean z) {
            this.mIsAsciiCapable = z;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeIconResId(int i) {
            this.mSubtypeIconResId = i;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeNameResId(int i) {
            this.mSubtypeNameResId = i;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeNameOverride(CharSequence charSequence) {
            this.mSubtypeNameOverride = charSequence;
            return this;
        }

        public InputMethodSubtypeBuilder setLayoutLabelResource(int i) {
            if (!Flags.imeSwitcherRevampApi()) {
                return this;
            }
            this.mLayoutLabelResId = i;
            return this;
        }

        public InputMethodSubtypeBuilder setLayoutLabelNonLocalized(CharSequence charSequence) {
            if (!Flags.imeSwitcherRevampApi()) {
                return this;
            }
            Objects.requireNonNull(charSequence, "layoutLabelNonLocalized cannot be null");
            this.mLayoutLabelNonLocalized = charSequence;
            return this;
        }

        public InputMethodSubtypeBuilder setPhysicalKeyboardHint(ULocale uLocale, String str) {
            Objects.requireNonNull(str, "layoutType cannot be null");
            this.mPkLanguageTag = uLocale == null ? "" : uLocale.toLanguageTag();
            this.mPkLayoutType = str;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeId(int i) {
            this.mSubtypeId = i;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeLocale(String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeLocale = str;
            return this;
        }

        public InputMethodSubtypeBuilder setLanguageTag(String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeLanguageTag = str;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeMode(String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeMode = str;
            return this;
        }

        public InputMethodSubtypeBuilder setSubtypeExtraValue(String str) {
            if (str == null) {
                str = "";
            }
            this.mSubtypeExtraValue = str;
            return this;
        }

        public InputMethodSubtype build() {
            return new InputMethodSubtype(this);
        }
    }

    private static InputMethodSubtypeBuilder getBuilder(int i, int i2, String str, String str2, String str3, boolean z, boolean z2, int i3, boolean z3) {
        InputMethodSubtypeBuilder inputMethodSubtypeBuilder = new InputMethodSubtypeBuilder();
        inputMethodSubtypeBuilder.mSubtypeNameResId = i;
        inputMethodSubtypeBuilder.mSubtypeIconResId = i2;
        inputMethodSubtypeBuilder.mSubtypeLocale = str;
        inputMethodSubtypeBuilder.mSubtypeMode = str2;
        inputMethodSubtypeBuilder.mSubtypeExtraValue = str3;
        inputMethodSubtypeBuilder.mIsAuxiliary = z;
        inputMethodSubtypeBuilder.mOverridesImplicitlyEnabledSubtype = z2;
        inputMethodSubtypeBuilder.mSubtypeId = i3;
        inputMethodSubtypeBuilder.mIsAsciiCapable = z3;
        return inputMethodSubtypeBuilder;
    }

    @Deprecated
    public InputMethodSubtype(int i, int i2, String str, String str2, String str3, boolean z, boolean z2) {
        this(i, i2, str, str2, str3, z, z2, 0);
    }

    @Deprecated
    public InputMethodSubtype(int i, int i2, String str, String str2, String str3, boolean z, boolean z2, int i3) {
        this(getBuilder(i, i2, str, str2, str3, z, z2, i3, false));
    }

    private InputMethodSubtype(InputMethodSubtypeBuilder inputMethodSubtypeBuilder) {
        this.mLock = new Object();
        this.mSubtypeNameResId = inputMethodSubtypeBuilder.mSubtypeNameResId;
        this.mSubtypeNameOverride = inputMethodSubtypeBuilder.mSubtypeNameOverride;
        this.mLayoutLabelResId = inputMethodSubtypeBuilder.mLayoutLabelResId;
        this.mLayoutLabelNonLocalized = inputMethodSubtypeBuilder.mLayoutLabelNonLocalized;
        this.mPkLanguageTag = inputMethodSubtypeBuilder.mPkLanguageTag;
        this.mPkLayoutType = inputMethodSubtypeBuilder.mPkLayoutType;
        this.mSubtypeIconResId = inputMethodSubtypeBuilder.mSubtypeIconResId;
        String str = inputMethodSubtypeBuilder.mSubtypeLocale;
        this.mSubtypeLocale = str;
        this.mSubtypeLanguageTag = inputMethodSubtypeBuilder.mSubtypeLanguageTag;
        String str2 = inputMethodSubtypeBuilder.mSubtypeMode;
        this.mSubtypeMode = str2;
        String str3 = inputMethodSubtypeBuilder.mSubtypeExtraValue;
        this.mSubtypeExtraValue = str3;
        boolean z = inputMethodSubtypeBuilder.mIsAuxiliary;
        this.mIsAuxiliary = z;
        boolean z2 = inputMethodSubtypeBuilder.mOverridesImplicitlyEnabledSubtype;
        this.mOverridesImplicitlyEnabledSubtype = z2;
        int i = inputMethodSubtypeBuilder.mSubtypeId;
        this.mSubtypeId = i;
        boolean z3 = inputMethodSubtypeBuilder.mIsAsciiCapable;
        this.mIsAsciiCapable = z3;
        if (i != 0) {
            this.mSubtypeHashCode = i;
        } else {
            this.mSubtypeHashCode = hashCodeInternal(str, str2, str3, z, z2, z3);
        }
    }

    InputMethodSubtype(Parcel parcel) {
        this.mLock = new Object();
        this.mSubtypeNameResId = parcel.readInt();
        String strCreateFromParcel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtypeNameOverride = strCreateFromParcel == null ? "" : strCreateFromParcel;
        this.mLayoutLabelResId = parcel.readInt();
        String strCreateFromParcel2 = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mLayoutLabelNonLocalized = strCreateFromParcel2 == null ? "" : strCreateFromParcel2;
        String string8 = parcel.readString8();
        this.mPkLanguageTag = string8 == null ? "" : string8;
        String string82 = parcel.readString8();
        this.mPkLayoutType = string82 == null ? "" : string82;
        this.mSubtypeIconResId = parcel.readInt();
        String string = parcel.readString();
        this.mSubtypeLocale = string == null ? "" : string;
        String string2 = parcel.readString();
        this.mSubtypeLanguageTag = string2 == null ? "" : string2;
        String string3 = parcel.readString();
        this.mSubtypeMode = string3 == null ? "" : string3;
        String string4 = parcel.readString();
        this.mSubtypeExtraValue = string4 != null ? string4 : "";
        this.mIsAuxiliary = parcel.readInt() == 1;
        this.mOverridesImplicitlyEnabledSubtype = parcel.readInt() == 1;
        this.mSubtypeHashCode = parcel.readInt();
        this.mSubtypeId = parcel.readInt();
        this.mIsAsciiCapable = parcel.readInt() == 1;
    }

    public int getNameResId() {
        return this.mSubtypeNameResId;
    }

    public CharSequence getNameOverride() {
        return this.mSubtypeNameOverride;
    }

    public int getLayoutLabelResource() {
        return this.mLayoutLabelResId;
    }

    public CharSequence getLayoutLabelNonLocalized() {
        return this.mLayoutLabelNonLocalized;
    }

    public ULocale getPhysicalKeyboardHintLanguageTag() {
        if (TextUtils.isEmpty(this.mPkLanguageTag)) {
            return null;
        }
        return ULocale.forLanguageTag(this.mPkLanguageTag);
    }

    public String getPhysicalKeyboardHintLayoutType() {
        return this.mPkLayoutType;
    }

    public int getIconResId() {
        return this.mSubtypeIconResId;
    }

    @Deprecated
    public String getLocale() {
        return this.mSubtypeLocale;
    }

    public String getLanguageTag() {
        return this.mSubtypeLanguageTag;
    }

    public Locale getLocaleObject() {
        if (this.mCachedLocaleObj != null) {
            return this.mCachedLocaleObj;
        }
        synchronized (this.mLock) {
            if (this.mCachedLocaleObj != null) {
                return this.mCachedLocaleObj;
            }
            if (!TextUtils.isEmpty(this.mSubtypeLanguageTag)) {
                this.mCachedLocaleObj = Locale.forLanguageTag(this.mSubtypeLanguageTag);
            } else {
                this.mCachedLocaleObj = SubtypeLocaleUtils.constructLocaleFromString(this.mSubtypeLocale);
            }
            return this.mCachedLocaleObj;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getCanonicalizedLanguageTag() {
        String languageTag;
        String str = this.mCachedCanonicalizedLanguageTag;
        if (str != null) {
            return str;
        }
        Locale localeObject = getLocaleObject();
        if (localeObject != null) {
            String languageTag2 = localeObject.toLanguageTag();
            languageTag = !TextUtils.isEmpty(languageTag2) ? ULocale.createCanonical(ULocale.forLanguageTag(languageTag2)).toLanguageTag() : null;
        }
        String strEmptyIfNull = TextUtils.emptyIfNull(languageTag);
        this.mCachedCanonicalizedLanguageTag = strEmptyIfNull;
        return strEmptyIfNull;
    }

    public boolean isSuitableForPhysicalKeyboardLayoutMapping() {
        if (hashCode() != 0 && TextUtils.equals(getMode(), "keyboard")) {
            return !isAuxiliary();
        }
        return false;
    }

    public String getMode() {
        return this.mSubtypeMode;
    }

    public String getExtraValue() {
        return this.mSubtypeExtraValue;
    }

    public boolean isAuxiliary() {
        return this.mIsAuxiliary;
    }

    public boolean overridesImplicitlyEnabledSubtype() {
        return this.mOverridesImplicitlyEnabledSubtype;
    }

    public boolean isAsciiCapable() {
        return this.mIsAsciiCapable;
    }

    public CharSequence getDisplayName(Context context, String str, ApplicationInfo applicationInfo) {
        DisplayContext displayContext;
        String localeDisplayName;
        if (this.mSubtypeNameResId == 0) {
            if (TextUtils.isEmpty(this.mSubtypeNameOverride)) {
                return getLocaleDisplayName(getLocaleFromContext(context), getLocaleObject(), DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU);
            }
            return this.mSubtypeNameOverride;
        }
        CharSequence text = context.getPackageManager().getText(str, this.mSubtypeNameResId, applicationInfo);
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        String string = text.toString();
        if (containsExtraValueKey(EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME)) {
            localeDisplayName = getExtraValueOf(EXTRA_KEY_UNTRANSLATABLE_STRING_IN_SUBTYPE_NAME);
        } else {
            if (TextUtils.equals(string, "%s")) {
                displayContext = DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU;
            } else if (string.startsWith("%s")) {
                displayContext = DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE;
            } else {
                displayContext = DisplayContext.CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE;
            }
            localeDisplayName = getLocaleDisplayName(getLocaleFromContext(context), getLocaleObject(), displayContext);
        }
        if (localeDisplayName == null) {
            localeDisplayName = "";
        }
        try {
            return String.format(string, localeDisplayName);
        } catch (IllegalFormatException e) {
            Slog.w(TAG, "Found illegal format in subtype name(" + ((Object) text) + "): " + e);
            return "";
        }
    }

    public CharSequence getLayoutDisplayName(Context context, ApplicationInfo applicationInfo) {
        if (!Flags.imeSwitcherRevampApi()) {
            return "";
        }
        Objects.requireNonNull(context, "context cannot be null");
        Objects.requireNonNull(applicationInfo, "imeAppInfo cannot be null");
        if (this.mLayoutLabelResId == 0) {
            return this.mLayoutLabelNonLocalized;
        }
        CharSequence text = context.getPackageManager().getText(applicationInfo.packageName, this.mLayoutLabelResId, applicationInfo);
        return TextUtils.isEmpty(text) ? "" : text;
    }

    private static Locale getLocaleFromContext(Context context) {
        Configuration configuration;
        if (context == null || context.getResources() == null || (configuration = context.getResources().getConfiguration()) == null) {
            return null;
        }
        return configuration.getLocales().get(0);
    }

    private static String getLocaleDisplayName(Locale locale, Locale locale2, DisplayContext displayContext) {
        if (locale2 == null) {
            return "";
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return LocaleDisplayNames.getInstance(locale, displayContext).localeDisplayName(locale2);
    }

    private HashMap<String, String> getExtraValueHashMap() {
        synchronized (this) {
            HashMap<String, String> map = this.mExtraValueHashMapCache;
            if (map != null) {
                return map;
            }
            HashMap<String, String> map2 = new HashMap<>();
            for (String str : this.mSubtypeExtraValue.split(",")) {
                String[] strArrSplit = str.split(EXTRA_VALUE_KEY_VALUE_SEPARATOR);
                if (strArrSplit.length == 1) {
                    map2.put(strArrSplit[0], null);
                } else if (strArrSplit.length > 1) {
                    if (strArrSplit.length > 2) {
                        Slog.w(TAG, "ExtraValue has two or more '='s");
                    }
                    map2.put(strArrSplit[0], strArrSplit[1]);
                }
            }
            this.mExtraValueHashMapCache = map2;
            return map2;
        }
    }

    public boolean containsExtraValueKey(String str) {
        return getExtraValueHashMap().containsKey(str);
    }

    public String getExtraValueOf(String str) {
        return getExtraValueHashMap().get(str);
    }

    public int hashCode() {
        return this.mSubtypeHashCode;
    }

    public final boolean hasSubtypeId() {
        return this.mSubtypeId != 0;
    }

    public final int getSubtypeId() {
        return this.mSubtypeId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof InputMethodSubtype) {
            InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) obj;
            if (inputMethodSubtype.mSubtypeId == 0 && this.mSubtypeId == 0) {
                return inputMethodSubtype.hashCode() == hashCode() && inputMethodSubtype.getLocale().equals(getLocale()) && inputMethodSubtype.getLanguageTag().equals(getLanguageTag()) && inputMethodSubtype.getMode().equals(getMode()) && inputMethodSubtype.getExtraValue().equals(getExtraValue()) && inputMethodSubtype.isAuxiliary() == isAuxiliary() && inputMethodSubtype.overridesImplicitlyEnabledSubtype() == overridesImplicitlyEnabledSubtype() && inputMethodSubtype.isAsciiCapable() == isAsciiCapable();
            }
            if (inputMethodSubtype.hashCode() == hashCode()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSubtypeNameResId);
        TextUtils.writeToParcel(this.mSubtypeNameOverride, parcel, i);
        parcel.writeInt(this.mLayoutLabelResId);
        TextUtils.writeToParcel(this.mLayoutLabelNonLocalized, parcel, i);
        parcel.writeString8(this.mPkLanguageTag);
        parcel.writeString8(this.mPkLayoutType);
        parcel.writeInt(this.mSubtypeIconResId);
        parcel.writeString(this.mSubtypeLocale);
        parcel.writeString(this.mSubtypeLanguageTag);
        parcel.writeString(this.mSubtypeMode);
        parcel.writeString(this.mSubtypeExtraValue);
        parcel.writeInt(this.mIsAuxiliary ? 1 : 0);
        parcel.writeInt(this.mOverridesImplicitlyEnabledSubtype ? 1 : 0);
        parcel.writeInt(this.mSubtypeHashCode);
        parcel.writeInt(this.mSubtypeId);
        parcel.writeInt(this.mIsAsciiCapable ? 1 : 0);
    }

    void dump(Printer printer, String str) {
        printer.println(str + "mSubtypeNameOverride=" + ((Object) this.mSubtypeNameOverride) + " mLayoutLabelNonLocalized=" + ((Object) this.mLayoutLabelNonLocalized) + " mPkLanguageTag=" + this.mPkLanguageTag + " mPkLayoutType=" + this.mPkLayoutType + " mSubtypeId=" + this.mSubtypeId + " mSubtypeLocale=" + this.mSubtypeLocale + " mSubtypeLanguageTag=" + this.mSubtypeLanguageTag + " mSubtypeMode=" + this.mSubtypeMode + " mIsAuxiliary=" + this.mIsAuxiliary + " mOverridesImplicitlyEnabledSubtype=" + this.mOverridesImplicitlyEnabledSubtype + " mIsAsciiCapable=" + this.mIsAsciiCapable + " mSubtypeHashCode=" + this.mSubtypeHashCode);
    }

    private static int hashCodeInternal(String str, String str2, String str3, boolean z, boolean z2, boolean z3) {
        if (!z3) {
            return Arrays.hashCode(new Object[]{str, str2, str3, Boolean.valueOf(z), Boolean.valueOf(z2)});
        }
        return Arrays.hashCode(new Object[]{str, str2, str3, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3)});
    }

    public static List<InputMethodSubtype> sort(InputMethodInfo inputMethodInfo, List<InputMethodSubtype> list) {
        if (inputMethodInfo == null) {
            return list;
        }
        HashSet hashSet = new HashSet(list);
        ArrayList arrayList = new ArrayList();
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            if (hashSet.contains(subtypeAt)) {
                arrayList.add(subtypeAt);
                hashSet.remove(subtypeAt);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add((InputMethodSubtype) it.next());
        }
        return arrayList;
    }
}
