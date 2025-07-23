package android.content.pm;

import android.annotation.IdRes;
import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;

/* loaded from: classes.dex */
public final class Attribution implements Parcelable {
    public static final Parcelable.Creator<Attribution> CREATOR = new Parcelable.Creator<Attribution>() { // from class: android.content.pm.Attribution.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Attribution[] newArray(int i) {
            return new Attribution[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Attribution createFromParcel(Parcel parcel) {
            return new Attribution(parcel);
        }
    };
    private final int mLabel;
    private final String mTag;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Attribution(String str, int i) {
        this.mTag = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mLabel = i;
        AnnotationValidations.validate((Class<? extends Annotation>) IdRes.class, (Annotation) null, i);
    }

    public String getTag() {
        return this.mTag;
    }

    public int getLabel() {
        return this.mLabel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mTag);
        parcel.writeInt(this.mLabel);
    }

    Attribution(Parcel parcel) {
        String readString = parcel.readString();
        int readInt = parcel.readInt();
        this.mTag = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
        this.mLabel = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) IdRes.class, (Annotation) null, readInt);
    }
}
