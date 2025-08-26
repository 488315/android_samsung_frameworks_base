package androidx.compose.foundation.lazy.layout;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.layout.PinnableContainer;
import androidx.compose.ui.layout.PinnableContainerKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class LazyLayoutPinnableItemKt {
    /* JADX WARN: Removed duplicated region for block: B:44:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyLayoutPinnableItem(final Object obj, final int i, final LazyLayoutPinnedItemList lazyLayoutPinnedItemList, final Function2 function2, Composer composer, final int i2) {
        int i3;
        LazyLayoutPinnableItem lazyLayoutPinnableItem;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2079116560);
        if ((i2 & 6) == 0) {
            i3 = (composerImpl.changedInstance(obj) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerImpl.changed(i) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerImpl.changedInstance(lazyLayoutPinnedItemList) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 2048 : 1024;
        }
        if (composerImpl.shouldExecute(1 & i3, (i3 & 1171) != 1170)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.LazyLayoutPinnableItem (LazyLayoutPinnableItem.kt:50)");
            }
            boolean zChanged = composerImpl.changed(obj) | composerImpl.changed(lazyLayoutPinnedItemList);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new LazyLayoutPinnableItem(obj, lazyLayoutPinnedItemList);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                final LazyLayoutPinnableItem lazyLayoutPinnableItem2 = (LazyLayoutPinnableItem) objRememberedValue;
                lazyLayoutPinnableItem2.index = i;
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = PinnableContainerKt.LocalPinnableContainer;
                PinnableContainer pinnableContainer = (PinnableContainer) composerImpl.consume(dynamicProvidableCompositionLocal);
                Snapshot.Companion.getClass();
                Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                MutableState mutableState = lazyLayoutPinnableItem2._parentPinnableContainer$delegate;
                try {
                    if (pinnableContainer != ((PinnableContainer) ((SnapshotMutableStateImpl) mutableState).getValue())) {
                        ((SnapshotMutableStateImpl) mutableState).setValue(pinnableContainer);
                        if (lazyLayoutPinnableItem2.pinsCount > 0) {
                            PinnableContainer.PinnedHandle pinnedHandle = lazyLayoutPinnableItem2.parentHandle;
                            if (pinnedHandle != null) {
                                ((LazyLayoutPinnableItem) pinnedHandle).release();
                            }
                            if (pinnableContainer != null) {
                                lazyLayoutPinnableItem = (LazyLayoutPinnableItem) pinnableContainer;
                                lazyLayoutPinnableItem.pin();
                            } else {
                                lazyLayoutPinnableItem = null;
                            }
                            lazyLayoutPinnableItem2.parentHandle = lazyLayoutPinnableItem;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                    boolean zChanged2 = composerImpl.changed(lazyLayoutPinnableItem2);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChanged2) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutPinnableItemKt$LazyLayoutPinnableItem$1$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj2) {
                                    final LazyLayoutPinnableItem lazyLayoutPinnableItem3 = lazyLayoutPinnableItem2;
                                    return new DisposableEffectResult() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutPinnableItemKt$LazyLayoutPinnableItem$1$1$invoke$$inlined$onDispose$1
                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                        public final void dispose() {
                                            lazyLayoutPinnableItem3.isDisposed = true;
                                        }
                                    };
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        EffectsKt.DisposableEffect(lazyLayoutPinnableItem2, (Function1) objRememberedValue2, composerImpl);
                        CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(lazyLayoutPinnableItem2), function2, composerImpl, ((i3 >> 6) & 112) | 8);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                } catch (Throwable th) {
                    Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                    throw th;
                }
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutPinnableItemKt.LazyLayoutPinnableItem.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    LazyLayoutPinnableItemKt.LazyLayoutPinnableItem(obj, i, lazyLayoutPinnedItemList, function2, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
