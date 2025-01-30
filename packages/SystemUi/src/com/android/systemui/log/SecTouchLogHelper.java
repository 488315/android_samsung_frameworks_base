package com.android.systemui.log;

import android.util.Log;
import android.view.MotionEvent;
import com.android.systemui.Dependency;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecTouchLogHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean shouldPrintOnInterceptTouchEventMove;
    public boolean shouldPrintOnTouchEventMove;
    public boolean touchMoveLogFlag;
    public final Lazy secPanelLogger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.log.SecTouchLogHelper$secPanelLogger$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelLogger) Dependency.get(SecPanelLogger.class);
        }
    });
    public final StringBuilder logBuilder = new StringBuilder();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public final void printDispatchTouchEvent(MotionEvent motionEvent, String str) {
        if (motionEvent.getAction() != 2) {
            printLogInternal(str, "dispatchTouch", motionEvent.toString(), "");
            this.touchMoveLogFlag = true;
        } else if (this.touchMoveLogFlag) {
            printLogInternal(str, "dispatchTouch", motionEvent.toString(), "");
            this.touchMoveLogFlag = false;
        }
    }

    public final void printLogInternal(String str, String str2, String str3, String str4) {
        StringBuilder sb = this.logBuilder;
        sb.setLength(0);
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        AppOpItem$$ExternalSyntheticOutline0.m97m(sb, "(", str3, ") ", str4);
        SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) ((SecPanelLogger) this.secPanelLogger$delegate.getValue());
        secPanelLoggerImpl.appendStatusBarState(sb, " | ");
        String sb2 = sb.toString();
        Log.d("SecPanelLogger", sb2);
        secPanelLoggerImpl.writer.logPanel("TOUCH", sb2);
    }

    public final void printOnInterceptTouchEventLog(MotionEvent motionEvent, String str, String str2) {
        int action = motionEvent.getAction();
        String actionToString = MotionEvent.actionToString(action);
        if (action != 2) {
            printLogInternal(str, "interceptTouch", actionToString, str2);
            this.shouldPrintOnInterceptTouchEventMove = true;
        } else if (this.shouldPrintOnInterceptTouchEventMove) {
            printLogInternal(str, "interceptTouch", actionToString, str2);
            this.shouldPrintOnInterceptTouchEventMove = false;
        }
    }

    public final void printOnTouchEventLog(MotionEvent motionEvent, String str, String str2) {
        int action = motionEvent.getAction();
        String actionToString = MotionEvent.actionToString(action);
        if (action != 2) {
            printLogInternal(str, "onTouch", actionToString, str2);
            this.shouldPrintOnTouchEventMove = true;
        } else if (this.shouldPrintOnTouchEventMove) {
            printLogInternal(str, "onTouch", actionToString, str2);
            this.shouldPrintOnTouchEventMove = false;
        }
    }
}
