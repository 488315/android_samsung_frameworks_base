package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.widget.Toast;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import com.android.internal.logging.InstanceId;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ExecutorUtils;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.common.split.SplitScreenUtils;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.transition.Transitions;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ISplitScreen$Stub extends Binder implements IInterface {
    public ISplitScreen$Stub() {
        attachInterface(this, "com.android.wm.shell.splitscreen.ISplitScreen");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        final int i3 = 1;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.splitscreen.ISplitScreen");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.splitscreen.ISplitScreen");
            return true;
        }
        final Object obj = null;
        final int i4 = 2;
        final int i5 = 0;
        if (i != 2) {
            int i6 = 3;
            if (i == 3) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof ISplitScreenListener)) {
                        new ISplitScreenListener$Stub$Proxy(readStrongBinder);
                    }
                }
                parcel.enforceNoDataAvail();
                SplitScreenController.ISplitScreenImpl iSplitScreenImpl = (SplitScreenController.ISplitScreenImpl) this;
                ExecutorUtils.executeRemoteCallWithTaskPermission(iSplitScreenImpl.mController, "unregisterSplitScreenListener", new SplitScreenController$$ExternalSyntheticLambda2(iSplitScreenImpl, i6), false);
            } else if (i == 102) {
                final int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startSplitByTwoTouchSwipeIfPossible", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        switch (i3) {
                            case 0:
                                ((SplitScreenController) obj2).exitSplitScreen(readInt, 0);
                                break;
                            case 1:
                                int i7 = readInt;
                                SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                splitScreenController.getClass();
                                splitScreenController.mGestureStarter.ifPresent(new SplitScreenController$$ExternalSyntheticLambda7(i7, "ISplitScreen", 0));
                                break;
                            default:
                                ((SplitScreenController) obj2).removeFromSideStage(readInt);
                                break;
                        }
                    }
                }, false);
            } else if (i != 103) {
                switch (i) {
                    case 5:
                        final int readInt2 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "removeFromSideStage", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                switch (i4) {
                                    case 0:
                                        ((SplitScreenController) obj2).exitSplitScreen(readInt2, 0);
                                        break;
                                    case 1:
                                        int i7 = readInt2;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        splitScreenController.mGestureStarter.ifPresent(new SplitScreenController$$ExternalSyntheticLambda7(i7, "ISplitScreen", 0));
                                        break;
                                    default:
                                        ((SplitScreenController) obj2).removeFromSideStage(readInt2);
                                        break;
                                }
                            }
                        }, false);
                        break;
                    case 6:
                        final int readInt3 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreen", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                switch (i5) {
                                    case 0:
                                        ((SplitScreenController) obj2).exitSplitScreen(readInt3, 0);
                                        break;
                                    case 1:
                                        int i7 = readInt3;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        splitScreenController.mGestureStarter.ifPresent(new SplitScreenController$$ExternalSyntheticLambda7(i7, "ISplitScreen", 0));
                                        break;
                                    default:
                                        ((SplitScreenController) obj2).removeFromSideStage(readInt3);
                                        break;
                                }
                            }
                        }, false);
                        break;
                    case 7:
                        boolean readBoolean = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreenOnHide", new SplitScreenController$$ExternalSyntheticLambda8(readBoolean, i3), false);
                        break;
                    case 8:
                        final int readInt4 = parcel.readInt();
                        final int readInt5 = parcel.readInt();
                        final Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                ((SplitScreenController) obj2).startTask(readInt4, readInt5, bundle);
                            }
                        }, false);
                        break;
                    case 9:
                        final String readString = parcel.readString();
                        final String readString2 = parcel.readString();
                        final int readInt6 = parcel.readInt();
                        final Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                        final UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                        final InstanceId instanceId = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcut", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                String str = readString;
                                String str2 = readString2;
                                int i7 = readInt6;
                                Bundle bundle3 = bundle2;
                                UserHandle userHandle2 = userHandle;
                                InstanceId instanceId2 = instanceId;
                                SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                if (!stageCoordinator.isSplitScreenVisible() && !Transitions.ENABLE_SHELL_TRANSITIONS) {
                                    stageCoordinator.exitSplitScreen(null, 10);
                                }
                                SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
                                splitscreenEventLogger.mEnterSessionId = instanceId2;
                                splitscreenEventLogger.mEnterReason = 3;
                                splitScreenController.startShortcut(str, str2, i7, bundle3, userHandle2);
                            }
                        }, false);
                        break;
                    case 10:
                        final PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt7 = parcel.readInt();
                        final Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                        final int readInt8 = parcel.readInt();
                        final Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                        final InstanceId instanceId2 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntent", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda8
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                PendingIntent pendingIntent2 = pendingIntent;
                                int i7 = readInt7;
                                Intent intent2 = intent;
                                int i8 = readInt8;
                                Bundle bundle4 = bundle3;
                                InstanceId instanceId3 = instanceId2;
                                SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                if (!stageCoordinator.isSplitScreenVisible() && !Transitions.ENABLE_SHELL_TRANSITIONS) {
                                    stageCoordinator.exitSplitScreen(null, 10);
                                }
                                SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
                                splitscreenEventLogger.mEnterSessionId = instanceId3;
                                splitscreenEventLogger.mEnterReason = 3;
                                splitScreenController.startIntent(pendingIntent2, i7, intent2, i8, bundle4, -1, 0);
                            }
                        }, false);
                        break;
                    case 11:
                        final int readInt9 = parcel.readInt();
                        Parcelable.Creator creator = Bundle.CREATOR;
                        final Bundle bundle4 = (Bundle) parcel.readTypedObject(creator);
                        final int readInt10 = parcel.readInt();
                        final Bundle bundle5 = (Bundle) parcel.readTypedObject(creator);
                        final int readInt11 = parcel.readInt();
                        final float readFloat = parcel.readFloat();
                        final RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                        final InstanceId instanceId3 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final SplitScreenController.ISplitScreenImpl iSplitScreenImpl2 = (SplitScreenController.ISplitScreenImpl) this;
                        if (readInt10 != -1) {
                            final int i7 = 1;
                            ExecutorUtils.executeRemoteCallWithTaskPermission(iSplitScreenImpl2.mController, "startTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    switch (i7) {
                                        case 0:
                                            int i8 = readInt9;
                                            Bundle bundle6 = bundle4;
                                            int i9 = readInt10;
                                            Bundle bundle7 = bundle5;
                                            int i10 = readInt11;
                                            float f = readFloat;
                                            RemoteAnimationAdapter remoteAnimationAdapter = remoteTransition;
                                            InstanceId instanceId4 = instanceId3;
                                            StageCoordinator stageCoordinator = ((SplitScreenController) obj2).mStageCoordinator;
                                            stageCoordinator.getClass();
                                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                            if (bundle6 == null) {
                                                bundle6 = new Bundle();
                                            }
                                            SideStage sideStage = stageCoordinator.mSideStage;
                                            if (i9 != -1) {
                                                StageCoordinator.addActivityOptions(bundle6, sideStage);
                                                windowContainerTransaction.startTask(i8, bundle6);
                                                stageCoordinator.mSplitRequest = new StageCoordinator.SplitRequest(stageCoordinator, i8, i9, i10);
                                                stageCoordinator.startWithLegacyTransition(windowContainerTransaction, i9, bundle7, i10, f, remoteAnimationAdapter, instanceId4);
                                                break;
                                            } else {
                                                if (stageCoordinator.mMainStage.containsTask(i8) || sideStage.containsTask(i8)) {
                                                    stageCoordinator.exitSplitScreen(null, 10);
                                                }
                                                ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle6);
                                                fromBundle.update(ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter));
                                                Bundle bundle8 = fromBundle.toBundle();
                                                StageCoordinator.addActivityOptions(bundle8, null);
                                                windowContainerTransaction.startTask(i8, bundle8);
                                                stageCoordinator.mSyncQueue.queue(windowContainerTransaction);
                                                break;
                                            }
                                        default:
                                            ((SplitScreenController) obj2).mStageCoordinator.startTasks(readInt9, bundle4, readInt10, bundle5, -1, null, readInt11, readFloat, 0, 0.5f, remoteTransition, instanceId3, -1, null);
                                            break;
                                    }
                                }
                            }, false);
                            break;
                        } else {
                            final SplitScreenController.CallerInfo callerInfo = new SplitScreenController.CallerInfo();
                            ExecutorUtils.executeRemoteCallWithTaskPermission(iSplitScreenImpl2.mController, "startTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    SplitScreenController.ISplitScreenImpl iSplitScreenImpl3 = SplitScreenController.ISplitScreenImpl.this;
                                    int i8 = readInt9;
                                    Bundle bundle6 = bundle4;
                                    int i9 = readInt10;
                                    Bundle bundle7 = bundle5;
                                    int i10 = readInt11;
                                    float f = readFloat;
                                    RemoteTransition remoteTransition2 = remoteTransition;
                                    InstanceId instanceId4 = instanceId3;
                                    SplitScreenController.CallerInfo callerInfo2 = callerInfo;
                                    SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                    iSplitScreenImpl3.getClass();
                                    splitScreenController.mStageCoordinator.startTasks(i8, bundle6, i9, bundle7, -1, null, i10, f, 0, 0.5f, remoteTransition2, instanceId4, (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || MultiWindowUtils.isInSubDisplay(iSplitScreenImpl3.mController.mContext)) ? -1 : 0, callerInfo2);
                                }
                            }, false);
                            break;
                        }
                    case 12:
                        final int readInt12 = parcel.readInt();
                        Parcelable.Creator creator2 = Bundle.CREATOR;
                        final Bundle bundle6 = (Bundle) parcel.readTypedObject(creator2);
                        final int readInt13 = parcel.readInt();
                        final Bundle bundle7 = (Bundle) parcel.readTypedObject(creator2);
                        final int readInt14 = parcel.readInt();
                        final float readFloat2 = parcel.readFloat();
                        final RemoteAnimationAdapter remoteAnimationAdapter = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                        final InstanceId instanceId4 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i8 = 0;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                switch (i8) {
                                    case 0:
                                        int i82 = readInt12;
                                        Bundle bundle62 = bundle6;
                                        int i9 = readInt13;
                                        Bundle bundle72 = bundle7;
                                        int i10 = readInt14;
                                        float f = readFloat2;
                                        RemoteAnimationAdapter remoteAnimationAdapter2 = remoteAnimationAdapter;
                                        InstanceId instanceId42 = instanceId4;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj2).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                        if (bundle62 == null) {
                                            bundle62 = new Bundle();
                                        }
                                        SideStage sideStage = stageCoordinator.mSideStage;
                                        if (i9 != -1) {
                                            StageCoordinator.addActivityOptions(bundle62, sideStage);
                                            windowContainerTransaction.startTask(i82, bundle62);
                                            stageCoordinator.mSplitRequest = new StageCoordinator.SplitRequest(stageCoordinator, i82, i9, i10);
                                            stageCoordinator.startWithLegacyTransition(windowContainerTransaction, i9, bundle72, i10, f, remoteAnimationAdapter2, instanceId42);
                                            break;
                                        } else {
                                            if (stageCoordinator.mMainStage.containsTask(i82) || sideStage.containsTask(i82)) {
                                                stageCoordinator.exitSplitScreen(null, 10);
                                            }
                                            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle62);
                                            fromBundle.update(ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter2));
                                            Bundle bundle8 = fromBundle.toBundle();
                                            StageCoordinator.addActivityOptions(bundle8, null);
                                            windowContainerTransaction.startTask(i82, bundle8);
                                            stageCoordinator.mSyncQueue.queue(windowContainerTransaction);
                                            break;
                                        }
                                    default:
                                        ((SplitScreenController) obj2).mStageCoordinator.startTasks(readInt12, bundle6, readInt13, bundle7, -1, null, readInt14, readFloat2, 0, 0.5f, remoteAnimationAdapter, instanceId4, -1, null);
                                        break;
                                }
                            }
                        }, false);
                        break;
                    case 13:
                        final PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt15 = parcel.readInt();
                        Parcelable.Creator creator3 = Bundle.CREATOR;
                        final Bundle bundle8 = (Bundle) parcel.readTypedObject(creator3);
                        final int readInt16 = parcel.readInt();
                        final Bundle bundle9 = (Bundle) parcel.readTypedObject(creator3);
                        final int readInt17 = parcel.readInt();
                        final float readFloat3 = parcel.readFloat();
                        final RemoteAnimationAdapter remoteAnimationAdapter2 = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                        final InstanceId instanceId5 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i9 = 0;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntentAndTaskWithLegacyTransition", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0090  */
                            /* JADX WARN: Removed duplicated region for block: B:19:0x009a  */
                            /* JADX WARN: Removed duplicated region for block: B:22:0x00a5  */
                            /* JADX WARN: Removed duplicated region for block: B:24:0x0096  */
                            /* JADX WARN: Removed duplicated region for block: B:43:0x0158  */
                            /* JADX WARN: Removed duplicated region for block: B:47:0x016d  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj2) {
                                Intent intent2;
                                int i10;
                                int i11;
                                Intent intent3;
                                switch (i9) {
                                    case 0:
                                        PendingIntent pendingIntent3 = pendingIntent2;
                                        int i12 = readInt15;
                                        Bundle bundle10 = bundle8;
                                        int i13 = readInt16;
                                        Bundle bundle11 = bundle9;
                                        int i14 = readInt17;
                                        float f = readFloat3;
                                        RemoteAnimationAdapter remoteAnimationAdapter3 = remoteAnimationAdapter2;
                                        InstanceId instanceId6 = instanceId5;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        String packageName = SplitScreenUtils.getPackageName(pendingIntent3);
                                        ShellTaskOrganizer shellTaskOrganizer = splitScreenController.mTaskOrganizer;
                                        String packageName2 = SplitScreenUtils.getPackageName(i13, shellTaskOrganizer);
                                        ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i13);
                                        if (SplitScreenUtils.samePackage(i12, runningTaskInfo != null ? runningTaskInfo.userId : -1, packageName, packageName2)) {
                                            if (splitScreenController.supportMultiInstancesSplit(packageName)) {
                                                Intent intent4 = new Intent();
                                                intent4.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                                intent3 = intent4;
                                                i11 = i13;
                                                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                                stageCoordinator.getClass();
                                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                                Bundle bundle12 = bundle10 != null ? new Bundle() : bundle10;
                                                if (i11 == -1) {
                                                    StageCoordinator.addActivityOptions(bundle12, stageCoordinator.mSideStage);
                                                    windowContainerTransaction.sendPendingIntent(pendingIntent3, intent3, bundle12);
                                                    stageCoordinator.mSplitRequest = new StageCoordinator.SplitRequest(stageCoordinator, i11, pendingIntent3.getIntent(), i14);
                                                    stageCoordinator.startWithLegacyTransition(windowContainerTransaction, i11, bundle11, i14, f, remoteAnimationAdapter3, instanceId6);
                                                    break;
                                                } else {
                                                    stageCoordinator.launchAsFullscreenWithRemoteAnimation(pendingIntent3, intent3, null, bundle12, remoteAnimationAdapter3, windowContainerTransaction);
                                                    break;
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                i13 = -1;
                                            }
                                        }
                                        i11 = i13;
                                        intent3 = null;
                                        StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                                        stageCoordinator2.getClass();
                                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                        if (bundle10 != null) {
                                        }
                                        if (i11 == -1) {
                                        }
                                    default:
                                        PendingIntent pendingIntent4 = pendingIntent2;
                                        int i15 = readInt15;
                                        Bundle bundle13 = bundle8;
                                        int i16 = readInt16;
                                        Bundle bundle14 = bundle9;
                                        int i17 = readInt17;
                                        float f2 = readFloat3;
                                        RemoteTransition remoteTransition2 = remoteAnimationAdapter2;
                                        InstanceId instanceId7 = instanceId5;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        String packageName3 = SplitScreenUtils.getPackageName(pendingIntent4);
                                        ShellTaskOrganizer shellTaskOrganizer2 = splitScreenController2.mTaskOrganizer;
                                        String packageName4 = SplitScreenUtils.getPackageName(i16, shellTaskOrganizer2);
                                        ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer2.getRunningTaskInfo(i16);
                                        if (SplitScreenUtils.samePackage(i15, runningTaskInfo2 != null ? runningTaskInfo2.userId : -1, packageName3, packageName4)) {
                                            if (splitScreenController2.supportMultiInstancesSplit(packageName3)) {
                                                intent2 = new Intent();
                                                intent2.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                                StageCoordinator stageCoordinator3 = splitScreenController2.mStageCoordinator;
                                                stageCoordinator3.getClass();
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                if (i16 == -1) {
                                                    stageCoordinator3.setSideStagePosition(windowContainerTransaction3, i17);
                                                    if (bundle13 == null) {
                                                        bundle13 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle13, stageCoordinator3.mSideStage);
                                                    windowContainerTransaction3.sendPendingIntent(pendingIntent4, intent2, bundle13);
                                                    stageCoordinator3.startWithTask(windowContainerTransaction3, i16, bundle14, f2, -1, null, 0.5f, 0, -1, remoteTransition2, instanceId7, true, true, null);
                                                    break;
                                                } else {
                                                    if (bundle13 == null) {
                                                        bundle13 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle13, null);
                                                    windowContainerTransaction3.sendPendingIntent(pendingIntent4, intent2, bundle13);
                                                    stageCoordinator3.mSplitTransitions.startFullscreenTransition(windowContainerTransaction3, remoteTransition2);
                                                    break;
                                                }
                                            } else {
                                                Optional optional = splitScreenController2.mRecentTasksOptional;
                                                if (optional.isPresent()) {
                                                    ((RecentTasksController) optional.get()).removeSplitPair(i16);
                                                }
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    i10 = 0;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                } else {
                                                    i10 = 0;
                                                }
                                                Toast.makeText(splitScreenController2.mContext, R.string.dock_multi_instances_not_supported_text, i10).show();
                                                i16 = -1;
                                            }
                                        }
                                        intent2 = null;
                                        StageCoordinator stageCoordinator32 = splitScreenController2.mStageCoordinator;
                                        stageCoordinator32.getClass();
                                        WindowContainerTransaction windowContainerTransaction32 = new WindowContainerTransaction();
                                        if (i16 == -1) {
                                        }
                                }
                            }
                        }, false);
                        break;
                    case 14:
                        final RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                        parcel.enforceNoDataAvail();
                        final RemoteAnimationTarget[][] remoteAnimationTargetArr2 = {null};
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "onGoingToRecentsLegacy", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                RemoteAnimationTarget[] remoteAnimationTargetArr3 = null;
                                switch (i5) {
                                    case 0:
                                        RemoteAnimationTarget[][] remoteAnimationTargetArr4 = (RemoteAnimationTarget[][]) remoteAnimationTargetArr2;
                                        RemoteAnimationTarget[] remoteAnimationTargetArr5 = (RemoteAnimationTarget[]) remoteAnimationTargetArr;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        if (!Transitions.ENABLE_SHELL_TRANSITIONS && splitScreenController.isSplitScreenVisible()) {
                                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                            StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                            stageCoordinator.mMainStage.evictInvisibleChildren(windowContainerTransaction);
                                            stageCoordinator.mSideStage.evictInvisibleChildren(windowContainerTransaction);
                                            splitScreenController.mSyncQueue.queue(windowContainerTransaction);
                                            splitScreenController.mStageCoordinator.mSplitBackgroundController.updateBackgroundVisibility(false, false);
                                            TransactionPool transactionPool = splitScreenController.mTransactionPool;
                                            SurfaceControl.Transaction acquire = transactionPool.acquire();
                                            SurfaceControl surfaceControl = splitScreenController.mGoingToRecentsTasksLayer;
                                            if (surfaceControl != null) {
                                                acquire.remove(surfaceControl);
                                            }
                                            splitScreenController.mGoingToRecentsTasksLayer = splitScreenController.reparentSplitTasksForAnimation(remoteAnimationTargetArr5, acquire, "SplitScreenController#onGoingToRecentsLegacy");
                                            acquire.apply();
                                            transactionPool.release(acquire);
                                            remoteAnimationTargetArr3 = new RemoteAnimationTarget[]{splitScreenController.mStageCoordinator.getDividerBarLegacyTarget()};
                                        }
                                        remoteAnimationTargetArr4[0] = remoteAnimationTargetArr3;
                                        return;
                                    case 1:
                                        RemoteAnimationTarget[][] remoteAnimationTargetArr6 = (RemoteAnimationTarget[][]) remoteAnimationTargetArr2;
                                        RemoteAnimationTarget[] remoteAnimationTargetArr7 = (RemoteAnimationTarget[]) remoteAnimationTargetArr;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                                            int i10 = 0;
                                            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr7) {
                                                if (remoteAnimationTarget.mode == 0) {
                                                    i10++;
                                                }
                                            }
                                            if (i10 >= 2) {
                                                TransactionPool transactionPool2 = splitScreenController2.mTransactionPool;
                                                SurfaceControl.Transaction acquire2 = transactionPool2.acquire();
                                                SurfaceControl surfaceControl2 = splitScreenController2.mStartingSplitTasksLayer;
                                                if (surfaceControl2 != null) {
                                                    acquire2.remove(surfaceControl2);
                                                }
                                                splitScreenController2.mStartingSplitTasksLayer = splitScreenController2.reparentSplitTasksForAnimation(remoteAnimationTargetArr7, acquire2, "SplitScreenController#onStartingSplitLegacy");
                                                acquire2.apply();
                                                transactionPool2.release(acquire2);
                                                try {
                                                    remoteAnimationTargetArr3 = new RemoteAnimationTarget[]{splitScreenController2.mStageCoordinator.getDividerBarLegacyTarget()};
                                                    for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr7) {
                                                        SurfaceControl surfaceControl3 = remoteAnimationTarget2.leash;
                                                        if (surfaceControl3 != null) {
                                                            surfaceControl3.release();
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr7) {
                                                        SurfaceControl surfaceControl4 = remoteAnimationTarget3.leash;
                                                        if (surfaceControl4 != null) {
                                                            surfaceControl4.release();
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            }
                                        }
                                        remoteAnimationTargetArr6[0] = remoteAnimationTargetArr3;
                                        return;
                                    default:
                                        ((SplitScreenController.ISplitScreenImpl) remoteAnimationTargetArr2).mListener.register((ISplitScreenListener) remoteAnimationTargetArr);
                                        return;
                                }
                            }
                        }, true);
                        RemoteAnimationTarget[] remoteAnimationTargetArr3 = remoteAnimationTargetArr2[0];
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(remoteAnimationTargetArr3, 1);
                        break;
                    case 15:
                        final RemoteAnimationTarget[] remoteAnimationTargetArr4 = (RemoteAnimationTarget[]) parcel.createTypedArray(RemoteAnimationTarget.CREATOR);
                        parcel.enforceNoDataAvail();
                        final RemoteAnimationTarget[][] remoteAnimationTargetArr5 = {null};
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "onStartingSplitLegacy", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                RemoteAnimationTarget[] remoteAnimationTargetArr32 = null;
                                switch (i3) {
                                    case 0:
                                        RemoteAnimationTarget[][] remoteAnimationTargetArr42 = (RemoteAnimationTarget[][]) remoteAnimationTargetArr5;
                                        RemoteAnimationTarget[] remoteAnimationTargetArr52 = (RemoteAnimationTarget[]) remoteAnimationTargetArr4;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        if (!Transitions.ENABLE_SHELL_TRANSITIONS && splitScreenController.isSplitScreenVisible()) {
                                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                            StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                            stageCoordinator.mMainStage.evictInvisibleChildren(windowContainerTransaction);
                                            stageCoordinator.mSideStage.evictInvisibleChildren(windowContainerTransaction);
                                            splitScreenController.mSyncQueue.queue(windowContainerTransaction);
                                            splitScreenController.mStageCoordinator.mSplitBackgroundController.updateBackgroundVisibility(false, false);
                                            TransactionPool transactionPool = splitScreenController.mTransactionPool;
                                            SurfaceControl.Transaction acquire = transactionPool.acquire();
                                            SurfaceControl surfaceControl = splitScreenController.mGoingToRecentsTasksLayer;
                                            if (surfaceControl != null) {
                                                acquire.remove(surfaceControl);
                                            }
                                            splitScreenController.mGoingToRecentsTasksLayer = splitScreenController.reparentSplitTasksForAnimation(remoteAnimationTargetArr52, acquire, "SplitScreenController#onGoingToRecentsLegacy");
                                            acquire.apply();
                                            transactionPool.release(acquire);
                                            remoteAnimationTargetArr32 = new RemoteAnimationTarget[]{splitScreenController.mStageCoordinator.getDividerBarLegacyTarget()};
                                        }
                                        remoteAnimationTargetArr42[0] = remoteAnimationTargetArr32;
                                        return;
                                    case 1:
                                        RemoteAnimationTarget[][] remoteAnimationTargetArr6 = (RemoteAnimationTarget[][]) remoteAnimationTargetArr5;
                                        RemoteAnimationTarget[] remoteAnimationTargetArr7 = (RemoteAnimationTarget[]) remoteAnimationTargetArr4;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                                            int i10 = 0;
                                            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr7) {
                                                if (remoteAnimationTarget.mode == 0) {
                                                    i10++;
                                                }
                                            }
                                            if (i10 >= 2) {
                                                TransactionPool transactionPool2 = splitScreenController2.mTransactionPool;
                                                SurfaceControl.Transaction acquire2 = transactionPool2.acquire();
                                                SurfaceControl surfaceControl2 = splitScreenController2.mStartingSplitTasksLayer;
                                                if (surfaceControl2 != null) {
                                                    acquire2.remove(surfaceControl2);
                                                }
                                                splitScreenController2.mStartingSplitTasksLayer = splitScreenController2.reparentSplitTasksForAnimation(remoteAnimationTargetArr7, acquire2, "SplitScreenController#onStartingSplitLegacy");
                                                acquire2.apply();
                                                transactionPool2.release(acquire2);
                                                try {
                                                    remoteAnimationTargetArr32 = new RemoteAnimationTarget[]{splitScreenController2.mStageCoordinator.getDividerBarLegacyTarget()};
                                                    for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr7) {
                                                        SurfaceControl surfaceControl3 = remoteAnimationTarget2.leash;
                                                        if (surfaceControl3 != null) {
                                                            surfaceControl3.release();
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr7) {
                                                        SurfaceControl surfaceControl4 = remoteAnimationTarget3.leash;
                                                        if (surfaceControl4 != null) {
                                                            surfaceControl4.release();
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            }
                                        }
                                        remoteAnimationTargetArr6[0] = remoteAnimationTargetArr32;
                                        return;
                                    default:
                                        ((SplitScreenController.ISplitScreenImpl) remoteAnimationTargetArr5).mListener.register((ISplitScreenListener) remoteAnimationTargetArr4);
                                        return;
                                }
                            }
                        }, true);
                        RemoteAnimationTarget[] remoteAnimationTargetArr6 = remoteAnimationTargetArr5[0];
                        parcel2.writeNoException();
                        parcel2.writeTypedArray(remoteAnimationTargetArr6, 1);
                        break;
                    case 16:
                        final ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                        Parcelable.Creator creator4 = Bundle.CREATOR;
                        final Bundle bundle10 = (Bundle) parcel.readTypedObject(creator4);
                        final int readInt18 = parcel.readInt();
                        final Bundle bundle11 = (Bundle) parcel.readTypedObject(creator4);
                        final int readInt19 = parcel.readInt();
                        final float readFloat4 = parcel.readFloat();
                        final RemoteAnimationAdapter remoteAnimationAdapter3 = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                        final InstanceId instanceId6 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i10 = 1;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcutAndTaskWithLegacyTransition", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                int i11;
                                switch (i10) {
                                    case 0:
                                        ShortcutInfo shortcutInfo2 = shortcutInfo;
                                        Bundle bundle12 = bundle10;
                                        int i12 = readInt18;
                                        Bundle bundle13 = bundle11;
                                        int i13 = readInt19;
                                        float f = readFloat4;
                                        RemoteTransition remoteTransition2 = remoteAnimationAdapter3;
                                        InstanceId instanceId7 = instanceId6;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        if (bundle12 == null) {
                                            bundle12 = new Bundle();
                                        }
                                        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle12);
                                        String str = shortcutInfo2.getPackage();
                                        ShellTaskOrganizer shellTaskOrganizer = splitScreenController.mTaskOrganizer;
                                        String packageName = SplitScreenUtils.getPackageName(i12, shellTaskOrganizer);
                                        int userId = shortcutInfo2.getUserId();
                                        ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i12);
                                        if (SplitScreenUtils.samePackage(userId, runningTaskInfo != null ? runningTaskInfo.userId : -1, str, packageName)) {
                                            if (splitScreenController.supportMultiInstancesSplit(str)) {
                                                fromBundle.setApplyMultipleTaskFlagForShortcut(true);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                            } else {
                                                Optional optional = splitScreenController.mRecentTasksOptional;
                                                if (optional.isPresent()) {
                                                    ((RecentTasksController) optional.get()).removeSplitPair(i12);
                                                }
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    i11 = 0;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                } else {
                                                    i11 = 0;
                                                }
                                                Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, i11).show();
                                                i12 = -1;
                                            }
                                        }
                                        StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                        stageCoordinator.getClass();
                                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                        Context context = stageCoordinator.mContext;
                                        if (i12 != -1) {
                                            stageCoordinator.setSideStagePosition(windowContainerTransaction, i13);
                                            StageCoordinator.addActivityOptions(bundle12, stageCoordinator.mSideStage);
                                            windowContainerTransaction.startShortcut(context.getPackageName(), shortcutInfo2, bundle12);
                                            stageCoordinator.startWithTask(windowContainerTransaction, i12, bundle13, f, -1, null, 0.5f, 0, -1, remoteTransition2, instanceId7, true, true, null);
                                            break;
                                        } else {
                                            StageCoordinator.addActivityOptions(bundle12, null);
                                            windowContainerTransaction.startShortcut(context.getPackageName(), shortcutInfo2, bundle12);
                                            stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition2);
                                            break;
                                        }
                                    default:
                                        ShortcutInfo shortcutInfo3 = shortcutInfo;
                                        Bundle bundle14 = bundle10;
                                        int i14 = readInt18;
                                        Bundle bundle15 = bundle11;
                                        int i15 = readInt19;
                                        float f2 = readFloat4;
                                        RemoteAnimationAdapter remoteAnimationAdapter4 = remoteAnimationAdapter3;
                                        InstanceId instanceId8 = instanceId6;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        if (bundle14 == null) {
                                            bundle14 = new Bundle();
                                        }
                                        ActivityOptions fromBundle2 = ActivityOptions.fromBundle(bundle14);
                                        String str2 = shortcutInfo3.getPackage();
                                        ShellTaskOrganizer shellTaskOrganizer2 = splitScreenController2.mTaskOrganizer;
                                        String packageName2 = SplitScreenUtils.getPackageName(i14, shellTaskOrganizer2);
                                        int userId2 = shortcutInfo3.getUserId();
                                        ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer2.getRunningTaskInfo(i14);
                                        if (SplitScreenUtils.samePackage(userId2, runningTaskInfo2 != null ? runningTaskInfo2.userId : -1, str2, packageName2)) {
                                            if (splitScreenController2.supportMultiInstancesSplit(shortcutInfo3.getPackage())) {
                                                fromBundle2.setApplyMultipleTaskFlagForShortcut(true);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController2.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                i14 = -1;
                                            }
                                        }
                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                        Bundle bundle16 = fromBundle2.toBundle();
                                        stageCoordinator2.getClass();
                                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                        if (bundle16 == null) {
                                            bundle16 = new Bundle();
                                        }
                                        if (i14 != -1) {
                                            StageCoordinator.addActivityOptions(bundle16, stageCoordinator2.mSideStage);
                                            windowContainerTransaction2.startShortcut(stageCoordinator2.mContext.getPackageName(), shortcutInfo3, bundle16);
                                            stageCoordinator2.startWithLegacyTransition(windowContainerTransaction2, i14, bundle15, i15, f2, remoteAnimationAdapter4, instanceId8);
                                            break;
                                        } else {
                                            stageCoordinator2.launchAsFullscreenWithRemoteAnimation(null, null, shortcutInfo3, bundle16, remoteAnimationAdapter4, windowContainerTransaction2);
                                            break;
                                        }
                                }
                            }
                        }, false);
                        break;
                    case 17:
                        final PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt20 = parcel.readInt();
                        Parcelable.Creator creator5 = Bundle.CREATOR;
                        final Bundle bundle12 = (Bundle) parcel.readTypedObject(creator5);
                        final int readInt21 = parcel.readInt();
                        final Bundle bundle13 = (Bundle) parcel.readTypedObject(creator5);
                        final int readInt22 = parcel.readInt();
                        final float readFloat5 = parcel.readFloat();
                        final RemoteTransition remoteTransition2 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                        final InstanceId instanceId7 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i11 = 1;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntentAndTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0090  */
                            /* JADX WARN: Removed duplicated region for block: B:19:0x009a  */
                            /* JADX WARN: Removed duplicated region for block: B:22:0x00a5  */
                            /* JADX WARN: Removed duplicated region for block: B:24:0x0096  */
                            /* JADX WARN: Removed duplicated region for block: B:43:0x0158  */
                            /* JADX WARN: Removed duplicated region for block: B:47:0x016d  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj2) {
                                Intent intent2;
                                int i102;
                                int i112;
                                Intent intent3;
                                switch (i11) {
                                    case 0:
                                        PendingIntent pendingIntent32 = pendingIntent3;
                                        int i12 = readInt20;
                                        Bundle bundle102 = bundle12;
                                        int i13 = readInt21;
                                        Bundle bundle112 = bundle13;
                                        int i14 = readInt22;
                                        float f = readFloat5;
                                        RemoteAnimationAdapter remoteAnimationAdapter32 = remoteTransition2;
                                        InstanceId instanceId62 = instanceId7;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        String packageName = SplitScreenUtils.getPackageName(pendingIntent32);
                                        ShellTaskOrganizer shellTaskOrganizer = splitScreenController.mTaskOrganizer;
                                        String packageName2 = SplitScreenUtils.getPackageName(i13, shellTaskOrganizer);
                                        ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i13);
                                        if (SplitScreenUtils.samePackage(i12, runningTaskInfo != null ? runningTaskInfo.userId : -1, packageName, packageName2)) {
                                            if (splitScreenController.supportMultiInstancesSplit(packageName)) {
                                                Intent intent4 = new Intent();
                                                intent4.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                                intent3 = intent4;
                                                i112 = i13;
                                                StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                                                stageCoordinator2.getClass();
                                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                                Bundle bundle122 = bundle102 != null ? new Bundle() : bundle102;
                                                if (i112 == -1) {
                                                    StageCoordinator.addActivityOptions(bundle122, stageCoordinator2.mSideStage);
                                                    windowContainerTransaction2.sendPendingIntent(pendingIntent32, intent3, bundle122);
                                                    stageCoordinator2.mSplitRequest = new StageCoordinator.SplitRequest(stageCoordinator2, i112, pendingIntent32.getIntent(), i14);
                                                    stageCoordinator2.startWithLegacyTransition(windowContainerTransaction2, i112, bundle112, i14, f, remoteAnimationAdapter32, instanceId62);
                                                    break;
                                                } else {
                                                    stageCoordinator2.launchAsFullscreenWithRemoteAnimation(pendingIntent32, intent3, null, bundle122, remoteAnimationAdapter32, windowContainerTransaction2);
                                                    break;
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                i13 = -1;
                                            }
                                        }
                                        i112 = i13;
                                        intent3 = null;
                                        StageCoordinator stageCoordinator22 = splitScreenController.mStageCoordinator;
                                        stageCoordinator22.getClass();
                                        WindowContainerTransaction windowContainerTransaction22 = new WindowContainerTransaction();
                                        if (bundle102 != null) {
                                        }
                                        if (i112 == -1) {
                                        }
                                    default:
                                        PendingIntent pendingIntent4 = pendingIntent3;
                                        int i15 = readInt20;
                                        Bundle bundle132 = bundle12;
                                        int i16 = readInt21;
                                        Bundle bundle14 = bundle13;
                                        int i17 = readInt22;
                                        float f2 = readFloat5;
                                        RemoteTransition remoteTransition22 = remoteTransition2;
                                        InstanceId instanceId72 = instanceId7;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        String packageName3 = SplitScreenUtils.getPackageName(pendingIntent4);
                                        ShellTaskOrganizer shellTaskOrganizer2 = splitScreenController2.mTaskOrganizer;
                                        String packageName4 = SplitScreenUtils.getPackageName(i16, shellTaskOrganizer2);
                                        ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer2.getRunningTaskInfo(i16);
                                        if (SplitScreenUtils.samePackage(i15, runningTaskInfo2 != null ? runningTaskInfo2.userId : -1, packageName3, packageName4)) {
                                            if (splitScreenController2.supportMultiInstancesSplit(packageName3)) {
                                                intent2 = new Intent();
                                                intent2.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                                StageCoordinator stageCoordinator32 = splitScreenController2.mStageCoordinator;
                                                stageCoordinator32.getClass();
                                                WindowContainerTransaction windowContainerTransaction32 = new WindowContainerTransaction();
                                                if (i16 == -1) {
                                                    stageCoordinator32.setSideStagePosition(windowContainerTransaction32, i17);
                                                    if (bundle132 == null) {
                                                        bundle132 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle132, stageCoordinator32.mSideStage);
                                                    windowContainerTransaction32.sendPendingIntent(pendingIntent4, intent2, bundle132);
                                                    stageCoordinator32.startWithTask(windowContainerTransaction32, i16, bundle14, f2, -1, null, 0.5f, 0, -1, remoteTransition22, instanceId72, true, true, null);
                                                    break;
                                                } else {
                                                    if (bundle132 == null) {
                                                        bundle132 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle132, null);
                                                    windowContainerTransaction32.sendPendingIntent(pendingIntent4, intent2, bundle132);
                                                    stageCoordinator32.mSplitTransitions.startFullscreenTransition(windowContainerTransaction32, remoteTransition22);
                                                    break;
                                                }
                                            } else {
                                                Optional optional = splitScreenController2.mRecentTasksOptional;
                                                if (optional.isPresent()) {
                                                    ((RecentTasksController) optional.get()).removeSplitPair(i16);
                                                }
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    i102 = 0;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                } else {
                                                    i102 = 0;
                                                }
                                                Toast.makeText(splitScreenController2.mContext, R.string.dock_multi_instances_not_supported_text, i102).show();
                                                i16 = -1;
                                            }
                                        }
                                        intent2 = null;
                                        StageCoordinator stageCoordinator322 = splitScreenController2.mStageCoordinator;
                                        stageCoordinator322.getClass();
                                        WindowContainerTransaction windowContainerTransaction322 = new WindowContainerTransaction();
                                        if (i16 == -1) {
                                        }
                                }
                            }
                        }, false);
                        break;
                    case 18:
                        final ShortcutInfo shortcutInfo2 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                        Parcelable.Creator creator6 = Bundle.CREATOR;
                        final Bundle bundle14 = (Bundle) parcel.readTypedObject(creator6);
                        final int readInt23 = parcel.readInt();
                        final Bundle bundle15 = (Bundle) parcel.readTypedObject(creator6);
                        final int readInt24 = parcel.readInt();
                        final float readFloat6 = parcel.readFloat();
                        final RemoteTransition remoteTransition3 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                        final InstanceId instanceId8 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i12 = 0;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcutAndTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                int i112;
                                switch (i12) {
                                    case 0:
                                        ShortcutInfo shortcutInfo22 = shortcutInfo2;
                                        Bundle bundle122 = bundle14;
                                        int i122 = readInt23;
                                        Bundle bundle132 = bundle15;
                                        int i13 = readInt24;
                                        float f = readFloat6;
                                        RemoteTransition remoteTransition22 = remoteTransition3;
                                        InstanceId instanceId72 = instanceId8;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        if (bundle122 == null) {
                                            bundle122 = new Bundle();
                                        }
                                        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle122);
                                        String str = shortcutInfo22.getPackage();
                                        ShellTaskOrganizer shellTaskOrganizer = splitScreenController.mTaskOrganizer;
                                        String packageName = SplitScreenUtils.getPackageName(i122, shellTaskOrganizer);
                                        int userId = shortcutInfo22.getUserId();
                                        ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i122);
                                        if (SplitScreenUtils.samePackage(userId, runningTaskInfo != null ? runningTaskInfo.userId : -1, str, packageName)) {
                                            if (splitScreenController.supportMultiInstancesSplit(str)) {
                                                fromBundle.setApplyMultipleTaskFlagForShortcut(true);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                            } else {
                                                Optional optional = splitScreenController.mRecentTasksOptional;
                                                if (optional.isPresent()) {
                                                    ((RecentTasksController) optional.get()).removeSplitPair(i122);
                                                }
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    i112 = 0;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                } else {
                                                    i112 = 0;
                                                }
                                                Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, i112).show();
                                                i122 = -1;
                                            }
                                        }
                                        StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                        stageCoordinator.getClass();
                                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                        Context context = stageCoordinator.mContext;
                                        if (i122 != -1) {
                                            stageCoordinator.setSideStagePosition(windowContainerTransaction, i13);
                                            StageCoordinator.addActivityOptions(bundle122, stageCoordinator.mSideStage);
                                            windowContainerTransaction.startShortcut(context.getPackageName(), shortcutInfo22, bundle122);
                                            stageCoordinator.startWithTask(windowContainerTransaction, i122, bundle132, f, -1, null, 0.5f, 0, -1, remoteTransition22, instanceId72, true, true, null);
                                            break;
                                        } else {
                                            StageCoordinator.addActivityOptions(bundle122, null);
                                            windowContainerTransaction.startShortcut(context.getPackageName(), shortcutInfo22, bundle122);
                                            stageCoordinator.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition22);
                                            break;
                                        }
                                    default:
                                        ShortcutInfo shortcutInfo3 = shortcutInfo2;
                                        Bundle bundle142 = bundle14;
                                        int i14 = readInt23;
                                        Bundle bundle152 = bundle15;
                                        int i15 = readInt24;
                                        float f2 = readFloat6;
                                        RemoteAnimationAdapter remoteAnimationAdapter4 = remoteTransition3;
                                        InstanceId instanceId82 = instanceId8;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        if (bundle142 == null) {
                                            bundle142 = new Bundle();
                                        }
                                        ActivityOptions fromBundle2 = ActivityOptions.fromBundle(bundle142);
                                        String str2 = shortcutInfo3.getPackage();
                                        ShellTaskOrganizer shellTaskOrganizer2 = splitScreenController2.mTaskOrganizer;
                                        String packageName2 = SplitScreenUtils.getPackageName(i14, shellTaskOrganizer2);
                                        int userId2 = shortcutInfo3.getUserId();
                                        ActivityManager.RunningTaskInfo runningTaskInfo2 = shellTaskOrganizer2.getRunningTaskInfo(i14);
                                        if (SplitScreenUtils.samePackage(userId2, runningTaskInfo2 != null ? runningTaskInfo2.userId : -1, str2, packageName2)) {
                                            if (splitScreenController2.supportMultiInstancesSplit(shortcutInfo3.getPackage())) {
                                                fromBundle2.setApplyMultipleTaskFlagForShortcut(true);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController2.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                i14 = -1;
                                            }
                                        }
                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                        Bundle bundle16 = fromBundle2.toBundle();
                                        stageCoordinator2.getClass();
                                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                        if (bundle16 == null) {
                                            bundle16 = new Bundle();
                                        }
                                        if (i14 != -1) {
                                            StageCoordinator.addActivityOptions(bundle16, stageCoordinator2.mSideStage);
                                            windowContainerTransaction2.startShortcut(stageCoordinator2.mContext.getPackageName(), shortcutInfo3, bundle16);
                                            stageCoordinator2.startWithLegacyTransition(windowContainerTransaction2, i14, bundle152, i15, f2, remoteAnimationAdapter4, instanceId82);
                                            break;
                                        } else {
                                            stageCoordinator2.launchAsFullscreenWithRemoteAnimation(null, null, shortcutInfo3, bundle16, remoteAnimationAdapter4, windowContainerTransaction2);
                                            break;
                                        }
                                }
                            }
                        }, false);
                        break;
                    case 19:
                        final PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt25 = parcel.readInt();
                        final ShortcutInfo shortcutInfo3 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                        Parcelable.Creator creator7 = Bundle.CREATOR;
                        final Bundle bundle16 = (Bundle) parcel.readTypedObject(creator7);
                        final PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt26 = parcel.readInt();
                        final ShortcutInfo shortcutInfo4 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                        final Bundle bundle17 = (Bundle) parcel.readTypedObject(creator7);
                        final int readInt27 = parcel.readInt();
                        final float readFloat7 = parcel.readFloat();
                        final RemoteAnimationAdapter remoteAnimationAdapter4 = (RemoteAnimationAdapter) parcel.readTypedObject(RemoteAnimationAdapter.CREATOR);
                        final InstanceId instanceId9 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i13 = 0;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntentsWithLegacyTransition", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda7
                            /* JADX WARN: Removed duplicated region for block: B:13:0x009c  */
                            /* JADX WARN: Removed duplicated region for block: B:15:0x00a6  */
                            /* JADX WARN: Removed duplicated region for block: B:18:0x00af  */
                            /* JADX WARN: Removed duplicated region for block: B:24:0x00a3  */
                            /* JADX WARN: Removed duplicated region for block: B:41:0x0172  */
                            /* JADX WARN: Removed duplicated region for block: B:49:0x0192  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj2) {
                                Intent intent2;
                                Intent intent3;
                                Intent intent4;
                                PendingIntent pendingIntent6;
                                Intent intent5;
                                Intent intent6;
                                Intent intent7;
                                switch (i13) {
                                    case 0:
                                        PendingIntent pendingIntent7 = pendingIntent4;
                                        int i14 = readInt25;
                                        ShortcutInfo shortcutInfo5 = shortcutInfo3;
                                        Bundle bundle18 = bundle16;
                                        PendingIntent pendingIntent8 = pendingIntent5;
                                        int i15 = readInt26;
                                        ShortcutInfo shortcutInfo6 = shortcutInfo4;
                                        Bundle bundle19 = bundle17;
                                        int i16 = readInt27;
                                        float f = readFloat7;
                                        RemoteAnimationAdapter remoteAnimationAdapter5 = remoteAnimationAdapter4;
                                        InstanceId instanceId10 = instanceId9;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        String packageName = SplitScreenUtils.getPackageName(pendingIntent7);
                                        if (SplitScreenUtils.samePackage(i14, i15, packageName, SplitScreenUtils.getPackageName(pendingIntent8))) {
                                            if (splitScreenController.supportMultiInstancesSplit(packageName)) {
                                                Intent intent8 = new Intent();
                                                intent8.addFlags(134217728);
                                                Intent intent9 = new Intent();
                                                intent9.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    intent7 = intent8;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                } else {
                                                    intent7 = intent8;
                                                }
                                                intent6 = intent9;
                                                pendingIntent6 = pendingIntent8;
                                                intent5 = intent7;
                                                StageCoordinator stageCoordinator = splitScreenController.mStageCoordinator;
                                                stageCoordinator.getClass();
                                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                                Bundle bundle20 = bundle18 != null ? new Bundle() : bundle18;
                                                if (pendingIntent6 == null) {
                                                    StageCoordinator.addActivityOptions(bundle20, stageCoordinator.mSideStage);
                                                    if (shortcutInfo5 != null) {
                                                        windowContainerTransaction.startShortcut(stageCoordinator.mContext.getPackageName(), shortcutInfo5, bundle20);
                                                    } else {
                                                        windowContainerTransaction.sendPendingIntent(pendingIntent7, intent5, bundle20);
                                                        stageCoordinator.mSplitRequest = new StageCoordinator.SplitRequest(stageCoordinator, pendingIntent7.getIntent(), pendingIntent6.getIntent(), i16);
                                                    }
                                                    stageCoordinator.startWithLegacyTransition(windowContainerTransaction, -1, pendingIntent6, intent6, shortcutInfo6, bundle19, i16, f, remoteAnimationAdapter5, instanceId10);
                                                    break;
                                                } else {
                                                    stageCoordinator.launchAsFullscreenWithRemoteAnimation(pendingIntent7, intent5, shortcutInfo5, bundle20, remoteAnimationAdapter5, windowContainerTransaction);
                                                    break;
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                pendingIntent8 = null;
                                            }
                                        }
                                        pendingIntent6 = pendingIntent8;
                                        intent5 = null;
                                        intent6 = null;
                                        StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                                        stageCoordinator2.getClass();
                                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                        if (bundle18 != null) {
                                        }
                                        if (pendingIntent6 == null) {
                                        }
                                    default:
                                        PendingIntent pendingIntent9 = pendingIntent4;
                                        int i17 = readInt25;
                                        ShortcutInfo shortcutInfo7 = shortcutInfo3;
                                        Bundle bundle21 = bundle16;
                                        PendingIntent pendingIntent10 = pendingIntent5;
                                        int i18 = readInt26;
                                        ShortcutInfo shortcutInfo8 = shortcutInfo4;
                                        Bundle bundle22 = bundle17;
                                        int i19 = readInt27;
                                        float f2 = readFloat7;
                                        RemoteTransition remoteTransition4 = remoteAnimationAdapter4;
                                        InstanceId instanceId11 = instanceId9;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        String packageName2 = SplitScreenUtils.getPackageName(pendingIntent9);
                                        if (SplitScreenUtils.samePackage(i17, i18, packageName2, SplitScreenUtils.getPackageName(pendingIntent10))) {
                                            if (splitScreenController2.supportMultiInstancesSplit(packageName2)) {
                                                Intent intent10 = new Intent();
                                                intent10.addFlags(134217728);
                                                intent3 = new Intent();
                                                intent3.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    intent4 = intent10;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                } else {
                                                    intent4 = intent10;
                                                }
                                                intent2 = intent4;
                                                StageCoordinator stageCoordinator3 = splitScreenController2.mStageCoordinator;
                                                stageCoordinator3.getClass();
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                Context context = stageCoordinator3.mContext;
                                                if (pendingIntent10 == null) {
                                                    MainStage mainStage = stageCoordinator3.mMainStage;
                                                    if (!mainStage.mIsActive) {
                                                        mainStage.activate(windowContainerTransaction3, false);
                                                    }
                                                    stageCoordinator3.mSplitLayout.setDivideRatio(f2, true, true);
                                                    stageCoordinator3.updateWindowBounds(stageCoordinator3.mSplitLayout, windowContainerTransaction3, false);
                                                    windowContainerTransaction3.reorder(stageCoordinator3.mRootTaskInfo.token, true);
                                                    windowContainerTransaction3.setReparentLeafTaskIfRelaunch(stageCoordinator3.mRootTaskInfo.token, false);
                                                    stageCoordinator3.setRootForceTranslucent(windowContainerTransaction3, false);
                                                    stageCoordinator3.setSideStagePosition(windowContainerTransaction3, i19);
                                                    if (bundle21 == null) {
                                                        bundle21 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle21, stageCoordinator3.mSideStage);
                                                    if (shortcutInfo7 != null) {
                                                        windowContainerTransaction3.startShortcut(context.getPackageName(), shortcutInfo7, bundle21);
                                                    } else {
                                                        windowContainerTransaction3.sendPendingIntent(pendingIntent9, intent2, bundle21);
                                                    }
                                                    if (bundle22 == null) {
                                                        bundle22 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle22, mainStage);
                                                    if (shortcutInfo8 != null) {
                                                        windowContainerTransaction3.startShortcut(context.getPackageName(), shortcutInfo8, bundle22);
                                                    } else {
                                                        windowContainerTransaction3.sendPendingIntent(pendingIntent10, intent3, bundle22);
                                                    }
                                                    stageCoordinator3.mSplitTransitions.startEnterTransition(windowContainerTransaction3, remoteTransition4, stageCoordinator3, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, false);
                                                    if (instanceId11 != null) {
                                                        SplitscreenEventLogger splitscreenEventLogger = stageCoordinator3.mLogger;
                                                        splitscreenEventLogger.mEnterSessionId = instanceId11;
                                                        splitscreenEventLogger.mEnterReason = 3;
                                                        break;
                                                    }
                                                } else {
                                                    if (bundle21 == null) {
                                                        bundle21 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle21, null);
                                                    if (shortcutInfo7 != null) {
                                                        windowContainerTransaction3.startShortcut(context.getPackageName(), shortcutInfo7, bundle21);
                                                    } else {
                                                        windowContainerTransaction3.sendPendingIntent(pendingIntent9, intent2, bundle21);
                                                    }
                                                    stageCoordinator3.mSplitTransitions.startFullscreenTransition(windowContainerTransaction3, remoteTransition4);
                                                    break;
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController2.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                pendingIntent10 = null;
                                            }
                                        }
                                        intent2 = null;
                                        intent3 = null;
                                        StageCoordinator stageCoordinator32 = splitScreenController2.mStageCoordinator;
                                        stageCoordinator32.getClass();
                                        WindowContainerTransaction windowContainerTransaction32 = new WindowContainerTransaction();
                                        Context context2 = stageCoordinator32.mContext;
                                        if (pendingIntent10 == null) {
                                        }
                                        break;
                                }
                            }
                        }, false);
                        break;
                    case 20:
                        final PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt28 = parcel.readInt();
                        final ShortcutInfo shortcutInfo5 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                        Parcelable.Creator creator8 = Bundle.CREATOR;
                        final Bundle bundle18 = (Bundle) parcel.readTypedObject(creator8);
                        final PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                        final int readInt29 = parcel.readInt();
                        final ShortcutInfo shortcutInfo6 = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
                        final Bundle bundle19 = (Bundle) parcel.readTypedObject(creator8);
                        final int readInt30 = parcel.readInt();
                        final float readFloat8 = parcel.readFloat();
                        final RemoteTransition remoteTransition4 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                        final InstanceId instanceId10 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        final int i14 = 1;
                        ExecutorUtils.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntents", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda7
                            /* JADX WARN: Removed duplicated region for block: B:13:0x009c  */
                            /* JADX WARN: Removed duplicated region for block: B:15:0x00a6  */
                            /* JADX WARN: Removed duplicated region for block: B:18:0x00af  */
                            /* JADX WARN: Removed duplicated region for block: B:24:0x00a3  */
                            /* JADX WARN: Removed duplicated region for block: B:41:0x0172  */
                            /* JADX WARN: Removed duplicated region for block: B:49:0x0192  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj2) {
                                Intent intent2;
                                Intent intent3;
                                Intent intent4;
                                PendingIntent pendingIntent62;
                                Intent intent5;
                                Intent intent6;
                                Intent intent7;
                                switch (i14) {
                                    case 0:
                                        PendingIntent pendingIntent72 = pendingIntent6;
                                        int i142 = readInt28;
                                        ShortcutInfo shortcutInfo52 = shortcutInfo5;
                                        Bundle bundle182 = bundle18;
                                        PendingIntent pendingIntent8 = pendingIntent7;
                                        int i15 = readInt29;
                                        ShortcutInfo shortcutInfo62 = shortcutInfo6;
                                        Bundle bundle192 = bundle19;
                                        int i16 = readInt30;
                                        float f = readFloat8;
                                        RemoteAnimationAdapter remoteAnimationAdapter5 = remoteTransition4;
                                        InstanceId instanceId102 = instanceId10;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj2;
                                        splitScreenController.getClass();
                                        String packageName = SplitScreenUtils.getPackageName(pendingIntent72);
                                        if (SplitScreenUtils.samePackage(i142, i15, packageName, SplitScreenUtils.getPackageName(pendingIntent8))) {
                                            if (splitScreenController.supportMultiInstancesSplit(packageName)) {
                                                Intent intent8 = new Intent();
                                                intent8.addFlags(134217728);
                                                Intent intent9 = new Intent();
                                                intent9.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    intent7 = intent8;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                } else {
                                                    intent7 = intent8;
                                                }
                                                intent6 = intent9;
                                                pendingIntent62 = pendingIntent8;
                                                intent5 = intent7;
                                                StageCoordinator stageCoordinator2 = splitScreenController.mStageCoordinator;
                                                stageCoordinator2.getClass();
                                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                                Bundle bundle20 = bundle182 != null ? new Bundle() : bundle182;
                                                if (pendingIntent62 == null) {
                                                    StageCoordinator.addActivityOptions(bundle20, stageCoordinator2.mSideStage);
                                                    if (shortcutInfo52 != null) {
                                                        windowContainerTransaction2.startShortcut(stageCoordinator2.mContext.getPackageName(), shortcutInfo52, bundle20);
                                                    } else {
                                                        windowContainerTransaction2.sendPendingIntent(pendingIntent72, intent5, bundle20);
                                                        stageCoordinator2.mSplitRequest = new StageCoordinator.SplitRequest(stageCoordinator2, pendingIntent72.getIntent(), pendingIntent62.getIntent(), i16);
                                                    }
                                                    stageCoordinator2.startWithLegacyTransition(windowContainerTransaction2, -1, pendingIntent62, intent6, shortcutInfo62, bundle192, i16, f, remoteAnimationAdapter5, instanceId102);
                                                    break;
                                                } else {
                                                    stageCoordinator2.launchAsFullscreenWithRemoteAnimation(pendingIntent72, intent5, shortcutInfo52, bundle20, remoteAnimationAdapter5, windowContainerTransaction2);
                                                    break;
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                pendingIntent8 = null;
                                            }
                                        }
                                        pendingIntent62 = pendingIntent8;
                                        intent5 = null;
                                        intent6 = null;
                                        StageCoordinator stageCoordinator22 = splitScreenController.mStageCoordinator;
                                        stageCoordinator22.getClass();
                                        WindowContainerTransaction windowContainerTransaction22 = new WindowContainerTransaction();
                                        if (bundle182 != null) {
                                        }
                                        if (pendingIntent62 == null) {
                                        }
                                    default:
                                        PendingIntent pendingIntent9 = pendingIntent6;
                                        int i17 = readInt28;
                                        ShortcutInfo shortcutInfo7 = shortcutInfo5;
                                        Bundle bundle21 = bundle18;
                                        PendingIntent pendingIntent10 = pendingIntent7;
                                        int i18 = readInt29;
                                        ShortcutInfo shortcutInfo8 = shortcutInfo6;
                                        Bundle bundle22 = bundle19;
                                        int i19 = readInt30;
                                        float f2 = readFloat8;
                                        RemoteTransition remoteTransition42 = remoteTransition4;
                                        InstanceId instanceId11 = instanceId10;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                                        splitScreenController2.getClass();
                                        String packageName2 = SplitScreenUtils.getPackageName(pendingIntent9);
                                        if (SplitScreenUtils.samePackage(i17, i18, packageName2, SplitScreenUtils.getPackageName(pendingIntent10))) {
                                            if (splitScreenController2.supportMultiInstancesSplit(packageName2)) {
                                                Intent intent10 = new Intent();
                                                intent10.addFlags(134217728);
                                                intent3 = new Intent();
                                                intent3.addFlags(134217728);
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    intent4 = intent10;
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1585164509, 0, "Adding MULTIPLE_TASK", null);
                                                } else {
                                                    intent4 = intent10;
                                                }
                                                intent2 = intent4;
                                                StageCoordinator stageCoordinator32 = splitScreenController2.mStageCoordinator;
                                                stageCoordinator32.getClass();
                                                WindowContainerTransaction windowContainerTransaction32 = new WindowContainerTransaction();
                                                Context context2 = stageCoordinator32.mContext;
                                                if (pendingIntent10 == null) {
                                                    MainStage mainStage = stageCoordinator32.mMainStage;
                                                    if (!mainStage.mIsActive) {
                                                        mainStage.activate(windowContainerTransaction32, false);
                                                    }
                                                    stageCoordinator32.mSplitLayout.setDivideRatio(f2, true, true);
                                                    stageCoordinator32.updateWindowBounds(stageCoordinator32.mSplitLayout, windowContainerTransaction32, false);
                                                    windowContainerTransaction32.reorder(stageCoordinator32.mRootTaskInfo.token, true);
                                                    windowContainerTransaction32.setReparentLeafTaskIfRelaunch(stageCoordinator32.mRootTaskInfo.token, false);
                                                    stageCoordinator32.setRootForceTranslucent(windowContainerTransaction32, false);
                                                    stageCoordinator32.setSideStagePosition(windowContainerTransaction32, i19);
                                                    if (bundle21 == null) {
                                                        bundle21 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle21, stageCoordinator32.mSideStage);
                                                    if (shortcutInfo7 != null) {
                                                        windowContainerTransaction32.startShortcut(context2.getPackageName(), shortcutInfo7, bundle21);
                                                    } else {
                                                        windowContainerTransaction32.sendPendingIntent(pendingIntent9, intent2, bundle21);
                                                    }
                                                    if (bundle22 == null) {
                                                        bundle22 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle22, mainStage);
                                                    if (shortcutInfo8 != null) {
                                                        windowContainerTransaction32.startShortcut(context2.getPackageName(), shortcutInfo8, bundle22);
                                                    } else {
                                                        windowContainerTransaction32.sendPendingIntent(pendingIntent10, intent3, bundle22);
                                                    }
                                                    stageCoordinator32.mSplitTransitions.startEnterTransition(windowContainerTransaction32, remoteTransition42, stageCoordinator32, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, false);
                                                    if (instanceId11 != null) {
                                                        SplitscreenEventLogger splitscreenEventLogger = stageCoordinator32.mLogger;
                                                        splitscreenEventLogger.mEnterSessionId = instanceId11;
                                                        splitscreenEventLogger.mEnterReason = 3;
                                                        break;
                                                    }
                                                } else {
                                                    if (bundle21 == null) {
                                                        bundle21 = new Bundle();
                                                    }
                                                    StageCoordinator.addActivityOptions(bundle21, null);
                                                    if (shortcutInfo7 != null) {
                                                        windowContainerTransaction32.startShortcut(context2.getPackageName(), shortcutInfo7, bundle21);
                                                    } else {
                                                        windowContainerTransaction32.sendPendingIntent(pendingIntent9, intent2, bundle21);
                                                    }
                                                    stageCoordinator32.mSplitTransitions.startFullscreenTransition(windowContainerTransaction32, remoteTransition42);
                                                    break;
                                                }
                                            } else {
                                                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                                                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 791232520, 0, "Cancel entering split as not supporting multi-instances", null);
                                                }
                                                Toast.makeText(splitScreenController2.mContext, R.string.dock_multi_instances_not_supported_text, 0).show();
                                                pendingIntent10 = null;
                                            }
                                        }
                                        intent2 = null;
                                        intent3 = null;
                                        StageCoordinator stageCoordinator322 = splitScreenController2.mStageCoordinator;
                                        stageCoordinator322.getClass();
                                        WindowContainerTransaction windowContainerTransaction322 = new WindowContainerTransaction();
                                        Context context22 = stageCoordinator322.mContext;
                                        if (pendingIntent10 == null) {
                                        }
                                        break;
                                }
                            }
                        }, false);
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                final int readInt31 = parcel.readInt();
                Parcelable.Creator creator9 = Bundle.CREATOR;
                final Bundle bundle20 = (Bundle) parcel.readTypedObject(creator9);
                final int readInt32 = parcel.readInt();
                final Bundle bundle21 = (Bundle) parcel.readTypedObject(creator9);
                final int readInt33 = parcel.readInt();
                final Bundle bundle22 = (Bundle) parcel.readTypedObject(creator9);
                final int readInt34 = parcel.readInt();
                final float readFloat9 = parcel.readFloat();
                final int readInt35 = parcel.readInt();
                final float readFloat10 = parcel.readFloat();
                boolean readBoolean2 = parcel.readBoolean();
                final RemoteTransition remoteTransition5 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final InstanceId instanceId11 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                SplitScreenController splitScreenController = ((SplitScreenController.ISplitScreenImpl) this).mController;
                final int i15 = readBoolean2 ? 1 : 0;
                ExecutorUtils.executeRemoteCallWithTaskPermission(splitScreenController, "startMultiTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        ((SplitScreenController) obj2).mStageCoordinator.startTasks(readInt31, bundle20, readInt32, bundle21, readInt33, bundle22, readInt34, readFloat9, readInt35, readFloat10, remoteTransition5, instanceId11, i15, null);
                    }
                }, false);
            }
        } else {
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            if (readStrongBinder2 != null) {
                IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                obj = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ISplitScreenListener)) ? new ISplitScreenListener$Stub$Proxy(readStrongBinder2) : (ISplitScreenListener) queryLocalInterface2;
            }
            parcel.enforceNoDataAvail();
            final SplitScreenController.ISplitScreenImpl iSplitScreenImpl3 = (SplitScreenController.ISplitScreenImpl) this;
            ExecutorUtils.executeRemoteCallWithTaskPermission(iSplitScreenImpl3.mController, "registerSplitScreenListener", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    RemoteAnimationTarget[] remoteAnimationTargetArr32 = null;
                    switch (i4) {
                        case 0:
                            RemoteAnimationTarget[][] remoteAnimationTargetArr42 = (RemoteAnimationTarget[][]) iSplitScreenImpl3;
                            RemoteAnimationTarget[] remoteAnimationTargetArr52 = (RemoteAnimationTarget[]) obj;
                            SplitScreenController splitScreenController2 = (SplitScreenController) obj2;
                            splitScreenController2.getClass();
                            if (!Transitions.ENABLE_SHELL_TRANSITIONS && splitScreenController2.isSplitScreenVisible()) {
                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                stageCoordinator.mMainStage.evictInvisibleChildren(windowContainerTransaction);
                                stageCoordinator.mSideStage.evictInvisibleChildren(windowContainerTransaction);
                                splitScreenController2.mSyncQueue.queue(windowContainerTransaction);
                                splitScreenController2.mStageCoordinator.mSplitBackgroundController.updateBackgroundVisibility(false, false);
                                TransactionPool transactionPool = splitScreenController2.mTransactionPool;
                                SurfaceControl.Transaction acquire = transactionPool.acquire();
                                SurfaceControl surfaceControl = splitScreenController2.mGoingToRecentsTasksLayer;
                                if (surfaceControl != null) {
                                    acquire.remove(surfaceControl);
                                }
                                splitScreenController2.mGoingToRecentsTasksLayer = splitScreenController2.reparentSplitTasksForAnimation(remoteAnimationTargetArr52, acquire, "SplitScreenController#onGoingToRecentsLegacy");
                                acquire.apply();
                                transactionPool.release(acquire);
                                remoteAnimationTargetArr32 = new RemoteAnimationTarget[]{splitScreenController2.mStageCoordinator.getDividerBarLegacyTarget()};
                            }
                            remoteAnimationTargetArr42[0] = remoteAnimationTargetArr32;
                            return;
                        case 1:
                            RemoteAnimationTarget[][] remoteAnimationTargetArr62 = (RemoteAnimationTarget[][]) iSplitScreenImpl3;
                            RemoteAnimationTarget[] remoteAnimationTargetArr7 = (RemoteAnimationTarget[]) obj;
                            SplitScreenController splitScreenController22 = (SplitScreenController) obj2;
                            splitScreenController22.getClass();
                            if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                                int i102 = 0;
                                for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr7) {
                                    if (remoteAnimationTarget.mode == 0) {
                                        i102++;
                                    }
                                }
                                if (i102 >= 2) {
                                    TransactionPool transactionPool2 = splitScreenController22.mTransactionPool;
                                    SurfaceControl.Transaction acquire2 = transactionPool2.acquire();
                                    SurfaceControl surfaceControl2 = splitScreenController22.mStartingSplitTasksLayer;
                                    if (surfaceControl2 != null) {
                                        acquire2.remove(surfaceControl2);
                                    }
                                    splitScreenController22.mStartingSplitTasksLayer = splitScreenController22.reparentSplitTasksForAnimation(remoteAnimationTargetArr7, acquire2, "SplitScreenController#onStartingSplitLegacy");
                                    acquire2.apply();
                                    transactionPool2.release(acquire2);
                                    try {
                                        remoteAnimationTargetArr32 = new RemoteAnimationTarget[]{splitScreenController22.mStageCoordinator.getDividerBarLegacyTarget()};
                                        for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr7) {
                                            SurfaceControl surfaceControl3 = remoteAnimationTarget2.leash;
                                            if (surfaceControl3 != null) {
                                                surfaceControl3.release();
                                            }
                                        }
                                    } catch (Throwable th) {
                                        for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr7) {
                                            SurfaceControl surfaceControl4 = remoteAnimationTarget3.leash;
                                            if (surfaceControl4 != null) {
                                                surfaceControl4.release();
                                            }
                                        }
                                        throw th;
                                    }
                                }
                            }
                            remoteAnimationTargetArr62[0] = remoteAnimationTargetArr32;
                            return;
                        default:
                            ((SplitScreenController.ISplitScreenImpl) iSplitScreenImpl3).mListener.register((ISplitScreenListener) obj);
                            return;
                    }
                }
            }, false);
        }
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
