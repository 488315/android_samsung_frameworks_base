package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.Button;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FooterButtonStyleUtils {
    public static final HashMap defaultTextColor = new HashMap();

    private FooterButtonStyleUtils() {
    }

    public static GradientDrawable getGradientDrawable(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof InsetDrawable) {
            return (GradientDrawable) ((LayerDrawable) ((InsetDrawable) background).getDrawable()).getDrawable(0);
        }
        if (!(background instanceof RippleDrawable)) {
            return null;
        }
        RippleDrawable rippleDrawable = (RippleDrawable) background;
        return rippleDrawable.getDrawable(0) instanceof GradientDrawable ? (GradientDrawable) rippleDrawable.getDrawable(0) : (GradientDrawable) ((InsetDrawable) rippleDrawable.getDrawable(0)).getDrawable();
    }

    public static void updateButtonTextDisabledColorWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        if (PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)) {
            int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig);
            if (color != 0) {
                button.setTextColor(ColorStateList.valueOf(color));
                return;
            }
            return;
        }
        HashMap hashMap = defaultTextColor;
        if (!hashMap.containsKey(Integer.valueOf(button.getId()))) {
            throw new IllegalStateException("There is no saved default color for button");
        }
        button.setTextColor((ColorStateList) hashMap.get(Integer.valueOf(button.getId())));
    }
}
