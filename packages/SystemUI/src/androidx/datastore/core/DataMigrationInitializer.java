package androidx.datastore.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public final class DataMigrationInitializer {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0073  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0099  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
        /* JADX WARN: Type inference failed for: r5v4, types: [T, java.lang.Throwable] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x008a -> B:25:0x006d). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x008d -> B:25:0x006d). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final Object access$runMigrations(Companion companion, List list, DataStoreImpl$InitDataStore$doRun$initData$1$api$1 dataStoreImpl$InitDataStore$doRun$initData$1$api$1, ContinuationImpl continuationImpl) throws Throwable {
            DataMigrationInitializer$Companion$runMigrations$1 dataMigrationInitializer$Companion$runMigrations$1;
            List list2;
            Iterator it;
            Ref$ObjectRef ref$ObjectRef;
            Throwable th;
            companion.getClass();
            if (continuationImpl instanceof DataMigrationInitializer$Companion$runMigrations$1) {
                dataMigrationInitializer$Companion$runMigrations$1 = (DataMigrationInitializer$Companion$runMigrations$1) continuationImpl;
                int i = dataMigrationInitializer$Companion$runMigrations$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    dataMigrationInitializer$Companion$runMigrations$1.label = i - Integer.MIN_VALUE;
                } else {
                    dataMigrationInitializer$Companion$runMigrations$1 = new DataMigrationInitializer$Companion$runMigrations$1(companion, continuationImpl);
                }
            }
            Object obj = dataMigrationInitializer$Companion$runMigrations$1.result;
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = dataMigrationInitializer$Companion$runMigrations$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ArrayList arrayList = new ArrayList();
                Function2 dataMigrationInitializer$Companion$runMigrations$2 = new DataMigrationInitializer$Companion$runMigrations$2(list, arrayList, null);
                dataMigrationInitializer$Companion$runMigrations$1.L$0 = arrayList;
                dataMigrationInitializer$Companion$runMigrations$1.label = 1;
                if (dataStoreImpl$InitDataStore$doRun$initData$1$api$1.updateData(dataMigrationInitializer$Companion$runMigrations$2, dataMigrationInitializer$Companion$runMigrations$1) == obj2) {
                    return obj2;
                }
                list2 = arrayList;
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    it = (Iterator) dataMigrationInitializer$Companion$runMigrations$1.L$1;
                    ref$ObjectRef = (Ref$ObjectRef) dataMigrationInitializer$Companion$runMigrations$1.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                    } catch (Throwable th2) {
                        T t = ref$ObjectRef.element;
                        if (t == 0) {
                            ref$ObjectRef.element = th2;
                        } else {
                            ExceptionsKt__ExceptionsKt.addSuppressed((Throwable) t, th2);
                        }
                    }
                    while (it.hasNext()) {
                        Function1 function1 = (Function1) it.next();
                        dataMigrationInitializer$Companion$runMigrations$1.L$0 = ref$ObjectRef;
                        dataMigrationInitializer$Companion$runMigrations$1.L$1 = it;
                        dataMigrationInitializer$Companion$runMigrations$1.label = 2;
                        if (function1.mo781invoke(dataMigrationInitializer$Companion$runMigrations$1) == obj2) {
                            return obj2;
                        }
                    }
                    th = (Throwable) ref$ObjectRef.element;
                    if (th != null) {
                        return Unit.INSTANCE;
                    }
                    throw th;
                }
                list2 = (List) dataMigrationInitializer$Companion$runMigrations$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            it = list2.iterator();
            ref$ObjectRef = ref$ObjectRef2;
            while (it.hasNext()) {
            }
            th = (Throwable) ref$ObjectRef.element;
            if (th != null) {
            }
        }

        private Companion() {
        }
    }
}
