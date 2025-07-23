package com.android.systemui.qs.logging;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                return MutablePreferences$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), "[", str1, "][", "] Tile secondary clicked. StatusBarState="), logMessage.getStr2(), ". TileState=", logMessage.getStr3());
            case 1:
                String str12 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str12, "] Tile updated. Label=", str2, ". State=");
                m.append(int1);
                m.append(". Icon=");
                m.append(str3);
                m.append(".");
                return m.toString();
            case 2:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "[", logMessage.getStr1(), "][", "] Tile handling click.");
            case 3:
                return FakeFeatures$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] Tile listening=", logMessage.getBool1());
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] Tile destroyed. Reason: ", logMessage.getStr2());
            case 5:
                String str13 = logMessage.getStr1();
                return TransitionKt$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), "[", str13, "] mLastTileState=", ", Callback="), logMessage.getStr2(), ".");
            case 6:
                return logMessage.getStr1() + " expanded=" + logMessage.getBool1();
            case 7:
                String str14 = logMessage.getStr1();
                return MutablePreferences$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), "[", str14, "][", "] Tile clicked. StatusBarState="), logMessage.getStr2(), ". TileState=", logMessage.getStr3());
            case 8:
                return MutableVectorKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Distributing tiles: [tilesPerPageCount=", "] [totalTilesCount=", "]");
            case 9:
                String str15 = logMessage.getStr1();
                return MutablePreferences$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), "[", str15, "][", "] Tile long clicked. StatusBarState="), logMessage.getStr2(), ". TileState=", logMessage.getStr3());
            case 10:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Adding ", logMessage.getStr1(), " to page number ");
            case 11:
                return "Tiles listening=" + logMessage.getBool1() + " in " + logMessage.getStr1() + ". " + logMessage.getStr2();
            case 12:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "[", logMessage.getStr1(), "][", "] Tile handling secondary click.");
            default:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "[", logMessage.getStr1(), "][", "] Tile handling long click.");
        }
    }
}
