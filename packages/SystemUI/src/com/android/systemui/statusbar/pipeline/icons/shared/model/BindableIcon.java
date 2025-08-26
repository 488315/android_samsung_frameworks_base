package com.android.systemui.statusbar.pipeline.icons.shared.model;

/* loaded from: classes3.dex */
public interface BindableIcon {
    ModernStatusBarViewCreator getInitializer();

    boolean getShouldBindIcon();

    String getSlot();
}
