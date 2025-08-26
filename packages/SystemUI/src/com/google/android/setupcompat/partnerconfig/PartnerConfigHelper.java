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
import java.util.Objects;

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
    private PartnerConfigHelper(Context context) throws PackageManager.NameNotFoundException {
        Handler handler = null;
        this.resultBundle = null;
        EnumMap<PartnerConfig, Object> enumMap = new EnumMap<>(PartnerConfig.class);
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
        try {
            if (!isValidInstance(context)) {
                instance = new PartnerConfigHelper(context);
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public static Uri getContentUri(Context context) throws PackageManager.NameNotFoundException {
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

    public static TypedValue getTypedValueFromResource(Resources resources, int i) throws Resources.NotFoundException {
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
        boolean zIsEmbeddedActivityOnePaneEnabled = isEmbeddedActivityOnePaneEnabled(context);
        if (!z && zIsEmbeddedActivityOnePaneEnabled == savedConfigEmbeddedActivityMode && configuration.orientation == savedOrientation && configuration.screenWidthDp == savedScreenWidth && configuration.screenHeightDp == savedScreenHeight) {
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

    public final boolean getBoolean(Context context, PartnerConfig partnerConfig, boolean z) throws Resources.NotFoundException {
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

    public final int getColor(Context context, PartnerConfig partnerConfig) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.COLOR) {
            throw new IllegalArgumentException("Not a color resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Integer) this.partnerResourceCache.get(partnerConfig)).intValue();
        }
        int color = 0;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            Resources resources = resourceEntryFromKey.resources;
            int i = resourceEntryFromKey.resourceId;
            TypedValue typedValue = new TypedValue();
            resources.getValue(i, typedValue, true);
            if (typedValue.type == 1 && typedValue.data == 0) {
                return 0;
            }
            color = resources.getColor(i, null);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Integer.valueOf(color));
            return color;
        } catch (NullPointerException unused) {
            return color;
        }
    }

    public final float getDimension(Context context, PartnerConfig partnerConfig, float f) throws Resources.NotFoundException {
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

    public final Drawable getDrawable(Context context, PartnerConfig partnerConfig) throws Resources.NotFoundException {
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
        float fraction = 0.0f;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            fraction = resourceEntryFromKey.resources.getFraction(resourceEntryFromKey.resourceId, 1, 1);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Float.valueOf(fraction));
            return fraction;
        } catch (Resources.NotFoundException | NullPointerException unused) {
            return fraction;
        }
    }

    public final int getInteger(Context context, PartnerConfig partnerConfig, int i) throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:58:0x013d A[Catch: NameNotFoundException | NotFoundException -> 0x0174, TryCatch #1 {NameNotFoundException | NotFoundException -> 0x0174, blocks: (B:8:0x0027, B:10:0x002b, B:10:0x002b, B:12:0x0041, B:12:0x0041, B:14:0x005a, B:14:0x005a, B:16:0x0068, B:16:0x0068, B:21:0x0078, B:23:0x0080, B:25:0x0098, B:32:0x00bb, B:32:0x00bb, B:34:0x00bf, B:34:0x00bf, B:36:0x00d5, B:36:0x00d5, B:37:0x00ed, B:37:0x00ed, B:39:0x00fb, B:39:0x00fb, B:56:0x0135, B:58:0x013d, B:60:0x0155), top: B:72:0x0023 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ResourceEntry getResourceEntryFromKey(Context context, String str) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        Bundle bundle;
        String str2;
        ResourceEntry resourceEntry;
        ResourceEntry resourceEntry2;
        Bundle bundle2 = this.resultBundle.getBundle(str);
        Bundle bundle3 = this.resultBundle.getBundle(KEY_FALLBACK_CONFIG);
        if (bundle3 != null) {
            bundle2.putBundle(KEY_FALLBACK_CONFIG, bundle3.getBundle(str));
        }
        ResourceEntry resourceEntryFromBundle = ResourceEntry.fromBundle(context, bundle2);
        try {
            if (isActivityEmbedded(context)) {
                Resources resources = resourceEntryFromBundle.resources;
                String str3 = resourceEntryFromBundle.packageName;
                String resourceTypeName = resources.getResourceTypeName(resourceEntryFromBundle.resourceId);
                String strConcat = resourceEntryFromBundle.resourceName.concat(EMBEDDED_ACTIVITY_RESOURCE_SUFFIX);
                int identifier = resourceEntryFromBundle.resources.getIdentifier(strConcat, resourceTypeName, str3);
                if (identifier != 0) {
                    Log.i("PartnerConfigHelper", "use embedded activity resource:" + strConcat);
                    resourceEntry = new ResourceEntry(str3, strConcat, identifier, resourceEntryFromBundle.resources);
                    resourceEntryFromBundle = resourceEntry;
                } else {
                    Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(SUW_PACKAGE_NAME);
                    int identifier2 = resourcesForApplication.getIdentifier(strConcat, resourceTypeName, SUW_PACKAGE_NAME);
                    if (identifier2 != 0) {
                        resourceEntry2 = new ResourceEntry(SUW_PACKAGE_NAME, strConcat, identifier2, resourcesForApplication);
                        resourceEntryFromBundle = resourceEntry2;
                    }
                }
            } else if (isGlifExpressiveEnabled(context)) {
                String str4 = resourceEntryFromBundle.packageName;
                if (Objects.equals(str4, SUW_PACKAGE_NAME)) {
                    String resourceTypeName2 = resourceEntryFromBundle.resources.getResourceTypeName(resourceEntryFromBundle.resourceId);
                    String strConcat2 = resourceEntryFromBundle.resourceName.concat(GLIF_EXPRESSIVE_RESOURCE_SUFFIX);
                    int identifier3 = resourceEntryFromBundle.resources.getIdentifier(strConcat2, resourceTypeName2, str4);
                    if (identifier3 != 0) {
                        Log.i("PartnerConfigHelper", "use expressive resource:" + strConcat2);
                        resourceEntry = new ResourceEntry(str4, strConcat2, identifier3, resourceEntryFromBundle.resources);
                        resourceEntryFromBundle = resourceEntry;
                    }
                }
            } else if (!isForceTwoPaneEnabled(context)) {
                Bundle bundle4 = applyMaterialYouConfigBundle;
                if (bundle4 == null || bundle4.isEmpty()) {
                    try {
                        Bundle bundleCall = context.getContentResolver().call(getContentUri(context), IS_MATERIAL_YOU_STYLE_ENABLED_METHOD, (String) null, (Bundle) null);
                        applyMaterialYouConfigBundle = bundleCall;
                        if (bundleCall != null) {
                            bundleCall.isEmpty();
                        }
                        bundle = applyMaterialYouConfigBundle;
                        if ((bundle == null && bundle.getBoolean(IS_MATERIAL_YOU_STYLE_ENABLED_METHOD, false)) || isGlifExpressiveEnabled(context)) {
                            str2 = resourceEntryFromBundle.packageName;
                            if (Objects.equals(str2, SUW_PACKAGE_NAME)) {
                                String resourceTypeName3 = resourceEntryFromBundle.resources.getResourceTypeName(resourceEntryFromBundle.resourceId);
                                String strConcat3 = resourceEntryFromBundle.resourceName.concat(MATERIAL_YOU_RESOURCE_SUFFIX);
                                int identifier4 = resourceEntryFromBundle.resources.getIdentifier(strConcat3, resourceTypeName3, str2);
                                if (identifier4 != 0) {
                                    Log.i("PartnerConfigHelper", "use material you resource:" + strConcat3);
                                    resourceEntry = new ResourceEntry(str2, strConcat3, identifier4, resourceEntryFromBundle.resources);
                                    resourceEntryFromBundle = resourceEntry;
                                }
                            }
                        }
                    } catch (IllegalArgumentException | SecurityException unused) {
                        Log.w("PartnerConfigHelper", "SetupWizard Material You configs supporting status unknown; return as false.");
                        applyMaterialYouConfigBundle = null;
                    }
                } else {
                    bundle = applyMaterialYouConfigBundle;
                    if (bundle == null) {
                        str2 = resourceEntryFromBundle.packageName;
                        if (Objects.equals(str2, SUW_PACKAGE_NAME)) {
                        }
                    } else {
                        str2 = resourceEntryFromBundle.packageName;
                        if (Objects.equals(str2, SUW_PACKAGE_NAME)) {
                        }
                    }
                }
            } else if (context != null) {
                Resources resources2 = resourceEntryFromBundle.resources;
                String str5 = resourceEntryFromBundle.packageName;
                String resourceTypeName4 = resources2.getResourceTypeName(resourceEntryFromBundle.resourceId);
                String strConcat4 = resourceEntryFromBundle.resourceName.concat(FORCE_TWO_PANE_SUFFIX);
                int identifier5 = resourceEntryFromBundle.resources.getIdentifier(strConcat4, resourceTypeName4, str5);
                if (identifier5 != 0) {
                    Log.i("PartnerConfigHelper", "two pane resource=" + strConcat4);
                    resourceEntry = new ResourceEntry(str5, strConcat4, identifier5, resourceEntryFromBundle.resources);
                    resourceEntryFromBundle = resourceEntry;
                } else {
                    Resources resourcesForApplication2 = context.getPackageManager().getResourcesForApplication(SUW_PACKAGE_NAME);
                    int identifier6 = resourcesForApplication2.getIdentifier(strConcat4, resourceTypeName4, SUW_PACKAGE_NAME);
                    if (identifier6 != 0) {
                        resourceEntry2 = new ResourceEntry(SUW_PACKAGE_NAME, strConcat4, identifier6, resourcesForApplication2);
                        resourceEntryFromBundle = resourceEntry2;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused2) {
        }
        Resources resources3 = resourceEntryFromBundle.resources;
        Configuration configuration = resources3.getConfiguration();
        if (!isSetupWizardDayNightEnabled(context)) {
            int i = configuration.uiMode;
            if ((i & 48) == 32) {
                configuration.uiMode = (i & (-49)) | 16;
                resources3.updateConfiguration(configuration, resources3.getDisplayMetrics());
            }
        }
        return resourceEntryFromBundle;
    }

    public final String getString(Context context, PartnerConfig partnerConfig) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.STRING) {
            throw new IllegalArgumentException("Not a string resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return (String) this.partnerResourceCache.get(partnerConfig);
        }
        String string = null;
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            string = resourceEntryFromKey.resources.getString(resourceEntryFromKey.resourceId);
            this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) string);
            return string;
        } catch (NullPointerException unused) {
            return string;
        }
    }

    public boolean isActivityEmbedded(Context context) {
        try {
            Activity activityLookupActivityFromContext = lookupActivityFromContext(context);
            if (!isEmbeddedActivityOnePaneEnabled(context)) {
                return false;
            }
            EmbeddingInterfaceCompat embeddingInterfaceCompat = ((ExtensionEmbeddingBackend) ActivityEmbeddingController.getInstance(activityLookupActivityFromContext).backend).embeddingExtension;
            return embeddingInterfaceCompat != null ? ((EmbeddingCompat) embeddingInterfaceCompat).embeddingExtension.isActivityEmbedded(activityLookupActivityFromContext) : false;
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
