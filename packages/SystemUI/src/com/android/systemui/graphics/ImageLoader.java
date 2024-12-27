package com.android.systemui.graphics;

import android.content.Context;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.graphics.ImageLoader;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ImageLoader {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ImageLoader(Context context, CoroutineDispatcher coroutineDispatcher) {
    }

    public static Drawable loadDrawableSync(ImageDecoder.Source source, final int i, final int i2, final int i3) {
        try {
            return ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.graphics.ImageLoader$loadDrawableSync$1
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                    ImageLoader.Companion companion = ImageLoader.Companion;
                    Size size = imageInfo.getSize();
                    int i4 = i;
                    int i5 = i2;
                    companion.getClass();
                    if ((i4 != 0 || i5 != 0) && (size.getWidth() > i4 || size.getHeight() > i5)) {
                        float min = Math.min(i4 <= 0 ? 1.0f : i4 / size.getWidth(), i5 <= 0 ? 1.0f : i5 / size.getHeight());
                        if (min < 1.0f) {
                            int width = (int) (size.getWidth() * min);
                            int height = (int) (size.getHeight() * min);
                            if (Log.isLoggable("ImageLoader", 3)) {
                                SuggestionsAdapter$$ExternalSyntheticOutline0.m(width, height, "Configured image size to ", " x ", "ImageLoader");
                            }
                            imageDecoder.setTargetSize(width, height);
                        }
                    }
                    imageDecoder.setAllocator(i3);
                }
            });
        } catch (IOException e) {
            Log.w("ImageLoader", "Failed to load source " + source, e);
            return null;
        }
    }
}
