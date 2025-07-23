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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDisplayAreaOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDisplayAreaOrganizerController)) {
                return (IDisplayAreaOrganizerController) queryLocalInterface;
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
                IDisplayAreaOrganizer asInterface = IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                ParceledListSlice<DisplayAreaAppearedInfo> registerOrganizer = registerOrganizer(asInterface, readInt);
                parcel2.writeNoException();
                parcel2.writeTypedObject(registerOrganizer, 1);
            } else if (i == 2) {
                IDisplayAreaOrganizer asInterface2 = IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterOrganizer(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                IDisplayAreaOrganizer asInterface3 = IDisplayAreaOrganizer.Stub.asInterface(parcel.readStrongBinder());
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                DisplayAreaAppearedInfo createTaskDisplayArea = createTaskDisplayArea(asInterface3, readInt2, readInt3, readString);
                parcel2.writeNoException();
                parcel2.writeTypedObject(createTaskDisplayArea, 1);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayAreaOrganizer);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public void unregisterOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayAreaOrganizer);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public DisplayAreaAppearedInfo createTaskDisplayArea(IDisplayAreaOrganizer iDisplayAreaOrganizer, int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayAreaOrganizer);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (DisplayAreaAppearedInfo) obtain2.readTypedObject(DisplayAreaAppearedInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizerController
            public void deleteTaskDisplayArea(WindowContainerToken windowContainerToken) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDisplayAreaOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerToken, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
