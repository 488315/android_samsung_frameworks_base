package com.google.android.setupcompat.partnerconfig;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import androidx.window.embedding.ActivityEmbeddingController;
import androidx.window.embedding.EmbeddingCompat;
import androidx.window.embedding.EmbeddingInterfaceCompat;
import androidx.window.embedding.ExtensionEmbeddingBackend;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import java.util.EnumMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PartnerConfigHelper {
    public static final String EMBEDDED_ACTIVITY_RESOURCE_SUFFIX = "_embedded_activity";
    static final String FORCE_TWO_PANE_SUFFIX = "_two_pane";
    public static final String GET_SUW_DEFAULT_THEME_STRING_METHOD = "suwDefaultThemeString";
    public static final String GLIF_EXPRESSIVE_RESOURCE_SUFFIX = "_expressive";
    public static final String IS_DYNAMIC_COLOR_ENABLED_METHOD = "isDynamicColorEnabled";
    public static final String IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD = "isEmbeddedActivityOnePaneEnabled";
    public static final String IS_ENHANCED_SETUP_DESIGN_METRICS_ENABLED = "isEnhancedSetupDesignMetricsEnabled";
    public static final String IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD = "isExtendedPartnerConfigEnabled";
    public static final String IS_FONT_WEIGHT_ENABLED_METHOD = "isFontWeightEnabled";
    public static final String IS_FORCE_TWO_PANE_ENABLED_METHOD = "isForceTwoPaneEnabled";
    public static final String IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD = "isFullDynamicColorEnabled";
    public static final String IS_GLIF_EXPRESSIVE_ENABLED = "isGlifExpressiveEnabled";
    public static final String IS_KEYBOARD_FOCUS_ENHANCEMENT_ENABLED_METHOD = "isKeyboardFocusEnhancementEnabled";
    public static final String IS_MATERIAL_YOU_STYLE_ENABLED_METHOD = "IsMaterialYouStyleEnabled";
    public static final String IS_NEUTRAL_BUTTON_STYLE_ENABLED_METHOD = "isNeutralButtonStyleEnabled";
    public static final String IS_SUW_DAY_NIGHT_ENABLED_METHOD = "isSuwDayNightEnabled";
    public static final String KEY_FALLBACK_CONFIG = "fallbackConfig";
    public static final String MATERIAL_YOU_RESOURCE_SUFFIX = "_material_you";
    public static final String SUW_GET_PARTNER_CONFIG_METHOD = "getOverlayConfig";
    public static final String SUW_PACKAGE_NAME = "com.google.android.setupwizard";
    public static Bundle applyDynamicColorBundle = null;
    public static Bundle applyEmbeddedActivityOnePaneBundle = null;
    public static Bundle applyExtendedPartnerConfigBundle = null;
    public static Bundle applyFontWeightBundle = null;
    public static Bundle applyForceTwoPaneBundle = null;
    public static Bundle applyFullDynamicColorBundle = null;
    public static Bundle applyGlifExpressiveBundle = null;
    public static Bundle applyMaterialYouConfigBundle = null;
    public static Bundle applyNeutralButtonStyleBundle = null;
    static Bundle applyTransitionBundle = null;
    public static AnonymousClass1 contentObserver = null;
    public static Bundle enableMetricsLoggingBundle = null;
    public static PartnerConfigHelper instance = null;
    public static Bundle keyboardFocusEnhancementBundle = null;
    public static String mAuthority = null;
    public static boolean savedConfigEmbeddedActivityMode = false;
    public static int savedConfigUiMode = 0;
    public static int savedOrientation = 1;
    public static int savedScreenHeight;
    public static int savedScreenWidth;
    public static Bundle suwDayNightEnabledBundle;
    public static Bundle suwDefaultThemeBundle;
    final EnumMap<PartnerConfig, Object> partnerResourceCache;
    Bundle resultBundle;

    /* JADX WARN: Type inference failed for: r1v5, types: [com.google.android.setupcompat.partnerconfig.PartnerConfigHelper$1] */
    private PartnerConfigHelper(Context context) {
        Handler handler = null;
        this.resultBundle = null;
        EnumMap<PartnerConfig, Object> enumMap = new EnumMap<>((Class<PartnerConfig>) PartnerConfig.class);
        this.partnerResourceCache = enumMap;
        Bundle bundle = this.resultBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                this.resultBundle = context.getContentResolver().call(getContentUri(context), SUW_GET_PARTNER_CONFIG_METHOD, (String) null, (Bundle) null);
                enumMap.clear();
                StringBuilder sb = new StringBuilder("PartnerConfigsBundle=");
                Bundle bundle2 = this.resultBundle;
                sb.append(bundle2 != null ? Integer.valueOf(bundle2.size()) : "(null)");
                Log.i("PartnerConfigHelper", sb.toString());
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "Fail to get config from suw provider");
            }
        }
        if (isSetupWizardDayNightEnabled(context)) {
            if (contentObserver != null) {
                try {
                    context.getContentResolver().unregisterContentObserver(contentObserver);
                    contentObserver = null;
                } catch (IllegalArgumentException | NullPointerException | SecurityException e) {
                    Log.w("PartnerConfigHelper", "Failed to unregister content observer: " + e);
                }
            }
            Uri contentUri = getContentUri(context);
            try {
                contentObserver = new ContentObserver(handler) { // from class: com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        super.onChange(z);
                        PartnerConfigHelper.resetInstance();
                    }
                };
                context.getContentResolver().registerContentObserver(contentUri, true, contentObserver);
            } catch (IllegalArgumentException | NullPointerException | SecurityException e2) {
                Log.w("PartnerConfigHelper", "Failed to register content observer for " + contentUri + ": " + e2);
            }
        }
    }

    public static synchronized PartnerConfigHelper get(Context context) {
        PartnerConfigHelper partnerConfigHelper;
        synchronized (PartnerConfigHelper.class) {
            try {
                if (!isValidInstance(context)) {
                    instance = new PartnerConfigHelper(context);
                }
                partnerConfigHelper = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return partnerConfigHelper;
    }

    public static Uri getContentUri(Context context) {
        String str;
        if (mAuthority == null) {
            try {
                context.getPackageManager().getApplicationInfo(SUW_PACKAGE_NAME, 128);
                str = "com.google.android.setupwizard.partner";
            } catch (PackageManager.NameNotFoundException unused) {
                str = "com.sec.android.app.SecSetupWizard.partner";
            }
            mAuthority = str;
            Log.w("PartnerConfigHelper", "getContentUri() mAuthority=" + mAuthority);
        }
        return new Uri.Builder().scheme("content").authority(mAuthority).build();
    }

    public static TypedValue getTypedValueFromResource(Resources resources, int i) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(i, typedValue, true);
        if (typedValue.type == 5) {
            return typedValue;
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValue.type) + " is not valid");
    }

    public static boolean isEmbeddedActivityOnePaneEnabled(Context context) {
        if (applyEmbeddedActivityOnePaneBundle == null) {
            try {
                applyEmbeddedActivityOnePaneBundle = context.getContentResolver().call(getContentUri(context), IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard one-pane support in embedded activity status unknown; return as false.");
                applyEmbeddedActivityOnePaneBundle = null;
                return false;
            }
        }
        Bundle bundle = applyEmbeddedActivityOnePaneBundle;
        return bundle != null && bundle.getBoolean(IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD, false);
    }

    public static boolean isEnhancedSetupDesignMetricsEnabled(Context context) {
        Bundle bundle = enableMetricsLoggingBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                enableMetricsLoggingBundle = context.getContentResolver().call(getContentUri(context), IS_ENHANCED_SETUP_DESIGN_METRICS_ENABLED, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "Method isEnhancedSetupDesignMetricsEnabled is unknown");
                enableMetricsLoggingBundle = null;
                return false;
            }
        }
        Bundle bundle2 = enableMetricsLoggingBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return enableMetricsLoggingBundle.getBoolean(IS_ENHANCED_SETUP_DESIGN_METRICS_ENABLED, false);
    }

    public static boolean isFontWeightEnabled(Context context) {
        if (applyFontWeightBundle == null) {
            try {
                applyFontWeightBundle = context.getContentResolver().call(getContentUri(context), IS_FONT_WEIGHT_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "Font weight supporting status unknown; return as false.");
                applyFontWeightBundle = null;
                return false;
            }
        }
        Bundle bundle = applyFontWeightBundle;
        return bundle != null && bundle.getBoolean(IS_FONT_WEIGHT_ENABLED_METHOD, true);
    }

    public static boolean isForceTwoPaneEnabled(Context context) {
        Bundle bundle = applyForceTwoPaneBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                applyForceTwoPaneBundle = context.getContentResolver().call(getContentUri(context), IS_FORCE_TWO_PANE_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "isForceTwoPaneEnabled status is unknown; return as false.");
            }
        }
        Bundle bundle2 = applyForceTwoPaneBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return applyForceTwoPaneBundle.getBoolean(IS_FORCE_TWO_PANE_ENABLED_METHOD, false);
    }

    public static boolean isGlifExpressiveEnabled(Context context) {
        Bundle bundle = applyGlifExpressiveBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                applyGlifExpressiveBundle = context.getContentResolver().call(getContentUri(context), IS_GLIF_EXPRESSIVE_ENABLED, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "isGlifExpressiveEnabled status is unknown; return as false.");
            }
        }
        Bundle bundle2 = applyGlifExpressiveBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return applyGlifExpressiveBundle.getBoolean(IS_GLIF_EXPRESSIVE_ENABLED, false);
    }

    public static boolean isSetupWizardDayNightEnabled(Context context) {
        if (suwDayNightEnabledBundle == null) {
            try {
                suwDayNightEnabledBundle = context.getContentResolver().call(getContentUri(context), IS_SUW_DAY_NIGHT_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard DayNight supporting status unknown; return as false.");
                suwDayNightEnabledBundle = null;
                return false;
            }
        }
        if ("com.sec.android.app.SecSetupWizard.partner".equalsIgnoreCase(mAuthority)) {
            Bundle bundle = suwDayNightEnabledBundle;
            return bundle != null && bundle.containsKey(IS_SUW_DAY_NIGHT_ENABLED_METHOD);
        }
        Bundle bundle2 = suwDayNightEnabledBundle;
        return bundle2 != null && bundle2.getBoolean(IS_SUW_DAY_NIGHT_ENABLED_METHOD, false);
    }

    public static boolean isSetupWizardDynamicColorEnabled(Context context) {
        if (applyDynamicColorBundle == null) {
            try {
                applyDynamicColorBundle = context.getContentResolver().call(getContentUri(context), IS_DYNAMIC_COLOR_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard dynamic color supporting status unknown; return as false.");
                applyDynamicColorBundle = null;
                return false;
            }
        }
        Bundle bundle = applyDynamicColorBundle;
        return bundle != null && bundle.getBoolean(IS_DYNAMIC_COLOR_ENABLED_METHOD, false);
    }

    public static boolean isSetupWizardFullDynamicColorEnabled(Context context) {
        if (applyFullDynamicColorBundle == null) {
            try {
                applyFullDynamicColorBundle = context.getContentResolver().call(getContentUri(context), IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard full dynamic color supporting status unknown; return as false.");
                applyFullDynamicColorBundle = null;
                return false;
            }
        }
        Bundle bundle = applyFullDynamicColorBundle;
        return bundle != null && bundle.getBoolean(IS_FULL_DYNAMIC_COLOR_ENABLED_METHOD, false);
    }

    public static boolean isValidInstance(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        if (instance == null) {
            savedConfigEmbeddedActivityMode = isEmbeddedActivityOnePaneEnabled(context);
            savedConfigUiMode = configuration.uiMode & 48;
            savedOrientation = configuration.orientation;
            savedScreenWidth = configuration.screenWidthDp;
            savedScreenHeight = configuration.screenHeightDp;
            return false;
        }
        boolean z = isSetupWizardDayNightEnabled(context) && (configuration.uiMode & 48) != savedConfigUiMode;
        boolean isEmbeddedActivityOnePaneEnabled = isEmbeddedActivityOnePaneEnabled(context);
        if (!z && isEmbeddedActivityOnePaneEnabled == savedConfigEmbeddedActivityMode && configuration.orientation == savedOrientation && configuration.screenWidthDp == savedScreenWidth && configuration.screenHeightDp == savedScreenHeight) {
            return true;
        }
        savedConfigUiMode = configuration.uiMode & 48;
        savedOrientation = configuration.orientation;
        savedScreenHeight = configuration.screenHeightDp;
        savedScreenWidth = configuration.screenWidthDp;
        resetInstance();
        return false;
    }

    public static Activity lookupActivityFromContext(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return lookupActivityFromContext(((ContextWrapper) context).getBaseContext());
        }
        throw new IllegalArgumentException("Cannot find instance of Activity in parent tree");
    }

    public static synchronized void resetInstance() {
        synchronized (PartnerConfigHelper.class) {
            instance = null;
            suwDayNightEnabledBundle = null;
            applyExtendedPartnerConfigBundle = null;
            applyMaterialYouConfigBundle = null;
            applyDynamicColorBundle = null;
            applyFullDynamicColorBundle = null;
            applyNeutralButtonStyleBundle = null;
            applyEmbeddedActivityOnePaneBundle = null;
            suwDefaultThemeBundle = null;
            applyTransitionBundle = null;
            applyForceTwoPaneBundle = null;
            applyGlifExpressiveBundle = null;
            keyboardFocusEnhancementBundle = null;
            enableMetricsLoggingBundle = null;
        }
    }

    public static boolean shouldApplyExtendedPartnerConfig(Context context) {
        if (applyExtendedPartnerConfigBundle == null) {
            try {
                applyExtendedPartnerConfigBundle = context.getContentResolver().call(getContentUri(context), IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard extended partner configs supporting status unknown; return as false.");
                applyExtendedPartnerConfigBundle = null;
                return false;
            }
        }
        Bundle bundle = applyExtendedPartnerConfigBundle;
        return bundle != null && bundle.getBoolean(IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD, false);
    }

    public final boolean getBoolean(Context context, PartnerConfig partnerConfig, boolean z) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.BOOL) {
            throw new IllegalArgumentException("Not a bool resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Boolean) this.partnerResourceCache.get(partnerConfig)).booleanValue();
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            z = resourceEntryFromKey.resources.getBoolean(resourceEntryFromKey.resourceId);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Boolean.valueOf(z));
            return z;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return z;
        }
    }

    public final int getColor(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.COLOR) {
            throw new IllegalArgumentException("Not a color resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Integer) this.partnerResourceCache.get(partnerConfig)).intValue();
        }
        int i = 0;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.resources;
            int i2 = resourceEntryFromKey.resourceId;
            TypedValue typedValue = new TypedValue();
            resources.getValue(i2, typedValue, true);
            if (typedValue.type == 1 && typedValue.data == 0) {
                return 0;
            }
            i = resources.getColor(i2, null);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Integer.valueOf(i));
            return i;
        } catch (NullPointerException unused) {
            return i;
        }
    }

    public final float getDimension(Context context, PartnerConfig partnerConfig, float f) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.DIMENSION) {
            throw new IllegalArgumentException("Not a dimension resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((TypedValue) this.partnerResourceCache.get(partnerConfig)).getDimension(context.getResources().getDisplayMetrics());
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.resources;
            int i = resourceEntryFromKey.resourceId;
            f = resources.getDimension(i);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) getTypedValueFromResource(resources, i));
            return ((TypedValue) this.partnerResourceCache.get(partnerConfig)).getDimension(context.getResources().getDisplayMetrics());
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return f;
        }
    }

    public final Drawable getDrawable(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.DRAWABLE) {
            throw new IllegalArgumentException("Not a drawable resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return (Drawable) this.partnerResourceCache.get(partnerConfig);
        }
        Drawable drawable = null;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.resources;
            int i = resourceEntryFromKey.resourceId;
            TypedValue typedValue = new TypedValue();
            resources.getValue(i, typedValue, true);
            if (typedValue.type == 1 && typedValue.data == 0) {
                return null;
            }
            drawable = resources.getDrawable(i, null);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) drawable);
            return drawable;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return drawable;
        }
    }

    public final float getFraction(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.FRACTION) {
            throw new IllegalArgumentException("Not a fraction resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Float) this.partnerResourceCache.get(partnerConfig)).floatValue();
        }
        float f = 0.0f;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            f = resourceEntryFromKey.resources.getFraction(resourceEntryFromKey.resourceId, 1, 1);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Float.valueOf(f));
            return f;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return f;
        }
    }

    public final int getInteger(Context context, PartnerConfig partnerConfig, int i) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.INTEGER) {
            throw new IllegalArgumentException("Not a integer resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Integer) this.partnerResourceCache.get(partnerConfig)).intValue();
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            i = resourceEntryFromKey.resources.getInteger(resourceEntryFromKey.resourceId);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Integer.valueOf(i));
            return i;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0180  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.setupcompat.partnerconfig.ResourceEntry getResourceEntryFromKey(android.content.Context r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.getResourceEntryFromKey(android.content.Context, java.lang.String):com.google.android.setupcompat.partnerconfig.ResourceEntry");
    }

    public final String getString(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.STRING) {
            throw new IllegalArgumentException("Not a string resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return (String) this.partnerResourceCache.get(partnerConfig);
        }
        String str = null;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            str = resourceEntryFromKey.resources.getString(resourceEntryFromKey.resourceId);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) str);
            return str;
        } catch (NullPointerException unused) {
            return str;
        }
    }

    public boolean isActivityEmbedded(Context context) {
        try {
            Activity lookupActivityFromContext = lookupActivityFromContext(context);
            if (!isEmbeddedActivityOnePaneEnabled(context)) {
                return false;
            }
            EmbeddingInterfaceCompat embeddingInterfaceCompat = ((ExtensionEmbeddingBackend) ActivityEmbeddingController.getInstance(lookupActivityFromContext).backend).embeddingExtension;
            return embeddingInterfaceCompat != null ? ((EmbeddingCompat) embeddingInterfaceCompat).embeddingExtension.isActivityEmbedded(lookupActivityFromContext) : false;
        } catch (IllegalArgumentException unused) {
            Log.w("PartnerConfigHelper", "Not a Activity instance in parent tree");
            return false;
        }
    }

    public final boolean isAvailable() {
        Bundle bundle = this.resultBundle;
        return (bundle == null || bundle.isEmpty()) ? false : true;
    }

    public final boolean isPartnerConfigAvailable(PartnerConfig partnerConfig) {
        return isAvailable() && this.resultBundle.containsKey(partnerConfig.getResourceName());
    }
}
