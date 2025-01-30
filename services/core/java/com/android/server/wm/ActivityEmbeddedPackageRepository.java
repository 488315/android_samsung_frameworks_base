package com.android.server.wm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ActivityEmbeddedPackageRepository {
    public final ActivityTaskManagerService mAtm;
    public final List mRepository = new ArrayList();
    public final BroadcastReceiver mPackageReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.ActivityEmbeddedPackageRepository.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data;
            String action = intent.getAction();
            int userId = context.getUserId();
            String str = null;
            if (("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_REPLACED".equals(action)) && (data = intent.getData()) != null) {
                str = data.getSchemeSpecificPart();
            }
            if (str == null) {
                return;
            }
            if (!ActivityEmbeddedPackageRepository.this.allowSystemOverride(str)) {
                if (action == "android.intent.action.PACKAGE_REMOVED" && ActivityEmbeddedPackageRepository.this.remove(str)) {
                    ActivityEmbeddedPackageRepository.this.mAtm.mMultiTaskingController.updateEmbedActivityPackageEnabled(str, 0, userId, true);
                    return;
                }
                return;
            }
            if (action == "android.intent.action.PACKAGE_ADDED" || action == "android.intent.action.PACKAGE_REPLACED") {
                ActivityEmbeddedPackageRepository.this.mAtm.mMultiTaskingController.updateEmbedActivityPackageEnabled(str, 1, userId, false);
                ActivityEmbeddedPackageRepository.this.add(str);
            }
        }
    };

    public ActivityEmbeddedPackageRepository(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public synchronized boolean add(String str) {
        if (this.mRepository.contains(str)) {
            return false;
        }
        this.mRepository.add(str);
        return true;
    }

    public synchronized boolean remove(String str) {
        if (!this.mRepository.contains(str)) {
            return false;
        }
        this.mRepository.remove(str);
        return true;
    }

    public synchronized List getActivityEmbeddedPackages() {
        return this.mRepository;
    }

    public boolean allowSystemOverride(String str) {
        try {
            return this.mAtm.mContext.getPackageManager().getProperty("android.window.PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE", str) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public void loadActivityEmbeddedPackages() {
        if (this.mAtm.mContext.getPackageManager() == null) {
            Slog.v("ActivityEmbeddedPackageRepository", "PackageManager is not ready yet.");
            return;
        }
        for (ApplicationInfo applicationInfo : this.mAtm.mContext.getPackageManager().getInstalledApplications(0)) {
            if (allowSystemOverride(applicationInfo.packageName)) {
                ActivityTaskManagerService activityTaskManagerService = this.mAtm;
                activityTaskManagerService.mMultiTaskingController.updateEmbedActivityPackageEnabled(applicationInfo.packageName, 1, activityTaskManagerService.getCurrentUserId(), false);
                add(applicationInfo.packageName);
            }
        }
    }

    public void registerActivityEmbeddedPackageReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        activityTaskManagerService.mContext.registerReceiver(this.mPackageReceiver, intentFilter, null, activityTaskManagerService.f1734mH);
    }

    public synchronized void dump(PrintWriter printWriter) {
        printWriter.print("    ");
        printWriter.print("ActivityEmbeddedPackageRepository : ");
        if (this.mRepository.isEmpty()) {
            printWriter.println("Empty");
            return;
        }
        printWriter.print(this.mRepository.size());
        printWriter.println();
        for (String str : this.mRepository) {
            printWriter.print("    ");
            printWriter.print("    ");
            printWriter.print(str);
            printWriter.println();
        }
    }
}
