package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.VelocityTracker;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.util.ScopeTimer;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

public final class MotionPauseDetector {
    public final boolean directionY;
    public boolean hasEverBeenPaused;
    public boolean isPaused;
    public final boolean makePauseHarderToTrigger;
    public final MotionPauseListener motionPauseListener;
    public Float previousVelocity;
    public long slowStartTime;
    public final float speedFast;
    public final float speedSlow;
    public final float speedSomewhatFast;
    public final float speedVerySlow;
    public final String tag;
    public final ScopeTimer timer;
    public final SystemVelocityProvider velocityProvider;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class SystemVelocityProvider {
        public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    }

    static {
        new Companion(null);
    }

    public MotionPauseDetector(Context context, boolean z, MotionPauseListener motionPauseListener, boolean z2) {
        this.makePauseHarderToTrigger = z;
        this.motionPauseListener = motionPauseListener;
        this.directionY = z2;
        this.tag = "MotionPauseDetector";
        Resources resources = context.getResources();
        this.speedVerySlow = resources.getDimension(R.dimen.motion_pause_detector_speed_very_slow);
        this.speedSlow = resources.getDimension(R.dimen.motion_pause_detector_speed_slow);
        this.speedSomewhatFast = resources.getDimension(R.dimen.motion_pause_detector_speed_somewhat_fast);
        this.speedFast = resources.getDimension(R.dimen.motion_pause_detector_speed_fast);
        this.velocityProvider = new SystemVelocityProvider();
        this.timer = new ScopeTimer(CoroutineScopeKt.CoroutineScope(Dispatchers.Default));
    }

    public final void updatePaused(String str, boolean z) {
        if (this.isPaused != z) {
            this.isPaused = z;
            Log.d(this.tag, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("updatePaused, ", "onMotionPauseChanged, paused=" + z + ", reason=" + str));
            boolean z2 = !this.hasEverBeenPaused && this.isPaused;
            if (this.isPaused) {
                this.hasEverBeenPaused = true;
            }
            MotionPauseListener motionPauseListener = this.motionPauseListener;
            if (z2) {
                AccessibilityGestureHandler accessibilityGestureHandler = (AccessibilityGestureHandler) motionPauseListener;
                if (accessibilityGestureHandler.gestureDetected) {
                    accessibilityGestureHandler.isPaused = true;
                    Log.d(accessibilityGestureHandler.tag, "onMotionPauseDetected, AccessibilityButtonLongClick");
                    NavBarHelper navBarHelper = accessibilityGestureHandler.navBarHelper;
                    navBarHelper.getClass();
                    Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                    intent.addFlags(268468224);
                    intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                    navBarHelper.mContext.startActivityAsUser(intent, ((UserTrackerImpl) navBarHelper.mUserTracker).getUserHandle());
                }
            }
            motionPauseListener.getClass();
        }
    }

    public /* synthetic */ MotionPauseDetector(Context context, boolean z, MotionPauseListener motionPauseListener, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, z, motionPauseListener, (i & 8) != 0 ? true : z2);
    }
}
