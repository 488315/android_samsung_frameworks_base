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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISubscriber.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISubscriber)) {
                return (ISubscriber) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    CVMessage cVMessage = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestCVAsync = requestCVAsync(string, cVMessage);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestCVAsync);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    CVMessage cVMessage2 = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    CVMessage cVMessageRequestCVSync = requestCVSync(string2, cVMessage2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cVMessageRequestCVSync, 1);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    CVMessage cVMessage3 = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSubscribeEvent = subscribeEvent(string3, cVMessage3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSubscribeEvent);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    CVMessage cVMessage4 = (CVMessage) parcel.readTypedObject(CVMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    unsubscribeEvent(string4, cVMessage4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String serviceVersion = getServiceVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(serviceVersion);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String captionFilePathFromURI = getCaptionFilePathFromURI(string5);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public CVMessage requestCVSync(String str, CVMessage cVMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CVMessage) parcelObtain2.readTypedObject(CVMessage.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public boolean subscribeEvent(String str, CVMessage cVMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public void unsubscribeEvent(String str, CVMessage cVMessage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cVMessage, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public String getServiceVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.allshare.iface.ISubscriber
            public String getCaptionFilePathFromURI(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISubscriber.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
