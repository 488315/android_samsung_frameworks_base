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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.frameRate);
        parcel.writeBoolean(this.isSWCodec);
        parcel.writeBoolean(this.isEncoder);
        parcel.writeString(this.componentName);
        parcel.writeInt(this.bitrate);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.width = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.height = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.frameRate = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.isSWCodec = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.isEncoder = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.componentName = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.bitrate = parcel.readInt();
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
