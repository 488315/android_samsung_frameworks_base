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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIncidentManager)) {
                return (IIncidentManager) iInterfaceQueryLocalInterface;
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
                    IIncidentReportStatusListener iIncidentReportStatusListenerAsInterface = IIncidentReportStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    FileDescriptor rawFileDescriptor = parcel.readRawFileDescriptor();
                    parcel.enforceNoDataAvail();
                    reportIncidentToStream(incidentReportArgs2, iIncidentReportStatusListenerAsInterface, rawFileDescriptor);
                    return true;
                case 3:
                    FileDescriptor rawFileDescriptor2 = parcel.readRawFileDescriptor();
                    IIncidentReportStatusListener iIncidentReportStatusListenerAsInterface2 = IIncidentReportStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    reportIncidentToDumpstate(rawFileDescriptor2, iIncidentReportStatusListenerAsInterface2);
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    IIncidentDumpCallback iIncidentDumpCallbackAsInterface = IIncidentDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSection(i3, string, iIncidentDumpCallbackAsInterface);
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSection(i4);
                    return true;
                case 6:
                    systemRunning();
                    return true;
                case 7:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> incidentReportList = getIncidentReportList(string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(incidentReportList);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IncidentManager.IncidentReport incidentReport = getIncidentReport(string4, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(incidentReport, 1);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteIncidentReports(string7, string8, string9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteAllIncidentReports(string10);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(incidentReportArgs, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void reportIncidentToStream(IncidentReportArgs incidentReportArgs, IIncidentReportStatusListener iIncidentReportStatusListener, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(incidentReportArgs, 0);
                    parcelObtain.writeStrongInterface(iIncidentReportStatusListener);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void reportIncidentToDumpstate(FileDescriptor fileDescriptor, IIncidentReportStatusListener iIncidentReportStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeRawFileDescriptor(fileDescriptor);
                    parcelObtain.writeStrongInterface(iIncidentReportStatusListener);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void registerSection(int i, String str, IIncidentDumpCallback iIncidentDumpCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iIncidentDumpCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void unregisterSection(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void systemRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public List<String> getIncidentReportList(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public IncidentManager.IncidentReport getIncidentReport(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IncidentManager.IncidentReport) parcelObtain2.readTypedObject(IncidentManager.IncidentReport.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void deleteIncidentReports(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIncidentManager
            public void deleteAllIncidentReports(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
