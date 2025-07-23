package com.samsung.android.infoextraction;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ThreadedRenderer;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;

/* loaded from: classes6.dex */
public interface IKerykeion extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.infoextraction.IKerykeion";

    public static class Default implements IKerykeion {
        @Override // com.samsung.android.infoextraction.IKerykeion
        public void addResultRule(int i, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void dismiss() throws RemoteException {
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void restart() throws RemoteException {
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void setInfoExtractionListener(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void show(String str, Rect rect) throws RemoteException {
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void start(int i, KerykeionRequest kerykeionRequest, Rect rect) throws RemoteException {
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void stop(int i) throws RemoteException {
        }

        @Override // com.samsung.android.infoextraction.IKerykeion
        public void training(String str) throws RemoteException {
        }
    }

    void addResultRule(int i, String str) throws RemoteException;

    void dismiss() throws RemoteException;

    void restart() throws RemoteException;

    void setInfoExtractionListener(int i, IBinder iBinder) throws RemoteException;

    void show(String str, Rect rect) throws RemoteException;

    void start(int i, KerykeionRequest kerykeionRequest, Rect rect) throws RemoteException;

    void stop(int i) throws RemoteException;

    void training(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IKerykeion {
        static final int TRANSACTION_addResultRule = 8;
        static final int TRANSACTION_dismiss = 6;
        static final int TRANSACTION_restart = 3;
        static final int TRANSACTION_setInfoExtractionListener = 4;
        static final int TRANSACTION_show = 5;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;
        static final int TRANSACTION_training = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, IKerykeion.DESCRIPTOR);
        }

        public static IKerykeion asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKerykeion.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKerykeion)) {
                return (IKerykeion) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "start";
                case 2:
                    return "stop";
                case 3:
                    return DefaultActionNames.ACTION_RESTART;
                case 4:
                    return "setInfoExtractionListener";
                case 5:
                    return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 6:
                    return "dismiss";
                case 7:
                    return "training";
                case 8:
                    return "addResultRule";
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
                parcel.enforceInterface(IKerykeion.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKerykeion.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    KerykeionRequest kerykeionRequest = (KerykeionRequest) parcel.readTypedObject(KerykeionRequest.CREATOR);
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    start(readInt, kerykeionRequest, rect);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stop(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    restart();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setInfoExtractionListener(readInt3, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString = parcel.readString();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    show(readString, rect2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    dismiss();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    training(readString2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addResultRule(readInt4, readString3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKerykeion {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKerykeion.DESCRIPTOR;
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void start(int i, KerykeionRequest kerykeionRequest, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(kerykeionRequest, 0);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void stop(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void restart() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void setInfoExtractionListener(int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void show(String str, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void dismiss() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void training(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void addResultRule(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
