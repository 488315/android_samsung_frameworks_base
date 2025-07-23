package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BluetoothCastEventManager {
    public final String TAG;
    public LocalBluetoothCastProfileManager mBluetoothCastProfileManager;
    public final Collection mCallbacks;
    public final IntentFilter mCastAdapterFilter;
    public final AnonymousClass1 mCastAdapterReceiver;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final IntentFilter mCastProfileFilter;
    public final AnonymousClass2 mCastProfileReceiver;
    public final Context mContext;
    public final Map mHandlerMap;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;
    public final ArrayList mReceivers;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AdapterStateChangedHandler implements Handler {
        public /* synthetic */ AdapterStateChangedHandler(BluetoothCastEventManager bluetoothCastEventManager, int i) {
            this();
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            ListPopupWindow$$ExternalSyntheticOutline0.m(intExtra, "AdapterStateChangedHandler :: BluetoothAdapter.ACTION_STATE_CHANGED, state = ", BluetoothCastEventManager.this.TAG);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BluetootCastDeviceFoundHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BluetootCastDeviceRemovedHandler implements Handler {
        public /* synthetic */ BluetootCastDeviceRemovedHandler(BluetoothCastEventManager bluetoothCastEventManager, int i) {
            this();
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            int i = 0;
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
                    BluetoothCastEventManager bluetoothCastEventManager = cachedBluetoothCastDeviceManager.mBtManager.mCastEventManager;
                    synchronized (bluetoothCastEventManager.mCallbacks) {
                        try {
                            ArrayList arrayList = (ArrayList) bluetoothCastEventManager.mCallbacks;
                            int size = arrayList.size();
                            while (i < size) {
                                Object obj = arrayList.get(i);
                                i++;
                                ((BluetoothCastCallback) obj).onCastDeviceRemoved();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }

        private BluetootCastDeviceRemovedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CastDiscoveryStateChangedHandler implements Handler {
        public final boolean mStarted;

        public CastDiscoveryStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        public final void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice) {
            synchronized (BluetoothCastEventManager.this.mCallbacks) {
                try {
                    ArrayList arrayList = (ArrayList) BluetoothCastEventManager.this.mCallbacks;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((BluetoothCastCallback) obj).onCastDiscoveryStateChanged(this.mStarted);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Handler {
        void onReceive(Context context, Intent intent, SemBluetoothCastDevice semBluetoothCastDevice);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager$2] */
    public BluetoothCastEventManager(LocalBluetoothCastAdapter localBluetoothCastAdapter, CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager, Context context) {
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
        this.mCallbacks = new ArrayList();
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
        Log.d(simpleName, "BluetoothCastEventManager");
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
        Log.d(simpleName, "registerReceiver");
        synchronized (arrayList) {
            try {
                if (arrayList.contains(r2)) {
                    context.unregisterReceiver(r2);
                    arrayList.remove((Object) r2);
                    Log.e(simpleName, "registerReceiver :: mCastAdapterReceiver was registered already. Receiver will refresh.");
                }
                context.registerReceiver(r2, intentFilter);
                arrayList.add(r2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addCastAdapterHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mCastAdapterFilter.addAction(str);
    }
}
