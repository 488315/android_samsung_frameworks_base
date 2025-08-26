package com.android.systemui.statusbar.notification.collection.render;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeViewDifferLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Skip detaching ", str1, " from ", str2, " isTransfer="), logMessage.getBool1(), " isParentRemoved=", logMessage.getBool2());
            case 1:
                String str12 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Detach ", str12, " isTransfer=", " isParentRemoved=", bool1);
                sbM.append(bool2);
                sbM.append(" oldParent=");
                sbM.append(str22);
                sbM.append(" newParent=");
                sbM.append(str3);
                return sbM.toString();
            case 2:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), " when mapping tree: ", logMessage.getStr2());
            case 3:
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Moving child view ", str13, " in ", str23, " to index ");
                sbM2.append(int1);
                return sbM2.toString();
            default:
                String str14 = logMessage.getStr1();
                String str24 = logMessage.getStr2();
                int int12 = logMessage.getInt1();
                StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Attaching view ", str14, " to ", str24, " at index ");
                sbM3.append(int12);
                return sbM3.toString();
        }
    }
}
