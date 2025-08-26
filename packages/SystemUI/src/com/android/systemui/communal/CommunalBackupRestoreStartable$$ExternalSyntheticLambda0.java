package com.android.systemui.communal;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalBackupRestoreStartable$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = CommunalBackupRestoreStartable.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("On old to new widget ids mapping updated: ", logMessage.getStr1());
            default:
                int i2 = CommunalBackupRestoreStartable.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("User setup complete: ", logMessage.getBool1());
        }
    }
}
