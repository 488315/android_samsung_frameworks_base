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
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import java.io.PrintWriter;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class NotificationLockscreenScrimViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public static final Companion Companion = new Companion(null);
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final State element$delegate;
    public final Hydrator hydrator;
    public final ReadonlyStateFlow shadeMode;
    public final NotificationStackAppearanceInteractor stackAppearanceInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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

    public interface Factory {
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return NotificationLockscreenScrimViewModel.this.onActivated(this);
        }
    }

    public NotificationLockscreenScrimViewModel(DumpManager dumpManager, ShadeModeInteractor shadeModeInteractor, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor) {
        this.$$delegate_0 = new ActivatableFlowDumperImpl(dumpManager, "NotificationScrollViewModel");
        this.stackAppearanceInteractor = notificationStackAppearanceInteractor;
        Hydrator hydrator = new Hydrator("NotificationLockscreenScrimViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        final ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode;
        this.shadeMode = readonlyStateFlow;
        this.element$delegate = hydrator.hydratedStateOf("elementKey", element((ShadeMode) readonlyStateFlow.$$delegate_0.getValue()), new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        NotificationLockscreenScrimViewModel.Companion companion = NotificationLockscreenScrimViewModel.Companion;
                        this.this$0.getClass();
                        NotificationLockscreenScrimViewModel.ElementViewModel elementViewModelElement = NotificationLockscreenScrimViewModel.element((ShadeMode) obj);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(elementViewModelElement, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }

    public static ElementViewModel element(ShadeMode shadeMode) {
        if (Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
            Notifications$Elements.INSTANCE.getClass();
            return new ElementViewModel(Notifications$Elements.NotificationScrim, new Function3() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.element.4
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
                    return Color.m456boximpl(j);
                }
            });
        }
        Shade$Elements.INSTANCE.getClass();
        return new ElementViewModel(Shade$Elements.BackgroundScrim, new Function3() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.element.5
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                long jColorResource;
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(-1769572002);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.element.<anonymous> (NotificationLockscreenScrimViewModel.kt:89)");
                }
                if (zBooleanValue) {
                    composerImpl.startReplaceGroup(-517273929);
                    NotificationLockscreenScrimViewModel.Companion.getClass();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLockscreenScrimViewModel.Companion.<get-SplitShadeBouncerToLockscreenBackground> (NotificationLockscreenScrimViewModel.kt:117)");
                    }
                    MaterialTheme.INSTANCE.getClass();
                    jColorResource = MaterialTheme.getColorScheme(composerImpl).surface;
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
                    jColorResource = ColorResources_androidKt.colorResource(R.color.shade_scrim_background_dark, composerImpl);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return Color.m456boximpl(jColorResource);
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1 notificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1 = new NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1(null, "NotificationLockscreenScrimViewModel", this);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(notificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
