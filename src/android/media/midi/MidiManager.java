package android.media.midi;

import android.appwidget.AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
import android.bluetooth.BluetoothDevice;
import android.media.midi.IMidiDeviceListener;
import android.media.midi.IMidiDeviceOpenCallback;
import android.media.midi.MidiDeviceServer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class MidiManager {
    public static final String BLUETOOTH_MIDI_SERVICE_CLASS = "com.android.bluetoothmidiservice.BluetoothMidiService";
    public static final String BLUETOOTH_MIDI_SERVICE_INTENT = "android.media.midi.BluetoothMidiService";
    public static final String BLUETOOTH_MIDI_SERVICE_PACKAGE = "com.android.bluetoothmidiservice";
    private static final String TAG = "MidiManager";
    public static final int TRANSPORT_MIDI_BYTE_STREAM = 1;
    public static final int TRANSPORT_UNIVERSAL_MIDI_PACKETS = 2;
    private final IMidiManager mService;
    private final IBinder mToken = new Binder();
    private ConcurrentHashMap<DeviceCallback, DeviceListener> mDeviceListeners = new ConcurrentHashMap<>();

    public static class DeviceCallback {
        public void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) {
        }

        public void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) {
        }

        public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) {
        }
    }

    public interface OnDeviceOpenedListener {
        void onDeviceOpened(MidiDevice midiDevice);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Transport {
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DeviceListener extends IMidiDeviceListener.Stub {
        private final DeviceCallback mCallback;
        private final Executor mExecutor;
        private final int mTransport;

        DeviceListener(MidiManager midiManager, DeviceCallback deviceCallback, Executor executor, int i) {
            this.mCallback = deviceCallback;
            this.mExecutor = executor;
            this.mTransport = i;
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceAdded(final MidiDeviceInfo midiDeviceInfo) {
            if (shouldInvokeCallback(midiDeviceInfo)) {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onDeviceAdded$0(midiDeviceInfo);
                        }
                    });
                } else {
                    this.mCallback.onDeviceAdded(midiDeviceInfo);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceAdded$0(MidiDeviceInfo midiDeviceInfo) {
            this.mCallback.onDeviceAdded(midiDeviceInfo);
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceRemoved(final MidiDeviceInfo midiDeviceInfo) {
            if (shouldInvokeCallback(midiDeviceInfo)) {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    executor.execute(new Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onDeviceRemoved$1(midiDeviceInfo);
                        }
                    });
                } else {
                    this.mCallback.onDeviceRemoved(midiDeviceInfo);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceRemoved$1(MidiDeviceInfo midiDeviceInfo) {
            this.mCallback.onDeviceRemoved(midiDeviceInfo);
        }

        @Override // android.media.midi.IMidiDeviceListener
        public void onDeviceStatusChanged(final MidiDeviceStatus midiDeviceStatus) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.media.midi.MidiManager$DeviceListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDeviceStatusChanged$2(midiDeviceStatus);
                    }
                });
            } else {
                this.mCallback.onDeviceStatusChanged(midiDeviceStatus);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceStatusChanged$2(MidiDeviceStatus midiDeviceStatus) {
            this.mCallback.onDeviceStatusChanged(midiDeviceStatus);
        }

        private boolean shouldInvokeCallback(MidiDeviceInfo midiDeviceInfo) {
            int i = this.mTransport;
            if (i == 2) {
                return midiDeviceInfo.getDefaultProtocol() != -1;
            }
            if (i == 1) {
                return midiDeviceInfo.getDefaultProtocol() == -1;
            }
            Log.e(MidiManager.TAG, "Invalid transport type: " + this.mTransport);
            return false;
        }
    }

    public MidiManager(IMidiManager iMidiManager) {
        this.mService = iMidiManager;
    }

    @Deprecated
    public void registerDeviceCallback(DeviceCallback deviceCallback, Handler handler) {
        AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1;
        if (handler != null) {
            Objects.requireNonNull(handler);
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = new AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(handler);
        } else {
            appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 = null;
        }
        DeviceListener deviceListener = new DeviceListener(this, deviceCallback, appWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1, 1);
        try {
            this.mService.registerListener(this.mToken, deviceListener);
            this.mDeviceListeners.put(deviceCallback, deviceListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerDeviceCallback(int i, Executor executor, DeviceCallback deviceCallback) {
        Objects.requireNonNull(executor);
        DeviceListener deviceListener = new DeviceListener(this, deviceCallback, executor, i);
        try {
            this.mService.registerListener(this.mToken, deviceListener);
            this.mDeviceListeners.put(deviceCallback, deviceListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDeviceCallback(DeviceCallback deviceCallback) {
        DeviceListener deviceListenerRemove = this.mDeviceListeners.remove(deviceCallback);
        if (deviceListenerRemove != null) {
            try {
                this.mService.unregisterListener(this.mToken, deviceListenerRemove);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public MidiDeviceInfo[] getDevices() {
        try {
            return this.mService.getDevices();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<MidiDeviceInfo> getDevicesForTransport(int i) {
        try {
            MidiDeviceInfo[] devicesForTransport = this.mService.getDevicesForTransport(i);
            if (devicesForTransport == null) {
                return Collections.EMPTY_SET;
            }
            return new ArraySet(devicesForTransport);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOpenDeviceResponse(final MidiDevice midiDevice, final OnDeviceOpenedListener onDeviceOpenedListener, Handler handler) {
        if (handler != null) {
            handler.post(new Runnable(this) { // from class: android.media.midi.MidiManager.1
                @Override // java.lang.Runnable
                public void run() {
                    onDeviceOpenedListener.onDeviceOpened(midiDevice);
                }
            });
        } else {
            onDeviceOpenedListener.onDeviceOpened(midiDevice);
        }
    }

    public void openDevice(final MidiDeviceInfo midiDeviceInfo, final OnDeviceOpenedListener onDeviceOpenedListener, final Handler handler) {
        try {
            this.mService.openDevice(this.mToken, midiDeviceInfo, new IMidiDeviceOpenCallback.Stub() { // from class: android.media.midi.MidiManager.2
                @Override // android.media.midi.IMidiDeviceOpenCallback
                public void onDeviceOpened(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder) {
                    MidiManager.this.sendOpenDeviceResponse(iMidiDeviceServer != null ? new MidiDevice(midiDeviceInfo, iMidiDeviceServer, MidiManager.this.mService, MidiManager.this.mToken, iBinder) : null, onDeviceOpenedListener, handler);
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void openBluetoothDevice(BluetoothDevice bluetoothDevice, final OnDeviceOpenedListener onDeviceOpenedListener, final Handler handler) {
        Log.d(TAG, "openBluetoothDevice() " + bluetoothDevice);
        try {
            this.mService.openBluetoothDevice(this.mToken, bluetoothDevice, new IMidiDeviceOpenCallback.Stub() { // from class: android.media.midi.MidiManager.3
                @Override // android.media.midi.IMidiDeviceOpenCallback
                public void onDeviceOpened(IMidiDeviceServer iMidiDeviceServer, IBinder iBinder) {
                    MidiDevice midiDevice;
                    Log.d(MidiManager.TAG, "onDeviceOpened() server:" + iMidiDeviceServer);
                    if (iMidiDeviceServer != null) {
                        try {
                            midiDevice = new MidiDevice(iMidiDeviceServer.getDeviceInfo(), iMidiDeviceServer, MidiManager.this.mService, MidiManager.this.mToken, iBinder);
                        } catch (RemoteException unused) {
                            Log.e(MidiManager.TAG, "remote exception in getDeviceInfo()");
                        }
                    } else {
                        midiDevice = null;
                    }
                    MidiManager.this.sendOpenDeviceResponse(midiDevice, onDeviceOpenedListener, handler);
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void closeBluetoothDevice(MidiDevice midiDevice) {
        try {
            midiDevice.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception closing BLE-MIDI device" + e);
        }
    }

    public MidiDeviceServer createDeviceServer(MidiReceiver[] midiReceiverArr, int i, String[] strArr, String[] strArr2, Bundle bundle, int i2, int i3, MidiDeviceServer.Callback callback) {
        try {
            MidiDeviceServer midiDeviceServer = new MidiDeviceServer(this.mService, midiReceiverArr, i, callback);
            if (this.mService.registerDeviceServer(midiDeviceServer.getBinderInterface(), midiReceiverArr.length, i, strArr, strArr2, bundle, i2, i3) != null) {
                return midiDeviceServer;
            }
            Log.e(TAG, "registerVirtualDevice failed");
            return null;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
