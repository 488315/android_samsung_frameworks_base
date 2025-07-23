package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.Slog;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.recents.IRecentTasksListener;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.GroupedTaskInfo;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IRecentTasks$Stub extends Binder implements IInterface {
    public IRecentTasks$Stub() {
        attachInterface(this, "com.android.wm.shell.recents.IRecentTasks");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        GroupedTaskInfo[] groupedTaskInfoArr;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.recents.IRecentTasks");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.recents.IRecentTasks");
            return true;
        }
        final IRecentTasksListener iRecentTasksListener = null;
        IRecentsAnimationRunner iRecentsAnimationRunner$Stub$Proxy = null;
        if (i == 2) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.recents.IRecentTasksListener");
                iRecentTasksListener = (queryLocalInterface == null || !(queryLocalInterface instanceof IRecentTasksListener)) ? new IRecentTasksListener.Stub.Proxy(readStrongBinder) : (IRecentTasksListener) queryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            final RecentTasksController.IRecentTasksImpl iRecentTasksImpl = (RecentTasksController.IRecentTasksImpl) this;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iRecentTasksImpl.mController, "registerRecentTasksListener", new Consumer() { // from class: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RecentTasksController.IRecentTasksImpl iRecentTasksImpl2 = RecentTasksController.IRecentTasksImpl.this;
                    iRecentTasksImpl2.mListener.register(iRecentTasksListener);
                }
            }, false);
            return true;
        }
        if (i == 3) {
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            if (readStrongBinder2 != null) {
                IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.wm.shell.recents.IRecentTasksListener");
                if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IRecentTasksListener)) {
                    new IRecentTasksListener.Stub.Proxy(readStrongBinder2);
                }
            }
            parcel.enforceNoDataAvail();
            RecentTasksController.IRecentTasksImpl iRecentTasksImpl2 = (RecentTasksController.IRecentTasksImpl) this;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iRecentTasksImpl2.mController, "unregisterRecentTasksListener", new RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda3(iRecentTasksImpl2, 1), false);
            return true;
        }
        if (i == 4) {
            final int readInt = parcel.readInt();
            final int readInt2 = parcel.readInt();
            final int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            RecentTasksController recentTasksController = ((RecentTasksController.IRecentTasksImpl) this).mController;
            if (recentTasksController == null) {
                groupedTaskInfoArr = new GroupedTaskInfo[0];
            } else {
                final GroupedTaskInfo[][] groupedTaskInfoArr2 = {null};
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(recentTasksController, "getRecentTasks", new Consumer() { // from class: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i3 = readInt;
                        int i4 = readInt2;
                        int i5 = readInt3;
                        GroupedTaskInfo[][] groupedTaskInfoArr3 = groupedTaskInfoArr2;
                        int i6 = RecentTasksController.IRecentTasksImpl.$r8$clinit;
                        groupedTaskInfoArr3[0] = (GroupedTaskInfo[]) ((RecentTasksController) obj).getRecentTasks(i3, i4, i5).toArray(new GroupedTaskInfo[0]);
                    }
                }, true);
                groupedTaskInfoArr = groupedTaskInfoArr2[0];
            }
            parcel2.writeNoException();
            parcel2.writeTypedArray(groupedTaskInfoArr, 1);
            return true;
        }
        if (i == 5) {
            final int readInt4 = parcel.readInt();
            parcel.enforceNoDataAvail();
            final ActivityManager.RunningTaskInfo[][] runningTaskInfoArr = {null};
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((RecentTasksController.IRecentTasksImpl) this).mController, "getRunningTasks", new Consumer() { // from class: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ActivityManager.RunningTaskInfo[][] runningTaskInfoArr2 = runningTaskInfoArr;
                    int i3 = readInt4;
                    int i4 = RecentTasksController.IRecentTasksImpl.$r8$clinit;
                    runningTaskInfoArr2[0] = (ActivityManager.RunningTaskInfo[]) ActivityTaskManager.getInstance().getTasks(i3).toArray(new ActivityManager.RunningTaskInfo[0]);
                }
            }, true);
            ActivityManager.RunningTaskInfo[] runningTaskInfoArr2 = runningTaskInfoArr[0];
            parcel2.writeNoException();
            parcel2.writeTypedArray(runningTaskInfoArr2, 1);
            return true;
        }
        if (i != 6) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        final PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
        final Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        final Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
        final IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
        IBinder readStrongBinder3 = parcel.readStrongBinder();
        if (readStrongBinder3 != null) {
            IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.android.wm.shell.recents.IRecentsAnimationRunner");
            iRecentsAnimationRunner$Stub$Proxy = (queryLocalInterface3 == null || !(queryLocalInterface3 instanceof IRecentsAnimationRunner)) ? new IRecentsAnimationRunner$Stub$Proxy(readStrongBinder3) : (IRecentsAnimationRunner) queryLocalInterface3;
        }
        final IRecentsAnimationRunner iRecentsAnimationRunner = iRecentsAnimationRunner$Stub$Proxy;
        parcel.enforceNoDataAvail();
        RecentTasksController recentTasksController2 = ((RecentTasksController.IRecentTasksImpl) this).mController;
        if (recentTasksController2.mTransitionHandler == null) {
            Slog.e("RecentTasksController", "Used shell-transitions startRecentsTransition without shell-transitions");
            return true;
        }
        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(recentTasksController2, "startRecentsTransition", new Consumer() { // from class: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PendingIntent pendingIntent2 = pendingIntent;
                Intent intent2 = intent;
                Bundle bundle2 = bundle;
                IApplicationThread iApplicationThread = asInterface;
                IRecentsAnimationRunner iRecentsAnimationRunner2 = iRecentsAnimationRunner;
                int i3 = RecentTasksController.IRecentTasksImpl.$r8$clinit;
                ((RecentTasksController) obj).mTransitionHandler.startRecentsTransition(pendingIntent2, intent2, bundle2, iApplicationThread, iRecentsAnimationRunner2);
            }
        }, false);
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
