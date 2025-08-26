package com.android.systemui.qs.panels.shared.model;

/* loaded from: classes2.dex */
public interface SizedTile {
    Object getTile();

    int getWidth();

    default boolean isIcon() {
        return getWidth() == 1;
    }
}
