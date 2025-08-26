package android.media.tv.interactive;

import android.media.tv.interactive.ITvInteractiveAppServiceCallback;
import android.media.tv.interactive.ITvInteractiveAppSessionCallback;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppService";

    public static class Default implements ITvInteractiveAppService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void createSession(InputChannel inputChannel, ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void sendAppLinkCommand(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws RemoteException {
        }
    }

    void createSession(InputChannel inputChannel, ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, String str, int i) throws RemoteException;

    void registerAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException;

    void registerCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws RemoteException;

    void sendAppLinkCommand(Bundle bundle) throws RemoteException;

    void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException;

    void unregisterCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInteractiveAppService {
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_registerAppLinkInfo = 4;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendAppLinkCommand = 6;
        static final int TRANSACTION_unregisterAppLinkInfo = 5;
        static final int TRANSACTION_unregisterCallback = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ITvInteractiveAppService.DESCRIPTOR);
        }

        public static ITvInteractiveAppService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvInteractiveAppService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvInteractiveAppService)) {
                return (ITvInteractiveAppService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITvInteractiveAppService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvInteractiveAppService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallbackAsInterface = ITvInteractiveAppServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(iTvInteractiveAppServiceCallbackAsInterface);
                    return true;
                case 2:
                    ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallbackAsInterface2 = ITvInteractiveAppServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iTvInteractiveAppServiceCallbackAsInterface2);
                    return true;
                case 3:
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallbackAsInterface = ITvInteractiveAppSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, iTvInteractiveAppSessionCallbackAsInterface, string, i3);
                    return true;
                case 4:
                    AppLinkInfo appLinkInfo = (AppLinkInfo) parcel.readTypedObject(AppLinkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAppLinkInfo(appLinkInfo);
                    return true;
                case 5:
                    AppLinkInfo appLinkInfo2 = (AppLinkInfo) parcel.readTypedObject(AppLinkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterAppLinkInfo(appLinkInfo2);
                    return true;
                case 6:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInteractiveAppService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppService.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInteractiveAppServiceCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterCallback(ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvInteractiveAppServiceCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void createSession(InputChannel inputChannel, ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeStrongInterface(iTvInteractiveAppSessionCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appLinkInfo, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appLinkInfo, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void sendAppLinkCommand(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvInteractiveAppService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
