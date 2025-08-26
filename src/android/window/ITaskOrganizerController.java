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
        public void createDeskRootTask(int i, int i2, int i3, int i4, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
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

    void createDeskRootTask(int i, int i2, int i3, int i4, IBinder iBinder, boolean z, boolean z2) throws RemoteException;

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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITaskOrganizerController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITaskOrganizerController)) {
                return (ITaskOrganizerController) iInterfaceQueryLocalInterface;
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
                    ITaskOrganizer iTaskOrganizerAsInterface = ITaskOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<TaskAppearedInfo> parceledListSliceRegisterTaskOrganizer = registerTaskOrganizer(iTaskOrganizerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceRegisterTaskOrganizer, 1);
                    return true;
                case 2:
                    ITaskOrganizer iTaskOrganizerAsInterface2 = ITaskOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskOrganizer(iTaskOrganizerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createRootTask(i3, i4, strongBinder, z, z2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createStageRootTask(i5, i6, i7, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createDeskRootTask(i8, i9, i10, i11, strongBinder3, z3, z4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDeleteRootTask = deleteRootTask(windowContainerToken);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteRootTask);
                    return true;
                case 7:
                    WindowContainerToken windowContainerToken2 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> childTasks = getChildTasks(windowContainerToken2, iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(childTasks, 1);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    List<ActivityManager.RunningTaskInfo> rootTasks = getRootTasks(i12, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(rootTasks, 1);
                    return true;
                case 9:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowContainerToken imeTarget = getImeTarget(i13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imeTarget, 1);
                    return true;
                case 10:
                    WindowContainerToken windowContainerToken3 = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterceptBackPressedOnTaskRoot(windowContainerToken3, z5);
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
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFreeformTaskSurfaceOverlappedWithNavi(windowContainerToken5, z6);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskOrganizer);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void unregisterTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskOrganizer);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createRootTask(int i, int i2, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createStageRootTask(int i, int i2, int i3, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void createDeskRootTask(int i, int i2, int i3, int i4, IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public boolean deleteRootTask(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public List<ActivityManager.RunningTaskInfo> getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public List<ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public WindowContainerToken getImeTarget(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowContainerToken) parcelObtain2.readTypedObject(WindowContainerToken.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void setInterceptBackPressedOnTaskRoot(WindowContainerToken windowContainerToken, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizerController
            public void setFreeformTaskSurfaceOverlappedWithNavi(WindowContainerToken windowContainerToken, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
