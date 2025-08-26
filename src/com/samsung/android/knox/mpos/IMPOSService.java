package com.samsung.android.knox.mpos;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMPOSService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mpos.IMPOSService";

    public static class Default implements IMPOSService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.mpos.IMPOSService
        public boolean loadTa(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2, MposTZServiceConfig mposTZServiceConfig) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.mpos.IMPOSService
        public TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.mpos.IMPOSService
        public boolean unloadTa(int i) throws RemoteException {
            return false;
        }
    }

    boolean loadTa(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2, MposTZServiceConfig mposTZServiceConfig) throws RemoteException;

    TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) throws RemoteException;

    boolean unloadTa(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMPOSService {
        static final int TRANSACTION_loadTa = 1;
        static final int TRANSACTION_processTACommand = 3;
        static final int TRANSACTION_unloadTa = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IMPOSService.DESCRIPTOR);
        }

        public static IMPOSService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMPOSService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMPOSService)) {
                return (IMPOSService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "loadTa";
            }
            if (i == 2) {
                return "unloadTa";
            }
            if (i != 3) {
                return null;
            }
            return "processTACommand";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMPOSService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMPOSService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                long j = parcel.readLong();
                long j2 = parcel.readLong();
                MposTZServiceConfig mposTZServiceConfig = (MposTZServiceConfig) parcel.readTypedObject(MposTZServiceConfig.CREATOR);
                parcel.enforceNoDataAvail();
                boolean zLoadTa = loadTa(i3, parcelFileDescriptor, j, j2, mposTZServiceConfig);
                parcel2.writeNoException();
                parcel2.writeBoolean(zLoadTa);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zUnloadTa = unloadTa(i4);
                parcel2.writeNoException();
                parcel2.writeBoolean(zUnloadTa);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                TACommandRequest tACommandRequest = (TACommandRequest) parcel.readTypedObject(TACommandRequest.CREATOR);
                parcel.enforceNoDataAvail();
                TACommandResponse tACommandResponseProcessTACommand = processTACommand(i5, tACommandRequest);
                parcel2.writeNoException();
                parcel2.writeTypedObject(tACommandResponseProcessTACommand, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMPOSService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMPOSService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public boolean loadTa(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2, MposTZServiceConfig mposTZServiceConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(mposTZServiceConfig, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public boolean unloadTa(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(tACommandRequest, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TACommandResponse) parcelObtain2.readTypedObject(TACommandResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
