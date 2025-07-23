package androidx.appcompat.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.collection.LongSparseArray;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AppCompatActivity extends FragmentActivity implements AppCompatCallback {
    public AppCompatDelegateImpl mDelegate;

    public AppCompatActivity() {
        this.savedStateRegistryController.savedStateRegistry.registerSavedStateProvider("androidx:appcompat", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.appcompat.app.AppCompatActivity.1
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                Bundle bundle = new Bundle();
                AppCompatActivity.this.getDelegate().getClass();
                return bundle;
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: androidx.appcompat.app.AppCompatActivity.2
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public final void onContextAvailable() {
                AppCompatActivity appCompatActivity = AppCompatActivity.this;
                AppCompatDelegate delegate = appCompatActivity.getDelegate();
                delegate.installViewFactory();
                appCompatActivity.savedStateRegistryController.savedStateRegistry.consumeRestoredStateForKey("androidx:appcompat");
                delegate.onCreate();
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().addContentView(view, layoutParams);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public final void attachBaseContext(final Context context) {
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        appCompatDelegateImpl.mBaseContextAttached = true;
        int i = appCompatDelegateImpl.mLocalNightMode;
        if (i == -100) {
            i = AppCompatDelegate.sDefaultNightMode;
        }
        int mapNightMode = appCompatDelegateImpl.mapNightMode(i, context);
        if (AppCompatDelegate.isAutoStorageOptedIn(context) && AppCompatDelegate.isAutoStorageOptedIn(context) && !AppCompatDelegate.sIsFrameworkSyncChecked) {
            AppCompatDelegate.sSerialExecutorForLocalesStorage.execute(new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegate$$ExternalSyntheticLambda0
                /* JADX WARN: Code restructure failed: missing block: B:32:0x009c, code lost:
                
                    if (r6 != null) goto L73;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:42:0x009e, code lost:
                
                    r6.close();
                 */
                /* JADX WARN: Code restructure failed: missing block: B:48:0x0087, code lost:
                
                    if (r9 != 4) goto L84;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:51:0x0094, code lost:
                
                    if (r7.getName().equals("locales") == false) goto L89;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:53:0x0096, code lost:
                
                    r3 = r7.getAttributeValue(null, "application_locales");
                 */
                /* JADX WARN: Code restructure failed: missing block: B:72:0x00ab, code lost:
                
                    if (r6 == null) goto L47;
                 */
                /* JADX WARN: Removed duplicated region for block: B:39:0x00c9  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        r12 = this;
                        android.content.Context r12 = r1
                        androidx.appcompat.app.AppCompatDelegate$SerialExecutor r0 = androidx.appcompat.app.AppCompatDelegate.sSerialExecutorForLocalesStorage
                        android.content.ComponentName r0 = new android.content.ComponentName
                        java.lang.String r1 = "androidx.appcompat.app.AppLocalesMetadataHolderService"
                        r0.<init>(r12, r1)
                        android.content.pm.PackageManager r1 = r12.getPackageManager()
                        int r1 = r1.getComponentEnabledSetting(r0)
                        r2 = 1
                        if (r1 == r2) goto Ldc
                        androidx.collection.ArraySet r1 = androidx.appcompat.app.AppCompatDelegate.sActivityDelegates
                        r1.getClass()
                        androidx.collection.ArraySet$ElementIterator r3 = new androidx.collection.ArraySet$ElementIterator
                        r3.<init>()
                    L20:
                        boolean r1 = r3.hasNext()
                        java.lang.String r4 = "locale"
                        r5 = 0
                        if (r1 == 0) goto L42
                        java.lang.Object r1 = r3.next()
                        java.lang.ref.WeakReference r1 = (java.lang.ref.WeakReference) r1
                        java.lang.Object r1 = r1.get()
                        androidx.appcompat.app.AppCompatDelegate r1 = (androidx.appcompat.app.AppCompatDelegate) r1
                        if (r1 == 0) goto L20
                        android.content.Context r1 = r1.getContextForDelegate()
                        if (r1 == 0) goto L20
                        java.lang.Object r1 = r1.getSystemService(r4)
                        goto L43
                    L42:
                        r1 = r5
                    L43:
                        if (r1 == 0) goto L50
                        android.app.LocaleManager r1 = (android.app.LocaleManager) r1
                        android.os.LocaleList r1 = r1.getApplicationLocales()
                        androidx.core.os.LocaleListCompat r1 = androidx.core.os.LocaleListCompat.wrap(r1)
                        goto L52
                    L50:
                        androidx.core.os.LocaleListCompat r1 = androidx.core.os.LocaleListCompat.sEmptyLocaleList
                    L52:
                        androidx.core.os.LocaleListInterface r1 = r1.mImpl
                        boolean r1 = r1.isEmpty()
                        if (r1 == 0) goto Ld5
                        java.lang.Object r1 = androidx.core.app.AppLocalesStorageHelper.sAppLocaleStorageSync
                        monitor-enter(r1)
                        java.lang.String r3 = ""
                        java.lang.String r6 = "androidx.appcompat.app.AppCompatDelegate.application_locales_record_file"
                        java.io.FileInputStream r6 = r12.openFileInput(r6)     // Catch: java.lang.Throwable -> La2 java.io.FileNotFoundException -> Lc2
                        org.xmlpull.v1.XmlPullParser r7 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                        java.lang.String r8 = "UTF-8"
                        r7.setInput(r6, r8)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                        int r8 = r7.getDepth()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                    L72:
                        int r9 = r7.next()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                        if (r9 == r2) goto L9c
                        r10 = 3
                        if (r9 != r10) goto L84
                        int r11 = r7.getDepth()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                        if (r11 <= r8) goto L9c
                        goto L84
                    L82:
                        r12 = move-exception
                        goto Lbc
                    L84:
                        if (r9 == r10) goto L72
                        r10 = 4
                        if (r9 != r10) goto L8a
                        goto L72
                    L8a:
                        java.lang.String r9 = r7.getName()     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                        java.lang.String r10 = "locales"
                        boolean r9 = r9.equals(r10)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                        if (r9 == 0) goto L72
                        java.lang.String r8 = "application_locales"
                        java.lang.String r3 = r7.getAttributeValue(r5, r8)     // Catch: java.lang.Throwable -> L82 java.lang.Throwable -> La4
                    L9c:
                        if (r6 == 0) goto Lae
                    L9e:
                        r6.close()     // Catch: java.lang.Throwable -> La2 java.io.IOException -> Lae
                        goto Lae
                    La2:
                        r12 = move-exception
                        goto Ld3
                    La4:
                        java.lang.String r5 = "AppLocalesStorageHelper"
                        java.lang.String r7 = "Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file"
                        android.util.Log.w(r5, r7)     // Catch: java.lang.Throwable -> L82
                        if (r6 == 0) goto Lae
                        goto L9e
                    Lae:
                        boolean r5 = r3.isEmpty()     // Catch: java.lang.Throwable -> La2
                        if (r5 != 0) goto Lb5
                        goto Lba
                    Lb5:
                        java.lang.String r5 = "androidx.appcompat.app.AppCompatDelegate.application_locales_record_file"
                        r12.deleteFile(r5)     // Catch: java.lang.Throwable -> La2
                    Lba:
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> La2
                        goto Lc3
                    Lbc:
                        if (r6 == 0) goto Lc1
                        r6.close()     // Catch: java.lang.Throwable -> La2 java.io.IOException -> Lc1
                    Lc1:
                        throw r12     // Catch: java.lang.Throwable -> La2
                    Lc2:
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> La2
                    Lc3:
                        java.lang.Object r1 = r12.getSystemService(r4)
                        if (r1 == 0) goto Ld5
                        android.os.LocaleList r3 = android.os.LocaleList.forLanguageTags(r3)
                        android.app.LocaleManager r1 = (android.app.LocaleManager) r1
                        r1.setApplicationLocales(r3)
                        goto Ld5
                    Ld3:
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> La2
                        throw r12
                    Ld5:
                        android.content.pm.PackageManager r12 = r12.getPackageManager()
                        r12.setComponentEnabledSetting(r0, r2, r2)
                    Ldc:
                        androidx.appcompat.app.AppCompatDelegate.sIsFrameworkSyncChecked = r2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegate$$ExternalSyntheticLambda0.run():void");
                }
            });
        }
        Configuration configuration = null;
        if (context instanceof ContextThemeWrapper) {
            try {
                ((ContextThemeWrapper) context).applyOverrideConfiguration(AppCompatDelegateImpl.createOverrideAppConfiguration(context, mapNightMode, null, false));
            } catch (IllegalStateException unused) {
            }
            super.attachBaseContext(context);
        }
        if (context instanceof androidx.appcompat.view.ContextThemeWrapper) {
            try {
                ((androidx.appcompat.view.ContextThemeWrapper) context).applyOverrideConfiguration(AppCompatDelegateImpl.createOverrideAppConfiguration(context, mapNightMode, null, false));
            } catch (IllegalStateException unused2) {
            }
            super.attachBaseContext(context);
        }
        if (AppCompatDelegateImpl.sCanReturnDifferentContext) {
            Configuration configuration2 = new Configuration();
            configuration2.uiMode = -1;
            configuration2.fontScale = 0.0f;
            Configuration configuration3 = context.createConfigurationContext(configuration2).getResources().getConfiguration();
            Configuration configuration4 = context.getResources().getConfiguration();
            configuration3.uiMode = configuration4.uiMode;
            if (!configuration3.equals(configuration4)) {
                configuration = new Configuration();
                configuration.fontScale = 0.0f;
                if (configuration3.diff(configuration4) != 0) {
                    float f = configuration3.fontScale;
                    float f2 = configuration4.fontScale;
                    if (f != f2) {
                        configuration.fontScale = f2;
                    }
                    int i2 = configuration3.mcc;
                    int i3 = configuration4.mcc;
                    if (i2 != i3) {
                        configuration.mcc = i3;
                    }
                    int i4 = configuration3.mnc;
                    int i5 = configuration4.mnc;
                    if (i4 != i5) {
                        configuration.mnc = i5;
                    }
                    LocaleList locales = configuration3.getLocales();
                    LocaleList locales2 = configuration4.getLocales();
                    if (!locales.equals(locales2)) {
                        configuration.setLocales(locales2);
                        configuration.locale = configuration4.locale;
                    }
                    int i6 = configuration3.touchscreen;
                    int i7 = configuration4.touchscreen;
                    if (i6 != i7) {
                        configuration.touchscreen = i7;
                    }
                    int i8 = configuration3.keyboard;
                    int i9 = configuration4.keyboard;
                    if (i8 != i9) {
                        configuration.keyboard = i9;
                    }
                    int i10 = configuration3.keyboardHidden;
                    int i11 = configuration4.keyboardHidden;
                    if (i10 != i11) {
                        configuration.keyboardHidden = i11;
                    }
                    int i12 = configuration3.navigation;
                    int i13 = configuration4.navigation;
                    if (i12 != i13) {
                        configuration.navigation = i13;
                    }
                    int i14 = configuration3.navigationHidden;
                    int i15 = configuration4.navigationHidden;
                    if (i14 != i15) {
                        configuration.navigationHidden = i15;
                    }
                    int i16 = configuration3.orientation;
                    int i17 = configuration4.orientation;
                    if (i16 != i17) {
                        configuration.orientation = i17;
                    }
                    int i18 = configuration3.screenLayout & 15;
                    int i19 = configuration4.screenLayout & 15;
                    if (i18 != i19) {
                        configuration.screenLayout |= i19;
                    }
                    int i20 = configuration3.screenLayout & 192;
                    int i21 = configuration4.screenLayout & 192;
                    if (i20 != i21) {
                        configuration.screenLayout |= i21;
                    }
                    int i22 = configuration3.screenLayout & 48;
                    int i23 = configuration4.screenLayout & 48;
                    if (i22 != i23) {
                        configuration.screenLayout |= i23;
                    }
                    int i24 = configuration3.screenLayout & 768;
                    int i25 = configuration4.screenLayout & 768;
                    if (i24 != i25) {
                        configuration.screenLayout |= i25;
                    }
                    int i26 = configuration3.colorMode & 3;
                    int i27 = configuration4.colorMode & 3;
                    if (i26 != i27) {
                        configuration.colorMode |= i27;
                    }
                    int i28 = configuration3.colorMode & 12;
                    int i29 = configuration4.colorMode & 12;
                    if (i28 != i29) {
                        configuration.colorMode |= i29;
                    }
                    int i30 = configuration3.uiMode & 15;
                    int i31 = configuration4.uiMode & 15;
                    if (i30 != i31) {
                        configuration.uiMode |= i31;
                    }
                    int i32 = configuration3.uiMode & 48;
                    int i33 = configuration4.uiMode & 48;
                    if (i32 != i33) {
                        configuration.uiMode |= i33;
                    }
                    int i34 = configuration3.screenWidthDp;
                    int i35 = configuration4.screenWidthDp;
                    if (i34 != i35) {
                        configuration.screenWidthDp = i35;
                    }
                    int i36 = configuration3.screenHeightDp;
                    int i37 = configuration4.screenHeightDp;
                    if (i36 != i37) {
                        configuration.screenHeightDp = i37;
                    }
                    int i38 = configuration3.smallestScreenWidthDp;
                    int i39 = configuration4.smallestScreenWidthDp;
                    if (i38 != i39) {
                        configuration.smallestScreenWidthDp = i39;
                    }
                    int i40 = configuration3.densityDpi;
                    int i41 = configuration4.densityDpi;
                    if (i40 != i41) {
                        configuration.densityDpi = i41;
                    }
                }
            }
            Configuration createOverrideAppConfiguration = AppCompatDelegateImpl.createOverrideAppConfiguration(context, mapNightMode, configuration, true);
            androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 2132018774);
            contextThemeWrapper.applyOverrideConfiguration(createOverrideAppConfiguration);
            try {
                if (context.getTheme() != null) {
                    contextThemeWrapper.getTheme().rebase();
                }
            } catch (NullPointerException unused3) {
            }
            context = contextThemeWrapper;
        }
        super.attachBaseContext(context);
    }

    @Override // android.app.Activity
    public final void closeOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        if (getWindow().hasFeature(0)) {
            if (supportActionBar == null || !supportActionBar.closeOptionsMenu()) {
                super.closeOptionsMenu();
            }
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        ActionBar supportActionBar = getSupportActionBar();
        if (keyCode == 82 && supportActionBar != null && supportActionBar.onMenuKeyEvent(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Activity
    public final View findViewById(int i) {
        return getDelegate().findViewById(i);
    }

    public final AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            AppCompatDelegate.SerialExecutor serialExecutor = AppCompatDelegate.sSerialExecutorForLocalesStorage;
            this.mDelegate = new AppCompatDelegateImpl(this, this);
        }
        return this.mDelegate;
    }

    @Override // android.app.Activity
    public final MenuInflater getMenuInflater() {
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        if (appCompatDelegateImpl.mMenuInflater == null) {
            appCompatDelegateImpl.initWindowDecorActionBar();
            ActionBar actionBar = appCompatDelegateImpl.mActionBar;
            appCompatDelegateImpl.mMenuInflater = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : appCompatDelegateImpl.mContext);
        }
        return appCompatDelegateImpl.mMenuInflater;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        int i = VectorEnabledTintResources.$r8$clinit;
        return super.getResources();
    }

    public final ActionBar getSupportActionBar() {
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        appCompatDelegateImpl.initWindowDecorActionBar();
        return appCompatDelegateImpl.mActionBar;
    }

    public final void initViewTreeOwners() {
        ViewTreeLifecycleOwner.set(this, getWindow().getDecorView());
        ViewTreeViewModelStoreOwner.set(getWindow().getDecorView(), this);
        ViewTreeSavedStateRegistryOwner.set(getWindow().getDecorView(), this);
        ViewTreeOnBackPressedDispatcherOwner.set(getWindow().getDecorView(), this);
    }

    @Override // android.app.Activity
    public final void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        if (appCompatDelegateImpl.mHasActionBar && appCompatDelegateImpl.mSubDecorInstalled) {
            appCompatDelegateImpl.initWindowDecorActionBar();
            ActionBar actionBar = appCompatDelegateImpl.mActionBar;
            if (actionBar != null) {
                actionBar.onConfigurationChanged();
            }
        }
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        Context context = appCompatDelegateImpl.mContext;
        synchronized (appCompatDrawableManager) {
            ResourceManagerInternal resourceManagerInternal = appCompatDrawableManager.mResourceManager;
            synchronized (resourceManagerInternal) {
                LongSparseArray longSparseArray = (LongSparseArray) resourceManagerInternal.mDrawableCaches.get(context);
                if (longSparseArray != null) {
                    longSparseArray.clear();
                }
            }
        }
        appCompatDelegateImpl.mEffectiveConfiguration = new Configuration(appCompatDelegateImpl.mContext.getResources().getConfiguration());
        appCompatDelegateImpl.applyApplicationSpecificConfig(false);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        Intent parentActivityIntent;
        if (!super.onMenuItemSelected(i, menuItem)) {
            ActionBar supportActionBar = getSupportActionBar();
            if (menuItem.getItemId() != 16908332 || supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0 || (parentActivityIntent = NavUtils.getParentActivityIntent(this)) == null) {
                return false;
            }
            if (!shouldUpRecreateTask(parentActivityIntent)) {
                navigateUpTo(parentActivityIntent);
                return true;
            }
            TaskStackBuilder create = TaskStackBuilder.create(this);
            Intent parentActivityIntent2 = NavUtils.getParentActivityIntent(this);
            if (parentActivityIntent2 == null) {
                parentActivityIntent2 = NavUtils.getParentActivityIntent(this);
            }
            if (parentActivityIntent2 != null) {
                ComponentName component = parentActivityIntent2.getComponent();
                if (component == null) {
                    component = parentActivityIntent2.resolveActivity(create.mSourceContext.getPackageManager());
                }
                create.addParentStack(component);
                create.mIntents.add(parentActivityIntent2);
            }
            create.startActivities();
            try {
                finishAffinity();
            } catch (IllegalStateException unused) {
                finish();
            }
        }
        return true;
    }

    @Override // android.app.Activity
    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        ((AppCompatDelegateImpl) getDelegate()).ensureSubDecor();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPostResume() {
        super.onPostResume();
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        appCompatDelegateImpl.initWindowDecorActionBar();
        ActionBar actionBar = appCompatDelegateImpl.mActionBar;
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        ((AppCompatDelegateImpl) getDelegate()).applyApplicationSpecificConfig(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override // android.app.Activity
    public final void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        getDelegate().setTitle(charSequence);
    }

    @Override // android.app.Activity
    public final void openOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        if (getWindow().hasFeature(0)) {
            if (supportActionBar == null || !supportActionBar.openOptionsMenu()) {
                super.openOptionsMenu();
            }
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        initViewTreeOwners();
        getDelegate().setContentView(i);
    }

    public final void setSupportActionBar(Toolbar toolbar) {
        AppCompatDelegateImpl appCompatDelegateImpl = (AppCompatDelegateImpl) getDelegate();
        if (appCompatDelegateImpl.mHost instanceof Activity) {
            appCompatDelegateImpl.initWindowDecorActionBar();
            ActionBar actionBar = appCompatDelegateImpl.mActionBar;
            if (actionBar instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            appCompatDelegateImpl.mMenuInflater = null;
            if (actionBar != null) {
                actionBar.onDestroy();
            }
            appCompatDelegateImpl.mActionBar = null;
            if (toolbar != null) {
                Object obj = appCompatDelegateImpl.mHost;
                ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, obj instanceof Activity ? ((Activity) obj).getTitle() : appCompatDelegateImpl.mTitle, appCompatDelegateImpl.mAppCompatWindowCallback);
                appCompatDelegateImpl.mActionBar = toolbarActionBar;
                appCompatDelegateImpl.mAppCompatWindowCallback.mActionBarCallback = toolbarActionBar.mMenuCallback;
                if (!toolbar.mBackInvokedCallbackEnabled) {
                    toolbar.mBackInvokedCallbackEnabled = true;
                    toolbar.updateBackInvokedCallbackState();
                }
                Window window = appCompatDelegateImpl.mWindow;
                if (window != null) {
                    window.setCallback(appCompatDelegateImpl.mAppCompatWindowCallback);
                }
            } else {
                appCompatDelegateImpl.mAppCompatWindowCallback.mActionBarCallback = null;
            }
            appCompatDelegateImpl.invalidateOptionsMenu();
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i) {
        super.setTheme(i);
        ((AppCompatDelegateImpl) getDelegate()).mThemeResId = i;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void setContentView(View view) {
        initViewTreeOwners();
        getDelegate().setContentView(view);
    }

    public AppCompatActivity(int i) {
        super(i);
        this.savedStateRegistryController.savedStateRegistry.registerSavedStateProvider("androidx:appcompat", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.appcompat.app.AppCompatActivity.1
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                Bundle bundle = new Bundle();
                AppCompatActivity.this.getDelegate().getClass();
                return bundle;
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: androidx.appcompat.app.AppCompatActivity.2
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public final void onContextAvailable() {
                AppCompatActivity appCompatActivity = AppCompatActivity.this;
                AppCompatDelegate delegate = appCompatActivity.getDelegate();
                delegate.installViewFactory();
                appCompatActivity.savedStateRegistryController.savedStateRegistry.consumeRestoredStateForKey("androidx:appcompat");
                delegate.onCreate();
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().setContentView(view, layoutParams);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onContentChanged() {
    }
}
