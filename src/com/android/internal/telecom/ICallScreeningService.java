package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.ParcelableCall;
import com.android.internal.telecom.ICallScreeningAdapter;

/* loaded from: classes4.dex */
public interface ICallScreeningService extends IInterface {

    public static class Default implements ICallScreeningService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallScreeningService
        public void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) throws RemoteException {
        }
    }

    void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallScreeningService {
        public static final String DESCRIPTOR = "com.android.internal.telecom.ICallScreeningService";
        static final int TRANSACTION_screenCall = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICallScreeningService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallScreeningService)) {
                return (ICallScreeningService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "screenCall";
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
            if (i == 1) {
                ICallScreeningAdapter asInterface = ICallScreeningAdapter.Stub.asInterface(parcel.readStrongBinder());
                ParcelableCall parcelableCall = (ParcelableCall) parcel.readTypedObject(ParcelableCall.CREATOR);
                parcel.enforceNoDataAvail();
                screenCall(asInterface, parcelableCall);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICallScreeningService {
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

            @Override // com.android.internal.telecom.ICallScreeningService
            public void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCallScreeningAdapter);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
