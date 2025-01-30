package com.android.systemui.plugins.keyguardstatusview;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginFaceWidgetColorScheme {
    private int accentPrimaryFromScheme;
    private int accentSecondaryFromScheme;
    private int backgroundEndFromScheme;
    private int backgroundStartFromScheme;
    private int surfaceFromScheme;

    public PluginFaceWidgetColorScheme(int i, int i2, int i3, int i4, int i5) {
        this.surfaceFromScheme = i;
        this.accentPrimaryFromScheme = i2;
        this.accentSecondaryFromScheme = i3;
        this.backgroundStartFromScheme = i4;
        this.backgroundEndFromScheme = i5;
    }

    public static /* synthetic */ PluginFaceWidgetColorScheme copy$default(PluginFaceWidgetColorScheme pluginFaceWidgetColorScheme, int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i = pluginFaceWidgetColorScheme.surfaceFromScheme;
        }
        if ((i6 & 2) != 0) {
            i2 = pluginFaceWidgetColorScheme.accentPrimaryFromScheme;
        }
        int i7 = i2;
        if ((i6 & 4) != 0) {
            i3 = pluginFaceWidgetColorScheme.accentSecondaryFromScheme;
        }
        int i8 = i3;
        if ((i6 & 8) != 0) {
            i4 = pluginFaceWidgetColorScheme.backgroundStartFromScheme;
        }
        int i9 = i4;
        if ((i6 & 16) != 0) {
            i5 = pluginFaceWidgetColorScheme.backgroundEndFromScheme;
        }
        return pluginFaceWidgetColorScheme.copy(i, i7, i8, i9, i5);
    }

    public final int component1() {
        return this.surfaceFromScheme;
    }

    public final int component2() {
        return this.accentPrimaryFromScheme;
    }

    public final int component3() {
        return this.accentSecondaryFromScheme;
    }

    public final int component4() {
        return this.backgroundStartFromScheme;
    }

    public final int component5() {
        return this.backgroundEndFromScheme;
    }

    public final PluginFaceWidgetColorScheme copy(int i, int i2, int i3, int i4, int i5) {
        return new PluginFaceWidgetColorScheme(i, i2, i3, i4, i5);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PluginFaceWidgetColorScheme)) {
            return false;
        }
        PluginFaceWidgetColorScheme pluginFaceWidgetColorScheme = (PluginFaceWidgetColorScheme) obj;
        return this.surfaceFromScheme == pluginFaceWidgetColorScheme.surfaceFromScheme && this.accentPrimaryFromScheme == pluginFaceWidgetColorScheme.accentPrimaryFromScheme && this.accentSecondaryFromScheme == pluginFaceWidgetColorScheme.accentSecondaryFromScheme && this.backgroundStartFromScheme == pluginFaceWidgetColorScheme.backgroundStartFromScheme && this.backgroundEndFromScheme == pluginFaceWidgetColorScheme.backgroundEndFromScheme;
    }

    public final int getAccentPrimaryFromScheme() {
        return this.accentPrimaryFromScheme;
    }

    public final int getAccentSecondaryFromScheme() {
        return this.accentSecondaryFromScheme;
    }

    public final int getBackgroundEndFromScheme() {
        return this.backgroundEndFromScheme;
    }

    public final int getBackgroundStartFromScheme() {
        return this.backgroundStartFromScheme;
    }

    public final int getSurfaceFromScheme() {
        return this.surfaceFromScheme;
    }

    public int hashCode() {
        return Integer.hashCode(this.backgroundEndFromScheme) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.backgroundStartFromScheme, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.accentSecondaryFromScheme, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.accentPrimaryFromScheme, Integer.hashCode(this.surfaceFromScheme) * 31, 31), 31), 31);
    }

    public final void setAccentPrimaryFromScheme(int i) {
        this.accentPrimaryFromScheme = i;
    }

    public final void setAccentSecondaryFromScheme(int i) {
        this.accentSecondaryFromScheme = i;
    }

    public final void setBackgroundEndFromScheme(int i) {
        this.backgroundEndFromScheme = i;
    }

    public final void setBackgroundStartFromScheme(int i) {
        this.backgroundStartFromScheme = i;
    }

    public final void setSurfaceFromScheme(int i) {
        this.surfaceFromScheme = i;
    }

    public String toString() {
        int i = this.surfaceFromScheme;
        int i2 = this.accentPrimaryFromScheme;
        int i3 = this.accentSecondaryFromScheme;
        int i4 = this.backgroundStartFromScheme;
        int i5 = this.backgroundEndFromScheme;
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("PluginFaceWidgetColorScheme(surfaceFromScheme=", i, ", accentPrimaryFromScheme=", i2, ", accentSecondaryFromScheme=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i3, ", backgroundStartFromScheme=", i4, ", backgroundEndFromScheme=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(m45m, i5, ")");
    }
}
