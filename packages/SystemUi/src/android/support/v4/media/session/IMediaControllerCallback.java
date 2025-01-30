package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface IMediaControllerCallback extends IInterface {
    void onExtrasChanged(Bundle bundle);

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat);

    void onQueueChanged(List list);

    void onQueueTitleChanged(CharSequence charSequence);

    void onSessionDestroyed();

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements IMediaControllerCallback {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements IMediaControllerCallback {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public final void onSessionDestroyed() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        int i = Stub.$r8$clinit;
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public final void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (parcelableVolumeInfo != null) {
                        obtain.writeInt(1);
                        parcelableVolumeInfo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, obtain, null, 1)) {
                        int i = Stub.$r8$clinit;
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "android.support.v4.media.session.IMediaControllerCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1598968902) {
                parcel2.writeString("android.support.v4.media.session.IMediaControllerCallback");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    String readString = parcel.readString();
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                    MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback) ((MediaControllerCompat.Callback.StubCompat) this).mCallback.get();
                    if (callback != null) {
                        callback.postToHandler(1, readString, bundle);
                    }
                    return true;
                case 2:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    onSessionDestroyed();
                    return true;
                case 3:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    PlaybackStateCompat createFromParcel = parcel.readInt() != 0 ? PlaybackStateCompat.CREATOR.createFromParcel(parcel) : null;
                    MediaControllerCompat.Callback callback2 = (MediaControllerCompat.Callback) ((MediaControllerCompat.Callback.StubCompat) this).mCallback.get();
                    if (callback2 != null) {
                        callback2.postToHandler(2, createFromParcel, null);
                    }
                    return true;
                case 4:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    onMetadataChanged(parcel.readInt() != 0 ? MediaMetadataCompat.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 5:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    onQueueChanged(parcel.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR));
                    return true;
                case 6:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    onQueueTitleChanged(parcel.readInt() != 0 ? (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 7:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    onExtrasChanged(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 8:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    onVolumeInfoChanged(parcel.readInt() != 0 ? ParcelableVolumeInfo.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 9:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    int readInt = parcel.readInt();
                    MediaControllerCompat.Callback callback3 = (MediaControllerCompat.Callback) ((MediaControllerCompat.Callback.StubCompat) this).mCallback.get();
                    if (callback3 != null) {
                        callback3.postToHandler(9, Integer.valueOf(readInt), null);
                    }
                    return true;
                case 10:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    parcel.readInt();
                    return true;
                case 11:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    boolean z = parcel.readInt() != 0;
                    MediaControllerCompat.Callback callback4 = (MediaControllerCompat.Callback) ((MediaControllerCompat.Callback.StubCompat) this).mCallback.get();
                    if (callback4 != null) {
                        callback4.postToHandler(11, Boolean.valueOf(z), null);
                    }
                    return true;
                case 12:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    int readInt2 = parcel.readInt();
                    MediaControllerCompat.Callback callback5 = (MediaControllerCompat.Callback) ((MediaControllerCompat.Callback.StubCompat) this).mCallback.get();
                    if (callback5 != null) {
                        callback5.postToHandler(12, Integer.valueOf(readInt2), null);
                    }
                    return true;
                case 13:
                    parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    MediaControllerCompat.Callback callback6 = (MediaControllerCompat.Callback) ((MediaControllerCompat.Callback.StubCompat) this).mCallback.get();
                    if (callback6 != null) {
                        callback6.postToHandler(13, null, null);
                    }
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
