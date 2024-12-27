package com.android.server.input;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.input.IInputDeviceBatteryListener;
import android.hardware.input.IInputDeviceBatteryState;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UEventObserver;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.view.InputDevice;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.input.BatteryController;
import com.android.server.input.UEventManager;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class BatteryController {
    public static final boolean DEBUG = Log.isLoggable("BatteryController", 3);
    static final long POLLING_PERIOD_MILLIS = 10000;
    static final long USI_BATTERY_VALIDITY_DURATION_MILLIS = 3600000;
    public BatteryController$$ExternalSyntheticLambda4 mBluetoothBatteryListener;
    public final BluetoothBatteryManager mBluetoothBatteryManager;
    public final Context mContext;
    public final Handler mHandler;
    public final NativeInputManagerService mNative;
    public final UEventManager mUEventManager;
    public final Object mLock = new Object();
    public final ArrayMap mListenerRecords = new ArrayMap();
    public final ArrayMap mDeviceMonitors = new ArrayMap();
    public boolean mIsPolling = false;
    public boolean mIsInteractive = true;
    public final AnonymousClass1 mInputDeviceListener = new AnonymousClass1();

    /* renamed from: com.android.server.input.BatteryController$1, reason: invalid class name */
    public final class AnonymousClass1 implements InputManager.InputDeviceListener {
        public AnonymousClass1() {
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceAdded(int i) {
            synchronized (BatteryController.this.mLock) {
                try {
                    BatteryController batteryController = BatteryController.this;
                    batteryController.getClass();
                    if (((Boolean) batteryController.processInputDevice(i, Boolean.FALSE, new BatteryController$$ExternalSyntheticLambda0(2))).booleanValue() && !BatteryController.this.mDeviceMonitors.containsKey(Integer.valueOf(i))) {
                        BatteryController.this.mDeviceMonitors.put(Integer.valueOf(i), BatteryController.this.new UsiDeviceMonitor(i));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceChanged(int i) {
            synchronized (BatteryController.this.mLock) {
                try {
                    DeviceMonitor deviceMonitor = (DeviceMonitor) BatteryController.this.mDeviceMonitors.get(Integer.valueOf(i));
                    if (deviceMonitor == null) {
                        return;
                    }
                    deviceMonitor.onConfiguration(SystemClock.uptimeMillis());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.input.InputManager.InputDeviceListener
        public final void onInputDeviceRemoved(int i) {
        }
    }

    interface BluetoothBatteryManager {

        public interface BluetoothBatteryListener {
            void onBluetoothBatteryChanged(int i, String str, long j);
        }

        void addBatteryListener(BatteryController$$ExternalSyntheticLambda4 batteryController$$ExternalSyntheticLambda4);

        void addMetadataListener(String str, BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener);

        int getBatteryLevel(String str);

        byte[] getMetadata(int i, String str);

        void removeBatteryListener(BluetoothBatteryListener bluetoothBatteryListener);

        void removeMetadataListener(String str, BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener);
    }

    public class DeviceMonitor {
        public BluetoothDevice mBluetoothDevice;
        public BatteryController$DeviceMonitor$$ExternalSyntheticLambda2 mBluetoothMetadataListener;
        public final State mState;
        public AnonymousClass1 mUEventBatteryListener;
        public boolean mHasBattery = false;
        public long mBluetoothEventTime = 0;
        public int mBluetoothBatteryLevel = -1;
        public int mBluetoothMetadataBatteryLevel = -1;
        public int mBluetoothMetadataBatteryStatus = 1;

        public DeviceMonitor(int i) {
            this.mState = new State(i);
            configureDeviceMonitor(SystemClock.uptimeMillis());
        }

        /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda2] */
        /* JADX WARN: Type inference failed for: r4v11, types: [com.android.server.input.BatteryController$DeviceMonitor$1, com.android.server.input.UEventManager$UEventListener] */
        public final void configureDeviceMonitor(long j) {
            State state = this.mState;
            int i = ((IInputDeviceBatteryState) state).deviceId;
            boolean z = this.mHasBattery;
            boolean z2 = BatteryController.DEBUG;
            final BatteryController batteryController = BatteryController.this;
            batteryController.getClass();
            if (z != ((Boolean) batteryController.processInputDevice(i, Boolean.FALSE, new BatteryController$$ExternalSyntheticLambda0(0))).booleanValue()) {
                boolean z3 = !this.mHasBattery;
                this.mHasBattery = z3;
                if (z3) {
                    String batteryDevicePath = batteryController.mNative.getBatteryDevicePath(((IInputDeviceBatteryState) state).deviceId);
                    if (batteryDevicePath != null) {
                        final int i2 = ((IInputDeviceBatteryState) state).deviceId;
                        ?? r4 = new UEventBatteryListener() { // from class: com.android.server.input.BatteryController.DeviceMonitor.1
                            @Override // com.android.server.input.BatteryController.UEventBatteryListener
                            public final void onBatteryUEvent(long j2) {
                                BatteryController batteryController2 = BatteryController.this;
                                int i3 = i2;
                                synchronized (batteryController2.mLock) {
                                    try {
                                        DeviceMonitor deviceMonitor = (DeviceMonitor) batteryController2.mDeviceMonitors.get(Integer.valueOf(i3));
                                        if (deviceMonitor == null) {
                                            return;
                                        }
                                        deviceMonitor.onUEvent(j2);
                                    } finally {
                                    }
                                }
                            }
                        };
                        this.mUEventBatteryListener = r4;
                        StringBuilder sb = new StringBuilder("DEVPATH=");
                        if (batteryDevicePath.startsWith("/sys")) {
                            batteryDevicePath = batteryDevicePath.substring(4);
                        }
                        sb.append(batteryDevicePath);
                        String sb2 = sb.toString();
                        batteryController.mUEventManager.getClass();
                        r4.mObserver.startObserving(sb2);
                    }
                } else {
                    AnonymousClass1 anonymousClass1 = this.mUEventBatteryListener;
                    if (anonymousClass1 != null) {
                        batteryController.mUEventManager.getClass();
                        anonymousClass1.mObserver.stopObserving();
                        this.mUEventBatteryListener = null;
                    }
                }
                updateBatteryStateFromNative(j);
            }
            BluetoothDevice bluetoothDevice = BatteryController.getBluetoothDevice(batteryController.mContext, (String) batteryController.processInputDevice(i, null, new BatteryController$$ExternalSyntheticLambda0(3)));
            if (Objects.equals(this.mBluetoothDevice, bluetoothDevice)) {
                return;
            }
            if (BatteryController.DEBUG) {
                StringBuilder sb3 = new StringBuilder("Bluetooth device is now ");
                sb3.append(bluetoothDevice != null ? "" : "not");
                sb3.append(" present for deviceId ");
                sb3.append(i);
                Slog.d("BatteryController", sb3.toString());
            }
            this.mBluetoothBatteryLevel = -1;
            stopBluetoothMetadataMonitoring();
            this.mBluetoothDevice = bluetoothDevice;
            batteryController.updateBluetoothBatteryMonitoring();
            BluetoothDevice bluetoothDevice2 = this.mBluetoothDevice;
            if (bluetoothDevice2 != null) {
                String address = bluetoothDevice2.getAddress();
                BluetoothBatteryManager bluetoothBatteryManager = batteryController.mBluetoothBatteryManager;
                this.mBluetoothBatteryLevel = bluetoothBatteryManager.getBatteryLevel(address);
                Objects.requireNonNull(this.mBluetoothDevice);
                this.mBluetoothMetadataListener = new BluetoothAdapter.OnMetadataChangedListener() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda2
                    public final void onMetadataChanged(BluetoothDevice bluetoothDevice3, final int i3, final byte[] bArr) {
                        BatteryController batteryController2 = BatteryController.this;
                        synchronized (batteryController2.mLock) {
                            try {
                                final BatteryController.DeviceMonitor deviceMonitor = (BatteryController.DeviceMonitor) BatteryController.findIf(batteryController2.mDeviceMonitors, new BatteryController$$ExternalSyntheticLambda9(1, bluetoothDevice3));
                                if (deviceMonitor != null) {
                                    deviceMonitor.processChangesAndNotify(SystemClock.uptimeMillis(), new Consumer() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            BatteryController.DeviceMonitor deviceMonitor2 = BatteryController.DeviceMonitor.this;
                                            int i4 = i3;
                                            byte[] bArr2 = bArr;
                                            deviceMonitor2.getClass();
                                            deviceMonitor2.updateBluetoothMetadataState(((Long) obj).longValue(), bArr2, i4);
                                        }
                                    });
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
                bluetoothBatteryManager.addMetadataListener(this.mBluetoothDevice.getAddress(), this.mBluetoothMetadataListener);
                updateBluetoothMetadataState(j, bluetoothBatteryManager.getMetadata(18, this.mBluetoothDevice.getAddress()), 18);
                updateBluetoothMetadataState(j, bluetoothBatteryManager.getMetadata(19, this.mBluetoothDevice.getAddress()), 19);
            }
        }

        public State getBatteryStateForReporting() {
            return (State) Objects.requireNonNullElseGet(resolveBluetoothBatteryState(), new BatteryController$DeviceMonitor$$ExternalSyntheticLambda1(this, 0));
        }

        public void onConfiguration(long j) {
            processChangesAndNotify(j, new BatteryController$DeviceMonitor$$ExternalSyntheticLambda0(this, 1));
        }

        public void onPoll(long j) {
            processChangesAndNotify(j, new BatteryController$DeviceMonitor$$ExternalSyntheticLambda0(this, 0));
        }

        public void onStylusGestureStarted(long j) {
        }

        public void onTimeout(long j) {
        }

        public void onUEvent(long j) {
            processChangesAndNotify(j, new BatteryController$DeviceMonitor$$ExternalSyntheticLambda0(this, 0));
        }

        public final void processChangesAndNotify(long j, Consumer consumer) {
            State batteryStateForReporting = getBatteryStateForReporting();
            consumer.accept(Long.valueOf(j));
            final State batteryStateForReporting2 = getBatteryStateForReporting();
            long j2 = ((IInputDeviceBatteryState) batteryStateForReporting).updateTime;
            ((IInputDeviceBatteryState) batteryStateForReporting).updateTime = ((IInputDeviceBatteryState) batteryStateForReporting2).updateTime;
            boolean equals = batteryStateForReporting.equals(batteryStateForReporting2);
            ((IInputDeviceBatteryState) batteryStateForReporting).updateTime = j2;
            if (equals) {
                return;
            }
            BatteryController batteryController = BatteryController.this;
            synchronized (batteryController.mLock) {
                try {
                    if (BatteryController.DEBUG) {
                        Slog.d("BatteryController", "Notifying all listeners of battery state: " + batteryStateForReporting2);
                    }
                    batteryController.mListenerRecords.forEach(new BiConsumer() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda10
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            BatteryController.State state = BatteryController.State.this;
                            BatteryController.ListenerRecord listenerRecord = (BatteryController.ListenerRecord) obj2;
                            boolean z = BatteryController.DEBUG;
                            if (((ArraySet) listenerRecord.mMonitoredDevices).contains(Integer.valueOf(((IInputDeviceBatteryState) state).deviceId))) {
                                BatteryController.notifyBatteryListener(listenerRecord, state);
                            }
                        }
                    });
                } finally {
                }
            }
        }

        public final State resolveBluetoothBatteryState() {
            int i = this.mBluetoothMetadataBatteryLevel;
            if ((i < 0 || i > 100) && ((i = this.mBluetoothBatteryLevel) < 0 || i > 100)) {
                return null;
            }
            return new State(((IInputDeviceBatteryState) this.mState).deviceId, this.mBluetoothEventTime, true, this.mBluetoothMetadataBatteryStatus, i / 100.0f);
        }

        public final void stopBluetoothMetadataMonitoring() {
            if (this.mBluetoothMetadataListener == null) {
                return;
            }
            Objects.requireNonNull(this.mBluetoothDevice);
            BatteryController.this.mBluetoothBatteryManager.removeMetadataListener(this.mBluetoothDevice.getAddress(), this.mBluetoothMetadataListener);
            this.mBluetoothMetadataListener = null;
            this.mBluetoothMetadataBatteryLevel = -1;
            this.mBluetoothMetadataBatteryStatus = 1;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("DeviceId=");
            State state = this.mState;
            sb.append(((IInputDeviceBatteryState) state).deviceId);
            sb.append(", Name='");
            int i = ((IInputDeviceBatteryState) state).deviceId;
            boolean z = BatteryController.DEBUG;
            BatteryController batteryController = BatteryController.this;
            batteryController.getClass();
            sb.append((String) batteryController.processInputDevice(i, "<none>", new BatteryController$$ExternalSyntheticLambda0(1)));
            sb.append("', NativeBattery=");
            sb.append(state);
            sb.append(", UEventListener=");
            sb.append(this.mUEventBatteryListener != null ? "added" : "none");
            sb.append(", BluetoothState=");
            sb.append(resolveBluetoothBatteryState());
            return sb.toString();
        }

        public final void updateBatteryStateFromNative(long j) {
            State state = this.mState;
            int i = ((IInputDeviceBatteryState) state).deviceId;
            boolean z = this.mHasBattery;
            boolean z2 = BatteryController.DEBUG;
            State queryBatteryStateFromNative = BatteryController.this.queryBatteryStateFromNative(i, j, z);
            long j2 = ((IInputDeviceBatteryState) state).updateTime;
            ((IInputDeviceBatteryState) state).updateTime = ((IInputDeviceBatteryState) queryBatteryStateFromNative).updateTime;
            boolean equals = state.equals(queryBatteryStateFromNative);
            ((IInputDeviceBatteryState) state).updateTime = j2;
            if (equals) {
                return;
            }
            int i2 = ((IInputDeviceBatteryState) queryBatteryStateFromNative).deviceId;
            long j3 = ((IInputDeviceBatteryState) queryBatteryStateFromNative).updateTime;
            boolean z3 = ((IInputDeviceBatteryState) queryBatteryStateFromNative).isPresent;
            int i3 = ((IInputDeviceBatteryState) queryBatteryStateFromNative).status;
            float f = ((IInputDeviceBatteryState) queryBatteryStateFromNative).capacity;
            ((IInputDeviceBatteryState) state).deviceId = i2;
            ((IInputDeviceBatteryState) state).updateTime = j3;
            ((IInputDeviceBatteryState) state).isPresent = z3;
            ((IInputDeviceBatteryState) state).status = i3;
            ((IInputDeviceBatteryState) state).capacity = f;
        }

        public final void updateBluetoothMetadataState(long j, byte[] bArr, int i) {
            if (i != 18) {
                if (i != 19) {
                    return;
                }
                this.mBluetoothEventTime = j;
                if (bArr != null) {
                    this.mBluetoothMetadataBatteryStatus = Boolean.parseBoolean(new String(bArr)) ? 2 : 3;
                    return;
                } else {
                    this.mBluetoothMetadataBatteryStatus = 1;
                    return;
                }
            }
            this.mBluetoothEventTime = j;
            this.mBluetoothMetadataBatteryLevel = -1;
            if (bArr != null) {
                try {
                    this.mBluetoothMetadataBatteryLevel = Integer.parseInt(new String(bArr));
                } catch (NumberFormatException unused) {
                    boolean z = BatteryController.DEBUG;
                    Slog.wtf("BatteryController", "Failed to parse bluetooth METADATA_MAIN_BATTERY with value '" + new String(bArr) + "' for device " + this.mBluetoothDevice);
                }
            }
        }
    }

    public final class ListenerRecord {
        public final BatteryController$ListenerRecord$$ExternalSyntheticLambda0 mDeathRecipient;
        public final IInputDeviceBatteryListener mListener;
        public final Set mMonitoredDevices = new ArraySet();
        public final int mPid;

        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.input.BatteryController$ListenerRecord$$ExternalSyntheticLambda0] */
        public ListenerRecord(final int i, IInputDeviceBatteryListener iInputDeviceBatteryListener) {
            this.mPid = i;
            this.mListener = iInputDeviceBatteryListener;
            this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.input.BatteryController$ListenerRecord$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    BatteryController.ListenerRecord listenerRecord = BatteryController.ListenerRecord.this;
                    int i2 = i;
                    BatteryController batteryController = BatteryController.this;
                    synchronized (batteryController.mLock) {
                        try {
                            BatteryController.ListenerRecord listenerRecord2 = (BatteryController.ListenerRecord) batteryController.mListenerRecords.get(Integer.valueOf(i2));
                            if (listenerRecord2 == null) {
                                return;
                            }
                            if (BatteryController.DEBUG) {
                                Slog.d("BatteryController", "Removing battery listener for pid " + i2 + " because the process died");
                            }
                            Iterator it = ((ArraySet) listenerRecord2.mMonitoredDevices).iterator();
                            while (it.hasNext()) {
                                batteryController.unregisterRecordLocked(listenerRecord2, ((Integer) it.next()).intValue());
                            }
                        } finally {
                        }
                    }
                }
            };
        }

        public final String toString() {
            return "pid=" + this.mPid + ", monitored devices=" + Arrays.toString(((ArraySet) this.mMonitoredDevices).toArray());
        }
    }

    public final class LocalBluetoothBatteryManager implements BluetoothBatteryManager {
        public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.input.BatteryController.LocalBluetoothBatteryManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                BluetoothDevice bluetoothDevice;
                if ("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED".equals(intent.getAction()) && (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class)) != null) {
                    int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BATTERY_LEVEL", -1);
                    synchronized (LocalBluetoothBatteryManager.this.mBroadcastReceiver) {
                        try {
                            if (LocalBluetoothBatteryManager.this.mRegisteredListener != null) {
                                LocalBluetoothBatteryManager.this.mRegisteredListener.onBluetoothBatteryChanged(intExtra, bluetoothDevice.getAddress(), SystemClock.uptimeMillis());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        };
        public final Context mContext;
        public final Executor mExecutor;
        public BluetoothBatteryManager.BluetoothBatteryListener mRegisteredListener;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.input.BatteryController$LocalBluetoothBatteryManager$1] */
        public LocalBluetoothBatteryManager(Context context, Looper looper) {
            this.mContext = context;
            this.mExecutor = new HandlerExecutor(new Handler(looper));
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public final void addBatteryListener(BatteryController$$ExternalSyntheticLambda4 batteryController$$ExternalSyntheticLambda4) {
            synchronized (this.mBroadcastReceiver) {
                try {
                    if (this.mRegisteredListener != null) {
                        throw new IllegalStateException("Only one bluetooth battery listener can be registered at once.");
                    }
                    this.mRegisteredListener = batteryController$$ExternalSyntheticLambda4;
                    this.mContext.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED"));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public final void addMetadataListener(String str, BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener) {
            BluetoothManager bluetoothManager = (BluetoothManager) this.mContext.getSystemService(BluetoothManager.class);
            Objects.requireNonNull(bluetoothManager);
            bluetoothManager.getAdapter().addOnMetadataChangedListener(BatteryController.getBluetoothDevice(this.mContext, str), this.mExecutor, onMetadataChangedListener);
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public final int getBatteryLevel(String str) {
            return BatteryController.getBluetoothDevice(this.mContext, str).getBatteryLevel();
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public final byte[] getMetadata(int i, String str) {
            return BatteryController.getBluetoothDevice(this.mContext, str).getMetadata(i);
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public final void removeBatteryListener(BluetoothBatteryManager.BluetoothBatteryListener bluetoothBatteryListener) {
            synchronized (this.mBroadcastReceiver) {
                try {
                    if (!bluetoothBatteryListener.equals(this.mRegisteredListener)) {
                        throw new IllegalStateException("Listener is not registered.");
                    }
                    this.mRegisteredListener = null;
                    this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.input.BatteryController.BluetoothBatteryManager
        public final void removeMetadataListener(String str, BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener) {
            BluetoothManager bluetoothManager = (BluetoothManager) this.mContext.getSystemService(BluetoothManager.class);
            Objects.requireNonNull(bluetoothManager);
            bluetoothManager.getAdapter().removeOnMetadataChangedListener(BatteryController.getBluetoothDevice(this.mContext, str), onMetadataChangedListener);
        }
    }

    public final class State extends IInputDeviceBatteryState {
        public State(int i) {
            ((IInputDeviceBatteryState) this).deviceId = i;
            ((IInputDeviceBatteryState) this).updateTime = 0L;
            ((IInputDeviceBatteryState) this).isPresent = false;
            ((IInputDeviceBatteryState) this).status = 1;
            ((IInputDeviceBatteryState) this).capacity = Float.NaN;
        }

        public State(int i, long j, boolean z, int i2, float f) {
            ((IInputDeviceBatteryState) this).deviceId = i;
            ((IInputDeviceBatteryState) this).updateTime = j;
            ((IInputDeviceBatteryState) this).isPresent = z;
            ((IInputDeviceBatteryState) this).status = i2;
            ((IInputDeviceBatteryState) this).capacity = f;
        }

        public State(IInputDeviceBatteryState iInputDeviceBatteryState) {
            int i = iInputDeviceBatteryState.deviceId;
            long j = iInputDeviceBatteryState.updateTime;
            boolean z = iInputDeviceBatteryState.isPresent;
            int i2 = iInputDeviceBatteryState.status;
            float f = iInputDeviceBatteryState.capacity;
            ((IInputDeviceBatteryState) this).deviceId = i;
            ((IInputDeviceBatteryState) this).updateTime = j;
            ((IInputDeviceBatteryState) this).isPresent = z;
            ((IInputDeviceBatteryState) this).status = i2;
            ((IInputDeviceBatteryState) this).capacity = f;
        }

        public final String toString() {
            if (!((IInputDeviceBatteryState) this).isPresent) {
                return "State{<not present>}";
            }
            return "State{time=" + ((IInputDeviceBatteryState) this).updateTime + ", isPresent=" + ((IInputDeviceBatteryState) this).isPresent + ", status=" + ((IInputDeviceBatteryState) this).status + ", capacity=" + ((IInputDeviceBatteryState) this).capacity + "}";
        }
    }

    abstract class UEventBatteryListener extends UEventManager.UEventListener {
        public abstract void onBatteryUEvent(long j);

        @Override // com.android.server.input.UEventManager.UEventListener
        public final void onUEvent(UEventObserver.UEvent uEvent) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (BatteryController.DEBUG) {
                Slog.d("BatteryController", "UEventListener: Received UEvent: " + uEvent + " eventTime: " + uptimeMillis);
            }
            if ("CHANGE".equalsIgnoreCase(uEvent.get("ACTION")) && "POWER_SUPPLY".equalsIgnoreCase(uEvent.get("SUBSYSTEM"))) {
                onBatteryUEvent(uptimeMillis);
            }
        }
    }

    public final class UsiDeviceMonitor extends DeviceMonitor {
        public BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 mValidityTimeoutCallback;

        public UsiDeviceMonitor(int i) {
            super(i);
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final State getBatteryStateForReporting() {
            return (State) Objects.requireNonNullElseGet(resolveBluetoothBatteryState(), new BatteryController$DeviceMonitor$$ExternalSyntheticLambda1(this, 1));
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2] */
        public final void markUsiBatteryValid() {
            BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 = this.mValidityTimeoutCallback;
            BatteryController batteryController = BatteryController.this;
            if (batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2 != null) {
                batteryController.mHandler.removeCallbacks(batteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2);
            } else {
                final int i = ((IInputDeviceBatteryState) this.mState).deviceId;
                this.mValidityTimeoutCallback = new Runnable() { // from class: com.android.server.input.BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryController.UsiDeviceMonitor usiDeviceMonitor = BatteryController.UsiDeviceMonitor.this;
                        int i2 = i;
                        BatteryController batteryController2 = BatteryController.this;
                        synchronized (batteryController2.mLock) {
                            try {
                                BatteryController.DeviceMonitor deviceMonitor = (BatteryController.DeviceMonitor) batteryController2.mDeviceMonitors.get(Integer.valueOf(i2));
                                if (deviceMonitor == null) {
                                    return;
                                }
                                deviceMonitor.onTimeout(SystemClock.uptimeMillis());
                            } finally {
                            }
                        }
                    }
                };
            }
            batteryController.mHandler.postDelayed(this.mValidityTimeoutCallback, 3600000L);
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final void onConfiguration(long j) {
            super.onConfiguration(j);
            if (!this.mHasBattery) {
                throw new IllegalStateException("UsiDeviceMonitor: USI devices are always expected to report a valid battery, but no battery was detected!");
            }
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final void onPoll(long j) {
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final void onStylusGestureStarted(long j) {
            processChangesAndNotify(j, new BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda0(this, 0));
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final void onTimeout(long j) {
            processChangesAndNotify(j, new BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final void onUEvent(long j) {
            processChangesAndNotify(j, new BatteryController$UsiDeviceMonitor$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.server.input.BatteryController.DeviceMonitor
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append(", UsiStateIsValid=");
            sb.append(this.mValidityTimeoutCallback != null);
            return sb.toString();
        }
    }

    public static void $r8$lambda$CQleDsNEE174VGVNEyfPVkU2Ab0(BatteryController batteryController) {
        synchronized (batteryController.mLock) {
            try {
                if (batteryController.mIsPolling) {
                    final long uptimeMillis = SystemClock.uptimeMillis();
                    batteryController.mDeviceMonitors.forEach(new BiConsumer() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda8
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            long j = uptimeMillis;
                            boolean z = BatteryController.DEBUG;
                            ((BatteryController.DeviceMonitor) obj2).onPoll(j);
                        }
                    });
                    batteryController.mHandler.postDelayed(new BatteryController$$ExternalSyntheticLambda2(batteryController), POLLING_PERIOD_MILLIS);
                }
            } finally {
            }
        }
    }

    public BatteryController(Context context, NativeInputManagerService nativeInputManagerService, Looper looper, UEventManager uEventManager, BluetoothBatteryManager bluetoothBatteryManager) {
        this.mContext = context;
        this.mNative = nativeInputManagerService;
        this.mHandler = new Handler(looper);
        this.mUEventManager = uEventManager;
        this.mBluetoothBatteryManager = bluetoothBatteryManager;
    }

    public static Object findIf(ArrayMap arrayMap, Predicate predicate) {
        for (int i = 0; i < arrayMap.size(); i++) {
            Object valueAt = arrayMap.valueAt(i);
            if (predicate.test(valueAt)) {
                return valueAt;
            }
        }
        return null;
    }

    public static BluetoothDevice getBluetoothDevice(Context context, String str) {
        if (str == null) {
            return null;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(BluetoothManager.class);
        Objects.requireNonNull(bluetoothManager);
        return bluetoothManager.getAdapter().getRemoteDevice(str);
    }

    public static void notifyBatteryListener(ListenerRecord listenerRecord, State state) {
        try {
            listenerRecord.mListener.onBatteryStateChanged(state);
        } catch (RemoteException e) {
            Slog.e("BatteryController", "Failed to notify listener", e);
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("Notified battery listener from pid ");
            sb.append(listenerRecord.mPid);
            sb.append(" of state of deviceId ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, ((IInputDeviceBatteryState) state).deviceId, "BatteryController");
        }
    }

    public final void monitor() {
        synchronized (this.mLock) {
        }
    }

    public final Object processInputDevice(int i, Object obj, Function function) {
        InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        Objects.requireNonNull(inputManager);
        InputDevice inputDevice = inputManager.getInputDevice(i);
        return inputDevice == null ? obj : function.apply(inputDevice);
    }

    public final State queryBatteryStateFromNative(int i, long j, boolean z) {
        return new State(i, j, z, z ? this.mNative.getBatteryStatus(i) : 1, z ? r8.getBatteryCapacity(i) / 100.0f : Float.NaN);
    }

    public final void unregisterRecordLocked(ListenerRecord listenerRecord, int i) {
        boolean remove = ((ArraySet) listenerRecord.mMonitoredDevices).remove(Integer.valueOf(i));
        int i2 = listenerRecord.mPid;
        if (!remove) {
            throw new IllegalStateException(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Cannot unregister battery callback: The deviceId ", " is not being monitored by pid "));
        }
        int i3 = 0;
        while (true) {
            if (i3 < this.mListenerRecords.size()) {
                if (((ArraySet) ((ListenerRecord) this.mListenerRecords.valueAt(i3)).mMonitoredDevices).contains(Integer.valueOf(i))) {
                    break;
                } else {
                    i3++;
                }
            } else {
                DeviceMonitor deviceMonitor = (DeviceMonitor) this.mDeviceMonitors.get(Integer.valueOf(i));
                Objects.requireNonNull(deviceMonitor, "Maps are out of sync: Cannot find device state for deviceId " + i);
                if (!(deviceMonitor instanceof UsiDeviceMonitor)) {
                    DeviceMonitor.AnonymousClass1 anonymousClass1 = deviceMonitor.mUEventBatteryListener;
                    BatteryController batteryController = BatteryController.this;
                    if (anonymousClass1 != null) {
                        batteryController.mUEventManager.getClass();
                        anonymousClass1.mObserver.stopObserving();
                        deviceMonitor.mUEventBatteryListener = null;
                    }
                    deviceMonitor.stopBluetoothMetadataMonitoring();
                    deviceMonitor.mBluetoothDevice = null;
                    batteryController.updateBluetoothBatteryMonitoring();
                    this.mDeviceMonitors.remove(Integer.valueOf(i));
                }
            }
        }
        if (((ArraySet) listenerRecord.mMonitoredDevices).isEmpty()) {
            listenerRecord.mListener.asBinder().unlinkToDeath(listenerRecord.mDeathRecipient, 0);
            this.mListenerRecords.remove(Integer.valueOf(i2));
            if (DEBUG) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "Battery listener removed for pid ", "BatteryController");
            }
        }
        updatePollingLocked(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.server.input.BatteryController$$ExternalSyntheticLambda4] */
    public final void updateBluetoothBatteryMonitoring() {
        synchronized (this.mLock) {
            try {
                if (findIf(this.mDeviceMonitors, new BatteryController$$ExternalSyntheticLambda1(1)) != null) {
                    if (this.mBluetoothBatteryListener == null) {
                        if (DEBUG) {
                            Slog.d("BatteryController", "Registering bluetooth battery listener");
                        }
                        ?? r1 = new BluetoothBatteryManager.BluetoothBatteryListener() { // from class: com.android.server.input.BatteryController$$ExternalSyntheticLambda4
                            @Override // com.android.server.input.BatteryController.BluetoothBatteryManager.BluetoothBatteryListener
                            public final void onBluetoothBatteryChanged(final int i, String str, long j) {
                                BatteryController batteryController = BatteryController.this;
                                synchronized (batteryController.mLock) {
                                    final BatteryController.DeviceMonitor deviceMonitor = (BatteryController.DeviceMonitor) BatteryController.findIf(batteryController.mDeviceMonitors, new BatteryController$$ExternalSyntheticLambda9(0, str));
                                    if (deviceMonitor != null) {
                                        deviceMonitor.processChangesAndNotify(j, new Consumer() { // from class: com.android.server.input.BatteryController$DeviceMonitor$$ExternalSyntheticLambda4
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                BatteryController.DeviceMonitor deviceMonitor2 = BatteryController.DeviceMonitor.this;
                                                deviceMonitor2.mBluetoothBatteryLevel = i;
                                                deviceMonitor2.mBluetoothEventTime = ((Long) obj).longValue();
                                            }
                                        });
                                    }
                                }
                            }
                        };
                        this.mBluetoothBatteryListener = r1;
                        this.mBluetoothBatteryManager.addBatteryListener(r1);
                    }
                } else if (this.mBluetoothBatteryListener != null) {
                    if (DEBUG) {
                        Slog.d("BatteryController", "Unregistering bluetooth battery listener");
                    }
                    this.mBluetoothBatteryManager.removeBatteryListener(this.mBluetoothBatteryListener);
                    this.mBluetoothBatteryListener = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updatePollingLocked(boolean z) {
        boolean z2 = this.mIsInteractive;
        Handler handler = this.mHandler;
        if (!z2 || findIf(this.mDeviceMonitors, new BatteryController$$ExternalSyntheticLambda1(0)) == null) {
            this.mIsPolling = false;
            handler.removeCallbacks(new BatteryController$$ExternalSyntheticLambda2(this));
        } else {
            if (this.mIsPolling) {
                return;
            }
            this.mIsPolling = true;
            handler.postDelayed(new BatteryController$$ExternalSyntheticLambda2(this), z ? POLLING_PERIOD_MILLIS : 0L);
        }
    }
}
