package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        final IDesktopTaskListener iDesktopTaskListener = null;
        final IMoveToDesktopCallback iMoveToDesktopCallback = null;
        switch (i) {
            case 1:
                final int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "createDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$createDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.createDesk$default((DesktopTasksController) obj, readInt, 0, false, null, 46);
                    }
                }, false);
                return true;
            case 2:
                final int readInt2 = parcel.readInt();
                final RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "activateDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$activateDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.activateDesk$default((DesktopTasksController) obj, readInt2, remoteTransition, 0, 0, 12);
                    }
                }, false);
                return true;
            case 3:
                final int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.removeDesk$default((DesktopTasksController) obj, readInt3);
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
                final int readInt4 = parcel.readInt();
                final RemoteTransition remoteTransition2 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "showDesktopApps", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$showDesktopApps$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        int i3 = readInt4;
                        RemoteTransition remoteTransition3 = remoteTransition2;
                        desktopTasksController.getClass();
                        DesktopTasksController.logV$1("showDesktopApps", new Object[0]);
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i3, false);
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
                final int readInt5 = parcel.readInt();
                final RemoteTransition remoteTransition3 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final DesktopTaskToFrontReason desktopTaskToFrontReason = (DesktopTaskToFrontReason) parcel.readTypedObject(DesktopTaskToFrontReason.CREATOR);
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "showDesktopApp", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$showDesktopApp$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason;
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        int i3 = readInt5;
                        RemoteTransition remoteTransition4 = remoteTransition3;
                        DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                        DesktopTaskToFrontReason desktopTaskToFrontReason2 = desktopTaskToFrontReason;
                        companion.getClass();
                        int i4 = DesktopTasksController.Companion.WhenMappings.$EnumSwitchMapping$0[desktopTaskToFrontReason2.ordinal()];
                        if (i4 == 1) {
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.UNKNOWN;
                        } else if (i4 == 2) {
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.TASKBAR_TAP;
                        } else if (i4 == 3) {
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.ALT_TAB;
                        } else {
                            if (i4 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            unminimizeReason = DesktopModeEventLogger.Companion.UnminimizeReason.TASKBAR_MANAGE_WINDOW;
                        }
                        desktopTasksController.moveTaskToFront(i3, remoteTransition4, unminimizeReason);
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
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    iDesktopTaskListener = (queryLocalInterface == null || !(queryLocalInterface instanceof IDesktopTaskListener)) ? new IDesktopTaskListener$Stub$Proxy(readStrongBinder) : (IDesktopTaskListener) queryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                final DesktopTasksController.IDesktopModeImpl iDesktopModeImpl = (DesktopTasksController.IDesktopModeImpl) this;
                ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: set task listener=%s", new Object[]{iDesktopTaskListener});
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iDesktopModeImpl.controller, "setTaskListener", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$setTaskListener$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        IDesktopTaskListener iDesktopTaskListener2 = IDesktopTaskListener.this;
                        DesktopTasksController.IDesktopModeImpl iDesktopModeImpl2 = iDesktopModeImpl;
                        if (iDesktopTaskListener2 != null) {
                            SingleInstanceRemoteListener singleInstanceRemoteListener = iDesktopModeImpl2.remoteListener;
                            (singleInstanceRemoteListener != null ? singleInstanceRemoteListener : null).register(iDesktopTaskListener2);
                        } else {
                            SingleInstanceRemoteListener singleInstanceRemoteListener2 = iDesktopModeImpl2.remoteListener;
                            (singleInstanceRemoteListener2 != null ? singleInstanceRemoteListener2 : null).unregister();
                        }
                    }
                }, false);
                return true;
            case 11:
                final int readInt6 = parcel.readInt();
                final DesktopModeTransitionSource desktopModeTransitionSource = (DesktopModeTransitionSource) parcel.readTypedObject(DesktopModeTransitionSource.CREATOR);
                final RemoteTransition remoteTransition4 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.wm.shell.desktopmode.IMoveToDesktopCallback");
                    iMoveToDesktopCallback = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IMoveToDesktopCallback)) ? new IMoveToDesktopCallback$Stub$Proxy(readStrongBinder2) : (IMoveToDesktopCallback) queryLocalInterface2;
                }
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "moveTaskToDesktop", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$moveToDesktop$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController.moveTaskToDefaultDeskAndActivate$default((DesktopTasksController) obj, readInt6, desktopModeTransitionSource, remoteTransition4, iMoveToDesktopCallback, 2);
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 12:
                final int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeDefaultDeskInDisplay", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeDefaultDeskInDisplay$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        int i3 = readInt7;
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i3, false);
                        if (orCreateDefaultDeskId != null) {
                            desktopTasksController.removeDesk(i3, orCreateDefaultDeskId.intValue(), desktopTasksController.taskRepository);
                        }
                    }
                }, false);
                return true;
            case 13:
                final int readInt8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "moveTaskToExternalDisplay", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$moveToExternalDisplay$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DesktopTasksController) obj).moveToNextDisplay(readInt8);
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 14:
                final Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                final Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                final int readInt9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "startLaunchIntentTransition", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$startLaunchIntentTransition$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        Intent intent2 = intent;
                        Bundle bundle2 = bundle;
                        int i3 = readInt9;
                        desktopTasksController.getClass();
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        DisplayLayout displayLayout = desktopTasksController.displayController.getDisplayLayout(i3);
                        if (displayLayout == null) {
                            return;
                        }
                        Rect calculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateDefaultDesktopTaskBounds(displayLayout);
                        if (DesktopModeFlags.ENABLE_CASCADING_WINDOWS.isTrue()) {
                            desktopTasksController.cascadeWindow(calculateDefaultDesktopTaskBounds, displayLayout, i3);
                        }
                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(desktopTasksController.context, 0, intent2, 67108864, null, UserHandle.of(desktopTasksController.userId));
                        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle2);
                        fromBundle.setLaunchWindowingMode(5);
                        fromBundle.setPendingIntentBackgroundActivityStartMode(3);
                        fromBundle.setLaunchBounds(calculateDefaultDesktopTaskBounds);
                        fromBundle.setLaunchDisplayId(i3);
                        if (DesktopModeFlags.ENABLE_SHELL_INITIAL_BOUNDS_REGRESSION_BUG_FIX.isTrue()) {
                            fromBundle.setFlexibleLaunchSize(true);
                        }
                        windowContainerTransaction.sendPendingIntent(activityAsUser, intent2, fromBundle.toBundle());
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i3, false);
                        if (orCreateDefaultDeskId != null) {
                            DesktopTasksController.startLaunchTransition$default(desktopTasksController, windowContainerTransaction, orCreateDefaultDeskId.intValue(), i3);
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
                            desktopTasksController.prepareDeskDeactivationIfNeeded(windowContainerTransaction, activeDeskId);
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
                final int readInt10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "minimizeTaskById", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$minimizeTaskById$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DesktopTasksController desktopTasksController = (DesktopTasksController) obj;
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopTasksController.shellTaskOrganizer.getRunningTaskInfo(readInt10);
                        if (runningTaskInfo2 != null) {
                            desktopTasksController.minimizeTask(runningTaskInfo2, DesktopModeEventLogger.Companion.MinimizeReason.HOME_ACTION);
                        }
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 18:
                final int readInt11 = parcel.readInt();
                final RemoteTransition remoteTransition5 = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                final int readInt12 = parcel.readInt();
                final int readInt13 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "activateDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$activateDeskExt$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DesktopTasksController) obj).activateDesk(readInt11, remoteTransition5, readInt12, readInt13);
                    }
                }, false);
                return true;
            case 19:
                final int readInt14 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeAllTasksInDesk", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeAllTasksInDesk$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DesktopTasksController) obj).removeAllTasksInDesk(readInt14);
                    }
                }, false);
                parcel2.writeNoException();
                return true;
            case 20:
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DesktopTasksController.IDesktopModeImpl) this).controller, "removeAllVisibleRecentTasks", new Consumer() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$removeAllVisibleRecentTasks$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        if (((DesktopTasksController) obj).recentTasksController != null) {
                            ActivityTaskManager.getService().removeAllVisibleRecentTasksExt(true, true);
                        }
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
