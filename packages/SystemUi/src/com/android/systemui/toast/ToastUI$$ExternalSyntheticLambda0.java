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
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.ToastPlugin;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ToastUI$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ToastUI f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ CharSequence f$3;
    public final /* synthetic */ String f$4;
    public final /* synthetic */ ITransientNotificationCallback f$5;
    public final /* synthetic */ IBinder f$6;
    public final /* synthetic */ IBinder f$7;
    public final /* synthetic */ int f$8;

    public /* synthetic */ ToastUI$$ExternalSyntheticLambda0(ToastUI toastUI, int i, int i2, CharSequence charSequence, String str, ITransientNotificationCallback iTransientNotificationCallback, IBinder iBinder, IBinder iBinder2, int i3) {
        this.f$0 = toastUI;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = charSequence;
        this.f$4 = str;
        this.f$5 = iTransientNotificationCallback;
        this.f$6 = iBinder;
        this.f$7 = iBinder2;
        this.f$8 = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        IBinder iBinder;
        SystemUIToast systemUIToast;
        IBinder iBinder2;
        boolean z2;
        ToastUI toastUI = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        CharSequence charSequence = this.f$3;
        String str = this.f$4;
        ITransientNotificationCallback iTransientNotificationCallback = this.f$5;
        IBinder iBinder3 = this.f$6;
        IBinder iBinder4 = this.f$7;
        int i3 = this.f$8;
        toastUI.getClass();
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        Context context = toastUI.mContext;
        Context createContextAsUser = context.createContextAsUser(userHandleForUid, 0);
        Display display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i2);
        if (display == null) {
            Log.w("ToastUI", "showToast: display " + i2 + " doesn't exist. Toast is ignored!!");
            return;
        }
        Context createDisplayContext = createContextAsUser.createDisplayContext(display);
        int identifier = userHandleForUid.getIdentifier();
        int i4 = toastUI.mOrientation;
        ToastFactory toastFactory = toastUI.mToastFactory;
        ToastPlugin toastPlugin = toastFactory.mPlugin;
        if (toastPlugin != null) {
            z = false;
            iBinder = iBinder4;
            systemUIToast = new SystemUIToast(toastFactory.mLayoutInflater, createDisplayContext, charSequence, toastPlugin.createToast(charSequence, str, identifier), str, identifier, i4);
        } else {
            z = false;
            iBinder = iBinder4;
            systemUIToast = new SystemUIToast(toastFactory.mLayoutInflater, createDisplayContext, charSequence, str, identifier, i4);
        }
        toastUI.mToast = systemUIToast;
        Animator animator = systemUIToast.mInAnimator;
        if (animator != null) {
            animator.start();
        }
        toastUI.mCallback = iTransientNotificationCallback;
        ToastPresenter toastPresenter = new ToastPresenter(createDisplayContext, toastUI.mIAccessibilityManager, toastUI.mNotificationManager, str);
        toastUI.mPresenter = toastPresenter;
        toastPresenter.getLayoutParams().setTrustedOverlay();
        String charSequence2 = charSequence.toString();
        String obj = iBinder3.toString();
        ToastLogger toastLogger = toastUI.mToastLogger;
        toastLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ToastLogger$logOnShowToast$2 toastLogger$logOnShowToast$2 = new Function1() { // from class: com.android.systemui.toast.ToastLogger$logOnShowToast$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                String str3 = logMessage.getStr3();
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("[", str3, "] Show toast for (", str1, ", ");
                m87m.append(int1);
                m87m.append("). msg='");
                m87m.append(str2);
                m87m.append("'");
                return m87m.toString();
            }
        };
        LogBuffer logBuffer = toastLogger.buffer;
        LogMessage obtain = logBuffer.obtain("ToastLog", logLevel, toastLogger$logOnShowToast$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        obtain.setStr2(charSequence2);
        obtain.setStr3(obj);
        logBuffer.commit(obtain);
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
        if (systemUIToast3.mInAnimator == null && systemUIToast3.mOutAnimator == null) {
            iBinder2 = iBinder3;
            z2 = z;
        } else {
            iBinder2 = iBinder3;
            z2 = true;
        }
        toastPresenter2.show(view, iBinder2, iBinder, i3, intValue, intValue2, intValue3, intValue4, intValue5, iTransientNotificationCallback2, z2);
    }
}
