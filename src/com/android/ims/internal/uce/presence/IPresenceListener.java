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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPresenceListener)) {
                return (IPresenceListener) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getVersionCb(string);
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
                    String string2 = parcel.readString();
                    PresTupleInfo[] presTupleInfoArr = (PresTupleInfo[]) parcel.createTypedArray(PresTupleInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    capInfoReceived(string2, presTupleInfoArr);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void serviceAvailable(StatusCode statusCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void serviceUnAvailable(StatusCode statusCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void publishTriggering(PresPublishTriggerType presPublishTriggerType) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(presPublishTriggerType, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void cmdStatus(PresCmdStatus presCmdStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(presCmdStatus, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void sipResponseReceived(PresSipResponse presSipResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(presSipResponse, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void capInfoReceived(String str, PresTupleInfo[] presTupleInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(presTupleInfoArr, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void listCapInfoReceived(PresRlmiInfo presRlmiInfo, PresResInfo[] presResInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(presRlmiInfo, 0);
                    parcelObtain.writeTypedArray(presResInfoArr, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceListener
            public void unpublishMessageSent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
