package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.GroupedTaskInfo;

/* loaded from: classes3.dex */
public interface IRecentTasksListener extends IInterface {
    void onRecentTasksChanged();

    void onRunningTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onRunningTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onRunningTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo);

    void onTaskMovedToFront(GroupedTaskInfo groupedTaskInfo);

    void onVisibleTasksChanged(GroupedTaskInfo[] groupedTaskInfoArr);

    public abstract class Stub extends Binder implements IRecentTasksListener {

        public class Proxy implements IRecentTasksListener {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRecentTasksChanged() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onTaskMovedToFront(GroupedTaskInfo groupedTaskInfo) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    parcelObtain.writeTypedObject(groupedTaskInfo, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onVisibleTasksChanged(GroupedTaskInfo[] groupedTaskInfoArr) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentTasksListener");
                    parcelObtain.writeTypedArray(groupedTaskInfoArr, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.wm.shell.recents.IRecentTasksListener");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.wm.shell.recents.IRecentTasksListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.wm.shell.recents.IRecentTasksListener");
                return true;
            }
            switch (i) {
                case 1:
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRecentTasksChanged();
                    return true;
                case 2:
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRunningTaskAppeared(runningTaskInfo);
                    return true;
                case 3:
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRunningTaskVanished(runningTaskInfo2);
                    return true;
                case 4:
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onRunningTaskChanged(runningTaskInfo3);
                    return true;
                case 5:
                    GroupedTaskInfo groupedTaskInfo = (GroupedTaskInfo) parcel.readTypedObject(GroupedTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onTaskMovedToFront(groupedTaskInfo);
                    return true;
                case 6:
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onTaskInfoChanged(runningTaskInfo4);
                    return true;
                case 7:
                    GroupedTaskInfo[] groupedTaskInfoArr = (GroupedTaskInfo[]) parcel.createTypedArray(GroupedTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    ((RecentTasksController.IRecentTasksImpl.AnonymousClass1) this).onVisibleTasksChanged(groupedTaskInfoArr);
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
}
