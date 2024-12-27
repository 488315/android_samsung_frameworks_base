package com.android.systemui.media.mediaoutput.dagger;

import android.content.Context;
import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.PreferenceDataStore;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import dagger.internal.Provider;
import java.io.File;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

public final class MediaOutputModule_Companion_ProvideDataStoreFactory implements Provider {
    public final javax.inject.Provider contextProvider;

    public MediaOutputModule_Companion_ProvideDataStoreFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static PreferenceDataStore provideDataStore(final Context context) {
        MediaOutputModule.Companion.getClass();
        PreferenceDataStoreFactory preferenceDataStoreFactory = PreferenceDataStoreFactory.INSTANCE;
        ReplaceFileCorruptionHandler replaceFileCorruptionHandler = new ReplaceFileCorruptionHandler(new Function1() { // from class: com.android.systemui.media.mediaoutput.dagger.MediaOutputModule$Companion$provideDataStore$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((CorruptionException) obj).printStackTrace();
                return new MutablePreferences(null, true, 1, null);
            }
        });
        DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
        SupervisorJobImpl SupervisorJob$default = SupervisorKt.SupervisorJob$default();
        defaultIoScheduler.getClass();
        return PreferenceDataStoreFactory.create$default(preferenceDataStoreFactory, replaceFileCorruptionHandler, CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultIoScheduler, SupervisorJob$default)), new Function0() { // from class: com.android.systemui.media.mediaoutput.dagger.MediaOutputModule$Companion$provideDataStore$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new File(context.getApplicationContext().getApplicationContext().getFilesDir(), "datastore/".concat((context.getPackageName() + "_media_output").concat(".preferences_pb")));
            }
        });
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDataStore((Context) this.contextProvider.get());
    }
}
