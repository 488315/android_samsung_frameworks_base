package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.savedstate.SavedStateRegistry;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppCompatActivity extends FragmentActivity implements AppCompatCallback {
    public AppCompatDelegateImpl mDelegate;

    public AppCompatActivity() {
        this.mSavedStateRegistryController.savedStateRegistry.registerSavedStateProvider("androidx:appcompat", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.appcompat.app.AppCompatActivity.1
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
                appCompatActivity.mSavedStateRegistryController.savedStateRegistry.consumeRestoredStateForKey("androidx:appcompat");
                delegate.onCreate();
            }
        });
    }

    private void initViewTreeOwners() {
        getWindow().getDecorView().setTag(R.id.view_tree_lifecycle_owner, this);
        getWindow().getDecorView().setTag(R.id.view_tree_view_model_store_owner, this);
        getWindow().getDecorView().setTag(R.id.view_tree_saved_state_registry_owner, this);
        getWindow().getDecorView().setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().addContentView(view, layoutParams);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(getDelegate().attachBaseContext2(context));
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
            AppLocalesStorageHelper$SerialExecutor appLocalesStorageHelper$SerialExecutor = AppCompatDelegate.sSerialExecutorForLocalesStorage;
            this.mDelegate = new AppCompatDelegateImpl(this, this);
        }
        return this.mDelegate;
    }

    @Override // android.app.Activity
    public final MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final Resources getResources() {
        int i = VectorEnabledTintResources.$r8$clinit;
        return super.getResources();
    }

    public final ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    @Override // android.app.Activity
    public final void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getDelegate().onConfigurationChanged(configuration);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        Intent parentActivityIntent;
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (menuItem.getItemId() == 16908332 && supportActionBar != null && (supportActionBar.getDisplayOptions() & 4) != 0 && (parentActivityIntent = NavUtils.getParentActivityIntent(this)) != null) {
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
                int size = create.mIntents.size();
                try {
                    for (Intent parentActivityIntent3 = NavUtils.getParentActivityIntent(create.mSourceContext, component); parentActivityIntent3 != null; parentActivityIntent3 = NavUtils.getParentActivityIntent(create.mSourceContext, parentActivityIntent3.getComponent())) {
                        create.mIntents.add(size, parentActivityIntent3);
                    }
                    create.mIntents.add(parentActivityIntent2);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
                    throw new IllegalArgumentException(e);
                }
            }
            if (create.mIntents.isEmpty()) {
                throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
            }
            Intent[] intentArr = (Intent[]) create.mIntents.toArray(new Intent[0]);
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            Context context = create.mSourceContext;
            Object obj = ContextCompat.sLock;
            context.startActivities(intentArr, null);
            try {
                int i2 = ActivityCompat.$r8$clinit;
                finishAffinity();
                return true;
            } catch (IllegalStateException unused) {
                finish();
                return true;
            }
        }
        return false;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onMenuOpened(int i, Menu menu) {
        return super.onMenuOpened(i, menu);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public final void onPanelClosed(int i, Menu menu) {
        super.onPanelClosed(i, menu);
    }

    @Override // android.app.Activity
    public final void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        ((AppCompatDelegateImpl) getDelegate()).ensureSubDecor();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        getDelegate().onStart();
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
    public final void setContentView(int i) {
        initViewTreeOwners();
        getDelegate().setContentView(i);
    }

    public final void setSupportActionBar(Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i) {
        super.setTheme(i);
        getDelegate().setTheme(i);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void setContentView(View view) {
        initViewTreeOwners();
        getDelegate().setContentView(view);
    }

    public AppCompatActivity(int i) {
        super(i);
        this.mSavedStateRegistryController.savedStateRegistry.registerSavedStateProvider("androidx:appcompat", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.appcompat.app.AppCompatActivity.1
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
                appCompatActivity.mSavedStateRegistryController.savedStateRegistry.consumeRestoredStateForKey("androidx:appcompat");
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

    @Override // androidx.appcompat.app.AppCompatCallback
    public final void onSupportActionModeFinished() {
    }

    @Override // androidx.appcompat.app.AppCompatCallback
    public final void onSupportActionModeStarted() {
    }

    @Override // androidx.appcompat.app.AppCompatCallback
    public final void onWindowStartingSupportActionMode() {
    }
}
