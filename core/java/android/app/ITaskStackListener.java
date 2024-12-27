package android.app;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.TaskSnapshot;

public interface ITaskStackListener extends IInterface {
    public static final int FORCED_RESIZEABLE_REASON_DEX_FREEFORM = 4;
    public static final int FORCED_RESIZEABLE_REASON_FREEFORM = 3;
    public static final int FORCED_RESIZEABLE_REASON_SECONDARY_DISPLAY = 2;
    public static final int FORCED_RESIZEABLE_REASON_SPLIT_SCREEN = 1;

    void onActivityDismissingDockedTask() throws RemoteException;

    void onActivityDismissingSplitTask(String str) throws RemoteException;

    void onActivityForcedResizable(String str, int i, int i2) throws RemoteException;

    void onActivityLaunchOnSecondaryDisplayFailed(
            ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onActivityLaunchOnSecondaryDisplayRerouted(
            ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void onActivityPinned(String str, int i, int i2, int i3) throws RemoteException;

    void onActivityRequestedOrientationChanged(int i, int i2) throws RemoteException;

    void onActivityRestartAttempt(
            ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3)
            throws RemoteException;

    void onActivityRotation(int i) throws RemoteException;

    void onActivityUnpinned() throws RemoteException;

    void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo)
            throws RemoteException;

    void onLockTaskModeChanged(int i) throws RemoteException;

    void onRecentTaskListFrozenChanged(boolean z) throws RemoteException;

    void onRecentTaskListUpdated() throws RemoteException;

    void onTaskCreated(int i, ComponentName componentName) throws RemoteException;

    void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo)
            throws RemoteException;

    void onTaskDisplayChanged(int i, int i2) throws RemoteException;

    void onTaskFocusChanged(int i, boolean z) throws RemoteException;

    void onTaskMovedToBack(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo, int i)
            throws RemoteException;

    void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo)
            throws RemoteException;

    void onTaskRemoved(int i) throws RemoteException;

    void onTaskRequestedOrientationChanged(int i, int i2) throws RemoteException;

    void onTaskSnapshotChanged(int i, TaskSnapshot taskSnapshot) throws RemoteException;

    void onTaskSnapshotInvalidated(int i) throws RemoteException;

    void onTaskStackChanged() throws RemoteException;

    void onTaskWindowingModeChanged(int i) throws RemoteException;

    void onTaskbarIconVisibleChangeRequest(ComponentName componentName, boolean z)
            throws RemoteException;

    public static class Default implements ITaskStackListener {
        @Override // android.app.ITaskStackListener
        public void onTaskStackChanged() throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityPinned(String packageName, int userId, int taskId, int stackId)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityUnpinned() throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityRestartAttempt(
                ActivityManager.RunningTaskInfo task,
                boolean homeTaskVisible,
                boolean clearedTask,
                boolean wasVisible)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityForcedResizable(String packageName, int taskId, int reason)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityDismissingDockedTask() throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityLaunchOnSecondaryDisplayFailed(
                ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityLaunchOnSecondaryDisplayRerouted(
                ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskCreated(int taskId, ComponentName componentName) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskRemoved(int taskId) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo taskInfo)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo taskInfo)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityRequestedOrientationChanged(int taskId, int requestedOrientation)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo taskInfo)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskProfileLocked(ActivityManager.RunningTaskInfo taskInfo, int userId)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskSnapshotChanged(int taskId, TaskSnapshot snapshot)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskSnapshotInvalidated(int taskId) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onRecentTaskListUpdated() throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onRecentTaskListFrozenChanged(boolean frozen) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskFocusChanged(int taskId, boolean focused) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskRequestedOrientationChanged(int taskId, int requestedOrientation)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityRotation(int displayId) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskMovedToBack(ActivityManager.RunningTaskInfo taskInfo)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onLockTaskModeChanged(int mode) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskbarIconVisibleChangeRequest(ComponentName cn, boolean visible)
                throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onTaskWindowingModeChanged(int taskId) throws RemoteException {}

        @Override // android.app.ITaskStackListener
        public void onActivityDismissingSplitTask(String packageName) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITaskStackListener {
        public static final String DESCRIPTOR = "android.app.ITaskStackListener";
        static final int TRANSACTION_onActivityDismissingDockedTask = 6;
        static final int TRANSACTION_onActivityDismissingSplitTask = 29;
        static final int TRANSACTION_onActivityForcedResizable = 5;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayFailed = 7;
        static final int TRANSACTION_onActivityLaunchOnSecondaryDisplayRerouted = 8;
        static final int TRANSACTION_onActivityPinned = 2;
        static final int TRANSACTION_onActivityRequestedOrientationChanged = 13;
        static final int TRANSACTION_onActivityRestartAttempt = 4;
        static final int TRANSACTION_onActivityRotation = 24;
        static final int TRANSACTION_onActivityUnpinned = 3;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 18;
        static final int TRANSACTION_onLockTaskModeChanged = 26;
        static final int TRANSACTION_onRecentTaskListFrozenChanged = 21;
        static final int TRANSACTION_onRecentTaskListUpdated = 20;
        static final int TRANSACTION_onTaskCreated = 9;
        static final int TRANSACTION_onTaskDescriptionChanged = 12;
        static final int TRANSACTION_onTaskDisplayChanged = 19;
        static final int TRANSACTION_onTaskFocusChanged = 22;
        static final int TRANSACTION_onTaskMovedToBack = 25;
        static final int TRANSACTION_onTaskMovedToFront = 11;
        static final int TRANSACTION_onTaskProfileLocked = 15;
        static final int TRANSACTION_onTaskRemovalStarted = 14;
        static final int TRANSACTION_onTaskRemoved = 10;
        static final int TRANSACTION_onTaskRequestedOrientationChanged = 23;
        static final int TRANSACTION_onTaskSnapshotChanged = 16;
        static final int TRANSACTION_onTaskSnapshotInvalidated = 17;
        static final int TRANSACTION_onTaskStackChanged = 1;
        static final int TRANSACTION_onTaskWindowingModeChanged = 28;
        static final int TRANSACTION_onTaskbarIconVisibleChangeRequest = 27;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITaskStackListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITaskStackListener)) {
                return (ITaskStackListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "onTaskFocusChanged";
                case 23:
                    return "onTaskRequestedOrientationChanged";
                case 24:
                    return "onActivityRotation";
                case 25:
                    return "onTaskMovedToBack";
                case 26:
                    return "onLockTaskModeChanged";
                case 27:
                    return "onTaskbarIconVisibleChangeRequest";
                case 28:
                    return "onTaskWindowingModeChanged";
                case 29:
                    return "onActivityDismissingSplitTask";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onTaskStackChanged();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onActivityPinned(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 3:
                    onActivityUnpinned();
                    return true;
                case 4:
                    ActivityManager.RunningTaskInfo _arg02 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    boolean _arg12 = data.readBoolean();
                    boolean _arg22 = data.readBoolean();
                    boolean _arg32 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onActivityRestartAttempt(_arg02, _arg12, _arg22, _arg32);
                    return true;
                case 5:
                    String _arg03 = data.readString();
                    int _arg13 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    onActivityForcedResizable(_arg03, _arg13, _arg23);
                    return true;
                case 6:
                    onActivityDismissingDockedTask();
                    return true;
                case 7:
                    ActivityManager.RunningTaskInfo _arg04 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayFailed(_arg04, _arg14);
                    return true;
                case 8:
                    ActivityManager.RunningTaskInfo _arg05 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    onActivityLaunchOnSecondaryDisplayRerouted(_arg05, _arg15);
                    return true;
                case 9:
                    int _arg06 = data.readInt();
                    ComponentName _arg16 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    onTaskCreated(_arg06, _arg16);
                    return true;
                case 10:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    onTaskRemoved(_arg07);
                    return true;
                case 11:
                    ActivityManager.RunningTaskInfo _arg08 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onTaskMovedToFront(_arg08);
                    return true;
                case 12:
                    ActivityManager.RunningTaskInfo _arg09 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onTaskDescriptionChanged(_arg09);
                    return true;
                case 13:
                    int _arg010 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    onActivityRequestedOrientationChanged(_arg010, _arg17);
                    return true;
                case 14:
                    ActivityManager.RunningTaskInfo _arg011 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onTaskRemovalStarted(_arg011);
                    return true;
                case 15:
                    ActivityManager.RunningTaskInfo _arg012 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    onTaskProfileLocked(_arg012, _arg18);
                    return true;
                case 16:
                    int _arg013 = data.readInt();
                    TaskSnapshot _arg19 = (TaskSnapshot) data.readTypedObject(TaskSnapshot.CREATOR);
                    data.enforceNoDataAvail();
                    onTaskSnapshotChanged(_arg013, _arg19);
                    return true;
                case 17:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    onTaskSnapshotInvalidated(_arg014);
                    return true;
                case 18:
                    ActivityManager.RunningTaskInfo _arg015 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onBackPressedOnTaskRoot(_arg015);
                    return true;
                case 19:
                    int _arg016 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    onTaskDisplayChanged(_arg016, _arg110);
                    return true;
                case 20:
                    onRecentTaskListUpdated();
                    return true;
                case 21:
                    boolean _arg017 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onRecentTaskListFrozenChanged(_arg017);
                    return true;
                case 22:
                    int _arg018 = data.readInt();
                    boolean _arg111 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onTaskFocusChanged(_arg018, _arg111);
                    return true;
                case 23:
                    int _arg019 = data.readInt();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    onTaskRequestedOrientationChanged(_arg019, _arg112);
                    return true;
                case 24:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    onActivityRotation(_arg020);
                    return true;
                case 25:
                    ActivityManager.RunningTaskInfo _arg021 =
                            (ActivityManager.RunningTaskInfo)
                                    data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onTaskMovedToBack(_arg021);
                    return true;
                case 26:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    onLockTaskModeChanged(_arg022);
                    return true;
                case 27:
                    ComponentName _arg023 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg113 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onTaskbarIconVisibleChangeRequest(_arg023, _arg113);
                    return true;
                case 28:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    onTaskWindowingModeChanged(_arg024);
                    return true;
                case 29:
                    String _arg025 = data.readString();
                    data.enforceNoDataAvail();
                    onActivityDismissingSplitTask(_arg025);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITaskStackListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityPinned(String packageName, int userId, int taskId, int stackId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(taskId);
                    _data.writeInt(stackId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityUnpinned() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRestartAttempt(
                    ActivityManager.RunningTaskInfo task,
                    boolean homeTaskVisible,
                    boolean clearedTask,
                    boolean wasVisible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(task, 0);
                    _data.writeBoolean(homeTaskVisible);
                    _data.writeBoolean(clearedTask);
                    _data.writeBoolean(wasVisible);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityForcedResizable(String packageName, int taskId, int reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(taskId);
                    _data.writeInt(reason);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingDockedTask() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayFailed(
                    ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    _data.writeInt(requestedDisplayId);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityLaunchOnSecondaryDisplayRerouted(
                    ActivityManager.RunningTaskInfo taskInfo, int requestedDisplayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    _data.writeInt(requestedDisplayId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskCreated(int taskId, ComponentName componentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemoved(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToFront(ActivityManager.RunningTaskInfo taskInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo taskInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRequestedOrientationChanged(int taskId, int requestedOrientation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(requestedOrientation);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo taskInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskProfileLocked(ActivityManager.RunningTaskInfo taskInfo, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotChanged(int taskId, TaskSnapshot snapshot)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeTypedObject(snapshot, 0);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskSnapshotInvalidated(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskDisplayChanged(int taskId, int newDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(newDisplayId);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListUpdated() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onRecentTaskListFrozenChanged(boolean frozen) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(frozen);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskFocusChanged(int taskId, boolean focused) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeBoolean(focused);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskRequestedOrientationChanged(int taskId, int requestedOrientation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(requestedOrientation);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityRotation(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskMovedToBack(ActivityManager.RunningTaskInfo taskInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(taskInfo, 0);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onLockTaskModeChanged(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskbarIconVisibleChangeRequest(ComponentName cn, boolean visible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(cn, 0);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onTaskWindowingModeChanged(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ITaskStackListener
            public void onActivityDismissingSplitTask(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
