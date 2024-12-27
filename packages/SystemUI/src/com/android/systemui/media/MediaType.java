package com.android.systemui.media;

import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaType {
    public static final /* synthetic */ MediaType[] $VALUES;
    public static final MediaType COVER;
    public static final MediaType QS;
    private final int layout;
    private final boolean supportArtwork;
    private final boolean supportBudsButton;
    private final boolean supportCapsule;
    private final boolean supportCarousel;
    private final boolean supportColorSchemeTransition;
    private final boolean supportExpandable;
    private final boolean supportFixedFontSize;
    private final boolean supportGuts;
    private final boolean supportPlayLastSong;
    private final boolean supportRoundedCorner;
    private final boolean supportSettings;
    private final boolean supportSquiggly;
    private final boolean supportWidgetTimer;

    static {
        MediaType mediaType = new MediaType(QS.TAG, 0, R.layout.sec_media_view, true, false, true, true, true, false, true, true, true, true, false, true, true);
        QS = mediaType;
        MediaType mediaType2 = new MediaType("COVER", 1, R.layout.sec_media_view_jr, true, true, false, true, false, true, false, false, false, true, true, false, false);
        COVER = mediaType2;
        MediaType[] mediaTypeArr = {mediaType, mediaType2};
        $VALUES = mediaTypeArr;
        EnumEntriesKt.enumEntries(mediaTypeArr);
    }

    private MediaType(String str, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13) {
        this.layout = i2;
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
        this.supportPlayLastSong = z13;
    }

    public static MediaType valueOf(String str) {
        return (MediaType) Enum.valueOf(MediaType.class, str);
    }

    public static MediaType[] values() {
        return (MediaType[]) $VALUES.clone();
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

    public final boolean getSupportPlayLastSong() {
        return this.supportPlayLastSong;
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
