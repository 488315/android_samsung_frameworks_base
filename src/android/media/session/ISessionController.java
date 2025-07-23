package android.media.session;

import android.app.PendingIntent;
import android.content.pm.ParceledListSlice;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.ISessionControllerCallback;
import android.media.session.MediaController;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.KeyEvent;

/* loaded from: classes3.dex */
public interface ISessionController extends IInterface {

    public static class Default implements ISessionController {
        @Override // android.media.session.ISessionController
        public void adjustVolume(String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.ISessionController
        public void fastForward(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public Bundle getExtras() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public long getFlags() throws RemoteException {
            return 0L;
        }

        @Override // android.media.session.ISessionController
        public PendingIntent getLaunchPendingIntent() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public MediaMetadata getMetadata() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public String getPackageName() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public PlaybackState getPlaybackState() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public ParceledListSlice getQueue() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public CharSequence getQueueTitle() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public int getRatingType() throws RemoteException {
            return 0;
        }

        @Override // android.media.session.ISessionController
        public Bundle getSessionInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public String getTag() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public void next(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void pause(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void play(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void playFromMediaId(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void playFromSearch(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void playFromUri(String str, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepare(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepareFromMediaId(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepareFromSearch(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepareFromUri(String str, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void previous(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void rate(String str, Rating rating) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void registerCallback(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void rewind(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void seekTo(String str, long j) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void sendCommand(String str, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void sendCustomAction(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public boolean sendMediaButton(String str, KeyEvent keyEvent) throws RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionController
        public void setPlaybackSpeed(String str, float f) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void setVolumeTo(String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void skipToQueueItem(String str, long j) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void stop(String str) throws RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
        }
    }

    void adjustVolume(String str, String str2, int i, int i2) throws RemoteException;

    void fastForward(String str) throws RemoteException;

    Bundle getExtras() throws RemoteException;

    long getFlags() throws RemoteException;

    PendingIntent getLaunchPendingIntent() throws RemoteException;

    MediaMetadata getMetadata() throws RemoteException;

    String getPackageName() throws RemoteException;

    PlaybackState getPlaybackState() throws RemoteException;

    ParceledListSlice getQueue() throws RemoteException;

    CharSequence getQueueTitle() throws RemoteException;

    int getRatingType() throws RemoteException;

    Bundle getSessionInfo() throws RemoteException;

    String getTag() throws RemoteException;

    MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException;

    void next(String str) throws RemoteException;

    void pause(String str) throws RemoteException;

    void play(String str) throws RemoteException;

    void playFromMediaId(String str, String str2, Bundle bundle) throws RemoteException;

    void playFromSearch(String str, String str2, Bundle bundle) throws RemoteException;

    void playFromUri(String str, Uri uri, Bundle bundle) throws RemoteException;

    void prepare(String str) throws RemoteException;

    void prepareFromMediaId(String str, String str2, Bundle bundle) throws RemoteException;

    void prepareFromSearch(String str, String str2, Bundle bundle) throws RemoteException;

    void prepareFromUri(String str, Uri uri, Bundle bundle) throws RemoteException;

    void previous(String str) throws RemoteException;

    void rate(String str, Rating rating) throws RemoteException;

    void registerCallback(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    void rewind(String str) throws RemoteException;

    void seekTo(String str, long j) throws RemoteException;

    void sendCommand(String str, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException;

    void sendCustomAction(String str, String str2, Bundle bundle) throws RemoteException;

    boolean sendMediaButton(String str, KeyEvent keyEvent) throws RemoteException;

    void setPlaybackSpeed(String str, float f) throws RemoteException;

    void setVolumeTo(String str, String str2, int i, int i2) throws RemoteException;

    void skipToQueueItem(String str, long j) throws RemoteException;

    void stop(String str) throws RemoteException;

    void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISessionController {
        public static final String DESCRIPTOR = "android.media.session.ISessionController";
        static final int TRANSACTION_adjustVolume = 11;
        static final int TRANSACTION_fastForward = 26;
        static final int TRANSACTION_getExtras = 36;
        static final int TRANSACTION_getFlags = 9;
        static final int TRANSACTION_getLaunchPendingIntent = 8;
        static final int TRANSACTION_getMetadata = 32;
        static final int TRANSACTION_getPackageName = 5;
        static final int TRANSACTION_getPlaybackState = 33;
        static final int TRANSACTION_getQueue = 34;
        static final int TRANSACTION_getQueueTitle = 35;
        static final int TRANSACTION_getRatingType = 37;
        static final int TRANSACTION_getSessionInfo = 7;
        static final int TRANSACTION_getTag = 6;
        static final int TRANSACTION_getVolumeAttributes = 10;
        static final int TRANSACTION_next = 24;
        static final int TRANSACTION_pause = 22;
        static final int TRANSACTION_play = 17;
        static final int TRANSACTION_playFromMediaId = 18;
        static final int TRANSACTION_playFromSearch = 19;
        static final int TRANSACTION_playFromUri = 20;
        static final int TRANSACTION_prepare = 13;
        static final int TRANSACTION_prepareFromMediaId = 14;
        static final int TRANSACTION_prepareFromSearch = 15;
        static final int TRANSACTION_prepareFromUri = 16;
        static final int TRANSACTION_previous = 25;
        static final int TRANSACTION_rate = 29;
        static final int TRANSACTION_registerCallback = 3;
        static final int TRANSACTION_rewind = 27;
        static final int TRANSACTION_seekTo = 28;
        static final int TRANSACTION_sendCommand = 1;
        static final int TRANSACTION_sendCustomAction = 31;
        static final int TRANSACTION_sendMediaButton = 2;
        static final int TRANSACTION_setPlaybackSpeed = 30;
        static final int TRANSACTION_setVolumeTo = 12;
        static final int TRANSACTION_skipToQueueItem = 21;
        static final int TRANSACTION_stop = 23;
        static final int TRANSACTION_unregisterCallback = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 36;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISessionController)) {
                return (ISessionController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendCommand";
                case 2:
                    return "sendMediaButton";
                case 3:
                    return "registerCallback";
                case 4:
                    return "unregisterCallback";
                case 5:
                    return "getPackageName";
                case 6:
                    return "getTag";
                case 7:
                    return "getSessionInfo";
                case 8:
                    return "getLaunchPendingIntent";
                case 9:
                    return "getFlags";
                case 10:
                    return "getVolumeAttributes";
                case 11:
                    return "adjustVolume";
                case 12:
                    return "setVolumeTo";
                case 13:
                    return "prepare";
                case 14:
                    return "prepareFromMediaId";
                case 15:
                    return "prepareFromSearch";
                case 16:
                    return "prepareFromUri";
                case 17:
                    return TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PLAY;
                case 18:
                    return "playFromMediaId";
                case 19:
                    return "playFromSearch";
                case 20:
                    return "playFromUri";
                case 21:
                    return "skipToQueueItem";
                case 22:
                    return TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE;
                case 23:
                    return "stop";
                case 24:
                    return "next";
                case 25:
                    return "previous";
                case 26:
                    return "fastForward";
                case 27:
                    return "rewind";
                case 28:
                    return "seekTo";
                case 29:
                    return TextToSpeech.Engine.KEY_PARAM_RATE;
                case 30:
                    return "setPlaybackSpeed";
                case 31:
                    return "sendCustomAction";
                case 32:
                    return "getMetadata";
                case 33:
                    return "getPlaybackState";
                case 34:
                    return "getQueue";
                case 35:
                    return "getQueueTitle";
                case 36:
                    return "getExtras";
                case 37:
                    return "getRatingType";
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
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCommand(readString, readString2, bundle, resultReceiver);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendMediaButton = sendMediaButton(readString3, keyEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendMediaButton);
                    return true;
                case 3:
                    String readString4 = parcel.readString();
                    ISessionControllerCallback asInterface = ISessionControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(readString4, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ISessionControllerCallback asInterface2 = ISessionControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String packageName = getPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(packageName);
                    return true;
                case 6:
                    String tag = getTag();
                    parcel2.writeNoException();
                    parcel2.writeString(tag);
                    return true;
                case 7:
                    Bundle sessionInfo = getSessionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionInfo, 1);
                    return true;
                case 8:
                    PendingIntent launchPendingIntent = getLaunchPendingIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchPendingIntent, 1);
                    return true;
                case 9:
                    long flags = getFlags();
                    parcel2.writeNoException();
                    parcel2.writeLong(flags);
                    return true;
                case 10:
                    MediaController.PlaybackInfo volumeAttributes = getVolumeAttributes();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(volumeAttributes, 1);
                    return true;
                case 11:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustVolume(readString5, readString6, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolumeTo(readString7, readString8, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    prepare(readString9);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareFromMediaId(readString10, readString11, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareFromSearch(readString12, readString13, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString14 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareFromUri(readString14, uri, bundle4);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    play(readString15);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    playFromMediaId(readString16, readString17, bundle5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    playFromSearch(readString18, readString19, bundle6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString20 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    playFromUri(readString20, uri2, bundle7);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString21 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    skipToQueueItem(readString21, readLong);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    pause(readString22);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stop(readString23);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    next(readString24);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    previous(readString25);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    fastForward(readString26);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rewind(readString27);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString28 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    seekTo(readString28, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString29 = parcel.readString();
                    Rating rating = (Rating) parcel.readTypedObject(Rating.CREATOR);
                    parcel.enforceNoDataAvail();
                    rate(readString29, rating);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString30 = parcel.readString();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setPlaybackSpeed(readString30, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCustomAction(readString31, readString32, bundle8);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    MediaMetadata metadata = getMetadata();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(metadata, 1);
                    return true;
                case 33:
                    PlaybackState playbackState = getPlaybackState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(playbackState, 1);
                    return true;
                case 34:
                    ParceledListSlice queue = getQueue();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queue, 1);
                    return true;
                case 35:
                    CharSequence queueTitle = getQueueTitle();
                    parcel2.writeNoException();
                    if (queueTitle != null) {
                        parcel2.writeInt(1);
                        TextUtils.writeToParcel(queueTitle, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 36:
                    Bundle extras = getExtras();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(extras, 1);
                    return true;
                case 37:
                    int ratingType = getRatingType();
                    parcel2.writeNoException();
                    parcel2.writeInt(ratingType);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISessionController {
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

            @Override // android.media.session.ISessionController
            public void sendCommand(String str, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public boolean sendMediaButton(String str, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void registerCallback(String str, ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSessionControllerCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void unregisterCallback(ISessionControllerCallback iSessionControllerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSessionControllerCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public String getPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public String getTag() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public Bundle getSessionInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public PendingIntent getLaunchPendingIntent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PendingIntent) obtain2.readTypedObject(PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public long getFlags() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public MediaController.PlaybackInfo getVolumeAttributes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaController.PlaybackInfo) obtain2.readTypedObject(MediaController.PlaybackInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void adjustVolume(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void setVolumeTo(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepare(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepareFromMediaId(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepareFromSearch(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepareFromUri(String str, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void play(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void playFromMediaId(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void playFromSearch(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void playFromUri(String str, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void skipToQueueItem(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void pause(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void stop(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void next(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void previous(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void fastForward(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void rewind(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void seekTo(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void rate(String str, Rating rating) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(rating, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void setPlaybackSpeed(String str, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeFloat(f);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void sendCustomAction(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public MediaMetadata getMetadata() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaMetadata) obtain2.readTypedObject(MediaMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public PlaybackState getPlaybackState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PlaybackState) obtain2.readTypedObject(PlaybackState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public ParceledListSlice getQueue() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public CharSequence getQueueTitle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CharSequence) obtain2.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public Bundle getExtras() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public int getRatingType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
