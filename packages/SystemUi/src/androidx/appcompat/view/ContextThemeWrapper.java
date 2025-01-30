package androidx.appcompat.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ContextThemeWrapper extends ContextWrapper {
    public static Configuration sEmptyConfig;
    public LayoutInflater mInflater;
    public Configuration mOverrideConfiguration;
    public Resources mResources;
    public Resources.Theme mTheme;
    public int mThemeResource;

    public ContextThemeWrapper() {
        super(null);
    }

    public final void applyOverrideConfiguration(Configuration configuration) {
        if (this.mResources != null) {
            throw new IllegalStateException("getResources() or getAssets() has already been called");
        }
        if (this.mOverrideConfiguration != null) {
            throw new IllegalStateException("Override configuration has already been set");
        }
        this.mOverrideConfiguration = new Configuration(configuration);
    }

    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        if (this.mResources == null) {
            Configuration configuration = this.mOverrideConfiguration;
            if (configuration != null) {
                if (sEmptyConfig == null) {
                    Configuration configuration2 = new Configuration();
                    configuration2.fontScale = 0.0f;
                    sEmptyConfig = configuration2;
                }
                if (!configuration.equals(sEmptyConfig)) {
                    this.mResources = createConfigurationContext(this.mOverrideConfiguration).getResources();
                }
            }
            this.mResources = super.getResources();
        }
        return this.mResources;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources.Theme getTheme() {
        Resources.Theme theme = this.mTheme;
        if (theme != null) {
            return theme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = 2132018393;
        }
        initializeTheme();
        return this.mTheme;
    }

    public final int getThemeResId() {
        return this.mThemeResource;
    }

    public final void initializeTheme() {
        if (this.mTheme == null) {
            this.mTheme = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        this.mTheme.applyStyle(this.mThemeResource, true);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i) {
        if (this.mThemeResource != i) {
            this.mThemeResource = i;
            initializeTheme();
        }
    }

    public ContextThemeWrapper(Context context, int i) {
        super(context);
        this.mThemeResource = i;
    }

    public ContextThemeWrapper(Context context, Resources.Theme theme) {
        super(context);
        this.mTheme = theme;
    }
}
