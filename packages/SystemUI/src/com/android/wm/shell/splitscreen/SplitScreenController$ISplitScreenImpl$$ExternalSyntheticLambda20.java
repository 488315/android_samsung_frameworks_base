package com.android.wm.shell.splitscreen;

import android.R;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.Vibrator;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import android.widget.Toast;
import android.window.WindowContainerTransaction;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20(int i, Object obj, int i2, int i3, int i4) {
        this.$r8$classId = i4;
        this.f$0 = obj;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SplitScreenController splitScreenController;
        switch (this.$r8$classId) {
            case 0:
                boolean[] zArr = (boolean[]) this.f$0;
                final int i = this.f$1;
                final int i2 = this.f$2;
                final int i3 = this.f$3;
                int i4 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                final StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                final boolean[] zArr2 = {false};
                stageCoordinator.mRecentTasks.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda27
                    /* JADX WARN: Code restructure failed: missing block: B:39:0x00cc, code lost:
                    
                        r15 = com.android.wm.shell.splitscreen.AppPairShortcutController.supportAppPairShortCut(r15, r12);
                     */
                    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
                    @Override // java.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void accept(Object obj2) throws PackageManager.NameNotFoundException {
                        boolean z;
                        boolean z2;
                        boolean zSupportAppPairShortCut;
                        boolean z3;
                        StageCoordinator stageCoordinator2 = stageCoordinator;
                        int i5 = i;
                        int i6 = i2;
                        int i7 = i3;
                        boolean[] zArr3 = zArr2;
                        stageCoordinator2.getClass();
                        SplitBounds splitBoundsForTaskId = ((RecentTasksController) obj2).getSplitBoundsForTaskId(i5);
                        if (splitBoundsForTaskId == null || i5 != splitBoundsForTaskId.leftTopTaskId || i6 != splitBoundsForTaskId.rightBottomTaskId || i7 != splitBoundsForTaskId.cellTaskId) {
                            Slog.d("StageCoordinator", "canShowAddAppPairDialogForRecent: Invalid taskId");
                            return;
                        }
                        SplitWindowManager splitWindowManager = stageCoordinator2.mSplitLayout.mSplitWindowManager;
                        if (splitWindowManager.mAppPairShortcutController == null) {
                            z = false;
                        } else {
                            ArrayMap appPairDialogItems = splitWindowManager.mDividerPanel.getAppPairDialogItems();
                            AppPairShortcutController appPairShortcutController = splitWindowManager.mAppPairShortcutController;
                            Context context = splitWindowManager.mContext;
                            appPairShortcutController.mStageCoordinator.getClass();
                            ActivityManager.RecentTaskInfo recentTaskInfo = StageCoordinator.getRecentTaskInfo(i5);
                            ActivityManager.RecentTaskInfo recentTaskInfo2 = StageCoordinator.getRecentTaskInfo(i6);
                            z = true;
                            if (recentTaskInfo == null || recentTaskInfo2 == null) {
                                Log.e("AppPairShortcutController", "[isSupportAppPairPolicyForRecents] getChildTasks() is null or empty");
                            } else {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(recentTaskInfo);
                                arrayList.add(recentTaskInfo2);
                                if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && i7 != -1) {
                                    arrayList.add(StageCoordinator.getRecentTaskInfo(i7));
                                }
                                HashMap map = new HashMap();
                                HashSet hashSet = new HashSet();
                                int size = arrayList.size();
                                int i8 = 0;
                                while (true) {
                                    if (i8 >= size) {
                                        zSupportAppPairShortCut = true;
                                        break;
                                    }
                                    Object obj3 = arrayList.get(i8);
                                    i8++;
                                    ActivityManager.RecentTaskInfo recentTaskInfo3 = (ActivityManager.RecentTaskInfo) obj3;
                                    ComponentName componentName = recentTaskInfo3.realActivity;
                                    if (componentName != null) {
                                        String packageName = componentName.getPackageName();
                                        HashSet hashSet2 = (HashSet) map.get(Integer.valueOf(recentTaskInfo3.userId));
                                        if (hashSet2 == null) {
                                            hashSet2 = new HashSet();
                                            map.put(Integer.valueOf(recentTaskInfo3.userId), hashSet2);
                                            hashSet.add(componentName.getClassName());
                                        }
                                        if (hashSet2.contains(packageName) && (!packageName.equals("com.google.android.googlequicksearchbox") || hashSet.contains(componentName.getClassName()))) {
                                            break;
                                        } else {
                                            hashSet2.add(packageName);
                                        }
                                    } else {
                                        Log.w("AppPairShortcutController", "componentName is null");
                                        zSupportAppPairShortCut = false;
                                        break;
                                    }
                                }
                                if (zSupportAppPairShortCut) {
                                    int size2 = arrayList.size();
                                    int i9 = 0;
                                    while (i9 < size2) {
                                        Object obj4 = arrayList.get(i9);
                                        i9++;
                                        ActivityManager.RecentTaskInfo recentTaskInfo4 = (ActivityManager.RecentTaskInfo) obj4;
                                        ComponentName componentName2 = recentTaskInfo4.realActivity;
                                        if (componentName2 == null) {
                                            Log.w("AppPairShortcutController", "componentName is null");
                                        } else {
                                            ActivityInfo activityInfo = recentTaskInfo4.topActivityInfo;
                                            if (activityInfo == null || activityInfo.applicationInfo.uid != 1001) {
                                                String packageName2 = componentName2.getPackageName();
                                                int i10 = recentTaskInfo4.userId;
                                                Intent launchIntentForPackageAsUser = MultiWindowUtils.getLaunchIntentForPackageAsUser(packageName2, i10);
                                                if (launchIntentForPackageAsUser == null || launchIntentForPackageAsUser.getComponent() == null) {
                                                    Log.w("AppPairShortcutController", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i10, "getLaunchIntentForPackageAsUser is null or empty component (", packageName2, ",", ")"));
                                                }
                                            }
                                        }
                                        z3 = false;
                                        break;
                                    }
                                    z3 = true;
                                    if (z3) {
                                        z2 = true;
                                        if (z2 || appPairDialogItems.isEmpty()) {
                                        }
                                    } else {
                                        Log.i("AppPairShortcutController", "[isSupportAppPairPolicyForRecents] isSupportAppPairType returns false. " + arrayList);
                                    }
                                } else {
                                    Log.i("AppPairShortcutController", "[isSupportAppPairPolicyForRecents] isSupportAppPairForMultiInstance returns false. " + arrayList);
                                }
                            }
                            z2 = false;
                            if (z2) {
                                z = false;
                            }
                        }
                        zArr3[0] = z;
                    }
                });
                zArr[0] = zArr2[0];
                break;
            default:
                SplitScreenController.SplitTwoFingerGestureStarter splitTwoFingerGestureStarter = (SplitScreenController.SplitTwoFingerGestureStarter) this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$2;
                int i7 = this.f$3;
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                String str = splitTwoFingerGestureStarter.TAG;
                boolean z = splitTwoFingerGestureStarter.DEBUG;
                if (z) {
                    Slog.d(str, "top task: " + runningTaskInfo);
                }
                int activityType = runningTaskInfo.getActivityType();
                boolean z2 = runningTaskInfo.originallySupportedMultiWindow;
                SplitScreenController splitScreenController2 = SplitScreenController.this;
                if (!z2 || activityType != 1) {
                    if (activityType == 1) {
                        FullscreenTaskListener fullscreenTaskListener = splitScreenController2.mFullscreenTaskListener;
                        if (fullscreenTaskListener != null) {
                            fullscreenTaskListener.animForAffordance(runningTaskInfo.taskId, i5 != 1 ? i5 != 3 ? i5 != 4 ? 0 : 2 : 8 : 4);
                            if (splitTwoFingerGestureStarter.mVibrator == null) {
                                splitTwoFingerGestureStarter.mVibrator = (Vibrator) splitScreenController2.mContext.getSystemService(Vibrator.class);
                            }
                            splitTwoFingerGestureStarter.mVibrator.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(127), "Split#performHapticFeedback", 0, 0);
                        }
                        Context context = splitScreenController2.mContext;
                        Toast.makeText(context, context.getString(R.string.indeterminate_progress_41), 0).show();
                    }
                    if (z) {
                        Slog.d(str, "top task doesn't fit in split. info=" + runningTaskInfo + " type=" + runningTaskInfo.getActivityType());
                        break;
                    }
                } else {
                    ComponentName component = runningTaskInfo.baseActivity;
                    if (component == null) {
                        component = runningTaskInfo.baseIntent.getComponent();
                    }
                    Intent edgeAllAppsActivityIntent = MultiWindowUtils.getEdgeAllAppsActivityIntent(component, runningTaskInfo.userId, runningTaskInfo.taskId);
                    if (z) {
                        Slog.d(str, "enterSplitIfPossible: start intent=" + edgeAllAppsActivityIntent);
                    }
                    if (splitScreenController2.mStageCoordinator.mMainStage.mIsActive) {
                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(splitScreenController2.mContext, 0, edgeAllAppsActivityIntent, 1107296256, null, UserHandle.CURRENT);
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.sendPendingIntent(activityAsUser, edgeAllAppsActivityIntent, splitScreenController2.mStageCoordinator.resolveStartStage(-1, i6, new Bundle(), windowContainerTransaction, i7));
                        splitScreenController2.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                        splitScreenController = splitScreenController2;
                    } else {
                        splitScreenController2.startIntent(edgeAllAppsActivityIntent, null, i6, i7, null);
                        splitScreenController = splitScreenController2;
                    }
                    if (splitTwoFingerGestureStarter.mVibrator == null) {
                        splitTwoFingerGestureStarter.mVibrator = (Vibrator) splitScreenController.mContext.getSystemService(Vibrator.class);
                    }
                    splitTwoFingerGestureStarter.mVibrator.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(127), "Split#performHapticFeedback", 0, 0);
                    if (CoreRune.MW_SPLIT_FULL_TO_SPLIT_BY_GESTURE_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced("1000", "From Gesture");
                        break;
                    }
                }
                break;
        }
    }
}
