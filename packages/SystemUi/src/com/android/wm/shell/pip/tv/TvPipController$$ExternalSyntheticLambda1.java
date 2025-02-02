package com.android.wm.shell.pip.tv;

import android.R;
import android.app.ActivityManager;
import android.app.RemoteAction;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.session.MediaSessionManager;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.util.ImageUtils;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import com.android.wm.shell.pip.PipAppOpsListener;
import com.android.wm.shell.pip.PipMediaController;
import com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipUtils;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ShellController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TvPipController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TvPipController f$0;

    public /* synthetic */ TvPipController$$ExternalSyntheticLambda1(TvPipController tvPipController, int i) {
        this.$r8$classId = i;
        this.f$0 = tvPipController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final TvPipController tvPipController = this.f$0;
                ((ArrayList) tvPipController.mPipTransitionController.mPipTransitionCallbacks).add(tvPipController);
                tvPipController.reloadResources();
                tvPipController.mPipParamsChangedForwarder.addListener(new PipParamsChangedForwarder.PipParamsChangedCallback() { // from class: com.android.wm.shell.pip.tv.TvPipController.2
                    @Override // com.android.wm.shell.pip.PipParamsChangedForwarder.PipParamsChangedCallback
                    public final void onActionsChanged(List list, RemoteAction remoteAction) {
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 462871743, 0, "%s: onActionsChanged()", "TvPipController");
                        }
                        TvPipController.this.mTvPipActionsProvider.setAppActions(list, remoteAction);
                    }

                    @Override // com.android.wm.shell.pip.PipParamsChangedForwarder.PipParamsChangedCallback
                    public final void onAspectRatioChanged(float f) {
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1408555887, 8, "%s: onAspectRatioChanged: %f", "TvPipController", Double.valueOf(f));
                        }
                        TvPipController tvPipController2 = TvPipController.this;
                        TvPipBoundsState tvPipBoundsState = tvPipController2.mTvPipBoundsState;
                        tvPipBoundsState.mAspectRatio = f;
                        if (tvPipBoundsState.mIsTvPipExpanded) {
                            return;
                        }
                        tvPipController2.updatePinnedStackBounds();
                    }

                    @Override // com.android.wm.shell.pip.PipParamsChangedForwarder.PipParamsChangedCallback
                    public final void onExpandedAspectRatioChanged(float f) {
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1353368200, 8, "%s: onExpandedAspectRatioChanged: %f", "TvPipController", Double.valueOf(f));
                        }
                        TvPipController tvPipController2 = TvPipController.this;
                        tvPipController2.mTvPipBoundsState.setDesiredTvExpandedAspectRatio(f, false);
                        TvPipBoundsState tvPipBoundsState = tvPipController2.mTvPipBoundsState;
                        tvPipController2.mTvPipActionsProvider.updateExpansionEnabled(tvPipBoundsState.mIsTvExpandedPipSupported && tvPipBoundsState.mDesiredTvExpandedAspectRatio != 0.0f);
                        boolean z = tvPipBoundsState.mIsTvPipExpanded;
                        TvPipBoundsAlgorithm tvPipBoundsAlgorithm = tvPipController2.mTvPipBoundsAlgorithm;
                        if (z && f != 0.0f) {
                            tvPipBoundsAlgorithm.updateExpandedPipSize();
                            tvPipController2.updatePinnedStackBounds();
                        }
                        if (tvPipBoundsState.mIsTvPipExpanded && f == 0.0f) {
                            tvPipBoundsAlgorithm.updateGravityOnExpansionToggled(false);
                            tvPipBoundsState.mIsTvPipExpanded = false;
                            tvPipController2.updatePinnedStackBounds();
                        }
                        if (tvPipBoundsState.mIsTvPipExpanded || f == 0.0f || tvPipBoundsState.mTvPipManuallyCollapsed) {
                            return;
                        }
                        tvPipBoundsAlgorithm.updateExpandedPipSize();
                        tvPipBoundsAlgorithm.updateGravityOnExpansionToggled(true);
                        tvPipBoundsState.mIsTvPipExpanded = true;
                        tvPipController2.updatePinnedStackBounds();
                    }
                });
                tvPipController.mTaskStackListener.addListener(new TaskStackListenerCallback() { // from class: com.android.wm.shell.pip.tv.TvPipController.1
                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityPinned(String str, int i) {
                        Bitmap bitmap;
                        ComponentName componentName;
                        TaskInfo pinnedTaskInfo = TvPipController.getPinnedTaskInfo();
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1225237129, 0, "%s: onActivityPinned(), task=%s", "TvPipController", String.valueOf(pinnedTaskInfo));
                        }
                        if (pinnedTaskInfo == null || pinnedTaskInfo.topActivity == null) {
                            return;
                        }
                        int i2 = pinnedTaskInfo.taskId;
                        TvPipController tvPipController2 = TvPipController.this;
                        tvPipController2.mPinnedTaskId = i2;
                        PipMediaController pipMediaController = tvPipController2.mPipMediaController;
                        pipMediaController.getClass();
                        pipMediaController.resolveActiveMediaController(pipMediaController.mMediaSessionManager.getActiveSessionsForUser(null, UserHandle.CURRENT));
                        ActionBroadcastReceiver actionBroadcastReceiver = tvPipController2.mActionBroadcastReceiver;
                        if (!actionBroadcastReceiver.mRegistered) {
                            TvPipController tvPipController3 = TvPipController.this;
                            tvPipController3.mContext.registerReceiverForAllUsers(actionBroadcastReceiver, actionBroadcastReceiver.mIntentFilter, "com.android.systemui.permission.SELF", tvPipController3.mMainHandler, 4);
                            actionBroadcastReceiver.mRegistered = true;
                        }
                        String packageName = pinnedTaskInfo.topActivity.getPackageName();
                        TvPipNotificationController tvPipNotificationController = tvPipController2.mPipNotificationController;
                        tvPipNotificationController.getClass();
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1476597384, 0, "%s: show %s", "TvPipNotificationController", String.valueOf(packageName));
                        }
                        if (tvPipNotificationController.mTvPipActionsProvider != null) {
                            tvPipNotificationController.mIsNotificationShown = true;
                            tvPipNotificationController.mPackageName = packageName;
                            Context context = tvPipNotificationController.mContext;
                            if (context != null && (componentName = (ComponentName) PipUtils.getTopPipActivity(context).first) != null) {
                                try {
                                    bitmap = ImageUtils.buildScaledBitmap(tvPipNotificationController.mPackageManager.getActivityIcon(componentName), context.getResources().getDimensionPixelSize(R.dimen.notification_large_icon_width), context.getResources().getDimensionPixelSize(R.dimen.notification_large_icon_height), true);
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                                tvPipNotificationController.mActivityIcon = bitmap;
                                tvPipNotificationController.updateNotificationContent();
                            }
                            bitmap = null;
                            tvPipNotificationController.mActivityIcon = bitmap;
                            tvPipNotificationController.updateNotificationContent();
                        } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1326129381, 0, "%s: Missing TvPipActionsProvider", "TvPipNotificationController");
                        }
                        TvPipBoundsController tvPipBoundsController = tvPipController2.mTvPipBoundsController;
                        tvPipBoundsController.mCurrentPlacementBounds = null;
                        tvPipBoundsController.mPipTargetBounds = null;
                        tvPipBoundsController.cancelScheduledPlacement();
                        PipAppOpsListener pipAppOpsListener = tvPipController2.mAppOpsListener;
                        pipAppOpsListener.mAppOpsManager.startWatchingMode(67, str, pipAppOpsListener.mAppOpsChangedListener);
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
                        if (runningTaskInfo.getWindowingMode() == 2) {
                            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 83467309, 0, "%s: onPinnedActivityRestartAttempt()", "TvPipController");
                            }
                            TvPipController.this.movePipToFullscreen();
                        }
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityUnpinned() {
                        PipAppOpsListener pipAppOpsListener = TvPipController.this.mAppOpsListener;
                        pipAppOpsListener.mAppOpsManager.stopWatchingMode(pipAppOpsListener.mAppOpsChangedListener);
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onTaskStackChanged() {
                        TvPipController tvPipController2 = TvPipController.this;
                        tvPipController2.getClass();
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1483878713, 0, "%s: onTaskStackChanged()", "TvPipController");
                        }
                        if ((tvPipController2.mState != 0) && TvPipController.getPinnedTaskInfo() == null) {
                            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2088927117, 0, "%s: Pinned task is gone.", "TvPipController");
                            }
                            tvPipController2.onPipDisappeared();
                        }
                    }
                });
                try {
                    tvPipController.mWmShellWrapper.addPinnedStackListener(new PinnedStackListenerForwarder.PinnedTaskListener() { // from class: com.android.wm.shell.pip.tv.TvPipController.3
                        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
                        public final void onImeVisibilityChanged(boolean z, int i) {
                            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -12659856, 28, "%s: onImeVisibilityChanged(), visible=%b, height=%d", "TvPipController", Boolean.valueOf(z), Long.valueOf(i));
                            }
                            TvPipController tvPipController2 = TvPipController.this;
                            TvPipBoundsState tvPipBoundsState = tvPipController2.mTvPipBoundsState;
                            if (z != tvPipBoundsState.mIsImeShowing || (z && i != tvPipBoundsState.mImeHeight)) {
                                tvPipBoundsState.mIsImeShowing = z;
                                tvPipBoundsState.mImeHeight = i;
                                if (tvPipController2.mState != 0) {
                                    tvPipController2.updatePinnedStackBounds();
                                }
                            }
                        }
                    });
                } catch (RemoteException e) {
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1581014830, 0, "%s: Failed to register pinned stack listener, %s", "TvPipController", String.valueOf(e));
                    }
                }
                PipMediaController pipMediaController = tvPipController.mPipMediaController;
                MediaSessionManager mediaSessionManager = pipMediaController.mMediaSessionManager;
                PipMediaController$$ExternalSyntheticLambda0 pipMediaController$$ExternalSyntheticLambda0 = pipMediaController.mSessionsChangedListener;
                mediaSessionManager.removeOnActiveSessionsChangedListener(pipMediaController$$ExternalSyntheticLambda0);
                mediaSessionManager.addOnActiveSessionsChangedListener(null, UserHandle.CURRENT, pipMediaController.mHandlerExecutor, pipMediaController$$ExternalSyntheticLambda0);
                tvPipController.mDisplayController.addDisplayWindowListener(tvPipController);
                ShellController shellController = tvPipController.mShellController;
                shellController.addConfigurationChangeListener(tvPipController);
                CopyOnWriteArrayList copyOnWriteArrayList = shellController.mUserChangeListeners;
                copyOnWriteArrayList.remove(tvPipController);
                copyOnWriteArrayList.add(tvPipController);
                break;
            default:
                TvPipController tvPipController2 = this.f$0;
                tvPipController2.closeCurrentPiP(tvPipController2.mPinnedTaskId);
                break;
        }
    }
}
