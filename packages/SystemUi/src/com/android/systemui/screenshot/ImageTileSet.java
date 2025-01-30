package com.android.systemui.screenshot;

import android.graphics.Region;
import android.os.Handler;
import com.android.internal.util.CallbackRegistry;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageTileSet {
    public CallbackRegistry mContentListeners;
    public final List mTiles = new ArrayList();
    public final Region mRegion = new Region();

    public ImageTileSet(Handler handler) {
    }

    public final int getHeight() {
        return this.mRegion.getBounds().height();
    }

    public final int getWidth() {
        return this.mRegion.getBounds().width();
    }
}
