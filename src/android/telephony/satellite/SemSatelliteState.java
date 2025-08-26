package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class SemSatelliteState implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteState> CREATOR = new Parcelable.Creator<SemSatelliteState>() { // from class: android.telephony.satellite.SemSatelliteState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteState createFromParcel(Parcel parcel) {
            return new SemSatelliteState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteState[] newArray(int i) {
            return new SemSatelliteState[i];
        }
    };
    static final boolean DBG = true;
    static final String LOG_TAG = "SatelliteState";
    private SemSatelliteServiceState mSatelliteServiceState;
    private SemSatelliteSignalStrength mSatelliteSignalStrength;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSatelliteState() {
        this.mSatelliteServiceState = new SemSatelliteServiceState();
        this.mSatelliteSignalStrength = new SemSatelliteSignalStrength();
    }

    public SemSatelliteState(SemSatelliteServiceState semSatelliteServiceState, SemSatelliteSignalStrength semSatelliteSignalStrength) {
        this.mSatelliteServiceState = new SemSatelliteServiceState(semSatelliteServiceState);
        this.mSatelliteSignalStrength = new SemSatelliteSignalStrength(semSatelliteSignalStrength);
    }

    public SemSatelliteState(SemSatelliteState semSatelliteState) {
        copyFrom(semSatelliteState);
    }

    private SemSatelliteState(Parcel parcel) {
        this.mSatelliteServiceState = (SemSatelliteServiceState) parcel.readParcelable(SemSatelliteServiceState.class.getClassLoader(), SemSatelliteServiceState.class);
        this.mSatelliteSignalStrength = (SemSatelliteSignalStrength) parcel.readParcelable(SemSatelliteSignalStrength.class.getClassLoader(), SemSatelliteSignalStrength.class);
    }

    public SemSatelliteServiceState getSatelliteServiceState() {
        return this.mSatelliteServiceState;
    }

    public SemSatelliteSignalStrength getSatelliteSignalStrength() {
        return this.mSatelliteSignalStrength;
    }

    protected void copyFrom(SemSatelliteState semSatelliteState) {
        this.mSatelliteServiceState = new SemSatelliteServiceState(semSatelliteState.mSatelliteServiceState);
        this.mSatelliteSignalStrength = new SemSatelliteSignalStrength(semSatelliteState.mSatelliteSignalStrength);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mSatelliteServiceState, i);
        parcel.writeParcelable(this.mSatelliteSignalStrength, i);
    }

    private SemSatelliteState copy() {
        Parcel parcelObtain = Parcel.obtain();
        writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        SemSatelliteState semSatelliteState = new SemSatelliteState(parcelObtain);
        parcelObtain.recycle();
        return semSatelliteState;
    }

    public String toString() {
        return "SatelliteState {serviceState=" + this.mSatelliteServiceState + " signalStrength=" + this.mSatelliteSignalStrength + "}";
    }
}
