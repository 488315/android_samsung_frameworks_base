package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.window.DesktopModeFlags;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.desktopmode.DesktopTaskToFrontReason;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes3.dex */
public abstract class IDesktopMode$Stub extends Binder implements IInterface {
    public IDesktopMode$Stub() {
        attachInterface(this, "com.android.wm.shell.desktopmode.IDesktopMode");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.desktopmode.IDesktopMode");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.desktopmode.IDesktopMode");
            return true;
        }
        final IDesktopTaskListener iDesktopTaskListener$Stub$Proxy = null;
        final IMoveToDesktopCallback iMoveToDesktopCallback$Stub$Proxy = null;
        switch (i) {
            case 1:
                final int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "createDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$createDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.createDesk$default((DesktopTasksController) obj, i3, 0, false, null, 46);
                    }
                }, false);
                return true;
            case 2:
                final int i4 = parcel.readInt();
                final RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "activateDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$activateDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        DesktopTasksController.activateDesk$default((DesktopTasksController) obj, i4, remoteTransition, 0, 0, 12);
                    }
                }, false);
                return true;
            case 3:
                final int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.removeDesk$default((DesktopTasksController) obj, i5);
                    }
                }, false);
                return true;
            case 4:
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeAllDesks", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeAllDesks$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        Iterator it = desktopTasksController.taskRepository.getAllDeskIds().iterator();
                        while (it.hasNext()) {
                            DesktopTasksController.removeDesk$default(desktopTasksController, ((Number) it.next()).intValue());
                        }
                    }
                }, false);
                return true;
            case 5:
                final int i6 = parcel.readInt();
                final RemoteTransition remoteTransition2 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "showDesktopApps", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$showDesktopApps$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        int i7 = i6;
                        RemoteTransition remoteTransition3 = remoteTransition2;
                        desktopTasksController.getClass();
                        DesktopTasksController.logV$1("showDesktopApps", new Object[0]);
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i7, false);
                        if (orCreateDefaultDeskId != null) {
                            DesktopTasksController.activateDesk$default(desktopTasksController, orCreateDefaultDeskId.intValue(), remoteTransition3, 0, 0, 12);
                        }
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 6:
                parcel.readInt();
                parcel.enforceNoDataAvail();
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: stashDesktopApps is deprecated", new Object[0]);
                parcel2.writeNoException();
                return true;
            case 7:
                parcel.readInt();
                parcel.enforceNoDataAvail();
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: hideStashedDesktopApps is deprecated", new Object[0]);
                parcel2.writeNoException();
                return true;
            case 8:
                final int i7 = parcel.readInt();
                final RemoteTransition remoteTransition3 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final DesktopTaskToFrontReason desktopTaskToFrontReason = (DesktopTaskToFrontReason) parcel.readTypedObject(DesktopTaskToFrontReason.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "showDesktopApp", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$showDesktopApp$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason;
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        int i8 = i7;
                        RemoteTransition remoteTransition4 = remoteTransition3;
                        DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                        DesktopTaskToFrontReason desktopTaskToFrontReason2 = desktopTaskToFrontReason;
                        companion.getClass();
                        int i9 = DesktopTasksController.Companion.WhenMappings.$EnumSwitchMapping$0[desktopTaskToFrontReason2.ordinal()];
                        if (i9 == 1) {
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.UNKNOWN;
                        } else if (i9 == 2) {
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.TASKBAR_TAP;
                        } else if (i9 == 3) {
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.ALT_TAB;
                        } else {
                            if (i9 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.TASKBAR_MANAGE_WINDOW;
                        }
                        desktopTasksController.moveTaskToFront(i8, remoteTransition4, unminimizeReason);
                    }
                }, false);
                return true;
            case 9:
                final ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "onDesktopSplitSelectAnimComplete", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$onDesktopSplitSelectAnimComplete$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                        desktopTasksController.getClass();
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setBounds(runningTaskInfo2.token, new Rect());
                        windowContainerTransaction.setWindowingMode(runningTaskInfo2.token, 0);
                        desktopTasksController.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                    }
                }, false);
                return true;
            case 10:
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    iDesktopTaskListener$Stub$Proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDesktopTaskListener)) ? new IDesktopTaskListener$Stub$Proxy(strongBinder) : (IDesktopTaskListener) iInterfaceQueryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                final DesktopTasksController.IDesktopModeImpl iDesktopModeImpl = (DesktopTasksController.IDesktopModeImpl) this;
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: set task listener=%s", new Object[]{iDesktopTaskListener$Stub$Proxy});
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iDesktopModeImpl.controller, "setTaskListener", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$setTaskListener$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws RemoteException {
                        IDesktopTaskListener iDesktopTaskListener = iDesktopTaskListener$Stub$Proxy;
                        DesktopTasksController.IDesktopModeImpl iDesktopModeImpl2 = iDesktopModeImpl;
                        if (iDesktopTaskListener != null) {
                            SingleInstanceRemoteListener singleInstanceRemoteListener = iDesktopModeImpl2.remoteListener;
                            (singleInstanceRemoteListener != null ? singleInstanceRemoteListener : null).register(iDesktopTaskListener);
                        } else {
                            SingleInstanceRemoteListener singleInstanceRemoteListener2 = iDesktopModeImpl2.remoteListener;
                            (singleInstanceRemoteListener2 != null ? singleInstanceRemoteListener2 : null).unregister();
                        }
                    }
                }, false);
                return true;
            case 11:
                final int i8 = parcel.readInt();
                final DesktopModeTransitionSource desktopModeTransitionSource = (DesktopModeTransitionSource) parcel.readTypedObject(DesktopModeTransitionSource.CREATOR);
                final RemoteTransition remoteTransition4 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.android.wm.shell.desktopmode.IMoveToDesktopCallback");
                    iMoveToDesktopCallback$Stub$Proxy = (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof IMoveToDesktopCallback)) ? new IMoveToDesktopCallback$Stub$Proxy(strongBinder2) : (IMoveToDesktopCallback) iInterfaceQueryLocalInterface2;
                }
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "moveTaskToDesktop", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$moveToDesktop$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.moveTaskToDefaultDeskAndActivate$default((DesktopTasksController) obj, i8, desktopModeTransitionSource, remoteTransition4, iMoveToDesktopCallback$Stub$Proxy, 2);
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 12:
                final int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeDefaultDeskInDisplay", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeDefaultDeskInDisplay$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        int i10 = i9;
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i10, false);
                        if (orCreateDefaultDeskId != null) {
                            desktopTasksController.removeDesk(i10, orCreateDefaultDeskId.intValue(), desktopTasksController.taskRepository);
                        }
                    }
                }, false);
                return true;
            case 13:
                final int i10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "moveTaskToExternalDisplay", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$moveToExternalDisplay$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        ((DesktopTasksController) obj).moveToNextDisplay(i10);
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 14:
                final Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                final Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                final int i11 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "startLaunchIntentTransition", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$startLaunchIntentTransition$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        Intent intent2 = intent;
                        Bundle bundle2 = bundle;
                        int i12 = i11;
                        desktopTasksController.getClass();
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        DisplayLayout displayLayout = desktopTasksController.displayController.getDisplayLayout(i12);
                        if (displayLayout == null) {
                            return;
                        }
                        Rect rectCalculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout);
                        if (DesktopModeFlags.ENABLE_CASCADING_WINDOWS.isTrue()) {
                            desktopTasksController.cascadeWindow(rectCalculateDefaultDesktopTaskBounds, displayLayout, i12);
                        }
                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(desktopTasksController.context, 0, intent2, 67108864, null, UserHandle.of(desktopTasksController.userId));
                        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundle2);
                        activityOptionsFromBundle.setLaunchWindowingMode(5);
                        activityOptionsFromBundle.setPendingIntentBackgroundActivityStartMode(3);
                        activityOptionsFromBundle.setLaunchBounds(rectCalculateDefaultDesktopTaskBounds);
                        activityOptionsFromBundle.setLaunchDisplayId(i12);
                        if (DesktopModeFlags.ENABLE_SHELL_INITIAL_BOUNDS_REGRESSION_BUG_FIX.isTrue()) {
                            activityOptionsFromBundle.setFlexibleLaunchSize(true);
                        }
                        windowContainerTransaction.sendPendingIntent(activityAsUser, intent2, activityOptionsFromBundle.toBundle());
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i12, false);
                        if (orCreateDefaultDeskId != null) {
                            DesktopTasksController.startLaunchTransition$default(desktopTasksController, windowContainerTransaction, orCreateDefaultDeskId.intValue(), i12);
                        }
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 15:
                final boolean[] zArr = new boolean[1];
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "isInDesktopMode", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$isInDesktopMode$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean[] zArr2 = zArr;
                        DesktopStateImpl.Companion.getClass();
                        zArr2[0] = DesktopStateImpl.Companion.inDesktopWindowing(0);
                    }
                }, true);
                boolean z = zArr[0];
                parcel2.writeNoException();
                parcel2.writeBoolean(z);
                return true;
            case 16:
                parcel.readString();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "exitDesktopModeFromHome", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$exitDesktopModeFromHome$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        Integer activeDeskId = desktopTasksController.taskRepository.getActiveDeskId(0);
                        if (activeDeskId != null) {
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            DesktopTasksController.prepareDeskDeactivationIfNeeded$default(desktopTasksController, windowContainerTransaction, activeDeskId, 0, 0, 28);
                            desktopTasksController.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                            DesktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 = desktopTasksController.exitDesktopModeListener;
                            if (desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1 != null) {
                                desktopTasksController$IDesktopModeImpl$exitDesktopModeListener$1.onExitDesktopModeStarted();
                            }
                            desktopTasksController.taskRepository.setDeskInactive(activeDeskId.intValue());
                        }
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 17:
                final int i12 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "minimizeTaskById", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$minimizeTaskById$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopTasksController.shellTaskOrganizer.getRunningTaskInfo(i12);
                        if (runningTaskInfo2 != null) {
                            desktopTasksController.minimizeTask(runningTaskInfo2, DesktopModeEventLogger.Companion.MinimizeReason.HOME_ACTION);
                        }
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 18:
                final int i13 = parcel.readInt();
                final RemoteTransition remoteTransition5 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final int i14 = parcel.readInt();
                final int i15 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "activateDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$activateDeskExt$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) throws Resources.NotFoundException {
                        ((DesktopTasksController) obj).activateDesk(i13, remoteTransition5, i14, i15);
                    }
                }, false);
                return true;
            case 19:
                final int i16 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeAllTasksInDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeAllTasksInDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DesktopTasksController) obj).removeAllTasksInDesk(i16);
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 20:
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeAllVisibleRecentTasks", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeAllVisibleRecentTasks$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DesktopTasksController) obj).removeAllVisibleRecentTasks();
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
