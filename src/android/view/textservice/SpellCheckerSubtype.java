package android.view.textservice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.inputmethod.SubtypeLocaleUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class SpellCheckerSubtype implements Parcelable {
    public static final Parcelable.Creator<SpellCheckerSubtype> CREATOR = new Parcelable.Creator<SpellCheckerSubtype>() { // from class: android.view.textservice.SpellCheckerSubtype.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpellCheckerSubtype createFromParcel(Parcel parcel) {
            return new SpellCheckerSubtype(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpellCheckerSubtype[] newArray(int i) {
            return new SpellCheckerSubtype[i];
        }
    };
    private static final String EXTRA_VALUE_KEY_VALUE_SEPARATOR = "=";
    private static final String EXTRA_VALUE_PAIR_SEPARATOR = ",";
    public static final int SUBTYPE_ID_NONE = 0;
    private static final String SUBTYPE_LANGUAGE_TAG_NONE = "";
    private static final String TAG = "SpellCheckerSubtype";
    private HashMap<String, String> mExtraValueHashMapCache;
    private final String mSubtypeExtraValue;
    private final int mSubtypeHashCode;
    private final int mSubtypeId;
    private final String mSubtypeLanguageTag;
    private final String mSubtypeLocale;
    private final int mSubtypeNameResId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SpellCheckerSubtype(int i, String str, String str2, String str3, int i2) {
        this.mSubtypeNameResId = i;
        str = str == null ? "" : str;
        this.mSubtypeLocale = str;
        this.mSubtypeLanguageTag = str2 == null ? "" : str2;
        str3 = str3 == null ? "" : str3;
        this.mSubtypeExtraValue = str3;
        this.mSubtypeId = i2;
        this.mSubtypeHashCode = i2 == 0 ? hashCodeInternal(str, str3) : i2;
    }

    @Deprecated
    public SpellCheckerSubtype(int i, String str, String str2) {
        this(i, str, "", str2, 0);
    }

    SpellCheckerSubtype(Parcel parcel) {
        this.mSubtypeNameResId = parcel.readInt();
        String readString = parcel.readString();
        readString = readString == null ? "" : readString;
        this.mSubtypeLocale = readString;
        String readString2 = parcel.readString();
        this.mSubtypeLanguageTag = readString2 == null ? "" : readString2;
        String readString3 = parcel.readString();
        String str = readString3 != null ? readString3 : "";
        this.mSubtypeExtraValue = str;
        int readInt = parcel.readInt();
        this.mSubtypeId = readInt;
        this.mSubtypeHashCode = readInt == 0 ? hashCodeInternal(readString, str) : readInt;
    }

    public int getNameResId() {
        return this.mSubtypeNameResId;
    }

    @Deprecated
    public String getLocale() {
        return this.mSubtypeLocale;
    }

    public String getLanguageTag() {
        return this.mSubtypeLanguageTag;
    }

    public String getExtraValue() {
        return this.mSubtypeExtraValue;
    }

    private HashMap<String, String> getExtraValueHashMap() {
        if (this.mExtraValueHashMapCache == null) {
            this.mExtraValueHashMapCache = new HashMap<>();
            for (String str : this.mSubtypeExtraValue.split(",")) {
                String[] split = str.split(EXTRA_VALUE_KEY_VALUE_SEPARATOR);
                if (split.length == 1) {
                    this.mExtraValueHashMapCache.put(split[0], null);
                } else if (split.length > 1) {
                    if (split.length > 2) {
                        Slog.w(TAG, "ExtraValue has two or more '='s");
                    }
                    this.mExtraValueHashMapCache.put(split[0], split[1]);
                }
            }
        }
        return this.mExtraValueHashMapCache;
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

    public boolean equals(Object obj) {
        if (obj instanceof SpellCheckerSubtype) {
            SpellCheckerSubtype spellCheckerSubtype = (SpellCheckerSubtype) obj;
            if (spellCheckerSubtype.mSubtypeId == 0 && this.mSubtypeId == 0) {
                return spellCheckerSubtype.hashCode() == hashCode() && spellCheckerSubtype.getNameResId() == getNameResId() && spellCheckerSubtype.getLocale().equals(getLocale()) && spellCheckerSubtype.getLanguageTag().equals(getLanguageTag()) && spellCheckerSubtype.getExtraValue().equals(getExtraValue());
            }
            if (spellCheckerSubtype.hashCode() == hashCode()) {
                return true;
            }
        }
        return false;
    }

    public Locale getLocaleObject() {
        if (!TextUtils.isEmpty(this.mSubtypeLanguageTag)) {
            return Locale.forLanguageTag(this.mSubtypeLanguageTag);
        }
        return SubtypeLocaleUtils.constructLocaleFromString(this.mSubtypeLocale);
    }

    public CharSequence getDisplayName(Context context, String str, ApplicationInfo applicationInfo) {
        Locale localeObject = getLocaleObject();
        String displayName = localeObject != null ? localeObject.getDisplayName() : this.mSubtypeLocale;
        if (this.mSubtypeNameResId != 0) {
            CharSequence text = context.getPackageManager().getText(str, this.mSubtypeNameResId, applicationInfo);
            if (!TextUtils.isEmpty(text)) {
                return String.format(text.toString(), displayName);
            }
        }
        return displayName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSubtypeNameResId);
        parcel.writeString(this.mSubtypeLocale);
        parcel.writeString(this.mSubtypeLanguageTag);
        parcel.writeString(this.mSubtypeExtraValue);
        parcel.writeInt(this.mSubtypeId);
    }

    private static int hashCodeInternal(String str, String str2) {
        return Arrays.hashCode(new Object[]{str, str2});
    }

    public static List<SpellCheckerSubtype> sort(Context context, int i, SpellCheckerInfo spellCheckerInfo, List<SpellCheckerSubtype> list) {
        if (spellCheckerInfo == null) {
            return list;
        }
        HashSet hashSet = new HashSet(list);
        ArrayList arrayList = new ArrayList();
        int subtypeCount = spellCheckerInfo.getSubtypeCount();
        for (int i2 = 0; i2 < subtypeCount; i2++) {
            SpellCheckerSubtype subtypeAt = spellCheckerInfo.getSubtypeAt(i2);
            if (hashSet.contains(subtypeAt)) {
                arrayList.add(subtypeAt);
                hashSet.remove(subtypeAt);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add((SpellCheckerSubtype) it.next());
        }
        return arrayList;
    }
}
