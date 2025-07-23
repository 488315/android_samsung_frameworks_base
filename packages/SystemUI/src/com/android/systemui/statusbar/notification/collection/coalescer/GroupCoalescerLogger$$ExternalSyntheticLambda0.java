package com.android.systemui.statusbar.notification.collection.coalescer;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class GroupCoalescerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(logMessage.getLong1(), "ms", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), "Emitting batch for group ", str1, " size=", " age="));
            case 1:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Modification of notif ", logMessage.getStr1(), " triggered early emit of batched group ", logMessage.getStr2());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RankingMap is missing an entry for coalesced notification ", logMessage.getStr1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("COALESCED: ", logMessage.getStr1());
            default:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Modification of notif ", logMessage.getStr1(), " triggered TIMEOUT emit of batched group ", logMessage.getStr2());
        }
    }
}
