package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.telephony.Rlog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsExternalCallState implements Parcelable {
    public static final int CALL_STATE_CONFIRMED = 1;
    public static final int CALL_STATE_TERMINATED = 2;
    public static final Parcelable.Creator<ImsExternalCallState> CREATOR = new Parcelable.Creator<ImsExternalCallState>() { // from class: android.telephony.ims.ImsExternalCallState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsExternalCallState createFromParcel(Parcel parcel) {
            return new ImsExternalCallState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsExternalCallState[] newArray(int i) {
            return new ImsExternalCallState[i];
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
    public @interface ExternalCallState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExternalCallType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsExternalCallState() {
    }

    public ImsExternalCallState(int i, Uri uri, boolean z, int i2, int i3, boolean z2) {
        this.mCallId = i;
        this.mAddress = uri;
        this.mIsPullable = z;
        this.mCallState = i2;
        this.mCallType = i3;
        this.mIsHeld = z2;
        Rlog.d(TAG, "ImsExternalCallState = " + this);
    }

    public ImsExternalCallState(int i, Uri uri, Uri uri2, boolean z, int i2, int i3, boolean z2) {
        this.mCallId = i;
        this.mAddress = uri;
        this.mLocalAddress = uri2;
        this.mIsPullable = z;
        this.mCallState = i2;
        this.mCallType = i3;
        this.mIsHeld = z2;
        Rlog.d(TAG, "ImsExternalCallState = " + this);
    }

    public ImsExternalCallState(String str, Uri uri, Uri uri2, boolean z, int i, int i2, boolean z2) {
        this.mCallId = getIdForString(str);
        this.mAddress = uri;
        this.mLocalAddress = uri2;
        this.mIsPullable = z;
        this.mCallState = i;
        this.mCallType = i2;
        this.mIsHeld = z2;
        Rlog.d(TAG, "ImsExternalCallState = " + this);
    }

    public ImsExternalCallState(Parcel parcel) {
        this.mCallId = parcel.readInt();
        ClassLoader classLoader = ImsExternalCallState.class.getClassLoader();
        this.mAddress = (Uri) parcel.readParcelable(classLoader, Uri.class);
        this.mLocalAddress = (Uri) parcel.readParcelable(classLoader, Uri.class);
        this.mIsPullable = parcel.readInt() != 0;
        this.mCallState = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mIsHeld = parcel.readInt() != 0;
        Rlog.d(TAG, "ImsExternalCallState const = " + this);
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
        Rlog.d(TAG, "ImsExternalCallState writeToParcel = " + parcel.toString());
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
        return "ImsExternalCallState { mCallId = " + this.mCallId + ", mAddress = " + Rlog.pii(TAG, this.mAddress) + ", mLocalAddress = " + Rlog.pii(TAG, this.mLocalAddress) + ", mIsPullable = " + this.mIsPullable + ", mCallState = " + this.mCallState + ", mCallType = " + this.mCallType + ", mIsHeld = " + this.mIsHeld + "}";
    }

    private int getIdForString(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return str.hashCode();
        }
    }
}
