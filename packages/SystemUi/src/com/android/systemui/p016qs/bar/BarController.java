package com.android.systemui.p016qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.android.systemui.p016qs.SecQSPanel;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BarController implements Dumpable, PanelScreenShotLogger.LogProvider {
    public ArrayList mAllBarItems;
    public final BarBackUpRestoreHelper mBarBackUpRestoreHelper;
    public C20863 mBarListener;
    public ArrayList mCollapsedBarItems;
    public final Context mContext;
    public ArrayList mExpandedBarItems;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public Runnable mQSLastExpansionInitializer;
    public boolean mQsFullyExpanded;
    public SecQSPanel mQsPanel;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SettingsHelper mSettingsHelper;
    public int mThemeSeq;
    public int mUiMode;
    public final C20841 mKnoxStateMonitorCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.qs.bar.BarController.1
        @Override // com.android.systemui.knox.KnoxStateMonitorCallback
        public final void onUpdateQuickPanelButtons() {
            Log.d("BarController", "onUpdateQuickPanelButtons");
            BarController.this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(1));
        }
    };
    public final C20852 mOnConfigurationChangedListener = new SecQSPanel.OnConfigurationChangedListener() { // from class: com.android.systemui.qs.bar.BarController.2
        @Override // com.android.systemui.qs.SecQSPanel.OnConfigurationChangedListener
        public final void onConfigurationChange(Configuration configuration) {
            BarController barController = BarController.this;
            barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda8(configuration, 1));
            int i = configuration.uiMode & 48;
            int i2 = barController.mUiMode;
            Context context = barController.mContext;
            if (i != i2) {
                barController.mUiMode = i;
                barController.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(2));
                if (context != null) {
                    Log.d("BarController", "<QUICK_UIMODE : ");
                    BarController.logForColors(context, new BarController$$ExternalSyntheticLambda1(6));
                    Log.d("BarController", ">");
                }
            }
            int i3 = barController.mThemeSeq;
            int i4 = configuration.themeSeq;
            if (i3 != i4) {
                barController.mThemeSeq = i4;
                if (context != null) {
                    Log.d("BarController", "<QUICK_OPENTHEME is " + barController.mSettingsHelper.getActiveThemePackage());
                    BarController.logForColors(context, new BarController$$ExternalSyntheticLambda1(7));
                    Log.d("BarController", ">");
                }
            }
        }
    };
    public int mDisplayCutoutTopInset = 0;
    public int mNavBarHeight = 0;
    public int mOrientation = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.bar.BarController$3 */
    public final class C20863 {
        public final /* synthetic */ Runnable val$animatorRunner;
        public final /* synthetic */ Runnable val$containerRunner;

        public C20863(Runnable runnable, Runnable runnable2) {
            this.val$animatorRunner = runnable;
            this.val$containerRunner = runnable2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.bar.BarController$4 */
    public final class C20874 {
        public C20874() {
        }

        public final void onBarHeightChanged() {
            C20863 c20863 = BarController.this.mBarListener;
            if (c20863 != null) {
                Runnable runnable = BarController.this.mQSLastExpansionInitializer;
                if (runnable != null) {
                    runnable.run();
                }
                c20863.val$animatorRunner.run();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                barController2.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda1(3));
            }
            return windowInsets;
        }

        private OnApplyWindowInsetsListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.bar.BarController$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.bar.BarController$2] */
    public BarController(Context context, SettingsHelper settingsHelper, DumpManager dumpManager, BarFactory barFactory, SecQSPanelResourcePicker secQSPanelResourcePicker, KnoxStateMonitor knoxStateMonitor) {
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mKnoxStateMonitor = knoxStateMonitor;
        dumpManager.unregisterDumpable("BarController");
        DumpManager.registerDumpable$default(dumpManager, "BarController", this);
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mCollapsedBarItems = new ArrayList(barFactory.createBarItems(true));
        this.mExpandedBarItems = new ArrayList(barFactory.createBarItems(false));
        ArrayList arrayList = new ArrayList();
        this.mAllBarItems = arrayList;
        arrayList.addAll(this.mCollapsedBarItems);
        this.mAllBarItems.addAll(this.mExpandedBarItems);
        this.mBarBackUpRestoreHelper = new BarBackUpRestoreHelper(context, settingsHelper);
    }

    public static void logForColors(final Context context, final Consumer consumer) {
        Map.of("open_theme_qp_bg_color", Integer.valueOf(R.color.open_theme_qp_bg_color), "sec_panel_background_color", Integer.valueOf(R.color.sec_panel_background_color), "animated_brightness_icon_color", Integer.valueOf(R.color.animated_brightness_icon_color), "tw_progress_color_control_activated", Integer.valueOf(R.color.tw_progress_color_control_activated), "tw_progress_color_control_normal", Integer.valueOf(R.color.tw_progress_color_control_normal), "qs_tile_round_background_off", Integer.valueOf(R.color.qs_tile_round_background_off), "qs_tile_round_background_on", Integer.valueOf(R.color.qs_tile_round_background_on), "qs_tile_icon_on_dim_tint_color", Integer.valueOf(R.color.qs_tile_icon_on_dim_tint_color), "qs_tile_label", Integer.valueOf(R.color.qs_tile_label)).forEach(new BiConsumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Consumer consumer2 = consumer;
                Context context2 = context;
                StringBuilder m2m = AbstractC0000x2c234b15.m2m((String) obj, ": #");
                m2m.append(Integer.toHexString(context2.getColor(((Integer) obj2).intValue())));
                consumer2.accept(m2m.toString());
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("Dump Bar state =============================================== ");
        final int i = 0;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda11
            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                }
                printWriter.println((String) obj);
            }
        };
        this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda10(new StringBuilder(), consumer, 0));
        final int i2 = 1;
        Context context = this.mContext;
        if (context != null) {
            logForColors(context, new Consumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda11
                /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i2) {
                    }
                    printWriter.println((String) obj);
                }
            });
        }
        printWriter.println("============================================================== ");
        this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda10(printWriter, strArr, 1));
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        final ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("BarController", arrayList);
        final int i = 0;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda7
            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                }
                arrayList.add((String) obj);
            }
        };
        this.mAllBarItems.forEach(new BarController$$ExternalSyntheticLambda10(new StringBuilder(), consumer, 0));
        Context context = this.mContext;
        if (context != null) {
            final int i2 = 1;
            logForColors(context, new Consumer() { // from class: com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda7
                /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i2) {
                    }
                    arrayList.add((String) obj);
                }
            });
        }
        return arrayList;
    }

    public final BarItemImpl getBarInCollapsed(BarType barType) {
        return (BarItemImpl) this.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda4(barType, 1)).findFirst().orElse(null);
    }

    public final BarItemImpl getBarInExpanded(BarType barType) {
        return (BarItemImpl) this.mExpandedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda4(barType, 0)).findFirst().orElse(null);
    }

    public final void updateBarUnderneathQqs() {
        this.mCollapsedBarItems.forEach(new BarController$$ExternalSyntheticLambda1(4));
        this.mCollapsedBarItems.stream().filter(new BarController$$ExternalSyntheticLambda5(1)).findFirst().ifPresent(new BarController$$ExternalSyntheticLambda1(5));
    }
}
