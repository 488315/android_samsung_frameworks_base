package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class PlaybackSettings implements Parcelable {
    public static final Parcelable.Creator<PlaybackSettings> CREATOR = new Parcelable.Creator<PlaybackSettings>() { // from class: android.hardware.tv.tuner.PlaybackSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackSettings createFromParcel(Parcel parcel) {
            PlaybackSettings playbackSettings = new PlaybackSettings();
            playbackSettings.readFromParcel(parcel);
            return playbackSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackSettings[] newArray(int i) {
            return new PlaybackSettings[i];
        }
    };
    public int statusMask = 0;
    public long lowThreshold = 0;
    public long highThreshold = 0;
    public int dataFormat = 4;
    public long packetSize = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.statusMask);
        parcel.writeLong(this.lowThreshold);
        parcel.writeLong(this.highThreshold);
        parcel.writeInt(this.dataFormat);
        parcel.writeLong(this.packetSize);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.statusMask = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.lowThreshold = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.highThreshold = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.dataFormat = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.packetSize = parcel.readLong();
                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
