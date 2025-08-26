package com.android.systemui.shared.clocks.view;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.clocks.VPoint;
import com.android.systemui.plugins.clocks.VPointF;
import com.android.systemui.plugins.clocks.VRectF;
import com.android.systemui.shared.clocks.view.SimpleDigitalClockTextView;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class SimpleDigitalClockTextView$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                SimpleDigitalClockTextView.Companion companion = SimpleDigitalClockTextView.Companion;
                return MotionLayout$$ExternalSyntheticOutline0.m("setInterpolatedSize(size=", VPointF.m2782toStringimpl(VPointF.Companion.m2787fromLongAsyRdg(logMessage.getLong1())), ", mode=", VPoint.m2728toStringimpl(VPoint.Companion.m2733fromLongDO4cnVw(logMessage.getLong2())), ")");
            default:
                SimpleDigitalClockTextView.Companion companion2 = SimpleDigitalClockTextView.Companion;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("setInterpolatedLocation(", VRectF.m2845toStringimpl(VRectF.Companion.m2849fromLongWMibXUk(logMessage.getLong1())), ")");
        }
    }
}
