package com.android.systemui.qs.customize.setting;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.LinearLayout;
import androidx.activity.ComponentActivity;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.customize.SecQSCustomizer;
import com.android.systemui.qs.customize.SecQSCustomizerBase;
import com.android.systemui.qs.customize.SecQSCustomizerController;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.SecQSTopCustomizer;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQSSettingEditTilesActivity extends ComponentActivity {
    public float currentDensity;
    public SecQSCustomizerController customizerController;
    public final SecQSSettingEditResources editResources;
    public boolean isTopEdit;

    public SecQSSettingEditTilesActivity(SecQSSettingEditResources secQSSettingEditResources) {
        this.editResources = secQSSettingEditResources;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00b3, code lost:
    
        if (r3 != false) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0049  */
    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void attachBaseContext(Context context) {
        int i;
        boolean z;
        this.isTopEdit = this.editResources.isCurrentTopEdit;
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Display display = context.getDisplay();
        if (display != null) {
            Integer valueOf = windowManagerService != null ? Integer.valueOf(windowManagerService.getInitialDisplayDensity(display.getDisplayId())) : null;
            if (valueOf != null) {
                i = valueOf.intValue();
                Configuration configuration = new Configuration();
                configuration.setTo(context.getResources().getConfiguration());
                this.currentDensity = displayMetrics.density;
                if (QpRune.QUICK_TABLET) {
                    if (!this.isTopEdit && context.getResources().getConfiguration().orientation == 2) {
                        if ((context.getResources().getDisplayMetrics().heightPixels - context.getResources().getDimensionPixelSize(R.dimen.status_bar_height)) - context.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height) < context.getResources().getDimensionPixelSize(R.dimen.qs_edit_buttons_height) + ((context.getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height) + context.getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size)) * 4)) {
                            this.currentDensity = context.getResources().getDisplayMetrics().density;
                            z = false;
                        }
                    }
                    z = true;
                }
                if (displayMetrics.densityDpi != i) {
                    configuration.densityDpi = i;
                    context = context.createConfigurationContext(configuration);
                }
                super.attachBaseContext(context);
            }
        }
        i = DisplayMetrics.DENSITY_DEVICE_STABLE;
        Configuration configuration2 = new Configuration();
        configuration2.setTo(context.getResources().getConfiguration());
        this.currentDensity = displayMetrics.density;
        if (QpRune.QUICK_TABLET) {
        }
        if (displayMetrics.densityDpi != i) {
        }
        super.attachBaseContext(context);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        SecQSCustomizerController secQSCustomizerController = this.customizerController;
        if (secQSCustomizerController == null) {
            secQSCustomizerController = null;
        }
        secQSCustomizerController.startClosingAnim();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z = QpRune.QUICK_TABLET;
        if (!z) {
            setRequestedOrientation(1);
        }
        super.onCreate(bundle);
        setContentView(R.layout.qs_setting_tiles_edit_activity);
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        int i = (getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32 ? systemUiVisibility & (-17) : systemUiVisibility | 16;
        if (!z) {
            decorView.setSystemUiVisibility(i);
        }
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditTilesActivity$onCreate$2
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                float f = SecQSSettingEditTilesActivity.this.getResources().getDisplayMetrics().density;
                SecQSSettingEditTilesActivity secQSSettingEditTilesActivity = SecQSSettingEditTilesActivity.this;
                secQSSettingEditTilesActivity.editResources.getClass();
                int bottomPadding = SecQSSettingEditResources.getBottomPadding(secQSSettingEditTilesActivity, windowInsets);
                int dimensionPixelSize = SecQSSettingEditTilesActivity.this.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
                SecQSSettingEditTilesActivity secQSSettingEditTilesActivity2 = SecQSSettingEditTilesActivity.this;
                float f2 = secQSSettingEditTilesActivity2.currentDensity;
                if (!(f == f2)) {
                    bottomPadding = (int) ((bottomPadding / f) * f2);
                    dimensionPixelSize = (int) ((dimensionPixelSize / f) * f2);
                }
                View requireViewById = secQSSettingEditTilesActivity2.requireViewById(R.id.edit_navigation_bar_view);
                if (requireViewById != null) {
                    if (QpRune.QUICK_TABLET) {
                        requireViewById.setBackgroundColor(0);
                    }
                    requireViewById.setLayoutParams(new LinearLayout.LayoutParams(-1, bottomPadding));
                }
                View requireViewById2 = SecQSSettingEditTilesActivity.this.requireViewById(R.id.edit_status_bar_view);
                if (requireViewById2 != null) {
                    requireViewById2.setLayoutParams(new LinearLayout.LayoutParams(-1, dimensionPixelSize));
                }
                View requireViewById3 = SecQSSettingEditTilesActivity.this.requireViewById(R.id.qs_customize_panel_buttons_parent);
                Resources resources = requireViewById3.getResources();
                boolean z2 = QpRune.QUICK_TABLET;
                int dimensionPixelOffset = resources.getDimensionPixelOffset(z2 ? R.dimen.qs_edit_tablet_available_area_bottom : R.dimen.qs_edit_available_area_bottom);
                ViewGroup.LayoutParams layoutParams = requireViewById3.getLayoutParams();
                layoutParams.height = requireViewById3.getResources().getDimensionPixelOffset(R.dimen.qs_edit_buttons_height) + dimensionPixelOffset;
                requireViewById3.setLayoutParams(layoutParams);
                requireViewById3.setPadding(requireViewById3.getPaddingLeft(), requireViewById3.getPaddingTop(), requireViewById3.getPaddingRight(), dimensionPixelOffset);
                if (z2) {
                    requireViewById3.setBackgroundColor(0);
                    requireViewById3.setBackground(requireViewById3.getResources().getDrawable(R.drawable.qs_edit_panel_available_background_bottom, null));
                }
                SecQSSettingEditTilesActivity secQSSettingEditTilesActivity3 = SecQSSettingEditTilesActivity.this;
                secQSSettingEditTilesActivity3.editResources.getClass();
                int sidePadding = SecQSSettingEditResources.getSidePadding(secQSSettingEditTilesActivity3);
                SecQSSettingEditTilesActivity secQSSettingEditTilesActivity4 = SecQSSettingEditTilesActivity.this;
                secQSSettingEditTilesActivity4.editResources.getClass();
                view.setPadding(sidePadding, 0, SecQSSettingEditResources.getSidePadding(secQSSettingEditTilesActivity4), 0);
                return WindowInsets.CONSUMED;
            }
        });
        SecQSCustomizerBase secQSTopCustomizer = this.isTopEdit ? new SecQSTopCustomizer(this) : new SecQSCustomizer(this);
        secQSTopCustomizer.setOrientation(1);
        secQSTopCustomizer.setVisibility(8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 1;
        Unit unit = Unit.INSTANCE;
        addContentView(secQSTopCustomizer, layoutParams);
        SecQSCustomizerController secQSCustomizerController = new SecQSCustomizerController(secQSTopCustomizer, this.editResources, this.isTopEdit);
        this.customizerController = secQSCustomizerController;
        secQSCustomizerController.init();
        SecQSCustomizerController secQSCustomizerController2 = this.customizerController;
        if (secQSCustomizerController2 == null) {
            secQSCustomizerController2 = null;
        }
        SecQSSettingEditTilesActivity$onCreate$5 secQSSettingEditTilesActivity$onCreate$5 = new SecQSSettingEditTilesActivity$onCreate$5(this);
        if (!((SecQSCustomizerBase) secQSCustomizerController2.mView).isShown()) {
            final SecQSCustomizerBase secQSCustomizerBase = (SecQSCustomizerBase) secQSCustomizerController2.mView;
            if (!secQSCustomizerBase.isShown) {
                Log.d("SecQSCustomizerBase", "show customizer");
                secQSCustomizerBase.isShown = true;
                secQSCustomizerBase.setVisibility(0);
                SystemUIAnalytics.sendScreenViewLog("QPP102");
                LinearLayout linearLayout = (LinearLayout) secQSCustomizerBase.findViewById(R.id.qs_customizer_top_summary);
                secQSCustomizerBase.mSummary = linearLayout;
                if (linearLayout != null) {
                    linearLayout.postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.SecQSCustomizerBase$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecQSCustomizerBase.this.mSummary.setSelected(true);
                        }
                    }, 500L);
                }
            }
            secQSCustomizerController2.mDoneCallBack = secQSSettingEditTilesActivity$onCreate$5;
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.semAddExtensionFlags(16777216);
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        SecQSCustomizerController secQSCustomizerController = this.customizerController;
        if (secQSCustomizerController == null) {
            secQSCustomizerController = null;
        }
        boolean isChangingConfigurations = isChangingConfigurations();
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secQSCustomizerController.mTileAdapter;
        if (secQSCustomizerTileAdapter != null) {
            secQSCustomizerTileAdapter.saveTiles(secQSCustomizerController.mActiveTileLayout, secQSCustomizerController.mAvailableTileLayout, !isChangingConfigurations);
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.editResources.isPhoneLandscape()) {
            setResult(-1);
            finish();
        }
    }
}
