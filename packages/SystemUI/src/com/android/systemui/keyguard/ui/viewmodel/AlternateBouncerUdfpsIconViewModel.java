package com.android.systemui.keyguard.ui.viewmodel;

import android.R;
import android.content.Context;
import com.android.settingslib.Utils;
import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
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
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class AlternateBouncerUdfpsIconViewModel {
    public final ChannelFlowTransformLatest accessibilityDelegateHint;
    public final AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1 alpha;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 bgAlpha;
    public final ChannelFlowTransformLatest bgColor;
    public final Context context;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 fgIconColor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 fgViewModel;
    public final ChannelFlowTransformLatest iconLocation;
    public final ReadonlyStateFlow isSupported;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;

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
            return Integer.hashCode(this.bottom) + ReorderTile$$ExternalSyntheticOutline0.m(this.right, ReorderTile$$ExternalSyntheticOutline0.m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("IconLocation(left=");
            sb.append(this.left);
            sb.append(", top=");
            sb.append(this.top);
            sb.append(", right=");
            sb.append(this.right);
            sb.append(", bottom=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.bottom, ")", sb);
        }
    }

    public AlternateBouncerUdfpsIconViewModel(Context context, ConfigurationInteractor configurationInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel, FingerprintPropertyInteractor fingerprintPropertyInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, AlternateBouncerViewModel alternateBouncerViewModel, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AccessibilityInteractor accessibilityInteractor) {
        this.context = context;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        ReadonlyStateFlow readonlyStateFlow = deviceEntryUdfpsInteractor.isUdfpsSupported;
        this.isSupported = readonlyStateFlow;
        this.alpha = new AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1(alternateBouncerViewModel.transitionToAlternateBouncerProgress);
        this.iconLocation = FlowKt.transformLatest(readonlyStateFlow, new AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$1(null, fingerprintPropertyInteractor));
        this.accessibilityDelegateHint = FlowKt.transformLatest(accessibilityInteractor.isEnabled, new AlternateBouncerUdfpsIconViewModel$special$$inlined$flatMapLatest$2(null));
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = ((ConfigurationInteractorImpl) configurationInteractor).onAnyConfigurationChange;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AlternateBouncerUdfpsIconViewModel$fgIconColor$2(this, null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$2

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
                        Integer num = new Integer(Utils.getColorAttrDefaultColor(this.this$0.context, R.attr.textColorPrimary, 0));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.fgIconColor = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12;
        this.fgViewModel = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, udfpsOverlayInteractor.iconPadding, new AlternateBouncerUdfpsIconViewModel$fgViewModel$1(null));
        this.bgColor = deviceEntryBackgroundViewModel.color;
        this.bgAlpha = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Float.valueOf(1.0f));
    }
}
