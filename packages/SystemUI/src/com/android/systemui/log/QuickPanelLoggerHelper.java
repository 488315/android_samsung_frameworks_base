package com.android.systemui.log;

import android.view.MotionEvent;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QuickPanelLoggerHelper {
    public final TouchLogger dispatchTouchEventLogger;
    public final FlingLogger flingLogger;
    public final TouchLogger handleTouchLogger;
    public final TouchLogger onInterceptTouchEventLogger;
    public final TouchLogger onTouchEventLogger;
    public final PanelStateLogger panelStateLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FlingLogger {
        public final QuickPanelExternalLogger externalLogger;
        public final StringBuilder logBuilder = new StringBuilder();

        public FlingLogger(QuickPanelExternalLogger quickPanelExternalLogger) {
            this.externalLogger = quickPanelExternalLogger;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PanelStateLogger {
        public final QuickPanelExternalLogger externalLogger;
        public final StringBuilder logBuilder = new StringBuilder();
        public String lastPanelState = "";

        public PanelStateLogger(QuickPanelExternalLogger quickPanelExternalLogger) {
            this.externalLogger = quickPanelExternalLogger;
        }
    }

    static {
        new Companion(null);
    }

    public QuickPanelLoggerHelper() {
        QuickPanelExternalLogger quickPanelExternalLogger = (QuickPanelExternalLogger) Dependency.sDependency.getDependencyInner(QuickPanelExternalLogger.class);
        this.dispatchTouchEventLogger = new TouchLogger("dispatchTouchEvent", quickPanelExternalLogger);
        this.flingLogger = new FlingLogger(quickPanelExternalLogger);
        this.handleTouchLogger = new TouchLogger("handleTouch", quickPanelExternalLogger);
        this.onInterceptTouchEventLogger = new TouchLogger("onInterceptTouchEvent", quickPanelExternalLogger);
        this.onTouchEventLogger = new TouchLogger("onTouchEvent", quickPanelExternalLogger);
        this.panelStateLogger = new PanelStateLogger(quickPanelExternalLogger);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TouchLogger {
        public final QuickPanelExternalLogger externalLogger;
        public final String header;
        public final StringBuilder logBuilder = new StringBuilder();
        public boolean moveEventAllowed;

        public TouchLogger(String str, QuickPanelExternalLogger quickPanelExternalLogger) {
            this.header = str;
            this.externalLogger = quickPanelExternalLogger;
        }

        public final String getToAction(MotionEvent motionEvent) {
            String str;
            String str2 = this.header;
            if (Intrinsics.areEqual(str2, "dispatchTouchEvent")) {
                return motionEvent.toString();
            }
            if (!Intrinsics.areEqual(str2, "onInterceptTouchEvent")) {
                return MotionEvent.actionToString(motionEvent.getAction());
            }
            String actionToString = MotionEvent.actionToString(motionEvent.getAction());
            if (motionEvent.getPointerCount() > 0) {
                str = " | x:" + motionEvent.getX(0) + ", y:" + motionEvent.getY(0);
            } else {
                str = "";
            }
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(actionToString, str);
        }

        public final void log(MotionEvent motionEvent, String str, String str2) {
            boolean z;
            if (motionEvent.getAction() != 2) {
                z = true;
            } else if (!this.moveEventAllowed) {
                return;
            } else {
                z = false;
            }
            this.moveEventAllowed = z;
            StringBuilder sb = this.logBuilder;
            sb.setLength(0);
            sb.append(str);
            sb.append(" | ");
            sb.append(this.header);
            sb.append(" | ");
            sb.append(getToAction(motionEvent));
            sb.append(" | ");
            sb.append(str2);
            sb.append(" | ");
            QuickPanelExternalLogger quickPanelExternalLogger = this.externalLogger;
            sb.append(StatusBarState.toString(((StatusBarStateControllerImpl) quickPanelExternalLogger.statusBarStateController).mState));
            quickPanelExternalLogger.log("[TOUCH]", sb.toString());
        }

        public final void log(MotionEvent motionEvent, String str, String str2, boolean z) {
            if (motionEvent.getAction() == 2) {
                return;
            }
            StringBuilder sb = this.logBuilder;
            sb.setLength(0);
            sb.append(str);
            sb.append(" | ");
            sb.append(this.header);
            sb.append(" | ");
            sb.append(getToAction(motionEvent));
            sb.append(" | ");
            sb.append("return: ");
            sb.append(z);
            sb.append(" | ");
            QuickPanelExternalLogger quickPanelExternalLogger = this.externalLogger;
            sb.append(StatusBarState.toString(((StatusBarStateControllerImpl) quickPanelExternalLogger.statusBarStateController).mState));
            sb.append(" | ");
            sb.append(str2);
            quickPanelExternalLogger.log("[TOUCH]", sb.toString());
        }
    }
}
