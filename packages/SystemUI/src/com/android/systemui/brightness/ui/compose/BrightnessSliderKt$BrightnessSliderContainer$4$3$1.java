package com.android.systemui.brightness.ui.compose;

import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.brightness.domain.interactor.BrightnessPolicyEnforcementInteractor;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.utils.PolicyRestriction;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class BrightnessSliderKt$BrightnessSliderContainer$4$3$1 extends FunctionReferenceImpl implements Function1 {
    public BrightnessSliderKt$BrightnessSliderContainer$4$3$1(Object obj) {
        super(1, obj, BrightnessSliderViewModel.class, "showPolicyRestrictionDialog", "showPolicyRestrictionDialog(Lcom/android/systemui/utils/PolicyRestriction$Restricted;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        BrightnessPolicyEnforcementInteractor brightnessPolicyEnforcementInteractor = ((BrightnessSliderViewModel) this.receiver).brightnessPolicyEnforcementInteractor;
        brightnessPolicyEnforcementInteractor.getClass();
        brightnessPolicyEnforcementInteractor.activityStarter.postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(((PolicyRestriction.Restricted) obj).admin), 0);
        return Unit.INSTANCE;
    }
}
