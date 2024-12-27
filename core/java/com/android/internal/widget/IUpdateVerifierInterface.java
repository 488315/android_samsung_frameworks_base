package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IUpdateVerifierInterface extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IUpdateVerifierInterface";

    void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException;

    void updateVerifier(byte[] bArr, byte[] bArr2) throws RemoteException;

    void updateVerifierWithType(byte[] bArr, byte[] bArr2, int i) throws RemoteException;

    void updateVerifierWithWk(byte[] bArr, byte[] bArr2, byte[] bArr3, int i)
            throws RemoteException;

    public static class Default implements IUpdateVerifierInterface {
        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback)
                throws RemoteException {}

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifier(byte[] verifier, byte[] salt) throws RemoteException {}

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifierWithType(byte[] verifier, byte[] salt, int verifierType)
                throws RemoteException {}

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifierWithWk(byte[] verifier, byte[] salt, byte[] wk, int verifierType)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUpdateVerifierInterface {
        static final int TRANSACTION_requestSaGuid = 1;
        static final int TRANSACTION_updateVerifier = 2;
        static final int TRANSACTION_updateVerifierWithType = 3;
        static final int TRANSACTION_updateVerifierWithWk = 4;

        public Stub() {
            attachInterface(this, IUpdateVerifierInterface.DESCRIPTOR);
        }

        public static IUpdateVerifierInterface asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUpdateVerifierInterface.DESCRIPTOR);
            if (iin != null && (iin instanceof IUpdateVerifierInterface)) {
                return (IUpdateVerifierInterface) iin;
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
                    return "requestSaGuid";
                case 2:
                    return "updateVerifier";
                case 3:
                    return "updateVerifierWithType";
                case 4:
                    return "updateVerifierWithWk";
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
                data.enforceInterface(IUpdateVerifierInterface.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUpdateVerifierInterface.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IUpdateVerifierCallback _arg0 =
                            IUpdateVerifierCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestSaGuid(_arg0);
                    return true;
                case 2:
                    byte[] _arg02 = data.createByteArray();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    updateVerifier(_arg02, _arg1);
                    return true;
                case 3:
                    byte[] _arg03 = data.createByteArray();
                    byte[] _arg12 = data.createByteArray();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    updateVerifierWithType(_arg03, _arg12, _arg2);
                    return true;
                case 4:
                    byte[] _arg04 = data.createByteArray();
                    byte[] _arg13 = data.createByteArray();
                    byte[] _arg22 = data.createByteArray();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    updateVerifierWithWk(_arg04, _arg13, _arg22, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUpdateVerifierInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUpdateVerifierInterface.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    _data.writeStrongInterface(iUpdateVerifierCallback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void updateVerifier(byte[] verifier, byte[] salt) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    _data.writeByteArray(verifier);
                    _data.writeByteArray(salt);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void updateVerifierWithType(byte[] verifier, byte[] salt, int verifierType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    _data.writeByteArray(verifier);
                    _data.writeByteArray(salt);
                    _data.writeInt(verifierType);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void updateVerifierWithWk(
                    byte[] verifier, byte[] salt, byte[] wk, int verifierType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUpdateVerifierInterface.DESCRIPTOR);
                    _data.writeByteArray(verifier);
                    _data.writeByteArray(salt);
                    _data.writeByteArray(wk);
                    _data.writeInt(verifierType);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
