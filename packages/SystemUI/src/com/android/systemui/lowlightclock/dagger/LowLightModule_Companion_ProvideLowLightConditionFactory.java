package com.android.systemui.lowlightclock.dagger;

import com.android.systemui.lowlightclock.DirectBootCondition;
import com.android.systemui.lowlightclock.LowLightCondition;
import com.android.systemui.shared.condition.CombinedCondition;
import com.android.systemui.shared.condition.Condition;
import dagger.internal.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: classes2.dex */
public final class LowLightModule_Companion_ProvideLowLightConditionFactory implements Provider {
    public final Provider directBootConditionProvider;
    public final Provider lowLightConditionProvider;

    public LowLightModule_Companion_ProvideLowLightConditionFactory(Provider provider, Provider provider2) {
        this.lowLightConditionProvider = provider;
        this.directBootConditionProvider = provider2;
    }

    public static CombinedCondition provideLowLightCondition(LowLightCondition lowLightCondition, DirectBootCondition directBootCondition) {
        LowLightModule.Companion.getClass();
        directBootCondition.getClass();
        List listListOf = CollectionsKt__CollectionsKt.listOf(Arrays.copyOf(new Condition[]{lowLightCondition}, 1));
        ArrayList arrayList = new ArrayList();
        arrayList.add(directBootCondition);
        arrayList.addAll(listListOf);
        return new CombinedCondition(directBootCondition._scope, arrayList, 1);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideLowLightCondition((LowLightCondition) this.lowLightConditionProvider.get(), (DirectBootCondition) this.directBootConditionProvider.get());
    }
}
