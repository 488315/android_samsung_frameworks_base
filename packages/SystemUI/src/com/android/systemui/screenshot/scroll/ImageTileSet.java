package com.android.systemui.screenshot.scroll;

import android.graphics.Region;
import android.os.Handler;
import com.android.internal.util.CallbackRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ImageTileSet {
    public CallbackRegistry mContentListeners;
    public final Handler mHandler;
    public final List mTiles = new ArrayList();
    public final Region mRegion = new Region();

    public ImageTileSet(Handler handler) {
        this.mHandler = handler;
    }

    public final void addTile(final ImageTile imageTile) {
        Handler handler = this.mHandler;
        if (!handler.getLooper().isCurrentThread()) {
            handler.post(new Runnable() { // from class: com.android.systemui.screenshot.scroll.ImageTileSet$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImageTileSet.this.addTile(imageTile);
                }
            });
            return;
        }
        ((ArrayList) this.mTiles).add(imageTile);
        Region region = this.mRegion;
        region.op(imageTile.mLocation, region, Region.Op.UNION);
        CallbackRegistry callbackRegistry = this.mContentListeners;
        if (callbackRegistry != null) {
            callbackRegistry.notifyCallbacks(this, 0, (Object) null);
        }
    }

    public final void clear() {
        if (((ArrayList) this.mTiles).isEmpty()) {
            return;
        }
        this.mRegion.setEmpty();
        Iterator it = ((ArrayList) this.mTiles).iterator();
        while (it.hasNext()) {
            ((ImageTile) it.next()).close();
            it.remove();
        }
        CallbackRegistry callbackRegistry = this.mContentListeners;
        if (callbackRegistry != null) {
            callbackRegistry.notifyCallbacks(this, 0, (Object) null);
        }
    }

    public final int getHeight() {
        return this.mRegion.getBounds().height();
    }

    public final int getTop() {
        return this.mRegion.getBounds().top;
    }

    public final int getWidth() {
        return this.mRegion.getBounds().width();
    }
}
