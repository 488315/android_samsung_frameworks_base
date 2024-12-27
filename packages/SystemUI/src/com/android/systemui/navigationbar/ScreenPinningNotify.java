package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.SystemClock;
import android.util.Slog;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.keyguard.DisplayLifecycle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenPinningNotify {
    public final Context mContext;
    public long mLastShowToastTime;
    public Toast mLastToast;
    public boolean mTouchExplorationEnabled;

    public ScreenPinningNotify(Context context) {
        this.mContext = context;
    }

    public final void showEscapeToast(boolean z, boolean z2) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastShowToastTime < 1000) {
            Slog.i("ScreenPinningNotify", "Ignore toast since it is requested in very short interval.");
            return;
        }
        Toast toast = this.mLastToast;
        if (toast != null) {
            toast.cancel();
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        boolean z3 = false;
        this.mTouchExplorationEnabled = accessibilityManager != null && accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
        if (BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            z3 = true;
        }
        Toast makeText = SysUIToast.makeText(this.mContext, z ? z3 ? R.string.sec_screen_pinning_toast_gesture_nav_sub_screen : R.string.sec_screen_pinning_toast_gesture_nav : z2 ? this.mTouchExplorationEnabled ? R.string.sec_screen_pinning_toast_accessibility : z3 ? R.string.sec_screen_pinning_toast_sub_screen : R.string.sec_screen_pinning_toast : R.string.screen_pinning_toast_recents_invisible, 1);
        makeText.show();
        this.mLastToast = makeText;
        this.mLastShowToastTime = elapsedRealtime;
    }
}
