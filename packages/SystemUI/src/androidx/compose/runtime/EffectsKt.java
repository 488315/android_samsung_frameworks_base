package androidx.compose.runtime;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.changelist.ChangeList;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import java.util.Arrays;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes.dex */
public abstract class EffectsKt {
    public static final DisposableEffectScope InternalDisposableEffectScope = new DisposableEffectScope();

    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DisposableEffect(Object obj, Function1 function1, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.DisposableEffect (Effects.kt:150)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        boolean zChanged = composerImpl.changed(obj);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new DisposableEffectImpl(function1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LaunchedEffect(Composer composer, Object obj, Function2 function2) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.LaunchedEffect (Effects.kt:338)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        CoroutineContext coroutineContext = composerImpl.applyCoroutineContext;
        boolean zChanged = composerImpl.changed(obj);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new LaunchedEffectImpl(coroutineContext, function2);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    public static final void SideEffect(Function0 function0, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.SideEffect (Effects.kt:51)");
        }
        ChangeList changeList = ((ComposerImpl) composer).changeListWriter.changeList;
        changeList.getClass();
        Operation.SideEffect sideEffect = Operation.SideEffect.INSTANCE;
        Operations operations = changeList.operations;
        operations.pushOp(sideEffect);
        Operations.WriteScope.m339setObjectDKhxnng(operations, 0, function0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    public static final CoroutineScope createCompositionCoroutineScope(EmptyCoroutineContext emptyCoroutineContext, Composer composer) {
        Job.Key key = Job.Key;
        emptyCoroutineContext.getClass();
        return new RememberedCoroutineScope(((ComposerImpl) composer).applyCoroutineContext, emptyCoroutineContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DisposableEffect(Object obj, Object obj2, Function1 function1, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.DisposableEffect (Effects.kt:187)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        boolean zChanged = composerImpl.changed(obj) | composerImpl.changed(obj2);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new DisposableEffectImpl(function1);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LaunchedEffect(Object obj, Object obj2, Function2 function2, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.LaunchedEffect (Effects.kt:357)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        CoroutineContext coroutineContext = composerImpl.applyCoroutineContext;
        boolean zChanged = composerImpl.changed(obj) | composerImpl.changed(obj2);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new LaunchedEffectImpl(coroutineContext, function2);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DisposableEffect(Object[] objArr, Function1 function1, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.DisposableEffect (Effects.kt:261)");
        }
        boolean zChanged = false;
        for (Object obj : Arrays.copyOf(objArr, objArr.length)) {
            zChanged |= ((ComposerImpl) composer).changed(obj);
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                composerImpl.updateRememberedValue(new DisposableEffectImpl(function1));
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LaunchedEffect(Object obj, Object obj2, Object obj3, Function2 function2, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.LaunchedEffect (Effects.kt:376)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        CoroutineContext coroutineContext = composerImpl.applyCoroutineContext;
        boolean zChanged = composerImpl.changed(obj) | composerImpl.changed(obj2) | composerImpl.changed(obj3);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new LaunchedEffectImpl(coroutineContext, function2);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LaunchedEffect(Object[] objArr, Function2 function2, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.LaunchedEffect (Effects.kt:399)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        CoroutineContext coroutineContext = composerImpl.applyCoroutineContext;
        boolean zChanged = false;
        for (Object obj : Arrays.copyOf(objArr, objArr.length)) {
            zChanged |= composerImpl.changed(obj);
        }
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                composerImpl.updateRememberedValue(new LaunchedEffectImpl(coroutineContext, function2));
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }
}
