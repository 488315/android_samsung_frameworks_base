package com.android.systemui.communal.ui.compose;

import android.os.SystemClock;
import android.view.MotionEvent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.input.pointer.PointerInputChange;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda9 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                CommunalHubKt.DisclaimerBottomSheetContent((Function0) this.f$0, (Composer) obj, iUpdateChangedFlags);
                break;
            default:
                PointerInputChange pointerInputChange = (PointerInputChange) obj;
                ((Float) obj2).floatValue();
                pointerInputChange.consume();
                long jUptimeMillis = SystemClock.uptimeMillis();
                long j = pointerInputChange.position;
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 2, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (4294967295L & j)), 0);
                UniqueObjectHostView uniqueObjectHostView = ((BaseCommunalViewModel) this.f$0).mediaHost.hostView;
                if (uniqueObjectHostView == null) {
                    uniqueObjectHostView = null;
                }
                uniqueObjectHostView.dispatchTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                break;
        }
        return Unit.INSTANCE;
    }
}
