package android.view.translation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes4.dex */
public final class UiTranslationSpec implements Parcelable {
    public static final Parcelable.Creator<UiTranslationSpec> CREATOR = new Parcelable.Creator<UiTranslationSpec>() { // from class: android.view.translation.UiTranslationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiTranslationSpec[] newArray(int i) {
            return new UiTranslationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiTranslationSpec createFromParcel(Parcel parcel) {
            return new UiTranslationSpec(parcel);
        }
    };
    private boolean mShouldPadContentForCompat;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean shouldPadContentForCompat() {
        return this.mShouldPadContentForCompat;
    }

    UiTranslationSpec(boolean z) {
        this.mShouldPadContentForCompat = z;
    }

    public String toString() {
        return "UiTranslationSpec { shouldPadContentForCompat = " + this.mShouldPadContentForCompat + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mShouldPadContentForCompat == ((UiTranslationSpec) obj).mShouldPadContentForCompat;
    }

    public int hashCode() {
        return 31 + Boolean.hashCode(this.mShouldPadContentForCompat);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mShouldPadContentForCompat ? (byte) 1 : (byte) 0);
    }

    UiTranslationSpec(Parcel parcel) {
        this.mShouldPadContentForCompat = false;
        this.mShouldPadContentForCompat = (parcel.readByte() & 1) != 0;
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private boolean mShouldPadContentForCompat;

        public Builder setShouldPadContentForCompat(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mShouldPadContentForCompat = z;
            return this;
        }

        public UiTranslationSpec build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 2;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mShouldPadContentForCompat = false;
            }
            return new UiTranslationSpec(this.mShouldPadContentForCompat);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
