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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKerykeion.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKerykeion)) {
                return (IKerykeion) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    KerykeionRequest kerykeionRequest = (KerykeionRequest) parcel.readTypedObject(KerykeionRequest.CREATOR);
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    start(i3, kerykeionRequest, rect);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stop(i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    restart();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setInfoExtractionListener(i5, strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string = parcel.readString();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    show(string, rect2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    dismiss();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    training(string2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addResultRule(i6, string3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(kerykeionRequest, 0);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void stop(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void restart() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void setInfoExtractionListener(int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void show(String str, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void dismiss() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void training(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.infoextraction.IKerykeion
            public void addResultRule(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKerykeion.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
