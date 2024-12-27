package com.android.systemui.keyboard.docking.ui.viewmodel;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;
import com.android.settingslib.Utils;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.keyboard.docking.domain.interactor.KeyboardDockingIndicationInteractor;
import com.android.systemui.surfaceeffects.glowboxeffect.GlowBoxConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyboardDockingIndicationViewModel {
    public final StateFlowImpl _edgeGlow;
    public final Context context;
    public final ReadonlyStateFlow edgeGlow;
    public final Flow keyboardConnected;
    public final WindowManager windowManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyboard.docking.ui.viewmodel.KeyboardDockingIndicationViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ConfigurationInteractor $configurationInteractor;
        int label;
        final /* synthetic */ KeyboardDockingIndicationViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ConfigurationInteractor configurationInteractor, KeyboardDockingIndicationViewModel keyboardDockingIndicationViewModel, Continuation continuation) {
            super(2, continuation);
            this.$configurationInteractor = configurationInteractor;
            this.this$0 = keyboardDockingIndicationViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$configurationInteractor, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = this.$configurationInteractor.onAnyConfigurationChange;
                final KeyboardDockingIndicationViewModel keyboardDockingIndicationViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.docking.ui.viewmodel.KeyboardDockingIndicationViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyboardDockingIndicationViewModel keyboardDockingIndicationViewModel2 = KeyboardDockingIndicationViewModel.this;
                        keyboardDockingIndicationViewModel2._edgeGlow.updateState(null, keyboardDockingIndicationViewModel2.createEffectConfig());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyboardDockingIndicationViewModel(WindowManager windowManager, Context context, KeyboardDockingIndicationInteractor keyboardDockingIndicationInteractor, ConfigurationInteractor configurationInteractor, CoroutineScope coroutineScope) {
        this.windowManager = windowManager;
        this.context = context;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(createEffectConfig());
        this._edgeGlow = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
        Flow flow = keyboardDockingIndicationInteractor.onKeyboardConnected;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(configurationInteractor, this, null), 3);
    }

    public final GlowBoxConfig createEffectConfig() {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        Rect bounds = this.windowManager.getCurrentWindowMetrics().getBounds();
        float width = bounds.width();
        float height = bounds.height();
        int rotation = this.context.getDisplay().getRotation();
        if (rotation != 0) {
            if (rotation == 1) {
                f4 = width;
                f = 20.0f;
                f3 = 0.5f * width;
                f2 = f3;
                f5 = -300.0f;
                f6 = 0.0f;
            } else if (rotation == 2) {
                f = height;
                f4 = 20.0f;
                f5 = 0.5f * height;
                f6 = f5;
                f3 = -300.0f;
                f2 = 0.0f;
            } else if (rotation == 3) {
                f6 = height;
                f4 = width;
                f5 = height + 300.0f;
                f = 20.0f;
                f3 = width * 0.5f;
                f2 = f3;
            }
            return new GlowBoxConfig(f3, f5, f2, f6, f4, f, Utils.getColorAttr(R.attr.colorAccent, this.context).getDefaultColor(), 700.0f, 3000L, 800L, 800L);
        }
        f = height;
        f2 = width;
        f3 = width + 300.0f;
        f4 = 20.0f;
        f5 = height * 0.5f;
        f6 = f5;
        return new GlowBoxConfig(f3, f5, f2, f6, f4, f, Utils.getColorAttr(R.attr.colorAccent, this.context).getDefaultColor(), 700.0f, 3000L, 800L, 800L);
    }
}
