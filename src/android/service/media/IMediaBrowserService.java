package android.service.media;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.service.media.IMediaBrowserServiceCallbacks;

/* loaded from: classes3.dex */
public interface IMediaBrowserService extends IInterface {

    public static class Default implements IMediaBrowserService {
        @Override // android.service.media.IMediaBrowserService
        public void addSubscription(String str, IBinder iBinder, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.media.IMediaBrowserService
        public void connect(String str, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void disconnect(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void getMediaItem(String str, ResultReceiver resultReceiver, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscription(String str, IBinder iBinder, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
        }
    }

    void addSubscription(String str, IBinder iBinder, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void addSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void connect(String str, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void disconnect(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void getMediaItem(String str, ResultReceiver resultReceiver, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void removeSubscription(String str, IBinder iBinder, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    void removeSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaBrowserService {
        public static final String DESCRIPTOR = "android.service.media.IMediaBrowserService";
        static final int TRANSACTION_addSubscription = 6;
        static final int TRANSACTION_addSubscriptionDeprecated = 3;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_disconnect = 2;
        static final int TRANSACTION_getMediaItem = 5;
        static final int TRANSACTION_removeSubscription = 7;
        static final int TRANSACTION_removeSubscriptionDeprecated = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaBrowserService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaBrowserService)) {
                return (IMediaBrowserService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return MediaMetrics.Value.CONNECT;
                case 2:
                    return MediaMetrics.Value.DISCONNECT;
                case 3:
                    return "addSubscriptionDeprecated";
                case 4:
                    return "removeSubscriptionDeprecated";
                case 5:
                    return "getMediaItem";
                case 6:
                    return "addSubscription";
                case 7:
                    return "removeSubscription";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IMediaBrowserServiceCallbacks asInterface = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connect(readString, bundle, asInterface);
                    return true;
                case 2:
                    IMediaBrowserServiceCallbacks asInterface2 = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    disconnect(asInterface2);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    IMediaBrowserServiceCallbacks asInterface3 = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSubscriptionDeprecated(readString2, asInterface3);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    IMediaBrowserServiceCallbacks asInterface4 = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSubscriptionDeprecated(readString3, asInterface4);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    IMediaBrowserServiceCallbacks asInterface5 = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getMediaItem(readString4, resultReceiver, asInterface5);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IMediaBrowserServiceCallbacks asInterface6 = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSubscription(readString5, readStrongBinder, bundle2, asInterface6);
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    IMediaBrowserServiceCallbacks asInterface7 = IMediaBrowserServiceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSubscription(readString6, readStrongBinder2, asInterface7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaBrowserService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.media.IMediaBrowserService
            public void connect(String str, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void disconnect(IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void addSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void removeSubscriptionDeprecated(String str, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void getMediaItem(String str, ResultReceiver resultReceiver, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void addSubscription(String str, IBinder iBinder, Bundle bundle, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserService
            public void removeSubscription(String str, IBinder iBinder, IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iMediaBrowserServiceCallbacks);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
