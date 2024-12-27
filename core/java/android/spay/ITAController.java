package android.spay;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import java.util.List;

public interface ITAController extends IInterface {
    public static final String DESCRIPTOR = "android.spay.ITAController";

    CertInfo checkCertInfo(List<String> list) throws RemoteException;

    boolean clearDeviceCertificates(String str) throws RemoteException;

    boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2)
            throws RemoteException;

    boolean makeSystemCall(int i) throws RemoteException;

    TACommandResponse processTACommand(TACommandRequest tACommandRequest) throws RemoteException;

    void unloadTA() throws RemoteException;

    public static class Default implements ITAController {
        @Override // android.spay.ITAController
        public boolean loadTA(ParcelFileDescriptor pfd, long offset, long len)
                throws RemoteException {
            return false;
        }

        @Override // android.spay.ITAController
        public void unloadTA() throws RemoteException {}

        @Override // android.spay.ITAController
        public TACommandResponse processTACommand(TACommandRequest request) throws RemoteException {
            return null;
        }

        @Override // android.spay.ITAController
        public boolean makeSystemCall(int cmd) throws RemoteException {
            return false;
        }

        @Override // android.spay.ITAController
        public boolean clearDeviceCertificates(String certFolderPath) throws RemoteException {
            return false;
        }

        @Override // android.spay.ITAController
        public CertInfo checkCertInfo(List<String> certFilePaths) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITAController {
        static final int TRANSACTION_checkCertInfo = 6;
        static final int TRANSACTION_clearDeviceCertificates = 5;
        static final int TRANSACTION_loadTA = 1;
        static final int TRANSACTION_makeSystemCall = 4;
        static final int TRANSACTION_processTACommand = 3;
        static final int TRANSACTION_unloadTA = 2;

        public Stub() {
            attachInterface(this, ITAController.DESCRIPTOR);
        }

        public static ITAController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITAController.DESCRIPTOR);
            if (iin != null && (iin instanceof ITAController)) {
                return (ITAController) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "loadTA";
                case 2:
                    return "unloadTA";
                case 3:
                    return "processTACommand";
                case 4:
                    return "makeSystemCall";
                case 5:
                    return "clearDeviceCertificates";
                case 6:
                    return "checkCertInfo";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITAController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITAController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result = loadTA(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    unloadTA();
                    reply.writeNoException();
                    return true;
                case 3:
                    TACommandRequest _arg02 =
                            (TACommandRequest) data.readTypedObject(TACommandRequest.CREATOR);
                    data.enforceNoDataAvail();
                    TACommandResponse _result2 = processTACommand(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = makeSystemCall(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 5:
                    String _arg04 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result4 = clearDeviceCertificates(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 6:
                    List<String> _arg05 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    CertInfo _result5 = checkCertInfo(_arg05);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITAController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITAController.DESCRIPTOR;
            }

            @Override // android.spay.ITAController
            public boolean loadTA(ParcelFileDescriptor pfd, long offset, long len)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeTypedObject(pfd, 0);
                    _data.writeLong(offset);
                    _data.writeLong(len);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.spay.ITAController
            public void unloadTA() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.spay.ITAController
            public TACommandResponse processTACommand(TACommandRequest request)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    TACommandResponse _result =
                            (TACommandResponse) _reply.readTypedObject(TACommandResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.spay.ITAController
            public boolean makeSystemCall(int cmd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeInt(cmd);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.spay.ITAController
            public boolean clearDeviceCertificates(String certFolderPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeString(certFolderPath);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.spay.ITAController
            public CertInfo checkCertInfo(List<String> certFilePaths) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ITAController.DESCRIPTOR);
                    _data.writeStringList(certFilePaths);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    CertInfo _result = (CertInfo) _reply.readTypedObject(CertInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
