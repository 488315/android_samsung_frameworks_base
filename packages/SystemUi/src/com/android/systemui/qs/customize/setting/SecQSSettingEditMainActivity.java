package com.android.systemui.qs.customize.setting;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qs.customize.SecQSCustomizerTileAdapter;
import com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity;
import com.android.systemui.qs.customize.setting.SecQSSettingEditPopUpMenu;
import com.android.systemui.qs.customize.setting.SecQSSettingEditResources;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.gls.GlsIntent;
import kotlin.Unit;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSSettingEditMainActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecQSSettingEditResources editResources;
    public Drawable mScreenShot;
    public final SettingsHelper mSettingsHelper;
    public final int CHILD_ACTIVITY_REQUEST_CODE = EnterpriseContainerCallback.CONTAINER_MOUNT_STATUS;
    public final SecQSSettingEditMainActivity$mFoldStateObserver$1 mFoldStateObserver = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$mFoldStateObserver$1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            SecQSSettingEditMainActivity secQSSettingEditMainActivity = SecQSSettingEditMainActivity.this;
            secQSSettingEditMainActivity.finishActivity(secQSSettingEditMainActivity.CHILD_ACTIVITY_REQUEST_CODE);
            secQSSettingEditMainActivity.finish();
        }
    };
    public final SecQSSettingEditMainActivity$accessibilityDelegate$1 accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$accessibilityDelegate$1
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setSelected(false);
            accessibilityNodeInfoCompat.setContentDescription(SecQSSettingEditMainActivity.this.getString(R.string.editscreen_close_button));
            accessibilityNodeInfoCompat.setClassName("android.widget.Button");
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum POPUPSTRING {
        /* JADX INFO: Fake field, exist only in values array */
        BRIGHTNESS(R.string.qs_panel_detail_popup_menu_always_text, R.string.qs_panel_detail_popup_menu_when_expanded_text),
        /* JADX INFO: Fake field, exist only in values array */
        DEVICEMEDIA(R.string.qs_panel_detail_popup_menu_when_collapsed_text, R.string.qs_panel_detail_popup_menu_dont_show_text),
        /* JADX INFO: Fake field, exist only in values array */
        MULTISIM(R.string.qs_panel_detail_popup_menu_when_expanded_text, R.string.qs_panel_detail_popup_menu_dont_show_text),
        /* JADX INFO: Fake field, exist only in values array */
        HIDE_SMART_VIEW_LARGE_TILE(R.string.qs_panel_detail_popup_menu_when_expanded_text, R.string.qs_panel_detail_popup_menu_dont_show_text);

        private final int first;
        private final int second;

        POPUPSTRING(int i, int i2) {
            this.first = i;
            this.second = i2;
        }

        public final int getFirst() {
            return this.first;
        }

        public final int getSecond() {
            return this.second;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum POPUPTYPE {
        /* JADX INFO: Fake field, exist only in values array */
        BRIGHTNESS(R.id.brightness_control, R.string.sec_brightness_control),
        /* JADX INFO: Fake field, exist only in values array */
        DEVICEMEDIA(R.id.device_control_media, R.string.sec_devices_and_media_control),
        /* JADX INFO: Fake field, exist only in values array */
        MULTISIM(R.id.multi_sim_info, R.string.sec_multi_sim_info_control),
        /* JADX INFO: Fake field, exist only in values array */
        HIDE_SMART_VIEW_LARGE_TILE(R.id.hide_smart_view_tile_info, R.string.sec_smart_view_large_tile_info);


        /* renamed from: id */
        private final int f333id;
        private final int title;

        POPUPTYPE(int i, int i2) {
            this.f333id = i;
            this.title = i2;
        }

        public final int getId() {
            return this.f333id;
        }

        public final int getTitle() {
            return this.title;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$mFoldStateObserver$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$accessibilityDelegate$1] */
    public SecQSSettingEditMainActivity(SecQSSettingEditResources secQSSettingEditResources, SettingsHelper settingsHelper) {
        this.editResources = secQSSettingEditResources;
        this.mSettingsHelper = settingsHelper;
    }

    public final void applyBlur(boolean z) {
        if (!z) {
            QSColorCurve qSColorCurve = new QSColorCurve(getApplicationContext());
            qSColorCurve.setFraction(1.0f);
            getWindow().getDecorView().getRootView().semSetBlurInfo(new SemBlurInfo.Builder(0).setRadius((int) qSColorCurve.radius).setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY).build());
            return;
        }
        if (this.mScreenShot == null) {
            Drawable drawable = this.editResources.mResourcePicker.mCapturedBlurredBackground;
            this.mScreenShot = drawable;
            if (drawable == null) {
                Point point = new Point();
                Display defaultDisplay = ((WindowManager) getApplicationContext().getSystemService("window")).getDefaultDisplay();
                defaultDisplay.getRealSize(point);
                Bitmap screenshot = SemWindowManager.getInstance().screenshot(defaultDisplay.getDisplayId(), 2000, false, new Rect(), point.x / 5, point.y / 5, false, 0, true);
                SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
                QSColorCurve qSColorCurve2 = new QSColorCurve(getApplicationContext());
                qSColorCurve2.setFraction(1.0f);
                semGfxImageFilter.setProportionalSaturation(0.0f);
                semGfxImageFilter.setBlurRadius(qSColorCurve2.radius);
                semGfxImageFilter.setCurveLevel(qSColorCurve2.curve);
                semGfxImageFilter.setCurveMinX(qSColorCurve2.minX);
                semGfxImageFilter.setCurveMaxX(qSColorCurve2.maxX);
                semGfxImageFilter.setCurveMinY(qSColorCurve2.minY);
                semGfxImageFilter.setCurveMaxY(qSColorCurve2.maxY);
                this.mScreenShot = new BitmapDrawable(getResources(), semGfxImageFilter.applyToBitmap(screenshot));
            }
        }
        ((ViewGroup) getWindow().getDecorView()).getRootView().setBackground(this.mScreenShot);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.CHILD_ACTIVITY_REQUEST_CODE) {
            SecQSSettingEditResources secQSSettingEditResources = this.editResources;
            secQSSettingEditResources.isAnotherActivityOverMain = false;
            secQSSettingEditResources.isMainRelaunchedByConfigChanged = false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        TunerService tunerService;
        super.onCreate(bundle);
        setContentView(R.layout.qs_setting_edit_activity);
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mFoldStateObserver);
        if (QpRune.QUICK_TABLET) {
            requireViewById(R.id.root_view).setBackgroundResource(R.drawable.qs_setting_edit_button_ripple);
        }
        final SecQSSettingEditResources secQSSettingEditResources = this.editResources;
        if (secQSSettingEditResources.isPhoneLandscape()) {
            requireViewById(R.id.action_bar).setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height_phone_land));
        } else {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$onCreate$1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    SecQSSettingEditMainActivity secQSSettingEditMainActivity = SecQSSettingEditMainActivity.this;
                    secQSSettingEditMainActivity.editResources.getClass();
                    int sidePadding = SecQSSettingEditResources.getSidePadding(secQSSettingEditMainActivity);
                    int dimensionPixelSize = SecQSSettingEditMainActivity.this.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
                    SecQSSettingEditMainActivity secQSSettingEditMainActivity2 = SecQSSettingEditMainActivity.this;
                    secQSSettingEditMainActivity2.editResources.getClass();
                    int sidePadding2 = SecQSSettingEditResources.getSidePadding(secQSSettingEditMainActivity2);
                    SecQSSettingEditMainActivity secQSSettingEditMainActivity3 = SecQSSettingEditMainActivity.this;
                    secQSSettingEditMainActivity3.editResources.getClass();
                    view.setPadding(sidePadding, dimensionPixelSize, sidePadding2, SecQSSettingEditResources.getBottomPadding(secQSSettingEditMainActivity3, windowInsets));
                    return WindowInsets.CONSUMED;
                }
            });
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.semAddExtensionFlags(16777216);
        getWindow().setAttributes(attributes);
        boolean z = false;
        if (getIntent().getBooleanExtra("user_starts", false)) {
            secQSSettingEditResources.isAnotherActivityOverMain = false;
            secQSSettingEditResources.isMainRelaunchedByConfigChanged = false;
            getIntent().removeExtra("user_starts");
        }
        boolean z2 = secQSSettingEditResources.isMainRelaunchedByConfigChanged;
        Context context = secQSSettingEditResources.mContext;
        if (z2) {
            requireViewById(R.id.qs_setting_edit_activity).setVisibility(4);
        } else {
            SecQSSettingEditResources.POPUPSELECTED.Companion companion = SecQSSettingEditResources.POPUPSELECTED.Companion;
            SecQSSettingEditResources$init$1 secQSSettingEditResources$init$1 = new SecQSSettingEditResources$init$1(secQSSettingEditResources);
            companion.getClass();
            SecQSSettingEditResources.POPUPSELECTED[] values = SecQSSettingEditResources.POPUPSELECTED.values();
            int length = values.length;
            int i = 0;
            while (true) {
                tunerService = secQSSettingEditResources.tunerService;
                if (i >= length) {
                    break;
                }
                SecQSSettingEditResources.POPUPSELECTED popupselected = values[i];
                popupselected.tunerService = tunerService;
                popupselected.mContext = context;
                popupselected.multiSIMController = secQSSettingEditResources.multiSIMController;
                popupselected.updateSALog = secQSSettingEditResources$init$1;
                i++;
            }
            secQSSettingEditResources.tileFullAdapter = new SecQSCustomizerTileAdapter(secQSSettingEditResources.mContext, secQSSettingEditResources.tileHost, false, secQSSettingEditResources.userTracker, secQSSettingEditResources.mainExecutor, secQSSettingEditResources.bgExecutor);
            secQSSettingEditResources.tileTopAdapter = new SecQSCustomizerTileAdapter(secQSSettingEditResources.mContext, secQSSettingEditResources.tileHost, true, secQSSettingEditResources.userTracker, secQSSettingEditResources.mainExecutor, secQSSettingEditResources.bgExecutor);
            tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditResources$init$tunerCallback$1
                @Override // com.android.systemui.tuner.TunerService.Tunable
                public final void onTuningChanged(String str, String str2) {
                    if (QpRune.QUICK_HIDE_TILE_FROM_BAR) {
                        SecQSSettingEditResources secQSSettingEditResources2 = SecQSSettingEditResources.this;
                        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secQSSettingEditResources2.tileFullAdapter;
                        if (secQSCustomizerTileAdapter != null) {
                            secQSCustomizerTileAdapter.updateTiles();
                        }
                        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSSettingEditResources2.tileTopAdapter;
                        if (secQSCustomizerTileAdapter2 != null) {
                            secQSCustomizerTileAdapter2.updateTiles();
                        }
                    }
                }
            }, "hide_smart_view_large_tile_on_panel");
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secQSSettingEditResources.tileFullAdapter;
            if (secQSCustomizerTileAdapter != null) {
                secQSCustomizerTileAdapter.updateTiles();
            }
            SecQSCustomizerTileAdapter secQSCustomizerTileAdapter2 = secQSSettingEditResources.tileTopAdapter;
            if (secQSCustomizerTileAdapter2 != null) {
                secQSCustomizerTileAdapter2.updateTiles();
            }
        }
        boolean z3 = true;
        if (!QpRune.QUICK_PANEL_BLUR || this.mSettingsHelper.isReduceTransparencyEnabled()) {
            getWindow().getDecorView().setBackground(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.open_theme_qp_bg_color, null) | EmergencyPhoneWidget.BG_COLOR));
        } else if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
            applyBlur(false);
        } else if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            applyBlur(true);
        }
        requireViewById(R.id.action_arrow).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$setupButtons$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecQSSettingEditMainActivity.this.finish();
            }
        });
        if (secQSSettingEditResources.isPhoneLandscape()) {
            requireViewById(R.id.edit_tile_type_container).setVisibility(8);
        } else {
            final View requireViewById = requireViewById(R.id.edit_tile_top_container);
            requireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$setupTileEditButtons$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecQSSettingEditMainActivity secQSSettingEditMainActivity = SecQSSettingEditMainActivity.this;
                    secQSSettingEditMainActivity.editResources.isCurrentTopEdit = true;
                    secQSSettingEditMainActivity.startActivityForResult(new Intent(secQSSettingEditMainActivity, (Class<?>) SecQSSettingEditTilesActivity.class), secQSSettingEditMainActivity.CHILD_ACTIVITY_REQUEST_CODE, ActivityOptions.makeCustomAnimation(secQSSettingEditMainActivity, 0, 0).toBundle());
                    SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1018", "QUICK_PANEL_BUTTON");
                }
            });
            requireViewById(R.id.edit_tile_full_container).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$setupTileEditButtons$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecQSSettingEditMainActivity secQSSettingEditMainActivity = SecQSSettingEditMainActivity.this;
                    secQSSettingEditMainActivity.editResources.isCurrentTopEdit = false;
                    secQSSettingEditMainActivity.startActivityForResult(new Intent(secQSSettingEditMainActivity, (Class<?>) SecQSSettingEditTilesActivity.class), secQSSettingEditMainActivity.CHILD_ACTIVITY_REQUEST_CODE, ActivityOptions.makeCustomAnimation(secQSSettingEditMainActivity, 0, 0).toBundle());
                    SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1008", GlsIntent.Extras.EXTRA_LOCATION, "last button", "QUICK_PANEL_BUTTON");
                }
            });
        }
        int i2 = 0;
        for (final POPUPTYPE popuptype : POPUPTYPE.values()) {
            if (SecQSSettingEditResources.POPUPSELECTED.values()[popuptype.ordinal()].isAvailable()) {
                i2++;
                View requireViewById2 = requireViewById(popuptype.getId());
                TextView textView = (TextView) requireViewById2.requireViewById(R.id.button_title);
                if (textView != null) {
                    textView.setText(requireViewById2.getResources().getString(popuptype.getTitle()));
                }
                TextView textView2 = (TextView) requireViewById2.requireViewById(R.id.button_summary);
                if (textView2 != null) {
                    textView2.setText(getString(SecQSSettingEditResources.POPUPSELECTED.values()[popuptype.ordinal()].getSelectedIdx() == 0 ? POPUPSTRING.values()[popuptype.ordinal()].getFirst() : POPUPSTRING.values()[popuptype.ordinal()].getSecond()));
                }
                ListBuilder listBuilder = new ListBuilder();
                POPUPSTRING popupstring = POPUPSTRING.values()[popuptype.ordinal()];
                boolean z4 = SecQSSettingEditResources.POPUPSELECTED.values()[popuptype.ordinal()].getSelectedIdx() == 0;
                listBuilder.add(new SecQSSettingEditPopUpMenu.EditPopUpContent(getString(popupstring.getFirst()), z4));
                listBuilder.add(new SecQSSettingEditPopUpMenu.EditPopUpContent(getString(popupstring.getSecond()), !z4));
                listBuilder.build();
                final SecQSSettingEditPopUpMenu.PopupListAdapter popupListAdapter = new SecQSSettingEditPopUpMenu.PopupListAdapter(getApplicationContext(), listBuilder);
                final View requireViewById3 = requireViewById(popuptype.getId());
                requireViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$makePopUpMenu$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        final SecQSSettingEditPopUpMenu secQSSettingEditPopUpMenu = new SecQSSettingEditPopUpMenu(SecQSSettingEditMainActivity.this.getApplicationContext());
                        View view2 = requireViewById3;
                        final SecQSSettingEditPopUpMenu.PopupListAdapter popupListAdapter2 = popupListAdapter;
                        final SecQSSettingEditMainActivity secQSSettingEditMainActivity = SecQSSettingEditMainActivity.this;
                        final SecQSSettingEditMainActivity.POPUPTYPE popuptype2 = popuptype;
                        secQSSettingEditPopUpMenu.setWidth(-2);
                        secQSSettingEditPopUpMenu.setAnchorView(view2);
                        secQSSettingEditPopUpMenu.setDropDownGravity(8388611);
                        secQSSettingEditPopUpMenu.setAdapter(popupListAdapter2);
                        secQSSettingEditPopUpMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$makePopUpMenu$1$1$1
                            @Override // android.widget.AdapterView.OnItemClickListener
                            public final void onItemClick(AdapterView adapterView, View view3, int i3, long j) {
                                int count = SecQSSettingEditPopUpMenu.PopupListAdapter.this.getCount();
                                for (int i4 = 0; i4 < count; i4++) {
                                    Object item = SecQSSettingEditPopUpMenu.PopupListAdapter.this.getItem(i4);
                                    Intrinsics.checkNotNull(item);
                                    ((SecQSSettingEditPopUpMenu.EditPopUpContent) item).checked = false;
                                }
                                Object item2 = SecQSSettingEditPopUpMenu.PopupListAdapter.this.getItem(i3);
                                Intrinsics.checkNotNull(item2);
                                ((SecQSSettingEditPopUpMenu.EditPopUpContent) item2).checked = true;
                                View requireViewById4 = secQSSettingEditMainActivity.requireViewById(popuptype2.getId());
                                SecQSSettingEditMainActivity secQSSettingEditMainActivity2 = secQSSettingEditMainActivity;
                                SecQSSettingEditMainActivity.POPUPTYPE popuptype3 = popuptype2;
                                TextView textView3 = (TextView) requireViewById4.requireViewById(R.id.button_summary);
                                if (textView3 != null) {
                                    textView3.setText(secQSSettingEditMainActivity2.getString(i3 == 0 ? SecQSSettingEditMainActivity.POPUPSTRING.values()[popuptype3.ordinal()].getFirst() : SecQSSettingEditMainActivity.POPUPSTRING.values()[popuptype3.ordinal()].getSecond()));
                                }
                                SecQSSettingEditResources secQSSettingEditResources2 = secQSSettingEditMainActivity.editResources;
                                int ordinal = popuptype2.ordinal();
                                boolean z5 = i3 == 0;
                                secQSSettingEditResources2.getClass();
                                SecQSSettingEditResources.POPUPSELECTED.values()[ordinal].updateValue(z5);
                                secQSSettingEditPopUpMenu.dismiss();
                            }
                        });
                        secQSSettingEditPopUpMenu.show();
                    }
                });
            }
        }
        Integer[] numArr = {Integer.valueOf(R.drawable.qs_setting_edit_button_top_ripple), Integer.valueOf(R.drawable.qs_setting_edit_button_mid_ripple), Integer.valueOf(R.drawable.qs_setting_edit_button_bottom_ripple)};
        POPUPTYPE[] values2 = POPUPTYPE.values();
        int length2 = values2.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= length2) {
                break;
            }
            POPUPTYPE popuptype2 = values2[i3];
            if (i2 != 0) {
                if (i2 == 1) {
                    if (SecQSSettingEditResources.POPUPSELECTED.values()[popuptype2.ordinal()].isAvailable()) {
                        View requireViewById4 = requireViewById(popuptype2.getId());
                        requireViewById4.setVisibility(0);
                        requireViewById4.setBackgroundResource(R.drawable.qs_setting_edit_button_ripple);
                        break;
                    }
                } else if (SecQSSettingEditResources.POPUPSELECTED.values()[popuptype2.ordinal()].isAvailable()) {
                    char c = (i4 <= 0 || i4 >= i2 + (-1)) ? i4 >= i2 + (-1) ? (char) 2 : (char) 0 : (char) 1;
                    if (c != 2) {
                        int ordinal = popuptype2.ordinal();
                        if (ordinal == 0) {
                            ((LinearLayout) requireViewById(R.id.divider1)).setVisibility(0);
                        } else if (ordinal != 1) {
                            ((LinearLayout) requireViewById(R.id.divider3)).setVisibility(0);
                        } else {
                            ((LinearLayout) requireViewById(R.id.divider2)).setVisibility(0);
                        }
                    }
                    View requireViewById5 = requireViewById(popuptype2.getId());
                    requireViewById5.setVisibility(0);
                    requireViewById5.setBackgroundResource(numArr[c].intValue());
                    i4++;
                }
            } else {
                requireViewById(popuptype2.getId()).setVisibility(8);
            }
            i3++;
        }
        View requireViewById6 = requireViewById(R.id.open_quick_settings);
        TextView textView3 = (TextView) requireViewById6.requireViewById(R.id.button_summary);
        if (textView3 != null) {
            textView3.setVisibility(8);
        }
        TextView textView4 = (TextView) requireViewById6.requireViewById(R.id.button_title);
        if (textView4 != null) {
            textView4.setText(getString(R.string.qs_setting_instant_access));
        }
        requireViewById6.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$setupIsolatedButtons$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecQSSettingEditMainActivity secQSSettingEditMainActivity = SecQSSettingEditMainActivity.this;
                int i5 = SecQSSettingEditMainActivity.$r8$clinit;
                secQSSettingEditMainActivity.getClass();
                secQSSettingEditMainActivity.startActivityForResult(new Intent(secQSSettingEditMainActivity, (Class<?>) SecQSSettingEditDirectlyActivity.class), secQSSettingEditMainActivity.CHILD_ACTIVITY_REQUEST_CODE, ActivityOptions.makeSceneTransitionAnimation(secQSSettingEditMainActivity, new Pair[0]).toBundle());
            }
        });
        View requireViewById7 = requireViewById(R.id.contact_us);
        Point point = DeviceState.sDisplaySize;
        try {
            context.getPackageManager().getPackageInfo("com.samsung.android.voc", 1);
            Log.i("DeviceState", "Installed - ".concat("com.samsung.android.voc"));
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("DeviceState", "NOT Installed - ".concat("com.samsung.android.voc"));
            z3 = false;
        }
        if (z3) {
            try {
                if (context.getPackageManager().getPackageInfoAsUser("com.samsung.android.voc", 0, ActivityManager.getCurrentUser()).versionCode >= 170001000) {
                    z = z3;
                }
            } catch (PackageManager.NameNotFoundException unused2) {
            }
        } else {
            Log.i(secQSSettingEditResources.TAG, "contact us not installed.");
        }
        if (z) {
            TextView textView5 = (TextView) requireViewById7.requireViewById(R.id.button_summary);
            if (textView5 != null) {
                textView5.setVisibility(8);
            }
            TextView textView6 = (TextView) requireViewById7.requireViewById(R.id.button_title);
            if (textView6 != null) {
                textView6.setText(getString(R.string.sec_more_button_menu_contact_us));
            }
            requireViewById7.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$setupIsolatedButtons$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecQSSettingEditResources secQSSettingEditResources2 = SecQSSettingEditMainActivity.this.editResources;
                    Context context2 = secQSSettingEditResources2.mContext;
                    String string = context2.getString(R.string.sec_quick_settings);
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
                    intent.putExtra("packageName", "com.android.systemui.quickpanel");
                    intent.putExtra("appId", "3l25p17305");
                    intent.putExtra("appName", string);
                    intent.putExtra("feedbackType", "ask");
                    try {
                        if (intent.resolveActivity(context2.getPackageManager()) != null) {
                            secQSSettingEditResources2.mActivityStarter.startActivity(intent, true, true);
                        }
                    } catch (ActivityNotFoundException unused3) {
                        Log.e(secQSSettingEditResources2.TAG, "Don't find samsung members package.");
                    }
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1014");
                }
            });
        } else {
            requireViewById7.setVisibility(8);
        }
        ViewCompat.setAccessibilityDelegate(requireViewById(R.id.action_arrow), this.accessibilityDelegate);
        Function2 function2 = new Function2() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity$updateSize$updateFont$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int intValue = ((Number) obj).intValue();
                FontSizeUtils.updateFontSize((TextView) SecQSSettingEditMainActivity.this.findViewById(intValue), ((Number) obj2).intValue(), 0.8f, 1.3f);
                return Unit.INSTANCE;
            }
        };
        Integer valueOf = Integer.valueOf(R.id.qs_setting_top_text);
        Integer valueOf2 = Integer.valueOf(R.dimen.qs_setting_text_size);
        function2.invoke(valueOf, valueOf2);
        function2.invoke(Integer.valueOf(R.id.qs_setting_full_text), valueOf2);
        Integer valueOf3 = Integer.valueOf(R.id.qs_setting_top_edit_text);
        Integer valueOf4 = Integer.valueOf(R.dimen.qs_setting_edit_text_size);
        function2.invoke(valueOf3, valueOf4);
        function2.invoke(Integer.valueOf(R.id.qs_setting_full_edit_text), valueOf4);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        SecQSSettingEditResources secQSSettingEditResources = this.editResources;
        if (secQSSettingEditResources.isAnotherActivityOverMain) {
            secQSSettingEditResources.isMainRelaunchedByConfigChanged = isChangingConfigurations();
        }
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.mFoldStateObserver);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.editResources.isMainRelaunchedByConfigChanged) {
            return;
        }
        requireViewById(R.id.qs_setting_edit_activity).setVisibility(0);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void startActivityForResult(Intent intent, int i, Bundle bundle) {
        this.editResources.isAnotherActivityOverMain = true;
        requireViewById(R.id.qs_setting_edit_activity).setVisibility(4);
        super.startActivityForResult(intent, i, bundle);
    }
}
