package com.android.systemui.statusbar.notification.collection.notifcollection;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotifCollectionLogger$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotifCollectionLogger$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISS Already Dismissed Child ", str1, " of parent ", str2, " (");
                sbM.append(int1);
                sbM.append("/");
                sbM.append(int2);
                sbM.append(")");
                return sbM.toString();
            case 1:
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("UPDATED INTERNALLY ", str12, " BY ", str22, " BECAUSE ");
                sbM2.append(str3);
                return sbM2.toString();
            case 2:
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("FAILED INTERNAL UPDATE ", str13, " BY ", str23, " BECAUSE ");
                sbM3.append(str32);
                return sbM3.toString();
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
                StringBuilder sbM4 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Mismatch: current ", str24, " is ", str33, " for: ");
                sbM4.append(str14);
                return sbM4.toString();
            case 9:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "POSTED GROUP ", logMessage.getStr1(), " (", " events)");
            case 10:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Reusing existing registration: ", logMessage.getStr1());
            case 11:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Registered: ", logMessage.getStr1());
            case 12:
                String str15 = logMessage.getStr1();
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(logMessage.getInt1(), "LOCALLY DISMISS Non Existent ", str15, " (", "/"));
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
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int12, "LOCALLY DISMISS Mismatch ", str16, " (", "/");
                sbM890m.append(int22);
                sbM890m.append("): dismissing @");
                sbM890m.append(str25);
                sbM890m.append(" but stored @");
                sbM890m.append(str34);
                return sbM890m.toString();
            case 16:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("ENTER CANCELED_ENTRY: ", logMessage.getStr1());
            default:
                String str17 = logMessage.getStr1();
                String str26 = logMessage.getStr2();
                int int13 = logMessage.getInt1();
                int int23 = logMessage.getInt2();
                StringBuilder sbM5 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISSED CHILD (inferred): ", str17, " of parent ", str26, " (");
                sbM5.append(int13);
                sbM5.append("/");
                sbM5.append(int23);
                sbM5.append(")");
                return sbM5.toString();
        }
    }
}
