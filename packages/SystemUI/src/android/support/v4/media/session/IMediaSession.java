package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.view.KeyEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface IMediaSession extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
        public static Object access$000(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

    void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i);

    void adjustVolume(int i, int i2);

    void fastForward();

    Bundle getExtras();

    long getFlags();

    PendingIntent getLaunchPendingIntent();

    MediaMetadataCompat getMetadata();

    String getPackageName();

    void getPlaybackState();

    void getQueue();

    CharSequence getQueueTitle();

    void getRatingType();

    void getRepeatMode();

    Bundle getSessionInfo();

    void getShuffleMode();

    String getTag();

    ParcelableVolumeInfo getVolumeAttributes();

    void isCaptioningEnabled();

    boolean isTransportControlEnabled();

    void next();

    void pause();

    void play();

    void playFromMediaId(Bundle bundle, String str);

    void playFromSearch(Bundle bundle, String str);

    void playFromUri(Uri uri, Bundle bundle);

    void prepare();

    void prepareFromMediaId(Bundle bundle, String str);

    void prepareFromSearch(Bundle bundle, String str);

    void prepareFromUri(Uri uri, Bundle bundle);

    void previous();

    void rate(RatingCompat ratingCompat);

    void rateWithExtras(RatingCompat ratingCompat, Bundle bundle);

    void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback);

    void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

    void removeQueueItemAt(int i);

    void rewind();

    void seekTo(long j);

    void sendCommand(String str, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper);

    void sendCustomAction(Bundle bundle, String str);

    boolean sendMediaButton(KeyEvent keyEvent);

    void setCaptioningEnabled(boolean z);

    void setPlaybackSpeed(float f);

    void setRepeatMode(int i);

    void setShuffleMode(int i);

    void setVolumeTo(int i, int i2);

    void skipToQueueItem(long j);

    void stop();

    void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IMediaSession {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IMediaSession {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeStrongInterface(iMediaControllerCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.support.v4.media.session.IMediaSession
            public final boolean sendMediaButton(KeyEvent keyEvent) {
                throw null;
            }
        }

        public Stub() {
            attachInterface(this, "android.support.v4.media.session.IMediaSession");
        }

        public static IMediaSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.support.v4.media.session.IMediaSession");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMediaSession)) ? new Proxy(iBinder) : (IMediaSession) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.support.v4.media.session.IMediaSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.support.v4.media.session.IMediaSession");
                return true;
            }
            IMediaControllerCallback iMediaControllerCallback = null;
            switch (i) {
                case 1:
                    sendCommand(parcel.readString(), (Bundle) _Parcel.access$000(parcel, Bundle.CREATOR), (MediaSessionCompat.ResultReceiverWrapper) _Parcel.access$000(parcel, MediaSessionCompat.ResultReceiverWrapper.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean sendMediaButton = sendMediaButton((KeyEvent) _Parcel.access$000(parcel, KeyEvent.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(sendMediaButton ? 1 : 0);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("android.support.v4.media.session.IMediaControllerCallback");
                        iMediaControllerCallback = (queryLocalInterface == null || !(queryLocalInterface instanceof IMediaControllerCallback)) ? new IMediaControllerCallback.Stub.Proxy(readStrongBinder) : (IMediaControllerCallback) queryLocalInterface;
                    }
                    registerCallbackListener(iMediaControllerCallback);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("android.support.v4.media.session.IMediaControllerCallback");
                        iMediaControllerCallback = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IMediaControllerCallback)) ? new IMediaControllerCallback.Stub.Proxy(readStrongBinder2) : (IMediaControllerCallback) queryLocalInterface2;
                    }
                    unregisterCallbackListener(iMediaControllerCallback);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean isTransportControlEnabled = isTransportControlEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isTransportControlEnabled ? 1 : 0);
                    return true;
                case 6:
                    String packageName = getPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(packageName);
                    return true;
                case 7:
                    String tag = getTag();
                    parcel2.writeNoException();
                    parcel2.writeString(tag);
                    return true;
                case 8:
                    PendingIntent launchPendingIntent = getLaunchPendingIntent();
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, launchPendingIntent, 1);
                    return true;
                case 9:
                    long flags = getFlags();
                    parcel2.writeNoException();
                    parcel2.writeLong(flags);
                    return true;
                case 10:
                    ParcelableVolumeInfo volumeAttributes = getVolumeAttributes();
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, volumeAttributes, 1);
                    return true;
                case 11:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.readString();
                    adjustVolume(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.readString();
                    setVolumeTo(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    play();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    playFromMediaId((Bundle) _Parcel.access$000(parcel, Bundle.CREATOR), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 15:
                    playFromSearch((Bundle) _Parcel.access$000(parcel, Bundle.CREATOR), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 16:
                    playFromUri((Uri) _Parcel.access$000(parcel, Uri.CREATOR), (Bundle) _Parcel.access$000(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 17:
                    skipToQueueItem(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 18:
                    pause();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    next();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    previous();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    fastForward();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    rewind();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    seekTo(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 25:
                    rate((RatingCompat) _Parcel.access$000(parcel, RatingCompat.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 26:
                    sendCustomAction((Bundle) _Parcel.access$000(parcel, Bundle.CREATOR), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    MediaMetadataCompat metadata = getMetadata();
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, metadata, 1);
                    return true;
                case 28:
                    getPlaybackState();
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 29:
                    parcel2.writeNoException();
                    parcel2.writeInt(-1);
                    return true;
                case 30:
                    CharSequence queueTitle = getQueueTitle();
                    parcel2.writeNoException();
                    if (queueTitle == null) {
                        parcel2.writeInt(0);
                        return true;
                    }
                    parcel2.writeInt(1);
                    TextUtils.writeToParcel(queueTitle, parcel2, 1);
                    return true;
                case 31:
                    Bundle extras = getExtras();
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, extras, 1);
                    return true;
                case 32:
                    getRatingType();
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 33:
                    prepare();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    prepareFromMediaId((Bundle) _Parcel.access$000(parcel, Bundle.CREATOR), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 35:
                    prepareFromSearch((Bundle) _Parcel.access$000(parcel, Bundle.CREATOR), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 36:
                    prepareFromUri((Uri) _Parcel.access$000(parcel, Uri.CREATOR), (Bundle) _Parcel.access$000(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 37:
                    getRepeatMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 38:
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 39:
                    setRepeatMode(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 40:
                    parcel.readInt();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    addQueueItem((MediaDescriptionCompat) _Parcel.access$000(parcel, MediaDescriptionCompat.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 42:
                    addQueueItemAt((MediaDescriptionCompat) _Parcel.access$000(parcel, MediaDescriptionCompat.CREATOR), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 43:
                    removeQueueItem((MediaDescriptionCompat) _Parcel.access$000(parcel, MediaDescriptionCompat.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 44:
                    removeQueueItemAt(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 45:
                    isCaptioningEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 46:
                    setCaptioningEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    getShuffleMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                    return true;
                case 48:
                    setShuffleMode(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 49:
                    setPlaybackSpeed(parcel.readFloat());
                    parcel2.writeNoException();
                    return true;
                case 50:
                    Bundle sessionInfo = getSessionInfo();
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, sessionInfo, 1);
                    return true;
                case 51:
                    rateWithExtras((RatingCompat) _Parcel.access$000(parcel, RatingCompat.CREATOR), (Bundle) _Parcel.access$000(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
