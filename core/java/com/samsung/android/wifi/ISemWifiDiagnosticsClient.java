package com.samsung.android.wifi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISemWifiDiagnosticsClient extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiDiagnosticsClient";

    void clearHistory() throws RemoteException;

    List<String> getDiagnosisResults() throws RemoteException;

    void runDiagnosis(String str, String str2) throws RemoteException;

    List<Bundle> setupDelegation(String str) throws RemoteException;

    public static class Default implements ISemWifiDiagnosticsClient {
        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public List<Bundle> setupDelegation(String serviceVersion) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public void runDiagnosis(String diagnosisName, String dump) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public void clearHistory() throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public List<String> getDiagnosisResults() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemWifiDiagnosticsClient {
        static final int TRANSACTION_clearHistory = 3;
        static final int TRANSACTION_getDiagnosisResults = 4;
        static final int TRANSACTION_runDiagnosis = 2;
        static final int TRANSACTION_setupDelegation = 1;

        public Stub() {
            attachInterface(this, ISemWifiDiagnosticsClient.DESCRIPTOR);
        }

        public static ISemWifiDiagnosticsClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemWifiDiagnosticsClient.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemWifiDiagnosticsClient)) {
                return (ISemWifiDiagnosticsClient) iin;
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
                    return "setupDelegation";
                case 2:
                    return "runDiagnosis";
                case 3:
                    return "clearHistory";
                case 4:
                    return "getDiagnosisResults";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemWifiDiagnosticsClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemWifiDiagnosticsClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    List<Bundle> _result = setupDelegation(_arg0);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    runDiagnosis(_arg02, _arg1);
                    return true;
                case 3:
                    clearHistory();
                    return true;
                case 4:
                    List<String> _result2 = getDiagnosisResults();
                    reply.writeNoException();
                    reply.writeStringList(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemWifiDiagnosticsClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiDiagnosticsClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public List<Bundle> setupDelegation(String serviceVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    _data.writeString(serviceVersion);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<Bundle> _result = _reply.createTypedArrayList(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public void runDiagnosis(String diagnosisName, String dump) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    _data.writeString(diagnosisName);
                    _data.writeString(dump);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public void clearHistory() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public List<String> getDiagnosisResults() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
