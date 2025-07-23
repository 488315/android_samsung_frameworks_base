package android.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.TransitionUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class SharedElementCallback {
    private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final String BUNDLE_SNAPSHOT_COLOR_SPACE = "sharedElement:snapshot:colorSpace";
    private static final String BUNDLE_SNAPSHOT_HARDWARE_BUFFER = "sharedElement:snapshot:hardwareBuffer";
    private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    static final SharedElementCallback NULL_CALLBACK = new SharedElementCallback() { // from class: android.app.SharedElementCallback.1
    };
    private Matrix mTempMatrix;

    public interface OnSharedElementsReadyListener {
        void onSharedElementsReady();
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
    }

    public void onRejectSharedElements(List<View> list) {
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
    }

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
        Bitmap createDrawableBitmap;
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            Drawable drawable = imageView.getDrawable();
            Drawable background = imageView.getBackground();
            if (drawable != null && ((background == null || background.getAlpha() == 0) && (createDrawableBitmap = TransitionUtils.createDrawableBitmap(drawable, imageView)) != null)) {
                Bundle bundle = new Bundle();
                if (createDrawableBitmap.getConfig() != Bitmap.Config.HARDWARE) {
                    bundle.putParcelable(BUNDLE_SNAPSHOT_BITMAP, createDrawableBitmap);
                } else {
                    bundle.putParcelable(BUNDLE_SNAPSHOT_HARDWARE_BUFFER, createDrawableBitmap.getHardwareBuffer());
                    ColorSpace colorSpace = createDrawableBitmap.getColorSpace();
                    if (colorSpace != null) {
                        bundle.putInt(BUNDLE_SNAPSHOT_COLOR_SPACE, colorSpace.getId());
                    }
                }
                bundle.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, imageView.getScaleType().toString());
                if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
                    float[] fArr = new float[9];
                    imageView.getImageMatrix().getValues(fArr);
                    bundle.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, fArr);
                }
                return bundle;
            }
        }
        Matrix matrix2 = this.mTempMatrix;
        if (matrix2 == null) {
            this.mTempMatrix = new Matrix(matrix);
        } else {
            matrix2.set(matrix);
        }
        return TransitionUtils.createViewBitmap(view, this.mTempMatrix, rectF, (ViewGroup) view.getParent());
    }

    public View onCreateSnapshotView(Context context, Parcelable parcelable) {
        ColorSpace colorSpace = null;
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            HardwareBuffer hardwareBuffer = (HardwareBuffer) bundle.getParcelable(BUNDLE_SNAPSHOT_HARDWARE_BUFFER, HardwareBuffer.class);
            Bitmap bitmap = (Bitmap) bundle.getParcelable(BUNDLE_SNAPSHOT_BITMAP, Bitmap.class);
            if (hardwareBuffer == null && bitmap == null) {
                return null;
            }
            if (bitmap == null) {
                int i = bundle.getInt(BUNDLE_SNAPSHOT_COLOR_SPACE, 0);
                if (i >= 0 && i < ColorSpace.Named.values().length) {
                    colorSpace = ColorSpace.get(ColorSpace.Named.values()[i]);
                }
                bitmap = Bitmap.wrapHardwareBuffer(hardwareBuffer, colorSpace);
            }
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.valueOf(bundle.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
            if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
                float[] floatArray = bundle.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
                Matrix matrix = new Matrix();
                matrix.setValues(floatArray);
                imageView.setImageMatrix(matrix);
            }
            return imageView;
        }
        if (!(parcelable instanceof Bitmap)) {
            return null;
        }
        View view = new View(context);
        view.setBackground(new BitmapDrawable(context.getResources(), (Bitmap) parcelable));
        return view;
    }

    public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener.onSharedElementsReady();
    }
}
