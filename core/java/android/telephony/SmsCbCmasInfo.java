package android.telephony;

import android.annotation.SystemApi;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
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
  public static final Parcelable.Creator<SmsCbCmasInfo> CREATOR =
      new Parcelable.Creator<SmsCbCmasInfo>() { // from class: android.telephony.SmsCbCmasInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbCmasInfo createFromParcel(Parcel in) {
          return new SmsCbCmasInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbCmasInfo[] newArray(int size) {
          return new SmsCbCmasInfo[size];
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
  public @interface Category {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface Certainty {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface Class {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface ResponseType {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface Severity {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface Urgency {}

  public SmsCbCmasInfo(
      int messageClass, int category, int responseType, int severity, int urgency, int certainty) {
    this.mMessageClass = messageClass;
    this.mCategory = category;
    this.mResponseType = responseType;
    this.mSeverity = severity;
    this.mUrgency = urgency;
    this.mCertainty = certainty;
    this.mRecordType = 0;
    this.mMessageID = 0;
    this.mAlertHandling = 0;
    this.mMsgExpires = 0L;
    this.mLanguage = 0;
  }

  SmsCbCmasInfo(Parcel in) {
    this.mMessageClass = in.readInt();
    this.mCategory = in.readInt();
    this.mResponseType = in.readInt();
    this.mSeverity = in.readInt();
    this.mUrgency = in.readInt();
    this.mCertainty = in.readInt();
    this.mMessageID = in.readInt();
    this.mLanguage = in.readInt();
    this.mAlertHandling = in.readInt();
    this.mMsgExpires = in.readLong();
    this.mRecordType = in.readInt();
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mMessageClass);
    dest.writeInt(this.mCategory);
    dest.writeInt(this.mResponseType);
    dest.writeInt(this.mSeverity);
    dest.writeInt(this.mUrgency);
    dest.writeInt(this.mCertainty);
    dest.writeInt(this.mMessageID);
    dest.writeInt(this.mLanguage);
    dest.writeInt(this.mAlertHandling);
    dest.writeLong(this.mMsgExpires);
    dest.writeInt(this.mRecordType);
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
    return "SmsCbCmasInfo{messageClass="
        + this.mMessageClass
        + ", category="
        + this.mCategory
        + ", responseType="
        + this.mResponseType
        + ", severity="
        + this.mSeverity
        + ", urgency="
        + this.mUrgency
        + ", certainty="
        + this.mCertainty
        + ", recordType="
        + this.mRecordType
        + ", messageID="
        + this.mMessageID
        + ", alertHandling="
        + this.mAlertHandling
        + ", language="
        + this.mLanguage
        + ", mMsgExpires="
        + this.mMsgExpires
        + '}';
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  public SmsCbCmasInfo(
      int messageClass,
      int category,
      int responseType,
      int severity,
      int urgency,
      int certainty,
      int recordTypeAll) {
    this.mMessageClass = messageClass;
    this.mCategory = category;
    this.mResponseType = responseType;
    this.mSeverity = severity;
    this.mUrgency = urgency;
    this.mCertainty = certainty;
    this.mRecordType = recordTypeAll;
  }

  public SmsCbCmasInfo(
      int messageClass,
      int category,
      int responseType,
      int severity,
      int urgency,
      int certainty,
      int messageID,
      int alertHandling,
      long msgExpires,
      int language,
      int recordTypeAll) {
    this.mMessageClass = messageClass;
    this.mCategory = category;
    this.mResponseType = responseType;
    this.mSeverity = severity;
    this.mUrgency = urgency;
    this.mCertainty = certainty;
    this.mMessageID = messageID;
    this.mAlertHandling = alertHandling;
    this.mMsgExpires = msgExpires;
    this.mLanguage = language;
    this.mRecordType = recordTypeAll;
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
    } catch (NullPointerException e) {
      return 0L;
    }
  }

  public int getLanguage() {
    return this.mLanguage;
  }

  public boolean getCMASRecordTypeFirstExists() {
    try {
      return (this.mRecordType & 2) == 2;
    } catch (NullPointerException e) {
      return false;
    }
  }

  public boolean getCMASRecordTypeSecondExists() {
    try {
      return (this.mRecordType & 4) == 4;
    } catch (NullPointerException e) {
      return false;
    }
  }
}
