package android.print;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.print.IPrintSpoolerCallbacks;
import android.print.IPrintSpoolerClient;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPrintSpooler extends IInterface {

    public static class Default implements IPrintSpooler {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.print.IPrintSpooler
        public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void createPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void getPrintJobInfos(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, ComponentName componentName, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void removeObsoletePrintJobs() throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setClient(IPrintSpoolerClient iPrintSpoolerClient) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setPrintJobCancelling(PrintJobId printJobId, boolean z) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setPrintJobState(PrintJobId printJobId, int i, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setPrintJobTag(PrintJobId printJobId, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpooler
        public void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException {
        }
    }

    void clearCustomPrinterIconCache(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void createPrintJob(PrintJobInfo printJobInfo) throws RemoteException;

    void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws RemoteException;

    void getPrintJobInfos(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, ComponentName componentName, int i, int i2, int i3) throws RemoteException;

    void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException;

    void removeObsoletePrintJobs() throws RemoteException;

    void setClient(IPrintSpoolerClient iPrintSpoolerClient) throws RemoteException;

    void setPrintJobCancelling(PrintJobId printJobId, boolean z) throws RemoteException;

    void setPrintJobState(PrintJobId printJobId, int i, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws RemoteException;

    void setPrintJobTag(PrintJobId printJobId, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException;

    void setProgress(PrintJobId printJobId, float f) throws RemoteException;

    void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException;

    void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException;

    void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException;

    public static abstract class Stub extends Binder implements IPrintSpooler {
        public static final String DESCRIPTOR = "android.print.IPrintSpooler";
        static final int TRANSACTION_clearCustomPrinterIconCache = 11;
        static final int TRANSACTION_createPrintJob = 4;
        static final int TRANSACTION_getCustomPrinterIcon = 10;
        static final int TRANSACTION_getPrintJobInfo = 3;
        static final int TRANSACTION_getPrintJobInfos = 2;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 9;
        static final int TRANSACTION_pruneApprovedPrintServices = 16;
        static final int TRANSACTION_removeObsoletePrintJobs = 1;
        static final int TRANSACTION_setClient = 14;
        static final int TRANSACTION_setPrintJobCancelling = 15;
        static final int TRANSACTION_setPrintJobState = 5;
        static final int TRANSACTION_setPrintJobTag = 12;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpooler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPrintSpooler)) {
                return (IPrintSpooler) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "removeObsoletePrintJobs";
                case 2:
                    return "getPrintJobInfos";
                case 3:
                    return "getPrintJobInfo";
                case 4:
                    return "createPrintJob";
                case 5:
                    return "setPrintJobState";
                case 6:
                    return "setProgress";
                case 7:
                    return "setStatus";
                case 8:
                    return "setStatusRes";
                case 9:
                    return "onCustomPrinterIconLoaded";
                case 10:
                    return "getCustomPrinterIcon";
                case 11:
                    return "clearCustomPrinterIconCache";
                case 12:
                    return "setPrintJobTag";
                case 13:
                    return "writePrintJobData";
                case 14:
                    return "setClient";
                case 15:
                    return "setPrintJobCancelling";
                case 16:
                    return "pruneApprovedPrintServices";
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
                    removeObsoletePrintJobs();
                    return true;
                case 2:
                    IPrintSpoolerCallbacks asInterface = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPrintJobInfos(asInterface, componentName, readInt, readInt2, readInt3);
                    return true;
                case 3:
                    PrintJobId printJobId = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    IPrintSpoolerCallbacks asInterface2 = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPrintJobInfo(printJobId, asInterface2, readInt4, readInt5);
                    return true;
                case 4:
                    PrintJobInfo printJobInfo = (PrintJobInfo) parcel.readTypedObject(PrintJobInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    createPrintJob(printJobInfo);
                    return true;
                case 5:
                    PrintJobId printJobId2 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    IPrintSpoolerCallbacks asInterface3 = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintJobState(printJobId2, readInt6, readString, asInterface3, readInt7);
                    return true;
                case 6:
                    PrintJobId printJobId3 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setProgress(printJobId3, readFloat);
                    return true;
                case 7:
                    PrintJobId printJobId4 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatus(printJobId4, charSequence);
                    return true;
                case 8:
                    PrintJobId printJobId5 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt8 = parcel.readInt();
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusRes(printJobId5, readInt8, charSequence2);
                    return true;
                case 9:
                    PrinterId printerId = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    IPrintSpoolerCallbacks asInterface4 = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCustomPrinterIconLoaded(printerId, icon, asInterface4, readInt9);
                    return true;
                case 10:
                    PrinterId printerId2 = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    IPrintSpoolerCallbacks asInterface5 = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCustomPrinterIcon(printerId2, asInterface5, readInt10);
                    return true;
                case 11:
                    IPrintSpoolerCallbacks asInterface6 = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearCustomPrinterIconCache(asInterface6, readInt11);
                    return true;
                case 12:
                    PrintJobId printJobId6 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    String readString2 = parcel.readString();
                    IPrintSpoolerCallbacks asInterface7 = IPrintSpoolerCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrintJobTag(printJobId6, readString2, asInterface7, readInt12);
                    return true;
                case 13:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    PrintJobId printJobId7 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    writePrintJobData(parcelFileDescriptor, printJobId7);
                    return true;
                case 14:
                    IPrintSpoolerClient asInterface8 = IPrintSpoolerClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setClient(asInterface8);
                    return true;
                case 15:
                    PrintJobId printJobId8 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPrintJobCancelling(printJobId8, readBoolean);
                    return true;
                case 16:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    pruneApprovedPrintServices(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPrintSpooler {
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

            @Override // android.print.IPrintSpooler
            public void removeObsoletePrintJobs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void getPrintJobInfos(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, ComponentName componentName, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void createPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setPrintJobState(PrintJobId printJobId, int i, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setPrintJobTag(PrintJobId printJobId, String str, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPrintSpoolerCallbacks);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(printJobId, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setClient(IPrintSpoolerClient iPrintSpoolerClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintSpoolerClient);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void setPrintJobCancelling(PrintJobId printJobId, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintSpooler
            public void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
