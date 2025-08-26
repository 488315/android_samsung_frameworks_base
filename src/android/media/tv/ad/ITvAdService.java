package android.media.tv.ad;

import android.media.tv.ad.ITvAdServiceCallback;
import android.media.tv.ad.ITvAdSessionCallback;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

/* loaded from: classes3.dex */
public interface ITvAdService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdService";

    public static class Default implements ITvAdService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdService
        public void createSession(InputChannel inputChannel, ITvAdSessionCallback iTvAdSessionCallback, String str, String str2) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdService
        public void registerCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdService
        public void sendAppLinkCommand(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdService
        public void unregisterCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException {
        }
    }

    void createSession(InputChannel inputChannel, ITvAdSessionCallback iTvAdSessionCallback, String str, String str2) throws RemoteException;

    void registerCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException;

    void sendAppLinkCommand(Bundle bundle) throws RemoteException;

    void unregisterCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvAdService {
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_sendAppLinkCommand = 4;
        static final int TRANSACTION_unregisterCallback = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ITvAdService.DESCRIPTOR);
        }

        public static ITvAdService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvAdService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvAdService)) {
                return (ITvAdService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerCallback";
            }
            if (i == 2) {
                return "unregisterCallback";
            }
            if (i == 3) {
                return "createSession";
            }
            if (i != 4) {
                return null;
            }
            return "sendAppLinkCommand";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITvAdService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvAdService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ITvAdServiceCallback iTvAdServiceCallbackAsInterface = ITvAdServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerCallback(iTvAdServiceCallbackAsInterface);
            } else if (i == 2) {
                ITvAdServiceCallback iTvAdServiceCallbackAsInterface2 = ITvAdServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterCallback(iTvAdServiceCallbackAsInterface2);
            } else if (i == 3) {
                InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                ITvAdSessionCallback iTvAdSessionCallbackAsInterface = ITvAdSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                createSession(inputChannel, iTvAdSessionCallbackAsInterface, string, string2);
            } else if (i == 4) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                sendAppLinkCommand(bundle);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITvAdService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdService.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdService
            public void registerCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvAdServiceCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void unregisterCallback(ITvAdServiceCallback iTvAdServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvAdServiceCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void createSession(InputChannel inputChannel, ITvAdSessionCallback iTvAdSessionCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeStrongInterface(iTvAdSessionCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void sendAppLinkCommand(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
