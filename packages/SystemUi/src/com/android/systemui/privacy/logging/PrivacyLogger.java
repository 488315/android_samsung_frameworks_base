package com.android.systemui.privacy.logging;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyLogger {
    public final LogBuffer buffer;

    public PrivacyLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logChipCreateRemove(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logChipCreateRemove$2 privacyLogger$logChipCreateRemove$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logChipCreateRemove$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Chip view is " + (logMessage.getBool1() ? "created" : "removed") + ", chip width=" + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logChipCreateRemove$2, null);
        obtain.setBool1(z);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logChipVisible(boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logChipVisible$2 privacyLogger$logChipVisible$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logChipVisible$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("Chip visible: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logChipVisible$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logCurrentProfilesChanged(List list) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logCurrentProfilesChanged$2 privacyLogger$logCurrentProfilesChanged$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logCurrentProfilesChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Profiles changed: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logCurrentProfilesChanged$2, null);
        obtain.setStr1(list.toString());
        logBuffer.commit(obtain);
    }

    public final void logPrivacyDialogDismissed() {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logPrivacyDialogDismissed$2 privacyLogger$logPrivacyDialogDismissed$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyDialogDismissed$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Privacy dialog dismissed";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyDialogDismissed$2, null));
    }

    public final void logPrivacyDotViewState(String str) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logPrivacyDotViewState$2 privacyLogger$logPrivacyDotViewState$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyDotViewState$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Privacy Dot ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyDotViewState$2, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logPrivacyItemsToHold(List list) {
        LogLevel logLevel = LogLevel.DEBUG;
        PrivacyLogger$logPrivacyItemsToHold$2 privacyLogger$logPrivacyItemsToHold$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyItemsToHold$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Holding items: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyItemsToHold$2, null);
        obtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, PrivacyLogger$listToString$1.INSTANCE, 30));
        logBuffer.commit(obtain);
    }

    public final void logPrivacyItemsUpdateScheduled(long j) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logPrivacyItemsUpdateScheduled$2 privacyLogger$logPrivacyItemsUpdateScheduled$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logPrivacyItemsUpdateScheduled$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Updating items scheduled for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logPrivacyItemsUpdateScheduled$2, null);
        obtain.setStr1(PrivacyLoggerKt.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis() + j)));
        logBuffer.commit(obtain);
    }

    public final void logRetrievedPrivacyItemsList(List list) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logRetrievedPrivacyItemsList$2 privacyLogger$logRetrievedPrivacyItemsList$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logRetrievedPrivacyItemsList$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Retrieved list to process: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logRetrievedPrivacyItemsList$2, null);
        obtain.setStr1(CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, PrivacyLogger$listToString$1.INSTANCE, 30));
        logBuffer.commit(obtain);
    }

    public final void logShowDialogContents(List list) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logShowDialogContents$2 privacyLogger$logShowDialogContents$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logShowDialogContents$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Privacy dialog shown. Contents: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logShowDialogContents$2, null);
        obtain.setStr1(list.toString());
        logBuffer.commit(obtain);
    }

    public final void logStartSettingsActivityFromDialog(int i, String str) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logStartSettingsActivityFromDialog$2 privacyLogger$logStartSettingsActivityFromDialog$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logStartSettingsActivityFromDialog$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Start settings activity from dialog for packageName=" + logMessage.getStr1() + ", userId=" + logMessage.getInt1() + " ";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logStartSettingsActivityFromDialog$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logStatusBarAlpha(int i) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logStatusBarAlpha$2 privacyLogger$logStatusBarAlpha$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logStatusBarAlpha$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0000x2c234b15.m0m("StatusBar applied alpha=", ((LogMessage) obj).getInt1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logStatusBarAlpha$2, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logStatusBarIconsVisible(boolean z, boolean z2, boolean z3) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logStatusBarIconsVisible$2 privacyLogger$logStatusBarIconsVisible$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logStatusBarIconsVisible$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("Status bar icons visible: camera=", bool1, ", microphone=", bool2, ", location=");
                m69m.append(bool3);
                return m69m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logStatusBarIconsVisible$2, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        logBuffer.commit(obtain);
    }

    public final void logUnfilteredPermGroupUsage(List list) {
        LogLevel logLevel = LogLevel.DEBUG;
        PrivacyLogger$logUnfilteredPermGroupUsage$2 privacyLogger$logUnfilteredPermGroupUsage$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logUnfilteredPermGroupUsage$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Perm group usage: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logUnfilteredPermGroupUsage$2, null);
        obtain.setStr1(list.toString());
        logBuffer.commit(obtain);
    }

    public final void logUpdatedItemFromAppOps(boolean z, String str, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logUpdatedItemFromAppOps$2 privacyLogger$logUpdatedItemFromAppOps$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logUpdatedItemFromAppOps$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m61m = AbstractC0662xaf167275.m61m("App Op: ", int1, " for ", str1, "(");
                m61m.append(int2);
                m61m.append("), active=");
                m61m.append(bool1);
                return m61m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logUpdatedItemFromAppOps$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logUpdatedItemFromMediaProjection(int i, String str, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        PrivacyLogger$logUpdatedItemFromMediaProjection$2 privacyLogger$logUpdatedItemFromMediaProjection$2 = new Function1() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$logUpdatedItemFromMediaProjection$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m92m = AbstractC0950x8906c950.m92m("MediaProjection: ", str1, "(", int1, "), active=");
                m92m.append(bool1);
                return m92m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$logUpdatedItemFromMediaProjection$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}
