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
        static final int TRANSACTION_onActivityDismissingDockedTask = 6;
        static final int TRANSACTION_onActivityDismissingSplitTask = 30;
        static final int TRANSACTION_onActivityForcedResizable = 5;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayFailed = 7;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayRerouted = 8;
        static final int TRANSACTION_onActivityPinned = 2;
        static final int TRANSACTION_onActivityRequestedOrientationChanged = 13;
        static final int TRANSACTION_onActivityRestartAttempt = 4;
        static final int TRANSACTION_onActivityRotation = 25;
        static final int TRANSACTION_onActivityUnpinned = 3;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 18;
        static final int TRANSACTION_onLockTaskModeChanged = 27;
        static final int TRANSACTION_onRecentTaskListFrozenChanged = 21;
        static final int TRANSACTION_onRecentTaskListUpdated = 20;
        static final int TRANSACTION_onRecentTaskRemovedForAddTask = 22;
        static final int TRANSACTION_onTaskCreated = 9;
        static final int TRANSACTION_onTaskDescriptionChanged = 12;
        static final int TRANSACTION_onTaskDisplayChanged = 19;
        static final int TRANSACTION_onTaskFocusChanged = 23;
        static final int TRANSACTION_onTaskMovedToBack = 26;
        static final int TRANSACTION_onTaskMovedToFront = 11;
        static final int TRANSACTION_onTaskProfileLocked = 15;
        static final int TRANSACTION_onTaskRemovalStarted = 14;
        static final int TRANSACTION_onTaskRemoved = 10;
        static final int TRANSACTION_onTaskRequestedOrientationChanged = 24;
        static final int TRANSACTION_onTaskSnapshotChanged = 16;
        static final int TRANSACTION_onTaskSnapshotInvalidated = 17;
        static final int TRANSACTION_onTaskStackChanged = 1;
        static final int TRANSACTION_onTaskWindowingModeChanged = 29;
        static final int TRANSACTION_onTaskbarIconVisibleChangeRequest = 28;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITaskStackListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITaskStackListener)) {
                return (ITaskStackListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTaskStackChanged";
                case 2:
                    return "onActivityPinned";
                case 3:
                    return "onActivityUnpinned";
                case 4:
                    return "onActivityRestartAttempt";
                case 5:
                    return "onActivityForcedResizable";
                case 6:
                    return "onActivityDismissingDockedTask";
                case 7:
                    return "onActivityLaunchOnSecondaryDisplayFailed";
                case 8:
                    return "onActivityLaunchOnSecondaryDisplayRerouted";
                case 9:
                    return "onTaskCreated";
                case 10:
                    return "onTaskRemoved";
                case 11:
                    return "onTaskMovedToFront";
                case 12:
                    return "onTaskDescriptionChanged";
                case 13:
                    return "onActivityRequestedOrientationChanged";
                case 14:
                    return "onTaskRemovalStarted";
                case 15:
                    return "onTaskProfileLocked";
                case 16:
                    return "onTaskSnapshotChanged";
                case 17:
                    return "onTaskSnapshotInvalidated";
                case 18:
                    return "onBackPressedOnTaskRoot";
                case 19:
                    return "onTaskDisplayChanged";
                case 20:
                    return "onRecentTaskListUpdated";
                case 21:
                    return "onRecentTaskListFrozenChanged";
                case 22:
                    return "onRecentTaskRemovedForAddTask";
                case 23:
                    return "onTaskFocusChanged";
                case 24:
                    return "onTaskRequestedOrientationChanged";
                case 25:
                    return "onActivityRotation";
                case 26:
                    return "onTaskMovedToBack";
                case 27:
                    return "onLockTaskModeChanged";
                case 28:
                    return "onTaskbarIconVisibleChangeRequest";
                case 29:
                    return "onTaskWindowingModeChanged";
                case 30:
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
                    onTaskStackChanged();
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityPinned(readString, readInt, readInt2, readInt3);
                    return true;
                case 3:
                    onActivityUnpinned();
                    return true;
                case 4:
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onActivityRestartAttempt(runningTaskInfo, readBoolean, readBoolean2, readBoolean3);
                    return true;
                case 5:
                    String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityForcedResizable(readString2, readInt4, readInt5);
                    return true;
                case 6:
                    onActivityDismissingDockedTask();
                    return true;
                case 7:
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayFailed(runningTaskInfo2, readInt6);
                    return true;
                case 8:
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayRerouted(runningTaskInfo3, readInt7);
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskCreated(readInt8, componentName);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskRemoved(readInt9);
                    return true;
                case 11:
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskMovedToFront(runningTaskInfo4);
                    return true;
                case 12:
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskDescriptionChanged(runningTaskInfo5);
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityRequestedOrientationChanged(readInt10, readInt11);
                    return true;
                case 14:
                    ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskRemovalStarted(runningTaskInfo6);
                    return true;
                case 15:
                    ActivityManager.RunningTaskInfo runningTaskInfo7 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskProfileLocked(runningTaskInfo7, readInt12);
                    return true;
                case 16:
                    int readInt13 = parcel.readInt();
                    TaskSnapshot taskSnapshot = (TaskSnapshot) parcel.readTypedObject(TaskSnapshot.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskSnapshotChanged(readInt13, taskSnapshot);
                    return true;
                case 17:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskSnapshotInvalidated(readInt14);
                    return true;
                case 18:
                    ActivityManager.RunningTaskInfo runningTaskInfo8 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackPressedOnTaskRoot(runningTaskInfo8);
                    return true;
                case 19:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskDisplayChanged(readInt15, readInt16);
                    return true;
                case 20:
                    onRecentTaskListUpdated();
                    return true;
                case 21:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRecentTaskListFrozenChanged(readBoolean4);
                    return true;
                case 22:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecentTaskRemovedForAddTask(readInt17);
                    return true;
                case 23:
                    int readInt18 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTaskFocusChanged(readInt18, readBoolean5);
                    return true;
                case 24:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskRequestedOrientationChanged(readInt19, readInt20);
                    return true;
                case 25:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActivityRotation(readInt21);
                    return true;
                case 26:
                    ActivityManager.RunningTaskInfo runningTaskInfo9 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskMovedToBack(runningTaskInfo9);
                    return true;
                case 27:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLockTaskModeChanged(readInt22);
                    return true;
                case 28:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTaskbarIconVisibleChangeRequest(componentName2, readBoolean6);
                    return true;
                case 29:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTaskWindowingModeChanged(readInt23);
                    return true;
                case 30:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onActivityDismissingSplitTask(readString3);
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
            public void onTaskStackChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityUnpinned() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityForcedResizable(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingDockedTask() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskCreated(int i, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemoved(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(taskSnapshot, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotInvalidated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListUpdated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListFrozenChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskRemovedForAddTask(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskFocusChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRequestedOrientationChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRotation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onLockTaskModeChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskWindowingModeChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingSplitTask(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
