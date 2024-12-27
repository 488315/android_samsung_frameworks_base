package com.android.systemui.util;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class RecoilEffectUtil {
    public static StateListAnimator getRecoilLargeAnimator(Context context) {
        return AnimatorInflater.loadStateListAnimator(context, R.animator.sesl_recoil_list_selector);
    }

    public static StateListAnimator getRecoilSmallAnimator(Context context) {
        return AnimatorInflater.loadStateListAnimator(context, R.animator.sesl_recoil_button_selector);
    }

    public static StateListAnimator getSecRecoilSmallAnimator(Context context) {
        return AnimatorInflater.loadStateListAnimator(context, R.animator.sec_small_recoil_button_selector);
    }
}
