package androidx.compose.ui.graphics;

/* loaded from: classes.dex */
public abstract class CanvasKt {
    public static final AndroidCanvas Canvas(AndroidImageBitmap androidImageBitmap) {
        android.graphics.Canvas canvas = AndroidCanvas_androidKt.EmptyCanvas;
        AndroidCanvas androidCanvas = new AndroidCanvas();
        androidCanvas.internalCanvas = new android.graphics.Canvas(AndroidImageBitmap_androidKt.asAndroidBitmap(androidImageBitmap));
        return androidCanvas;
    }
}
