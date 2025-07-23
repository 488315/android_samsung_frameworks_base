package com.google.android.setupdesign.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PartnerStyleHelper {
    private PartnerStyleHelper() {
    }

    public static int getLayoutGravity(Context context) {
        String string = PartnerConfigHelper.get(context).getString(context, PartnerConfig.CONFIG_LAYOUT_GRAVITY);
        if (string == null) {
            return 0;
        }
        String lowerCase = string.toLowerCase(Locale.ROOT);
        lowerCase.getClass();
        if (lowerCase.equals("center")) {
            return 17;
        }
        return !lowerCase.equals(NetworkAnalyticsConstants.DataPoints.OPEN_TIME) ? 0 : 8388611;
    }

    public static boolean shouldApplyPartnerHeavyThemeResource(View view) {
        View findViewById;
        if (view == null) {
            return false;
        }
        if (view instanceof GlifLayout) {
            return ((GlifLayout) view).shouldApplyPartnerHeavyThemeResource();
        }
        Context context = view.getContext();
        try {
            Logger logger = PartnerCustomizationLayout.LOG;
            Activity lookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(context);
            TemplateLayout templateLayout = null;
            if (lookupActivityFromContext != null && (findViewById = lookupActivityFromContext.findViewById(R.id.suc_layout_status)) != null) {
                templateLayout = (TemplateLayout) findViewById.getParent();
            }
            if (templateLayout instanceof GlifLayout) {
                return ((GlifLayout) templateLayout).shouldApplyPartnerHeavyThemeResource();
            }
        } catch (ClassCastException | IllegalArgumentException unused) {
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.sudUsePartnerHeavyTheme});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return shouldApplyPartnerResource(context) && (z || PartnerConfigHelper.shouldApplyExtendedPartnerConfig(context));
    }

    public static boolean shouldApplyPartnerResource(View view) {
        if (view == null) {
            return false;
        }
        return view instanceof PartnerCustomizationLayout ? ((PartnerCustomizationLayout) view).shouldApplyPartnerResource() : shouldApplyPartnerResource(view.getContext());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003c  */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shouldApplyPartnerResource(android.content.Context r4) {
        /*
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r0 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            boolean r0 = r0.isAvailable()
            r1 = 0
            if (r0 != 0) goto Lc
            return r1
        Lc:
            r0 = 0
            com.google.android.setupcompat.util.Logger r2 = com.google.android.setupcompat.PartnerCustomizationLayout.LOG     // Catch: java.lang.Throwable -> L30
            android.app.Activity r2 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.lookupActivityFromContext(r4)     // Catch: java.lang.Throwable -> L30
            if (r2 == 0) goto L31
            r3 = 2131365184(0x7f0a0d40, float:1.8350226E38)
            android.view.View r3 = r2.findViewById(r3)     // Catch: java.lang.Throwable -> L2f
            if (r3 == 0) goto L24
            android.view.ViewParent r0 = r3.getParent()     // Catch: java.lang.Throwable -> L2f
            com.google.android.setupcompat.internal.TemplateLayout r0 = (com.google.android.setupcompat.internal.TemplateLayout) r0     // Catch: java.lang.Throwable -> L2f
        L24:
            boolean r3 = r0 instanceof com.google.android.setupcompat.PartnerCustomizationLayout     // Catch: java.lang.Throwable -> L2f
            if (r3 == 0) goto L31
            com.google.android.setupcompat.PartnerCustomizationLayout r0 = (com.google.android.setupcompat.PartnerCustomizationLayout) r0     // Catch: java.lang.Throwable -> L2f
            boolean r4 = r0.shouldApplyPartnerResource()     // Catch: java.lang.Throwable -> L2f
            return r4
        L2f:
            r0 = r2
        L30:
            r2 = r0
        L31:
            if (r2 == 0) goto L3c
            android.content.Intent r0 = r2.getIntent()
            boolean r0 = com.google.android.setupcompat.util.WizardManagerHelper.isAnySetupWizard(r0)
            goto L3d
        L3c:
            r0 = r1
        L3d:
            r2 = 2130970292(0x7f0406b4, float:1.754929E38)
            int[] r2 = new int[]{r2}
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r2)
            r2 = 1
            boolean r3 = r4.getBoolean(r1, r2)
            r4.recycle()
            if (r0 != 0) goto L54
            if (r3 == 0) goto L55
        L54:
            r1 = r2
        L55:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.util.PartnerStyleHelper.shouldApplyPartnerResource(android.content.Context):boolean");
    }
}
