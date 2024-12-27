package com.android.systemui.screenshot.scroll;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.screenshot.scroll.ScrollCaptureClient;
import java.util.concurrent.Executor;

public final class ScrollCaptureController {
    public final Executor mBgExecutor;

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
