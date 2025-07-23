package android.window;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class PictureInPictureSurfaceTransaction implements Parcelable {
    public static final Parcelable.Creator<PictureInPictureSurfaceTransaction> CREATOR = new Parcelable.Creator<PictureInPictureSurfaceTransaction>() { // from class: android.window.PictureInPictureSurfaceTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureInPictureSurfaceTransaction createFromParcel(Parcel parcel) {
            return new PictureInPictureSurfaceTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureInPictureSurfaceTransaction[] newArray(int i) {
            return new PictureInPictureSurfaceTransaction[i];
        }
    };
    private static final float NOT_SET = -1.0f;
    public final float mAlpha;
    public final float mCornerRadius;
    public final float[] mFloat9;
    public final PointF mPosition;
    public final float mRotation;
    public final float mShadowRadius;
    private boolean mShouldDisableCanAffectSystemUiFlags;
    private final Rect mWindowCrop;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PictureInPictureSurfaceTransaction(Parcel parcel) {
        this.mAlpha = parcel.readFloat();
        this.mPosition = (PointF) parcel.readTypedObject(PointF.CREATOR);
        float[] fArr = new float[9];
        this.mFloat9 = fArr;
        parcel.readFloatArray(fArr);
        this.mRotation = parcel.readFloat();
        this.mCornerRadius = parcel.readFloat();
        this.mShadowRadius = parcel.readFloat();
        this.mWindowCrop = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mShouldDisableCanAffectSystemUiFlags = parcel.readBoolean();
    }

    private PictureInPictureSurfaceTransaction(float f, PointF pointF, float[] fArr, float f2, float f3, float f4, Rect rect) {
        this.mAlpha = f;
        this.mPosition = pointF;
        if (fArr == null) {
            float[] fArr2 = new float[9];
            this.mFloat9 = fArr2;
            Matrix.IDENTITY_MATRIX.getValues(fArr2);
            this.mRotation = 0.0f;
        } else {
            this.mFloat9 = Arrays.copyOf(fArr, 9);
            this.mRotation = f2;
        }
        this.mCornerRadius = f3;
        this.mShadowRadius = f4;
        this.mWindowCrop = rect == null ? null : new Rect(rect);
    }

    public PictureInPictureSurfaceTransaction(PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction) {
        this(pictureInPictureSurfaceTransaction.mAlpha, pictureInPictureSurfaceTransaction.mPosition, pictureInPictureSurfaceTransaction.mFloat9, pictureInPictureSurfaceTransaction.mRotation, pictureInPictureSurfaceTransaction.mCornerRadius, pictureInPictureSurfaceTransaction.mShadowRadius, pictureInPictureSurfaceTransaction.mWindowCrop);
        this.mShouldDisableCanAffectSystemUiFlags = pictureInPictureSurfaceTransaction.mShouldDisableCanAffectSystemUiFlags;
    }

    public Matrix getMatrix() {
        Matrix matrix = new Matrix();
        matrix.setValues(this.mFloat9);
        return matrix;
    }

    public boolean hasCornerRadiusSet() {
        return this.mCornerRadius > 0.0f;
    }

    public boolean hasShadowRadiusSet() {
        return this.mShadowRadius > 0.0f;
    }

    public void setShouldDisableCanAffectSystemUiFlags(boolean z) {
        this.mShouldDisableCanAffectSystemUiFlags = z;
    }

    public boolean getShouldDisableCanAffectSystemUiFlags() {
        return this.mShouldDisableCanAffectSystemUiFlags;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PictureInPictureSurfaceTransaction)) {
            return false;
        }
        PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = (PictureInPictureSurfaceTransaction) obj;
        return Objects.equals(Float.valueOf(this.mAlpha), Float.valueOf(pictureInPictureSurfaceTransaction.mAlpha)) && Objects.equals(this.mPosition, pictureInPictureSurfaceTransaction.mPosition) && Arrays.equals(this.mFloat9, pictureInPictureSurfaceTransaction.mFloat9) && Objects.equals(Float.valueOf(this.mRotation), Float.valueOf(pictureInPictureSurfaceTransaction.mRotation)) && Objects.equals(Float.valueOf(this.mCornerRadius), Float.valueOf(pictureInPictureSurfaceTransaction.mCornerRadius)) && Objects.equals(Float.valueOf(this.mShadowRadius), Float.valueOf(pictureInPictureSurfaceTransaction.mShadowRadius)) && Objects.equals(this.mWindowCrop, pictureInPictureSurfaceTransaction.mWindowCrop) && this.mShouldDisableCanAffectSystemUiFlags == pictureInPictureSurfaceTransaction.mShouldDisableCanAffectSystemUiFlags;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mAlpha), this.mPosition, Integer.valueOf(Arrays.hashCode(this.mFloat9)), Float.valueOf(this.mRotation), Float.valueOf(this.mCornerRadius), Float.valueOf(this.mShadowRadius), this.mWindowCrop, Boolean.valueOf(this.mShouldDisableCanAffectSystemUiFlags));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mAlpha);
        parcel.writeTypedObject(this.mPosition, 0);
        parcel.writeFloatArray(this.mFloat9);
        parcel.writeFloat(this.mRotation);
        parcel.writeFloat(this.mCornerRadius);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeTypedObject(this.mWindowCrop, 0);
        parcel.writeBoolean(this.mShouldDisableCanAffectSystemUiFlags);
    }

    public String toString() {
        return "PictureInPictureSurfaceTransaction( alpha=" + this.mAlpha + " position=" + this.mPosition + " matrix=" + getMatrix().toShortString() + " rotation=" + this.mRotation + " cornerRadius=" + this.mCornerRadius + " shadowRadius=" + this.mShadowRadius + " crop=" + this.mWindowCrop + " shouldDisableCanAffectSystemUiFlags" + this.mShouldDisableCanAffectSystemUiFlags + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static void apply(PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
        transaction.setMatrix(surfaceControl, pictureInPictureSurfaceTransaction.getMatrix(), new float[9]);
        PointF pointF = pictureInPictureSurfaceTransaction.mPosition;
        if (pointF != null) {
            transaction.setPosition(surfaceControl, pointF.x, pictureInPictureSurfaceTransaction.mPosition.y);
        }
        Rect rect = pictureInPictureSurfaceTransaction.mWindowCrop;
        if (rect != null) {
            transaction.setWindowCrop(surfaceControl, rect);
        }
        if (pictureInPictureSurfaceTransaction.hasCornerRadiusSet()) {
            transaction.setCornerRadius(surfaceControl, CoreRune.MW_PIP_DISABLE_ROUNDED_CORNER ? 0.0f : pictureInPictureSurfaceTransaction.mCornerRadius);
        }
        if (pictureInPictureSurfaceTransaction.hasShadowRadiusSet()) {
            transaction.setShadowRadius(surfaceControl, CoreRune.MW_PIP_DISABLE_ROUNDED_CORNER ? 0.0f : pictureInPictureSurfaceTransaction.mShadowRadius);
        }
        float f = pictureInPictureSurfaceTransaction.mAlpha;
        if (f != -1.0f) {
            transaction.setAlpha(surfaceControl, f);
        }
    }

    public static class Builder {
        private float[] mFloat9;
        private PointF mPosition;
        private float mRotation;
        private Rect mWindowCrop;
        private float mAlpha = -1.0f;
        private float mCornerRadius = -1.0f;
        private float mShadowRadius = -1.0f;

        public Builder setAlpha(float f) {
            this.mAlpha = f;
            return this;
        }

        public Builder setPosition(float f, float f2) {
            this.mPosition = new PointF(f, f2);
            return this;
        }

        public Builder setTransform(float[] fArr, float f) {
            this.mFloat9 = Arrays.copyOf(fArr, 9);
            this.mRotation = f;
            return this;
        }

        public Builder setCornerRadius(float f) {
            if (CoreRune.MW_PIP_DISABLE_ROUNDED_CORNER) {
                f = 0.0f;
            }
            this.mCornerRadius = f;
            return this;
        }

        public Builder setShadowRadius(float f) {
            if (CoreRune.MW_PIP_DISABLE_ROUNDED_CORNER) {
                f = 0.0f;
            }
            this.mShadowRadius = f;
            return this;
        }

        public Builder setWindowCrop(Rect rect) {
            this.mWindowCrop = new Rect(rect);
            return this;
        }

        public PictureInPictureSurfaceTransaction build() {
            return new PictureInPictureSurfaceTransaction(this.mAlpha, this.mPosition, this.mFloat9, this.mRotation, this.mCornerRadius, this.mShadowRadius, this.mWindowCrop);
        }
    }
}
