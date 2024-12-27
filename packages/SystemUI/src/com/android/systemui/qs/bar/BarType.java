package com.android.systemui.qs.bar;

import com.android.systemui.QpRune;

public enum BarType {
    DUMMY(BarItemImpl.class, false, false),
    VIDEO_CALL_MIC_MODE(VideoCallMicModeBar.class, false, true),
    TOP_LARGE_TILE(TopLargeTileBar.class, false, true),
    TILE_CHUNK_LAYOUT(TileChunkLayoutBar.class, false, true),
    BRIGHTNESS(BrightnessBar.class, false, false),
    VOLUME(VolumeBar.class, false, false),
    BRIGHTNESS_VOLUME(BrightnessVolumeBar.class, false, true),
    QS_MEDIA_PLAYER(QSMediaPlayerBar.class, false, true),
    MULTI_SIM_PREFERRED_SLOT(MultiSIMPreferredSlotBar.class, false, QpRune.QUICK_BAR_MULTISIM),
    QUICK_CONTROL(QuickControlBar.class, false, true),
    BRIGHTNESS_MEDIA_DEVICES(BrightnessMediaDevicesBar.class, true, false),
    BOTTOM_LARGE_TILE(BottomLargeTileBar.class, false, true),
    SMARTVIEW_LARGE_TILE(SmartViewLargeTileBar.class, false, true),
    MEDIA_DEVICES(MediaDevicesBar.class, false, false),
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
