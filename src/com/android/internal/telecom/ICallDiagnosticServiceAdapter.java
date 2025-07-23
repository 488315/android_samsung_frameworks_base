package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public interface ICallDiagnosticServiceAdapter extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallDiagnosticServiceAdapter";

    public static class Default implements ICallDiagnosticServiceAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void clearDiagnosticMessage(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void displayDiagnosticMessage(String str, int i, CharSequence charSequence) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void overrideDisconnectMessage(String str, CharSequence charSequence) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
        public void sendDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException {
        }
    }

    void clearDiagnosticMessage(String str, int i) throws RemoteException;

    void displayDiagnosticMessage(String str, int i, CharSequence charSequence) throws RemoteException;

    void overrideDisconnectMessage(String str, CharSequence charSequence) throws RemoteException;

    void sendDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallDiagnosticServiceAdapter {
        static final int TRANSACTION_clearDiagnosticMessage = 2;
        static final int TRANSACTION_displayDiagnosticMessage = 1;
        static final int TRANSACTION_overrideDisconnectMessage = 4;
        static final int TRANSACTION_sendDeviceToDeviceMessage = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ICallDiagnosticServiceAdapter.DESCRIPTOR);
        }

        public static ICallDiagnosticServiceAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallDiagnosticServiceAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallDiagnosticServiceAdapter)) {
                return (ICallDiagnosticServiceAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "displayDiagnosticMessage";
            }
            if (i == 2) {
                return "clearDiagnosticMessage";
            }
            if (i == 3) {
                return "sendDeviceToDeviceMessage";
            }
            if (i != 4) {
                return null;
            }
            return "overrideDisconnectMessage";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallDiagnosticServiceAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                displayDiagnosticMessage(readString, readInt, charSequence);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                clearDiagnosticMessage(readString2, readInt2);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                sendDeviceToDeviceMessage(readString3, readInt3, readInt4);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                overrideDisconnectMessage(readString4, charSequence2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICallDiagnosticServiceAdapter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallDiagnosticServiceAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void displayDiagnosticMessage(String str, int i, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void clearDiagnosticMessage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void sendDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void overrideDisconnectMessage(String str, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
