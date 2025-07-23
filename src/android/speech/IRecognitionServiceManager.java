package android.speech;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.speech.IRecognitionServiceManagerCallback;

/* loaded from: classes3.dex */
public interface IRecognitionServiceManager extends IInterface {
    public static final String DESCRIPTOR = "android.speech.IRecognitionServiceManager";

    public static class Default implements IRecognitionServiceManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.IRecognitionServiceManager
        public void createSession(ComponentName componentName, IBinder iBinder, boolean z, IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) throws RemoteException {
        }

        @Override // android.speech.IRecognitionServiceManager
        public void setTemporaryComponent(ComponentName componentName) throws RemoteException {
        }
    }

    void createSession(ComponentName componentName, IBinder iBinder, boolean z, IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) throws RemoteException;

    void setTemporaryComponent(ComponentName componentName) throws RemoteException;

    public static abstract class Stub extends Binder implements IRecognitionServiceManager {
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_setTemporaryComponent = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IRecognitionServiceManager.DESCRIPTOR);
        }

        public static IRecognitionServiceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRecognitionServiceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRecognitionServiceManager)) {
                return (IRecognitionServiceManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createSession";
            }
            if (i != 2) {
                return null;
            }
            return "setTemporaryComponent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRecognitionServiceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRecognitionServiceManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                IBinder readStrongBinder = parcel.readStrongBinder();
                boolean readBoolean = parcel.readBoolean();
                IRecognitionServiceManagerCallback asInterface = IRecognitionServiceManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                createSession(componentName, readStrongBinder, readBoolean, asInterface);
            } else if (i == 2) {
                ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                parcel.enforceNoDataAvail();
                setTemporaryComponent(componentName2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRecognitionServiceManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRecognitionServiceManager.DESCRIPTOR;
            }

            @Override // android.speech.IRecognitionServiceManager
            public void createSession(ComponentName componentName, IBinder iBinder, boolean z, IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRecognitionServiceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iRecognitionServiceManagerCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IRecognitionServiceManager
            public void setTemporaryComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRecognitionServiceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
