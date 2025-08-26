package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class RestrictionEntry implements Parcelable {
    public static final Parcelable.Creator<RestrictionEntry> CREATOR = new Parcelable.Creator<RestrictionEntry>() { // from class: android.content.RestrictionEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RestrictionEntry createFromParcel(Parcel parcel) {
            return new RestrictionEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RestrictionEntry[] newArray(int i) {
            return new RestrictionEntry[i];
        }
    };
    public static final int TYPE_BOOLEAN = 1;
    public static final int TYPE_BUNDLE = 7;
    public static final int TYPE_BUNDLE_ARRAY = 8;
    public static final int TYPE_CHOICE = 2;
    public static final int TYPE_CHOICE_LEVEL = 3;
    public static final int TYPE_INTEGER = 5;
    public static final int TYPE_MULTI_SELECT = 4;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_STRING = 6;
    private String[] mChoiceEntries;
    private String[] mChoiceValues;
    private String mCurrentValue;
    private String[] mCurrentValues;
    private String mDescription;
    private String mKey;
    private RestrictionEntry[] mRestrictions;
    private String mTitle;
    private int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RestrictionEntry(int i, String str) {
        this.mType = i;
        this.mKey = str;
    }

    public RestrictionEntry(String str, String str2) {
        this.mKey = str;
        this.mType = 2;
        this.mCurrentValue = str2;
    }

    public RestrictionEntry(String str, boolean z) {
        this.mKey = str;
        this.mType = 1;
        setSelectedState(z);
    }

    public RestrictionEntry(String str, String[] strArr) {
        this.mKey = str;
        this.mType = 4;
        this.mCurrentValues = strArr;
    }

    public RestrictionEntry(String str, int i) {
        this.mKey = str;
        this.mType = 5;
        setIntValue(i);
    }

    private RestrictionEntry(String str, RestrictionEntry[] restrictionEntryArr, boolean z) {
        this.mKey = str;
        if (z) {
            this.mType = 8;
            if (restrictionEntryArr != null) {
                for (RestrictionEntry restrictionEntry : restrictionEntryArr) {
                    if (restrictionEntry.getType() != 7) {
                        throw new IllegalArgumentException("bundle_array restriction can only have nested restriction entries of type bundle");
                    }
                }
            }
        } else {
            this.mType = 7;
        }
        setRestrictions(restrictionEntryArr);
    }

    public static RestrictionEntry createBundleEntry(String str, RestrictionEntry[] restrictionEntryArr) {
        return new RestrictionEntry(str, restrictionEntryArr, false);
    }

    public static RestrictionEntry createBundleArrayEntry(String str, RestrictionEntry[] restrictionEntryArr) {
        return new RestrictionEntry(str, restrictionEntryArr, true);
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }

    public String getSelectedString() {
        return this.mCurrentValue;
    }

    public String[] getAllSelectedStrings() {
        return this.mCurrentValues;
    }

    public boolean getSelectedState() {
        return Boolean.parseBoolean(this.mCurrentValue);
    }

    public int getIntValue() {
        return Integer.parseInt(this.mCurrentValue);
    }

    public void setIntValue(int i) {
        this.mCurrentValue = Integer.toString(i);
    }

    public void setSelectedString(String str) {
        this.mCurrentValue = str;
    }

    public void setSelectedState(boolean z) {
        this.mCurrentValue = Boolean.toString(z);
    }

    public void setAllSelectedStrings(String[] strArr) {
        this.mCurrentValues = strArr;
    }

    public void setChoiceValues(String[] strArr) {
        this.mChoiceValues = strArr;
    }

    public void setChoiceValues(Context context, int i) {
        this.mChoiceValues = context.getResources().getStringArray(i);
    }

    public RestrictionEntry[] getRestrictions() {
        return this.mRestrictions;
    }

    public void setRestrictions(RestrictionEntry[] restrictionEntryArr) {
        this.mRestrictions = restrictionEntryArr;
    }

    public String[] getChoiceValues() {
        return this.mChoiceValues;
    }

    public void setChoiceEntries(String[] strArr) {
        this.mChoiceEntries = strArr;
    }

    public void setChoiceEntries(Context context, int i) {
        this.mChoiceEntries = context.getResources().getStringArray(i);
    }

    public String[] getChoiceEntries() {
        return this.mChoiceEntries;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String str) {
        this.mDescription = str;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public boolean equals(Object obj) {
        String str;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RestrictionEntry)) {
            return false;
        }
        RestrictionEntry restrictionEntry = (RestrictionEntry) obj;
        if (this.mType == restrictionEntry.mType && this.mKey.equals(restrictionEntry.mKey)) {
            if (this.mCurrentValues == null && restrictionEntry.mCurrentValues == null && this.mRestrictions == null && restrictionEntry.mRestrictions == null && Objects.equals(this.mCurrentValue, restrictionEntry.mCurrentValue)) {
                return true;
            }
            if (this.mCurrentValue == null && restrictionEntry.mCurrentValue == null && this.mRestrictions == null && restrictionEntry.mRestrictions == null && Arrays.equals(this.mCurrentValues, restrictionEntry.mCurrentValues)) {
                return true;
            }
            String str2 = this.mCurrentValue;
            if (str2 == null && (str = restrictionEntry.mCurrentValue) == null && str2 == null && str == null && Arrays.equals(this.mRestrictions, restrictionEntry.mRestrictions)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i;
        int iHashCode;
        int iHashCode2 = 527 + this.mKey.hashCode();
        String str = this.mCurrentValue;
        if (str != null) {
            i = iHashCode2 * 31;
            iHashCode = str.hashCode();
        } else {
            String[] strArr = this.mCurrentValues;
            if (strArr != null) {
                for (String str2 : strArr) {
                    if (str2 != null) {
                        iHashCode2 = (iHashCode2 * 31) + str2.hashCode();
                    }
                }
                return iHashCode2;
            }
            RestrictionEntry[] restrictionEntryArr = this.mRestrictions;
            if (restrictionEntryArr == null) {
                return iHashCode2;
            }
            i = iHashCode2 * 31;
            iHashCode = Arrays.hashCode(restrictionEntryArr);
        }
        return i + iHashCode;
    }

    public RestrictionEntry(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mKey = parcel.readString();
        this.mTitle = parcel.readString();
        this.mDescription = parcel.readString();
        this.mChoiceEntries = parcel.readStringArray();
        this.mChoiceValues = parcel.readStringArray();
        this.mCurrentValue = parcel.readString();
        this.mCurrentValues = parcel.readStringArray();
        Parcelable[] parcelableArr = (Parcelable[]) parcel.readParcelableArray(null, RestrictionEntry.class);
        if (parcelableArr != null) {
            this.mRestrictions = new RestrictionEntry[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mRestrictions[i] = (RestrictionEntry) parcelableArr[i];
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeString(this.mKey);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mDescription);
        parcel.writeStringArray(this.mChoiceEntries);
        parcel.writeStringArray(this.mChoiceValues);
        parcel.writeString(this.mCurrentValue);
        parcel.writeStringArray(this.mCurrentValues);
        parcel.writeParcelableArray(this.mRestrictions, 0);
    }

    public String toString() {
        return "RestrictionEntry{mType=" + this.mType + ", mKey='" + this.mKey + "', mTitle='" + this.mTitle + "', mDescription='" + this.mDescription + "', mChoiceEntries=" + Arrays.toString(this.mChoiceEntries) + ", mChoiceValues=" + Arrays.toString(this.mChoiceValues) + ", mCurrentValue='" + this.mCurrentValue + "', mCurrentValues=" + Arrays.toString(this.mCurrentValues) + ", mRestrictions=" + Arrays.toString(this.mRestrictions) + '}';
    }
}
