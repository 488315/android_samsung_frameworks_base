package com.android.wm.shell.splitscreen;

import android.R;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.window.RemoteTransition;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                iSplitScreenListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ISplitScreenListener$Stub$Proxy)) ? new ISplitScreenListener$Stub$Proxy(readStrongBinder) : (ISplitScreenListener$Stub$Proxy) queryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            SplitScreenController.ISplitScreenImpl iSplitScreenImpl = (SplitScreenController.ISplitScreenImpl) this;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl.mController, "registerSplitScreenListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(iSplitScreenImpl, iSplitScreenListener$Stub$Proxy), false);
            return true;
        }
        if (i == 3) {
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            if (readStrongBinder2 != null) {
                IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitScreenListener");
                if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ISplitScreenListener$Stub$Proxy)) {
                    new ISplitScreenListener$Stub$Proxy(readStrongBinder2);
                }
            }
            parcel.enforceNoDataAvail();
            SplitScreenController.ISplitScreenImpl iSplitScreenImpl2 = (SplitScreenController.ISplitScreenImpl) this;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl2.mController, "unregisterSplitScreenListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda3(iSplitScreenImpl2, 4), false);
            return true;
        }
        if (i == 17) {
            final PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
            final int readInt = parcel.readInt();
            Parcelable.Creator creator = Bundle.CREATOR;
            final Bundle bundle = (Bundle) parcel.readTypedObject(creator);
            final int readInt2 = parcel.readInt();
            final Bundle bundle2 = (Bundle) parcel.readTypedObject(creator);
            final int readInt3 = parcel.readInt();
            final int readInt4 = parcel.readInt();
            final RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
            final InstanceId instanceId = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
            parcel.enforceNoDataAvail();
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntentAndTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda25
                /* JADX WARN: Removed duplicated region for block: B:26:0x00f2  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x012c  */
                /* JADX WARN: Removed duplicated region for block: B:50:0x0189  */
                /* JADX WARN: Removed duplicated region for block: B:52:0x0142  */
                @Override // java.util.function.Consumer
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void accept(java.lang.Object r22) {
                    /*
                        Method dump skipped, instructions count: 411
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda25.accept(java.lang.Object):void");
                }
            }, false);
            return true;
        }
        if (i == 18) {
            final ShortcutInfo shortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
            Parcelable.Creator creator2 = Bundle.CREATOR;
            final Bundle bundle3 = (Bundle) parcel.readTypedObject(creator2);
            final int readInt5 = parcel.readInt();
            final Bundle bundle4 = (Bundle) parcel.readTypedObject(creator2);
            final int readInt6 = parcel.readInt();
            final int readInt7 = parcel.readInt();
            final RemoteTransition remoteTransition2 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
            final InstanceId instanceId2 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
            parcel.enforceNoDataAvail();
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcutAndTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda8
                /* JADX WARN: Removed duplicated region for block: B:26:0x00d3  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x0100  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x011c  */
                @Override // java.util.function.Consumer
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void accept(java.lang.Object r21) {
                    /*
                        Method dump skipped, instructions count: 337
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda8.accept(java.lang.Object):void");
                }
            }, false);
            return true;
        }
        switch (i) {
            case 6:
                int readInt8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreen", new SplitScreenController$$ExternalSyntheticLambda9(readInt8, i5), false);
                return true;
            case 7:
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "exitSplitScreenOnHide", new SplitScreenController$$ExternalSyntheticLambda10(readBoolean, i5), false);
                return true;
            case 8:
                final int readInt9 = parcel.readInt();
                final int readInt10 = parcel.readInt();
                final Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startTask", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda23
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i6 = readInt9;
                        int i7 = readInt10;
                        Bundle bundle6 = bundle5;
                        int i8 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        ((SplitScreenController) obj).startTask(i6, i7, bundle6, null);
                    }
                }, false);
                return true;
            case 9:
                final String readString = parcel.readString();
                final String readString2 = parcel.readString();
                final int readInt11 = parcel.readInt();
                final Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                final UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                final InstanceId instanceId3 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startShortcut", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda24
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str = readString;
                        String str2 = readString2;
                        int i6 = readInt11;
                        Bundle bundle7 = bundle6;
                        UserHandle userHandle2 = userHandle;
                        InstanceId instanceId4 = instanceId3;
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i7 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        splitScreenController.getClass();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7987057462806301533L, 1, 3L);
                        }
                        SplitscreenEventLogger splitscreenEventLogger = splitScreenController.mStageCoordinator.mLogger;
                        splitscreenEventLogger.mEnterSessionId = instanceId4;
                        splitscreenEventLogger.mEnterReason = 3;
                        splitScreenController.startShortcut(str, str2, i6, bundle7, userHandle2);
                    }
                }, false);
                return true;
            case 10:
                final PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                final int readInt12 = parcel.readInt();
                final Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                final int readInt13 = parcel.readInt();
                final Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                final InstanceId instanceId4 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntent", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PendingIntent pendingIntent3 = pendingIntent2;
                        int i6 = readInt12;
                        Intent intent2 = intent;
                        int i7 = readInt13;
                        Bundle bundle8 = bundle7;
                        InstanceId instanceId5 = instanceId4;
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i8 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        splitScreenController.getClass();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1752260962322402115L, 1, 3L);
                        }
                        SplitscreenEventLogger splitscreenEventLogger = splitScreenController.mStageCoordinator.mLogger;
                        splitscreenEventLogger.mEnterSessionId = instanceId5;
                        splitscreenEventLogger.mEnterReason = 3;
                        splitScreenController.startIntent(pendingIntent3, i6, intent2, i7, bundle8, null, false, -1, -1, 0, false);
                    }
                }, false);
                return true;
            case 11:
                final int readInt14 = parcel.readInt();
                Parcelable.Creator creator3 = Bundle.CREATOR;
                final Bundle bundle8 = (Bundle) parcel.readTypedObject(creator3);
                final int readInt15 = parcel.readInt();
                final Bundle bundle9 = (Bundle) parcel.readTypedObject(creator3);
                final int readInt16 = parcel.readInt();
                final int readInt17 = parcel.readInt();
                final RemoteTransition remoteTransition3 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final InstanceId instanceId5 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                final SplitScreenController.ISplitScreenImpl iSplitScreenImpl3 = (SplitScreenController.ISplitScreenImpl) this;
                if (readInt15 != -1) {
                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl3.mController, "startTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda10
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i6 = readInt14;
                            Bundle bundle10 = bundle8;
                            int i7 = readInt15;
                            Bundle bundle11 = bundle9;
                            int i8 = readInt16;
                            int i9 = readInt17;
                            RemoteTransition remoteTransition4 = remoteTransition3;
                            InstanceId instanceId6 = instanceId5;
                            int i10 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                            ((SplitScreenController) obj).mStageCoordinator.startTasks(i6, bundle10, i7, bundle11, -1, null, i8, i9, 0.5f, 0, 0.5f, remoteTransition4, instanceId6, -1, null);
                        }
                    }, false);
                    return true;
                }
                final SplitScreenController.CallerInfo callerInfo = new SplitScreenController.CallerInfo();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl3.mController, "startSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl4 = SplitScreenController.ISplitScreenImpl.this;
                        int i6 = readInt14;
                        Bundle bundle10 = bundle8;
                        int i7 = readInt15;
                        Bundle bundle11 = bundle9;
                        int i8 = readInt16;
                        RemoteTransition remoteTransition4 = remoteTransition3;
                        InstanceId instanceId6 = instanceId5;
                        SplitScreenController.CallerInfo callerInfo2 = callerInfo;
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        int i9 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                        splitScreenController.mStageCoordinator.startTasks(i6, bundle10, i7, bundle11, -1, null, i8, 8, 0.5f, 0, 0.5f, remoteTransition4, instanceId6, (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || MultiWindowUtils.isInSubDisplay(iSplitScreenImpl4.mController.mContext)) ? -1 : 0, callerInfo2);
                    }
                }, false);
                return true;
            default:
                switch (i) {
                    case 20:
                        Parcelable.Creator creator4 = PendingIntent.CREATOR;
                        final PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(creator4);
                        final int readInt18 = parcel.readInt();
                        Parcelable.Creator creator5 = ShortcutInfo.CREATOR;
                        final ShortcutInfo shortcutInfo2 = (ShortcutInfo) parcel.readTypedObject(creator5);
                        Parcelable.Creator creator6 = Bundle.CREATOR;
                        final Bundle bundle10 = (Bundle) parcel.readTypedObject(creator6);
                        final PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(creator4);
                        final int readInt19 = parcel.readInt();
                        final ShortcutInfo shortcutInfo3 = (ShortcutInfo) parcel.readTypedObject(creator5);
                        final Bundle bundle11 = (Bundle) parcel.readTypedObject(creator6);
                        final int readInt20 = parcel.readInt();
                        final int readInt21 = parcel.readInt();
                        final RemoteTransition remoteTransition4 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                        final InstanceId instanceId6 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                        parcel.enforceNoDataAvail();
                        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startIntents", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda19
                            /* JADX WARN: Removed duplicated region for block: B:31:0x0128  */
                            /* JADX WARN: Removed duplicated region for block: B:37:0x016d  */
                            /* JADX WARN: Removed duplicated region for block: B:48:0x0191  */
                            /* JADX WARN: Removed duplicated region for block: B:95:0x0160  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void accept(java.lang.Object r23) {
                                /*
                                    Method dump skipped, instructions count: 608
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda19.accept(java.lang.Object):void");
                            }
                        }, false);
                        return true;
                    case 21:
                        IBinder readStrongBinder3 = parcel.readStrongBinder();
                        if (readStrongBinder3 != null) {
                            IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitSelectListener");
                            iSplitSelectListener$Stub$Proxy = (queryLocalInterface3 == null || !(queryLocalInterface3 instanceof ISplitSelectListener$Stub$Proxy)) ? new ISplitSelectListener$Stub$Proxy(readStrongBinder3) : (ISplitSelectListener$Stub$Proxy) queryLocalInterface3;
                        }
                        parcel.enforceNoDataAvail();
                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl4 = (SplitScreenController.ISplitScreenImpl) this;
                        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl4.mController, "registerSplitSelectListener", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(iSplitScreenImpl4, iSplitSelectListener$Stub$Proxy), false);
                        return true;
                    case 22:
                        IBinder readStrongBinder4 = parcel.readStrongBinder();
                        if (readStrongBinder4 != null) {
                            IInterface queryLocalInterface4 = readStrongBinder4.queryLocalInterface("com.android.wm.shell.splitscreen.ISplitSelectListener");
                            if (queryLocalInterface4 == null || !(queryLocalInterface4 instanceof ISplitSelectListener$Stub$Proxy)) {
                                new ISplitSelectListener$Stub$Proxy(readStrongBinder4);
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
                                        int i6 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        if (splitScreenController.mStageCoordinator.isSplitScreenVisible()) {
                                            splitScreenController.mStageCoordinator.switchSplitPosition("remoteCall");
                                            break;
                                        }
                                        break;
                                    default:
                                        int i7 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
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
                                int readInt22 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "startSplitByTwoTouchSwipeIfPossible", new SplitScreenController$$ExternalSyntheticLambda9(readInt22, i3), false);
                                return true;
                            case 103:
                                final int readInt23 = parcel.readInt();
                                Parcelable.Creator creator7 = Bundle.CREATOR;
                                final Bundle bundle12 = (Bundle) parcel.readTypedObject(creator7);
                                final int readInt24 = parcel.readInt();
                                final Bundle bundle13 = (Bundle) parcel.readTypedObject(creator7);
                                final int readInt25 = parcel.readInt();
                                final float readFloat = parcel.readFloat();
                                final RemoteTransition remoteTransition5 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                                final InstanceId instanceId7 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                                parcel.enforceNoDataAvail();
                                final SplitScreenController.ISplitScreenImpl iSplitScreenImpl6 = (SplitScreenController.ISplitScreenImpl) this;
                                if (readInt24 != -1) {
                                    ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl6.mController, "startSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda15
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i6 = readInt23;
                                            Bundle bundle14 = bundle12;
                                            int i7 = readInt24;
                                            Bundle bundle15 = bundle13;
                                            int i8 = readInt25;
                                            float f = readFloat;
                                            RemoteTransition remoteTransition6 = remoteTransition5;
                                            InstanceId instanceId8 = instanceId7;
                                            int i9 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                            ((SplitScreenController) obj).mStageCoordinator.startTasks(i6, bundle14, i7, bundle15, -1, null, i8, 8, f, 0, 0.5f, remoteTransition6, instanceId8, -1, null);
                                        }
                                    }, false);
                                    return true;
                                }
                                final SplitScreenController.CallerInfo callerInfo2 = new SplitScreenController.CallerInfo();
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iSplitScreenImpl6.mController, "startSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda14
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        SplitScreenController.ISplitScreenImpl iSplitScreenImpl7 = SplitScreenController.ISplitScreenImpl.this;
                                        int i6 = readInt23;
                                        Bundle bundle14 = bundle12;
                                        int i7 = readInt24;
                                        Bundle bundle15 = bundle13;
                                        int i8 = readInt25;
                                        float f = readFloat;
                                        RemoteTransition remoteTransition6 = remoteTransition5;
                                        InstanceId instanceId8 = instanceId7;
                                        SplitScreenController.CallerInfo callerInfo3 = callerInfo2;
                                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                                        int i9 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        splitScreenController.mStageCoordinator.startTasks(i6, bundle14, i7, bundle15, -1, null, i8, 8, f, 0, 0.5f, remoteTransition6, instanceId8, (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || MultiWindowUtils.isInSubDisplay(iSplitScreenImpl7.mController.mContext)) ? -1 : 0, callerInfo3);
                                    }
                                }, false);
                                return true;
                            case 104:
                                final int readInt26 = parcel.readInt();
                                Parcelable.Creator creator8 = Bundle.CREATOR;
                                final Bundle bundle14 = (Bundle) parcel.readTypedObject(creator8);
                                final int readInt27 = parcel.readInt();
                                final Bundle bundle15 = (Bundle) parcel.readTypedObject(creator8);
                                final int readInt28 = parcel.readInt();
                                final Bundle bundle16 = (Bundle) parcel.readTypedObject(creator8);
                                final int readInt29 = parcel.readInt();
                                final float readFloat2 = parcel.readFloat();
                                final int readInt30 = parcel.readInt();
                                final float readFloat3 = parcel.readFloat();
                                boolean readBoolean2 = parcel.readBoolean();
                                final RemoteTransition remoteTransition6 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                                final InstanceId instanceId8 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                                parcel.enforceNoDataAvail();
                                SplitScreenController splitScreenController = ((SplitScreenController.ISplitScreenImpl) this).mController;
                                final int i6 = readBoolean2 ? 1 : 0;
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(splitScreenController, "startMultiSplitTasks", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda22
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i7 = readInt26;
                                        Bundle bundle17 = bundle14;
                                        int i8 = readInt27;
                                        Bundle bundle18 = bundle15;
                                        int i9 = readInt28;
                                        Bundle bundle19 = bundle16;
                                        int i10 = readInt29;
                                        float f = readFloat2;
                                        int i11 = readInt30;
                                        float f2 = readFloat3;
                                        RemoteTransition remoteTransition7 = remoteTransition6;
                                        InstanceId instanceId9 = instanceId8;
                                        int i12 = i6;
                                        int i13 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        ((SplitScreenController) obj).mStageCoordinator.startTasks(i7, bundle17, i8, bundle18, i9, bundle19, i10, 8, f, i11, f2, remoteTransition7, instanceId9, i12, null);
                                    }
                                }, false);
                                return true;
                            case 105:
                                final int readInt31 = parcel.readInt();
                                final int readInt32 = parcel.readInt();
                                final int readInt33 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "showAddAppPairDialogForRecent", new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda7
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        final int i7 = readInt31;
                                        final int i8 = readInt32;
                                        final int i9 = readInt33;
                                        int i10 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                        final StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.mRecentTasks.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda22
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj2) {
                                                StageCoordinator stageCoordinator2 = StageCoordinator.this;
                                                final int i11 = i7;
                                                final int i12 = i8;
                                                final int i13 = i9;
                                                stageCoordinator2.getClass();
                                                final SplitBounds splitBoundsForTaskId = ((RecentTasksController) obj2).getSplitBoundsForTaskId(i11);
                                                if (splitBoundsForTaskId == null || i11 != splitBoundsForTaskId.leftTopTaskId || i12 != splitBoundsForTaskId.rightBottomTaskId || i13 != splitBoundsForTaskId.cellTaskId) {
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
                                                splitWindowManager.mContext = new ContextThemeWrapper(splitWindowManager.mContext, R.style.Theme.DeviceDefault.DayNight);
                                                AlertDialog.Builder builder = new AlertDialog.Builder(splitWindowManager.mContext);
                                                builder.setTitle(com.android.systemui.R.string.add_app_pair_to);
                                                builder.setItems((CharSequence[]) appPairDialogItems.values().toArray(new String[appPairDialogItems.size()]), new DialogInterface.OnClickListener() { // from class: com.android.wm.shell.common.split.SplitWindowManager$$ExternalSyntheticLambda1
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(DialogInterface dialogInterface, int i14) {
                                                        SplitWindowManager splitWindowManager2 = SplitWindowManager.this;
                                                        ArrayMap arrayMap = appPairDialogItems;
                                                        int i15 = i11;
                                                        int i16 = i12;
                                                        int i17 = i13;
                                                        SplitBounds splitBounds = splitBoundsForTaskId;
                                                        AppPairShortcutController appPairShortcutController = splitWindowManager2.mAppPairShortcutController;
                                                        int intValue = ((Integer) arrayMap.keyAt(i14)).intValue();
                                                        appPairShortcutController.getClass();
                                                        int i18 = -1;
                                                        if (i15 == -1 || i16 == -1) {
                                                            Slog.e("AppPairShortcutController", "createAppPairShortcutForRecent: Invalid taskId");
                                                            return;
                                                        }
                                                        appPairShortcutController.mStageCoordinator.getClass();
                                                        ActivityManager.RecentTaskInfo recentTaskInfo = StageCoordinator.getRecentTaskInfo(i15);
                                                        ActivityManager.RecentTaskInfo recentTaskInfo2 = StageCoordinator.getRecentTaskInfo(i16);
                                                        if (recentTaskInfo == null || recentTaskInfo2 == null) {
                                                            Slog.e("AppPairShortcutController", "createAppPairShortcutForRecent: Can't find tasks.");
                                                            return;
                                                        }
                                                        ArrayList arrayList = new ArrayList();
                                                        boolean z = i17 != -1;
                                                        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && z) {
                                                            ActivityManager.RecentTaskInfo recentTaskInfo3 = StageCoordinator.getRecentTaskInfo(i17);
                                                            if (recentTaskInfo3 == null) {
                                                                Slog.e("AppPairShortcutController", "createAppPairShortcutForRecent: Can't find tasks for cell");
                                                                return;
                                                            }
                                                            boolean z2 = splitBounds.appsStackedVertically;
                                                            int i19 = z2 ? (splitBounds.cellPosition & 16) != 0 ? 3 : 5 : (splitBounds.cellPosition & 8) != 0 ? 2 : 4;
                                                            int cellSide = CellUtil.getCellSide(splitBounds.cellPosition, !z2, false);
                                                            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && appPairShortcutController.mSplitLayout.mParallelMultiSplit) {
                                                                if (i19 == 2) {
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo3);
                                                                } else if (i19 == 3) {
                                                                    arrayList.add(recentTaskInfo3);
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo);
                                                                } else if (i19 == 4) {
                                                                    arrayList.add(recentTaskInfo2);
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo3);
                                                                } else if (i19 == 5) {
                                                                    arrayList.add(recentTaskInfo3);
                                                                    arrayList.add(recentTaskInfo);
                                                                    arrayList.add(recentTaskInfo2);
                                                                }
                                                            } else if (i19 == 2 || i19 == 3) {
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
                                                            i18 = i19;
                                                        } else {
                                                            arrayList.add(recentTaskInfo);
                                                            arrayList.add(recentTaskInfo2);
                                                        }
                                                        ArrayList arrayList2 = new ArrayList();
                                                        int[] iArr = new int[3];
                                                        for (int i20 = 0; i20 < arrayList.size(); i20++) {
                                                            ActivityManager.RecentTaskInfo recentTaskInfo4 = (ActivityManager.RecentTaskInfo) arrayList.get(i20);
                                                            String launchActivityForTask = AppPairShortcutController.getLaunchActivityForTask(recentTaskInfo4);
                                                            if (launchActivityForTask != null) {
                                                                ComponentName componentName = recentTaskInfo4.baseActivity;
                                                                if (componentName == null || !componentName.getClassName().equals("com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity")) {
                                                                    arrayList2.add(launchActivityForTask);
                                                                } else {
                                                                    arrayList2.add("com.google.android.apps.bard/.shellapp.BardEntryPointActivity");
                                                                }
                                                                iArr[i20] = recentTaskInfo4.userId;
                                                            }
                                                        }
                                                        if (arrayList.size() != arrayList2.size()) {
                                                            return;
                                                        }
                                                        Intent createAppPairShortcutIntentForRecent = (intValue == 0 || intValue == 1) ? appPairShortcutController.createAppPairShortcutIntentForRecent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_LAUNCHER", arrayList2, iArr, intValue, i18, z, splitBounds) : intValue != 2 ? intValue != 3 ? null : appPairShortcutController.createAppPairShortcutIntentForRecent("com.samsung.android.multiwindow.SEND_SPLIT_STATE_CHANGED", arrayList2, iArr, intValue, i18, z, splitBounds) : appPairShortcutController.createAppPairShortcutIntentForRecent("com.samsung.android.multiwindow.ADD_PAIR_APP_SHORTCUT_EDGEPANEL", arrayList2, iArr, intValue, i18, z, splitBounds);
                                                        if (createAppPairShortcutIntentForRecent != null) {
                                                            AppPairShortcutController.H h = appPairShortcutController.mH;
                                                            if (intValue == 3) {
                                                                h.sendMessage(h.obtainMessage(7, createAppPairShortcutIntentForRecent));
                                                            } else {
                                                                h.sendMessage(h.obtainMessage(6, createAppPairShortcutIntentForRecent));
                                                            }
                                                        }
                                                    }
                                                });
                                                AlertDialog create = builder.create();
                                                splitWindowManager.mAddToAppPairDialogForRecent = create;
                                                create.getWindow().setType(2008);
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
                                                int i62 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                                                if (splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    splitScreenController2.mStageCoordinator.switchSplitPosition("remoteCall");
                                                    break;
                                                }
                                                break;
                                            default:
                                                int i7 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
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
                                int readInt34 = parcel.readInt();
                                int readInt35 = parcel.readInt();
                                int readInt36 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                boolean[] zArr = {false};
                                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((SplitScreenController.ISplitScreenImpl) this).mController, "canShowAddAppPairDialogForRecent", new SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda20(readInt34, zArr, readInt35, readInt36, 0), true);
                                boolean z = zArr[0];
                                parcel2.writeNoException();
                                parcel2.writeBoolean(z);
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
