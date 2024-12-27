package com.android.systemui.plugins.qs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(version = 1)
public abstract class QSIconView extends ViewGroup {
    public static final int VERSION = 1;

    public QSIconView(Context context) {
        super(context);
    }

    public abstract void disableAnimation();

    public abstract View getIconView();

    public abstract void setIcon(QSTile.State state, boolean z);
}
