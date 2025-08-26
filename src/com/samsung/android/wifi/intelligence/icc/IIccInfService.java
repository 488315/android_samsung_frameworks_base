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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIccInfService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIccInfService)) {
                return (IIccInfService) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                int i3 = parcel.readInt();
                String string2 = parcel.readString();
                float[] fArrCreateFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                iccInfResult(string, i3, string2, fArrCreateFloatArray);
            } else if (i == 2) {
                String string3 = parcel.readString();
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                inferenceKey(string3, arrayListCreateStringArrayList);
            } else if (i == 3) {
                ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                inferenceList(arrayListCreateStringArrayList2, arrayListCreateStringArrayList3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIccInfService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
            public void inferenceKey(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIccInfService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccInfService
            public void inferenceList(List<String> list, List<String> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIccInfService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
