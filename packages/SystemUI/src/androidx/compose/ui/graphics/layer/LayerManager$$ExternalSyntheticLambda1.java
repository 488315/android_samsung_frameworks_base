package androidx.compose.ui.graphics.layer;

import android.media.Image;
import android.media.ImageReader;
import androidx.compose.ui.graphics.layer.LayerManager;

/* loaded from: classes.dex */
public final /* synthetic */ class LayerManager$$ExternalSyntheticLambda1 implements ImageReader.OnImageAvailableListener {
    @Override // android.media.ImageReader.OnImageAvailableListener
    public final void onImageAvailable(ImageReader imageReader) {
        Image imageAcquireLatestImage;
        LayerManager.Companion companion = LayerManager.Companion;
        if (imageReader == null || (imageAcquireLatestImage = imageReader.acquireLatestImage()) == null) {
            return;
        }
        imageAcquireLatestImage.close();
    }
}
