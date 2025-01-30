package android.print;

import android.graphics.drawable.Icon;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPrintSpoolerCallbacks extends IInterface {
    void customPrinterIconCacheCleared(int i) throws RemoteException;

    void onCancelPrintJobResult(boolean z, int i) throws RemoteException;

    void onCustomPrinterIconCached(int i) throws RemoteException;

    void onGetCustomPrinterIconResult(Icon icon, int i) throws RemoteException;

    void onGetPrintJobInfoResult(PrintJobInfo printJobInfo, int i) throws RemoteException;

    void onGetPrintJobInfosResult(List<PrintJobInfo> list, int i) throws RemoteException;

    void onSetPrintJobStateResult(boolean z, int i) throws RemoteException;

    void onSetPrintJobTagResult(boolean z, int i) throws RemoteException;

    public static class Default implements IPrintSpoolerCallbacks {
        @Override // android.print.IPrintSpoolerCallbacks
        public void onGetPrintJobInfosResult(List<PrintJobInfo> printJob, int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void onCancelPrintJobResult(boolean canceled, int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void onSetPrintJobStateResult(boolean success, int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void onSetPrintJobTagResult(boolean success, int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void onGetPrintJobInfoResult(PrintJobInfo printJob, int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void onGetCustomPrinterIconResult(Icon icon, int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void onCustomPrinterIconCached(int sequence) throws RemoteException {
        }

        @Override // android.print.IPrintSpoolerCallbacks
        public void customPrinterIconCacheCleared(int sequence) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintSpoolerCallbacks {
        public static final String DESCRIPTOR = "android.print.IPrintSpoolerCallbacks";
        static final int TRANSACTION_customPrinterIconCacheCleared = 8;
        static final int TRANSACTION_onCancelPrintJobResult = 2;
        static final int TRANSACTION_onCustomPrinterIconCached = 7;
        static final int TRANSACTION_onGetCustomPrinterIconResult = 6;
        static final int TRANSACTION_onGetPrintJobInfoResult = 5;
        static final int TRANSACTION_onGetPrintJobInfosResult = 1;
        static final int TRANSACTION_onSetPrintJobStateResult = 3;
        static final int TRANSACTION_onSetPrintJobTagResult = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpoolerCallbacks asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPrintSpoolerCallbacks)) {
                return (IPrintSpoolerCallbacks) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onGetPrintJobInfosResult";
                case 2:
                    return "onCancelPrintJobResult";
                case 3:
                    return "onSetPrintJobStateResult";
                case 4:
                    return "onSetPrintJobTagResult";
                case 5:
                    return "onGetPrintJobInfoResult";
                case 6:
                    return "onGetCustomPrinterIconResult";
                case 7:
                    return "onCustomPrinterIconCached";
                case 8:
                    return "customPrinterIconCacheCleared";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            List<PrintJobInfo> _arg0 = data.createTypedArrayList(PrintJobInfo.CREATOR);
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onGetPrintJobInfosResult(_arg0, _arg1);
                            return true;
                        case 2:
                            boolean _arg02 = data.readBoolean();
                            int _arg12 = data.readInt();
                            data.enforceNoDataAvail();
                            onCancelPrintJobResult(_arg02, _arg12);
                            return true;
                        case 3:
                            boolean _arg03 = data.readBoolean();
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            onSetPrintJobStateResult(_arg03, _arg13);
                            return true;
                        case 4:
                            boolean _arg04 = data.readBoolean();
                            int _arg14 = data.readInt();
                            data.enforceNoDataAvail();
                            onSetPrintJobTagResult(_arg04, _arg14);
                            return true;
                        case 5:
                            PrintJobInfo _arg05 = (PrintJobInfo) data.readTypedObject(PrintJobInfo.CREATOR);
                            int _arg15 = data.readInt();
                            data.enforceNoDataAvail();
                            onGetPrintJobInfoResult(_arg05, _arg15);
                            return true;
                        case 6:
                            Icon _arg06 = (Icon) data.readTypedObject(Icon.CREATOR);
                            int _arg16 = data.readInt();
                            data.enforceNoDataAvail();
                            onGetCustomPrinterIconResult(_arg06, _arg16);
                            return true;
                        case 7:
                            int _arg07 = data.readInt();
                            data.enforceNoDataAvail();
                            onCustomPrinterIconCached(_arg07);
                            return true;
                        case 8:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            customPrinterIconCacheCleared(_arg08);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IPrintSpoolerCallbacks {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onGetPrintJobInfosResult(List<PrintJobInfo> printJob, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(printJob, 0);
                    _data.writeInt(sequence);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onCancelPrintJobResult(boolean canceled, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(canceled);
                    _data.writeInt(sequence);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onSetPrintJobStateResult(boolean success, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(success);
                    _data.writeInt(sequence);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onSetPrintJobTagResult(boolean success, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(success);
                    _data.writeInt(sequence);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onGetPrintJobInfoResult(PrintJobInfo printJob, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(printJob, 0);
                    _data.writeInt(sequence);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onGetCustomPrinterIconResult(Icon icon, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(icon, 0);
                    _data.writeInt(sequence);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void onCustomPrinterIconCached(int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.print.IPrintSpoolerCallbacks
            public void customPrinterIconCacheCleared(int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
