package com.android.ims.internal.uce.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.CapInfo;
import com.android.ims.internal.uce.common.StatusCode;
import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;

/* loaded from: classes5.dex */
public interface IOptionsService extends IInterface {

    public static class Default implements IOptionsService {
        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode addListener(int i, IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode getContactCap(int i, String str, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode getMyInfo(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode getVersion(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode removeListener(int i, UceLong uceLong) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode responseIncomingOptions(int i, int i2, int i3, String str, OptionsCapInfo optionsCapInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsService
        public StatusCode setMyInfo(int i, CapInfo capInfo, int i2) throws RemoteException {
            return null;
        }
    }

    StatusCode addListener(int i, IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException;

    StatusCode getContactCap(int i, String str, int i2) throws RemoteException;

    StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException;

    StatusCode getMyInfo(int i, int i2) throws RemoteException;

    StatusCode getVersion(int i) throws RemoteException;

    StatusCode removeListener(int i, UceLong uceLong) throws RemoteException;

    StatusCode responseIncomingOptions(int i, int i2, int i3, String str, OptionsCapInfo optionsCapInfo, boolean z) throws RemoteException;

    StatusCode setMyInfo(int i, CapInfo capInfo, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IOptionsService {
        public static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsService";
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_getContactCap = 6;
        static final int TRANSACTION_getContactListCap = 7;
        static final int TRANSACTION_getMyInfo = 5;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_responseIncomingOptions = 8;
        static final int TRANSACTION_setMyInfo = 4;

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

        public static IOptionsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOptionsService)) {
                return (IOptionsService) queryLocalInterface;
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
                    return "setMyInfo";
                case 5:
                    return "getMyInfo";
                case 6:
                    return "getContactCap";
                case 7:
                    return "getContactListCap";
                case 8:
                    return "responseIncomingOptions";
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
                    IOptionsListener asInterface = IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
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
                    CapInfo capInfo = (CapInfo) parcel.readTypedObject(CapInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode myInfo = setMyInfo(readInt4, capInfo, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(myInfo, 1);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode myInfo2 = getMyInfo(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(myInfo2, 1);
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
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String readString2 = parcel.readString();
                    OptionsCapInfo optionsCapInfo = (OptionsCapInfo) parcel.readTypedObject(OptionsCapInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    StatusCode responseIncomingOptions = responseIncomingOptions(readInt12, readInt13, readInt14, readString2, optionsCapInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(responseIncomingOptions, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOptionsService {
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

            @Override // com.android.ims.internal.uce.options.IOptionsService
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

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode addListener(int i, IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iOptionsListener);
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

            @Override // com.android.ims.internal.uce.options.IOptionsService
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

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode setMyInfo(int i, CapInfo capInfo, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(capInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode getMyInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusCode) obtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
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

            @Override // com.android.ims.internal.uce.options.IOptionsService
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

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode responseIncomingOptions(int i, int i2, int i3, String str, OptionsCapInfo optionsCapInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeTypedObject(optionsCapInfo, 0);
                    obtain.writeBoolean(z);
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
