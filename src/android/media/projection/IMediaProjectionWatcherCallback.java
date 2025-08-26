package android.media.projection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ContentRecordingSession;

/* loaded from: classes3.dex */
public interface IMediaProjectionWatcherCallback extends IInterface {

    public static class Default implements IMediaProjectionWatcherCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onMediaProjectionEvent(MediaProjectionEvent mediaProjectionEvent, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStart(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStop(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
        }
    }

    void onMediaProjectionEvent(MediaProjectionEvent mediaProjectionEvent, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) throws RemoteException;

    void onRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) throws RemoteException;

    void onStart(MediaProjectionInfo mediaProjectionInfo) throws RemoteException;

    void onStop(MediaProjectionInfo mediaProjectionInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaProjectionWatcherCallback {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjectionWatcherCallback";
        static final int TRANSACTION_onMediaProjectionEvent = 4;
        static final int TRANSACTION_onRecordingSessionSet = 3;
        static final int TRANSACTION_onStart = 1;
        static final int TRANSACTION_onStop = 2;

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

        public static IMediaProjectionWatcherCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaProjectionWatcherCallback)) {
                return (IMediaProjectionWatcherCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStart";
            }
            if (i == 2) {
                return "onStop";
            }
            if (i == 3) {
                return "onRecordingSessionSet";
            }
            if (i != 4) {
                return null;
            }
            return "onMediaProjectionEvent";
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
                MediaProjectionInfo mediaProjectionInfo = (MediaProjectionInfo) parcel.readTypedObject(MediaProjectionInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onStart(mediaProjectionInfo);
            } else if (i == 2) {
                MediaProjectionInfo mediaProjectionInfo2 = (MediaProjectionInfo) parcel.readTypedObject(MediaProjectionInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onStop(mediaProjectionInfo2);
            } else if (i == 3) {
                MediaProjectionInfo mediaProjectionInfo3 = (MediaProjectionInfo) parcel.readTypedObject(MediaProjectionInfo.CREATOR);
                ContentRecordingSession contentRecordingSession = (ContentRecordingSession) parcel.readTypedObject(ContentRecordingSession.CREATOR);
                parcel.enforceNoDataAvail();
                onRecordingSessionSet(mediaProjectionInfo3, contentRecordingSession);
            } else if (i == 4) {
                MediaProjectionEvent mediaProjectionEvent = (MediaProjectionEvent) parcel.readTypedObject(MediaProjectionEvent.CREATOR);
                MediaProjectionInfo mediaProjectionInfo4 = (MediaProjectionInfo) parcel.readTypedObject(MediaProjectionInfo.CREATOR);
                ContentRecordingSession contentRecordingSession2 = (ContentRecordingSession) parcel.readTypedObject(ContentRecordingSession.CREATOR);
                parcel.enforceNoDataAvail();
                onMediaProjectionEvent(mediaProjectionEvent, mediaProjectionInfo4, contentRecordingSession2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaProjectionWatcherCallback {
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

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onStart(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaProjectionInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onStop(MediaProjectionInfo mediaProjectionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaProjectionInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaProjectionInfo, 0);
                    parcelObtain.writeTypedObject(contentRecordingSession, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionWatcherCallback
            public void onMediaProjectionEvent(MediaProjectionEvent mediaProjectionEvent, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaProjectionEvent, 0);
                    parcelObtain.writeTypedObject(mediaProjectionInfo, 0);
                    parcelObtain.writeTypedObject(contentRecordingSession, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
