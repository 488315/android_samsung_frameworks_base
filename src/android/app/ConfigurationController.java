package android.app;

import android.app.servertransaction.ClientTransactionListenerController;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.os.LocaleList;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.WindowManagerGlobal;
import android.window.ConfigurationHelper;
import com.samsung.android.core.CompatSandbox;
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

    ConfigurationController(ActivityThreadInternal activityThreadInternal) {
        this.mActivityThread = activityThreadInternal;
    }

    Configuration updatePendingConfiguration(Configuration configuration) {
        synchronized (this.mResourcesManager) {
            Configuration configuration2 = this.mPendingConfiguration;
            if (configuration2 != null && !configuration2.isOtherSeqNewer(configuration)) {
                return null;
            }
            this.mPendingConfiguration = configuration;
            return configuration;
        }
    }

    Configuration getPendingConfiguration(boolean z) {
        Configuration configuration;
        synchronized (this.mResourcesManager) {
            configuration = this.mPendingConfiguration;
            if (configuration == null) {
                configuration = null;
            } else if (z) {
                this.mPendingConfiguration = null;
            }
        }
        return configuration;
    }

    void setCompatConfiguration(Configuration configuration) {
        this.mCompatConfiguration = new Configuration(configuration);
    }

    Configuration getCompatConfiguration() {
        return this.mCompatConfiguration;
    }

    final Configuration applyCompatConfiguration() {
        Configuration configuration = this.mConfiguration;
        int i = configuration.densityDpi;
        if (this.mCompatConfiguration == null) {
            this.mCompatConfiguration = new Configuration();
        }
        this.mCompatConfiguration.setTo(this.mConfiguration);
        return this.mResourcesManager.applyCompatConfiguration(i, this.mCompatConfiguration) ? this.mCompatConfiguration : configuration;
    }

    void setConfiguration(Configuration configuration) {
        this.mConfiguration = new Configuration(configuration);
    }

    Configuration getConfiguration() {
        return this.mConfiguration;
    }

    void handleConfigurationChanged(Configuration configuration) {
        Trace.traceBegin(64L, "configChanged");
        handleConfigurationChanged(configuration, null);
        Trace.traceEnd(64L);
    }

    void handleConfigurationChanged(CompatibilityInfo compatibilityInfo) {
        handleConfigurationChanged(this.mConfiguration, compatibilityInfo);
        WindowManagerGlobal.getInstance().reportNewConfiguration(this.mConfiguration);
    }

    void handleConfigurationChanged(Configuration configuration, CompatibilityInfo compatibilityInfo) {
        ClientTransactionListenerController clientTransactionListenerController = ClientTransactionListenerController.getInstance();
        Application currentApplication = ActivityThread.currentApplication();
        clientTransactionListenerController.onContextConfigurationPreChanged(currentApplication);
        try {
            handleConfigurationChangedInner(configuration, compatibilityInfo);
        } finally {
            clientTransactionListenerController.onContextConfigurationPostChanged(currentApplication);
        }
    }

    private void handleConfigurationChangedInner(Configuration configuration, CompatibilityInfo compatibilityInfo) {
        Resources.Theme theme = this.mActivityThread.getSystemContext().getTheme();
        Context systemUiContextNoCreate = this.mActivityThread.getSystemUiContextNoCreate();
        Resources.Theme theme2 = systemUiContextNoCreate != null ? systemUiContextNoCreate.getTheme() : null;
        synchronized (this.mResourcesManager) {
            Configuration configuration2 = this.mPendingConfiguration;
            if (configuration2 != null) {
                if (!configuration2.isOtherSeqNewer(configuration)) {
                    configuration = this.mPendingConfiguration;
                    updateDefaultDensity(configuration.densityDpi);
                }
                this.mPendingConfiguration = null;
            }
            if (configuration == null) {
                return;
            }
            Configuration configuration3 = this.mConfiguration;
            boolean z = configuration3 != null && configuration3.diffPublicOnly(configuration) == 0;
            Application application = this.mActivityThread.getApplication();
            application.getResources();
            this.mResourcesManager.applyConfigurationToResources(configuration, compatibilityInfo);
            updateLocaleListFromAppContext(application.getApplicationContext());
            if (this.mConfiguration == null) {
                this.mConfiguration = new Configuration();
            }
            if (this.mConfiguration.isOtherSeqNewer(configuration) || compatibilityInfo != null) {
                int updateFrom = this.mConfiguration.updateFrom(configuration);
                Configuration applyCompatConfiguration = applyCompatConfiguration();
                HardwareRenderer.sendDeviceConfigurationForDebugging(applyCompatConfiguration);
                if ((theme.getChangingConfigurations() & updateFrom) != 0) {
                    theme.rebase();
                }
                if (theme2 != null && (theme2.getChangingConfigurations() & updateFrom) != 0) {
                    theme2.rebase();
                }
                ArrayList<ComponentCallbacks2> collectComponentCallbacks = this.mActivityThread.collectComponentCallbacks(false);
                ConfigurationHelper.freeTextLayoutCachesIfNeeded(updateFrom);
                if (collectComponentCallbacks != null) {
                    int size = collectComponentCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ComponentCallbacks2 componentCallbacks2 = collectComponentCallbacks.get(i);
                        if (!z) {
                            performConfigurationChanged(componentCallbacks2, applyCompatConfiguration);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void performConfigurationChanged(ComponentCallbacks2 componentCallbacks2, Configuration configuration) {
        componentCallbacks2.onConfigurationChanged(createNewConfigAndUpdateIfNotNull(configuration, componentCallbacks2 instanceof ContextThemeWrapper ? ((ContextThemeWrapper) componentCallbacks2).getOverrideConfiguration() : null));
    }

    void updateDefaultDensity(int i) {
        if (this.mActivityThread.isInDensityCompatMode() || i == 0 || i == DisplayMetrics.DENSITY_DEVICE) {
            return;
        }
        DisplayMetrics.DENSITY_DEVICE = i;
        Bitmap.setDefaultDensity(i);
    }

    int getCurDefaultDisplayDpi() {
        return this.mConfiguration.densityDpi;
    }

    void updateLocaleListFromAppContext(Context context) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        LocaleList locales = this.mResourcesManager.getConfiguration().getLocales();
        int size = locales.size();
        for (int i = 0; i < size; i++) {
            if (locale.equals(locales.get(i))) {
                LocaleList.setDefault(locales, i);
                return;
            }
        }
        LocaleList.setDefault(new LocaleList(locale, locales));
    }

    static Configuration createNewConfigAndUpdateIfNotNull(Configuration configuration, Configuration configuration2) {
        if (configuration2 == null) {
            return configuration;
        }
        Configuration configuration3 = new Configuration(configuration);
        if (CompatSandbox.updateConfigWithoutWindowConfigurationIfNeeded(configuration3, configuration, configuration2)) {
            return configuration3;
        }
        configuration3.updateFrom(configuration2);
        return configuration3;
    }
}
