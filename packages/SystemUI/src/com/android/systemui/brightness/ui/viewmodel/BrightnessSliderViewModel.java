package com.android.systemui.brightness.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.brightness.domain.interactor.BrightnessPolicyEnforcementInteractor;
import com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BrightnessSliderViewModel {
    public final BrightnessPolicyEnforcementInteractor brightnessPolicyEnforcementInteractor;
    public final ReadonlyStateFlow currentBrightness;
    public final Icon.Resource icon;
    public final Text.Resource label;
    public final int maxBrightness;
    public final Flow policyRestriction;
    public final ScreenBrightnessInteractor screenBrightnessInteractor;

    public BrightnessSliderViewModel(ScreenBrightnessInteractor screenBrightnessInteractor, BrightnessPolicyEnforcementInteractor brightnessPolicyEnforcementInteractor, CoroutineScope coroutineScope) {
        FlowKt.stateIn(screenBrightnessInteractor.gammaBrightness, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), GammaBrightness.m933boximpl(0));
        int i = screenBrightnessInteractor.maxGammaBrightness;
        new Icon.Resource(R.drawable.ic_brightness_full, new ContentDescription.Resource(new Text.Resource(R.string.quick_settings_brightness_dialog_title).res));
        Flow flow = brightnessPolicyEnforcementInteractor.brightnessPolicyRestriction;
    }
}
