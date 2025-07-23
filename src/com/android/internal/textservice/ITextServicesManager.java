package com.android.internal.textservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SpellCheckerSubtype;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesSessionListener;

/* loaded from: classes4.dex */
public interface ITextServicesManager extends IInterface {

    public static class Default implements ITextServicesManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public void finishSpellCheckerService(int i, ISpellCheckerSessionListener iSpellCheckerSessionListener) throws RemoteException {
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public SpellCheckerInfo getCurrentSpellChecker(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public SpellCheckerInfo[] getEnabledSpellCheckers(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public void getSpellCheckerService(int i, String str, String str2, ITextServicesSessionListener iTextServicesSessionListener, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // com.android.internal.textservice.ITextServicesManager
        public boolean isSpellCheckerEnabled(int i) throws RemoteException {
            return false;
        }
    }

    void finishSpellCheckerService(int i, ISpellCheckerSessionListener iSpellCheckerSessionListener) throws RemoteException;

    SpellCheckerInfo getCurrentSpellChecker(int i, String str) throws RemoteException;

    SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) throws RemoteException;

    SpellCheckerInfo[] getEnabledSpellCheckers(int i) throws RemoteException;

    void getSpellCheckerService(int i, String str, String str2, ITextServicesSessionListener iTextServicesSessionListener, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i2) throws RemoteException;

    boolean isSpellCheckerEnabled(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITextServicesManager {
        public static final String DESCRIPTOR = "com.android.internal.textservice.ITextServicesManager";
        static final int TRANSACTION_finishSpellCheckerService = 4;
        static final int TRANSACTION_getCurrentSpellChecker = 1;
        static final int TRANSACTION_getCurrentSpellCheckerSubtype = 2;
        static final int TRANSACTION_getEnabledSpellCheckers = 6;
        static final int TRANSACTION_getSpellCheckerService = 3;
        static final int TRANSACTION_isSpellCheckerEnabled = 5;

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

        public static ITextServicesManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITextServicesManager)) {
                return (ITextServicesManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCurrentSpellChecker";
                case 2:
                    return "getCurrentSpellCheckerSubtype";
                case 3:
                    return "getSpellCheckerService";
                case 4:
                    return "finishSpellCheckerService";
                case 5:
                    return "isSpellCheckerEnabled";
                case 6:
                    return "getEnabledSpellCheckers";
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SpellCheckerInfo currentSpellChecker = getCurrentSpellChecker(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentSpellChecker, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SpellCheckerSubtype currentSpellCheckerSubtype = getCurrentSpellCheckerSubtype(readInt2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentSpellCheckerSubtype, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    ITextServicesSessionListener asInterface = ITextServicesSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    ISpellCheckerSessionListener asInterface2 = ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSpellCheckerService(readInt3, readString2, readString3, asInterface, asInterface2, bundle, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    ISpellCheckerSessionListener asInterface3 = ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishSpellCheckerService(readInt5, asInterface3);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSpellCheckerEnabled = isSpellCheckerEnabled(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpellCheckerEnabled);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SpellCheckerInfo[] enabledSpellCheckers = getEnabledSpellCheckers(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(enabledSpellCheckers, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITextServicesManager {
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

            @Override // com.android.internal.textservice.ITextServicesManager
            public SpellCheckerInfo getCurrentSpellChecker(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SpellCheckerInfo) obtain2.readTypedObject(SpellCheckerInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public SpellCheckerSubtype getCurrentSpellCheckerSubtype(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SpellCheckerSubtype) obtain2.readTypedObject(SpellCheckerSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public void getSpellCheckerService(int i, String str, String str2, ITextServicesSessionListener iTextServicesSessionListener, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iTextServicesSessionListener);
                    obtain.writeStrongInterface(iSpellCheckerSessionListener);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public void finishSpellCheckerService(int i, ISpellCheckerSessionListener iSpellCheckerSessionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSpellCheckerSessionListener);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public boolean isSpellCheckerEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.textservice.ITextServicesManager
            public SpellCheckerInfo[] getEnabledSpellCheckers(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SpellCheckerInfo[]) obtain2.createTypedArray(SpellCheckerInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
