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
        int iBeep = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iBeep = service.beep(str, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.29
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
                Log.e(str2, "beep getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iBeep, "Leave beep() with ", TAG);
        return iBeep;
    }

    public int check(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter check()");
        int iCheck = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iCheck = service.check(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.1
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iCheck, "Leave check() with ", TAG);
        return iCheck;
    }

    public int clearMemory(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter clearMemory()");
        int iClearMemory = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iClearMemory = service.clearMemory(str, str2, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.14
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i, str4);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iClearMemory, "Leave clearMemory() with ", TAG);
        return iClearMemory;
    }

    public int connectPeripheral(BluetoothDevice bluetoothDevice, final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter connectPeripheral()");
        int iConnectPeripheral = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PeripheralConstants.Internal.INTERNAL_KEY_BLUETOOTH_DEVICE, bluetoothDevice);
                iConnectPeripheral = service.connectPeripheral(bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.26
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iConnectPeripheral, "Leave connectPeripheral() with ", TAG);
        return iConnectPeripheral;
    }

    public int disable() {
        String str = TAG;
        Log.i(str, "Enter disable()");
        int iDisable = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iDisable = service.disable();
            } else {
                Log.e(str, "disable getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iDisable, "Leave disable() with ", TAG);
        return iDisable;
    }

    public int disconnectPeripheral(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter disconnectPeripheral()");
        int iDisconnectPeripheral = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iDisconnectPeripheral = service.disconnectPeripheral(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.27
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iDisconnectPeripheral, "Leave disconnectPeripheral() with ", TAG);
        return iDisconnectPeripheral;
    }

    public int displayText(String str, String str2, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter displayText()");
        int iDisplayText = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iDisplayText = service.displayText(str, str2, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.28
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i2, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i2, str4);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iDisplayText, "Leave displayText() with ", TAG);
        return iDisplayText;
    }

    public int enable(Bundle bundle) {
        return enable(bundle, true);
    }

    public int getAvailablePeripherals(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getAvailablePeripherals()");
        int availablePeripherals = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                availablePeripherals = service.getAvailablePeripherals(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.4
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(availablePeripherals, "Leave getAvailablePeripherals() with ", TAG);
        return availablePeripherals;
    }

    public int getBluetoothPeripherals(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getBluetoothPeripherals()");
        int bluetoothPeripherals = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                bluetoothPeripherals = service.getBluetoothPeripherals(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.25
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(bluetoothPeripherals, "Leave getBluetoothPeripherals() with ", TAG);
        return bluetoothPeripherals;
    }

    public int getConfiguration(String str, List<String> list, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getConfiguration()");
        int configuration = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                configuration = service.getConfiguration(str, list, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.6
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(configuration, "Leave getConfiguration() with ", TAG);
        return configuration;
    }

    public int getConnectionProfile(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getConnectionProfile()");
        int connectionProfile = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                connectionProfile = service.getConnectionProfile(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.20
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(connectionProfile, "Leave getConnectionProfile() with ", TAG);
        return connectionProfile;
    }

    public int getInformation(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getInformation()");
        int information = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                information = service.getInformation(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.5
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(information, "Leave getInformation() with ", TAG);
        return information;
    }

    public int getPairingBarcodeData(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getPairingBarcodeData()");
        int pairingBarcodeData = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                pairingBarcodeData = service.getPairingBarcodeData(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.23
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(pairingBarcodeData, "Leave getPairingBarcodeData() with ", TAG);
        return pairingBarcodeData;
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
        int storedData = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                storedData = service.getStoredData(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.13
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(storedData, "Leave getStoredData() with ", TAG);
        return storedData;
    }

    public int getSupportedPeripherals(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getSupportedPeripherals()");
        int supportedPeripherals = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                supportedPeripherals = service.getSupportedPeripherals(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.22
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(supportedPeripherals, "Leave getSupportedPeripherals() with ", TAG);
        return supportedPeripherals;
    }

    public boolean isEnabled() {
        String str = TAG;
        Log.i(str, "Enter isEnabled()");
        boolean zIsEnabled = false;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                zIsEnabled = service.isEnabled();
            } else {
                Log.e(str, "isEnabled getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isEnabled() with ", TAG, zIsEnabled);
        return zIsEnabled;
    }

    public boolean isStarted() {
        String str = TAG;
        Log.i(str, "Enter isStarted()");
        boolean zIsStarted = false;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                zIsStarted = service.isStarted();
            } else {
                Log.e(str, "isStarted getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isStarted() with ", TAG, zIsStarted);
        return zIsStarted;
    }

    public int registerDataListener(final PeripheralDataListener peripheralDataListener) {
        String str = TAG;
        Log.i(str, "Enter registerDataListener()");
        int iRegisterDataListener = 1;
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
                    public void onFail(int i, String str2) {
                        peripheralDataListener.onFail(i, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IDataListener
                    public void onReceive(int i, Bundle bundle) {
                        peripheralDataListener.onReceive(i, bundle);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IDataListener
                    public void onSuccess() {
                        peripheralDataListener.onSuccess();
                    }
                });
                iRegisterDataListener = service.registerDataListener(this.mDataListeners.get(peripheralDataListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iRegisterDataListener, "Leave registerDataListener() with ", TAG);
        return iRegisterDataListener;
    }

    public int registerInfoListener(final PeripheralInfoListener peripheralInfoListener) {
        String str = TAG;
        Log.i(str, "Enter registerInfoListener()");
        int iRegisterInfoListener = 1;
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
                    public void onFail(int i, String str2) {
                        peripheralInfoListener.onFail(i, str2);
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
                iRegisterInfoListener = service.registerInfoListener(this.mInfoListeners.get(peripheralInfoListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iRegisterInfoListener, "Leave registerInfoListener() with ", TAG);
        return iRegisterInfoListener;
    }

    public int registerStateListener(final PeripheralStateListener peripheralStateListener) {
        String str = TAG;
        Log.i(str, "Enter registerStateListener()");
        int iRegisterStateListener = 1;
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
                    public void onFail(int i, String str2) {
                        peripheralStateListener.onFail(i, str2);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IStateListener
                    public void onStateChange(int i, Bundle bundle) {
                        peripheralStateListener.onStateChange(i, bundle);
                    }

                    @Override // com.samsung.android.knox.ex.peripheral.IStateListener
                    public void onSuccess() {
                        peripheralStateListener.onSuccess();
                    }
                });
                iRegisterStateListener = service.registerStateListener(this.mStateListeners.get(peripheralStateListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iRegisterStateListener, "Leave registerStateListener() with ", TAG);
        return iRegisterStateListener;
    }

    public int resetPeripheral(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter resetPeripheral()");
        int iResetPeripheral = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iResetPeripheral = service.resetPeripheral(str, str2, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.17
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i, str4);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iResetPeripheral, "Leave resetPeripheral() with ", TAG);
        return iResetPeripheral;
    }

    public int setConfiguration(String str, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter setConfiguration()");
        int configuration = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                configuration = service.setConfiguration(str, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.7
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(configuration, "Leave setConfiguration() with ", TAG);
        return configuration;
    }

    public int setConnectionProfile(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter setConnectionProfile()");
        int connectionProfile = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                connectionProfile = service.setConnectionProfile(str, str2, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.21
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str4) throws RemoteException {
                        peripheralResultListener.onFail(i, str4);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(connectionProfile, "Leave setConnectionProfile() with ", TAG);
        return connectionProfile;
    }

    public int start(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter start()");
        int iStart = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStart = service.start(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.2
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStart, "Leave start() with ", TAG);
        return iStart;
    }

    public int startAutoTriggerMode(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter startAutoTriggerMode()");
        int iStartAutoTriggerMode = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStartAutoTriggerMode = service.startAutoTriggerMode(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.15
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStartAutoTriggerMode, "Leave startAutoTriggerMode() with ", TAG);
        return iStartAutoTriggerMode;
    }

    public int startBarcodeScan(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter startBarcodeScan()");
        int iStartBarcodeScan = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStartBarcodeScan = service.startBarcodeScan(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.11
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStartBarcodeScan, "Leave startBarcodeScan() with ", TAG);
        return iStartBarcodeScan;
    }

    public int stop(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter stop()");
        int iStop = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStop = service.stop(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.3
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStop, "Leave stop() with ", TAG);
        return iStop;
    }

    public int stopAutoTriggerMode(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter stopAutoTriggerMode()");
        int iStopAutoTriggerMode = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStopAutoTriggerMode = service.stopAutoTriggerMode(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.16
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStopAutoTriggerMode, "Leave stopAutoTriggerMode() with ", TAG);
        return iStopAutoTriggerMode;
    }

    public int stopBarcodeScan(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter stopBarcodeScan()");
        int iStopBarcodeScan = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStopBarcodeScan = service.stopBarcodeScan(str, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.12
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str3) throws RemoteException {
                        peripheralResultListener.onFail(i, str3);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStopBarcodeScan, "Leave stopBarcodeScan() with ", TAG);
        return iStopBarcodeScan;
    }

    public int stopPairingPeripheral(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter stopPairingPeripheral()");
        int iStopPairingPeripheral = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iStopPairingPeripheral = service.stopPairingPeripheral(new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.24
                    @Override // com.samsung.android.knox.ex.peripheral.IResultListener
                    public void onFail(int i, String str2) throws RemoteException {
                        peripheralResultListener.onFail(i, str2);
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStopPairingPeripheral, "Leave stopPairingPeripheral() with ", TAG);
        return iStopPairingPeripheral;
    }

    public int triggerVendorCommand(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter triggerVendorCommand()");
        int iTriggerVendorCommand = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iTriggerVendorCommand = service.triggerVendorCommand(str, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.18
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iTriggerVendorCommand, "Leave triggerVendorCommand() with ", TAG);
        return iTriggerVendorCommand;
    }

    public int unregisterDataListener(PeripheralDataListener peripheralDataListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterDataListener()");
        int iUnregisterDataListener = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iUnregisterDataListener = service.unregisterDataListener(this.mDataListeners.get(peripheralDataListener));
                this.mDataListeners.remove(peripheralDataListener);
            } else {
                Log.e(str, "unregisterDataListener getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iUnregisterDataListener, "Leave unregisterDataListener() with ", TAG);
        return iUnregisterDataListener;
    }

    public int unregisterInfoListener(PeripheralInfoListener peripheralInfoListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterInfoListener()");
        int iUnregisterInfoListener = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iUnregisterInfoListener = service.unregisterInfoListener(this.mInfoListeners.get(peripheralInfoListener));
                this.mInfoListeners.remove(peripheralInfoListener);
            } else {
                Log.e(str, "unregisterInfoListener getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iUnregisterInfoListener, "Leave unregisterInfoListener() with ", TAG);
        return iUnregisterInfoListener;
    }

    public int unregisterStateListener(PeripheralStateListener peripheralStateListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterStateListener()");
        int iUnregisterStateListener = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iUnregisterStateListener = service.unregisterStateListener(this.mStateListeners.get(peripheralStateListener));
                this.mStateListeners.remove(peripheralStateListener);
            } else {
                Log.e(str, "unregisterStateListener getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iUnregisterStateListener, "Leave unregisterStateListener() with ", TAG);
        return iUnregisterStateListener;
    }

    public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter updateFirmware()");
        int iUpdateFirmware = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iUpdateFirmware = service.updateFirmware(str, bArr, i, i2, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.19
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iUpdateFirmware, "Leave updateFirmware() with ", TAG);
        return iUpdateFirmware;
    }

    public int vibrate(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter vibrate()");
        int iVibrate = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iVibrate = service.vibrate(str, i, bundle, new IResultListener.Stub(this) { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.30
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
                Log.e(str2, "vibrate getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iVibrate, "Leave vibrate() with ", TAG);
        return iVibrate;
    }

    public int enable(Bundle bundle, boolean z) {
        String str = TAG;
        Log.i(str, "Enter enable()");
        int iEnable = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                iEnable = service.enable(bundle, z);
            } else {
                Log.e(str, "enable getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e2, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iEnable, "Leave enable() with ", TAG);
        return iEnable;
    }
}
