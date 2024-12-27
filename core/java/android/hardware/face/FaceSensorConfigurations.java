package android.hardware.face;

import android.content.Context;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.SensorProps;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class FaceSensorConfigurations implements Parcelable {
    public static final Parcelable.Creator<FaceSensorConfigurations> CREATOR =
            new Parcelable.Creator<FaceSensorConfigurations>() { // from class:
                // android.hardware.face.FaceSensorConfigurations.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FaceSensorConfigurations createFromParcel(Parcel in) {
                    return new FaceSensorConfigurations(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FaceSensorConfigurations[] newArray(int size) {
                    return new FaceSensorConfigurations[size];
                }
            };
    private static final String TAG = "FaceSensorConfigurations";
    private final boolean mResetLockoutRequiresChallenge;
    private final Map<String, SensorProps[]> mSensorPropsMap;

    public FaceSensorConfigurations(boolean resetLockoutRequiresChallenge) {
        this.mResetLockoutRequiresChallenge = resetLockoutRequiresChallenge;
        this.mSensorPropsMap = new HashMap();
    }

    protected FaceSensorConfigurations(Parcel in) {
        this.mResetLockoutRequiresChallenge = in.readByte() != 0;
        this.mSensorPropsMap = in.readHashMap(null, String.class, SensorProps[].class);
    }

    public void addAidlConfigs(String[] aidlInstances) {
        for (String aidlInstance : aidlInstances) {
            this.mSensorPropsMap.put(aidlInstance, null);
        }
    }

    public void addHidlConfigs(String[] hidlConfigStrings, Context context) {
        List<HidlFaceSensorConfig> hidlFaceSensorConfigs = new ArrayList<>();
        for (String hidlConfig : hidlConfigStrings) {
            HidlFaceSensorConfig hidlFaceSensorConfig = new HidlFaceSensorConfig();
            try {
                hidlFaceSensorConfig.parse(hidlConfig, context);
                if (hidlFaceSensorConfig.getModality() == 8) {
                    hidlFaceSensorConfigs.add(hidlFaceSensorConfig);
                }
            } catch (Exception e) {
                Log.e(TAG, "HIDL sensor configuration format is incorrect.");
            }
        }
        this.mSensorPropsMap.put(
                "defaultHIDL",
                (SensorProps[])
                        hidlFaceSensorConfigs.toArray(
                                new SensorProps[hidlFaceSensorConfigs.size()]));
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
        Optional<String> notAVirtualInstance =
                this.mSensorPropsMap.keySet().stream()
                        .filter(
                                new Predicate() { // from class:
                                    // android.hardware.face.FaceSensorConfigurations$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return FaceSensorConfigurations
                                                .lambda$getSensorNameNotForInstance$0(
                                                        instance, (String) obj);
                                    }
                                })
                        .findFirst();
        return notAVirtualInstance.orElse(null);
    }

    static /* synthetic */ boolean lambda$getSensorNameNotForInstance$0(
            String instance, String instanceName) {
        return !instanceName.equals(instance);
    }

    public String getSensorInstance() {
        Optional<String> optionalInstance = this.mSensorPropsMap.keySet().stream().findFirst();
        return optionalInstance.orElse(null);
    }

    public boolean getResetLockoutRequiresChallenge() {
        return this.mResetLockoutRequiresChallenge;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mResetLockoutRequiresChallenge ? (byte) 1 : (byte) 0);
        parcel.writeMap(this.mSensorPropsMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SensorProps[] getSensorPropForInstance(String str) {
        SensorProps[] sensorPropsArr = this.mSensorPropsMap.get(str);
        if (sensorPropsArr != null) {
            return sensorPropsArr;
        }
        String str2 = IFace.DESCRIPTOR + "/" + str;
        IFace asInterface =
                IFace.Stub.asInterface(
                        Binder.allowBlocking(ServiceManager.waitForDeclaredService(str2)));
        String str3 = TAG;
        try {
            if (asInterface == null) {
                Slog.e(TAG, "Unable to get declared service: " + str2);
                str3 = str3;
            } else {
                SensorProps[] sensorProps = asInterface.getSensorProps();
                sensorPropsArr = sensorProps;
                str3 = sensorProps;
            }
        } catch (RemoteException e) {
            Log.d(str3, "Unable to get sensor properties!");
        }
        return sensorPropsArr;
    }
}
