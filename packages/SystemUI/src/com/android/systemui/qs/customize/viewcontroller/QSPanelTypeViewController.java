package com.android.systemui.qs.customize.viewcontroller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.AnimType;
import com.android.systemui.qs.customize.view.ClickableLottieAnimView;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPanelTypeViewController extends ViewControllerBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int FONT_WEIGHT_REGULAR;
    public final int FONT_WEIGHT_SEMIBOLD;
    public final TextView buttonDescription;
    public final SecQSSettingEditResources editResources;
    public final ClickableLottieAnimView lottieSeparate;
    public final ClickableLottieAnimView lottieSeparateRTL;
    public final ClickableLottieAnimView lottieTogether;
    public final RadioButton newButton;
    public final RadioButton oldButton;
    public final QSSettingViewController parent;
    public final TextView separatorText;
    public final Lazy settingsHelper$delegate;
    public final TextView togetherText;

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

    public QSPanelTypeViewController(Context context, SecQSSettingEditResources secQSSettingEditResources, QSSettingViewController qSSettingViewController) {
        super(LayoutInflater.from(context).inflate(R.layout.qs_customize_panel_type_layout, (ViewGroup) null, false));
        this.editResources = secQSSettingEditResources;
        this.parent = qSSettingViewController;
        this.FONT_WEIGHT_REGULAR = 400;
        this.FONT_WEIGHT_SEMIBOLD = VolteConstants.ErrorCode.BUSY_EVERYWHERE;
        this.settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new QSPanelTypeViewController$$ExternalSyntheticLambda0());
        SecPanelSplitHelper.Companion.getClass();
        boolean z = SecPanelSplitHelper.isEnabled;
        final T t = this.mView;
        SwitchCompat switchCompat = (SwitchCompat) t.requireViewById(R.id.qs_edit_swap_switch);
        switchCompat.setText(switchCompat.getResources().getText(R.string.qs_edit_swap_swipe_position));
        switchCompat.setChecked(getSettingsHelper$4().isPanelSplitReversed());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$1$2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                RadioButton radioButton = QSPanelTypeViewController.this.newButton;
                if (radioButton == null) {
                    radioButton = null;
                }
                boolean isChecked = radioButton.isChecked();
                QSPanelTypeViewController.this.getSettingsHelper$4().setPanelSplitReversed(z2);
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                qSPanelTypeViewController.playAnim(isChecked, qSPanelTypeViewController.isReversed(z2));
                TextView textView = QSPanelTypeViewController.this.buttonDescription;
                QSPanelTypeViewController.updateText(textView != null ? textView : null, isChecked, z2);
                QSPanelTypeViewController.this.editResources.updateSALog(SystemUIAnalytics.STATUS_QUICK_SETTINGS_REVERSE_SWIPE, z2);
            }
        });
        t.requireViewById(R.id.view_type_container_separate).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initClickListener$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSPanelTypeViewController.this.view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                boolean z2 = view.getId() == R.id.view_type_container_separate;
                RadioButton radioButton = QSPanelTypeViewController.this.newButton;
                if (radioButton == null) {
                    radioButton = null;
                }
                radioButton.setChecked(z2);
                RadioButton radioButton2 = QSPanelTypeViewController.this.oldButton;
                if (radioButton2 == null) {
                    radioButton2 = null;
                }
                radioButton2.setChecked(!z2);
                RadioButton radioButton3 = QSPanelTypeViewController.this.newButton;
                if (radioButton3 == null) {
                    radioButton3 = null;
                }
                if (radioButton3.isChecked()) {
                    TextView textView = (TextView) t.requireViewById(R.id.view_type_text_separate);
                    TextView textView2 = QSPanelTypeViewController.this.separatorText;
                    if (textView2 == null) {
                        textView2 = null;
                    }
                    textView.announceForAccessibility(((Object) textView2.getText()) + ", selected, radio button");
                } else {
                    TextView textView3 = (TextView) t.requireViewById(R.id.view_type_text_together);
                    TextView textView4 = QSPanelTypeViewController.this.togetherText;
                    if (textView4 == null) {
                        textView4 = null;
                    }
                    textView3.announceForAccessibility(((Object) textView4.getText()) + ", selected, radio button");
                }
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                View view2 = t;
                RadioButton radioButton4 = qSPanelTypeViewController.newButton;
                if (radioButton4 == null) {
                    radioButton4 = null;
                }
                QSPanelTypeViewController.initRadioContentDescription(view2, R.id.view_type_text_separate, radioButton4.isChecked());
                QSPanelTypeViewController qSPanelTypeViewController2 = QSPanelTypeViewController.this;
                View view3 = t;
                RadioButton radioButton5 = qSPanelTypeViewController2.oldButton;
                QSPanelTypeViewController.initRadioContentDescription(view3, R.id.view_type_text_together, (radioButton5 != null ? radioButton5 : null).isChecked());
            }
        });
        t.requireViewById(R.id.view_type_container_together).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initClickListener$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSPanelTypeViewController.this.view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                boolean z2 = view.getId() == R.id.view_type_container_separate;
                RadioButton radioButton = QSPanelTypeViewController.this.newButton;
                if (radioButton == null) {
                    radioButton = null;
                }
                radioButton.setChecked(z2);
                RadioButton radioButton2 = QSPanelTypeViewController.this.oldButton;
                if (radioButton2 == null) {
                    radioButton2 = null;
                }
                radioButton2.setChecked(!z2);
                RadioButton radioButton3 = QSPanelTypeViewController.this.newButton;
                if (radioButton3 == null) {
                    radioButton3 = null;
                }
                if (radioButton3.isChecked()) {
                    TextView textView = (TextView) t.requireViewById(R.id.view_type_text_separate);
                    TextView textView2 = QSPanelTypeViewController.this.separatorText;
                    if (textView2 == null) {
                        textView2 = null;
                    }
                    textView.announceForAccessibility(((Object) textView2.getText()) + ", selected, radio button");
                } else {
                    TextView textView3 = (TextView) t.requireViewById(R.id.view_type_text_together);
                    TextView textView4 = QSPanelTypeViewController.this.togetherText;
                    if (textView4 == null) {
                        textView4 = null;
                    }
                    textView3.announceForAccessibility(((Object) textView4.getText()) + ", selected, radio button");
                }
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                View view2 = t;
                RadioButton radioButton4 = qSPanelTypeViewController.newButton;
                if (radioButton4 == null) {
                    radioButton4 = null;
                }
                QSPanelTypeViewController.initRadioContentDescription(view2, R.id.view_type_text_separate, radioButton4.isChecked());
                QSPanelTypeViewController qSPanelTypeViewController2 = QSPanelTypeViewController.this;
                View view3 = t;
                RadioButton radioButton5 = qSPanelTypeViewController2.oldButton;
                QSPanelTypeViewController.initRadioContentDescription(view3, R.id.view_type_text_together, (radioButton5 != null ? radioButton5 : null).isChecked());
            }
        });
        boolean z2 = !z;
        final RadioButton radioButton = (RadioButton) t.requireViewById(R.id.radio_button_old);
        radioButton.setChecked(z2);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initButton$1$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                RadioButton radioButton2 = radioButton;
                radioButton2.getClass();
                QSPanelTypeViewController.access$onCheckedChanged(qSPanelTypeViewController, radioButton2, z3);
            }
        });
        updateColor(radioButton, radioButton.isChecked());
        this.oldButton = radioButton;
        final RadioButton radioButton2 = (RadioButton) t.requireViewById(R.id.radio_button_new);
        radioButton2.setChecked(z);
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initButton$1$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                QSPanelTypeViewController qSPanelTypeViewController = QSPanelTypeViewController.this;
                RadioButton radioButton22 = radioButton2;
                radioButton22.getClass();
                QSPanelTypeViewController.access$onCheckedChanged(qSPanelTypeViewController, radioButton22, z3);
            }
        });
        updateColor(radioButton2, radioButton2.isChecked());
        this.newButton = radioButton2;
        TextView textView = (TextView) t.requireViewById(R.id.view_type_text_together);
        textView.getClass();
        toUpdateFontTypeface(textView, z2);
        this.togetherText = textView;
        TextView textView2 = (TextView) t.requireViewById(R.id.view_type_text_separate);
        textView2.getClass();
        toUpdateFontTypeface(textView2, z);
        this.separatorText = textView2;
        initRadioContentDescription(t, R.id.view_type_text_separate, radioButton2.isChecked());
        initRadioContentDescription(t, R.id.view_type_text_together, radioButton.isChecked());
        TextView textView3 = (TextView) t.requireViewById(R.id.edit_view_type_description);
        textView3.getClass();
        updateText(textView3, z, getSettingsHelper$4().isPanelSplitReversed());
        this.buttonDescription = textView3;
        t.requireViewById(R.id.qs_edit_swap_switch_container).setVisibility(z ? 0 : 8);
        t.requireViewById(R.id.qs_toggle_divider).setVisibility(z ? 0 : 8);
        this.lottieSeparate = initLottieAnim(t, R.id.panel_type_separate_anim, "tablet_settings_separate.json");
        this.lottieSeparateRTL = initLottieAnim(t, R.id.panel_type_separate_rtl_anim, "tablet_settings_separate_rtl.json");
        ClickableLottieAnimView initLottieAnim = initLottieAnim(t, R.id.panel_type_together_anim, "tablet_settings_together.json");
        this.lottieTogether = initLottieAnim;
        playAnim(z, isReversed(getSettingsHelper$4().isPanelSplitReversed()));
        this.message = 200;
        initLottieAnim.animType = AnimType.TOGETHER;
    }

    public static final void access$onCheckedChanged(QSPanelTypeViewController qSPanelTypeViewController, RadioButton radioButton, boolean z) {
        RadioButton radioButton2;
        qSPanelTypeViewController.view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
        if (z) {
            RadioButton radioButton3 = qSPanelTypeViewController.newButton;
            if (radioButton3 == null) {
                radioButton3 = null;
            }
            boolean areEqual = Intrinsics.areEqual(radioButton, radioButton3);
            SharedPreferences.Editor editor = qSPanelTypeViewController.editResources.editor;
            if (editor != null) {
                editor.putString(SystemUIAnalytics.STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE, areEqual ? "view separately" : "view all");
                editor.apply();
            }
            qSPanelTypeViewController.getSettingsHelper$4().setPanelSplit(areEqual);
            TextView textView = qSPanelTypeViewController.buttonDescription;
            if (textView == null) {
                textView = null;
            }
            updateText(textView, areEqual, qSPanelTypeViewController.getSettingsHelper$4().isPanelSplitReversed());
            qSPanelTypeViewController.playAnim(areEqual, qSPanelTypeViewController.isReversed(qSPanelTypeViewController.getSettingsHelper$4().isPanelSplitReversed()));
            T t = qSPanelTypeViewController.mView;
            t.requireViewById(R.id.qs_edit_swap_switch_container).setVisibility(areEqual ? 0 : 8);
            t.requireViewById(R.id.qs_toggle_divider).setVisibility(areEqual ? 0 : 8);
            qSPanelTypeViewController.parent.updateButtonsVisibility(areEqual);
            RadioButton radioButton4 = qSPanelTypeViewController.newButton;
            if (radioButton4 == null) {
                radioButton4 = null;
            }
            if (!Intrinsics.areEqual(radioButton, radioButton4) ? (radioButton2 = qSPanelTypeViewController.newButton) == null : (radioButton2 = qSPanelTypeViewController.oldButton) == null) {
                radioButton2 = null;
            }
            radioButton2.setChecked(false);
            RadioButton radioButton5 = qSPanelTypeViewController.newButton;
            RadioButton radioButton6 = radioButton5 == null ? null : radioButton5;
            if (radioButton5 == null) {
                radioButton5 = null;
            }
            updateColor(radioButton6, radioButton5.isChecked());
            RadioButton radioButton7 = qSPanelTypeViewController.oldButton;
            RadioButton radioButton8 = radioButton7 == null ? null : radioButton7;
            if (radioButton7 == null) {
                radioButton7 = null;
            }
            updateColor(radioButton8, radioButton7.isChecked());
            TextView textView2 = qSPanelTypeViewController.separatorText;
            if (textView2 == null) {
                textView2 = null;
            }
            RadioButton radioButton9 = qSPanelTypeViewController.newButton;
            if (radioButton9 == null) {
                radioButton9 = null;
            }
            qSPanelTypeViewController.toUpdateFontTypeface(textView2, radioButton9.isChecked());
            TextView textView3 = qSPanelTypeViewController.togetherText;
            if (textView3 == null) {
                textView3 = null;
            }
            RadioButton radioButton10 = qSPanelTypeViewController.oldButton;
            qSPanelTypeViewController.toUpdateFontTypeface(textView3, (radioButton10 != null ? radioButton10 : null).isChecked());
        }
    }

    public static ClickableLottieAnimView initLottieAnim(View view, int i, String str) {
        ClickableLottieAnimView clickableLottieAnimView = (ClickableLottieAnimView) view.requireViewById(i);
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            clickableLottieAnimView.setAnimation(str);
        }
        clickableLottieAnimView.setUseCompositionFrameRate(true);
        return clickableLottieAnimView;
    }

    public static void initRadioContentDescription(View view, int i, boolean z) {
        TextView textView = (TextView) view.requireViewById(i);
        CharSequence text = textView.getText();
        textView.setContentDescription(((Object) text) + ", " + (z ? "selected" : "not selected") + ", radio button");
    }

    public static void updateColor(RadioButton radioButton, boolean z) {
        if (z) {
            radioButton.setButtonTintList(ColorStateList.valueOf(radioButton.getResources().getColor(R.color.qs_edit_content_text_color)));
        } else {
            radioButton.setButtonTintList(ColorStateList.valueOf(radioButton.getResources().getColor(R.color.qs_edit_panel_type_unselected_text_color)));
        }
    }

    public static void updateText(TextView textView, boolean z, boolean z2) {
        textView.setText((z2 && z) ? R.string.qs_edit_separate_description_rtl : z ? R.string.qs_edit_separate_description : R.string.qs_edit_together_description);
    }

    public final SettingsHelper getSettingsHelper$4() {
        return (SettingsHelper) this.settingsHelper$delegate.getValue();
    }

    public final boolean isReversed(boolean z) {
        boolean z2 = getContext().getResources().getConfiguration().getLayoutDirection() == 1;
        return (z2 && !z) || (!z2 && z);
    }

    public final void playAnim(boolean z, boolean z2) {
        ClickableLottieAnimView clickableLottieAnimView = this.lottieSeparateRTL;
        ClickableLottieAnimView clickableLottieAnimView2 = this.lottieSeparate;
        ClickableLottieAnimView clickableLottieAnimView3 = this.lottieTogether;
        if (z2 && z) {
            (clickableLottieAnimView3 == null ? null : clickableLottieAnimView3).cancelAnimation();
            if (clickableLottieAnimView3 == null) {
                clickableLottieAnimView3 = null;
            }
            clickableLottieAnimView3.setVisibility(4);
            (clickableLottieAnimView2 == null ? null : clickableLottieAnimView2).cancelAnimation();
            if (clickableLottieAnimView2 == null) {
                clickableLottieAnimView2 = null;
            }
            clickableLottieAnimView2.setVisibility(4);
            (clickableLottieAnimView == null ? null : clickableLottieAnimView).playAnimation();
            if (clickableLottieAnimView == null) {
                clickableLottieAnimView = null;
            }
            clickableLottieAnimView.setVisibility(0);
            return;
        }
        if (z) {
            (clickableLottieAnimView3 == null ? null : clickableLottieAnimView3).cancelAnimation();
            if (clickableLottieAnimView3 == null) {
                clickableLottieAnimView3 = null;
            }
            clickableLottieAnimView3.setVisibility(4);
            (clickableLottieAnimView == null ? null : clickableLottieAnimView).cancelAnimation();
            if (clickableLottieAnimView == null) {
                clickableLottieAnimView = null;
            }
            clickableLottieAnimView.setVisibility(4);
            (clickableLottieAnimView2 == null ? null : clickableLottieAnimView2).playAnimation();
            if (clickableLottieAnimView2 == null) {
                clickableLottieAnimView2 = null;
            }
            clickableLottieAnimView2.setVisibility(0);
            return;
        }
        (clickableLottieAnimView2 == null ? null : clickableLottieAnimView2).cancelAnimation();
        if (clickableLottieAnimView2 == null) {
            clickableLottieAnimView2 = null;
        }
        clickableLottieAnimView2.setVisibility(4);
        (clickableLottieAnimView == null ? null : clickableLottieAnimView).cancelAnimation();
        if (clickableLottieAnimView == null) {
            clickableLottieAnimView = null;
        }
        clickableLottieAnimView.setVisibility(4);
        (clickableLottieAnimView3 == null ? null : clickableLottieAnimView3).playAnimation();
        if (clickableLottieAnimView3 == null) {
            clickableLottieAnimView3 = null;
        }
        clickableLottieAnimView3.setVisibility(0);
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
