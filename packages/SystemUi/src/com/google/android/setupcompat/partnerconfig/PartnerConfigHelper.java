package com.google.android.setupcompat.partnerconfig;

import android.content.Context;
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
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.util.BuildCompatUtils;
import java.util.EnumMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PartnerConfigHelper {
    public static final String EMBEDDED_ACTIVITY_RESOURCE_SUFFIX = "_embedded_activity";
    public static final String GET_SUW_DEFAULT_THEME_STRING_METHOD = "suwDefaultThemeString";
    public static final String IS_DYNAMIC_COLOR_ENABLED_METHOD = "isDynamicColorEnabled";
    public static final String IS_EMBEDDED_ACTIVITY_ONE_PANE_ENABLED_METHOD = "isEmbeddedActivityOnePaneEnabled";
    public static final String IS_EXTENDED_PARTNER_CONFIG_ENABLED_METHOD = "isExtendedPartnerConfigEnabled";
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
    public static Bundle applyMaterialYouConfigBundle = null;
    public static Bundle applyNeutralButtonStyleBundle = null;
    static Bundle applyTransitionBundle = null;
    public static C44161 contentObserver = null;
    public static PartnerConfigHelper instance = null;
    public static String mAuthority = null;
    public static boolean savedConfigEmbeddedActivityMode = false;
    public static int savedConfigUiMode = 0;
    public static int savedOrientation = 1;
    public static int savedScreenHeight;
    public static int savedScreenWidth;
    static Bundle suwDayNightEnabledBundle;
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
            Log.w("PartnerConfigHelper", "isSetupWizardDayNightEnabled() is true");
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
            if (!isValidInstance(context)) {
                instance = new PartnerConfigHelper(context);
            }
            partnerConfigHelper = instance;
        }
        return partnerConfigHelper;
    }

    public static Uri getContentUri(Context context) {
        boolean z;
        if (mAuthority == null) {
            try {
                context.getPackageManager().getApplicationInfo(SUW_PACKAGE_NAME, 128);
                z = true;
            } catch (PackageManager.NameNotFoundException unused) {
                z = false;
            }
            mAuthority = z ? "com.google.android.setupwizard.partner" : "com.sec.android.app.SecSetupWizard.partner";
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

    public static boolean isValidInstance(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        if (instance == null) {
            savedConfigEmbeddedActivityMode = isEmbeddedActivityOnePaneEnabled(context) && BuildCompatUtils.isAtLeastU();
            savedConfigUiMode = configuration.uiMode & 48;
            savedOrientation = configuration.orientation;
            savedScreenWidth = configuration.screenWidthDp;
            savedScreenHeight = configuration.screenHeightDp;
            return false;
        }
        boolean z = isSetupWizardDayNightEnabled(context) && (configuration.uiMode & 48) != savedConfigUiMode;
        boolean z2 = isEmbeddedActivityOnePaneEnabled(context) && BuildCompatUtils.isAtLeastU();
        if (!z && z2 == savedConfigEmbeddedActivityMode && configuration.orientation == savedOrientation && configuration.screenWidthDp == savedScreenWidth && configuration.screenHeightDp == savedScreenHeight) {
            return true;
        }
        savedConfigUiMode = configuration.uiMode & 48;
        savedOrientation = configuration.orientation;
        savedScreenHeight = configuration.screenHeightDp;
        savedScreenWidth = configuration.screenWidthDp;
        resetInstance();
        return false;
    }

    public static synchronized void resetInstance() {
        synchronized (PartnerConfigHelper.class) {
            instance = null;
            suwDayNightEnabledBundle = null;
            applyExtendedPartnerConfigBundle = null;
            applyMaterialYouConfigBundle = null;
            applyDynamicColorBundle = null;
            applyNeutralButtonStyleBundle = null;
            applyEmbeddedActivityOnePaneBundle = null;
            suwDefaultThemeBundle = null;
            applyTransitionBundle = null;
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
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            float fraction = resourceEntryFromKey.resources.getFraction(resourceEntryFromKey.resourceId, 1, 1);
            try {
                this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Float.valueOf(fraction));
                return fraction;
            } catch (Resources.NotFoundException | NullPointerException unused) {
                return fraction;
            }
        } catch (Resources.NotFoundException | NullPointerException unused2) {
            return 0.0f;
        }
    }

    public final int getInteger(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.INTEGER) {
            throw new IllegalArgumentException("Not a integer resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return ((Integer) this.partnerResourceCache.get(partnerConfig)).intValue();
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            int integer = resourceEntryFromKey.resources.getInteger(resourceEntryFromKey.resourceId);
            try {
                this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) Integer.valueOf(integer));
                return integer;
            } catch (Resources.NotFoundException | NullPointerException unused) {
                return integer;
            }
        } catch (Resources.NotFoundException | NullPointerException unused2) {
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a6 A[Catch: NotFoundException -> 0x00cf, TryCatch #1 {NotFoundException -> 0x00cf, blocks: (B:21:0x009c, B:23:0x00a0, B:25:0x00a6, B:27:0x00ba), top: B:20:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x005a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ResourceEntry getResourceEntryFromKey(Context context, String str) {
        boolean z;
        String str2;
        Bundle bundle = this.resultBundle.getBundle(str);
        Bundle bundle2 = this.resultBundle.getBundle(KEY_FALLBACK_CONFIG);
        if (bundle2 != null) {
            bundle.putBundle(KEY_FALLBACK_CONFIG, bundle2.getBundle(str));
        }
        ResourceEntry fromBundle = ResourceEntry.fromBundle(context, bundle);
        Bundle bundle3 = applyMaterialYouConfigBundle;
        if (bundle3 == null || bundle3.isEmpty()) {
            try {
                Bundle call = context.getContentResolver().call(getContentUri(context), IS_MATERIAL_YOU_STYLE_ENABLED_METHOD, (String) null, (Bundle) null);
                applyMaterialYouConfigBundle = call;
                if (call != null) {
                    call.isEmpty();
                }
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard Material You configs supporting status unknown; return as false.");
                applyMaterialYouConfigBundle = null;
            }
        }
        Bundle bundle4 = applyMaterialYouConfigBundle;
        if (bundle4 != null && bundle4.getBoolean(IS_MATERIAL_YOU_STYLE_ENABLED_METHOD, false)) {
            z = true;
            if (z) {
                try {
                    String str3 = fromBundle.packageName;
                    Resources resources = fromBundle.resources;
                    if (SUW_PACKAGE_NAME.equals(str3)) {
                        String resourceTypeName = resources.getResourceTypeName(fromBundle.resourceId);
                        String concat = fromBundle.resourceName.concat(MATERIAL_YOU_RESOURCE_SUFFIX);
                        int identifier = resources.getIdentifier(concat, resourceTypeName, str3);
                        if (identifier != 0) {
                            Log.i("PartnerConfigHelper", "use material you resource:" + concat);
                            fromBundle = new ResourceEntry(str3, concat, identifier, resources);
                        }
                    }
                } catch (Resources.NotFoundException unused2) {
                }
            }
            if (BuildCompatUtils.isAtLeastU() && isEmbeddedActivityOnePaneEnabled(context)) {
                try {
                    str2 = fromBundle.packageName;
                    Resources resources2 = fromBundle.resources;
                    if (SUW_PACKAGE_NAME.equals(str2)) {
                        String resourceTypeName2 = resources2.getResourceTypeName(fromBundle.resourceId);
                        String concat2 = fromBundle.resourceName.concat(EMBEDDED_ACTIVITY_RESOURCE_SUFFIX);
                        int identifier2 = resources2.getIdentifier(concat2, resourceTypeName2, str2);
                        if (identifier2 != 0) {
                            Log.i("PartnerConfigHelper", "use embedded activity resource:" + concat2);
                            fromBundle = new ResourceEntry(str2, concat2, identifier2, resources2);
                        }
                    }
                } catch (Resources.NotFoundException unused3) {
                }
            }
            Resources resources3 = fromBundle.resources;
            Configuration configuration = resources3.getConfiguration();
            if (!isSetupWizardDayNightEnabled(context)) {
                int i = configuration.uiMode;
                if ((i & 48) == 32) {
                    configuration.uiMode = (i & (-49)) | 16;
                    resources3.updateConfiguration(configuration, resources3.getDisplayMetrics());
                }
            }
            return fromBundle;
        }
        z = false;
        if (z) {
        }
        if (BuildCompatUtils.isAtLeastU()) {
            str2 = fromBundle.packageName;
            Resources resources22 = fromBundle.resources;
            if (SUW_PACKAGE_NAME.equals(str2)) {
            }
        }
        Resources resources32 = fromBundle.resources;
        Configuration configuration2 = resources32.getConfiguration();
        if (!isSetupWizardDayNightEnabled(context)) {
        }
        return fromBundle;
    }

    public final String getString(Context context, PartnerConfig partnerConfig) {
        if (partnerConfig.getResourceType() != PartnerConfig.ResourceType.STRING) {
            throw new IllegalArgumentException("Not a string resource");
        }
        if (this.partnerResourceCache.containsKey(partnerConfig)) {
            return (String) this.partnerResourceCache.get(partnerConfig);
        }
        try {
            ResourceEntry resourceEntryFromKey = getResourceEntryFromKey(context, partnerConfig.getResourceName());
            String string = resourceEntryFromKey.resources.getString(resourceEntryFromKey.resourceId);
            try {
                this.partnerResourceCache.put((EnumMap<PartnerConfig, Object>) partnerConfig, (PartnerConfig) string);
                return string;
            } catch (NullPointerException unused) {
                return string;
            }
        } catch (NullPointerException unused2) {
            return null;
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
