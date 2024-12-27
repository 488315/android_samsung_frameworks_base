package com.android.systemui.screenshot.scroll;

import android.graphics.Region;
import android.os.Handler;
import com.android.internal.util.CallbackRegistry;
import java.util.ArrayList;
import java.util.List;

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
