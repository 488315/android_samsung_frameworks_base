package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BaseAppResult implements AppResult {
    public final String mContentType;
    public final ExecutableAppHolder.MultiInstanceAllowList mMultiInstanceAllowList;
    public final ExecutableAppHolder.MultiInstanceBlockList mMultiInstanceBlockList;

    public BaseAppResult(ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, String str) {
        this.mMultiInstanceBlockList = multiInstanceBlockList;
        this.mMultiInstanceAllowList = multiInstanceAllowList;
        this.mContentType = str;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final String getContentType() {
        return this.mContentType;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0124 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isVisibleSingleInstance(List list, ActivityInfo activityInfo) {
        boolean z;
        int i;
        ActivityInfo activityInfo2;
        int indexOf;
        Bundle bundle;
        Bundle bundle2;
        if ((activityInfo == null || (bundle2 = activityInfo.metaData) == null || !bundle2.getBoolean("com.samsung.android.drag_and_drop.launch.multiwindow.mode", false)) ? false : true) {
            return false;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
            if (runningTaskInfo.getWindowingMode() == 1) {
                ActivityInfo activityInfo3 = runningTaskInfo.topActivityInfo;
                if ((activityInfo3 == null || (bundle = activityInfo3.metaData) == null || !bundle.getBoolean("com.samsung.android.drag_and_drop.launch.multiwindow.mode", false)) ? false : true) {
                    return false;
                }
            }
        }
        ComponentName componentName = activityInfo.getComponentName();
        ArrayMap arrayMap = new ArrayMap();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ((ActivityManager.RunningTaskInfo) it2.next()).getWindowingMode();
        }
        if (!arrayMap.isEmpty()) {
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) it3.next();
                ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) arrayMap.get(Integer.valueOf(runningTaskInfo2.configuration.windowConfiguration.getStageType()));
                if (runningTaskInfo3 != null && !runningTaskInfo3.equals(runningTaskInfo2) && runningTaskInfo2.getWindowingMode() == runningTaskInfo3.getWindowingMode()) {
                    it3.remove();
                }
            }
        }
        Bundle bundle3 = activityInfo.metaData;
        if (bundle3 != null) {
            String string = bundle3.getString("com.samsung.android.multiwindow.activity.alias.targetactivity");
            if (!TextUtils.isEmpty(string)) {
                ComponentName componentName2 = new ComponentName(componentName.getPackageName(), string);
                z = true;
                componentName = componentName2;
                if (activityInfo.launchMode == 1) {
                    Iterator it4 = list.iterator();
                    while (it4.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) it4.next();
                        if (componentName.equals(runningTaskInfo4.topActivity) || componentName.equals(runningTaskInfo4.baseActivity)) {
                            if (runningTaskInfo4.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                                return true;
                            }
                        }
                    }
                }
                i = activityInfo.launchMode;
                if (i != 3 || i == 2) {
                    Iterator it5 = list.iterator();
                    while (it5.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) it5.next();
                        if (componentName.equals(runningTaskInfo5.baseActivity) && runningTaskInfo5.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                            return true;
                        }
                    }
                }
                if (activityInfo.launchMode != 4) {
                    return false;
                }
                if (this.mMultiInstanceBlockList.mBlockList.contains(activityInfo.packageName)) {
                    Iterator it6 = list.iterator();
                    while (it6.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) it6.next();
                        if (componentName.getPackageName().equals(runningTaskInfo6.baseActivity.getPackageName()) && runningTaskInfo6.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                            return true;
                        }
                    }
                }
                Iterator it7 = list.iterator();
                while (it7.hasNext()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo7 = (ActivityManager.RunningTaskInfo) it7.next();
                    boolean z2 = runningTaskInfo7.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid);
                    ComponentName componentName3 = runningTaskInfo7.baseActivity;
                    boolean z3 = componentName3 != null && activityInfo.packageName.equals(componentName3.getPackageName());
                    boolean z4 = componentName.equals(runningTaskInfo7.baseIntent.getComponent()) || componentName.equals(runningTaskInfo7.topActivity);
                    if (z2) {
                        if (z3 && !z4) {
                            if (this.mMultiInstanceAllowList.mBlockList.contains(activityInfo.packageName)) {
                                return false;
                            }
                        }
                        if (z4 || z3) {
                            return !z;
                        }
                    }
                }
                int i2 = activityInfo.launchMode;
                if (i2 == 3 || i2 == 2) {
                    String str = activityInfo.taskAffinity;
                    Iterator it8 = list.iterator();
                    while (it8.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo8 = (ActivityManager.RunningTaskInfo) it8.next();
                        String str2 = runningTaskInfo8.rootAffinity;
                        String substring = (str2 == null || (indexOf = str2.indexOf(":")) < 0) ? null : str2.substring(indexOf + 1);
                        if (str != null && substring != null && (activityInfo2 = runningTaskInfo8.topActivityInfo) != null && activityInfo2.launchMode != 3 && str.equals(substring) && runningTaskInfo8.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        z = false;
        if (activityInfo.launchMode == 1) {
        }
        i = activityInfo.launchMode;
        if (i != 3 || i == 2) {
        }
        if (activityInfo.launchMode != 4) {
        }
    }
}
