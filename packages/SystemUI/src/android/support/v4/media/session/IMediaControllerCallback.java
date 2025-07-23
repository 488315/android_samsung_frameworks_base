package android.support.v4.media.session;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface IMediaControllerCallback extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
        public static Object access$000(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }

    void onExtrasChanged();

    void onMetadataChanged();

    void onQueueChanged();

    void onQueueTitleChanged();

    void onSessionDestroyed();

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IMediaControllerCallback {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IMediaControllerCallback {
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
                throw null;
            }
        }

        public Stub() {
            attachInterface(this, "android.support.v4.media.session.IMediaControllerCallback");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.support.v4.media.session.IMediaControllerCallback");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.readString();
                    return true;
                case 2:
                    onSessionDestroyed();
                    return true;
                case 3:
                    return true;
                case 4:
                    onMetadataChanged();
                    return true;
                case 5:
                    parcel.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
                    onQueueChanged();
                    return true;
                case 6:
                    onQueueTitleChanged();
                    return true;
                case 7:
                    onExtrasChanged();
                    return true;
                case 8:
                    onVolumeInfoChanged((ParcelableVolumeInfo) _Parcel.access$000(parcel, ParcelableVolumeInfo.CREATOR));
                    return true;
                case 9:
                    parcel.readInt();
                    return true;
                case 10:
                    parcel.readInt();
                    return true;
                case 11:
                    parcel.readInt();
                    return true;
                case 12:
                    parcel.readInt();
                    return true;
                case 13:
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
