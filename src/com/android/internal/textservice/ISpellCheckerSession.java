package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.textservice.TextInfo;

/* loaded from: classes4.dex */
public interface ISpellCheckerSession extends IInterface {

    public static class Default implements ISpellCheckerSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onCancel() throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onClose() throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) throws RemoteException {
        }
    }

    void onCancel() throws RemoteException;

    void onClose() throws RemoteException;

    void onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) throws RemoteException;

    void onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpellCheckerSession {
        public static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSession";
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onClose = 4;
        static final int TRANSACTION_onGetSentenceSuggestionsMultiple = 2;
        static final int TRANSACTION_onGetSuggestionsMultiple = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpellCheckerSession)) {
                return (ISpellCheckerSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onGetSuggestionsMultiple";
            }
            if (i == 2) {
                return "onGetSentenceSuggestionsMultiple";
            }
            if (i == 3) {
                return "onCancel";
            }
            if (i != 4) {
                return null;
            }
            return "onClose";
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
            if (i == 1) {
                TextInfo[] textInfoArr = (TextInfo[]) parcel.createTypedArray(TextInfo.CREATOR);
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onGetSuggestionsMultiple(textInfoArr, readInt, readBoolean);
            } else if (i == 2) {
                TextInfo[] textInfoArr2 = (TextInfo[]) parcel.createTypedArray(TextInfo.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGetSentenceSuggestionsMultiple(textInfoArr2, readInt2);
            } else if (i == 3) {
                onCancel();
            } else if (i == 4) {
                onClose();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISpellCheckerSession {
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

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(textInfoArr, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(textInfoArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onCancel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onClose() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
