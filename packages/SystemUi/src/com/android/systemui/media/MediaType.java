package com.android.systemui.media;

import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum MediaType {
    QS(R.layout.sec_media_view, true, false, true, true, true, false, true, true, true, true, false, false),
    COVER(R.layout.sec_media_view_jr, true, true, false, true, false, true, false, false, false, true, true, false);

    private final int layout;
    private final boolean supportArtwork;
    private final boolean supportBudsButton;
    private final boolean supportCapsule;
    private final boolean supportCarousel;
    private final boolean supportColorSchemeTransition;
    private final boolean supportExpandable;
    private final boolean supportFixedFontSize;
    private final boolean supportGuts;
    private final boolean supportRoundedCorner;
    private final boolean supportSettings;
    private final boolean supportSquiggly;
    private final boolean supportWidgetTimer;

    MediaType(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12) {
        this.layout = i;
        this.supportArtwork = z;
        this.supportCapsule = z2;
        this.supportCarousel = z3;
        this.supportColorSchemeTransition = z4;
        this.supportExpandable = z5;
        this.supportFixedFontSize = z6;
        this.supportGuts = z7;
        this.supportRoundedCorner = z8;
        this.supportSettings = z9;
        this.supportSquiggly = z10;
        this.supportWidgetTimer = z11;
        this.supportBudsButton = z12;
    }

    public final int getLayout() {
        return this.layout;
    }

    public final boolean getSupportArtwork() {
        return this.supportArtwork;
    }

    public final boolean getSupportBudsButton() {
        return this.supportBudsButton;
    }

    public final boolean getSupportCapsule() {
        return this.supportCapsule;
    }

    public final boolean getSupportCarousel() {
        return this.supportCarousel;
    }

    public final boolean getSupportColorSchemeTransition() {
        return this.supportColorSchemeTransition;
    }

    public final boolean getSupportExpandable() {
        return this.supportExpandable;
    }

    public final boolean getSupportFixedFontSize() {
        return this.supportFixedFontSize;
    }

    public final boolean getSupportGuts() {
        return this.supportGuts;
    }

    public final boolean getSupportRoundedCorner() {
        return this.supportRoundedCorner;
    }

    public final boolean getSupportSettings() {
        return this.supportSettings;
    }

    public final boolean getSupportSquiggly() {
        return this.supportSquiggly;
    }

    public final boolean getSupportWidgetTimer() {
        return this.supportWidgetTimer;
    }
}
