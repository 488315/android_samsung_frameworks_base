package androidx.navigation.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.InspectionModeKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.navigation.NavBackStackEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DialogHostKt {
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007d, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void DialogHost(androidx.navigation.compose.DialogNavigator r17, androidx.compose.runtime.Composer r18, final int r19) {
        /*
            Method dump skipped, instructions count: 403
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.compose.DialogHostKt.DialogHost(androidx.navigation.compose.DialogNavigator, androidx.compose.runtime.Composer, int):void");
    }

    public static final void PopulateVisibleList(final List list, final Collection collection, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1537894851);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(list) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(collection) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.navigation.compose.PopulateVisibleList (DialogHost.kt:88)");
            }
            final boolean booleanValue = ((Boolean) composerImpl.consume(InspectionModeKt.LocalInspectionMode)).booleanValue();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                final NavBackStackEntry navBackStackEntry = (NavBackStackEntry) it.next();
                LifecycleRegistry lifecycleRegistry = navBackStackEntry._lifecycle;
                boolean changed = composerImpl.changed(booleanValue) | composerImpl.changedInstance(list) | composerImpl.changedInstance(navBackStackEntry);
                Object rememberedValue = composerImpl.rememberedValue();
                if (!changed) {
                    Composer.Companion.getClass();
                    if (rememberedValue != Composer.Companion.Empty) {
                        EffectsKt.DisposableEffect(lifecycleRegistry, (Function1) rememberedValue, composerImpl);
                    }
                }
                rememberedValue = new Function1() { // from class: androidx.navigation.compose.DialogHostKt$PopulateVisibleList$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        final boolean z = booleanValue;
                        final List<NavBackStackEntry> list2 = list;
                        final NavBackStackEntry navBackStackEntry2 = NavBackStackEntry.this;
                        final LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.navigation.compose.DialogHostKt$PopulateVisibleList$1$1$1$$ExternalSyntheticLambda0
                            @Override // androidx.lifecycle.LifecycleEventObserver
                            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                                List list3 = list2;
                                boolean z2 = z;
                                NavBackStackEntry navBackStackEntry3 = navBackStackEntry2;
                                if (z2 && !list3.contains(navBackStackEntry3)) {
                                    list3.add(navBackStackEntry3);
                                }
                                if (event == Lifecycle.Event.ON_START && !list3.contains(navBackStackEntry3)) {
                                    list3.add(navBackStackEntry3);
                                }
                                if (event == Lifecycle.Event.ON_STOP) {
                                    list3.remove(navBackStackEntry3);
                                }
                            }
                        };
                        navBackStackEntry2._lifecycle.addObserver(lifecycleEventObserver);
                        final NavBackStackEntry navBackStackEntry3 = NavBackStackEntry.this;
                        return new DisposableEffectResult() { // from class: androidx.navigation.compose.DialogHostKt$PopulateVisibleList$1$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                NavBackStackEntry.this._lifecycle.removeObserver(lifecycleEventObserver);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue);
                EffectsKt.DisposableEffect(lifecycleRegistry, (Function1) rememberedValue, composerImpl);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.navigation.compose.DialogHostKt$PopulateVisibleList$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    DialogHostKt.PopulateVisibleList(list, collection, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
