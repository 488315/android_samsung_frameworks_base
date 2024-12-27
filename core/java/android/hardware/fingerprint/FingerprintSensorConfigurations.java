package android.hardware.fingerprint;

import android.content.Context;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.SensorProps;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class FingerprintSensorConfigurations implements Parcelable {
    public static final Parcelable.Creator<FingerprintSensorConfigurations> CREATOR = new Parcelable.Creator<FingerprintSensorConfigurations>() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintSensorConfigurations createFromParcel(Parcel in) {
            return new FingerprintSensorConfigurations(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintSensorConfigurations[] newArray(int size) {
            return new FingerprintSensorConfigurations[size];
        }
    };
    private static final String TAG = "FingerprintSensorConfigurations";
    private final boolean mResetLockoutRequiresHardwareAuthToken;
    private final Map<String, SensorProps[]> mSensorPropsMap;

    public FingerprintSensorConfigurations(boolean resetLockoutRequiresHardwareAuthToken) {
        this.mResetLockoutRequiresHardwareAuthToken = resetLockoutRequiresHardwareAuthToken;
        this.mSensorPropsMap = new HashMap();
    }

    public void addAidlSensors(String[] aidlInstances) {
        for (String aidlInstance : aidlInstances) {
            this.mSensorPropsMap.put(aidlInstance, null);
        }
    }

    public void addHidlSensors(String[] hidlConfigStrings, Context context) {
        List<HidlFingerprintSensorConfig> hidlFingerprintSensorConfigs = new ArrayList<>();
        for (String hidlConfigString : hidlConfigStrings) {
            HidlFingerprintSensorConfig hidlFingerprintSensorConfig = new HidlFingerprintSensorConfig();
            try {
                hidlFingerprintSensorConfig.parse(hidlConfigString, context);
                if (hidlFingerprintSensorConfig.getModality() == 2) {
                    hidlFingerprintSensorConfigs.add(hidlFingerprintSensorConfig);
                }
            } catch (Exception e) {
                Log.e(TAG, "HIDL sensor configuration format is incorrect.");
            }
        }
        this.mSensorPropsMap.put("defaultHIDL", (SensorProps[]) hidlFingerprintSensorConfigs.toArray(new HidlFingerprintSensorConfig[hidlFingerprintSensorConfigs.size()]));
    }

    protected FingerprintSensorConfigurations(Parcel in) {
        this.mResetLockoutRequiresHardwareAuthToken = in.readByte() != 0;
        this.mSensorPropsMap = in.readHashMap(null, String.class, SensorProps[].class);
    }

    public boolean hasSensorConfigurations() {
        return this.mSensorPropsMap.size() > 0;
    }

    public boolean isSingleSensorConfigurationPresent() {
        return this.mSensorPropsMap.size() == 1;
    }

    public boolean doesInstanceExist(String instance) {
        return this.mSensorPropsMap.containsKey(instance);
    }

    public String getSensorNameNotForInstance(final String instance) {
        Optional<String> notAVirtualInstance = this.mSensorPropsMap.keySet().stream().filter(new Predicate() { // from class: android.hardware.fingerprint.FingerprintSensorConfigurations$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FingerprintSensorConfigurations.lambda$getSensorNameNotForInstance$0(instance, (String) obj);
            }
        }).findFirst();
        return notAVirtualInstance.orElse(null);
    }

    static /* synthetic */ boolean lambda$getSensorNameNotForInstance$0(String instance, String instanceName) {
        return !instanceName.equals(instance);
    }

    public String getSensorInstance() {
        Optional<String> optionalInstance = this.mSensorPropsMap.keySet().stream().findFirst();
        return optionalInstance.orElse(null);
    }

    public boolean getResetLockoutRequiresHardwareAuthToken() {
        return this.mResetLockoutRequiresHardwareAuthToken;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mResetLockoutRequiresHardwareAuthToken ? (byte) 1 : (byte) 0);
        parcel.writeMap(this.mSensorPropsMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.hardware.biometrics.fingerprint.SensorProps[]] */
    public SensorProps[] getSensorPropForInstance(String str) {
        String str2 = TAG;
        SensorProps[] sensorPropsArr = this.mSensorPropsMap.get(str);
        if (sensorPropsArr != null) {
            return sensorPropsArr;
        }
        try {
            IFingerprint asInterface = IFingerprint.Stub.asInterface(Binder.allowBlocking(ServiceManager.waitForDeclaredService(IFingerprint.DESCRIPTOR + "/" + str)));
            if (asInterface == null) {
                Log.d(TAG, "IFingerprint null for instance " + str);
                str2 = str2;
            } else {
                ?? sensorProps = asInterface.getSensorProps();
                sensorPropsArr = sensorProps;
                str2 = sensorProps;
            }
        } catch (RemoteException e) {
            Log.d(str2, "Unable to get sensor properties!");
        }
        return sensorPropsArr;
    }
}
