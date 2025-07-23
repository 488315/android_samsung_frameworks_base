package android.speech;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class RecognitionSupport implements Parcelable {
    public static final Parcelable.Creator<RecognitionSupport> CREATOR = new Parcelable.Creator<RecognitionSupport>() { // from class: android.speech.RecognitionSupport.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionSupport[] newArray(int i) {
            return new RecognitionSupport[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionSupport createFromParcel(Parcel parcel) {
            return new RecognitionSupport(parcel);
        }
    };
    private List<String> mInstalledOnDeviceLanguages;
    private List<String> mOnlineLanguages;
    private List<String> mPendingOnDeviceLanguages;
    private List<String> mSupportedOnDeviceLanguages;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RecognitionSupport(List<String> list, List<String> list2, List<String> list3, List<String> list4) {
        this.mInstalledOnDeviceLanguages = Collections.EMPTY_LIST;
        this.mPendingOnDeviceLanguages = Collections.EMPTY_LIST;
        this.mSupportedOnDeviceLanguages = Collections.EMPTY_LIST;
        this.mOnlineLanguages = Collections.EMPTY_LIST;
        this.mInstalledOnDeviceLanguages = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.mPendingOnDeviceLanguages = list2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list2);
        this.mSupportedOnDeviceLanguages = list3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list3);
        this.mOnlineLanguages = list4;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list4);
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
        return "RecognitionSupport { installedOnDeviceLanguages = " + this.mInstalledOnDeviceLanguages + ", pendingOnDeviceLanguages = " + this.mPendingOnDeviceLanguages + ", supportedOnDeviceLanguages = " + this.mSupportedOnDeviceLanguages + ", onlineLanguages = " + this.mOnlineLanguages + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RecognitionSupport recognitionSupport = (RecognitionSupport) obj;
            if (Objects.equals(this.mInstalledOnDeviceLanguages, recognitionSupport.mInstalledOnDeviceLanguages) && Objects.equals(this.mPendingOnDeviceLanguages, recognitionSupport.mPendingOnDeviceLanguages) && Objects.equals(this.mSupportedOnDeviceLanguages, recognitionSupport.mSupportedOnDeviceLanguages) && Objects.equals(this.mOnlineLanguages, recognitionSupport.mOnlineLanguages)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((Objects.hashCode(this.mInstalledOnDeviceLanguages) + 31) * 31) + Objects.hashCode(this.mPendingOnDeviceLanguages)) * 31) + Objects.hashCode(this.mSupportedOnDeviceLanguages)) * 31) + Objects.hashCode(this.mOnlineLanguages);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.mInstalledOnDeviceLanguages);
        parcel.writeStringList(this.mPendingOnDeviceLanguages);
        parcel.writeStringList(this.mSupportedOnDeviceLanguages);
        parcel.writeStringList(this.mOnlineLanguages);
    }

    RecognitionSupport(Parcel parcel) {
        this.mInstalledOnDeviceLanguages = Collections.EMPTY_LIST;
        this.mPendingOnDeviceLanguages = Collections.EMPTY_LIST;
        this.mSupportedOnDeviceLanguages = Collections.EMPTY_LIST;
        this.mOnlineLanguages = Collections.EMPTY_LIST;
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        parcel.readStringList(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        parcel.readStringList(arrayList3);
        ArrayList arrayList4 = new ArrayList();
        parcel.readStringList(arrayList4);
        this.mInstalledOnDeviceLanguages = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mPendingOnDeviceLanguages = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList2);
        this.mSupportedOnDeviceLanguages = arrayList3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList3);
        this.mOnlineLanguages = arrayList4;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList4);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private List<String> mInstalledOnDeviceLanguages;
        private List<String> mOnlineLanguages;
        private List<String> mPendingOnDeviceLanguages;
        private List<String> mSupportedOnDeviceLanguages;

        public Builder setInstalledOnDeviceLanguages(List<String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mInstalledOnDeviceLanguages = list;
            return this;
        }

        public Builder addInstalledOnDeviceLanguage(String str) {
            if (this.mInstalledOnDeviceLanguages == null) {
                setInstalledOnDeviceLanguages(new ArrayList());
            }
            this.mInstalledOnDeviceLanguages.add(str);
            return this;
        }

        public Builder setPendingOnDeviceLanguages(List<String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mPendingOnDeviceLanguages = list;
            return this;
        }

        public Builder addPendingOnDeviceLanguage(String str) {
            if (this.mPendingOnDeviceLanguages == null) {
                setPendingOnDeviceLanguages(new ArrayList());
            }
            this.mPendingOnDeviceLanguages.add(str);
            return this;
        }

        public Builder setSupportedOnDeviceLanguages(List<String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mSupportedOnDeviceLanguages = list;
            return this;
        }

        public Builder addSupportedOnDeviceLanguage(String str) {
            if (this.mSupportedOnDeviceLanguages == null) {
                setSupportedOnDeviceLanguages(new ArrayList());
            }
            this.mSupportedOnDeviceLanguages.add(str);
            return this;
        }

        public Builder setOnlineLanguages(List<String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mOnlineLanguages = list;
            return this;
        }

        public Builder addOnlineLanguage(String str) {
            if (this.mOnlineLanguages == null) {
                setOnlineLanguages(new ArrayList());
            }
            this.mOnlineLanguages.add(str);
            return this;
        }

        public RecognitionSupport build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mInstalledOnDeviceLanguages = Collections.EMPTY_LIST;
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mPendingOnDeviceLanguages = Collections.EMPTY_LIST;
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mSupportedOnDeviceLanguages = Collections.EMPTY_LIST;
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mOnlineLanguages = Collections.EMPTY_LIST;
            }
            return new RecognitionSupport(this.mInstalledOnDeviceLanguages, this.mPendingOnDeviceLanguages, this.mSupportedOnDeviceLanguages, this.mOnlineLanguages);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
