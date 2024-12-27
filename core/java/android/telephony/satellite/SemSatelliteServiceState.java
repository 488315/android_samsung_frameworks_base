package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SemSatelliteServiceState implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteServiceState> CREATOR =
            new Parcelable.Creator<
                    SemSatelliteServiceState>() { // from class:
                                                  // android.telephony.satellite.SemSatelliteServiceState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSatelliteServiceState createFromParcel(Parcel source) {
                    return new SemSatelliteServiceState(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSatelliteServiceState[] newArray(int size) {
                    return new SemSatelliteServiceState[size];
                }
            };
    static final boolean DBG = true;
    static final String LOG_TAG = "SemSatelliteServiceState";
    public static final int SATELLITE_RADIO_POWER_OFF = 0;
    public static final int SATELLITE_RADIO_POWER_ON = 1;
    public static final int SATELLITE_RADIO_POWER_UNAVAILABLE = 2;
    private int mRadioState;
    private SemSatelliteRegistrationStateResult mRegState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatelliteRadioState {}

    public SemSatelliteServiceState() {
        this.mRadioState = 2;
        this.mRegState = new SemSatelliteRegistrationStateResult();
    }

    public SemSatelliteServiceState(int radioState, SemSatelliteRegistrationStateResult regState) {
        this.mRadioState = radioState;
        this.mRegState = new SemSatelliteRegistrationStateResult(regState);
    }

    public SemSatelliteServiceState(SemSatelliteServiceState s) {
        copyFrom(s);
    }

    private SemSatelliteServiceState(Parcel in) {
        this.mRadioState = in.readInt();
        this.mRegState =
                (SemSatelliteRegistrationStateResult)
                        in.readParcelable(
                                SemSatelliteRegistrationStateResult.class.getClassLoader(),
                                SemSatelliteRegistrationStateResult.class);
    }

    public int getRadioState() {
        return this.mRadioState;
    }

    public SemSatelliteRegistrationStateResult getRegistrationState() {
        return this.mRegState;
    }

    protected void copyFrom(SemSatelliteServiceState s) {
        this.mRadioState = s.mRadioState;
        this.mRegState = new SemSatelliteRegistrationStateResult(s.mRegState);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRadioState);
        dest.writeParcelable(this.mRegState, flags);
    }

    private SemSatelliteServiceState copy() {
        Parcel p = Parcel.obtain();
        writeToParcel(p, 0);
        p.setDataPosition(0);
        SemSatelliteServiceState result = new SemSatelliteServiceState(p);
        p.recycle();
        return result;
    }

    public static String radioStateToString(int radioState) {
        switch (radioState) {
            case 0:
                return "RADIO_POWER_OFF";
            case 1:
                return "RADIO_POWER_ON";
            case 2:
                return "RADIO_UNAVAILABLE";
            default:
                return "Unknown radio state " + radioState;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "SemSatelliteServiceState {radioState="
                + radioStateToString(this.mRadioState)
                + " RegistrationStateResult="
                + this.mRegState
                + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRadioState), this.mRegState);
    }

    private static boolean equalsHandlesNulls(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    public boolean equals(Object o) {
        if (!(o instanceof SemSatelliteServiceState)) {
            return false;
        }
        SemSatelliteServiceState other = (SemSatelliteServiceState) o;
        return this.mRadioState == other.mRadioState
                && equalsHandlesNulls(this.mRegState, other.mRegState);
    }
}
