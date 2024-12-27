package com.android.systemui.qs.pipeline.dagger;

import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.qs.pipeline.domain.autoaddable.AutoAddableSetting;
import com.android.systemui.qs.pipeline.domain.autoaddable.AutoAddableSettingList;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public final class BaseAutoAddableModule_Companion_ProvidesAutoAddableSettingFactory implements Provider {
    public final javax.inject.Provider autoAddableSettingFactoryProvider;
    public final javax.inject.Provider resourcesProvider;

    public BaseAutoAddableModule_Companion_ProvidesAutoAddableSettingFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.resourcesProvider = provider;
        this.autoAddableSettingFactoryProvider = provider2;
    }

    public static Set providesAutoAddableSetting(Resources resources, DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass48 anonymousClass48) {
        BaseAutoAddableModule.Companion.getClass();
        AutoAddableSettingList.INSTANCE.getClass();
        String[] stringArray = resources.getStringArray(R.array.config_quickSettingsAutoAdd);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            Intrinsics.checkNotNull(str);
            List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, 2, 2);
            AutoAddableSetting autoAddableSetting = null;
            if (split$default.size() == 2) {
                String str2 = (String) split$default.get(0);
                String str3 = (String) split$default.get(1);
                TileSpec.Companion.getClass();
                if (Intrinsics.areEqual(TileSpec.Companion.create(str3), TileSpec.Invalid.INSTANCE)) {
                    Log.w("AutoAddableSettingList", "Malformed item in array: ".concat(str));
                } else {
                    autoAddableSetting = anonymousClass48.create(str2, TileSpec.Companion.create(str3));
                }
            } else {
                Log.w("AutoAddableSettingList", "Malformed item in array: ".concat(str));
            }
            if (autoAddableSetting != null) {
                arrayList.add(autoAddableSetting);
            }
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
        Preconditions.checkNotNullFromProvides(set);
        return set;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesAutoAddableSetting((Resources) this.resourcesProvider.get(), (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass48) this.autoAddableSettingFactoryProvider.get());
    }
}
