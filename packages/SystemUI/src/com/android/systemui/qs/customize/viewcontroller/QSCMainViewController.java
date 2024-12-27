package com.android.systemui.qs.customize.viewcontroller;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.FrameLayout;
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
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.customize.QSBlurPopUpMenu;
import com.android.systemui.qs.customize.QSCPopupButtonController;
import com.android.systemui.qs.customize.QSCustomizerWindowHelper;
import com.android.systemui.qs.customize.SecQSCustomizerAnimator;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.QSCMainView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public final class QSCMainViewController extends ViewControllerBase {
    public final String TAG;
    public final QSCMainViewController$accessibilityDelegate$1 accessibilityDelegate;
    public final BarOrderInteractor barOrderInteractor;
    public Runnable cloneBarRunnable;
    public final QSCustomizerWindowHelper customizerWindowHelper;
    public ViewControllerType doShowingInRotation;
    public final SecQSSettingEditResources editResources;
    public int mCurrentOrientation;
    public QSPanelHost panelHost;
    public final QSCPopupButtonController popupButtonController;
    public final FrameLayout primeContainer;
    public final SecQSPanelResourcePicker resourcePicker;
    private final SettingsHelper settingsHelper;
    public final FrameLayout subContainer;
    public QsTransitionAnimator transitionAnimator;
    public final Lazy viewControllerRepo$delegate;

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
                iArr[ViewControllerType.PanelType.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ViewControllerType.Setting.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$accessibilityDelegate$1] */
    public QSCMainViewController(QSCMainView qSCMainView, Context context, SecQSSettingEditResources secQSSettingEditResources, SecQSPanelResourcePicker secQSPanelResourcePicker, BarOrderInteractor barOrderInteractor, QSCPopupButtonController qSCPopupButtonController, SettingsHelper settingsHelper) {
        super(qSCMainView);
        this.editResources = secQSSettingEditResources;
        this.resourcePicker = secQSPanelResourcePicker;
        this.barOrderInteractor = barOrderInteractor;
        this.popupButtonController = qSCPopupButtonController;
        this.settingsHelper = settingsHelper;
        this.TAG = "QSCutomizerMainViewCon";
        this.viewControllerRepo$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$viewControllerRepo$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new ViewControllerRepository();
            }
        });
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
        View view;
        if (this.isShown) {
            prepareForShowing(false);
            QsTransitionAnimator qsTransitionAnimator = this.transitionAnimator;
            if (qsTransitionAnimator == null) {
                qsTransitionAnimator = null;
            }
            qsTransitionAnimator.showQsPanelForCustomizer(true);
            Log.d(this.TAG, "close()");
            super.close();
            ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).mQsEditMode = false;
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
            qSCustomizerWindowHelper.getClass();
            if (!QpRune.QUICK_TABLET && (view = qSCustomizerWindowHelper.windowRootView) != null) {
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

    public final ViewControllerRepository getViewControllerRepo() {
        return (ViewControllerRepository) this.viewControllerRepo$delegate.getValue();
    }

    public final void initResources() {
        View requireViewById = ((QSCMainView) this.mView).requireViewById(R.id.navigation_bar_view);
        requireViewById.getLayoutParams().height = requireViewById.getResources().getConfiguration().orientation == 2 ? 0 : requireViewById.getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
        ((QSCMainView) this.mView).requireViewById(R.id.main_content).getLayoutParams().width = this.resourcePicker.getPanelWidth(getContext());
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((QSCMainView) this.mView).configChangedCallback = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$onViewAttached$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ViewControllerType viewControllerType;
                QSCMainViewController qSCMainViewController = QSCMainViewController.this;
                int i = qSCMainViewController.mCurrentOrientation;
                int i2 = configuration.orientation;
                if (i != i2) {
                    qSCMainViewController.mCurrentOrientation = i2;
                    if (qSCMainViewController.isShown) {
                        if (i2 != 2 && (viewControllerType = qSCMainViewController.doShowingInRotation) != null) {
                            qSCMainViewController.doShowingInRotation = null;
                            qSCMainViewController.showView(viewControllerType);
                        }
                        qSCMainViewController.initResources();
                        for (ViewControllerBase viewControllerBase : qSCMainViewController.getViewControllerRepo().viewControllers) {
                            if (viewControllerBase != null) {
                                viewControllerBase.configChanged();
                            }
                        }
                    }
                }
            }
        };
        ((QSCMainView) this.mView).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$onViewAttached$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        View view;
        QSCustomizerWindowHelper qSCustomizerWindowHelper = this.customizerWindowHelper;
        qSCustomizerWindowHelper.getClass();
        if (!QpRune.QUICK_TABLET && (view = qSCustomizerWindowHelper.windowRootView) != null) {
            WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
            if (windowManager != null) {
                windowManager.removeViewImmediate(view);
            }
            qSCustomizerWindowHelper.windowRootView = null;
        }
        ((QSCMainView) this.mView).configChangedCallback = null;
    }

    public final void prepareForShowing(boolean z) {
        QsTransitionAnimator qsTransitionAnimator = this.transitionAnimator;
        if (qsTransitionAnimator == null) {
            qsTransitionAnimator = null;
        }
        if (qsTransitionAnimator.mQsPanel == null || qsTransitionAnimator.mQSPanelController.mExpandSettingsPanel || !(qsTransitionAnimator.isThereNoView() || (!qsTransitionAnimator.mAnimatorsInitialiezed))) {
            if (z) {
                qsTransitionAnimator.mAnimStateCallback.getClass();
                SecQSImplAnimatorManager.AnonymousClass2.setCustomizerShowing(true);
            } else {
                qsTransitionAnimator.mAnimStateCallback.getClass();
                SecQSImplAnimatorManager.AnonymousClass2.setCustomizerShowing(false);
            }
        }
    }

    public final void setPosition(float f) {
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
        ListView listView = qSBlurPopUpMenu != null ? qSBlurPopUpMenu.getListView() : null;
        if (listView == null) {
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
        if (this.isShown) {
            return;
        }
        super.show(null);
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
        qSCustomizerWindowHelper.getClass();
        if (!QpRune.QUICK_TABLET && qSCustomizerWindowHelper.windowRootView == null) {
            qSCustomizerWindowHelper.windowRootView = LayoutInflater.from(qSCustomizerWindowHelper.sysUIContext).inflate(R.layout.qs_customize_boundary_panel, (ViewGroup) null);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, qSCustomizerWindowHelper.WINDOW_PREFIX + 2040, 520, -2);
            layoutParams.setTitle(qSCustomizerWindowHelper.WINDOW_TITLE);
            layoutParams.screenOrientation = -1;
            layoutParams.privateFlags |= 16;
            WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
            if (windowManager != null) {
                windowManager.addView(qSCustomizerWindowHelper.windowRootView, layoutParams);
            }
        }
        SecQSSettingEditResources secQSSettingEditResources = this.editResources;
        secQSSettingEditResources.tileFullAdapter = new SecQSCustomizerTileAdapter(secQSSettingEditResources.context, secQSSettingEditResources.tileHost, false, secQSSettingEditResources.userTracker, secQSSettingEditResources.mainExecutor, secQSSettingEditResources.bgExecutor);
        secQSSettingEditResources.tileTopAdapter = new SecQSCustomizerTileAdapter(secQSSettingEditResources.context, secQSSettingEditResources.tileHost, true, secQSSettingEditResources.userTracker, secQSSettingEditResources.mainExecutor, secQSSettingEditResources.bgExecutor);
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

    public final void showView(ViewControllerType viewControllerType) {
        if (this.doShowingInRotation != null) {
            return;
        }
        boolean z = viewControllerType == getViewControllerRepo().MAIN_TYPE;
        FrameLayout frameLayout = z ? this.primeContainer : this.subContainer;
        boolean z2 = z && frameLayout.getChildCount() > 0;
        if (z) {
            this.subContainer.removeAllViews();
        }
        String str = this.TAG;
        Log.d(str, "setCurrentType: " + viewControllerType);
        ViewControllerRepository viewControllerRepo = getViewControllerRepo();
        ViewControllerType viewControllerType2 = viewControllerRepo.MAIN_TYPE;
        if (viewControllerType == viewControllerType2) {
            viewControllerType2 = ViewControllerType.None;
        } else if (viewControllerType != ViewControllerType.Setting) {
            viewControllerType2 = viewControllerRepo.currentType;
        }
        viewControllerRepo.prevType = viewControllerType2;
        viewControllerRepo.currentType = viewControllerType;
        ViewControllerBase currentViewController = getCurrentViewController();
        if (currentViewController == null) {
            Log.d(str, "createCurrentViewController()");
            int i = WhenMappings.$EnumSwitchMapping$0[getViewControllerRepo().currentType.ordinal()];
            SecQSSettingEditResources secQSSettingEditResources = this.editResources;
            if (i == 1) {
                currentViewController = new QSTileCustomizerController(getContext(), secQSSettingEditResources);
            } else if (i != 2) {
                currentViewController = i != 3 ? i != 4 ? null : new QSSettingViewController(getContext(), this.editResources, this.popupButtonController, this.accessibilityDelegate, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        QSCMainViewController.this.backKeyEvent();
                    }
                }, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        QSCMainViewController qSCMainViewController = QSCMainViewController.this;
                        qSCMainViewController.editResources.isCurrentTopEdit = true;
                        qSCMainViewController.showViewWithFixRotation(ViewControllerType.TileEdit);
                    }
                }, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        QSCMainViewController.this.showViewWithFixRotation(ViewControllerType.PanelType);
                    }
                }) : new QSPanelTypeViewController(getContext(), secQSSettingEditResources, this.accessibilityDelegate, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        QSCMainViewController.this.backKeyEvent();
                    }
                });
            } else {
                Runnable runnable = this.cloneBarRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                Context context = getContext();
                ArrayList arrayList = new ArrayList();
                QSPanelHost qSPanelHost = this.panelHost;
                if (qSPanelHost == null) {
                    qSPanelHost = null;
                }
                arrayList.addAll(qSPanelHost.getBarItems());
                Unit unit = Unit.INSTANCE;
                currentViewController = new QSLayoutEditViewController(context, this.barOrderInteractor, arrayList, this.editResources, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        QSCMainViewController qSCMainViewController = QSCMainViewController.this;
                        qSCMainViewController.editResources.isCurrentTopEdit = false;
                        qSCMainViewController.showViewWithFixRotation(ViewControllerType.TileEdit);
                    }
                }, new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$createCurrentViewController$controller$3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        QSCMainViewController.this.showViewWithFixRotation(ViewControllerType.Setting);
                    }
                });
            }
            if (currentViewController != null) {
                currentViewController.init();
            }
        }
        ViewControllerRepository viewControllerRepo2 = getViewControllerRepo();
        viewControllerRepo2.viewControllers[viewControllerRepo2.currentType.ordinal()] = currentViewController;
        ViewControllerBase currentViewController2 = getCurrentViewController();
        if (currentViewController2 != null) {
            if (z2 || currentViewController2.view.getParent() != null) {
                View view = currentViewController2.view;
                if (view != null) {
                    view.setVisibility(0);
                }
            } else {
                frameLayout.addView(currentViewController2.view);
                currentViewController2.show(new Runnable() { // from class: com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$showView$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSCMainViewController.this.backKeyEvent();
                    }
                });
            }
            if (getViewControllerRepo().prevType != getViewControllerRepo().MAIN_TYPE) {
                ViewControllerRepository viewControllerRepo3 = getViewControllerRepo();
                ViewControllerType viewControllerType3 = viewControllerRepo3.prevType;
                ViewControllerType viewControllerType4 = ViewControllerType.None;
                ViewControllerBase viewControllerBase = (viewControllerType3 == viewControllerType4 || viewControllerType3 == viewControllerType4) ? null : viewControllerRepo3.viewControllers[viewControllerType3.ordinal()];
                View view2 = viewControllerBase != null ? viewControllerBase.view : null;
                if (view2 != null) {
                    view2.setVisibility(8);
                }
            }
        }
        this.primeContainer.setVisibility(z ? 0 : 8);
        this.subContainer.setVisibility(z ? 8 : 0);
        boolean z3 = getViewControllerRepo().currentType == ViewControllerType.TileEdit;
        int color = z3 ? getContext().getColor(R.color.qs_edit_panel_available_background_color) : 0;
        ((QSCMainView) this.mView).requireViewById(R.id.navigation_bar_view).setAlpha(1.0f);
        ((QSCMainView) this.mView).requireViewById(R.id.navigation_bar_view).setBackgroundColor(color);
        ((QSCMainView) this.mView).requireViewById(R.id.navigation_bar_view).semSetBlurInfo(z3 ? new SemBlurInfo.Builder(0).setColorCurvePreset(14).setRadius(200).build() : null);
    }

    public final void showViewWithFixRotation(ViewControllerType viewControllerType) {
        View view;
        if (getViewControllerRepo().currentType == viewControllerType) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[viewControllerType.ordinal()];
        boolean z = i == 1 || i == 2 || !QpRune.QUICK_PANEL_BLUR_DEFAULT;
        QSCustomizerWindowHelper qSCustomizerWindowHelper = this.customizerWindowHelper;
        qSCustomizerWindowHelper.getClass();
        if (!QpRune.QUICK_TABLET && (view = qSCustomizerWindowHelper.windowRootView) != null) {
            int i2 = z ? 1 : -1;
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
            WindowManager windowManager = qSCustomizerWindowHelper.mWindowManager;
            if (windowManager != null && layoutParams.screenOrientation != i2) {
                layoutParams.screenOrientation = i2;
                windowManager.updateViewLayout(view, layoutParams);
            }
        }
        if (this.mCurrentOrientation == 2 && z) {
            this.doShowingInRotation = viewControllerType;
        }
        showView(viewControllerType);
    }
}
