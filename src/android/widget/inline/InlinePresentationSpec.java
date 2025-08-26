package android.widget.inline;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.widget.InlinePresentationStyleUtils;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class InlinePresentationSpec implements Parcelable {
    public static final Parcelable.Creator<InlinePresentationSpec> CREATOR = new Parcelable.Creator<InlinePresentationSpec>() { // from class: android.widget.inline.InlinePresentationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlinePresentationSpec[] newArray(int i) {
            return new InlinePresentationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlinePresentationSpec createFromParcel(Parcel parcel) {
            return new InlinePresentationSpec(parcel);
        }
    };
    private final Size mMaxSize;
    private final Size mMinSize;
    private final Bundle mStyle;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle defaultStyle() {
        return Bundle.EMPTY;
    }

    private boolean styleEquals(Bundle bundle) {
        return InlinePresentationStyleUtils.bundleEquals(this.mStyle, bundle);
    }

    public void filterContentTypes() {
        InlinePresentationStyleUtils.filterContentTypes(this.mStyle);
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }
    }

    InlinePresentationSpec(Size size, Size size2, Bundle bundle) {
        this.mMinSize = size;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) size);
        this.mMaxSize = size2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) size2);
        this.mStyle = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
    }

    public Size getMinSize() {
        return this.mMinSize;
    }

    public Size getMaxSize() {
        return this.mMaxSize;
    }

    public Bundle getStyle() {
        return this.mStyle;
    }

    public String toString() {
        return "InlinePresentationSpec { minSize = " + this.mMinSize + ", maxSize = " + this.mMaxSize + ", style = " + this.mStyle + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InlinePresentationSpec inlinePresentationSpec = (InlinePresentationSpec) obj;
            if (Objects.equals(this.mMinSize, inlinePresentationSpec.mMinSize) && Objects.equals(this.mMaxSize, inlinePresentationSpec.mMaxSize) && styleEquals(inlinePresentationSpec.mStyle)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mMinSize) + 31) * 31) + Objects.hashCode(this.mMaxSize)) * 31) + Objects.hashCode(this.mStyle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSize(this.mMinSize);
        parcel.writeSize(this.mMaxSize);
        parcel.writeBundle(this.mStyle);
    }

    InlinePresentationSpec(Parcel parcel) {
        Size size = parcel.readSize();
        Size size2 = parcel.readSize();
        Bundle bundle = parcel.readBundle();
        this.mMinSize = size;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) size);
        this.mMaxSize = size2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) size2);
        this.mStyle = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private Size mMaxSize;
        private Size mMinSize;
        private Bundle mStyle;

        public Builder(Size size, Size size2) {
            this.mMinSize = size;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) size);
            this.mMaxSize = size2;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) size2);
        }

        public Builder setStyle(Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mStyle = bundle;
            return this;
        }

        public InlinePresentationSpec build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 8;
            this.mBuilderFieldsSet = j;
            if ((j & 4) == 0) {
                this.mStyle = InlinePresentationSpec.defaultStyle();
            }
            return new InlinePresentationSpec(this.mMinSize, this.mMaxSize, this.mStyle);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
