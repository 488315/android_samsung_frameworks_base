package com.android.internal.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAppClipsService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.statusbar.IAppClipsService";

    public static class Default implements IAppClipsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.statusbar.IAppClipsService
        public boolean canLaunchCaptureContentActivityForNote(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IAppClipsService
        public int canLaunchCaptureContentActivityForNoteInternal(int i) throws RemoteException {
            return 0;
        }
    }

    boolean canLaunchCaptureContentActivityForNote(int i) throws RemoteException;

    int canLaunchCaptureContentActivityForNoteInternal(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppClipsService {
        static final int TRANSACTION_canLaunchCaptureContentActivityForNote = 1;
        static final int TRANSACTION_canLaunchCaptureContentActivityForNoteInternal = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IAppClipsService.DESCRIPTOR);
        }

        public static IAppClipsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppClipsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppClipsService)) {
                return (IAppClipsService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "canLaunchCaptureContentActivityForNote";
            }
            if (i != 2) {
                return null;
            }
            return "canLaunchCaptureContentActivityForNoteInternal";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppClipsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppClipsService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean canLaunchCaptureContentActivityForNote = canLaunchCaptureContentActivityForNote(readInt);
                parcel2.writeNoException();
                parcel2.writeBoolean(canLaunchCaptureContentActivityForNote);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int canLaunchCaptureContentActivityForNoteInternal = canLaunchCaptureContentActivityForNoteInternal(readInt2);
                parcel2.writeNoException();
                parcel2.writeInt(canLaunchCaptureContentActivityForNoteInternal);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAppClipsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppClipsService.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IAppClipsService
            public boolean canLaunchCaptureContentActivityForNote(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppClipsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IAppClipsService
            public int canLaunchCaptureContentActivityForNoteInternal(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppClipsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
