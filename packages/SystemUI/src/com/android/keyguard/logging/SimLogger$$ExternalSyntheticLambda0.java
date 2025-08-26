package com.android.keyguard.logging;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class SimLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SimLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "reportSimUnlocked(subId=", ")");
            case 1:
                return "handleServiceStateChange(subId=" + logMessage.getInt1() + ", serviceState=" + logMessage.getStr1() + ")";
            case 2:
                return MutableVectorKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Previously active subId: ", ", slotId: ", " is now invalid, will remove");
            case 3:
                return TransitionKt$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "handleSimStateChange(subId=", ", slotId=", ", state="), logMessage.getStr1(), ")");
            case 4:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("action ", str1, " state: ", str2, " slotId: ");
                sbM.append(int1);
                sbM.append(" subid: ");
                sbM.append(int2);
                return sbM.toString();
            case 5:
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                int int12 = logMessage.getInt1();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("action ", str12, " serviceState=", str22, " subId=");
                sbM2.append(int12);
                return sbM2.toString();
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("SubInfo:", logMessage.getStr1());
        }
    }
}
