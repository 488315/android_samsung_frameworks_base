package com.android.systemui.toast;

import android.animation.Animator;
import android.app.ITransientNotificationCallback;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.UserHandle;
import android.view.Display;
import android.view.View;
import android.widget.ToastPresenter;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ToastPlugin;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        int i;
        int i2;
        ToastLogger toastLogger;
        String str;
        boolean z;
        IBinder iBinder;
        Throwable th;
        SystemUIToast systemUIToast;
        ToastUI toastUI = this.f$0;
        int i3 = this.f$1;
        int i4 = this.f$2;
        String str2 = this.f$3;
        IBinder iBinder2 = this.f$4;
        CharSequence charSequence = this.f$5;
        ITransientNotificationCallback iTransientNotificationCallback = this.f$6;
        IBinder iBinder3 = this.f$7;
        int i5 = this.f$8;
        toastUI.getClass();
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i3);
        Context createContextAsUser = toastUI.mContext.createContextAsUser(userHandleForUid, 0);
        Display display = ((DisplayManager) toastUI.mContext.getSystemService(DisplayManager.class)).getDisplay(i4);
        ToastLogger toastLogger2 = toastUI.mToastLogger;
        if (display == null) {
            String iBinder4 = iBinder2.toString();
            toastLogger2.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            ToastLogger$logOnSkipToastForInvalidDisplay$2 toastLogger$logOnSkipToastForInvalidDisplay$2 = new Function1() { // from class: com.android.systemui.toast.ToastLogger$logOnSkipToastForInvalidDisplay$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str22 = logMessage.getStr2();
                    String str1 = logMessage.getStr1();
                    int int1 = logMessage.getInt1();
                    StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str22, "] Skip toast for [", str1, "] scheduled on unavailable display #");
                    m.append(int1);
                    return m.toString();
                }
            };
            LogBuffer logBuffer = toastLogger2.buffer;
            LogMessage obtain = logBuffer.obtain("ToastLog", logLevel, toastLogger$logOnSkipToastForInvalidDisplay$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str2;
            logMessageImpl.str2 = iBinder4;
            logMessageImpl.int1 = i4;
            logBuffer.commit(obtain);
            return;
        }
        Context createDisplayContext = createContextAsUser.createDisplayContext(display);
        int identifier = userHandleForUid.getIdentifier();
        int i6 = toastUI.mOrientation;
        ToastFactory toastFactory = toastUI.mToastFactory;
        ToastPlugin toastPlugin = toastFactory.mPlugin;
        if (toastPlugin != null) {
            toastLogger = toastLogger2;
            i = i5;
            str = "ToastLog";
            i2 = i3;
            th = null;
            z = false;
            iBinder = iBinder3;
            systemUIToast = new SystemUIToast(toastFactory.mLayoutInflater, createDisplayContext, charSequence, toastPlugin.createToast(charSequence, str2, identifier), str2, identifier, i6);
        } else {
            i = i5;
            i2 = i3;
            toastLogger = toastLogger2;
            str = "ToastLog";
            z = false;
            iBinder = iBinder3;
            th = null;
            systemUIToast = new SystemUIToast(toastFactory.mLayoutInflater, createDisplayContext, charSequence, str2, identifier, i6);
        }
        toastUI.mToast = systemUIToast;
        Animator animator = systemUIToast.mInAnimator;
        if (animator != null) {
            animator.start();
        }
        toastUI.mCallback = iTransientNotificationCallback;
        ToastPresenter toastPresenter = new ToastPresenter(createDisplayContext, toastUI.mIAccessibilityManager, toastUI.mNotificationManager, str2);
        toastUI.mPresenter = toastPresenter;
        toastPresenter.getLayoutParams().setTrustedOverlay();
        String charSequence2 = charSequence.toString();
        String iBinder5 = iBinder2.toString();
        toastLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        ToastLogger$logOnShowToast$2 toastLogger$logOnShowToast$2 = new Function1() { // from class: com.android.systemui.toast.ToastLogger$logOnShowToast$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str3 = logMessage.getStr3();
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str22 = logMessage.getStr2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str3, "] Show toast for (", str1, ", ");
                m.append(int1);
                m.append("). msg='");
                m.append(str22);
                m.append("'");
                return m.toString();
            }
        };
        LogBuffer logBuffer2 = toastLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain(str, logLevel2, toastLogger$logOnShowToast$2, th);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
        logMessageImpl2.int1 = i2;
        logMessageImpl2.str1 = str2;
        logMessageImpl2.str2 = charSequence2;
        logMessageImpl2.str3 = iBinder5;
        logBuffer2.commit(obtain2);
        ToastPresenter toastPresenter2 = toastUI.mPresenter;
        SystemUIToast systemUIToast2 = toastUI.mToast;
        View view = systemUIToast2.mToastView;
        int intValue = systemUIToast2.getGravity().intValue();
        int intValue2 = toastUI.mToast.getXOffset().intValue();
        int intValue3 = toastUI.mToast.getYOffset().intValue();
        float intValue4 = toastUI.mToast.getHorizontalMargin().intValue();
        float intValue5 = toastUI.mToast.getVerticalMargin().intValue();
        ITransientNotificationCallback iTransientNotificationCallback2 = toastUI.mCallback;
        SystemUIToast systemUIToast3 = toastUI.mToast;
        toastPresenter2.show(view, iBinder2, iBinder, i, intValue, intValue2, intValue3, intValue4, intValue5, iTransientNotificationCallback2, (systemUIToast3.mInAnimator == null && systemUIToast3.mOutAnimator == null) ? z : true);
    }
}
