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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallDiagnosticServiceAdapter.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallDiagnosticServiceAdapter)) {
                return (ICallDiagnosticServiceAdapter) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                int i3 = parcel.readInt();
                CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                displayDiagnosticMessage(string, i3, charSequence);
            } else if (i == 2) {
                String string2 = parcel.readString();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                clearDiagnosticMessage(string2, i4);
            } else if (i == 3) {
                String string3 = parcel.readString();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                sendDeviceToDeviceMessage(string3, i5, i6);
            } else if (i == 4) {
                String string4 = parcel.readString();
                CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                parcel.enforceNoDataAvail();
                overrideDisconnectMessage(string4, charSequence2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void clearDiagnosticMessage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void sendDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticServiceAdapter
            public void overrideDisconnectMessage(String str, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticServiceAdapter.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
