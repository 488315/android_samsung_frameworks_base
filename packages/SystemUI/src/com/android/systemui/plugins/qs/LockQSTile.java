package com.android.systemui.plugins.qs;

import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
