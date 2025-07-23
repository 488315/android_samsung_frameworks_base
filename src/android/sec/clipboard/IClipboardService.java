package android.sec.clipboard;

import android.content.ClipData;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.clipboard.IClipboardDataPasteEvent;
import com.samsung.android.content.clipboard.IOnClipboardEventListener;
import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes3.dex */
public interface IClipboardService extends IInterface {
    public static final String DESCRIPTOR = "android.sec.clipboard.IClipboardService";

    public static class Default implements IClipboardService {
        @Override // android.sec.clipboard.IClipboardService
        public void addClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.sec.clipboard.IClipboardService
        public int getFilter() throws RemoteException {
            return 0;
        }

        @Override // android.sec.clipboard.IClipboardService
        public SemClipData getPrimarySemClip(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.sec.clipboard.IClipboardService
        public boolean hasPrimaryClip(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.IClipboardService
        public boolean isEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.IClipboardService
        public boolean pasteClipData(ClipData clipData, String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.IClipboardService
        public void removeClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public void setPrimaryClip(ClipData clipData, int i) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public void setPrimarySemClip(SemClipData semClipData, String str, int i) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public void updateFilter(int i, IClipboardDataPasteEvent iClipboardDataPasteEvent) throws RemoteException {
        }
    }

    void addClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener, String str) throws RemoteException;

    int getFilter() throws RemoteException;

    SemClipData getPrimarySemClip(String str, int i) throws RemoteException;

    boolean hasPrimaryClip(String str, int i) throws RemoteException;

    boolean isEnabled(int i) throws RemoteException;

    boolean pasteClipData(ClipData clipData, String str, int i) throws RemoteException;

    void removeClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener) throws RemoteException;

    void setPrimaryClip(ClipData clipData, int i) throws RemoteException;

    void setPrimarySemClip(SemClipData semClipData, String str, int i) throws RemoteException;

    void updateFilter(int i, IClipboardDataPasteEvent iClipboardDataPasteEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IClipboardService {
        static final int TRANSACTION_addClipboardEventListener = 3;
        static final int TRANSACTION_getFilter = 2;
        static final int TRANSACTION_getPrimarySemClip = 7;
        static final int TRANSACTION_hasPrimaryClip = 8;
        static final int TRANSACTION_isEnabled = 10;
        static final int TRANSACTION_pasteClipData = 9;
        static final int TRANSACTION_removeClipboardEventListener = 4;
        static final int TRANSACTION_setPrimaryClip = 5;
        static final int TRANSACTION_setPrimarySemClip = 6;
        static final int TRANSACTION_updateFilter = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IClipboardService.DESCRIPTOR);
        }

        public static IClipboardService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IClipboardService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClipboardService)) {
                return (IClipboardService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateFilter";
                case 2:
                    return "getFilter";
                case 3:
                    return "addClipboardEventListener";
                case 4:
                    return "removeClipboardEventListener";
                case 5:
                    return "setPrimaryClip";
                case 6:
                    return "setPrimarySemClip";
                case 7:
                    return "getPrimarySemClip";
                case 8:
                    return "hasPrimaryClip";
                case 9:
                    return "pasteClipData";
                case 10:
                    return "isEnabled";
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
                parcel.enforceInterface(IClipboardService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IClipboardService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    IClipboardDataPasteEvent asInterface = IClipboardDataPasteEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateFilter(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int filter = getFilter();
                    parcel2.writeNoException();
                    parcel2.writeInt(filter);
                    return true;
                case 3:
                    IOnClipboardEventListener asInterface2 = IOnClipboardEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addClipboardEventListener(asInterface2, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IOnClipboardEventListener asInterface3 = IOnClipboardEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClipboardEventListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimaryClip(clipData, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SemClipData semClipData = (SemClipData) parcel.readTypedObject(SemClipData.CREATOR);
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimarySemClip(semClipData, readString2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemClipData primarySemClip = getPrimarySemClip(readString3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primarySemClip, 1);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasPrimaryClip = hasPrimaryClip(readString4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasPrimaryClip);
                    return true;
                case 9:
                    ClipData clipData2 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean pasteClipData = pasteClipData(clipData2, readString5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pasteClipData);
                    return true;
                case 10:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isEnabled = isEnabled(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IClipboardService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClipboardService.DESCRIPTOR;
            }

            @Override // android.sec.clipboard.IClipboardService
            public void updateFilter(int i, IClipboardDataPasteEvent iClipboardDataPasteEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iClipboardDataPasteEvent);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public int getFilter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void addClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnClipboardEventListener);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void removeClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnClipboardEventListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void setPrimaryClip(ClipData clipData, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void setPrimarySemClip(SemClipData semClipData, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeTypedObject(semClipData, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public SemClipData getPrimarySemClip(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemClipData) obtain2.readTypedObject(SemClipData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean hasPrimaryClip(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean pasteClipData(ClipData clipData, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeTypedObject(clipData, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean isEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
