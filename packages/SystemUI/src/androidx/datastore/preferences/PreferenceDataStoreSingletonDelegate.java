package androidx.datastore.preferences;

import android.content.Context;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.core.PreferenceDataStore;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class PreferenceDataStoreSingletonDelegate implements ReadOnlyProperty {
    public volatile PreferenceDataStore INSTANCE;
    public final ReplaceFileCorruptionHandler corruptionHandler;
    public final Object lock = new Object();
    public final String name;
    public final Function1 produceMigrations;
    public final CoroutineScope scope;

    public PreferenceDataStoreSingletonDelegate(String str, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, Function1 function1, CoroutineScope coroutineScope) {
        this.name = str;
        this.corruptionHandler = replaceFileCorruptionHandler;
        this.produceMigrations = function1;
        this.scope = coroutineScope;
    }

    @Override // kotlin.properties.ReadOnlyProperty
    public final Object getValue(Object obj, KProperty kProperty) {
        PreferenceDataStore preferenceDataStore;
        Context context = (Context) obj;
        PreferenceDataStore preferenceDataStore2 = this.INSTANCE;
        if (preferenceDataStore2 != null) {
            return preferenceDataStore2;
        }
        synchronized (this.lock) {
            try {
                if (this.INSTANCE == null) {
                    final Context applicationContext = context.getApplicationContext();
                    PreferenceDataStoreFactory preferenceDataStoreFactory = PreferenceDataStoreFactory.INSTANCE;
                    ReplaceFileCorruptionHandler replaceFileCorruptionHandler = this.corruptionHandler;
                    Function1 function1 = this.produceMigrations;
                    applicationContext.getClass();
                    List list = (List) function1.mo781invoke(applicationContext);
                    CoroutineScope coroutineScope = this.scope;
                    Function0 function0 = new Function0() { // from class: androidx.datastore.preferences.PreferenceDataStoreSingletonDelegate$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Context context2 = applicationContext;
                            context2.getClass();
                            return PreferenceDataStoreFile.preferencesDataStoreFile(context2, this.name);
                        }
                    };
                    preferenceDataStoreFactory.getClass();
                    this.INSTANCE = PreferenceDataStoreFactory.create(replaceFileCorruptionHandler, list, coroutineScope, function0);
                }
                preferenceDataStore = this.INSTANCE;
                preferenceDataStore.getClass();
            } catch (Throwable th) {
                throw th;
            }
        }
        return preferenceDataStore;
    }
}
