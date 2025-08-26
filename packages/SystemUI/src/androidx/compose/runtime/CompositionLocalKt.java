package androidx.compose.runtime;

import androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.runtime.internal.PersistentCompositionLocalMapKt;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class CompositionLocalKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.compose.runtime.PersistentCompositionLocalMap, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CompositionLocalProvider(final ProvidedValue[] providedValueArr, final Function2 function2, Composer composer, final int i) {
        PersistentCompositionLocalHashMap persistentCompositionLocalHashMapUpdateProviderMapGroup;
        boolean z;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1390796515);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.CompositionLocalProvider (CompositionLocal.kt:361)");
        }
        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        composerImpl.startGroup(201, ComposerKt.provider);
        if (composerImpl.inserting) {
            persistentCompositionLocalHashMapUpdateProviderMapGroup = composerImpl.updateProviderMapGroup(persistentCompositionLocalMapCurrentCompositionLocalScope, CompositionLocalMapKt.updateCompositionMap(providedValueArr, persistentCompositionLocalMapCurrentCompositionLocalScope, PersistentCompositionLocalMapKt.persistentCompositionLocalHashMapOf()));
            composerImpl.writerHasAProvider = true;
        } else {
            SlotReader slotReader = composerImpl.reader;
            ?? r1 = (PersistentCompositionLocalMap) slotReader.groupGet(slotReader.currentGroup, 0);
            SlotReader slotReader2 = composerImpl.reader;
            PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) slotReader2.groupGet(slotReader2.currentGroup, 1);
            PersistentCompositionLocalHashMap persistentCompositionLocalHashMapUpdateCompositionMap = CompositionLocalMapKt.updateCompositionMap(providedValueArr, persistentCompositionLocalMapCurrentCompositionLocalScope, persistentCompositionLocalMap);
            if (!composerImpl.getSkipping() || composerImpl.reusing || !Intrinsics.areEqual(persistentCompositionLocalMap, persistentCompositionLocalHashMapUpdateCompositionMap)) {
                persistentCompositionLocalHashMapUpdateProviderMapGroup = composerImpl.updateProviderMapGroup(persistentCompositionLocalMapCurrentCompositionLocalScope, persistentCompositionLocalHashMapUpdateCompositionMap);
                if (composerImpl.reusing || !Intrinsics.areEqual(persistentCompositionLocalHashMapUpdateProviderMapGroup, (Object) r1)) {
                    z = true;
                }
                if (z && !composerImpl.inserting) {
                    composerImpl.recordProviderUpdate(persistentCompositionLocalHashMapUpdateProviderMapGroup);
                }
                boolean z2 = composerImpl.providersInvalid;
                IntStack intStack = composerImpl.providersInvalidStack;
                intStack.push(z2 ? 1 : 0);
                composerImpl.providersInvalid = z;
                composerImpl.providerCache = persistentCompositionLocalHashMapUpdateProviderMapGroup;
                OpaqueKey opaqueKey = ComposerKt.compositionLocalMap;
                GroupKind.Companion.getClass();
                composerImpl.m332startBaiHCIY(202, 0, opaqueKey, persistentCompositionLocalHashMapUpdateProviderMapGroup);
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i >> 3) & 14, function2, composerImpl, false, false);
                composerImpl.providersInvalid = intStack.pop() != 0;
                composerImpl.providerCache = null;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ProvidedValue<?>[] providedValueArr2 = providedValueArr;
                            CompositionLocalKt.CompositionLocalProvider((ProvidedValue[]) Arrays.copyOf(providedValueArr2, providedValueArr2.length), function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            composerImpl.groupNodeCount = composerImpl.reader.skipGroup() + composerImpl.groupNodeCount;
            persistentCompositionLocalHashMapUpdateProviderMapGroup = r1;
        }
        z = false;
        if (z) {
            composerImpl.recordProviderUpdate(persistentCompositionLocalHashMapUpdateProviderMapGroup);
        }
        boolean z22 = composerImpl.providersInvalid;
        IntStack intStack2 = composerImpl.providersInvalidStack;
        intStack2.push(z22 ? 1 : 0);
        composerImpl.providersInvalid = z;
        composerImpl.providerCache = persistentCompositionLocalHashMapUpdateProviderMapGroup;
        OpaqueKey opaqueKey2 = ComposerKt.compositionLocalMap;
        GroupKind.Companion.getClass();
        composerImpl.m332startBaiHCIY(202, 0, opaqueKey2, persistentCompositionLocalHashMapUpdateProviderMapGroup);
        SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i >> 3) & 14, function2, composerImpl, false, false);
        composerImpl.providersInvalid = intStack2.pop() != 0;
        composerImpl.providerCache = null;
        if (ComposerKt.isTraceInProgress()) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static DynamicProvidableCompositionLocal compositionLocalOf$default(Function0 function0) {
        return new DynamicProvidableCompositionLocal(StructuralEqualityPolicy.INSTANCE, function0);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CompositionLocalProvider(final ProvidedValue providedValue, final Function2 function2, Composer composer, final int i) {
        boolean z;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1350970552);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.CompositionLocalProvider (CompositionLocal.kt:381)");
        }
        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        composerImpl.startGroup(201, ComposerKt.provider);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        ValueHolder valueHolder = Intrinsics.areEqual(objRememberedValue, Composer.Companion.Empty) ? null : (ValueHolder) objRememberedValue;
        CompositionLocal compositionLocal = providedValue.compositionLocal;
        ValueHolder valueHolderUpdatedStateOf$runtime_release = compositionLocal.updatedStateOf$runtime_release(providedValue, valueHolder);
        boolean zEquals = valueHolderUpdatedStateOf$runtime_release.equals(valueHolder);
        if (!zEquals) {
            composerImpl.updateRememberedValue(valueHolderUpdatedStateOf$runtime_release);
        }
        if (composerImpl.inserting) {
            if (providedValue.canOverride || !persistentCompositionLocalMapCurrentCompositionLocalScope.containsKey(compositionLocal)) {
                persistentCompositionLocalMapCurrentCompositionLocalScope = ((PersistentCompositionLocalHashMap) persistentCompositionLocalMapCurrentCompositionLocalScope).putValue(compositionLocal, valueHolderUpdatedStateOf$runtime_release);
            }
            composerImpl.writerHasAProvider = true;
        } else {
            SlotReader slotReader = composerImpl.reader;
            PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) slotReader.aux(slotReader.currentGroup, slotReader.groups);
            if ((composerImpl.getSkipping() && zEquals) || (!providedValue.canOverride && persistentCompositionLocalMapCurrentCompositionLocalScope.containsKey(compositionLocal))) {
                if ((zEquals && !composerImpl.providersInvalid) || !composerImpl.providersInvalid) {
                    persistentCompositionLocalMapCurrentCompositionLocalScope = persistentCompositionLocalMap;
                }
            } else {
                persistentCompositionLocalMapCurrentCompositionLocalScope = ((PersistentCompositionLocalHashMap) persistentCompositionLocalMapCurrentCompositionLocalScope).putValue(compositionLocal, valueHolderUpdatedStateOf$runtime_release);
            }
            if (composerImpl.reusing || persistentCompositionLocalMap != persistentCompositionLocalMapCurrentCompositionLocalScope) {
                z = true;
            }
            if (z && !composerImpl.inserting) {
                composerImpl.recordProviderUpdate(persistentCompositionLocalMapCurrentCompositionLocalScope);
            }
            boolean z2 = composerImpl.providersInvalid;
            IntStack intStack = composerImpl.providersInvalidStack;
            intStack.push(z2 ? 1 : 0);
            composerImpl.providersInvalid = z;
            composerImpl.providerCache = persistentCompositionLocalMapCurrentCompositionLocalScope;
            Object obj = ComposerKt.compositionLocalMap;
            GroupKind.Companion.getClass();
            composerImpl.m332startBaiHCIY(202, 0, obj, persistentCompositionLocalMapCurrentCompositionLocalScope);
            SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i >> 3) & 14, function2, composerImpl, false, false);
            composerImpl.providersInvalid = intStack.pop() != 0;
            composerImpl.providerCache = null;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        ((Number) obj3).intValue();
                        CompositionLocalKt.CompositionLocalProvider(providedValue, function2, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        z = false;
        if (z) {
            composerImpl.recordProviderUpdate(persistentCompositionLocalMapCurrentCompositionLocalScope);
        }
        boolean z22 = composerImpl.providersInvalid;
        IntStack intStack2 = composerImpl.providersInvalidStack;
        intStack2.push(z22 ? 1 : 0);
        composerImpl.providersInvalid = z;
        composerImpl.providerCache = persistentCompositionLocalMapCurrentCompositionLocalScope;
        Object obj2 = ComposerKt.compositionLocalMap;
        GroupKind.Companion.getClass();
        composerImpl.m332startBaiHCIY(202, 0, obj2, persistentCompositionLocalMapCurrentCompositionLocalScope);
        SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i >> 3) & 14, function2, composerImpl, false, false);
        composerImpl.providersInvalid = intStack2.pop() != 0;
        composerImpl.providerCache = null;
        if (ComposerKt.isTraceInProgress()) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
