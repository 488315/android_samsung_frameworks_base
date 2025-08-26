package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;

/* loaded from: classes2.dex */
public final class TopAreaSection {
    public final DefaultClockSection clockSection;
    public final KeyguardClockViewModel clockViewModel;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final MediaCarouselSection mediaCarouselSection;
    public final SmartSpaceSection smartSpaceSection;
    public final WeatherClockSection weatherClockSection;

    public TopAreaSection(KeyguardClockViewModel keyguardClockViewModel, SmartSpaceSection smartSpaceSection, MediaCarouselSection mediaCarouselSection, DefaultClockSection defaultClockSection, WeatherClockSection weatherClockSection, KeyguardClockViewModel keyguardClockViewModel2) {
        this.clockViewModel = keyguardClockViewModel;
        this.smartSpaceSection = smartSpaceSection;
        this.mediaCarouselSection = mediaCarouselSection;
        this.clockSection = defaultClockSection;
        this.weatherClockSection = weatherClockSection;
        this.keyguardClockViewModel = keyguardClockViewModel2;
    }
}
