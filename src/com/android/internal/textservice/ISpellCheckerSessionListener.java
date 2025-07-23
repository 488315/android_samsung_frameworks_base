package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;

/* loaded from: classes4.dex */
public interface ISpellCheckerSessionListener extends IInterface {

    public static class Default implements ISpellCheckerSessionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) throws RemoteException {
        }
    }

    void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) throws RemoteException;

    void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpellCheckerSessionListener {
        public static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSessionListener";
        static final int TRANSACTION_onGetSentenceSuggestions = 2;
        static final int TRANSACTION_onGetSuggestions = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpellCheckerSessionListener)) {
                return (ISpellCheckerSessionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onGetSuggestions";
            }
            if (i != 2) {
                return null;
            }
            return "onGetSentenceSuggestions";
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
                SuggestionsInfo[] suggestionsInfoArr = (SuggestionsInfo[]) parcel.createTypedArray(SuggestionsInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onGetSuggestions(suggestionsInfoArr);
            } else if (i == 2) {
                SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr = (SentenceSuggestionsInfo[]) parcel.createTypedArray(SentenceSuggestionsInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onGetSentenceSuggestions(sentenceSuggestionsInfoArr);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISpellCheckerSessionListener {
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

            @Override // com.android.internal.textservice.ISpellCheckerSessionListener
            public void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(suggestionsInfoArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSessionListener
            public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(sentenceSuggestionsInfoArr, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
