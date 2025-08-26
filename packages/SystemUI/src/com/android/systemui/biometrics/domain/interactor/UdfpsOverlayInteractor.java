package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class UdfpsOverlayInteractor {
    public final StateFlowImpl _requestId;
    public final StateFlowImpl _shouldHandleTouches;
    public final AuthController authController;
    public final FingerprintManager fingerprintManager;
    public final Flow iconPadding;
    public final int iconSize;
    public final ReadonlyStateFlow requestId;
    public final ReadonlyStateFlow shouldHandleTouches;
    public final ReadonlyStateFlow udfpsOverlayParams;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public UdfpsOverlayInteractor(Context context, AuthController authController, SelectedUserInteractor selectedUserInteractor, FingerprintManager fingerprintManager, CoroutineScope coroutineScope) {
        this.authController = authController;
        this.fingerprintManager = fingerprintManager;
        float f = context.getResources().getFloat(R.dimen.pixel_pitch);
        if (f <= 0.0f) {
            Log.e("UdfpsOverlayInteractor", "invalid pixelPitch: " + f + ". Pixel pitch must be updated per device.");
        }
        this.iconSize = (int) (context.getResources().getFloat(R.dimen.udfps_icon_size) / f);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(0L);
        this._requestId = stateFlowImplMutableStateFlow;
        this.requestId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._shouldHandleTouches = stateFlowImplMutableStateFlow2;
        this.shouldHandleTouches = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UdfpsOverlayInteractor$udfpsOverlayParams$1 udfpsOverlayInteractor$udfpsOverlayParams$1 = new UdfpsOverlayInteractor$udfpsOverlayParams$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(udfpsOverlayInteractor$udfpsOverlayParams$1);
        SharingStarted.Companion.getClass();
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowConflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 0, 127, null));
        this.udfpsOverlayParams = readonlyStateFlowStateIn;
        this.iconPadding = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UdfpsOverlayInteractor this$0;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, UdfpsOverlayInteractor udfpsOverlayInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = udfpsOverlayInteractor;
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
                        UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) obj;
                        Rect rect = udfpsOverlayParams.nativeSensorBounds;
                        Integer num = new Integer(Math.max(0, (int) ((((rect.right - rect.left) - this.this$0.iconSize) / 2) * udfpsOverlayParams.scaleFactor)));
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }

    public final void setHandleTouches(boolean z) {
        AuthController authController = this.authController;
        if (authController.isUdfpsSupported()) {
            FingerprintManager fingerprintManager = this.fingerprintManager;
            if (fingerprintManager != null) {
                long jLongValue = ((Number) this.requestId.$$delegate_0.getValue()).longValue();
                List list = authController.mUdfpsProps;
                list.getClass();
                fingerprintManager.setIgnoreDisplayTouches(jLongValue, ((FingerprintSensorPropertiesInternal) list.get(0)).sensorId, !z);
            }
        } else {
            Log.d("UdfpsOverlayInteractor", "setIgnoreDisplayTouches not set, UDFPS not supported");
        }
        this._shouldHandleTouches.updateState(null, Boolean.valueOf(z));
    }
}
