package android.hardware.fingerprint;

import android.content.Context;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal;
import android.media.audio.common.AudioDeviceDescription;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.Slog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class FingerprintSensorConfigurations implements Parcelable {
    public static final Parcelable.Creator<FingerprintSensorConfigurations> CREATOR = new Parcelable.Creator<FingerprintSensorConfigurations>() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintSensorConfigurations createFromParcel(Parcel parcel) {
            return new FingerprintSensorConfigurations(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintSensorConfigurations[] newArray(int i) {
            return new FingerprintSensorConfigurations[i];
        }
    };
    private static final String TAG = "FingerprintSensorConfigurations";
    private final boolean mResetLockoutRequiresHardwareAuthToken;
    private final Map<String, SensorProps[]> mSensorPropsMap;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FingerprintSensorConfigurations(boolean z) {
        this.mResetLockoutRequiresHardwareAuthToken = z;
        this.mSensorPropsMap = new HashMap();
    }

    public void addAidlSensors(String[] strArr) {
        for (String str : strArr) {
            this.mSensorPropsMap.put(str, null);
        }
    }

    public void addHidlSensors(String[] strArr, Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            HidlFingerprintSensorConfig hidlFingerprintSensorConfig = new HidlFingerprintSensorConfig();
            try {
                hidlFingerprintSensorConfig.parse(str, context);
                if (hidlFingerprintSensorConfig.getModality() == 2) {
                    arrayList.add(hidlFingerprintSensorConfig);
                }
            } catch (Exception unused) {
                Log.e(TAG, "HIDL sensor configuration format is incorrect.");
            }
        }
        this.mSensorPropsMap.put("defaultHIDL", (SensorProps[]) arrayList.toArray(new HidlFingerprintSensorConfig[arrayList.size()]));
    }

    protected FingerprintSensorConfigurations(Parcel parcel) {
        this.mResetLockoutRequiresHardwareAuthToken = parcel.readByte() != 0;
        this.mSensorPropsMap = parcel.readHashMap(null, String.class, SensorProps[].class);
    }

    public boolean hasSensorConfigurations() {
        return this.mSensorPropsMap.size() > 0;
    }

    public boolean isSingleSensorConfigurationPresent() {
        return this.mSensorPropsMap.size() == 1;
    }

    public boolean doesInstanceExist(String str) {
        return this.mSensorPropsMap.containsKey(str);
    }

    public String getSensorNameNotForInstance(final String str) {
        return this.mSensorPropsMap.keySet().stream().filter(new Predicate() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FingerprintSensorConfigurations.lambda$getSensorNameNotForInstance$0(str, (String) obj);
            }
        }).findFirst().orElse(null);
    }

    static /* synthetic */ boolean lambda$getSensorNameNotForInstance$0(String str, String str2) {
        return !str2.equals(str);
    }

    public String getSensorInstance() {
        return this.mSensorPropsMap.keySet().stream().findFirst().orElse(null);
    }

    public boolean getResetLockoutRequiresHardwareAuthToken() {
        return this.mResetLockoutRequiresHardwareAuthToken;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mResetLockoutRequiresHardwareAuthToken ? (byte) 1 : (byte) 0);
        parcel.writeMap(this.mSensorPropsMap);
    }

    public static String remapFqName(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(IFingerprint.DESCRIPTOR);
        sb.append("/virtual");
        return !str.contains(sb.toString()) ? str : str.replace("IFingerprint", "virtualhal.IVirtualHal");
    }

    public static IFingerprint getIFingerprint(String str) {
        if (str.contains(AudioDeviceDescription.CONNECTION_VIRTUAL)) {
            String strRemapFqName = remapFqName(str);
            Slog.i(TAG, "getIFingerprint fqName is mapped: " + str + Session.SUBSESSION_SEPARATION_CHAR + strRemapFqName);
            try {
                return IVirtualHal.Stub.asInterface(Binder.allowBlocking(ServiceManager.waitForService(strRemapFqName))).getFingerprintHal();
            } catch (RemoteException unused) {
                Slog.e(TAG, "Remote exception in vhal.getFingerprintHal() call" + strRemapFqName);
            }
        }
        return IFingerprint.Stub.asInterface(Binder.allowBlocking(ServiceManager.waitForDeclaredService(str)));
    }

    public SensorProps[] getSensorPropForInstance(String str) {
        SensorProps[] sensorProps = this.mSensorPropsMap.get(str);
        if (sensorProps != null) {
            return sensorProps;
        }
        try {
            IFingerprint iFingerprint = getIFingerprint(IFingerprint.DESCRIPTOR + "/" + str);
            if (iFingerprint != null) {
                sensorProps = iFingerprint.getSensorProps();
            } else {
                Log.d(TAG, "IFingerprint null for instance " + str);
            }
        } catch (RemoteException unused) {
            Log.d(TAG, "Unable to get sensor properties!");
        }
        return sensorProps == null ? new SensorProps[0] : sensorProps;
    }
}
