package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import android.util.IndentingPrintWriter;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.res.ColorResources_androidKt;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.notifications.ui.composable.Notifications$Elements;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.shade.ui.composable.Shade$Elements;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationLockscreenScrimViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public static final Companion Companion = new Companion(null);
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final State element$delegate;
    public final Hydrator hydrator;
    public final ReadonlyStateFlow shadeMode;
    public final NotificationStackAppearanceInteractor stackAppearanceInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ElementViewModel {
        public final Function3 color;
        public final ElementKey key;

        public ElementViewModel(ElementKey elementKey, Function3 function3) {
            this.key = elementKey;
            this.color = function3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ElementViewModel)) {
                return false;
            }
            ElementViewModel elementViewModel = (ElementViewModel) obj;
            return Intrinsics.areEqual(this.key, elementViewModel.key) && Intrinsics.areEqual(this.color, elementViewModel.color);
        }

        public final int hashCode() {
            return this.color.hashCode() + (this.key.identity.hashCode() * 31);
        }

        public final String toString() {
            return "ElementViewModel(key=" + this.key + ", color=" + this.color + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public NotificationLockscreenScrimViewModel(DumpManager dumpManager, ShadeModeInteractor shadeModeInteractor, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor) {
        this.$$delegate_0 = new ActivatableFlowDumperImpl(dumpManager, "NotificationScrollViewModel");
        this.stackAppearanceInteractor = notificationStackAppearanceInteractor;
        Hydrator hydrator = new Hydrator("NotificationLockscreenScrimViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        final ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode;
        this.shadeMode = readonlyStateFlow;
        this.element$delegate = hydrator.hydratedStateOf("elementKey", element((ShadeMode) readonlyStateFlow.$$delegate_0.getValue()), new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationLockscreenScrimViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, NotificationLockscreenScrimViewModel notificationLockscreenScrimViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationLockscreenScrimViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.shade.shared.model.ShadeMode r5 = (com.android.systemui.shade.shared.model.ShadeMode) r5
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$Companion r6 = com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.Companion
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel r6 = r4.this$0
                        r6.getClass()
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$ElementViewModel r5 = com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.element(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    public static ElementViewModel element(ShadeMode shadeMode) {
        if (Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
            Notifications$Elements.INSTANCE.getClass();
            return new ElementViewModel(Notifications$Elements.NotificationScrim, new Function3() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$element$4
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Boolean) obj).getClass();
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                    composerImpl.startReplaceGroup(1867377109);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.element.<anonymous> (NotificationLockscreenScrimViewModel.kt:83)");
                    }
                    NotificationLockscreenScrimViewModel.Companion.getClass();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.Companion.<get-SingleShadeBackground> (NotificationLockscreenScrimViewModel.kt:114)");
                    }
                    MaterialTheme.INSTANCE.getClass();
                    long j = MaterialTheme.getColorScheme(composerImpl).surface;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    return Color.m454boximpl(j);
                }
            });
        }
        Shade$Elements.INSTANCE.getClass();
        return new ElementViewModel(Shade$Elements.BackgroundScrim, new Function3() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$element$5
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                long colorResource;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(-1769572002);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.element.<anonymous> (NotificationLockscreenScrimViewModel.kt:89)");
                }
                if (booleanValue) {
                    composerImpl.startReplaceGroup(-517273929);
                    NotificationLockscreenScrimViewModel.Companion.getClass();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.Companion.<get-SplitShadeBouncerToLockscreenBackground> (NotificationLockscreenScrimViewModel.kt:117)");
                    }
                    MaterialTheme.INSTANCE.getClass();
                    colorResource = MaterialTheme.getColorScheme(composerImpl).surface;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(-517182045);
                    NotificationLockscreenScrimViewModel.Companion.getClass();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.Companion.<get-SplitShadeDefaultBackground> (NotificationLockscreenScrimViewModel.kt:122)");
                    }
                    colorResource = ColorResources_androidKt.colorResource(R.color.shade_scrim_background_dark, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return Color.m454boximpl(colorResource);
            }
        });
    }

    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    public final Object activateFlowDumper(Continuation continuation) {
        return this.$$delegate_0.activateFlowDumper(continuation);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.$$delegate_0.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        this.$$delegate_0.dumpFlows(indentingPrintWriter);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final SharedFlow dumpReplayCache(SharedFlow sharedFlow, String str) {
        return this.$$delegate_0.dumpReplayCache(sharedFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final StateFlow dumpValue(StateFlow stateFlow, String str) {
        return this.$$delegate_0.dumpValue(stateFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final Flow dumpWhileCollecting(Flow flow, String str) {
        return this.$$delegate_0.dumpWhileCollecting(flow, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2b:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1 r6 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1
            r2 = 0
            java.lang.String r4 = "NotificationLockscreenScrimViewModel"
            r6.<init>(r2, r4, r5)
            r0.label = r3
            java.lang.Object r5 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r5 = new kotlin.KotlinNothingValueException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
