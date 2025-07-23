package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioOffloadInfo implements Parcelable {
    public static final Parcelable.Creator<AudioOffloadInfo> CREATOR = new Parcelable.Creator<AudioOffloadInfo>() { // from class: android.media.audio.common.AudioOffloadInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioOffloadInfo createFromParcel(Parcel parcel) {
            AudioOffloadInfo audioOffloadInfo = new AudioOffloadInfo();
            audioOffloadInfo.readFromParcel(parcel);
            return audioOffloadInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioOffloadInfo[] newArray(int i) {
            return new AudioOffloadInfo[i];
        }
    };
    public AudioConfigBase base;
    public int streamType = -2;
    public int bitRatePerSecond = 0;
    public long durationUs = 0;
    public boolean hasVideo = false;
    public boolean isStreaming = false;
    public int bitWidth = 16;
    public int offloadBufferSize = 0;
    public int usage = -1;
    public byte encapsulationMode = -1;
    public int contentId = 0;
    public int syncId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.base, i);
        parcel.writeInt(this.streamType);
        parcel.writeInt(this.bitRatePerSecond);
        parcel.writeLong(this.durationUs);
        parcel.writeBoolean(this.hasVideo);
        parcel.writeBoolean(this.isStreaming);
        parcel.writeInt(this.bitWidth);
        parcel.writeInt(this.offloadBufferSize);
        parcel.writeInt(this.usage);
        parcel.writeByte(this.encapsulationMode);
        parcel.writeInt(this.contentId);
        parcel.writeInt(this.syncId);
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
                this.base = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.streamType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.bitRatePerSecond = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.durationUs = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.hasVideo = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.isStreaming = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.bitWidth = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.offloadBufferSize = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.usage = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.encapsulationMode = parcel.readByte();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.contentId = parcel.readInt();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.syncId = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("base: " + Objects.toString(this.base));
        stringJoiner.add("streamType: " + this.streamType);
        stringJoiner.add("bitRatePerSecond: " + this.bitRatePerSecond);
        stringJoiner.add("durationUs: " + this.durationUs);
        stringJoiner.add("hasVideo: " + this.hasVideo);
        stringJoiner.add("isStreaming: " + this.isStreaming);
        stringJoiner.add("bitWidth: " + this.bitWidth);
        stringJoiner.add("offloadBufferSize: " + this.offloadBufferSize);
        stringJoiner.add("usage: " + this.usage);
        stringJoiner.add("encapsulationMode: " + ((int) this.encapsulationMode));
        stringJoiner.add("contentId: " + this.contentId);
        stringJoiner.add("syncId: " + this.syncId);
        return "AudioOffloadInfo" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioOffloadInfo)) {
            return false;
        }
        AudioOffloadInfo audioOffloadInfo = (AudioOffloadInfo) obj;
        return Objects.deepEquals(this.base, audioOffloadInfo.base) && Objects.deepEquals(Integer.valueOf(this.streamType), Integer.valueOf(audioOffloadInfo.streamType)) && Objects.deepEquals(Integer.valueOf(this.bitRatePerSecond), Integer.valueOf(audioOffloadInfo.bitRatePerSecond)) && Objects.deepEquals(java.lang.Long.valueOf(this.durationUs), java.lang.Long.valueOf(audioOffloadInfo.durationUs)) && Objects.deepEquals(java.lang.Boolean.valueOf(this.hasVideo), java.lang.Boolean.valueOf(audioOffloadInfo.hasVideo)) && Objects.deepEquals(java.lang.Boolean.valueOf(this.isStreaming), java.lang.Boolean.valueOf(audioOffloadInfo.isStreaming)) && Objects.deepEquals(Integer.valueOf(this.bitWidth), Integer.valueOf(audioOffloadInfo.bitWidth)) && Objects.deepEquals(Integer.valueOf(this.offloadBufferSize), Integer.valueOf(audioOffloadInfo.offloadBufferSize)) && Objects.deepEquals(Integer.valueOf(this.usage), Integer.valueOf(audioOffloadInfo.usage)) && Objects.deepEquals(java.lang.Byte.valueOf(this.encapsulationMode), java.lang.Byte.valueOf(audioOffloadInfo.encapsulationMode)) && Objects.deepEquals(Integer.valueOf(this.contentId), Integer.valueOf(audioOffloadInfo.contentId)) && Objects.deepEquals(Integer.valueOf(this.syncId), Integer.valueOf(audioOffloadInfo.syncId));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.base, Integer.valueOf(this.streamType), Integer.valueOf(this.bitRatePerSecond), java.lang.Long.valueOf(this.durationUs), java.lang.Boolean.valueOf(this.hasVideo), java.lang.Boolean.valueOf(this.isStreaming), Integer.valueOf(this.bitWidth), Integer.valueOf(this.offloadBufferSize), Integer.valueOf(this.usage), java.lang.Byte.valueOf(this.encapsulationMode), Integer.valueOf(this.contentId), Integer.valueOf(this.syncId)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.base);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
