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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAppClipsService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppClipsService)) {
                return (IAppClipsService) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zCanLaunchCaptureContentActivityForNote = canLaunchCaptureContentActivityForNote(i3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zCanLaunchCaptureContentActivityForNote);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iCanLaunchCaptureContentActivityForNoteInternal = canLaunchCaptureContentActivityForNoteInternal(i4);
                parcel2.writeNoException();
                parcel2.writeInt(iCanLaunchCaptureContentActivityForNoteInternal);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppClipsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IAppClipsService
            public int canLaunchCaptureContentActivityForNoteInternal(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppClipsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
