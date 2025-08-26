package com.android.systemui.statusbar.notification.headsup;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpManagerLogger$$ExternalSyntheticLambda11 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ HeadsUpManagerLogger$$ExternalSyntheticLambda11(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("request: ", logMessage.getStr2(), " => remove entry ", logMessage.getStr1(), " isWaiting: ");
                sbM.append(this.f$0);
                return sbM.toString();
            default:
                return logMessage.getStr2() + " => remove entry " + logMessage.getStr1() + " isWaiting: " + this.f$0;
        }
    }
}
