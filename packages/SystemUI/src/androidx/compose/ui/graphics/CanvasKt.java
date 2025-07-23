package androidx.compose.ui.graphics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CanvasKt {
    public static final AndroidCanvas Canvas(AndroidImageBitmap androidImageBitmap) {
        android.graphics.Canvas canvas = AndroidCanvas_androidKt.EmptyCanvas;
        AndroidCanvas androidCanvas = new AndroidCanvas();
        androidCanvas.internalCanvas = new android.graphics.Canvas(AndroidImageBitmap_androidKt.asAndroidBitmap(androidImageBitmap));
        return androidCanvas;
    }
}
