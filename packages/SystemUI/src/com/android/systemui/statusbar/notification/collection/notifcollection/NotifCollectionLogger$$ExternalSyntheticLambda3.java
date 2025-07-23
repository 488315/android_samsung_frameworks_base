package com.android.systemui.statusbar.notification.collection.notifcollection;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotifCollectionLogger$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotifCollectionLogger$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISS Already Dismissed Child ", str1, " of parent ", str2, " (");
                m.append(int1);
                m.append("/");
                m.append(int2);
                m.append(")");
                return m.toString();
            case 1:
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("UPDATED INTERNALLY ", str12, " BY ", str22, " BECAUSE ");
                m2.append(str3);
                return m2.toString();
            case 2:
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder m3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("FAILED INTERNAL UPDATE ", str13, " BY ", str23, " BECAUSE ");
                m3.append(str32);
                return m3.toString();
            case 3:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("No notification to remove with key ", logMessage.getStr1(), " reason=", NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1()));
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("UPDATED ", logMessage.getStr1());
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Ignoring: entry already cancelled by server: ", logMessage.getStr1());
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("REMOVED ", logMessage.getStr1(), " reason=", NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1()));
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CANCEL LOCAL DISMISS Not Dismissed ", logMessage.getStr1());
            case 8:
                String str24 = logMessage.getStr2();
                String str33 = logMessage.getStr3();
                String str14 = logMessage.getStr1();
                StringBuilder m4 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Mismatch: current ", str24, " is ", str33, " for: ");
                m4.append(str14);
                return m4.toString();
            case 9:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "POSTED GROUP ", logMessage.getStr1(), " (", " events)");
            case 10:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Reusing existing registration: ", logMessage.getStr1());
            case 11:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Registered: ", logMessage.getStr1());
            case 12:
                String str15 = logMessage.getStr1();
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), "LOCALLY DISMISS Non Existent ", str15, " (", "/"));
            case 13:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("LOCALLY DISMISSED Already Canceled ", logMessage.getStr1(), ". Trying to remove.");
            case 14:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RELEASED ", logMessage.getStr1());
            case 15:
                String str16 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                String str25 = logMessage.getStr2();
                String str34 = logMessage.getStr3();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int12, "LOCALLY DISMISS Mismatch ", str16, " (", "/");
                m888m.append(int22);
                m888m.append("): dismissing @");
                m888m.append(str25);
                m888m.append(" but stored @");
                m888m.append(str34);
                return m888m.toString();
            case 16:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("ENTER CANCELED_ENTRY: ", logMessage.getStr1());
            default:
                String str17 = logMessage.getStr1();
                String str26 = logMessage.getStr2();
                int int13 = logMessage.getInt1();
                int int23 = logMessage.getInt2();
                StringBuilder m5 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISSED CHILD (inferred): ", str17, " of parent ", str26, " (");
                m5.append(int13);
                m5.append("/");
                m5.append(int23);
                m5.append(")");
                return m5.toString();
        }
    }
}
