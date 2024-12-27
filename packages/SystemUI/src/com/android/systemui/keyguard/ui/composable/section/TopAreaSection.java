package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;

public final class TopAreaSection {
    public final KeyguardClockInteractor clockInteractor;
    public final DefaultClockSection clockSection;
    public final KeyguardClockViewModel clockViewModel;
    public final MediaCarouselSection mediaCarouselSection;
    public final SmartSpaceSection smartSpaceSection;
    public final WeatherClockSection weatherClockSection;

    public TopAreaSection(KeyguardClockViewModel keyguardClockViewModel, SmartSpaceSection smartSpaceSection, MediaCarouselSection mediaCarouselSection, DefaultClockSection defaultClockSection, WeatherClockSection weatherClockSection, KeyguardClockInteractor keyguardClockInteractor) {
    }
}
