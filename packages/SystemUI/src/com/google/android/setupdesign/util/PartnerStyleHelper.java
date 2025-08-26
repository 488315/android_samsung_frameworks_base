package com.google.android.setupdesign.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.View;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class PartnerStyleHelper {
    private PartnerStyleHelper() {
    }

    public static int getLayoutGravity(Context context) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
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
        View viewFindViewById;
        if (view == null) {
            return false;
        }
        if (view instanceof GlifLayout) {
            return ((GlifLayout) view).shouldApplyPartnerHeavyThemeResource();
        }
        Context context = view.getContext();
        try {
            Logger logger = PartnerCustomizationLayout.LOG;
            Activity activityLookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(context);
            TemplateLayout templateLayout = null;
            if (activityLookupActivityFromContext != null && (viewFindViewById = activityLookupActivityFromContext.findViewById(R.id.suc_layout_status)) != null) {
                templateLayout = (TemplateLayout) viewFindViewById.getParent();
            }
            if (templateLayout instanceof GlifLayout) {
                return ((GlifLayout) templateLayout).shouldApplyPartnerHeavyThemeResource();
            }
        } catch (ClassCastException | IllegalArgumentException unused) {
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.sudUsePartnerHeavyTheme});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return shouldApplyPartnerResource(context) && (z || PartnerConfigHelper.shouldApplyExtendedPartnerConfig(context));
    }

    public static boolean shouldApplyPartnerResource(View view) {
        if (view == null) {
            return false;
        }
        return view instanceof PartnerCustomizationLayout ? ((PartnerCustomizationLayout) view).shouldApplyPartnerResource() : shouldApplyPartnerResource(view.getContext());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean shouldApplyPartnerResource(Context context) {
        ?? r2;
        if (!PartnerConfigHelper.get(context).isAvailable()) {
            return false;
        }
        try {
            Logger logger = PartnerCustomizationLayout.LOG;
            ?? LookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(context);
            r2 = LookupActivityFromContext;
            if (LookupActivityFromContext != 0) {
                try {
                    View viewFindViewById = LookupActivityFromContext.findViewById(R.id.suc_layout_status);
                    templateLayout = viewFindViewById != null ? (TemplateLayout) viewFindViewById.getParent() : null;
                    r2 = LookupActivityFromContext;
                    if (templateLayout instanceof PartnerCustomizationLayout) {
                        return ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
                    }
                } catch (ClassCastException | IllegalArgumentException unused) {
                    templateLayout = LookupActivityFromContext;
                    r2 = templateLayout;
                    if (r2 == 0) {
                    }
                    TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.sucUsePartnerResource});
                    boolean z = typedArrayObtainStyledAttributes.getBoolean(0, true);
                    typedArrayObtainStyledAttributes.recycle();
                    if (zIsAnySetupWizard) {
                    }
                }
            }
        } catch (ClassCastException | IllegalArgumentException unused2) {
        }
        boolean zIsAnySetupWizard = r2 == 0 ? WizardManagerHelper.isAnySetupWizard(r2.getIntent()) : false;
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(new int[]{R.attr.sucUsePartnerResource});
        boolean z2 = typedArrayObtainStyledAttributes2.getBoolean(0, true);
        typedArrayObtainStyledAttributes2.recycle();
        return !zIsAnySetupWizard || z2;
    }
}
