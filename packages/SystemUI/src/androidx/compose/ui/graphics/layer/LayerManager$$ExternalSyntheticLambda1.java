package androidx.compose.ui.graphics.layer;

import android.media.Image;
import android.media.ImageReader;
import androidx.compose.ui.graphics.layer.LayerManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LayerManager$$ExternalSyntheticLambda1 implements ImageReader.OnImageAvailableListener {
    @Override // android.media.ImageReader.OnImageAvailableListener
    public final void onImageAvailable(ImageReader imageReader) {
        Image acquireLatestImage;
        LayerManager.Companion companion = LayerManager.Companion;
        if (imageReader == null || (acquireLatestImage = imageReader.acquireLatestImage()) == null) {
            return;
        }
        acquireLatestImage.close();
    }
}
