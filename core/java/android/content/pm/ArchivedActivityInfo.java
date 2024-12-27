package android.content.pm;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Slog;

import com.android.internal.util.AnnotationValidations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ArchivedActivityInfo {
    private static final String TAG = "ArchivedActivityInfo";
    private ComponentName mComponentName;
    private Drawable mIcon;
    private CharSequence mLabel;
    private Drawable mMonochromeIcon;

    public ArchivedActivityInfo(CharSequence label, ComponentName componentName) {
        Objects.requireNonNull(label);
        Objects.requireNonNull(componentName);
        this.mLabel = label;
        this.mComponentName = componentName;
    }

    ArchivedActivityInfo(ArchivedActivityParcel parcel) {
        this.mLabel = parcel.title;
        this.mComponentName = parcel.originalComponentName;
        this.mIcon = drawableFromCompressedBitmap(parcel.iconBitmap);
        this.mMonochromeIcon = drawableFromCompressedBitmap(parcel.monochromeIconBitmap);
    }

    ArchivedActivityParcel getParcel() {
        ArchivedActivityParcel parcel = new ArchivedActivityParcel();
        parcel.title = this.mLabel.toString();
        parcel.originalComponentName = this.mComponentName;
        parcel.iconBitmap =
                this.mIcon == null ? null : bytesFromBitmap(drawableToBitmap(this.mIcon));
        parcel.monochromeIconBitmap =
                this.mMonochromeIcon != null
                        ? bytesFromBitmap(drawableToBitmap(this.mMonochromeIcon))
                        : null;
        return parcel;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawableToBitmap(drawable, 0);
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int iconSize) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap =
                        Bitmap.createBitmap(
                                drawable.getIntrinsicWidth(),
                                drawable.getIntrinsicHeight(),
                                Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        if (iconSize <= 0) {
            return bitmap;
        }
        if (bitmap.getWidth() < iconSize
                || bitmap.getHeight() < iconSize
                || bitmap.getWidth() > iconSize * 2
                || bitmap.getHeight() > iconSize * 2) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, true);
            if (scaledBitmap != bitmap) {
                bitmap.recycle();
            }
            return scaledBitmap;
        }
        return bitmap;
    }

    public static byte[] bytesFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmap.getByteCount());
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] byteArray = baos.toByteArray();
                baos.close();
                return byteArray;
            } finally {
            }
        } catch (IOException e) {
            Slog.e(TAG, "Failed to compress bitmap", e);
            return null;
        }
    }

    private static Drawable drawableFromCompressedBitmap(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new BitmapDrawable((Resources) null, new ByteArrayInputStream(bytes));
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public Drawable getMonochromeIcon() {
        return this.mMonochromeIcon;
    }

    public ArchivedActivityInfo setLabel(CharSequence value) {
        this.mLabel = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mLabel);
        return this;
    }

    public ArchivedActivityInfo setComponentName(ComponentName value) {
        this.mComponentName = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mComponentName);
        return this;
    }

    public ArchivedActivityInfo setIcon(Drawable value) {
        this.mIcon = value;
        return this;
    }

    public ArchivedActivityInfo setMonochromeIcon(Drawable value) {
        this.mMonochromeIcon = value;
        return this;
    }

    @Deprecated
    private void __metadata() {}
}
