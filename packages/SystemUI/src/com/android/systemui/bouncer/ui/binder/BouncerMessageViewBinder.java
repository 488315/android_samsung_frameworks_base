package com.android.systemui.bouncer.ui.binder;

import com.android.keyguard.KeyguardMessageAreaController;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.ui.BouncerMessageView;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.BouncerLogger;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BouncerMessageViewBinder {
    public static final BouncerMessageViewBinder INSTANCE = new BouncerMessageViewBinder();

    private BouncerMessageViewBinder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$updateView(com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder r2, com.android.keyguard.KeyguardSecMessageAreaController r3, com.android.keyguard.BouncerKeyguardMessageArea r4, com.android.systemui.bouncer.shared.model.Message r5, boolean r6) {
        /*
            r2.getClass()
            if (r4 == 0) goto L6a
            if (r3 != 0) goto L8
            goto L6a
        L8:
            r2 = 0
            if (r5 == 0) goto Le
            java.lang.String r0 = r5.message
            goto Lf
        Le:
            r0 = r2
        Lf:
            if (r0 != 0) goto L20
            if (r5 == 0) goto L15
            java.lang.Integer r2 = r5.messageResId
        L15:
            if (r2 == 0) goto L18
            goto L20
        L18:
            r2 = 0
            r3.setIsVisible(r2)
            r3.setMessage(r2)
            goto L57
        L20:
            r2 = 1
            r3.setIsVisible(r2)
            java.lang.String r2 = r5.message
            if (r2 != 0) goto L2a
            java.lang.String r2 = ""
        L2a:
            java.lang.Integer r0 = r5.messageResId
            if (r0 == 0) goto L52
            int r1 = r0.intValue()
            if (r1 == 0) goto L52
            android.content.res.Resources r2 = r4.getResources()
            int r1 = r0.intValue()
            java.lang.String r2 = r2.getString(r1)
            java.util.Map r1 = r5.formatterArgs
            if (r1 == 0) goto L52
            android.content.res.Resources r2 = r4.getResources()
            java.util.Map r1 = r5.formatterArgs
            int r0 = r0.intValue()
            java.lang.String r2 = android.util.PluralsMessageFormatter.format(r2, r1, r0)
        L52:
            boolean r0 = r5.animate
            r3.setMessage$1(r2, r0)
        L57:
            if (r5 == 0) goto L60
            android.content.res.ColorStateList r2 = r5.colorState
            if (r2 == 0) goto L60
            r3.setNextMessageColor(r2)
        L60:
            if (r6 == 0) goto L65
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.END
            goto L67
        L65:
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.MARQUEE
        L67:
            r4.setEllipsize(r2)
        L6a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder.access$updateView(com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder, com.android.keyguard.KeyguardSecMessageAreaController, com.android.keyguard.BouncerKeyguardMessageArea, com.android.systemui.bouncer.shared.model.Message, boolean):void");
    }

    public static final void bind(BouncerMessageView bouncerMessageView, BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, BouncerLogger bouncerLogger) {
        RepeatWhenAttachedKt.repeatWhenAttached(bouncerMessageView, EmptyCoroutineContext.INSTANCE, new BouncerMessageViewBinder$bind$1(bouncerMessageView, factory, bouncerLogger, bouncerMessageInteractor, null));
    }
}
