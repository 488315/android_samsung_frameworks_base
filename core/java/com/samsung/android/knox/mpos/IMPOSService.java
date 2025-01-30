package com.samsung.android.knox.mpos;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IMPOSService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mpos.IMPOSService";

    boolean loadTa(int i, ParcelFileDescriptor parcelFileDescriptor, long j, long j2, MposTZServiceConfig mposTZServiceConfig) throws RemoteException;

    TACommandResponse processTACommand(int i, TACommandRequest tACommandRequest) throws RemoteException;

    boolean unloadTa(int i) throws RemoteException;

    public static class Default implements IMPOSService {
        @Override // com.samsung.android.knox.mpos.IMPOSService
        public boolean loadTa(int taId, ParcelFileDescriptor pFd, long offset, long len, MposTZServiceConfig config) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.mpos.IMPOSService
        public boolean unloadTa(int taId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.mpos.IMPOSService
        public TACommandResponse processTACommand(int taId, TACommandRequest request) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMPOSService {
        static final int TRANSACTION_loadTa = 1;
        static final int TRANSACTION_processTACommand = 3;
        static final int TRANSACTION_unloadTa = 2;

        public Stub() {
            attachInterface(this, IMPOSService.DESCRIPTOR);
        }

        public static IMPOSService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMPOSService.DESCRIPTOR);
            if (iin != null && (iin instanceof IMPOSService)) {
                return (IMPOSService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "loadTa";
                case 2:
                    return "unloadTa";
                case 3:
                    return "processTACommand";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IMPOSService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IMPOSService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            ParcelFileDescriptor _arg1 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            long _arg2 = data.readLong();
                            long _arg3 = data.readLong();
                            MposTZServiceConfig _arg4 = (MposTZServiceConfig) data.readTypedObject(MposTZServiceConfig.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result = loadTa(_arg0, _arg1, _arg2, _arg3, _arg4);
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result2 = unloadTa(_arg02);
                            reply.writeNoException();
                            reply.writeBoolean(_result2);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            TACommandRequest _arg12 = (TACommandRequest) data.readTypedObject(TACommandRequest.CREATOR);
                            data.enforceNoDataAvail();
                            TACommandResponse _result3 = processTACommand(_arg03, _arg12);
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IMPOSService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMPOSService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public boolean loadTa(int taId, ParcelFileDescriptor pFd, long offset, long len, MposTZServiceConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    _data.writeInt(taId);
                    _data.writeTypedObject(pFd, 0);
                    _data.writeLong(offset);
                    _data.writeLong(len);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public boolean unloadTa(int taId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    _data.writeInt(taId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.mpos.IMPOSService
            public TACommandResponse processTACommand(int taId, TACommandRequest request) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMPOSService.DESCRIPTOR);
                    _data.writeInt(taId);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    TACommandResponse _result = (TACommandResponse) _reply.readTypedObject(TACommandResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
