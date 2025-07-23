package androidx.datastore.preferences.core;

import androidx.datastore.core.DataStore;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PreferenceDataStore implements DataStore {
    public final DataStore delegate;

    public PreferenceDataStore(DataStore dataStore) {
        this.delegate = dataStore;
    }

    @Override // androidx.datastore.core.DataStore
    public final Flow getData() {
        return this.delegate.getData();
    }

    @Override // androidx.datastore.core.DataStore
    public final Object updateData(Function2 function2, Continuation continuation) {
        return this.delegate.updateData(new PreferenceDataStore$updateData$2(function2, null), continuation);
    }
}
