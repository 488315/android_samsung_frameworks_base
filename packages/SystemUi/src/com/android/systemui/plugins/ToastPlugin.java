package com.android.systemui.plugins;

import android.animation.Animator;
import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = ToastPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface ToastPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_TOAST";
    public static final int VERSION = 1;

    Toast createToast(CharSequence charSequence, String str, int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Toast {
        default Integer getGravity() {
            return null;
        }

        default Integer getHorizontalMargin() {
            return null;
        }

        default Animator getInAnimation() {
            return null;
        }

        default Animator getOutAnimation() {
            return null;
        }

        default Integer getVerticalMargin() {
            return null;
        }

        default View getView() {
            return null;
        }

        default Integer getXOffset() {
            return null;
        }

        default Integer getYOffset() {
            return null;
        }

        default void onOrientationChange(int i) {
        }
    }
}
