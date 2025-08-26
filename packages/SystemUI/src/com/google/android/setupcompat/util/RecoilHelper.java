package com.google.android.setupcompat.util;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import com.android.systemui.R;
import com.google.android.setupcompat.template.FooterButtonStyleUtils;

/* loaded from: classes4.dex */
public class RecoilHelper {
    public static void apply(Context context, Button button) {
        Log.d("RecoilHelper", "apply " + button);
        try {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.seslLargeTouchAnimator, typedValue, true);
            button.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, typedValue.resourceId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GradientDrawable gradientDrawable = FooterButtonStyleUtils.getGradientDrawable(button);
            SeslRecoilDrawable seslRecoilDrawable = (SeslRecoilDrawable) context.getDrawable(R.drawable.sesl_recoil_footer_button);
            if (gradientDrawable == null || seslRecoilDrawable == null) {
                return;
            }
            float cornerRadius = gradientDrawable.getCornerRadius();
            for (int i = 0; i < seslRecoilDrawable.getNumberOfLayers(); i++) {
                ((GradientDrawable) seslRecoilDrawable.getDrawable(i)).setCornerRadius(cornerRadius);
            }
            seslRecoilDrawable.setTintList(button.getBackgroundTintList());
            seslRecoilDrawable.getDrawable(0).setTintBlendMode(BlendMode.SRC);
            InsetDrawable insetDrawable = (InsetDrawable) button.getBackground();
            button.setBackground(new InsetDrawable((Drawable) seslRecoilDrawable, insetDrawable.getOpticalInsets().left, insetDrawable.getOpticalInsets().top, insetDrawable.getOpticalInsets().right, insetDrawable.getOpticalInsets().bottom));
            ColorStateList backgroundTintList = button.getBackgroundTintList();
            button.getBackground().mutate().setState(new int[0]);
            button.refreshDrawableState();
            button.setBackgroundTintList(backgroundTintList);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
