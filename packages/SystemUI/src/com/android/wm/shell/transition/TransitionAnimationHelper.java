package com.android.wm.shell.transition;

import android.graphics.Insets;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.window.TransitionInfo;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.shared.TransitionUtil;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TransitionAnimationHelper {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RoundedContentPerDisplay implements DisplayInsetsController.OnInsetsChangedListener {
        public final Rect mBounds = new Rect();

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            Insets insets = Insets.NONE;
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
                if (sourceAt.hasFlags(2)) {
                    insets = Insets.max(sourceAt.calculateInsets(insetsState.getDisplayFrame(), false), insets);
                }
            }
            this.mBounds.set(insetsState.getDisplayFrame());
            this.mBounds.inset(insets);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RoundedContentTracker implements DisplayController.OnDisplaysChangedListener {
        public final DisplayController mDisplayController;
        public final DisplayInsetsController mDisplayInsetsController;
        public final SparseArray mPerDisplay = new SparseArray();

        public RoundedContentTracker(DisplayController displayController, DisplayInsetsController displayInsetsController) {
            this.mDisplayController = displayController;
            this.mDisplayInsetsController = displayInsetsController;
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayAdded(int i) {
            RoundedContentPerDisplay roundedContentPerDisplay = new RoundedContentPerDisplay();
            this.mDisplayInsetsController.addInsetsChangedListener(i, roundedContentPerDisplay);
            this.mPerDisplay.put(i, roundedContentPerDisplay);
            DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
            roundedContentPerDisplay.mBounds.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayRemoved(int i) {
            RoundedContentPerDisplay roundedContentPerDisplay = (RoundedContentPerDisplay) this.mPerDisplay.removeReturnOld(i);
            if (roundedContentPerDisplay != null) {
                this.mDisplayInsetsController.removeInsetsChangedListener(i, roundedContentPerDisplay);
            }
        }
    }

    public static int getTransitionTypeFromInfo(TransitionInfo transitionInfo) {
        int type = transitionInfo.getType();
        if ((type == 13 || type == 14) && !transitionInfo.getChanges().isEmpty()) {
            return TransitionUtil.isOpeningMode(((TransitionInfo.Change) transitionInfo.getChanges().get(0)).getMode()) ? 1 : 2;
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if (change.getAnimationOptions() != null && change.getAnimationOptions().getType() == 1) {
                    break;
                }
            }
        }
        if (type == 1) {
            for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
                if ((change2.getTaskInfo() == null && !change2.hasFlags(32)) || TransitionUtil.isOrderOnly(change2)) {
                    if (change2.getTaskInfo() == null || !change2.hasFlags(65826)) {
                        if (change2.getMode() == 1) {
                            return type;
                        }
                    }
                }
            }
            return 2;
        }
        return type;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0031, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isCoveredByOpaqueFullscreenChange(android.window.TransitionInfo.Change r2, android.window.TransitionInfo r3) {
        /*
            java.util.List r3 = r3.getChanges()
            java.util.Iterator r3 = r3.iterator()
        L8:
            boolean r0 = r3.hasNext()
            r1 = 0
            if (r0 == 0) goto L31
            java.lang.Object r0 = r3.next()
            android.window.TransitionInfo$Change r0 = (android.window.TransitionInfo.Change) r0
            if (r0 != r2) goto L18
            return r1
        L18:
            int r1 = r0.getFlags()
            r1 = r1 & 4
            if (r1 != 0) goto L8
            android.app.ActivityManager$RunningTaskInfo r1 = r0.getTaskInfo()
            if (r1 == 0) goto L8
            android.app.ActivityManager$RunningTaskInfo r0 = r0.getTaskInfo()
            int r0 = r0.getWindowingMode()
            r1 = 1
            if (r0 != r1) goto L8
        L31:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.TransitionAnimationHelper.isCoveredByOpaqueFullscreenChange(android.window.TransitionInfo$Change, android.window.TransitionInfo):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0168  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.animation.Animation loadAttributeAnimation(int r17, android.window.TransitionInfo r18, android.window.TransitionInfo.Change r19, int r20, com.android.internal.policy.TransitionAnimation r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 495
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.TransitionAnimationHelper.loadAttributeAnimation(int, android.window.TransitionInfo, android.window.TransitionInfo$Change, int, com.android.internal.policy.TransitionAnimation, boolean):android.view.animation.Animation");
    }
}
