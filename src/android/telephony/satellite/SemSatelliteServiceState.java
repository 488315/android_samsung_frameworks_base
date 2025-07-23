package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SemSatelliteServiceState implements Parcelable {
    public static final Parcelable.Creator<SemSatelliteServiceState> CREATOR = new Parcelable.Creator<SemSatelliteServiceState>() { // from class: android.telephony.satellite.SemSatelliteServiceState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteServiceState createFromParcel(Parcel parcel) {
            return new SemSatelliteServiceState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatelliteServiceState[] newArray(int i) {
            return new SemSatelliteServiceState[i];
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
    public @interface SatelliteRadioState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSatelliteServiceState() {
        this.mRadioState = 2;
        this.mRegState = new SemSatelliteRegistrationStateResult();
    }

    public SemSatelliteServiceState(int i, SemSatelliteRegistrationStateResult semSatelliteRegistrationStateResult) {
        this.mRadioState = i;
        this.mRegState = new SemSatelliteRegistrationStateResult(semSatelliteRegistrationStateResult);
    }

    public SemSatelliteServiceState(SemSatelliteServiceState semSatelliteServiceState) {
        copyFrom(semSatelliteServiceState);
    }

    private SemSatelliteServiceState(Parcel parcel) {
        this.mRadioState = parcel.readInt();
        this.mRegState = (SemSatelliteRegistrationStateResult) parcel.readParcelable(SemSatelliteRegistrationStateResult.class.getClassLoader(), SemSatelliteRegistrationStateResult.class);
    }

    public int getRadioState() {
        return this.mRadioState;
    }

    public SemSatelliteRegistrationStateResult getRegistrationState() {
        return this.mRegState;
    }

    protected void copyFrom(SemSatelliteServiceState semSatelliteServiceState) {
        this.mRadioState = semSatelliteServiceState.mRadioState;
        this.mRegState = new SemSatelliteRegistrationStateResult(semSatelliteServiceState.mRegState);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRadioState);
        parcel.writeParcelable(this.mRegState, i);
    }

    private SemSatelliteServiceState copy() {
        Parcel obtain = Parcel.obtain();
        writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        SemSatelliteServiceState semSatelliteServiceState = new SemSatelliteServiceState(obtain);
        obtain.recycle();
        return semSatelliteServiceState;
    }

    public static String radioStateToString(int i) {
        if (i == 0) {
            return "RADIO_POWER_OFF";
        }
        if (i == 1) {
            return "RADIO_POWER_ON";
        }
        if (i == 2) {
            return "RADIO_UNAVAILABLE";
        }
        return "Unknown radio state " + i;
    }

    public String toString() {
        return "SemSatelliteServiceState {radioState=" + radioStateToString(this.mRadioState) + " RegistrationStateResult=" + this.mRegState + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mRadioState), this.mRegState);
    }

    private static boolean equalsHandlesNulls(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SemSatelliteServiceState)) {
            return false;
        }
        SemSatelliteServiceState semSatelliteServiceState = (SemSatelliteServiceState) obj;
        return this.mRadioState == semSatelliteServiceState.mRadioState && equalsHandlesNulls(this.mRegState, semSatelliteServiceState.mRegState);
    }
}
