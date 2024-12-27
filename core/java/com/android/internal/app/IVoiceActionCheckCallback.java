package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IVoiceActionCheckCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVoiceActionCheckCallback";

    void onComplete(List<String> list) throws RemoteException;

    public static class Default implements IVoiceActionCheckCallback {
        @Override // com.android.internal.app.IVoiceActionCheckCallback
        public void onComplete(List<String> voiceActions) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVoiceActionCheckCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IVoiceActionCheckCallback.DESCRIPTOR);
        }

        public static IVoiceActionCheckCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVoiceActionCheckCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IVoiceActionCheckCallback)) {
                return (IVoiceActionCheckCallback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(IVoiceActionCheckCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVoiceActionCheckCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<String> _arg0 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    onComplete(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVoiceActionCheckCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoiceActionCheckCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceActionCheckCallback
            public void onComplete(List<String> voiceActions) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVoiceActionCheckCallback.DESCRIPTOR);
                    _data.writeStringList(voiceActions);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
