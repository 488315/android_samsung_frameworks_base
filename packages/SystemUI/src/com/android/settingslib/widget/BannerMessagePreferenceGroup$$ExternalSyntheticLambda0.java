package com.android.settingslib.widget;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BannerMessagePreferenceGroup$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ BannerMessagePreferenceGroup f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int i = BannerMessagePreferenceGroup.$r8$clinit;
        BannerMessagePreferenceGroup bannerMessagePreferenceGroup = this.f$0;
        bannerMessagePreferenceGroup.isExpanded = !bannerMessagePreferenceGroup.isExpanded;
        bannerMessagePreferenceGroup.updateExpandCollapsePreference();
        bannerMessagePreferenceGroup.updateChildrenVisibility$1();
        return Unit.INSTANCE;
    }
}
