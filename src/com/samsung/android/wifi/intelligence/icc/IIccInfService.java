package com.samsung.android.wifi.intelligence.icc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IIccInfService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.icc.IIccInfService";

    public static class Default implements IIccInfService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
        public void iccInfResult(String str, int i, String str2, float[] fArr) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
        public void inferenceKey(String str, List<String> list) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
        public void inferenceList(List<String> list, List<String> list2) throws RemoteException {
        }
    }

    void iccInfResult(String str, int i, String str2, float[] fArr) throws RemoteException;

    void inferenceKey(String str, List<String> list) throws RemoteException;

    void inferenceList(List<String> list, List<String> list2) throws RemoteException;

    public static abstract class Stub extends Binder implements IIccInfService {
        static final int TRANSACTION_iccInfResult = 1;
        static final int TRANSACTION_inferenceKey = 2;
        static final int TRANSACTION_inferenceList = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IIccInfService.DESCRIPTOR);
        }

        public static IIccInfService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIccInfService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIccInfService)) {
                return (IIccInfService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "iccInfResult";
            }
            if (i == 2) {
                return "inferenceKey";
            }
            if (i != 3) {
                return null;
            }
            return "inferenceList";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIccInfService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIccInfService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                float[] createFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                iccInfResult(readString, readInt, readString2, createFloatArray);
            } else if (i == 2) {
                String readString3 = parcel.readString();
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                inferenceKey(readString3, createStringArrayList);
            } else if (i == 3) {
                ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                inferenceList(createStringArrayList2, createStringArrayList3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIccInfService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIccInfService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
            public void iccInfResult(String str, int i, String str2, float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIccInfService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
            public void inferenceKey(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIccInfService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
            public void inferenceList(List<String> list, List<String> list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIccInfService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
