package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotifCollectionLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotifCollectionLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISS Already Parent-Dismissed Child ", str1, " of parent ", str2, " (");
                m.append(int1);
                m.append("/");
                m.append(int2);
                m.append(")");
                return m.toString();
            case 1:
                LogMessage logMessage2 = (LogMessage) obj;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("LIFETIME EXTENDED: ", logMessage2.getStr1(), " by ", logMessage2.getStr2());
            case 2:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "DISMISS ALL notifications for user ");
            case 3:
                LogMessage logMessage3 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage3.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage3.getInt1(), "LOCALLY DISMISSED ", logMessage3.getStr1(), " (", "/"));
            case 4:
                LogMessage logMessage4 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage4.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage4.getInt1(), "LOCALLY DISMISS Already Dismissed ", logMessage4.getStr1(), " (", "/"));
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RemoteException while attempting to clear all notifications:\n", ((LogMessage) obj).getStr1());
            case 6:
                LogMessage logMessage5 = (LogMessage) obj;
                String str12 = logMessage5.getStr1();
                int int12 = logMessage5.getInt1();
                int int22 = logMessage5.getInt2();
                String str22 = logMessage5.getStr2();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int12, "DISMISS Already Parent-Dismissed ", str12, " (", "/");
                m888m.append(int22);
                m888m.append(") with summary ");
                m888m.append(str22);
                return m888m.toString();
            case 7:
                LogMessage logMessage6 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage6.getInt1(), " remaining extensions", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LIFETIME EXTENSION ENDED for ", logMessage6.getStr1(), " by '", logMessage6.getStr2(), "'; "));
            case 8:
                LogMessage logMessage7 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage7.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage7.getInt1(), "DISMISS Non Existent ", logMessage7.getStr1(), " (", "/"));
            case 9:
                LogMessage logMessage8 = (LogMessage) obj;
                int int13 = logMessage8.getInt1();
                int int23 = logMessage8.getInt2();
                String str13 = logMessage8.getStr1();
                StringBuilder m2 = MutableObjectList$$ExternalSyntheticOutline0.m(int13, int23, "Collection missing ", " entries in ranking update. Just lost ", ": ");
                m2.append(str13);
                return m2.toString();
            case 10:
                String logKey = NotificationUtils.logKey((String) obj);
                return logKey != null ? logKey : "null";
            case 11:
                LogMessage logMessage9 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage9.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage9.getInt1(), "LOCALLY DISMISS Already Dismissed ", logMessage9.getStr1(), " (", "/"));
            case 12:
                LogMessage logMessage10 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage10.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage10.getInt1(), "DISMISS INTERCEPTED ", logMessage10.getStr1(), " (", "/"));
            case 13:
                LogMessage logMessage11 = (LogMessage) obj;
                int int14 = logMessage11.getInt1();
                int int24 = logMessage11.getInt2();
                String str14 = logMessage11.getStr1();
                StringBuilder m3 = MutableObjectList$$ExternalSyntheticOutline0.m(int14, int24, "Ranking update is missing ranking for ", " entries (", " new): ");
                m3.append(str14);
                return m3.toString();
            case 14:
                String logKey2 = NotificationUtilsKt.getLogKey((NotificationEntry) obj);
                return logKey2 != null ? logKey2 : "null";
            case 15:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Ranking map contents: ", ((LogMessage) obj).getStr1());
            case 16:
                LogMessage logMessage12 = (LogMessage) obj;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage12.getInt1(), "Ranking update now contains rankings for ", " previously inconsistent entries: ", logMessage12.getStr1());
            case 17:
                String logKey3 = NotificationUtils.logKey((String) obj);
                return logKey3 != null ? logKey3 : "null";
            case 18:
                LogMessage logMessage13 = (LogMessage) obj;
                String str15 = logMessage13.getStr1();
                int int15 = logMessage13.getInt1();
                int int25 = logMessage13.getInt2();
                String str23 = logMessage13.getStr2();
                StringBuilder m888m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int15, "RemoteException while attempting to clear ", str15, " (", "/");
                m888m2.append(int25);
                m888m2.append("):\n");
                m888m2.append(str23);
                return m888m2.toString();
            case 19:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("System server double cancelled: ", ((LogMessage) obj).getStr1());
            case 20:
                LogMessage logMessage14 = (LogMessage) obj;
                String str24 = logMessage14.getStr2();
                String str16 = logMessage14.getStr1();
                String str3 = logMessage14.getStr3();
                StringBuilder m4 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("While ending lifetime extension by ", str24, " of ", str16, ", entry in collection is ");
                m4.append(str3);
                return m4.toString();
            case 21:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("MAYBE ALREADY_REMOVED_ENTRY but new posted: ", ((LogMessage) obj).getStr1());
            case 22:
                LogMessage logMessage15 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage15.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage15.getInt1(), "CLEAR ALL DISMISSAL INTERCEPTED ", logMessage15.getStr1(), " (", "/"));
            case 23:
                LogMessage logMessage16 = (LogMessage) obj;
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage16.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage16.getInt1(), "DISMISS Already Dismissed ", logMessage16.getStr1(), " (", "/"));
            case 24:
                LogMessage logMessage17 = (LogMessage) obj;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("SystemServer cancelled: ", logMessage17.getStr1(), " reason=", NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage17.getInt1()));
            case 25:
                LogMessage logMessage18 = (LogMessage) obj;
                int int16 = logMessage18.getInt1();
                int int26 = logMessage18.getInt2();
                String str17 = logMessage18.getStr1();
                StringBuilder m5 = MutableObjectList$$ExternalSyntheticOutline0.m(int16, int26, "Collection missing ", " entries in ranking update. Just found ", ": ");
                m5.append(str17);
                return m5.toString();
            case 26:
                String logKey4 = NotificationUtils.logKey((String) obj);
                return logKey4 != null ? logKey4 : "null";
            case 27:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Double run: ", ((LogMessage) obj).getStr1());
            case 28:
                LogMessage logMessage19 = (LogMessage) obj;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Dismissing ", logMessage19.getStr2(), " for: ", logMessage19.getStr1());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("POSTED ", ((LogMessage) obj).getStr1());
        }
    }
}
