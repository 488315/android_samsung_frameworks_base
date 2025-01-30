package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.telephony.Rlog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class ImsExternalCallState implements Parcelable {
  public static final int CALL_STATE_CONFIRMED = 1;
  public static final int CALL_STATE_TERMINATED = 2;
  public static final Parcelable.Creator<ImsExternalCallState> CREATOR =
      new Parcelable.Creator<
          ImsExternalCallState>() { // from class: android.telephony.ims.ImsExternalCallState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsExternalCallState createFromParcel(Parcel in) {
          return new ImsExternalCallState(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsExternalCallState[] newArray(int size) {
          return new ImsExternalCallState[size];
        }
      };
  private static final String TAG = "ImsExternalCallState";
  private Uri mAddress;
  private int mCallId;
  private int mCallState;
  private int mCallType;
  private boolean mIsHeld;
  private boolean mIsPullable;
  private Uri mLocalAddress;

  @Retention(RetentionPolicy.SOURCE)
  public @interface ExternalCallState {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface ExternalCallType {}

  public ImsExternalCallState() {}

  public ImsExternalCallState(
      int callId,
      Uri address,
      boolean isPullable,
      int callState,
      int callType,
      boolean isCallheld) {
    this.mCallId = callId;
    this.mAddress = address;
    this.mIsPullable = isPullable;
    this.mCallState = callState;
    this.mCallType = callType;
    this.mIsHeld = isCallheld;
    Rlog.m238d(TAG, "ImsExternalCallState = " + this);
  }

  public ImsExternalCallState(
      int callId,
      Uri address,
      Uri localAddress,
      boolean isPullable,
      int callState,
      int callType,
      boolean isCallheld) {
    this.mCallId = callId;
    this.mAddress = address;
    this.mLocalAddress = localAddress;
    this.mIsPullable = isPullable;
    this.mCallState = callState;
    this.mCallType = callType;
    this.mIsHeld = isCallheld;
    Rlog.m238d(TAG, "ImsExternalCallState = " + this);
  }

  public ImsExternalCallState(
      String callId,
      Uri address,
      Uri localAddress,
      boolean isPullable,
      int callState,
      int callType,
      boolean isCallheld) {
    this.mCallId = getIdForString(callId);
    this.mAddress = address;
    this.mLocalAddress = localAddress;
    this.mIsPullable = isPullable;
    this.mCallState = callState;
    this.mCallType = callType;
    this.mIsHeld = isCallheld;
    Rlog.m238d(TAG, "ImsExternalCallState = " + this);
  }

  public ImsExternalCallState(Parcel in) {
    this.mCallId = in.readInt();
    ClassLoader classLoader = ImsExternalCallState.class.getClassLoader();
    this.mAddress = (Uri) in.readParcelable(classLoader, Uri.class);
    this.mLocalAddress = (Uri) in.readParcelable(classLoader, Uri.class);
    this.mIsPullable = in.readInt() != 0;
    this.mCallState = in.readInt();
    this.mCallType = in.readInt();
    this.mIsHeld = in.readInt() != 0;
    Rlog.m238d(TAG, "ImsExternalCallState const = " + this);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(this.mCallId);
    parcel.writeParcelable(this.mAddress, 0);
    parcel.writeParcelable(this.mLocalAddress, 0);
    parcel.writeInt(this.mIsPullable ? 1 : 0);
    parcel.writeInt(this.mCallState);
    parcel.writeInt(this.mCallType);
    parcel.writeInt(this.mIsHeld ? 1 : 0);
    Rlog.m238d(TAG, "ImsExternalCallState writeToParcel = " + parcel.toString());
  }

  public int getCallId() {
    return this.mCallId;
  }

  public Uri getAddress() {
    return this.mAddress;
  }

  public Uri getLocalAddress() {
    return this.mLocalAddress;
  }

  public boolean isCallPullable() {
    return this.mIsPullable;
  }

  public int getCallState() {
    return this.mCallState;
  }

  public int getCallType() {
    return this.mCallType;
  }

  public boolean isCallHeld() {
    return this.mIsHeld;
  }

  public String toString() {
    return "ImsExternalCallState { mCallId = "
        + this.mCallId
        + ", mAddress = "
        + Rlog.pii(TAG, this.mAddress)
        + ", mLocalAddress = "
        + Rlog.pii(TAG, this.mLocalAddress)
        + ", mIsPullable = "
        + this.mIsPullable
        + ", mCallState = "
        + this.mCallState
        + ", mCallType = "
        + this.mCallType
        + ", mIsHeld = "
        + this.mIsHeld
        + "}";
  }

  private int getIdForString(String idString) {
    try {
      return Integer.parseInt(idString);
    } catch (NumberFormatException e) {
      return idString.hashCode();
    }
  }
}
