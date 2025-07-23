package com.samsung.android.mhs.ai;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemMhsAiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mhs.ai.ISemMhsAiService";

    public static class Default implements ISemMhsAiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mhs.ai.ISemMhsAiService
        public void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException {
        }

        @Override // com.samsung.android.mhs.ai.ISemMhsAiService
        public void toggleDebugMode(boolean z) throws RemoteException {
        }
    }

    void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException;

    void toggleDebugMode(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemMhsAiService {
        static final int TRANSACTION_serviceTypeQuery = 1;
        static final int TRANSACTION_toggleDebugMode = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemMhsAiService.DESCRIPTOR);
        }

        public static ISemMhsAiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemMhsAiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemMhsAiService)) {
                return (ISemMhsAiService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "serviceTypeQuery";
            }
            if (i != 2) {
                return null;
            }
            return "toggleDebugMode";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemMhsAiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemMhsAiService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float[][] fArr = (float[][]) parcel.createFixedArray(float[][].class, 12, 60);
                String[] createStringArray = parcel.createStringArray();
                int[] createIntArray = parcel.createIntArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                serviceTypeQuery(fArr, createStringArray, createIntArray, readInt);
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                toggleDebugMode(readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemMhsAiService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMhsAiService.DESCRIPTOR;
            }

            @Override // com.samsung.android.mhs.ai.ISemMhsAiService
            public void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemMhsAiService.DESCRIPTOR);
                    obtain.writeFixedArray(fArr, 0, 12, 60);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mhs.ai.ISemMhsAiService
            public void toggleDebugMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemMhsAiService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
