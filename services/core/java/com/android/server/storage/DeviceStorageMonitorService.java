package com.android.server.storage;

import android.R;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.ArrayMap;
import android.util.DataUnit;
import android.util.EventLog;
import android.util.LocalLog;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DeviceStorageMonitorService extends SystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long BOOT_IMAGE_STORAGE_REQUIREMENT;
    public static final long DEFAULT_LOG_DELTA_BYTES;
    public static long mCautionBytes;
    public static long mExhaustionBytes;
    public static long mFullBytes;
    public static long mFullFileNodes;
    public static long mLowBytes;
    public static long mLowFileNodes;
    public static long mRomTotalBytes;
    public static long mTotalBytes;
    public static long mTotalFileNode;
    public static long mUsableBytes;
    public static long mUsableFileNode;
    public static long mWarningBytes;
    public final DeviceStorageMonitorYuva dsm_yuva;
    public volatile int mFnForceLevel;
    public final ArrayMap mFnStates;
    public volatile int mForceLevel;
    public final AnonymousClass1 mHandler;
    public final LocalLog mLocalLog;
    public final AnonymousClass2 mLocalService;
    public NotificationManager mNotifManager;
    public final AnonymousClass3 mRemoteService;
    public final AtomicInteger mSeq;
    public final ArrayMap mStates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.storage.DeviceStorageMonitorService$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final boolean isMemoryLow() {
            return Environment.getDataDirectory().getUsableSpace() < ((StorageManager) DeviceStorageMonitorService.this.getContext().getSystemService(StorageManager.class)).getStorageLowBytes(Environment.getDataDirectory());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CacheFileDeletedObserver extends FileObserver {
        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            EventLog.writeEvent(2748, str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FileNodeState {
        public int level;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Shell extends ShellCommand {
        public Shell() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final int onCommand(String str) {
            char c;
            DeviceStorageMonitorService deviceStorageMonitorService = DeviceStorageMonitorService.this;
            deviceStorageMonitorService.getClass();
            if (str == null) {
                return handleDefaultCommands(str);
            }
            PrintWriter outPrintWriter = getOutPrintWriter();
            switch (str.hashCode()) {
                case 108404047:
                    if (str.equals("reset")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1526871410:
                    if (str.equals("force-low")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1692300408:
                    if (str.equals("force-not-low")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int parseOptions = DeviceStorageMonitorService.parseOptions(this);
                    deviceStorageMonitorService.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    deviceStorageMonitorService.mForceLevel = -1;
                    int incrementAndGet = deviceStorageMonitorService.mSeq.incrementAndGet();
                    if ((parseOptions & 1) == 0) {
                        return 0;
                    }
                    deviceStorageMonitorService.mHandler.removeMessages(1);
                    deviceStorageMonitorService.mHandler.obtainMessage(1).sendToTarget();
                    outPrintWriter.println(incrementAndGet);
                    return 0;
                case 1:
                    int parseOptions2 = DeviceStorageMonitorService.parseOptions(this);
                    deviceStorageMonitorService.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    deviceStorageMonitorService.mForceLevel = 3;
                    int incrementAndGet2 = deviceStorageMonitorService.mSeq.incrementAndGet();
                    if ((parseOptions2 & 1) == 0) {
                        return 0;
                    }
                    deviceStorageMonitorService.mHandler.removeMessages(1);
                    deviceStorageMonitorService.mHandler.obtainMessage(1).sendToTarget();
                    outPrintWriter.println(incrementAndGet2);
                    return 0;
                case 2:
                    int parseOptions3 = DeviceStorageMonitorService.parseOptions(this);
                    deviceStorageMonitorService.getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                    deviceStorageMonitorService.mForceLevel = 0;
                    int incrementAndGet3 = deviceStorageMonitorService.mSeq.incrementAndGet();
                    if ((parseOptions3 & 1) == 0) {
                        return 0;
                    }
                    deviceStorageMonitorService.mHandler.removeMessages(1);
                    deviceStorageMonitorService.mHandler.obtainMessage(1).sendToTarget();
                    outPrintWriter.println(incrementAndGet3);
                    return 0;
                default:
                    return handleDefaultCommands(str);
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            int i = DeviceStorageMonitorService.$r8$clinit;
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "Device storage monitor service (devicestoragemonitor) commands:", "  help", "    Print this help text.", "  force-low [-f]");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Force storage to be low, freezing storage state.", "    -f: force a storage change broadcast be sent, prints new sequence.", "  force-not-low [-f]", "    Force storage to not be low, freezing storage state.");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    -f: force a storage change broadcast be sent, prints new sequence.", "  reset [-f]", "    Unfreeze storage state, returning to current real values.", "    -f: force a storage change broadcast be sent, prints new sequence.");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class State {
        public static boolean isExhaustion;
        public long lastUsableBytes;
        public int level;

        /* renamed from: -$$Nest$smisEntering, reason: not valid java name */
        public static boolean m962$$Nest$smisEntering(int i, int i2, int i3) {
            return i3 >= i && (i2 < i || i2 == -1);
        }

        /* renamed from: -$$Nest$smisLeaving, reason: not valid java name */
        public static boolean m963$$Nest$smisLeaving(int i, int i2, int i3) {
            return i3 < i && (i2 >= i || i2 == -1);
        }

        /* renamed from: -$$Nest$smlevelToString, reason: not valid java name */
        public static String m964$$Nest$smlevelToString(int i) {
            return i != -1 ? i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? Integer.toString(i) : "FULL" : "LOW" : "WARNING" : "CAUTION" : "NORMAL" : "UNKNOWN";
        }
    }

    static {
        DataUnit dataUnit = DataUnit.MEBIBYTES;
        DEFAULT_LOG_DELTA_BYTES = dataUnit.toBytes(64L);
        BOOT_IMAGE_STORAGE_REQUIREMENT = dataUnit.toBytes(250L);
        mFullBytes = 0L;
        mLowBytes = 0L;
        mWarningBytes = 0L;
        mCautionBytes = 0L;
        mExhaustionBytes = 0L;
        mTotalBytes = 0L;
        mUsableBytes = 0L;
        mFullFileNodes = 0L;
        mLowFileNodes = 0L;
        mTotalFileNode = 0L;
        mUsableFileNode = 0L;
        mRomTotalBytes = 0L;
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.storage.DeviceStorageMonitorService$3] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.storage.DeviceStorageMonitorService$1] */
    public DeviceStorageMonitorService(Context context) {
        super(context);
        this.mSeq = new AtomicInteger(1);
        this.mForceLevel = -1;
        this.mFnForceLevel = -1;
        this.mStates = new ArrayMap();
        this.mFnStates = new ArrayMap();
        this.mLocalService = new AnonymousClass2();
        this.mRemoteService = new Binder() { // from class: com.android.server.storage.DeviceStorageMonitorService.3
            @Override // android.os.Binder
            public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                if (DumpUtils.checkDumpPermission(DeviceStorageMonitorService.this.getContext(), "DeviceStorageMonitorService", printWriter)) {
                    DeviceStorageMonitorService deviceStorageMonitorService = DeviceStorageMonitorService.this;
                    deviceStorageMonitorService.getClass();
                    IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                    if (strArr != null && strArr.length != 0 && !"-a".equals(strArr[0])) {
                        deviceStorageMonitorService.new Shell().exec(deviceStorageMonitorService.mRemoteService, (FileDescriptor) null, fileDescriptor, (FileDescriptor) null, strArr, (ShellCallback) null, new ResultReceiver(null));
                        return;
                    }
                    StorageManager storageManager = (StorageManager) deviceStorageMonitorService.getContext().getSystemService(StorageManager.class);
                    indentingPrintWriter.println("Known volumes:");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < deviceStorageMonitorService.mStates.size(); i++) {
                        UUID uuid = (UUID) deviceStorageMonitorService.mStates.keyAt(i);
                        State state = (State) deviceStorageMonitorService.mStates.valueAt(i);
                        if (StorageManager.UUID_DEFAULT.equals(uuid)) {
                            indentingPrintWriter.println("Default:");
                        } else {
                            indentingPrintWriter.println(uuid + ":");
                        }
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.printPair("level", State.m964$$Nest$smlevelToString(state.level));
                        indentingPrintWriter.printPair("lastUsableBytes", Long.valueOf(state.lastUsableBytes));
                        indentingPrintWriter.println();
                        Iterator it = storageManager.getWritablePrivateVolumes().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                VolumeInfo volumeInfo = (VolumeInfo) it.next();
                                File path = volumeInfo.getPath();
                                if (Objects.equals(uuid, StorageManager.convert(volumeInfo.getFsUuid()))) {
                                    indentingPrintWriter.print("lowBytes=");
                                    indentingPrintWriter.print(storageManager.getStorageLowBytes(path));
                                    indentingPrintWriter.print(" fullBytes=");
                                    indentingPrintWriter.println(storageManager.getStorageFullBytes(path));
                                    indentingPrintWriter.print("path=");
                                    indentingPrintWriter.println(path);
                                    break;
                                }
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    for (int i2 = 0; i2 < deviceStorageMonitorService.mFnStates.size(); i2++) {
                        UUID uuid2 = (UUID) deviceStorageMonitorService.mFnStates.keyAt(i2);
                        FileNodeState fileNodeState = (FileNodeState) deviceStorageMonitorService.mFnStates.valueAt(i2);
                        if (StorageManager.UUID_DEFAULT.equals(uuid2)) {
                            indentingPrintWriter.println("Default:");
                        } else {
                            indentingPrintWriter.println(uuid2 + ":");
                        }
                        indentingPrintWriter.increaseIndent();
                        int i3 = fileNodeState.level;
                        indentingPrintWriter.printPair("level", i3 != -1 ? i3 != 0 ? i3 != 1 ? i3 != 2 ? Integer.toString(i3) : "FN_FULL" : "FN_LOW" : "FN_NORMAL" : "FN_UNKNOWN");
                        indentingPrintWriter.println();
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    indentingPrintWriter.printPair("mSeq", Integer.valueOf(deviceStorageMonitorService.mSeq.get()));
                    indentingPrintWriter.printPair("mForceState", State.m964$$Nest$smlevelToString(deviceStorageMonitorService.mForceLevel));
                    indentingPrintWriter.println();
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Storage info : ");
                    indentingPrintWriter.println("  mRomTotalBytes : " + DeviceStorageMonitorService.mRomTotalBytes);
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("  mFullBytes : " + DeviceStorageMonitorService.mFullBytes);
                    indentingPrintWriter.println("  mLowBytes : " + DeviceStorageMonitorService.mLowBytes);
                    indentingPrintWriter.println("  mWarningBytes : " + DeviceStorageMonitorService.mWarningBytes);
                    indentingPrintWriter.println("  mCautionBytes : " + DeviceStorageMonitorService.mCautionBytes);
                    indentingPrintWriter.println("  mExhaustionBytes : " + DeviceStorageMonitorService.mExhaustionBytes);
                    indentingPrintWriter.println("  mTotalBytes : " + DeviceStorageMonitorService.mTotalBytes);
                    indentingPrintWriter.println("  mUsableBytes : " + DeviceStorageMonitorService.mUsableBytes);
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("  mFullFileNodes : " + DeviceStorageMonitorService.mFullFileNodes);
                    indentingPrintWriter.println("  mLowFileNodes : " + DeviceStorageMonitorService.mLowFileNodes);
                    indentingPrintWriter.println("  mTotalFileNode : " + DeviceStorageMonitorService.mTotalFileNode);
                    indentingPrintWriter.println("  mUsableFileNode : " + DeviceStorageMonitorService.mUsableFileNode);
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Log history : ");
                    deviceStorageMonitorService.mLocalLog.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.println();
                }
            }

            public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
                DeviceStorageMonitorService.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        };
        HandlerThread handlerThread = new HandlerThread("DeviceStorageMonitorService", 10);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper()) { // from class: com.android.server.storage.DeviceStorageMonitorService.1
            /* JADX WARN: Removed duplicated region for block: B:100:0x032b  */
            /* JADX WARN: Removed duplicated region for block: B:103:0x035f  */
            /* JADX WARN: Removed duplicated region for block: B:107:0x038b A[ADDED_TO_REGION] */
            /* JADX WARN: Removed duplicated region for block: B:111:0x03ed  */
            /* JADX WARN: Removed duplicated region for block: B:114:0x0733  */
            /* JADX WARN: Removed duplicated region for block: B:118:0x0758  */
            /* JADX WARN: Removed duplicated region for block: B:121:0x0770  */
            /* JADX WARN: Removed duplicated region for block: B:124:0x0786  */
            /* JADX WARN: Removed duplicated region for block: B:127:0x079e  */
            /* JADX WARN: Removed duplicated region for block: B:130:0x07c9 A[ADDED_TO_REGION] */
            /* JADX WARN: Removed duplicated region for block: B:136:0x07d4  */
            /* JADX WARN: Removed duplicated region for block: B:141:0x08b0  */
            /* JADX WARN: Removed duplicated region for block: B:153:0x0852  */
            /* JADX WARN: Removed duplicated region for block: B:159:0x078b  */
            /* JADX WARN: Removed duplicated region for block: B:166:0x077e  */
            /* JADX WARN: Removed duplicated region for block: B:167:0x073b  */
            /* JADX WARN: Removed duplicated region for block: B:168:0x03f8  */
            /* JADX WARN: Removed duplicated region for block: B:174:0x04e4  */
            /* JADX WARN: Removed duplicated region for block: B:177:0x058c  */
            /* JADX WARN: Removed duplicated region for block: B:180:0x0654  */
            /* JADX WARN: Removed duplicated region for block: B:183:0x06a1  */
            /* JADX WARN: Removed duplicated region for block: B:186:0x05c5  */
            /* JADX WARN: Removed duplicated region for block: B:189:0x051e  */
            /* JADX WARN: Removed duplicated region for block: B:196:0x037b  */
            /* JADX WARN: Removed duplicated region for block: B:197:0x032f  */
            /* JADX WARN: Removed duplicated region for block: B:216:0x0318  */
            /* JADX WARN: Removed duplicated region for block: B:232:0x027b  */
            /* JADX WARN: Removed duplicated region for block: B:239:0x0252  */
            /* JADX WARN: Removed duplicated region for block: B:240:0x018c  */
            /* JADX WARN: Removed duplicated region for block: B:57:0x0185  */
            /* JADX WARN: Removed duplicated region for block: B:60:0x0215  */
            /* JADX WARN: Removed duplicated region for block: B:68:0x0266  */
            /* JADX WARN: Removed duplicated region for block: B:71:0x0291  */
            /* JADX WARN: Removed duplicated region for block: B:76:0x029f  */
            /* JADX WARN: Removed duplicated region for block: B:81:0x02b0  */
            /* JADX WARN: Removed duplicated region for block: B:95:0x030a  */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void handleMessage(android.os.Message r49) {
                /*
                    Method dump skipped, instructions count: 2475
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.storage.DeviceStorageMonitorService.AnonymousClass1.handleMessage(android.os.Message):void");
            }
        };
        this.mLocalLog = new LocalLog(20);
        this.dsm_yuva = new DeviceStorageMonitorYuva(context);
    }

    public static int parseOptions(Shell shell) {
        int i = 0;
        while (true) {
            String nextOption = shell.getNextOption();
            if (nextOption == null) {
                return i;
            }
            if ("-f".equals(nextOption)) {
                i = 1;
            }
        }
    }

    public final void loge(String str) {
        Slog.e("DeviceStorageMonitorService", str);
        this.mLocalLog.log(str);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Context context = getContext();
        this.mNotifManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        new CacheFileDeletedObserver(Environment.getDownloadCacheDirectory().getAbsolutePath(), 512).startWatching();
        if (context.getPackageManager().hasSystemFeature("android.software.leanback")) {
            this.mNotifManager.createNotificationChannel(new NotificationChannel("devicestoragemonitor.tv", context.getString(R.string.granularity_label_word), 4));
        }
        publishBinderService("devicestoragemonitor", this.mRemoteService);
        publishLocalService(AnonymousClass2.class, this.mLocalService);
        mRomTotalBytes = FileUtils.roundStorageSize(Environment.getRootDirectory().getTotalSpace() + Environment.getDataDirectory().getTotalSpace());
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.removeMessages(1);
        anonymousClass1.obtainMessage(1).sendToTarget();
    }

    public final void updateExhaustionBroadcasts(VolumeInfo volumeInfo, boolean z, int i) {
        Slog.w("DeviceStorageMonitorService", "updateExhaustionBroadcasts(" + volumeInfo.path + ") seq:" + i);
        Intent putExtra = new Intent("com.samsung.intent.action.DEVICE_STORAGE_EXHAUSTION").addFlags(67108864).addFlags(16777216).putExtra("seq", i);
        Intent putExtra2 = new Intent("com.samsung.intent.action.DEVICE_STORAGE_NOT_EXHAUSTION").addFlags(67108864).putExtra("seq", i);
        if (z) {
            loge("updateExhaustionBroadcasts(" + volumeInfo.path + ") sending intent : ACTION_DEVICE_STORAGE_EXHAUSTION");
            loge("mUsableBytes : " + mUsableBytes + ", mUsableFileNode : " + mUsableFileNode);
            getContext().sendStickyBroadcastAsUser(putExtra, UserHandle.ALL);
            return;
        }
        loge("updateExhaustionBroadcasts(" + volumeInfo.path + ") sending intent : ACTION_DEVICE_STORAGE_NOT_EXHAUSTION");
        loge("mUsableBytes : " + mUsableBytes + ", mUsableFileNode : " + mUsableFileNode);
        Context context = getContext();
        UserHandle userHandle = UserHandle.ALL;
        context.removeStickyBroadcastAsUser(putExtra, userHandle);
        getContext().sendBroadcastAsUser(putExtra2, userHandle);
    }
}
