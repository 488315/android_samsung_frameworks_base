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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITaskFragmentOrganizerController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITaskFragmentOrganizerController)) {
                return (ITaskFragmentOrganizerController) iInterfaceQueryLocalInterface;
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
                    ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    boolean z = parcel.readBoolean();
                    Bundle bundle = new Bundle();
                    parcel.enforceNoDataAvail();
                    registerOrganizer(iTaskFragmentOrganizerAsInterface, z, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundle, 1);
                    return true;
                case 2:
                    ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface2 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterOrganizer(iTaskFragmentOrganizerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface3 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    RemoteAnimationDefinition remoteAnimationDefinition = (RemoteAnimationDefinition) parcel.readTypedObject(RemoteAnimationDefinition.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerRemoteAnimations(iTaskFragmentOrganizerAsInterface3, remoteAnimationDefinition);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface4 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteAnimations(iTaskFragmentOrganizerAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ITaskFragmentOrganizer iTaskFragmentOrganizerAsInterface5 = ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSavedState(iTaskFragmentOrganizerAsInterface5, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder = parcel.readStrongBinder();
                    WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    int i3 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTransactionHandled(strongBinder, windowContainerTransaction, i3, z2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    WindowContainerTransaction windowContainerTransaction2 = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                    int i4 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    RemoteTransition remoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyTransaction(windowContainerTransaction2, i4, z3, remoteTransition);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSupportActivityEmbedded = isSupportActivityEmbedded(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportActivityEmbedded);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFragmentOrganizer);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void unregisterOrganizer(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFragmentOrganizer);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void registerRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer, RemoteAnimationDefinition remoteAnimationDefinition) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFragmentOrganizer);
                    parcelObtain.writeTypedObject(remoteAnimationDefinition, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void unregisterRemoteAnimations(ITaskFragmentOrganizer iTaskFragmentOrganizer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFragmentOrganizer);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void setSavedState(ITaskFragmentOrganizer iTaskFragmentOrganizer, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskFragmentOrganizer);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void onTransactionHandled(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(windowContainerTransaction, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public void applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, boolean z, RemoteTransition remoteTransition) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(windowContainerTransaction, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(remoteTransition, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskFragmentOrganizerController
            public boolean isSupportActivityEmbedded(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITaskFragmentOrganizerController.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
