package com.android.systemui.qs.pipeline.shared.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPipelineLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ QSPipelineLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = QSPipelineLogger.$r8$clinit;
                return "Using retail tiles";
            case 1:
                int i2 = QSPipelineLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Tile ", logMessage.getStr1(), " destroyed. Reason: ", logMessage.getStr2());
            case 2:
                int i3 = QSPipelineLogger.$r8$clinit;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Restored settings data for user ", "\n\tRestored tiles: ", str1, "\n\tRestored auto added tiles: ");
                m.append(str2);
                return m.toString();
            case 3:
                int i4 = QSPipelineLogger.$r8$clinit;
                String str12 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                String str22 = logMessage.getStr2();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int12, "Processing ", str12, " for user ", "\nNew list: ");
                m888m.append(str22);
                return m888m.toString();
            case 4:
                int i5 = QSPipelineLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Tile ", logMessage.getStr1(), " ignored as it was already destroyed.");
            case 5:
                int i6 = QSPipelineLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Tile ", logMessage.getStr1(), " not found in factory");
            case 6:
                int i7 = QSPipelineLogger.$r8$clinit;
                int int13 = logMessage.getInt1();
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                StringBuilder m2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int13, "Tiles restored and reconciled for user: ", "\nWas: ", str13, "\nSet to: ");
                m2.append(str23);
                return m2.toString();
            case 7:
                int i8 = QSPipelineLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "User changed to ", " for tile ", logMessage.getStr1());
            case 8:
                int i9 = QSPipelineLogger.$r8$clinit;
                String str14 = logMessage.getStr1();
                int int14 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m888m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int14, "Tile ", str14, " auto added for user ", " at position ");
                m888m2.append(int2);
                return m888m2.toString();
            case 9:
                int i10 = QSPipelineLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Restore ", logMessage.getStr2(), " processed by ", logMessage.getStr1());
            case 10:
                int i11 = QSPipelineLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Auto-add tiles reconciled for user ", ": ", logMessage.getStr1());
            case 11:
                int i12 = QSPipelineLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Tile ", logMessage.getStr1(), " created. Reason: ", logMessage.getStr2());
            case 12:
                int i13 = QSPipelineLogger.$r8$clinit;
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Tile ", logMessage.getStr1(), " unmarked as auto-added for user ");
            case 13:
                int i14 = QSPipelineLogger.$r8$clinit;
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Tile ", logMessage.getStr1(), " auto removed for user ");
            case 14:
                int i15 = QSPipelineLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Auto add tiles parsed for user ", ": ", logMessage.getStr1());
            case 15:
                int i16 = QSPipelineLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("SS BNR data(", logMessage.getStr1(), ") : ", logMessage.getStr2());
            case 16:
                int i17 = QSPipelineLogger.$r8$clinit;
                boolean bool1 = logMessage.getBool1();
                int int15 = logMessage.getInt1();
                String str15 = logMessage.getStr1();
                StringBuilder m3 = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("Parsed tiles (default=", int15, ", user=", bool1, "): ");
                m3.append(str15);
                return m3.toString();
            case 17:
                int i18 = QSPipelineLogger.$r8$clinit;
                String str16 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                int int16 = logMessage.getInt1();
                String str24 = logMessage.getStr2();
                StringBuilder m4 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Parsed tiles (QSType=", str16, ", default=", ", user=", bool12);
                m4.append(int16);
                m4.append("): ");
                m4.append(str24);
                return m4.toString();
            case 18:
                int i19 = QSPipelineLogger.$r8$clinit;
                boolean bool13 = logMessage.getBool1();
                int int17 = logMessage.getInt1();
                String str17 = logMessage.getStr1();
                String str25 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m5 = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("Tiles fota updated (maintained=", int17, ", user=", bool13, "): \nOld: ");
                MoveResult$$ExternalSyntheticOutline0.m(m5, str17, "\nNew: ", str25, "\nFotaTiles: ");
                m5.append(str3);
                return m5.toString();
            case 19:
                int i20 = QSPipelineLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Tiles kept for not installed packages for user ", ": ", logMessage.getStr1());
            case 20:
                int i21 = QSPipelineLogger.$r8$clinit;
                String str26 = logMessage.getStr2();
                String str18 = logMessage.getStr1();
                boolean bool14 = logMessage.getBool1();
                int int18 = logMessage.getInt1();
                StringBuilder m6 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str26, "] Tile ", str18, " visibility updated. (Added: ");
                m6.append(bool14);
                m6.append(", Index: ");
                m6.append(int18);
                m6.append(")");
                return m6.toString();
            case 21:
                int i22 = QSPipelineLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Tile ", logMessage.getStr1(), " created");
            case 22:
                int i23 = QSPipelineLogger.$r8$clinit;
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Restored from single intent after user setup complete for user ");
            default:
                int i24 = QSPipelineLogger.$r8$clinit;
                String str32 = logMessage.getStr3();
                String str19 = logMessage.getStr1();
                int int19 = logMessage.getInt1();
                String str27 = logMessage.getStr2();
                StringBuilder m7 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Processing ", str32, " ", str19, " for user ");
                m7.append(int19);
                m7.append("\nNew list: ");
                m7.append(str27);
                return m7.toString();
        }
    }
}
