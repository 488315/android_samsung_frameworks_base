package com.samsung.android.knox.mtd;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.mtd.IMtdCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IMTDService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mtd.IMTDService";

    public static class Default implements IMTDService {
        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeContent(String str, String str2, int i, boolean z, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeFrameBuffers(List<FrameBuffersInfo> list) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeURL(String str, String str2, int i, boolean z, Intent intent) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void analyzeURLs(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.mtd.IMTDService
        public String getSystemProperty(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.mtd.IMTDService
        public void setSystemProperty(String str, String str2) throws RemoteException {
        }
    }

    void analyzeContent(String str, String str2, int i, boolean z, int i2) throws RemoteException;

    void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException;

    void analyzeFrameBuffers(List<FrameBuffersInfo> list) throws RemoteException;

    void analyzeURL(String str, String str2, int i, boolean z, Intent intent) throws RemoteException;

    void analyzeURLs(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException;

    String getSystemProperty(String str) throws RemoteException;

    void setSystemProperty(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMTDService {
        static final int TRANSACTION_analyzeContent = 1;
        static final int TRANSACTION_analyzeContents = 4;
        static final int TRANSACTION_analyzeFrameBuffers = 5;
        static final int TRANSACTION_analyzeURL = 2;
        static final int TRANSACTION_analyzeURLs = 3;
        static final int TRANSACTION_getSystemProperty = 7;
        static final int TRANSACTION_setSystemProperty = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IMTDService.DESCRIPTOR);
        }

        public static IMTDService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMTDService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMTDService)) {
                return (IMTDService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "analyzeContent";
                case 2:
                    return "analyzeURL";
                case 3:
                    return "analyzeURLs";
                case 4:
                    return "analyzeContents";
                case 5:
                    return "analyzeFrameBuffers";
                case 6:
                    return "setSystemProperty";
                case 7:
                    return "getSystemProperty";
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
                parcel.enforceInterface(IMTDService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMTDService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    analyzeContent(readString, readString2, readInt, readBoolean, readInt2);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    analyzeURL(readString3, readString4, readInt3, readBoolean2, intent);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    IMtdCallback asInterface = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    analyzeURLs(createStringArrayList, asInterface, readString5);
                    return true;
                case 4:
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    IMtdCallback asInterface2 = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    analyzeContents(createStringArrayList2, asInterface2);
                    return true;
                case 5:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(FrameBuffersInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    analyzeFrameBuffers(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSystemProperty(readString6, readString7);
                    return true;
                case 7:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String systemProperty = getSystemProperty(readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(systemProperty);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMTDService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMTDService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeContent(String str, String str2, int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeURL(String str, String str2, int i, boolean z, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeURLs(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iMtdCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iMtdCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeFrameBuffers(List<FrameBuffersInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void setSystemProperty(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public String getSystemProperty(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
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
