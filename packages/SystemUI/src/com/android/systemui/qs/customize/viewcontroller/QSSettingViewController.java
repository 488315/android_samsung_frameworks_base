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
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.qs.customize.QSCPopupButtonController;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class QSSettingViewController extends ViewControllerBase {
    public final AccessibilityDelegateCompat accessibilityDelegate;
    public final SecQSSettingEditResources editResources;
    public final View.OnClickListener onActionArrowClickListener;
    public final View.OnClickListener panelTypeEditClickListener;
    public final QSCPopupButtonController popupButtonController;
    public final View.OnClickListener topTileEditClickListener;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QSSettingViewController(Context context, SecQSSettingEditResources secQSSettingEditResources, QSCPopupButtonController qSCPopupButtonController, AccessibilityDelegateCompat accessibilityDelegateCompat, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, View.OnClickListener onClickListener3) {
        super(LayoutInflater.from(context).inflate(R.layout.qs_customize_setting_layout, (ViewGroup) null, false));
        this.editResources = secQSSettingEditResources;
        this.popupButtonController = qSCPopupButtonController;
        this.accessibilityDelegate = accessibilityDelegateCompat;
        this.onActionArrowClickListener = onClickListener;
        this.topTileEditClickListener = onClickListener2;
        this.panelTypeEditClickListener = onClickListener3;
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
        setupView$1();
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void resolveMessage(Integer num) {
        if (num != null && num.intValue() == 200) {
            updateSeparateButtonText();
        }
    }

    public final void setupView$1() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_customize_main_side_padding);
        this.mView.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        this.mView.requireViewById(R.id.action_bar).setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height));
        View requireViewById = this.mView.requireViewById(R.id.action_arrow);
        if (requireViewById != null) {
            requireViewById.setOnClickListener(this.onActionArrowClickListener);
        }
        this.mView.requireViewById(R.id.top_tile_edit_button_container).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController$setupTileEditButtons$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSSettingViewController.this.topTileEditClickListener.onClick(view);
                SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_EDIT_TOP_TILES, SystemUIAnalytics.RUNESTONE_LABEL_QP_BUTTON);
            }
        });
        LinearLayout linearLayout = (LinearLayout) this.mView.requireViewById(R.id.popup_container);
        LinearLayout linearLayout2 = (LinearLayout) this.mView.requireViewById(R.id.isolated_buttons);
        QSCPopupButtonController qSCPopupButtonController = this.popupButtonController;
        qSCPopupButtonController.container = linearLayout;
        linearLayout.removeAllViews();
        qSCPopupButtonController.children.clear();
        AbstractList abstractList = (AbstractList) QSCPopupButtonController.POPUPTYPE.$ENTRIES;
        abstractList.getClass();
        AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
        int i = 0;
        while (iteratorImpl.hasNext()) {
            QSCPopupButtonController.POPUPTYPE popuptype = (QSCPopupButtonController.POPUPTYPE) iteratorImpl.next();
            if (qSCPopupButtonController.isAvailableMenu(popuptype.ordinal())) {
                LinearLayout linearLayout3 = qSCPopupButtonController.container;
                View view = null;
                if (linearLayout3 == null) {
                    linearLayout3 = null;
                }
                if (linearLayout3.getVisibility() != 0) {
                    LinearLayout linearLayout4 = qSCPopupButtonController.container;
                    if (linearLayout4 == null) {
                        linearLayout4 = null;
                    }
                    linearLayout4.setVisibility(0);
                }
                int i2 = QSCPopupButtonController.WhenMappings.$EnumSwitchMapping$0[popuptype.ordinal()];
                if (i2 == 1) {
                    view = linearLayout2.requireViewById(R.id.multi_sim);
                } else if (i2 != 2) {
                    i++;
                    LayoutInflater from = LayoutInflater.from(qSCPopupButtonController.context);
                    LinearLayout linearLayout5 = qSCPopupButtonController.container;
                    if (linearLayout5 == null) {
                        linearLayout5 = null;
                    }
                    View inflate = from.inflate(R.layout.qs_customize_main_button, (ViewGroup) linearLayout5, false);
                    LinearLayout linearLayout6 = qSCPopupButtonController.container;
                    if (linearLayout6 == null) {
                        linearLayout6 = null;
                    }
                    linearLayout6.addView(inflate);
                    qSCPopupButtonController.children.add(inflate);
                    Intrinsics.checkNotNull(inflate);
                    qSCPopupButtonController.setPopupText(inflate, popuptype);
                } else {
                    view = linearLayout2.requireViewById(R.id.smart_view);
                }
                if (view != null) {
                    view.setVisibility(0);
                    qSCPopupButtonController.setPopupText(view, popuptype);
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
            View view2 = (View) qSCPopupButtonController.children.get(i3);
            if (i == 0) {
                break;
            }
            if (i != 1) {
                if (qSCPopupButtonController.isAvailableMenu(i3)) {
                    char c = (i4 <= 0 || i4 >= i + (-1)) ? i4 >= i + (-1) ? (char) 2 : (char) 0 : (char) 1;
                    if (c != 2) {
                        view2.requireViewById(R.id.divider).setVisibility(0);
                    }
                    view2.setVisibility(0);
                    view2.setBackgroundResource(numArr[c].intValue());
                    i4++;
                }
            } else if (qSCPopupButtonController.isAvailableMenu(i3)) {
                view2.setVisibility(0);
                view2.setBackgroundResource(R.drawable.qs_setting_edit_button_ripple);
                break;
            }
            i3++;
        }
        View requireViewById2 = this.mView.requireViewById(R.id.separate_panel);
        if (requireViewById2 != null) {
            TextView textView = (TextView) requireViewById2.requireViewById(R.id.button_title);
            if (textView != null) {
                textView.setText(requireViewById2.getResources().getString(R.string.qs_edit_view_type));
            }
            requireViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController$setupIsolatedButtons$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    QSSettingViewController.this.panelTypeEditClickListener.onClick(view3);
                }
            });
            requireViewById2.setClickable(true);
        }
        View requireViewById3 = this.mView.requireViewById(R.id.contact_us);
        if (requireViewById3 != null) {
            SecQSSettingEditResources secQSSettingEditResources = this.editResources;
            if (DeviceState.isAppInstalled(secQSSettingEditResources.context, "com.samsung.android.voc")) {
                try {
                    if (secQSSettingEditResources.context.getPackageManager().getPackageInfoAsUser("com.samsung.android.voc", 0, ActivityManager.getCurrentUser()).versionCode >= 170001000) {
                        TextView textView2 = (TextView) requireViewById3.requireViewById(R.id.button_summary);
                        if (textView2 != null) {
                            textView2.setVisibility(8);
                        }
                        TextView textView3 = (TextView) requireViewById3.requireViewById(R.id.button_title);
                        if (textView3 != null) {
                            textView3.setText(requireViewById3.getContext().getString(R.string.sec_more_button_menu_contact_us));
                        }
                        requireViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController$setupIsolatedButtons$2$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view3) {
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
        Function2 function2 = new Function2() { // from class: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController$updateFontSize$updateFont$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                View view3;
                int intValue = ((Number) obj).intValue();
                int intValue2 = ((Number) obj2).intValue();
                view3 = ((ViewController) QSSettingViewController.this).mView;
                FontSizeUtils.updateFontSize((TextView) view3.requireViewById(intValue), intValue2, 0.8f, 1.3f);
                return Unit.INSTANCE;
            }
        };
        function2.invoke(Integer.valueOf(R.id.qs_setting_top_text), Integer.valueOf(R.dimen.qs_setting_text_size));
        function2.invoke(Integer.valueOf(R.id.qs_setting_top_edit_text), Integer.valueOf(R.dimen.qs_setting_edit_text_size));
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        if (this.isShown) {
            return;
        }
        setupView$1();
        super.show(runnable);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSeparateButtonText() {
        /*
            r7 = this;
            com.android.systemui.shade.SecPanelSplitHelper$Companion r0 = com.android.systemui.shade.SecPanelSplitHelper.Companion
            r0.getClass()
            boolean r0 = com.android.systemui.shade.SecPanelSplitHelper.isEnabled
            r1 = 2131362795(0x7f0a03eb, float:1.834538E38)
            r2 = 8
            r3 = 0
            if (r0 != 0) goto L39
            com.android.systemui.qs.customize.SecQSSettingEditResources r4 = r7.editResources
            r4.getClass()
            boolean r5 = com.android.systemui.QpRune.QUICK_TABLET
            if (r5 == 0) goto L1f
            int r5 = android.util.DisplayMetrics.DENSITY_DEVICE_STABLE
            r6 = 213(0xd5, float:2.98E-43)
            if (r5 <= r6) goto L1f
            goto L2f
        L1f:
            android.content.Context r4 = r4.context
            android.content.res.Resources r4 = r4.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r4 = r4.orientation
            r5 = 2
            if (r4 != r5) goto L2f
            goto L39
        L2f:
            T extends android.view.View r4 = r7.mView
            android.view.View r1 = r4.requireViewById(r1)
            r1.setVisibility(r3)
            goto L42
        L39:
            T extends android.view.View r4 = r7.mView
            android.view.View r1 = r4.requireViewById(r1)
            r1.setVisibility(r2)
        L42:
            T extends android.view.View r1 = r7.mView
            r4 = 2131364570(0x7f0a0ada, float:1.834898E38)
            android.view.View r1 = r1.requireViewById(r4)
            if (r1 == 0) goto L72
            r4 = 2131362351(0x7f0a022f, float:1.834448E38)
            android.view.View r4 = r1.requireViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            if (r0 == 0) goto L64
            android.content.res.Resources r1 = r1.getResources()
            r5 = 2131955261(0x7f130e3d, float:1.9547044E38)
            java.lang.String r1 = r1.getString(r5)
            goto L6f
        L64:
            android.content.res.Resources r1 = r1.getResources()
            r5 = 2131955266(0x7f130e42, float:1.9547055E38)
            java.lang.String r1 = r1.getString(r5)
        L6f:
            r4.setText(r1)
        L72:
            T extends android.view.View r1 = r7.mView
            r4 = 2131365181(0x7f0a0d3d, float:1.835022E38)
            android.view.View r1 = r1.requireViewById(r4)
            if (r1 != 0) goto L7e
            goto L86
        L7e:
            if (r0 == 0) goto L82
            r4 = r2
            goto L83
        L82:
            r4 = r3
        L83:
            r1.setVisibility(r4)
        L86:
            com.android.systemui.qs.customize.QSCPopupButtonController r7 = r7.popupButtonController
            android.widget.LinearLayout r7 = r7.container
            if (r7 != 0) goto L8d
            r7 = 0
        L8d:
            if (r0 == 0) goto L90
            goto L91
        L90:
            r2 = r3
        L91:
            r7.setVisibility(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.viewcontroller.QSSettingViewController.updateSeparateButtonText():void");
    }
}
