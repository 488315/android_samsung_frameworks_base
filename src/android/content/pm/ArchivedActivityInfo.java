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

    @Deprecated
    private void __metadata() {
    }

    public ArchivedActivityInfo(CharSequence charSequence, ComponentName componentName) {
        Objects.requireNonNull(charSequence);
        Objects.requireNonNull(componentName);
        this.mLabel = charSequence;
        this.mComponentName = componentName;
    }

    ArchivedActivityInfo(ArchivedActivityParcel archivedActivityParcel) {
        this.mLabel = archivedActivityParcel.title;
        this.mComponentName = archivedActivityParcel.originalComponentName;
        this.mIcon = drawableFromCompressedBitmap(archivedActivityParcel.iconBitmap);
        this.mMonochromeIcon = drawableFromCompressedBitmap(archivedActivityParcel.monochromeIconBitmap);
    }

    ArchivedActivityParcel getParcel() {
        ArchivedActivityParcel archivedActivityParcel = new ArchivedActivityParcel();
        archivedActivityParcel.title = this.mLabel.toString();
        archivedActivityParcel.originalComponentName = this.mComponentName;
        Drawable drawable = this.mIcon;
        archivedActivityParcel.iconBitmap = drawable == null ? null : bytesFromBitmap(drawableToBitmap(drawable));
        Drawable drawable2 = this.mMonochromeIcon;
        archivedActivityParcel.monochromeIconBitmap = drawable2 != null ? bytesFromBitmap(drawableToBitmap(drawable2)) : null;
        return archivedActivityParcel;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawableToBitmap(drawable, 0);
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int i) {
        Bitmap bitmapCreateBitmap;
        Bitmap bitmap;
        int i2;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            bitmap = bitmapCreateBitmap;
        }
        if (i <= 0 || (bitmap.getWidth() >= i && bitmap.getHeight() >= i && bitmap.getWidth() <= (i2 = i * 2) && bitmap.getHeight() <= i2)) {
            return bitmap;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i, true);
        if (bitmapCreateScaledBitmap != bitmap) {
            bitmap.recycle();
        }
        return bitmapCreateScaledBitmap;
    }

    public static byte[] bytesFromBitmap(Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bitmap.getByteCount());
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException e) {
            Slog.e(TAG, "Failed to compress bitmap", e);
            return null;
        }
    }

    private static Drawable drawableFromCompressedBitmap(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new BitmapDrawable((Resources) null, new ByteArrayInputStream(bArr));
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

    public ArchivedActivityInfo setLabel(CharSequence charSequence) {
        this.mLabel = charSequence;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) charSequence);
        return this;
    }

    public ArchivedActivityInfo setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) componentName);
        return this;
    }

    public ArchivedActivityInfo setIcon(Drawable drawable) {
        this.mIcon = drawable;
        return this;
    }

    public ArchivedActivityInfo setMonochromeIcon(Drawable drawable) {
        this.mMonochromeIcon = drawable;
        return this;
    }
}
