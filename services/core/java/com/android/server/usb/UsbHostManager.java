package com.android.server.usb;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.FactoryTest;
import android.os.ParcelFileDescriptor;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.sysfwutil.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.dump.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.UsbConfigDescriptor;
import com.android.server.usb.descriptors.UsbDescriptor;
import com.android.server.usb.descriptors.UsbDescriptorParser;
import com.android.server.usb.descriptors.UsbDeviceDescriptor;
import com.android.server.usb.descriptors.UsbEndpointDescriptor;
import com.android.server.usb.descriptors.UsbInterfaceDescriptor;
import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.tree.UsbDescriptorsConfigNode;
import com.android.server.usb.descriptors.tree.UsbDescriptorsDeviceNode;
import com.android.server.usb.descriptors.tree.UsbDescriptorsEndpointNode;
import com.android.server.usb.descriptors.tree.UsbDescriptorsInterfaceNode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class UsbHostManager {
    public static final SimpleDateFormat sFormat = new SimpleDateFormat("MM-dd HH:mm:ss:SSS");
    public final Context mContext;
    public UsbProfileGroupSettingsManager mCurrentSettings;
    public final boolean mHasMidiFeature;
    public final String[] mHostDenyList;
    public final AnonymousClass1 mHostPathObserver;
    public ConnectionRecord mLastConnect;
    public int mNumConnects;
    public final ArrayList mPendingIntent;
    public final Object mPendingIntentLock;
    public final UsbPermissionManager mPermissionManager;
    public boolean mSystemReady;
    public final UsbAlsaManager mUsbAlsaManager;
    public final AnonymousClass1 mUsbControlObserver;
    public ComponentName mUsbDeviceConnectionHandler;
    public final Object mLock = new Object();
    public final HashMap mDevices = new HashMap();
    public final Object mSettingsLock = new Object();
    public final Object mHandlerLock = new Object();
    public final LinkedList mConnections = new LinkedList();
    public final ArrayMap mConnected = new ArrayMap();
    public final HashMap mMidiDevices = new HashMap();
    public final HashSet mMidiUniqueCodes = new HashSet();
    public final Random mRandom = new Random();
    public boolean mBootCompleted = false;
    public int mCurrentUnlockedUser = -10000;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConnectionRecord {
        public final byte[] mDescriptors;
        public final String mDeviceAddress;
        public final int mMode;
        public final long mTimestamp = System.currentTimeMillis();

        public ConnectionRecord(int i, String str, byte[] bArr) {
            this.mDeviceAddress = str;
            this.mMode = i;
            this.mDescriptors = bArr;
        }

        public final void dump(DualDumpOutputStream dualDumpOutputStream) {
            long start = dualDumpOutputStream.start("connections", 2246267895812L);
            String str = this.mDeviceAddress;
            dualDumpOutputStream.write("device_address", 1138166333441L, str);
            int i = this.mMode;
            dualDumpOutputStream.write("mode", 1159641169922L, i);
            dualDumpOutputStream.write("timestamp", 1112396529667L, this.mTimestamp);
            if (i != -1) {
                UsbDescriptorParser usbDescriptorParser = new UsbDescriptorParser(str, this.mDescriptors);
                UsbDeviceDescriptor usbDeviceDescriptor = usbDescriptorParser.mDeviceDescriptor;
                dualDumpOutputStream.write("manufacturer", 1120986464260L, usbDeviceDescriptor.mVendorID);
                dualDumpOutputStream.write("product", 1120986464261L, usbDeviceDescriptor.mProductID);
                long start2 = dualDumpOutputStream.start("is_headset", 1146756268038L);
                dualDumpOutputStream.write("in", 1133871366145L, usbDescriptorParser.isInputHeadset());
                dualDumpOutputStream.write("out", 1133871366146L, usbDescriptorParser.isOutputHeadset());
                dualDumpOutputStream.end(start2);
            }
            dualDumpOutputStream.end(start);
        }

        public final String formatTime() {
            return new StringBuilder(UsbHostManager.sFormat.format(new Date(this.mTimestamp))).toString();
        }
    }

    /* renamed from: -$$Nest$mbroadcastWithPendingQueue, reason: not valid java name */
    public static void m1019$$Nest$mbroadcastWithPendingQueue(UsbHostManager usbHostManager, Intent intent, UserHandle userHandle) {
        synchronized (usbHostManager.mPendingIntentLock) {
            try {
                if (usbHostManager.mSystemReady) {
                    usbHostManager.mContext.sendBroadcastAsUser(intent, userHandle);
                    Slog.d("UsbHostManager", "broadcasting " + intent + " extras: " + intent.getExtras());
                } else {
                    usbHostManager.mPendingIntent.add(intent);
                    Slog.d("UsbHostManager", "pending " + intent);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public UsbHostManager(Context context, UsbAlsaManager usbAlsaManager, UsbPermissionManager usbPermissionManager) {
        final int i = 0;
        UEventObserver uEventObserver = new UEventObserver(this) { // from class: com.android.server.usb.UsbHostManager.1
            public final /* synthetic */ UsbHostManager this$0;

            {
                this.this$0 = this;
            }

            public final void onUEvent(UEventObserver.UEvent uEvent) {
                switch (i) {
                    case 0:
                        SimpleDateFormat simpleDateFormat = UsbHostManager.sFormat;
                        Slog.v("UsbHostManager", "onUEvent(Host Path): " + uEvent);
                        String str = uEvent.get("ACTION");
                        String str2 = uEvent.get("STATE");
                        if (!"change".equals(str) || str2 == null) {
                            return;
                        }
                        if (str2.equals("REMOVE")) {
                            Intent intent = new Intent("com.samsung.UsbOtgCableConnection");
                            intent.putExtra("Connect", "Off");
                            UsbHostManager.m1019$$Nest$mbroadcastWithPendingQueue(this.this$0, intent, UserHandle.ALL);
                            return;
                        } else {
                            if (str2.equals("ADD")) {
                                Intent intent2 = new Intent("com.samsung.UsbOtgCableConnection");
                                intent2.putExtra("Connect", "On");
                                UsbHostManager.m1019$$Nest$mbroadcastWithPendingQueue(this.this$0, intent2, UserHandle.ALL);
                                return;
                            }
                            return;
                        }
                    default:
                        SimpleDateFormat simpleDateFormat2 = UsbHostManager.sFormat;
                        Slog.d("UsbHostManager", "onUEvent(USB Control): " + uEvent);
                        String str3 = uEvent.get("TYPE");
                        String str4 = uEvent.get("STATE");
                        if ("usbaudio".equals(str3)) {
                            String str5 = uEvent.get("PATH");
                            String str6 = uEvent.get("CARDNUM");
                            if ("ADD".equals(str4)) {
                                UsbAlsaManager usbAlsaManager2 = this.this$0.mUsbAlsaManager;
                                int parseInt = Integer.parseInt(str6);
                                usbAlsaManager2.getClass();
                                Slog.d("UsbAlsaManager", "usbDeviceAddedBundle(): deviceAddress=" + str5 + " cardNum=" + parseInt);
                                if (usbAlsaManager2.mAudioService == null) {
                                    Slog.e("UsbAlsaManager", "no AudioService");
                                    return;
                                }
                                if (Settings.Secure.getInt(usbAlsaManager2.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0) != 0) {
                                    Slog.e("UsbAlsaManager", "Disable USB audio routing is ON at usbDeviceAddedBundle");
                                    return;
                                }
                                UsbAlsaDevice usbAlsaDevice = new UsbAlsaDevice(usbAlsaManager2.mAudioService, parseInt, str5, true, true, true, true, false);
                                usbAlsaManager2.mSelectedBundleDevice = usbAlsaDevice;
                                usbAlsaManager2.isBundleRemovedDone = false;
                                synchronized (usbAlsaDevice) {
                                    usbAlsaDevice.mDeviceName = "USB-Audio - Samsung USB C Earphone";
                                }
                                usbAlsaDevice.updateWiredDeviceConnectionStateByBundle(true);
                                return;
                            }
                            if ("REMOVE".equals(str4)) {
                                UsbAlsaManager usbAlsaManager3 = this.this$0.mUsbAlsaManager;
                                int parseInt2 = Integer.parseInt(str6);
                                usbAlsaManager3.getClass();
                                Slog.d("UsbAlsaManager", "usbDeviceRemovedBundle(): deviceAddress=" + str5 + " cardNum=" + parseInt2);
                                if (usbAlsaManager3.mAudioService == null) {
                                    Slog.e("UsbAlsaManager", "no AudioService");
                                    return;
                                }
                                if (Settings.Secure.getInt(usbAlsaManager3.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0) != 0) {
                                    Slog.e("UsbAlsaManager", "Disable USB audio routing is ON at usbDeviceRemovedBundle");
                                    return;
                                }
                                if (usbAlsaManager3.mSelectedBundleDevice == null) {
                                    Slog.e("UsbAlsaManager", "No Selected Bundel Device at usbDeviceRemovedBundle");
                                    return;
                                }
                                UsbAlsaDevice usbAlsaDevice2 = new UsbAlsaDevice(usbAlsaManager3.mAudioService, parseInt2, str5, true, true, true, true, false);
                                usbAlsaManager3.mSelectedBundleDevice = null;
                                usbAlsaManager3.isBundleRemovedDone = true;
                                synchronized (usbAlsaDevice2) {
                                    usbAlsaDevice2.mDeviceName = "USB-Audio - Samsung USB C Earphone";
                                }
                                usbAlsaDevice2.updateWiredDeviceConnectionStateByBundle(false);
                                return;
                            }
                            return;
                        }
                        return;
                }
            }
        };
        final int i2 = 1;
        UEventObserver uEventObserver2 = new UEventObserver(this) { // from class: com.android.server.usb.UsbHostManager.1
            public final /* synthetic */ UsbHostManager this$0;

            {
                this.this$0 = this;
            }

            public final void onUEvent(UEventObserver.UEvent uEvent) {
                switch (i2) {
                    case 0:
                        SimpleDateFormat simpleDateFormat = UsbHostManager.sFormat;
                        Slog.v("UsbHostManager", "onUEvent(Host Path): " + uEvent);
                        String str = uEvent.get("ACTION");
                        String str2 = uEvent.get("STATE");
                        if (!"change".equals(str) || str2 == null) {
                            return;
                        }
                        if (str2.equals("REMOVE")) {
                            Intent intent = new Intent("com.samsung.UsbOtgCableConnection");
                            intent.putExtra("Connect", "Off");
                            UsbHostManager.m1019$$Nest$mbroadcastWithPendingQueue(this.this$0, intent, UserHandle.ALL);
                            return;
                        } else {
                            if (str2.equals("ADD")) {
                                Intent intent2 = new Intent("com.samsung.UsbOtgCableConnection");
                                intent2.putExtra("Connect", "On");
                                UsbHostManager.m1019$$Nest$mbroadcastWithPendingQueue(this.this$0, intent2, UserHandle.ALL);
                                return;
                            }
                            return;
                        }
                    default:
                        SimpleDateFormat simpleDateFormat2 = UsbHostManager.sFormat;
                        Slog.d("UsbHostManager", "onUEvent(USB Control): " + uEvent);
                        String str3 = uEvent.get("TYPE");
                        String str4 = uEvent.get("STATE");
                        if ("usbaudio".equals(str3)) {
                            String str5 = uEvent.get("PATH");
                            String str6 = uEvent.get("CARDNUM");
                            if ("ADD".equals(str4)) {
                                UsbAlsaManager usbAlsaManager2 = this.this$0.mUsbAlsaManager;
                                int parseInt = Integer.parseInt(str6);
                                usbAlsaManager2.getClass();
                                Slog.d("UsbAlsaManager", "usbDeviceAddedBundle(): deviceAddress=" + str5 + " cardNum=" + parseInt);
                                if (usbAlsaManager2.mAudioService == null) {
                                    Slog.e("UsbAlsaManager", "no AudioService");
                                    return;
                                }
                                if (Settings.Secure.getInt(usbAlsaManager2.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0) != 0) {
                                    Slog.e("UsbAlsaManager", "Disable USB audio routing is ON at usbDeviceAddedBundle");
                                    return;
                                }
                                UsbAlsaDevice usbAlsaDevice = new UsbAlsaDevice(usbAlsaManager2.mAudioService, parseInt, str5, true, true, true, true, false);
                                usbAlsaManager2.mSelectedBundleDevice = usbAlsaDevice;
                                usbAlsaManager2.isBundleRemovedDone = false;
                                synchronized (usbAlsaDevice) {
                                    usbAlsaDevice.mDeviceName = "USB-Audio - Samsung USB C Earphone";
                                }
                                usbAlsaDevice.updateWiredDeviceConnectionStateByBundle(true);
                                return;
                            }
                            if ("REMOVE".equals(str4)) {
                                UsbAlsaManager usbAlsaManager3 = this.this$0.mUsbAlsaManager;
                                int parseInt2 = Integer.parseInt(str6);
                                usbAlsaManager3.getClass();
                                Slog.d("UsbAlsaManager", "usbDeviceRemovedBundle(): deviceAddress=" + str5 + " cardNum=" + parseInt2);
                                if (usbAlsaManager3.mAudioService == null) {
                                    Slog.e("UsbAlsaManager", "no AudioService");
                                    return;
                                }
                                if (Settings.Secure.getInt(usbAlsaManager3.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0) != 0) {
                                    Slog.e("UsbAlsaManager", "Disable USB audio routing is ON at usbDeviceRemovedBundle");
                                    return;
                                }
                                if (usbAlsaManager3.mSelectedBundleDevice == null) {
                                    Slog.e("UsbAlsaManager", "No Selected Bundel Device at usbDeviceRemovedBundle");
                                    return;
                                }
                                UsbAlsaDevice usbAlsaDevice2 = new UsbAlsaDevice(usbAlsaManager3.mAudioService, parseInt2, str5, true, true, true, true, false);
                                usbAlsaManager3.mSelectedBundleDevice = null;
                                usbAlsaManager3.isBundleRemovedDone = true;
                                synchronized (usbAlsaDevice2) {
                                    usbAlsaDevice2.mDeviceName = "USB-Audio - Samsung USB C Earphone";
                                }
                                usbAlsaDevice2.updateWiredDeviceConnectionStateByBundle(false);
                                return;
                            }
                            return;
                        }
                        return;
                }
            }
        };
        this.mSystemReady = false;
        this.mPendingIntentLock = new Object();
        this.mPendingIntent = new ArrayList();
        this.mContext = context;
        this.mHostDenyList = context.getResources().getStringArray(17236343);
        this.mUsbAlsaManager = usbAlsaManager;
        this.mPermissionManager = usbPermissionManager;
        String string = context.getResources().getString(R.string.config_work_badge_path_24);
        if (!TextUtils.isEmpty(string)) {
            Slog.d("UsbHostManager", "deviceConnectionHandler is not empty");
            ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
            synchronized (this.mHandlerLock) {
                this.mUsbDeviceConnectionHandler = unflattenFromString;
            }
        }
        uEventObserver.startObserving("DEVPATH=/devices/virtual/host_notify");
        uEventObserver2.startObserving("DEVPATH=/devices/virtual/usb_notify/usb_control");
        this.mHasMidiFeature = context.getPackageManager().hasSystemFeature("android.software.midi");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void monitorUsbHostBus();

    private native ParcelFileDescriptor nativeOpenDevice(String str);

    public final void addConnectionRecord(int i, String str, byte[] bArr) {
        this.mNumConnects++;
        while (this.mConnections.size() >= 32) {
            this.mConnections.removeFirst();
        }
        ConnectionRecord connectionRecord = new ConnectionRecord(i, str, bArr);
        this.mConnections.add(connectionRecord);
        if (i != -1) {
            this.mLastConnect = connectionRecord;
        }
        if (i == 0) {
            this.mConnected.put(str, connectionRecord);
        } else if (i == -1) {
            this.mConnected.remove(str);
        }
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long start = dualDumpOutputStream.start("host_manager", 1146756268034L);
        synchronized (this.mHandlerLock) {
            try {
                ComponentName componentName = this.mUsbDeviceConnectionHandler;
                if (componentName != null) {
                    DumpUtils.writeComponentName(dualDumpOutputStream, "default_usb_host_connection_handler", 1146756268033L, componentName);
                }
            } finally {
            }
        }
        synchronized (this.mLock) {
            try {
                Iterator it = this.mDevices.keySet().iterator();
                while (it.hasNext()) {
                    com.android.internal.usb.DumpUtils.writeDevice(dualDumpOutputStream, "devices", 2246267895810L, (UsbDevice) this.mDevices.get((String) it.next()));
                }
                dualDumpOutputStream.write("num_connects", 1120986464259L, this.mNumConnects);
                Iterator it2 = this.mConnections.iterator();
                while (it2.hasNext()) {
                    ((ConnectionRecord) it2.next()).dump(dualDumpOutputStream);
                }
                Iterator it3 = this.mMidiDevices.values().iterator();
                while (it3.hasNext()) {
                    Iterator it4 = ((ArrayList) it3.next()).iterator();
                    while (it4.hasNext()) {
                        ((UsbDirectMidiDevice) it4.next()).dump(dualDumpOutputStream);
                    }
                }
            } finally {
            }
        }
        dualDumpOutputStream.end(start);
    }

    public final void dumpDescriptors(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        if (this.mLastConnect == null) {
            indentingPrintWriter.println("No USB Devices have been connected.");
            return;
        }
        indentingPrintWriter.println("Last Connected USB Device:");
        byte b = 1;
        if (strArr.length <= 1 || strArr[1].equals("-dump-short")) {
            ConnectionRecord connectionRecord = this.mLastConnect;
            String str = connectionRecord.mDeviceAddress;
            int i = connectionRecord.mMode;
            if (i == -1) {
                indentingPrintWriter.println(connectionRecord.formatTime() + " Disconnect " + str);
                return;
            }
            indentingPrintWriter.println(connectionRecord.formatTime() + " Connect " + str + " mode:" + i);
            UsbDescriptorParser usbDescriptorParser = new UsbDescriptorParser(str, connectionRecord.mDescriptors);
            UsbDeviceDescriptor usbDeviceDescriptor = usbDescriptorParser.mDeviceDescriptor;
            StringBuilder sb = new StringBuilder("manfacturer:0x");
            BatteryService$$ExternalSyntheticOutline0.m(usbDeviceDescriptor.mVendorID, sb, " product:");
            sb.append(Integer.toHexString(usbDeviceDescriptor.mProductID));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("isHeadset[in: " + usbDescriptorParser.isInputHeadset() + " , out: " + usbDescriptorParser.isOutputHeadset() + "], isDock: " + usbDescriptorParser.isDock());
            return;
        }
        if (strArr[1].equals("-dump-tree")) {
            ConnectionRecord connectionRecord2 = this.mLastConnect;
            String str2 = connectionRecord2.mDeviceAddress;
            int i2 = connectionRecord2.mMode;
            if (i2 == -1) {
                indentingPrintWriter.println(connectionRecord2.formatTime() + " Disconnect " + str2);
                return;
            }
            indentingPrintWriter.println(connectionRecord2.formatTime() + " Connect " + str2 + " mode:" + i2);
            UsbDescriptorParser usbDescriptorParser2 = new UsbDescriptorParser(str2, connectionRecord2.mDescriptors);
            StringBuilder sb2 = new StringBuilder();
            ArrayList arrayList = usbDescriptorParser2.mDescriptors;
            UsbDescriptorsInterfaceNode usbDescriptorsInterfaceNode = null;
            UsbDescriptorsConfigNode usbDescriptorsConfigNode = null;
            UsbDescriptorsDeviceNode usbDescriptorsDeviceNode = null;
            int i3 = 0;
            while (i3 < arrayList.size()) {
                UsbDescriptor usbDescriptor = (UsbDescriptor) arrayList.get(i3);
                byte b2 = usbDescriptor.mType;
                if (b2 == b) {
                    usbDescriptorsDeviceNode = new UsbDescriptorsDeviceNode((UsbDeviceDescriptor) usbDescriptor);
                } else if (b2 == 2) {
                    usbDescriptorsConfigNode = new UsbDescriptorsConfigNode((UsbConfigDescriptor) usbDescriptor);
                    usbDescriptorsDeviceNode.mConfigNodes.add(usbDescriptorsConfigNode);
                } else if (b2 == 4) {
                    usbDescriptorsInterfaceNode = new UsbDescriptorsInterfaceNode((UsbInterfaceDescriptor) usbDescriptor);
                    usbDescriptorsConfigNode.mInterfaceNodes.add(usbDescriptorsInterfaceNode);
                } else if (b2 == 5) {
                    usbDescriptorsInterfaceNode.mEndpointNodes.add(new UsbDescriptorsEndpointNode((UsbEndpointDescriptor) usbDescriptor));
                }
                i3++;
                b = 1;
            }
            TextReportCanvas textReportCanvas = new TextReportCanvas(usbDescriptorParser2, sb2);
            usbDescriptorsDeviceNode.mDeviceDescriptor.report(textReportCanvas);
            Iterator it = usbDescriptorsDeviceNode.mConfigNodes.iterator();
            while (it.hasNext()) {
                UsbDescriptorsConfigNode usbDescriptorsConfigNode2 = (UsbDescriptorsConfigNode) it.next();
                usbDescriptorsConfigNode2.mConfigDescriptor.report(textReportCanvas);
                textReportCanvas.openList();
                Iterator it2 = usbDescriptorsConfigNode2.mInterfaceNodes.iterator();
                while (it2.hasNext()) {
                    UsbDescriptorsInterfaceNode usbDescriptorsInterfaceNode2 = (UsbDescriptorsInterfaceNode) it2.next();
                    usbDescriptorsInterfaceNode2.mInterfaceDescriptor.report(textReportCanvas);
                    if (usbDescriptorsInterfaceNode2.mACInterfaceNodes.size() > 0) {
                        textReportCanvas.writeParagraph("Audio Class Interfaces", false);
                        textReportCanvas.openList();
                        Iterator it3 = usbDescriptorsInterfaceNode2.mACInterfaceNodes.iterator();
                        if (it3.hasNext()) {
                            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(it3.next());
                            throw null;
                        }
                        textReportCanvas.closeList();
                    }
                    if (usbDescriptorsInterfaceNode2.mEndpointNodes.size() > 0) {
                        textReportCanvas.writeParagraph("Endpoints", false);
                        textReportCanvas.openList();
                        Iterator it4 = usbDescriptorsInterfaceNode2.mEndpointNodes.iterator();
                        while (it4.hasNext()) {
                            ((UsbDescriptorsEndpointNode) it4.next()).mEndpointDescriptor.report(textReportCanvas);
                        }
                        textReportCanvas.closeList();
                    }
                }
                textReportCanvas.closeList();
            }
            sb2.append("isHeadset[in: " + usbDescriptorParser2.isInputHeadset() + " , out: " + usbDescriptorParser2.isOutputHeadset() + "], isDock: " + usbDescriptorParser2.isDock());
            indentingPrintWriter.println(sb2.toString());
            return;
        }
        if (strArr[1].equals("-dump-list")) {
            ConnectionRecord connectionRecord3 = this.mLastConnect;
            String str3 = connectionRecord3.mDeviceAddress;
            int i4 = connectionRecord3.mMode;
            if (i4 == -1) {
                indentingPrintWriter.println(connectionRecord3.formatTime() + " Disconnect " + str3);
                return;
            }
            indentingPrintWriter.println(connectionRecord3.formatTime() + " Connect " + str3 + " mode:" + i4);
            UsbDescriptorParser usbDescriptorParser3 = new UsbDescriptorParser(str3, connectionRecord3.mDescriptors);
            StringBuilder sb3 = new StringBuilder();
            TextReportCanvas textReportCanvas2 = new TextReportCanvas(usbDescriptorParser3, sb3);
            Iterator it5 = usbDescriptorParser3.mDescriptors.iterator();
            while (it5.hasNext()) {
                ((UsbDescriptor) it5.next()).report(textReportCanvas2);
            }
            indentingPrintWriter.println(sb3.toString());
            indentingPrintWriter.println("isHeadset[in: " + usbDescriptorParser3.isInputHeadset() + " , out: " + usbDescriptorParser3.isOutputHeadset() + "], isDock: " + usbDescriptorParser3.isDock());
            return;
        }
        if (strArr[1].equals("-dump-raw")) {
            ConnectionRecord connectionRecord4 = this.mLastConnect;
            String str4 = connectionRecord4.mDeviceAddress;
            int i5 = connectionRecord4.mMode;
            if (i5 == -1) {
                indentingPrintWriter.println(connectionRecord4.formatTime() + " Disconnect " + str4);
                return;
            }
            indentingPrintWriter.println(connectionRecord4.formatTime() + " Connect " + str4 + " mode:" + i5);
            byte[] bArr = connectionRecord4.mDescriptors;
            int length = bArr.length;
            StringBuilder sb4 = new StringBuilder("Raw Descriptors ");
            sb4.append(length);
            sb4.append(" bytes");
            indentingPrintWriter.println(sb4.toString());
            int i6 = 0;
            for (int i7 = 0; i7 < length / 16; i7++) {
                StringBuilder sb5 = new StringBuilder();
                int i8 = 0;
                while (i8 < 16) {
                    sb5.append(String.format("0x%02X", Byte.valueOf(bArr[i6])));
                    sb5.append(" ");
                    i8++;
                    i6++;
                }
                indentingPrintWriter.println(sb5.toString());
            }
            StringBuilder sb6 = new StringBuilder();
            while (i6 < length) {
                sb6.append(String.format("0x%02X", Byte.valueOf(bArr[i6])));
                sb6.append(" ");
                i6++;
            }
            indentingPrintWriter.println(sb6.toString());
        }
    }

    public final String generateNewUsbDeviceIdentifier() {
        String str;
        int i = 0;
        do {
            if (i > 10) {
                Slog.w("UsbHostManager", "MIDI unique code array resetting");
                this.mMidiUniqueCodes.clear();
                i = 0;
            }
            str = "";
            for (int i2 = 0; i2 < 3; i2++) {
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                m.append(this.mRandom.nextInt(10));
                str = m.toString();
            }
            i++;
        } while (this.mMidiUniqueCodes.contains(str));
        this.mMidiUniqueCodes.add(str);
        return str;
    }

    public final UsbProfileGroupSettingsManager getCurrentUserSettings() {
        UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
        synchronized (this.mSettingsLock) {
            usbProfileGroupSettingsManager = this.mCurrentSettings;
        }
        return usbProfileGroupSettingsManager;
    }

    public final ParcelFileDescriptor openDevice(String str, UsbUserPermissionManager usbUserPermissionManager, String str2, int i, int i2) {
        ParcelFileDescriptor nativeOpenDevice;
        synchronized (this.mLock) {
            try {
                for (String str3 : this.mHostDenyList) {
                    if (str.startsWith(str3)) {
                        throw new SecurityException("USB device is on a restricted bus");
                    }
                }
                UsbDevice usbDevice = (UsbDevice) this.mDevices.get(str);
                if (usbDevice == null) {
                    throw new IllegalArgumentException("device " + str + " does not exist or is restricted");
                }
                usbUserPermissionManager.checkPermission(usbDevice, str2, i, i2);
                nativeOpenDevice = nativeOpenDevice(str);
            } catch (Throwable th) {
                throw th;
            }
        }
        return nativeOpenDevice;
    }

    public final boolean usbDeviceAdded(String str, int i, int i2, byte[] bArr) {
        boolean z;
        String str2;
        int i3;
        int i4;
        String str3;
        String str4;
        ComponentName componentName;
        boolean z2;
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "usbDeviceAdded - start: deviceAddress=", str, " deviceClass=", " deviceSubclass=");
        m.append(i2);
        Slog.d("UsbHostManager", m.toString());
        String[] strArr = this.mHostDenyList;
        int length = strArr.length;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                z = false;
                break;
            }
            if (str.startsWith(strArr[i5])) {
                z = true;
                break;
            }
            i5++;
        }
        if (z) {
            Slog.d("UsbHostManager", "device address is Deny listed");
            return false;
        }
        if (i == 9 || (!FactoryTest.isFactoryBinary() && i == 3 && i2 == 1)) {
            Slog.d("UsbHostManager", "device class is deny listed");
            return false;
        }
        if (bArr == null) {
            Slog.e("UsbHostManager", "Failed to add device as the descriptor is null");
            return false;
        }
        UsbDescriptorParser usbDescriptorParser = new UsbDescriptorParser(str, bArr);
        if (i == 0) {
            Iterator it = usbDescriptorParser.mDescriptors.iterator();
            boolean z3 = false;
            while (it.hasNext()) {
                UsbDescriptor usbDescriptor = (UsbDescriptor) it.next();
                if (usbDescriptor instanceof UsbInterfaceDescriptor) {
                    UsbInterfaceDescriptor usbInterfaceDescriptor = (UsbInterfaceDescriptor) usbDescriptor;
                    int i6 = usbInterfaceDescriptor.mUsbClass;
                    z3 = i6 == 9 || (!FactoryTest.isFactoryBinary() && i6 == 3 && usbInterfaceDescriptor.mUsbSubclass == 1);
                    if (!z3) {
                        break;
                    }
                }
            }
            if (z3) {
                Slog.d("UsbHostManager", "usb interface class is deny listed");
                z2 = false;
            } else {
                z2 = true;
            }
            if (!z2) {
                return false;
            }
        }
        UsbDeviceDescriptor usbDeviceDescriptor = usbDescriptorParser.mDeviceDescriptor;
        if (usbDeviceDescriptor != null) {
            i3 = usbDeviceDescriptor.mVendorID;
            i4 = usbDeviceDescriptor.mProductID;
            str2 = usbDescriptorParser.getDescriptorString(usbDeviceDescriptor.mMfgIndex);
            str3 = usbDescriptorParser.getDescriptorString(usbDeviceDescriptor.mProductIndex);
            str4 = usbDeviceDescriptor.getDeviceReleaseString();
            usbDescriptorParser.getDescriptorString(usbDeviceDescriptor.mSerialIndex);
        } else {
            str2 = "<unknown>";
            i3 = 0;
            i4 = 0;
            str3 = "<unknown>";
            str4 = str3;
        }
        if (i3 != 7531) {
            boolean z4 = !usbDescriptorParser.getInterfaceDescriptorsForClass(1).isEmpty();
            boolean hasHIDInterface = usbDescriptorParser.hasHIDInterface();
            boolean z5 = !usbDescriptorParser.getInterfaceDescriptorsForClass(8).isEmpty();
            StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m("USB device attached: ".concat(String.format("vidpid %04x:%04x", Integer.valueOf(i3), Integer.valueOf(i4))));
            StringBuilder m3 = InitialConfiguration$$ExternalSyntheticOutline0.m(" mfg/product/ver/serial ", str2, "/", str3, "/");
            m3.append(str4);
            m3.append("/[hidden value]");
            m2.append(m3.toString());
            StringBuilder m4 = BootReceiver$$ExternalSyntheticOutline0.m(m2.toString());
            StringBuilder m5 = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(" hasAudio/HID/Storage: ", z4, "/", hasHIDInterface, "/");
            m5.append(z5);
            m4.append(m5.toString());
            Slog.d("UsbHostManager", m4.toString());
        }
        synchronized (this.mLock) {
            try {
                if (this.mDevices.get(str) != null) {
                    Slog.w("UsbHostManager", "device already on mDevices list: " + str);
                    return false;
                }
                UsbDevice.Builder androidUsbDeviceBuilder = usbDescriptorParser.toAndroidUsbDeviceBuilder();
                if (androidUsbDeviceBuilder == null) {
                    Slog.e("UsbHostManager", "Couldn't create UsbDevice object.");
                    addConnectionRecord(2, str, usbDescriptorParser.getRawDescriptors());
                } else {
                    UsbSerialReader usbSerialReader = new UsbSerialReader(this.mContext, this.mPermissionManager, androidUsbDeviceBuilder.serialNumber);
                    UsbDevice build = androidUsbDeviceBuilder.build(usbSerialReader);
                    usbSerialReader.mDevice = build;
                    this.mDevices.put(str, build);
                    Slog.d("UsbHostManager", "Added device " + build);
                    if (this.mBootCompleted && this.mCurrentUnlockedUser != -10000) {
                        synchronized (this.mHandlerLock) {
                            componentName = this.mUsbDeviceConnectionHandler;
                        }
                        if (componentName == null) {
                            getCurrentUserSettings().deviceAttached(build);
                        } else {
                            Slog.d("UsbHostManager", "usbDeviceConnectionHandler is not null");
                            getCurrentUserSettings().deviceAttachedForFixedHandler(build, componentName);
                        }
                    }
                    this.mUsbAlsaManager.usbDeviceAdded(str, build, usbDescriptorParser);
                    if (this.mHasMidiFeature) {
                        String generateNewUsbDeviceIdentifier = generateNewUsbDeviceIdentifier();
                        ArrayList arrayList = new ArrayList();
                        if (UsbDescriptorParser.doesInterfaceContainEndpoint(usbDescriptorParser.findMidiInterfaceDescriptors(512))) {
                            UsbDirectMidiDevice create = UsbDirectMidiDevice.create(this.mContext, build, usbDescriptorParser, true, generateNewUsbDeviceIdentifier);
                            if (create != null) {
                                arrayList.add(create);
                            } else {
                                Slog.e("UsbHostManager", "Universal Midi Device is null.");
                            }
                            if (UsbDescriptorParser.doesInterfaceContainEndpoint(usbDescriptorParser.findMidiInterfaceDescriptors(256))) {
                                UsbDirectMidiDevice create2 = UsbDirectMidiDevice.create(this.mContext, build, usbDescriptorParser, false, generateNewUsbDeviceIdentifier);
                                if (create2 != null) {
                                    arrayList.add(create2);
                                } else {
                                    Slog.e("UsbHostManager", "Legacy Midi Device is null.");
                                }
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            this.mMidiDevices.put(str, arrayList);
                        }
                    }
                    addConnectionRecord(0, str, bArr);
                    FrameworkStatsLog.write(77, build.getVendorId(), build.getProductId(), !usbDescriptorParser.getInterfaceDescriptorsForClass(1).isEmpty(), usbDescriptorParser.hasHIDInterface(), !usbDescriptorParser.getInterfaceDescriptorsForClass(8).isEmpty(), 1, 0L);
                }
                Slog.d("UsbHostManager", "usbDeviceAdded - end");
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void usbDeviceRemoved(String str) {
        Slog.d("UsbHostManager", "usbDeviceRemoved - start: deviceAddress=" + str);
        synchronized (this.mLock) {
            try {
                UsbDevice usbDevice = (UsbDevice) this.mDevices.remove(str);
                if (usbDevice != null) {
                    Slog.d("UsbHostManager", "Removed device at " + str + ": " + usbDevice.getProductName());
                    this.mUsbAlsaManager.usbDeviceRemoved(str);
                    this.mPermissionManager.usbDeviceRemoved(usbDevice);
                    ArrayList arrayList = (ArrayList) this.mMidiDevices.remove(str);
                    if (arrayList != null) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            UsbDirectMidiDevice usbDirectMidiDevice = (UsbDirectMidiDevice) it.next();
                            if (usbDirectMidiDevice != null) {
                                IoUtils.closeQuietly(usbDirectMidiDevice);
                            }
                        }
                        Slog.i("UsbHostManager", "USB MIDI Devices Removed: " + str);
                    }
                    getCurrentUserSettings().getClass();
                    ConnectionRecord connectionRecord = (ConnectionRecord) this.mConnected.get(str);
                    addConnectionRecord(-1, str, null);
                    if (connectionRecord != null) {
                        FrameworkStatsLog.write(77, usbDevice.getVendorId(), usbDevice.getProductId(), !r13.getInterfaceDescriptorsForClass(1).isEmpty(), new UsbDescriptorParser(str, connectionRecord.mDescriptors).hasHIDInterface(), !r13.getInterfaceDescriptorsForClass(8).isEmpty(), 0, System.currentTimeMillis() - connectionRecord.mTimestamp);
                    }
                } else {
                    Slog.d("UsbHostManager", "Removed device at " + str + " was already gone");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Slog.d("UsbHostManager", "usbDeviceRemoved - end");
    }
}
