package com.android.systemui.plugins;

import android.animation.Animator;
import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = ToastPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface ToastPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_TOAST";
    public static final int VERSION = 1;

    Toast createToast(CharSequence charSequence, String str, int i);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
