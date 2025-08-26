package android.window;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.window.IDisplayAreaOrganizer;

/* loaded from: classes5.dex */
public interface IDisplayAreaOrganizerController extends IInterface {
    public static final String DESCRIPTOR = "android.window.IDisplayAreaOrganizerController";

    public static class Default implements IDisplayAreaOrganizerController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public DisplayAreaAppearedInfo createTaskDisplayArea(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public void deleteTaskDisplayArea(WindowContainerToken windowContainerToken) throws RemoteException {
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public ParceledListSlice<DisplayAreaAppearedInfo> registerOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) throws RemoteException {
            return null;
        }

        @Override // android.window.IDisplayAreaOrganizerController
        public void unregisterOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer) throws RemoteException {
        }
    }

    DisplayAreaAppearedInfo createTaskDisplayArea(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, String str) throws RemoteException;

    void deleteTaskDisplayArea(WindowContainerToken windowContainerToken) throws RemoteException;

    ParceledListSlice<DisplayAreaAppearedInfo> registerOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) throws RemoteException;

    void unregisterOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayAreaOrganizerController {
        static final int TRANSACTION_createTaskDisplayArea = 3;
        static final int TRANSACTION_deleteTaskDisplayArea = 4;
        static final int TRANSACTION_registerOrganizer = 1;
        static final int TRANSACTION_unregisterOrganizer = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IDisplayAreaOrganizerController.DESCRIPTOR);
        }

        public static IDisplayAreaOrganizerController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDisplayAreaOrganizerController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayAreaOrganizerController)) {
                return (IDisplayAreaOrganizerController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerOrganizer";
            }
            if (i == 2) {
                return "unregisterOrganizer";
            }
            if (i == 3) {
                return "createTaskDisplayArea";
            }
            if (i != 4) {
                return null;
            }
            return "deleteTaskDisplayArea";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDisplayAreaOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayAreaOrganizerController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IDisplayAreaOrganizer iDisplayAreaOrganizerAsInterface = IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ParceledListSlice<DisplayAreaAppearedInfo> parceledListSliceRegisterOrganizer = registerOrganizer(iDisplayAreaOrganizerAsInterface, i3);
                parcel2.writeNoException();
                parcel2.writeTypedObject(parceledListSliceRegisterOrganizer, 1);
            } else if (i == 2) {
                IDisplayAreaOrganizer iDisplayAreaOrganizerAsInterface2 = IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterOrganizer(iDisplayAreaOrganizerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                IDisplayAreaOrganizer iDisplayAreaOrganizerAsInterface3 = IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                DisplayAreaAppearedInfo displayAreaAppearedInfoCreateTaskDisplayArea = createTaskDisplayArea(iDisplayAreaOrganizerAsInterface3, i4, i5, string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(displayAreaAppearedInfoCreateTaskDisplayArea, 1);
            } else if (i == 4) {
                WindowContainerToken windowContainerToken = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
                parcel.enforceNoDataAvail();
                deleteTaskDisplayArea(windowContainerToken);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDisplayAreaOrganizerController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayAreaOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public ParceledListSlice<DisplayAreaAppearedInfo> registerOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayAreaOrganizer);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public void unregisterOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayAreaOrganizer);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public DisplayAreaAppearedInfo createTaskDisplayArea(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDisplayAreaOrganizer);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (DisplayAreaAppearedInfo) parcelObtain2.readTypedObject(DisplayAreaAppearedInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public void deleteTaskDisplayArea(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
