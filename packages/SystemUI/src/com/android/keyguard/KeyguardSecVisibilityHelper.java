package com.android.keyguard;

import android.view.View;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardSecVisibilityHelper {
    public boolean isVisibilityAnimating;
    public final boolean mAnimateYPos;
    public final KeyguardStateController mKeyguardStateController;
    public boolean mLastOccludedState;
    public final LogBuffer mLogBuffer;
    public DcmMascotViewContainer mMascotViewContainer;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final View mView;
    public final AnimationProperties mAnimationProperties = new AnimationProperties();
    public final KeyguardSecVisibilityHelper$mSetInvisibleEndAction$1 mSetInvisibleEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardSecVisibilityHelper$mSetInvisibleEndAction$1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            KeyguardSecVisibilityHelper keyguardSecVisibilityHelper = KeyguardSecVisibilityHelper.this;
            keyguardSecVisibilityHelper.isVisibilityAnimating = false;
            keyguardSecVisibilityHelper.mView.setVisibility(4);
            LogBuffer logBuffer = KeyguardSecVisibilityHelper.this.mLogBuffer;
            if (logBuffer != null) {
                LogBuffer.log$default(logBuffer, "KeyguardSecVisibilityHelper", LogLevel.DEBUG, "Callback Set Visibility to INVISIBLE");
            }
        }
    };
    public final KeyguardSecVisibilityHelper$mSetGoneEndAction$1 mSetGoneEndAction = new Consumer() { // from class: com.android.keyguard.KeyguardSecVisibilityHelper$mSetGoneEndAction$1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            KeyguardSecVisibilityHelper keyguardSecVisibilityHelper = KeyguardSecVisibilityHelper.this;
            keyguardSecVisibilityHelper.isVisibilityAnimating = false;
            keyguardSecVisibilityHelper.mView.setVisibility(8);
            LogBuffer logBuffer = KeyguardSecVisibilityHelper.this.mLogBuffer;
            if (logBuffer != null) {
                LogBuffer.log$default(logBuffer, "KeyguardSecVisibilityHelper", LogLevel.DEBUG, "CallbackSet Visibility to GONE");
            }
        }
    };
    public final KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1 mSetVisibleEndRunnable = new KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1(this);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardSecVisibilityHelper$mSetInvisibleEndAction$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardSecVisibilityHelper$mSetGoneEndAction$1] */
    public KeyguardSecVisibilityHelper(View view, KeyguardStateController keyguardStateController, ScreenOffAnimationController screenOffAnimationController, boolean z, LogBuffer logBuffer) {
        this.mView = view;
        this.mKeyguardStateController = keyguardStateController;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mAnimateYPos = z;
        this.mLogBuffer = logBuffer;
    }
}
