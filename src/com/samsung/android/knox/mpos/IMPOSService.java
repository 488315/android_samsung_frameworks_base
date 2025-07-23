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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMPOSService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMPOSService)) {
                return (IMPOSService) queryLocalInterface;
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
                int readInt = parcel.readInt();
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                long readLong = parcel.readLong();
                long readLong2 = parcel.readLong();
                MposTZServiceConfig mposTZServiceConfig = (MposTZServiceConfig) parcel.readTypedObject(MposTZServiceConfig.CREATOR);
                parcel.enforceNoDataAvail();
                boolean loadTa = loadTa(readInt, parcelFileDescriptor, readLong, readLong2, mposTZServiceConfig);
                parcel2.writeNoException();
                parcel2.writeBoolean(loadTa);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean unloadTa = unloadTa(readInt2);
                parcel2.writeNoException();
                parcel2.writeBoolean(unloadTa);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                TACommandRequest tACommandRequest = (TACommandRequest) parcel.readTypedObject(TACommandRequest.CREATOR);
                parcel.enforceNoDataAvail();
                TACommandResponse processTACommand = processTACommand(readInt3, tACommandRequest);
                parcel2.writeNoException();
                parcel2.writeTypedObject(processTACommand, 1);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(mposTZServiceConfig, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public boolean unloadTa(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tACommandRequest, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TACommandResponse) obtain2.readTypedObject(TACommandResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
