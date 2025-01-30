package com.android.systemui.qs.bar;

import com.android.systemui.QpRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum BarType {
    /* JADX INFO: Fake field, exist only in values array */
    DUMMY(BarItemImpl.class, false, false),
    VIDEO_CALL_MIC_MODE(VideoCallMicModeBar.class, false, true),
    TOP_LARGE_TILE(TopLargeTileBar.class, false, true),
    PAGED_TILE(PagedTileLayoutBar.class, false, true),
    MULTI_SIM_PREFERRED_SLOT(MultiSIMPreferredSlotBar.class, false, QpRune.QUICK_BAR_MULTISIM),
    BRIGHTNESS(BrightnessBar.class, false, true),
    BRIGHTNESS_MEDIA_DEVICES(BrightnessMediaDevicesBar.class, true, false),
    BOTTOM_LARGE_TILE(BottomLargeTileBar.class, false, true),
    MEDIA_DEVICES(MediaDevicesBar.class, false, false),
    QS_MEDIA_PLAYER(QSMediaPlayerBar.class, true, false),
    BUDS(BudsBar.class, false, true),
    SECURITY_FOOTER(SecurityFooterBar.class, false, true);

    private final Class<? extends BarItemImpl> mCls;
    private final boolean mCollapsed;
    private final boolean mExpanded;

    BarType(Class cls, boolean z, boolean z2) {
        this.mCls = cls;
        this.mCollapsed = z;
        this.mExpanded = z2;
    }

    public final Class getCls() {
        return this.mCls;
    }

    public final boolean hasCollapsed() {
        return this.mCollapsed;
    }

    public final boolean hasExpanded() {
        return this.mExpanded;
    }
}
