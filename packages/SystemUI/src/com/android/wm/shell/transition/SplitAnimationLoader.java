package com.android.wm.shell.transition;

import android.app.WindowConfiguration;
import android.content.Context;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplitAnimationLoader extends AnimationLoader {
    public SplitAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final float getCornerRadius(Context context) {
        if (context == null) {
            return 0.0f;
        }
        return (int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, 12);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        return multiTaskingTransitionState.mWindowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(multiTaskingTransitionState.mConfiguration.windowConfiguration);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    @Override // com.android.wm.shell.transition.AnimationLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadAnimationIfPossible() {
        /*
            r6 = this;
            com.android.wm.shell.transition.MultiTaskingTransitionState r0 = r6.mState
            boolean r1 = r0.mHasCustomDisplayChangeTransition
            if (r1 != 0) goto L85
            boolean r1 = r0.mSeparatedFromCustomDisplayChange
            if (r1 == 0) goto Lc
            goto L85
        Lc:
            boolean r1 = r0.mIsPopOverAnimationNeeded
            if (r1 == 0) goto L12
            goto L84
        L12:
            android.app.ActivityManager$RunningTaskInfo r1 = r0.mOpeningAppsEdgeTaskInfo
            r2 = -1
            r3 = 2130772657(0x7f0102b1, float:1.7148439E38)
            r4 = 2130772658(0x7f0102b2, float:1.714844E38)
            if (r1 != 0) goto L1e
            goto L3e
        L1e:
            android.content.res.Configuration r1 = r0.mConfiguration
            android.app.WindowConfiguration r1 = r1.windowConfiguration
            int r1 = r1.getStageType()
            android.app.ActivityManager$RunningTaskInfo r5 = r0.mOpeningAppsEdgeTaskInfo
            android.content.res.Configuration r5 = r5.getConfiguration()
            android.app.WindowConfiguration r5 = r5.windowConfiguration
            int r5 = r5.getStageType()
            if (r1 == 0) goto L3e
            if (r1 != r5) goto L3e
            boolean r1 = r0.mIsEnter
            if (r1 != 0) goto L3e
            r1 = 2130772662(0x7f0102b6, float:1.7148449E38)
            goto L5f
        L3e:
            boolean r1 = r0.isOpeningTransitionType()
            if (r1 == 0) goto L4e
            boolean r1 = r0.mIsEnter
            if (r1 == 0) goto L4a
            r1 = r4
            goto L5f
        L4a:
            r1 = 2130772659(0x7f0102b3, float:1.7148443E38)
            goto L5f
        L4e:
            boolean r1 = r0.isClosingTransitionType()
            if (r1 == 0) goto L5e
            boolean r1 = r0.mIsEnter
            if (r1 == 0) goto L5c
            r1 = 2130772656(0x7f0102b0, float:1.7148437E38)
            goto L5f
        L5c:
            r1 = r3
            goto L5f
        L5e:
            r1 = r2
        L5f:
            if (r1 == r2) goto L84
            android.view.animation.Animation r2 = r0.loadAnimationFromResources(r1)
            if (r1 == r4) goto L69
            if (r1 != r3) goto L77
        L69:
            boolean r1 = r2 instanceof android.view.animation.AnimationSet
            if (r1 == 0) goto L77
            android.graphics.Rect r1 = r0.getBounds()
            r3 = r2
            android.view.animation.AnimationSet r3 = (android.view.animation.AnimationSet) r3
            r6.addRoundedClipAnimation(r1, r3)
        L77:
            if (r2 != 0) goto L81
            java.lang.String r6 = "SplitAnimationLoader"
            java.lang.String r0 = "loadAnimationIfPossible: animation is null"
            android.util.Slog.w(r6, r0)
            return
        L81:
            r0.setAnimation(r2)
        L84:
            return
        L85:
            android.view.animation.Animation r6 = com.android.wm.shell.transition.AnimationLoader.NO_ANIMATION
            r0.setAnimation(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.SplitAnimationLoader.loadAnimationIfPossible():void");
    }

    public final String toString() {
        return "SplitAnimationLoader";
    }
}
