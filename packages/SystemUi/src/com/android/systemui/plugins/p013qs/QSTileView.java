package com.android.systemui.plugins.p013qs;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.p013qs.QSTile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Dependencies({@DependsOn(target = QSIconView.class), @DependsOn(target = QSTile.class)})
@ProvidesInterface(version = 3)
/* loaded from: classes2.dex */
public abstract class QSTileView extends LinearLayout {
    public static final int VERSION = 3;

    public QSTileView(Context context) {
        super(context);
    }

    public abstract int getDetailY();

    public abstract QSIconView getIcon();

    public abstract View getIconWithBackground();

    public View getLabel() {
        return null;
    }

    public View getLabelContainer() {
        return null;
    }

    public View getSecondaryIcon() {
        return null;
    }

    public View getSecondaryLabel() {
        return null;
    }

    public abstract void init(QSTile qSTile);

    public abstract void onStateChanged(QSTile.State state);

    public abstract void setPosition(int i);

    public abstract View updateAccessibilityOrder(View view);

    public void setShowLabels(boolean z) {
    }

    public void onPanelModeChanged() {
    }
}
