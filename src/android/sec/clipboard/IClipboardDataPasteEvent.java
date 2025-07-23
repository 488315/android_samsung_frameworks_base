package android.sec.clipboard;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes3.dex */
public interface IClipboardDataPasteEvent extends IInterface {
    public static final String DESCRIPTOR = "android.sec.clipboard.IClipboardDataPasteEvent";

    public static class Default implements IClipboardDataPasteEvent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.sec.clipboard.IClipboardDataPasteEvent
        public void onPaste(SemClipData semClipData) throws RemoteException {
        }
    }

    void onPaste(SemClipData semClipData) throws RemoteException;

    public static abstract class Stub extends Binder implements IClipboardDataPasteEvent {
        static final int TRANSACTION_onPaste = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IClipboardDataPasteEvent.DESCRIPTOR);
        }

        public static IClipboardDataPasteEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IClipboardDataPasteEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClipboardDataPasteEvent)) {
                return (IClipboardDataPasteEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onPaste";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IClipboardDataPasteEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IClipboardDataPasteEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemClipData semClipData = (SemClipData) parcel.readTypedObject(SemClipData.CREATOR);
                parcel.enforceNoDataAvail();
                onPaste(semClipData);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IClipboardDataPasteEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClipboardDataPasteEvent.DESCRIPTOR;
            }

            @Override // android.sec.clipboard.IClipboardDataPasteEvent
            public void onPaste(SemClipData semClipData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardDataPasteEvent.DESCRIPTOR);
                    obtain.writeTypedObject(semClipData, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
