package com.samsung.android.wifi.ai;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiAiService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ai.ISemWifiAiService";

    void nsdTerminate() throws RemoteException;

    void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException;

    void toggleDebugMode(boolean z) throws RemoteException;

    public static class Default implements ISemWifiAiService {
        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void nsdTerminate() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void serviceTypeQuery(float[][] trfDataArr, String[] convArr, int[] timeStepArr, int convCnt) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ai.ISemWifiAiService
        public void toggleDebugMode(boolean debug) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemWifiAiService {
        static final int TRANSACTION_nsdTerminate = 1;
        static final int TRANSACTION_serviceTypeQuery = 2;
        static final int TRANSACTION_toggleDebugMode = 3;

        public Stub() {
            attachInterface(this, ISemWifiAiService.DESCRIPTOR);
        }

        public static ISemWifiAiService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiAiService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiAiService)) {
                return (ISemWifiAiService) iin;
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
                    return "nsdTerminate";
                case 2:
                    return "serviceTypeQuery";
                case 3:
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
                data.enforceInterface(ISemWifiAiService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemWifiAiService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    nsdTerminate();
                    return true;
                case 2:
                    float[][] _arg0 = (float[][]) data.createFixedArray(float[][].class, 7, 60);
                    String[] _arg1 = data.createStringArray();
                    int[] _arg2 = data.createIntArray();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    serviceTypeQuery(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 3:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    toggleDebugMode(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemWifiAiService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ai.ISemWifiAiService
            public void serviceTypeQuery(float[][] trfDataArr, String[] convArr, int[] timeStepArr, int convCnt) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    _data.writeFixedArray(trfDataArr, 0, 7, 60);
                    _data.writeStringArray(convArr);
                    _data.writeIntArray(timeStepArr);
                    _data.writeInt(convCnt);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ai.ISemWifiAiService
            public void toggleDebugMode(boolean debug) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
                    _data.writeBoolean(debug);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
