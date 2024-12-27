package android.content.pm;

import android.annotation.IdRes;
import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;

public final class Attribution implements Parcelable {
    public static final Parcelable.Creator<Attribution> CREATOR =
            new Parcelable.Creator<Attribution>() { // from class: android.content.pm.Attribution.1
                @Override // android.os.Parcelable.Creator
                public Attribution[] newArray(int size) {
                    return new Attribution[size];
                }

                @Override // android.os.Parcelable.Creator
                public Attribution createFromParcel(Parcel in) {
                    return new Attribution(in);
                }
            };
    private final int mLabel;
    private final String mTag;

    public Attribution(String tag, int label) {
        this.mTag = tag;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTag);
        this.mLabel = label;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) IdRes.class, (Annotation) null, this.mLabel);
    }

    public String getTag() {
        return this.mTag;
    }

    public int getLabel() {
        return this.mLabel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTag);
        dest.writeInt(this.mLabel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Attribution(Parcel in) {
        String tag = in.readString();
        int label = in.readInt();
        this.mTag = tag;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mTag);
        this.mLabel = label;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) IdRes.class, (Annotation) null, this.mLabel);
    }

    @Deprecated
    private void __metadata() {}
}
