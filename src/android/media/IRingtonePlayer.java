package android.media;

import android.app.INotificationPlayerOnCompletionListener;
import android.media.VolumeShaper;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;

/* loaded from: classes2.dex */
public interface IRingtonePlayer extends IInterface {

    public static class Default implements IRingtonePlayer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public void fadeinRingtone(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void fadeoutRingtone(IBinder iBinder, int i, float f) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public String getTitle(Uri uri) throws RemoteException {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public boolean isPlaying(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.media.IRingtonePlayer
        public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void playAsync(Uri uri, UserHandle userHandle, boolean z, AudioAttributes audioAttributes, float f) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z, VolumeShaper.Configuration configuration) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener iNotificationPlayerOnCompletionListener) throws RemoteException {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public void setPlaybackProperties(IBinder iBinder, float f, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void stop(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void stopAsync() throws RemoteException {
        }
    }

    void fadeinRingtone(IBinder iBinder) throws RemoteException;

    void fadeoutRingtone(IBinder iBinder, int i, float f) throws RemoteException;

    String getTitle(Uri uri) throws RemoteException;

    boolean isPlaying(IBinder iBinder) throws RemoteException;

    ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException;

    void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z) throws RemoteException;

    void playAsync(Uri uri, UserHandle userHandle, boolean z, AudioAttributes audioAttributes, float f) throws RemoteException;

    void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z, VolumeShaper.Configuration configuration) throws RemoteException;

    IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener iNotificationPlayerOnCompletionListener) throws RemoteException;

    void setPlaybackProperties(IBinder iBinder, float f, boolean z, boolean z2) throws RemoteException;

    void stop(IBinder iBinder) throws RemoteException;

    void stopAsync() throws RemoteException;

    public static abstract class Stub extends Binder implements IRingtonePlayer {
        public static final String DESCRIPTOR = "android.media.IRingtonePlayer";
        static final int TRANSACTION_fadeinRingtone = 10;
        static final int TRANSACTION_fadeoutRingtone = 11;
        static final int TRANSACTION_getTitle = 8;
        static final int TRANSACTION_isPlaying = 4;
        static final int TRANSACTION_openRingtone = 9;
        static final int TRANSACTION_play = 1;
        static final int TRANSACTION_playAsync = 6;
        static final int TRANSACTION_playWithVolumeShaping = 2;
        static final int TRANSACTION_setOnCompletionListener = 12;
        static final int TRANSACTION_setPlaybackProperties = 5;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_stopAsync = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRingtonePlayer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRingtonePlayer)) {
                return (IRingtonePlayer) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PLAY;
                case 2:
                    return "playWithVolumeShaping";
                case 3:
                    return "stop";
                case 4:
                    return "isPlaying";
                case 5:
                    return "setPlaybackProperties";
                case 6:
                    return "playAsync";
                case 7:
                    return "stopAsync";
                case 8:
                    return "getTitle";
                case 9:
                    return "openRingtone";
                case 10:
                    return "fadeinRingtone";
                case 11:
                    return "fadeoutRingtone";
                case 12:
                    return "setOnCompletionListener";
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    float readFloat = parcel.readFloat();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    play(readStrongBinder, uri, audioAttributes, readFloat, readBoolean);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    AudioAttributes audioAttributes2 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    float readFloat2 = parcel.readFloat();
                    boolean readBoolean2 = parcel.readBoolean();
                    VolumeShaper.Configuration configuration = (VolumeShaper.Configuration) parcel.readTypedObject(VolumeShaper.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    playWithVolumeShaping(readStrongBinder2, uri2, audioAttributes2, readFloat2, readBoolean2, configuration);
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stop(readStrongBinder3);
                    return true;
                case 4:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isPlaying = isPlaying(readStrongBinder4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPlaying);
                    return true;
                case 5:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    float readFloat3 = parcel.readFloat();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPlaybackProperties(readStrongBinder5, readFloat3, readBoolean3, readBoolean4);
                    return true;
                case 6:
                    Uri uri3 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    AudioAttributes audioAttributes3 = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                    float readFloat4 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    playAsync(uri3, userHandle, readBoolean5, audioAttributes3, readFloat4);
                    return true;
                case 7:
                    stopAsync();
                    return true;
                case 8:
                    Uri uri4 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    String title = getTitle(uri4);
                    parcel2.writeNoException();
                    parcel2.writeString(title);
                    return true;
                case 9:
                    Uri uri5 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParcelFileDescriptor openRingtone = openRingtone(uri5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openRingtone, 1);
                    return true;
                case 10:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    fadeinRingtone(readStrongBinder6);
                    return true;
                case 11:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    float readFloat5 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    fadeoutRingtone(readStrongBinder7, readInt, readFloat5);
                    return true;
                case 12:
                    INotificationPlayerOnCompletionListener asInterface = INotificationPlayerOnCompletionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IBinder onCompletionListener = setOnCompletionListener(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(onCompletionListener);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRingtonePlayer {
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

            @Override // android.media.IRingtonePlayer
            public void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z, VolumeShaper.Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void stop(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public boolean isPlaying(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void setPlaybackProperties(IBinder iBinder, float f, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void playAsync(Uri uri, UserHandle userHandle, boolean z, AudioAttributes audioAttributes, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void stopAsync() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public String getTitle(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void fadeinRingtone(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void fadeoutRingtone(IBinder iBinder, int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener iNotificationPlayerOnCompletionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationPlayerOnCompletionListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
