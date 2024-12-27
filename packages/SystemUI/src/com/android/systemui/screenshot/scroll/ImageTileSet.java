package com.android.systemui.screenshot.scroll;

import android.graphics.Region;
import android.os.Handler;
import com.android.internal.util.CallbackRegistry;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ImageTileSet {
    public CallbackRegistry mContentListeners;
    public final List mTiles = new ArrayList();
    public final Region mRegion = new Region();

    public ImageTileSet(Handler handler) {
    }

    public final int getHeight() {
        return this.mRegion.getBounds().height();
    }
}
