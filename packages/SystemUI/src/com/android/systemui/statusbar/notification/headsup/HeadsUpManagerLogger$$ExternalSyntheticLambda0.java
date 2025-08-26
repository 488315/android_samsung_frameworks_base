package com.android.systemui.statusbar.notification.headsup;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpManagerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HeadsUpManagerLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("package snoozed ", logMessage.getStr1());
            case 1:
                return FakeFeatures$$ExternalSyntheticOutline0.m("request: show notification ", logMessage.getStr1(), ". isPinnedByUser=", logMessage.getBool1());
            case 2:
                return FakeFeatures$$ExternalSyntheticOutline0.m("show notification ", logMessage.getStr1(), ". isPinnedByUser=", logMessage.getBool1());
            case 3:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("notification removed ", logMessage.getStr1(), " ");
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("request: cancel auto remove of ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
            case 5:
                return "has pinned notification changed to " + logMessage.getBool1() + ", status=" + logMessage.getStr1();
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("[AC] ", logMessage.getStr1(), " ", logMessage.getStr2());
            case 7:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("request: reschedule auto remove of ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
            case 8:
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sb = new StringBuilder();
                sb.append(str1);
                sb.append("\n=> AC[enabled:");
                sb.append(bool1);
                sb.append("] update: ");
                sb.append(str2);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb, "\n=> ", str3);
            case 9:
                return "release all immediately";
            case 10:
                return logMessage.getStr2() + " => set entry pinned " + logMessage.getStr1() + " pinned: " + logMessage.getStr3();
            case 11:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "snooze length changed: ", "ms");
            case 12:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("remove entry after expand: ", logMessage.getStr1());
            case 13:
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str12);
                sb2.append("\n=> AC[enabled:");
                sb2.append(bool12);
                sb2.append("] delete: ");
                sb2.append(str22);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb2, "\n=> ", str32);
            case 14:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("cancel auto remove of ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
            case 15:
                String str13 = logMessage.getStr1();
                long long1 = logMessage.getLong1();
                String str23 = logMessage.getStr2();
                StringBuilder sb3 = new StringBuilder("reschedule auto remove of ");
                sb3.append(str13);
                sb3.append(" in ");
                sb3.append(long1);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb3, " ms reason: ", str23);
            case 16:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("[AC] dropped:\n ", logMessage.getStr1());
            case 17:
                String str14 = logMessage.getStr1();
                boolean bool13 = logMessage.getBool1();
                String str24 = logMessage.getStr2();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("update entry ", str14, " updatePostTime: ", " reason: ", bool13);
                sbM.append(str24);
                return sbM.toString();
            case 18:
                String str15 = logMessage.getStr1();
                long long12 = logMessage.getLong1();
                String str25 = logMessage.getStr2();
                StringBuilder sb4 = new StringBuilder("schedule auto remove of ");
                sb4.append(str15);
                sb4.append(" in ");
                sb4.append(long12);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb4, " ms reason: ", str25);
            case 19:
                String str16 = logMessage.getStr1();
                return MutablePreferences$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(logMessage.getInt1(), "[AC] ", str16, " | ", " ms | "), logMessage.getStr2(), " ", logMessage.getStr3());
            case 20:
                String str17 = logMessage.getStr1();
                boolean bool14 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                String str26 = logMessage.getStr2();
                StringBuilder sbM2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("remove notification ", str17, " releaseImmediately: ", " isWaiting: ", bool14);
                sbM2.append(bool2);
                sbM2.append(" reason: ");
                sbM2.append(str26);
                return sbM2.toString();
            case 21:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("remove notification ", logMessage.getStr1(), " when headsUpEntry is null, reason: ", logMessage.getStr2());
            case 22:
                String str18 = logMessage.getStr1();
                boolean bool22 = logMessage.getBool2();
                String str27 = logMessage.getStr2();
                StringBuilder sbM3 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("update notification ", str18, ". hasEntry: ", ". requestedPinnedStatus: ", bool22);
                sbM3.append(str27);
                return sbM3.toString();
            case 23:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("package unsnoozed ", logMessage.getStr1());
            case 24:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("unpin entry ", logMessage.getStr1());
            case 25:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("package snoozed when queried ", logMessage.getStr1());
            case 26:
                String str19 = logMessage.getStr1();
                boolean bool15 = logMessage.getBool1();
                String str28 = logMessage.getStr2();
                StringBuilder sbM4 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("request: update notification ", str19, ". hasEntry: ", ". requestedPinnedStatus: ", bool15);
                sbM4.append(str28);
                return sbM4.toString();
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("request: unpin entry ", logMessage.getStr1());
        }
    }
}
