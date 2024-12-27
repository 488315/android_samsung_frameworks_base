package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor$special$$inlined$map$1;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class AlternateBouncerUdfpsIconViewModel {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 accessibilityDelegateHint;
    public final AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1 alpha;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 bgAlpha;
    public final ChannelFlowTransformLatest bgColor;
    public final Context context;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 fgIconColor;
    public final UdfpsOverlayInteractor$special$$inlined$map$1 fgIconPadding;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 fgViewModel;
    public final ChannelFlowTransformLatest iconLocation;
    public final StateFlow isSupported;

    public final class IconLocation {
        public final int bottom;
        public final int height;
        public final int left;
        public final int right;
        public final int top;
        public final int width;

        public IconLocation(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
            this.width = i3 - i;
            this.height = i4 - i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IconLocation)) {
                return false;
            }
            IconLocation iconLocation = (IconLocation) obj;
            return this.left == iconLocation.left && this.top == iconLocation.top && this.right == iconLocation.right && this.bottom == iconLocation.bottom;
        }

        public final int hashCode() {
            return Integer.hashCode(this.bottom) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.right, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("IconLocation(left=");
            sb.append(this.left);
            sb.append(", top=");
            sb.append(this.top);
            sb.append(", right=");
            sb.append(this.right);
            sb.append(", bottom=");
            return Anchor$$ExternalSyntheticOutline0.m(this.bottom, ")", sb);
        }
    }

    public AlternateBouncerUdfpsIconViewModel(Context context, ConfigurationInteractor configurationInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel, FingerprintPropertyInteractor fingerprintPropertyInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, AlternateBouncerViewModel alternateBouncerViewModel) {
        this.context = context;
        StateFlow stateFlow = deviceEntryUdfpsInteractor.isUdfpsSupported;
        this.alpha = new AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1(alternateBouncerViewModel.transitionToAlternateBouncerProgress);
        this.iconLocation = FlowKt.transformLatest(stateFlow, new AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1(null, fingerprintPropertyInteractor));
        this.accessibilityDelegateHint = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(DeviceEntryIconView.AccessibilityHintType.ENTER);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = configurationInteractor.onAnyConfigurationChange;
        this.fgViewModel = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AlternateBouncerUdfpsIconViewModel$fgIconColor$2(this, null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AlternateBouncerUdfpsIconViewModel this$0;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = alternateBouncerUdfpsIconViewModel;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel r5 = r4.this$0
                        android.content.Context r5 = r5.context
                        r6 = 0
                        r2 = 16842806(0x1010036, float:2.369371E-38)
                        int r5 = com.android.settingslib.Utils.getColorAttrDefaultColor(r5, r2, r6)
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), udfpsOverlayInteractor.iconPadding, new AlternateBouncerUdfpsIconViewModel$fgViewModel$1(null));
        this.bgColor = deviceEntryBackgroundViewModel.color;
        this.bgAlpha = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Float.valueOf(1.0f));
    }
}
