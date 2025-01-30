package com.samsung.android.knox.p045ex.peripheral;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.ServiceManager;
import android.util.Log;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.knox.p045ex.peripheral.IDataListener;
import com.samsung.android.knox.p045ex.peripheral.IInfoListener;
import com.samsung.android.knox.p045ex.peripheral.IPeripheralService;
import com.samsung.android.knox.p045ex.peripheral.IResultListener;
import com.samsung.android.knox.p045ex.peripheral.IStateListener;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PeripheralManager {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Temp {
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
                if (sInstance == null) {
                    sInstance = new PeripheralManager(context);
                }
            }
        }
        return sInstance;
    }

    public final int beep(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter beep()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.beep(str, i, bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.29
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i3, String str3) {
                        peripheralResultListener.onFail(i3, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "beep getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave beep() with ", i2, TAG);
        return i2;
    }

    public final int check(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter check()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.check(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.1
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "check getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave check() with ", i, TAG);
        return i;
    }

    public final int clearMemory(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter clearMemory()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.clearMemory(str, str2, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.14
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str4) {
                        peripheralResultListener.onFail(i2, str4);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str3, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave clearMemory() with ", i, TAG);
        return i;
    }

    public final int connectPeripheral(BluetoothDevice bluetoothDevice, final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter connectPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PeripheralConstants.Internal.INTERNAL_KEY_BLUETOOTH_DEVICE, bluetoothDevice);
                i = service.connectPeripheral(bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.26
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave connectPeripheral() with ", i, TAG);
        return i;
    }

    public final int disable() {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave disable() with ", i, TAG);
        return i;
    }

    public final int disconnectPeripheral(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter disconnectPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.disconnectPeripheral(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.27
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave disconnectPeripheral() with ", i, TAG);
        return i;
    }

    public final int displayText(String str, String str2, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter displayText()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.displayText(str, str2, i, bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.28
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i3, String str4) {
                        peripheralResultListener.onFail(i3, str4);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str3, "displayText getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave displayText() with ", i2, TAG);
        return i2;
    }

    public final int enable(Bundle bundle) {
        return enable(bundle, true);
    }

    public final int getAvailablePeripherals(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getAvailablePeripherals()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getAvailablePeripherals(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.4
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "getAvailablePeripherals getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getAvailablePeripherals() with ", i, TAG);
        return i;
    }

    public final int getBluetoothPeripherals(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getBluetoothPeripherals()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getBluetoothPeripherals(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.25
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getBluetoothPeripherals() with ", i, TAG);
        return i;
    }

    public final int getConfiguration(String str, List<String> list, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getConfiguration()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getConfiguration(str, list, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.6
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getConfiguration() with ", i, TAG);
        return i;
    }

    public final int getConnectionProfile(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getConnectionProfile()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getConnectionProfile(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.20
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getConnectionProfile() with ", i, TAG);
        return i;
    }

    public final int getInformation(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getInformation()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getInformation(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.5
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getInformation() with ", i, TAG);
        return i;
    }

    public final int getPairingBarcodeData(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getPairingBarcodeData()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getPairingBarcodeData(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.23
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getPairingBarcodeData() with ", i, TAG);
        return i;
    }

    public final List<String> getPluginsToSetup() {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        Log.i(TAG, "Leave getPluginsToSetup() with " + arrayList);
        return arrayList;
    }

    public final IPeripheralService getService() {
        return IPeripheralService.Stub.asInterface(ServiceManager.getService("peripheral"));
    }

    public final int getStoredData(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter getStoredData()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getStoredData(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.13
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getStoredData() with ", i, TAG);
        return i;
    }

    public final int getSupportedPeripherals(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter getSupportedPeripherals()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.getSupportedPeripherals(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.22
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave getSupportedPeripherals() with ", i, TAG);
        return i;
    }

    public final boolean isEnabled() {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("Leave isEnabled() with ", z, TAG);
        return z;
    }

    public final boolean isStarted() {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("Leave isStarted() with ", z, TAG);
        return z;
    }

    public final int registerDataListener(final PeripheralDataListener peripheralDataListener) {
        String str = TAG;
        Log.i(str, "Enter registerDataListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service == null) {
                Log.e(str, "registerDataListener getService failed!");
            } else if (!this.mDataListeners.containsKey(peripheralDataListener)) {
                this.mDataListeners.put(peripheralDataListener, new IDataListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.8
                    @Override // com.samsung.android.knox.p045ex.peripheral.IDataListener
                    public final long getHashCode() {
                        return peripheralDataListener.hashCode();
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IDataListener
                    public final void onFail(int i2, String str2) {
                        peripheralDataListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IDataListener
                    public final void onReceive(int i2, Bundle bundle) {
                        peripheralDataListener.onReceive(i2, bundle);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IDataListener
                    public final void onSuccess() {
                        peripheralDataListener.onSuccess();
                    }
                });
                i = service.registerDataListener(this.mDataListeners.get(peripheralDataListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave registerDataListener() with ", i, TAG);
        return i;
    }

    public final int registerInfoListener(final PeripheralInfoListener peripheralInfoListener) {
        String str = TAG;
        Log.i(str, "Enter registerInfoListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service == null) {
                Log.e(str, "registerInfoListener getService failed!");
            } else if (!this.mInfoListeners.containsKey(peripheralInfoListener)) {
                this.mInfoListeners.put(peripheralInfoListener, new IInfoListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.9
                    @Override // com.samsung.android.knox.p045ex.peripheral.IInfoListener
                    public final long getHashCode() {
                        return peripheralInfoListener.hashCode();
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IInfoListener
                    public final void onFail(int i2, String str2) {
                        peripheralInfoListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IInfoListener
                    public final void onReceive(Bundle bundle) {
                        peripheralInfoListener.onReceive(bundle);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IInfoListener
                    public final void onSuccess() {
                        peripheralInfoListener.onSuccess();
                    }
                });
                i = service.registerInfoListener(this.mInfoListeners.get(peripheralInfoListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave registerInfoListener() with ", i, TAG);
        return i;
    }

    public final int registerStateListener(final PeripheralStateListener peripheralStateListener) {
        String str = TAG;
        Log.i(str, "Enter registerStateListener()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service == null) {
                Log.e(str, "registerStateListener getService failed!");
            } else if (!this.mStateListeners.containsKey(peripheralStateListener)) {
                this.mStateListeners.put(peripheralStateListener, new IStateListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.10
                    @Override // com.samsung.android.knox.p045ex.peripheral.IStateListener
                    public final long getHashCode() {
                        return peripheralStateListener.hashCode();
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IStateListener
                    public final void onFail(int i2, String str2) {
                        peripheralStateListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IStateListener
                    public final void onStateChange(int i2, Bundle bundle) {
                        peripheralStateListener.onStateChange(i2, bundle);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IStateListener
                    public final void onSuccess() {
                        peripheralStateListener.onSuccess();
                    }
                });
                i = service.registerStateListener(this.mStateListeners.get(peripheralStateListener));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave registerStateListener() with ", i, TAG);
        return i;
    }

    public final int resetPeripheral(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter resetPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.resetPeripheral(str, str2, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.17
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str4) {
                        peripheralResultListener.onFail(i2, str4);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str3, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave resetPeripheral() with ", i, TAG);
        return i;
    }

    public final int setConfiguration(String str, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter setConfiguration()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.setConfiguration(str, bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.7
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave setConfiguration() with ", i, TAG);
        return i;
    }

    public final int setConnectionProfile(String str, String str2, final PeripheralResultListener peripheralResultListener) {
        String str3 = TAG;
        Log.i(str3, "Enter setConnectionProfile()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.setConnectionProfile(str, str2, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.21
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str4) {
                        peripheralResultListener.onFail(i2, str4);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str3, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave setConnectionProfile() with ", i, TAG);
        return i;
    }

    public final int start(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter start()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.start(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.2
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "start getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave start() with ", i, TAG);
        return i;
    }

    public final int startAutoTriggerMode(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter startAutoTriggerMode()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.startAutoTriggerMode(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.15
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave startAutoTriggerMode() with ", i, TAG);
        return i;
    }

    public final int startBarcodeScan(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter startBarcodeScan()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.startBarcodeScan(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.11
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave startBarcodeScan() with ", i, TAG);
        return i;
    }

    public final int stop(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter stop()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stop(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.3
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave stop() with ", i, TAG);
        return i;
    }

    public final int stopAutoTriggerMode(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter stopAutoTriggerMode()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stopAutoTriggerMode(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.16
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave stopAutoTriggerMode() with ", i, TAG);
        return i;
    }

    public final int stopBarcodeScan(String str, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter stopBarcodeScan()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stopBarcodeScan(str, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.12
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str3) {
                        peripheralResultListener.onFail(i2, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave stopBarcodeScan() with ", i, TAG);
        return i;
    }

    public final int stopPairingPeripheral(final PeripheralResultListener peripheralResultListener) {
        String str = TAG;
        Log.i(str, "Enter stopPairingPeripheral()");
        int i = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i = service.stopPairingPeripheral(new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.24
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i2, String str2) {
                        peripheralResultListener.onFail(i2, str2);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle) {
                        peripheralResultListener.onSuccess(bundle);
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave stopPairingPeripheral() with ", i, TAG);
        return i;
    }

    public final int triggerVendorCommand(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter triggerVendorCommand()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.triggerVendorCommand(str, i, bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.18
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i3, String str3) {
                        peripheralResultListener.onFail(i3, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave triggerVendorCommand() with ", i2, TAG);
        return i2;
    }

    public final int unregisterDataListener(PeripheralDataListener peripheralDataListener) {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave unregisterDataListener() with ", i, TAG);
        return i;
    }

    public final int unregisterInfoListener(PeripheralInfoListener peripheralInfoListener) {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave unregisterInfoListener() with ", i, TAG);
        return i;
    }

    public final int unregisterStateListener(PeripheralStateListener peripheralStateListener) {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave unregisterStateListener() with ", i, TAG);
        return i;
    }

    public final int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter updateFirmware()");
        int i3 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i3 = service.updateFirmware(str, bArr, i, i2, bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.19
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i4, String str3) {
                        peripheralResultListener.onFail(i4, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "stop getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave updateFirmware() with ", i3, TAG);
        return i3;
    }

    public final int vibrate(String str, int i, Bundle bundle, final PeripheralResultListener peripheralResultListener) {
        String str2 = TAG;
        Log.i(str2, "Enter vibrate()");
        int i2 = 1;
        try {
            IPeripheralService service = getService();
            if (service != null) {
                i2 = service.vibrate(str, i, bundle, new IResultListener.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralManager.30
                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onFail(int i3, String str3) {
                        peripheralResultListener.onFail(i3, str3);
                    }

                    @Override // com.samsung.android.knox.p045ex.peripheral.IResultListener
                    public final void onSuccess(Bundle bundle2) {
                        peripheralResultListener.onSuccess(bundle2);
                    }
                });
            } else {
                Log.e(str2, "vibrate getService failed!");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave vibrate() with ", i2, TAG);
        return i2;
    }

    public final int enable(Bundle bundle, boolean z) {
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
            AbstractC0790xf8f53ce8.m80m(e2, new StringBuilder("Exception: "), TAG);
        }
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Leave enable() with ", i, TAG);
        return i;
    }
}
