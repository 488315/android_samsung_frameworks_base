package android.service.controls;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.controls.IControlsSubscription;

/* loaded from: classes3.dex */
public interface IControlsSubscriber extends IInterface {
    public static final String DESCRIPTOR = "android.service.controls.IControlsSubscriber";

    public static class Default implements IControlsSubscriber {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onComplete(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onError(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onNext(IBinder iBinder, Control control) throws RemoteException {
        }

        @Override // android.service.controls.IControlsSubscriber
        public void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) throws RemoteException {
        }
    }

    void onComplete(IBinder iBinder) throws RemoteException;

    void onError(IBinder iBinder, String str) throws RemoteException;

    void onNext(IBinder iBinder, Control control) throws RemoteException;

    void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) throws RemoteException;

    public static abstract class Stub extends Binder implements IControlsSubscriber {
        static final int TRANSACTION_onComplete = 4;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onNext = 2;
        static final int TRANSACTION_onSubscribe = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IControlsSubscriber.DESCRIPTOR);
        }

        public static IControlsSubscriber asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IControlsSubscriber.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IControlsSubscriber)) {
                return (IControlsSubscriber) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSubscribe";
            }
            if (i == 2) {
                return "onNext";
            }
            if (i == 3) {
                return "onError";
            }
            if (i != 4) {
                return null;
            }
            return "onComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IControlsSubscriber.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IControlsSubscriber.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                IControlsSubscription asInterface = IControlsSubscription.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onSubscribe(readStrongBinder, asInterface);
            } else if (i == 2) {
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                Control control = (Control) parcel.readTypedObject(Control.CREATOR);
                parcel.enforceNoDataAvail();
                onNext(readStrongBinder2, control);
            } else if (i == 3) {
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readStrongBinder3, readString);
            } else if (i == 4) {
                IBinder readStrongBinder4 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onComplete(readStrongBinder4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IControlsSubscriber {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IControlsSubscriber.DESCRIPTOR;
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iControlsSubscription);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onNext(IBinder iBinder, Control control) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(control, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onError(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.controls.IControlsSubscriber
            public void onComplete(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IControlsSubscriber.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
