package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IActivityManager;
import android.app.UidObserver;
import android.app.pinner.IPinnerService;
import android.app.pinner.PinnedFileStat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.app.ResolverActivity;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.flags.Flags;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import dalvik.system.DexFile;
import dalvik.system.VMRuntime;

import sun.misc.Unsafe;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class PinnerService extends SystemService {
    public final ActivityManagerInternal mAmInternal;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final AnonymousClass1 mBroadcastReceiver;
    public final int mConfiguredHomePinBytes;
    public final boolean mConfiguredToPinAssistant;
    public final boolean mConfiguredToPinCamera;
    public final int mConfiguredWebviewPinBytes;
    public final Context mContext;
    public long mCurrentlyPinnedAnonSize;
    public final AnonymousClass2 mDeviceConfigAnonSizeListener;
    public final DeviceConfigInterface mDeviceConfigInterface;
    public final Injector mInjector;
    public final ArrayMap mPendingRepin;
    public long mPinAnonAddress;
    public long mPinAnonSize;
    public ArraySet mPinKeys;
    public final ArrayMap mPinnedApps;
    public final ArrayMap mPinnedFiles;
    public final PinnerHandler mPinnerHandler;
    public final UserManager mUserManager;
    public static final int PAGE_SIZE = (int) Os.sysconf(OsConstants._SC_PAGESIZE);
    public static final boolean PROP_PIN_PINLIST =
            SystemProperties.getBoolean("pinner.use_pinlist", true);
    public static final long DEFAULT_ANON_SIZE =
            SystemProperties.getLong("pinner.pin_shared_anon_size", 0);

    public final class BinderService extends IPinnerService.Stub {
        public BinderService() {}

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    PinnerService.this.mContext, "PinnerService", printWriter)) {
                HashSet hashSet = new HashSet();
                HashSet hashSet2 = new HashSet();
                synchronized (PinnerService.this) {
                    try {
                        Iterator it = PinnerService.this.mPinnedApps.keySet().iterator();
                        long j = 0;
                        while (true) {
                            int i = 1048576;
                            if (!it.hasNext()) {
                                break;
                            }
                            Integer num = (Integer) it.next();
                            int intValue = num.intValue();
                            PinnedApp pinnedApp =
                                    (PinnedApp) PinnerService.this.mPinnedApps.get(num);
                            PinnerService.this.getClass();
                            printWriter.print(PinnerService.getNameForKey(intValue));
                            printWriter.print(" uid=");
                            printWriter.print(pinnedApp.uid);
                            printWriter.print(" active=");
                            printWriter.print(pinnedApp.active);
                            printWriter.println();
                            Iterator it2 =
                                    ((PinnedApp) PinnerService.this.mPinnedApps.get(num))
                                            .mFiles.iterator();
                            while (it2.hasNext()) {
                                PinnedFile pinnedFile = (PinnedFile) it2.next();
                                printWriter.print("  ");
                                printWriter.format(
                                        "%s pinned:%d bytes (%d MB) pinlist:%b\n",
                                        pinnedFile.fileName,
                                        Integer.valueOf(pinnedFile.bytesPinned),
                                        Integer.valueOf(pinnedFile.bytesPinned / i),
                                        Boolean.valueOf(pinnedFile.used_pinlist));
                                j += pinnedFile.bytesPinned;
                                hashSet.add(pinnedFile);
                                Iterator it3 = pinnedFile.pinnedDeps.iterator();
                                while (it3.hasNext()) {
                                    PinnedFile pinnedFile2 = (PinnedFile) it3.next();
                                    printWriter.print("  ");
                                    printWriter.format(
                                            "%s pinned:%d bytes (%d MB) pinlist:%b (Dependency)\n",
                                            pinnedFile2.fileName,
                                            Integer.valueOf(pinnedFile2.bytesPinned),
                                            Integer.valueOf(pinnedFile2.bytesPinned / i),
                                            Boolean.valueOf(pinnedFile2.used_pinlist));
                                    j += pinnedFile2.bytesPinned;
                                    hashSet.add(pinnedFile2);
                                    i = 1048576;
                                }
                            }
                        }
                        printWriter.println();
                        for (PinnedFile pinnedFile3 : PinnerService.this.mPinnedFiles.values()) {
                            if (!hashSet2.contains(pinnedFile3.groupName)) {
                                hashSet2.add(pinnedFile3.groupName);
                            }
                        }
                        Iterator it4 = hashSet2.iterator();
                        boolean z = true;
                        while (it4.hasNext()) {
                            String str = (String) it4.next();
                            for (PinnedFile pinnedFile4 :
                                    PinnerService.this.getAllPinsForGroup(str)) {
                                if (!hashSet.contains(pinnedFile4)) {
                                    if (z) {
                                        printWriter.print("Group:" + str);
                                        printWriter.println();
                                        z = false;
                                    }
                                    printWriter.format(
                                            "  %s pinned:%d bytes (%d MB) pinlist:%b\n",
                                            pinnedFile4.fileName,
                                            Integer.valueOf(pinnedFile4.bytesPinned),
                                            Integer.valueOf(pinnedFile4.bytesPinned / 1048576),
                                            Boolean.valueOf(pinnedFile4.used_pinlist));
                                    j += pinnedFile4.bytesPinned;
                                }
                            }
                        }
                        printWriter.println();
                        PinnerService pinnerService = PinnerService.this;
                        if (pinnerService.mPinAnonAddress != 0) {
                            printWriter.format(
                                    "Pinned anon region: %d (%d MB)\n",
                                    Long.valueOf(pinnerService.mCurrentlyPinnedAnonSize),
                                    Long.valueOf(
                                            PinnerService.this.mCurrentlyPinnedAnonSize / 1048576));
                            j += PinnerService.this.mCurrentlyPinnedAnonSize;
                        }
                        printWriter.format(
                                "Total pinned: %s bytes (%s MB)\n",
                                Long.valueOf(j), Long.valueOf(j / 1048576));
                        printWriter.println();
                        if (!PinnerService.this.mPendingRepin.isEmpty()) {
                            printWriter.print("Pending repin: ");
                            Iterator it5 = PinnerService.this.mPendingRepin.values().iterator();
                            while (it5.hasNext()) {
                                int intValue2 = ((Integer) it5.next()).intValue();
                                PinnerService.this.getClass();
                                printWriter.print(PinnerService.getNameForKey(intValue2));
                                printWriter.print(' ');
                            }
                            printWriter.println();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final List getPinnerStats() {
            Collection<PinnedFile> values;
            getPinnerStats_enforcePermission();
            PinnerService pinnerService = PinnerService.this;
            pinnerService.getClass();
            ArrayList arrayList = new ArrayList();
            synchronized (pinnerService) {
                values = pinnerService.mPinnedFiles.values();
            }
            for (PinnedFile pinnedFile : values) {
                arrayList.add(
                        new PinnedFileStat(
                                pinnedFile.fileName, pinnedFile.bytesPinned, pinnedFile.groupName));
            }
            if (pinnerService.mCurrentlyPinnedAnonSize > 0) {
                arrayList.add(
                        new PinnedFileStat(
                                "[anon]", pinnerService.mCurrentlyPinnedAnonSize, "[anon]"));
            }
            return arrayList;
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            if (strArr.length < 1) {
                PrintWriter printWriter = new PrintWriter(new FileOutputStream(fileDescriptor2));
                printWriter.println("Command is not given.");
                printWriter.flush();
                resultReceiver.send(-1, null);
                return;
            }
            String str = strArr[0];
            str.getClass();
            if (!str.equals("repin")) {
                String m =
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Unknown pinner command: ", str, ". Supported commands: repin");
                PrintWriter printWriter2 = new PrintWriter(new FileOutputStream(fileDescriptor2));
                printWriter2.println(m);
                printWriter2.flush();
                resultReceiver.send(-1, null);
                return;
            }
            PinnerService pinnerService = PinnerService.this;
            pinnerService.getClass();
            pinnerService.mPinnerHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new PinnerService$$ExternalSyntheticLambda4(), pinnerService));
            PinnerService pinnerService2 = PinnerService.this;
            pinnerService2.getClass();
            pinnerService2.mPinnerHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new PinnerService$$ExternalSyntheticLambda1(1), pinnerService2, 0));
            resultReceiver.send(0, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public class Injector {}

    public final class PinRange {
        public int length;
        public int start;
    }

    public abstract class PinRangeSource {
        public abstract boolean read(PinRange pinRange);
    }

    public final class PinRangeSourceStatic extends PinRangeSource {
        public boolean mDone;

        @Override // com.android.server.PinnerService.PinRangeSource
        public final boolean read(PinRange pinRange) {
            pinRange.start = 0;
            pinRange.length = Integer.MAX_VALUE;
            boolean z = this.mDone;
            this.mDone = true;
            return !z;
        }
    }

    public final class PinRangeSourceStream extends PinRangeSource {
        public boolean mDone = false;
        public final DataInputStream mStream;

        public PinRangeSourceStream(InputStream inputStream) {
            this.mStream = new DataInputStream(inputStream);
        }

        @Override // com.android.server.PinnerService.PinRangeSource
        public final boolean read(PinRange pinRange) {
            if (!this.mDone) {
                try {
                    pinRange.start = this.mStream.readInt();
                    pinRange.length = this.mStream.readInt();
                } catch (IOException unused) {
                    this.mDone = true;
                }
            }
            return !this.mDone;
        }
    }

    public final class PinnedApp {
        public boolean active;
        public final ArrayList mFiles = new ArrayList();
        public final int uid;

        public PinnedApp(PinnerService pinnerService, ApplicationInfo applicationInfo) {
            int i = applicationInfo.uid;
            this.uid = i;
            this.active = pinnerService.mAmInternal.isUidActive(i);
        }
    }

    public final class PinnedFile implements AutoCloseable {
        public final int bytesPinned;
        public final String fileName;
        public long mAddress;
        public final int mapSize;
        public boolean used_pinlist;
        public String groupName = "";
        public final ArrayList pinnedDeps = new ArrayList();

        public PinnedFile(int i, int i2, long j, String str) {
            this.mAddress = j;
            this.mapSize = i;
            this.fileName = str;
            this.bytesPinned = i2;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            long j = this.mAddress;
            if (j >= 0) {
                PinnerService.safeMunmap(j, this.mapSize);
                this.mAddress = -1L;
            }
            Iterator it = this.pinnedDeps.iterator();
            while (it.hasNext()) {
                PinnedFile pinnedFile = (PinnedFile) it.next();
                if (pinnedFile != null) {
                    pinnedFile.close();
                }
            }
        }

        public final void finalize() {
            close();
        }
    }

    public final class PinnedFileStats {
        public final String filename;
        public final int sizeKb;
        public final int uid;

        public PinnedFileStats(int i, PinnedFile pinnedFile) {
            this.uid = i;
            String str = pinnedFile.fileName;
            this.filename = str.substring(str.lastIndexOf(47) + 1);
            this.sizeKb = pinnedFile.bytesPinned / 1024;
        }
    }

    public final class PinnerHandler extends Handler {
        public PinnerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 4001) {
                super.handleMessage(message);
                return;
            }
            PinnerService pinnerService = PinnerService.this;
            for (String str :
                    pinnerService
                            .mContext
                            .getResources()
                            .getStringArray(R.array.config_sms_enabled_single_shift_tables)) {
                pinnerService.mInjector.getClass();
                PinnedFile pinFileInternal =
                        PinnerService.pinFileInternal(Integer.MAX_VALUE, str, false);
                if (pinFileInternal == null) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Failed to pin file = ", str, "PinnerService");
                } else {
                    synchronized (pinnerService) {
                        pinnerService.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
                    }
                    pinFileInternal.groupName = "system";
                    pinnerService.pinOptimizedDexDependencies(
                            pinFileInternal, Integer.MAX_VALUE, null);
                }
            }
            pinnerService.refreshPinAnonConfig();
        }
    }

    public PinnerService(Context context) {
        this(context, new Injector());
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.PinnerService$2] */
    public PinnerService(Context context, Injector injector) {
        super(context);
        this.mPinnedFiles = new ArrayMap();
        this.mPinnedApps = new ArrayMap();
        this.mPendingRepin = new ArrayMap();
        this.mPinnerHandler = null;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class: com.android.server.PinnerService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                            ArraySet arraySet = new ArraySet();
                            arraySet.add(schemeSpecificPart);
                            PinnerService.this.update(arraySet, true);
                        }
                    }
                };
        this.mDeviceConfigAnonSizeListener =
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.PinnerService.2
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        if ("runtime_native".equals(properties.getNamespace())
                                && properties.getKeyset().contains("pin_shared_anon_size")) {
                            PinnerService.this.refreshPinAnonConfig();
                        }
                    }
                };
        this.mContext = context;
        this.mInjector = injector;
        injector.getClass();
        this.mDeviceConfigInterface = DeviceConfigInterface.REAL;
        this.mConfiguredToPinCamera =
                context.getResources().getBoolean(R.bool.config_requireCallCapableAccountForHandle);
        this.mConfiguredHomePinBytes =
                context.getResources().getInteger(R.integer.config_searchKeyBehavior);
        this.mConfiguredToPinAssistant =
                context.getResources().getBoolean(R.bool.config_repairModeSupported);
        this.mConfiguredWebviewPinBytes =
                context.getResources().getInteger(R.integer.config_selected_udfps_touch_detection);
        this.mPinKeys = createPinKeys();
        this.mPinnerHandler = new PinnerHandler(BackgroundThread.get().getLooper());
        this.mAtmInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mAmInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        IActivityManager service = ActivityManager.getService();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(broadcastReceiver, intentFilter);
        try {
            service.registerUidObserver(
                    new UidObserver() { // from class: com.android.server.PinnerService.4
                        public final void onUidActive(int i) {
                            PinnerService pinnerService = PinnerService.this;
                            pinnerService.mPinnerHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new PinnerService$$ExternalSyntheticLambda1(3),
                                            pinnerService,
                                            Integer.valueOf(i)));
                        }

                        public final void onUidGone(int i, boolean z) {
                            PinnerService pinnerService = PinnerService.this;
                            pinnerService.mPinnerHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new PinnerService$$ExternalSyntheticLambda1(2),
                                            pinnerService,
                                            Integer.valueOf(i)));
                        }
                    },
                    10,
                    0,
                    (String) null);
        } catch (RemoteException e) {
            Slog.e("PinnerService", "Failed to register uid observer", e);
        }
        final Uri uriFor = Settings.Secure.getUriFor("user_setup_complete");
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        uriFor,
                        false,
                        new ContentObserver() { // from class: com.android.server.PinnerService.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(null);
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri) {
                                if (uriFor.equals(uri)) {
                                    PinnerService pinnerService = PinnerService.this;
                                    if (pinnerService.mConfiguredHomePinBytes > 0) {
                                        pinnerService.mPinnerHandler.sendMessage(
                                                PooledLambda.obtainMessage(
                                                        new PinnerService$$ExternalSyntheticLambda2(),
                                                        pinnerService,
                                                        1,
                                                        Integer.valueOf(
                                                                ActivityManager.getCurrentUser()),
                                                        Boolean.TRUE));
                                    }
                                }
                            }
                        },
                        -1);
        this.mDeviceConfigInterface.addOnPropertiesChangedListener(
                "runtime_native",
                new HandlerExecutor(this.mPinnerHandler),
                this.mDeviceConfigAnonSizeListener);
    }

    public static String getNameForKey(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "" : "Assistant" : "Home" : "Camera";
    }

    public static InputStream maybeOpenPinMetaInZip(ZipFile zipFile, String str) {
        if (!PROP_PIN_PINLIST) {
            return null;
        }
        ZipEntry entry = zipFile.getEntry("pinlist.meta");
        if (entry == null) {
            entry = zipFile.getEntry("assets/pinlist.meta");
        }
        if (entry == null) {
            PinnerService$$ExternalSyntheticOutline0.m(
                    "Could not find pinlist.meta for \"",
                    str,
                    "\": pinning as blob",
                    "PinnerService");
            return null;
        }
        try {
            return zipFile.getInputStream(entry);
        } catch (IOException e) {
            Slog.w(
                    "PinnerService",
                    "error reading pin metadata \"" + str + "\": pinning as blob",
                    e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004e A[Catch: all -> 0x0031, TRY_LEAVE, TryCatch #1 {all -> 0x0031, blocks: (B:23:0x002c, B:8:0x003a, B:9:0x0048, B:11:0x004e, B:20:0x0040), top: B:22:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0040 A[Catch: all -> 0x0031, TryCatch #1 {all -> 0x0031, blocks: (B:23:0x002c, B:8:0x003a, B:9:0x0048, B:11:0x004e, B:20:0x0040), top: B:22:0x002c }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x002c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003a A[Catch: all -> 0x0031, TryCatch #1 {all -> 0x0031, blocks: (B:23:0x002c, B:8:0x003a, B:9:0x0048, B:11:0x004e, B:20:0x0040), top: B:22:0x002c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.PinnerService.PinnedFile pinFileInternal(
            int r4, java.lang.String r5, boolean r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L29
            java.util.zip.ZipFile r6 = new java.util.zip.ZipFile     // Catch: java.io.IOException -> L9 java.lang.Throwable -> Lb
            r6.<init>(r5)     // Catch: java.io.IOException -> L9 java.lang.Throwable -> Lb
            goto L2a
        L9:
            r6 = move-exception
            goto Ld
        Lb:
            r4 = move-exception
            goto L27
        Ld:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb
            java.lang.String r2 = "could not open \""
            r1.<init>(r2)     // Catch: java.lang.Throwable -> Lb
            r1.append(r5)     // Catch: java.lang.Throwable -> Lb
            java.lang.String r2 = "\" as zip: pinning as blob"
            r1.append(r2)     // Catch: java.lang.Throwable -> Lb
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Lb
            java.lang.String r2 = "PinnerService"
            android.util.Slog.w(r2, r1, r6)     // Catch: java.lang.Throwable -> Lb
            goto L29
        L27:
            r6 = r0
            goto L57
        L29:
            r6 = r0
        L2a:
            if (r6 == 0) goto L33
            java.io.InputStream r0 = maybeOpenPinMetaInZip(r6, r5)     // Catch: java.lang.Throwable -> L31
            goto L33
        L31:
            r4 = move-exception
            goto L57
        L33:
            if (r0 == 0) goto L37
            r1 = 1
            goto L38
        L37:
            r1 = 0
        L38:
            if (r1 == 0) goto L40
            com.android.server.PinnerService$PinRangeSourceStream r2 = new com.android.server.PinnerService$PinRangeSourceStream     // Catch: java.lang.Throwable -> L31
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L31
            goto L48
        L40:
            com.android.server.PinnerService$PinRangeSourceStatic r2 = new com.android.server.PinnerService$PinRangeSourceStatic     // Catch: java.lang.Throwable -> L31
            r2.<init>()     // Catch: java.lang.Throwable -> L31
            r3 = 0
            r2.mDone = r3     // Catch: java.lang.Throwable -> L31
        L48:
            com.android.server.PinnerService$PinnedFile r4 = pinFileRanges(r5, r4, r2)     // Catch: java.lang.Throwable -> L31
            if (r4 == 0) goto L50
            r4.used_pinlist = r1     // Catch: java.lang.Throwable -> L31
        L50:
            safeClose(r0)
            safeClose(r6)
            return r4
        L57:
            safeClose(r0)
            safeClose(r6)
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.PinnerService.pinFileInternal(int,"
                    + " java.lang.String, boolean):com.android.server.PinnerService$PinnedFile");
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.PinnerService.PinnedFile pinFileRanges(
            java.lang.String r20, int r21, com.android.server.PinnerService.PinRangeSource r22) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.PinnerService.pinFileRanges(java.lang.String, int,"
                    + " com.android.server.PinnerService$PinRangeSource):com.android.server.PinnerService$PinnedFile");
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Slog.w("PinnerService", "ignoring error closing resource: " + closeable, e);
            }
        }
    }

    public static void safeClose(FileDescriptor fileDescriptor) {
        if (fileDescriptor == null || !fileDescriptor.valid()) {
            return;
        }
        try {
            Os.close(fileDescriptor);
        } catch (ErrnoException e) {
            if (e.errno == OsConstants.EBADF) {
                throw new AssertionError(e);
            }
        }
    }

    public static void safeMunmap(long j, long j2) {
        try {
            Os.munmap(j, j2);
        } catch (ErrnoException e) {
            Slog.w("PinnerService", "ignoring error in unmap", e);
        }
    }

    public final ArraySet createPinKeys() {
        ArraySet arraySet = new ArraySet();
        if (this.mConfiguredToPinCamera
                && this.mDeviceConfigInterface.getBoolean(
                        "runtime_native_boot",
                        "pin_camera",
                        SystemProperties.getBoolean("pinner.pin_camera", true))) {
            arraySet.add(0);
        }
        if (this.mConfiguredHomePinBytes > 0) {
            arraySet.add(1);
        }
        if (this.mConfiguredToPinAssistant) {
            arraySet.add(2);
        }
        return arraySet;
    }

    public final List getAllPinsForGroup(final String str) {
        List list;
        synchronized (this) {
            list =
                    this.mPinnedFiles.values().stream()
                            .filter(
                                    new Predicate() { // from class:
                                                      // com.android.server.PinnerService$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return ((PinnerService.PinnedFile) obj)
                                                    .groupName.equals(str);
                                        }
                                    })
                            .toList();
        }
        return list;
    }

    public final ApplicationInfo getApplicationInfoForIntent(int i, Intent intent, boolean z) {
        ResolveInfo resolveActivityAsUser =
                this.mContext.getPackageManager().resolveActivityAsUser(intent, 851968, i);
        if (resolveActivityAsUser == null) {
            return null;
        }
        if (!ResolverActivity.class.getName().equals(resolveActivityAsUser.activityInfo.name)) {
            return resolveActivityAsUser.activityInfo.applicationInfo;
        }
        if (!z) {
            return null;
        }
        Iterator it =
                this.mContext
                        .getPackageManager()
                        .queryIntentActivitiesAsUser(intent, 851968, i)
                        .iterator();
        ApplicationInfo applicationInfo = null;
        while (it.hasNext()) {
            ApplicationInfo applicationInfo2 =
                    ((ResolveInfo) it.next()).activityInfo.applicationInfo;
            if ((applicationInfo2.flags & 1) != 0) {
                if (applicationInfo != null) {
                    return null;
                }
                applicationInfo = applicationInfo2;
            }
        }
        return applicationInfo;
    }

    public final ApplicationInfo getInfoForKey(int i, int i2) {
        Intent homeIntent;
        if (i == 0) {
            ApplicationInfo applicationInfoForIntent =
                    getApplicationInfoForIntent(
                            i2, new Intent("android.media.action.STILL_IMAGE_CAMERA"), false);
            if (applicationInfoForIntent == null) {
                applicationInfoForIntent =
                        getApplicationInfoForIntent(
                                i2,
                                new Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE"),
                                false);
            }
            return applicationInfoForIntent == null
                    ? getApplicationInfoForIntent(
                            i2, new Intent("android.media.action.STILL_IMAGE_CAMERA"), true)
                    : applicationInfoForIntent;
        }
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            return getApplicationInfoForIntent(
                    i2, new Intent("android.intent.action.ASSIST"), true);
        }
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService) this.mAtmInternal;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                homeIntent = ActivityTaskManagerService.this.getHomeIntent();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return getApplicationInfoForIntent(i2, homeIntent, false);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        IPinnerService.Stub binderService = new BinderService();
        this.mInjector.getClass();
        publishBinderService("pinner", binderService);
        publishLocalService(PinnerService.class, this);
        this.mPinnerHandler.obtainMessage(4001).sendToTarget();
        sendPinAppsMessage(0);
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        int userIdentifier = targetUser2.getUserIdentifier();
        if (this.mUserManager.isManagedProfile(userIdentifier)) {
            return;
        }
        sendPinAppsMessage(userIdentifier);
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        if (userIdentifier == 0 || this.mUserManager.isManagedProfile(userIdentifier)) {
            return;
        }
        sendPinAppsMessage(userIdentifier);
    }

    public final void pinApp(int i, int i2, boolean z) {
        int i3;
        synchronized (this) {
            try {
                PinnedApp pinnedApp = (PinnedApp) this.mPinnedApps.get(Integer.valueOf(i));
                i3 = (pinnedApp == null || !pinnedApp.active) ? -1 : pinnedApp.uid;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z && i3 != -1) {
            synchronized (this) {
                this.mPendingRepin.put(Integer.valueOf(i3), Integer.valueOf(i));
            }
            return;
        }
        unpinApp(i);
        ApplicationInfo infoForKey = getInfoForKey(i, i2);
        if (infoForKey != null) {
            PinnedApp pinnedApp2 = new PinnedApp(this, infoForKey);
            synchronized (this) {
                this.mPinnedApps.put(Integer.valueOf(i), pinnedApp2);
            }
            boolean z2 = false;
            int i4 =
                    i != 0
                            ? i != 1 ? i != 2 ? 0 : 62914560 : this.mConfiguredHomePinBytes
                            : 83886080;
            ArrayList arrayList = new ArrayList();
            arrayList.add(infoForKey.sourceDir);
            String[] strArr = infoForKey.splitSourceDirs;
            if (strArr != null) {
                for (String str : strArr) {
                    arrayList.add(str);
                }
            }
            if (i == 1 && Flags.skipHomeArtPins()) {
                z2 = true;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (i4 <= 0) {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m(
                            "Reached to the pin size limit. Skipping: ", str2, "PinnerService");
                } else {
                    this.mInjector.getClass();
                    PinnedFile pinFileInternal = pinFileInternal(i4, str2, true);
                    if (pinFileInternal == null) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                "Failed to pin ", str2, "PinnerService");
                    } else {
                        pinFileInternal.groupName = getNameForKey(i);
                        synchronized (this) {
                            pinnedApp2.mFiles.add(pinFileInternal);
                            this.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
                        }
                        i4 -= pinFileInternal.bytesPinned;
                        if (str2.equals(infoForKey.sourceDir) && !z2) {
                            pinOptimizedDexDependencies(
                                    pinFileInternal, Integer.MAX_VALUE, infoForKey);
                        }
                    }
                }
            }
        }
    }

    public final void pinAppsInternal(int i, boolean z) {
        ArraySet arraySet;
        if (z) {
            ArraySet createPinKeys = createPinKeys();
            synchronized (this) {
                try {
                    if (!this.mPinnedApps.isEmpty()) {
                        Slog.e(
                                "PinnerService",
                                "Attempted to update a list of apps, but apps were already pinned."
                                    + " Skipping.");
                        return;
                    }
                    this.mPinKeys = createPinKeys;
                } finally {
                }
            }
        }
        synchronized (this) {
            arraySet = this.mPinKeys;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            pinApp(((Integer) arraySet.valueAt(size)).intValue(), i, true);
        }
    }

    public final void pinOptimizedDexDependencies(
            PinnedFile pinnedFile, int i, ApplicationInfo applicationInfo) {
        if (pinnedFile.fileName.endsWith(".jar") || pinnedFile.fileName.endsWith(".apk")) {
            String[] strArr = null;
            String str = applicationInfo != null ? applicationInfo.primaryCpuAbi : null;
            if (str == null) {
                str = Build.SUPPORTED_ABIS[0];
            }
            try {
                strArr =
                        DexFile.getDexFileOutputPaths(
                                pinnedFile.fileName, VMRuntime.getInstructionSet(str));
            } catch (IOException unused) {
            }
            if (strArr == null) {
                return;
            }
            for (String str2 : strArr) {
                unpinFile(str2);
                this.mInjector.getClass();
                PinnedFile pinFileInternal = pinFileInternal(i, str2, false);
                if (pinFileInternal == null) {
                    Slog.i("PinnerService", "Failed to pin ART file = " + str2);
                    return;
                }
                pinFileInternal.groupName = pinnedFile.groupName;
                pinnedFile.pinnedDeps.add(pinFileInternal);
                i -= pinFileInternal.bytesPinned;
                synchronized (this) {
                    this.mPinnedFiles.put(pinFileInternal.fileName, pinFileInternal);
                }
            }
        }
    }

    public final void refreshPinAnonConfig() {
        long j;
        long j2;
        long max =
                Math.max(
                        0L,
                        Math.min(
                                this.mDeviceConfigInterface.getLong(
                                        "runtime_native",
                                        "pin_shared_anon_size",
                                        DEFAULT_ANON_SIZE),
                                2147483648L));
        if (max != this.mPinAnonSize) {
            this.mPinAnonSize = max;
            if (max == 0) {
                Slog.d("PinnerService", "pinAnonRegion: releasing pinned region");
                long j3 = this.mPinAnonAddress;
                if (j3 != 0) {
                    safeMunmap(j3, this.mCurrentlyPinnedAnonSize);
                }
                this.mPinAnonAddress = 0L;
                this.mCurrentlyPinnedAnonSize = 0L;
                return;
            }
            long j4 = max % PAGE_SIZE;
            if (j4 != 0) {
                max -= j4;
                Slog.e("PinnerService", "pinAnonRegion: aligning size to " + max);
            }
            if (this.mPinAnonAddress != 0) {
                if (this.mCurrentlyPinnedAnonSize == max) {
                    Slog.d("PinnerService", "pinAnonRegion: already pinned region of size " + max);
                    return;
                }
                Slog.d(
                        "PinnerService",
                        "pinAnonRegion: resetting pinned region for new size " + max);
                long j5 = this.mPinAnonAddress;
                if (j5 != 0) {
                    safeMunmap(j5, this.mCurrentlyPinnedAnonSize);
                }
                this.mPinAnonAddress = 0L;
                this.mCurrentlyPinnedAnonSize = 0L;
            }
            try {
                j2 =
                        Os.mmap(
                                0L,
                                max,
                                OsConstants.PROT_READ | OsConstants.PROT_WRITE,
                                OsConstants.MAP_SHARED | OsConstants.MAP_ANONYMOUS,
                                new FileDescriptor(),
                                0L);
            } catch (Exception e) {
                e = e;
                j2 = 0;
            } catch (Throwable th) {
                th = th;
                j = 0;
                j2 = 0;
            }
            try {
                try {
                    Unsafe unsafe = null;
                    for (Field field : Unsafe.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (Unsafe.class.isInstance(obj)) {
                            unsafe = (Unsafe) Unsafe.class.cast(obj);
                        }
                    }
                    if (unsafe == null) {
                        throw new Exception("Couldn't get Unsafe");
                    }
                    Class cls = Long.TYPE;
                    Unsafe.class
                            .getMethod("setMemory", cls, cls, Byte.TYPE)
                            .invoke(unsafe, Long.valueOf(j2), Long.valueOf(max), (byte) 1);
                    Os.mlock(j2, max);
                    this.mCurrentlyPinnedAnonSize = max;
                    this.mPinAnonAddress = j2;
                    Slog.w(
                            "PinnerService",
                            "pinAnonRegion success, size=" + this.mCurrentlyPinnedAnonSize);
                } catch (Exception e2) {
                    e = e2;
                    Slog.e("PinnerService", "Could not pin anon region of size " + max, e);
                    if (j2 >= 0) {
                        safeMunmap(j2, max);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                j = 0;
                if (j2 >= j) {
                    safeMunmap(j2, max);
                }
                throw th;
            }
        }
    }

    public final void sendPinAppsMessage(int i) {
        this.mPinnerHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new PinnerService$$ExternalSyntheticLambda1(0), this, Integer.valueOf(i)));
    }

    public final void unpinApp(int i) {
        synchronized (this) {
            try {
                PinnedApp pinnedApp = (PinnedApp) this.mPinnedApps.get(Integer.valueOf(i));
                if (pinnedApp == null) {
                    return;
                }
                this.mPinnedApps.remove(Integer.valueOf(i));
                Iterator it = new ArrayList(pinnedApp.mFiles).iterator();
                while (it.hasNext()) {
                    unpinFile(((PinnedFile) it.next()).fileName);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unpinFile(String str) {
        PinnedFile pinnedFile;
        synchronized (this) {
            pinnedFile = (PinnedFile) this.mPinnedFiles.get(str);
        }
        if (pinnedFile == null) {
            return;
        }
        pinnedFile.close();
        synchronized (this) {
            try {
                this.mPinnedFiles.remove(pinnedFile.fileName);
                Iterator it = pinnedFile.pinnedDeps.iterator();
                while (it.hasNext()) {
                    PinnedFile pinnedFile2 = (PinnedFile) it.next();
                    if (pinnedFile2 != null) {
                        this.mPinnedFiles.remove(pinnedFile2.fileName);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void update(ArraySet arraySet, boolean z) {
        ArraySet arraySet2;
        synchronized (this) {
            arraySet2 = this.mPinKeys;
        }
        int currentUser = ActivityManager.getCurrentUser();
        for (int size = arraySet2.size() - 1; size >= 0; size--) {
            Integer num = (Integer) arraySet2.valueAt(size);
            ApplicationInfo infoForKey = getInfoForKey(num.intValue(), currentUser);
            if (infoForKey != null && arraySet.contains(infoForKey.packageName)) {
                Slog.i(
                        "PinnerService",
                        "Updating pinned files for " + infoForKey.packageName + " force=" + z);
                this.mPinnerHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new PinnerService$$ExternalSyntheticLambda2(),
                                this,
                                num,
                                Integer.valueOf(currentUser),
                                Boolean.valueOf(z)));
            }
        }
    }

    public final void updateActiveState(int i, boolean z) {
        synchronized (this) {
            try {
                for (int size = this.mPinnedApps.size() - 1; size >= 0; size--) {
                    PinnedApp pinnedApp = (PinnedApp) this.mPinnedApps.valueAt(size);
                    if (pinnedApp.uid == i) {
                        pinnedApp.active = z;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
