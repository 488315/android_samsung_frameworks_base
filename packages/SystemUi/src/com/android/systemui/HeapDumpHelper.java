package com.android.systemui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.util.Slog;
import com.android.systemui.HeapDumpHelper;
import com.android.systemui.util.DeviceType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HeapDumpHelper {
    public final Handler mBgHandler;
    public final Context mContext;
    public boolean isDumped = false;
    public String mHeapDumpFilePath = "";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FileManager {
        public final File mHeapDumpDir;

        public FileManager(String str) {
            File file = new File(str);
            this.mHeapDumpDir = file;
            if (file.exists()) {
                return;
            }
            file.mkdirs();
        }
    }

    public HeapDumpHelper(Context context, Handler handler) {
        this.mContext = context;
        this.mBgHandler = handler;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(final String str) {
        boolean z;
        final int i = 0;
        final int i2 = 1;
        boolean z2 = ("user".equals(Build.TYPE) && (DeviceType.getDebugLevel() == 0) && DeviceType.isShipBuild()) ? false : true;
        Context context = this.mContext;
        if (!z2) {
            String[] strArr = {"com.salab.act", "com.salab.issuetracker"};
            int i3 = 0;
            while (true) {
                if (i3 >= 2) {
                    Slog.d("HeapDumpHelper", "no test device");
                    strArr = null;
                    break;
                }
                String str2 = strArr[i3];
                try {
                    context.getPackageManager().getApplicationInfo(str2, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                }
                if (context.getPackageManager().checkSignatures(str2, "android") == 0) {
                    Slog.d("HeapDumpHelper", str2 + " is matched");
                    strArr = 1;
                    break;
                }
                Slog.d("HeapDumpHelper", str2 + " is not matched");
                i3++;
            }
            if (strArr == null) {
                return;
            }
        }
        if ("OutOfMemoryError".equals(str)) {
            try {
                Debug.dumpHprofData("/data/log/core/OOM_com.android.systemui.hprof");
                return;
            } catch (Exception e) {
                Slog.e("HeapDumpHelper", "Exception : " + e.getMessage());
                return;
            }
        }
        try {
        } catch (PackageManager.NameNotFoundException unused2) {
            Slog.d("HeapDumpHelper", "no UT device");
        }
        if (context.getPackageManager().getApplicationInfo("com.salab.issuetracker", 0) != null) {
            if (context.getPackageManager().checkSignatures("com.salab.issuetracker", "android") == 0) {
                Slog.d("HeapDumpHelper", "matched");
                z = true;
                Handler handler = this.mBgHandler;
                if (z) {
                    final String str3 = "/data/log/core/";
                    handler.post(new Runnable(this) { // from class: com.android.systemui.HeapDumpHelper$$ExternalSyntheticLambda0
                        public final /* synthetic */ HeapDumpHelper f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i2) {
                                case 0:
                                    HeapDumpHelper heapDumpHelper = this.f$0;
                                    String str4 = str3;
                                    heapDumpHelper.getClass();
                                    Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
                                    intent.setPackage("com.salab.issuetracker");
                                    intent.putExtra("ERRCODE", -132);
                                    intent.putExtra("ERRNAME", "SystemUI leak / " + str4);
                                    Context context2 = heapDumpHelper.mContext;
                                    intent.putExtra("ERRPKG", context2.getPackageName());
                                    intent.putExtra("ERRMSG", str4);
                                    intent.putExtra("EXTLOG", heapDumpHelper.mHeapDumpFilePath);
                                    context2.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                                    Slog.d("HeapDumpHelper", "sendIntent to IssueTracker");
                                    break;
                                default:
                                    HeapDumpHelper heapDumpHelper2 = this.f$0;
                                    String str5 = str3;
                                    heapDumpHelper2.getClass();
                                    try {
                                        for (File file : new HeapDumpHelper.FileManager(str5).mHeapDumpDir.listFiles(new HeapDumpHelper$FileManager$$ExternalSyntheticLambda0())) {
                                            file.delete();
                                        }
                                        String str6 = str5 + String.format("%s_%d_%s.hprof", "heap-systemui", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
                                        heapDumpHelper2.mHeapDumpFilePath = str6;
                                        Debug.dumpHprofData(str6);
                                        heapDumpHelper2.isDumped = true;
                                        break;
                                    } catch (Exception e2) {
                                        Slog.e("HeapDumpHelper", "Exception : " + e2.getMessage());
                                        heapDumpHelper2.isDumped = false;
                                        return;
                                    }
                            }
                        }
                    });
                    return;
                } else {
                    final String str4 = "/mnt/sdcard/ACT_LOGS/";
                    handler.post(new Runnable(this) { // from class: com.android.systemui.HeapDumpHelper$$ExternalSyntheticLambda0
                        public final /* synthetic */ HeapDumpHelper f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i2) {
                                case 0:
                                    HeapDumpHelper heapDumpHelper = this.f$0;
                                    String str42 = str4;
                                    heapDumpHelper.getClass();
                                    Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
                                    intent.setPackage("com.salab.issuetracker");
                                    intent.putExtra("ERRCODE", -132);
                                    intent.putExtra("ERRNAME", "SystemUI leak / " + str42);
                                    Context context2 = heapDumpHelper.mContext;
                                    intent.putExtra("ERRPKG", context2.getPackageName());
                                    intent.putExtra("ERRMSG", str42);
                                    intent.putExtra("EXTLOG", heapDumpHelper.mHeapDumpFilePath);
                                    context2.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                                    Slog.d("HeapDumpHelper", "sendIntent to IssueTracker");
                                    break;
                                default:
                                    HeapDumpHelper heapDumpHelper2 = this.f$0;
                                    String str5 = str4;
                                    heapDumpHelper2.getClass();
                                    try {
                                        for (File file : new HeapDumpHelper.FileManager(str5).mHeapDumpDir.listFiles(new HeapDumpHelper$FileManager$$ExternalSyntheticLambda0())) {
                                            file.delete();
                                        }
                                        String str6 = str5 + String.format("%s_%d_%s.hprof", "heap-systemui", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
                                        heapDumpHelper2.mHeapDumpFilePath = str6;
                                        Debug.dumpHprofData(str6);
                                        heapDumpHelper2.isDumped = true;
                                        break;
                                    } catch (Exception e2) {
                                        Slog.e("HeapDumpHelper", "Exception : " + e2.getMessage());
                                        heapDumpHelper2.isDumped = false;
                                        return;
                                    }
                            }
                        }
                    });
                    handler.postDelayed(new Runnable(this) { // from class: com.android.systemui.HeapDumpHelper$$ExternalSyntheticLambda0
                        public final /* synthetic */ HeapDumpHelper f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i) {
                                case 0:
                                    HeapDumpHelper heapDumpHelper = this.f$0;
                                    String str42 = str;
                                    heapDumpHelper.getClass();
                                    Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
                                    intent.setPackage("com.salab.issuetracker");
                                    intent.putExtra("ERRCODE", -132);
                                    intent.putExtra("ERRNAME", "SystemUI leak / " + str42);
                                    Context context2 = heapDumpHelper.mContext;
                                    intent.putExtra("ERRPKG", context2.getPackageName());
                                    intent.putExtra("ERRMSG", str42);
                                    intent.putExtra("EXTLOG", heapDumpHelper.mHeapDumpFilePath);
                                    context2.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                                    Slog.d("HeapDumpHelper", "sendIntent to IssueTracker");
                                    break;
                                default:
                                    HeapDumpHelper heapDumpHelper2 = this.f$0;
                                    String str5 = str;
                                    heapDumpHelper2.getClass();
                                    try {
                                        for (File file : new HeapDumpHelper.FileManager(str5).mHeapDumpDir.listFiles(new HeapDumpHelper$FileManager$$ExternalSyntheticLambda0())) {
                                            file.delete();
                                        }
                                        String str6 = str5 + String.format("%s_%d_%s.hprof", "heap-systemui", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
                                        heapDumpHelper2.mHeapDumpFilePath = str6;
                                        Debug.dumpHprofData(str6);
                                        heapDumpHelper2.isDumped = true;
                                        break;
                                    } catch (Exception e2) {
                                        Slog.e("HeapDumpHelper", "Exception : " + e2.getMessage());
                                        heapDumpHelper2.isDumped = false;
                                        return;
                                    }
                            }
                        }
                    }, 5000L);
                    return;
                }
            }
            Slog.d("HeapDumpHelper", "not matched");
        }
        z = false;
        Handler handler2 = this.mBgHandler;
        if (z) {
        }
    }
}
