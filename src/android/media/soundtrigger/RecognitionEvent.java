package android.media.soundtrigger;

import android.media.audio.common.AudioConfig;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class RecognitionEvent implements Parcelable {
    public static final Parcelable.Creator<RecognitionEvent> CREATOR = new Parcelable.Creator<RecognitionEvent>() { // from class: android.media.soundtrigger.RecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionEvent createFromParcel(Parcel parcel) {
            RecognitionEvent recognitionEvent = new RecognitionEvent();
            recognitionEvent.readFromParcel(parcel);
            return recognitionEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionEvent[] newArray(int i) {
            return new RecognitionEvent[i];
        }
    };
    public AudioConfig audioConfig;
    public byte[] data;
    public int status = -1;
    public int type = -1;
    public boolean captureAvailable = false;
    public int captureDelayMs = 0;
    public int capturePreambleMs = 0;
    public boolean triggerInData = false;
    public boolean recognitionStillActive = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeInt(this.type);
        parcel.writeBoolean(this.captureAvailable);
        parcel.writeInt(this.captureDelayMs);
        parcel.writeInt(this.capturePreambleMs);
        parcel.writeBoolean(this.triggerInData);
        parcel.writeTypedObject(this.audioConfig, i);
        parcel.writeByteArray(this.data);
        parcel.writeBoolean(this.recognitionStillActive);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.captureAvailable = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.captureDelayMs = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.capturePreambleMs = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.triggerInData = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.audioConfig = (AudioConfig) parcel.readTypedObject(AudioConfig.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.data = parcel.createByteArray();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.recognitionStillActive = parcel.readBoolean();
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("captureAvailable: " + this.captureAvailable);
        stringJoiner.add("captureDelayMs: " + this.captureDelayMs);
        stringJoiner.add("capturePreambleMs: " + this.capturePreambleMs);
        stringJoiner.add("triggerInData: " + this.triggerInData);
        stringJoiner.add("audioConfig: " + Objects.toString(this.audioConfig));
        stringJoiner.add("data: " + Arrays.toString(this.data));
        stringJoiner.add("recognitionStillActive: " + this.recognitionStillActive);
        return "RecognitionEvent" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RecognitionEvent)) {
            return false;
        }
        RecognitionEvent recognitionEvent = (RecognitionEvent) obj;
        return Objects.deepEquals(Integer.valueOf(this.status), Integer.valueOf(recognitionEvent.status)) && Objects.deepEquals(Integer.valueOf(this.type), Integer.valueOf(recognitionEvent.type)) && Objects.deepEquals(Boolean.valueOf(this.captureAvailable), Boolean.valueOf(recognitionEvent.captureAvailable)) && Objects.deepEquals(Integer.valueOf(this.captureDelayMs), Integer.valueOf(recognitionEvent.captureDelayMs)) && Objects.deepEquals(Integer.valueOf(this.capturePreambleMs), Integer.valueOf(recognitionEvent.capturePreambleMs)) && Objects.deepEquals(Boolean.valueOf(this.triggerInData), Boolean.valueOf(recognitionEvent.triggerInData)) && Objects.deepEquals(this.audioConfig, recognitionEvent.audioConfig) && Objects.deepEquals(this.data, recognitionEvent.data) && Objects.deepEquals(Boolean.valueOf(this.recognitionStillActive), Boolean.valueOf(recognitionEvent.recognitionStillActive));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.status), Integer.valueOf(this.type), Boolean.valueOf(this.captureAvailable), Integer.valueOf(this.captureDelayMs), Integer.valueOf(this.capturePreambleMs), Boolean.valueOf(this.triggerInData), this.audioConfig, this.data, Boolean.valueOf(this.recognitionStillActive)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.audioConfig);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
