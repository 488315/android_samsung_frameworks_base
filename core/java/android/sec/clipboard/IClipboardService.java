package android.sec.clipboard;

import android.content.ClipData;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.sec.clipboard.IClipboardDataPasteEvent;
import com.samsung.android.content.clipboard.IOnClipboardEventListener;
import com.samsung.android.content.clipboard.data.SemClipData;

/* loaded from: classes3.dex */
public interface IClipboardService extends IInterface {
    public static final String DESCRIPTOR = "android.sec.clipboard.IClipboardService";

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

    public static class Default implements IClipboardService {
        @Override // android.sec.clipboard.IClipboardService
        public void updateFilter(int type, IClipboardDataPasteEvent clPasteEvent) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public int getFilter() throws RemoteException {
            return 0;
        }

        @Override // android.sec.clipboard.IClipboardService
        public void addClipboardEventListener(IOnClipboardEventListener listener, String callingPackage) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public void removeClipboardEventListener(IOnClipboardEventListener listener) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public void setPrimaryClip(ClipData clip, int uid) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public void setPrimarySemClip(SemClipData clip, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.sec.clipboard.IClipboardService
        public SemClipData getPrimarySemClip(String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.sec.clipboard.IClipboardService
        public boolean hasPrimaryClip(String callingPackage, int userId) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.IClipboardService
        public boolean pasteClipData(ClipData clip, String callingPackage, int userId) throws RemoteException {
            return false;
        }

        @Override // android.sec.clipboard.IClipboardService
        public boolean isEnabled(int userId) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, IClipboardService.DESCRIPTOR);
        }

        public static IClipboardService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IClipboardService.DESCRIPTOR);
            if (iin != null && (iin instanceof IClipboardService)) {
                return (IClipboardService) iin;
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IClipboardService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IClipboardService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            IClipboardDataPasteEvent _arg1 = IClipboardDataPasteEvent.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            updateFilter(_arg0, _arg1);
                            reply.writeNoException();
                            return true;
                        case 2:
                            int _result = getFilter();
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 3:
                            IOnClipboardEventListener _arg02 = IOnClipboardEventListener.Stub.asInterface(data.readStrongBinder());
                            String _arg12 = data.readString();
                            data.enforceNoDataAvail();
                            addClipboardEventListener(_arg02, _arg12);
                            reply.writeNoException();
                            return true;
                        case 4:
                            IOnClipboardEventListener _arg03 = IOnClipboardEventListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            removeClipboardEventListener(_arg03);
                            reply.writeNoException();
                            return true;
                        case 5:
                            ClipData _arg04 = (ClipData) data.readTypedObject(ClipData.CREATOR);
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            setPrimaryClip(_arg04, _arg13);
                            reply.writeNoException();
                            return true;
                        case 6:
                            SemClipData _arg05 = (SemClipData) data.readTypedObject(SemClipData.CREATOR);
                            String _arg14 = data.readString();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            setPrimarySemClip(_arg05, _arg14, _arg2);
                            reply.writeNoException();
                            return true;
                        case 7:
                            String _arg06 = data.readString();
                            int _arg15 = data.readInt();
                            data.enforceNoDataAvail();
                            SemClipData _result2 = getPrimarySemClip(_arg06, _arg15);
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 8:
                            String _arg07 = data.readString();
                            int _arg16 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result3 = hasPrimaryClip(_arg07, _arg16);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 9:
                            ClipData _arg08 = (ClipData) data.readTypedObject(ClipData.CREATOR);
                            String _arg17 = data.readString();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result4 = pasteClipData(_arg08, _arg17, _arg22);
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 10:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result5 = isEnabled(_arg09);
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IClipboardService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClipboardService.DESCRIPTOR;
            }

            @Override // android.sec.clipboard.IClipboardService
            public void updateFilter(int type, IClipboardDataPasteEvent clPasteEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeStrongInterface(clPasteEvent);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public int getFilter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void addClipboardEventListener(IOnClipboardEventListener listener, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void removeClipboardEventListener(IOnClipboardEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void setPrimaryClip(ClipData clip, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeTypedObject(clip, 0);
                    _data.writeInt(uid);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public void setPrimarySemClip(SemClipData clip, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeTypedObject(clip, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public SemClipData getPrimarySemClip(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    SemClipData _result = (SemClipData) _reply.readTypedObject(SemClipData.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean hasPrimaryClip(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean pasteClipData(ClipData clip, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeTypedObject(clip, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.sec.clipboard.IClipboardService
            public boolean isEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IClipboardService.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
