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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IClipboardService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IClipboardService)) {
                return (IClipboardService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    IClipboardDataPasteEvent iClipboardDataPasteEventAsInterface = IClipboardDataPasteEvent.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateFilter(i3, iClipboardDataPasteEventAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int filter = getFilter();
                    parcel2.writeNoException();
                    parcel2.writeInt(filter);
                    return true;
                case 3:
                    IOnClipboardEventListener iOnClipboardEventListenerAsInterface = IOnClipboardEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addClipboardEventListener(iOnClipboardEventListenerAsInterface, string);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IOnClipboardEventListener iOnClipboardEventListenerAsInterface2 = IOnClipboardEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClipboardEventListener(iOnClipboardEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimaryClip(clipData, i4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    SemClipData semClipData = (SemClipData) parcel.readTypedObject(SemClipData.CREATOR);
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPrimarySemClip(semClipData, string2, i5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemClipData primarySemClip = getPrimarySemClip(string3, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primarySemClip, 1);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasPrimaryClip = hasPrimaryClip(string4, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasPrimaryClip);
                    return true;
                case 9:
                    ClipData clipData2 = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    String string5 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPasteClipData = pasteClipData(clipData2, string5, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPasteClipData);
                    return true;
                case 10:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsEnabled = isEnabled(i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnabled);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iClipboardDataPasteEvent);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public int getFilter() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void addClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnClipboardEventListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void removeClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnClipboardEventListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void setPrimaryClip(ClipData clipData, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clipData, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void setPrimarySemClip(SemClipData semClipData, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semClipData, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public SemClipData getPrimarySemClip(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemClipData) parcelObtain2.readTypedObject(SemClipData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean hasPrimaryClip(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean pasteClipData(ClipData clipData, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(clipData, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean isEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
