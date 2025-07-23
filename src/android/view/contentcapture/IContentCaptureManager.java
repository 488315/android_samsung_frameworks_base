package android.view.contentcapture;

import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.contentcapture.IContentCaptureOptionsCallback;
import android.view.contentcapture.IDataShareWriteAdapter;
import com.android.internal.os.IResultReceiver;

/* loaded from: classes4.dex */
public interface IContentCaptureManager extends IInterface {
    public static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureManager";

    public static class Default implements IContentCaptureManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void finishSession(int i) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void getContentCaptureConditions(String str, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void onLoginDetected(ParceledListSlice<ContentCaptureEvent> parceledListSlice) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void registerContentCaptureOptionsCallback(String str, IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void resetTemporaryService(int i) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void setDefaultServiceEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void setTemporaryService(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void shareData(DataShareRequest dataShareRequest, IDataShareWriteAdapter iDataShareWriteAdapter) throws RemoteException {
        }

        @Override // android.view.contentcapture.IContentCaptureManager
        public void startSession(IBinder iBinder, IBinder iBinder2, ComponentName componentName, int i, int i2, IResultReceiver iResultReceiver) throws RemoteException {
        }
    }

    void finishSession(int i) throws RemoteException;

    void getContentCaptureConditions(String str, IResultReceiver iResultReceiver) throws RemoteException;

    void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException;

    void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException;

    void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException;

    void onLoginDetected(ParceledListSlice<ContentCaptureEvent> parceledListSlice) throws RemoteException;

    void registerContentCaptureOptionsCallback(String str, IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws RemoteException;

    void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException;

    void resetTemporaryService(int i) throws RemoteException;

    void setDefaultServiceEnabled(int i, boolean z) throws RemoteException;

    void setTemporaryService(int i, String str, int i2) throws RemoteException;

    void shareData(DataShareRequest dataShareRequest, IDataShareWriteAdapter iDataShareWriteAdapter) throws RemoteException;

    void startSession(IBinder iBinder, IBinder iBinder2, ComponentName componentName, int i, int i2, IResultReceiver iResultReceiver) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentCaptureManager {
        static final int TRANSACTION_finishSession = 2;
        static final int TRANSACTION_getContentCaptureConditions = 8;
        static final int TRANSACTION_getServiceComponentName = 3;
        static final int TRANSACTION_getServiceSettingsActivity = 7;
        static final int TRANSACTION_isContentCaptureFeatureEnabled = 6;
        static final int TRANSACTION_onLoginDetected = 13;
        static final int TRANSACTION_registerContentCaptureOptionsCallback = 12;
        static final int TRANSACTION_removeData = 4;
        static final int TRANSACTION_resetTemporaryService = 9;
        static final int TRANSACTION_setDefaultServiceEnabled = 11;
        static final int TRANSACTION_setTemporaryService = 10;
        static final int TRANSACTION_shareData = 5;
        static final int TRANSACTION_startSession = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IContentCaptureManager.DESCRIPTOR);
        }

        public static IContentCaptureManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContentCaptureManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentCaptureManager)) {
                return (IContentCaptureManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startSession";
                case 2:
                    return "finishSession";
                case 3:
                    return "getServiceComponentName";
                case 4:
                    return "removeData";
                case 5:
                    return "shareData";
                case 6:
                    return "isContentCaptureFeatureEnabled";
                case 7:
                    return "getServiceSettingsActivity";
                case 8:
                    return "getContentCaptureConditions";
                case 9:
                    return "resetTemporaryService";
                case 10:
                    return "setTemporaryService";
                case 11:
                    return "setDefaultServiceEnabled";
                case 12:
                    return "registerContentCaptureOptionsCallback";
                case 13:
                    return "onLoginDetected";
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
                parcel.enforceInterface(IContentCaptureManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentCaptureManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    IResultReceiver asInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSession(readStrongBinder, readStrongBinder2, componentName, readInt, readInt2, asInterface);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSession(readInt3);
                    return true;
                case 3:
                    IResultReceiver asInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getServiceComponentName(asInterface2);
                    return true;
                case 4:
                    DataRemovalRequest dataRemovalRequest = (DataRemovalRequest) parcel.readTypedObject(DataRemovalRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeData(dataRemovalRequest);
                    return true;
                case 5:
                    DataShareRequest dataShareRequest = (DataShareRequest) parcel.readTypedObject(DataShareRequest.CREATOR);
                    IDataShareWriteAdapter asInterface3 = IDataShareWriteAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    shareData(dataShareRequest, asInterface3);
                    return true;
                case 6:
                    IResultReceiver asInterface4 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isContentCaptureFeatureEnabled(asInterface4);
                    return true;
                case 7:
                    IResultReceiver asInterface5 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getServiceSettingsActivity(asInterface5);
                    return true;
                case 8:
                    String readString = parcel.readString();
                    IResultReceiver asInterface6 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getContentCaptureConditions(readString, asInterface6);
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetTemporaryService(readInt4);
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTemporaryService(readInt5, readString2, readInt6);
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultServiceEnabled(readInt7, readBoolean);
                    return true;
                case 12:
                    String readString3 = parcel.readString();
                    IContentCaptureOptionsCallback asInterface7 = IContentCaptureOptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerContentCaptureOptionsCallback(readString3, asInterface7);
                    return true;
                case 13:
                    ParceledListSlice<ContentCaptureEvent> parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLoginDetected(parceledListSlice);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContentCaptureManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentCaptureManager.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void startSession(IBinder iBinder, IBinder iBinder2, ComponentName componentName, int i, int i2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void finishSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeTypedObject(dataRemovalRequest, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void shareData(DataShareRequest dataShareRequest, IDataShareWriteAdapter iDataShareWriteAdapter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeTypedObject(dataShareRequest, 0);
                    obtain.writeStrongInterface(iDataShareWriteAdapter);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getContentCaptureConditions(String str, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void resetTemporaryService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void setTemporaryService(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void setDefaultServiceEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void registerContentCaptureOptionsCallback(String str, IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iContentCaptureOptionsCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void onLoginDetected(ParceledListSlice<ContentCaptureEvent> parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
