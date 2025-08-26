package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.TaskSnapshot;

/* loaded from: classes.dex */
public interface ITaskStackListener extends IInterface {
    public static final int FORCED_RESIZEABLE_REASON_DEX_FREEFORM = 4;
    public static final int FORCED_RESIZEABLE_REASON_FREEFORM = 3;
    public static final int FORCED_RESIZEABLE_REASON_SECONDARY_DISPLAY = 2;
    public static final int FORCED_RESIZEABLE_REASON_SPLIT_SCREEN = 1;

    public static class Default implements ITaskStackListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ITaskStackListener
        public void onActivityDismissingDockedTask() throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityDismissingSplitTask(String str) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityForcedResizable(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityRotation(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onActivityUnpinned() throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onLockTaskModeChanged(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onRecentTaskListFrozenChanged(boolean z) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onRecentTaskListUpdated() throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onRecentTaskRemoved(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onRecentTaskRemovedForAddTask(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskCreated(int i, ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskFocusChanged(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskRemoved(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskRequestedOrientationChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskSnapshotInvalidated(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskStackChanged() throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskWindowingModeChanged(int i) throws RemoteException {
        }

        @Override // android.app.ITaskStackListener
        public void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z) throws RemoteException {
        }
    }

    void onActivityDismissingDockedTask() throws RemoteException;

    void onActivityDismissingSplitTask(String str) throws RemoteException;

    void onActivityForcedResizable(String str, int i, int i2) throws RemoteException;

    void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException;

    void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException;

    void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws RemoteException;

    void onActivityRotation(int i) throws RemoteException;

    void onActivityUnpinned() throws RemoteException;

    void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onLockTaskModeChanged(int i) throws RemoteException;

    void onRecentTaskListFrozenChanged(boolean z) throws RemoteException;

    void onRecentTaskListUpdated() throws RemoteException;

    void onRecentTaskRemoved(int i) throws RemoteException;

    void onRecentTaskRemovedForAddTask(int i) throws RemoteException;

    void onTaskCreated(int i, ComponentName componentName) throws RemoteException;

    void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskDisplayChanged(int i, int i2) throws RemoteException;

    void onTaskFocusChanged(int i, boolean z) throws RemoteException;

    void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskRemoved(int i) throws RemoteException;

    void onTaskRequestedOrientationChanged(int i, int i2) throws RemoteException;

    void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) throws RemoteException;

    void onTaskSnapshotInvalidated(int i) throws RemoteException;

    void onTaskStackChanged() throws RemoteException;

    void onTaskWindowingModeChanged(int i) throws RemoteException;

    void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskStackListener {
        public static final String DESCRIPTOR = "android.app.ITaskStackListener";
        static final int TRANSACTION_onActivityDismissingDockedTask = 7;
        static final int TRANSACTION_onActivityDismissingSplitTask = 31;
        static final int TRANSACTION_onActivityForcedResizable = 6;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayFailed = 8;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayRerouted = 9;
        static final int TRANSACTION_onActivityPinned = 3;
        static final int TRANSACTION_onActivityRequestedOrientationChanged = 14;
        static final int TRANSACTION_onActivityRestartAttempt = 5;
        static final int TRANSACTION_onActivityRotation = 26;
        static final int TRANSACTION_onActivityUnpinned = 4;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 19;
        static final int TRANSACTION_onLockTaskModeChanged = 28;
        static final int TRANSACTION_onRecentTaskListFrozenChanged = 22;
        static final int TRANSACTION_onRecentTaskListUpdated = 21;
        static final int TRANSACTION_onRecentTaskRemoved = 1;
        static final int TRANSACTION_onRecentTaskRemovedForAddTask = 23;
        static final int TRANSACTION_onTaskCreated = 10;
        static final int TRANSACTION_onTaskDescriptionChanged = 13;
        static final int TRANSACTION_onTaskDisplayChanged = 20;
        static final int TRANSACTION_onTaskFocusChanged = 24;
        static final int TRANSACTION_onTaskMovedToBack = 27;
        static final int TRANSACTION_onTaskMovedToFront = 12;
        static final int TRANSACTION_onTaskProfileLocked = 16;
        static final int TRANSACTION_onTaskRemovalStarted = 15;
        static final int TRANSACTION_onTaskRemoved = 11;
        static final int TRANSACTION_onTaskRequestedOrientationChanged = 25;
        static final int TRANSACTION_onTaskSnapshotChanged = 17;
        static final int TRANSACTION_onTaskSnapshotInvalidated = 18;
        static final int TRANSACTION_onTaskStackChanged = 2;
        static final int TRANSACTION_onTaskWindowingModeChanged = 30;
        static final int TRANSACTION_onTaskbarIconVisibleChangeRequest = 29;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITaskStackListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITaskStackListener)) {
                return (ITaskStackListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRecentTaskRemoved";
                case 2:
                    return "onTaskStackChanged";
                case 3:
                    return "onActivityPinned";
                case 4:
                    return "onActivityUnpinned";
                case 5:
                    return "onActivityRestartAttempt";
                case 6:
                    return "onActivityForcedResizable";
                case 7:
                    return "onActivityDismissingDockedTask";
                case 8:
                    return "onActivityLaunchOnSecondaryDisplayFailed";
                case 9:
                    return "onActivityLaunchOnSecondaryDisplayRerouted";
                case 10:
                    return "onTaskCreated";
                case 11:
                    return "onTaskRemoved";
                case 12:
                    return "onTaskMovedToFront";
                case 13:
                    return "onTaskDescriptionChanged";
                case 14:
                    return "onActivityRequestedOrientationChanged";
                case 15:
                    return "onTaskRemovalStarted";
                case 16:
                    return "onTaskProfileLocked";
                case 17:
                    return "onTaskSnapshotChanged";
                case 18:
                    return "onTaskSnapshotInvalidated";
                case 19:
                    return "onBackPressedOnTaskRoot";
                case 20:
                    return "onTaskDisplayChanged";
                case 21:
                    return "onRecentTaskListUpdated";
                case 22:
                    return "onRecentTaskListFrozenChanged";
                case 23:
                    return "onRecentTaskRemovedForAddTask";
                case 24:
                    return "onTaskFocusChanged";
                case 25:
                    return "onTaskRequestedOrientationChanged";
                case 26:
                    return "onActivityRotation";
                case 27:
                    return "onTaskMovedToBack";
                case 28:
                    return "onLockTaskModeChanged";
                case 29:
                    return "onTaskbarIconVisibleChangeRequest";
                case 30:
                    return "onTaskWindowingModeChanged";
                case 31:
                    return "onActivityDismissingSplitTask";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecentTaskRemoved(i3);
                    return true;
                case 2:
                    onTaskStackChanged();
                    return true;
                case 3:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityPinned(string, i4, i5, i6);
                    return true;
                case 4:
                    onActivityUnpinned();
                    return true;
                case 5:
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onActivityRestartAttempt(runningTaskInfo, z, z2, z3);
                    return true;
                case 6:
                    String string2 = parcel.readString();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityForcedResizable(string2, i7, i8);
                    return true;
                case 7:
                    onActivityDismissingDockedTask();
                    return true;
                case 8:
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayFailed(runningTaskInfo2, i9);
                    return true;
                case 9:
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayRerouted(runningTaskInfo3, i10);
                    return true;
                case 10:
                    int i11 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskCreated(i11, componentName);
                    return true;
                case 11:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskRemoved(i12);
                    return true;
                case 12:
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskMovedToFront(runningTaskInfo4);
                    return true;
                case 13:
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskDescriptionChanged(runningTaskInfo5);
                    return true;
                case 14:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityRequestedOrientationChanged(i13, i14);
                    return true;
                case 15:
                    ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskRemovalStarted(runningTaskInfo6);
                    return true;
                case 16:
                    ActivityManager.RunningTaskInfo runningTaskInfo7 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskProfileLocked(runningTaskInfo7, i15);
                    return true;
                case 17:
                    int i16 = parcel.readInt();
                    TaskSnapshot taskSnapshot = (TaskSnapshot) parcel.readTypedObject(TaskSnapshot.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskSnapshotChanged(i16, taskSnapshot);
                    return true;
                case 18:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskSnapshotInvalidated(i17);
                    return true;
                case 19:
                    ActivityManager.RunningTaskInfo runningTaskInfo8 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackPressedOnTaskRoot(runningTaskInfo8);
                    return true;
                case 20:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskDisplayChanged(i18, i19);
                    return true;
                case 21:
                    onRecentTaskListUpdated();
                    return true;
                case 22:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRecentTaskListFrozenChanged(z4);
                    return true;
                case 23:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecentTaskRemovedForAddTask(i20);
                    return true;
                case 24:
                    int i21 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTaskFocusChanged(i21, z5);
                    return true;
                case 25:
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskRequestedOrientationChanged(i22, i23);
                    return true;
                case 26:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityRotation(i24);
                    return true;
                case 27:
                    ActivityManager.RunningTaskInfo runningTaskInfo9 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskMovedToBack(runningTaskInfo9);
                    return true;
                case 28:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLockTaskModeChanged(i25);
                    return true;
                case 29:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTaskbarIconVisibleChangeRequest(componentName2, z6);
                    return true;
                case 30:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskWindowingModeChanged(i26);
                    return true;
                case 31:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onActivityDismissingSplitTask(string3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITaskStackListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskStackChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityUnpinned() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityForcedResizable(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingDockedTask() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskCreated(int i, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(taskSnapshot, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotInvalidated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListUpdated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListFrozenChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskRemovedForAddTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskFocusChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRequestedOrientationChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRotation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onLockTaskModeChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskWindowingModeChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingSplitTask(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
