package android.media.tv.interactive;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

public interface ITvInteractiveAppService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppService";

    void createSession(
            InputChannel inputChannel,
            ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback,
            String str,
            int i)
            throws RemoteException;

    void registerAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException;

    void registerCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback)
            throws RemoteException;

    void sendAppLinkCommand(Bundle bundle) throws RemoteException;

    void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException;

    void unregisterCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback)
            throws RemoteException;

    public static class Default implements ITvInteractiveAppService {
        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerCallback(ITvInteractiveAppServiceCallback callback)
                throws RemoteException {}

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterCallback(ITvInteractiveAppServiceCallback callback)
                throws RemoteException {}

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void createSession(
                InputChannel channel,
                ITvInteractiveAppSessionCallback callback,
                String iAppServiceId,
                int type)
                throws RemoteException {}

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerAppLinkInfo(AppLinkInfo info) throws RemoteException {}

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterAppLinkInfo(AppLinkInfo info) throws RemoteException {}

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void sendAppLinkCommand(Bundle command) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITvInteractiveAppService {
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_registerAppLinkInfo = 4;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendAppLinkCommand = 6;
        static final int TRANSACTION_unregisterAppLinkInfo = 5;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, ITvInteractiveAppService.DESCRIPTOR);
        }

        public static ITvInteractiveAppService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvInteractiveAppService.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInteractiveAppService)) {
                return (ITvInteractiveAppService) iin;
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
                    return "registerAppLinkInfo";
                case 5:
                    return "unregisterAppLinkInfo";
                case 6:
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
                data.enforceInterface(ITvInteractiveAppService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvInteractiveAppService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ITvInteractiveAppServiceCallback _arg0 =
                            ITvInteractiveAppServiceCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg0);
                    return true;
                case 2:
                    ITvInteractiveAppServiceCallback _arg02 =
                            ITvInteractiveAppServiceCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg02);
                    return true;
                case 3:
                    InputChannel _arg03 = (InputChannel) data.readTypedObject(InputChannel.CREATOR);
                    ITvInteractiveAppSessionCallback _arg1 =
                            ITvInteractiveAppSessionCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    createSession(_arg03, _arg1, _arg2, _arg3);
                    return true;
                case 4:
                    AppLinkInfo _arg04 = (AppLinkInfo) data.readTypedObject(AppLinkInfo.CREATOR);
                    data.enforceNoDataAvail();
                    registerAppLinkInfo(_arg04);
                    return true;
                case 5:
                    AppLinkInfo _arg05 = (AppLinkInfo) data.readTypedObject(AppLinkInfo.CREATOR);
                    data.enforceNoDataAvail();
                    unregisterAppLinkInfo(_arg05);
                    return true;
                case 6:
                    Bundle _arg06 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    sendAppLinkCommand(_arg06);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvInteractiveAppService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppService.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerCallback(ITvInteractiveAppServiceCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterCallback(ITvInteractiveAppServiceCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void createSession(
                    InputChannel channel,
                    ITvInteractiveAppSessionCallback callback,
                    String iAppServiceId,
                    int type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    _data.writeTypedObject(channel, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(iAppServiceId);
                    _data.writeInt(type);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerAppLinkInfo(AppLinkInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterAppLinkInfo(AppLinkInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void sendAppLinkCommand(Bundle command) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    _data.writeTypedObject(command, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
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
