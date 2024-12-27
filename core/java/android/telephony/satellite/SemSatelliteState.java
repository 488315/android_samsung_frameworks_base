package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class SemSatelliteState implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteState> CREATOR =
            new Parcelable.Creator<
                    SemSatelliteState>() { // from class:
                                           // android.telephony.satellite.SemSatelliteState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSatelliteState createFromParcel(Parcel source) {
                    return new SemSatelliteState(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSatelliteState[] newArray(int size) {
                    return new SemSatelliteState[size];
                }
            };
    static final boolean DBG = true;
    static final String LOG_TAG = "SatelliteState";
    private SemSatelliteServiceState mSatelliteServiceState;
    private SemSatelliteSignalStrength mSatelliteSignalStrength;

    public SemSatelliteState() {
        this.mSatelliteServiceState = new SemSatelliteServiceState();
        this.mSatelliteSignalStrength = new SemSatelliteSignalStrength();
    }

    public SemSatelliteState(SemSatelliteServiceState ss, SemSatelliteSignalStrength sig) {
        this.mSatelliteServiceState = new SemSatelliteServiceState(ss);
        this.mSatelliteSignalStrength = new SemSatelliteSignalStrength(sig);
    }

    public SemSatelliteState(SemSatelliteState s) {
        copyFrom(s);
    }

    private SemSatelliteState(Parcel in) {
        this.mSatelliteServiceState =
                (SemSatelliteServiceState)
                        in.readParcelable(
                                SemSatelliteServiceState.class.getClassLoader(),
                                SemSatelliteServiceState.class);
        this.mSatelliteSignalStrength =
                (SemSatelliteSignalStrength)
                        in.readParcelable(
                                SemSatelliteSignalStrength.class.getClassLoader(),
                                SemSatelliteSignalStrength.class);
    }

    public SemSatelliteServiceState getSatelliteServiceState() {
        return this.mSatelliteServiceState;
    }

    public SemSatelliteSignalStrength getSatelliteSignalStrength() {
        return this.mSatelliteSignalStrength;
    }

    protected void copyFrom(SemSatelliteState s) {
        this.mSatelliteServiceState = new SemSatelliteServiceState(s.mSatelliteServiceState);
        this.mSatelliteSignalStrength = new SemSatelliteSignalStrength(s.mSatelliteSignalStrength);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSatelliteServiceState, flags);
        dest.writeParcelable(this.mSatelliteSignalStrength, flags);
    }

    private SemSatelliteState copy() {
        Parcel p = Parcel.obtain();
        writeToParcel(p, 0);
        p.setDataPosition(0);
        SemSatelliteState result = new SemSatelliteState(p);
        p.recycle();
        return result;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "SatelliteState {serviceState="
                + this.mSatelliteServiceState
                + " signalStrength="
                + this.mSatelliteSignalStrength
                + "}";
    }
}
