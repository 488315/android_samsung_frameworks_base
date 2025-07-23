package android.media.session;

import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

/* loaded from: classes3.dex */
public interface ISessionCallback extends IInterface {

    public static class Default implements ISessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.session.ISessionCallback
        public void onAdjustVolume(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onCommand(String str, int i, int i2, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onCustomAction(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onFastForward(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButton(String str, int i, int i2, Intent intent, int i3, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButtonFromController(String str, int i, int i2, Intent intent) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onNext(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPause(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlay(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromMediaId(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromSearch(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromUri(String str, int i, int i2, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepare(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromMediaId(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromSearch(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromUri(String str, int i, int i2, Uri uri, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrevious(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onRate(String str, int i, int i2, Rating rating) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onRewind(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSeekTo(String str, int i, int i2, long j) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSetPlaybackSpeed(String str, int i, int i2, float f) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSetVolumeTo(String str, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSkipToTrack(String str, int i, int i2, long j) throws RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onStop(String str, int i, int i2) throws RemoteException {
        }
    }

    void onAdjustVolume(String str, int i, int i2, int i3) throws RemoteException;

    void onCommand(String str, int i, int i2, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException;

    void onCustomAction(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException;

    void onFastForward(String str, int i, int i2) throws RemoteException;

    void onMediaButton(String str, int i, int i2, Intent intent, int i3, ResultReceiver resultReceiver) throws RemoteException;

    void onMediaButtonFromController(String str, int i, int i2, Intent intent) throws RemoteException;

    void onNext(String str, int i, int i2) throws RemoteException;

    void onPause(String str, int i, int i2) throws RemoteException;

    void onPlay(String str, int i, int i2) throws RemoteException;

    void onPlayFromMediaId(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException;

    void onPlayFromSearch(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException;

    void onPlayFromUri(String str, int i, int i2, Uri uri, Bundle bundle) throws RemoteException;

    void onPrepare(String str, int i, int i2) throws RemoteException;

    void onPrepareFromMediaId(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException;

    void onPrepareFromSearch(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException;

    void onPrepareFromUri(String str, int i, int i2, Uri uri, Bundle bundle) throws RemoteException;

    void onPrevious(String str, int i, int i2) throws RemoteException;

    void onRate(String str, int i, int i2, Rating rating) throws RemoteException;

    void onRewind(String str, int i, int i2) throws RemoteException;

    void onSeekTo(String str, int i, int i2, long j) throws RemoteException;

    void onSetPlaybackSpeed(String str, int i, int i2, float f) throws RemoteException;

    void onSetVolumeTo(String str, int i, int i2, int i3) throws RemoteException;

    void onSkipToTrack(String str, int i, int i2, long j) throws RemoteException;

    void onStop(String str, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISessionCallback {
        public static final String DESCRIPTOR = "android.media.session.ISessionCallback";
        static final int TRANSACTION_onAdjustVolume = 23;
        static final int TRANSACTION_onCommand = 1;
        static final int TRANSACTION_onCustomAction = 22;
        static final int TRANSACTION_onFastForward = 17;
        static final int TRANSACTION_onMediaButton = 2;
        static final int TRANSACTION_onMediaButtonFromController = 3;
        static final int TRANSACTION_onNext = 15;
        static final int TRANSACTION_onPause = 13;
        static final int TRANSACTION_onPlay = 8;
        static final int TRANSACTION_onPlayFromMediaId = 9;
        static final int TRANSACTION_onPlayFromSearch = 10;
        static final int TRANSACTION_onPlayFromUri = 11;
        static final int TRANSACTION_onPrepare = 4;
        static final int TRANSACTION_onPrepareFromMediaId = 5;
        static final int TRANSACTION_onPrepareFromSearch = 6;
        static final int TRANSACTION_onPrepareFromUri = 7;
        static final int TRANSACTION_onPrevious = 16;
        static final int TRANSACTION_onRate = 20;
        static final int TRANSACTION_onRewind = 18;
        static final int TRANSACTION_onSeekTo = 19;
        static final int TRANSACTION_onSetPlaybackSpeed = 21;
        static final int TRANSACTION_onSetVolumeTo = 24;
        static final int TRANSACTION_onSkipToTrack = 12;
        static final int TRANSACTION_onStop = 14;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISessionCallback)) {
                return (ISessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCommand";
                case 2:
                    return "onMediaButton";
                case 3:
                    return "onMediaButtonFromController";
                case 4:
                    return "onPrepare";
                case 5:
                    return "onPrepareFromMediaId";
                case 6:
                    return "onPrepareFromSearch";
                case 7:
                    return "onPrepareFromUri";
                case 8:
                    return "onPlay";
                case 9:
                    return "onPlayFromMediaId";
                case 10:
                    return "onPlayFromSearch";
                case 11:
                    return "onPlayFromUri";
                case 12:
                    return "onSkipToTrack";
                case 13:
                    return "onPause";
                case 14:
                    return "onStop";
                case 15:
                    return "onNext";
                case 16:
                    return "onPrevious";
                case 17:
                    return "onFastForward";
                case 18:
                    return "onRewind";
                case 19:
                    return "onSeekTo";
                case 20:
                    return "onRate";
                case 21:
                    return "onSetPlaybackSpeed";
                case 22:
                    return "onCustomAction";
                case 23:
                    return "onAdjustVolume";
                case 24:
                    return "onSetVolumeTo";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCommand(readString, readInt, readInt2, readString2, bundle, resultReceiver);
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt5 = parcel.readInt();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaButton(readString3, readInt3, readInt4, intent, readInt5, resultReceiver2);
                    return true;
                case 3:
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaButtonFromController(readString4, readInt6, readInt7, intent2);
                    return true;
                case 4:
                    String readString5 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPrepare(readString5, readInt8, readInt9);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    String readString7 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrepareFromMediaId(readString6, readInt10, readInt11, readString7, bundle2);
                    return true;
                case 6:
                    String readString8 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    String readString9 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrepareFromSearch(readString8, readInt12, readInt13, readString9, bundle3);
                    return true;
                case 7:
                    String readString10 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrepareFromUri(readString10, readInt14, readInt15, uri, bundle4);
                    return true;
                case 8:
                    String readString11 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPlay(readString11, readInt16, readInt17);
                    return true;
                case 9:
                    String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    String readString13 = parcel.readString();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlayFromMediaId(readString12, readInt18, readInt19, readString13, bundle5);
                    return true;
                case 10:
                    String readString14 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    String readString15 = parcel.readString();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlayFromSearch(readString14, readInt20, readInt21, readString15, bundle6);
                    return true;
                case 11:
                    String readString16 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlayFromUri(readString16, readInt22, readInt23, uri2, bundle7);
                    return true;
                case 12:
                    String readString17 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onSkipToTrack(readString17, readInt24, readInt25, readLong);
                    return true;
                case 13:
                    String readString18 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPause(readString18, readInt26, readInt27);
                    return true;
                case 14:
                    String readString19 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStop(readString19, readInt28, readInt29);
                    return true;
                case 15:
                    String readString20 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNext(readString20, readInt30, readInt31);
                    return true;
                case 16:
                    String readString21 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPrevious(readString21, readInt32, readInt33);
                    return true;
                case 17:
                    String readString22 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFastForward(readString22, readInt34, readInt35);
                    return true;
                case 18:
                    String readString23 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRewind(readString23, readInt36, readInt37);
                    return true;
                case 19:
                    String readString24 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onSeekTo(readString24, readInt38, readInt39, readLong2);
                    return true;
                case 20:
                    String readString25 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    Rating rating = (Rating) parcel.readTypedObject(Rating.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRate(readString25, readInt40, readInt41, rating);
                    return true;
                case 21:
                    String readString26 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onSetPlaybackSpeed(readString26, readInt42, readInt43, readFloat);
                    return true;
                case 22:
                    String readString27 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    String readString28 = parcel.readString();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCustomAction(readString27, readInt44, readInt45, readString28, bundle8);
                    return true;
                case 23:
                    String readString29 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdjustVolume(readString29, readInt46, readInt47, readInt48);
                    return true;
                case 24:
                    String readString30 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetVolumeTo(readString30, readInt49, readInt50, readInt51);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISessionCallback {
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

            @Override // android.media.session.ISessionCallback
            public void onCommand(String str, int i, int i2, String str2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onMediaButton(String str, int i, int i2, Intent intent, int i3, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onMediaButtonFromController(String str, int i, int i2, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepare(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepareFromMediaId(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepareFromSearch(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepareFromUri(String str, int i, int i2, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlay(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlayFromMediaId(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlayFromSearch(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlayFromUri(String str, int i, int i2, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSkipToTrack(String str, int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPause(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onStop(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onNext(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrevious(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onFastForward(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onRewind(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSeekTo(String str, int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onRate(String str, int i, int i2, Rating rating) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(rating, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSetPlaybackSpeed(String str, int i, int i2, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onCustomAction(String str, int i, int i2, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onAdjustVolume(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSetVolumeTo(String str, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
