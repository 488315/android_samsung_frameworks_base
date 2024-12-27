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

public final class HeapDumpHelper {
    public final Handler mBgHandler;
    public final Context mContext;
    public boolean isDumped = false;
    public String mHeapDumpFilePath = "";

    public final class FileManager {
        public final File mHeapDumpDir;

        public FileManager(String str) {
            File file = new File(str);
            this.mHeapDumpDir = file;
            if (file.exists() || file.mkdirs()) {
                return;
            }
            Slog.e("HeapDumpHelper", "Failed to create directory: " + file.getAbsolutePath());
        }

        public final void deleteOldHeap() {
            File[] listFiles = this.mHeapDumpDir.listFiles(new HeapDumpHelper$FileManager$$ExternalSyntheticLambda0());
            if (listFiles == null) {
                Slog.d("HeapDumpHelper", "mHeapDumpDir(" + this.mHeapDumpDir + ").listFiles result is null!!!");
                StringBuilder sb = new StringBuilder("canWrite=");
                sb.append(this.mHeapDumpDir.canWrite());
                Slog.d("HeapDumpHelper", sb.toString());
                return;
            }
            for (File file : listFiles) {
                if (file.exists() && !file.delete()) {
                    Slog.w("HeapDumpHelper", "Failed to delete heapdump file : " + file);
                }
            }
        }
    }

    public HeapDumpHelper(Context context, Handler handler) {
        this.mContext = context;
        this.mBgHandler = handler;
    }

    public final void dump(final String str) {
        boolean equals = "user".equals(Build.TYPE);
        boolean z = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_LOW;
        if (equals && z && DeviceType.isShipBuild()) {
            String[] strArr = {"com.salab.act", "com.salab.issuetracker"};
            for (int i = 0; i < 2; i++) {
                String str2 = strArr[i];
                try {
                    this.mContext.getPackageManager().getApplicationInfo(str2, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                }
                if (this.mContext.getPackageManager().checkSignatures(str2, "android") == 0) {
                    Slog.d("HeapDumpHelper", str2 + " is matched");
                } else {
                    Slog.d("HeapDumpHelper", str2 + " is not matched");
                }
            }
            Slog.d("HeapDumpHelper", "no test device");
            return;
        }
        if ("OutOfMemoryError".equals(str)) {
            try {
                Debug.dumpHprofData("/data/log/core/OOM_com.android.systemui.hprof");
                return;
            } catch (Exception e) {
                Slog.e("HeapDumpHelper", "Exception : " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
        try {
            if (this.mContext.getPackageManager().getApplicationInfo("com.salab.issuetracker", 0) != null) {
                if (this.mContext.getPackageManager().checkSignatures("com.salab.issuetracker", "android") == 0) {
                    Slog.d("HeapDumpHelper", "matched");
                    final String str3 = "/mnt/sdcard/ACT_LOGS/";
                    final int i2 = 1;
                    this.mBgHandler.post(new Runnable(this) { // from class: com.android.systemui.HeapDumpHelper$$ExternalSyntheticLambda0
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
                                    intent.putExtra("ERRPKG", heapDumpHelper.mContext.getPackageName());
                                    intent.putExtra("ERRMSG", str4);
                                    intent.putExtra("EXTLOG", heapDumpHelper.mHeapDumpFilePath);
                                    heapDumpHelper.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                                    Slog.d("HeapDumpHelper", "sendIntent to IssueTracker");
                                    break;
                                default:
                                    HeapDumpHelper heapDumpHelper2 = this.f$0;
                                    String str5 = str3;
                                    heapDumpHelper2.getClass();
                                    try {
                                        new HeapDumpHelper.FileManager(str5).deleteOldHeap();
                                        String str6 = str5 + String.format("%s_%d_%s.hprof", "heap-systemui", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
                                        heapDumpHelper2.mHeapDumpFilePath = str6;
                                        Debug.dumpHprofData(str6);
                                        heapDumpHelper2.isDumped = true;
                                        break;
                                    } catch (Exception e2) {
                                        Slog.e("HeapDumpHelper", "Exception : " + e2.getMessage());
                                        e2.printStackTrace();
                                        Slog.d("HeapDumpHelper", "mHeapDumpFilePath= " + heapDumpHelper2.mHeapDumpFilePath + ", canWrite= " + new File(str5).canWrite());
                                        heapDumpHelper2.isDumped = false;
                                    }
                            }
                        }
                    });
                    final int i3 = 0;
                    this.mBgHandler.postDelayed(new Runnable(this) { // from class: com.android.systemui.HeapDumpHelper$$ExternalSyntheticLambda0
                        public final /* synthetic */ HeapDumpHelper f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    HeapDumpHelper heapDumpHelper = this.f$0;
                                    String str4 = str;
                                    heapDumpHelper.getClass();
                                    Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
                                    intent.setPackage("com.salab.issuetracker");
                                    intent.putExtra("ERRCODE", -132);
                                    intent.putExtra("ERRNAME", "SystemUI leak / " + str4);
                                    intent.putExtra("ERRPKG", heapDumpHelper.mContext.getPackageName());
                                    intent.putExtra("ERRMSG", str4);
                                    intent.putExtra("EXTLOG", heapDumpHelper.mHeapDumpFilePath);
                                    heapDumpHelper.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                                    Slog.d("HeapDumpHelper", "sendIntent to IssueTracker");
                                    break;
                                default:
                                    HeapDumpHelper heapDumpHelper2 = this.f$0;
                                    String str5 = str;
                                    heapDumpHelper2.getClass();
                                    try {
                                        new HeapDumpHelper.FileManager(str5).deleteOldHeap();
                                        String str6 = str5 + String.format("%s_%d_%s.hprof", "heap-systemui", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
                                        heapDumpHelper2.mHeapDumpFilePath = str6;
                                        Debug.dumpHprofData(str6);
                                        heapDumpHelper2.isDumped = true;
                                        break;
                                    } catch (Exception e2) {
                                        Slog.e("HeapDumpHelper", "Exception : " + e2.getMessage());
                                        e2.printStackTrace();
                                        Slog.d("HeapDumpHelper", "mHeapDumpFilePath= " + heapDumpHelper2.mHeapDumpFilePath + ", canWrite= " + new File(str5).canWrite());
                                        heapDumpHelper2.isDumped = false;
                                    }
                            }
                        }
                    }, 5000L);
                    return;
                }
                Slog.d("HeapDumpHelper", "not matched");
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            Slog.d("HeapDumpHelper", "no UT device");
        }
        final String str4 = "/data/log/core/";
        final int i4 = 1;
        this.mBgHandler.post(new Runnable(this) { // from class: com.android.systemui.HeapDumpHelper$$ExternalSyntheticLambda0
            public final /* synthetic */ HeapDumpHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i4) {
                    case 0:
                        HeapDumpHelper heapDumpHelper = this.f$0;
                        String str42 = str4;
                        heapDumpHelper.getClass();
                        Intent intent = new Intent("com.sec.android.ISSUE_TRACKER_ACTION");
                        intent.setPackage("com.salab.issuetracker");
                        intent.putExtra("ERRCODE", -132);
                        intent.putExtra("ERRNAME", "SystemUI leak / " + str42);
                        intent.putExtra("ERRPKG", heapDumpHelper.mContext.getPackageName());
                        intent.putExtra("ERRMSG", str42);
                        intent.putExtra("EXTLOG", heapDumpHelper.mHeapDumpFilePath);
                        heapDumpHelper.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                        Slog.d("HeapDumpHelper", "sendIntent to IssueTracker");
                        break;
                    default:
                        HeapDumpHelper heapDumpHelper2 = this.f$0;
                        String str5 = str4;
                        heapDumpHelper2.getClass();
                        try {
                            new HeapDumpHelper.FileManager(str5).deleteOldHeap();
                            String str6 = str5 + String.format("%s_%d_%s.hprof", "heap-systemui", Integer.valueOf(Process.myPid()), new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()));
                            heapDumpHelper2.mHeapDumpFilePath = str6;
                            Debug.dumpHprofData(str6);
                            heapDumpHelper2.isDumped = true;
                            break;
                        } catch (Exception e2) {
                            Slog.e("HeapDumpHelper", "Exception : " + e2.getMessage());
                            e2.printStackTrace();
                            Slog.d("HeapDumpHelper", "mHeapDumpFilePath= " + heapDumpHelper2.mHeapDumpFilePath + ", canWrite= " + new File(str5).canWrite());
                            heapDumpHelper2.isDumped = false;
                        }
                }
            }
        });
    }
}
