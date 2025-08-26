package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.split.SplitScreenUtils;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class ISplitScreen$Stub extends Binder implements IInterface {
    public ISplitScreen$Stub() {
        attachInterface(this, "com.android.wm.shell.splitscreen.ISplitScreen");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        int i3 = 2;
        final int i4 = 0;
        final int i5 = 1;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.splitscreen.ISplitScreen");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.splitscreen.ISplitScreen");
            return true;
        }
        ISplitScreenListener$Stub$Proxy iSplitScreenListener$Stub$Proxy = null;
        ISplitSelectListener$Stub$Proxy iSplitSelectListener$Stub$Proxy = null;
        if (i == 2) {
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder != null) {
                IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                iSplitScreenListener$Stub$Proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISplitScreenListener$Stub$Proxy)) ? new ISplitScreenListener$Stub$Proxy(strongBinder) : (ISplitScreenListener$Stub$Proxy) iInterfaceQueryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            SplitScreenController.ISplitScreenImpl iSplitScreenImpl = (SplitScreenController.ISplitScreenImpl) this;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl.mController, "registerSplitScreenListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(iSplitScreenImpl, iSplitScreenListener$Stub$Proxy), false);
            return true;
        }
        if (i == 3) {
            IBinder strongBinder2 = parcel.readStrongBinder();
            if (strongBinder2 != null) {
                IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof ISplitScreenListener$Stub$Proxy)) {
                    new ISplitScreenListener$Stub$Proxy(strongBinder2);
                }
            }
            parcel.enforceNoDataAvail();
            SplitScreenController.ISplitScreenImpl iSplitScreenImpl2 = (SplitScreenController.ISplitScreenImpl) this;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl2.mController, "unregisterSplitScreenListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(iSplitScreenImpl2, 4), false);
            return true;
        }
        if (i == 17) {
            final PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
            final int i6 = parcel.readInt();
            Parcelable.Creator creator = Bundle.CREATOR;
            final Bundle bundle = (Bundle) parcel.readTypedObject(creator);
            final int i7 = parcel.readInt();
            final Bundle bundle2 = (Bundle) parcel.readTypedObject(creator);
            final int i8 = parcel.readInt();
            final int i9 = parcel.readInt();
            final RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
            final InstanceId instanceId = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
            parcel.enforceNoDataAvail();
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntentAndTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda26
                /* JADX WARN: Removed duplicated region for block: B:40:0x00e6  */
                @Override // java.util.function.Consumer
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void accept(Object obj) {
                    boolean z;
                    Intent intent;
                    boolean zIsPackageActiveInPip;
                    Intent intent2;
                    PendingIntent pendingIntent2 = pendingIntent;
                    int i10 = i6;
                    Bundle bundle3 = bundle;
                    int i11 = i7;
                    Bundle bundle4 = bundle2;
                    int i12 = i8;
                    int i13 = i9;
                    RemoteTransition remoteTransition2 = remoteTransition;
                    InstanceId instanceId2 = instanceId;
                    SplitScreenController splitScreenController = (SplitScreenController) obj;
                    int i14 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                    splitScreenController.getClass();
                    String packageName = ComponentUtils.getPackageName(pendingIntent2);
                    ShellTaskOrganizer shellTaskOrganizer = splitScreenController.mTaskOrganizer;
                    ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i11);
                    String packageName2 = runningTaskInfo == null ? null : ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo).baseIntent);
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(i11);
                    if (MultiInstanceHelper.samePackage(i10, runningTaskInfo2 != null ? runningTaskInfo2.userId : -1, packageName, packageName2)) {
                        MultiInstanceHelper.Companion.getClass();
                        boolean zSupportsMultiInstanceSplit = splitScreenController.mMultiInstanceHelpher.supportsMultiInstanceSplit(i10, (pendingIntent2 == null || (intent2 = pendingIntent2.getIntent()) == null) ? null : intent2.getComponent());
                        z = true;
                        if (!zSupportsMultiInstanceSplit) {
                            if (splitScreenController.mRecentTasksOptional.isPresent()) {
                                ((RecentTasksController) splitScreenController.mRecentTasksOptional.get()).removeSplitPair(i11);
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2175723388761225132L, 0, null);
                            }
                            Log.w("SplitScreenController", SplitScreenUtils.splitFailureMessage("startIntentAndTask", "app package " + packageName + " does not support multi-instance"));
                            Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                            z = false;
                            i11 = -1;
                        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2564351321352908886L, 0, null);
                        }
                    } else {
                        z = false;
                    }
                    if (bundle4 != null) {
                        intent = (Intent) bundle4.getParcelable("key_extra_widget_intent", Intent.class);
                        if (z && intent != null) {
                            intent.addFlags(134217728);
                        } else if (intent == null) {
                            if (z) {
                                intent = new Intent();
                                intent.addFlags(134217728);
                            } else {
                                intent = null;
                            }
                        }
                    }
                    StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                    stageCoordinator.getClass();
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8162109405342937906L, 84, String.valueOf(pendingIntent2.getIntent()), Long.valueOf(i11), Long.valueOf(i12), Long.valueOf(i13));
                    }
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    boolean zIsIntentInPip = stageCoordinator.mMixedHandler.isIntentInPip(pendingIntent2);
                    PipTransitionController pipTransitionController = stageCoordinator.mMixedHandler.mPipHandler;
                    if (pipTransitionController != null) {
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = stageCoordinator.mTaskOrganizer.getRunningTaskInfo(i11);
                        zIsPackageActiveInPip = pipTransitionController.isPackageActiveInPip(runningTaskInfo3 == null ? null : ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo3).baseIntent));
                    } else {
                        zIsPackageActiveInPip = false;
                    }
                    if (i11 == -1 || zIsPackageActiveInPip) {
                        if (bundle3 == null) {
                            bundle3 = new Bundle();
                        }
                        StageCoordinator.addActivityOptions(bundle3, null);
                        windowContainerTransaction.sendPendingIntent(pendingIntent2, intent, bundle3);
                        stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition2);
                        return;
                    }
                    if (zIsIntentInPip) {
                        stageCoordinator.startSingleTask(i11, bundle4, windowContainerTransaction, remoteTransition2);
                        return;
                    }
                    stageCoordinator.setSideStagePosition$1(windowContainerTransaction, i12);
                    if (bundle3 == null) {
                        bundle3 = new Bundle();
                    }
                    StageCoordinator.addActivityOptions(bundle3, stageCoordinator.mSideStage);
                    windowContainerTransaction.sendPendingIntent(pendingIntent2, intent, bundle3);
                    stageCoordinator.prepareTasksForSplitScreen(new int[]{i11}, windowContainerTransaction);
                    stageCoordinator.startWithTask(windowContainerTransaction, i11, bundle4, i13, 0.5f, -1, null, 0.5f, 0, -1, false, remoteTransition2, instanceId2, true, true, null);
                }
            }, false);
            return true;
        }
        if (i == 18) {
            final ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
            Parcelable.Creator creator2 = Bundle.CREATOR;
            final Bundle bundle3 = (Bundle) parcel.readTypedObject(creator2);
            final int i10 = parcel.readInt();
            final Bundle bundle4 = (Bundle) parcel.readTypedObject(creator2);
            final int i11 = parcel.readInt();
            final int i12 = parcel.readInt();
            final RemoteTransition remoteTransition2 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
            final InstanceId instanceId2 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
            parcel.enforceNoDataAvail();
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcutAndTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ShortcutInfo shortcutInfo2 = shortcutInfo;
                    Bundle bundle5 = bundle3;
                    int i13 = i10;
                    Bundle bundle6 = bundle4;
                    int i14 = i11;
                    int i15 = i12;
                    RemoteTransition remoteTransition3 = remoteTransition2;
                    InstanceId instanceId3 = instanceId2;
                    SplitScreenController splitScreenController = (SplitScreenController) obj;
                    int i16 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                    splitScreenController.getClass();
                    if (bundle5 == null) {
                        bundle5 = new Bundle();
                    }
                    ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundle5);
                    String str = shortcutInfo2.getPackage();
                    int i17 = ComponentUtils.$r8$clinit;
                    ShellTaskOrganizer shellTaskOrganizer = splitScreenController.mTaskOrganizer;
                    ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i13);
                    String packageName = runningTaskInfo == null ? null : ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo).baseIntent);
                    int userId = shortcutInfo2.getUserId();
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer.getRunningTaskInfo(i13);
                    if (MultiInstanceHelper.samePackage(userId, runningTaskInfo2 != null ? runningTaskInfo2.userId : -1, str, packageName)) {
                        if (splitScreenController.mMultiInstanceHelpher.supportsMultiInstanceSplit(userId, shortcutInfo2.getActivity())) {
                            activityOptionsFromBundle.setApplyMultipleTaskFlagForShortcut(true);
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2564351321352908886L, 0, null);
                            }
                        } else {
                            if (splitScreenController.mRecentTasksOptional.isPresent()) {
                                ((RecentTasksController) splitScreenController.mRecentTasksOptional.get()).removeSplitPair(i13);
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2175723388761225132L, 0, null);
                            }
                            Log.w("SplitScreenController", SplitScreenUtils.splitFailureMessage("startShortcutAndTask", "app package " + str + " does not support multi-instance"));
                            Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                            i13 = -1;
                        }
                    }
                    StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                    Bundle bundle7 = activityOptionsFromBundle.toBundle();
                    stageCoordinator.getClass();
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3306666680418709578L, 84, String.valueOf(shortcutInfo2), Long.valueOf(i13), Long.valueOf(i14), Long.valueOf(i15));
                    }
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    if (i13 == -1) {
                        if (bundle7 == null) {
                            bundle7 = new Bundle();
                        }
                        StageCoordinator.addActivityOptions(bundle7, null);
                        windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo2, bundle7);
                        stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition3);
                        return;
                    }
                    stageCoordinator.setSideStagePosition$1(windowContainerTransaction, i14);
                    if (bundle7 == null) {
                        bundle7 = new Bundle();
                    }
                    StageCoordinator.addActivityOptions(bundle7, stageCoordinator.mSideStage);
                    windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo2, bundle7);
                    stageCoordinator.prepareTasksForSplitScreen(new int[]{i13}, windowContainerTransaction);
                    stageCoordinator.startWithTask(windowContainerTransaction, i13, bundle6, i15, 0.5f, -1, null, 0.5f, 0, -1, false, remoteTransition3, instanceId3, true, true, null);
                }
            }, false);
            return true;
        }
        switch (i) {
            case 6:
                int i13 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreen", new SplitScreenController$$ExternalSyntheticLambda9(i13, i5), false);
                return true;
            case 7:
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreenOnHide", new SplitScreenController$$ExternalSyntheticLambda10(z, i5), false);
                return true;
            case 8:
                final int i14 = parcel.readInt();
                final int i15 = parcel.readInt();
                final Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda23
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i16 = i14;
                        int i17 = i15;
                        Bundle bundle6 = bundle5;
                        int i18 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        ((SplitScreenController) obj).startTask(i16, i17, bundle6, null);
                    }
                }, false);
                return true;
            case 9:
                final String string = parcel.readString();
                final String string2 = parcel.readString();
                final int i16 = parcel.readInt();
                final Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                final UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                final InstanceId instanceId3 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcut", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda25
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = string;
                        String str2 = string2;
                        int i17 = i16;
                        Bundle bundle7 = bundle6;
                        UserHandle userHandle2 = userHandle;
                        InstanceId instanceId4 = instanceId3;
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i18 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        splitScreenController.getClass();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7987057462806301533L, 1, 3L);
                        }
                        SplitscreenEventLogger splitscreenEventLogger = splitScreenController.mStageCoordinator.mLogger;
                        splitscreenEventLogger.mEnterSessionId = instanceId4;
                        splitscreenEventLogger.mEnterReason = 3;
                        splitScreenController.startShortcut(str, str2, i17, bundle7, userHandle2);
                    }
                }, false);
                return true;
            case 10:
                final PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                final int i17 = parcel.readInt();
                final Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                final int i18 = parcel.readInt();
                final Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                final InstanceId instanceId4 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntent", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PendingIntent pendingIntent3 = pendingIntent2;
                        int i19 = i17;
                        Intent intent2 = intent;
                        int i20 = i18;
                        Bundle bundle8 = bundle7;
                        InstanceId instanceId5 = instanceId4;
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i21 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        splitScreenController.getClass();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1752260962322402115L, 1, 3L);
                        }
                        SplitscreenEventLogger splitscreenEventLogger = splitScreenController.mStageCoordinator.mLogger;
                        splitscreenEventLogger.mEnterSessionId = instanceId5;
                        splitscreenEventLogger.mEnterReason = 3;
                        splitScreenController.startIntent(pendingIntent3, i19, intent2, i20, bundle8, null, false, -1, -1, 0, false);
                    }
                }, false);
                return true;
            case 11:
                final int i19 = parcel.readInt();
                Parcelable.Creator creator3 = Bundle.CREATOR;
                final Bundle bundle8 = (Bundle) parcel.readTypedObject(creator3);
                final int i20 = parcel.readInt();
                final Bundle bundle9 = (Bundle) parcel.readTypedObject(creator3);
                final int i21 = parcel.readInt();
                final int i22 = parcel.readInt();
                final RemoteTransition remoteTransition3 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final InstanceId instanceId5 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                final SplitScreenController.ISplitScreenImpl iSplitScreenImpl3 = (SplitScreenController.ISplitScreenImpl) this;
                if (i20 != -1) {
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl3.mController, "startTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda10
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i23 = i19;
                            Bundle bundle10 = bundle8;
                            int i24 = i20;
                            Bundle bundle11 = bundle9;
                            int i25 = i21;
                            int i26 = i22;
                            RemoteTransition remoteTransition4 = remoteTransition3;
                            InstanceId instanceId6 = instanceId5;
                            int i27 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                            ((SplitScreenController) obj).mStageCoordinator.startTasks(i23, bundle10, i24, bundle11, -1, null, i25, i26, 0.5f, 0, 0.5f, remoteTransition4, instanceId6, -1, false, null);
                        }
                    }, false);
                    return true;
                }
                final SplitScreenController.CallerInfo callerInfo = new SplitScreenController.CallerInfo();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl3.mController, "startSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl4 = iSplitScreenImpl3;
                        int i23 = i19;
                        Bundle bundle10 = bundle8;
                        int i24 = i20;
                        Bundle bundle11 = bundle9;
                        int i25 = i21;
                        RemoteTransition remoteTransition4 = remoteTransition3;
                        InstanceId instanceId6 = instanceId5;
                        SplitScreenController.CallerInfo callerInfo2 = callerInfo;
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i26 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        splitScreenController.mStageCoordinator.startTasks(i23, bundle10, i24, bundle11, -1, null, i25, 8, 0.5f, 0, 0.5f, remoteTransition4, instanceId6, (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || MultiWindowUtils.isInSubDisplay(iSplitScreenImpl4.mController.mContext)) ? -1 : 0, false, callerInfo2);
                    }
                }, false);
                return true;
            default:
                switch (i) {
                    case 20:
                        Parcelable.Creator creator4 = PendingIntent.CREATOR;
                        final PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(creator4);
                        final int i23 = parcel.readInt();
                        Parcelable.Creator creator5 = ShortcutInfo.CREATOR;
                        final ShortcutInfo shortcutInfo2 = (ShortcutInfo) parcel.readTypedObject(creator5);
                        Parcelable.Creator creator6 = Bundle.CREATOR;
                        final Bundle bundle10 = (Bundle) parcel.readTypedObject(creator6);
                        final PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(creator4);
                        final int i24 = parcel.readInt();
                        final ShortcutInfo shortcutInfo3 = (ShortcutInfo) parcel.readTypedObject(creator5);
                        final Bundle bundle11 = (Bundle) parcel.readTypedObject(creator6);
                        final int i25 = parcel.readInt();
                        final int i26 = parcel.readInt();
                        final RemoteTransition remoteTransition4 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                        final InstanceId instanceId6 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntents", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda19
                            /* JADX WARN: Removed duplicated region for block: B:44:0x0112  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) {
                                ActivityOptions activityOptions;
                                ShortcutInfo shortcutInfo4;
                                Intent intent2;
                                boolean z2;
                                Intent intent3;
                                Bundle bundle12;
                                Bundle bundle13;
                                ShortcutInfo shortcutInfo5;
                                Intent intent4;
                                boolean z3;
                                Intent intent5;
                                PendingIntent pendingIntent5 = pendingIntent3;
                                int i27 = i23;
                                ShortcutInfo shortcutInfo6 = shortcutInfo2;
                                Bundle bundle14 = bundle10;
                                PendingIntent pendingIntent6 = pendingIntent4;
                                int i28 = i24;
                                ShortcutInfo shortcutInfo7 = shortcutInfo3;
                                Bundle bundle15 = bundle11;
                                int i29 = i25;
                                int i30 = i26;
                                RemoteTransition remoteTransition5 = remoteTransition4;
                                InstanceId instanceId7 = instanceId6;
                                SplitScreenController splitScreenController = (SplitScreenController) obj;
                                int i31 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                splitScreenController.getClass();
                                String packageName = ComponentUtils.getPackageName(pendingIntent5);
                                String packageName2 = ComponentUtils.getPackageName(pendingIntent6);
                                ActivityOptions activityOptionsFromBundle = bundle14 != null ? ActivityOptions.fromBundle(bundle14) : ActivityOptions.makeBasic();
                                ActivityOptions activityOptionsFromBundle2 = bundle15 != null ? ActivityOptions.fromBundle(bundle15) : ActivityOptions.makeBasic();
                                if (MultiInstanceHelper.samePackage(i27, i28, packageName, packageName2)) {
                                    MultiInstanceHelper.Companion.getClass();
                                    if (splitScreenController.mMultiInstanceHelpher.supportsMultiInstanceSplit(i27, (pendingIntent5 == null || (intent5 = pendingIntent5.getIntent()) == null) ? null : intent5.getComponent())) {
                                        Intent intent6 = new Intent();
                                        intent6.addFlags(134217728);
                                        if (shortcutInfo6 != null) {
                                            activityOptionsFromBundle.setApplyMultipleTaskFlagForShortcut(true);
                                        }
                                        if (shortcutInfo7 != null) {
                                            activityOptionsFromBundle2.setApplyMultipleTaskFlagForShortcut(true);
                                        }
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                                            z3 = true;
                                            shortcutInfo4 = shortcutInfo7;
                                            intent4 = intent6;
                                            activityOptions = activityOptionsFromBundle;
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2564351321352908886L, 0, null);
                                        } else {
                                            intent4 = intent6;
                                            activityOptions = activityOptionsFromBundle;
                                            z3 = true;
                                            shortcutInfo4 = shortcutInfo7;
                                        }
                                        z2 = z3;
                                        intent2 = intent4;
                                    } else {
                                        activityOptions = activityOptionsFromBundle;
                                        shortcutInfo4 = shortcutInfo7;
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2175723388761225132L, 0, null);
                                        }
                                        Log.w("SplitScreenController", SplitScreenUtils.splitFailureMessage("startIntents", "app package " + packageName + " does not support multi-instance"));
                                        Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                        intent2 = null;
                                        z2 = false;
                                        pendingIntent6 = null;
                                    }
                                } else {
                                    activityOptions = activityOptionsFromBundle;
                                    shortcutInfo4 = shortcutInfo7;
                                    intent2 = null;
                                    z2 = false;
                                }
                                if (bundle15 != null) {
                                    intent3 = (Intent) bundle15.getParcelable("key_extra_widget_intent", Intent.class);
                                    if (z2 && intent3 != null) {
                                        intent3.addFlags(134217728);
                                    } else if (intent3 == null) {
                                        if (z2) {
                                            Intent intent7 = new Intent();
                                            intent7.addFlags(134217728);
                                            intent3 = intent7;
                                        } else {
                                            intent3 = null;
                                        }
                                    }
                                }
                                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                Bundle bundle16 = activityOptions.toBundle();
                                Bundle bundle17 = activityOptionsFromBundle2.toBundle();
                                stageCoordinator.getClass();
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                                    shortcutInfo5 = shortcutInfo4;
                                    bundle12 = bundle16;
                                    bundle13 = bundle17;
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -3036901654114583224L, 80, String.valueOf(pendingIntent5.getIntent()), String.valueOf(pendingIntent6 != null ? pendingIntent6.getIntent() : "null"), Long.valueOf(i29), Long.valueOf(i30));
                                } else {
                                    bundle12 = bundle16;
                                    bundle13 = bundle17;
                                    shortcutInfo5 = shortcutInfo4;
                                }
                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                if (pendingIntent6 == null) {
                                    Bundle bundle18 = bundle12 != null ? bundle12 : new Bundle();
                                    StageCoordinator.addActivityOptions(bundle18, null);
                                    if (shortcutInfo6 != null) {
                                        windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo6, bundle18);
                                    } else {
                                        windowContainerTransaction.sendPendingIntent(pendingIntent5, intent2, bundle18);
                                    }
                                    stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition5);
                                    return;
                                }
                                boolean zIsIntentInPip = stageCoordinator.mMixedHandler.isIntentInPip(pendingIntent5);
                                boolean zIsIntentInPip2 = stageCoordinator.mMixedHandler.isIntentInPip(pendingIntent6);
                                if (zIsIntentInPip || zIsIntentInPip2) {
                                    ShortcutInfo shortcutInfo8 = shortcutInfo5;
                                    Bundle bundle19 = zIsIntentInPip2 ? bundle12 : bundle13;
                                    if (bundle19 == null) {
                                        bundle19 = new Bundle();
                                    }
                                    StageCoordinator.addActivityOptions(bundle19, null);
                                    if (shortcutInfo6 != null || shortcutInfo8 != null) {
                                        if (!zIsIntentInPip2) {
                                            shortcutInfo6 = shortcutInfo8;
                                        }
                                        windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo6, bundle19);
                                        stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition5);
                                        return;
                                    }
                                    if (!zIsIntentInPip2) {
                                        pendingIntent5 = pendingIntent6;
                                    }
                                    if (!zIsIntentInPip2) {
                                        intent2 = intent3;
                                    }
                                    StageCoordinator.addActivityOptions(bundle19, null);
                                    windowContainerTransaction.sendPendingIntent(pendingIntent5, intent2, bundle19);
                                    stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition5);
                                    return;
                                }
                                StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
                                if (!stageTaskListener.mIsActive) {
                                    stageTaskListener.activate(windowContainerTransaction, false);
                                }
                                stageCoordinator.setSideStagePosition$1(windowContainerTransaction, i29);
                                stageCoordinator.mSplitLayout.setDivideRatio(0.5f, true, true);
                                stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction, false);
                                windowContainerTransaction.reorder(stageCoordinator.mRootTaskInfo.token, true);
                                windowContainerTransaction.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, false);
                                stageCoordinator.setRootForceTranslucent(windowContainerTransaction, false);
                                Bundle bundle20 = bundle12 != null ? bundle12 : new Bundle();
                                StageCoordinator.addActivityOptions(bundle20, stageCoordinator.mSideStage);
                                if (shortcutInfo6 != null) {
                                    windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo6, bundle20);
                                } else {
                                    windowContainerTransaction.sendPendingIntent(pendingIntent5, intent2, bundle20);
                                }
                                Bundle bundle21 = bundle13 != null ? bundle13 : new Bundle();
                                StageCoordinator.addActivityOptions(bundle21, stageTaskListener);
                                if (shortcutInfo5 != null) {
                                    windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo5, bundle21);
                                } else {
                                    windowContainerTransaction.sendPendingIntent(pendingIntent6, intent3, bundle21);
                                }
                                stageCoordinator.mSplitTransitions.startEnterTransition(windowContainerTransaction, remoteTransition5, stageCoordinator, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, false, i30);
                                if (instanceId7 != null) {
                                    SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
                                    splitscreenEventLogger.mEnterSessionId = instanceId7;
                                    splitscreenEventLogger.mEnterReason = 3;
                                }
                            }
                        }, false);
                        return true;
                    case 21:
                        IBinder strongBinder3 = parcel.readStrongBinder();
                        if (strongBinder3 != null) {
                            IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitSelectListener");
                            iSplitSelectListener$Stub$Proxy = (iInterfaceQueryLocalInterface3 == null || !(iInterfaceQueryLocalInterface3 instanceof ISplitSelectListener$Stub$Proxy)) ? new ISplitSelectListener$Stub$Proxy(strongBinder3) : (ISplitSelectListener$Stub$Proxy) iInterfaceQueryLocalInterface3;
                        }
                        parcel.enforceNoDataAvail();
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl4 = (SplitScreenController.ISplitScreenImpl) this;
                        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl4.mController, "registerSplitSelectListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(iSplitScreenImpl4, iSplitSelectListener$Stub$Proxy), false);
                        return true;
                    case 22:
                        IBinder strongBinder4 = parcel.readStrongBinder();
                        if (strongBinder4 != null) {
                            IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitSelectListener");
                            if (iInterfaceQueryLocalInterface4 == null || !(iInterfaceQueryLocalInterface4 instanceof ISplitSelectListener$Stub$Proxy)) {
                                new ISplitSelectListener$Stub$Proxy(strongBinder4);
                            }
                        }
                        parcel.enforceNoDataAvail();
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl5 = (SplitScreenController.ISplitScreenImpl) this;
                        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl5.mController, "unregisterSplitSelectListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(iSplitScreenImpl5, 5), false);
                        return true;
                    case 23:
                        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "switchSplitPosition", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda13
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SplitScreenController splitScreenController = (SplitScreenController) obj;
                                switch (i4) {
                                    case 0:
                                        int i27 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        if (splitScreenController.mStageCoordinator.isSplitScreenVisible()) {
                                            splitScreenController.mStageCoordinator.switchSplitPosition("remoteCall");
                                            break;
                                        }
                                        break;
                                    default:
                                        int i28 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        SplitWindowManager splitWindowManager = splitScreenController.mStageCoordinator.mSplitLayout.mSplitWindowManager;
                                        AlertDialog alertDialog = splitWindowManager.mAddToAppPairDialogForRecent;
                                        if (alertDialog != null) {
                                            alertDialog.dismiss();
                                            splitWindowManager.mAddToAppPairDialogForRecent = null;
                                            break;
                                        }
                                        break;
                                }
                            }
                        }, false);
                        return true;
                    default:
                        switch (i) {
                            case 102:
                                int i27 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startSplitByTwoTouchSwipeIfPossible", new SplitScreenController$$ExternalSyntheticLambda9(i27, i3), false);
                                return true;
                            case 103:
                                final int i28 = parcel.readInt();
                                Parcelable.Creator creator7 = Bundle.CREATOR;
                                final Bundle bundle12 = (Bundle) parcel.readTypedObject(creator7);
                                final int i29 = parcel.readInt();
                                final Bundle bundle13 = (Bundle) parcel.readTypedObject(creator7);
                                final int i30 = parcel.readInt();
                                final float f = parcel.readFloat();
                                final RemoteTransition remoteTransition5 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                                final InstanceId instanceId7 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                                parcel.enforceNoDataAvail();
                                final SplitScreenController.ISplitScreenImpl iSplitScreenImpl6 = (SplitScreenController.ISplitScreenImpl) this;
                                if (i29 != -1) {
                                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl6.mController, "startSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda15
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i31 = i28;
                                            Bundle bundle14 = bundle12;
                                            int i32 = i29;
                                            Bundle bundle15 = bundle13;
                                            int i33 = i30;
                                            float f2 = f;
                                            RemoteTransition remoteTransition6 = remoteTransition5;
                                            InstanceId instanceId8 = instanceId7;
                                            int i34 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                            ((SplitScreenController) obj).mStageCoordinator.startTasks(i31, bundle14, i32, bundle15, -1, null, i33, 8, f2, 0, 0.5f, remoteTransition6, instanceId8, -1, false, null);
                                        }
                                    }, false);
                                    return true;
                                }
                                final SplitScreenController.CallerInfo callerInfo2 = new SplitScreenController.CallerInfo();
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl6.mController, "startSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda14
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl7 = iSplitScreenImpl6;
                                        int i31 = i28;
                                        Bundle bundle14 = bundle12;
                                        int i32 = i29;
                                        Bundle bundle15 = bundle13;
                                        int i33 = i30;
                                        float f2 = f;
                                        RemoteTransition remoteTransition6 = remoteTransition5;
                                        InstanceId instanceId8 = instanceId7;
                                        SplitScreenController.CallerInfo callerInfo3 = callerInfo2;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                                        int i34 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        splitScreenController.mStageCoordinator.startTasks(i31, bundle14, i32, bundle15, -1, null, i33, 8, f2, 0, 0.5f, remoteTransition6, instanceId8, (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || MultiWindowUtils.isInSubDisplay(iSplitScreenImpl7.mController.mContext)) ? -1 : 0, false, callerInfo3);
                                    }
                                }, false);
                                return true;
                            case 104:
                                final int i31 = parcel.readInt();
                                Parcelable.Creator creator8 = Bundle.CREATOR;
                                final Bundle bundle14 = (Bundle) parcel.readTypedObject(creator8);
                                final int i32 = parcel.readInt();
                                final Bundle bundle15 = (Bundle) parcel.readTypedObject(creator8);
                                final int i33 = parcel.readInt();
                                final Bundle bundle16 = (Bundle) parcel.readTypedObject(creator8);
                                final int i34 = parcel.readInt();
                                final float f2 = parcel.readFloat();
                                final int i35 = parcel.readInt();
                                final float f3 = parcel.readFloat();
                                boolean z2 = parcel.readBoolean();
                                final RemoteTransition remoteTransition6 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                                final InstanceId instanceId8 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                                parcel.enforceNoDataAvail();
                                SplitScreenController splitScreenController = ((SplitScreenController.ISplitScreenImpl) this).mController;
                                final int i36 = z2 ? 1 : 0;
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(splitScreenController, "startMultiSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda22
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i37 = i31;
                                        Bundle bundle17 = bundle14;
                                        int i38 = i32;
                                        Bundle bundle18 = bundle15;
                                        int i39 = i33;
                                        Bundle bundle19 = bundle16;
                                        int i40 = i34;
                                        float f4 = f2;
                                        int i41 = i35;
                                        float f5 = f3;
                                        RemoteTransition remoteTransition7 = remoteTransition6;
                                        InstanceId instanceId9 = instanceId8;
                                        int i42 = i36;
                                        int i43 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        ((SplitScreenController) obj).mStageCoordinator.startTasks(i37, bundle17, i38, bundle18, i39, bundle19, i40, 8, f4, i41, f5, remoteTransition7, instanceId9, i42, false, null);
                                    }
                                }, false);
                                return true;
                            case 105:
                                final int i37 = parcel.readInt();
                                final int i38 = parcel.readInt();
                                final int i39 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "showAddAppPairDialogForRecent", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda7
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        final int i40 = i37;
                                        final int i41 = i38;
                                        final int i42 = i39;
                                        int i43 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        final StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.mRecentTasks.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda25
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj2) {
                                                StageCoordinator stageCoordinator2 = stageCoordinator;
                                                final int i44 = i40;
                                                final int i45 = i41;
                                                final int i46 = i42;
                                                stageCoordinator2.getClass();
                                                final SplitBounds splitBoundsForTaskId = ((RecentTasksController) obj2).getSplitBoundsForTaskId(i44);
                                                if (splitBoundsForTaskId == null || i44 != splitBoundsForTaskId.leftTopTaskId || i45 != splitBoundsForTaskId.rightBottomTaskId || i46 != splitBoundsForTaskId.cellTaskId) {
                                                    Slog.d("StageCoordinator", "showAddAppPairDialogForRecent: Invalid taskId");
                                                    return;
                                                }
                                                final SplitWindowManager splitWindowManager = stageCoordinator2.mSplitLayout.mSplitWindowManager;
                                                AlertDialog alertDialog = splitWindowManager.mAddToAppPairDialogForRecent;
                                                if (alertDialog != null && alertDialog.isShowing()) {
                                                    Slog.d("SplitWindowManager", "showAddToAppPairDialogForRecent: The dialog already being displayed. it will not be executed again");
                                                    return;
                                                }
                                                final ArrayMap appPairDialogItems = splitWindowManager.mDividerPanel.getAppPairDialogItems();
                                                if (appPairDialogItems.isEmpty()) {
                                                    return;
                                                }
                                                splitWindowManager.mContext = new ContextThemeWrapper(splitWindowManager.mContext, android.R.style.Theme.DeviceDefault.DayNight);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(splitWindowManager.mContext);
                                                builder.setTitle(R.string.add_app_pair_to);
                                                builder.setItems((CharSequence[]) appPairDialogItems.values().toArray(new String[appPairDialogItems.size()]), new DialogInterface.OnClickListener() { // from class: com.android.wm.shell.common.split.SplitWindowManager$$ExternalSyntheticLambda1
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(DialogInterface dialogInterface, int i47) {
                                                        SplitWindowManager splitWindowManager2 = splitWindowManager;
                                                        ArrayMap arrayMap = appPairDialogItems;
                                                        int i48 = i44;
                                                        int i49 = i45;
                                                        int i50 = i46;
                                                        SplitBounds splitBounds = splitBoundsForTaskId;
                                                        AppPairShortcutController appPairShortcutController = splitWindowManager2.mAppPairShortcutController;
                                                        int iIntValue = ((Integer) arrayMap.keyAt(i47)).intValue();
                                                        appPairShortcutController.getClass();
                                                        int i51 = -1;
                                                        if (i48 == -1 || i49 == -1) {
                                                            Slog.e("AppPairShortcutController", "createAppPairShortcutForRecent: Invalid taskId");
                                                            return;
                                                        }
                                                        appPairShortcutController.mStageCoordinator.getClass();
                                                        ActivityManager.RecentTaskInfo recentTaskInfo = StageCoordinator.getRecentTaskInfo(i48);
                                                        ActivityManager.RecentTaskInfo recentTaskInfo2 = StageCoordinator.getRecentTaskInfo(i49);
                                                        if (recentTaskInfo == null || recentTaskInfo2 == null) {
                                                            Slog.e("AppPairShortcutController", "createAppPairShortcutForRecent: Can't find tasks.");
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        boolean z3 = i50 != -1;
                                                        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && z3) {
                                                            ActivityManager.RecentTaskInfo recentTaskInfo3 = StageCoordinator.getRecentTaskInfo(i50);
                                                            if (recentTaskInfo3 == null) {
                                                                Slog.e("AppPairShortcutController", "createAppPairShortcutForRecent: Can't find tasks for cell");
                                                                return;
                                                            }
                                                            boolean z4 = splitBounds.appsStackedVertically;
                                                            int i52 = z4 ? (splitBounds.cellPosition & 16) != 0 ? 3 : 5 : (splitBounds.cellPosition & 8) != 0 ? 2 : 4;
                                                            int cellSide = CellUtil.getCellSide(splitBounds.cellPosition, !z4, false);
                                                            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitBounds.parallelMultiSplit) {
                                                                if (i52 == 2) {
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo3);
                                                                } else if (i52 == 3) {
                                                                    arrayList.add(recentTaskInfo3);
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo);
                                                                } else if (i52 == 4) {
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo3);
                                                                } else if (i52 == 5) {
                                                                    arrayList.add(recentTaskInfo3);
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo2);
                                                                }
                                                            } else if (i52 == 2 || i52 == 3) {
                                                                if (cellSide == 2 || cellSide == 3) {
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo3);
                                                                } else {
                                                                    arrayList.add(recentTaskInfo3);
                                                                    arrayList.add(recentTaskInfo);
                                                                }
                                                                arrayList.add(1, recentTaskInfo2);
                                                            } else {
                                                                if (cellSide == 2 || cellSide == 3) {
                                                                    arrayList.add(recentTaskInfo3);
                                                                    arrayList.add(recentTaskInfo2);
                                                                } else {
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo3);
                                                                }
                                                                arrayList.add(1, recentTaskInfo);
                                                            }
                                                            i51 = i52;
                                                        } else {
                                                            arrayList.add(recentTaskInfo);
                                                            arrayList.add(recentTaskInfo2);
                                                        }
                                                        ArrayList arrayList2 = new ArrayList();
                                                        int[] iArr = new int[3];
                                                        for (int i53 = 0; i53 < arrayList.size(); i53++) {
                                                            ActivityManager.RecentTaskInfo recentTaskInfo4 = (ActivityManager.RecentTaskInfo) arrayList.get(i53);
                                                            String launchActivityForTask = AppPairShortcutController.getLaunchActivityForTask(recentTaskInfo4);
                                                            if (launchActivityForTask != null) {
                                                                ComponentName componentName = recentTaskInfo4.baseActivity;
                                                                if (componentName == null || !componentName.getClassName().equals("com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity")) {
                                                                    arrayList2.add(launchActivityForTask);
                                                                } else {
                                                                    arrayList2.add("com.google.android.apps.bard/.shellapp.BardEntryPointActivity");
                                                                }
                                                                iArr[i53] = recentTaskInfo4.userId;
                                                            }
                                                        }
                                                        if (arrayList.size() != arrayList2.size()) {
                                                            return;
                                                        }
                                                        Intent intentCreateAppPairShortcutIntentForRecent = (iIntValue == 0 || iIntValue == 1) ? appPairShortcutController.createAppPairShortcutIntentForRecent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_LAUNCHER", arrayList2, iArr, iIntValue, i51, z3, splitBounds) : iIntValue != 2 ? iIntValue != 3 ? null : appPairShortcutController.createAppPairShortcutIntentForRecent("com.samsung.android.multiwindow.SEND_SPLIT_STATE_CHANGED", arrayList2, iArr, iIntValue, i51, z3, splitBounds) : appPairShortcutController.createAppPairShortcutIntentForRecent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_EDGEPANEL", arrayList2, iArr, iIntValue, i51, z3, splitBounds);
                                                        if (intentCreateAppPairShortcutIntentForRecent != null) {
                                                            AppPairShortcutController.H h = appPairShortcutController.mH;
                                                            if (iIntValue == 3) {
                                                                h.sendMessage(h.obtainMessage(7, intentCreateAppPairShortcutIntentForRecent));
                                                            } else {
                                                                h.sendMessage(h.obtainMessage(6, intentCreateAppPairShortcutIntentForRecent));
                                                            }
                                                        }
                                                    }
                                                });
                                                AlertDialog alertDialogCreate = builder.create();
                                                splitWindowManager.mAddToAppPairDialogForRecent = alertDialogCreate;
                                                alertDialogCreate.getWindow().setType(2008);
                                                splitWindowManager.mAddToAppPairDialogForRecent.getWindow().setGravity(80);
                                                splitWindowManager.mAddToAppPairDialogForRecent.show();
                                            }
                                        });
                                    }
                                }, false);
                                return true;
                            case 106:
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "dismissAddToAppPairDialogForRecent", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda13
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        switch (i5) {
                                            case 0:
                                                int i272 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                                if (splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    splitScreenController2.mStageCoordinator.switchSplitPosition("remoteCall");
                                                    break;
                                                }
                                                break;
                                            default:
                                                int i282 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                                SplitWindowManager splitWindowManager = splitScreenController2.mStageCoordinator.mSplitLayout.mSplitWindowManager;
                                                AlertDialog alertDialog = splitWindowManager.mAddToAppPairDialogForRecent;
                                                if (alertDialog != null) {
                                                    alertDialog.dismiss();
                                                    splitWindowManager.mAddToAppPairDialogForRecent = null;
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                }, false);
                                return true;
                            case 107:
                                int i40 = parcel.readInt();
                                int i41 = parcel.readInt();
                                int i42 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                boolean[] zArr = {false};
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "canShowAddAppPairDialogForRecent", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20(i40, zArr, i41, i42, 0), true);
                                boolean z3 = zArr[0];
                                parcel2.writeNoException();
                                parcel2.writeBoolean(z3);
                                return true;
                            case 108:
                                final int i43 = parcel.readInt();
                                Parcelable.Creator creator9 = Bundle.CREATOR;
                                final Bundle bundle17 = (Bundle) parcel.readTypedObject(creator9);
                                final int i44 = parcel.readInt();
                                final Bundle bundle18 = (Bundle) parcel.readTypedObject(creator9);
                                final int i45 = parcel.readInt();
                                final Bundle bundle19 = (Bundle) parcel.readTypedObject(creator9);
                                final int i46 = parcel.readInt();
                                final float f4 = parcel.readFloat();
                                final int i47 = parcel.readInt();
                                final float f5 = parcel.readFloat();
                                boolean z4 = parcel.readBoolean();
                                final boolean z5 = parcel.readBoolean();
                                final RemoteTransition remoteTransition7 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                                final InstanceId instanceId9 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                                parcel.enforceNoDataAvail();
                                SplitScreenController splitScreenController2 = ((SplitScreenController.ISplitScreenImpl) this).mController;
                                final int i48 = z4 ? 1 : 0;
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(splitScreenController2, "startMultiSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda24
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i49 = i43;
                                        Bundle bundle20 = bundle17;
                                        int i50 = i44;
                                        Bundle bundle21 = bundle18;
                                        int i51 = i45;
                                        Bundle bundle22 = bundle19;
                                        int i52 = i46;
                                        float f6 = f4;
                                        int i53 = i47;
                                        float f7 = f5;
                                        RemoteTransition remoteTransition8 = remoteTransition7;
                                        InstanceId instanceId10 = instanceId9;
                                        int i54 = i48;
                                        boolean z6 = z5;
                                        int i55 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        ((SplitScreenController) obj).mStageCoordinator.startTasks(i49, bundle20, i50, bundle21, i51, bundle22, i52, 8, f6, i53, f7, remoteTransition8, instanceId10, i54, z6, null);
                                    }
                                }, false);
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                }
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
