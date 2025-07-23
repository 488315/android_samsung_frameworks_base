package com.android.systemui.controls.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.management.ControlsAnimations;
import com.android.systemui.controls.management.ControlsAnimations$observerForAnimations$1;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.fragment.ControlsFragmentFactory;
import com.android.systemui.controls.ui.fragment.MainFragment;
import com.android.systemui.controls.ui.fragment.NoAppFragment;
import com.android.systemui.controls.ui.fragment.NoFavoriteFragment;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.util.SpanManager;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.settings.UserTracker;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecControlsActivity extends BaseActivity {
    public final AUIFacade auiFacade;
    public final BroadcastDispatcher broadcastDispatcher;
    public final SecControlsActivity$broadcastReceiver$1 broadcastReceiver;
    public final ControlsFragmentFactory controlsFragmentFactory;
    public final ControlsUtil controlsUtil;
    public final Handler handler;
    public int lastOrientation;
    public ViewGroup parent;
    public final SecControlsUiController secUiController;
    public final ControlsUiController uiController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.ui.SecControlsActivity$broadcastReceiver$1] */
    public SecControlsActivity(Executor executor, ControlsController controlsController, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, ControlsUiController controlsUiController, SecControlsUiController secControlsUiController, ControlsUtil controlsUtil, ControlsFragmentFactory controlsFragmentFactory, AUIFacade aUIFacade, Handler handler) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.broadcastDispatcher = broadcastDispatcher;
        this.uiController = controlsUiController;
        this.secUiController = secControlsUiController;
        this.controlsUtil = controlsUtil;
        this.controlsFragmentFactory = controlsFragmentFactory;
        this.auiFacade = aUIFacade;
        this.handler = handler;
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.ui.SecControlsActivity$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                SecControlsActivity.this.getClass();
                Log.d("SecControlsActivity", "onReceive intent = " + intent);
                SecControlsActivity.this.finish();
            }
        };
    }

    public static void addView(ViewGroup viewGroup, String str, int i, int i2, boolean z) {
        View findViewWithTag = viewGroup.findViewWithTag(str);
        if (findViewWithTag != null) {
            Log.d("SecControlsActivity", str.concat(" is already done"));
        } else {
            findViewWithTag = new View(viewGroup.getContext());
            findViewWithTag.setTag(str);
            findViewWithTag.setBackgroundColor(Color.rgb((i >> 16) & 255, (i >> 8) & 255, i & 255));
            findViewWithTag.setAlpha(z ? ((i >> 24) & 255) / 255.0f : 1.0f);
        }
        viewGroup.addView(findViewWithTag, i2);
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTag() {
        return "SecControlsActivity";
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onBackKeyPressed() {
        Log.d("SecControlsActivity", "onBackKeyPressed");
        finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        Window window;
        super.onConfigurationChanged(configuration);
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.secUiController;
        if (secControlsUiControllerImpl.selectedItem instanceof SelectedItem.PanelItem) {
            MainFragment mainFragment = secControlsUiControllerImpl.mainFragment;
            if (mainFragment != null) {
                mainFragment.onConfigChanged();
            }
            StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl.controlAdapter;
            if (statefulControlAdapter != null) {
                SpanManager spanManager = statefulControlAdapter.spanManager;
                spanManager.updateSpanInfos(0);
                RecyclerView recyclerView = statefulControlAdapter.recyclerView;
                if (recyclerView != null) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(statefulControlAdapter.context, spanManager.maxSpan);
                    gridLayoutManager.mSpanSizeLookup = statefulControlAdapter.spanSizeLookup;
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
            }
            if (this.lastOrientation != configuration.orientation && (window = getWindow()) != null) {
                int i = configuration.smallestScreenWidthDp;
                BaseActivity.Companion.getClass();
                if (i > BaseActivity.BREAKPOINT_RANGE || configuration.orientation != 2) {
                    window.clearFlags(1024);
                } else {
                    window.addFlags(1024);
                }
            }
        } else {
            recreate();
        }
        this.lastOrientation = configuration.orientation;
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        ActionMenuPresenter.OverflowMenuButton overflowMenuButton;
        Log.d("SecControlsActivity", "onCreate");
        getSupportFragmentManager().mFragmentFactory = this.controlsFragmentFactory;
        this.isSkipDisableEdgeToEdge = true;
        this.isSkipUpdateStatusBarColor = true;
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            this.lastOrientation = window.getContext().getResources().getConfiguration().orientation;
            window.addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            window.setBackgroundDrawableResource(R.color.control_activity_base_color);
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            addView(viewGroup, "SolidViewTag", window.getContext().getColor(R.color.control_activity_background_blur_no_blur_model), 0, false);
            addView(viewGroup, "DimViewTag", window.getContext().getColor(R.color.control_activity_dim_color), 1, true);
        }
        ControlsUtil controlsUtil = this.controlsUtil;
        controlsUtil.getClass();
        if (Settings.Secure.getInt(getContentResolver(), "lockscreen_show_controls", 0) != 0) {
            Log.d("SecControlsActivity", "canAccessLockScreenDevice");
            setShowWhenLocked(controlsUtil.isSecureLocked());
        }
        setContentView(R.layout.activity_collapsing_toolbar);
        LifecycleRegistry lifecycleRegistry = this.lifecycleRegistry;
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup2 = (ViewGroup) requireViewById(R.id.activity_root);
        Window window2 = getWindow();
        Intent intent = getIntent();
        controlsAnimations.getClass();
        lifecycleRegistry.addObserver(new ControlsAnimations$observerForAnimations$1(intent, viewGroup2, false, window2));
        Toolbar toolbar = (Toolbar) requireViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.ensureMenu();
        ActionMenuView actionMenuView = toolbar.mMenuView;
        actionMenuView.getMenu();
        ActionMenuPresenter actionMenuPresenter = actionMenuView.mPresenter;
        Drawable drawable = (actionMenuPresenter.mUseTextItemMode || (overflowMenuButton = actionMenuPresenter.mOverflowButton) == null) ? null : ((AppCompatImageView) overflowMenuButton.mInnerView).getDrawable();
        if (drawable != null) {
            drawable.setTint(toolbar.getResources().getColor(R.color.control_more_icon_color));
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_root);
        if (linearLayout != null) {
            linearLayout.setClipToOutline(true);
            linearLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.controls.ui.SecControlsActivity$onCreate$3$1
                @Override // android.view.ViewOutlineProvider
                public final void getOutline(View view, Outline outline) {
                    int dimensionPixelSize = linearLayout.getResources().getDimensionPixelSize(R.dimen.basic_interaction_list_radius);
                    outline.setRoundRect(0, -dimensionPixelSize, view.getMeasuredWidth(), view.getMeasuredHeight(), dimensionPixelSize);
                }
            });
            linearLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.ui.SecControlsActivity$onCreate$3$2
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.navigationBars());
                    if (view.getContext().getResources().getConfiguration().orientation == 2) {
                        view.setPadding(0, 0, insets.right, 0);
                    } else {
                        view.setPadding(0, insets.top, 0, insets.bottom);
                    }
                    return WindowInsets.CONSUMED;
                }
            });
        }
        ((AUIFacadeImpl) this.auiFacade).initialize();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.qs.action.CLOSE_DEVICE_CONTROL");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiverWithHandler$default(this.broadcastDispatcher, this.broadcastReceiver, intentFilter, this.handler, null, "com.android.systemui.qs.permission.CLOSE_DEVICE_CONTROL", 24);
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        Log.d("SecControlsActivity", "onDestroy");
        this.auiFacade.finalize();
        this.broadcastDispatcher.unregisterReceiver(this.broadcastReceiver);
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.secUiController;
        secControlsUiControllerImpl.getClass();
        Log.d("SecControlsUiControllerImpl", "clear");
        ViewGroup viewGroup = secControlsUiControllerImpl.parent;
        if (viewGroup != null) {
            secControlsUiControllerImpl.hide(viewGroup);
        }
        NoAppFragment noAppFragment = secControlsUiControllerImpl.noAppFragment;
        if (noAppFragment != null) {
            noAppFragment.onDestroy();
        }
        secControlsUiControllerImpl.noAppFragment = null;
        NoFavoriteFragment noFavoriteFragment = secControlsUiControllerImpl.noFavoriteFragment;
        if (noFavoriteFragment != null) {
            noFavoriteFragment.onDestroy();
        }
        secControlsUiControllerImpl.noFavoriteFragment = null;
        MainFragment mainFragment = secControlsUiControllerImpl.mainFragment;
        if (mainFragment != null) {
            mainFragment.onDestroy();
        }
        secControlsUiControllerImpl.mainFragment = null;
        secControlsUiControllerImpl.isChanged = false;
        ArrayList arrayList = new ArrayList();
        MainComponentModel mainComponentModel = secControlsUiControllerImpl.componentModel;
        mainComponentModel.controlsSpinnerInfo = arrayList;
        ComponentInfo.Companion.getClass();
        mainComponentModel.selected = ComponentInfo.EMPTY_COMPONENT;
        mainComponentModel.showButton = false;
        ((ArrayList) secControlsUiControllerImpl.models).clear();
        StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl.controlAdapter;
        if (statefulControlAdapter != null) {
            ((LinkedHashMap) StatefulControlAdapter.controlViewHolders).clear();
            ((ControlActionCoordinatorImpl) statefulControlAdapter.secControlActionCoordinator).activityContext = null;
            RecyclerView recyclerView = statefulControlAdapter.recyclerView;
            if (recyclerView != null) {
                recyclerView.removeAllViews();
            }
        }
        secControlsUiControllerImpl.controlAdapter = null;
        secControlsUiControllerImpl.panelPendingIntent = null;
        RenderInfo.Companion.getClass();
        RenderInfo.iconMap.clear();
        RenderInfo.appIconMap.clear();
        SecRenderInfo.Companion.getClass();
        SecRenderInfo.actionIconMap.clear();
        SecRenderInfo.statusIconDrawableMap.clear();
        FragmentManagerImpl fragmentManagerImpl = secControlsUiControllerImpl.fragmentManager;
        Fragment findFragmentById = fragmentManagerImpl != null ? fragmentManagerImpl.findFragmentById(R.id.frame_layout) : null;
        new SALogger.Event.QuitDevices(findFragmentById instanceof NoAppFragment ? SALogger.Screen.IntroNoAppsToShow.INSTANCE : findFragmentById instanceof NoFavoriteFragment ? SALogger.Screen.NoDeviceSelected.INSTANCE : SALogger.Screen.MainScreen.INSTANCE).sendEvent(secControlsUiControllerImpl.saLogger.systemUIAnalyticsWrapper);
        secControlsUiControllerImpl.unsubscribeAndUnbindIfNecessary();
        secControlsUiControllerImpl.verificationStructureInfos.clear();
        secControlsUiControllerImpl.allComponentInfo = EmptyList.INSTANCE;
        ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = ((ControlsBindingControllerImpl) ((ControlsControllerImpl) ((SecControlsController) secControlsUiControllerImpl.secControlsController.get())).secBindingController).loadSubscriber;
        if (loadSubscriber != null) {
            loadSubscriber.loadedControls.clear();
        }
        secControlsUiControllerImpl.fragmentManager = null;
        secControlsUiControllerImpl.activityContext = null;
        secControlsUiControllerImpl.onDismiss = null;
        secControlsUiControllerImpl.panelView = null;
        secControlsUiControllerImpl.parent = null;
        SelectedItem.Companion.getClass();
        secControlsUiControllerImpl.selectedItem = SelectedItem.EMPTY_SELECTION_COMPONENT;
        this.parent = null;
        super.onDestroy();
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onHomeKeyPressed() {
        super.onHomeKeyPressed();
        ((SecControlsUiControllerImpl) this.secUiController).unsubscribeAndUnbindIfNecessary();
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onRecentAppsKeyPressed() {
        super.onRecentAppsKeyPressed();
        ((SecControlsUiControllerImpl) this.secUiController).unsubscribeAndUnbindIfNecessary();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00b6, code lost:
    
        if (com.android.systemui.controls.controller.Favorites.getActiveFlag(r5) == false) goto L19;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x011e  */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.controls.ui.SecControlsActivity$onStart$1$1] */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStart() {
        /*
            Method dump skipped, instructions count: 359
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.SecControlsActivity.onStart():void");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        Log.d("SecControlsActivity", "onStop");
        super.onStop();
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            ((SecControlsUiControllerImpl) this.uiController).hide(viewGroup);
        }
    }
}
