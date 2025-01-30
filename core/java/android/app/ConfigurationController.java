package android.app;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.p009os.LocaleList;
import android.p009os.Trace;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.WindowManagerGlobal;
import android.window.ConfigurationHelper;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes.dex */
class ConfigurationController {
    private static final String TAG = "ConfigurationController";
    private final ActivityThreadInternal mActivityThread;
    private Configuration mCompatConfiguration;
    private Configuration mConfiguration;
    private Configuration mPendingConfiguration;
    private final ResourcesManager mResourcesManager = ResourcesManager.getInstance();

    ConfigurationController(ActivityThreadInternal activityThread) {
        this.mActivityThread = activityThread;
    }

    Configuration updatePendingConfiguration(Configuration config) {
        synchronized (this.mResourcesManager) {
            Configuration configuration = this.mPendingConfiguration;
            if (configuration != null && !configuration.isOtherSeqNewer(config)) {
                return null;
            }
            this.mPendingConfiguration = config;
            return config;
        }
    }

    Configuration getPendingConfiguration(boolean clearPending) {
        Configuration outConfig = null;
        synchronized (this.mResourcesManager) {
            Configuration configuration = this.mPendingConfiguration;
            if (configuration != null) {
                outConfig = configuration;
                if (clearPending) {
                    this.mPendingConfiguration = null;
                }
            }
        }
        return outConfig;
    }

    void setCompatConfiguration(Configuration config) {
        this.mCompatConfiguration = new Configuration(config);
    }

    Configuration getCompatConfiguration() {
        return this.mCompatConfiguration;
    }

    final Configuration applyCompatConfiguration() {
        Configuration config = this.mConfiguration;
        int displayDensity = config.densityDpi;
        if (this.mCompatConfiguration == null) {
            this.mCompatConfiguration = new Configuration();
        }
        this.mCompatConfiguration.setTo(this.mConfiguration);
        if (this.mResourcesManager.applyCompatConfiguration(displayDensity, this.mCompatConfiguration)) {
            return this.mCompatConfiguration;
        }
        return config;
    }

    void setConfiguration(Configuration config) {
        this.mConfiguration = new Configuration(config);
    }

    Configuration getConfiguration() {
        return this.mConfiguration;
    }

    void handleConfigurationChanged(Configuration config) {
        Trace.traceBegin(64L, "configChanged");
        handleConfigurationChanged(config, null);
        Trace.traceEnd(64L);
    }

    void handleConfigurationChanged(CompatibilityInfo compat) {
        handleConfigurationChanged(this.mConfiguration, compat);
        WindowManagerGlobal.getInstance().reportNewConfiguration(this.mConfiguration);
    }

    void handleConfigurationChanged(Configuration config, CompatibilityInfo compat) {
        Resources.Theme systemTheme = this.mActivityThread.getSystemContext().getTheme();
        ContextImpl systemUiContext = this.mActivityThread.getSystemUiContextNoCreate();
        Resources.Theme systemUiTheme = systemUiContext != null ? systemUiContext.getTheme() : null;
        synchronized (this.mResourcesManager) {
            Configuration configuration = this.mPendingConfiguration;
            if (configuration != null) {
                if (!configuration.isOtherSeqNewer(config)) {
                    config = this.mPendingConfiguration;
                    updateDefaultDensity(config.densityDpi);
                }
                this.mPendingConfiguration = null;
            }
            if (config == null) {
                return;
            }
            Configuration configuration2 = this.mConfiguration;
            boolean equivalent = configuration2 != null && configuration2.diffPublicOnly(config) == 0;
            Application app = this.mActivityThread.getApplication();
            app.getResources();
            this.mResourcesManager.applyConfigurationToResources(config, compat);
            updateLocaleListFromAppContext(app.getApplicationContext());
            if (this.mConfiguration == null) {
                this.mConfiguration = new Configuration();
            }
            if (this.mConfiguration.isOtherSeqNewer(config) || compat != null) {
                int configDiff = this.mConfiguration.updateFrom(config);
                Configuration config2 = applyCompatConfiguration();
                HardwareRenderer.sendDeviceConfigurationForDebugging(config2);
                if ((systemTheme.getChangingConfigurations() & configDiff) != 0) {
                    systemTheme.rebase();
                }
                if (systemUiTheme != null && (systemUiTheme.getChangingConfigurations() & configDiff) != 0) {
                    systemUiTheme.rebase();
                }
                ArrayList<ComponentCallbacks2> callbacks = this.mActivityThread.collectComponentCallbacks(false);
                ConfigurationHelper.freeTextLayoutCachesIfNeeded(configDiff);
                if (callbacks != null) {
                    int size = callbacks.size();
                    for (int i = 0; i < size; i++) {
                        ComponentCallbacks2 cb = callbacks.get(i);
                        if (!equivalent) {
                            performConfigurationChanged(cb, config2);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void performConfigurationChanged(ComponentCallbacks2 componentCallbacks2, Configuration newConfig) {
        Configuration contextThemeWrapperOverrideConfig = null;
        if (componentCallbacks2 instanceof ContextThemeWrapper) {
            ContextThemeWrapper contextThemeWrapper = (ContextThemeWrapper) componentCallbacks2;
            contextThemeWrapperOverrideConfig = contextThemeWrapper.getOverrideConfiguration();
        }
        Configuration configToReport = createNewConfigAndUpdateIfNotNull(newConfig, contextThemeWrapperOverrideConfig);
        componentCallbacks2.onConfigurationChanged(configToReport);
    }

    void updateDefaultDensity(int densityDpi) {
        if (!this.mActivityThread.isInDensityCompatMode() && densityDpi != 0 && densityDpi != DisplayMetrics.DENSITY_DEVICE) {
            DisplayMetrics.DENSITY_DEVICE = densityDpi;
            Bitmap.setDefaultDensity(densityDpi);
        }
    }

    int getCurDefaultDisplayDpi() {
        return this.mConfiguration.densityDpi;
    }

    void updateLocaleListFromAppContext(Context context) {
        Locale bestLocale = context.getResources().getConfiguration().getLocales().get(0);
        LocaleList newLocaleList = this.mResourcesManager.getConfiguration().getLocales();
        int newLocaleListSize = newLocaleList.size();
        for (int i = 0; i < newLocaleListSize; i++) {
            if (bestLocale.equals(newLocaleList.get(i))) {
                LocaleList.setDefault(newLocaleList, i);
                return;
            }
        }
        LocaleList.setDefault(new LocaleList(bestLocale, newLocaleList));
    }

    static Configuration createNewConfigAndUpdateIfNotNull(Configuration base, Configuration override) {
        int compatSandboxFlags;
        if (override == null) {
            return base;
        }
        Configuration newConfig = new Configuration(base);
        if (CoreRune.MT_SUPPORT_COMPAT_SANDBOX && ((compatSandboxFlags = base.windowConfiguration.getCompatSandboxFlags()) != 0 || compatSandboxFlags != override.windowConfiguration.getCompatSandboxFlags())) {
            newConfig.updateFrom(override, true);
            return newConfig;
        }
        newConfig.updateFrom(override);
        return newConfig;
    }
}
