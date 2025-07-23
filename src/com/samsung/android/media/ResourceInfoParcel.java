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
        int dataPosition = parcel.dataPosition();
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
                this.subType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.pid = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.clientId = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.state = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.width = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.height = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.frameRate = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.isEncoder = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.isSWCodec = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.codecName = parcel.readString();
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
