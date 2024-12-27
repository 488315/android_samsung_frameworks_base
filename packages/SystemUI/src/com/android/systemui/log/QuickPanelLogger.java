package com.android.systemui.log;

import android.os.Debug;
import android.view.MotionEvent;
import com.android.systemui.log.QuickPanelLoggerHelper;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QuickPanelLogger {
    public final QuickPanelLoggerHelper quickPanelLoggerHelper = new QuickPanelLoggerHelper();
    public final String tag;

    public QuickPanelLogger(String str) {
        this.tag = str;
    }

    public final void handleTouch(MotionEvent motionEvent, String str, boolean z) {
        this.quickPanelLoggerHelper.handleTouchLogger.log(motionEvent, this.tag, str, z);
    }

    public final void logPanelState(String str) {
        QuickPanelLoggerHelper.PanelStateLogger panelStateLogger = this.quickPanelLoggerHelper.panelStateLogger;
        String str2 = Intrinsics.areEqual(panelStateLogger.lastPanelState, str) ^ true ? str : null;
        if (str2 != null) {
            panelStateLogger.lastPanelState = str2;
            StringBuilder sb = panelStateLogger.logBuilder;
            sb.setLength(0);
            sb.append(this.tag);
            sb.append(" | ");
            sb.append(str);
            sb.append("\n");
            sb.append(Debug.getCallers(10, " - "));
            panelStateLogger.externalLogger.log("[PANEL_STATE_INFO]", sb.toString());
        }
    }

    public final void onInterceptTouchEvent(MotionEvent motionEvent) {
        this.quickPanelLoggerHelper.onInterceptTouchEventLogger.log(motionEvent, this.tag, "");
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
        this.quickPanelLoggerHelper.onTouchEventLogger.log(motionEvent, this.tag, "");
    }

    public final void onTouchEvent(MotionEvent motionEvent, String str, boolean z) {
        this.quickPanelLoggerHelper.onTouchEventLogger.log(motionEvent, this.tag, str, z);
    }

    public final void onInterceptTouchEvent(MotionEvent motionEvent, String str, boolean z) {
        this.quickPanelLoggerHelper.onInterceptTouchEventLogger.log(motionEvent, this.tag, str, z);
    }
}
