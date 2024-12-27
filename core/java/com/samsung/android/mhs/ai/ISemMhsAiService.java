package com.samsung.android.mhs.ai;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemMhsAiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mhs.ai.ISemMhsAiService";

    void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException;

    void toggleDebugMode(boolean z) throws RemoteException;

    public static class Default implements ISemMhsAiService {
        @Override // com.samsung.android.mhs.ai.ISemMhsAiService
        public void serviceTypeQuery(float[][] trfDataArr, String[] convoStrArr, int[] timeStepArr, int convoCnt) throws RemoteException {
        }

        @Override // com.samsung.android.mhs.ai.ISemMhsAiService
        public void toggleDebugMode(boolean debug) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemMhsAiService {
        static final int TRANSACTION_serviceTypeQuery = 1;
        static final int TRANSACTION_toggleDebugMode = 2;

        public Stub() {
            attachInterface(this, ISemMhsAiService.DESCRIPTOR);
        }

        public static ISemMhsAiService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemMhsAiService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemMhsAiService)) {
                return (ISemMhsAiService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "serviceTypeQuery";
                case 2:
                    return "toggleDebugMode";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemMhsAiService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemMhsAiService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    float[][] _arg0 = (float[][]) data.createFixedArray(float[][].class, 12, 60);
                    String[] _arg1 = data.createStringArray();
                    int[] _arg2 = data.createIntArray();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    serviceTypeQuery(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    toggleDebugMode(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemMhsAiService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMhsAiService.DESCRIPTOR;
            }

            @Override // com.samsung.android.mhs.ai.ISemMhsAiService
            public void serviceTypeQuery(float[][] trfDataArr, String[] convoStrArr, int[] timeStepArr, int convoCnt) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemMhsAiService.DESCRIPTOR);
                    _data.writeFixedArray(trfDataArr, 0, 12, 60);
                    _data.writeStringArray(convoStrArr);
                    _data.writeIntArray(timeStepArr);
                    _data.writeInt(convoCnt);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.mhs.ai.ISemMhsAiService
            public void toggleDebugMode(boolean debug) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemMhsAiService.DESCRIPTOR);
                    _data.writeBoolean(debug);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
