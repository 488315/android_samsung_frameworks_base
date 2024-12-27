package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager$$ExternalSyntheticLambda0;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class BarController implements Dumpable, PanelScreenShotLogger.LogProvider, PanelTransitionStateListener {
    public final ArrayList mAllBarItems;
    public final ColoredBGHelper mBGColorHelper;
    public final BarBackUpRestoreHelper mBarBackUpRestoreHelper;
    public AnonymousClass3 mBarListener;
    public final BarOrderInteractor mBarOrderInteractor;
    public final ArrayList mCollapsedBarItems;
    public final Context mContext;
    public final ArrayList mExpandedBarItems;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public Runnable mQSLastExpansionInitializer;
    public SecQSPanel mQsPanel;
    public final SecQSPanelResourcePicker mResourcePicker;
    private final SettingsHelper mSettingsHelper;
    public int mThemeSeq;
    public int mUiMode;
    public Runnable mUpdateAnimatorsRunner;
    public final AnonymousClass1 mKnoxStateMonitorCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.qs.bar.BarController.1
        @Override // com.android.systemui.knox.KnoxStateMonitorCallback
        public final void onUpdateQuickPanelButtons() {
            Log.d("BarController", "onUpdateQuickPanelButtons");
            BarController.this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(6));
        }
    };
    public final AnonymousClass2 mOnConfigurationChangedListener = new SecQSPanel.OnConfigurationChangedListener() { // from class: com.android.systemui.qs.bar.BarController.2
        @Override // com.android.systemui.qs.SecQSPanel.OnConfigurationChangedListener
        public final void onConfigurationChange(Configuration configuration) {
            BarController barController = BarController.this;
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda12(configuration, 1));
            int i = configuration.uiMode & 48;
            if (i != barController.mUiMode) {
                barController.mUiMode = i;
                barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(7));
                Context context = barController.mContext;
                if (context != null) {
                    Log.d("BarController", "<QUICK_UIMODE : ");
                    BarController.logForColors(context, new BarController$$ExternalSyntheticLambda3(4));
                    Log.d("BarController", ">");
                }
            }
            int i2 = barController.mThemeSeq;
            int i3 = configuration.themeSeq;
            if (i2 != i3) {
                barController.mThemeSeq = i3;
                Context context2 = barController.mContext;
                if (context2 != null) {
                    BarController.m2065$$Nest$mlogForOpenTheme(barController, context2);
                }
            }
        }
    };
    public int mDisplayCutoutTopInset = 0;
    public int mNavBarHeight = 0;
    public int mOrientation = 1;

    /* renamed from: com.android.systemui.qs.bar.BarController$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public final /* synthetic */ Runnable val$animatorRunner;
        public final /* synthetic */ Runnable val$containerRunner;

        public AnonymousClass3(Runnable runnable, Runnable runnable2) {
            this.val$animatorRunner = runnable;
            this.val$containerRunner = runnable2;
        }
    }

    /* renamed from: com.android.systemui.qs.bar.BarController$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public AnonymousClass4() {
        }
    }

    public final class OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
        public /* synthetic */ OnApplyWindowInsetsListener(BarController barController, int i) {
            this();
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            DisplayCutout displayCutout = view.getRootWindowInsets().getDisplayCutout();
            int safeInsetTop = displayCutout != null ? displayCutout.getSafeInsetTop() : 0;
            BarController barController = BarController.this;
            int navBarHeight = barController.mResourcePicker.getNavBarHeight(barController.mContext);
            BarController barController2 = BarController.this;
            if (safeInsetTop != barController2.mDisplayCutoutTopInset || navBarHeight != barController2.mNavBarHeight) {
                barController2.mDisplayCutoutTopInset = safeInsetTop;
                barController2.mNavBarHeight = navBarHeight;
                barController2.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(8));
            }
            return windowInsets;
        }

        private OnApplyWindowInsetsListener() {
        }
    }

    /* renamed from: -$$Nest$mlogForOpenTheme, reason: not valid java name */
    public static void m2065$$Nest$mlogForOpenTheme(BarController barController, Context context) {
        Log.d("BarController", "<QUICK_OPENTHEME is " + barController.mSettingsHelper.getActiveThemePackage());
        logForColors(context, new BarController$$ExternalSyntheticLambda3(1));
        Log.d("BarController", ">");
    }

    public BarController(Context context, SettingsHelper settingsHelper, DumpManager dumpManager, final BarFactory barFactory, SecQSPanelResourcePicker secQSPanelResourcePicker, KnoxStateMonitor knoxStateMonitor, BarOrderInteractor barOrderInteractor, ColoredBGHelper coloredBGHelper) {
        final boolean z = false;
        final boolean z2 = true;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mBarOrderInteractor = barOrderInteractor;
        this.mKnoxStateMonitor = knoxStateMonitor;
        dumpManager.unregisterDumpable("BarController");
        DumpManager.registerDumpable$default(dumpManager, "BarController", this);
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mBGColorHelper = coloredBGHelper;
        barFactory.getClass();
        final ArrayList arrayList = new ArrayList();
        Arrays.stream(BarType.values()).filter(new Predicate() { // from class: com.android.systemui.qs.bar.BarFactory$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                BarType barType = (BarType) obj;
                return z2 ? barType.hasCollapsed() : barType.hasExpanded();
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.qs.bar.BarFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BarFactory barFactory2 = BarFactory.this;
                ArrayList arrayList2 = arrayList;
                boolean z3 = z2;
                BarType barType = (BarType) obj;
                BarItemImpl createBarItem = barFactory2.createBarItem(barType);
                if (createBarItem == null) {
                    return;
                }
                if (!createBarItem.isAvailable()) {
                    createBarItem.destroy();
                    return;
                }
                barType.name();
                createBarItem.mIsOnCollapsedState = z3;
                arrayList2.add(createBarItem);
            }
        });
        this.mCollapsedBarItems = new ArrayList(arrayList);
        final ArrayList arrayList2 = new ArrayList();
        Arrays.stream(BarType.values()).filter(new Predicate() { // from class: com.android.systemui.qs.bar.BarFactory$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                BarType barType = (BarType) obj;
                return z ? barType.hasCollapsed() : barType.hasExpanded();
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.qs.bar.BarFactory$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BarFactory barFactory2 = BarFactory.this;
                ArrayList arrayList22 = arrayList2;
                boolean z3 = z;
                BarType barType = (BarType) obj;
                BarItemImpl createBarItem = barFactory2.createBarItem(barType);
                if (createBarItem == null) {
                    return;
                }
                if (!createBarItem.isAvailable()) {
                    createBarItem.destroy();
                    return;
                }
                barType.name();
                createBarItem.mIsOnCollapsedState = z3;
                arrayList22.add(createBarItem);
            }
        });
        ArrayList arrayList3 = new ArrayList(arrayList2);
        this.mExpandedBarItems = arrayList3;
        arrayList3.forEach(new BarController$$ExternalSyntheticLambda0(this, 0));
        ArrayList arrayList4 = new ArrayList();
        this.mAllBarItems = arrayList4;
        arrayList4.addAll(this.mCollapsedBarItems);
        this.mAllBarItems.addAll(this.mExpandedBarItems);
        this.mBarBackUpRestoreHelper = new BarBackUpRestoreHelper(context, settingsHelper, barOrderInteractor);
    }

    public static void logForColors(final Context context, final Consumer consumer) {
        Map.of("open_theme_qp_bg_color", Integer.valueOf(R.color.open_theme_qp_bg_color), "sec_panel_background_color", Integer.valueOf(R.color.sec_panel_background_color), "animated_brightness_sun_icon_color", Integer.valueOf(R.color.animated_brightness_sun_icon_color), "tw_progress_color_control_activated", Integer.valueOf(R.color.tw_progress_color_control_activated), "tw_progress_color_control_normal", Integer.valueOf(R.color.tw_progress_color_control_normal), "qs_tile_round_background_off", Integer.valueOf(R.color.qs_tile_round_background_off), "qs_tile_round_background_on", Integer.valueOf(R.color.qs_tile_round_background_on), "qs_tile_icon_on_dim_tint_color", Integer.valueOf(R.color.qs_tile_icon_on_dim_tint_color), "qs_tile_label", Integer.valueOf(R.color.qs_tile_label), "qs_tile_container_bg", Integer.valueOf(R.color.qs_tile_container_bg)).forEach(new BiConsumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Consumer consumer2 = consumer;
                Context context2 = context;
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m((String) obj, ": #");
                m.append(Integer.toHexString(context2.getColor(((Integer) obj2).intValue())));
                consumer2.accept(m.toString());
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Dump Bar state =============================================== ");
        SecQSImplAnimatorManager$$ExternalSyntheticLambda0 secQSImplAnimatorManager$$ExternalSyntheticLambda0 = new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(printWriter, 0);
        this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda7(new StringBuilder(), secQSImplAnimatorManager$$ExternalSyntheticLambda0));
        Context context = this.mContext;
        if (context != null) {
            logForColors(context, new SecQSImplAnimatorManager$$ExternalSyntheticLambda0(printWriter, 0));
        }
        printWriter.println("============================================================== ");
        this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda3(0));
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("BarController", arrayList);
        BarController$$ExternalSyntheticLambda12 barController$$ExternalSyntheticLambda12 = new BarController$$ExternalSyntheticLambda12(arrayList, 0);
        this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda7(new StringBuilder(), barController$$ExternalSyntheticLambda12));
        Context context = this.mContext;
        if (context != null) {
            logForColors(context, new BarController$$ExternalSyntheticLambda12(arrayList, 0));
        }
        return arrayList;
    }

    public final BarItemImpl getBarInExpanded(BarType barType) {
        return (BarItemImpl) this.mExpandedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda6(barType, 0)).findFirst().orElse(null);
    }

    @Override // com.android.systemui.shade.PanelTransitionStateListener
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        this.mExpandedBarItems.forEach(new BarController$$ExternalSyntheticLambda1(panelTransitionStateChangeEvent.state != 1, 0));
    }

    public final void updateBarUnderneathQqs() {
        this.mCollapsedBarItems.forEach(new BarController$$ExternalSyntheticLambda3(2));
        this.mCollapsedBarItems.stream().filter(new BarController$$ExternalSyntheticLambda9()).findFirst().ifPresent(new BarController$$ExternalSyntheticLambda3(3));
    }
}
