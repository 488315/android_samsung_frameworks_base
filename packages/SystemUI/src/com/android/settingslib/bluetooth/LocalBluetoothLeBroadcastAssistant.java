package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.samsung.android.bluetooth.SemLeBroadcastAssistant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LocalBluetoothLeBroadcastAssistant implements LocalBluetoothProfile {
    public final AnonymousClass2 mAssistantCallback;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final Executor mExecutor;
    public boolean mIsProfileReady;
    public BluetoothLeAudio mLeAudioService;
    public final AnonymousClass4 mLeAudioServiceProfileListener;
    public final AnonymousClass3 mLeBroadcastAssistantListener;
    public final LocalBluetoothProfileManager mProfileManager;
    public SemLeBroadcastAssistant mSemLeBroadcastAssistant;
    public BluetoothLeBroadcastAssistant mService;
    public final AnonymousClass1 mServiceListener;
    public final Map mCachedCallbackExecutorMap = new ConcurrentHashMap();
    public final HashSet mServiceConnectedCallbacks = new HashSet();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$1, reason: invalid class name */
    public class AnonymousClass1 implements BluetoothProfile.ServiceListener {
        public AnonymousClass1() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service connected");
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = (BluetoothLeBroadcastAssistant) bluetoothProfile;
            LocalBluetoothLeBroadcastAssistant.this.mService = bluetoothLeBroadcastAssistant;
            List connectedDevices = bluetoothLeBroadcastAssistant.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.d("LocalBluetoothLeBroadcastAssistant", "LocalBluetoothLeBroadcastAssistant found new device: " + bluetoothDevice);
                    findDevice = LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                findDevice.onProfileStateChanged(LocalBluetoothLeBroadcastAssistant.this, 2);
                findDevice.refresh();
            }
            LocalBluetoothLeBroadcastAssistant.this.mProfileManager.callServiceConnectedListeners();
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = LocalBluetoothLeBroadcastAssistant.this;
            if (localBluetoothLeBroadcastAssistant.mIsProfileReady) {
                return;
            }
            localBluetoothLeBroadcastAssistant.mIsProfileReady = true;
            localBluetoothLeBroadcastAssistant.registerServiceCallBack(localBluetoothLeBroadcastAssistant.mExecutor, localBluetoothLeBroadcastAssistant.mAssistantCallback);
            Log.d("LocalBluetoothLeBroadcastAssistant", "onServiceConnected, register mCachedCallbackExecutorMap = " + LocalBluetoothLeBroadcastAssistant.this.mCachedCallbackExecutorMap);
            ((ConcurrentHashMap) LocalBluetoothLeBroadcastAssistant.this.mCachedCallbackExecutorMap).forEach(new BiConsumer() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant2 = LocalBluetoothLeBroadcastAssistant.this;
                    localBluetoothLeBroadcastAssistant2.registerServiceCallBack((Executor) obj2, (BluetoothLeBroadcastAssistant.Callback) obj);
                }
            });
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            if (i != 29) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "The profile is not LE_AUDIO_BROADCAST_ASSISTANT");
                return;
            }
            Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service disconnected");
            LocalBluetoothLeBroadcastAssistant.this.mProfileManager.callServiceDisconnectedListeners();
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = LocalBluetoothLeBroadcastAssistant.this;
            if (localBluetoothLeBroadcastAssistant.mIsProfileReady) {
                localBluetoothLeBroadcastAssistant.mIsProfileReady = false;
                localBluetoothLeBroadcastAssistant.unregisterServiceCallBack(localBluetoothLeBroadcastAssistant.mAssistantCallback);
                ((ConcurrentHashMap) LocalBluetoothLeBroadcastAssistant.this.mCachedCallbackExecutorMap).clear();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$2] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$3, com.samsung.android.bluetooth.SemLeBroadcastAssistant$LeBroadcastAssistantProfileListener] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.bluetooth.BluetoothProfile$ServiceListener, com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$4] */
    public LocalBluetoothLeBroadcastAssistant(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mServiceListener = anonymousClass1;
        this.mAssistantCallback = new BluetoothLeBroadcastAssistant.Callback(this) { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant.2
            public final void onSearchStartFailed(int i) {
            }

            public final void onSearchStarted(int i) {
            }

            public final void onSearchStopFailed(int i) {
            }

            public final void onSearchStopped(int i) {
            }

            public final void onSourceFound(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            }

            public final void onReceiveStateChanged(BluetoothDevice bluetoothDevice, int i, BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
            }

            public final void onSourceAddFailed(BluetoothDevice bluetoothDevice, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, int i) {
            }

            public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
            }

            public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {
            }

            public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            }

            public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            }

            public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
            }
        };
        ?? r1 = new SemLeBroadcastAssistant.LeBroadcastAssistantProfileListener() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant.3
            public final void onServiceConnected(SemLeBroadcastAssistant semLeBroadcastAssistant) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "LE Broadcast Assistant Profile Service Connected");
                LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = LocalBluetoothLeBroadcastAssistant.this;
                localBluetoothLeBroadcastAssistant.mSemLeBroadcastAssistant = semLeBroadcastAssistant;
                if (localBluetoothLeBroadcastAssistant.mLeAudioService == null || semLeBroadcastAssistant == null) {
                    return;
                }
                Iterator it = localBluetoothLeBroadcastAssistant.mServiceConnectedCallbacks.iterator();
                if (it.hasNext()) {
                    throw FragmentManager$$ExternalSyntheticOutline0.m(it);
                }
            }

            public final void onServiceDisconnected() {
                Log.d("LocalBluetoothLeBroadcastAssistant", "LE Broadcast Assistant Profile Service Disconnected");
                LocalBluetoothLeBroadcastAssistant.this.mSemLeBroadcastAssistant = null;
            }
        };
        this.mLeBroadcastAssistantListener = r1;
        ?? r2 = new BluetoothProfile.ServiceListener() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant.4
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "LE Audio Profile Service Connected");
                LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = LocalBluetoothLeBroadcastAssistant.this;
                BluetoothLeAudio bluetoothLeAudio = (BluetoothLeAudio) bluetoothProfile;
                localBluetoothLeBroadcastAssistant.mLeAudioService = bluetoothLeAudio;
                if (bluetoothLeAudio == null || localBluetoothLeBroadcastAssistant.mSemLeBroadcastAssistant == null) {
                    return;
                }
                Iterator it = localBluetoothLeBroadcastAssistant.mServiceConnectedCallbacks.iterator();
                if (it.hasNext()) {
                    throw FragmentManager$$ExternalSyntheticOutline0.m(it);
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceDisconnected(int i) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "LE Audio Profile Service Disconnected");
                LocalBluetoothLeBroadcastAssistant.this.mLeAudioService = null;
            }
        };
        this.mLeAudioServiceProfileListener = r2;
        this.mProfileManager = localBluetoothProfileManager;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mExecutor = Executors.newSingleThreadExecutor();
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, anonymousClass1, 29);
        new BluetoothLeBroadcastMetadata.Builder();
        SemLeBroadcastAssistant.getProxy(context, (SemLeBroadcastAssistant.LeBroadcastAssistantProfileListener) r1);
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, r2, 22);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("LocalBluetoothLeBroadcastAssistant", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(29, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcastAssistant", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    public final List getAllSources(BluetoothDevice bluetoothDevice) {
        Log.d("LocalBluetoothLeBroadcastAssistant", "getAllSources()");
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant != null) {
            return bluetoothLeBroadcastAssistant.getAllSources(bluetoothDevice);
        }
        Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcastAssistant is null");
        return new ArrayList();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            return 0;
        }
        return bluetoothLeBroadcastAssistant.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 29;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        return (bluetoothLeBroadcastAssistant == null || bluetoothDevice == null || bluetoothLeBroadcastAssistant.getConnectionPolicy(bluetoothDevice) <= 0) ? false : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final void registerServiceCallBack(Executor executor, BluetoothLeBroadcastAssistant.Callback callback) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d("LocalBluetoothLeBroadcastAssistant", "registerServiceCallBack failed, the BluetoothLeBroadcastAssistant is null.");
            ((ConcurrentHashMap) this.mCachedCallbackExecutorMap).putIfAbsent(callback, executor);
            return;
        }
        try {
            bluetoothLeBroadcastAssistant.registerCallback(executor, callback);
        } catch (IllegalArgumentException e) {
            Log.w("LocalBluetoothLeBroadcastAssistant", "registerServiceCallBack failed. " + e.getMessage());
        }
    }

    public final String toString() {
        return "LE_AUDIO_BROADCAST_ASSISTANT";
    }

    public final void unregisterServiceCallBack(BluetoothLeBroadcastAssistant.Callback callback) {
        ((ConcurrentHashMap) this.mCachedCallbackExecutorMap).remove(callback);
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d("LocalBluetoothLeBroadcastAssistant", "unregisterServiceCallBack failed, the BluetoothLeBroadcastAssistant is null.");
            return;
        }
        try {
            bluetoothLeBroadcastAssistant.unregisterCallback(callback);
        } catch (IllegalArgumentException e) {
            Log.w("LocalBluetoothLeBroadcastAssistant", "unregisterServiceCallBack failed. " + e.getMessage());
        }
    }
}
