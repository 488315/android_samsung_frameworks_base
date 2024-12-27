package com.android.systemui.audio.soundcraft.utils;

import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeClipBounds;
import androidx.transition.ChangeScroll;
import androidx.transition.ChangeTransform;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionManagerUtil {
    public static final TransitionManagerUtil INSTANCE = new TransitionManagerUtil();

    private TransitionManagerUtil() {
    }

    public static void requestLayoutTransition(ViewGroup viewGroup) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeClipBounds());
        transitionSet.addTransition(new ChangeScroll());
        transitionSet.setDuration(250L);
        transitionSet.setInterpolator(new DecelerateInterpolator());
        TransitionManager.beginDelayedTransition(transitionSet, viewGroup);
        viewGroup.requestLayout();
    }
}
