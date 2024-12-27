package com.android.keyguard;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.ImeBackAnimationController;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.widget.FrameLayout;
import android.window.CompatOnBackInvokedCallback;
import android.window.ImeOnBackInvokedDispatcher;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.WindowOnBackInvokedDispatcher;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.util.SafeUIState;

public final class KeyguardBouncerContainer extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAttachStateListener;
    public boolean mIsBackCallbackRegistered;
    public final KeyguardSysDumpTrigger mKeyguardSysDumpTrigger;
    public final AnonymousClass2 mOnBackInvokedCallback;
    public final SysUIKeyEventHandler mSysUIKeyEventHandler;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.keyguard.KeyguardBouncerContainer$2] */
    public KeyguardBouncerContainer(Context context, SysUIKeyEventHandler sysUIKeyEventHandler, KeyguardSysDumpTrigger keyguardSysDumpTrigger) {
        super(context);
        View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.KeyguardBouncerContainer.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                KeyguardBouncerContainer keyguardBouncerContainer = KeyguardBouncerContainer.this;
                Context context2 = keyguardBouncerContainer.getContext();
                keyguardBouncerContainer.getClass();
                try {
                    if (context2.getPackageManager().getApplicationInfo("com.salab.issuetracker", 0) != null) {
                        if (context2.getPackageManager().checkSignatures("com.salab.issuetracker", "android") == 0) {
                            KeyguardBouncerContainer keyguardBouncerContainer2 = KeyguardBouncerContainer.this;
                            ViewRootImpl viewRootImpl = keyguardBouncerContainer2.getViewRootImpl();
                            if (viewRootImpl == null) {
                                Log.d("KeyguardBouncerContainer", "checkRegisterBackInvokedCallbackIntegrity : Cannot check because RootView is null");
                            } else if (viewRootImpl.getOnBackInvokedDispatcher().isOnBackInvokedCallbackEnabled()) {
                                Log.d("KeyguardBouncerContainer", "checkRegisterBackInvokedCallbackIntegrity : Pass !!");
                            } else {
                                Log.d("KeyguardBouncerContainer", "checkRegisterBackInvokedCallbackIntegrity isOnBackInvokedCallbackEnabled() : WindowOnBackInvokedDispatcher = false, ApplicationInfo = " + keyguardBouncerContainer2.getContext().getApplicationInfo().isOnBackInvokedCallbackEnabled());
                                Log.d("KeyguardBouncerContainer", "Cannot registerBackInvokedCallback !!");
                                final KeyguardSysDumpTrigger keyguardSysDumpTrigger2 = keyguardBouncerContainer2.mKeyguardSysDumpTrigger;
                                keyguardSysDumpTrigger2.getClass();
                                Log.d("KeyguardSysDumpTrigger", "setTriggerIssueReport !!");
                                if (keyguardSysDumpTrigger2.isEnabled()) {
                                    synchronized (keyguardSysDumpTrigger2) {
                                        synchronized (keyguardSysDumpTrigger2) {
                                            Runnable runnable = keyguardSysDumpTrigger2.cancelIssueReportExecToken;
                                            if (runnable != null) {
                                                runnable.run();
                                                keyguardSysDumpTrigger2.cancelIssueReportExecToken = null;
                                            }
                                        }
                                    }
                                    final long j = -1;
                                    final int i = 4;
                                    keyguardSysDumpTrigger2.cancelIssueReportExecToken = keyguardSysDumpTrigger2.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSysDumpTrigger$startIssueReport$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            KeyguardSysDumpTrigger keyguardSysDumpTrigger3 = KeyguardSysDumpTrigger.this;
                                            int i2 = i;
                                            long j2 = j;
                                            if (j2 <= 0) {
                                                j2 = System.currentTimeMillis();
                                            }
                                            keyguardSysDumpTrigger3.sendIssueReportIntent(i2, j2);
                                        }
                                    }, 500L);
                                    Log.d("KeyguardSysDumpTrigger", "startIssueReport 500");
                                }
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
                KeyguardBouncerContainer.this.registerBackInvokedCallback();
                KeyguardBouncerContainer.this.checkBackInvokedCallback(false);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ViewRootImpl viewRootImpl;
                KeyguardBouncerContainer keyguardBouncerContainer = KeyguardBouncerContainer.this;
                int i = KeyguardBouncerContainer.$r8$clinit;
                keyguardBouncerContainer.getClass();
                Log.d("KeyguardBouncerContainer", "unregisterBackInvokedCallback");
                if (!keyguardBouncerContainer.mIsBackCallbackRegistered || (viewRootImpl = keyguardBouncerContainer.getViewRootImpl()) == null) {
                    return;
                }
                viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(keyguardBouncerContainer.mOnBackInvokedCallback);
                keyguardBouncerContainer.mIsBackCallbackRegistered = false;
            }
        };
        this.mOnBackInvokedCallback = new OnBackAnimationCallback() { // from class: com.android.keyguard.KeyguardBouncerContainer.2
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                KeyguardBouncerContainer.this.mSysUIKeyEventHandler.dispatchKeyEvent(new KeyEvent(1, 4));
            }
        };
        this.mIsBackCallbackRegistered = false;
        this.mSysUIKeyEventHandler = sysUIKeyEventHandler;
        this.mKeyguardSysDumpTrigger = keyguardSysDumpTrigger;
        addOnAttachStateChangeListener(onAttachStateChangeListener);
    }

    public static String getBackCallbackInstanceName(OnBackInvokedCallback onBackInvokedCallback) {
        if (onBackInvokedCallback == null) {
            return "null";
        }
        String str = onBackInvokedCallback instanceof OnBackAnimationCallback ? "OnBackAnimationCallback" : "null";
        if (onBackInvokedCallback instanceof ImeBackAnimationController) {
            str = "ImeBackAnimationController";
        }
        if (onBackInvokedCallback instanceof ImeOnBackInvokedDispatcher) {
            str = "ImeOnBackInvokedDispatcher";
        }
        return onBackInvokedCallback instanceof CompatOnBackInvokedCallback ? "CompatOnBackInvokedCallback" : str;
    }

    public final void checkBackInvokedCallback(boolean z) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            Log.d("KeyguardBouncerContainer", "checkBackInvokedCallback get failed");
            return;
        }
        WindowOnBackInvokedDispatcher onBackInvokedDispatcher = viewRootImpl.getOnBackInvokedDispatcher();
        OnBackInvokedCallback topCallback = onBackInvokedDispatcher.getTopCallback();
        Log.d("KeyguardBouncerContainer", "checkBackInvokedCallback isOnBackInvokedCallbackEnabled= " + onBackInvokedDispatcher.isOnBackInvokedCallbackEnabled() + ", instanceOf (" + getBackCallbackInstanceName(topCallback) + ")= " + topCallback);
        if (z && topCallback != null && (topCallback instanceof CompatOnBackInvokedCallback) && onBackInvokedDispatcher.isOnBackInvokedCallbackEnabled()) {
            this.mIsBackCallbackRegistered = false;
            registerBackInvokedCallback();
            OnBackInvokedCallback topCallback2 = onBackInvokedDispatcher.getTopCallback();
            Log.d("KeyguardBouncerContainer", "registerBackInvokedCallback restored!! isOnBackInvokedCallbackEnabled= " + onBackInvokedDispatcher.isOnBackInvokedCallbackEnabled() + ", instanceOf (" + getBackCallbackInstanceName(topCallback2) + ")= " + topCallback2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (this.mSysUIKeyEventHandler.dispatchKeyEvent(keyEvent) || this.mSysUIKeyEventHandler.interceptMediaKey(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void registerBackInvokedCallback() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("registerBackInvokedCallback :  registered="), this.mIsBackCallbackRegistered, "KeyguardBouncerContainer");
        if (this.mIsBackCallbackRegistered) {
            Log.d("KeyguardBouncerContainer", "prevented registering back callback twice");
            return;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            Log.d("KeyguardBouncerContainer", "view root was null, could not register back callback");
        } else {
            viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
            this.mIsBackCallbackRegistered = true;
        }
    }
}
