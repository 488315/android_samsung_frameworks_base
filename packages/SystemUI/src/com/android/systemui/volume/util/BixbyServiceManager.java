package com.android.systemui.volume.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.android.systemui.basic.util.LogWrapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BixbyServiceManager {
    public static final Uri VOICE_ENABLE_URI;
    public final ActivityManagerWrapper activityManagerWrapper;
    public final Context context;
    public boolean isBixbyServiceChecked;
    public boolean isBixbyServiceOn;
    public final ReentrantLock lock = new ReentrantLock();
    public final LogWrapper logWrapper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        VOICE_ENABLE_URI = Uri.parse("content://com.samsung.android.bixby.agent.settings/bixby_voice_isenable");
    }

    public BixbyServiceManager(Context context, LogWrapper logWrapper, ActivityManagerWrapper activityManagerWrapper) {
        this.context = context;
        this.logWrapper = logWrapper;
        this.activityManagerWrapper = activityManagerWrapper;
    }

    public final boolean checkBixbyServiceEnabled() {
        LogWrapper logWrapper = this.logWrapper;
        try {
            if (!this.lock.tryLock(3L, TimeUnit.SECONDS)) {
                logWrapper.e("BixbyServiceManager", "isBixbyServiceOn() : the waiting time elapsed before the lock was acquired!!");
                this.isBixbyServiceChecked = false;
                return false;
            }
            boolean z = true;
            this.isBixbyServiceChecked = true;
            Cursor query = this.context.getContentResolver().query(VOICE_ENABLE_URI, null, null, null, null);
            if (query != null) {
                query.moveToFirst();
                int i = query.getInt(query.getColumnIndex("bixby_voice_isenable"));
                query.close();
                if (i == 1) {
                    this.lock.unlock();
                    return z;
                }
            }
            z = false;
            this.lock.unlock();
            return z;
        } catch (Exception e) {
            logWrapper.e("BixbyServiceManager", "isBixbyServiceOn() : exception = " + e);
            return false;
        }
    }

    public final boolean isBixbyServiceForeground() {
        ActivityManagerWrapper activityManagerWrapper = this.activityManagerWrapper;
        activityManagerWrapper.getClass();
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = activityManagerWrapper.context;
        systemServiceExtension.getClass();
        Object systemService = context.getSystemService((Class<Object>) ActivityManager.class);
        Intrinsics.checkNotNull(systemService);
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) ((ActivityManager) systemService).getRunningTasks(1));
        if (runningTaskInfo == null) {
            return false;
        }
        ComponentName componentName = runningTaskInfo.topActivity;
        return "com.samsung.android.bixby.agent".equals(componentName != null ? componentName.getPackageName() : null);
    }
}
