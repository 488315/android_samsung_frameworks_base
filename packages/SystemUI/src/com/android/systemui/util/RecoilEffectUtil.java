package com.android.systemui.util;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import com.android.systemui.R;

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
