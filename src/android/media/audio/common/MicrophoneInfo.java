package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class MicrophoneInfo implements Parcelable {
    public static final Parcelable.Creator<MicrophoneInfo> CREATOR = new Parcelable.Creator<MicrophoneInfo>() { // from class: android.media.audio.common.MicrophoneInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MicrophoneInfo createFromParcel(Parcel parcel) {
            MicrophoneInfo microphoneInfo = new MicrophoneInfo();
            microphoneInfo.readFromParcel(parcel);
            return microphoneInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MicrophoneInfo[] newArray(int i) {
            return new MicrophoneInfo[i];
        }
    };
    public static final int GROUP_UNKNOWN = -1;
    public static final int INDEX_IN_THE_GROUP_UNKNOWN = -1;
    public AudioDevice device;
    public FrequencyResponsePoint[] frequencyResponse;
    public String id;
    public Coordinate orientation;
    public Coordinate position;
    public Sensitivity sensitivity;
    public int location = 0;
    public int group = -1;
    public int indexInTheGroup = -1;
    public int directionality = 0;

    public @interface Directionality {
        public static final int BI_DIRECTIONAL = 2;
        public static final int CARDIOID = 3;
        public static final int HYPER_CARDIOID = 4;
        public static final int OMNI = 1;
        public static final int SUPER_CARDIOID = 5;
        public static final int UNKNOWN = 0;
    }

    public @interface Location {
        public static final int MAINBODY = 1;
        public static final int MAINBODY_MOVABLE = 2;
        public static final int PERIPHERAL = 3;
        public static final int UNKNOWN = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.id);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.location);
        parcel.writeInt(this.group);
        parcel.writeInt(this.indexInTheGroup);
        parcel.writeTypedObject(this.sensitivity, i);
        parcel.writeInt(this.directionality);
        parcel.writeTypedArray(this.frequencyResponse, i);
        parcel.writeTypedObject(this.position, i);
        parcel.writeTypedObject(this.orientation, i);
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
                this.id = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.device = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.location = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.group = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.indexInTheGroup = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.sensitivity = (Sensitivity) parcel.readTypedObject(Sensitivity.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.directionality = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.frequencyResponse = (FrequencyResponsePoint[]) parcel.createTypedArray(FrequencyResponsePoint.CREATOR);
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.position = (Coordinate) parcel.readTypedObject(Coordinate.CREATOR);
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.orientation = (Coordinate) parcel.readTypedObject(Coordinate.CREATOR);
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("id: " + Objects.toString(this.id));
        stringJoiner.add("device: " + Objects.toString(this.device));
        stringJoiner.add("location: " + this.location);
        stringJoiner.add("group: " + this.group);
        stringJoiner.add("indexInTheGroup: " + this.indexInTheGroup);
        stringJoiner.add("sensitivity: " + Objects.toString(this.sensitivity));
        stringJoiner.add("directionality: " + this.directionality);
        stringJoiner.add("frequencyResponse: " + Arrays.toString(this.frequencyResponse));
        stringJoiner.add("position: " + Objects.toString(this.position));
        stringJoiner.add("orientation: " + Objects.toString(this.orientation));
        return "MicrophoneInfo" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MicrophoneInfo)) {
            return false;
        }
        MicrophoneInfo microphoneInfo = (MicrophoneInfo) obj;
        return Objects.deepEquals(this.id, microphoneInfo.id) && Objects.deepEquals(this.device, microphoneInfo.device) && Objects.deepEquals(Integer.valueOf(this.location), Integer.valueOf(microphoneInfo.location)) && Objects.deepEquals(Integer.valueOf(this.group), Integer.valueOf(microphoneInfo.group)) && Objects.deepEquals(Integer.valueOf(this.indexInTheGroup), Integer.valueOf(microphoneInfo.indexInTheGroup)) && Objects.deepEquals(this.sensitivity, microphoneInfo.sensitivity) && Objects.deepEquals(Integer.valueOf(this.directionality), Integer.valueOf(microphoneInfo.directionality)) && Objects.deepEquals(this.frequencyResponse, microphoneInfo.frequencyResponse) && Objects.deepEquals(this.position, microphoneInfo.position) && Objects.deepEquals(this.orientation, microphoneInfo.orientation);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.id, this.device, Integer.valueOf(this.location), Integer.valueOf(this.group), Integer.valueOf(this.indexInTheGroup), this.sensitivity, Integer.valueOf(this.directionality), this.frequencyResponse, this.position, this.orientation).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.orientation) | describeContents(this.device) | describeContents(this.sensitivity) | describeContents(this.frequencyResponse) | describeContents(this.position);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static class Sensitivity implements Parcelable {
        public static final Parcelable.Creator<Sensitivity> CREATOR = new Parcelable.Creator<Sensitivity>() { // from class: android.media.audio.common.MicrophoneInfo.Sensitivity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Sensitivity createFromParcel(Parcel parcel) {
                Sensitivity sensitivity = new Sensitivity();
                sensitivity.readFromParcel(parcel);
                return sensitivity;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Sensitivity[] newArray(int i) {
                return new Sensitivity[i];
            }
        };
        public float leveldBFS = 0.0f;
        public float maxSpldB = 0.0f;
        public float minSpldB = 0.0f;

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
            parcel.writeFloat(this.leveldBFS);
            parcel.writeFloat(this.maxSpldB);
            parcel.writeFloat(this.minSpldB);
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
                    this.leveldBFS = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxSpldB = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.minSpldB = parcel.readFloat();
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

    public static class FrequencyResponsePoint implements Parcelable {
        public static final Parcelable.Creator<FrequencyResponsePoint> CREATOR = new Parcelable.Creator<FrequencyResponsePoint>() { // from class: android.media.audio.common.MicrophoneInfo.FrequencyResponsePoint.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FrequencyResponsePoint createFromParcel(Parcel parcel) {
                FrequencyResponsePoint frequencyResponsePoint = new FrequencyResponsePoint();
                frequencyResponsePoint.readFromParcel(parcel);
                return frequencyResponsePoint;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FrequencyResponsePoint[] newArray(int i) {
                return new FrequencyResponsePoint[i];
            }
        };
        public float frequencyHz = 0.0f;
        public float leveldB = 0.0f;

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
            parcel.writeFloat(this.frequencyHz);
            parcel.writeFloat(this.leveldB);
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
                    this.frequencyHz = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.leveldB = parcel.readFloat();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
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

    public static class Coordinate implements Parcelable {
        public static final Parcelable.Creator<Coordinate> CREATOR = new Parcelable.Creator<Coordinate>() { // from class: android.media.audio.common.MicrophoneInfo.Coordinate.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Coordinate createFromParcel(Parcel parcel) {
                Coordinate coordinate = new Coordinate();
                coordinate.readFromParcel(parcel);
                return coordinate;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Coordinate[] newArray(int i) {
                return new Coordinate[i];
            }
        };
        public float x = 0.0f;
        public float y = 0.0f;
        public float z = 0.0f;

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
            parcel.writeFloat(this.x);
            parcel.writeFloat(this.y);
            parcel.writeFloat(this.z);
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
                    this.x = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.y = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.z = parcel.readFloat();
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
}
