package com.android.systemui.plugins.keyguardstatusview;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final class PluginFaceWidgetColorScheme {
    public static final int $stable = 8;
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
        if ((i6 & 4) != 0) {
            i3 = pluginFaceWidgetColorScheme.accentSecondaryFromScheme;
        }
        if ((i6 & 8) != 0) {
            i4 = pluginFaceWidgetColorScheme.backgroundStartFromScheme;
        }
        if ((i6 & 16) != 0) {
            i5 = pluginFaceWidgetColorScheme.backgroundEndFromScheme;
        }
        int i7 = i5;
        int i8 = i3;
        return pluginFaceWidgetColorScheme.copy(i, i2, i8, i4, i7);
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
        return Integer.hashCode(this.backgroundEndFromScheme) + ReorderTile$$ExternalSyntheticOutline0.m(this.backgroundStartFromScheme, ReorderTile$$ExternalSyntheticOutline0.m(this.accentSecondaryFromScheme, ReorderTile$$ExternalSyntheticOutline0.m(this.accentPrimaryFromScheme, Integer.hashCode(this.surfaceFromScheme) * 31, 31), 31), 31);
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
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "PluginFaceWidgetColorScheme(surfaceFromScheme=", ", accentPrimaryFromScheme=", ", accentSecondaryFromScheme=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i3, ", backgroundStartFromScheme=", i4, ", backgroundEndFromScheme=");
        return ReorderTile$$ExternalSyntheticOutline0.m(i5, ")", sbM);
    }
}
