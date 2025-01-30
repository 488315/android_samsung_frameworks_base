package com.android.systemui.keyguard.p009ui.viewmodel;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.shared.model.SettingsClockSize;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ KeyguardPreviewSmartspaceViewModel this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1$2 */
    public final class C17602 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ KeyguardPreviewSmartspaceViewModel this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1$2", m277f = "KeyguardPreviewSmartspaceViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                return C17602.this.emit(null, this);
            }
        }

        public C17602(FlowCollector flowCollector, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = keyguardPreviewSmartspaceViewModel;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            int i;
            int dimensionPixelSize;
            int dimensionPixelSize2;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i2 = anonymousClass1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        int i3 = KeyguardPreviewSmartspaceViewModel.WhenMappings.$EnumSwitchMapping$0[((SettingsClockSize) obj).ordinal()];
                        KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel = this.this$0;
                        if (i3 == 1) {
                            KeyguardPreviewSmartspaceViewModel.Companion companion = KeyguardPreviewSmartspaceViewModel.Companion;
                            Resources resources = keyguardPreviewSmartspaceViewModel.context.getResources();
                            companion.getClass();
                            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_smartspace_top_offset) + resources.getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard);
                            dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
                        } else {
                            if (i3 != 2) {
                                throw new NoWhenBranchMatchedException();
                            }
                            KeyguardPreviewSmartspaceViewModel.Companion companion2 = KeyguardPreviewSmartspaceViewModel.Companion;
                            Resources resources2 = keyguardPreviewSmartspaceViewModel.context.getResources();
                            companion2.getClass();
                            int identifier = resources2.getIdentifier("status_bar_height", "dimen", "android");
                            dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.small_clock_padding_top) + (identifier > 0 ? resources2.getDimensionPixelSize(identifier) : 0);
                            dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.small_clock_height);
                        }
                        Integer num = new Integer(dimensionPixelSize2 + dimensionPixelSize);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }
            anonymousClass1 = new AnonymousClass1(continuation);
            Object obj22 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = anonymousClass1.label;
            if (i != 0) {
            }
            return Unit.INSTANCE;
        }
    }

    public KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1(Flow flow, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = keyguardPreviewSmartspaceViewModel;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new C17602(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
