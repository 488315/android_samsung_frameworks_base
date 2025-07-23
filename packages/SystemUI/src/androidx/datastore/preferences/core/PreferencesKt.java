package androidx.datastore.preferences.core;

import androidx.datastore.core.DataStore;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PreferencesKt {
    public static final Object edit(DataStore dataStore, Function2 function2, Continuation continuation) {
        return dataStore.updateData(new PreferencesKt$edit$2(function2, null), continuation);
    }
}
