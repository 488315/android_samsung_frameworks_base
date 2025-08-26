package com.android.systemui.qs.pipeline.dagger;

import android.content.Context;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.PreferenceDataStoreFile;
import androidx.datastore.preferences.core.PreferenceDataStore;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import dagger.internal.Provider;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class QSPipelineModule_Companion_ProvideDataStoreFactory implements Provider {
    public final Provider contextProvider;
    public final Provider coroutineScopeProvider;

    public QSPipelineModule_Companion_ProvideDataStoreFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.coroutineScopeProvider = provider2;
    }

    public static PreferenceDataStore provideDataStore(final Context context, CoroutineScope coroutineScope) {
        QSPipelineModule.Companion.getClass();
        return PreferenceDataStoreFactory.create$default(PreferenceDataStoreFactory.INSTANCE, new ReplaceFileCorruptionHandler(new QSPipelineModule$Companion$$ExternalSyntheticLambda0()), coroutineScope, new Function0() { // from class: com.android.systemui.qs.pipeline.dagger.QSPipelineModule$Companion$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                return PreferenceDataStoreFile.preferencesDataStoreFile(context2.getApplicationContext(), context2.getPackageName() + "_compose_qs_grid_tile");
            }
        });
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDataStore((Context) this.contextProvider.get(), (CoroutineScope) this.coroutineScopeProvider.get());
    }
}
