package com.android.internal.app;

import android.app.VoiceInteractor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractorRequest;

/* loaded from: classes5.dex */
public interface IVoiceInteractorCallback extends IInterface {

    public static class Default implements IVoiceInteractorCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void destroy() throws RemoteException {
        }
    }

    void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException;

    void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) throws RemoteException;

    void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) throws RemoteException;

    void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException;

    void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) throws RemoteException;

    void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) throws RemoteException;

    void destroy() throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractorCallback {
        public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractorCallback";
        static final int TRANSACTION_deliverAbortVoiceResult = 4;
        static final int TRANSACTION_deliverCancel = 6;
        static final int TRANSACTION_deliverCommandResult = 5;
        static final int TRANSACTION_deliverCompleteVoiceResult = 3;
        static final int TRANSACTION_deliverConfirmationResult = 1;
        static final int TRANSACTION_deliverPickOptionResult = 2;
        static final int TRANSACTION_destroy = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractorCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractorCallback)) {
                return (IVoiceInteractorCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "deliverConfirmationResult";
                case 2:
                    return "deliverPickOptionResult";
                case 3:
                    return "deliverCompleteVoiceResult";
                case 4:
                    return "deliverAbortVoiceResult";
                case 5:
                    return "deliverCommandResult";
                case 6:
                    return "deliverCancel";
                case 7:
                    return "destroy";
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
                    IVoiceInteractorRequest asInterface = IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverConfirmationResult(asInterface, readBoolean, bundle);
                    return true;
                case 2:
                    IVoiceInteractorRequest asInterface2 = IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    VoiceInteractor.PickOptionRequest.Option[] optionArr = (VoiceInteractor.PickOptionRequest.Option[]) parcel.createTypedArray(VoiceInteractor.PickOptionRequest.Option.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverPickOptionResult(asInterface2, readBoolean2, optionArr, bundle2);
                    return true;
                case 3:
                    IVoiceInteractorRequest asInterface3 = IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverCompleteVoiceResult(asInterface3, bundle3);
                    return true;
                case 4:
                    IVoiceInteractorRequest asInterface4 = IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverAbortVoiceResult(asInterface4, bundle4);
                    return true;
                case 5:
                    IVoiceInteractorRequest asInterface5 = IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean3 = parcel.readBoolean();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    deliverCommandResult(asInterface5, readBoolean3, bundle5);
                    return true;
                case 6:
                    IVoiceInteractorRequest asInterface6 = IVoiceInteractorRequest.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deliverCancel(asInterface6);
                    return true;
                case 7:
                    destroy();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoiceInteractorCallback {
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

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeBoolean(z);
                    obtain.writeTypedArray(optionArr, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoiceInteractorRequest);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void destroy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
