package com.sec.android.allshare.iface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISubscriber extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.allshare.iface.ISubscriber";

    public static class Default implements ISubscriber {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public String getCaptionFilePathFromURI(String str) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public String getServiceVersion() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public boolean requestCVAsync(String str, CVMessage cVMessage) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public CVMessage requestCVSync(String str, CVMessage cVMessage) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public boolean subscribeEvent(String str, CVMessage cVMessage) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.allshare.iface.ISubscriber
        public void unsubscribeEvent(String str, CVMessage cVMessage) throws RemoteException {
        }
    }

    String getCaptionFilePathFromURI(String str) throws RemoteException;

    String getServiceVersion() throws RemoteException;

    boolean requestCVAsync(String str, CVMessage cVMessage) throws RemoteException;

    CVMessage requestCVSync(String str, CVMessage cVMessage) throws RemoteException;

    boolean subscribeEvent(String str, CVMessage cVMessage) throws RemoteException;

    void unsubscribeEvent(String str, CVMessage cVMessage) throws RemoteException;

    public static abstract class Stub extends Binder implements ISubscriber {
        static final int TRANSACTION_getCaptionFilePathFromURI = 6;
        static final int TRANSACTION_getServiceVersion = 5;
        static final int TRANSACTION_requestCVAsync = 1;
        static final int TRANSACTION_requestCVSync = 2;
        static final int TRANSACTION_subscribeEvent = 3;
        static final int TRANSACTION_unsubscribeEvent = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISubscriber.DESCRIPTOR);
        }

        public static ISubscriber asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISubscriber.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISubscriber)) {
                return (ISubscriber) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "requestCVAsync";
                case 2:
                    return "requestCVSync";
                case 3:
                    return "subscribeEvent";
                case 4:
                    return "unsubscribeEvent";
                case 5:
                    return "getServiceVersion";
                case 6:
                    return "getCaptionFilePathFromURI";
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
                parcel.enforceInterface(ISubscriber.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISubscriber.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    CVMessage cVMessage = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestCVAsync = requestCVAsync(readString, cVMessage);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestCVAsync);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    CVMessage cVMessage2 = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    CVMessage requestCVSync = requestCVSync(readString2, cVMessage2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(requestCVSync, 1);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    CVMessage cVMessage3 = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean subscribeEvent = subscribeEvent(readString3, cVMessage3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(subscribeEvent);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    CVMessage cVMessage4 = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    unsubscribeEvent(readString4, cVMessage4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String serviceVersion = getServiceVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(serviceVersion);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String captionFilePathFromURI = getCaptionFilePathFromURI(readString5);
                    parcel2.writeNoException();
                    parcel2.writeString(captionFilePathFromURI);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISubscriber {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISubscriber.DESCRIPTOR;
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public boolean requestCVAsync(String str, CVMessage cVMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public CVMessage requestCVSync(String str, CVMessage cVMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CVMessage) obtain2.readTypedObject(CVMessage.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public boolean subscribeEvent(String str, CVMessage cVMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public void unsubscribeEvent(String str, CVMessage cVMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public String getServiceVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public String getCaptionFilePathFromURI(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
