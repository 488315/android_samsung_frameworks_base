package com.android.ims.internal.uce.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.uce.common.StatusCode;

/* loaded from: classes5.dex */
public interface IOptionsListener extends IInterface {

    public static class Default implements IOptionsListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void getVersionCb(String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void incomingOptions(String str, OptionsCapInfo optionsCapInfo, int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void serviceAvailable(StatusCode statusCode) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
        }

        @Override // com.android.ims.internal.uce.options.IOptionsListener
        public void sipResponseReceived(String str, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo) throws RemoteException {
        }
    }

    void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException;

    void getVersionCb(String str) throws RemoteException;

    void incomingOptions(String str, OptionsCapInfo optionsCapInfo, int i) throws RemoteException;

    void serviceAvailable(StatusCode statusCode) throws RemoteException;

    void serviceUnavailable(StatusCode statusCode) throws RemoteException;

    void sipResponseReceived(String str, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IOptionsListener {
        public static final String DESCRIPTOR = "com.android.ims.internal.uce.options.IOptionsListener";
        static final int TRANSACTION_cmdStatus = 5;
        static final int TRANSACTION_getVersionCb = 1;
        static final int TRANSACTION_incomingOptions = 6;
        static final int TRANSACTION_serviceAvailable = 2;
        static final int TRANSACTION_serviceUnavailable = 3;
        static final int TRANSACTION_sipResponseReceived = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOptionsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOptionsListener)) {
                return (IOptionsListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVersionCb";
                case 2:
                    return "serviceAvailable";
                case 3:
                    return "serviceUnavailable";
                case 4:
                    return "sipResponseReceived";
                case 5:
                    return "cmdStatus";
                case 6:
                    return "incomingOptions";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getVersionCb(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    StatusCode statusCode = (StatusCode) parcel.readTypedObject(StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceAvailable(statusCode);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    StatusCode statusCode2 = (StatusCode) parcel.readTypedObject(StatusCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    serviceUnavailable(statusCode2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    OptionsSipResponse optionsSipResponse = (OptionsSipResponse) parcel.readTypedObject(OptionsSipResponse.CREATOR);
                    OptionsCapInfo optionsCapInfo = (OptionsCapInfo) parcel.readTypedObject(OptionsCapInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sipResponseReceived(readString2, optionsSipResponse, optionsCapInfo);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    OptionsCmdStatus optionsCmdStatus = (OptionsCmdStatus) parcel.readTypedObject(OptionsCmdStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    cmdStatus(optionsCmdStatus);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    OptionsCapInfo optionsCapInfo2 = (OptionsCapInfo) parcel.readTypedObject(OptionsCapInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    incomingOptions(readString3, optionsCapInfo2, readInt);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOptionsListener {
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

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void getVersionCb(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void serviceAvailable(StatusCode statusCode) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void serviceUnavailable(StatusCode statusCode) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(statusCode, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void sipResponseReceived(String str, OptionsSipResponse optionsSipResponse, OptionsCapInfo optionsCapInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(optionsSipResponse, 0);
                    obtain.writeTypedObject(optionsCapInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void cmdStatus(OptionsCmdStatus optionsCmdStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(optionsCmdStatus, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.uce.options.IOptionsListener
            public void incomingOptions(String str, OptionsCapInfo optionsCapInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(optionsCapInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
