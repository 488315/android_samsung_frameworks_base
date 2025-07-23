package com.android.wm.shell.dagger;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.wm.shell.compatui.api.CompatUISpec;
import com.android.wm.shell.compatui.components.RestartButtonSpecKt;
import com.android.wm.shell.compatui.impl.DefaultCompatUIRepository;
import dagger.internal.Provider;
import java.util.LinkedHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideCompatUIRepositoryFactory implements Provider {
    public static DefaultCompatUIRepository provideCompatUIRepository() {
        DefaultCompatUIRepository defaultCompatUIRepository = new DefaultCompatUIRepository();
        CompatUISpec compatUISpec = RestartButtonSpecKt.RestartButtonSpec;
        Object obj = ((LinkedHashMap) defaultCompatUIRepository.allSpecs).get(compatUISpec.name);
        String str = compatUISpec.name;
        if (obj != null) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Spec with id:", str, " already present"));
        }
        defaultCompatUIRepository.allSpecs.put(str, compatUISpec);
        return defaultCompatUIRepository;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideCompatUIRepository();
    }
}
