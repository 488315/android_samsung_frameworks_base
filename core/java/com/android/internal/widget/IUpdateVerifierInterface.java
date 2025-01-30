package com.android.internal.widget;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.android.internal.widget.IUpdateVerifierCallback;

/* loaded from: classes5.dex */
public interface IUpdateVerifierInterface extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IUpdateVerifierInterface";

    void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException;

    void updateVerifier(byte[] bArr, byte[] bArr2) throws RemoteException;

    public static class Default implements IUpdateVerifierInterface {
        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.IUpdateVerifierInterface
        public void updateVerifier(byte[] verifier, byte[] salt) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUpdateVerifierInterface {
        static final int TRANSACTION_requestSaGuid = 1;
        static final int TRANSACTION_updateVerifier = 2;

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

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "requestSaGuid";
                case 2:
                    return "updateVerifier";
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
                data.enforceInterface(IUpdateVerifierInterface.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IUpdateVerifierInterface.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            IUpdateVerifierCallback _arg0 = IUpdateVerifierCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            requestSaGuid(_arg0);
                            return true;
                        case 2:
                            byte[] _arg02 = data.createByteArray();
                            byte[] _arg1 = data.createByteArray();
                            data.enforceNoDataAvail();
                            updateVerifier(_arg02, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUpdateVerifierInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUpdateVerifierInterface.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IUpdateVerifierInterface
            public void requestSaGuid(IUpdateVerifierCallback iUpdateVerifierCallback) throws RemoteException {
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
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
