package android.hardware.biometrics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SensorPropertiesInternal implements Parcelable {
    public static final Parcelable.Creator<SensorPropertiesInternal> CREATOR = new Parcelable.Creator<SensorPropertiesInternal>() { // from class: android.hardware.biometrics.SensorPropertiesInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorPropertiesInternal createFromParcel(Parcel parcel) {
            return new SensorPropertiesInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorPropertiesInternal[] newArray(int i) {
            return new SensorPropertiesInternal[i];
        }
    };
    public final List<ComponentInfoInternal> componentInfo;
    public final int maxEnrollmentsPerUser;
    public final boolean resetLockoutRequiresChallenge;
    public final boolean resetLockoutRequiresHardwareAuthToken;
    public final int sensorId;
    public int sensorStrength;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static SensorPropertiesInternal from(SensorPropertiesInternal sensorPropertiesInternal) {
        return new SensorPropertiesInternal(sensorPropertiesInternal.sensorId, sensorPropertiesInternal.sensorStrength, sensorPropertiesInternal.maxEnrollmentsPerUser, sensorPropertiesInternal.componentInfo, sensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken, sensorPropertiesInternal.resetLockoutRequiresChallenge);
    }

    public SensorPropertiesInternal(int i, int i2, int i3, List<ComponentInfoInternal> list, boolean z, boolean z2) {
        this.sensorId = i;
        this.sensorStrength = i2;
        this.maxEnrollmentsPerUser = i3;
        this.componentInfo = list;
        this.resetLockoutRequiresHardwareAuthToken = z;
        this.resetLockoutRequiresChallenge = z2;
    }

    protected SensorPropertiesInternal(Parcel parcel) {
        this.sensorId = parcel.readInt();
        this.sensorStrength = parcel.readInt();
        this.maxEnrollmentsPerUser = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        this.componentInfo = arrayList;
        parcel.readList(arrayList, ComponentInfoInternal.class.getClassLoader(), ComponentInfoInternal.class);
        this.resetLockoutRequiresHardwareAuthToken = parcel.readBoolean();
        this.resetLockoutRequiresChallenge = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.sensorId);
        parcel.writeInt(this.sensorStrength);
        parcel.writeInt(this.maxEnrollmentsPerUser);
        parcel.writeList(this.componentInfo);
        parcel.writeBoolean(this.resetLockoutRequiresHardwareAuthToken);
        parcel.writeBoolean(this.resetLockoutRequiresChallenge);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (ComponentInfoInternal componentInfoInternal : this.componentInfo) {
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            sb.append(componentInfoInternal.toString());
            sb.append("] ");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return "ID: " + this.sensorId + ", Strength: " + this.sensorStrength + ", MaxEnrollmentsPerUser: " + this.maxEnrollmentsPerUser + ", ComponentInfo: " + sb.toString();
    }
}
