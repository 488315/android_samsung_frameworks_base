package android.app.contentsuggestions;

import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.os.IResultReceiver;

/* loaded from: classes.dex */
public interface IContentSuggestionsManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.contentsuggestions.IContentSuggestionsManager";

    public static class Default implements IContentSuggestionsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void classifyContentSelections(int i, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void isEnabled(int i, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void notifyInteraction(int i, String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void provideContextBitmap(int i, Bitmap bitmap, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void provideContextImage(int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void resetTemporaryService(int i) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void setDefaultServiceEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void setTemporaryService(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.app.contentsuggestions.IContentSuggestionsManager
        public void suggestContentSelections(int i, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
        }
    }

    void classifyContentSelections(int i, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException;

    void isEnabled(int i, IResultReceiver iResultReceiver) throws RemoteException;

    void notifyInteraction(int i, String str, Bundle bundle) throws RemoteException;

    void provideContextBitmap(int i, Bitmap bitmap, Bundle bundle) throws RemoteException;

    void provideContextImage(int i, int i2, Bundle bundle) throws RemoteException;

    void resetTemporaryService(int i) throws RemoteException;

    void setDefaultServiceEnabled(int i, boolean z) throws RemoteException;

    void setTemporaryService(int i, String str, int i2) throws RemoteException;

    void suggestContentSelections(int i, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentSuggestionsManager {
        static final int TRANSACTION_classifyContentSelections = 4;
        static final int TRANSACTION_isEnabled = 6;
        static final int TRANSACTION_notifyInteraction = 5;
        static final int TRANSACTION_provideContextBitmap = 2;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_resetTemporaryService = 7;
        static final int TRANSACTION_setDefaultServiceEnabled = 9;
        static final int TRANSACTION_setTemporaryService = 8;
        static final int TRANSACTION_suggestContentSelections = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IContentSuggestionsManager.DESCRIPTOR);
        }

        public static IContentSuggestionsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContentSuggestionsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentSuggestionsManager)) {
                return (IContentSuggestionsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "provideContextImage";
                case 2:
                    return "provideContextBitmap";
                case 3:
                    return "suggestContentSelections";
                case 4:
                    return "classifyContentSelections";
                case 5:
                    return "notifyInteraction";
                case 6:
                    return "isEnabled";
                case 7:
                    return "resetTemporaryService";
                case 8:
                    return "setTemporaryService";
                case 9:
                    return "setDefaultServiceEnabled";
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
                parcel.enforceInterface(IContentSuggestionsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentSuggestionsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideContextImage(readInt, readInt2, bundle);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    provideContextBitmap(readInt3, bitmap, bundle2);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    SelectionsRequest selectionsRequest = (SelectionsRequest) parcel.readTypedObject(SelectionsRequest.CREATOR);
                    ISelectionsCallback asInterface = ISelectionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    suggestContentSelections(readInt4, selectionsRequest, asInterface);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    ClassificationsRequest classificationsRequest = (ClassificationsRequest) parcel.readTypedObject(ClassificationsRequest.CREATOR);
                    IClassificationsCallback asInterface2 = IClassificationsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    classifyContentSelections(readInt5, classificationsRequest, asInterface2);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyInteraction(readInt6, readString, bundle3);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    IResultReceiver asInterface3 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isEnabled(readInt7, asInterface3);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetTemporaryService(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTemporaryService(readInt9, readString2, readInt10);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultServiceEnabled(readInt11, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContentSuggestionsManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentSuggestionsManager.DESCRIPTOR;
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void provideContextImage(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void provideContextBitmap(int i, Bitmap bitmap, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void suggestContentSelections(int i, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(selectionsRequest, 0);
                    obtain.writeStrongInterface(iSelectionsCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void classifyContentSelections(int i, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(classificationsRequest, 0);
                    obtain.writeStrongInterface(iClassificationsCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void notifyInteraction(int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void isEnabled(int i, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void resetTemporaryService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void setTemporaryService(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.contentsuggestions.IContentSuggestionsManager
            public void setDefaultServiceEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentSuggestionsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
