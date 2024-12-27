package com.android.systemui.statusbar.notification.stack.domain.interactor;

import android.content.Context;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class SharedNotificationContainerInteractor {
    public final StateFlowImpl _notificationStackChanged;
    public final StateFlowImpl _topPosition;
    public final Flow configurationBasedDimensions;
    public final Context context;
    public final Flow isSplitShadeEnabled;
    public final Flow notificationStackChanged;
    public final SplitShadeStateController splitShadeStateController;
    public final ReadonlyStateFlow topPosition = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(Float.valueOf(0.0f)));
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 useExtraShelfSpace;

    public final class ConfigurationBasedDimensions {
        public final int keyguardSplitShadeTopMargin;
        public final int marginBottom;
        public final int marginHorizontal;
        public final int marginTop;
        public final int marginTopLargeScreen;
        public final boolean useLargeScreenHeader;
        public final boolean useSplitShade;

        public ConfigurationBasedDimensions(boolean z, boolean z2, int i, int i2, int i3, int i4, int i5) {
            this.useSplitShade = z;
            this.useLargeScreenHeader = z2;
            this.marginHorizontal = i;
            this.marginBottom = i2;
            this.marginTop = i3;
            this.marginTopLargeScreen = i4;
            this.keyguardSplitShadeTopMargin = i5;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.useSplitShade == configurationBasedDimensions.useSplitShade && this.useLargeScreenHeader == configurationBasedDimensions.useLargeScreenHeader && this.marginHorizontal == configurationBasedDimensions.marginHorizontal && this.marginBottom == configurationBasedDimensions.marginBottom && this.marginTop == configurationBasedDimensions.marginTop && this.marginTopLargeScreen == configurationBasedDimensions.marginTopLargeScreen && this.keyguardSplitShadeTopMargin == configurationBasedDimensions.keyguardSplitShadeTopMargin;
        }

        public final int hashCode() {
            return Integer.hashCode(this.keyguardSplitShadeTopMargin) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginTopLargeScreen, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginTop, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginBottom, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.marginHorizontal, TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.useSplitShade) * 31, 31, this.useLargeScreenHeader), 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ConfigurationBasedDimensions(useSplitShade=");
            sb.append(this.useSplitShade);
            sb.append(", useLargeScreenHeader=");
            sb.append(this.useLargeScreenHeader);
            sb.append(", marginHorizontal=");
            sb.append(this.marginHorizontal);
            sb.append(", marginBottom=");
            sb.append(this.marginBottom);
            sb.append(", marginTop=");
            sb.append(this.marginTop);
            sb.append(", marginTopLargeScreen=");
            sb.append(this.marginTopLargeScreen);
            sb.append(", keyguardSplitShadeTopMargin=");
            return Anchor$$ExternalSyntheticOutline0.m(this.keyguardSplitShadeTopMargin, ")", sb);
        }
    }

    public SharedNotificationContainerInteractor(ConfigurationRepository configurationRepository, Context context, SplitShadeStateController splitShadeStateController, KeyguardInteractor keyguardInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, final Lazy lazy) {
        this.context = context;
        this.splitShadeStateController = splitShadeStateController;
        FlowKt.debounce(StateFlowKt.MutableStateFlow(0L), 20L);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SharedNotificationContainerInteractor$configurationBasedDimensions$1(null), ((ConfigurationRepositoryImpl) configurationRepository).onAnyConfigurationChange);
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Lazy $largeScreenHeaderHelperLazy$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SharedNotificationContainerInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SharedNotificationContainerInteractor sharedNotificationContainerInteractor, Lazy lazy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sharedNotificationContainerInteractor;
                    this.$largeScreenHeaderHelperLazy$inlined = lazy;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                    /*
                        r12 = this;
                        boolean r0 = r14 instanceof com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r14
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r14)
                    L18:
                        java.lang.Object r14 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r14)
                        goto L98
                    L27:
                        java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                        java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                        r12.<init>(r13)
                        throw r12
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r14)
                        kotlin.Unit r13 = (kotlin.Unit) r13
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor r13 = r12.this$0
                        android.content.Context r14 = r13.context
                        android.content.res.Resources r14 = r14.getResources()
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$ConfigurationBasedDimensions r2 = new com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$ConfigurationBasedDimensions
                        android.content.Context r4 = r13.context
                        r4.getResources()
                        com.android.systemui.statusbar.policy.SplitShadeStateController r13 = r13.splitShadeStateController
                        com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl r13 = (com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl) r13
                        r13.shouldUseSplitNotificationShade()
                        r13 = 2131034193(0x7f050051, float:1.7678897E38)
                        boolean r6 = r14.getBoolean(r13)
                        r13 = 2131168127(0x7f070b7f, float:1.7950547E38)
                        int r7 = r14.getDimensionPixelSize(r13)
                        r13 = 2131168126(0x7f070b7e, float:1.7950545E38)
                        int r8 = r14.getDimensionPixelSize(r13)
                        r13 = 2131168128(0x7f070b80, float:1.795055E38)
                        int r9 = r14.getDimensionPixelSize(r13)
                        com.android.systemui.FeatureFlagsImpl r13 = com.android.systemui.Flags.FEATURE_FLAGS
                        r13.getClass()
                        dagger.Lazy r13 = r12.$largeScreenHeaderHelperLazy$inlined
                        java.lang.Object r13 = r13.get()
                        com.android.systemui.shade.LargeScreenHeaderHelper r13 = (com.android.systemui.shade.LargeScreenHeaderHelper) r13
                        android.content.Context r4 = r13.context
                        com.android.systemui.qs.SecQSPanelResourcePicker r13 = r13.qsPanelResourcePicker
                        com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper r13 = r13.resourcePickHelper
                        com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker r13 = r13.getTargetPicker()
                        int r10 = r13.getShadeHeaderHeight(r4)
                        r13 = 2131166464(0x7f070500, float:1.7947174E38)
                        int r11 = r14.getDimensionPixelSize(r13)
                        r5 = 0
                        r4 = r2
                        r4.<init>(r5, r6, r7, r8, r9, r10, r11)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r12 = r12.$this_unsafeFlow
                        java.lang.Object r12 = r12.emit(r2, r0)
                        if (r12 != r1) goto L98
                        return r1
                    L98:
                        kotlin.Unit r12 = kotlin.Unit.INSTANCE
                        return r12
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, lazy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.configurationBasedDimensions = distinctUntilChanged;
        new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardInteractor.ambientIndicationVisible, deviceEntryUdfpsInteractor.isUdfpsSupported, new SharedNotificationContainerInteractor$useExtraShelfSpace$1(null));
        this.isSplitShadeEnabled = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$ConfigurationBasedDimensions r5 = (com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor.ConfigurationBasedDimensions) r5
                        boolean r5 = r5.useSplitShade
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }
}
