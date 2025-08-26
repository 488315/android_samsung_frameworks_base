package android.os.epic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.epic.IEpicObject;

/* loaded from: classes3.dex */
public interface IEpicManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.epic.IEpicManager";

    public static class Default implements IEpicManager {
        @Override // android.os.epic.IEpicManager
        public IEpicObject Create(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.epic.IEpicManager
        public IEpicObject Creates(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    IEpicObject Create(int i) throws RemoteException;

    IEpicObject Creates(int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IEpicManager {
        static final int TRANSACTION_Create = 1;
        static final int TRANSACTION_Creates = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IEpicManager.DESCRIPTOR);
        }

        public static IEpicManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEpicManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEpicManager)) {
                return (IEpicManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "Create";
            }
            if (i != 2) {
                return null;
            }
            return "Creates";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEpicManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEpicManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                IEpicObject iEpicObjectCreate = Create(i3);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iEpicObjectCreate);
            } else if (i == 2) {
                int[] iArrCreateIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                IEpicObject iEpicObjectCreates = Creates(iArrCreateIntArray);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iEpicObjectCreates);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IEpicManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEpicManager.DESCRIPTOR;
            }

            @Override // android.os.epic.IEpicManager
            public IEpicObject Create(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IEpicObject.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.epic.IEpicManager
            public IEpicObject Creates(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEpicManager.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IEpicObject.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
