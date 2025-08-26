package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telecom.IInternalServiceRetriever;
import com.android.internal.telecom.ITelecomService;

/* loaded from: classes4.dex */
public interface ITelecomLoader extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ITelecomLoader";

    public static class Default implements ITelecomLoader {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomLoader
        public ITelecomService createTelecomService(IInternalServiceRetriever iInternalServiceRetriever, String str) throws RemoteException {
            return null;
        }
    }

    ITelecomService createTelecomService(IInternalServiceRetriever iInternalServiceRetriever, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITelecomLoader {
        static final int TRANSACTION_createTelecomService = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITelecomLoader.DESCRIPTOR);
        }

        public static ITelecomLoader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITelecomLoader.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITelecomLoader)) {
                return (ITelecomLoader) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "createTelecomService";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITelecomLoader.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITelecomLoader.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IInternalServiceRetriever iInternalServiceRetrieverAsInterface = IInternalServiceRetriever.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                ITelecomService iTelecomServiceCreateTelecomService = createTelecomService(iInternalServiceRetrieverAsInterface, string);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iTelecomServiceCreateTelecomService);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITelecomLoader {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITelecomLoader.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ITelecomLoader
            public ITelecomService createTelecomService(IInternalServiceRetriever iInternalServiceRetriever, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITelecomLoader.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInternalServiceRetriever);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ITelecomService.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
