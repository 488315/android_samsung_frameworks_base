package com.android.systemui.keyguard;

import android.content.res.Configuration;
import android.os.Handler;
import android.view.IDisplayWindowListener;
import android.view.IWindowManager;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import dagger.Lazy;
import java.util.List;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardFixedRotationMonitor {
    public static final boolean DEBUG;
    public final Handler handler;
    public boolean isFixedRotated;
    public boolean isMonitorStarted;
    public final Lazy notificationShadeWindowController;
    public Runnable pendingRunnable;
    public final IWindowManager windowManager;
    public final kotlin.Lazy notificationShadeView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardFixedRotationMonitor$notificationShadeView$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) KeyguardFixedRotationMonitor.this.notificationShadeWindowController.get())).mWindowRootView;
        }
    });
    public final KeyguardFixedRotationMonitor$preDrawListener$1 preDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.keyguard.KeyguardFixedRotationMonitor$preDrawListener$1
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public final boolean onPreDraw() {
            KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = KeyguardFixedRotationMonitor.this;
            boolean z = KeyguardFixedRotationMonitor.DEBUG;
            ViewGroup viewGroup = (ViewGroup) keyguardFixedRotationMonitor.notificationShadeView$delegate.getValue();
            boolean hasWindowFocus = viewGroup != null ? viewGroup.hasWindowFocus() : false;
            if (!hasWindowFocus) {
                android.util.Log.d("KeyguardFixedRotation", "onPreDraw no window focus");
            }
            Runnable runnable = keyguardFixedRotationMonitor.pendingRunnable;
            if (runnable == null || hasWindowFocus) {
                return true;
            }
            runnable.run();
            keyguardFixedRotationMonitor.setPendingRunnable(null);
            return true;
        }
    };
    public final KeyguardFixedRotationMonitor$safeRunnable$1 safeRunnable = new KeyguardFixedRotationMonitor$safeRunnable$1(this);
    public final KeyguardFixedRotationMonitor$displayWindowListener$1 displayWindowListener = new IDisplayWindowListener.Stub() { // from class: com.android.systemui.keyguard.KeyguardFixedRotationMonitor$displayWindowListener$1
        public final void onFixedRotationStarted(int i, final int i2) {
            KeyguardFixedRotationMonitor keyguardFixedRotationMonitor = KeyguardFixedRotationMonitor.this;
            String m = HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(i, i2, "onFixedRotationStarted ", ", ");
            boolean z = KeyguardFixedRotationMonitor.DEBUG;
            keyguardFixedRotationMonitor.getClass();
            if (KeyguardFixedRotationMonitor.DEBUG) {
                android.util.Log.d("KeyguardFixedRotation", m);
            }
            if (i != 0) {
                return;
            }
            final KeyguardFixedRotationMonitor keyguardFixedRotationMonitor2 = KeyguardFixedRotationMonitor.this;
            keyguardFixedRotationMonitor2.handler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFixedRotationMonitor$displayWindowListener$1$onFixedRotationStarted$1
                @Override // java.lang.Runnable
                public final void run() {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onFixedRotationStarted rotation=", "KeyguardFixedRotation");
                    keyguardFixedRotationMonitor2.isFixedRotated = true;
                }
            });
        }

        public final void onDisplayAdded(int i) {
        }

        public final void onDisplayRemoved(int i) {
        }

        public final void onFixedRotationFinished(int i) {
        }

        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        }

        public final void onKeepClearAreasChanged(int i, List list, List list2) {
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        DEBUG = android.util.Log.isLoggable("KeyguardFixedRotation", 3);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.keyguard.KeyguardFixedRotationMonitor$preDrawListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.keyguard.KeyguardFixedRotationMonitor$displayWindowListener$1] */
    public KeyguardFixedRotationMonitor(Lazy lazy, Handler handler, IWindowManager iWindowManager) {
        this.notificationShadeWindowController = lazy;
        this.handler = handler;
        this.windowManager = iWindowManager;
    }

    public final void cancel() {
        if (this.isMonitorStarted) {
            android.util.Log.d("KeyguardFixedRotation", "cancel");
            KeyguardFixedRotationMonitor$safeRunnable$1 keyguardFixedRotationMonitor$safeRunnable$1 = this.safeRunnable;
            Handler handler = this.handler;
            if (handler.hasCallbacks(keyguardFixedRotationMonitor$safeRunnable$1)) {
                handler.removeCallbacks(this.safeRunnable);
                this.safeRunnable.run();
            }
            this.windowManager.unregisterDisplayWindowListener(this.displayWindowListener);
            this.isFixedRotated = false;
            this.isMonitorStarted = false;
        }
    }

    public final boolean isFixedRotated() {
        return this.isMonitorStarted && this.isFixedRotated;
    }

    public final void setPendingRunnable(KeyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1) {
        ViewTreeObserver viewTreeObserver;
        String str = "set " + keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1;
        if (DEBUG) {
            android.util.Log.d("KeyguardFixedRotation", str);
        }
        this.pendingRunnable = keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1;
        ViewGroup viewGroup = (ViewGroup) this.notificationShadeView$delegate.getValue();
        if (viewGroup != null && (viewTreeObserver = viewGroup.getViewTreeObserver()) != null) {
            KeyguardFixedRotationMonitor$preDrawListener$1 keyguardFixedRotationMonitor$preDrawListener$1 = this.preDrawListener;
            viewTreeObserver.removeOnPreDrawListener(keyguardFixedRotationMonitor$preDrawListener$1);
            if (keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 != null) {
                viewTreeObserver.addOnPreDrawListener(keyguardFixedRotationMonitor$preDrawListener$1);
            }
        }
        KeyguardFixedRotationMonitor$safeRunnable$1 keyguardFixedRotationMonitor$safeRunnable$1 = this.safeRunnable;
        Handler handler = this.handler;
        handler.removeCallbacks(keyguardFixedRotationMonitor$safeRunnable$1);
        if (keyguardViewMediatorHelperImpl$showForegroundImmediatelyIfNeeded$1$1 != null) {
            handler.postDelayed(this.safeRunnable, 500L);
        }
    }
}
