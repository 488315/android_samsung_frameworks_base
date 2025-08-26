package android.hardware.face;

import android.content.Context;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.face.SensorProps;
import android.hardware.biometrics.face.virtualhal.IVirtualHal;
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
public class FaceSensorConfigurations implements Parcelable {
    public static final Parcelable.Creator<FaceSensorConfigurations> CREATOR = new Parcelable.Creator<FaceSensorConfigurations>() { // from class: android.hardware.face.FaceSensorConfigurations.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceSensorConfigurations createFromParcel(Parcel parcel) {
            return new FaceSensorConfigurations(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceSensorConfigurations[] newArray(int i) {
            return new FaceSensorConfigurations[i];
        }
    };
    private static final String TAG = "FaceSensorConfigurations";
    private final boolean mResetLockoutRequiresChallenge;
    private final Map<String, SensorProps[]> mSensorPropsMap;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FaceSensorConfigurations(boolean z) {
        this.mResetLockoutRequiresChallenge = z;
        this.mSensorPropsMap = new HashMap();
    }

    protected FaceSensorConfigurations(Parcel parcel) {
        this.mResetLockoutRequiresChallenge = parcel.readByte() != 0;
        this.mSensorPropsMap = parcel.readHashMap(null, String.class, SensorProps[].class);
    }

    public void addAidlConfigs(String[] strArr) {
        for (String str : strArr) {
            this.mSensorPropsMap.put(str, null);
        }
    }

    public void addHidlConfigs(String[] strArr, Context context) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            HidlFaceSensorConfig hidlFaceSensorConfig = new HidlFaceSensorConfig();
            try {
                hidlFaceSensorConfig.parse(str, context);
                if (hidlFaceSensorConfig.getModality() == 8) {
                    arrayList.add(hidlFaceSensorConfig);
                }
            } catch (Exception unused) {
                Log.e(TAG, "HIDL sensor configuration format is incorrect.");
            }
        }
        this.mSensorPropsMap.put("defaultHIDL", (SensorProps[]) arrayList.toArray(new SensorProps[arrayList.size()]));
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
        return this.mSensorPropsMap.keySet().stream().filter(new Predicate() { // from class: android.hardware.face.FaceSensorConfigurations$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FaceSensorConfigurations.lambda$getSensorNameNotForInstance$0(str, (String) obj);
            }
        }).findFirst().orElse(null);
    }

    static /* synthetic */ boolean lambda$getSensorNameNotForInstance$0(String str, String str2) {
        return !str2.equals(str);
    }

    public String getSensorInstance() {
        return this.mSensorPropsMap.keySet().stream().findFirst().orElse(null);
    }

    public boolean getResetLockoutRequiresChallenge() {
        return this.mResetLockoutRequiresChallenge;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mResetLockoutRequiresChallenge ? (byte) 1 : (byte) 0);
        parcel.writeMap(this.mSensorPropsMap);
    }

    public static String remapFqName(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(IFace.DESCRIPTOR);
        sb.append("/virtual");
        return !str.contains(sb.toString()) ? str : str.replace("IFace", "virtualhal.IVirtualHal");
    }

    public static IFace getIFace(String str) {
        if (str.contains(AudioDeviceDescription.CONNECTION_VIRTUAL)) {
            String strRemapFqName = remapFqName(str);
            Slog.i(TAG, "getIFace fqName is mapped: " + str + Session.SUBSESSION_SEPARATION_CHAR + strRemapFqName);
            try {
                return IVirtualHal.Stub.asInterface(Binder.allowBlocking(ServiceManager.waitForService(strRemapFqName))).getFaceHal();
            } catch (RemoteException unused) {
                Slog.e(TAG, "Remote exception in vhal.getFaceHal() call" + strRemapFqName);
            }
        }
        return IFace.Stub.asInterface(Binder.allowBlocking(ServiceManager.waitForDeclaredService(str)));
    }

    public SensorProps[] getSensorPropForInstance(String str) {
        SensorProps[] sensorPropsArr = this.mSensorPropsMap.get(str);
        if (sensorPropsArr == null) {
            try {
                IFace iFace = getIFace(IFace.DESCRIPTOR + "/" + str);
                if (iFace != null) {
                    return iFace.getSensorProps();
                }
                Log.d(TAG, "IFace null for instance " + str);
                return sensorPropsArr;
            } catch (RemoteException unused) {
                Log.d(TAG, "Unable to get sensor properties!");
            }
        }
        return sensorPropsArr;
    }
}
