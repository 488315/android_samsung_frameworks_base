package com.android.systemui.qs.customize.viewcontroller;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.QSCPopupButtonController;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSSettingViewController extends ViewControllerBase {
    public final AccessibilityDelegateCompat accessibilityDelegate;
    public final SecQSSettingEditResources editResources;
    public final View.OnClickListener onActionArrowClickListener;
    public final QSCPopupButtonController popupButtonController;
    public final View.OnClickListener topTileEditClickListener;

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

    public QSSettingViewController(Context context, SecQSSettingEditResources secQSSettingEditResources, QSCPopupButtonController qSCPopupButtonController, AccessibilityDelegateCompat accessibilityDelegateCompat, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        super(LayoutInflater.from(context).inflate(R.layout.qs_customize_setting_layout, (ViewGroup) null, false));
        this.editResources = secQSSettingEditResources;
        this.popupButtonController = qSCPopupButtonController;
        this.accessibilityDelegate = accessibilityDelegateCompat;
        this.onActionArrowClickListener = onClickListener;
        this.topTileEditClickListener = onClickListener2;
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void close() {
        QSCPopupButtonController qSCPopupButtonController = this.popupButtonController;
        this.message = qSCPopupButtonController.barChanged ? 300 : null;
        qSCPopupButtonController.barChanged = false;
        super.close();
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void configChanged() {
        setupView();
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void resolveMessage(Integer num) {
        if (num != null && num.intValue() == 200) {
            updateSeparateButtonText();
        }
    }

    public final void setupView() {
        View requireViewById;
        int popOverMargin = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() ? ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getPopOverMargin(getContext()) : ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelSidePadding(getContext());
        this.mView.setPadding(popOverMargin, 0, popOverMargin, 0);
        this.mView.requireViewById(R.id.action_bar).setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height));
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            this.mView.requireViewById(R.id.action_bar).setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.pop_over_layout_edit_action_min_height));
            LinearLayout linearLayout = (LinearLayout) this.mView.findViewById(R.id.action_bar);
            if (linearLayout != null) {
                linearLayout.setGravity(16);
            }
        }
        View requireViewById2 = this.mView.requireViewById(R.id.action_arrow);
        if (requireViewById2 != null) {
            requireViewById2.setOnClickListener(this.onActionArrowClickListener);
        }
        this.mView.requireViewById(R.id.edit_tile_type_container).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController$setupTileEditButtons$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSSettingViewController.this.topTileEditClickListener.onClick(view);
                SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_EDIT_TOP_TILES, SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            }
        });
        LinearLayout linearLayout2 = (LinearLayout) this.mView.requireViewById(R.id.popup_container);
        LinearLayout linearLayout3 = (LinearLayout) this.mView.requireViewById(R.id.isolated_buttons);
        QSCPopupButtonController qSCPopupButtonController = this.popupButtonController;
        qSCPopupButtonController.container = linearLayout2;
        if (linearLayout2 == null) {
            linearLayout2 = null;
        }
        linearLayout2.removeAllViews();
        qSCPopupButtonController.children.clear();
        AbstractList abstractList = (AbstractList) QSCPopupButtonController.POPUPTYPE.$ENTRIES;
        abstractList.getClass();
        AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
        int i = 0;
        while (iteratorImpl.hasNext()) {
            QSCPopupButtonController.POPUPTYPE popuptype = (QSCPopupButtonController.POPUPTYPE) iteratorImpl.next();
            if (qSCPopupButtonController.isAvailableMenu(popuptype.ordinal())) {
                LinearLayout linearLayout4 = qSCPopupButtonController.container;
                if (linearLayout4 == null) {
                    linearLayout4 = null;
                }
                if (linearLayout4.getVisibility() != 0) {
                    LinearLayout linearLayout5 = qSCPopupButtonController.container;
                    if (linearLayout5 == null) {
                        linearLayout5 = null;
                    }
                    linearLayout5.setVisibility(0);
                }
                int i2 = QSCPopupButtonController.WhenMappings.$EnumSwitchMapping$0[popuptype.ordinal()];
                if (i2 == 1) {
                    requireViewById = linearLayout3.requireViewById(R.id.multi_sim);
                } else if (i2 != 2) {
                    i++;
                    LayoutInflater from = LayoutInflater.from(qSCPopupButtonController.context);
                    LinearLayout linearLayout6 = qSCPopupButtonController.container;
                    if (linearLayout6 == null) {
                        linearLayout6 = null;
                    }
                    View inflate = from.inflate(R.layout.qs_customize_main_button, (ViewGroup) linearLayout6, false);
                    LinearLayout linearLayout7 = qSCPopupButtonController.container;
                    if (linearLayout7 == null) {
                        linearLayout7 = null;
                    }
                    linearLayout7.addView(inflate);
                    qSCPopupButtonController.children.add(inflate);
                    inflate.getClass();
                    qSCPopupButtonController.setPopupText(inflate, popuptype);
                    requireViewById = null;
                } else {
                    requireViewById = linearLayout3.requireViewById(R.id.smart_view);
                }
                if (requireViewById != null) {
                    requireViewById.setVisibility(0);
                    qSCPopupButtonController.setPopupText(requireViewById, popuptype);
                }
            }
        }
        Integer[] numArr = {Integer.valueOf(R.drawable.qs_setting_edit_button_top_ripple), Integer.valueOf(R.drawable.qs_setting_edit_button_mid_ripple), Integer.valueOf(R.drawable.qs_setting_edit_button_bottom_ripple)};
        int size = qSCPopupButtonController.children.size();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            View view = (View) qSCPopupButtonController.children.get(i3);
            if (i == 0) {
                break;
            }
            if (i != 1) {
                if (qSCPopupButtonController.isAvailableMenu(i3)) {
                    char c = (i4 <= 0 || i4 >= i + (-1)) ? i4 >= i + (-1) ? (char) 2 : (char) 0 : (char) 1;
                    if (c != 2) {
                        view.requireViewById(R.id.divider).setVisibility(0);
                    }
                    view.setVisibility(0);
                    view.setBackgroundResource(numArr[c].intValue());
                    i4++;
                }
            } else if (qSCPopupButtonController.isAvailableMenu(i3)) {
                view.setVisibility(0);
                view.setBackgroundResource(R.drawable.qs_setting_edit_button_ripple);
                break;
            }
            i3++;
        }
        View requireViewById3 = this.mView.requireViewById(R.id.contact_us);
        if (requireViewById3 != null) {
            SecQSSettingEditResources secQSSettingEditResources = this.editResources;
            if (DeviceState.isAppInstalled(secQSSettingEditResources.context, "com.samsung.android.voc")) {
                try {
                    if (secQSSettingEditResources.context.getPackageManager().getPackageInfoAsUser("com.samsung.android.voc", 0, ActivityManager.getCurrentUser()).versionCode >= 170001000) {
                        TextView textView = (TextView) requireViewById3.requireViewById(R.id.button_summary);
                        if (textView != null) {
                            textView.setVisibility(8);
                        }
                        TextView textView2 = (TextView) requireViewById3.requireViewById(R.id.button_title);
                        if (textView2 != null) {
                            textView2.setText(requireViewById3.getContext().getString(R.string.sec_more_button_menu_contact_us));
                        }
                        requireViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController$setupIsolatedButtons$1$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                SecQSSettingEditResources secQSSettingEditResources2 = QSSettingViewController.this.editResources;
                                secQSSettingEditResources2.getClass();
                                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("voc://view/contactUs"));
                                intent.putExtra("packageName", "com.android.systemui.quickpanel");
                                intent.putExtra("appId", "3l25p17305");
                                intent.putExtra("appName", "Quick Settings/");
                                intent.putExtra("feedbackType", "ask");
                                if (intent.resolveActivity(secQSSettingEditResources2.context.getPackageManager()) == null) {
                                    intent = null;
                                }
                                if (intent != null) {
                                    try {
                                        secQSSettingEditResources2.activityStarter.startActivity(intent, true, true);
                                        Unit unit = Unit.INSTANCE;
                                    } catch (ActivityNotFoundException unused) {
                                        Log.e("SecQSSettingEditResources", "Don't find samsung members package.");
                                    }
                                }
                                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_CONTACT_US_EVENT_ID);
                            }
                        });
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("SecQSSettingEditResources", "contact us not found exception occurred.");
                }
            } else {
                Log.e("SecQSSettingEditResources", "contact us not installed.");
            }
            requireViewById3.setVisibility(8);
        }
        updateSeparateButtonText();
        View requireViewById4 = this.mView.requireViewById(R.id.action_arrow);
        if (requireViewById4 != null) {
            ViewCompat.setAccessibilityDelegate(requireViewById4, this.accessibilityDelegate);
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        if (this.isShown) {
            return;
        }
        setupView();
        ((ViewGroup) this.mView.requireViewById(R.id.separate_panel)).addView(new QSPanelTypeViewController(getContext(), this.editResources, this).view);
        super.show(runnable);
    }

    public final void updateButtonsVisibility(boolean z) {
        if (z || this.editResources.isPhoneLandscape()) {
            this.mView.requireViewById(R.id.edit_tile_type_container).setVisibility(8);
        } else {
            this.mView.requireViewById(R.id.edit_tile_type_container).setVisibility(0);
        }
        LinearLayout linearLayout = this.popupButtonController.container;
        if (linearLayout == null) {
            linearLayout = null;
        }
        linearLayout.setVisibility(z ? 8 : 0);
    }

    public final void updateSeparateButtonText() {
        SecPanelSplitHelper.Companion.getClass();
        boolean z = SecPanelSplitHelper.isEnabled;
        if (z || this.editResources.isPhoneLandscape()) {
            this.mView.requireViewById(R.id.edit_tile_type_container).setVisibility(8);
        } else {
            this.mView.requireViewById(R.id.edit_tile_type_container).setVisibility(0);
        }
        LinearLayout linearLayout = this.popupButtonController.container;
        if (linearLayout == null) {
            linearLayout = null;
        }
        linearLayout.setVisibility(z ? 8 : 0);
    }
}
