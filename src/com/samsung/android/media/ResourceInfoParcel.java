package com.samsung.android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class ResourceInfoParcel implements Parcelable {
    public static final Parcelable.Creator<ResourceInfoParcel> CREATOR = new Parcelable.Creator<ResourceInfoParcel>() { // from class: com.samsung.android.media.ResourceInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceInfoParcel createFromParcel(Parcel parcel) {
            ResourceInfoParcel resourceInfoParcel = new ResourceInfoParcel();
            resourceInfoParcel.readFromParcel(parcel);
            return resourceInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResourceInfoParcel[] newArray(int i) {
            return new ResourceInfoParcel[i];
        }
    };
    public String codecName;
    public int subType = 0;
    public int type = 0;
    public int pid = 0;
    public long clientId = 0;
    public int state = 0;
    public int width = 0;
    public int height = 0;
    public int frameRate = 0;
    public int isEncoder = 0;
    public int isSWCodec = 0;
    public int bitrate = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subType);
        parcel.writeInt(this.type);
        parcel.writeInt(this.pid);
        parcel.writeLong(this.clientId);
        parcel.writeInt(this.state);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.frameRate);
        parcel.writeInt(this.isEncoder);
        parcel.writeInt(this.isSWCodec);
        parcel.writeString(this.codecName);
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
                this.subType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.pid = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.clientId = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.state = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.width = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.height = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.frameRate = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.isEncoder = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.isSWCodec = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.codecName = parcel.readString();
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
