package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FooterButtonStyleUtils {
    public static final HashMap defaultTextColor = new HashMap();

    private FooterButtonStyleUtils() {
    }

    public static void updateButtonTextDisabledColorWithPartnerConfig(Context context, FooterActionButton footerActionButton, PartnerConfig partnerConfig) {
        if (PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)) {
            int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig);
            if (color != 0) {
                footerActionButton.setTextColor(ColorStateList.valueOf(color));
                return;
            }
            return;
        }
        HashMap hashMap = defaultTextColor;
        if (!hashMap.containsKey(Integer.valueOf(footerActionButton.getId()))) {
            throw new IllegalStateException("There is no saved default color for button");
        }
        footerActionButton.setTextColor((ColorStateList) hashMap.get(Integer.valueOf(footerActionButton.getId())));
    }
}
