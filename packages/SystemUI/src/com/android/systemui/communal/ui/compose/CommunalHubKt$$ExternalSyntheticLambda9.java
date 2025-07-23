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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda9 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                CommunalHubKt.DisclaimerBottomSheetContent((Function0) this.f$0, (Composer) obj, updateChangedFlags);
                break;
            default:
                PointerInputChange pointerInputChange = (PointerInputChange) obj;
                ((Float) obj2).floatValue();
                pointerInputChange.consume();
                long uptimeMillis = SystemClock.uptimeMillis();
                long j = pointerInputChange.position;
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 2, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (4294967295L & j)), 0);
                UniqueObjectHostView uniqueObjectHostView = ((BaseCommunalViewModel) this.f$0).mediaHost.hostView;
                if (uniqueObjectHostView == null) {
                    uniqueObjectHostView = null;
                }
                uniqueObjectHostView.dispatchTouchEvent(obtain);
                obtain.recycle();
                break;
        }
        return Unit.INSTANCE;
    }
}
