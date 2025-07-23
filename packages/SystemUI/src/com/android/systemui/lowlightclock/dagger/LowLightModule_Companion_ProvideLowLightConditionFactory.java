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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        List listOf = CollectionsKt__CollectionsKt.listOf(Arrays.copyOf(new Condition[]{lowLightCondition}, 1));
        ArrayList arrayList = new ArrayList();
        arrayList.add(directBootCondition);
        arrayList.addAll(listOf);
        return new CombinedCondition(directBootCondition._scope, arrayList, 1);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideLowLightCondition((LowLightCondition) this.lowLightConditionProvider.get(), (DirectBootCondition) this.directBootConditionProvider.get());
    }
}
