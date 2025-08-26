package com.android.internal.compat;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IOverrideValidator extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.compat.IOverrideValidator";

    public static class Default implements IOverrideValidator {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.compat.IOverrideValidator
        public OverrideAllowedState getOverrideAllowedState(long j, String str) throws RemoteException {
            return null;
        }
    }

    OverrideAllowedState getOverrideAllowedState(long j, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IOverrideValidator {
        static final int TRANSACTION_getOverrideAllowedState = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOverrideValidator.DESCRIPTOR);
        }

        public static IOverrideValidator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IOverrideValidator.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOverrideValidator)) {
                return (IOverrideValidator) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getOverrideAllowedState";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOverrideValidator.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOverrideValidator.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                OverrideAllowedState overrideAllowedState = getOverrideAllowedState(j, string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(overrideAllowedState, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOverrideValidator {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOverrideValidator.DESCRIPTOR;
            }

            @Override // com.android.internal.compat.IOverrideValidator
            public OverrideAllowedState getOverrideAllowedState(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IOverrideValidator.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (OverrideAllowedState) parcelObtain2.readTypedObject(OverrideAllowedState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
