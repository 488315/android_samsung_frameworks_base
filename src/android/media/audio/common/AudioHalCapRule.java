package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalCapRule implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapRule> CREATOR = new Parcelable.Creator<AudioHalCapRule>() { // from class: android.media.audio.common.AudioHalCapRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapRule createFromParcel(Parcel parcel) {
            AudioHalCapRule audioHalCapRule = new AudioHalCapRule();
            audioHalCapRule.readFromParcel(parcel);
            return audioHalCapRule;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapRule[] newArray(int i) {
            return new AudioHalCapRule[i];
        }
    };
    public byte compoundRule = 0;
    public CriterionRule[] criterionRules;
    public AudioHalCapRule[] nestedRules;

    public @interface CompoundRule {
        public static final byte ALL = 2;
        public static final byte ANY = 1;
        public static final byte INVALID = 0;
    }

    public @interface MatchingRule {
        public static final byte EXCLUDES = 3;
        public static final byte INCLUDES = 2;
        public static final byte INVALID = -1;
        public static final byte IS = 0;
        public static final byte IS_NOT = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.compoundRule);
        parcel.writeTypedArray(this.criterionRules, i);
        parcel.writeTypedArray(this.nestedRules, i);
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
                this.compoundRule = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.criterionRules = (CriterionRule[]) parcel.createTypedArray(CriterionRule.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.nestedRules = (AudioHalCapRule[]) parcel.createTypedArray(CREATOR);
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("compoundRule: " + ((int) this.compoundRule));
        stringJoiner.add("criterionRules: " + Arrays.toString(this.criterionRules));
        stringJoiner.add("nestedRules: " + Arrays.toString(this.nestedRules));
        return "AudioHalCapRule" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalCapRule)) {
            return false;
        }
        AudioHalCapRule audioHalCapRule = (AudioHalCapRule) obj;
        return Objects.deepEquals(java.lang.Byte.valueOf(this.compoundRule), java.lang.Byte.valueOf(audioHalCapRule.compoundRule)) && Objects.deepEquals(this.criterionRules, audioHalCapRule.criterionRules) && Objects.deepEquals(this.nestedRules, audioHalCapRule.nestedRules);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(java.lang.Byte.valueOf(this.compoundRule), this.criterionRules, this.nestedRules).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.nestedRules) | describeContents(this.criterionRules);
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

    public static class CriterionRule implements Parcelable {
        public static final Parcelable.Creator<CriterionRule> CREATOR = new Parcelable.Creator<CriterionRule>() { // from class: android.media.audio.common.AudioHalCapRule.CriterionRule.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CriterionRule createFromParcel(Parcel parcel) {
                CriterionRule criterionRule = new CriterionRule();
                criterionRule.readFromParcel(parcel);
                return criterionRule;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CriterionRule[] newArray(int i) {
                return new CriterionRule[i];
            }
        };
        public AudioHalCapCriterionV2 criterionAndValue;
        public byte matchingRule = -1;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeByte(this.matchingRule);
            parcel.writeTypedObject(this.criterionAndValue, i);
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
                    this.matchingRule = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.criterionAndValue = (AudioHalCapCriterionV2) parcel.readTypedObject(AudioHalCapCriterionV2.CREATOR);
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.criterionAndValue);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
