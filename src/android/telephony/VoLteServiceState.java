package android.telephony;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes4.dex */
public final class VoLteServiceState implements Parcelable {
    public static final Parcelable.Creator<VoLteServiceState> CREATOR = new Parcelable.Creator() { // from class: android.telephony.VoLteServiceState.1
        @Override // android.os.Parcelable.Creator
        public VoLteServiceState createFromParcel(Parcel parcel) {
            return new VoLteServiceState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VoLteServiceState[] newArray(int i) {
            return new VoLteServiceState[i];
        }
    };
    private static final boolean DBG = false;
    public static final int HANDOVER_CANCELED = 3;
    public static final int HANDOVER_COMPLETED = 1;
    public static final int HANDOVER_FAILED = 2;
    public static final int HANDOVER_STARTED = 0;
    public static final int INVALID = Integer.MAX_VALUE;
    private static final String LOG_TAG = "VoLteServiceState";
    public static final int NOT_SUPPORTED = 0;
    public static final int SUPPORTED = 1;
    private int mSrvccState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void validateInput() {
    }

    public static VoLteServiceState newFromBundle(Bundle bundle) {
        VoLteServiceState voLteServiceState = new VoLteServiceState();
        voLteServiceState.setFromNotifierBundle(bundle);
        return voLteServiceState;
    }

    public VoLteServiceState() {
        initialize();
    }

    public VoLteServiceState(int i) {
        initialize();
        this.mSrvccState = i;
    }

    public VoLteServiceState(VoLteServiceState voLteServiceState) {
        copyFrom(voLteServiceState);
    }

    private void initialize() {
        this.mSrvccState = Integer.MAX_VALUE;
    }

    protected void copyFrom(VoLteServiceState voLteServiceState) {
        this.mSrvccState = voLteServiceState.mSrvccState;
    }

    public VoLteServiceState(Parcel parcel) {
        this.mSrvccState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSrvccState);
    }

    public int hashCode() {
        return this.mSrvccState * 31;
    }

    public boolean equals(Object obj) {
        return obj != null && this.mSrvccState == ((VoLteServiceState) obj).mSrvccState;
    }

    public String toString() {
        return "VoLteServiceState: " + this.mSrvccState;
    }

    private void setFromNotifierBundle(Bundle bundle) {
        this.mSrvccState = bundle.getInt("mSrvccState");
    }

    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putInt("mSrvccState", this.mSrvccState);
    }

    public int getSrvccState() {
        return this.mSrvccState;
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
