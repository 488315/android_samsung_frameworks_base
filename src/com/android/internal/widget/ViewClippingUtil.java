package com.android.internal.widget;

import android.util.ArraySet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/* loaded from: classes6.dex */
public class ViewClippingUtil {
    private static final int CLIP_CHILDREN_TAG = 16908939;
    private static final int CLIP_CLIPPING_SET = 16908938;
    private static final int CLIP_TO_PADDING = 16908941;

    public static void setClippingDeactivated(View view, boolean z, ClippingParameters clippingParameters) {
        if ((!z && !clippingParameters.isClippingEnablingAllowed(view)) || !(view.getParent() instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        while (true) {
            if (!z && !clippingParameters.isClippingEnablingAllowed(view)) {
                return;
            }
            ArraySet arraySet = (ArraySet) viewGroup.getTag(16908938);
            if (arraySet == null) {
                arraySet = new ArraySet();
                viewGroup.setTagInternal(16908938, arraySet);
            }
            Boolean bool = (Boolean) viewGroup.getTag(16908939);
            if (bool == null) {
                bool = Boolean.valueOf(viewGroup.getClipChildren());
                viewGroup.setTagInternal(16908939, bool);
            }
            Boolean bool2 = (Boolean) viewGroup.getTag(16908941);
            if (bool2 == null) {
                bool2 = Boolean.valueOf(viewGroup.getClipToPadding());
                viewGroup.setTagInternal(16908941, bool2);
            }
            if (!z) {
                arraySet.remove(view);
                if (arraySet.isEmpty()) {
                    viewGroup.setClipChildren(bool.booleanValue());
                    viewGroup.setClipToPadding(bool2.booleanValue());
                    viewGroup.setTagInternal(16908938, null);
                    clippingParameters.onClippingStateChanged(viewGroup, true);
                }
            } else {
                arraySet.add(view);
                viewGroup.setClipChildren(false);
                viewGroup.setClipToPadding(false);
                clippingParameters.onClippingStateChanged(viewGroup, false);
            }
            if (clippingParameters.shouldFinish(viewGroup)) {
                return;
            }
            ViewParent parent = viewGroup.getParent();
            if (!(parent instanceof ViewGroup)) {
                return;
            } else {
                viewGroup = (ViewGroup) parent;
            }
        }
    }

    public interface ClippingParameters {
        default void onClippingStateChanged(View view, boolean z) {
        }

        boolean shouldFinish(View view);

        default boolean isClippingEnablingAllowed(View view) {
            return !MessagingPropertyAnimator.isAnimatingTranslation(view);
        }
    }
}
