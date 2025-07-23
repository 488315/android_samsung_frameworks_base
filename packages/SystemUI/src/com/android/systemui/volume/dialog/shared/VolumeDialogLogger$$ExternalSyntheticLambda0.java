package com.android.systemui.volume.dialog.shared;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.Events;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                LogMessage logMessage = (LogMessage) obj;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Showing streams: primary=", " floating=", logMessage.getStr1());
            case 1:
                return String.valueOf(((Integer) obj).intValue());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Ringer drawer available with modes: ", ((LogMessage) obj).getStr1());
            case 3:
                return String.valueOf(((RingerMode) obj).value);
            case 4:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Current ringer mode: ", ", ringer mode is unsupported in ringer drawer options");
            case 5:
                return "Ringer drawer unavailable";
            case 6:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Show: ", Events.SHOW_REASONS[((LogMessage) obj).getInt1()]);
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Dismiss: ", Events.DISMISS_REASONS[((LogMessage) obj).getInt1()]);
            case 8:
                LogMessage logMessage2 = (LogMessage) obj;
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage2.getInt1(), logMessage2.getInt2(), "Volume adjusted: volume=", " stream=");
            default:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Ringer mode changed to: ");
        }
    }
}
