package androidx.datastore.preferences.core;

import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PreferencesKt$edit$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $transform;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreferencesKt$edit$2(Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PreferencesKt$edit$2 preferencesKt$edit$2 = new PreferencesKt$edit$2(this.$transform, continuation);
        preferencesKt$edit$2.L$0 = obj;
        return preferencesKt$edit$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PreferencesKt$edit$2) create((Preferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
            ResultKt.throwOnFailure(obj);
            return mutablePreferences;
        }
        ResultKt.throwOnFailure(obj);
        MutablePreferences mutablePreferences2 = new MutablePreferences(new LinkedHashMap(((Preferences) this.L$0).asMap()), false);
        Function2 function2 = this.$transform;
        this.L$0 = mutablePreferences2;
        this.label = 1;
        return function2.invoke(mutablePreferences2, this) == coroutineSingletons ? coroutineSingletons : mutablePreferences2;
    }
}
