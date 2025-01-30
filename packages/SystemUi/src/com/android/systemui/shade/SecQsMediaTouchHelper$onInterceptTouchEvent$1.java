package com.android.systemui.shade;

import android.view.MotionEvent;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class SecQsMediaTouchHelper$onInterceptTouchEvent$1 extends FunctionReferenceImpl implements Function3 {
    public SecQsMediaTouchHelper$onInterceptTouchEvent$1(Object obj) {
        super(3, obj, SecPanelLogger.class, "addNpvcInterceptTouchLog", "addNpvcInterceptTouchLog(Landroid/view/MotionEvent;Ljava/lang/String;Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        ((SecPanelLoggerImpl) ((SecPanelLogger) this.receiver)).addNpvcInterceptTouchLog((MotionEvent) obj, (String) obj2, booleanValue);
        return Unit.INSTANCE;
    }
}
