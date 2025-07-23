package androidx.datastore.preferences.core;

import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.core.okio.OkioStorage;
import java.io.File;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import okio.FileSystem;
import okio.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PreferenceDataStoreFactory {
    public static final PreferenceDataStoreFactory INSTANCE = new PreferenceDataStoreFactory();

    private PreferenceDataStoreFactory() {
    }

    public static PreferenceDataStore create(ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, final Function0 function0) {
        OkioStorage okioStorage = new OkioStorage(FileSystem.SYSTEM, PreferencesSerializer.INSTANCE, null, new Function0() { // from class: androidx.datastore.preferences.core.PreferenceDataStoreFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PreferenceDataStoreFactory preferenceDataStoreFactory = PreferenceDataStoreFactory.INSTANCE;
                File file = (File) Function0.this.invoke();
                if (Intrinsics.areEqual(StringsKt__StringsKt.substringAfterLast(file.getName(), ""), "preferences_pb")) {
                    return Path.Companion.get$default(Path.Companion, file.getAbsoluteFile());
                }
                throw new IllegalStateException(("File extension for file: " + file + " does not match required extension for Preferences file: preferences_pb").toString());
            }
        }, 4, null);
        DataStoreFactory.INSTANCE.getClass();
        return new PreferenceDataStore(new PreferenceDataStore(DataStoreFactory.create(okioStorage, replaceFileCorruptionHandler, list, coroutineScope)));
    }

    public static PreferenceDataStore create$default(PreferenceDataStoreFactory preferenceDataStoreFactory, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, CoroutineScope coroutineScope, Function0 function0) {
        EmptyList emptyList = EmptyList.INSTANCE;
        preferenceDataStoreFactory.getClass();
        return create(replaceFileCorruptionHandler, emptyList, coroutineScope, function0);
    }
}
