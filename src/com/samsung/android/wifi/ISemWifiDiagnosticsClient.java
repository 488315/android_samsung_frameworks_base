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

    public static class Default implements ISemWifiDiagnosticsClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public void clearHistory() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public List<String> getDiagnosisResults() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public void runDiagnosis(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
        public List<Bundle> setupDelegation(String str) throws RemoteException {
            return null;
        }
    }

    void clearHistory() throws RemoteException;

    List<String> getDiagnosisResults() throws RemoteException;

    void runDiagnosis(String str, String str2) throws RemoteException;

    List<Bundle> setupDelegation(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiDiagnosticsClient {
        static final int TRANSACTION_clearHistory = 3;
        static final int TRANSACTION_getDiagnosisResults = 4;
        static final int TRANSACTION_runDiagnosis = 2;
        static final int TRANSACTION_setupDelegation = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISemWifiDiagnosticsClient.DESCRIPTOR);
        }

        public static ISemWifiDiagnosticsClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemWifiDiagnosticsClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemWifiDiagnosticsClient)) {
                return (ISemWifiDiagnosticsClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setupDelegation";
            }
            if (i == 2) {
                return "runDiagnosis";
            }
            if (i == 3) {
                return "clearHistory";
            }
            if (i != 4) {
                return null;
            }
            return "getDiagnosisResults";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiDiagnosticsClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiDiagnosticsClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                List<Bundle> list = setupDelegation(string);
                parcel2.writeNoException();
                parcel2.writeTypedList(list, 1);
            } else if (i == 2) {
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                runDiagnosis(string2, string3);
            } else if (i == 3) {
                clearHistory();
            } else if (i == 4) {
                List<String> diagnosisResults = getDiagnosisResults();
                parcel2.writeNoException();
                parcel2.writeStringList(diagnosisResults);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemWifiDiagnosticsClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiDiagnosticsClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public List<Bundle> setupDelegation(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public void runDiagnosis(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public void clearHistory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemWifiDiagnosticsClient
            public List<String> getDiagnosisResults() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemWifiDiagnosticsClient.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
