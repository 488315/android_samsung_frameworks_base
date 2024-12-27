package com.android.systemui.plugins;

import android.animation.Animator;
import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = ToastPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface ToastPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_TOAST";
    public static final int VERSION = 1;

    Toast createToast(CharSequence charSequence, String str, int i);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
