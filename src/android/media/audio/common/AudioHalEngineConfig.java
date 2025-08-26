package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalEngineConfig implements Parcelable {
    public static final Parcelable.Creator<AudioHalEngineConfig> CREATOR = new Parcelable.Creator<AudioHalEngineConfig>() { // from class: android.media.audio.common.AudioHalEngineConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalEngineConfig createFromParcel(Parcel parcel) {
            AudioHalEngineConfig audioHalEngineConfig = new AudioHalEngineConfig();
            audioHalEngineConfig.readFromParcel(parcel);
            return audioHalEngineConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalEngineConfig[] newArray(int i) {
            return new AudioHalEngineConfig[i];
        }
    };
    public CapSpecificConfig capSpecificConfig;
    public int defaultProductStrategyId = -1;
    public AudioHalProductStrategy[] productStrategies;
    public AudioHalVolumeGroup[] volumeGroups;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.defaultProductStrategyId);
        parcel.writeTypedArray(this.productStrategies, i);
        parcel.writeTypedArray(this.volumeGroups, i);
        parcel.writeTypedObject(this.capSpecificConfig, i);
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
                this.defaultProductStrategyId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.productStrategies = (AudioHalProductStrategy[]) parcel.createTypedArray(AudioHalProductStrategy.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.volumeGroups = (AudioHalVolumeGroup[]) parcel.createTypedArray(AudioHalVolumeGroup.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.capSpecificConfig = (CapSpecificConfig) parcel.readTypedObject(CapSpecificConfig.CREATOR);
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
        stringJoiner.add("defaultProductStrategyId: " + this.defaultProductStrategyId);
        stringJoiner.add("productStrategies: " + Arrays.toString(this.productStrategies));
        stringJoiner.add("volumeGroups: " + Arrays.toString(this.volumeGroups));
        stringJoiner.add("capSpecificConfig: " + Objects.toString(this.capSpecificConfig));
        return "AudioHalEngineConfig" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalEngineConfig)) {
            return false;
        }
        AudioHalEngineConfig audioHalEngineConfig = (AudioHalEngineConfig) obj;
        return Objects.deepEquals(Integer.valueOf(this.defaultProductStrategyId), Integer.valueOf(audioHalEngineConfig.defaultProductStrategyId)) && Objects.deepEquals(this.productStrategies, audioHalEngineConfig.productStrategies) && Objects.deepEquals(this.volumeGroups, audioHalEngineConfig.volumeGroups) && Objects.deepEquals(this.capSpecificConfig, audioHalEngineConfig.capSpecificConfig);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.defaultProductStrategyId), this.productStrategies, this.volumeGroups, this.capSpecificConfig).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.capSpecificConfig) | describeContents(this.productStrategies) | describeContents(this.volumeGroups);
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

    public static class CapSpecificConfig implements Parcelable {
        public static final Parcelable.Creator<CapSpecificConfig> CREATOR = new Parcelable.Creator<CapSpecificConfig>() { // from class: android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CapSpecificConfig createFromParcel(Parcel parcel) {
                CapSpecificConfig capSpecificConfig = new CapSpecificConfig();
                capSpecificConfig.readFromParcel(parcel);
                return capSpecificConfig;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CapSpecificConfig[] newArray(int i) {
                return new CapSpecificConfig[i];
            }
        };
        public AudioHalCapCriterion[] criteria;
        public AudioHalCapCriterionV2[] criteriaV2;
        public AudioHalCapCriterionType[] criterionTypes;
        public AudioHalCapDomain[] domains;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedArray(this.criteria, i);
            parcel.writeTypedArray(this.criterionTypes, i);
            parcel.writeTypedArray(this.criteriaV2, i);
            parcel.writeTypedArray(this.domains, i);
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
                    this.criteria = (AudioHalCapCriterion[]) parcel.createTypedArray(AudioHalCapCriterion.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.criterionTypes = (AudioHalCapCriterionType[]) parcel.createTypedArray(AudioHalCapCriterionType.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.criteriaV2 = (AudioHalCapCriterionV2[]) parcel.createTypedArray(AudioHalCapCriterionV2.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.domains = (AudioHalCapDomain[]) parcel.createTypedArray(AudioHalCapDomain.CREATOR);
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
            return describeContents(this.domains) | describeContents(this.criteria) | describeContents(this.criterionTypes) | describeContents(this.criteriaV2);
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
    }
}
