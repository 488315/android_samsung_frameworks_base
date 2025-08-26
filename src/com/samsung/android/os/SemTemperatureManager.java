package com.samsung.android.os;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.SparseArray;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemTemperatureManager {
    private static String LOG_TAG = "SemTemperatureManager";
    private static final int SDHMS_VALUE_SIOP_LEVEL = 1;
    public static final int UNSUPPORTED = -999;
    private static ISamsungDeviceHealthManager mService;
    private static SparseArray<Thermistor> mThermistorList;

    public static class Thermistor {
        private static final int NUM_OF_TYPE = 12;
        public static final int TYPE_5G_MODEM = 6;
        public static final int TYPE_AP = 0;
        public static final int TYPE_BATTERY = 1;
        public static final int TYPE_CAMERA_FLASH = 7;
        public static final int TYPE_CHARGER = 2;
        public static final int TYPE_PAM = 5;
        public static final int TYPE_PREDICTED_BACK_SURFACE = 11;
        public static final int TYPE_PREDICTED_FRONT_SURFACE = 10;
        public static final int TYPE_PREDICTED_SURFACE = 9;
        public static final int TYPE_TABLET_COOL_AREA = 8;
        public static final int TYPE_USB = 3;
        public static final int TYPE_WIFI = 4;
        private int mType;

        private Thermistor(int i) {
            this.mType = i;
        }

        public int getType() {
            return this.mType;
        }

        public int getTemperature() {
            ISamsungDeviceHealthManager service = SemTemperatureManager.getService();
            if (service == null) {
                return -999;
            }
            try {
                return service.getTemperature(this.mType);
            } catch (Exception e) {
                e.printStackTrace();
                return -999;
            }
        }
    }

    private SemTemperatureManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0026 A[Catch: all -> 0x002a, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x0010, B:10:0x0018, B:13:0x0023, B:14:0x0026), top: B:22:0x0003, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static synchronized ISamsungDeviceHealthManager getService() {
        IBinder service;
        if (mService == null && (service = ServiceManager.getService("sdhms")) != null) {
            ISamsungDeviceHealthManager iSamsungDeviceHealthManagerAsInterface = ISamsungDeviceHealthManager.Stub.asInterface(service);
            mService = iSamsungDeviceHealthManagerAsInterface;
            if (iSamsungDeviceHealthManagerAsInterface != null) {
                try {
                    service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.os.SemTemperatureManager.1
                        @Override // android.os.IBinder.DeathRecipient
                        public void binderDied() {
                            SemTemperatureManager.mService = null;
                        }
                    }, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return mService;
    }

    private static synchronized void initThermistorList() {
        mThermistorList = new SparseArray<>();
        int[] allTemperatures = getAllTemperatures();
        for (int i = 0; i < 12; i++) {
            Thermistor thermistor = new Thermistor(i);
            if (allTemperatures[i] != -999) {
                mThermistorList.append(i, thermistor);
            }
        }
    }

    private static int[] getAllTemperatures() {
        int[] iArr = new int[12];
        ISamsungDeviceHealthManager service = getService();
        if (service != null) {
            try {
                return service.getAllTemperatures(12);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 12; i++) {
            iArr[i] = -999;
        }
        return iArr;
    }

    public static int getOverheatingProtectionLevel(Context context) {
        ISamsungDeviceHealthManager service = getService();
        if (service == null) {
            return -999;
        }
        try {
            return service.getSsrmStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -999;
        }
    }

    public static List<Thermistor> getThermistorList() {
        SparseArray<Thermistor> sparseArray = mThermistorList;
        if (sparseArray == null || sparseArray.size() == 0) {
            initThermistorList();
        }
        ArrayList arrayList = new ArrayList(mThermistorList.size());
        for (int i = 0; i < mThermistorList.size(); i++) {
            if (mThermistorList.keyAt(i) != 8) {
                arrayList.add(mThermistorList.valueAt(i));
            }
        }
        return arrayList;
    }

    public static Thermistor getThermistor(int i) {
        SparseArray<Thermistor> sparseArray = mThermistorList;
        if (sparseArray == null || sparseArray.size() == 0) {
            initThermistorList();
        }
        return mThermistorList.get(i);
    }
}
