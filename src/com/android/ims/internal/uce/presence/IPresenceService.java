package com.android.ims.internal.uce.presence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.presence.IPresenceListener;

/* loaded from: classes5.dex */
public interface IPresenceService extends IInterface {

    public static class Default implements IPresenceService {
        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode addListener(int i, IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode getContactCap(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode getVersion(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode publishMyCap(int i, PresCapInfo presCapInfo, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode reenableService(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode removeListener(int i, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.presence.IPresenceService
        public StatusCode setNewFeatureTag(int i, String str, PresServiceInfo presServiceInfo, int i2) throws RemoteException {
            return null;
        }
    }

    StatusCode addListener(int i, IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException;

    StatusCode getContactCap(int i, String str, int i2) throws RemoteException;

    StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException;

    StatusCode getVersion(int i) throws RemoteException;

    StatusCode publishMyCap(int i, PresCapInfo presCapInfo, int i2) throws RemoteException;

    StatusCode reenableService(int i, int i2) throws RemoteException;

    StatusCode removeListener(int i, UceLong uceLong) throws RemoteException;

    StatusCode setNewFeatureTag(int i, String str, PresServiceInfo presServiceInfo, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IPresenceService {
        public static final String DESCRIPTOR = "com.android.ims.internal.uce.presence.IPresenceService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_publishMyCap = 5;
        static final int TRANSACTION_reenableService = 4;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setNewFeatureTag = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPresenceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPresenceService)) {
                return (IPresenceService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersion";
                case 2:
                    return "addListener";
                case 3:
                    return "removeListener";
                case 4:
                    return "reenableService";
                case 5:
                    return "publishMyCap";
                case 6:
                    return "getContactCap";
                case 7:
                    return "getContactListCap";
                case 8:
                    return "setNewFeatureTag";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode version = getVersion(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(version, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    IPresenceListener asInterface = IPresenceListener.Stub.asInterface(parcel.readStrongBinder());
                    UceLong uceLong = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    StatusCode addListener = addListener(readInt2, asInterface, uceLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addListener, 1);
                    parcel2.writeTypedObject(uceLong, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    UceLong uceLong2 = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    StatusCode removeListener = removeListener(readInt3, uceLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeListener, 1);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode reenableService = reenableService(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(reenableService, 1);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    PresCapInfo presCapInfo = (PresCapInfo) parcel.readTypedObject(PresCapInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode publishMyCap = publishMyCap(readInt6, presCapInfo, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(publishMyCap, 1);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode contactCap = getContactCap(readInt8, readString, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contactCap, 1);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode contactListCap = getContactListCap(readInt10, createStringArray, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contactListCap, 1);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    String readString2 = parcel.readString();
                    PresServiceInfo presServiceInfo = (PresServiceInfo) parcel.readTypedObject(PresServiceInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode newFeatureTag = setNewFeatureTag(readInt12, readString2, presServiceInfo, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(newFeatureTag, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPresenceService {
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

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode getVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode addListener(int i, IPresenceListener iPresenceListener, UceLong uceLong) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPresenceListener);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    StatusCode statusCode = (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                    if (obtain2.readInt() != 0) {
                        uceLong.readFromParcel(obtain2);
                    }
                    return statusCode;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode removeListener(int i, UceLong uceLong) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode reenableService(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode publishMyCap(int i, PresCapInfo presCapInfo, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(presCapInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode getContactCap(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.presence.IPresenceService
            public StatusCode setNewFeatureTag(int i, String str, PresServiceInfo presServiceInfo, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(presServiceInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
