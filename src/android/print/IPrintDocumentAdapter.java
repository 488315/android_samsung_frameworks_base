package android.print;

import android.media.TtmlUtils;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.print.ILayoutResultCallback;
import android.print.IPrintDocumentAdapterObserver;
import android.print.IWriteResultCallback;

/* loaded from: classes3.dex */
public interface IPrintDocumentAdapter extends IInterface {

    public static class Default implements IPrintDocumentAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.print.IPrintDocumentAdapter
        public void finish() throws RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void layout(PrintAttributes printAttributes, PrintAttributes printAttributes2, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void start() throws RemoteException {
        }

        @Override // android.print.IPrintDocumentAdapter
        public void write(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int i) throws RemoteException {
        }
    }

    void finish() throws RemoteException;

    void layout(PrintAttributes printAttributes, PrintAttributes printAttributes2, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int i) throws RemoteException;

    void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws RemoteException;

    void start() throws RemoteException;

    void write(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IPrintDocumentAdapter {
        public static final String DESCRIPTOR = "android.print.IPrintDocumentAdapter";
        static final int TRANSACTION_finish = 5;
        static final int TRANSACTION_layout = 3;
        static final int TRANSACTION_setObserver = 1;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_write = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintDocumentAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPrintDocumentAdapter)) {
                return (IPrintDocumentAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setObserver";
            }
            if (i == 2) {
                return "start";
            }
            if (i == 3) {
                return TtmlUtils.TAG_LAYOUT;
            }
            if (i == 4) {
                return "write";
            }
            if (i != 5) {
                return null;
            }
            return "finish";
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
            if (i == 1) {
                IPrintDocumentAdapterObserver asInterface = IPrintDocumentAdapterObserver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setObserver(asInterface);
            } else if (i == 2) {
                start();
            } else if (i == 3) {
                PrintAttributes printAttributes = (PrintAttributes) parcel.readTypedObject(PrintAttributes.CREATOR);
                PrintAttributes printAttributes2 = (PrintAttributes) parcel.readTypedObject(PrintAttributes.CREATOR);
                ILayoutResultCallback asInterface2 = ILayoutResultCallback.Stub.asInterface(parcel.readStrongBinder());
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                layout(printAttributes, printAttributes2, asInterface2, bundle, readInt);
            } else if (i == 4) {
                PageRange[] pageRangeArr = (PageRange[]) parcel.createTypedArray(PageRange.CREATOR);
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                IWriteResultCallback asInterface3 = IWriteResultCallback.Stub.asInterface(parcel.readStrongBinder());
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                write(pageRangeArr, parcelFileDescriptor, asInterface3, readInt2);
            } else if (i == 5) {
                finish();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPrintDocumentAdapter {
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

            @Override // android.print.IPrintDocumentAdapter
            public void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPrintDocumentAdapterObserver);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void start() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void layout(PrintAttributes printAttributes, PrintAttributes printAttributes2, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(printAttributes, 0);
                    obtain.writeTypedObject(printAttributes2, 0);
                    obtain.writeStrongInterface(iLayoutResultCallback);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void write(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(pageRangeArr, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iWriteResultCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.print.IPrintDocumentAdapter
            public void finish() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
