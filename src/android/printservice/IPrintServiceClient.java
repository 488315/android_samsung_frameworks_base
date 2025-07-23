package android.printservice;

import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPrintServiceClient extends IInterface {

    public static class Default implements IPrintServiceClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.printservice.IPrintServiceClient
        public PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException {
            return null;
        }

        @Override // android.printservice.IPrintServiceClient
        public List<PrintJobInfo> getPrintJobInfos() throws RemoteException {
            return null;
        }

        @Override // android.printservice.IPrintServiceClient
        public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public boolean setPrintJobState(PrintJobId printJobId, int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.printservice.IPrintServiceClient
        public boolean setPrintJobTag(PrintJobId printJobId, String str) throws RemoteException {
            return false;
        }

        @Override // android.printservice.IPrintServiceClient
        public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException {
        }

        @Override // android.printservice.IPrintServiceClient
        public void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException {
        }
    }

    PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException;

    List<PrintJobInfo> getPrintJobInfos() throws RemoteException;

    void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException;

    void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException;

    void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException;

    boolean setPrintJobState(PrintJobId printJobId, int i, String str) throws RemoteException;

    boolean setPrintJobTag(PrintJobId printJobId, String str) throws RemoteException;

    void setProgress(PrintJobId printJobId, float f) throws RemoteException;

    void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException;

    void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException;

    void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException;

    public static abstract class Stub extends Binder implements IPrintServiceClient {
        public static final String DESCRIPTOR = "android.printservice.IPrintServiceClient";
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 11;
        static final int TRANSACTION_onPrintersAdded = 9;
        static final int TRANSACTION_onPrintersRemoved = 10;
        static final int TRANSACTION_setPrintJobState = 3;
        static final int TRANSACTION_setPrintJobTag = 4;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintServiceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPrintServiceClient)) {
                return (IPrintServiceClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getPrintJobInfos";
                case 2:
                    return "getPrintJobInfo";
                case 3:
                    return "setPrintJobState";
                case 4:
                    return "setPrintJobTag";
                case 5:
                    return "writePrintJobData";
                case 6:
                    return "setProgress";
                case 7:
                    return "setStatus";
                case 8:
                    return "setStatusRes";
                case 9:
                    return "onPrintersAdded";
                case 10:
                    return "onPrintersRemoved";
                case 11:
                    return "onCustomPrinterIconLoaded";
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
                    List<PrintJobInfo> printJobInfos = getPrintJobInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(printJobInfos, 1);
                    return true;
                case 2:
                    PrintJobId printJobId = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    PrintJobInfo printJobInfo = getPrintJobInfo(printJobId);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(printJobInfo, 1);
                    return true;
                case 3:
                    PrintJobId printJobId2 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean printJobState = setPrintJobState(printJobId2, readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(printJobState);
                    return true;
                case 4:
                    PrintJobId printJobId3 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean printJobTag = setPrintJobTag(printJobId3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(printJobTag);
                    return true;
                case 5:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    PrintJobId printJobId4 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    parcel.enforceNoDataAvail();
                    writePrintJobData(parcelFileDescriptor, printJobId4);
                    return true;
                case 6:
                    PrintJobId printJobId5 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setProgress(printJobId5, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    PrintJobId printJobId6 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatus(printJobId6, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    PrintJobId printJobId7 = (PrintJobId) parcel.readTypedObject(PrintJobId.CREATOR);
                    int readInt2 = parcel.readInt();
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusRes(printJobId7, readInt2, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintersAdded(parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrintersRemoved(parceledListSlice2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    PrinterId printerId = (PrinterId) parcel.readTypedObject(PrinterId.CREATOR);
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCustomPrinterIconLoaded(printerId, icon);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPrintServiceClient {
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

            @Override // android.printservice.IPrintServiceClient
            public List<PrintJobInfo> getPrintJobInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PrintJobInfo) obtain2.readTypedObject(PrintJobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public boolean setPrintJobState(PrintJobId printJobId, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public boolean setPrintJobTag(PrintJobId printJobId, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(printJobId, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printJobId, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void setStatusRes(PrintJobId printJobId, int i, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
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
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.printservice.IPrintServiceClient
            public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printerId, 0);
                    obtain.writeTypedObject(icon, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
