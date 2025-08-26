package com.android.systemui.keyguard.ui.viewmodel;

import android.R;
import com.android.settingslib.Utils;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class DeviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ConfigurationInteractor $configurationInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceEntryForegroundViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1(Continuation continuation, ConfigurationInteractor configurationInteractor, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
        super(3, continuation);
        this.$configurationInteractor$inlined = configurationInteractor;
        this.this$0 = deviceEntryForegroundViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1 deviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1 = new DeviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1((Continuation) obj3, this.$configurationInteractor$inlined, this.this$0);
        deviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceEntryForegroundViewModel$color$lambda$3$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final boolean zBooleanValue = ((Boolean) this.L$1).booleanValue();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = ((ConfigurationInteractorImpl) this.$configurationInteractor$inlined).onAnyConfigurationChange;
            final DeviceEntryForegroundViewModel deviceEntryForegroundViewModel = this.this$0;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryForegroundViewModel$color$1$1$2(this.this$0, zBooleanValue, null), new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$color$lambda$3$lambda$2$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$color$lambda$3$lambda$2$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ boolean $useBgProtection$inlined;
                    public final /* synthetic */ DeviceEntryForegroundViewModel this$0;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel$color$lambda$3$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, boolean z) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = deviceEntryForegroundViewModel;
                        this.$useBgProtection$inlined = z;
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
                            DeviceEntryForegroundViewModel deviceEntryForegroundViewModel = this.this$0;
                            Integer num = new Integer(this.$useBgProtection$inlined ? Utils.getColorAttrDefaultColor(deviceEntryForegroundViewModel.context, R.attr.textColorPrimary, 0) : Utils.getColorAttrDefaultColor(deviceEntryForegroundViewModel.context, com.android.systemui.R.attr.wallpaperTextColorAccent, 0));
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
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector2, deviceEntryForegroundViewModel, zBooleanValue), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            });
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
