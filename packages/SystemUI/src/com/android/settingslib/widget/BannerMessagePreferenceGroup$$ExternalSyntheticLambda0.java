package com.android.settingslib.widget;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class BannerMessagePreferenceGroup$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ BannerMessagePreferenceGroup f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = BannerMessagePreferenceGroup.$r8$clinit;
        BannerMessagePreferenceGroup bannerMessagePreferenceGroup = this.f$0;
        bannerMessagePreferenceGroup.isExpanded = !bannerMessagePreferenceGroup.isExpanded;
        bannerMessagePreferenceGroup.updateExpandCollapsePreference();
        bannerMessagePreferenceGroup.updateChildrenVisibility$1();
        return Unit.INSTANCE;
    }
}
