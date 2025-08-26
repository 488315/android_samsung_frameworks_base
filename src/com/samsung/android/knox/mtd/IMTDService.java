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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMTDService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMTDService)) {
                return (IMTDService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    analyzeContent(string, string2, i3, z, i4);
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    int i5 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    analyzeURL(string3, string4, i5, z2, intent);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    IMtdCallback iMtdCallbackAsInterface = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    analyzeURLs(arrayListCreateStringArrayList, iMtdCallbackAsInterface, string5);
                    return true;
                case 4:
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    IMtdCallback iMtdCallbackAsInterface2 = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    analyzeContents(arrayListCreateStringArrayList2, iMtdCallbackAsInterface2);
                    return true;
                case 5:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(FrameBuffersInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    analyzeFrameBuffers(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSystemProperty(string6, string7);
                    return true;
                case 7:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String systemProperty = getSystemProperty(string8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeURL(String str, String str2, int i, boolean z, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeURLs(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iMtdCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iMtdCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void analyzeFrameBuffers(List<FrameBuffersInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public void setSystemProperty(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IMTDService
            public String getSystemProperty(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMTDService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
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
