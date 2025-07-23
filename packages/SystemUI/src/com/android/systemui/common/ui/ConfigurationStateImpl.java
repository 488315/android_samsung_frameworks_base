package com.android.systemui.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConfigurationStateImpl implements ConfigurationState {
    public final ConfigurationController configurationController;
    public final Context context;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ConfigurationStateImpl create(Context context, ConfigurationController configurationController);
    }

    public ConfigurationStateImpl(ConfigurationController configurationController, Context context) {
        this.configurationController = configurationController;
        this.context = context;
        LayoutInflater.from(context);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1] */
    public final ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1 getDimensionPixelSize(final int i) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), ConfigurationControllerExtKt.getOnDensityOrFontScaleChanged(this.configurationController));
        return new Flow() { // from class: com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $id$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConfigurationStateImpl this$0;

                /* renamed from: com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConfigurationStateImpl configurationStateImpl, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = configurationStateImpl;
                    this.$id$inlined = i;
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
                        boolean r0 = r6 instanceof com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1$2$1 r0 = (com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1$2$1 r0 = new com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L52
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.common.ui.ConfigurationStateImpl r5 = r4.this$0
                        android.content.Context r5 = r5.context
                        android.content.res.Resources r5 = r5.getResources()
                        int r6 = r4.$id$inlined
                        int r5 = r5.getDimensionPixelSize(r6)
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L52
                        return r1
                    L52:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
