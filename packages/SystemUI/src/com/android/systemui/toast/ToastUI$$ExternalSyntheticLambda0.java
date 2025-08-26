package com.android.systemui.toast;

import android.animation.Animator;
import android.app.ITransientNotificationCallback;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ToastPresenter;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes3.dex */
public final /* synthetic */ class ToastUI$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ToastUI f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ IBinder f$4;
    public final /* synthetic */ CharSequence f$5;
    public final /* synthetic */ ITransientNotificationCallback f$6;
    public final /* synthetic */ IBinder f$7;
    public final /* synthetic */ int f$8;

    public /* synthetic */ ToastUI$$ExternalSyntheticLambda0(ToastUI toastUI, int i, int i2, String str, IBinder iBinder, CharSequence charSequence, ITransientNotificationCallback iTransientNotificationCallback, IBinder iBinder2, int i3) {
        this.f$0 = toastUI;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = str;
        this.f$4 = iBinder;
        this.f$5 = charSequence;
        this.f$6 = iTransientNotificationCallback;
        this.f$7 = iBinder2;
        this.f$8 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ToastUI toastUI = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        String str = this.f$3;
        IBinder iBinder = this.f$4;
        CharSequence charSequence = this.f$5;
        ITransientNotificationCallback iTransientNotificationCallback = this.f$6;
        IBinder iBinder2 = this.f$7;
        int i3 = this.f$8;
        int i4 = ToastUI.$r8$clinit;
        toastUI.getClass();
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        try {
            Context contextCreateContextAsUser = toastUI.mContext.createContextAsUser(userHandleForUid, 0);
            Display display = ((DisplayManager) toastUI.mContext.getSystemService(DisplayManager.class)).getDisplay(i2);
            ToastLogger toastLogger = toastUI.mToastLogger;
            if (display == null) {
                String string = iBinder.toString();
                toastLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                ToastLogger$$ExternalSyntheticLambda0 toastLogger$$ExternalSyntheticLambda0 = new ToastLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = toastLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("ToastLog", logLevel, toastLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = string;
                logMessageImpl.int1 = i2;
                logBuffer.commit(logMessageObtain);
                return;
            }
            Context contextCreateDisplayContext = contextCreateContextAsUser.createDisplayContext(display);
            SystemUIToast systemUIToastCreateToast = toastUI.mToastFactory.createToast(toastUI.mContext, contextCreateDisplayContext, charSequence, str, userHandleForUid.getIdentifier(), toastUI.mOrientation);
            toastUI.mToast = systemUIToastCreateToast;
            Animator animator = systemUIToastCreateToast.mInAnimator;
            if (animator != null) {
                animator.start();
            }
            toastUI.mCallback = iTransientNotificationCallback;
            ToastPresenter toastPresenter = new ToastPresenter(contextCreateDisplayContext, toastUI.mIAccessibilityManager, toastUI.mNotificationManager, str);
            toastUI.mPresenter = toastPresenter;
            toastPresenter.getLayoutParams().setTrustedOverlay();
            String string2 = charSequence.toString();
            String string3 = iBinder.toString();
            toastLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            ToastLogger$$ExternalSyntheticLambda0 toastLogger$$ExternalSyntheticLambda02 = new ToastLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer2 = toastLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("ToastLog", logLevel2, toastLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.int1 = i;
            logMessageImpl2.str1 = str;
            logMessageImpl2.str2 = string2;
            logMessageImpl2.str3 = string3;
            logBuffer2.commit(logMessageObtain2);
            ToastPresenter toastPresenter2 = toastUI.mPresenter;
            SystemUIToast systemUIToast = toastUI.mToast;
            View view = systemUIToast.mToastView;
            int iIntValue = systemUIToast.getGravity().intValue();
            int iIntValue2 = toastUI.mToast.getXOffset().intValue();
            int iIntValue3 = toastUI.mToast.getYOffset().intValue();
            float fIntValue = toastUI.mToast.getHorizontalMargin().intValue();
            float fIntValue2 = toastUI.mToast.getVerticalMargin().intValue();
            ITransientNotificationCallback iTransientNotificationCallback2 = toastUI.mCallback;
            SystemUIToast systemUIToast2 = toastUI.mToast;
            toastPresenter2.show(view, iBinder, iBinder2, i3, iIntValue, iIntValue2, iIntValue3, fIntValue, fIntValue2, iTransientNotificationCallback2, (systemUIToast2.mInAnimator == null && systemUIToast2.mOutAnimator == null) ? false : true);
        } catch (IllegalStateException e) {
            Log.e("ToastUI", "Cannot create toast because cannot create context", e);
        }
    }
}
