package com.android.server.midi;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothUuid;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.MediaMetrics;
import android.media.midi.Flags;
import android.media.midi.IBluetoothMidiService;
import android.media.midi.IMidiDeviceListener;
import android.media.midi.IMidiDeviceOpenCallback;
import android.media.midi.IMidiDeviceServer;
import android.media.midi.IMidiManager;
import android.media.midi.MidiDevice;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceStatus;
import android.media.midi.MidiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.EventLog;
import android.util.Log;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class MidiService extends IMidiManager.Stub {
    public int mBluetoothServiceUid;
    public final Context mContext;
    public final HashSet mNonMidiUUIDs;
    public final PackageManager mPackageManager;
    public final AnonymousClass1 mPackageMonitor;
    public final UserManager mUserManager;
    public static final UUID MIDI_SERVICE = UUID.fromString("03B80E5A-EDE8-4B33-A751-6CE34EC4C700");
    public static final MidiDeviceInfo[] EMPTY_DEVICE_INFO_ARRAY = new MidiDeviceInfo[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public final HashMap mClients = new HashMap();
    public final HashMap mDevicesByInfo = new HashMap();
    public final HashMap mBluetoothDevices = new HashMap();
    public final HashMap mBleMidiDeviceMap = new HashMap();
    public final HashMap mDevicesByServer = new HashMap();
    public int mNextDeviceId = 1;
    public final Object mUsbMidiLock = new Object();
    public final HashMap mUsbMidiLegacyDeviceOpenCount = new HashMap();
    public final HashSet mUsbMidiUniversalDeviceInUse = new HashSet();

    public final class Client implements IBinder.DeathRecipient {
        public final IBinder mToken;
        public final HashMap mListeners = new HashMap();
        public final HashMap mDeviceConnections = new HashMap();
        public final int mUid = Binder.getCallingUid();
        public final int mPid = Binder.getCallingPid();

        public Client(IBinder iBinder) {
            this.mToken = iBinder;
        }

        public final void addDeviceConnection(final Device device, IMidiDeviceOpenCallback iMidiDeviceOpenCallback, int i) {
            Intent intent;
            Log.d("MidiService.Client", "addDeviceConnection() device:" + device + " userId:" + i);
            if (this.mDeviceConnections.size() >= 64) {
                Log.i("MidiService.Client", "too many MIDI connections for UID = " + this.mUid);
                throw new SecurityException("too many MIDI connections for UID = " + this.mUid);
            }
            DeviceConnection deviceConnection = new DeviceConnection(device, this, iMidiDeviceOpenCallback);
            this.mDeviceConnections.put(deviceConnection.mToken, deviceConnection);
            Log.d("MidiService.Device", "addDeviceConnection() [A] connection:" + deviceConnection);
            synchronized (device.mDeviceConnections) {
                try {
                    device.mDeviceConnectionsAdded.incrementAndGet();
                    if (device.mPreviousCounterInstant == null) {
                        device.mPreviousCounterInstant = Instant.now();
                    }
                    Log.d("MidiService.Device", "  mServer:" + device.mServer);
                    if (device.mServer != null) {
                        Log.i("MidiService.Device", "++++ A");
                        device.mDeviceConnections.add(deviceConnection);
                        deviceConnection.notifyClient(device.mServer);
                    } else if (device.mServiceConnection != null || (device.mServiceInfo == null && device.mBluetoothDevice == null)) {
                        Log.e("MidiService.Device", "No way to connect to device in addDeviceConnection");
                        deviceConnection.notifyClient(null);
                    } else {
                        Log.i("MidiService.Device", "++++ B");
                        device.mDeviceConnections.add(deviceConnection);
                        device.mServiceConnection = new ServiceConnection() { // from class: com.android.server.midi.MidiService.Device.1
                            @Override // android.content.ServiceConnection
                            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                                IMidiDeviceServer asInterface;
                                Log.i("MidiService.Device", "++++ onServiceConnected() mBluetoothDevice:" + Device.this.mBluetoothDevice);
                                if (Device.this.mBluetoothDevice != null) {
                                    IBluetoothMidiService asInterface2 = IBluetoothMidiService.Stub.asInterface(iBinder);
                                    Log.i("MidiService.Device", "++++ mBluetoothMidiService:" + asInterface2);
                                    if (asInterface2 != null) {
                                        try {
                                            asInterface = IMidiDeviceServer.Stub.asInterface(asInterface2.addBluetoothDevice(Device.this.mBluetoothDevice));
                                        } catch (RemoteException e) {
                                            Log.e("MidiService.Device", "Could not call addBluetoothDevice()", e);
                                        }
                                    }
                                    asInterface = null;
                                } else {
                                    asInterface = IMidiDeviceServer.Stub.asInterface(iBinder);
                                }
                                Device.this.setDeviceServer(asInterface);
                            }

                            @Override // android.content.ServiceConnection
                            public final void onServiceDisconnected(ComponentName componentName) {
                                Device.this.setDeviceServer(null);
                                Device.this.mServiceConnection = null;
                            }
                        };
                        if (device.mBluetoothDevice != null) {
                            intent = new Intent("android.media.midi.BluetoothMidiService");
                            intent.setComponent(new ComponentName("com.android.bluetoothmidiservice", "com.android.bluetoothmidiservice.BluetoothMidiService"));
                        } else {
                            MidiService midiService = MidiService.this;
                            MidiDeviceInfo midiDeviceInfo = device.mDeviceInfo;
                            midiService.getClass();
                            if (MidiService.isUmpDevice(midiDeviceInfo)) {
                                intent = new Intent("android.media.midi.MidiUmpDeviceService");
                                ServiceInfo serviceInfo = device.mServiceInfo;
                                intent.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
                            } else {
                                intent = new Intent("android.media.midi.MidiDeviceService");
                                ServiceInfo serviceInfo2 = device.mServiceInfo;
                                intent.setComponent(new ComponentName(serviceInfo2.packageName, serviceInfo2.name));
                            }
                        }
                        if (!MidiService.this.mContext.bindServiceAsUser(intent, device.mServiceConnection, 1, UserHandle.of(device.mUserId))) {
                            Log.e("MidiService.Device", "Unable to bind service: " + intent);
                            device.setDeviceServer(null);
                            device.mServiceConnection = null;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.d("MidiService.Client", "Client died: " + this);
            close();
        }

        public final void close() {
            synchronized (MidiService.this.mClients) {
                MidiService.this.mClients.remove(this.mToken);
                this.mToken.unlinkToDeath(this, 0);
            }
            for (DeviceConnection deviceConnection : this.mDeviceConnections.values()) {
                deviceConnection.mDevice.removeDeviceConnection(deviceConnection);
            }
        }

        public final void deviceAdded(Device device) {
            Log.d("MidiService.Client", "deviceAdded() " + device.mUserId + " userId:" + UserHandle.getUserId(this.mUid));
            if (device.isUidAllowed(this.mUid) && device.isUserIdAllowed(UserHandle.getUserId(this.mUid))) {
                MidiDeviceInfo midiDeviceInfo = device.mDeviceInfo;
                try {
                    Iterator it = this.mListeners.values().iterator();
                    while (it.hasNext()) {
                        ((IMidiDeviceListener) it.next()).onDeviceAdded(midiDeviceInfo);
                    }
                } catch (RemoteException e) {
                    Log.e("MidiService.Client", "remote exception", e);
                }
            }
        }

        public final void deviceRemoved(Device device) {
            Log.d("MidiService.Client", "deviceRemoved() " + device.mUserId + " userId:" + UserHandle.getUserId(this.mUid));
            if (device.isUidAllowed(this.mUid) && device.isUserIdAllowed(UserHandle.getUserId(this.mUid))) {
                MidiDeviceInfo midiDeviceInfo = device.mDeviceInfo;
                try {
                    Iterator it = this.mListeners.values().iterator();
                    while (it.hasNext()) {
                        ((IMidiDeviceListener) it.next()).onDeviceRemoved(midiDeviceInfo);
                    }
                } catch (RemoteException e) {
                    Log.e("MidiService.Client", "remote exception", e);
                }
            }
        }

        public final void deviceStatusChanged(Device device, MidiDeviceStatus midiDeviceStatus) {
            Log.d("MidiService.Client", "deviceStatusChanged() " + device.mUserId + " userId:" + UserHandle.getUserId(this.mUid));
            if (device.isUidAllowed(this.mUid) && device.isUserIdAllowed(UserHandle.getUserId(this.mUid))) {
                try {
                    Iterator it = this.mListeners.values().iterator();
                    while (it.hasNext()) {
                        ((IMidiDeviceListener) it.next()).onDeviceStatusChanged(midiDeviceStatus);
                    }
                } catch (RemoteException e) {
                    Log.e("MidiService.Client", "remote exception", e);
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Client: UID: ");
            sb.append(this.mUid);
            sb.append(" PID: ");
            sb.append(this.mPid);
            sb.append(" listener count: ");
            sb.append(this.mListeners.size());
            sb.append(" Device Connections:");
            for (DeviceConnection deviceConnection : this.mDeviceConnections.values()) {
                sb.append(" <device ");
                sb.append(deviceConnection.mDevice.mDeviceInfo.getId());
                sb.append(">");
            }
            return sb.toString();
        }
    }

    public final class Device implements IBinder.DeathRecipient {
        public final BluetoothDevice mBluetoothDevice;
        public final ArrayList mDeviceConnections;
        public final AtomicInteger mDeviceConnectionsAdded;
        public final AtomicInteger mDeviceConnectionsRemoved;
        public MidiDeviceInfo mDeviceInfo;
        public MidiDeviceStatus mDeviceStatus;
        public Instant mPreviousCounterInstant;
        public IMidiDeviceServer mServer;
        public ServiceConnection mServiceConnection;
        public final ServiceInfo mServiceInfo;
        public final AtomicInteger mTotalInputBytes;
        public final AtomicInteger mTotalOutputBytes;
        public final AtomicLong mTotalTimeConnectedNs;
        public final int mUid;
        public final int mUserId;

        public Device(BluetoothDevice bluetoothDevice) {
            this.mDeviceConnections = new ArrayList();
            this.mDeviceConnectionsAdded = new AtomicInteger();
            this.mDeviceConnectionsRemoved = new AtomicInteger();
            this.mTotalTimeConnectedNs = new AtomicLong();
            this.mPreviousCounterInstant = null;
            this.mTotalInputBytes = new AtomicInteger();
            this.mTotalOutputBytes = new AtomicInteger();
            this.mBluetoothDevice = bluetoothDevice;
            this.mServiceInfo = null;
            int i = MidiService.this.mBluetoothServiceUid;
            this.mUid = i;
            this.mUserId = UserHandle.getUserId(i);
        }

        public Device(IMidiDeviceServer iMidiDeviceServer, MidiDeviceInfo midiDeviceInfo, ServiceInfo serviceInfo, int i, int i2) {
            this.mDeviceConnections = new ArrayList();
            this.mDeviceConnectionsAdded = new AtomicInteger();
            this.mDeviceConnectionsRemoved = new AtomicInteger();
            this.mTotalTimeConnectedNs = new AtomicLong();
            this.mPreviousCounterInstant = null;
            this.mTotalInputBytes = new AtomicInteger();
            this.mTotalOutputBytes = new AtomicInteger();
            this.mDeviceInfo = midiDeviceInfo;
            this.mServiceInfo = serviceInfo;
            this.mUid = i;
            this.mUserId = i2;
            this.mBluetoothDevice = (BluetoothDevice) midiDeviceInfo.getProperties().getParcelable("bluetooth_device", BluetoothDevice.class);
            setDeviceServer(iMidiDeviceServer);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.d("MidiService.Device", "Device died: " + this);
            synchronized (MidiService.this.mDevicesByInfo) {
                closeLocked();
            }
        }

        public final void closeLocked() {
            synchronized (this.mDeviceConnections) {
                try {
                    Iterator it = this.mDeviceConnections.iterator();
                    while (it.hasNext()) {
                        DeviceConnection deviceConnection = (DeviceConnection) it.next();
                        if (deviceConnection.mDevice.mDeviceInfo.getType() == 1) {
                            synchronized (MidiService.this.mUsbMidiLock) {
                                MidiService.this.removeUsbMidiDeviceLocked(deviceConnection.mDevice.mDeviceInfo);
                            }
                        }
                        Client client = deviceConnection.mClient;
                        client.mDeviceConnections.remove(deviceConnection.mToken);
                        if (client.mListeners.size() == 0 && client.mDeviceConnections.size() == 0) {
                            client.close();
                        }
                    }
                    this.mDeviceConnections.clear();
                    if (this.mPreviousCounterInstant != null) {
                        Instant now = Instant.now();
                        this.mTotalTimeConnectedNs.addAndGet(Duration.between(this.mPreviousCounterInstant, now).toNanos());
                        this.mPreviousCounterInstant = now;
                    }
                    logMetrics(true);
                } catch (Throwable th) {
                    throw th;
                }
            }
            setDeviceServer(null);
            if (this.mServiceInfo == null) {
                MidiService.this.removeDeviceLocked(this);
            } else {
                this.mDeviceStatus = new MidiDeviceStatus(this.mDeviceInfo);
            }
            BluetoothDevice bluetoothDevice = this.mBluetoothDevice;
            if (bluetoothDevice != null) {
                MidiService.this.mBluetoothDevices.remove(bluetoothDevice);
            }
        }

        public final boolean isUidAllowed(int i) {
            return !this.mDeviceInfo.isPrivate() || this.mUid == i;
        }

        public final boolean isUserIdAllowed(int i) {
            return this.mDeviceInfo.getType() != 2 || this.mUserId == i;
        }

        public final void logMetrics(boolean z) {
            int i = this.mDeviceConnectionsAdded.get();
            if (this.mDeviceInfo == null || i <= 0) {
                return;
            }
            MediaMetrics.Item item = new MediaMetrics.Item("audio.midi").setUid(this.mUid).set(MediaMetrics.Property.DEVICE_ID, Integer.valueOf(this.mDeviceInfo.getId())).set(MediaMetrics.Property.INPUT_PORT_COUNT, Integer.valueOf(this.mDeviceInfo.getInputPortCount())).set(MediaMetrics.Property.OUTPUT_PORT_COUNT, Integer.valueOf(this.mDeviceInfo.getOutputPortCount())).set(MediaMetrics.Property.HARDWARE_TYPE, Integer.valueOf(this.mDeviceInfo.getType())).set(MediaMetrics.Property.DURATION_NS, Long.valueOf(this.mTotalTimeConnectedNs.get())).set(MediaMetrics.Property.OPENED_COUNT, Integer.valueOf(i)).set(MediaMetrics.Property.CLOSED_COUNT, Integer.valueOf(this.mDeviceConnectionsRemoved.get())).set(MediaMetrics.Property.DEVICE_DISCONNECTED, z ? "true" : "false").set(MediaMetrics.Property.IS_SHARED, !this.mDeviceInfo.isPrivate() ? "true" : "false");
            MediaMetrics.Key key = MediaMetrics.Property.SUPPORTS_MIDI_UMP;
            MidiService midiService = MidiService.this;
            MidiDeviceInfo midiDeviceInfo = this.mDeviceInfo;
            midiService.getClass();
            item.set(key, MidiService.isUmpDevice(midiDeviceInfo) ? "true" : "false").set(MediaMetrics.Property.USING_ALSA, this.mDeviceInfo.getProperties().get("alsa_card") != null ? "true" : "false").set(MediaMetrics.Property.EVENT, "deviceClosed").set(MediaMetrics.Property.TOTAL_INPUT_BYTES, Integer.valueOf(this.mTotalInputBytes.get())).set(MediaMetrics.Property.TOTAL_OUTPUT_BYTES, Integer.valueOf(this.mTotalOutputBytes.get())).record();
        }

        public final void removeDeviceConnection(DeviceConnection deviceConnection) {
            ServiceConnection serviceConnection;
            synchronized (MidiService.this.mDevicesByInfo) {
                synchronized (this.mDeviceConnections) {
                    try {
                        int incrementAndGet = this.mDeviceConnectionsRemoved.incrementAndGet();
                        Instant instant = this.mPreviousCounterInstant;
                        if (instant != null) {
                            this.mTotalTimeConnectedNs.addAndGet(Duration.between(instant, Instant.now()).toNanos());
                        }
                        if (incrementAndGet >= this.mDeviceConnectionsAdded.get()) {
                            this.mPreviousCounterInstant = null;
                        } else {
                            this.mPreviousCounterInstant = Instant.now();
                        }
                        logMetrics(false);
                        this.mDeviceConnections.remove(deviceConnection);
                        if (deviceConnection.mDevice.mDeviceInfo.getType() == 1) {
                            synchronized (MidiService.this.mUsbMidiLock) {
                                MidiService.this.removeUsbMidiDeviceLocked(deviceConnection.mDevice.mDeviceInfo);
                            }
                        }
                        if (this.mDeviceConnections.size() == 0 && (serviceConnection = this.mServiceConnection) != null) {
                            MidiService.this.mContext.unbindService(serviceConnection);
                            this.mServiceConnection = null;
                            if (this.mBluetoothDevice != null) {
                                closeLocked();
                            } else {
                                setDeviceServer(null);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void setDeviceServer(IMidiDeviceServer iMidiDeviceServer) {
            Log.i("MidiService.Device", "setDeviceServer()");
            if (iMidiDeviceServer == null) {
                IMidiDeviceServer iMidiDeviceServer2 = this.mServer;
                if (iMidiDeviceServer2 != null) {
                    this.mServer = null;
                    IBinder asBinder = iMidiDeviceServer2.asBinder();
                    MidiService.this.mDevicesByServer.remove(asBinder);
                    this.mDeviceStatus = null;
                    try {
                        iMidiDeviceServer2.closeDevice();
                        asBinder.unlinkToDeath(this, 0);
                    } catch (RemoteException unused) {
                    }
                    iMidiDeviceServer = iMidiDeviceServer2;
                }
            } else {
                if (this.mServer != null) {
                    Log.e("MidiService.Device", "mServer already set in setDeviceServer");
                    return;
                }
                IBinder asBinder2 = iMidiDeviceServer.asBinder();
                try {
                    asBinder2.linkToDeath(this, 0);
                    this.mServer = iMidiDeviceServer;
                    MidiService.this.mDevicesByServer.put(asBinder2, this);
                } catch (RemoteException unused2) {
                    this.mServer = null;
                    return;
                }
            }
            ArrayList arrayList = this.mDeviceConnections;
            if (arrayList != null) {
                synchronized (arrayList) {
                    try {
                        Iterator it = this.mDeviceConnections.iterator();
                        while (it.hasNext()) {
                            ((DeviceConnection) it.next()).notifyClient(iMidiDeviceServer);
                        }
                    } finally {
                    }
                }
            }
        }

        public final String toString() {
            return "Device Info: " + this.mDeviceInfo + " Status: " + this.mDeviceStatus + " UID: " + this.mUid + " DeviceConnection count: " + this.mDeviceConnections.size() + " mServiceConnection: " + this.mServiceConnection;
        }
    }

    public final class DeviceConnection {
        public IMidiDeviceOpenCallback mCallback;
        public final Client mClient;
        public final Device mDevice;
        public final IBinder mToken = new Binder();

        public DeviceConnection(Device device, Client client, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
            this.mDevice = device;
            this.mClient = client;
            this.mCallback = iMidiDeviceOpenCallback;
        }

        public final void notifyClient(IMidiDeviceServer iMidiDeviceServer) {
            IBinder iBinder;
            Log.d("MidiService.DeviceConnection", "notifyClient");
            IMidiDeviceOpenCallback iMidiDeviceOpenCallback = this.mCallback;
            if (iMidiDeviceOpenCallback != null) {
                if (iMidiDeviceServer == null) {
                    iBinder = null;
                } else {
                    try {
                        iBinder = this.mToken;
                    } catch (RemoteException unused) {
                    }
                }
                iMidiDeviceOpenCallback.onDeviceOpened(iMidiDeviceServer, iBinder);
                this.mCallback = null;
            }
        }

        public final String toString() {
            Device device = this.mDevice;
            if (device == null || device.mDeviceInfo == null) {
                return "null";
            }
            return "" + device.mDeviceInfo.getId();
        }
    }

    public final class Lifecycle extends SystemService {
        public MidiService mMidiService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? midiService = new MidiService(getContext());
            this.mMidiService = midiService;
            publishBinderService("midi", midiService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            MidiService.m683$$Nest$monStartOrUnlockUser(this.mMidiService, targetUser, false);
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            MidiService.m683$$Nest$monStartOrUnlockUser(this.mMidiService, targetUser, true);
        }
    }

    /* renamed from: -$$Nest$maddPackageDeviceServers, reason: not valid java name */
    public static void m680$$Nest$maddPackageDeviceServers(MidiService midiService, String str, int i) {
        midiService.getClass();
        try {
            ServiceInfo[] serviceInfoArr = midiService.mPackageManager.getPackageInfoAsUser(str, 262276, i).services;
            if (serviceInfoArr == null) {
                return;
            }
            for (int i2 = 0; i2 < serviceInfoArr.length; i2++) {
                midiService.addLegacyPackageDeviceServer(serviceInfoArr[i2], i);
                midiService.addUmpPackageDeviceServer(serviceInfoArr[i2], i);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MidiService", "handlePackageUpdate could not find package " + str, e);
        }
    }

    /* renamed from: -$$Nest$mdumpUuids, reason: not valid java name */
    public static void m681$$Nest$mdumpUuids(MidiService midiService, BluetoothDevice bluetoothDevice) {
        midiService.getClass();
        ParcelUuid[] uuids = bluetoothDevice.getUuids();
        StringBuilder sb = new StringBuilder("dumpUuids(");
        sb.append(bluetoothDevice);
        sb.append(") numParcels:");
        GestureWakeup$$ExternalSyntheticOutline0.m(sb, uuids != null ? uuids.length : 0, "MidiService");
        if (uuids == null) {
            Log.d("MidiService", "No UUID Parcels");
            return;
        }
        for (ParcelUuid parcelUuid : uuids) {
            Log.d("MidiService", " uuid:" + parcelUuid.getUuid());
        }
    }

    /* renamed from: -$$Nest$misBLEMIDIDevice, reason: not valid java name */
    public static boolean m682$$Nest$misBLEMIDIDevice(MidiService midiService, BluetoothDevice bluetoothDevice) {
        midiService.getClass();
        ParcelUuid[] uuids = bluetoothDevice.getUuids();
        if (uuids == null) {
            return false;
        }
        for (ParcelUuid parcelUuid : uuids) {
            if (parcelUuid.getUuid().equals(MIDI_SERVICE)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: -$$Nest$monStartOrUnlockUser, reason: not valid java name */
    public static void m683$$Nest$monStartOrUnlockUser(MidiService midiService, SystemService.TargetUser targetUser, boolean z) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        midiService.getClass();
        Log.d("MidiService", "onStartOrUnlockUser " + targetUser.getUserIdentifier() + " matchDirectBootUnaware: " + z);
        int i = z ? 262272 : 128;
        List queryIntentServicesAsUser = midiService.mPackageManager.queryIntentServicesAsUser(new Intent("android.media.midi.MidiDeviceService"), i, targetUser.getUserIdentifier());
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i2 = 0; i2 < size; i2++) {
                ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
                if (serviceInfo != null) {
                    midiService.addLegacyPackageDeviceServer(serviceInfo, targetUser.getUserIdentifier());
                }
            }
        }
        List queryIntentServicesAsUser2 = midiService.mPackageManager.queryIntentServicesAsUser(new Intent("android.media.midi.MidiUmpDeviceService"), i, targetUser.getUserIdentifier());
        if (queryIntentServicesAsUser2 != null) {
            int size2 = queryIntentServicesAsUser2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ServiceInfo serviceInfo2 = ((ResolveInfo) queryIntentServicesAsUser2.get(i3)).serviceInfo;
                if (serviceInfo2 != null) {
                    midiService.addUmpPackageDeviceServer(serviceInfo2, targetUser.getUserIdentifier());
                }
            }
        }
        UserHandle mainUser = midiService.mUserManager.getMainUser();
        if (mainUser == null || targetUser.getUserIdentifier() == mainUser.getIdentifier()) {
            try {
                packageInfo = midiService.mPackageManager.getPackageInfoAsUser("com.android.bluetoothmidiservice", 0, targetUser.getUserIdentifier());
            } catch (PackageManager.NameNotFoundException unused) {
                packageInfo = null;
            }
            if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
                return;
            }
            midiService.mBluetoothServiceUid = applicationInfo.uid;
        }
    }

    /* renamed from: -$$Nest$mopenBluetoothDevice, reason: not valid java name */
    public static void m684$$Nest$mopenBluetoothDevice(MidiService midiService, final BluetoothDevice bluetoothDevice) {
        midiService.getClass();
        Log.d("MidiService", "openBluetoothDevice() device: " + bluetoothDevice);
        ((MidiManager) midiService.mContext.getSystemService(MidiManager.class)).openBluetoothDevice(bluetoothDevice, new MidiManager.OnDeviceOpenedListener() { // from class: com.android.server.midi.MidiService.3
            @Override // android.media.midi.MidiManager.OnDeviceOpenedListener
            public final void onDeviceOpened(MidiDevice midiDevice) {
                synchronized (MidiService.this.mBleMidiDeviceMap) {
                    Log.i("MidiService", "onDeviceOpened() device:" + midiDevice);
                    MidiService.this.mBleMidiDeviceMap.put(bluetoothDevice, midiDevice);
                }
            }
        }, null);
    }

    /* renamed from: -$$Nest$mremovePackageDeviceServers, reason: not valid java name */
    public static void m685$$Nest$mremovePackageDeviceServers(MidiService midiService, String str, int i) {
        synchronized (midiService.mDevicesByInfo) {
            try {
                Iterator it = midiService.mDevicesByInfo.values().iterator();
                while (it.hasNext()) {
                    Device device = (Device) it.next();
                    ServiceInfo serviceInfo = device.mServiceInfo;
                    if (str.equals(serviceInfo == null ? null : serviceInfo.packageName) && device.mUserId == i) {
                        it.remove();
                        midiService.removeDeviceLocked(device);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public MidiService(Context context) {
        HashSet hashSet = new HashSet();
        this.mNonMidiUUIDs = hashSet;
        PackageMonitor packageMonitor = new PackageMonitor() { // from class: com.android.server.midi.MidiService.1
            public final void onPackageAdded(String str, int i) {
                MidiService.m680$$Nest$maddPackageDeviceServers(MidiService.this, str, getChangingUserId());
            }

            public final void onPackageModified(String str) {
                MidiService.m685$$Nest$mremovePackageDeviceServers(MidiService.this, str, getChangingUserId());
                MidiService.m680$$Nest$maddPackageDeviceServers(MidiService.this, str, getChangingUserId());
            }

            public final void onPackageRemoved(String str, int i) {
                MidiService.m685$$Nest$mremovePackageDeviceServers(MidiService.this, str, getChangingUserId());
            }
        };
        new BroadcastReceiver() { // from class: com.android.server.midi.MidiService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                MidiDevice midiDevice;
                String action = intent.getAction();
                if (action == null) {
                    Log.w("MidiService", "MidiService, action is null");
                    return;
                }
                switch (action) {
                    case "android.bluetooth.device.action.UUID":
                    case "android.bluetooth.device.action.BOND_STATE_CHANGED":
                        Log.d("MidiService", "ACTION_UUID");
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                        MidiService.m681$$Nest$mdumpUuids(MidiService.this, bluetoothDevice);
                        if (MidiService.m682$$Nest$misBLEMIDIDevice(MidiService.this, bluetoothDevice)) {
                            Log.d("MidiService", "BT MIDI DEVICE");
                            MidiService.m684$$Nest$mopenBluetoothDevice(MidiService.this, bluetoothDevice);
                            return;
                        }
                        return;
                    case "android.bluetooth.device.action.ACL_CONNECTED":
                        Log.d("MidiService", "ACTION_ACL_CONNECTED");
                        Log.d("MidiService", "Intent: " + intent.getAction());
                        Bundle extras = intent.getExtras();
                        if (extras != null) {
                            for (String str : extras.keySet()) {
                                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", str, " : ");
                                m.append(extras.get(str) != null ? extras.get(str) : "NULL");
                                Log.d("MidiService", m.toString());
                            }
                        }
                        Bundle extras2 = intent.getExtras();
                        if (extras2 == null || extras2.getInt("android.bluetooth.device.extra.TRANSPORT", 0) != 2) {
                            Log.i("MidiService", "No BLE transport - NOT MIDI");
                            return;
                        }
                        Log.d("MidiService", "BLE Device");
                        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                        MidiService.m681$$Nest$mdumpUuids(MidiService.this, bluetoothDevice2);
                        MidiService midiService = MidiService.this;
                        midiService.getClass();
                        ParcelUuid[] uuids = bluetoothDevice2.getUuids();
                        if (uuids != null) {
                            for (ParcelUuid parcelUuid : uuids) {
                                if (midiService.mNonMidiUUIDs.contains(parcelUuid)) {
                                    Log.d("MidiService", "Non-MIDI service UUIDs found. NOT MIDI");
                                    return;
                                }
                            }
                        }
                        Log.d("MidiService", "Potential MIDI Device.");
                        MidiService.m684$$Nest$mopenBluetoothDevice(MidiService.this, bluetoothDevice2);
                        return;
                    case "android.bluetooth.device.action.ACL_DISCONNECTED":
                        Log.d("MidiService", "ACTION_ACL_DISCONNECTED");
                        BluetoothDevice bluetoothDevice3 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                        if (MidiService.m682$$Nest$misBLEMIDIDevice(MidiService.this, bluetoothDevice3)) {
                            MidiService midiService2 = MidiService.this;
                            midiService2.getClass();
                            Log.d("MidiService", "closeBluetoothDevice() device: " + bluetoothDevice3);
                            synchronized (midiService2.mBleMidiDeviceMap) {
                                midiDevice = (MidiDevice) midiService2.mBleMidiDeviceMap.remove(bluetoothDevice3);
                            }
                            if (midiDevice != null) {
                                try {
                                    midiDevice.close();
                                    return;
                                } catch (IOException e) {
                                    Log.e("MidiService", "Exception closing BLE-MIDI device" + e);
                                    return;
                                }
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        packageMonitor.register(context, (Looper) null, UserHandle.ALL, true);
        this.mBluetoothServiceUid = -1;
        hashSet.add(BluetoothUuid.A2DP_SINK);
        hashSet.add(BluetoothUuid.A2DP_SOURCE);
        hashSet.add(BluetoothUuid.ADV_AUDIO_DIST);
        hashSet.add(BluetoothUuid.AVRCP_CONTROLLER);
        hashSet.add(BluetoothUuid.HFP);
        hashSet.add(BluetoothUuid.HSP);
        hashSet.add(BluetoothUuid.HID);
        hashSet.add(BluetoothUuid.LE_AUDIO);
        hashSet.add(BluetoothUuid.HOGP);
        hashSet.add(BluetoothUuid.HEARING_AID);
    }

    public static boolean isUmpDevice(MidiDeviceInfo midiDeviceInfo) {
        return midiDeviceInfo.getDefaultProtocol() != -1;
    }

    public final MidiDeviceInfo addDeviceLocked(int i, int i2, int i3, String[] strArr, String[] strArr2, Bundle bundle, IMidiDeviceServer iMidiDeviceServer, ServiceInfo serviceInfo, boolean z, int i4, int i5, int i6) {
        BluetoothDevice bluetoothDevice;
        GestureWakeup$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i4, i, "addDeviceLocked() ", " type:", " userId:"), i6, "MidiService");
        Iterator it = this.mDevicesByInfo.values().iterator();
        int i7 = 0;
        while (it.hasNext()) {
            if (((Device) it.next()).mUid == i4) {
                i7++;
            }
        }
        if (i7 >= 16) {
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(i4, "too many MIDI devices already created for UID = "));
        }
        int i8 = this.mNextDeviceId;
        this.mNextDeviceId = i8 + 1;
        MidiDeviceInfo midiDeviceInfo = new MidiDeviceInfo(i, i8, i2, i3, strArr, strArr2, bundle, z, i5);
        Device device = null;
        if (iMidiDeviceServer != null) {
            try {
                iMidiDeviceServer.setDeviceInfo(midiDeviceInfo);
            } catch (RemoteException unused) {
                Log.e("MidiService", "RemoteException in setDeviceInfo()");
                return null;
            }
        }
        if (i == 3) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) bundle.getParcelable("bluetooth_device", BluetoothDevice.class);
            Device device2 = (Device) this.mBluetoothDevices.get(bluetoothDevice2);
            if (device2 != null) {
                device2.mDeviceInfo = midiDeviceInfo;
            }
            bluetoothDevice = bluetoothDevice2;
            device = device2;
        } else {
            bluetoothDevice = null;
        }
        if (device == null) {
            device = new Device(iMidiDeviceServer, midiDeviceInfo, serviceInfo, i4, i6);
        }
        this.mDevicesByInfo.put(midiDeviceInfo, device);
        if (bluetoothDevice != null) {
            this.mBluetoothDevices.put(bluetoothDevice, device);
        }
        synchronized (this.mClients) {
            try {
                Iterator it2 = this.mClients.values().iterator();
                while (it2.hasNext()) {
                    ((Client) it2.next()).deviceAdded(device);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return midiDeviceInfo;
    }

    public final void addLegacyPackageDeviceServer(ServiceInfo serviceInfo, int i) {
        XmlResourceParser xmlResourceParser;
        int i2;
        ArrayList arrayList;
        ArrayList arrayList2;
        int i3;
        HashMap hashMap;
        HashMap hashMap2;
        String[] strArr;
        String str;
        String str2;
        XmlResourceParser xmlResourceParser2 = null;
        try {
            try {
                if (serviceInfo == null) {
                    Log.w("MidiService", "Skipping null service info");
                    return;
                }
                if (!"android.permission.BIND_MIDI_DEVICE_SERVICE".equals(serviceInfo.permission)) {
                    return;
                }
                XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(this.mPackageManager, "android.media.midi.MidiDeviceService");
                try {
                    if (loadXmlMetaData == null) {
                        Log.w("MidiService", "loading xml metadata failed");
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                            return;
                        }
                        return;
                    }
                    try {
                        ArrayList arrayList3 = new ArrayList();
                        ArrayList arrayList4 = new ArrayList();
                        int i4 = 0;
                        int i5 = 0;
                        int i6 = 0;
                        boolean z = false;
                        Bundle bundle = null;
                        while (true) {
                            int next = loadXmlMetaData.next();
                            if (next == 1) {
                                loadXmlMetaData.close();
                                return;
                            }
                            if (next == 2) {
                                String name = loadXmlMetaData.getName();
                                if ("device".equals(name)) {
                                    if (bundle != null) {
                                        Log.w("MidiService", "nested <device> elements in metadata for " + serviceInfo.packageName);
                                    } else {
                                        bundle = new Bundle();
                                        bundle.putParcelable("service_info", serviceInfo);
                                        int attributeCount = loadXmlMetaData.getAttributeCount();
                                        int i7 = i4;
                                        z = i7;
                                        while (i7 < attributeCount) {
                                            String attributeName = loadXmlMetaData.getAttributeName(i7);
                                            String attributeValue = loadXmlMetaData.getAttributeValue(i7);
                                            if ("private".equals(attributeName)) {
                                                z = "true".equals(attributeValue);
                                            } else {
                                                bundle.putString(attributeName, attributeValue);
                                            }
                                            i7++;
                                        }
                                        i5 = i4;
                                        i6 = i5;
                                    }
                                } else if ("input-port".equals(name)) {
                                    if (bundle == null) {
                                        Log.w("MidiService", "<input-port> outside of <device> in metadata for " + serviceInfo.packageName);
                                    } else {
                                        i5++;
                                        int attributeCount2 = loadXmlMetaData.getAttributeCount();
                                        int i8 = i4;
                                        while (true) {
                                            if (i8 >= attributeCount2) {
                                                str2 = null;
                                                break;
                                            }
                                            String attributeName2 = loadXmlMetaData.getAttributeName(i8);
                                            str2 = loadXmlMetaData.getAttributeValue(i8);
                                            if ("name".equals(attributeName2)) {
                                                break;
                                            } else {
                                                i8++;
                                            }
                                        }
                                        arrayList3.add(str2);
                                    }
                                } else if ("output-port".equals(name)) {
                                    if (bundle == null) {
                                        Log.w("MidiService", "<output-port> outside of <device> in metadata for " + serviceInfo.packageName);
                                    } else {
                                        i6++;
                                        int attributeCount3 = loadXmlMetaData.getAttributeCount();
                                        int i9 = i4;
                                        while (true) {
                                            if (i9 >= attributeCount3) {
                                                str = null;
                                                break;
                                            }
                                            String attributeName3 = loadXmlMetaData.getAttributeName(i9);
                                            str = loadXmlMetaData.getAttributeValue(i9);
                                            if ("name".equals(attributeName3)) {
                                                break;
                                            } else {
                                                i9++;
                                            }
                                        }
                                        arrayList4.add(str);
                                    }
                                }
                            } else if (next == 3 && "device".equals(loadXmlMetaData.getName()) && bundle != null) {
                                if (i5 == 0 && i6 == 0) {
                                    Log.w("MidiService", "<device> with no ports in metadata for " + serviceInfo.packageName);
                                } else {
                                    try {
                                        i3 = this.mPackageManager.getApplicationInfoAsUser(serviceInfo.packageName, i4, i).uid;
                                        hashMap = this.mDevicesByInfo;
                                    } catch (PackageManager.NameNotFoundException unused) {
                                        i2 = i4;
                                        arrayList = arrayList4;
                                        arrayList2 = arrayList3;
                                        xmlResourceParser = loadXmlMetaData;
                                        Log.e("MidiService", "could not fetch ApplicationInfo for " + serviceInfo.packageName);
                                    }
                                    synchronized (hashMap) {
                                        try {
                                            strArr = EMPTY_STRING_ARRAY;
                                            hashMap2 = hashMap;
                                            i2 = i4;
                                            arrayList = arrayList4;
                                            arrayList2 = arrayList3;
                                            xmlResourceParser = loadXmlMetaData;
                                        } catch (Throwable th) {
                                            th = th;
                                            hashMap2 = hashMap;
                                            xmlResourceParser = loadXmlMetaData;
                                            throw th;
                                        }
                                        try {
                                            addDeviceLocked(2, i5, i6, (String[]) arrayList3.toArray(strArr), (String[]) arrayList4.toArray(strArr), bundle, null, serviceInfo, z, i3, -1, i);
                                            try {
                                                arrayList2.clear();
                                                arrayList.clear();
                                                bundle = null;
                                                arrayList3 = arrayList2;
                                                i4 = i2;
                                                loadXmlMetaData = xmlResourceParser;
                                                arrayList4 = arrayList;
                                            } catch (Exception e) {
                                                e = e;
                                                xmlResourceParser2 = xmlResourceParser;
                                                Log.w("MidiService", "Unable to load component info " + serviceInfo.toString(), e);
                                                if (xmlResourceParser2 != null) {
                                                    xmlResourceParser2.close();
                                                    return;
                                                }
                                                return;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                xmlResourceParser2 = xmlResourceParser;
                                                if (xmlResourceParser2 != null) {
                                                    xmlResourceParser2.close();
                                                }
                                                throw th;
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            throw th;
                                        }
                                    }
                                }
                            }
                            i2 = i4;
                            arrayList = arrayList4;
                            arrayList2 = arrayList3;
                            xmlResourceParser = loadXmlMetaData;
                            arrayList3 = arrayList2;
                            i4 = i2;
                            loadXmlMetaData = xmlResourceParser;
                            arrayList4 = arrayList;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        xmlResourceParser = loadXmlMetaData;
                    } catch (Throwable th4) {
                        th = th4;
                        xmlResourceParser = loadXmlMetaData;
                    }
                } catch (Exception e3) {
                    e = e3;
                    xmlResourceParser2 = loadXmlMetaData;
                } catch (Throwable th5) {
                    th = th5;
                    xmlResourceParser2 = loadXmlMetaData;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        } catch (Exception e4) {
            e = e4;
        }
    }

    public final void addUmpPackageDeviceServer(ServiceInfo serviceInfo, int i) {
        XmlResourceParser xmlResourceParser;
        int i2;
        ArrayList arrayList;
        int i3;
        HashMap hashMap;
        HashMap hashMap2;
        String str;
        XmlResourceParser xmlResourceParser2 = null;
        try {
            try {
                if (serviceInfo == null) {
                    Log.w("MidiService", "Skipping null service info");
                    return;
                }
                if (!"android.permission.BIND_MIDI_DEVICE_SERVICE".equals(serviceInfo.permission)) {
                    return;
                }
                if (!Flags.virtualUmp()) {
                    Log.w("MidiService", "Skipping MIDI device service " + serviceInfo.packageName + ": virtual UMP flag not enabled");
                    return;
                }
                PackageManager.Property property = this.mPackageManager.getProperty("android.media.midi.MidiUmpDeviceService", new ComponentName(serviceInfo.packageName, serviceInfo.name));
                if (property == null) {
                    Log.w("MidiService", "Getting MidiUmpDeviceService property failed");
                    return;
                }
                int resourceId = property.getResourceId();
                if (resourceId == 0) {
                    Log.w("MidiService", "Getting MidiUmpDeviceService resourceId failed");
                    return;
                }
                Resources resourcesForApplication = this.mPackageManager.getResourcesForApplication(serviceInfo.packageName);
                if (resourcesForApplication == null) {
                    Log.w("MidiService", "Getting resource failed " + serviceInfo.packageName);
                    return;
                }
                XmlResourceParser xml = resourcesForApplication.getXml(resourceId);
                try {
                    if (xml == null) {
                        Log.w("MidiService", "Getting XML failed " + resourceId);
                        if (xml != null) {
                            xml.close();
                            return;
                        }
                        return;
                    }
                    try {
                        try {
                            ArrayList arrayList2 = new ArrayList();
                            int i4 = 0;
                            int i5 = 0;
                            boolean z = false;
                            Bundle bundle = null;
                            while (true) {
                                int next = xml.next();
                                if (next == 1) {
                                    xml.close();
                                    return;
                                }
                                if (next == 2) {
                                    String name = xml.getName();
                                    if ("device".equals(name)) {
                                        if (bundle != null) {
                                            Log.w("MidiService", "nested <device> elements in metadata for " + serviceInfo.packageName);
                                        } else {
                                            bundle = new Bundle();
                                            bundle.putParcelable("service_info", serviceInfo);
                                            int attributeCount = xml.getAttributeCount();
                                            int i6 = i4;
                                            z = i6;
                                            while (i6 < attributeCount) {
                                                String attributeName = xml.getAttributeName(i6);
                                                String attributeValue = xml.getAttributeValue(i6);
                                                if ("private".equals(attributeName)) {
                                                    z = "true".equals(attributeValue);
                                                } else {
                                                    bundle.putString(attributeName, attributeValue);
                                                }
                                                i6++;
                                            }
                                            i5 = i4;
                                        }
                                    } else if ("port".equals(name)) {
                                        if (bundle == null) {
                                            Log.w("MidiService", "<port> outside of <device> in metadata for " + serviceInfo.packageName);
                                        } else {
                                            i5++;
                                            int attributeCount2 = xml.getAttributeCount();
                                            int i7 = i4;
                                            while (true) {
                                                if (i7 >= attributeCount2) {
                                                    str = null;
                                                    break;
                                                }
                                                String attributeName2 = xml.getAttributeName(i7);
                                                str = xml.getAttributeValue(i7);
                                                if ("name".equals(attributeName2)) {
                                                    break;
                                                } else {
                                                    i7++;
                                                }
                                            }
                                            arrayList2.add(str);
                                        }
                                    }
                                } else if (next == 3 && "device".equals(xml.getName()) && bundle != null) {
                                    if (i5 == 0) {
                                        Log.w("MidiService", "<device> with no ports in metadata for " + serviceInfo.packageName);
                                    } else {
                                        try {
                                            i3 = this.mPackageManager.getApplicationInfoAsUser(serviceInfo.packageName, i4, i).uid;
                                            hashMap = this.mDevicesByInfo;
                                        } catch (PackageManager.NameNotFoundException unused) {
                                            i2 = i4;
                                            arrayList = arrayList2;
                                            xmlResourceParser = xml;
                                            Log.e("MidiService", "could not fetch ApplicationInfo for " + serviceInfo.packageName);
                                        }
                                        synchronized (hashMap) {
                                            try {
                                                String[] strArr = EMPTY_STRING_ARRAY;
                                                hashMap2 = hashMap;
                                                i2 = i4;
                                                arrayList = arrayList2;
                                                xmlResourceParser = xml;
                                                try {
                                                    addDeviceLocked(2, i5, i5, (String[]) arrayList2.toArray(strArr), (String[]) arrayList2.toArray(strArr), bundle, null, serviceInfo, z, i3, 17, i);
                                                    try {
                                                        arrayList.clear();
                                                        bundle = null;
                                                        arrayList2 = arrayList;
                                                        xml = xmlResourceParser;
                                                        i4 = i2;
                                                    } catch (PackageManager.NameNotFoundException unused2) {
                                                        xmlResourceParser2 = xmlResourceParser;
                                                        if (xmlResourceParser2 == null) {
                                                            return;
                                                        }
                                                        xmlResourceParser2.close();
                                                        return;
                                                    } catch (Exception e) {
                                                        e = e;
                                                        xmlResourceParser2 = xmlResourceParser;
                                                        Log.w("MidiService", "Unable to load component info " + serviceInfo.toString(), e);
                                                        if (xmlResourceParser2 == null) {
                                                            return;
                                                        }
                                                        xmlResourceParser2.close();
                                                        return;
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        xmlResourceParser2 = xmlResourceParser;
                                                        if (xmlResourceParser2 != null) {
                                                            xmlResourceParser2.close();
                                                        }
                                                        throw th;
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    throw th;
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                                hashMap2 = hashMap;
                                                xmlResourceParser = xml;
                                                throw th;
                                            }
                                        }
                                    }
                                }
                                i2 = i4;
                                arrayList = arrayList2;
                                xmlResourceParser = xml;
                                arrayList2 = arrayList;
                                xml = xmlResourceParser;
                                i4 = i2;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            xmlResourceParser = xml;
                        } catch (Throwable th4) {
                            th = th4;
                            xmlResourceParser = xml;
                        }
                    } catch (PackageManager.NameNotFoundException unused3) {
                        xmlResourceParser = xml;
                    }
                } catch (PackageManager.NameNotFoundException unused4) {
                    xmlResourceParser2 = xml;
                } catch (Exception e3) {
                    e = e3;
                    xmlResourceParser2 = xml;
                } catch (Throwable th5) {
                    th = th5;
                    xmlResourceParser2 = xml;
                }
            } catch (PackageManager.NameNotFoundException unused5) {
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public final void addUsbMidiDeviceLocked(MidiDeviceInfo midiDeviceInfo) {
        String string = midiDeviceInfo.getProperties().getString("name");
        if (string.length() < 8) {
            return;
        }
        String m = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(8, 0, string);
        String substring = string.substring(string.length() - 8);
        Log.i("MidiService", "Adding " + m + " " + substring);
        if (substring.equals("MIDI 2.0")) {
            this.mUsbMidiUniversalDeviceInUse.add(m);
        } else if (substring.equals("MIDI 1.0")) {
            this.mUsbMidiLegacyDeviceOpenCount.put(m, Integer.valueOf(((Integer) this.mUsbMidiLegacyDeviceOpenCount.getOrDefault(m, 0)).intValue() + 1));
        }
    }

    public final void closeDevice(IBinder iBinder, IBinder iBinder2) {
        Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        DeviceConnection deviceConnection = (DeviceConnection) client.mDeviceConnections.remove(iBinder2);
        if (deviceConnection != null) {
            deviceConnection.mDevice.removeDeviceConnection(deviceConnection);
        }
        if (client.mListeners.size() == 0 && client.mDeviceConnections.size() == 0) {
            client.close();
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "MidiService", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.println("MIDI Manager State:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Devices:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mDevicesByInfo) {
                try {
                    Iterator it = this.mDevicesByInfo.values().iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println(((Device) it.next()).toString());
                    }
                } finally {
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Clients:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mClients) {
                try {
                    Iterator it2 = this.mClients.values().iterator();
                    while (it2.hasNext()) {
                        indentingPrintWriter.println(((Client) it2.next()).toString());
                    }
                } finally {
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final Client getClient(IBinder iBinder) {
        Client client;
        synchronized (this.mClients) {
            try {
                client = (Client) this.mClients.get(iBinder);
                if (client == null) {
                    client = new Client(iBinder);
                    try {
                        iBinder.linkToDeath(client, 0);
                        this.mClients.put(iBinder, client);
                    } catch (RemoteException unused) {
                        return null;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return client;
    }

    public final MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) {
        Device device = (Device) this.mDevicesByInfo.get(midiDeviceInfo);
        if (device == null) {
            throw new IllegalArgumentException("no such device for " + midiDeviceInfo);
        }
        int callingUid = Binder.getCallingUid();
        if (device.isUidAllowed(callingUid)) {
            return device.mDeviceStatus;
        }
        Log.e("MidiService", "getDeviceStatus() invalid UID = " + callingUid);
        EventLog.writeEvent(1397638484, "203549963", Integer.valueOf(callingUid), "getDeviceStatus: invalid uid");
        return null;
    }

    public final MidiDeviceInfo[] getDevices() {
        return getDevicesForTransport(1);
    }

    public final MidiDeviceInfo[] getDevicesForTransport(int i) {
        ArrayList arrayList = new ArrayList();
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mDevicesByInfo) {
            try {
                for (Device device : this.mDevicesByInfo.values()) {
                    if (device.isUidAllowed(callingUid) && device.isUserIdAllowed(userId)) {
                        if (i == 2) {
                            if (isUmpDevice(device.mDeviceInfo)) {
                                arrayList.add(device.mDeviceInfo);
                            }
                        } else if (i == 1 && !isUmpDevice(device.mDeviceInfo)) {
                            arrayList.add(device.mDeviceInfo);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return (MidiDeviceInfo[]) arrayList.toArray(EMPTY_DEVICE_INFO_ARRAY);
    }

    public final MidiDeviceInfo getServiceDeviceInfo(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        synchronized (this.mDevicesByInfo) {
            try {
                for (Device device : this.mDevicesByInfo.values()) {
                    ServiceInfo serviceInfo = device.mServiceInfo;
                    if (serviceInfo != null && str.equals(serviceInfo.packageName) && str2.equals(serviceInfo.name)) {
                        if (device.isUidAllowed(callingUid)) {
                            return device.mDeviceInfo;
                        }
                        EventLog.writeEvent(1397638484, "185796676", -1, "");
                        return null;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isUsbMidiDeviceInUseLocked(MidiDeviceInfo midiDeviceInfo) {
        String string = midiDeviceInfo.getProperties().getString("name");
        if (string.length() < 8) {
            return false;
        }
        String m = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(8, 0, string);
        String substring = string.substring(string.length() - 8);
        Log.i("MidiService", "Checking " + m + " " + substring);
        if (this.mUsbMidiUniversalDeviceInUse.contains(m)) {
            return true;
        }
        return substring.equals("MIDI 2.0") && this.mUsbMidiLegacyDeviceOpenCount.containsKey(m);
    }

    public final void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
        Device device;
        Log.d("MidiService", "openBluetoothDevice()");
        Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        Log.i("MidiService", "alloc device...");
        synchronized (this.mDevicesByInfo) {
            try {
                device = (Device) this.mBluetoothDevices.get(bluetoothDevice);
                if (device == null) {
                    device = new Device(bluetoothDevice);
                    this.mBluetoothDevices.put(bluetoothDevice, device);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Log.i("MidiService", "device: " + device);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.i("MidiService", "addDeviceConnection() [C] device:" + device);
            client.addDeviceConnection(device, iMidiDeviceOpenCallback, UserHandle.getUserId(Binder.getCallingUid()));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) {
        Device device;
        Client client = getClient(iBinder);
        Log.d("MidiService", "openDevice() client:" + client);
        if (client == null) {
            return;
        }
        synchronized (this.mDevicesByInfo) {
            device = (Device) this.mDevicesByInfo.get(midiDeviceInfo);
            Log.d("MidiService", "  device:" + device);
            if (device == null) {
                throw new IllegalArgumentException("device does not exist: " + midiDeviceInfo);
            }
            if (!device.isUidAllowed(Binder.getCallingUid())) {
                throw new SecurityException("Attempt to open private device with wrong UID");
            }
            if (!device.isUserIdAllowed(UserHandle.getUserId(Binder.getCallingUid()))) {
                throw new SecurityException("Attempt to open virtual device with wrong user id");
            }
        }
        if (midiDeviceInfo.getType() == 1) {
            synchronized (this.mUsbMidiLock) {
                try {
                    if (isUsbMidiDeviceInUseLocked(midiDeviceInfo)) {
                        throw new IllegalArgumentException("device already in use: " + midiDeviceInfo);
                    }
                    addUsbMidiDeviceLocked(midiDeviceInfo);
                } finally {
                }
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Log.i("MidiService", "addDeviceConnection() [B] device:" + device);
            client.addDeviceConnection(device, iMidiDeviceOpenCallback, UserHandle.getUserId(Binder.getCallingUid()));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int i, int i2, String[] strArr, String[] strArr2, Bundle bundle, int i3, int i4) {
        MidiDeviceInfo addDeviceLocked;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        if (i3 == 1 && callingUid != 1000) {
            throw new SecurityException("only system can create USB devices");
        }
        if (i3 == 3 && callingUid != this.mBluetoothServiceUid) {
            throw new SecurityException("only MidiBluetoothService can create Bluetooth devices");
        }
        synchronized (this.mDevicesByInfo) {
            addDeviceLocked = addDeviceLocked(i3, i, i2, strArr, strArr2, bundle, iMidiDeviceServer, null, false, callingUid, i4, userId);
        }
        return addDeviceLocked;
    }

    public final void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) {
        Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        if (client.mListeners.size() >= 16) {
            throw new SecurityException("too many MIDI listeners for UID = " + client.mUid);
        }
        client.mListeners.put(iMidiDeviceListener.asBinder(), iMidiDeviceListener);
        int i = client.mUid;
        int userId = UserHandle.getUserId(i);
        synchronized (this.mDevicesByInfo) {
            for (Device device : this.mDevicesByInfo.values()) {
                if (device.isUidAllowed(i) && device.isUserIdAllowed(userId)) {
                    try {
                        MidiDeviceStatus midiDeviceStatus = device.mDeviceStatus;
                        if (midiDeviceStatus != null) {
                            iMidiDeviceListener.onDeviceStatusChanged(midiDeviceStatus);
                        }
                    } catch (RemoteException e) {
                        Log.e("MidiService", "remote exception", e);
                    }
                }
            }
        }
    }

    public final void removeDeviceLocked(Device device) {
        IMidiDeviceServer iMidiDeviceServer = device.mServer;
        if (iMidiDeviceServer != null) {
            this.mDevicesByServer.remove(iMidiDeviceServer.asBinder());
        }
        this.mDevicesByInfo.remove(device.mDeviceInfo);
        synchronized (this.mClients) {
            try {
                Iterator it = this.mClients.values().iterator();
                while (it.hasNext()) {
                    ((Client) it.next()).deviceRemoved(device);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeUsbMidiDeviceLocked(MidiDeviceInfo midiDeviceInfo) {
        String string = midiDeviceInfo.getProperties().getString("name");
        if (string.length() < 8) {
            return;
        }
        String m = DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(8, 0, string);
        String substring = string.substring(string.length() - 8);
        Log.i("MidiService", "Removing " + m + " " + substring);
        if (substring.equals("MIDI 2.0")) {
            this.mUsbMidiUniversalDeviceInUse.remove(m);
            return;
        }
        if (substring.equals("MIDI 1.0") && this.mUsbMidiLegacyDeviceOpenCount.containsKey(m)) {
            int intValue = ((Integer) this.mUsbMidiLegacyDeviceOpenCount.get(m)).intValue();
            if (intValue > 1) {
                this.mUsbMidiLegacyDeviceOpenCount.put(m, Integer.valueOf(intValue - 1));
            } else {
                this.mUsbMidiLegacyDeviceOpenCount.remove(m);
            }
        }
    }

    public final void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) {
        Device device = (Device) this.mDevicesByServer.get(iMidiDeviceServer.asBinder());
        if (device != null) {
            if (Binder.getCallingUid() != device.mUid) {
                throw new SecurityException("setDeviceStatus() caller UID " + Binder.getCallingUid() + " does not match device's UID " + device.mUid);
            }
            device.mDeviceStatus = midiDeviceStatus;
            synchronized (this.mClients) {
                try {
                    Iterator it = this.mClients.values().iterator();
                    while (it.hasNext()) {
                        ((Client) it.next()).deviceStatusChanged(device, midiDeviceStatus);
                    }
                } finally {
                }
            }
        }
    }

    public final void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) {
        synchronized (this.mDevicesByInfo) {
            try {
                Device device = (Device) this.mDevicesByServer.get(iMidiDeviceServer.asBinder());
                if (device != null) {
                    device.closeLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) {
        Client client = getClient(iBinder);
        if (client == null) {
            return;
        }
        client.mListeners.remove(iMidiDeviceListener.asBinder());
        if (client.mListeners.size() == 0 && client.mDeviceConnections.size() == 0) {
            client.close();
        }
    }

    public final void updateTotalBytes(IMidiDeviceServer iMidiDeviceServer, int i, int i2) {
        synchronized (this.mDevicesByInfo) {
            Device device = (Device) this.mDevicesByServer.get(iMidiDeviceServer.asBinder());
            if (device != null) {
                device.mTotalInputBytes.set(i);
                device.mTotalOutputBytes.set(i2);
            }
        }
    }
}
