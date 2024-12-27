package com.android.systemui.screenshot.scroll;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScrollCaptureController {
    public final Executor mBgExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LongScreenshot {
        public final ImageTileSet mImageTileSet;

        public LongScreenshot(ScrollCaptureClient.Session session, ImageTileSet imageTileSet) {
            this.mImageTileSet = imageTileSet;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LongScreenshot{l=");
            ImageTileSet imageTileSet = this.mImageTileSet;
            sb.append(imageTileSet.mRegion.getBounds().left);
            sb.append(", t=");
            sb.append(imageTileSet.mRegion.getBounds().top);
            sb.append(", r=");
            sb.append(imageTileSet.mRegion.getBounds().right);
            sb.append(", b=");
            sb.append(imageTileSet.mRegion.getBounds().bottom);
            sb.append(", w=");
            sb.append(imageTileSet.mRegion.getBounds().width());
            sb.append(", h=");
            sb.append(imageTileSet.getHeight());
            sb.append("}");
            return sb.toString();
        }
    }

    public ScrollCaptureController(Context context, Executor executor, ScrollCaptureClient scrollCaptureClient, ImageTileSet imageTileSet, UiEventLogger uiEventLogger) {
        this.mBgExecutor = executor;
    }

    public float getTargetTopSizeRatio() {
        return 0.4f;
    }
}
