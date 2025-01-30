package com.android.systemui.screenshot;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.screenshot.ScrollCaptureClient;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScrollCaptureController {
    public final Executor mBgExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LongScreenshot {
        public final ImageTileSet mImageTileSet;

        public LongScreenshot(ScrollCaptureClient.Session session, ImageTileSet imageTileSet) {
            this.mImageTileSet = imageTileSet;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LongScreenshot{w=");
            ImageTileSet imageTileSet = this.mImageTileSet;
            sb.append(imageTileSet.getWidth());
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
