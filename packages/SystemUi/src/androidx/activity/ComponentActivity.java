package androidx.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Trace;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.contextaware.ContextAwareHelper;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuHostHelper;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ComponentActivity extends androidx.core.app.ComponentActivity implements ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, OnConfigurationChangedProvider, OnTrimMemoryProvider, OnMultiWindowModeChangedProvider, OnPictureInPictureModeChangedProvider, MenuHost {
    public final C00142 mActivityResultRegistry;
    public final int mContentLayoutId;
    public final ContextAwareHelper mContextAwareHelper;
    public SavedStateViewModelFactory mDefaultFactory;
    public boolean mDispatchingOnMultiWindowModeChanged;
    public boolean mDispatchingOnPictureInPictureModeChanged;
    public final FullyDrawnReporter mFullyDrawnReporter;
    public final LifecycleRegistry mLifecycleRegistry;
    public final MenuHostHelper mMenuHostHelper;
    public final OnBackPressedDispatcher mOnBackPressedDispatcher;
    public final CopyOnWriteArrayList mOnConfigurationChangedListeners;
    public final CopyOnWriteArrayList mOnMultiWindowModeChangedListeners;
    public final CopyOnWriteArrayList mOnNewIntentListeners;
    public final CopyOnWriteArrayList mOnPictureInPictureModeChangedListeners;
    public final CopyOnWriteArrayList mOnTrimMemoryListeners;
    public final ReportFullyDrawnExecutorApi16Impl mReportFullyDrawnExecutor;
    public final SavedStateRegistryController mSavedStateRegistryController;
    public ViewModelStore mViewModelStore;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NonConfigurationInstances {
        public Object custom;
        public ViewModelStore viewModelStore;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ReportFullyDrawnExecutorApi16Impl implements Executor, ViewTreeObserver.OnDrawListener, Runnable {
        public final long mEndWatchTimeMillis = SystemClock.uptimeMillis() + 10000;
        public boolean mOnDrawScheduled = false;
        public Runnable mRunnable;

        public ReportFullyDrawnExecutorApi16Impl() {
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.mRunnable = runnable;
            View decorView = ComponentActivity.this.getWindow().getDecorView();
            if (!this.mOnDrawScheduled) {
                decorView.postOnAnimation(new ComponentActivity$$ExternalSyntheticLambda0(this, 1));
            } else if (Looper.myLooper() == Looper.getMainLooper()) {
                decorView.invalidate();
            } else {
                decorView.postInvalidate();
            }
        }

        @Override // android.view.ViewTreeObserver.OnDrawListener
        public final void onDraw() {
            boolean z;
            Runnable runnable = this.mRunnable;
            if (runnable == null) {
                if (SystemClock.uptimeMillis() > this.mEndWatchTimeMillis) {
                    this.mOnDrawScheduled = false;
                    ComponentActivity.this.getWindow().getDecorView().post(this);
                    return;
                }
                return;
            }
            runnable.run();
            this.mRunnable = null;
            FullyDrawnReporter fullyDrawnReporter = ComponentActivity.this.mFullyDrawnReporter;
            synchronized (fullyDrawnReporter.lock) {
                z = fullyDrawnReporter.reportedFullyDrawn;
            }
            if (z) {
                this.mOnDrawScheduled = false;
                ComponentActivity.this.getWindow().getDecorView().post(this);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            ComponentActivity.this.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(this);
        }

        public final void viewCreated(View view) {
            if (this.mOnDrawScheduled) {
                return;
            }
            this.mOnDrawScheduled = true;
            view.getViewTreeObserver().addOnDrawListener(this);
        }
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [androidx.activity.ComponentActivity$2] */
    public ComponentActivity() {
        this.mContextAwareHelper = new ContextAwareHelper();
        this.mMenuHostHelper = new MenuHostHelper(new ComponentActivity$$ExternalSyntheticLambda0(this, 0));
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.mLifecycleRegistry = lifecycleRegistry;
        SavedStateRegistryController.Companion.getClass();
        SavedStateRegistryController savedStateRegistryController = new SavedStateRegistryController(this, null);
        this.mSavedStateRegistryController = savedStateRegistryController;
        this.mOnBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable() { // from class: androidx.activity.ComponentActivity.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ComponentActivity.super.onBackPressed();
                } catch (IllegalStateException e) {
                    if (!TextUtils.equals(e.getMessage(), "Can not perform this action after onSaveInstanceState")) {
                        throw e;
                    }
                }
            }
        });
        ReportFullyDrawnExecutorApi16Impl reportFullyDrawnExecutorApi16Impl = new ReportFullyDrawnExecutorApi16Impl();
        this.mReportFullyDrawnExecutor = reportFullyDrawnExecutorApi16Impl;
        this.mFullyDrawnReporter = new FullyDrawnReporter(reportFullyDrawnExecutorApi16Impl, new Function0() { // from class: androidx.activity.ComponentActivity$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ComponentActivity.this.reportFullyDrawn();
                return null;
            }
        });
        new AtomicInteger();
        this.mActivityResultRegistry = new ActivityResultRegistry(this) { // from class: androidx.activity.ComponentActivity.2
        };
        this.mOnConfigurationChangedListeners = new CopyOnWriteArrayList();
        this.mOnTrimMemoryListeners = new CopyOnWriteArrayList();
        this.mOnNewIntentListeners = new CopyOnWriteArrayList();
        this.mOnMultiWindowModeChangedListeners = new CopyOnWriteArrayList();
        this.mOnPictureInPictureModeChangedListeners = new CopyOnWriteArrayList();
        this.mDispatchingOnMultiWindowModeChanged = false;
        this.mDispatchingOnPictureInPictureModeChanged = false;
        lifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: androidx.activity.ComponentActivity.3
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_STOP) {
                    Window window = ComponentActivity.this.getWindow();
                    View peekDecorView = window != null ? window.peekDecorView() : null;
                    if (peekDecorView != null) {
                        peekDecorView.cancelPendingInputEvents();
                    }
                }
            }
        });
        lifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: androidx.activity.ComponentActivity.4
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    ComponentActivity.this.mContextAwareHelper.mContext = null;
                    if (ComponentActivity.this.isChangingConfigurations()) {
                        return;
                    }
                    ComponentActivity.this.getViewModelStore().clear();
                }
            }
        });
        lifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: androidx.activity.ComponentActivity.5
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                ComponentActivity componentActivity = ComponentActivity.this;
                if (componentActivity.mViewModelStore == null) {
                    NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) componentActivity.getLastNonConfigurationInstance();
                    if (nonConfigurationInstances != null) {
                        componentActivity.mViewModelStore = nonConfigurationInstances.viewModelStore;
                    }
                    if (componentActivity.mViewModelStore == null) {
                        componentActivity.mViewModelStore = new ViewModelStore();
                    }
                }
                componentActivity.mLifecycleRegistry.removeObserver(this);
            }
        });
        savedStateRegistryController.performAttach();
        SavedStateHandleSupport.enableSavedStateHandles(this);
        savedStateRegistryController.savedStateRegistry.registerSavedStateProvider("android:support:activity-result", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.activity.ComponentActivity$$ExternalSyntheticLambda2
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            public final Bundle saveState() {
                ComponentActivity componentActivity = ComponentActivity.this;
                componentActivity.getClass();
                Bundle bundle = new Bundle();
                ComponentActivity.C00142 c00142 = componentActivity.mActivityResultRegistry;
                c00142.getClass();
                HashMap hashMap = (HashMap) c00142.mKeyToRc;
                bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(hashMap.values()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(hashMap.keySet()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(c00142.mLaunchedKeys));
                bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) c00142.mPendingResults.clone());
                bundle.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", c00142.mRandom);
                return bundle;
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: androidx.activity.ComponentActivity$$ExternalSyntheticLambda3
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public final void onContextAvailable() {
                ComponentActivity componentActivity = ComponentActivity.this;
                Bundle consumeRestoredStateForKey = componentActivity.mSavedStateRegistryController.savedStateRegistry.consumeRestoredStateForKey("android:support:activity-result");
                if (consumeRestoredStateForKey != null) {
                    ComponentActivity.C00142 c00142 = componentActivity.mActivityResultRegistry;
                    c00142.getClass();
                    ArrayList<Integer> integerArrayList = consumeRestoredStateForKey.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
                    ArrayList<String> stringArrayList = consumeRestoredStateForKey.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
                    if (stringArrayList == null || integerArrayList == null) {
                        return;
                    }
                    c00142.mLaunchedKeys = consumeRestoredStateForKey.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
                    c00142.mRandom = (Random) consumeRestoredStateForKey.getSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT");
                    Bundle bundle = consumeRestoredStateForKey.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT");
                    Bundle bundle2 = c00142.mPendingResults;
                    bundle2.putAll(bundle);
                    for (int i = 0; i < stringArrayList.size(); i++) {
                        String str = stringArrayList.get(i);
                        Map map = c00142.mKeyToRc;
                        HashMap hashMap = (HashMap) map;
                        boolean containsKey = hashMap.containsKey(str);
                        Map map2 = c00142.mRcToKey;
                        if (containsKey) {
                            Integer num = (Integer) hashMap.remove(str);
                            if (!bundle2.containsKey(str)) {
                                ((HashMap) map2).remove(num);
                            }
                        }
                        int intValue = integerArrayList.get(i).intValue();
                        String str2 = stringArrayList.get(i);
                        ((HashMap) map2).put(Integer.valueOf(intValue), str2);
                        ((HashMap) map).put(str2, Integer.valueOf(intValue));
                    }
                }
            }
        });
    }

    private void initViewTreeOwners() {
        getWindow().getDecorView().setTag(R.id.view_tree_lifecycle_owner, this);
        getWindow().getDecorView().setTag(R.id.view_tree_view_model_store_owner, this);
        getWindow().getDecorView().setTag(R.id.view_tree_saved_state_registry_owner, this);
        getWindow().getDecorView().setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, this);
        getWindow().getDecorView().setTag(R.id.report_drawn, this);
    }

    @Override // android.app.Activity
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        this.mReportFullyDrawnExecutor.viewCreated(getWindow().getDecorView());
        super.addContentView(view, layoutParams);
    }

    @Override // androidx.core.view.MenuHost
    public final void addMenuProvider(FragmentManager.C02322 c02322) {
        MenuHostHelper menuHostHelper = this.mMenuHostHelper;
        menuHostHelper.mMenuProviders.add(c02322);
        menuHostHelper.mOnInvalidateMenuCallback.run();
    }

    @Override // androidx.core.content.OnConfigurationChangedProvider
    public final void addOnConfigurationChangedListener(Consumer consumer) {
        this.mOnConfigurationChangedListeners.add(consumer);
    }

    public final void addOnContextAvailableListener(OnContextAvailableListener onContextAvailableListener) {
        ContextAwareHelper contextAwareHelper = this.mContextAwareHelper;
        if (contextAwareHelper.mContext != null) {
            onContextAvailableListener.onContextAvailable();
        }
        ((CopyOnWriteArraySet) contextAwareHelper.mListeners).add(onContextAvailableListener);
    }

    @Override // androidx.core.app.OnMultiWindowModeChangedProvider
    public final void addOnMultiWindowModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnMultiWindowModeChangedListeners.add(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // androidx.core.app.OnPictureInPictureModeChangedProvider
    public final void addOnPictureInPictureModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnPictureInPictureModeChangedListeners.add(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // androidx.core.content.OnTrimMemoryProvider
    public final void addOnTrimMemoryListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnTrimMemoryListeners.add(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // androidx.activity.result.ActivityResultRegistryOwner
    public final ActivityResultRegistry getActivityResultRegistry() {
        return this.mActivityResultRegistry;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final MutableCreationExtras getDefaultViewModelCreationExtras() {
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras();
        if (getApplication() != null) {
            mutableCreationExtras.set(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY, getApplication());
        }
        mutableCreationExtras.set(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY, this);
        mutableCreationExtras.set(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY, this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mutableCreationExtras.set(SavedStateHandleSupport.DEFAULT_ARGS_KEY, getIntent().getExtras());
        }
        return mutableCreationExtras;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.mDefaultFactory == null) {
            this.mDefaultFactory = new SavedStateViewModelFactory(getApplication(), this, getIntent() != null ? getIntent().getExtras() : null);
        }
        return this.mDefaultFactory;
    }

    @Override // androidx.core.app.ComponentActivity, androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override // androidx.activity.OnBackPressedDispatcherOwner
    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        return this.mOnBackPressedDispatcher;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.savedStateRegistry;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        if (getApplication() == null) {
            throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        }
        if (this.mViewModelStore == null) {
            NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance();
            if (nonConfigurationInstances != null) {
                this.mViewModelStore = nonConfigurationInstances.viewModelStore;
            }
            if (this.mViewModelStore == null) {
                this.mViewModelStore = new ViewModelStore();
            }
        }
        return this.mViewModelStore;
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (dispatchResult(i, i2, intent)) {
            return;
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        this.mOnBackPressedDispatcher.onBackPressed();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Iterator it = this.mOnConfigurationChangedListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(configuration);
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.mSavedStateRegistryController.performRestore(bundle);
        ContextAwareHelper contextAwareHelper = this.mContextAwareHelper;
        contextAwareHelper.mContext = this;
        Iterator it = ((CopyOnWriteArraySet) contextAwareHelper.mListeners).iterator();
        while (it.hasNext()) {
            ((OnContextAvailableListener) it.next()).onContextAvailable();
        }
        super.onCreate(bundle);
        ReportFragment.injectIfNeededIn(this);
        OnBackPressedDispatcher onBackPressedDispatcher = this.mOnBackPressedDispatcher;
        onBackPressedDispatcher.mInvokedDispatcher = getOnBackInvokedDispatcher();
        onBackPressedDispatcher.updateBackInvokedCallbackState();
        int i = this.mContentLayoutId;
        if (i != 0) {
            setContentView(i);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0) {
            return true;
        }
        super.onCreatePanelMenu(i, menu);
        MenuInflater menuInflater = getMenuInflater();
        Iterator it = this.mMenuHostHelper.mMenuProviders.iterator();
        while (it.hasNext()) {
            FragmentManager.this.dispatchCreateOptionsMenu(menu, menuInflater);
        }
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 0) {
            return this.mMenuHostHelper.onMenuItemSelected(menuItem);
        }
        return false;
    }

    @Override // android.app.Activity
    public final void onMultiWindowModeChanged(boolean z) {
        if (this.mDispatchingOnMultiWindowModeChanged) {
            return;
        }
        Iterator it = this.mOnMultiWindowModeChangedListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(new MultiWindowModeChangedInfo(z));
        }
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Iterator it = this.mOnNewIntentListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(intent);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i, Menu menu) {
        Iterator it = this.mMenuHostHelper.mMenuProviders.iterator();
        while (it.hasNext()) {
            FragmentManager.this.dispatchOptionsMenuClosed();
        }
        super.onPanelClosed(i, menu);
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z) {
        if (this.mDispatchingOnPictureInPictureModeChanged) {
            return;
        }
        Iterator it = this.mOnPictureInPictureModeChangedListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(new PictureInPictureModeChangedInfo(z));
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0) {
            return true;
        }
        super.onPreparePanel(i, view, menu);
        this.mMenuHostHelper.onPrepareMenu(menu);
        return true;
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (dispatchResult(i, -1, new Intent().putExtra("androidx.activity.result.contract.extra.PERMISSIONS", strArr).putExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS", iArr))) {
            return;
        }
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    @Override // android.app.Activity
    public final Object onRetainNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances;
        Object onRetainCustomNonConfigurationInstance = onRetainCustomNonConfigurationInstance();
        ViewModelStore viewModelStore = this.mViewModelStore;
        if (viewModelStore == null && (nonConfigurationInstances = (NonConfigurationInstances) getLastNonConfigurationInstance()) != null) {
            viewModelStore = nonConfigurationInstances.viewModelStore;
        }
        if (viewModelStore == null && onRetainCustomNonConfigurationInstance == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances2 = new NonConfigurationInstances();
        nonConfigurationInstances2.custom = onRetainCustomNonConfigurationInstance;
        nonConfigurationInstances2.viewModelStore = viewModelStore;
        return nonConfigurationInstances2;
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
        if (lifecycleRegistry instanceof LifecycleRegistry) {
            lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        }
        super.onSaveInstanceState(bundle);
        this.mSavedStateRegistryController.performSave(bundle);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        super.onTrimMemory(i);
        Iterator it = this.mOnTrimMemoryListeners.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(Integer.valueOf(i));
        }
    }

    @Override // androidx.core.view.MenuHost
    public final void removeMenuProvider(FragmentManager.C02322 c02322) {
        this.mMenuHostHelper.removeMenuProvider(c02322);
    }

    @Override // androidx.core.content.OnConfigurationChangedProvider
    public final void removeOnConfigurationChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnConfigurationChangedListeners.remove(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // androidx.core.app.OnMultiWindowModeChangedProvider
    public final void removeOnMultiWindowModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnMultiWindowModeChangedListeners.remove(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // androidx.core.app.OnPictureInPictureModeChangedProvider
    public final void removeOnPictureInPictureModeChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnPictureInPictureModeChangedListeners.remove(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // androidx.core.content.OnTrimMemoryProvider
    public final void removeOnTrimMemoryListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0) {
        this.mOnTrimMemoryListeners.remove(fragmentManager$$ExternalSyntheticLambda0);
    }

    @Override // android.app.Activity
    public final void reportFullyDrawn() {
        try {
            if (Trace.isEnabled()) {
                Trace.beginSection("reportFullyDrawn() for ComponentActivity");
            }
            super.reportFullyDrawn();
            FullyDrawnReporter fullyDrawnReporter = this.mFullyDrawnReporter;
            synchronized (fullyDrawnReporter.lock) {
                fullyDrawnReporter.reportedFullyDrawn = true;
                Iterator it = ((ArrayList) fullyDrawnReporter.onReportCallbacks).iterator();
                while (it.hasNext()) {
                    ((Function0) it.next()).invoke();
                }
                ((ArrayList) fullyDrawnReporter.onReportCallbacks).clear();
                Unit unit = Unit.INSTANCE;
            }
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        initViewTreeOwners();
        this.mReportFullyDrawnExecutor.viewCreated(getWindow().getDecorView());
        super.setContentView(i);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4) {
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        super.startActivityForResult(intent, i, bundle);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }

    @Override // android.app.Activity
    public void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        this.mDispatchingOnMultiWindowModeChanged = true;
        try {
            super.onMultiWindowModeChanged(z, configuration);
            this.mDispatchingOnMultiWindowModeChanged = false;
            Iterator it = this.mOnMultiWindowModeChangedListeners.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(new MultiWindowModeChangedInfo(z, configuration));
            }
        } catch (Throwable th) {
            this.mDispatchingOnMultiWindowModeChanged = false;
            throw th;
        }
    }

    @Override // android.app.Activity
    public final void onPictureInPictureModeChanged(boolean z, Configuration configuration) {
        this.mDispatchingOnPictureInPictureModeChanged = true;
        try {
            super.onPictureInPictureModeChanged(z, configuration);
            this.mDispatchingOnPictureInPictureModeChanged = false;
            Iterator it = this.mOnPictureInPictureModeChangedListeners.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(new PictureInPictureModeChangedInfo(z, configuration));
            }
        } catch (Throwable th) {
            this.mDispatchingOnPictureInPictureModeChanged = false;
            throw th;
        }
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        initViewTreeOwners();
        this.mReportFullyDrawnExecutor.viewCreated(getWindow().getDecorView());
        super.setContentView(view);
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        this.mReportFullyDrawnExecutor.viewCreated(getWindow().getDecorView());
        super.setContentView(view, layoutParams);
    }

    public ComponentActivity(int i) {
        this();
        this.mContentLayoutId = i;
    }
}
