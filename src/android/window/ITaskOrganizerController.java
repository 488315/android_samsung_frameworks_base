package android.window;

import android.app.ActivityManager;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.ITaskOrganizer;
import java.util.List;

/* loaded from: classes5.dex */
public interface ITaskOrganizerController extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITaskOrganizerController";

    public static class Default implements ITaskOrganizerController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public void createDeskRootTask(int i, int i2, int i3, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void createRootTask(int i, int i2, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void createStageRootTask(int i, int i2, int i3, IBinder iBinder) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public boolean deleteRootTask(WindowContainerToken windowContainerToken) throws RemoteException {
            return false;
        }

        @Override // android.window.ITaskOrganizerController
        public List<ActivityManager.RunningTaskInfo> getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public WindowContainerToken getImeTarget(int i) throws RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public List<ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException {
            return null;
        }

        @Override // android.window.ITaskOrganizerController
        public void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void setFreeformTaskSurfaceOverlappedWithNavi(WindowContainerToken windowContainerToken, boolean z) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void setInterceptBackPressedOnTaskRoot(WindowContainerToken windowContainerToken, boolean z) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizerController
        public void unregisterTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException {
        }
    }

    void createDeskRootTask(int i, int i2, int i3, IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    void createRootTask(int i, int i2, IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    void createStageRootTask(int i, int i2, int i3, IBinder iBinder) throws RemoteException;

    boolean deleteRootTask(WindowContainerToken windowContainerToken) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) throws RemoteException;

    WindowContainerToken getImeTarget(int i) throws RemoteException;

    List<ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws RemoteException;

    ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException;

    void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken) throws RemoteException;

    void setFreeformTaskSurfaceOverlappedWithNavi(WindowContainerToken windowContainerToken, boolean z) throws RemoteException;

    void setInterceptBackPressedOnTaskRoot(WindowContainerToken windowContainerToken, boolean z) throws RemoteException;

    void unregisterTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskOrganizerController {
        static final int TRANSACTION_createDeskRootTask = 5;
        static final int TRANSACTION_createRootTask = 3;
        static final int TRANSACTION_createStageRootTask = 4;
        static final int TRANSACTION_deleteRootTask = 6;
        static final int TRANSACTION_getChildTasks = 7;
        static final int TRANSACTION_getImeTarget = 9;
        static final int TRANSACTION_getRootTasks = 8;
        static final int TRANSACTION_registerTaskOrganizer = 1;
        static final int TRANSACTION_restartTaskTopActivityProcessIfVisible = 11;
        static final int TRANSACTION_setFreeformTaskSurfaceOverlappedWithNavi = 12;
        static final int TRANSACTION_setInterceptBackPressedOnTaskRoot = 10;
        static final int TRANSACTION_unregisterTaskOrganizer = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, ITaskOrganizerController.DESCRIPTOR);
        }

        public static ITaskOrganizerController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITaskOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITaskOrganizerController)) {
                return (ITaskOrganizerController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerTaskOrganizer";
                case 2:
                    return "unregisterTaskOrganizer";
                case 3:
                    return "createRootTask";
                case 4:
                    return "createStageRootTask";
                case 5:
                    return "createDeskRootTask";
                case 6:
                    return "deleteRootTask";
                case 7:
                    return "getChildTasks";
                case 8:
                    return "getRootTasks";
                case 9:
                    return "getImeTarget";
                case 10:
                    return "setInterceptBackPressedOnTaskRoot";
                case 11:
                    return "restartTaskTopActivityProcessIfVisible";
                case 12:
                    return "setFreeformTaskSurfaceOverlappedWithNavi";
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
                parcel.enforceInterface(ITaskOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITaskOrganizer asInterface = ITaskOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer = registerTaskOrganizer(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerTaskOrganizer, 1);
                    return true;
                case 2:
                    ITaskOrganizer asInterface2 = ITaskOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskOrganizer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createRootTask(readInt, readInt2, readStrongBinder, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createStageRootTask(readInt3, readInt4, readInt5, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createDeskRootTask(readInt6, readInt7, readInt8, readStrongBinder3, readBoolean3, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteRootTask = deleteRootTask(windowContainerToken);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteRootTask);
                    return true;
                case 7:
                    WindowContainerToken windowContainerToken2 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> childTasks = getChildTasks(windowContainerToken2, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(childTasks, 1);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> rootTasks = getRootTasks(readInt9, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(rootTasks, 1);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowContainerToken imeTarget = getImeTarget(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imeTarget, 1);
                    return true;
                case 10:
                    WindowContainerToken windowContainerToken3 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterceptBackPressedOnTaskRoot(windowContainerToken3, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    WindowContainerToken windowContainerToken4 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    restartTaskTopActivityProcessIfVisible(windowContainerToken4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    WindowContainerToken windowContainerToken5 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFreeformTaskSurfaceOverlappedWithNavi(windowContainerToken5, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITaskOrganizerController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.ITaskOrganizerController
            public ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskOrganizer);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void unregisterTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskOrganizer);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createRootTask(int i, int i2, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createStageRootTask(int i, int i2, int i3, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createDeskRootTask(int i, int i2, int i3, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public boolean deleteRootTask(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public List<ActivityManager.RunningTaskInfo> getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public List<ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public WindowContainerToken getImeTarget(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WindowContainerToken) obtain2.readTypedObject(WindowContainerToken.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void setInterceptBackPressedOnTaskRoot(WindowContainerToken windowContainerToken, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void setFreeformTaskSurfaceOverlappedWithNavi(WindowContainerToken windowContainerToken, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
