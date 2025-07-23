package com.android.systemui.plugins.qs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public abstract class QSIconView extends ViewGroup {
    public static final int VERSION = 1;

    public QSIconView(Context context) {
        super(context);
    }

    public abstract void disableAnimation();

    public abstract View getIconView();

    public abstract void setIcon(QSTile.State state, boolean z);
}
