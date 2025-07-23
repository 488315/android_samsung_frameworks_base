package com.android.systemui.qs.pipeline.dagger;

import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.qs.pipeline.domain.autoaddable.AutoAddableSetting;
import com.android.systemui.qs.pipeline.domain.autoaddable.AutoAddableSettingList;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BaseAutoAddableModule_Companion_ProvidesAutoAddableSettingFactory implements Provider {
    public final Provider autoAddableSettingFactoryProvider;
    public final Provider resourcesProvider;

    public BaseAutoAddableModule_Companion_ProvidesAutoAddableSettingFactory(Provider provider, Provider provider2) {
        this.resourcesProvider = provider;
        this.autoAddableSettingFactoryProvider = provider2;
    }

    public static Set providesAutoAddableSetting(Resources resources, DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass156 anonymousClass156) {
        BaseAutoAddableModule.Companion.getClass();
        AutoAddableSettingList.INSTANCE.getClass();
        String[] stringArray = resources.getStringArray(R.array.config_quickSettingsAutoAdd);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            str.getClass();
            List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, 2, 2);
            AutoAddableSetting autoAddableSetting = null;
            if (split$default.size() == 2) {
                String str2 = (String) split$default.get(0);
                String str3 = (String) split$default.get(1);
                TileSpec.Companion.getClass();
                if (Intrinsics.areEqual(TileSpec.Companion.create(str3), TileSpec.Invalid.INSTANCE)) {
                    Log.w("AutoAddableSettingList", "Malformed item in array: ".concat(str));
                } else {
                    autoAddableSetting = anonymousClass156.create(str2, TileSpec.Companion.create(str3));
                }
            } else {
                Log.w("AutoAddableSettingList", "Malformed item in array: ".concat(str));
            }
            if (autoAddableSetting != null) {
                arrayList.add(autoAddableSetting);
            }
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
        set.getClass();
        return set;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesAutoAddableSetting((Resources) this.resourcesProvider.get(), (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass156) this.autoAddableSettingFactoryProvider.get());
    }
}
