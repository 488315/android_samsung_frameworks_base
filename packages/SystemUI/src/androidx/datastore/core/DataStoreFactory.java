package androidx.datastore.core;

import androidx.datastore.core.handlers.NoOpCorruptionHandler;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import java.util.Collections;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class DataStoreFactory {
    public static final DataStoreFactory INSTANCE = new DataStoreFactory();

    private DataStoreFactory() {
    }

    public static DataStoreImpl create(Storage storage, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope) {
        CorruptionHandler noOpCorruptionHandler = replaceFileCorruptionHandler;
        if (replaceFileCorruptionHandler == null) {
            noOpCorruptionHandler = new NoOpCorruptionHandler();
        }
        DataMigrationInitializer.Companion.getClass();
        return new DataStoreImpl(storage, Collections.singletonList(new DataMigrationInitializer$Companion$getInitializer$1(list, null)), noOpCorruptionHandler, coroutineScope);
    }

    public static DataStoreImpl create$default(DataStoreFactory dataStoreFactory, Serializer serializer, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, CoroutineScope coroutineScope, Function0 function0, int i) {
        EmptyList emptyList = EmptyList.INSTANCE;
        if ((i & 8) != 0) {
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            DefaultIoScheduler defaultIoScheduler = DefaultIoScheduler.INSTANCE;
            SupervisorJobImpl supervisorJobImplSupervisorJob$default = SupervisorKt.SupervisorJob$default();
            defaultIoScheduler.getClass();
            coroutineScope = CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultIoScheduler, supervisorJobImplSupervisorJob$default));
        }
        dataStoreFactory.getClass();
        return create(new FileStorage(serializer, null, function0, 2, null), replaceFileCorruptionHandler, emptyList, coroutineScope);
    }
}
