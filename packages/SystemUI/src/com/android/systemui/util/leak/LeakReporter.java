package com.android.systemui.util.leak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.google.android.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LeakReporter {
    public static final String FILEPROVIDER_AUTHORITY = "com.android.systemui.fileprovider";
    static final String LEAK_DIR = "leak";
    static final String LEAK_DUMP = "leak.dump";
    static final String LEAK_HPROF = "leak.hprof";
    static final String TAG = "LeakReporter";
    private final Context mContext;
    private final LeakDetector mLeakDetector;
    private final String mLeakReportEmail;
    private final UserTracker mUserTracker;

    public LeakReporter(Context context, UserTracker userTracker, LeakDetector leakDetector, String str) {
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mLeakDetector = leakDetector;
        this.mLeakReportEmail = str;
    }

    private Intent getIntent(File file, File file2) {
        Uri uriForFile = FileProvider.getUriForFile(this.mContext, FILEPROVIDER_AUTHORITY, file2);
        Uri uriForFile2 = FileProvider.getUriForFile(this.mContext, FILEPROVIDER_AUTHORITY, file);
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.addFlags(1);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.leakreport");
        intent.putExtra("android.intent.extra.SUBJECT", "SystemUI leak report");
        intent.putExtra("android.intent.extra.TEXT", "Build info: " + SystemProperties.get("ro.build.description"));
        ClipData clipData = new ClipData(null, new String[]{"application/vnd.android.leakreport"}, new ClipData.Item(null, null, null, uriForFile));
        ArrayList<? extends Parcelable> newArrayList = Lists.newArrayList(new Uri[]{uriForFile});
        clipData.addItem(new ClipData.Item(null, null, null, uriForFile2));
        newArrayList.add(uriForFile2);
        intent.setClipData(clipData);
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", newArrayList);
        if (!TextUtils.isEmpty(this.mLeakReportEmail)) {
            intent.putExtra("android.intent.extra.EMAIL", new String[]{this.mLeakReportEmail});
        }
        return intent;
    }

    public void dumpLeak(int i) {
        try {
            File file = new File(this.mContext.getCacheDir(), LEAK_DIR);
            file.mkdir();
            File file2 = new File(file, LEAK_HPROF);
            Debug.dumpHprofData(file2.getAbsolutePath());
            File file3 = new File(file, LEAK_DUMP);
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            try {
                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                printWriter.print("Build: ");
                printWriter.println(SystemProperties.get("ro.build.description"));
                printWriter.println();
                printWriter.flush();
                this.mLeakDetector.dump(printWriter, new String[0]);
                printWriter.close();
                fileOutputStream.close();
                NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
                NotificationChannel notificationChannel = new NotificationChannel(LEAK_DIR, "Leak Alerts", 4);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
                notificationManager.notify(TAG, 0, new Notification.Builder(this.mContext, notificationChannel.getId()).setAutoCancel(true).setShowWhen(true).setContentTitle("Memory Leak Detected").setContentText(String.format("SystemUI has detected %d leaked objects. Tap to send", Integer.valueOf(i))).setSmallIcon(17304445).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, getIntent(file2, file3), 201326592, null, ((UserTrackerImpl) this.mUserTracker).getUserHandle())).build());
            } finally {
            }
        } catch (IOException e) {
            Log.e(TAG, "Couldn't dump heap for leak", e);
        }
    }
}
