package com.samsung.android.chimera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.chimera.genie.MemRequest;
import java.util.List;

/* loaded from: classes6.dex */
public interface IChimera extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.chimera.IChimera";

    public static class Default implements IChimera {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.chimera.IChimera
        public List<PSIAvailableMem> getAvailableMemInfo(long j, long j2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.chimera.IChimera
        public void prepareMemory(MemRequest memRequest) throws RemoteException {
        }

        @Override // com.samsung.android.chimera.IChimera
        public void setGenieSessionEnd() throws RemoteException {
        }

        @Override // com.samsung.android.chimera.IChimera
        public void setGenieSessionStart() throws RemoteException {
        }
    }

    List<PSIAvailableMem> getAvailableMemInfo(long j, long j2) throws RemoteException;

    void prepareMemory(MemRequest memRequest) throws RemoteException;

    void setGenieSessionEnd() throws RemoteException;

    void setGenieSessionStart() throws RemoteException;

    public static abstract class Stub extends Binder implements IChimera {
        static final int TRANSACTION_getAvailableMemInfo = 1;
        static final int TRANSACTION_prepareMemory = 2;
        static final int TRANSACTION_setGenieSessionEnd = 4;
        static final int TRANSACTION_setGenieSessionStart = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IChimera.DESCRIPTOR);
        }

        public static IChimera asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IChimera.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IChimera)) {
                return (IChimera) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getAvailableMemInfo";
            }
            if (i == 2) {
                return "prepareMemory";
            }
            if (i == 3) {
                return "setGenieSessionStart";
            }
            if (i != 4) {
                return null;
            }
            return "setGenieSessionEnd";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IChimera.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IChimera.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                long j2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                List<PSIAvailableMem> availableMemInfo = getAvailableMemInfo(j, j2);
                parcel2.writeNoException();
                parcel2.writeTypedList(availableMemInfo, 1);
            } else if (i == 2) {
                MemRequest memRequest = (MemRequest) parcel.readTypedObject(MemRequest.CREATOR);
                parcel.enforceNoDataAvail();
                prepareMemory(memRequest);
                parcel2.writeNoException();
            } else if (i == 3) {
                setGenieSessionStart();
                parcel2.writeNoException();
            } else if (i == 4) {
                setGenieSessionEnd();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IChimera {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IChimera.DESCRIPTOR;
            }

            @Override // com.samsung.android.chimera.IChimera
            public List<PSIAvailableMem> getAvailableMemInfo(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IChimera.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(PSIAvailableMem.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.chimera.IChimera
            public void prepareMemory(MemRequest memRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IChimera.DESCRIPTOR);
                    parcelObtain.writeTypedObject(memRequest, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.chimera.IChimera
            public void setGenieSessionStart() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IChimera.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.chimera.IChimera
            public void setGenieSessionEnd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IChimera.DESCRIPTOR);
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
