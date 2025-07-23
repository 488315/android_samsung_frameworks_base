package com.samsung.android.wifi.ai;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiAiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ai.ISemWifiAiService";

    public static class Default implements ISemWifiAiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void nsdTerminate() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void reInitialize() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void toggleDebugMode(boolean z) throws RemoteException {
        }
    }

    void nsdTerminate() throws RemoteException;

    void reInitialize() throws RemoteException;

    void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException;

    void toggleDebugMode(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiAiService {
        static final int TRANSACTION_nsdTerminate = 1;
        static final int TRANSACTION_reInitialize = 4;
        static final int TRANSACTION_serviceTypeQuery = 2;
        static final int TRANSACTION_toggleDebugMode = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISemWifiAiService.DESCRIPTOR);
        }

        public static ISemWifiAiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemWifiAiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemWifiAiService)) {
                return (ISemWifiAiService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "nsdTerminate";
            }
            if (i == 2) {
                return "serviceTypeQuery";
            }
            if (i == 3) {
                return "toggleDebugMode";
            }
            if (i != 4) {
                return null;
            }
            return "reInitialize";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiAiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiAiService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                nsdTerminate();
            } else if (i == 2) {
                float[][] fArr = (float[][]) parcel.createFixedArray(float[][].class, 7, 60);
                String[] createStringArray = parcel.createStringArray();
                int[] createIntArray = parcel.createIntArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                serviceTypeQuery(fArr, createStringArray, createIntArray, readInt);
            } else if (i == 3) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                toggleDebugMode(readBoolean);
            } else if (i == 4) {
                reInitialize();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemWifiAiService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiAiService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ai.ISemWifiAiService
            public void nsdTerminate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ai.ISemWifiAiService
            public void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    obtain.writeFixedArray(fArr, 0, 7, 60);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ai.ISemWifiAiService
            public void toggleDebugMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ai.ISemWifiAiService
            public void reInitialize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
