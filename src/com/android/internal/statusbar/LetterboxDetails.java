package com.android.internal.statusbar;

import android.annotation.NonNull;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes4.dex */
public class LetterboxDetails implements Parcelable {
    public static final Parcelable.Creator<LetterboxDetails> CREATOR = new Parcelable.Creator<LetterboxDetails>() { // from class: com.android.internal.statusbar.LetterboxDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LetterboxDetails[] newArray(int i) {
            return new LetterboxDetails[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LetterboxDetails createFromParcel(Parcel parcel) {
            return new LetterboxDetails(parcel);
        }
    };
    private final int mAppAppearance;
    private final Rect mLetterboxFullBounds;
    private final Rect mLetterboxInnerBounds;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Rect getLetterboxInnerBounds() {
        return this.mLetterboxInnerBounds;
    }

    public Rect getLetterboxFullBounds() {
        return this.mLetterboxFullBounds;
    }

    public int getAppAppearance() {
        return this.mAppAppearance;
    }

    public String appAppearanceToString() {
        return ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.mAppAppearance);
    }

    public LetterboxDetails(Rect rect, Rect rect2, int i) {
        this.mLetterboxInnerBounds = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
        this.mLetterboxFullBounds = rect2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect2);
        this.mAppAppearance = i;
    }

    public String toString() {
        return "LetterboxDetails { letterboxInnerBounds = " + this.mLetterboxInnerBounds + ", letterboxFullBounds = " + this.mLetterboxFullBounds + ", appAppearance = " + appAppearanceToString() + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            LetterboxDetails letterboxDetails = (LetterboxDetails) obj;
            if (Objects.equals(this.mLetterboxInnerBounds, letterboxDetails.mLetterboxInnerBounds) && Objects.equals(this.mLetterboxFullBounds, letterboxDetails.mLetterboxFullBounds) && this.mAppAppearance == letterboxDetails.mAppAppearance) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mLetterboxInnerBounds) + 31) * 31) + Objects.hashCode(this.mLetterboxFullBounds)) * 31) + this.mAppAppearance;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mLetterboxInnerBounds, i);
        parcel.writeTypedObject(this.mLetterboxFullBounds, i);
        parcel.writeInt(this.mAppAppearance);
    }

    protected LetterboxDetails(Parcel parcel) {
        Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
        int readInt = parcel.readInt();
        this.mLetterboxInnerBounds = rect;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect);
        this.mLetterboxFullBounds = rect2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rect2);
        this.mAppAppearance = readInt;
    }
}
