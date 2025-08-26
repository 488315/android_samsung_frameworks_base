package com.sec.android.smartfpsadjuster;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIntelligentDynamicFpsService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService";

    public static class Default implements IIntelligentDynamicFpsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
        public int cameraPolicyChange(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
        public void cameraPolicyStart() throws RemoteException {
        }

        @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
        public void cameraPolicyStop() throws RemoteException {
        }
    }

    int cameraPolicyChange(int i) throws RemoteException;

    void cameraPolicyStart() throws RemoteException;

    void cameraPolicyStop() throws RemoteException;

    public static abstract class Stub extends Binder implements IIntelligentDynamicFpsService {
        static final int TRANSACTION_cameraPolicyChange = 3;
        static final int TRANSACTION_cameraPolicyStart = 1;
        static final int TRANSACTION_cameraPolicyStop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IIntelligentDynamicFpsService.DESCRIPTOR);
        }

        public static IIntelligentDynamicFpsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIntelligentDynamicFpsService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIntelligentDynamicFpsService)) {
                return (IIntelligentDynamicFpsService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "cameraPolicyStart";
            }
            if (i == 2) {
                return "cameraPolicyStop";
            }
            if (i != 3) {
                return null;
            }
            return "cameraPolicyChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIntelligentDynamicFpsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntelligentDynamicFpsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                cameraPolicyStart();
                parcel2.writeNoException();
            } else if (i == 2) {
                cameraPolicyStop();
                parcel2.writeNoException();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iCameraPolicyChange = cameraPolicyChange(i3);
                parcel2.writeNoException();
                parcel2.writeInt(iCameraPolicyChange);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIntelligentDynamicFpsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntelligentDynamicFpsService.DESCRIPTOR;
            }

            @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
            public void cameraPolicyStart() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentDynamicFpsService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
            public void cameraPolicyStop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentDynamicFpsService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.smartfpsadjuster.IIntelligentDynamicFpsService
            public int cameraPolicyChange(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentDynamicFpsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
