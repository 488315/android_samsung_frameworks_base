package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.BundleMerger;
import android.os.DropBoxManager;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.text.TextUtils;
import android.text.format.TimeMigrationUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.IDropBoxManagerService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ObjectUtils;
import com.android.server.feature.flags.Flags;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.zip.GZIPOutputStream;
import libcore.io.IoUtils;

public final class DropBoxManagerService extends SystemService {
    public static final int DEFAULT_QUOTA_KB;
    public static final List DISABLED_BY_DEFAULT_TAGS;
    public static final BundleMerger sDropboxEntryAddedExtrasMerger;
    public FileList mAllFiles;
    public int mBlockSize;
    public volatile boolean mBooted;
    public int mCachedQuotaBlocks;
    public long mCachedQuotaUptimeMillis;
    public final ContentResolver mContentResolver;
    public final File mDropBoxDir;
    public ArrayMap mFilesByTag;
    public final DropBoxManagerBroadcastHandler mHandler;
    public long mLowPriorityRateLimitPeriod;
    public ArraySet mLowPriorityTags;
    public int mMaxFiles;
    public final AnonymousClass1 mReceiver;
    public StatFs mStatFs;
    public final AnonymousClass2 mStub;

    /* renamed from: com.android.server.DropBoxManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            DropBoxManagerService.this.mCachedQuotaUptimeMillis = 0L;
            new Thread() { // from class: com.android.server.DropBoxManagerService.1.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    try {
                        DropBoxManagerService dropBoxManagerService = DropBoxManagerService.this;
                        int i = DropBoxManagerService.DEFAULT_QUOTA_KB;
                        dropBoxManagerService.init$1();
                        DropBoxManagerService.this.trimToFit();
                    } catch (IOException e) {
                        Slog.e("DropBoxManagerService", "Can't init", e);
                    }
                }
            }.start();
        }
    }

    public final class DropBoxManagerBroadcastHandler extends Handler {
        public final ArrayMap mDeferredMap;
        public final Object mLock;

        public DropBoxManagerBroadcastHandler(Looper looper) {
            super(looper);
            this.mLock = new Object();
            this.mDeferredMap = new ArrayMap();
        }

        public static void setBroadcastOptionsForDeferral(BroadcastOptions broadcastOptions, String str) {
            broadcastOptions.setDeliveryGroupPolicy(2).setDeliveryGroupMatchingKey("android.intent.action.DROPBOX_ENTRY_ADDED", str).setDeliveryGroupExtrasMerger(DropBoxManagerService.sDropboxEntryAddedExtrasMerger).setDeferralPolicy(2);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Intent intent;
            int i = message.what;
            if (i == 1) {
                prepareAndSendBroadcast((Intent) message.obj, false);
                return;
            }
            if (i != 2) {
                return;
            }
            synchronized (this.mLock) {
                intent = (Intent) this.mDeferredMap.remove((String) message.obj);
            }
            if (intent != null) {
                prepareAndSendBroadcast(intent, true);
            }
        }

        public final void maybeDeferBroadcast(long j, String str) {
            synchronized (this.mLock) {
                try {
                    Intent intent = (Intent) this.mDeferredMap.get(str);
                    if (intent != null) {
                        intent.putExtra("time", j);
                        intent.putExtra("android.os.extra.DROPPED_COUNT", intent.getIntExtra("android.os.extra.DROPPED_COUNT", 0) + 1);
                        return;
                    }
                    ArrayMap arrayMap = this.mDeferredMap;
                    Intent intent2 = new Intent("android.intent.action.DROPBOX_ENTRY_ADDED");
                    intent2.putExtra("tag", str);
                    intent2.putExtra("time", j);
                    intent2.putExtra("android.os.extra.DROPPED_COUNT", 0);
                    arrayMap.put(str, intent2);
                    sendMessageDelayed(obtainMessage(2, str), DropBoxManagerService.this.mLowPriorityRateLimitPeriod);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void prepareAndSendBroadcast(Intent intent, boolean z) {
            if (!DropBoxManagerService.this.mBooted) {
                intent.addFlags(1073741824);
            }
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            if (!Flags.enableReadDropboxPermission()) {
                if (z) {
                    setBroadcastOptionsForDeferral(makeBasic, intent.getStringExtra("tag"));
                }
                DropBoxManagerService.this.getContext().sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.READ_LOGS", makeBasic.toBundle());
                return;
            }
            makeBasic.setRequireCompatChange(296060945L, true);
            if (z) {
                setBroadcastOptionsForDeferral(makeBasic, intent.getStringExtra("tag") + "-READ_DROPBOX_DATA");
            }
            Context context = DropBoxManagerService.this.getContext();
            UserHandle userHandle = UserHandle.ALL;
            context.sendBroadcastAsUser(intent, userHandle, "android.permission.READ_DROPBOX_DATA", makeBasic.toBundle());
            makeBasic.setRequireCompatChange(296060945L, false);
            if (z) {
                setBroadcastOptionsForDeferral(makeBasic, intent.getStringExtra("tag") + "-READ_LOGS");
            }
            DropBoxManagerService.this.getContext().sendBroadcastAsUser(intent, userHandle, "android.permission.READ_LOGS", makeBasic.toBundle());
        }
    }

    public final class DropBoxManagerInternalImpl {
        public DropBoxManagerInternalImpl() {
        }
    }

    final class EntryFile implements Comparable {
        public final int blocks;
        public final int flags;
        public final String tag;
        public final long timestampMillis;

        public EntryFile(long j) {
            this.tag = null;
            this.timestampMillis = j;
            this.flags = 1;
            this.blocks = 0;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public EntryFile(java.io.File r12, int r13) {
            /*
                r11 = this;
                r11.<init>()
                java.lang.String r0 = r12.getName()
                r1 = 64
                int r1 = r0.lastIndexOf(r1)
                r2 = 1
                r3 = 0
                r4 = 0
                r5 = 0
                if (r1 >= 0) goto L19
                r1 = r2
                r8 = r3
                r7 = r4
            L17:
                r9 = r5
                goto L6c
            L19:
                java.lang.String r7 = r0.substring(r3, r1)
                java.lang.String r7 = android.net.Uri.decode(r7)
                java.lang.String r8 = ".gz"
                boolean r8 = r0.endsWith(r8)
                r9 = 4
                if (r8 == 0) goto L31
                r8 = 3
                java.lang.String r0 = com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(r8, r3, r0)
                r8 = r9
                goto L32
            L31:
                r8 = r3
            L32:
                java.lang.String r10 = ".lost"
                boolean r10 = r0.endsWith(r10)
                if (r10 == 0) goto L44
                r8 = r8 | 1
                int r1 = r1 + r2
                r9 = 5
                java.lang.String r0 = com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(r9, r1, r0)
            L42:
                r1 = r3
                goto L63
            L44:
                java.lang.String r10 = ".txt"
                boolean r10 = r0.endsWith(r10)
                if (r10 == 0) goto L54
                r8 = r8 | 2
                int r1 = r1 + r2
                java.lang.String r0 = com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(r9, r1, r0)
                goto L42
            L54:
                java.lang.String r10 = ".dat"
                boolean r10 = r0.endsWith(r10)
                if (r10 == 0) goto L62
                int r1 = r1 + r2
                java.lang.String r0 = com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(r9, r1, r0)
                goto L42
            L62:
                r1 = r2
            L63:
                if (r1 != 0) goto L17
                long r9 = java.lang.Long.parseLong(r0)     // Catch: java.lang.NumberFormatException -> L6a
                goto L6c
            L6a:
                r1 = r2
                goto L17
            L6c:
                if (r1 == 0) goto L8d
                java.lang.StringBuilder r13 = new java.lang.StringBuilder
                java.lang.String r0 = "Invalid filename: "
                r13.<init>(r0)
                r13.append(r12)
                java.lang.String r13 = r13.toString()
                java.lang.String r0 = "DropBoxManagerService"
                android.util.Slog.wtf(r0, r13)
                r12.delete()
                r11.tag = r4
                r11.flags = r2
                r11.timestampMillis = r5
                r11.blocks = r3
                return
            L8d:
                long r0 = r12.length()
                long r12 = (long) r13
                long r0 = r0 + r12
                r2 = 1
                long r0 = r0 - r2
                long r0 = r0 / r12
                int r12 = (int) r0
                r11.blocks = r12
                java.lang.String r12 = android.text.TextUtils.safeIntern(r7)
                r11.tag = r12
                r11.flags = r8
                r11.timestampMillis = r9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.DropBoxManagerService.EntryFile.<init>(java.io.File, int):void");
        }

        public EntryFile(File file, File file2, String str, long j, int i, int i2) {
            if ((i & 1) != 0) {
                throw new IllegalArgumentException();
            }
            this.tag = TextUtils.safeIntern(str);
            this.timestampMillis = j;
            this.flags = i;
            File file3 = getFile(file2);
            if (file.renameTo(file3)) {
                long j2 = i2;
                this.blocks = (int) (((file3.length() + j2) - 1) / j2);
            } else {
                throw new IOException("Can't rename " + file + " to " + file3);
            }
        }

        public EntryFile(File file, String str, long j) {
            this.tag = TextUtils.safeIntern(str);
            this.timestampMillis = j;
            this.flags = 1;
            this.blocks = 0;
            new FileOutputStream(getFile(file)).close();
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            EntryFile entryFile = (EntryFile) obj;
            int compare = Long.compare(this.timestampMillis, entryFile.timestampMillis);
            if (compare != 0) {
                return compare;
            }
            int compare2 = ObjectUtils.compare(this.tag, entryFile.tag);
            if (compare2 != 0) {
                return compare2;
            }
            int compare3 = Integer.compare(this.flags, entryFile.flags);
            return compare3 != 0 ? compare3 : Integer.compare(hashCode(), entryFile.hashCode());
        }

        public final File getFile(File file) {
            String concat;
            String str = null;
            if (!(this.tag != null)) {
                return null;
            }
            if (this.tag != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(Uri.encode(this.tag));
                sb.append("@");
                sb.append(this.timestampMillis);
                int i = this.flags;
                if ((i & 1) != 0) {
                    concat = ".lost";
                } else {
                    concat = ((i & 2) != 0 ? ".txt" : ".dat").concat((i & 4) != 0 ? ".gz" : "");
                }
                sb.append(concat);
                str = sb.toString();
            }
            return new File(file, str);
        }
    }

    public final class FileList implements Comparable {
        public int blocks = 0;
        public final TreeSet contents = new TreeSet();

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            FileList fileList = (FileList) obj;
            int i = this.blocks;
            int i2 = fileList.blocks;
            if (i != i2) {
                return i2 - i;
            }
            if (this == fileList) {
                return 0;
            }
            if (hashCode() < fileList.hashCode()) {
                return -1;
            }
            return hashCode() > fileList.hashCode() ? 1 : 0;
        }
    }

    public final class ShellCmd extends ShellCommand {
        public ShellCmd() {
        }

        public final int onCommand(String str) {
            char c;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                switch (str.hashCode()) {
                    case -1412652367:
                        if (str.equals("restore-defaults")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -529247831:
                        if (str.equals("add-low-priority")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -444925274:
                        if (str.equals("remove-low-priority")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1936917209:
                        if (str.equals("set-rate-limit")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
            } catch (Exception e) {
                outPrintWriter.println(e);
            }
            if (c == 0) {
                long parseLong = Long.parseLong(getNextArgRequired());
                DropBoxManagerService dropBoxManagerService = DropBoxManagerService.this;
                int i = DropBoxManagerService.DEFAULT_QUOTA_KB;
                synchronized (dropBoxManagerService) {
                    dropBoxManagerService.mLowPriorityRateLimitPeriod = parseLong;
                }
                return 0;
            }
            if (c == 1) {
                String nextArgRequired = getNextArgRequired();
                DropBoxManagerService dropBoxManagerService2 = DropBoxManagerService.this;
                int i2 = DropBoxManagerService.DEFAULT_QUOTA_KB;
                synchronized (dropBoxManagerService2) {
                    dropBoxManagerService2.mLowPriorityTags.add(nextArgRequired);
                }
                return 0;
            }
            if (c == 2) {
                String nextArgRequired2 = getNextArgRequired();
                DropBoxManagerService dropBoxManagerService3 = DropBoxManagerService.this;
                int i3 = DropBoxManagerService.DEFAULT_QUOTA_KB;
                synchronized (dropBoxManagerService3) {
                    dropBoxManagerService3.mLowPriorityTags.remove(nextArgRequired2);
                }
                return 0;
            }
            if (c != 3) {
                return handleDefaultCommands(str);
            }
            DropBoxManagerService dropBoxManagerService4 = DropBoxManagerService.this;
            int i4 = DropBoxManagerService.DEFAULT_QUOTA_KB;
            synchronized (dropBoxManagerService4) {
                dropBoxManagerService4.getLowPriorityResourceConfigs();
            }
            return 0;
            outPrintWriter.println(e);
            return 0;
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Dropbox manager service commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  set-rate-limit PERIOD");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Sets low priority broadcast rate limit period to PERIOD ms", "  add-low-priority TAG", "    Add TAG to dropbox low priority list", "  remove-low-priority TAG");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Remove TAG from dropbox low priority list", "  restore-defaults", "    restore dropbox settings to defaults");
        }
    }

    public final class SimpleEntrySource implements DropBoxManagerInternal$EntrySource {
        public final boolean forceCompress;
        public final InputStream in;
        public final long length;

        public SimpleEntrySource(long j, InputStream inputStream, boolean z) {
            this.in = inputStream;
            this.length = j;
            this.forceCompress = z;
        }

        @Override // com.android.server.DropBoxManagerInternal$EntrySource, java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            FileUtils.closeQuietly(this.in);
        }

        @Override // com.android.server.DropBoxManagerInternal$EntrySource
        public final long length() {
            return this.length;
        }

        @Override // com.android.server.DropBoxManagerInternal$EntrySource
        public final void writeTo(FileDescriptor fileDescriptor) {
            if (!this.forceCompress) {
                FileUtils.copy(this.in, new FileOutputStream(fileDescriptor));
                return;
            }
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(new FileOutputStream(fileDescriptor));
            FileUtils.copy(this.in, gZIPOutputStream);
            gZIPOutputStream.close();
        }
    }

    static {
        DEFAULT_QUOTA_KB = Build.IS_USERDEBUG ? 20480 : 10240;
        DISABLED_BY_DEFAULT_TAGS = List.of("data_app_wtf", "system_app_wtf", "system_server_wtf");
        BundleMerger bundleMerger = new BundleMerger();
        sDropboxEntryAddedExtrasMerger = bundleMerger;
        bundleMerger.setDefaultMergeStrategy(1);
        bundleMerger.setMergeStrategy("time", 4);
        bundleMerger.setMergeStrategy("android.os.extra.DROPPED_COUNT", 25);
    }

    public DropBoxManagerService(Context context) {
        this(context, new File("/data/system/dropbox"), FgThread.get().getLooper());
    }

    public DropBoxManagerService(Context context, File file, Looper looper) {
        super(context);
        this.mAllFiles = null;
        this.mFilesByTag = null;
        this.mLowPriorityRateLimitPeriod = 0L;
        this.mLowPriorityTags = null;
        this.mStatFs = null;
        this.mBlockSize = 0;
        this.mCachedQuotaBlocks = 0;
        this.mCachedQuotaUptimeMillis = 0L;
        this.mBooted = false;
        this.mMaxFiles = -1;
        this.mReceiver = new AnonymousClass1();
        this.mStub = new IDropBoxManagerService.Stub() { // from class: com.android.server.DropBoxManagerService.2
            public final void addData(String str, byte[] bArr, int i) {
                DropBoxManagerService dropBoxManagerService = DropBoxManagerService.this;
                dropBoxManagerService.getClass();
                dropBoxManagerService.addEntry(str, new ByteArrayInputStream(bArr), bArr.length, i);
            }

            public final void addFile(String str, ParcelFileDescriptor parcelFileDescriptor, int i) {
                DropBoxManagerService dropBoxManagerService = DropBoxManagerService.this;
                dropBoxManagerService.getClass();
                try {
                    StructStat fstat = Os.fstat(parcelFileDescriptor.getFileDescriptor());
                    if (OsConstants.S_ISREG(fstat.st_mode)) {
                        dropBoxManagerService.addEntry(str, new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor), fstat.st_size, i);
                        return;
                    }
                    throw new IllegalArgumentException(str + " entry must be real file");
                } catch (ErrnoException e) {
                    throw new IllegalArgumentException(e);
                }
            }

            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void dump(java.io.FileDescriptor r23, java.io.PrintWriter r24, java.lang.String[] r25) {
                /*
                    Method dump skipped, instructions count: 874
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.DropBoxManagerService.AnonymousClass2.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
            }

            public final DropBoxManager.Entry getNextEntry(String str, long j, String str2) {
                return getNextEntryWithAttribution(str, j, str2, null);
            }

            public final DropBoxManager.Entry getNextEntryWithAttribution(String str, long j, String str2, String str3) {
                DropBoxManagerService dropBoxManagerService = DropBoxManagerService.this;
                synchronized (dropBoxManagerService) {
                    try {
                        if (!dropBoxManagerService.checkPermission(Binder.getCallingUid(), str2, str3)) {
                            return null;
                        }
                        try {
                            dropBoxManagerService.init$1();
                            FileList fileList = str == null ? dropBoxManagerService.mAllFiles : (FileList) dropBoxManagerService.mFilesByTag.get(str);
                            if (fileList == null) {
                                return null;
                            }
                            for (EntryFile entryFile : fileList.contents.tailSet(new EntryFile(j + 1))) {
                                if (entryFile.tag != null) {
                                    if ((entryFile.flags & 1) != 0) {
                                        return new DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis);
                                    }
                                    File file2 = entryFile.getFile(dropBoxManagerService.mDropBoxDir);
                                    try {
                                        return new DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis, file2, entryFile.flags);
                                    } catch (IOException e) {
                                        Slog.wtf("DropBoxManagerService", "Can't read: " + file2, e);
                                    }
                                }
                            }
                            return null;
                        } catch (IOException e2) {
                            Slog.e("DropBoxManagerService", "Can't init", e2);
                            return null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final boolean isTagEnabled(String str) {
                return DropBoxManagerService.this.isTagEnabled(str);
            }

            public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
                DropBoxManagerService.this.new ShellCmd().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        };
        this.mDropBoxDir = file;
        this.mContentResolver = getContext().getContentResolver();
        this.mHandler = new DropBoxManagerBroadcastHandler(looper);
        LocalServices.addService(DropBoxManagerInternalImpl.class, new DropBoxManagerInternalImpl());
    }

    public static boolean matchEntry(EntryFile entryFile, ArrayList arrayList) {
        String formatMillisWithFixedFormat = TimeMigrationUtils.formatMillisWithFixedFormat(entryFile.timestampMillis);
        int size = arrayList.size();
        boolean z = true;
        for (int i = 0; i < size && z; i++) {
            String str = (String) arrayList.get(i);
            z = formatMillisWithFixedFormat.contains(str) || str.equals(entryFile.tag);
        }
        return z;
    }

    public final void addEntry(String str, DropBoxManagerInternal$EntrySource dropBoxManagerInternal$EntrySource, int i) {
        File file;
        ?? r6 = "Dropping: ";
        File file2 = null;
        try {
            try {
                Slog.i("DropBoxManagerService", "add tag=" + str + " isTagEnabled=" + isTagEnabled(str) + " flags=0x" + Integer.toHexString(i));
                try {
                    if ((i & 1) != 0) {
                        throw new IllegalArgumentException();
                    }
                    init$1();
                    if (!isTagEnabled(str)) {
                        IoUtils.closeQuietly(dropBoxManagerInternal$EntrySource);
                        return;
                    }
                    long length = dropBoxManagerInternal$EntrySource.length();
                    long trimToFit = trimToFit();
                    try {
                        if (length > trimToFit) {
                            Slog.w("DropBoxManagerService", "Dropping: " + str + " (" + length + " > " + trimToFit + " bytes)");
                            FrameworkStatsLog.write(FrameworkStatsLog.DROPBOX_ENTRY_DROPPED, 6, str, 0L);
                            file = null;
                        } else {
                            r6 = new File(this.mDropBoxDir, "drop" + Thread.currentThread().getId() + ".tmp");
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream((File) r6);
                                try {
                                } catch (Throwable th) {
                                    th = th;
                                }
                                try {
                                    dropBoxManagerInternal$EntrySource.writeTo(fileOutputStream.getFD());
                                    fileOutputStream.close();
                                    file = r6;
                                } catch (Throwable th2) {
                                    th = th2;
                                    Throwable th3 = th;
                                    try {
                                        fileOutputStream.close();
                                        throw th3;
                                    } catch (Throwable th4) {
                                        th3.addSuppressed(th4);
                                        throw th3;
                                    }
                                }
                            } catch (IOException e) {
                                e = e;
                                file2 = r6;
                                Slog.e("DropBoxManagerService", "Can't write: " + str, e);
                                FrameworkStatsLog.write(FrameworkStatsLog.DROPBOX_ENTRY_DROPPED, 5, str, 0L);
                                IoUtils.closeQuietly(dropBoxManagerInternal$EntrySource);
                                if (file2 != null) {
                                    file2.delete();
                                    return;
                                }
                                return;
                            } catch (Throwable th5) {
                                th = th5;
                                file2 = r6;
                                IoUtils.closeQuietly(dropBoxManagerInternal$EntrySource);
                                if (file2 != null) {
                                    file2.delete();
                                }
                                throw th;
                            }
                        }
                        long createEntry = createEntry(i, file, str);
                        ArraySet arraySet = this.mLowPriorityTags;
                        DropBoxManagerBroadcastHandler dropBoxManagerBroadcastHandler = this.mHandler;
                        if (arraySet == null || !arraySet.contains(str)) {
                            dropBoxManagerBroadcastHandler.getClass();
                            Intent intent = new Intent("android.intent.action.DROPBOX_ENTRY_ADDED");
                            intent.putExtra("tag", str);
                            intent.putExtra("time", createEntry);
                            intent.putExtra("android.os.extra.DROPPED_COUNT", 0);
                            dropBoxManagerBroadcastHandler.sendMessage(dropBoxManagerBroadcastHandler.obtainMessage(1, intent));
                        } else {
                            dropBoxManagerBroadcastHandler.maybeDeferBroadcast(createEntry, str);
                        }
                        IoUtils.closeQuietly(dropBoxManagerInternal$EntrySource);
                    } catch (IOException e2) {
                        e = e2;
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e = e4;
            } catch (Throwable th7) {
                th = th7;
            }
        } catch (Throwable th8) {
            th = th8;
        }
    }

    public final void addEntry(String str, InputStream inputStream, long j, int i) {
        boolean z;
        if ((i & 4) != 0 || j <= 16384) {
            z = false;
        } else {
            i |= 4;
            z = true;
        }
        addEntry(str, new SimpleEntrySource(j, inputStream, z), i);
    }

    public final boolean checkPermission(int i, String str, String str2) {
        if (getContext().checkCallingPermission("android.permission.PEEK_DROPBOX_DATA") == 0) {
            return true;
        }
        getContext().enforceCallingOrSelfPermission((Flags.enableReadDropboxPermission() && CompatChanges.isChangeEnabled(296060945L, i)) ? "android.permission.READ_DROPBOX_DATA" : "android.permission.READ_LOGS", "DropBoxManagerService");
        int noteOp = ((AppOpsManager) getContext().getSystemService(AppOpsManager.class)).noteOp(43, i, str, str2, (String) null);
        if (noteOp != 0) {
            if (noteOp != 3) {
                return false;
            }
            getContext().enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", "DropBoxManagerService");
        }
        return true;
    }

    public final synchronized long createEntry(int i, File file, String str) {
        long currentTimeMillis;
        EntryFile[] entryFileArr;
        try {
            currentTimeMillis = System.currentTimeMillis();
            SortedSet tailSet = this.mAllFiles.contents.tailSet(new EntryFile(10000 + currentTimeMillis));
            if (tailSet.isEmpty()) {
                entryFileArr = null;
            } else {
                entryFileArr = (EntryFile[]) tailSet.toArray(new EntryFile[tailSet.size()]);
                tailSet.clear();
            }
            if (!this.mAllFiles.contents.isEmpty()) {
                currentTimeMillis = Math.max(currentTimeMillis, ((EntryFile) this.mAllFiles.contents.last()).timestampMillis + 1);
            }
            if (entryFileArr != null) {
                long j = currentTimeMillis;
                for (EntryFile entryFile : entryFileArr) {
                    this.mAllFiles.blocks -= entryFile.blocks;
                    FileList fileList = (FileList) this.mFilesByTag.get(entryFile.tag);
                    if (fileList != null && fileList.contents.remove(entryFile)) {
                        fileList.blocks -= entryFile.blocks;
                    }
                    if ((entryFile.flags & 1) == 0) {
                        enrollEntry(new EntryFile(entryFile.getFile(this.mDropBoxDir), this.mDropBoxDir, entryFile.tag, j, entryFile.flags, this.mBlockSize));
                        j++;
                    } else {
                        enrollEntry(new EntryFile(this.mDropBoxDir, entryFile.tag, j));
                        j++;
                    }
                }
                currentTimeMillis = j;
            }
            if (file == null) {
                enrollEntry(new EntryFile(this.mDropBoxDir, str, currentTimeMillis));
            } else {
                enrollEntry(new EntryFile(file, this.mDropBoxDir, str, currentTimeMillis, i, this.mBlockSize));
            }
        } catch (Throwable th) {
            throw th;
        }
        return currentTimeMillis;
    }

    public final void dumpProtoLocked(FileDescriptor fileDescriptor, ArrayList arrayList) {
        File file;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        Iterator it = this.mAllFiles.contents.iterator();
        while (it.hasNext()) {
            EntryFile entryFile = (EntryFile) it.next();
            if (matchEntry(entryFile, arrayList) && (file = entryFile.getFile(this.mDropBoxDir)) != null && (entryFile.flags & 1) == 0) {
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1112396529665L, entryFile.timestampMillis);
                try {
                    DropBoxManager.Entry entry = new DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis, file, entryFile.flags);
                    try {
                        InputStream inputStream = entry.getInputStream();
                        if (inputStream != null) {
                            try {
                                byte[] bArr = new byte[262144];
                                int i = 0;
                                int i2 = 0;
                                while (i >= 0) {
                                    i2 += i;
                                    if (i2 >= 262144) {
                                        break;
                                    } else {
                                        i = inputStream.read(bArr, i2, 262144 - i2);
                                    }
                                }
                                protoOutputStream.write(1151051235330L, Arrays.copyOf(bArr, i2));
                            } catch (Throwable th) {
                                try {
                                    inputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entry.close();
                    } catch (Throwable th3) {
                        try {
                            entry.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                } catch (IOException e) {
                    Slog.e("DropBoxManagerService", "Can't read: " + file, e);
                }
                protoOutputStream.end(start);
            }
        }
        protoOutputStream.flush();
    }

    public final synchronized void enrollEntry(EntryFile entryFile) {
        try {
            this.mAllFiles.contents.add(entryFile);
            FileList fileList = this.mAllFiles;
            int i = fileList.blocks;
            int i2 = entryFile.blocks;
            fileList.blocks = i + i2;
            String str = entryFile.tag;
            if (str != null && i2 > 0) {
                FileList fileList2 = (FileList) this.mFilesByTag.get(str);
                if (fileList2 == null) {
                    fileList2 = new FileList();
                    this.mFilesByTag.put(TextUtils.safeIntern(entryFile.tag), fileList2);
                }
                fileList2.contents.add(entryFile);
                fileList2.blocks += entryFile.blocks;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void getLowPriorityResourceConfigs() {
        this.mLowPriorityRateLimitPeriod = Resources.getSystem().getInteger(R.integer.config_lockSoundVolumeDb);
        String[] stringArray = Resources.getSystem().getStringArray(R.array.face_error_vendor);
        int length = stringArray.length;
        if (length == 0) {
            this.mLowPriorityTags = null;
            return;
        }
        this.mLowPriorityTags = new ArraySet(length);
        for (String str : stringArray) {
            this.mLowPriorityTags.add(str);
        }
    }

    public final synchronized void init$1() {
        if (this.mStatFs == null) {
            if (!this.mDropBoxDir.isDirectory() && !this.mDropBoxDir.mkdirs()) {
                throw new IOException("Can't mkdir: " + this.mDropBoxDir);
            }
            try {
                StatFs statFs = new StatFs(this.mDropBoxDir.getPath());
                this.mStatFs = statFs;
                this.mBlockSize = statFs.getBlockSize();
            } catch (IllegalArgumentException unused) {
                throw new IOException("Can't statfs: " + this.mDropBoxDir);
            }
        }
        if (this.mAllFiles == null) {
            File[] listFiles = this.mDropBoxDir.listFiles();
            if (listFiles == null) {
                throw new IOException("Can't list files: " + this.mDropBoxDir);
            }
            this.mAllFiles = new FileList();
            this.mFilesByTag = new ArrayMap();
            for (File file : listFiles) {
                if (file.getName().endsWith(".tmp")) {
                    Slog.i("DropBoxManagerService", "Cleaning temp file: " + file);
                    file.delete();
                } else {
                    EntryFile entryFile = new EntryFile(file, this.mBlockSize);
                    if (entryFile.tag != null) {
                        enrollEntry(entryFile);
                    }
                }
            }
        }
    }

    public final boolean isTagEnabled(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (DISABLED_BY_DEFAULT_TAGS.contains(str)) {
                return "enabled".equals(Settings.Global.getString(this.mContentResolver, "dropbox:" + str));
            }
            ContentResolver contentResolver = this.mContentResolver;
            return !"disabled".equals(Settings.Global.getString(contentResolver, "dropbox:" + str));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i != 500) {
            if (i != 1000) {
                return;
            }
            this.mBooted = true;
        } else {
            getContext().registerReceiver(this.mReceiver, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.DEVICE_STORAGE_LOW"));
            this.mContentResolver.registerContentObserver(Settings.Global.CONTENT_URI, true, new ContentObserver(new Handler()) { // from class: com.android.server.DropBoxManagerService.3
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    DropBoxManagerService dropBoxManagerService = DropBoxManagerService.this;
                    dropBoxManagerService.mReceiver.onReceive(dropBoxManagerService.getContext(), null);
                }
            });
            getLowPriorityResourceConfigs();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("dropbox", this.mStub);
    }

    public final synchronized long trimToFit() {
        try {
            int i = Settings.Global.getInt(this.mContentResolver, "dropbox_age_seconds", 259200);
            this.mMaxFiles = Settings.Global.getInt(this.mContentResolver, "dropbox_max_files", ActivityManager.isLowRamDeviceStatic() ? 300 : 1000);
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - (i * 1000);
            while (!this.mAllFiles.contents.isEmpty()) {
                EntryFile entryFile = (EntryFile) this.mAllFiles.contents.first();
                if (entryFile.timestampMillis > j && this.mAllFiles.contents.size() < this.mMaxFiles) {
                    break;
                }
                FrameworkStatsLog.write(FrameworkStatsLog.DROPBOX_ENTRY_DROPPED, 4, entryFile.tag, currentTimeMillis - entryFile.timestampMillis);
                FileList fileList = (FileList) this.mFilesByTag.get(entryFile.tag);
                if (fileList != null && fileList.contents.remove(entryFile)) {
                    fileList.blocks -= entryFile.blocks;
                }
                if (this.mAllFiles.contents.remove(entryFile)) {
                    this.mAllFiles.blocks -= entryFile.blocks;
                }
                File file = this.mDropBoxDir;
                if (entryFile.tag != null) {
                    entryFile.getFile(file).delete();
                }
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            int i2 = 0;
            if (uptimeMillis > this.mCachedQuotaUptimeMillis + 5000) {
                int i3 = Settings.Global.getInt(this.mContentResolver, "dropbox_quota_percent", 10);
                int i4 = Settings.Global.getInt(this.mContentResolver, "dropbox_reserve_percent", 0);
                int i5 = Settings.Global.getInt(this.mContentResolver, "dropbox_quota_kb", DEFAULT_QUOTA_KB);
                try {
                    this.mStatFs.restat(this.mDropBoxDir.getPath());
                    this.mCachedQuotaBlocks = Math.min((i5 * 1024) / this.mBlockSize, Math.toIntExact(Math.max(0L, Math.min(((this.mStatFs.getAvailableBlocksLong() - ((this.mStatFs.getBlockCountLong() * i4) / 100)) * i3) / 100, 2147483647L))));
                    this.mCachedQuotaUptimeMillis = uptimeMillis;
                } catch (IllegalArgumentException unused) {
                    throw new IOException("Can't restat: " + this.mDropBoxDir);
                }
            }
            int i6 = this.mAllFiles.blocks;
            if (i6 > this.mCachedQuotaBlocks) {
                TreeSet treeSet = new TreeSet(this.mFilesByTag.values());
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    FileList fileList2 = (FileList) it.next();
                    if (i2 > 0 && fileList2.blocks <= (this.mCachedQuotaBlocks - i6) / i2) {
                        break;
                    }
                    i6 -= fileList2.blocks;
                    i2++;
                }
                int i7 = (this.mCachedQuotaBlocks - i6) / i2;
                Iterator it2 = treeSet.iterator();
                while (it2.hasNext()) {
                    FileList fileList3 = (FileList) it2.next();
                    if (this.mAllFiles.blocks < this.mCachedQuotaBlocks) {
                        break;
                    }
                    while (fileList3.blocks > i7 && !fileList3.contents.isEmpty()) {
                        EntryFile entryFile2 = (EntryFile) fileList3.contents.first();
                        FrameworkStatsLog.write(FrameworkStatsLog.DROPBOX_ENTRY_DROPPED, 3, entryFile2.tag, currentTimeMillis - entryFile2.timestampMillis);
                        if (fileList3.contents.remove(entryFile2)) {
                            fileList3.blocks -= entryFile2.blocks;
                        }
                        if (this.mAllFiles.contents.remove(entryFile2)) {
                            this.mAllFiles.blocks -= entryFile2.blocks;
                        }
                        try {
                            File file2 = this.mDropBoxDir;
                            if (entryFile2.tag != null) {
                                entryFile2.getFile(file2).delete();
                            }
                            enrollEntry(new EntryFile(this.mDropBoxDir, entryFile2.tag, entryFile2.timestampMillis));
                        } catch (IOException e) {
                            Slog.e("DropBoxManagerService", "Can't write tombstone file", e);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mCachedQuotaBlocks * this.mBlockSize;
    }
}
