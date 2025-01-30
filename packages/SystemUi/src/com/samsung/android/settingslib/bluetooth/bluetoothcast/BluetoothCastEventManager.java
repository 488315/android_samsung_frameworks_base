package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BluetoothCastEventManager {
    public LocalBluetoothCastProfileManager mBluetoothCastProfileManager;
    public final IntentFilter mCastAdapterFilter;
    public final C47671 mCastAdapterReceiver;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final IntentFilter mCastProfileFilter;
    public final C47682 mCastProfileReceiver;
    public final Context mContext;
    public final Map mHandlerMap;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;
    public final ArrayList mReceivers;
    public final String TAG = "BluetoothCastEventManager";
    public final Collection mCallbacks = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdapterStateChangedHandler implements Handler {
        public /* synthetic */ AdapterStateChangedHandler(BluetoothCastEventManager bluetoothCastEventManager, int i) {
            this();
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", VideoPlayer.MEDIA_ERROR_SYSTEM);
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("AdapterStateChangedHandler :: BluetoothAdapter.ACTION_STATE_CHANGED, state = ", intExtra, BluetoothCastEventManager.this.TAG);
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = BluetoothCastEventManager.this.mCastDeviceManager;
            synchronized (cachedBluetoothCastDeviceManager) {
                if (intExtra == 13) {
                    Log.d(cachedBluetoothCastDeviceManager.TAG, "onBluetoothStateChanged :: clear mCachedCastDevices");
                    ((ArrayList) cachedBluetoothCastDeviceManager.mCachedCastDevices).clear();
                }
            }
        }

        private AdapterStateChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BluetootCastDeviceFoundHandler implements Handler {
        public /* synthetic */ BluetootCastDeviceFoundHandler(BluetoothCastEventManager bluetoothCastEventManager, int i) {
            this();
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            if (intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0) == 2) {
                BluetoothCastEventManager bluetoothCastEventManager = BluetoothCastEventManager.this;
                Log.d(bluetoothCastEventManager.TAG, semBluetoothCastDevice.getAddressForLog() + " found");
                CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = bluetoothCastEventManager.mCastDeviceManager;
                CachedBluetoothCastDevice findCastDevice = cachedBluetoothCastDeviceManager.findCastDevice(semBluetoothCastDevice);
                String str = bluetoothCastEventManager.TAG;
                if (findCastDevice == null) {
                    Log.d(str, "BluetootCastDeviceFoundHandler :: addCastDevice");
                    if (cachedBluetoothCastDeviceManager.addCastDevice(bluetoothCastEventManager.mBluetoothCastProfileManager, semBluetoothCastDevice) == null) {
                        Log.d(str, "Failed to created new CachedBluetoothDevice");
                        return;
                    }
                    return;
                }
                Log.d(str, "BluetootCastDeviceFoundHandler :: processActionFoundEvent");
                findCastDevice.mCastDevice = semBluetoothCastDevice;
                findCastDevice.mName = findCastDevice.mCastDevice.getDeviceName();
                findCastDevice.dispatchAttributesChanged();
            }
        }

        private BluetootCastDeviceFoundHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BluetootCastDeviceRemovedHandler implements Handler {
        public /* synthetic */ BluetootCastDeviceRemovedHandler(BluetoothCastEventManager bluetoothCastEventManager, int i) {
            this();
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            if (intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0) == 2) {
                Log.d(BluetoothCastEventManager.this.TAG, semBluetoothCastDevice.getAddressForLog() + " removed");
                CachedBluetoothCastDevice findCastDevice = BluetoothCastEventManager.this.mCastDeviceManager.findCastDevice(semBluetoothCastDevice);
                if (findCastDevice == null) {
                    Log.d(BluetoothCastEventManager.this.TAG, "BluetootCastDeviceRemovedHandler :: not found castdevice");
                    return;
                }
                Log.d(BluetoothCastEventManager.this.TAG, "BluetootCastDeviceRemovedHandler :: removeCastDevice");
                CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = BluetoothCastEventManager.this.mCastDeviceManager;
                synchronized (cachedBluetoothCastDeviceManager) {
                    Log.d(cachedBluetoothCastDeviceManager.TAG, "removeCastDevice : " + findCastDevice.getName());
                    ((ArrayList) cachedBluetoothCastDeviceManager.mCachedCastDevices).remove(findCastDevice);
                    cachedBluetoothCastDeviceManager.mBtManager.mCastEventManager.dispatchCastDeviceRemoved();
                }
            }
        }

        private BluetootCastDeviceRemovedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CastDiscoveryStateChangedHandler implements Handler {
        public CastDiscoveryStateChangedHandler(boolean z) {
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            synchronized (BluetoothCastEventManager.this.mCallbacks) {
                Iterator it = ((ArrayList) BluetoothCastEventManager.this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((BluetoothCastCallback) it.next()).getClass();
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Handler {
        void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager$2] */
    public BluetoothCastEventManager(LocalBluetoothCastAdapter localBluetoothCastAdapter, CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager, Context context) {
        ArrayList arrayList = new ArrayList();
        this.mReceivers = arrayList;
        ?? r2 = new BroadcastReceiver() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                String str = BluetoothCastEventManager.this.TAG;
                SemBluetoothCastDevice parcelableExtra = intent.getParcelableExtra("com.samsung.android.bluetooth.cast.device.extra.DEVICE");
                Handler handler = (Handler) ((HashMap) BluetoothCastEventManager.this.mHandlerMap).get(action);
                if (handler != null) {
                    handler.onReceive(context2, intent, parcelableExtra);
                }
            }
        };
        this.mCastAdapterReceiver = r2;
        this.mCastProfileReceiver = new BroadcastReceiver() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                String str = BluetoothCastEventManager.this.TAG;
                SemBluetoothCastDevice parcelableExtra = intent.getParcelableExtra("com.samsung.android.bluetooth.cast.device.extra.DEVICE");
                Handler handler = (Handler) ((HashMap) BluetoothCastEventManager.this.mHandlerMap).get(action);
                if (handler != null) {
                    handler.onReceive(context2, intent, parcelableExtra);
                }
            }
        };
        Log.d("BluetoothCastEventManager", "BluetoothCastEventManager");
        this.mContext = context;
        this.mLocalCastAdapter = localBluetoothCastAdapter;
        this.mCastDeviceManager = cachedBluetoothCastDeviceManager;
        IntentFilter intentFilter = new IntentFilter();
        this.mCastAdapterFilter = intentFilter;
        this.mCastProfileFilter = new IntentFilter();
        this.mHandlerMap = new HashMap();
        arrayList.clear();
        int i = 0;
        addCastAdapterHandler("android.bluetooth.adapter.action.STATE_CHANGED", new AdapterStateChangedHandler(this, i));
        addCastAdapterHandler("com.samsung.android.bluetooth.cast.action.DISCOVERY_STARTED", new CastDiscoveryStateChangedHandler(true));
        addCastAdapterHandler("com.samsung.android.bluetooth.cast.action.DISCOVERY_FINISHED", new CastDiscoveryStateChangedHandler(false));
        addCastAdapterHandler("com.samsung.android.bluetooth.cast.device.action.FOUND", new BluetootCastDeviceFoundHandler(this, i));
        addCastAdapterHandler("com.samsung.android.bluetooth.cast.device.action.REMOVED", new BluetootCastDeviceRemovedHandler(this, i));
        Log.d("BluetoothCastEventManager", "registerReceiver");
        synchronized (arrayList) {
            if (arrayList.contains(r2)) {
                context.unregisterReceiver(r2);
                arrayList.remove((Object) r2);
                Log.e("BluetoothCastEventManager", "registerReceiver :: mCastAdapterReceiver was registered already. Receiver will refresh.");
            }
            context.registerReceiver(r2, intentFilter);
            arrayList.add(r2);
        }
    }

    public final void addCastAdapterHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mCastAdapterFilter.addAction(str);
    }

    public final void dispatchCastDeviceAdded() {
        synchronized (this.mCallbacks) {
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) ((BluetoothCastCallback) it.next());
                if (SBluetoothControllerImpl.DEBUG) {
                    sBluetoothControllerImpl.getClass();
                    Log.d("SBluetoothControllerImpl", "onCastDeviceAdded");
                }
                sBluetoothControllerImpl.mHandler.sendEmptyMessage(1);
            }
        }
    }

    public final void dispatchCastDeviceRemoved() {
        synchronized (this.mCallbacks) {
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) ((BluetoothCastCallback) it.next());
                if (SBluetoothControllerImpl.DEBUG) {
                    sBluetoothControllerImpl.getClass();
                    Log.d("SBluetoothControllerImpl", "onCastDeviceRemoved:");
                }
                sBluetoothControllerImpl.mHandler.sendEmptyMessage(1);
            }
        }
    }
}
