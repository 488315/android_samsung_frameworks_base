package com.android.systemui.volume.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.basic.util.LogWrapper;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class BixbyServiceManager {
    public final ActivityManagerWrapper activityManagerWrapper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BixbyServiceManager(Context context, LogWrapper logWrapper, ActivityManagerWrapper activityManagerWrapper) {
        this.activityManagerWrapper = activityManagerWrapper;
    }

    public final boolean isBixbyServiceForeground() {
        ActivityManagerWrapper activityManagerWrapper = this.activityManagerWrapper;
        activityManagerWrapper.getClass();
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = activityManagerWrapper.context;
        systemServiceExtension.getClass();
        Object systemService = context.getSystemService((Class<Object>) ActivityManager.class);
        systemService.getClass();
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) ((ActivityManager) systemService).getRunningTasks(1));
        if (runningTaskInfo == null) {
            return false;
        }
        ComponentName componentName = runningTaskInfo.topActivity;
        return "com.samsung.android.bixby.agent".equals(componentName != null ? componentName.getPackageName() : null);
    }
}
