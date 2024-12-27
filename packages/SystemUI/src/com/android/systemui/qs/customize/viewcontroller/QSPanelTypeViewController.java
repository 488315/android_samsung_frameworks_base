package com.android.systemui.qs.customize.viewcontroller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSPanelTypeViewController extends ViewControllerBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int FONT_WEIGHT_REGULAR;
    public final int FONT_WEIGHT_SEMIBOLD;
    public final AccessibilityDelegateCompat accesibilityDelegate;
    public TextView buttonDescription;
    public final SecQSSettingEditResources editResources;
    public RadioButton newButton;
    public RadioButton oldButton;
    public final View.OnClickListener onActionArrowClickListener;
    public TextView separatorText;
    public final Lazy settingsHelper$delegate;
    public TextView togetherText;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public QSPanelTypeViewController(Context context, SecQSSettingEditResources secQSSettingEditResources, AccessibilityDelegateCompat accessibilityDelegateCompat, View.OnClickListener onClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.qs_customize_panel_type_layout, (ViewGroup) null, false));
        this.editResources = secQSSettingEditResources;
        this.accesibilityDelegate = accessibilityDelegateCompat;
        this.onActionArrowClickListener = onClickListener;
        this.FONT_WEIGHT_REGULAR = 400;
        this.FONT_WEIGHT_SEMIBOLD = VolteConstants.ErrorCode.BUSY_EVERYWHERE;
        this.settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$settingsHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            }
        });
    }

    public static void updateColor(RadioButton radioButton, boolean z) {
        if (z) {
            radioButton.setButtonTintList(ColorStateList.valueOf(radioButton.getResources().getColor(R.color.qs_edit_content_text_color)));
        } else {
            radioButton.setButtonTintList(ColorStateList.valueOf(radioButton.getResources().getColor(R.color.qs_edit_panel_type_unselected_text_color)));
        }
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void configChanged() {
        setupView();
    }

    public final void setupView() {
        SecPanelSplitHelper.Companion.getClass();
        boolean z = SecPanelSplitHelper.isEnabled;
        T t = this.mView;
        int dimensionPixelSize = t.getResources().getDimensionPixelSize(R.dimen.qs_customize_main_side_padding);
        t.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        t.requireViewById(R.id.action_bar).setMinimumHeight(t.getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height));
        TextView textView = (TextView) t.requireViewById(R.id.action_bar_text);
        textView.setText(R.string.qs_edit_view_type);
        textView.setSingleLine();
        textView.setSelected(true);
        t.requireViewById(R.id.action_arrow).setOnClickListener(this.onActionArrowClickListener);
        View requireViewById = t.requireViewById(R.id.action_arrow);
        if (requireViewById != null) {
            ViewCompat.setAccessibilityDelegate(requireViewById, this.accesibilityDelegate);
        }
        boolean z2 = !z;
        TextView textView2 = (TextView) t.requireViewById(R.id.view_type_text_together);
        Intrinsics.checkNotNull(textView2);
        toUpdateFontTypeface(textView2, z2);
        this.togetherText = textView2;
        TextView textView3 = (TextView) t.requireViewById(R.id.view_type_text_separate);
        Intrinsics.checkNotNull(textView3);
        toUpdateFontTypeface(textView3, z);
        this.separatorText = textView3;
        final RadioButton radioButton = (RadioButton) t.requireViewById(R.id.radio_button_old);
        radioButton.setChecked(z2);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initButton$1$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                RadioButton radioButton2;
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                RadioButton radioButton3 = radioButton;
                if (!z3) {
                    int i = QSPanelTypeViewController.$r8$clinit;
                    qSPanelTypeViewController.getClass();
                    return;
                }
                RadioButton radioButton4 = qSPanelTypeViewController.newButton;
                if (radioButton4 == null) {
                    radioButton4 = null;
                }
                boolean areEqual = Intrinsics.areEqual(radioButton3, radioButton4);
                SharedPreferences.Editor editor = qSPanelTypeViewController.editResources.editor;
                if (editor != null) {
                    editor.putString(SystemUIAnalytics.STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE, areEqual ? "view separately" : "view all");
                    editor.apply();
                }
                ((SettingsHelper) qSPanelTypeViewController.settingsHelper$delegate.getValue()).setPanelSplit(areEqual);
                TextView textView4 = qSPanelTypeViewController.buttonDescription;
                if (textView4 == null) {
                    textView4 = null;
                }
                textView4.setText(areEqual ? R.string.qs_edit_separate_description : R.string.qs_edit_together_description);
                RadioButton radioButton5 = qSPanelTypeViewController.newButton;
                if (radioButton5 == null) {
                    radioButton5 = null;
                }
                if (!Intrinsics.areEqual(radioButton3, radioButton5) ? (radioButton2 = qSPanelTypeViewController.newButton) == null : (radioButton2 = qSPanelTypeViewController.oldButton) == null) {
                    radioButton2 = null;
                }
                radioButton2.setChecked(false);
                RadioButton radioButton6 = qSPanelTypeViewController.newButton;
                RadioButton radioButton7 = radioButton6 == null ? null : radioButton6;
                if (radioButton6 == null) {
                    radioButton6 = null;
                }
                QSPanelTypeViewController.updateColor(radioButton7, radioButton6.isChecked());
                RadioButton radioButton8 = qSPanelTypeViewController.oldButton;
                RadioButton radioButton9 = radioButton8 == null ? null : radioButton8;
                if (radioButton8 == null) {
                    radioButton8 = null;
                }
                QSPanelTypeViewController.updateColor(radioButton9, radioButton8.isChecked());
                TextView textView5 = qSPanelTypeViewController.separatorText;
                if (textView5 == null) {
                    textView5 = null;
                }
                RadioButton radioButton10 = qSPanelTypeViewController.newButton;
                if (radioButton10 == null) {
                    radioButton10 = null;
                }
                qSPanelTypeViewController.toUpdateFontTypeface(textView5, radioButton10.isChecked());
                TextView textView6 = qSPanelTypeViewController.togetherText;
                if (textView6 == null) {
                    textView6 = null;
                }
                RadioButton radioButton11 = qSPanelTypeViewController.oldButton;
                qSPanelTypeViewController.toUpdateFontTypeface(textView6, (radioButton11 != null ? radioButton11 : null).isChecked());
            }
        });
        this.oldButton = radioButton;
        final RadioButton radioButton2 = (RadioButton) t.requireViewById(R.id.radio_button_new);
        radioButton2.setChecked(z);
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initButton$1$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                RadioButton radioButton22;
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                RadioButton radioButton3 = radioButton2;
                if (!z3) {
                    int i = QSPanelTypeViewController.$r8$clinit;
                    qSPanelTypeViewController.getClass();
                    return;
                }
                RadioButton radioButton4 = qSPanelTypeViewController.newButton;
                if (radioButton4 == null) {
                    radioButton4 = null;
                }
                boolean areEqual = Intrinsics.areEqual(radioButton3, radioButton4);
                SharedPreferences.Editor editor = qSPanelTypeViewController.editResources.editor;
                if (editor != null) {
                    editor.putString(SystemUIAnalytics.STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE, areEqual ? "view separately" : "view all");
                    editor.apply();
                }
                ((SettingsHelper) qSPanelTypeViewController.settingsHelper$delegate.getValue()).setPanelSplit(areEqual);
                TextView textView4 = qSPanelTypeViewController.buttonDescription;
                if (textView4 == null) {
                    textView4 = null;
                }
                textView4.setText(areEqual ? R.string.qs_edit_separate_description : R.string.qs_edit_together_description);
                RadioButton radioButton5 = qSPanelTypeViewController.newButton;
                if (radioButton5 == null) {
                    radioButton5 = null;
                }
                if (!Intrinsics.areEqual(radioButton3, radioButton5) ? (radioButton22 = qSPanelTypeViewController.newButton) == null : (radioButton22 = qSPanelTypeViewController.oldButton) == null) {
                    radioButton22 = null;
                }
                radioButton22.setChecked(false);
                RadioButton radioButton6 = qSPanelTypeViewController.newButton;
                RadioButton radioButton7 = radioButton6 == null ? null : radioButton6;
                if (radioButton6 == null) {
                    radioButton6 = null;
                }
                QSPanelTypeViewController.updateColor(radioButton7, radioButton6.isChecked());
                RadioButton radioButton8 = qSPanelTypeViewController.oldButton;
                RadioButton radioButton9 = radioButton8 == null ? null : radioButton8;
                if (radioButton8 == null) {
                    radioButton8 = null;
                }
                QSPanelTypeViewController.updateColor(radioButton9, radioButton8.isChecked());
                TextView textView5 = qSPanelTypeViewController.separatorText;
                if (textView5 == null) {
                    textView5 = null;
                }
                RadioButton radioButton10 = qSPanelTypeViewController.newButton;
                if (radioButton10 == null) {
                    radioButton10 = null;
                }
                qSPanelTypeViewController.toUpdateFontTypeface(textView5, radioButton10.isChecked());
                TextView textView6 = qSPanelTypeViewController.togetherText;
                if (textView6 == null) {
                    textView6 = null;
                }
                RadioButton radioButton11 = qSPanelTypeViewController.oldButton;
                qSPanelTypeViewController.toUpdateFontTypeface(textView6, (radioButton11 != null ? radioButton11 : null).isChecked());
            }
        });
        this.newButton = radioButton2;
        TextView textView4 = (TextView) t.requireViewById(R.id.edit_view_type_description);
        Intrinsics.checkNotNull(textView4);
        textView4.setText(z ? R.string.qs_edit_separate_description : R.string.qs_edit_together_description);
        this.buttonDescription = textView4;
        this.message = 200;
    }

    @Override // com.android.systemui.qs.customize.viewcontroller.ViewControllerBase
    public final void show(Runnable runnable) {
        if (this.isShown) {
            return;
        }
        setupView();
        super.show(runnable);
    }

    public final void toUpdateFontTypeface(TextView textView, boolean z) {
        Typeface create = Typeface.create("sec", 0);
        if (z) {
            textView.setTypeface(Typeface.create(create, this.FONT_WEIGHT_SEMIBOLD, false));
            textView.setTextColor(textView.getResources().getColor(R.color.qs_edit_content_text_color));
        } else {
            textView.setTypeface(Typeface.create(create, this.FONT_WEIGHT_REGULAR, false));
            textView.setTextColor(textView.getResources().getColor(R.color.qs_edit_panel_type_unselected_text_color));
        }
    }
}
