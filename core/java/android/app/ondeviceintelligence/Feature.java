package android.app.ondeviceintelligence;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

import com.android.internal.util.AnnotationValidations;

import java.util.Objects;

@SystemApi
public final class Feature implements Parcelable {
    public static final Parcelable.Creator<Feature> CREATOR =
            new Parcelable.Creator<
                    Feature>() { // from class: android.app.ondeviceintelligence.Feature.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Feature[] newArray(int size) {
                    return new Feature[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Feature createFromParcel(Parcel in) {
                    return new Feature(in);
                }
            };
    private final PersistableBundle mFeatureParams;
    private final int mId;
    private final String mModelName;
    private final String mName;
    private final int mType;
    private final int mVariant;

    Feature(
            int id,
            String name,
            String modelName,
            int type,
            int variant,
            PersistableBundle featureParams) {
        this.mId = id;
        this.mName = name;
        this.mModelName = modelName;
        this.mType = type;
        this.mVariant = variant;
        this.mFeatureParams = featureParams;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFeatureParams);
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public String getModelName() {
        return this.mModelName;
    }

    public int getType() {
        return this.mType;
    }

    public int getVariant() {
        return this.mVariant;
    }

    public PersistableBundle getFeatureParams() {
        return this.mFeatureParams;
    }

    public String toString() {
        return "Feature { id = "
                + this.mId
                + ", name = "
                + this.mName
                + ", modelName = "
                + this.mModelName
                + ", type = "
                + this.mType
                + ", variant = "
                + this.mVariant
                + ", featureParams = "
                + this.mFeatureParams
                + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feature that = (Feature) o;
        if (this.mId == that.mId
                && Objects.equals(this.mName, that.mName)
                && Objects.equals(this.mModelName, that.mModelName)
                && this.mType == that.mType
                && this.mVariant == that.mVariant
                && Objects.equals(this.mFeatureParams, that.mFeatureParams)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mId;
        return (((((((((_hash * 31) + Objects.hashCode(this.mName)) * 31)
                                                                + Objects.hashCode(this.mModelName))
                                                        * 31)
                                                + this.mType)
                                        * 31)
                                + this.mVariant)
                        * 31)
                + Objects.hashCode(this.mFeatureParams);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mName != null ? (byte) (0 | 2) : (byte) 0;
        if (this.mModelName != null) {
            flg = (byte) (flg | 4);
        }
        dest.writeByte(flg);
        dest.writeInt(this.mId);
        if (this.mName != null) {
            dest.writeString(this.mName);
        }
        if (this.mModelName != null) {
            dest.writeString(this.mModelName);
        }
        dest.writeInt(this.mType);
        dest.writeInt(this.mVariant);
        dest.writeTypedObject(this.mFeatureParams, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Feature(Parcel in) {
        byte flg = in.readByte();
        int id = in.readInt();
        String name = (flg & 2) == 0 ? null : in.readString();
        String modelName = (flg & 4) == 0 ? null : in.readString();
        int type = in.readInt();
        int variant = in.readInt();
        PersistableBundle featureParams =
                (PersistableBundle) in.readTypedObject(PersistableBundle.CREATOR);
        this.mId = id;
        this.mName = name;
        this.mModelName = modelName;
        this.mType = type;
        this.mVariant = variant;
        this.mFeatureParams = featureParams;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFeatureParams);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private PersistableBundle mFeatureParams = new PersistableBundle();
        private int mId;
        private String mModelName;
        private String mName;
        private int mType;
        private int mVariant;

        public Builder(int id) {
            this.mId = id;
        }

        public Builder setName(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mName = value;
            return this;
        }

        public Builder setModelName(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mModelName = value;
            return this;
        }

        public Builder setType(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mType = value;
            return this;
        }

        public Builder setVariant(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mVariant = value;
            return this;
        }

        public Builder setFeatureParams(PersistableBundle value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mFeatureParams = value;
            return this;
        }

        public Feature build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            Feature o =
                    new Feature(
                            this.mId,
                            this.mName,
                            this.mModelName,
                            this.mType,
                            this.mVariant,
                            this.mFeatureParams);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
