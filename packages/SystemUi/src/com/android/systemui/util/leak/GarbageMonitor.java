package com.android.systemui.util.leak;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import androidx.core.content.FileProvider;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import com.android.systemui.util.leak.GarbageMonitor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.settings.ImsProfile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GarbageMonitor implements Dumpable {
    public static final boolean DEBUG;
    public static final boolean ENABLE_AM_HEAP_LIMIT;
    public static final boolean HEAP_TRACKING_ENABLED;
    public static final boolean LEAK_REPORTING_ENABLED;
    public final Context mContext;
    public final DelayableExecutor mDelayableExecutor;
    public final DumpTruck mDumpTruck;
    public final long mHeapLimit;
    public final LeakReporter mLeakReporter;
    public final MessageRouter mMessageRouter;
    public MemoryTile mQSTile;
    public final TrackedGarbage mTrackedGarbage;
    public final LongSparseArray mData = new LongSparseArray();
    public final ArrayList mPids = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MemoryGraphIcon extends QSTile.Icon {
        public long limit;
        public long rss;

        public /* synthetic */ MemoryGraphIcon(int i) {
            this();
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public final Drawable getDrawable(Context context) {
            MemoryIconDrawable memoryIconDrawable = new MemoryIconDrawable(context);
            long j = this.rss;
            if (j != memoryIconDrawable.rss) {
                memoryIconDrawable.rss = j;
                memoryIconDrawable.invalidateSelf();
            }
            long j2 = this.limit;
            if (j2 != memoryIconDrawable.limit) {
                memoryIconDrawable.limit = j2;
                memoryIconDrawable.invalidateSelf();
            }
            return memoryIconDrawable;
        }

        private MemoryGraphIcon() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MemoryIconDrawable extends Drawable {
        public final Drawable baseIcon;

        /* renamed from: dp */
        public final float f394dp;
        public long limit;
        public final Paint paint;
        public long rss;

        public MemoryIconDrawable(Context context) {
            Paint paint = new Paint();
            this.paint = paint;
            this.baseIcon = context.getDrawable(R.drawable.ic_memory).mutate();
            this.f394dp = context.getResources().getDisplayMetrics().density;
            paint.setColor(-1);
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            this.baseIcon.draw(canvas);
            long j = this.limit;
            if (j > 0) {
                long j2 = this.rss;
                if (j2 > 0) {
                    float min = Math.min(1.0f, j2 / j);
                    float f = getBounds().left;
                    float f2 = this.f394dp;
                    canvas.translate((f2 * 8.0f) + f, (f2 * 5.0f) + r2.top);
                    float f3 = this.f394dp;
                    canvas.drawRect(0.0f, (1.0f - min) * f3 * 14.0f, (8.0f * f3) + 1.0f, (f3 * 14.0f) + 1.0f, this.paint);
                }
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return this.baseIcon.getIntrinsicHeight();
        }

        @Override // android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return this.baseIcon.getIntrinsicWidth();
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            this.baseIcon.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setBounds(int i, int i2, int i3, int i4) {
            super.setBounds(i, i2, i3, i4);
            this.baseIcon.setBounds(i, i2, i3, i4);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            this.baseIcon.setColorFilter(colorFilter);
            this.paint.setColorFilter(colorFilter);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTint(int i) {
            super.setTint(i);
            this.baseIcon.setTint(i);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            this.baseIcon.setTintList(colorStateList);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTintMode(PorterDuff.Mode mode) {
            super.setTintMode(mode);
            this.baseIcon.setTintMode(mode);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MemoryTile extends QSTileImpl {
        public boolean dumpInProgress;

        /* renamed from: gm */
        public final GarbageMonitor f395gm;
        public final PanelInteractor mPanelInteractor;
        public ProcessMemInfo pmi;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.util.leak.GarbageMonitor$MemoryTile$1 */
        public final class C35891 extends Thread {
            public C35891(String str) {
                super(str);
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                ArrayList arrayList;
                ArrayList arrayList2;
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException unused) {
                }
                GarbageMonitor garbageMonitor = MemoryTile.this.f395gm;
                ArrayList arrayList3 = garbageMonitor.mPids;
                DumpTruck dumpTruck = garbageMonitor.mDumpTruck;
                dumpTruck.getClass();
                Context context = dumpTruck.context;
                File file = new File(context.getCacheDir(), "leak");
                file.mkdirs();
                dumpTruck.hprofUri = null;
                StringBuilder sb = dumpTruck.body;
                sb.setLength(0);
                sb.append("Build: ");
                sb.append(Build.DISPLAY);
                sb.append("\n\nProcesses:\n");
                ArrayList arrayList4 = new ArrayList();
                int myPid = Process.myPid();
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    int intValue = ((Long) it.next()).intValue();
                    sb.append("  pid ");
                    sb.append(intValue);
                    ProcessMemInfo processMemInfo = (ProcessMemInfo) dumpTruck.mGarbageMonitor.mData.get(intValue);
                    if (processMemInfo != null) {
                        sb.append(":");
                        sb.append(" up=");
                        arrayList = arrayList4;
                        sb.append(System.currentTimeMillis() - processMemInfo.startTime);
                        sb.append(" rss=");
                        sb.append(processMemInfo.currentRss);
                        dumpTruck.rss = processMemInfo.currentRss;
                    } else {
                        arrayList = arrayList4;
                    }
                    if (intValue == myPid) {
                        String path = new File(file, String.format("heap-%d.ahprof", Integer.valueOf(intValue))).getPath();
                        try {
                            Debug.dumpHprofData(path);
                            arrayList2 = arrayList;
                        } catch (IOException e) {
                            e = e;
                            arrayList2 = arrayList;
                        }
                        try {
                            arrayList2.add(path);
                            sb.append(" (hprof attached)");
                        } catch (IOException e2) {
                            e = e2;
                            Log.e("DumpTruck", "error dumping memory:", e);
                            sb.append("\n** Could not dump heap: \n");
                            sb.append(e.toString());
                            sb.append("\n");
                            sb.append("\n");
                            arrayList4 = arrayList2;
                        }
                    } else {
                        arrayList2 = arrayList;
                    }
                    sb.append("\n");
                    arrayList4 = arrayList2;
                }
                ArrayList arrayList5 = arrayList4;
                try {
                    String canonicalPath = new File(file, String.format("hprof-%d.zip", Long.valueOf(System.currentTimeMillis()))).getCanonicalPath();
                    if (DumpTruck.zipUp(canonicalPath, arrayList5)) {
                        Uri uriForFile = FileProvider.getUriForFile(context, "com.android.systemui.fileprovider", new File(canonicalPath));
                        dumpTruck.hprofUri = uriForFile;
                        Objects.toString(uriForFile);
                    }
                } catch (IOException e3) {
                    Log.e("DumpTruck", "unable to zip up heapdumps", e3);
                    sb.append("\n** Could not zip up files: \n");
                    sb.append(e3.toString());
                    sb.append("\n");
                }
                final Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.addFlags(1);
                intent.putExtra("android.intent.extra.SUBJECT", String.format("SystemUI memory dump (rss=%dM)", Long.valueOf(dumpTruck.rss / 1024)));
                intent.putExtra("android.intent.extra.TEXT", sb.toString());
                if (dumpTruck.hprofUri != null) {
                    ArrayList<? extends Parcelable> arrayList6 = new ArrayList<>();
                    arrayList6.add(dumpTruck.hprofUri);
                    intent.setType("application/zip");
                    intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList6);
                    intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(dumpTruck.hprofUri)));
                    intent.addFlags(1);
                }
                MemoryTile.this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.leak.GarbageMonitor$MemoryTile$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GarbageMonitor.MemoryTile.C35891 c35891 = GarbageMonitor.MemoryTile.C35891.this;
                        Intent intent2 = intent;
                        GarbageMonitor.MemoryTile memoryTile = GarbageMonitor.MemoryTile.this;
                        memoryTile.dumpInProgress = false;
                        memoryTile.refreshState(null);
                        GarbageMonitor.MemoryTile.this.mPanelInteractor.collapsePanels();
                        GarbageMonitor.MemoryTile.this.mActivityStarter.postStartActivityDismissingKeyguard(intent2, 0);
                    }
                });
            }
        }

        public MemoryTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, GarbageMonitor garbageMonitor, PanelInteractor panelInteractor) {
            super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
            this.f395gm = garbageMonitor;
            this.mPanelInteractor = panelInteractor;
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
        public final Intent getLongClickIntent() {
            return new Intent();
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
        public final int getMetricsCategory() {
            return 0;
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
        public final CharSequence getTileLabel() {
            return this.mState.label;
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
        public final void handleClick(View view) {
            if (this.dumpInProgress) {
                return;
            }
            this.dumpInProgress = true;
            refreshState(null);
            new C35891("HeapDumpThread").start();
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
        public final void handleSetListening(boolean z) {
            super.handleSetListening(z);
            GarbageMonitor garbageMonitor = this.f395gm;
            if (garbageMonitor != null) {
                MemoryTile memoryTile = z ? this : null;
                garbageMonitor.mQSTile = memoryTile;
                if (memoryTile != null) {
                    memoryTile.refreshState(null);
                }
            }
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService(ActivityManager.class);
            if (z) {
                long j = garbageMonitor.mHeapLimit;
                if (j > 0) {
                    activityManager.setWatchHeapLimit(j * 1024);
                    return;
                }
            }
            activityManager.clearWatchHeapLimit();
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
        public final void handleUpdateState(QSTile.State state, Object obj) {
            int myPid = Process.myPid();
            GarbageMonitor garbageMonitor = this.f395gm;
            this.pmi = (ProcessMemInfo) garbageMonitor.mData.get(myPid);
            MemoryGraphIcon memoryGraphIcon = new MemoryGraphIcon(0);
            long j = garbageMonitor.mHeapLimit;
            memoryGraphIcon.limit = j;
            boolean z = this.dumpInProgress;
            state.state = z ? 0 : 2;
            state.label = z ? "Dumping..." : this.mContext.getString(R.string.heap_dump_tile_name);
            ProcessMemInfo processMemInfo = this.pmi;
            if (processMemInfo != null) {
                long j2 = processMemInfo.currentRss;
                memoryGraphIcon.rss = j2;
                state.secondaryLabel = String.format("rss: %s / %s", GarbageMonitor.m2727$$Nest$smformatBytes(j2 * 1024), GarbageMonitor.m2727$$Nest$smformatBytes(j * 1024));
            } else {
                memoryGraphIcon.rss = 0L;
                state.secondaryLabel = null;
            }
            state.icon = memoryGraphIcon;
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
        public final QSTile.State newTileState() {
            return new QSTile.State();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProcessMemInfo implements Dumpable {
        public long currentRss;
        public final String name;
        public final long pid;
        public final long startTime;
        public final long[] rss = new long[720];
        public long max = 1;
        public int head = 0;

        public ProcessMemInfo(long j, String str, long j2) {
            this.pid = j;
            this.name = str;
            this.startTime = j2;
        }

        @Override // com.android.systemui.Dumpable
        public final void dump(PrintWriter printWriter, String[] strArr) {
            printWriter.print("{ \"pid\": ");
            printWriter.print(this.pid);
            printWriter.print(", \"name\": \"");
            printWriter.print(this.name.replace('\"', '-'));
            printWriter.print("\", \"start\": ");
            printWriter.print(this.startTime);
            printWriter.print(", \"rss\": [");
            int i = 0;
            while (true) {
                long[] jArr = this.rss;
                if (i >= jArr.length) {
                    printWriter.println("] }");
                    return;
                }
                if (i > 0) {
                    printWriter.print(",");
                }
                printWriter.print(jArr[(this.head + i) % jArr.length]);
                i++;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Service implements CoreStartable, Dumpable {
        public final Context mContext;
        public final GarbageMonitor mGarbageMonitor;

        public Service(Context context, GarbageMonitor garbageMonitor) {
            this.mContext = context;
            this.mGarbageMonitor = garbageMonitor;
        }

        @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
        public final void dump(PrintWriter printWriter, String[] strArr) {
            GarbageMonitor garbageMonitor = this.mGarbageMonitor;
            if (garbageMonitor != null) {
                garbageMonitor.dump(printWriter, strArr);
            }
        }

        @Override // com.android.systemui.CoreStartable
        public final void start() {
            boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), "sysui_force_enable_leak_reporting", 0) != 0;
            if (GarbageMonitor.LEAK_REPORTING_ENABLED || z) {
                GarbageMonitor garbageMonitor = this.mGarbageMonitor;
                if (garbageMonitor.mTrackedGarbage != null) {
                    MessageRouter messageRouter = garbageMonitor.mMessageRouter;
                    messageRouter.getClass();
                    ((MessageRouterImpl) messageRouter).sendMessageDelayed(1000, 0L);
                }
            }
            if (GarbageMonitor.HEAP_TRACKING_ENABLED || z) {
                GarbageMonitor garbageMonitor2 = this.mGarbageMonitor;
                garbageMonitor2.getClass();
                long myPid = Process.myPid();
                String packageName = garbageMonitor2.mContext.getPackageName();
                long currentTimeMillis = System.currentTimeMillis();
                synchronized (garbageMonitor2.mPids) {
                    if (!garbageMonitor2.mPids.contains(Long.valueOf(myPid))) {
                        garbageMonitor2.mPids.add(Long.valueOf(myPid));
                        garbageMonitor2.logPids();
                        garbageMonitor2.mData.put(myPid, new ProcessMemInfo(myPid, packageName, currentTimeMillis));
                    }
                }
                MessageRouter messageRouter2 = garbageMonitor2.mMessageRouter;
                messageRouter2.getClass();
                ((MessageRouterImpl) messageRouter2).sendMessageDelayed(3000, 0L);
            }
        }
    }

    /* renamed from: -$$Nest$smformatBytes, reason: not valid java name */
    public static String m2727$$Nest$smformatBytes(long j) {
        String[] strArr = {ImsProfile.TIMER_NAME_B, ImsProfile.TIMER_NAME_K, "M", ImsProfile.TIMER_NAME_G, "T"};
        int i = 0;
        while (i < 5 && j >= 1024) {
            j /= 1024;
            i++;
        }
        return j + strArr[i];
    }

    static {
        LEAK_REPORTING_ENABLED = Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.enable_leak_reporting", false);
        boolean z = Build.IS_DEBUGGABLE;
        HEAP_TRACKING_ENABLED = z;
        ENABLE_AM_HEAP_LIMIT = z && SystemProperties.getBoolean("debug.enable_sysui_heap_limit", false);
        DEBUG = Log.isLoggable("GarbageMonitor", 3);
    }

    public GarbageMonitor(Context context, DelayableExecutor delayableExecutor, MessageRouter messageRouter, LeakDetector leakDetector, LeakReporter leakReporter, DumpManager dumpManager) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mDelayableExecutor = delayableExecutor;
        this.mMessageRouter = messageRouter;
        final int i = 0;
        ((MessageRouterImpl) messageRouter).subscribeTo(1000, new MessageRouter.SimpleMessageListener(this) { // from class: com.android.systemui.util.leak.GarbageMonitor$$ExternalSyntheticLambda0
            public final /* synthetic */ GarbageMonitor f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.SimpleMessageListener
            public final void onMessage() {
                boolean z = false;
                switch (i) {
                    case 0:
                        final GarbageMonitor garbageMonitor = this.f$0;
                        if (garbageMonitor.mTrackedGarbage.countOldGarbage() > 5) {
                            Runtime.getRuntime().gc();
                            z = true;
                        }
                        if (z) {
                            garbageMonitor.mDelayableExecutor.executeDelayed(100L, new Runnable() { // from class: com.android.systemui.util.leak.GarbageMonitor$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    GarbageMonitor garbageMonitor2 = GarbageMonitor.this;
                                    int countOldGarbage = garbageMonitor2.mTrackedGarbage.countOldGarbage();
                                    if (countOldGarbage <= 5) {
                                        return;
                                    }
                                    LeakReporter leakReporter2 = garbageMonitor2.mLeakReporter;
                                    Context context2 = leakReporter2.mContext;
                                    try {
                                        File file = new File(context2.getCacheDir(), "leak");
                                        file.mkdir();
                                        File file2 = new File(file, "leak.hprof");
                                        Debug.dumpHprofData(file2.getAbsolutePath());
                                        File file3 = new File(file, "leak.dump");
                                        FileOutputStream fileOutputStream = new FileOutputStream(file3);
                                        try {
                                            PrintWriter printWriter = new PrintWriter(fileOutputStream);
                                            printWriter.print("Build: ");
                                            printWriter.println(SystemProperties.get("ro.build.description"));
                                            printWriter.println();
                                            printWriter.flush();
                                            leakReporter2.mLeakDetector.dump(printWriter, new String[0]);
                                            printWriter.close();
                                            fileOutputStream.close();
                                            NotificationManager notificationManager = (NotificationManager) context2.getSystemService(NotificationManager.class);
                                            NotificationChannel notificationChannel = new NotificationChannel("leak", "Leak Alerts", 4);
                                            notificationChannel.enableVibration(true);
                                            notificationManager.createNotificationChannel(notificationChannel);
                                            notificationManager.notify("LeakReporter", 0, new Notification.Builder(context2, notificationChannel.getId()).setAutoCancel(true).setShowWhen(true).setContentTitle("Memory Leak Detected").setContentText(String.format("SystemUI has detected %d leaked objects. Tap to send", Integer.valueOf(countOldGarbage))).setSmallIcon(17304219).setContentIntent(PendingIntent.getActivityAsUser(leakReporter2.mContext, 0, leakReporter2.getIntent(file2, file3), 201326592, null, ((UserTrackerImpl) leakReporter2.mUserTracker).getUserHandle())).build());
                                        } finally {
                                        }
                                    } catch (IOException e) {
                                        Log.e("LeakReporter", "Couldn't dump heap for leak", e);
                                    }
                                }
                            });
                        }
                        MessageRouterImpl messageRouterImpl = (MessageRouterImpl) garbageMonitor.mMessageRouter;
                        messageRouterImpl.cancelMessages(1000);
                        messageRouterImpl.sendMessageDelayed(1000, 900000L);
                        return;
                    default:
                        GarbageMonitor garbageMonitor2 = this.f$0;
                        synchronized (garbageMonitor2.mPids) {
                            int i2 = 0;
                            while (true) {
                                if (i2 < garbageMonitor2.mPids.size()) {
                                    int intValue = ((Long) garbageMonitor2.mPids.get(i2)).intValue();
                                    long[] rss = Process.getRss(intValue);
                                    if (rss != null || rss.length != 0) {
                                        long j = rss[0];
                                        long j2 = intValue;
                                        GarbageMonitor.ProcessMemInfo processMemInfo = (GarbageMonitor.ProcessMemInfo) garbageMonitor2.mData.get(j2);
                                        long[] jArr = processMemInfo.rss;
                                        int i3 = processMemInfo.head;
                                        processMemInfo.currentRss = j;
                                        jArr[i3] = j;
                                        processMemInfo.head = (i3 + 1) % jArr.length;
                                        if (j > processMemInfo.max) {
                                            processMemInfo.max = j;
                                        }
                                        if (j == 0) {
                                            garbageMonitor2.mData.remove(j2);
                                        }
                                        i2++;
                                    } else if (GarbageMonitor.DEBUG) {
                                        Log.e("GarbageMonitor", "update: Process.getRss() didn't provide any values.");
                                    }
                                }
                            }
                            int size = garbageMonitor2.mPids.size();
                            while (true) {
                                size--;
                                if (size >= 0) {
                                    if (garbageMonitor2.mData.get(((Long) garbageMonitor2.mPids.get(size)).intValue()) == null) {
                                        garbageMonitor2.mPids.remove(size);
                                        garbageMonitor2.logPids();
                                    }
                                }
                            }
                        }
                        GarbageMonitor.MemoryTile memoryTile = garbageMonitor2.mQSTile;
                        if (memoryTile != null) {
                            memoryTile.refreshState(null);
                        }
                        ((MessageRouterImpl) garbageMonitor2.mMessageRouter).cancelMessages(3000);
                        ((MessageRouterImpl) garbageMonitor2.mMessageRouter).sendMessageDelayed(3000, 60000L);
                        return;
                }
            }
        });
        final int i2 = 1;
        ((MessageRouterImpl) messageRouter).subscribeTo(3000, new MessageRouter.SimpleMessageListener(this) { // from class: com.android.systemui.util.leak.GarbageMonitor$$ExternalSyntheticLambda0
            public final /* synthetic */ GarbageMonitor f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.SimpleMessageListener
            public final void onMessage() {
                boolean z = false;
                switch (i2) {
                    case 0:
                        final GarbageMonitor garbageMonitor = this.f$0;
                        if (garbageMonitor.mTrackedGarbage.countOldGarbage() > 5) {
                            Runtime.getRuntime().gc();
                            z = true;
                        }
                        if (z) {
                            garbageMonitor.mDelayableExecutor.executeDelayed(100L, new Runnable() { // from class: com.android.systemui.util.leak.GarbageMonitor$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    GarbageMonitor garbageMonitor2 = GarbageMonitor.this;
                                    int countOldGarbage = garbageMonitor2.mTrackedGarbage.countOldGarbage();
                                    if (countOldGarbage <= 5) {
                                        return;
                                    }
                                    LeakReporter leakReporter2 = garbageMonitor2.mLeakReporter;
                                    Context context2 = leakReporter2.mContext;
                                    try {
                                        File file = new File(context2.getCacheDir(), "leak");
                                        file.mkdir();
                                        File file2 = new File(file, "leak.hprof");
                                        Debug.dumpHprofData(file2.getAbsolutePath());
                                        File file3 = new File(file, "leak.dump");
                                        FileOutputStream fileOutputStream = new FileOutputStream(file3);
                                        try {
                                            PrintWriter printWriter = new PrintWriter(fileOutputStream);
                                            printWriter.print("Build: ");
                                            printWriter.println(SystemProperties.get("ro.build.description"));
                                            printWriter.println();
                                            printWriter.flush();
                                            leakReporter2.mLeakDetector.dump(printWriter, new String[0]);
                                            printWriter.close();
                                            fileOutputStream.close();
                                            NotificationManager notificationManager = (NotificationManager) context2.getSystemService(NotificationManager.class);
                                            NotificationChannel notificationChannel = new NotificationChannel("leak", "Leak Alerts", 4);
                                            notificationChannel.enableVibration(true);
                                            notificationManager.createNotificationChannel(notificationChannel);
                                            notificationManager.notify("LeakReporter", 0, new Notification.Builder(context2, notificationChannel.getId()).setAutoCancel(true).setShowWhen(true).setContentTitle("Memory Leak Detected").setContentText(String.format("SystemUI has detected %d leaked objects. Tap to send", Integer.valueOf(countOldGarbage))).setSmallIcon(17304219).setContentIntent(PendingIntent.getActivityAsUser(leakReporter2.mContext, 0, leakReporter2.getIntent(file2, file3), 201326592, null, ((UserTrackerImpl) leakReporter2.mUserTracker).getUserHandle())).build());
                                        } finally {
                                        }
                                    } catch (IOException e) {
                                        Log.e("LeakReporter", "Couldn't dump heap for leak", e);
                                    }
                                }
                            });
                        }
                        MessageRouterImpl messageRouterImpl = (MessageRouterImpl) garbageMonitor.mMessageRouter;
                        messageRouterImpl.cancelMessages(1000);
                        messageRouterImpl.sendMessageDelayed(1000, 900000L);
                        return;
                    default:
                        GarbageMonitor garbageMonitor2 = this.f$0;
                        synchronized (garbageMonitor2.mPids) {
                            int i22 = 0;
                            while (true) {
                                if (i22 < garbageMonitor2.mPids.size()) {
                                    int intValue = ((Long) garbageMonitor2.mPids.get(i22)).intValue();
                                    long[] rss = Process.getRss(intValue);
                                    if (rss != null || rss.length != 0) {
                                        long j = rss[0];
                                        long j2 = intValue;
                                        GarbageMonitor.ProcessMemInfo processMemInfo = (GarbageMonitor.ProcessMemInfo) garbageMonitor2.mData.get(j2);
                                        long[] jArr = processMemInfo.rss;
                                        int i3 = processMemInfo.head;
                                        processMemInfo.currentRss = j;
                                        jArr[i3] = j;
                                        processMemInfo.head = (i3 + 1) % jArr.length;
                                        if (j > processMemInfo.max) {
                                            processMemInfo.max = j;
                                        }
                                        if (j == 0) {
                                            garbageMonitor2.mData.remove(j2);
                                        }
                                        i22++;
                                    } else if (GarbageMonitor.DEBUG) {
                                        Log.e("GarbageMonitor", "update: Process.getRss() didn't provide any values.");
                                    }
                                }
                            }
                            int size = garbageMonitor2.mPids.size();
                            while (true) {
                                size--;
                                if (size >= 0) {
                                    if (garbageMonitor2.mData.get(((Long) garbageMonitor2.mPids.get(size)).intValue()) == null) {
                                        garbageMonitor2.mPids.remove(size);
                                        garbageMonitor2.logPids();
                                    }
                                }
                            }
                        }
                        GarbageMonitor.MemoryTile memoryTile = garbageMonitor2.mQSTile;
                        if (memoryTile != null) {
                            memoryTile.refreshState(null);
                        }
                        ((MessageRouterImpl) garbageMonitor2.mMessageRouter).cancelMessages(3000);
                        ((MessageRouterImpl) garbageMonitor2.mMessageRouter).sendMessageDelayed(3000, 60000L);
                        return;
                }
            }
        });
        this.mTrackedGarbage = leakDetector.mTrackedGarbage;
        this.mLeakReporter = leakReporter;
        this.mDumpTruck = new DumpTruck(applicationContext, this);
        dumpManager.registerDumpable("GarbageMonitor", this);
        if (ENABLE_AM_HEAP_LIMIT) {
            this.mHeapLimit = Settings.Global.getInt(context.getContentResolver(), "systemui_am_heap_limit", applicationContext.getResources().getInteger(R.integer.watch_heap_limit));
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("GarbageMonitor params:");
        printWriter.println(String.format("   mHeapLimit=%d KB", Long.valueOf(this.mHeapLimit)));
        printWriter.println(String.format("   GARBAGE_INSPECTION_INTERVAL=%d (%.1f mins)", 900000L, Float.valueOf(15.0f)));
        printWriter.println(String.format("   HEAP_TRACK_INTERVAL=%d (%.1f mins)", 60000L, Float.valueOf(1.0f)));
        printWriter.println(String.format("   HEAP_TRACK_HISTORY_LEN=%d (%.1f hr total)", 720, Float.valueOf(12.0f)));
        printWriter.println("GarbageMonitor tracked processes:");
        Iterator it = this.mPids.iterator();
        while (it.hasNext()) {
            ProcessMemInfo processMemInfo = (ProcessMemInfo) this.mData.get(((Long) it.next()).longValue());
            if (processMemInfo != null) {
                processMemInfo.dump(printWriter, strArr);
            }
        }
    }

    public final void logPids() {
        if (!DEBUG) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("Now tracking processes: ");
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mPids;
            if (i >= arrayList.size()) {
                return;
            }
            ((Long) arrayList.get(i)).intValue();
            stringBuffer.append(" ");
            i++;
        }
    }
}
