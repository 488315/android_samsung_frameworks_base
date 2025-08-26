package com.samsung.android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class VideoInfoParcel implements Parcelable {
    public static final Parcelable.Creator<VideoInfoParcel> CREATOR = new Parcelable.Creator<VideoInfoParcel>() { // from class: com.samsung.android.media.VideoInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoInfoParcel createFromParcel(Parcel parcel) {
            VideoInfoParcel videoInfoParcel = new VideoInfoParcel();
            videoInfoParcel.readFromParcel(parcel);
            return videoInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoInfoParcel[] newArray(int i) {
            return new VideoInfoParcel[i];
        }
    };
    public int width = 0;
    public int height = 0;
    public int frameRate = 0;
    public boolean isSWCodec = false;
    public boolean isEncoder = false;
    public String componentName = "";
    public int bitrate = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.frameRate);
        parcel.writeBoolean(this.isSWCodec);
        parcel.writeBoolean(this.isEncoder);
        parcel.writeString(this.componentName);
        parcel.writeInt(this.bitrate);
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
                this.width = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.height = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.frameRate = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isSWCodec = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.isEncoder = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.componentName = parcel.readString();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.bitrate = parcel.readInt();
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
