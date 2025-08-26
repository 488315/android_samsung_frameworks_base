package com.android.systemui.qs.tiles.base.shared.logging;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ QSTileLogger$$ExternalSyntheticLambda1(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        String str = this.f$0;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = QSTileLogger.$r8$clinit;
                return str;
            default:
                int i2 = QSTileLogger.$r8$clinit;
                StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("tile ", logMessage.getStr1(), ": rejected by policy, restriction: ");
                sbM.append(str);
                return sbM.toString();
        }
    }
}
