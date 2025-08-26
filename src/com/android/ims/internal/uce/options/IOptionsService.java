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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IOptionsService)) {
                return (IOptionsService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode version = getVersion(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(version, 1);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    IOptionsListener iOptionsListenerAsInterface = IOptionsListener.Stub.asInterface(parcel.readStrongBinder());
                    UceLong uceLong = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    StatusCode statusCodeAddListener = addListener(i4, iOptionsListenerAsInterface, uceLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statusCodeAddListener, 1);
                    parcel2.writeTypedObject(uceLong, 1);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    UceLong uceLong2 = (UceLong) parcel.readTypedObject(UceLong.CREATOR);
                    parcel.enforceNoDataAvail();
                    StatusCode statusCodeRemoveListener = removeListener(i5, uceLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statusCodeRemoveListener, 1);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    CapInfo capInfo = (CapInfo) parcel.readTypedObject(CapInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode myInfo = setMyInfo(i6, capInfo, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(myInfo, 1);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode myInfo2 = getMyInfo(i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(myInfo2, 1);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    String string = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode contactCap = getContactCap(i10, string, i11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contactCap, 1);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    StatusCode contactListCap = getContactListCap(i12, strArrCreateStringArray, i13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contactListCap, 1);
                    return true;
                case 8:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String string2 = parcel.readString();
                    OptionsCapInfo optionsCapInfo = (OptionsCapInfo) parcel.readTypedObject(OptionsCapInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    StatusCode statusCodeResponseIncomingOptions = responseIncomingOptions(i14, i15, i16, string2, optionsCapInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(statusCodeResponseIncomingOptions, 1);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode addListener(int i, IOptionsListener iOptionsListener, UceLong uceLong) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iOptionsListener);
                    parcelObtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    StatusCode statusCode = (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                    if (parcelObtain2.readInt() != 0) {
                        uceLong.readFromParcel(parcelObtain2);
                    }
                    return statusCode;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode removeListener(int i, UceLong uceLong) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(uceLong, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode setMyInfo(int i, CapInfo capInfo, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(capInfo, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode getMyInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode getContactCap(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode getContactListCap(int i, String[] strArr, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsService
            public StatusCode responseIncomingOptions(int i, int i2, int i3, String str, OptionsCapInfo optionsCapInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(optionsCapInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusCode) parcelObtain2.readTypedObject(StatusCode.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
