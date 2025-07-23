package android.window;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.RemoteAnimationDefinition;
import android.window.ITaskFragmentOrganizer;

/* loaded from: classes5.dex */
public interface ITaskFragmentOrganizerController extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITaskFragmentOrganizerController";

    public static class Default implements ITaskFragmentOrganizerController {
        @Override // android.window.ITaskFragmentOrganizerController
        public void applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, boolean z, RemoteTransition remoteTransition) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public boolean isSupportActivityEmbedded(String str) throws RemoteException {
            return false;
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void onTransactionHandled(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void registerOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void registerRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void setSavedState(ITaskFragmentOrganizer iTaskFragmentOrganizer, Bundle bundle) throws RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void unregisterOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException {
        }

        @Override // android.window.ITaskFragmentOrganizerController
        public void unregisterRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException {
        }
    }

    void applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, boolean z, RemoteTransition remoteTransition) throws RemoteException;

    boolean isSupportActivityEmbedded(String str) throws RemoteException;

    void onTransactionHandled(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws RemoteException;

    void registerOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z, Bundle bundle) throws RemoteException;

    void registerRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException;

    void setSavedState(ITaskFragmentOrganizer iTaskFragmentOrganizer, Bundle bundle) throws RemoteException;

    void unregisterOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException;

    void unregisterRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskFragmentOrganizerController {
        static final int TRANSACTION_applyTransaction = 7;
        static final int TRANSACTION_isSupportActivityEmbedded = 8;
        static final int TRANSACTION_onTransactionHandled = 6;
        static final int TRANSACTION_registerOrganizer = 1;
        static final int TRANSACTION_registerRemoteAnimations = 3;
        static final int TRANSACTION_setSavedState = 5;
        static final int TRANSACTION_unregisterOrganizer = 2;
        static final int TRANSACTION_unregisterRemoteAnimations = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, ITaskFragmentOrganizerController.DESCRIPTOR);
        }

        public static ITaskFragmentOrganizerController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITaskFragmentOrganizerController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITaskFragmentOrganizerController)) {
                return (ITaskFragmentOrganizerController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerOrganizer";
                case 2:
                    return "unregisterOrganizer";
                case 3:
                    return "registerRemoteAnimations";
                case 4:
                    return "unregisterRemoteAnimations";
                case 5:
                    return "setSavedState";
                case 6:
                    return "onTransactionHandled";
                case 7:
                    return "applyTransaction";
                case 8:
                    return "isSupportActivityEmbedded";
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
                parcel.enforceInterface(ITaskFragmentOrganizerController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskFragmentOrganizerController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITaskFragmentOrganizer asInterface = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    Bundle bundle = new Bundle();
                    parcel.enforceNoDataAvail();
                    registerOrganizer(asInterface, readBoolean, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    ITaskFragmentOrganizer asInterface2 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterOrganizer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITaskFragmentOrganizer asInterface3 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    RemoteAnimationDefinition remoteAnimationDefinition = (RemoteAnimationDefinition) parcel.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimations(asInterface3, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ITaskFragmentOrganizer asInterface4 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAnimations(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ITaskFragmentOrganizer asInterface5 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSavedState(asInterface5, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTransactionHandled(readStrongBinder, windowContainerTransaction, readInt, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    WindowContainerTransaction windowContainerTransaction2 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    int readInt2 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyTransaction(windowContainerTransaction2, readInt2, readBoolean3, remoteTransition);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSupportActivityEmbedded = isSupportActivityEmbedded(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportActivityEmbedded);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITaskFragmentOrganizerController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskFragmentOrganizerController.DESCRIPTOR;
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void registerOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void unregisterOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void registerRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    obtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void unregisterRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void setSavedState(ITaskFragmentOrganizer iTaskFragmentOrganizer, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFragmentOrganizer);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void onTransactionHandled(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, boolean z, RemoteTransition remoteTransition) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(remoteTransition, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public boolean isSupportActivityEmbedded(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
