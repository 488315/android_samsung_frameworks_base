package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class BaseAppResult implements AppResult {
    public final Map mBlockDropTargetClassNameMap = Map.ofEntries(Map.entry("com.google.android.apps.bard.shellapp.BardEntryPointActivity", "com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity"));
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

    /* JADX WARN: Code restructure failed: missing block: B:140:0x023f, code lost:
    
        return !r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02cd, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isVisibleSingleInstance(List list, ActivityInfo activityInfo, boolean z) {
        boolean z2;
        ActivityInfo activityInfo2;
        int iIndexOf;
        ComponentName componentName;
        ComponentName componentName2 = activityInfo.getComponentName();
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ActivityManager.RunningTaskInfo) it.next()).getWindowingMode();
        }
        if (!arrayMap.isEmpty()) {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it2.next();
                ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) arrayMap.get(Integer.valueOf(runningTaskInfo.configuration.windowConfiguration.getStageType()));
                if (runningTaskInfo2 != null && !runningTaskInfo2.equals(runningTaskInfo) && runningTaskInfo.getWindowingMode() == runningTaskInfo2.getWindowingMode()) {
                    it2.remove();
                }
            }
        }
        Bundle bundle = activityInfo.metaData;
        if (bundle != null) {
            String string = bundle.getString("com.samsung.android.multiwindow.activity.alias.targetactivity");
            if (TextUtils.isEmpty(string)) {
                z2 = false;
            } else {
                ComponentName componentName3 = new ComponentName(componentName2.getPackageName(), string);
                z2 = true;
                componentName2 = componentName3;
            }
        }
        if (!z) {
            Iterator it3 = list.iterator();
            boolean z3 = false;
            while (it3.hasNext()) {
                ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) it3.next();
                if (runningTaskInfo3.getWindowingMode() == 1) {
                    z3 = true;
                }
                if (runningTaskInfo3.isSplitScreen() && z3) {
                    Slog.d("BaseAppResult", "isVisibleSingleInstance - Visible SplitScreen and Fullscreen");
                    return false;
                }
            }
            if (activityInfo.launchMode == 1) {
                Iterator it4 = list.iterator();
                while (it4.hasNext()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) it4.next();
                    if (componentName2.equals(runningTaskInfo4.topActivity) || componentName2.equals(runningTaskInfo4.baseActivity)) {
                        if (runningTaskInfo4.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                            break;
                        }
                    }
                }
            }
            int i = activityInfo.launchMode;
            if (i == 3 || i == 2) {
                Iterator it5 = list.iterator();
                while (it5.hasNext()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) it5.next();
                    if (componentName2.equals(runningTaskInfo5.baseActivity) && runningTaskInfo5.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                        break;
                    }
                }
            }
            if ("com.samsung.knox.securefolder".equals(activityInfo.packageName)) {
                Iterator it6 = list.iterator();
                while (it6.hasNext()) {
                    ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) it6.next();
                    ComponentName componentName4 = runningTaskInfo6.baseActivity;
                    if (componentName4 != null && (componentName4.getPackageName().equals(activityInfo.packageName) || "android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER".equals(runningTaskInfo6.baseIntent.getAction()))) {
                        break;
                    }
                }
            }
            if (activityInfo.launchMode != 4) {
                if (this.mMultiInstanceBlockList.mBlockList.contains(activityInfo.packageName)) {
                    Iterator it7 = list.iterator();
                    while (it7.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo7 = (ActivityManager.RunningTaskInfo) it7.next();
                        if (componentName2.getPackageName().equals(runningTaskInfo7.baseActivity.getPackageName()) && runningTaskInfo7.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                            break;
                        }
                    }
                }
                Iterator it8 = list.iterator();
                while (true) {
                    if (it8.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo8 = (ActivityManager.RunningTaskInfo) it8.next();
                        boolean z4 = runningTaskInfo8.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid);
                        ComponentName componentName5 = runningTaskInfo8.baseActivity;
                        boolean z5 = componentName5 != null && activityInfo.packageName.equals(componentName5.getPackageName());
                        boolean z6 = componentName2.equals(runningTaskInfo8.baseIntent.getComponent()) || componentName2.equals(runningTaskInfo8.topActivity);
                        if (z4) {
                            if (z5 && !z6) {
                                if (this.mMultiInstanceAllowList.mBlockList.contains(activityInfo.packageName)) {
                                    break;
                                }
                            }
                            if (z6 || z5) {
                                if (!activityInfo.packageName.equals("com.google.android.googlequicksearchbox")) {
                                    break;
                                }
                                boolean zEquals = componentName2.getClassName().equals(runningTaskInfo8.baseActivity.getClassName());
                                boolean z7 = "com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity".equals(componentName2.getClassName()) || "com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity".equals(runningTaskInfo8.baseActivity.getClassName());
                                if (zEquals || !z7) {
                                    break;
                                }
                            } else if (activityInfo.packageName.equals("com.google.android.apps.bard") && (componentName = runningTaskInfo8.baseActivity) != null && componentName.getClassName().equals("com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity")) {
                                return !z2;
                            }
                        }
                    } else {
                        int i2 = activityInfo.launchMode;
                        if (i2 == 3 || i2 == 2) {
                            String str = activityInfo.taskAffinity;
                            Iterator it9 = list.iterator();
                            while (it9.hasNext()) {
                                ActivityManager.RunningTaskInfo runningTaskInfo9 = (ActivityManager.RunningTaskInfo) it9.next();
                                String str2 = runningTaskInfo9.rootAffinity;
                                String strSubstring = (str2 == null || (iIndexOf = str2.indexOf(":")) < 0) ? null : str2.substring(iIndexOf + 1);
                                if (str != null && strSubstring != null && (activityInfo2 = runningTaskInfo9.topActivityInfo) != null && activityInfo2.launchMode != 3 && str.equals(strSubstring) && runningTaskInfo9.userId == UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                                    break;
                                }
                            }
                        }
                        if (this.mBlockDropTargetClassNameMap.containsKey(componentName2.getClassName())) {
                            String str3 = (String) this.mBlockDropTargetClassNameMap.get(componentName2.getClassName());
                            Iterator it10 = list.iterator();
                            while (it10.hasNext()) {
                                ActivityManager.RunningTaskInfo runningTaskInfo10 = (ActivityManager.RunningTaskInfo) it10.next();
                                ComponentName componentName6 = runningTaskInfo10.baseActivity;
                                if (componentName6 == null || !componentName6.getClassName().equals(str3) || runningTaskInfo10.userId != UserHandle.getUserId(activityInfo.applicationInfo.uid)) {
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
