package com.android.systemui.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes.dex */
public final class ConfigurationStateImpl implements ConfigurationState {
    public final ConfigurationController configurationController;
    public final Context context;

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
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), ConfigurationControllerExtKt.getOnDensityOrFontScaleChanged(this.configurationController));
        return new Flow() { // from class: com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1

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
                        Integer num = new Integer(this.this$0.context.getResources().getDimensionPixelSize(this.$id$inlined));
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
