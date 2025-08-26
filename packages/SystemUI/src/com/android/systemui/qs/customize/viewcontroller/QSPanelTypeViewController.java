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
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.model.LottieCompositionCache;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.qs.customize.view.AnimType;
import com.android.systemui.qs.customize.view.ClickableLottieAnimView;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.File;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class QSPanelTypeViewController extends ViewControllerBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int FONT_WEIGHT_REGULAR;
    public final int FONT_WEIGHT_SEMIBOLD;
    public final TextView buttonDescription;
    public final SecQSSettingEditResources editResources;
    public final boolean isSplit;
    public final ClickableLottieAnimView lottieSeparate;
    public final ClickableLottieAnimView lottieSeparateRTL;
    public final ClickableLottieAnimView lottieTogether;
    public final RadioButton newButton;
    public final String notSelected;
    public final RadioButton oldButton;
    public final QSSettingViewController parent;
    public final String selected;
    public final TextView separatorText;
    public final Lazy settingsHelper$delegate;
    public final TextView togetherText;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class RoleDescriptionAccessibilityDelegate extends AccessibilityDelegateCompat {
        public final String mRoleDescription;

        public RoleDescriptionAccessibilityDelegate(String str) {
            this.mRoleDescription = str;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setRoleDescription(this.mRoleDescription);
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
        this.selected = getResources().getString(R.string.accessibility_quick_settings_selected);
        this.notSelected = getResources().getString(R.string.accessibility_quick_settings_not_selected);
        SecPanelSplitHelper.Companion.getClass();
        this.isSplit = SecPanelSplitHelper.isEnabled;
        Context context2 = this.mView.getContext();
        ((HashMap) LottieCompositionFactory.taskCache).clear();
        LottieCompositionCache.INSTANCE.cache.trimToSize(-1);
        File fileParentDir = L.networkCache(context2).parentDir();
        if (fileParentDir.exists()) {
            File[] fileArrListFiles = fileParentDir.listFiles();
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                for (File file : fileParentDir.listFiles()) {
                    file.delete();
                }
            }
            fileParentDir.delete();
        }
        final View view = this.mView;
        SwitchCompat switchCompat = (SwitchCompat) view.requireViewById(R.id.qs_edit_swap_switch);
        switchCompat.setText(switchCompat.getResources().getText(R.string.qs_edit_swap_swipe_position));
        switchCompat.setChecked(getSettingsHelper$5().isPanelSplitReversed());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$1$2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("reversedOption changed : ", " -> ", "QSPanelTypeViewController", !z, z);
                RadioButton radioButton = this.this$0.newButton;
                if (radioButton == null) {
                    radioButton = null;
                }
                boolean zIsChecked = radioButton.isChecked();
                this.this$0.getSettingsHelper$5().setPanelSplitReversed(z);
                QSPanelTypeViewController qSPanelTypeViewController = this.this$0;
                qSPanelTypeViewController.playAnim(zIsChecked, qSPanelTypeViewController.isReversed(z));
                TextView textView = this.this$0.buttonDescription;
                QSPanelTypeViewController.updateText(textView != null ? textView : null, zIsChecked, z);
                this.this$0.editResources.updateSALog(SystemUIAnalytics.STATUS_QUICK_SETTINGS_REVERSE_SWIPE, z);
            }
        });
        view.requireViewById(R.id.view_type_container_separate).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initClickListener$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.this$0.view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                boolean z = view2.getId() == R.id.view_type_container_separate;
                RadioButton radioButton = this.this$0.newButton;
                if (radioButton == null) {
                    radioButton = null;
                }
                radioButton.setChecked(z);
                RadioButton radioButton2 = this.this$0.oldButton;
                if (radioButton2 == null) {
                    radioButton2 = null;
                }
                radioButton2.setChecked(!z);
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("splitOption changed : ", " -> ", "QSPanelTypeViewController", !z, z);
                QSPanelTypeViewController qSPanelTypeViewController = this.this$0;
                qSPanelTypeViewController.playAnim(z, qSPanelTypeViewController.isReversed(qSPanelTypeViewController.getSettingsHelper$5().isPanelSplitReversed()));
                QSPanelTypeViewController qSPanelTypeViewController2 = this.this$0;
                View view3 = view;
                RadioButton radioButton3 = qSPanelTypeViewController2.newButton;
                if (radioButton3 == null) {
                    radioButton3 = null;
                }
                qSPanelTypeViewController2.initRadioContentDescription(view3, R.id.view_type_text_separate, radioButton3.isChecked());
                QSPanelTypeViewController qSPanelTypeViewController3 = this.this$0;
                View view4 = view;
                RadioButton radioButton4 = qSPanelTypeViewController3.oldButton;
                if (radioButton4 == null) {
                    radioButton4 = null;
                }
                qSPanelTypeViewController3.initRadioContentDescription(view4, R.id.view_type_text_together, radioButton4.isChecked());
                RadioButton radioButton5 = this.this$0.newButton;
                if ((radioButton5 != null ? radioButton5 : null).isChecked()) {
                    ((TextView) view.requireViewById(R.id.view_type_text_separate)).sendAccessibilityEvent(8);
                } else {
                    ((TextView) view.requireViewById(R.id.view_type_text_together)).sendAccessibilityEvent(8);
                }
            }
        });
        view.requireViewById(R.id.view_type_container_together).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initClickListener$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.this$0.view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                boolean z = view2.getId() == R.id.view_type_container_separate;
                RadioButton radioButton = this.this$0.newButton;
                if (radioButton == null) {
                    radioButton = null;
                }
                radioButton.setChecked(z);
                RadioButton radioButton2 = this.this$0.oldButton;
                if (radioButton2 == null) {
                    radioButton2 = null;
                }
                radioButton2.setChecked(!z);
                KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("splitOption changed : ", " -> ", "QSPanelTypeViewController", !z, z);
                QSPanelTypeViewController qSPanelTypeViewController = this.this$0;
                qSPanelTypeViewController.playAnim(z, qSPanelTypeViewController.isReversed(qSPanelTypeViewController.getSettingsHelper$5().isPanelSplitReversed()));
                QSPanelTypeViewController qSPanelTypeViewController2 = this.this$0;
                View view3 = view;
                RadioButton radioButton3 = qSPanelTypeViewController2.newButton;
                if (radioButton3 == null) {
                    radioButton3 = null;
                }
                qSPanelTypeViewController2.initRadioContentDescription(view3, R.id.view_type_text_separate, radioButton3.isChecked());
                QSPanelTypeViewController qSPanelTypeViewController3 = this.this$0;
                View view4 = view;
                RadioButton radioButton4 = qSPanelTypeViewController3.oldButton;
                if (radioButton4 == null) {
                    radioButton4 = null;
                }
                qSPanelTypeViewController3.initRadioContentDescription(view4, R.id.view_type_text_together, radioButton4.isChecked());
                RadioButton radioButton5 = this.this$0.newButton;
                if ((radioButton5 != null ? radioButton5 : null).isChecked()) {
                    ((TextView) view.requireViewById(R.id.view_type_text_separate)).sendAccessibilityEvent(8);
                } else {
                    ((TextView) view.requireViewById(R.id.view_type_text_together)).sendAccessibilityEvent(8);
                }
            }
        });
        RadioButton radioButtonInitButton = initButton(view, R.id.radio_button_old, !this.isSplit);
        this.oldButton = radioButtonInitButton;
        RadioButton radioButtonInitButton2 = initButton(view, R.id.radio_button_new, this.isSplit);
        this.newButton = radioButtonInitButton2;
        boolean z = !this.isSplit;
        TextView textView = (TextView) view.requireViewById(R.id.view_type_text_together);
        textView.getClass();
        toUpdateFontTypeface(textView, z);
        this.togetherText = textView;
        boolean z2 = this.isSplit;
        TextView textView2 = (TextView) view.requireViewById(R.id.view_type_text_separate);
        textView2.getClass();
        toUpdateFontTypeface(textView2, z2);
        this.separatorText = textView2;
        initRadioContentDescription(view, R.id.view_type_text_separate, radioButtonInitButton2.isChecked());
        initRadioContentDescription(view, R.id.view_type_text_together, radioButtonInitButton.isChecked());
        boolean z3 = this.isSplit;
        TextView textView3 = (TextView) view.requireViewById(R.id.edit_view_type_description);
        textView3.getClass();
        updateText(textView3, z3, getSettingsHelper$5().isPanelSplitReversed());
        this.buttonDescription = textView3;
        boolean z4 = this.isSplit;
        view.requireViewById(R.id.qs_edit_swap_switch_container).setVisibility(z4 ? 0 : 8);
        view.requireViewById(R.id.qs_toggle_divider).setVisibility(z4 ? 0 : 8);
        T t = this.mView;
        t.getClass();
        this.lottieSeparate = initLottieAnim(t, R.id.panel_type_separate_anim, R.raw.tablet_settings_separate);
        this.lottieSeparateRTL = initLottieAnim(t, R.id.panel_type_separate_rtl_anim, R.raw.tablet_settings_separate_rtl);
        this.lottieTogether = initLottieAnim(t, R.id.panel_type_together_anim, R.raw.tablet_settings_together);
        playAnim(this.isSplit, isReversed(getSettingsHelper$5().isPanelSplitReversed()));
        this.message = 200;
        ClickableLottieAnimView clickableLottieAnimView = this.lottieTogether;
        (clickableLottieAnimView != null ? clickableLottieAnimView : null).animType = AnimType.TOGETHER;
    }

    public static final void access$onCheckedChanged(QSPanelTypeViewController qSPanelTypeViewController, RadioButton radioButton, boolean z) {
        RadioButton radioButton2;
        qSPanelTypeViewController.view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
        if (z) {
            RadioButton radioButton3 = qSPanelTypeViewController.newButton;
            if (radioButton3 == null) {
                radioButton3 = null;
            }
            boolean zAreEqual = Intrinsics.areEqual(radioButton, radioButton3);
            SharedPreferences.Editor editor = qSPanelTypeViewController.editResources.editor;
            if (editor != null) {
                editor.putString(SystemUIAnalytics.STATUS_NOTIFICATION_AND_QUICK_SETTINGS_VIEW_TYPE, zAreEqual ? "view separately" : "view all");
                editor.apply();
            }
            qSPanelTypeViewController.getSettingsHelper$5().setPanelSplit(zAreEqual);
            TextView textView = qSPanelTypeViewController.buttonDescription;
            if (textView == null) {
                textView = null;
            }
            updateText(textView, zAreEqual, qSPanelTypeViewController.getSettingsHelper$5().isPanelSplitReversed());
            qSPanelTypeViewController.playAnim(zAreEqual, qSPanelTypeViewController.isReversed(qSPanelTypeViewController.getSettingsHelper$5().isPanelSplitReversed()));
            T t = qSPanelTypeViewController.mView;
            t.requireViewById(R.id.qs_edit_swap_switch_container).setVisibility(zAreEqual ? 0 : 8);
            t.requireViewById(R.id.qs_toggle_divider).setVisibility(zAreEqual ? 0 : 8);
            qSPanelTypeViewController.parent.updateButtonsVisibility(zAreEqual);
            RadioButton radioButton4 = qSPanelTypeViewController.newButton;
            if (radioButton4 == null) {
                radioButton4 = null;
            }
            if (!Intrinsics.areEqual(radioButton, radioButton4) ? (radioButton2 = qSPanelTypeViewController.newButton) == null : (radioButton2 = qSPanelTypeViewController.oldButton) == null) {
                radioButton2 = null;
            }
            radioButton2.setChecked(false);
            if (!DeviceState.isOpenTheme(qSPanelTypeViewController.getContext())) {
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
            }
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

    public static ClickableLottieAnimView initLottieAnim(View view, int i, int i2) {
        ClickableLottieAnimView clickableLottieAnimView = (ClickableLottieAnimView) view.requireViewById(i);
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            clickableLottieAnimView.cancelAnimation();
            clickableLottieAnimView.setAnimation(i2);
        }
        clickableLottieAnimView.setUseCompositionFrameRate(true);
        return clickableLottieAnimView;
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

    public final SettingsHelper getSettingsHelper$5() {
        return (SettingsHelper) this.settingsHelper$delegate.getValue();
    }

    public final RadioButton initButton(View view, int i, boolean z) {
        final RadioButton radioButton = (RadioButton) view.requireViewById(i);
        radioButton.setChecked(z);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.viewcontroller.QSPanelTypeViewController$initButton$1$1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                QSPanelTypeViewController qSPanelTypeViewController = this.this$0;
                RadioButton radioButton2 = radioButton;
                radioButton2.getClass();
                QSPanelTypeViewController.access$onCheckedChanged(qSPanelTypeViewController, radioButton2, z2);
            }
        });
        if (!DeviceState.isOpenTheme(radioButton.getContext())) {
            updateColor(radioButton, radioButton.isChecked());
        }
        return radioButton;
    }

    public final void initRadioContentDescription(View view, int i, boolean z) {
        TextView textView = (TextView) view.requireViewById(i);
        CharSequence text = textView.getText();
        textView.setContentDescription(((Object) text) + ", " + (z ? this.selected : this.notSelected));
        ViewCompat.setAccessibilityDelegate(textView, new RoleDescriptionAccessibilityDelegate(view.getContext().getResources().getString(R.string.qs_custom_action_radio_button)));
    }

    public final boolean isReversed(boolean z) {
        boolean z2 = getContext().getResources().getConfiguration().getLayoutDirection() == 1;
        return (z2 && !z) || (!z2 && z);
    }

    public final void playAnim(boolean z, boolean z2) {
        if (z2 && z) {
            ClickableLottieAnimView clickableLottieAnimView = this.lottieSeparateRTL;
            if (clickableLottieAnimView == null) {
                clickableLottieAnimView = null;
            }
            clickableLottieAnimView.playAnimation();
            ClickableLottieAnimView clickableLottieAnimView2 = this.lottieSeparateRTL;
            if (clickableLottieAnimView2 == null) {
                clickableLottieAnimView2 = null;
            }
            clickableLottieAnimView2.setVisibility(0);
            ClickableLottieAnimView clickableLottieAnimView3 = this.lottieTogether;
            if (clickableLottieAnimView3 == null) {
                clickableLottieAnimView3 = null;
            }
            clickableLottieAnimView3.pauseAnimation();
            ClickableLottieAnimView clickableLottieAnimView4 = this.lottieSeparate;
            if (clickableLottieAnimView4 == null) {
                clickableLottieAnimView4 = null;
            }
            clickableLottieAnimView4.pauseAnimation();
            ClickableLottieAnimView clickableLottieAnimView5 = this.lottieTogether;
            if (clickableLottieAnimView5 == null) {
                clickableLottieAnimView5 = null;
            }
            clickableLottieAnimView5.setVisibility(4);
            ClickableLottieAnimView clickableLottieAnimView6 = this.lottieSeparate;
            if (clickableLottieAnimView6 == null) {
                clickableLottieAnimView6 = null;
            }
            clickableLottieAnimView6.setVisibility(4);
            ClickableLottieAnimView clickableLottieAnimView7 = this.lottieTogether;
            if (clickableLottieAnimView7 == null) {
                clickableLottieAnimView7 = null;
            }
            clickableLottieAnimView7.cancelAnimation();
            ClickableLottieAnimView clickableLottieAnimView8 = this.lottieSeparate;
            (clickableLottieAnimView8 != null ? clickableLottieAnimView8 : null).cancelAnimation();
            return;
        }
        if (z) {
            ClickableLottieAnimView clickableLottieAnimView9 = this.lottieSeparate;
            if (clickableLottieAnimView9 == null) {
                clickableLottieAnimView9 = null;
            }
            clickableLottieAnimView9.playAnimation();
            ClickableLottieAnimView clickableLottieAnimView10 = this.lottieSeparate;
            if (clickableLottieAnimView10 == null) {
                clickableLottieAnimView10 = null;
            }
            clickableLottieAnimView10.setVisibility(0);
            ClickableLottieAnimView clickableLottieAnimView11 = this.lottieTogether;
            if (clickableLottieAnimView11 == null) {
                clickableLottieAnimView11 = null;
            }
            clickableLottieAnimView11.pauseAnimation();
            ClickableLottieAnimView clickableLottieAnimView12 = this.lottieSeparateRTL;
            if (clickableLottieAnimView12 == null) {
                clickableLottieAnimView12 = null;
            }
            clickableLottieAnimView12.pauseAnimation();
            ClickableLottieAnimView clickableLottieAnimView13 = this.lottieTogether;
            if (clickableLottieAnimView13 == null) {
                clickableLottieAnimView13 = null;
            }
            clickableLottieAnimView13.setVisibility(4);
            ClickableLottieAnimView clickableLottieAnimView14 = this.lottieSeparateRTL;
            if (clickableLottieAnimView14 == null) {
                clickableLottieAnimView14 = null;
            }
            clickableLottieAnimView14.setVisibility(4);
            ClickableLottieAnimView clickableLottieAnimView15 = this.lottieTogether;
            if (clickableLottieAnimView15 == null) {
                clickableLottieAnimView15 = null;
            }
            clickableLottieAnimView15.cancelAnimation();
            ClickableLottieAnimView clickableLottieAnimView16 = this.lottieSeparateRTL;
            (clickableLottieAnimView16 != null ? clickableLottieAnimView16 : null).cancelAnimation();
            return;
        }
        ClickableLottieAnimView clickableLottieAnimView17 = this.lottieTogether;
        if (clickableLottieAnimView17 == null) {
            clickableLottieAnimView17 = null;
        }
        clickableLottieAnimView17.playAnimation();
        ClickableLottieAnimView clickableLottieAnimView18 = this.lottieTogether;
        if (clickableLottieAnimView18 == null) {
            clickableLottieAnimView18 = null;
        }
        clickableLottieAnimView18.setVisibility(0);
        ClickableLottieAnimView clickableLottieAnimView19 = this.lottieSeparateRTL;
        if (clickableLottieAnimView19 == null) {
            clickableLottieAnimView19 = null;
        }
        clickableLottieAnimView19.pauseAnimation();
        ClickableLottieAnimView clickableLottieAnimView20 = this.lottieSeparate;
        if (clickableLottieAnimView20 == null) {
            clickableLottieAnimView20 = null;
        }
        clickableLottieAnimView20.pauseAnimation();
        ClickableLottieAnimView clickableLottieAnimView21 = this.lottieSeparateRTL;
        if (clickableLottieAnimView21 == null) {
            clickableLottieAnimView21 = null;
        }
        clickableLottieAnimView21.setVisibility(4);
        ClickableLottieAnimView clickableLottieAnimView22 = this.lottieSeparate;
        if (clickableLottieAnimView22 == null) {
            clickableLottieAnimView22 = null;
        }
        clickableLottieAnimView22.setVisibility(4);
        ClickableLottieAnimView clickableLottieAnimView23 = this.lottieSeparateRTL;
        if (clickableLottieAnimView23 == null) {
            clickableLottieAnimView23 = null;
        }
        clickableLottieAnimView23.cancelAnimation();
        ClickableLottieAnimView clickableLottieAnimView24 = this.lottieSeparate;
        (clickableLottieAnimView24 != null ? clickableLottieAnimView24 : null).cancelAnimation();
    }

    public final void toUpdateFontTypeface(TextView textView, boolean z) {
        Typeface typefaceCreate = Typeface.create("sec", 0);
        if (z) {
            textView.setTypeface(Typeface.create(typefaceCreate, this.FONT_WEIGHT_SEMIBOLD, false));
            textView.setTextColor(textView.getResources().getColor(R.color.qs_edit_content_text_color));
        } else {
            textView.setTypeface(Typeface.create(typefaceCreate, this.FONT_WEIGHT_REGULAR, false));
            textView.setTextColor(textView.getResources().getColor(R.color.qs_edit_panel_type_unselected_text_color));
        }
    }
}
