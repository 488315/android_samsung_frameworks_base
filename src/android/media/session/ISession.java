package android.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.session.ISessionController;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public interface ISession extends IInterface {

    public static class Default implements ISession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.ISession
        public void destroySession() throws RemoteException {
        }

        @Override // android.media.session.ISession
        public IBinder getBinderForSetQueue() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISession
        public ISessionController getController() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISession
        public void resetQueue() throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void sendEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setActive(boolean z) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setCurrentVolume(int i) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setExtras(Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setFlags(int i) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setLaunchPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setMediaButtonBroadcastReceiver(ComponentName componentName) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setMediaButtonReceiver(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setMetadata(MediaMetadata mediaMetadata, long j, String str) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setPlaybackState(PlaybackState playbackState) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setPlaybackToLocal(AudioAttributes audioAttributes) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setPlaybackToRemote(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setQueueTitle(CharSequence charSequence) throws RemoteException {
        }

        @Override // android.media.session.ISession
        public void setRatingType(int i) throws RemoteException {
        }
    }

    void destroySession() throws RemoteException;

    IBinder getBinderForSetQueue() throws RemoteException;

    ISessionController getController() throws RemoteException;

    void resetQueue() throws RemoteException;

    void sendEvent(String str, Bundle bundle) throws RemoteException;

    void setActive(boolean z) throws RemoteException;

    void setCurrentVolume(int i) throws RemoteException;

    void setExtras(Bundle bundle) throws RemoteException;

    void setFlags(int i) throws RemoteException;

    void setLaunchPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void setMediaButtonBroadcastReceiver(ComponentName componentName) throws RemoteException;

    void setMediaButtonReceiver(PendingIntent pendingIntent) throws RemoteException;

    void setMetadata(MediaMetadata mediaMetadata, long j, String str) throws RemoteException;

    void setPlaybackState(PlaybackState playbackState) throws RemoteException;

    void setPlaybackToLocal(AudioAttributes audioAttributes) throws RemoteException;

    void setPlaybackToRemote(int i, int i2, String str) throws RemoteException;

    void setQueueTitle(CharSequence charSequence) throws RemoteException;

    void setRatingType(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISession {
        public static final String DESCRIPTOR = "android.media.session.ISession";
        static final int TRANSACTION_destroySession = 8;
        static final int TRANSACTION_getBinderForSetQueue = 12;
        static final int TRANSACTION_getController = 2;
        static final int TRANSACTION_resetQueue = 11;
        static final int TRANSACTION_sendEvent = 1;
        static final int TRANSACTION_setActive = 4;
        static final int TRANSACTION_setCurrentVolume = 18;
        static final int TRANSACTION_setExtras = 14;
        static final int TRANSACTION_setFlags = 3;
        static final int TRANSACTION_setLaunchPendingIntent = 7;
        static final int TRANSACTION_setMediaButtonBroadcastReceiver = 6;
        static final int TRANSACTION_setMediaButtonReceiver = 5;
        static final int TRANSACTION_setMetadata = 9;
        static final int TRANSACTION_setPlaybackState = 10;
        static final int TRANSACTION_setPlaybackToLocal = 16;
        static final int TRANSACTION_setPlaybackToRemote = 17;
        static final int TRANSACTION_setQueueTitle = 13;
        static final int TRANSACTION_setRatingType = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISession)) {
                return (ISession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendEvent";
                case 2:
                    return "getController";
                case 3:
                    return "setFlags";
                case 4:
                    return "setActive";
                case 5:
                    return "setMediaButtonReceiver";
                case 6:
                    return "setMediaButtonBroadcastReceiver";
                case 7:
                    return "setLaunchPendingIntent";
                case 8:
                    return "destroySession";
                case 9:
                    return "setMetadata";
                case 10:
                    return "setPlaybackState";
                case 11:
                    return "resetQueue";
                case 12:
                    return "getBinderForSetQueue";
                case 13:
                    return "setQueueTitle";
                case 14:
                    return "setExtras";
                case 15:
                    return "setRatingType";
                case 16:
                    return "setPlaybackToLocal";
                case 17:
                    return "setPlaybackToRemote";
                case 18:
                    return "setCurrentVolume";
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
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEvent(string, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ISessionController controller = getController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(controller);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFlags(i3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActive(z);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaButtonReceiver(pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaButtonBroadcastReceiver(componentName);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLaunchPendingIntent(pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    destroySession();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    MediaMetadata mediaMetadata = (MediaMetadata) parcel.readTypedObject(MediaMetadata.CREATOR);
                    long j = parcel.readLong();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMetadata(mediaMetadata, j, string2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    PlaybackState playbackState = (PlaybackState) parcel.readTypedObject(PlaybackState.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPlaybackState(playbackState);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    resetQueue();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder binderForSetQueue = getBinderForSetQueue();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(binderForSetQueue);
                    return true;
                case 13:
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setQueueTitle(charSequence);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setExtras(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRatingType(i4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPlaybackToLocal(audioAttributes);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPlaybackToRemote(i5, i6, string3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentVolume(i7);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISession {
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

            @Override // android.media.session.ISession
            public void sendEvent(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public ISessionController getController() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISessionController.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setFlags(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setActive(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setMediaButtonReceiver(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setMediaButtonBroadcastReceiver(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setLaunchPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void destroySession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setMetadata(MediaMetadata mediaMetadata, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaMetadata, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setPlaybackState(PlaybackState playbackState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(playbackState, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void resetQueue() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public IBinder getBinderForSetQueue() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setQueueTitle(CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setExtras(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setRatingType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setPlaybackToLocal(AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setPlaybackToRemote(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.session.ISession
            public void setCurrentVolume(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
