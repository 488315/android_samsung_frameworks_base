package com.android.systemui.temporarydisplay;

import android.view.View;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class TemporaryViewLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public final String tag;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final void logViewAdditionDelayed(TemporaryViewInfo temporaryViewInfo) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$logViewAdditionDelayed$2 temporaryViewLogger$logViewAdditionDelayed$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logViewAdditionDelayed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("New view can't be displayed because higher priority view is currently displayed. New view id=", str1, " window=", str2, " priority=");
                m.append(str3);
                return m.toString();
            }
        };
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$logViewAdditionDelayed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = temporaryViewInfo.getPriority().name();
        logBuffer.commit(obtain);
    }

    public final void logViewExpiration(TemporaryViewInfo temporaryViewInfo) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$logViewExpiration$2 temporaryViewLogger$logViewExpiration$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logViewExpiration$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("View timeout has already expired; removing. id=", str1, " window=", str2, " priority=");
                m.append(str3);
                return m.toString();
            }
        };
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$logViewExpiration$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = temporaryViewInfo.getPriority().name();
        logBuffer.commit(obtain);
    }

    public final void logViewRemovalIgnored(String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$logViewRemovalIgnored$2 temporaryViewLogger$logViewRemovalIgnored$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logViewRemovalIgnored$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Removal of view with id=", logMessage.getStr2(), " is ignored because ", logMessage.getStr1());
            }
        };
        String str3 = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str3, logLevel, temporaryViewLogger$logViewRemovalIgnored$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
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
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Removing view from window manager", str, ". id=", str1, " window=");
                ConstraintWidget$$ExternalSyntheticOutline0.m(m, str2, " view=", str3, "(id=");
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(m, hexString, ")");
            }
        };
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$logViewRemovedFromWindowManager$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = view.getClass().getName();
        Companion.getClass();
        logMessageImpl.int1 = System.identityHashCode(view);
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logViewUpdate(TemporaryViewInfo temporaryViewInfo) {
        LogLevel logLevel = LogLevel.DEBUG;
        TemporaryViewLogger$logViewUpdate$2 temporaryViewLogger$logViewUpdate$2 = new Function1() { // from class: com.android.systemui.temporarydisplay.TemporaryViewLogger$logViewUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Existing view updated with new data. id=", str1, " window=", str2, " priority=");
                m.append(str3);
                return m.toString();
            }
        };
        String str = this.tag;
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, temporaryViewLogger$logViewUpdate$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = temporaryViewInfo.getId();
        logMessageImpl.str2 = temporaryViewInfo.getWindowTitle();
        logMessageImpl.str3 = temporaryViewInfo.getPriority().name();
        logBuffer.commit(obtain);
    }
}
