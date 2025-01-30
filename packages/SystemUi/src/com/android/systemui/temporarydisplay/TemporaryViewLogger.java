package com.android.systemui.temporarydisplay;

import android.view.View;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TemporaryViewLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public final String tag;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TemporaryViewLogger(LogBuffer logBuffer, String str) {
        this.buffer = logBuffer;
        this.tag = str;
    }

    public final void logAnimateInFailure() {
        LogLevel logLevel = LogLevel.WARNING;
        TemporaryViewLogger$logAnimateInFailure$2 temporaryViewLogger$logAnimateInFailure$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logAnimateInFailure$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "View's appearance animation failed. Forcing view display manually.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain(this.tag, logLevel, temporaryViewLogger$logAnimateInFailure$2, null));
    }

    public final void logAnimateOutFailure() {
        LogLevel logLevel = LogLevel.WARNING;
        TemporaryViewLogger$logAnimateOutFailure$2 temporaryViewLogger$logAnimateOutFailure$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logAnimateOutFailure$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "View's disappearance animation failed.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain(this.tag, logLevel, temporaryViewLogger$logAnimateOutFailure$2, null));
    }

    public final void logViewRemovalIgnored(String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$logViewRemovalIgnored$2 temporaryViewLogger$logViewRemovalIgnored$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logViewRemovalIgnored$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("Removal of view with id=", logMessage.getStr2(), " is ignored because ", logMessage.getStr1());
            }
        };
        String str3 = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str3, logLevel, temporaryViewLogger$logViewRemovalIgnored$2, null);
        CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, str2, str, logBuffer, obtain);
    }

    public final void logViewRemovedFromWindowManager(TemporaryViewInfo temporaryViewInfo, View view, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$logViewRemovedFromWindowManager$2 temporaryViewLogger$logViewRemovedFromWindowManager$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logViewRemovedFromWindowManager$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str = logMessage.getBool1() ? " due to reinflation" : "";
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                String hexString = Integer.toHexString(logMessage.getInt1());
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("Removing view from window manager", str, ". id=", str1, " window=");
                AppOpItem$$ExternalSyntheticOutline0.m97m(m87m, str2, " view=", str3, "(id=");
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(m87m, hexString, ")");
            }
        };
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$logViewRemovedFromWindowManager$2, null);
        obtain.setStr1(temporaryViewInfo.getId());
        obtain.setStr2(temporaryViewInfo.getWindowTitle());
        obtain.setStr3(view.getClass().getName());
        Companion.getClass();
        obtain.setInt1(System.identityHashCode(view));
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}
