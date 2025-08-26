package android.view;

import android.annotation.NonNull;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class PrivacyIndicatorBounds implements Parcelable {
    public static final Parcelable.Creator<PrivacyIndicatorBounds> CREATOR = new Parcelable.Creator<PrivacyIndicatorBounds>() { // from class: android.view.PrivacyIndicatorBounds.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrivacyIndicatorBounds[] newArray(int i) {
            return new PrivacyIndicatorBounds[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrivacyIndicatorBounds createFromParcel(Parcel parcel) {
            return new PrivacyIndicatorBounds(parcel);
        }
    };
    private final int mRotation;
    private final Rect[] mStaticBounds;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrivacyIndicatorBounds() {
        this.mStaticBounds = new Rect[4];
        this.mRotation = 0;
    }

    public PrivacyIndicatorBounds(Rect[] rectArr, int i) {
        this.mStaticBounds = rectArr;
        this.mRotation = i;
    }

    public PrivacyIndicatorBounds updateStaticBounds(Rect[] rectArr) {
        return new PrivacyIndicatorBounds(rectArr, this.mRotation);
    }

    public PrivacyIndicatorBounds updateBoundsForRotation(Rect rect, int i) {
        Rect[] rectArr = this.mStaticBounds;
        if (i >= rectArr.length || i < 0) {
            return this;
        }
        Rect[] rectArr2 = (Rect[]) ArrayUtils.cloneOrNull(rectArr);
        rectArr2[i] = rect;
        return updateStaticBounds(rectArr2);
    }

    public PrivacyIndicatorBounds inset(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return this;
        }
        Rect[] rectArr = new Rect[this.mStaticBounds.length];
        int i5 = 0;
        while (true) {
            Rect[] rectArr2 = this.mStaticBounds;
            if (i5 < rectArr2.length) {
                rectArr[i5] = insetRect(rectArr2[i5], i, i2, i3, i4);
                i5++;
            } else {
                return updateStaticBounds(rectArr);
            }
        }
    }

    private static Rect insetRect(Rect rect, int i, int i2, int i3, int i4) {
        if (rect == null) {
            return null;
        }
        int iMax = Math.max(0, rect.left - i);
        int iMax2 = Math.max(0, rect.top - i2);
        return new Rect(iMax, iMax2, Math.max(iMax, rect.right - i3), Math.max(iMax2, rect.bottom - i4));
    }

    public PrivacyIndicatorBounds rotate(int i) {
        return i == 0 ? this : new PrivacyIndicatorBounds(this.mStaticBounds, i);
    }

    public PrivacyIndicatorBounds scale(float f) {
        if (f == 1.0f) {
            return this;
        }
        Rect[] rectArr = new Rect[this.mStaticBounds.length];
        int i = 0;
        while (true) {
            Rect[] rectArr2 = this.mStaticBounds;
            if (i < rectArr2.length) {
                rectArr[i] = scaleRect(rectArr2[i], f);
                i++;
            } else {
                return new PrivacyIndicatorBounds(rectArr, this.mRotation);
            }
        }
    }

    private static Rect scaleRect(Rect rect, float f) {
        if (rect == null) {
            return null;
        }
        Rect rect2 = new Rect(rect);
        rect2.scale(f);
        return rect2;
    }

    public Rect getStaticPrivacyIndicatorBounds() {
        return this.mStaticBounds[this.mRotation];
    }

    public String toString() {
        return "PrivacyIndicatorBounds {static bounds=" + getStaticPrivacyIndicatorBounds() + " rotation=" + this.mRotation + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PrivacyIndicatorBounds privacyIndicatorBounds = (PrivacyIndicatorBounds) obj;
            if (Arrays.equals(this.mStaticBounds, privacyIndicatorBounds.mStaticBounds) && this.mRotation == privacyIndicatorBounds.mRotation) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Arrays.hashCode(this.mStaticBounds) + 31) * 31) + this.mRotation;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.mStaticBounds, i);
        parcel.writeInt(this.mRotation);
    }

    protected PrivacyIndicatorBounds(Parcel parcel) {
        Rect[] rectArr = (Rect[]) parcel.createTypedArray(Rect.CREATOR);
        int i = parcel.readInt();
        this.mStaticBounds = rectArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) rectArr);
        this.mRotation = i;
    }
}
