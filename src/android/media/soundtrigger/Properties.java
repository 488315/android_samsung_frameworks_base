package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class Properties implements Parcelable {
    public static final Parcelable.Creator<Properties> CREATOR = new Parcelable.Creator<Properties>() { // from class: android.media.soundtrigger.Properties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Properties createFromParcel(Parcel parcel) {
            Properties properties = new Properties();
            properties.readFromParcel(parcel);
            return properties;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Properties[] newArray(int i) {
            return new Properties[i];
        }
    };
    public String description;
    public String implementor;
    public String supportedModelArch;
    public String uuid;
    public int version = 0;
    public int maxSoundModels = 0;
    public int maxKeyPhrases = 0;
    public int maxUsers = 0;
    public int recognitionModes = 0;
    public boolean captureTransition = false;
    public int maxBufferMs = 0;
    public boolean concurrentCapture = false;
    public boolean triggerInEvent = false;
    public int powerConsumptionMw = 0;
    public int audioCapabilities = 0;

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.implementor);
        parcel.writeString(this.description);
        parcel.writeInt(this.version);
        parcel.writeString(this.uuid);
        parcel.writeString(this.supportedModelArch);
        parcel.writeInt(this.maxSoundModels);
        parcel.writeInt(this.maxKeyPhrases);
        parcel.writeInt(this.maxUsers);
        parcel.writeInt(this.recognitionModes);
        parcel.writeBoolean(this.captureTransition);
        parcel.writeInt(this.maxBufferMs);
        parcel.writeBoolean(this.concurrentCapture);
        parcel.writeBoolean(this.triggerInEvent);
        parcel.writeInt(this.powerConsumptionMw);
        parcel.writeInt(this.audioCapabilities);
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
                this.implementor = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.description = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.version = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.uuid = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.supportedModelArch = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.maxSoundModels = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.maxKeyPhrases = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.maxUsers = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.recognitionModes = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.captureTransition = parcel.readBoolean();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.maxBufferMs = parcel.readInt();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.concurrentCapture = parcel.readBoolean();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.triggerInEvent = parcel.readBoolean();
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    this.powerConsumptionMw = parcel.readInt();
                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                        this.audioCapabilities = parcel.readInt();
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
        stringJoiner.add("implementor: " + Objects.toString(this.implementor));
        stringJoiner.add("description: " + Objects.toString(this.description));
        stringJoiner.add("version: " + this.version);
        stringJoiner.add("uuid: " + Objects.toString(this.uuid));
        stringJoiner.add("supportedModelArch: " + Objects.toString(this.supportedModelArch));
        stringJoiner.add("maxSoundModels: " + this.maxSoundModels);
        stringJoiner.add("maxKeyPhrases: " + this.maxKeyPhrases);
        stringJoiner.add("maxUsers: " + this.maxUsers);
        stringJoiner.add("recognitionModes: " + this.recognitionModes);
        stringJoiner.add("captureTransition: " + this.captureTransition);
        stringJoiner.add("maxBufferMs: " + this.maxBufferMs);
        stringJoiner.add("concurrentCapture: " + this.concurrentCapture);
        stringJoiner.add("triggerInEvent: " + this.triggerInEvent);
        stringJoiner.add("powerConsumptionMw: " + this.powerConsumptionMw);
        stringJoiner.add("audioCapabilities: " + this.audioCapabilities);
        return "Properties" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Properties)) {
            return false;
        }
        Properties properties = (Properties) obj;
        return Objects.deepEquals(this.implementor, properties.implementor) && Objects.deepEquals(this.description, properties.description) && Objects.deepEquals(Integer.valueOf(this.version), Integer.valueOf(properties.version)) && Objects.deepEquals(this.uuid, properties.uuid) && Objects.deepEquals(this.supportedModelArch, properties.supportedModelArch) && Objects.deepEquals(Integer.valueOf(this.maxSoundModels), Integer.valueOf(properties.maxSoundModels)) && Objects.deepEquals(Integer.valueOf(this.maxKeyPhrases), Integer.valueOf(properties.maxKeyPhrases)) && Objects.deepEquals(Integer.valueOf(this.maxUsers), Integer.valueOf(properties.maxUsers)) && Objects.deepEquals(Integer.valueOf(this.recognitionModes), Integer.valueOf(properties.recognitionModes)) && Objects.deepEquals(Boolean.valueOf(this.captureTransition), Boolean.valueOf(properties.captureTransition)) && Objects.deepEquals(Integer.valueOf(this.maxBufferMs), Integer.valueOf(properties.maxBufferMs)) && Objects.deepEquals(Boolean.valueOf(this.concurrentCapture), Boolean.valueOf(properties.concurrentCapture)) && Objects.deepEquals(Boolean.valueOf(this.triggerInEvent), Boolean.valueOf(properties.triggerInEvent)) && Objects.deepEquals(Integer.valueOf(this.powerConsumptionMw), Integer.valueOf(properties.powerConsumptionMw)) && Objects.deepEquals(Integer.valueOf(this.audioCapabilities), Integer.valueOf(properties.audioCapabilities));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.implementor, this.description, Integer.valueOf(this.version), this.uuid, this.supportedModelArch, Integer.valueOf(this.maxSoundModels), Integer.valueOf(this.maxKeyPhrases), Integer.valueOf(this.maxUsers), Integer.valueOf(this.recognitionModes), Boolean.valueOf(this.captureTransition), Integer.valueOf(this.maxBufferMs), Boolean.valueOf(this.concurrentCapture), Boolean.valueOf(this.triggerInEvent), Integer.valueOf(this.powerConsumptionMw), Integer.valueOf(this.audioCapabilities)).toArray());
    }
}
