package com.samsung.android.knox.ex.peripheral;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.IDataListener;
import com.samsung.android.knox.ex.peripheral.IInfoListener;
import com.samsung.android.knox.ex.peripheral.IPeripheralService;
import com.samsung.android.knox.ex.peripheral.IResultListener;
import com.samsung.android.knox.ex.peripheral.IStateListener;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PeripheralManager {
    public static final int RESULT_CODE_FAIL_PERMISSION_ERROR = 3;
    public static final int RESULT_CODE_FAIL_SERVICE_UNAVAILABLE = 1;
    public static final int RESULT_CODE_FAIL_WRONG_ARGUMENT = 2;
    public static final int RESULT_CODE_INVALID = -1;
    public static final int RESULT_CODE_SUCCESS = 0;
    public static final String TAG = "PeripheralManager";
    public static volatile PeripheralManager sInstance;
    public final Context mContext;
    public final HashMap<PeripheralDataListener, IDataListener> mDataListeners = new HashMap<>();
    public final HashMap<PeripheralInfoListener, IInfoListener> mInfoListeners = new HashMap<>();
    public final HashMap<PeripheralStateListener, IStateListener> mStateListeners = new HashMap<>();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Temp {
        public static final String ACTION_REQUEST_VERSION = "com.samsung.android.knox.ex.peripheral.TEMP_ACTION_REQUEST_VERSION";
        public static final String ACTION_REQUEST_VERSION_RELAY = "com.samsung.android.knox.ex.peripheral.TEMP_ACTION_REQUEST_VERSION_RELAY";
        public static final String ACTION_RESPONSE_VERSION = "com.samsung.android.knox.ex.peripheral.TEMP_ACTION_RESPONSE_VERSION";
        public static final String ACTION_RESPONSE_VERSION_RELAY = "com.samsung.android.knox.ex.peripheral.TEMP_ACTION_RESPONSE_VERSION_RELAY";
        public static final String EXTRA_PACKAGE_NAME = "packageName";
        public static final String EXTRA_PACKAGE_VERSION = "packageVersion";
        public static final String EXTRA_SDK_VERSION = "sdkVersion";

        public static String getVersion() {
            return "PeripheralSDK-1.0.2.02";
        }
    }

    private PeripheralManager(Context context) {
        this.mContext = context;
    }

    public static PeripheralManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PeripheralManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new PeripheralManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public int beep(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter beep()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.beep(str, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.29
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i3, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i3, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "beep getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "Leave beep() with ", TAG);
        return i2;
    }

    public int check(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter check()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.check(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.1
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "check getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave check() with ", TAG);
        return i;
    }

    public int clearMemory(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter clearMemory()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.clearMemory(str, str2, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.14
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i2, str4);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str3, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave clearMemory() with ", TAG);
        return i;
    }

    public int connectPeripheral(BluetoothDevice bluetoothDevice, final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter connectPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PeripheralConstants.Internal.INTERNAL_KEY_BLUETOOTH_DEVICE, bluetoothDevice);
                i = service.connectPeripheral(bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.26
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave connectPeripheral() with ", TAG);
        return i;
    }

    public int disable() {
        String str = TAG;
        Log.i(str, "Enter disable()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.disable();
            } else {
                Log.e(str, "disable getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave disable() with ", TAG);
        return i;
    }

    public int disconnectPeripheral(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter disconnectPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.disconnectPeripheral(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.27
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave disconnectPeripheral() with ", TAG);
        return i;
    }

    public int displayText(String str, String str2, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter displayText()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.displayText(str, str2, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.28
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i3, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i3, str4);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str3, "displayText getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "Leave displayText() with ", TAG);
        return i2;
    }

    public int enable(Bundle bundle) {
        return enable(bundle, true);
    }

    public int getAvailablePeripherals(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getAvailablePeripherals()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getAvailablePeripherals(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.4
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "getAvailablePeripherals getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getAvailablePeripherals() with ", TAG);
        return i;
    }

    public int getBluetoothPeripherals(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getBluetoothPeripherals()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getBluetoothPeripherals(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.25
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getBluetoothPeripherals() with ", TAG);
        return i;
    }

    public int getConfiguration(String str, List<String> list, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getConfiguration()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getConfiguration(str, list, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.6
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getConfiguration() with ", TAG);
        return i;
    }

    public int getConnectionProfile(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getConnectionProfile()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getConnectionProfile(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.20
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getConnectionProfile() with ", TAG);
        return i;
    }

    public int getInformation(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getInformation()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getInformation(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.5
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getInformation() with ", TAG);
        return i;
    }

    public int getPairingBarcodeData(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getPairingBarcodeData()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getPairingBarcodeData(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.23
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getPairingBarcodeData() with ", TAG);
        return i;
    }

    public List<String> getPluginsToSetup() {
        String str = TAG;
        Log.i(str, "Enter getPluginsToSetup()");
        List<String> arrayList = new ArrayList<>();
        try {
            IPeripheralService service = getService();
            if (service != null) {
                arrayList = service.getPluginsToSetup();
            } else {
                Log.e(str, "getPluginsToSetup getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        Log.i(TAG, "Leave getPluginsToSetup() with " + arrayList);
        return arrayList;
    }

    public final IPeripheralService getService() {
        return IPeripheralService.Stub.asInterface(ServiceManager.getService("peripheral"));
    }

    public int getStoredData(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getStoredData()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getStoredData(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.13
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getStoredData() with ", TAG);
        return i;
    }

    public int getSupportedPeripherals(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getSupportedPeripherals()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getSupportedPeripherals(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.22
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave getSupportedPeripherals() with ", TAG);
        return i;
    }

    public boolean isEnabled() {
        String str = TAG;
        Log.i(str, "Enter isEnabled()");
        boolean z = false;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                z = service.isEnabled();
            } else {
                Log.e(str, "isEnabled getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isEnabled() with ", TAG, z);
        return z;
    }

    public boolean isStarted() {
        String str = TAG;
        Log.i(str, "Enter isStarted()");
        boolean z = false;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                z = service.isStarted();
            } else {
                Log.e(str, "isStarted getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isStarted() with ", TAG, z);
        return z;
    }

    public int registerDataListener(final PeripheralDataListener peripheralDataListener) {
        String str = TAG;
        Log.i(str, "Enter registerDataListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service == null) {
                Log.e(str, "registerDataListener getService failed!");
            } else if (!this.mDataListeners.containsKey(peripheralDataListener)) {
                this.mDataListeners.put(peripheralDataListener, new IDataListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.8
                    @Override // com.samsung.android.knox.ex.peripheral.IDataListener
                    public long getHashCode() {
                        return peripheralDataListener.hashCode();
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IDataListener
                    public void onFail(int i2, String str2) {
                        peripheralDataListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IDataListener
                    public void onReceive(int i2, Bundle bundle) {
                        peripheralDataListener.onReceive(i2, bundle);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IDataListener
                    public void onSuccess() {
                        peripheralDataListener.onSuccess();
                    }
                });
                i = service.registerDataListener(this.mDataListeners.get(peripheralDataListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave registerDataListener() with ", TAG);
        return i;
    }

    public int registerInfoListener(final PeripheralInfoListener peripheralInfoListener) {
        String str = TAG;
        Log.i(str, "Enter registerInfoListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service == null) {
                Log.e(str, "registerInfoListener getService failed!");
            } else if (!this.mInfoListeners.containsKey(peripheralInfoListener)) {
                this.mInfoListeners.put(peripheralInfoListener, new IInfoListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.9
                    @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
                    public long getHashCode() {
                        return peripheralInfoListener.hashCode();
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
                    public void onFail(int i2, String str2) {
                        peripheralInfoListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
                    public void onReceive(Bundle bundle) {
                        peripheralInfoListener.onReceive(bundle);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
                    public void onSuccess() {
                        peripheralInfoListener.onSuccess();
                    }
                });
                i = service.registerInfoListener(this.mInfoListeners.get(peripheralInfoListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave registerInfoListener() with ", TAG);
        return i;
    }

    public int registerStateListener(final PeripheralStateListener peripheralStateListener) {
        String str = TAG;
        Log.i(str, "Enter registerStateListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service == null) {
                Log.e(str, "registerStateListener getService failed!");
            } else if (!this.mStateListeners.containsKey(peripheralStateListener)) {
                this.mStateListeners.put(peripheralStateListener, new IStateListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.10
                    @Override // com.samsung.android.knox.ex.peripheral.IStateListener
                    public long getHashCode() {
                        return peripheralStateListener.hashCode();
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IStateListener
                    public void onFail(int i2, String str2) {
                        peripheralStateListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IStateListener
                    public void onStateChange(int i2, Bundle bundle) {
                        peripheralStateListener.onStateChange(i2, bundle);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IStateListener
                    public void onSuccess() {
                        peripheralStateListener.onSuccess();
                    }
                });
                i = service.registerStateListener(this.mStateListeners.get(peripheralStateListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave registerStateListener() with ", TAG);
        return i;
    }

    public int resetPeripheral(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter resetPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.resetPeripheral(str, str2, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.17
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i2, str4);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str3, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave resetPeripheral() with ", TAG);
        return i;
    }

    public int setConfiguration(String str, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter setConfiguration()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.setConfiguration(str, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.7
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave setConfiguration() with ", TAG);
        return i;
    }

    public int setConnectionProfile(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter setConnectionProfile()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.setConnectionProfile(str, str2, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.21
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i2, str4);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str3, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave setConnectionProfile() with ", TAG);
        return i;
    }

    public int start(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter start()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.start(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.2
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "start getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave start() with ", TAG);
        return i;
    }

    public int startAutoTriggerMode(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter startAutoTriggerMode()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.startAutoTriggerMode(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.15
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave startAutoTriggerMode() with ", TAG);
        return i;
    }

    public int startBarcodeScan(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter startBarcodeScan()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.startBarcodeScan(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.11
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave startBarcodeScan() with ", TAG);
        return i;
    }

    public int stop(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter stop()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stop(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.3
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave stop() with ", TAG);
        return i;
    }

    public int stopAutoTriggerMode(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter stopAutoTriggerMode()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stopAutoTriggerMode(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.16
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave stopAutoTriggerMode() with ", TAG);
        return i;
    }

    public int stopBarcodeScan(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter stopBarcodeScan()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stopBarcodeScan(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.12
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave stopBarcodeScan() with ", TAG);
        return i;
    }

    public int stopPairingPeripheral(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter stopPairingPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stopPairingPeripheral(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.24
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave stopPairingPeripheral() with ", TAG);
        return i;
    }

    public int triggerVendorCommand(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter triggerVendorCommand()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.triggerVendorCommand(str, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.18
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i3, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i3, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "Leave triggerVendorCommand() with ", TAG);
        return i2;
    }

    public int unregisterDataListener(PeripheralDataListener peripheralDataListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterDataListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.unregisterDataListener(this.mDataListeners.get(peripheralDataListener));
                this.mDataListeners.remove(peripheralDataListener);
            } else {
                Log.e(str, "unregisterDataListener getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave unregisterDataListener() with ", TAG);
        return i;
    }

    public int unregisterInfoListener(PeripheralInfoListener peripheralInfoListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterInfoListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.unregisterInfoListener(this.mInfoListeners.get(peripheralInfoListener));
                this.mInfoListeners.remove(peripheralInfoListener);
            } else {
                Log.e(str, "unregisterInfoListener getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave unregisterInfoListener() with ", TAG);
        return i;
    }

    public int unregisterStateListener(PeripheralStateListener peripheralStateListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterStateListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.unregisterStateListener(this.mStateListeners.get(peripheralStateListener));
                this.mStateListeners.remove(peripheralStateListener);
            } else {
                Log.e(str, "unregisterStateListener getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave unregisterStateListener() with ", TAG);
        return i;
    }

    public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter updateFirmware()");
        int i3 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i3 = service.updateFirmware(str, bArr, i, i2, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.19
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i4, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i4, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i3, "Leave updateFirmware() with ", TAG);
        return i3;
    }

    public int vibrate(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter vibrate()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.vibrate(str, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.30
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i3, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i3, str3);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onSuccess(Bundle bundle2) throws RemoteException {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "vibrate getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "Leave vibrate() with ", TAG);
        return i2;
    }

    public int enable(Bundle bundle, boolean z) {
        String str = TAG;
        Log.i(str, "Enter enable()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.enable(bundle, z);
            } else {
                Log.e(str, "enable getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave enable() with ", TAG);
        return i;
    }
}
