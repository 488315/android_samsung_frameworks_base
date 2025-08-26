package com.samsung.android.knox.ex.peripheral;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ex.peripheral.IPeripheralPluginService;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class PeripheralPluginService extends Service {
    public static final String DETECT_DEATH_BINDER = "detectDeathBinder";
    public static final String TAG = "PeripheralPluginService";
    public final IPeripheralPluginService.Stub mBinder = new IPeripheralPluginService.Stub() { // from class: com.samsung.android.knox.ex.peripheral.PeripheralPluginService.1
        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int beep(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.beep(str, i, bundle, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int clearMemory(String str, String str2, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.clearMemory(str, str2, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int connect(String str, String str2, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || str2 == null || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.connect(str, str2, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || str2 == null || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.connectEx(str, str2, bundle, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int disconnect(String str, String str2, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.disconnect(str, str2, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.displayText(str, str2, i, bundle, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getAllState(IResultListener iResultListener) throws RemoteException {
            if (iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getAllState(iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getAvailablePeripherals(IResultListener iResultListener) throws RemoteException {
            if (iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getAvailablePeripherals(iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getConfiguration(String str, List<String> list, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || list == null || list.size() == 0 || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getConfiguration(str, list, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getConnectionProfile(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getConnectionProfile(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getPairingBarcodeData(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getPairingBarcodeData(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getStoredData(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getStoredData(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int getSupportedPeripherals(IResultListener iResultListener) throws RemoteException {
            if (iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.getSupportedPeripherals(iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public boolean isStarted() throws RemoteException {
            return PeripheralPluginService.this.isStarted();
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int resetPeripheral(String str, String str2, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.resetPeripheral(str, str2, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int setConfiguration(String str, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || bundle == null || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.setConfiguration(str, bundle, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int setConnectionProfile(String str, String str2, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.setConnectionProfile(str, str2, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener) throws RemoteException {
            if (iEventListener == null || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.start(bundle, iEventListener, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int startAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.startAutoTriggerMode(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int startBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.startBarcodeScan(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int stop(IResultListener iResultListener) throws RemoteException {
            if (iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.stop(iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int stopAutoTriggerMode(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.stopAutoTriggerMode(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int stopBarcodeScan(String str, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.stopBarcodeScan(str, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.triggerVendorCommand(str, i, bundle, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.updateFirmware(str, bArr, i, i2, bundle, iResultListener);
            return 0;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IPeripheralPluginService
        public int vibrate(String str, int i, Bundle bundle, IResultListener iResultListener) throws RemoteException {
            if (TextUtils.isEmpty(str) || iResultListener == null) {
                return 2;
            }
            PeripheralPluginService.this.vibrate(str, i, bundle, iResultListener);
            return 0;
        }
    };
    public BinderDeathReceiver mBinderDeathReceiver;

    class BinderDeathReceiver implements IBinder.DeathRecipient {
        public IBinder mReceiver;

        public /* synthetic */ BinderDeathReceiver(int i) {
            this();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e(PeripheralPluginService.TAG, "Binder Death Detected!");
            IBinder iBinder = this.mReceiver;
            if (iBinder != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }

        public final void setReceiver(IBinder iBinder) {
            this.mReceiver = iBinder;
        }

        private BinderDeathReceiver() {
        }

        public final void handleBinderDeath() {
        }
    }

    public abstract void beep(String str, int i, Bundle bundle, IResultListener iResultListener);

    public abstract void clearMemory(String str, String str2, IResultListener iResultListener);

    public abstract void connect(String str, String str2, IResultListener iResultListener);

    public abstract void connectEx(String str, String str2, Bundle bundle, IResultListener iResultListener);

    public abstract void disconnect(String str, String str2, IResultListener iResultListener);

    public abstract void displayText(String str, String str2, int i, Bundle bundle, IResultListener iResultListener);

    public abstract void getAllState(IResultListener iResultListener);

    public abstract void getAvailablePeripherals(IResultListener iResultListener);

    public abstract void getConfiguration(String str, List<String> list, IResultListener iResultListener);

    public abstract void getConnectionProfile(String str, IResultListener iResultListener);

    public abstract void getPairingBarcodeData(String str, IResultListener iResultListener);

    public abstract void getStoredData(String str, IResultListener iResultListener);

    public abstract void getSupportedPeripherals(IResultListener iResultListener);

    public abstract boolean isStarted();

    @Override // android.app.Service
    public IBinder onBind(Intent intent) throws RemoteException {
        IBinder binder;
        Bundle extras = intent.getExtras();
        if (extras != null && (binder = extras.getBinder("detectDeathBinder")) != null) {
            try {
                BinderDeathReceiver binderDeathReceiver = this.mBinderDeathReceiver;
                binderDeathReceiver.mReceiver = binder;
                binder.linkToDeath(binderDeathReceiver, 0);
            } catch (RemoteException e) {
                Log.e(TAG, e.toString());
            }
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mBinderDeathReceiver = new BinderDeathReceiver(0);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    public abstract void resetPeripheral(String str, String str2, IResultListener iResultListener);

    public abstract void setConfiguration(String str, Bundle bundle, IResultListener iResultListener);

    public abstract void setConnectionProfile(String str, String str2, IResultListener iResultListener);

    public abstract void start(Bundle bundle, IEventListener iEventListener, IResultListener iResultListener);

    public abstract void startAutoTriggerMode(String str, IResultListener iResultListener);

    public abstract void startBarcodeScan(String str, IResultListener iResultListener);

    public abstract void stop(IResultListener iResultListener);

    public abstract void stopAutoTriggerMode(String str, IResultListener iResultListener);

    public abstract void stopBarcodeScan(String str, IResultListener iResultListener);

    public abstract void triggerVendorCommand(String str, int i, Bundle bundle, IResultListener iResultListener);

    public abstract void updateFirmware(String str, byte[] bArr, int i, int i2, Bundle bundle, IResultListener iResultListener);

    public abstract void vibrate(String str, int i, Bundle bundle, IResultListener iResultListener);
}
