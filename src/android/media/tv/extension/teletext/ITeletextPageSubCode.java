package android.media.tv.extension.teletext;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITeletextPageSubCode extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.teletext.ITeletextPageSubCode";

    public static class Default implements ITeletextPageSubCode {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public Bundle getTeletextHasTopInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public Bundle getTeletextPageNumber(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public Bundle getTeletextPageSubCode(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public Bundle getTeletextTopBlockList(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public Bundle getTeletextTopGroupList(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public Bundle getTeletextTopPageList(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public void setTeleltextPageNumber(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
        public void setTeletextPageSubCode(String str, int i) throws RemoteException {
        }
    }

    Bundle getTeletextHasTopInfo(String str) throws RemoteException;

    Bundle getTeletextPageNumber(String str) throws RemoteException;

    Bundle getTeletextPageSubCode(String str) throws RemoteException;

    Bundle getTeletextTopBlockList(String str) throws RemoteException;

    Bundle getTeletextTopGroupList(String str, int i) throws RemoteException;

    Bundle getTeletextTopPageList(String str, int i) throws RemoteException;

    void setTeleltextPageNumber(String str, int i) throws RemoteException;

    void setTeletextPageSubCode(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITeletextPageSubCode {
        static final int TRANSACTION_getTeletextHasTopInfo = 5;
        static final int TRANSACTION_getTeletextPageNumber = 1;
        static final int TRANSACTION_getTeletextPageSubCode = 3;
        static final int TRANSACTION_getTeletextTopBlockList = 6;
        static final int TRANSACTION_getTeletextTopGroupList = 7;
        static final int TRANSACTION_getTeletextTopPageList = 8;
        static final int TRANSACTION_setTeleltextPageNumber = 2;
        static final int TRANSACTION_setTeletextPageSubCode = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.teletext.ITeletextPageSubCode");
        }

        public static ITeletextPageSubCode asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.teletext.ITeletextPageSubCode");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITeletextPageSubCode)) {
                return (ITeletextPageSubCode) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTeletextPageNumber";
                case 2:
                    return "setTeleltextPageNumber";
                case 3:
                    return "getTeletextPageSubCode";
                case 4:
                    return "setTeletextPageSubCode";
                case 5:
                    return "getTeletextHasTopInfo";
                case 6:
                    return "getTeletextTopBlockList";
                case 7:
                    return "getTeletextTopGroupList";
                case 8:
                    return "getTeletextTopPageList";
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
                parcel.enforceInterface("android.media.tv.extension.teletext.ITeletextPageSubCode");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.teletext.ITeletextPageSubCode");
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle teletextPageNumber = getTeletextPageNumber(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(teletextPageNumber, 1);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTeleltextPageNumber(string2, i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle teletextPageSubCode = getTeletextPageSubCode(string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(teletextPageSubCode, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTeletextPageSubCode(string4, i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle teletextHasTopInfo = getTeletextHasTopInfo(string5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(teletextHasTopInfo, 1);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle teletextTopBlockList = getTeletextTopBlockList(string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(teletextTopBlockList, 1);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle teletextTopGroupList = getTeletextTopGroupList(string7, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(teletextTopGroupList, 1);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle teletextTopPageList = getTeletextTopPageList(string8, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(teletextTopPageList, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITeletextPageSubCode {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.teletext.ITeletextPageSubCode";
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public Bundle getTeletextPageNumber(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public void setTeleltextPageNumber(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public Bundle getTeletextPageSubCode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public void setTeletextPageSubCode(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public Bundle getTeletextHasTopInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public Bundle getTeletextTopBlockList(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public Bundle getTeletextTopGroupList(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.teletext.ITeletextPageSubCode
            public Bundle getTeletextTopPageList(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.teletext.ITeletextPageSubCode");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
