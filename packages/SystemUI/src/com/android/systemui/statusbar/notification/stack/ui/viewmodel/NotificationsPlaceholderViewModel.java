package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import android.util.IndentingPrintWriter;
import androidx.compose.runtime.State;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor$special$$inlined$mapNotNull$1;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import java.io.PrintWriter;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsPlaceholderViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final StateFlow expandFraction;
    public final HeadsUpNotificationInteractor headsUpNotificationInteractor;
    public final Hydrator hydrator;
    public final NotificationStackAppearanceInteractor interactor;
    public final State isCurrentGestureOverscroll$delegate;
    public final Flow isHeadsUpOrAnimatingAway;
    public final Flow isRemoteInputActive;
    public final State notificationsShadeContentKey$delegate;
    public final State quickSettingsShadeContentKey$delegate;
    public final RemoteInputInteractor$special$$inlined$mapNotNull$1 remoteInputRowBottomBound;
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;
    public final Flow shadeScrimRounding;
    public final StateFlow shadeToQsFraction;
    public final Flow syntheticScroll;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public NotificationsPlaceholderViewModel(NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, RemoteInputInteractor remoteInputInteractor, FeatureFlagsClassic featureFlagsClassic, DumpManager dumpManager) {
        Object obj;
        ActivatableFlowDumperImpl activatableFlowDumperImpl = new ActivatableFlowDumperImpl(dumpManager, "NotificationsPlaceholderViewModel");
        this.$$delegate_0 = activatableFlowDumperImpl;
        this.interactor = notificationStackAppearanceInteractor;
        this.sceneInteractor = sceneInteractor;
        this.shadeInteractor = shadeInteractor;
        this.headsUpNotificationInteractor = headsUpNotificationInteractor;
        Hydrator hydrator = new Hydrator("NotificationsPlaceholderViewModel", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        Object obj2 = ((ShadeMode) shadeModeInteractorImpl.shadeMode.$$delegate_0.getValue()) instanceof ShadeMode.Dual ? Overlays.NotificationsShade : Scenes.Shade;
        final ReadonlyStateFlow readonlyStateFlow = shadeModeInteractorImpl.shadeMode;
        this.notificationsShadeContentKey$delegate = hydrator.hydratedStateOf("notificationsShadeContentKey", obj2, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationsPlaceholderViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationsPlaceholderViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.shade.shared.model.ShadeMode r5 = (com.android.systemui.shade.shared.model.ShadeMode) r5
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel r6 = r4.this$0
                        r6.getClass()
                        boolean r5 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Dual
                        if (r5 == 0) goto L40
                        com.android.compose.animation.scene.OverlayKey r5 = com.android.systemui.scene.shared.model.Overlays.NotificationsShade
                        goto L42
                    L40:
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Shade
                    L42:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        ShadeMode shadeMode = (ShadeMode) readonlyStateFlow.$$delegate_0.getValue();
        if (shadeMode instanceof ShadeMode.Single) {
            obj = Scenes.QuickSettings;
        } else if (shadeMode instanceof ShadeMode.Split) {
            obj = Scenes.Shade;
        } else {
            if (!(shadeMode instanceof ShadeMode.Dual)) {
                throw new NoWhenBranchMatchedException();
            }
            obj = Overlays.QuickSettingsShade;
        }
        this.quickSettingsShadeContentKey$delegate = hydrator.hydratedStateOf("quickSettingsShadeContentKey", obj, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationsPlaceholderViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationsPlaceholderViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.shade.shared.model.ShadeMode r5 = (com.android.systemui.shade.shared.model.ShadeMode) r5
                        com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel r6 = r4.this$0
                        r6.getClass()
                        boolean r6 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Single
                        if (r6 == 0) goto L40
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.QuickSettings
                        goto L4d
                    L40:
                        boolean r6 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Split
                        if (r6 == 0) goto L47
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Shade
                        goto L4d
                    L47:
                        boolean r5 = r5 instanceof com.android.systemui.shade.shared.model.ShadeMode.Dual
                        if (r5 == 0) goto L5b
                        com.android.compose.animation.scene.OverlayKey r5 = com.android.systemui.scene.shared.model.Overlays.QuickSettingsShade
                    L4d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L5b:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isCurrentGestureOverscroll$delegate = hydrator.hydratedStateOf("isCurrentGestureOverscroll", Boolean.FALSE, notificationStackAppearanceInteractor.isCurrentGestureOverscroll);
        Flags flags = Flags.INSTANCE;
        featureFlagsClassic.getClass();
        this.isHeadsUpOrAnimatingAway = (Flow) headsUpNotificationInteractor.isHeadsUpOrAnimatingAway$delegate.getValue();
        this.shadeScrimRounding = activatableFlowDumperImpl.dumpWhileCollecting(notificationStackAppearanceInteractor.shadeScrimRounding, "shadeScrimRounding");
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.expandFraction = activatableFlowDumperImpl.dumpValue(shadeInteractorImpl.baseShadeInteractor.getAnyExpansion(), "expandFraction");
        this.shadeToQsFraction = activatableFlowDumperImpl.dumpValue(shadeInteractorImpl.baseShadeInteractor.getQsExpansion(), "shadeToQsFraction");
        this.syntheticScroll = activatableFlowDumperImpl.dumpWhileCollecting(notificationStackAppearanceInteractor.syntheticScroll, "syntheticScroll");
        this.isRemoteInputActive = remoteInputInteractor.isRemoteInputActive;
        this.remoteInputRowBottomBound = remoteInputInteractor.remoteInputRowBottomBound;
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0058, code lost:
    
        if (r6.$$delegate_0.activateFlowDumper(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005a, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
    
        if (kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3b
            if (r2 == r5) goto L33
            if (r2 == r4) goto L2f
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5b
        L33:
            java.lang.Object r6 = r0.L$0
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel r6 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2 r7 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2
            r7.<init>(r6, r3)
            r0.L$0 = r6
            r0.label = r5
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r7, r0)
            if (r7 != r1) goto L4e
            goto L5a
        L4e:
            r0.L$0 = r3
            r0.label = r4
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl r6 = r6.$$delegate_0
            java.lang.Object r6 = r6.activateFlowDumper(r0)
            if (r6 != r1) goto L5b
        L5a:
            return r1
        L5b:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
