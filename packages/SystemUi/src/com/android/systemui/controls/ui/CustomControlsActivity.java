package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.ui.fragment.ControlsFragmentFactory;
import com.android.systemui.controls.ui.fragment.MainFragment;
import com.android.systemui.controls.ui.fragment.NoAppFragment;
import com.android.systemui.controls.ui.fragment.NoFavoriteFragment;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.util.BlurFacade;
import com.android.systemui.controls.ui.util.BlurFacadeImpl;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.util.ControlsRuneWrapperImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlsActivity extends BaseActivity {
    public final String TAG;
    public final AUIFacade auiFacade;
    public final BlurFacade blurFacade;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsFragmentFactory controlsFragmentFactory;
    public final ControlsUtil controlsUtil;
    public final CustomControlsUiController customUiController;
    public ViewGroup parent;
    public final ControlsUiController uiController;

    public CustomControlsActivity(Executor executor, ControlsController controlsController, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, ControlsUiController controlsUiController, CustomControlsUiController customControlsUiController, ControlsUtil controlsUtil, ControlsFragmentFactory controlsFragmentFactory, BlurFacade blurFacade, AUIFacade aUIFacade) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.broadcastDispatcher = broadcastDispatcher;
        this.uiController = controlsUiController;
        this.customUiController = customControlsUiController;
        this.controlsUtil = controlsUtil;
        this.controlsFragmentFactory = controlsFragmentFactory;
        this.blurFacade = blurFacade;
        this.auiFacade = aUIFacade;
        this.TAG = "CustomControlsActivity";
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTAG() {
        return this.TAG;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        Log.d(this.TAG, "onBackPressed");
        finish();
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        ActionMenuPresenter.OverflowMenuButton overflowMenuButton;
        String str = this.TAG;
        Log.d(str, "onCreate");
        getSupportFragmentManager().mFragmentFactory = this.controlsFragmentFactory;
        super.onCreate(bundle);
        ControlsUtil controlsUtil = this.controlsUtil;
        controlsUtil.getClass();
        if (Settings.Secure.getInt(getContentResolver(), "lockscreen_show_controls", 0) != 0) {
            Log.d(str, "canAccessLockScreenDevice");
            setShowWhenLocked(controlsUtil.isSecureLocked());
        }
        setContentView(R.layout.activity_collapsing_toolbar);
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
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_root);
        coordinatorLayout.setClipToOutline(true);
        coordinatorLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.systemui.controls.ui.CustomControlsActivity$onCreate$2$1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                int dimensionPixelSize = CoordinatorLayout.this.getResources().getDimensionPixelSize(R.dimen.basic_interaction_list_radius);
                outline.setRoundRect(0, -dimensionPixelSize, view.getMeasuredWidth(), view.getMeasuredHeight(), dimensionPixelSize);
            }
        });
        ((AppBarLayout) findViewById(R.id.app_bar)).setExpanded(false, false, true);
        if (BasicRune.CONTROLS_AUI) {
            ((AUIFacadeImpl) this.auiFacade).initialize();
        }
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        Log.d(this.TAG, "onDestroy");
        if (BasicRune.CONTROLS_AUI) {
            ((AUIFacadeImpl) this.auiFacade).finalize();
        }
        CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) this.customUiController;
        customControlsUiControllerImpl.getClass();
        Log.d("CustomControlsUiControllerImpl", "clear");
        ViewGroup viewGroup = customControlsUiControllerImpl.parent;
        if (viewGroup != null) {
            customControlsUiControllerImpl.hide(viewGroup);
        }
        NoAppFragment noAppFragment = customControlsUiControllerImpl.noAppFragment;
        if (noAppFragment != null) {
            noAppFragment.onDestroy();
        }
        customControlsUiControllerImpl.noAppFragment = null;
        NoFavoriteFragment noFavoriteFragment = customControlsUiControllerImpl.noFavoriteFragment;
        if (noFavoriteFragment != null) {
            noFavoriteFragment.onDestroy();
        }
        customControlsUiControllerImpl.noFavoriteFragment = null;
        MainFragment mainFragment = customControlsUiControllerImpl.mainFragment;
        if (mainFragment != null) {
            mainFragment.onDestroy();
        }
        customControlsUiControllerImpl.mainFragment = null;
        customControlsUiControllerImpl.isChanged = false;
        ArrayList arrayList = new ArrayList();
        MainComponentModel mainComponentModel = customControlsUiControllerImpl.componentModel;
        mainComponentModel.controlsSpinnerInfo = arrayList;
        ComponentInfo.Companion.getClass();
        mainComponentModel.selected = ComponentInfo.EMPTY_COMPONENT;
        mainComponentModel.showButton = false;
        ((ArrayList) customControlsUiControllerImpl.models).clear();
        MainControlAdapter mainControlAdapter = customControlsUiControllerImpl.controlAdapter;
        if (mainControlAdapter != null) {
            ((LinkedHashMap) MainControlAdapter.controlViewHolders).clear();
            ((ControlActionCoordinatorImpl) mainControlAdapter.customControlActionCoordinator).activityContext = null;
        }
        customControlsUiControllerImpl.controlAdapter = null;
        RenderInfo.Companion.getClass();
        RenderInfo.iconMap.clear();
        RenderInfo.appIconMap.clear();
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            CustomRenderInfo.Companion.getClass();
            if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                CustomRenderInfo.actionIconMap.clear();
            }
            if (BasicRune.CONTROLS_CUSTOM_STATUS) {
                CustomRenderInfo.statusIconDrawableMap.clear();
            }
        }
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            FragmentManager fragmentManager = customControlsUiControllerImpl.fragmentManager;
            Fragment findFragmentById = fragmentManager != null ? fragmentManager.findFragmentById(R.id.frame_layout) : null;
            customControlsUiControllerImpl.saLogger.sendEvent(new SALogger.Event.QuitDevices(findFragmentById instanceof NoAppFragment ? SALogger.Screen.IntroNoAppsToShow.INSTANCE : findFragmentById instanceof NoFavoriteFragment ? SALogger.Screen.NoDeviceSelected.INSTANCE : SALogger.Screen.MainScreen.INSTANCE));
        }
        if (BasicRune.CONTROLS_SMARTTHINGS_UNBIND) {
            customControlsUiControllerImpl.unsubscribeAndUnbindIfNecessary();
        }
        customControlsUiControllerImpl.verificationStructureInfos.clear();
        customControlsUiControllerImpl.allComponentInfo = EmptyList.INSTANCE;
        ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = ((ControlsBindingControllerImpl) ((ControlsControllerImpl) ((CustomControlsController) customControlsUiControllerImpl.customControlsController.get())).customBindingController).loadSubscriber;
        if (loadSubscriber != null) {
            loadSubscriber.loadedControls.clear();
        }
        customControlsUiControllerImpl.fragmentManager = null;
        customControlsUiControllerImpl.activityContext = null;
        customControlsUiControllerImpl.onDismiss = null;
        customControlsUiControllerImpl.parent = null;
        SelectedItem.Companion.getClass();
        customControlsUiControllerImpl.selectedItem = SelectedItem.EMPTY_SELECTION_COMPONENT;
        this.parent = null;
        super.onDestroy();
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onHomeKeyPressed() {
        super.onHomeKeyPressed();
        if (BasicRune.CONTROLS_SMARTTHINGS_UNBIND) {
            ((CustomControlsUiControllerImpl) this.customUiController).unsubscribeAndUnbindIfNecessary();
        }
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onRecentAppsKeyPressed() {
        super.onRecentAppsKeyPressed();
        if (BasicRune.CONTROLS_SMARTTHINGS_UNBIND) {
            ((CustomControlsUiControllerImpl) this.customUiController).unsubscribeAndUnbindIfNecessary();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        ControlsListingController.ControlsListingCallback controlsListingCallback;
        Log.d(this.TAG, "onStart");
        super.onStart();
        Window window = getWindow();
        BlurFacadeImpl blurFacadeImpl = (BlurFacadeImpl) this.blurFacade;
        blurFacadeImpl.getClass();
        QSColorCurve qSColorCurve = new QSColorCurve(this);
        qSColorCurve.setFraction(1.0f);
        boolean z = true;
        if (blurFacadeImpl.settingsHelper.isReduceTransparencyEnabled()) {
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            blurFacadeImpl.removeCustomBackgroundView(viewGroup);
            BlurFacadeImpl.addView(viewGroup, "SolidColorViewTag", getColor(R.color.open_theme_qp_bg_color), 0);
        } else {
            ((ControlsRuneWrapperImpl) blurFacadeImpl.controlsRuneWrapper).getClass();
            if (BasicRune.CONTROLS_BLUR) {
                SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
                builder.setRadius((int) qSColorCurve.radius).setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY);
                SemBlurInfo build = builder.build();
                if (!CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
                    window.getAttributes().flags = blurFacadeImpl.multiWindowManager.getMode() == 2 ? window.getAttributes().flags | QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING : window.getAttributes().flags & (-1048577);
                }
                window.getDecorView().getRootView().semSetBlurInfo(build);
                ViewGroup viewGroup2 = (ViewGroup) window.getDecorView();
                blurFacadeImpl.removeCustomBackgroundView(viewGroup2);
                if ((Intrinsics.areEqual(Integer.toHexString(getColor(R.color.open_theme_qp_bg_color)), "ff5d5d5d") || ((getResources().getConfiguration().uiMode & 32) != 0)) ? false : true) {
                    BlurFacadeImpl.addView(viewGroup2, "SolidColorViewTag", getColor(R.color.open_theme_qp_bg_color), 0);
                    BlurFacadeImpl.addView(viewGroup2, "DimViewTag", Color.argb(13, 0, 0, 0), 1);
                }
            } else if (BasicRune.CONTROLS_CAPTURED_BLUR) {
                blurFacadeImpl.removeCustomBackgroundView((ViewGroup) window.getDecorView());
                Bitmap m121xe4a62413 = blurFacadeImpl.m121xe4a62413(this);
                if (m121xe4a62413 != null) {
                    ViewGroup viewGroup3 = (ViewGroup) window.getDecorView();
                    SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
                    semGfxImageFilter.setProportionalSaturation(0.0f);
                    semGfxImageFilter.setBlurRadius(qSColorCurve.radius);
                    semGfxImageFilter.setCurveLevel(qSColorCurve.curve);
                    semGfxImageFilter.setCurveMinX(qSColorCurve.minX);
                    semGfxImageFilter.setCurveMaxX(qSColorCurve.maxX);
                    semGfxImageFilter.setCurveMinY(qSColorCurve.minY);
                    semGfxImageFilter.setCurveMaxY(qSColorCurve.maxY);
                    Bitmap applyToBitmap = semGfxImageFilter.applyToBitmap(m121xe4a62413);
                    View findViewWithTag = viewGroup3.findViewWithTag("BlurViewTag");
                    if (findViewWithTag != null) {
                        Log.d("BlurFacadeImpl", "blurView is already done");
                    } else {
                        findViewWithTag = new View(this);
                        findViewWithTag.setTag("BlurViewTag");
                        findViewWithTag.setBackground(new BitmapDrawable(getResources(), applyToBitmap));
                    }
                    viewGroup3.addView(findViewWithTag, 0);
                    Log.d("BlurFacadeImpl", "apply captured blur for controls ");
                } else {
                    Log.d("BlurFacadeImpl", "apply captured blur for controls (capture failed)");
                }
            } else {
                window.setBackgroundDrawableResource(R.color.control_activity_background_blur_no_blur_model);
            }
        }
        window.setNavigationBarColor(0);
        window.setStatusBarColor(0);
        ViewGroup viewGroup4 = (ViewGroup) requireViewById(R.id.frame_layout);
        this.parent = viewGroup4;
        if (viewGroup4 != null) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsActivity$onStart$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomControlsActivity.this.finish();
                }
            };
            Context context = viewGroup4.getContext();
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            final CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) this.customUiController;
            customControlsUiControllerImpl.getClass();
            Log.d("CustomControlsUiControllerImpl", "show()");
            Trace.instant(4096L, "CustomControlsUiControllerImpl#show");
            customControlsUiControllerImpl.parent = viewGroup4;
            customControlsUiControllerImpl.onDismiss = runnable;
            customControlsUiControllerImpl.activityContext = context;
            customControlsUiControllerImpl.fragmentManager = supportFragmentManager;
            customControlsUiControllerImpl.hidden = false;
            ((ControlActionCoordinatorImpl) customControlsUiControllerImpl.controlActionCoordinator).activityContext = context;
            customControlsUiControllerImpl.loadComponentInfo();
            ControlsController controlsController = (ControlsController) customControlsUiControllerImpl.controlsController.get();
            final CustomControlsUiControllerImpl$show$1 customControlsUiControllerImpl$show$1 = new Consumer() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$show$1
                @Override // java.util.function.Consumer
                public final /* bridge */ /* synthetic */ void accept(Object obj) {
                }
            };
            final ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) controlsController;
            if (controlsControllerImpl.seedingInProgress) {
                ((ExecutorImpl) controlsControllerImpl.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$addSeedingFavoritesCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ControlsControllerImpl controlsControllerImpl2 = ControlsControllerImpl.this;
                        if (!controlsControllerImpl2.seedingInProgress) {
                            customControlsUiControllerImpl$show$1.accept(Boolean.FALSE);
                            return;
                        }
                        ((ArrayList) controlsControllerImpl2.seedingCallbacks).add(customControlsUiControllerImpl$show$1);
                    }
                });
            } else {
                z = false;
            }
            if (z) {
                final CustomControlsUiControllerImpl$show$2 customControlsUiControllerImpl$show$2 = new CustomControlsUiControllerImpl$show$2(customControlsUiControllerImpl);
                controlsListingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$createCallback$1
                    @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
                    public final void onServicesUpdated(final List list) {
                        final CustomControlsUiControllerImpl customControlsUiControllerImpl2 = CustomControlsUiControllerImpl.this;
                        Set<String> stringSet = ((AuthorizedPanelsRepositoryImpl) customControlsUiControllerImpl2.authorizedPanelsRepository).instantiateSharedPrefs().getStringSet("authorized_panels", EmptySet.INSTANCE);
                        Intrinsics.checkNotNull(stringSet);
                        final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = ((ArrayList) list).iterator();
                        while (it.hasNext()) {
                            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                            int i = controlsServiceInfo.serviceInfo.applicationInfo.uid;
                            CharSequence loadLabel = controlsServiceInfo.loadLabel();
                            Drawable loadIcon = controlsServiceInfo.loadIcon();
                            ComponentName componentName = controlsServiceInfo.componentName;
                            arrayList.add(new ControlsSelectionItem(loadLabel, loadIcon, componentName, i, stringSet.contains(componentName.getPackageName()) ? controlsServiceInfo.panelActivity : null));
                        }
                        final Function2 function2 = customControlsUiControllerImpl$show$2;
                        ((ExecutorImpl) customControlsUiControllerImpl2.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$createCallback$1$onServicesUpdated$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Function2 function22 = Function2.this;
                                List list2 = arrayList;
                                CustomControlsUiControllerImpl customControlsUiControllerImpl3 = customControlsUiControllerImpl2;
                                ControlsUtil controlsUtil = customControlsUiControllerImpl3.controlsUtil;
                                List list3 = list;
                                controlsUtil.getClass();
                                function22.invoke(list2, ControlsUtil.getListOfServices(customControlsUiControllerImpl3.context, list3));
                            }
                        });
                    }
                };
            } else if (customControlsUiControllerImpl.needToShowNonMainView()) {
                final CustomControlsUiControllerImpl$show$3 customControlsUiControllerImpl$show$3 = new CustomControlsUiControllerImpl$show$3(customControlsUiControllerImpl);
                controlsListingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$createCallback$1
                    @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
                    public final void onServicesUpdated(final List<? extends ControlsServiceInfo> list) {
                        final CustomControlsUiControllerImpl customControlsUiControllerImpl2 = CustomControlsUiControllerImpl.this;
                        Set<String> stringSet = ((AuthorizedPanelsRepositoryImpl) customControlsUiControllerImpl2.authorizedPanelsRepository).instantiateSharedPrefs().getStringSet("authorized_panels", EmptySet.INSTANCE);
                        Intrinsics.checkNotNull(stringSet);
                        final List<ControlsSelectionItem> arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = ((ArrayList) list).iterator();
                        while (it.hasNext()) {
                            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                            int i = controlsServiceInfo.serviceInfo.applicationInfo.uid;
                            CharSequence loadLabel = controlsServiceInfo.loadLabel();
                            Drawable loadIcon = controlsServiceInfo.loadIcon();
                            ComponentName componentName = controlsServiceInfo.componentName;
                            arrayList.add(new ControlsSelectionItem(loadLabel, loadIcon, componentName, i, stringSet.contains(componentName.getPackageName()) ? controlsServiceInfo.panelActivity : null));
                        }
                        final Function2 function2 = customControlsUiControllerImpl$show$3;
                        ((ExecutorImpl) customControlsUiControllerImpl2.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$createCallback$1$onServicesUpdated$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Function2 function22 = Function2.this;
                                List list2 = arrayList;
                                CustomControlsUiControllerImpl customControlsUiControllerImpl3 = customControlsUiControllerImpl2;
                                ControlsUtil controlsUtil = customControlsUiControllerImpl3.controlsUtil;
                                List list3 = list;
                                controlsUtil.getClass();
                                function22.invoke(list2, ControlsUtil.getListOfServices(customControlsUiControllerImpl3.context, list3));
                            }
                        });
                    }
                };
            } else {
                final CustomControlsUiControllerImpl$show$4 customControlsUiControllerImpl$show$4 = new CustomControlsUiControllerImpl$show$4(customControlsUiControllerImpl);
                controlsListingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$createCallback$1
                    @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
                    public final void onServicesUpdated(final List<? extends ControlsServiceInfo> list) {
                        final CustomControlsUiControllerImpl customControlsUiControllerImpl2 = CustomControlsUiControllerImpl.this;
                        Set<String> stringSet = ((AuthorizedPanelsRepositoryImpl) customControlsUiControllerImpl2.authorizedPanelsRepository).instantiateSharedPrefs().getStringSet("authorized_panels", EmptySet.INSTANCE);
                        Intrinsics.checkNotNull(stringSet);
                        final List<ControlsSelectionItem> arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = ((ArrayList) list).iterator();
                        while (it.hasNext()) {
                            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                            int i = controlsServiceInfo.serviceInfo.applicationInfo.uid;
                            CharSequence loadLabel = controlsServiceInfo.loadLabel();
                            Drawable loadIcon = controlsServiceInfo.loadIcon();
                            ComponentName componentName = controlsServiceInfo.componentName;
                            arrayList.add(new ControlsSelectionItem(loadLabel, loadIcon, componentName, i, stringSet.contains(componentName.getPackageName()) ? controlsServiceInfo.panelActivity : null));
                        }
                        final Function2 function2 = customControlsUiControllerImpl$show$4;
                        ((ExecutorImpl) customControlsUiControllerImpl2.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$createCallback$1$onServicesUpdated$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Function2 function22 = Function2.this;
                                List list2 = arrayList;
                                CustomControlsUiControllerImpl customControlsUiControllerImpl3 = customControlsUiControllerImpl2;
                                ControlsUtil controlsUtil = customControlsUiControllerImpl3.controlsUtil;
                                List list3 = list;
                                controlsUtil.getClass();
                                function22.invoke(list2, ControlsUtil.getListOfServices(customControlsUiControllerImpl3.context, list3));
                            }
                        });
                    }
                };
            }
            customControlsUiControllerImpl.listingCallback = controlsListingCallback;
            ControlsListingController controlsListingController = (ControlsListingController) customControlsUiControllerImpl.controlsListingController.get();
            ControlsListingController.ControlsListingCallback controlsListingCallback2 = customControlsUiControllerImpl.listingCallback;
            if (controlsListingCallback2 == null) {
                controlsListingCallback2 = null;
            }
            ((ControlsListingControllerImpl) controlsListingController).addCallback(controlsListingCallback2);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        Log.d(this.TAG, "onStop");
        super.onStop();
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            ((CustomControlsUiControllerImpl) this.uiController).hide(viewGroup);
        }
    }
}
