package android.os;

import android.os.IIncidentDumpCallback;
import android.os.IIncidentReportStatusListener;
import android.os.IncidentManager;
import java.io.FileDescriptor;
import java.util.List;

/* loaded from: classes3.dex */
public interface IIncidentManager extends IInterface {

    public static class Default implements IIncidentManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIncidentManager
        public void deleteAllIncidentReports(String str) throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void deleteIncidentReports(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.os.IIncidentManager
        public List<String> getIncidentReportList(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.os.IIncidentManager
        public void registerSection(int i, String str, IIncidentDumpCallback iIncidentDumpCallback) throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void reportIncident(IncidentReportArgs incidentReportArgs) throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void reportIncidentToDumpstate(FileDescriptor fileDescriptor, IIncidentReportStatusListener iIncidentReportStatusListener) throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void reportIncidentToStream(IncidentReportArgs incidentReportArgs, IIncidentReportStatusListener iIncidentReportStatusListener, FileDescriptor fileDescriptor) throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void systemRunning() throws RemoteException {
        }

        @Override // android.os.IIncidentManager
        public void unregisterSection(int i) throws RemoteException {
        }
    }

    void deleteAllIncidentReports(String str) throws RemoteException;

    void deleteIncidentReports(String str, String str2, String str3) throws RemoteException;

    IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException;

    List<String> getIncidentReportList(String str, String str2) throws RemoteException;

    void registerSection(int i, String str, IIncidentDumpCallback iIncidentDumpCallback) throws RemoteException;

    void reportIncident(IncidentReportArgs incidentReportArgs) throws RemoteException;

    void reportIncidentToDumpstate(FileDescriptor fileDescriptor, IIncidentReportStatusListener iIncidentReportStatusListener) throws RemoteException;

    void reportIncidentToStream(IncidentReportArgs incidentReportArgs, IIncidentReportStatusListener iIncidentReportStatusListener, FileDescriptor fileDescriptor) throws RemoteException;

    void systemRunning() throws RemoteException;

    void unregisterSection(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IIncidentManager {
        public static final String DESCRIPTOR = "android.os.IIncidentManager";
        static final int TRANSACTION_deleteAllIncidentReports = 10;
        static final int TRANSACTION_deleteIncidentReports = 9;
        static final int TRANSACTION_getIncidentReport = 8;
        static final int TRANSACTION_getIncidentReportList = 7;
        static final int TRANSACTION_registerSection = 4;
        static final int TRANSACTION_reportIncident = 1;
        static final int TRANSACTION_reportIncidentToDumpstate = 3;
        static final int TRANSACTION_reportIncidentToStream = 2;
        static final int TRANSACTION_systemRunning = 6;
        static final int TRANSACTION_unregisterSection = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncidentManager)) {
                return (IIncidentManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportIncident";
                case 2:
                    return "reportIncidentToStream";
                case 3:
                    return "reportIncidentToDumpstate";
                case 4:
                    return "registerSection";
                case 5:
                    return "unregisterSection";
                case 6:
                    return "systemRunning";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IncidentReportArgs incidentReportArgs = (IncidentReportArgs) parcel.readTypedObject(IncidentReportArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportIncident(incidentReportArgs);
                    return true;
                case 2:
                    IncidentReportArgs incidentReportArgs2 = (IncidentReportArgs) parcel.readTypedObject(IncidentReportArgs.CREATOR);
                    IIncidentReportStatusListener asInterface = IIncidentReportStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    reportIncidentToStream(incidentReportArgs2, asInterface, readRawFileDescriptor);
                    return true;
                case 3:
                    FileDescriptor readRawFileDescriptor2 = parcel.readRawFileDescriptor();
                    IIncidentReportStatusListener asInterface2 = IIncidentReportStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    reportIncidentToDumpstate(readRawFileDescriptor2, asInterface2);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    IIncidentDumpCallback asInterface3 = IIncidentDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSection(readInt, readString, asInterface3);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSection(readInt2);
                    return true;
                case 6:
                    systemRunning();
                    return true;
                case 7:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> incidentReportList = getIncidentReportList(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(incidentReportList);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IncidentManager.IncidentReport incidentReport = getIncidentReport(readString4, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(incidentReport, 1);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteIncidentReports(readString7, readString8, readString9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteAllIncidentReports(readString10);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIncidentManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IIncidentManager
            public void reportIncident(IncidentReportArgs incidentReportArgs) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incidentReportArgs, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void reportIncidentToStream(IncidentReportArgs incidentReportArgs, IIncidentReportStatusListener iIncidentReportStatusListener, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incidentReportArgs, 0);
                    obtain.writeStrongInterface(iIncidentReportStatusListener);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void reportIncidentToDumpstate(FileDescriptor fileDescriptor, IIncidentReportStatusListener iIncidentReportStatusListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeStrongInterface(iIncidentReportStatusListener);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void registerSection(int i, String str, IIncidentDumpCallback iIncidentDumpCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIncidentDumpCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void unregisterSection(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void systemRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public List<String> getIncidentReportList(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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

            @Override // android.os.IIncidentManager
            public IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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

            @Override // android.os.IIncidentManager
            public void deleteIncidentReports(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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

            @Override // android.os.IIncidentManager
            public void deleteAllIncidentReports(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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
