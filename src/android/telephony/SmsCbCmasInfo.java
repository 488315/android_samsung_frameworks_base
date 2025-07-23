package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class SmsCbCmasInfo implements Parcelable {
    public static final int CMAS_CATEGORY_CBRNE = 10;
    public static final int CMAS_CATEGORY_ENV = 7;
    public static final int CMAS_CATEGORY_FIRE = 5;
    public static final int CMAS_CATEGORY_GEO = 0;
    public static final int CMAS_CATEGORY_HEALTH = 6;
    public static final int CMAS_CATEGORY_INFRA = 9;
    public static final int CMAS_CATEGORY_MET = 1;
    public static final int CMAS_CATEGORY_OTHER = 11;
    public static final int CMAS_CATEGORY_RESCUE = 4;
    public static final int CMAS_CATEGORY_SAFETY = 2;
    public static final int CMAS_CATEGORY_SECURITY = 3;
    public static final int CMAS_CATEGORY_TRANSPORT = 8;
    public static final int CMAS_CATEGORY_UNKNOWN = -1;
    public static final int CMAS_CERTAINTY_LIKELY = 1;
    public static final int CMAS_CERTAINTY_OBSERVED = 0;
    public static final int CMAS_CERTAINTY_UNKNOWN = -1;
    public static final int CMAS_CLASS_CHILD_ABDUCTION_EMERGENCY = 3;
    public static final int CMAS_CLASS_CMAS_EXERCISE = 5;
    public static final int CMAS_CLASS_EXTREME_THREAT = 1;
    public static final int CMAS_CLASS_OPERATOR_DEFINED_USE = 6;
    public static final int CMAS_CLASS_PRESIDENTIAL_LEVEL_ALERT = 0;
    public static final int CMAS_CLASS_REQUIRED_MONTHLY_TEST = 4;
    public static final int CMAS_CLASS_SEVERE_THREAT = 2;
    public static final int CMAS_CLASS_UNKNOWN = -1;
    public static final int CMAS_RESPONSE_TYPE_ASSESS = 6;
    public static final int CMAS_RESPONSE_TYPE_AVOID = 5;
    public static final int CMAS_RESPONSE_TYPE_EVACUATE = 1;
    public static final int CMAS_RESPONSE_TYPE_EXECUTE = 3;
    public static final int CMAS_RESPONSE_TYPE_MONITOR = 4;
    public static final int CMAS_RESPONSE_TYPE_NONE = 7;
    public static final int CMAS_RESPONSE_TYPE_PREPARE = 2;
    public static final int CMAS_RESPONSE_TYPE_SHELTER = 0;
    public static final int CMAS_RESPONSE_TYPE_UNKNOWN = -1;
    public static final int CMAS_SEVERITY_EXTREME = 0;
    public static final int CMAS_SEVERITY_SEVERE = 1;
    public static final int CMAS_SEVERITY_UNKNOWN = -1;
    public static final int CMAS_URGENCY_EXPECTED = 1;
    public static final int CMAS_URGENCY_IMMEDIATE = 0;
    public static final int CMAS_URGENCY_UNKNOWN = -1;
    public static final Parcelable.Creator<SmsCbCmasInfo> CREATOR = new Parcelable.Creator<SmsCbCmasInfo>() { // from class: android.telephony.SmsCbCmasInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbCmasInfo createFromParcel(Parcel parcel) {
            return new SmsCbCmasInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbCmasInfo[] newArray(int i) {
            return new SmsCbCmasInfo[i];
        }
    };
    private int mAlertHandling;
    private final int mCategory;
    private final int mCertainty;
    private int mLanguage;
    private final int mMessageClass;
    private int mMessageID;
    private long mMsgExpires;
    private int mRecordType;
    private final int mResponseType;
    private final int mSeverity;
    private final int mUrgency;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Certainty {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Class {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Severity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Urgency {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmsCbCmasInfo(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMessageClass = i;
        this.mCategory = i2;
        this.mResponseType = i3;
        this.mSeverity = i4;
        this.mUrgency = i5;
        this.mCertainty = i6;
        this.mRecordType = 0;
        this.mMessageID = 0;
        this.mAlertHandling = 0;
        this.mMsgExpires = 0L;
        this.mLanguage = 0;
    }

    SmsCbCmasInfo(Parcel parcel) {
        this.mMessageClass = parcel.readInt();
        this.mCategory = parcel.readInt();
        this.mResponseType = parcel.readInt();
        this.mSeverity = parcel.readInt();
        this.mUrgency = parcel.readInt();
        this.mCertainty = parcel.readInt();
        this.mMessageID = parcel.readInt();
        this.mLanguage = parcel.readInt();
        this.mAlertHandling = parcel.readInt();
        this.mMsgExpires = parcel.readLong();
        this.mRecordType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMessageClass);
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mResponseType);
        parcel.writeInt(this.mSeverity);
        parcel.writeInt(this.mUrgency);
        parcel.writeInt(this.mCertainty);
        parcel.writeInt(this.mMessageID);
        parcel.writeInt(this.mLanguage);
        parcel.writeInt(this.mAlertHandling);
        parcel.writeLong(this.mMsgExpires);
        parcel.writeInt(this.mRecordType);
    }

    public int getMessageClass() {
        return this.mMessageClass;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getResponseType() {
        return this.mResponseType;
    }

    public int getSeverity() {
        return this.mSeverity;
    }

    public int getUrgency() {
        return this.mUrgency;
    }

    public int getCertainty() {
        return this.mCertainty;
    }

    public String toString() {
        return "SmsCbCmasInfo{messageClass=" + this.mMessageClass + ", category=" + this.mCategory + ", responseType=" + this.mResponseType + ", severity=" + this.mSeverity + ", urgency=" + this.mUrgency + ", certainty=" + this.mCertainty + ", recordType=" + this.mRecordType + ", messageID=" + this.mMessageID + ", alertHandling=" + this.mAlertHandling + ", language=" + this.mLanguage + ", mMsgExpires=" + this.mMsgExpires + '}';
    }

    public SmsCbCmasInfo(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.mMessageClass = i;
        this.mCategory = i2;
        this.mResponseType = i3;
        this.mSeverity = i4;
        this.mUrgency = i5;
        this.mCertainty = i6;
        this.mRecordType = i7;
    }

    public SmsCbCmasInfo(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, int i9, int i10) {
        this.mMessageClass = i;
        this.mCategory = i2;
        this.mResponseType = i3;
        this.mSeverity = i4;
        this.mUrgency = i5;
        this.mCertainty = i6;
        this.mMessageID = i7;
        this.mAlertHandling = i8;
        this.mMsgExpires = j;
        this.mLanguage = i9;
        this.mRecordType = i10;
    }

    public int getMessageID() {
        return this.mMessageID;
    }

    public int getAlertHandling() {
        return this.mAlertHandling;
    }

    public long getMsgExpires() {
        try {
            return this.mMsgExpires;
        } catch (NullPointerException unused) {
            return 0L;
        }
    }

    public int getLanguage() {
        return this.mLanguage;
    }

    public boolean getCMASRecordTypeFirstExists() {
        return (this.mRecordType & 2) == 2;
    }

    public boolean getCMASRecordTypeSecondExists() {
        return (this.mRecordType & 4) == 4;
    }
}
