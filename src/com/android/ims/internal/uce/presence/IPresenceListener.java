package com.android.ims.internal.uce.presence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;

/* loaded from: classes5.dex */
public interface IPresenceListener extends IInterface {

    public static class Default implements IPresenceListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void capInfoReceived(String str, PresTupleInfo[] presTupleInfoArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void getVersionCb(String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] presResInfoArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceListener
        public void unpublishMessageSent() throws RemoteException {
        }
    }

    void capInfoReceived(String str, PresTupleInfo[] presTupleInfoArr) throws RemoteException;

    void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException;

    void getVersionCb(String str) throws RemoteException;

    void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] presResInfoArr) throws RemoteException;

    void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException;

    void serviceAvailable(StatusCode statusCode) throws RemoteException;

    void serviceUnAvailable(StatusCode statusCode) throws RemoteException;

    void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException;

    void unpublishMessageSent() throws RemoteException;

    public static abstract class Stub extends Binder implements IPresenceListener {
        public static final String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceListener";
        static final int TRANSACTION_capInfoReceived = 7;
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_listCapInfoReceived = 8;
        static final int TRANSACTION_publishTriggering = 4;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnAvailable = 3;
        static final int TRANSACTION_sipResponseReceived = 6;
        static final int TRANSACTION_unpublishMessageSent = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPresenceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPresenceListener)) {
                return (IPresenceListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersionCb";
                case 2:
                    return "serviceAvailable";
                case 3:
                    return "serviceUnAvailable";
                case 4:
                    return "publishTriggering";
                case 5:
                    return "cmdStatus";
                case 6:
                    return "sipResponseReceived";
                case 7:
                    return "capInfoReceived";
                case 8:
                    return "listCapInfoReceived";
                case 9:
                    return "unpublishMessageSent";
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
                    parcel.enforceNoDataAvail();
                    getVersionCb(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    StatusCode statusCode = (StatusCode) parcel.readTypedObject(StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceAvailable(statusCode);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    StatusCode statusCode2 = (StatusCode) parcel.readTypedObject(StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceUnAvailable(statusCode2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    PresPublishTriggerType presPublishTriggerType = (PresPublishTriggerType) parcel.readTypedObject(PresPublishTriggerType.CREATOR);
                    parcel.enforceNoDataAvail();
                    publishTriggering(presPublishTriggerType);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    PresCmdStatus presCmdStatus = (PresCmdStatus) parcel.readTypedObject(PresCmdStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    cmdStatus(presCmdStatus);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    PresSipResponse presSipResponse = (PresSipResponse) parcel.readTypedObject(PresSipResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    sipResponseReceived(presSipResponse);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString2 = parcel.readString();
                    PresTupleInfo[] presTupleInfoArr = (PresTupleInfo[]) parcel.createTypedArray(PresTupleInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    capInfoReceived(readString2, presTupleInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    PresRlmiInfo presRlmiInfo = (PresRlmiInfo) parcel.readTypedObject(PresRlmiInfo.CREATOR);
                    PresResInfo[] presResInfoArr = (PresResInfo[]) parcel.createTypedArray(PresResInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    listCapInfoReceived(presRlmiInfo, presResInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    unpublishMessageSent();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPresenceListener {
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

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void getVersionCb(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void serviceAvailable(StatusCode statusCode) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presPublishTriggerType, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presCmdStatus, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presSipResponse, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void capInfoReceived(String str, PresTupleInfo[] presTupleInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(presTupleInfoArr, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] presResInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(presRlmiInfo, 0);
                    obtain.writeTypedArray(presResInfoArr, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void unpublishMessageSent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
