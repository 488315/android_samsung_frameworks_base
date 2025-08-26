package androidx.navigation.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import androidx.compose.runtime.saveable.SaveableStateHolderKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.platform.InspectionModeKt;
import androidx.compose.ui.window.AndroidDialog_androidKt;
import androidx.compose.ui.window.DialogProperties;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavigatorState;
import androidx.navigation.compose.DialogNavigator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class DialogHostKt {
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DialogHost(DialogNavigator dialogNavigator, Composer composer, final int i) {
        ComposerImpl composerImpl;
        final DialogNavigator dialogNavigator2;
        final DialogNavigator dialogNavigator3 = dialogNavigator;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(294589392);
        int i2 = (i & 6) == 0 ? (composerImpl2.changed(dialogNavigator3) ? 4 : 2) | i : i;
        if ((i2 & 3) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
            dialogNavigator2 = dialogNavigator3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.navigation.compose.DialogHost (DialogHost.kt:40)");
            }
            final SaveableStateHolder saveableStateHolderRememberSaveableStateHolder = SaveableStateHolderKt.rememberSaveableStateHolder(composerImpl2);
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(dialogNavigator3.getState().backStack, composerImpl2);
            Object obj = (Collection) ((List) mutableStateCollectAsState.getValue());
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.navigation.compose.rememberVisibleList (DialogHost.kt:119)");
            }
            boolean zBooleanValue = ((Boolean) composerImpl2.consume(InspectionModeKt.LocalInspectionMode)).booleanValue();
            boolean zChanged = composerImpl2.changed(obj);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                Object obj2 = objRememberedValue;
                if (objRememberedValue == Composer.Companion.Empty) {
                    SnapshotStateList snapshotStateList = new SnapshotStateList();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj3 : (Iterable) obj) {
                        if (zBooleanValue ? true : ((NavBackStackEntry) obj3)._lifecycle.state.isAtLeast(Lifecycle.State.STARTED)) {
                            arrayList.add(obj3);
                        }
                    }
                    snapshotStateList.addAll(arrayList);
                    composerImpl2.updateRememberedValue(snapshotStateList);
                    obj2 = snapshotStateList;
                }
                SnapshotStateList snapshotStateList2 = (SnapshotStateList) obj2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                PopulateVisibleList(snapshotStateList2, (List) mutableStateCollectAsState.getValue(), composerImpl2, 0);
                MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(dialogNavigator3.getState().transitionsInProgress, composerImpl2);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                companion.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new SnapshotStateList();
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                final SnapshotStateList snapshotStateList3 = (SnapshotStateList) objRememberedValue2;
                composerImpl2.startReplaceGroup(1361037007);
                ListIterator listIterator = snapshotStateList2.listIterator();
                ComposerImpl composerImpl3 = composerImpl2;
                while (listIterator.hasNext()) {
                    final NavBackStackEntry navBackStackEntry = (NavBackStackEntry) listIterator.next();
                    final DialogNavigator.Destination destination = (DialogNavigator.Destination) navBackStackEntry.destination;
                    boolean zChangedInstance = ((i2 & 14) == 4) | composerImpl3.changedInstance(navBackStackEntry);
                    Object objRememberedValue3 = composerImpl3.rememberedValue();
                    if (zChangedInstance || objRememberedValue3 == Composer.Companion.Empty) {
                        objRememberedValue3 = new Function0() { // from class: androidx.navigation.compose.DialogHostKt$DialogHost$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                dialogNavigator3.popBackStack(navBackStackEntry, false);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl3.updateRememberedValue(objRememberedValue3);
                    }
                    DialogProperties dialogProperties = destination.dialogProperties;
                    SaveableStateHolder saveableStateHolder = saveableStateHolderRememberSaveableStateHolder;
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(1129586364, new Function2() { // from class: androidx.navigation.compose.DialogHostKt$DialogHost$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x004a  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj4, Object obj5) {
                            Composer composer2 = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 3) == 2) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                if (composerImpl4.getSkipping()) {
                                    composerImpl4.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.navigation.compose.DialogHost.<anonymous>.<anonymous> (DialogHost.kt:55)");
                                    }
                                    NavBackStackEntry navBackStackEntry2 = navBackStackEntry;
                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                    boolean zChangedInstance2 = composerImpl5.changedInstance(navBackStackEntry2) | composerImpl5.changed(dialogNavigator3);
                                    final SnapshotStateList<NavBackStackEntry> snapshotStateList4 = snapshotStateList3;
                                    final NavBackStackEntry navBackStackEntry3 = navBackStackEntry;
                                    final DialogNavigator dialogNavigator4 = dialogNavigator3;
                                    Object objRememberedValue4 = composerImpl5.rememberedValue();
                                    if (!zChangedInstance2) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.navigation.compose.DialogHostKt$DialogHost$1$2$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj6) {
                                                    snapshotStateList4.add(navBackStackEntry3);
                                                    final DialogNavigator dialogNavigator5 = dialogNavigator4;
                                                    final NavBackStackEntry navBackStackEntry4 = navBackStackEntry3;
                                                    final SnapshotStateList<NavBackStackEntry> snapshotStateList5 = snapshotStateList4;
                                                    return new DisposableEffectResult() { // from class: androidx.navigation.compose.DialogHostKt$DialogHost$1$2$1$1$invoke$$inlined$onDispose$1
                                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                                        public final void dispose() {
                                                            NavigatorState state = dialogNavigator5.getState();
                                                            NavBackStackEntry navBackStackEntry5 = navBackStackEntry4;
                                                            state.markTransitionComplete(navBackStackEntry5);
                                                            snapshotStateList5.remove(navBackStackEntry5);
                                                        }
                                                    };
                                                }
                                            };
                                            composerImpl5.updateRememberedValue(objRememberedValue4);
                                        }
                                        EffectsKt.DisposableEffect(navBackStackEntry2, (Function1) objRememberedValue4, composerImpl5);
                                        final NavBackStackEntry navBackStackEntry4 = navBackStackEntry;
                                        SaveableStateHolder saveableStateHolder2 = saveableStateHolderRememberSaveableStateHolder;
                                        final DialogNavigator.Destination destination2 = destination;
                                        NavBackStackEntryProviderKt.LocalOwnersProvider(navBackStackEntry4, saveableStateHolder2, ComposableLambdaKt.rememberComposableLambda(-497631156, new Function2() { // from class: androidx.navigation.compose.DialogHostKt$DialogHost$1$2.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                            @Override // kotlin.jvm.functions.Function2
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj6, Object obj7) {
                                                Composer composer3 = (Composer) obj6;
                                                if ((((Number) obj7).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                    if (composerImpl6.getSkipping()) {
                                                        composerImpl6.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.navigation.compose.DialogHost.<anonymous>.<anonymous>.<anonymous> (DialogHost.kt:66)");
                                                        }
                                                        destination2.content.invoke(navBackStackEntry4, composer3, 0);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl5), composerImpl5, 384);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl3);
                    ComposerImpl composerImpl4 = composerImpl3;
                    AndroidDialog_androidKt.Dialog((Function0) objRememberedValue3, dialogProperties, composableLambdaImplRememberComposableLambda, composerImpl4, 384, 0);
                    dialogNavigator3 = dialogNavigator;
                    snapshotStateList3 = snapshotStateList3;
                    composerImpl3 = composerImpl4;
                    saveableStateHolderRememberSaveableStateHolder = saveableStateHolder;
                }
                SnapshotStateList snapshotStateList4 = snapshotStateList3;
                composerImpl = composerImpl3;
                dialogNavigator2 = dialogNavigator3;
                composerImpl.end(false);
                Set set = (Set) mutableStateCollectAsState2.getValue();
                boolean zChanged2 = composerImpl.changed(mutableStateCollectAsState2) | ((i2 & 14) == 4);
                Object objRememberedValue4 = composerImpl.rememberedValue();
                if (zChanged2 || objRememberedValue4 == Composer.Companion.Empty) {
                    objRememberedValue4 = new DialogHostKt$DialogHost$2$1(mutableStateCollectAsState2, dialogNavigator2, snapshotStateList4, null);
                    composerImpl.updateRememberedValue(objRememberedValue4);
                }
                EffectsKt.LaunchedEffect(set, snapshotStateList4, (Function2) objRememberedValue4, composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.navigation.compose.DialogHostKt.DialogHost.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    ((Number) obj5).intValue();
                    DialogHostKt.DialogHost(dialogNavigator2, (Composer) obj4, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            final boolean zBooleanValue = ((Boolean) composerImpl.consume(InspectionModeKt.LocalInspectionMode)).booleanValue();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                final NavBackStackEntry navBackStackEntry = (NavBackStackEntry) it.next();
                LifecycleRegistry lifecycleRegistry = navBackStackEntry._lifecycle;
                boolean zChanged = composerImpl.changed(zBooleanValue) | composerImpl.changedInstance(list) | composerImpl.changedInstance(navBackStackEntry);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChanged) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: androidx.navigation.compose.DialogHostKt$PopulateVisibleList$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                final boolean z = zBooleanValue;
                                final List<NavBackStackEntry> list2 = list;
                                final NavBackStackEntry navBackStackEntry2 = navBackStackEntry;
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
                                final NavBackStackEntry navBackStackEntry3 = navBackStackEntry;
                                return new DisposableEffectResult() { // from class: androidx.navigation.compose.DialogHostKt$PopulateVisibleList$1$1$1$invoke$$inlined$onDispose$1
                                    @Override // androidx.compose.runtime.DisposableEffectResult
                                    public final void dispose() {
                                        navBackStackEntry3._lifecycle.removeObserver(lifecycleEventObserver);
                                    }
                                };
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                }
                EffectsKt.DisposableEffect(lifecycleRegistry, (Function1) objRememberedValue, composerImpl);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.navigation.compose.DialogHostKt.PopulateVisibleList.2
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
