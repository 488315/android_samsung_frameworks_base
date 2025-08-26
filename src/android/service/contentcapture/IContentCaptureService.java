package android.service.contentcapture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.contentcapture.IDataShareCallback;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.DataRemovalRequest;
import android.view.contentcapture.DataShareRequest;
import com.android.internal.os.IResultReceiver;

/* loaded from: classes3.dex */
public interface IContentCaptureService extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentcapture.IContentCaptureService";

    public static class Default implements IContentCaptureService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivityEvent(ActivityEvent activityEvent) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onActivitySnapshot(int i, SnapshotData snapshotData) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onConnected(IBinder iBinder, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDataShared(DataShareRequest dataShareRequest, IDataShareCallback iDataShareCallback) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionFinished(int i) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentCaptureService
        public void onSessionStarted(ContentCaptureContext contentCaptureContext, int i, int i2, IResultReceiver iResultReceiver, int i3) throws RemoteException {
        }
    }

    void onActivityEvent(ActivityEvent activityEvent) throws RemoteException;

    void onActivitySnapshot(int i, SnapshotData snapshotData) throws RemoteException;

    void onConnected(IBinder iBinder, boolean z, boolean z2) throws RemoteException;

    void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) throws RemoteException;

    void onDataShared(DataShareRequest dataShareRequest, IDataShareCallback iDataShareCallback) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onSessionFinished(int i) throws RemoteException;

    void onSessionStarted(ContentCaptureContext contentCaptureContext, int i, int i2, IResultReceiver iResultReceiver, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentCaptureService {
        static final int TRANSACTION_onActivityEvent = 8;
        static final int TRANSACTION_onActivitySnapshot = 5;
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDataRemovalRequest = 6;
        static final int TRANSACTION_onDataShared = 7;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onSessionFinished = 4;
        static final int TRANSACTION_onSessionStarted = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, IContentCaptureService.DESCRIPTOR);
        }

        public static IContentCaptureService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContentCaptureService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContentCaptureService)) {
                return (IContentCaptureService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnected";
                case 2:
                    return "onDisconnected";
                case 3:
                    return "onSessionStarted";
                case 4:
                    return "onSessionFinished";
                case 5:
                    return "onActivitySnapshot";
                case 6:
                    return "onDataRemovalRequest";
                case 7:
                    return "onDataShared";
                case 8:
                    return "onActivityEvent";
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
                parcel.enforceInterface(IContentCaptureService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentCaptureService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConnected(strongBinder, z, z2);
                    return true;
                case 2:
                    onDisconnected();
                    return true;
                case 3:
                    ContentCaptureContext contentCaptureContext = (ContentCaptureContext) parcel.readTypedObject(ContentCaptureContext.CREATOR);
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStarted(contentCaptureContext, i3, i4, iResultReceiverAsInterface, i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionFinished(i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    SnapshotData snapshotData = (SnapshotData) parcel.readTypedObject(SnapshotData.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActivitySnapshot(i7, snapshotData);
                    return true;
                case 6:
                    DataRemovalRequest dataRemovalRequest = (DataRemovalRequest) parcel.readTypedObject(DataRemovalRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataRemovalRequest(dataRemovalRequest);
                    return true;
                case 7:
                    DataShareRequest dataShareRequest = (DataShareRequest) parcel.readTypedObject(DataShareRequest.CREATOR);
                    IDataShareCallback iDataShareCallbackAsInterface = IDataShareCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onDataShared(dataShareRequest, iDataShareCallbackAsInterface);
                    return true;
                case 8:
                    ActivityEvent activityEvent = (ActivityEvent) parcel.readTypedObject(ActivityEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActivityEvent(activityEvent);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContentCaptureService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentCaptureService.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onConnected(IBinder iBinder, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onSessionStarted(ContentCaptureContext contentCaptureContext, int i, int i2, IResultReceiver iResultReceiver, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contentCaptureContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onSessionFinished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onActivitySnapshot(int i, SnapshotData snapshotData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(snapshotData, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onDataRemovalRequest(DataRemovalRequest dataRemovalRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dataRemovalRequest, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onDataShared(DataShareRequest dataShareRequest, IDataShareCallback iDataShareCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(dataShareRequest, 0);
                    parcelObtain.writeStrongInterface(iDataShareCallback);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentCaptureService
            public void onActivityEvent(ActivityEvent activityEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(activityEvent, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
