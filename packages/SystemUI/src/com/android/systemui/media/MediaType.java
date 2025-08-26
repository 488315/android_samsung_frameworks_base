package com.android.systemui.media;

import com.android.systemui.R;
import com.android.systemui.plugins.qs.QS;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class MediaType {
    public static final /* synthetic */ MediaType[] $VALUES;
    public static final MediaType COVER;
    public static final MediaType COVER_QS;
    public static final MediaType ENR;
    public static final MediaType OA;
    public static final MediaType QS;
    private final int layout;
    private final boolean supportArtwork;
    private final boolean supportBudsButton;
    private final boolean supportCapsule;
    private final boolean supportCarousel;
    private final boolean supportClick;
    private final boolean supportColorSchemeTransition;
    private final boolean supportCoverQuickPanelMedia;
    private final boolean supportDetailView;
    private final boolean supportExpandable;
    private final boolean supportFixedFontSize;
    private final boolean supportGuts;
    private final boolean supportMediaOutput;
    private final boolean supportOAChip;
    private final boolean supportPlayLastSong;
    private final boolean supportRecoilAnimation;
    private final boolean supportRoundedCorner;
    private final boolean supportSettings;
    private final boolean supportSquiggly;
    private final boolean supportWidgetTimer;

    static {
        MediaType mediaType = new MediaType(QS.TAG, 0, R.layout.sec_media_view, true, false, true, true, true, false, true, true, true, true, false, true, true, true, true, true, true, false, false);
        QS = mediaType;
        MediaType mediaType2 = new MediaType("COVER", 1, R.layout.sec_media_view_jr, true, true, false, true, false, true, false, false, false, true, true, false, false, false, true, false, false, false, false);
        COVER = mediaType2;
        MediaType mediaType3 = new MediaType("OA", 2, R.layout.sec_media_view, true, false, false, true, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false);
        OA = mediaType3;
        MediaType mediaType4 = new MediaType("ENR", 3, R.layout.sec_media_view, true, false, false, true, true, false, false, true, false, true, false, false, false, false, false, true, false, false, false);
        ENR = mediaType4;
        MediaType mediaType5 = new MediaType("COVER_QS", 4, R.layout.sec_subscreen_media_view, true, false, false, true, true, false, false, false, false, false, false, false, true, false, false, true, false, false, true);
        COVER_QS = mediaType5;
        MediaType[] mediaTypeArr = {mediaType, mediaType2, mediaType3, mediaType4, mediaType5};
        $VALUES = mediaTypeArr;
        EnumEntriesKt.enumEntries(mediaTypeArr);
    }

    private MediaType(String str, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19) {
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
        this.supportDetailView = z14;
        this.supportMediaOutput = z15;
        this.supportClick = z16;
        this.supportRecoilAnimation = z17;
        this.supportOAChip = z18;
        this.supportCoverQuickPanelMedia = z19;
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

    public final boolean getSupportClick() {
        return this.supportClick;
    }

    public final boolean getSupportColorSchemeTransition() {
        return this.supportColorSchemeTransition;
    }

    public final boolean getSupportCoverQuickPanelMedia() {
        return this.supportCoverQuickPanelMedia;
    }

    public final boolean getSupportDetailView() {
        return this.supportDetailView;
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

    public final boolean getSupportMediaOutput() {
        return this.supportMediaOutput;
    }

    public final boolean getSupportOAChip() {
        return this.supportOAChip;
    }

    public final boolean getSupportPlayLastSong() {
        return this.supportPlayLastSong;
    }

    public final boolean getSupportRecoilAnimation() {
        return this.supportRecoilAnimation;
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
