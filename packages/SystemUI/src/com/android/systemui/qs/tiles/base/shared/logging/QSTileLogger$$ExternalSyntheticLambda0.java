package com.android.systemui.qs.tiles.base.shared.logging;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.StatusBarState;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = QSTileLogger.$r8$clinit;
                String str1 = logMessage.getStr1();
                str1.getClass();
                return str1;
            case 1:
                int i2 = QSTileLogger.$r8$clinit;
                String str12 = logMessage.getStr1();
                str12.getClass();
                return str12;
            case 2:
                int i3 = QSTileLogger.$r8$clinit;
                return "tile data initial update";
            case 3:
                int i4 = QSTileLogger.$r8$clinit;
                return "tile data force update";
            case 4:
                int i5 = QSTileLogger.$r8$clinit;
                return "user action delivered to the service";
            case 5:
                int i6 = QSTileLogger.$r8$clinit;
                String str13 = logMessage.getStr1();
                String statusBarState = StatusBarState.toString(logMessage.getInt1());
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("tile ", str13, ": statusBarState=", statusBarState, ", hasState="), logMessage.getBool1(), ", hasData=", logMessage.getBool2());
            case 6:
                int i7 = QSTileLogger.$r8$clinit;
                String str14 = logMessage.getStr1();
                String statusBarState2 = StatusBarState.toString(logMessage.getInt1());
                return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("tile ", str14, " pipeline: statusBarState=", statusBarState2, ", state="), logMessage.getStr2(), ", data=", logMessage.getStr3());
            case 7:
                int i8 = QSTileLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("tile state update: state=", logMessage.getStr1(), ", data=", logMessage.getStr2());
            default:
                int i9 = QSTileLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("tile ", logMessage.getStr1(), ": rejected by falsing");
        }
    }
}
