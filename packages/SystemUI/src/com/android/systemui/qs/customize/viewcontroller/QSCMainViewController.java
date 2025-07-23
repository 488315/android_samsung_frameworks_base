package com.android.systemui.qs.customize.viewcontroller;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.customize.QSBlurPopUpMenu;
import com.android.systemui.qs.customize.QSCPopupButtonController;
import com.android.systemui.qs.customize.QSCustomizerWindowHelper;
import com.android.systemui.qs.customize.SecQSCustomizerAnimator;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.QSCMainView;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.PanelPopOverManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSCMainViewController extends ViewControllerBase {
    public final String TAG;
    public final QSCMainViewController$accessibilityDelegate$1 accessibilityDelegate;
    public final BarOrderInteractor barOrderInteractor;
    public Runnable cloneBarRunnable;
    public final ColoredBGHelper coloredBGHelper;
    public final QSCustomizerWindowHelper customizerWindowHelper;
    public ViewControllerType doShowingInRotation;
    public final SecQSSettingEditResources editResources;
    public final Lazy largeScreenHeaderHelperLazy;
    public int mCurrentOrientation;
    public QSPanelHost panelHost;
    public final QSCPopupButtonController popupButtonController;
    public final FrameLayout primeContainer;
    public final SecQSPanelResourcePicker resourcePicker;
    private final SettingsHelper settingsHelper;
    public final FrameLayout subContainer;
    public QsTransitionAnimator transitionAnimator;
    public final kotlin.Lazy viewControllerRepo$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ViewControllerType.values().length];
            try {
                iArr[ViewControllerType.TileEdit.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ViewControllerType.LayoutEdit.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ViewControllerType.Setting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$accessibilityDelegate$1] */
    public QSCMainViewController(QSCMainView qSCMainView, Context context, SecQSSettingEditResources secQSSettingEditResources, SecQSPanelResourcePicker secQSPanelResourcePicker, BarOrderInteractor barOrderInteractor, QSCPopupButtonController qSCPopupButtonController, SettingsHelper settingsHelper, ColoredBGHelper coloredBGHelper, PanelPopOverManager panelPopOverManager, Lazy lazy) {
        super(qSCMainView);
        this.editResources = secQSSettingEditResources;
        this.resourcePicker = secQSPanelResourcePicker;
        this.barOrderInteractor = barOrderInteractor;
        this.popupButtonController = qSCPopupButtonController;
        this.settingsHelper = settingsHelper;
        this.coloredBGHelper = coloredBGHelper;
        this.largeScreenHeaderHelperLazy = lazy;
        this.TAG = "QSCutomizerMainViewCon";
        this.viewControllerRepo$delegate = LazyKt__LazyJVMKt.lazy(new QSCMainViewController$$ExternalSyntheticLambda0());
        this.primeContainer = (FrameLayout) ((QSCMainView) this.mView).requireViewById(R.id.prime_container);
        this.subContainer = (FrameLayout) ((QSCMainView) this.mView).requireViewById(R.id.sub_container);
        this.mCurrentOrientation = context.getResources().getConfiguration().orientation;
        this.customizerWindowHelper = new QSCustomizerWindowHelper(context);
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$accessibilityDelegate$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.mInfo.setSelected(false);
                accessibilityNodeInfoCompat.setContentDescription(QSCMainViewController.this.getContext().getString(R.string.editscreen_close_button) + ", Button");
            }
        };
    }

    public static final void access$showView(final QSCMainViewController qSCMainViewController, ViewControllerType viewControllerType) {
        ViewControllerBase qSLayoutEditViewController;
        if (qSCMainViewController.doShowingInRotation == null && qSCMainViewController.isShown) {
            boolean z = viewControllerType == qSCMainViewController.getViewControllerRepo().MAIN_TYPE;
            FrameLayout frameLayout = z ? qSCMainViewController.primeContainer : qSCMainViewController.subContainer;
            boolean z2 = z && frameLayout.getChildCount() > 0;
            if (z) {
                qSCMainViewController.subContainer.removeAllViews();
            }
            String str = qSCMainViewController.TAG;
            Log.d(str, "setCurrentType: " + viewControllerType);
            ViewControllerRepository viewControllerRepo = qSCMainViewController.getViewControllerRepo();
            ViewControllerType viewControllerType2 = viewControllerRepo.MAIN_TYPE;
            if (viewControllerType == viewControllerType2) {
                viewControllerType2 = ViewControllerType.None;
            } else if (viewControllerType != ViewControllerType.Setting) {
                viewControllerType2 = viewControllerRepo.currentType;
            }
            viewControllerRepo.prevType = viewControllerType2;
            viewControllerRepo.currentType = viewControllerType;
            ViewControllerBase currentViewController = qSCMainViewController.getCurrentViewController();
            SecQSSettingEditResources secQSSettingEditResources = qSCMainViewController.editResources;
            if (currentViewController == null) {
                Log.d(str, "createCurrentViewController()");
                int i = WhenMappings.$EnumSwitchMapping$0[qSCMainViewController.getViewControllerRepo().currentType.ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        Runnable runnable = qSCMainViewController.cloneBarRunnable;
                        if (runnable != null) {
                            runnable.run();
                        }
                        Context context = qSCMainViewController.getContext();
                        ArrayList arrayList = new ArrayList();
                        QSPanelHost qSPanelHost = qSCMainViewController.panelHost;
                        if (qSPanelHost == null) {
                            qSPanelHost = null;
                        }
                        arrayList.addAll(qSPanelHost.getBarItems());
                        Unit unit = Unit.INSTANCE;
                        qSLayoutEditViewController = new QSLayoutEditViewController(context, qSCMainViewController.barOrderInteractor, qSCMainViewController.coloredBGHelper, arrayList, qSCMainViewController.editResources, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                QSCMainViewController qSCMainViewController2 = QSCMainViewController.this;
                                qSCMainViewController2.editResources.isCurrentTopEdit = false;
                                qSCMainViewController2.showViewWithFixRotation(ViewControllerType.TileEdit);
                            }
                        }, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                QSCMainViewController.this.showViewWithFixRotation(ViewControllerType.Setting);
                            }
                        }, qSCMainViewController.getCutoutTopMargin());
                    } else if (i != 3) {
                        currentViewController = null;
                    } else {
                        qSLayoutEditViewController = new QSSettingViewController(qSCMainViewController.getContext(), qSCMainViewController.editResources, qSCMainViewController.popupButtonController, qSCMainViewController.accessibilityDelegate, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$4
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                QSCMainViewController.this.backKeyEvent();
                            }
                        }, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$5
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                QSCMainViewController qSCMainViewController2 = QSCMainViewController.this;
                                qSCMainViewController2.editResources.isCurrentTopEdit = true;
                                qSCMainViewController2.showViewWithFixRotation(ViewControllerType.TileEdit);
                            }
                        });
                    }
                    currentViewController = qSLayoutEditViewController;
                } else {
                    currentViewController = new QSTileCustomizerController(qSCMainViewController.getContext(), secQSSettingEditResources, QpRune.QUICK_POP_OVER_CUSTOMIZER && isLargeScreen$6(), qSCMainViewController.getCutoutTopMargin());
                }
                if (currentViewController != null) {
                    currentViewController.init();
                }
            }
            ViewControllerRepository viewControllerRepo2 = qSCMainViewController.getViewControllerRepo();
            viewControllerRepo2.viewControllers[viewControllerRepo2.currentType.ordinal()] = currentViewController;
            ViewControllerBase currentViewController2 = qSCMainViewController.getCurrentViewController();
            if (currentViewController2 != null) {
                if (z2 || currentViewController2.view.getParent() != null) {
                    View view = currentViewController2.view;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                } else {
                    frameLayout.addView(currentViewController2.view);
                    currentViewController2.show(new QSCMainViewController$showView$1$1(qSCMainViewController));
                }
                if (qSCMainViewController.getViewControllerRepo().prevType != qSCMainViewController.getViewControllerRepo().MAIN_TYPE) {
                    ViewControllerRepository viewControllerRepo3 = qSCMainViewController.getViewControllerRepo();
                    ViewControllerType viewControllerType3 = viewControllerRepo3.prevType;
                    ViewControllerType viewControllerType4 = ViewControllerType.None;
                    ViewControllerBase viewControllerBase = (viewControllerType3 == viewControllerType4 || viewControllerType3 == viewControllerType4) ? null : viewControllerRepo3.viewControllers[viewControllerType3.ordinal()];
                    View view2 = viewControllerBase != null ? viewControllerBase.view : null;
                    if (view2 != null) {
                        view2.setVisibility(8);
                    }
                }
            }
            qSCMainViewController.primeContainer.setVisibility(z ? 0 : 8);
            qSCMainViewController.subContainer.setVisibility(z ? 8 : 0);
            boolean z3 = qSCMainViewController.getViewControllerRepo().currentType == ViewControllerType.TileEdit;
            secQSSettingEditResources.getClass();
            boolean z4 = SecQSSettingEditResources.isBarPhone() && z3;
            int color = z4 ? qSCMainViewController.getContext().getColor(R.color.qs_edit_panel_available_background_color) : 0;
            ((QSCMainView) qSCMainViewController.mView).requireViewById(R.id.navigation_bar_view).setAlpha(1.0f);
            ((QSCMainView) qSCMainViewController.mView).requireViewById(R.id.navigation_bar_view).setBackgroundColor(color);
            if (SecQSSettingEditResources.isBarPhone() && Settings.System.getInt(qSCMainViewController.getContext().getContentResolver(), SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY, 0) == 0) {
                ((QSCMainView) qSCMainViewController.mView).requireViewById(R.id.navigation_bar_view).semSetBlurInfo(z4 ? new SemBlurInfo.Builder(0).setColorCurvePreset(14).setRadius(200).build() : null);
            }
        }
    }

    public static boolean isLargeScreen$6() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public final void backKeyEvent() {
        View view;
        ViewControllerType viewControllerType = getViewControllerRepo().prevType;
        ViewControllerType viewControllerType2 = ViewControllerType.None;
        if (viewControllerType == viewControllerType2) {
            close();
            return;
        }
        ViewControllerType viewControllerType3 = getViewControllerRepo().currentType;
        ViewControllerBase currentViewController = getCurrentViewController();
        if (currentViewController != null) {
            currentViewController.close();
        }
        Integer num = currentViewController != null ? currentViewController.message : null;
        ViewControllerRepository viewControllerRepo = getViewControllerRepo();
        ViewControllerType viewControllerType4 = viewControllerRepo.prevType;
        ViewControllerBase viewControllerBase = (viewControllerType4 == viewControllerType2 || viewControllerType4 == viewControllerType2) ? null : viewControllerRepo.viewControllers[viewControllerType4.ordinal()];
        if (viewControllerBase != null) {
            viewControllerBase.resolveMessage(num);
        }
        ViewControllerBase currentViewController2 = getCurrentViewController();
        if (currentViewController2 != null && (view = currentViewController2.view) != null) {
            this.subContainer.removeView(view);
        }
        showViewWithFixRotation(getViewControllerRepo().prevType);
        getViewControllerRepo().viewControllers[viewControllerType3.ordinal()] = null;
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void close() {
        if (this.isShown) {
            QsTransitionAnimator qsTransitionAnimator = this.transitionAnimator;
            if (qsTransitionAnimator == null) {
                qsTransitionAnimator = null;
            }
            if (!qsTransitionAnimator.isThereNoView() && qsTransitionAnimator.mAnimatorsInitialiezed && qsTransitionAnimator.animStateCallback != null) {
                SecQSImplAnimatorManager.AnonymousClass2.setCustomizerShowing(false);
            }
            QsTransitionAnimator qsTransitionAnimator2 = this.transitionAnimator;
            if (qsTransitionAnimator2 == null) {
                qsTransitionAnimator2 = null;
            }
            qsTransitionAnimator2.showQsPanelForCustomizer(true);
            Log.d(this.TAG, "close()");
            super.close();
            ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).mQsEditMode = false;
            ViewControllerBase currentViewController = getCurrentViewController();
            if (currentViewController != null) {
                currentViewController.close();
            }
            QSBlurPopUpMenu qSBlurPopUpMenu = this.popupButtonController.qsBlurPopUpMenu;
            if (qSBlurPopUpMenu != null) {
                qSBlurPopUpMenu.dismiss();
            }
            this.primeContainer.removeAllViews();
            this.subContainer.removeAllViews();
            ViewControllerBase[] viewControllerBaseArr = getViewControllerRepo().viewControllers;
            int length = viewControllerBaseArr.length;
            for (int i = 0; i < length; i++) {
                viewControllerBaseArr[i] = null;
            }
            SecQSSettingEditResources secQSSettingEditResources = this.editResources;
            secQSSettingEditResources.tileFullAdapter = null;
            secQSSettingEditResources.tileTopAdapter = null;
            QSCustomizerWindowHelper qSCustomizerWindowHelper = this.customizerWindowHelper;
            View view = qSCustomizerWindowHelper.windowRootView;
            if (view != null) {
                WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
                if (windowManager != null) {
                    windowManager.removeViewImmediate(view);
                }
                qSCustomizerWindowHelper.windowRootView = null;
            }
            ((QSCMainView) this.mView).setVisibility(8);
            this.cloneBarRunnable = null;
            SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
        }
    }

    public final ViewControllerBase getCurrentViewController() {
        ViewControllerRepository viewControllerRepo = getViewControllerRepo();
        ViewControllerType viewControllerType = viewControllerRepo.currentType;
        ViewControllerType viewControllerType2 = ViewControllerType.None;
        if (viewControllerType == viewControllerType2 || viewControllerType == viewControllerType2) {
            return null;
        }
        return viewControllerRepo.viewControllers[viewControllerType.ordinal()];
    }

    public final int getCutoutTopMargin() {
        int i;
        LargeScreenHeaderHelper largeScreenHeaderHelper = (LargeScreenHeaderHelper) this.largeScreenHeaderHelperLazy.get();
        int largeScreenHeaderHeight = largeScreenHeaderHelper.getLargeScreenHeaderHeight() + largeScreenHeaderHelper.getTopMargin(((QSCMainView) this.mView).getRootWindowInsets());
        if (DeviceState.isShowingPopOverStatusBar()) {
            i = largeScreenHeaderHelper.qsPanelResourcePicker.resourcePickHelper.getTargetPicker().getPopOverBlankSpace(largeScreenHeaderHelper.context);
        } else {
            i = 0;
        }
        return i + largeScreenHeaderHeight;
    }

    public final ViewControllerRepository getViewControllerRepo() {
        return (ViewControllerRepository) this.viewControllerRepo$delegate.getValue();
    }

    public final void initResources() {
        View requireViewById = ((QSCMainView) this.mView).requireViewById(R.id.navigation_bar_view);
        ViewGroup.LayoutParams layoutParams = requireViewById.getLayoutParams();
        this.editResources.getClass();
        layoutParams.height = (SecQSSettingEditResources.isBarPhone() && requireViewById.getResources().getConfiguration().orientation == 2) ? 0 : requireViewById.getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
        ((QSCMainView) this.mView).requireViewById(R.id.main_content).getLayoutParams().width = this.resourcePicker.getPanelWidth(getContext());
        if (isLargeScreen$6()) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) ((QSCMainView) this.mView).requireViewById(R.id.main_content).getLayoutParams();
            boolean z = QpRune.QUICK_POP_OVER_CUSTOMIZER;
            if (z) {
                QSCMainView qSCMainView = (QSCMainView) this.mView;
                qSCMainView.setPadding(qSCMainView.getPaddingLeft(), 0, ((QSCMainView) this.mView).getPaddingRight(), ((QSCMainView) this.mView).getPaddingBottom());
            }
            layoutParams2.topMargin = z ? getCutoutTopMargin() : 0;
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((QSCMainView) this.mView).configChangedCallback = new QSCMainViewController$onViewAttached$1(this);
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER) {
            ((QSCMainView) this.mView).windowInsetChangeListener = new QSCMainViewController$onViewAttached$2(this);
        }
        ((QSCMainView) this.mView).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$onViewAttached$3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        QSCustomizerWindowHelper qSCustomizerWindowHelper = this.customizerWindowHelper;
        View view = qSCustomizerWindowHelper.windowRootView;
        if (view != null) {
            WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
            if (windowManager != null) {
                windowManager.removeViewImmediate(view);
            }
            qSCustomizerWindowHelper.windowRootView = null;
        }
        ((QSCMainView) this.mView).configChangedCallback = null;
    }

    public final void setPosition$2(float f) {
        ListView listView;
        if (((QSCMainView) this.mView).getVisibility() != 0) {
            return;
        }
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mView, "alpha", 0.0f, 1.0f);
        builder.mStartDelay = 0.3f;
        builder.build().setPosition(f);
        if (f < 1.0f) {
            ((QSCMainView) this.mView).setVisibility(4);
        }
        QSBlurPopUpMenu qSBlurPopUpMenu = this.popupButtonController.qsBlurPopUpMenu;
        if (qSBlurPopUpMenu == null || (listView = qSBlurPopUpMenu.getListView()) == null) {
            return;
        }
        listView.setVisibility(4);
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        ViewPropertyAnimator alpha;
        ViewPropertyAnimator scaleX;
        ViewPropertyAnimator scaleY;
        ViewPropertyAnimator duration;
        ViewPropertyAnimator startDelay;
        ViewPropertyAnimator withStartAction;
        ViewGroup.LayoutParams layoutParams;
        if (this.isShown) {
            return;
        }
        super.show(null);
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER && (layoutParams = ((QSCMainView) this.view).getLayoutParams()) != null) {
            layoutParams.height = isLargeScreen$6() ? -2 : -1;
            ((QSCMainView) this.view).setLayoutParams(layoutParams);
        }
        ((QSCMainView) this.view).setTranslationX(isLargeScreen$6() ? this.resourcePicker.getQsFrameX() : 0.0f);
        this.mCurrentOrientation = getContext().getResources().getConfiguration().orientation;
        this.doShowingInRotation = null;
        this.cloneBarRunnable = runnable;
        QsTransitionAnimator qsTransitionAnimator = this.transitionAnimator;
        if (qsTransitionAnimator == null) {
            qsTransitionAnimator = null;
        }
        qsTransitionAnimator.showQsPanelForCustomizer(false);
        this.subContainer.removeAllViews();
        ViewControllerRepository viewControllerRepo = getViewControllerRepo();
        ViewControllerType viewControllerType = ViewControllerType.None;
        viewControllerRepo.prevType = viewControllerType;
        ViewControllerRepository viewControllerRepo2 = getViewControllerRepo();
        ViewControllerType viewControllerType2 = viewControllerRepo2.MAIN_TYPE;
        if (viewControllerType == viewControllerType2) {
            viewControllerType2 = viewControllerType;
        } else if (viewControllerType != ViewControllerType.Setting) {
            viewControllerType2 = viewControllerRepo2.currentType;
        }
        viewControllerRepo2.prevType = viewControllerType2;
        viewControllerRepo2.currentType = viewControllerType;
        Log.d(this.TAG, "show()");
        QSCustomizerWindowHelper qSCustomizerWindowHelper = this.customizerWindowHelper;
        if (qSCustomizerWindowHelper.windowRootView == null) {
            qSCustomizerWindowHelper.windowRootView = LayoutInflater.from(qSCustomizerWindowHelper.sysUIContext).inflate(R.layout.qs_customize_boundary_panel, (ViewGroup) null);
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(-2, -2, 2140, 520, -2);
            layoutParams2.setTitle("CustomizerDummyWindow");
            layoutParams2.screenOrientation = -1;
            layoutParams2.privateFlags |= 16;
            WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
            if (windowManager != null) {
                windowManager.addView(qSCustomizerWindowHelper.windowRootView, layoutParams2);
            }
        }
        SecQSSettingEditResources secQSSettingEditResources = this.editResources;
        secQSSettingEditResources.tileFullAdapter = new SecQSCustomizerTileAdapter(secQSSettingEditResources.context, secQSSettingEditResources.tileHost, false, secQSSettingEditResources.userTracker, secQSSettingEditResources.mainExecutor, secQSSettingEditResources.bgExecutor);
        secQSSettingEditResources.tileTopAdapter = new SecQSCustomizerTileAdapter(secQSSettingEditResources.context, secQSSettingEditResources.qqsTileHost, true, secQSSettingEditResources.userTracker, secQSSettingEditResources.mainExecutor, secQSSettingEditResources.bgExecutor);
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secQSSettingEditResources.tileFullAdapter;
        if (secQSCustomizerTileAdapter != null) {
            secQSCustomizerTileAdapter.updateTiles();
        }
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSSettingEditResources.tileTopAdapter;
        if (secQSCustomizerTileAdapter2 != null) {
            secQSCustomizerTileAdapter2.updateTiles();
        }
        initResources();
        showViewWithFixRotation(getViewControllerRepo().MAIN_TYPE);
        SecQSCustomizerAnimator.Companion companion = SecQSCustomizerAnimator.Companion;
        T t = this.mView;
        companion.getClass();
        SecQSCustomizerAnimator.mainView = t;
        T t2 = this.mView;
        t2.setAlpha(0.0f);
        t2.setScaleX(0.93f);
        t2.setScaleY(0.93f);
        ViewPropertyAnimator animate = this.mView.animate();
        if (animate != null && (alpha = animate.alpha(1.0f)) != null && (scaleX = alpha.scaleX(1.0f)) != null && (scaleY = scaleX.scaleY(1.0f)) != null && (duration = scaleY.setDuration(200L)) != null && (startDelay = duration.setStartDelay(0L)) != null && (withStartAction = startDelay.withStartAction(new Runnable() { // from class: com.android.systemui.qs.customize.SecQSCustomizerAnimator$Companion$startShowView$1
            @Override // java.lang.Runnable
            public final void run() {
                ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).mQsEditMode = true;
            }
        })) != null) {
            withStartAction.start();
        }
        SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_QUICKPANEL_CUSTOMIZER);
    }

    public final void showViewWithFixRotation(final ViewControllerType viewControllerType) {
        int i;
        if (getViewControllerRepo().currentType == viewControllerType) {
            return;
        }
        int i2 = 1;
        boolean z = !isLargeScreen$6() && ((i = WhenMappings.$EnumSwitchMapping$0[viewControllerType.ordinal()]) == 1 || i == 2 || !QpRune.QUICK_PANEL_BLUR_DEFAULT);
        QSCustomizerWindowHelper qSCustomizerWindowHelper = this.customizerWindowHelper;
        View view = qSCustomizerWindowHelper.windowRootView;
        if (view != null) {
            int i3 = z ? 1 : -1;
            if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && !QpRune.QUICK_PANEL_BLUR_DEFAULT) {
                if (qSCustomizerWindowHelper.sysUIContext.getResources().getConfiguration().orientation == 2) {
                    i2 = qSCustomizerWindowHelper.sysUIContext.getDisplay().getRotation() == 3 ? 8 : 0;
                } else if (qSCustomizerWindowHelper.sysUIContext.getDisplay().getRotation() == 2) {
                    i2 = 9;
                }
                i3 = i2;
            }
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
            WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
            if (windowManager != null && layoutParams.screenOrientation != i3) {
                layoutParams.screenOrientation = i3;
                windowManager.updateViewLayout(view, layoutParams);
            }
        }
        if (this.mCurrentOrientation == 2 && z) {
            this.doShowingInRotation = viewControllerType;
        }
        if (viewControllerType == ViewControllerType.Setting) {
            SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_PANEL_SETTINGS, SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
        }
        ((QSCMainView) this.mView).post(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$showViewWithFixRotation$1
            @Override // java.lang.Runnable
            public final void run() {
                QSCMainViewController.access$showView(QSCMainViewController.this, viewControllerType);
            }
        });
    }
}
