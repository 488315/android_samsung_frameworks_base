package android.speech;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RecognitionSupport implements Parcelable {
    public static final Parcelable.Creator<RecognitionSupport> CREATOR =
            new Parcelable.Creator<
                    RecognitionSupport>() { // from class: android.speech.RecognitionSupport.1
                @Override // android.os.Parcelable.Creator
                public RecognitionSupport[] newArray(int size) {
                    return new RecognitionSupport[size];
                }

                @Override // android.os.Parcelable.Creator
                public RecognitionSupport createFromParcel(Parcel in) {
                    return new RecognitionSupport(in);
                }
            };
    private List<String> mInstalledOnDeviceLanguages;
    private List<String> mOnlineLanguages;
    private List<String> mPendingOnDeviceLanguages;
    private List<String> mSupportedOnDeviceLanguages;

    RecognitionSupport(
            List<String> installedOnDeviceLanguages,
            List<String> pendingOnDeviceLanguages,
            List<String> supportedOnDeviceLanguages,
            List<String> onlineLanguages) {
        this.mInstalledOnDeviceLanguages = List.of();
        this.mPendingOnDeviceLanguages = List.of();
        this.mSupportedOnDeviceLanguages = List.of();
        this.mOnlineLanguages = List.of();
        this.mInstalledOnDeviceLanguages = installedOnDeviceLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mInstalledOnDeviceLanguages);
        this.mPendingOnDeviceLanguages = pendingOnDeviceLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mPendingOnDeviceLanguages);
        this.mSupportedOnDeviceLanguages = supportedOnDeviceLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mSupportedOnDeviceLanguages);
        this.mOnlineLanguages = onlineLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOnlineLanguages);
    }

    public List<String> getInstalledOnDeviceLanguages() {
        return this.mInstalledOnDeviceLanguages;
    }

    public List<String> getPendingOnDeviceLanguages() {
        return this.mPendingOnDeviceLanguages;
    }

    public List<String> getSupportedOnDeviceLanguages() {
        return this.mSupportedOnDeviceLanguages;
    }

    public List<String> getOnlineLanguages() {
        return this.mOnlineLanguages;
    }

    public String toString() {
        return "RecognitionSupport { installedOnDeviceLanguages = "
                + this.mInstalledOnDeviceLanguages
                + ", pendingOnDeviceLanguages = "
                + this.mPendingOnDeviceLanguages
                + ", supportedOnDeviceLanguages = "
                + this.mSupportedOnDeviceLanguages
                + ", onlineLanguages = "
                + this.mOnlineLanguages
                + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecognitionSupport that = (RecognitionSupport) o;
        if (Objects.equals(this.mInstalledOnDeviceLanguages, that.mInstalledOnDeviceLanguages)
                && Objects.equals(this.mPendingOnDeviceLanguages, that.mPendingOnDeviceLanguages)
                && Objects.equals(
                        this.mSupportedOnDeviceLanguages, that.mSupportedOnDeviceLanguages)
                && Objects.equals(this.mOnlineLanguages, that.mOnlineLanguages)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mInstalledOnDeviceLanguages);
        return (((((_hash * 31) + Objects.hashCode(this.mPendingOnDeviceLanguages)) * 31)
                                + Objects.hashCode(this.mSupportedOnDeviceLanguages))
                        * 31)
                + Objects.hashCode(this.mOnlineLanguages);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.mInstalledOnDeviceLanguages);
        dest.writeStringList(this.mPendingOnDeviceLanguages);
        dest.writeStringList(this.mSupportedOnDeviceLanguages);
        dest.writeStringList(this.mOnlineLanguages);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RecognitionSupport(Parcel in) {
        this.mInstalledOnDeviceLanguages = List.of();
        this.mPendingOnDeviceLanguages = List.of();
        this.mSupportedOnDeviceLanguages = List.of();
        this.mOnlineLanguages = List.of();
        List<String> installedOnDeviceLanguages = new ArrayList<>();
        in.readStringList(installedOnDeviceLanguages);
        List<String> pendingOnDeviceLanguages = new ArrayList<>();
        in.readStringList(pendingOnDeviceLanguages);
        List<String> supportedOnDeviceLanguages = new ArrayList<>();
        in.readStringList(supportedOnDeviceLanguages);
        List<String> onlineLanguages = new ArrayList<>();
        in.readStringList(onlineLanguages);
        this.mInstalledOnDeviceLanguages = installedOnDeviceLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mInstalledOnDeviceLanguages);
        this.mPendingOnDeviceLanguages = pendingOnDeviceLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mPendingOnDeviceLanguages);
        this.mSupportedOnDeviceLanguages = supportedOnDeviceLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class,
                (NonNull) null,
                (Object) this.mSupportedOnDeviceLanguages);
        this.mOnlineLanguages = onlineLanguages;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOnlineLanguages);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private List<String> mInstalledOnDeviceLanguages;
        private List<String> mOnlineLanguages;
        private List<String> mPendingOnDeviceLanguages;
        private List<String> mSupportedOnDeviceLanguages;

        public Builder setInstalledOnDeviceLanguages(List<String> value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mInstalledOnDeviceLanguages = value;
            return this;
        }

        public Builder addInstalledOnDeviceLanguage(String value) {
            if (this.mInstalledOnDeviceLanguages == null) {
                setInstalledOnDeviceLanguages(new ArrayList());
            }
            this.mInstalledOnDeviceLanguages.add(value);
            return this;
        }

        public Builder setPendingOnDeviceLanguages(List<String> value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mPendingOnDeviceLanguages = value;
            return this;
        }

        public Builder addPendingOnDeviceLanguage(String value) {
            if (this.mPendingOnDeviceLanguages == null) {
                setPendingOnDeviceLanguages(new ArrayList());
            }
            this.mPendingOnDeviceLanguages.add(value);
            return this;
        }

        public Builder setSupportedOnDeviceLanguages(List<String> value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mSupportedOnDeviceLanguages = value;
            return this;
        }

        public Builder addSupportedOnDeviceLanguage(String value) {
            if (this.mSupportedOnDeviceLanguages == null) {
                setSupportedOnDeviceLanguages(new ArrayList());
            }
            this.mSupportedOnDeviceLanguages.add(value);
            return this;
        }

        public Builder setOnlineLanguages(List<String> value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mOnlineLanguages = value;
            return this;
        }

        public Builder addOnlineLanguage(String value) {
            if (this.mOnlineLanguages == null) {
                setOnlineLanguages(new ArrayList());
            }
            this.mOnlineLanguages.add(value);
            return this;
        }

        public RecognitionSupport build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mInstalledOnDeviceLanguages = List.of();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mPendingOnDeviceLanguages = List.of();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mSupportedOnDeviceLanguages = List.of();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mOnlineLanguages = List.of();
            }
            RecognitionSupport o =
                    new RecognitionSupport(
                            this.mInstalledOnDeviceLanguages,
                            this.mPendingOnDeviceLanguages,
                            this.mSupportedOnDeviceLanguages,
                            this.mOnlineLanguages);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
