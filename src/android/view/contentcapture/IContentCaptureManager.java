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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContentCaptureManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContentCaptureManager)) {
                return (IContentCaptureManager) iInterfaceQueryLocalInterface;
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
                    IBinder strongBinder = parcel.readStrongBinder();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSession(strongBinder, strongBinder2, componentName, i3, i4, iResultReceiverAsInterface);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSession(i5);
                    return true;
                case 3:
                    IResultReceiver iResultReceiverAsInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getServiceComponentName(iResultReceiverAsInterface2);
                    return true;
                case 4:
                    DataRemovalRequest dataRemovalRequest = (DataRemovalRequest) parcel.readTypedObject(DataRemovalRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeData(dataRemovalRequest);
                    return true;
                case 5:
                    DataShareRequest dataShareRequest = (DataShareRequest) parcel.readTypedObject(DataShareRequest.CREATOR);
                    IDataShareWriteAdapter iDataShareWriteAdapterAsInterface = IDataShareWriteAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    shareData(dataShareRequest, iDataShareWriteAdapterAsInterface);
                    return true;
                case 6:
                    IResultReceiver iResultReceiverAsInterface3 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isContentCaptureFeatureEnabled(iResultReceiverAsInterface3);
                    return true;
                case 7:
                    IResultReceiver iResultReceiverAsInterface4 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getServiceSettingsActivity(iResultReceiverAsInterface4);
                    return true;
                case 8:
                    String string = parcel.readString();
                    IResultReceiver iResultReceiverAsInterface5 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getContentCaptureConditions(string, iResultReceiverAsInterface5);
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetTemporaryService(i6);
                    return true;
                case 10:
                    int i7 = parcel.readInt();
                    String string2 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTemporaryService(i7, string2, i8);
                    return true;
                case 11:
                    int i9 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDefaultServiceEnabled(i9, z);
                    return true;
                case 12:
                    String string3 = parcel.readString();
                    IContentCaptureOptionsCallback iContentCaptureOptionsCallbackAsInterface = IContentCaptureOptionsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerContentCaptureOptionsCallback(string3, iContentCaptureOptionsCallbackAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void finishSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void removeData(DataRemovalRequest dataRemovalRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dataRemovalRequest, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void shareData(DataShareRequest dataShareRequest, IDataShareWriteAdapter iDataShareWriteAdapter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dataShareRequest, 0);
                    parcelObtain.writeStrongInterface(iDataShareWriteAdapter);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void isContentCaptureFeatureEnabled(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getServiceSettingsActivity(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void getContentCaptureConditions(String str, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void resetTemporaryService(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void setTemporaryService(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void setDefaultServiceEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void registerContentCaptureOptionsCallback(String str, IContentCaptureOptionsCallback iContentCaptureOptionsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iContentCaptureOptionsCallback);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.contentcapture.IContentCaptureManager
            public void onLoginDetected(ParceledListSlice<ContentCaptureEvent> parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
