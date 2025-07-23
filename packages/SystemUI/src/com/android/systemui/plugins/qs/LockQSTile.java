package com.android.systemui.plugins.qs;

import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
