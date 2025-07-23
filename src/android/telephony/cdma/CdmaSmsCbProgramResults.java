package android.telephony.cdma;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class CdmaSmsCbProgramResults implements Parcelable {
    public static final Parcelable.Creator<CdmaSmsCbProgramResults> CREATOR = new Parcelable.Creator<CdmaSmsCbProgramResults>() { // from class: android.telephony.cdma.CdmaSmsCbProgramResults.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsCbProgramResults createFromParcel(Parcel parcel) {
            return new CdmaSmsCbProgramResults(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaSmsCbProgramResults[] newArray(int i) {
            return new CdmaSmsCbProgramResults[i];
        }
    };
    public static final int RESULT_CATEGORY_ALREADY_ADDED = 3;
    public static final int RESULT_CATEGORY_ALREADY_DELETED = 4;
    public static final int RESULT_CATEGORY_LIMIT_EXCEEDED = 2;
    public static final int RESULT_INVALID_ALERT_OPTION = 6;
    public static final int RESULT_INVALID_CATEGORY_NAME = 7;
    public static final int RESULT_INVALID_MAX_MESSAGES = 5;
    public static final int RESULT_MEMORY_LIMIT_EXCEEDED = 1;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNSPECIFIED_FAILURE = 8;
    private final int mCategory;
    private final int mCategoryResult;
    private final int mLanguage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CdmaSmsCbProgramResults(int i, int i2, int i3) {
        this.mCategory = i;
        this.mLanguage = i2;
        this.mCategoryResult = i3;
    }

    CdmaSmsCbProgramResults(Parcel parcel) {
        this.mCategory = parcel.readInt();
        this.mLanguage = parcel.readInt();
        this.mCategoryResult = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mLanguage);
        parcel.writeInt(this.mCategoryResult);
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getLanguage() {
        return this.mLanguage;
    }

    public int getCategoryResult() {
        return this.mCategoryResult;
    }

    public String toString() {
        return "CdmaSmsCbProgramResults{category=" + this.mCategory + ", language=" + this.mLanguage + ", result=" + this.mCategoryResult + '}';
    }
}
