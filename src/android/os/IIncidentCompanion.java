package android.os;

import android.os.IIncidentAuthListener;
import android.os.IncidentManager;
import java.util.List;

/* loaded from: classes3.dex */
public interface IIncidentCompanion extends IInterface {
    public static final String DESCRIPTOR = "android.os.IIncidentCompanion";

    public static class Default implements IIncidentCompanion {
        @Override // android.os.IIncidentCompanion
        public void approveReport(String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public void authorizeReport(int i, String str, String str2, String str3, int i2, IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void cancelAuthorization(IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void deleteAllIncidentReports(String str) throws RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void deleteIncidentReports(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public void denyReport(String str) throws RemoteException {
        }

        @Override // android.os.IIncidentCompanion
        public IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public List<String> getIncidentReportList(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public List<String> getPendingReports() throws RemoteException {
            return null;
        }

        @Override // android.os.IIncidentCompanion
        public void sendReportReadyBroadcast(String str, String str2) throws RemoteException {
        }
    }

    void approveReport(String str) throws RemoteException;

    void authorizeReport(int i, String str, String str2, String str3, int i2, IIncidentAuthListener iIncidentAuthListener) throws RemoteException;

    void cancelAuthorization(IIncidentAuthListener iIncidentAuthListener) throws RemoteException;

    void deleteAllIncidentReports(String str) throws RemoteException;

    void deleteIncidentReports(String str, String str2, String str3) throws RemoteException;

    void denyReport(String str) throws RemoteException;

    IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException;

    List<String> getIncidentReportList(String str, String str2) throws RemoteException;

    List<String> getPendingReports() throws RemoteException;

    void sendReportReadyBroadcast(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IIncidentCompanion {
        static final int TRANSACTION_approveReport = 5;
        static final int TRANSACTION_authorizeReport = 1;
        static final int TRANSACTION_cancelAuthorization = 2;
        static final int TRANSACTION_deleteAllIncidentReports = 10;
        static final int TRANSACTION_deleteIncidentReports = 9;
        static final int TRANSACTION_denyReport = 6;
        static final int TRANSACTION_getIncidentReport = 8;
        static final int TRANSACTION_getIncidentReportList = 7;
        static final int TRANSACTION_getPendingReports = 4;
        static final int TRANSACTION_sendReportReadyBroadcast = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IIncidentCompanion.DESCRIPTOR);
        }

        public static IIncidentCompanion asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIncidentCompanion.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncidentCompanion)) {
                return (IIncidentCompanion) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "authorizeReport";
                case 2:
                    return "cancelAuthorization";
                case 3:
                    return "sendReportReadyBroadcast";
                case 4:
                    return "getPendingReports";
                case 5:
                    return "approveReport";
                case 6:
                    return "denyReport";
                case 7:
                    return "getIncidentReportList";
                case 8:
                    return "getIncidentReport";
                case 9:
                    return "deleteIncidentReports";
                case 10:
                    return "deleteAllIncidentReports";
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
                parcel.enforceInterface(IIncidentCompanion.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIncidentCompanion.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    IIncidentAuthListener asInterface = IIncidentAuthListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    authorizeReport(readInt, readString, readString2, readString3, readInt2, asInterface);
                    return true;
                case 2:
                    IIncidentAuthListener asInterface2 = IIncidentAuthListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelAuthorization(asInterface2);
                    return true;
                case 3:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendReportReadyBroadcast(readString4, readString5);
                    return true;
                case 4:
                    List<String> pendingReports = getPendingReports();
                    parcel2.writeNoException();
                    parcel2.writeStringList(pendingReports);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    approveReport(readString6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    denyReport(readString7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> incidentReportList = getIncidentReportList(readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeStringList(incidentReportList);
                    return true;
                case 8:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IncidentManager.IncidentReport incidentReport = getIncidentReport(readString10, readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(incidentReport, 1);
                    return true;
                case 9:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteIncidentReports(readString13, readString14, readString15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteAllIncidentReports(readString16);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIncidentCompanion {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncidentCompanion.DESCRIPTOR;
            }

            @Override // android.os.IIncidentCompanion
            public void authorizeReport(int i, String str, String str2, String str3, int i2, IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIncidentAuthListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void cancelAuthorization(IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeStrongInterface(iIncidentAuthListener);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void sendReportReadyBroadcast(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public List<String> getPendingReports() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void approveReport(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void denyReport(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public List<String> getIncidentReportList(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IncidentManager.IncidentReport) obtain2.readTypedObject(IncidentManager.IncidentReport.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void deleteIncidentReports(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentCompanion
            public void deleteAllIncidentReports(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIncidentCompanion.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
