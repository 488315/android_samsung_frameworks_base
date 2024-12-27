package com.android.systemui.plugins.qs;

import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.qs.QSTile;

public interface LockQSTile {
    void addCallback(QSTile.Callback callback);

    void click();

    default Drawable getNextTileIconDrawable() {
        return null;
    }

    QSTile.State getState();

    Drawable getTileIconDrawable();

    CharSequence getTileLabel();

    boolean isAvailable();

    void removeCallback(QSTile.Callback callback);

    void setListening(Object obj, boolean z);
}
