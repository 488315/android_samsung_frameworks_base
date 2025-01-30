package com.android.systemui.biometrics.domain.interactor;

import android.view.MotionEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsOverlayInteractor {
    public final AuthController authController;
    public final ReadonlyStateFlow udfpsOverlayParams;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public UdfpsOverlayInteractor(AuthController authController, CoroutineScope coroutineScope) {
        this.authController = authController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UdfpsOverlayInteractor$udfpsOverlayParams$1 udfpsOverlayInteractor$udfpsOverlayParams$1 = new UdfpsOverlayInteractor$udfpsOverlayParams$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(udfpsOverlayInteractor$udfpsOverlayParams$1);
        SharingStarted.Companion.getClass();
        this.udfpsOverlayParams = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 63, null));
    }

    public final boolean isTouchWithinUdfpsArea(MotionEvent motionEvent) {
        return this.authController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser()) && ((UdfpsOverlayParams) this.udfpsOverlayParams.getValue()).overlayBounds.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }
}
