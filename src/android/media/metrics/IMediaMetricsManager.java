package android.media.metrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMediaMetricsManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.metrics.IMediaMetricsManager";

    public static class Default implements IMediaMetricsManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public String getBundleSessionId(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public String getEditingSessionId(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public String getPlaybackSessionId(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public String getRecordingSessionId(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public String getTranscodingSessionId(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void releaseSessionId(String str, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportBundleMetrics(String str, PersistableBundle persistableBundle, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportEditingEndedEvent(String str, EditingEndedEvent editingEndedEvent, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportNetworkEvent(String str, NetworkEvent networkEvent, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportPlaybackErrorEvent(String str, PlaybackErrorEvent playbackErrorEvent, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportPlaybackMetrics(String str, PlaybackMetrics playbackMetrics, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportPlaybackStateEvent(String str, PlaybackStateEvent playbackStateEvent, int i) throws RemoteException {
        }

        @Override // android.media.metrics.IMediaMetricsManager
        public void reportTrackChangeEvent(String str, TrackChangeEvent trackChangeEvent, int i) throws RemoteException {
        }
    }

    String getBundleSessionId(int i) throws RemoteException;

    String getEditingSessionId(int i) throws RemoteException;

    String getPlaybackSessionId(int i) throws RemoteException;

    String getRecordingSessionId(int i) throws RemoteException;

    String getTranscodingSessionId(int i) throws RemoteException;

    void releaseSessionId(String str, int i) throws RemoteException;

    void reportBundleMetrics(String str, PersistableBundle persistableBundle, int i) throws RemoteException;

    void reportEditingEndedEvent(String str, EditingEndedEvent editingEndedEvent, int i) throws RemoteException;

    void reportNetworkEvent(String str, NetworkEvent networkEvent, int i) throws RemoteException;

    void reportPlaybackErrorEvent(String str, PlaybackErrorEvent playbackErrorEvent, int i) throws RemoteException;

    void reportPlaybackMetrics(String str, PlaybackMetrics playbackMetrics, int i) throws RemoteException;

    void reportPlaybackStateEvent(String str, PlaybackStateEvent playbackStateEvent, int i) throws RemoteException;

    void reportTrackChangeEvent(String str, TrackChangeEvent trackChangeEvent, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaMetricsManager {
        static final int TRANSACTION_getBundleSessionId = 11;
        static final int TRANSACTION_getEditingSessionId = 10;
        static final int TRANSACTION_getPlaybackSessionId = 2;
        static final int TRANSACTION_getRecordingSessionId = 3;
        static final int TRANSACTION_getTranscodingSessionId = 9;
        static final int TRANSACTION_releaseSessionId = 13;
        static final int TRANSACTION_reportBundleMetrics = 12;
        static final int TRANSACTION_reportEditingEndedEvent = 8;
        static final int TRANSACTION_reportNetworkEvent = 4;
        static final int TRANSACTION_reportPlaybackErrorEvent = 5;
        static final int TRANSACTION_reportPlaybackMetrics = 1;
        static final int TRANSACTION_reportPlaybackStateEvent = 6;
        static final int TRANSACTION_reportTrackChangeEvent = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IMediaMetricsManager.DESCRIPTOR);
        }

        public static IMediaMetricsManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMediaMetricsManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaMetricsManager)) {
                return (IMediaMetricsManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportPlaybackMetrics";
                case 2:
                    return "getPlaybackSessionId";
                case 3:
                    return "getRecordingSessionId";
                case 4:
                    return "reportNetworkEvent";
                case 5:
                    return "reportPlaybackErrorEvent";
                case 6:
                    return "reportPlaybackStateEvent";
                case 7:
                    return "reportTrackChangeEvent";
                case 8:
                    return "reportEditingEndedEvent";
                case 9:
                    return "getTranscodingSessionId";
                case 10:
                    return "getEditingSessionId";
                case 11:
                    return "getBundleSessionId";
                case 12:
                    return "reportBundleMetrics";
                case 13:
                    return "releaseSessionId";
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
                parcel.enforceInterface(IMediaMetricsManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMediaMetricsManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    PlaybackMetrics playbackMetrics = (PlaybackMetrics) parcel.readTypedObject(PlaybackMetrics.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPlaybackMetrics(readString, playbackMetrics, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String playbackSessionId = getPlaybackSessionId(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(playbackSessionId);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String recordingSessionId = getRecordingSessionId(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(recordingSessionId);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    NetworkEvent networkEvent = (NetworkEvent) parcel.readTypedObject(NetworkEvent.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportNetworkEvent(readString2, networkEvent, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    PlaybackErrorEvent playbackErrorEvent = (PlaybackErrorEvent) parcel.readTypedObject(PlaybackErrorEvent.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPlaybackErrorEvent(readString3, playbackErrorEvent, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    PlaybackStateEvent playbackStateEvent = (PlaybackStateEvent) parcel.readTypedObject(PlaybackStateEvent.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPlaybackStateEvent(readString4, playbackStateEvent, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString5 = parcel.readString();
                    TrackChangeEvent trackChangeEvent = (TrackChangeEvent) parcel.readTypedObject(TrackChangeEvent.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportTrackChangeEvent(readString5, trackChangeEvent, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    EditingEndedEvent editingEndedEvent = (EditingEndedEvent) parcel.readTypedObject(EditingEndedEvent.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportEditingEndedEvent(readString6, editingEndedEvent, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String transcodingSessionId = getTranscodingSessionId(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeString(transcodingSessionId);
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String editingSessionId = getEditingSessionId(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeString(editingSessionId);
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String bundleSessionId = getBundleSessionId(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeString(bundleSessionId);
                    return true;
                case 12:
                    String readString7 = parcel.readString();
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportBundleMetrics(readString7, persistableBundle, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString8 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSessionId(readString8, readInt13);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMediaMetricsManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMediaMetricsManager.DESCRIPTOR;
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportPlaybackMetrics(String str, PlaybackMetrics playbackMetrics, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(playbackMetrics, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public String getPlaybackSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public String getRecordingSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportNetworkEvent(String str, NetworkEvent networkEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(networkEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportPlaybackErrorEvent(String str, PlaybackErrorEvent playbackErrorEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(playbackErrorEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportPlaybackStateEvent(String str, PlaybackStateEvent playbackStateEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(playbackStateEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportTrackChangeEvent(String str, TrackChangeEvent trackChangeEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(trackChangeEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportEditingEndedEvent(String str, EditingEndedEvent editingEndedEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(editingEndedEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public String getTranscodingSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public String getEditingSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public String getBundleSessionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void reportBundleMetrics(String str, PersistableBundle persistableBundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.metrics.IMediaMetricsManager
            public void releaseSessionId(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMediaMetricsManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
