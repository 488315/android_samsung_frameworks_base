package android.media.tv.ad;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

public interface ITvAdService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdService";

    void createSession(
            InputChannel inputChannel,
            ITvAdSessionCallback iTvAdSessionCallback,
            String str,
            String str2)
            throws RemoteException;

    void registerCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException;

    void sendAppLinkCommand(Bundle bundle) throws RemoteException;

    void unregisterCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException;

    public static class Default implements ITvAdService {
        @Override // android.media.tv.ad.ITvAdService
        public void registerCallback(ITvAdServiceCallback callback) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdService
        public void unregisterCallback(ITvAdServiceCallback callback) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdService
        public void createSession(
                InputChannel channel, ITvAdSessionCallback callback, String serviceId, String type)
                throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdService
        public void sendAppLinkCommand(Bundle command) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITvAdService {
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendAppLinkCommand = 4;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, ITvAdService.DESCRIPTOR);
        }

        public static ITvAdService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvAdService.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvAdService)) {
                return (ITvAdService) iin;
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
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "createSession";
                case 4:
                    return "sendAppLinkCommand";
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
                data.enforceInterface(ITvAdService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvAdService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ITvAdServiceCallback _arg0 =
                            ITvAdServiceCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg0);
                    return true;
                case 2:
                    ITvAdServiceCallback _arg02 =
                            ITvAdServiceCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg02);
                    return true;
                case 3:
                    InputChannel _arg03 = (InputChannel) data.readTypedObject(InputChannel.CREATOR);
                    ITvAdSessionCallback _arg1 =
                            ITvAdSessionCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    createSession(_arg03, _arg1, _arg2, _arg3);
                    return true;
                case 4:
                    Bundle _arg04 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    sendAppLinkCommand(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvAdService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdService.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdService
            public void registerCallback(ITvAdServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void unregisterCallback(ITvAdServiceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void createSession(
                    InputChannel channel,
                    ITvAdSessionCallback callback,
                    String serviceId,
                    String type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    _data.writeTypedObject(channel, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(serviceId);
                    _data.writeString(type);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void sendAppLinkCommand(Bundle command) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    _data.writeTypedObject(command, 0);
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
