package com.android.systemui.media.mediaoutput.dagger;

import android.content.Context;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.PreferenceDataStoreFile;
import androidx.datastore.preferences.core.PreferenceDataStore;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class MediaOutputModule_Companion_ProvideDataStoreFactory implements Provider {
    public final Provider contextProvider;

    public MediaOutputModule_Companion_ProvideDataStoreFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PreferenceDataStore provideDataStore(final Context context) {
        MediaOutputModule.Companion.getClass();
        PreferenceDataStoreFactory preferenceDataStoreFactory = PreferenceDataStoreFactory.INSTANCE;
        ReplaceFileCorruptionHandler replaceFileCorruptionHandler = new ReplaceFileCorruptionHandler(new MediaOutputModule$Companion$$ExternalSyntheticLambda0());
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        DefaultIoScheduler defaultIoScheduler = DefaultIoScheduler.INSTANCE;
        SupervisorJobImpl supervisorJobImplSupervisorJob$default = SupervisorKt.SupervisorJob$default();
        defaultIoScheduler.getClass();
        return PreferenceDataStoreFactory.create$default(preferenceDataStoreFactory, replaceFileCorruptionHandler, CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultIoScheduler, supervisorJobImplSupervisorJob$default)), new Function0() { // from class: com.android.systemui.media.mediaoutput.dagger.MediaOutputModule$Companion$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                return PreferenceDataStoreFile.preferencesDataStoreFile(context2.getApplicationContext(), context2.getPackageName() + "_media_output");
            }
        });
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDataStore((Context) this.contextProvider.get());
    }
}
