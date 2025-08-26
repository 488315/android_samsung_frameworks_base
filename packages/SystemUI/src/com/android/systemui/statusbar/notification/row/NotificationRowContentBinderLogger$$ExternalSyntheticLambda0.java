package com.android.systemui.statusbar.notification.row;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                NotificationRowContentBinderLogger.Companion companion = NotificationRowContentBinderLogger.Companion;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("async task for ", str1, " got exception ", str2, ": ");
                sbM.append(str3);
                return sbM.toString();
            case 1:
                NotificationRowContentBinderLogger.Companion companion2 = NotificationRowContentBinderLogger.Companion;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("async task for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            case 2:
                NotificationRowContentBinderLogger.Companion companion3 = NotificationRowContentBinderLogger.Companion;
                int int1 = logMessage.getInt1();
                NotificationRowContentBinderLogger.Companion.getClass();
                String strFlagToString = NotificationRowContentBinderLogger.Companion.flagToString(int1);
                String str12 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("inflateSingleLineView, inflationFlags: ", strFlagToString, " for ", str12, ", isConversation: ");
                sbM2.append(bool1);
                return sbM2.toString();
            case 3:
                NotificationRowContentBinderLogger.Companion companion4 = NotificationRowContentBinderLogger.Companion;
                int int12 = logMessage.getInt1();
                NotificationRowContentBinderLogger.Companion.getClass();
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("binding views ", NotificationRowContentBinderLogger.Companion.flagToString(int12), " for ", logMessage.getStr1());
            case 4:
                NotificationRowContentBinderLogger.Companion companion5 = NotificationRowContentBinderLogger.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("aborted task to cancel binding ", logMessage.getStr1());
            default:
                NotificationRowContentBinderLogger.Companion companion6 = NotificationRowContentBinderLogger.Companion;
                int int13 = logMessage.getInt1();
                NotificationRowContentBinderLogger.Companion.getClass();
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("unbinding views ", NotificationRowContentBinderLogger.Companion.flagToString(int13), " for ", logMessage.getStr1());
        }
    }
}
