package com.android.systemui.p014qp.flashlight;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.p014qp.util.SubscreenToolTipWindow;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubroomFlashLightUtil {
    public static SubroomFlashLightUtil mInstance;
    public final C20191 mAccessibilityDelegate;
    public RelativeLayout mBackButton;
    public TextView mFlashLightHelpText;
    public SubroomFlashLightButtonSettingsView mFlashLightHelpView;
    public SubroomFlashLightTurnOffView mFlashLightTurnOff;
    public SubroomFlashLightTurnOnView mFlashLightTurnOn;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.view.View$AccessibilityDelegate, com.android.systemui.qp.flashlight.SubroomFlashLightUtil$1] */
    private SubroomFlashLightUtil(Activity activity) {
        float f;
        ?? r2 = new View.AccessibilityDelegate(this) { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightUtil.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName(Button.class.getName());
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.setEnabled(true);
            }
        };
        this.mAccessibilityDelegate = r2;
        this.mFlashLightHelpView = (SubroomFlashLightButtonSettingsView) activity.findViewById(R.id.subroom_flashlight_button_settings);
        this.mFlashLightTurnOff = (SubroomFlashLightTurnOffView) activity.findViewById(R.id.subroom_flashlight_turnOff);
        this.mFlashLightTurnOn = (SubroomFlashLightTurnOnView) activity.findViewById(R.id.subroom_flashlight_turnOn);
        this.mBackButton = (RelativeLayout) activity.findViewById(R.id.subroom_back_button);
        this.mFlashLightHelpText = (TextView) activity.findViewById(R.id.subroom_flashlight_guide_text);
        SubscreenUtil.applyRotation(activity, this.mBackButton.findViewById(R.id.back_image_view));
        this.mBackButton.setAccessibilityDelegate(r2);
        boolean z = QpRune.QUICK_PANEL_SUBSCREEN;
        if (z) {
            Resources resources = activity.getResources();
            this.mFlashLightHelpView.findViewById(R.id.flashlight_help).setVisibility(8);
            this.mFlashLightHelpView.findViewById(R.id.flashlight_help_subpanel).setVisibility(0);
            this.mFlashLightHelpView.findViewById(R.id.flashlight_label).setVisibility(0);
            TextView textView = (TextView) this.mFlashLightHelpView.findViewById(R.id.subroom_flashlight_guide_text);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            TextView textView2 = (TextView) this.mFlashLightHelpView.findViewById(R.id.flashlight_label);
            textView2.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_help_text_top_margin);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_help_text_start_margin);
            int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_help_text_end_margin);
            layoutParams.height = resources.getDimensionPixelSize(R.dimen.subscreen_flashlight_helptext_height);
            layoutParams.setMarginStart(dimensionPixelSize2);
            layoutParams.setMarginEnd(dimensionPixelSize3);
            layoutParams.setMargins(0, dimensionPixelSize, 0, 0);
            textView.setLayoutParams(layoutParams);
            SubscreenUtil.setLabelTextSize(R.dimen.qs_tile_subscreen_panel_flashlight_label_text_size, textView2);
            textView.setLineSpacing(resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_help_text_line_spacing), 1.0f);
            textView.setTextSize(0, resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_help_text_size));
            textView.setTextColor(getTextColor(resources));
            textView.setTypeface(Typeface.create(Typeface.create("sec", 0), 400, false));
            this.mFlashLightHelpView.setBackgroundColor(getBackgroundColor(resources));
            Button button = (Button) this.mFlashLightTurnOff.findViewById(R.id.turn_off_view);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) button.getLayoutParams();
            layoutParams2.width = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_width);
            layoutParams2.height = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_height);
            int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_flashlight_turnoff_top_margin);
            layoutParams2.setMarginStart(resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_flashlight_turnoff_start_margin));
            layoutParams2.setMargins(0, dimensionPixelSize4, 0, 0);
            layoutParams2.addRule(13, 0);
            button.setLayoutParams(layoutParams2);
            button.setTextSize(0, resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_text_size));
            int dimensionPixelSize5 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_padding);
            button.setPadding(dimensionPixelSize5, 0, dimensionPixelSize5, 0);
            button.setTextAppearance(2132018317);
            button.setTextColor(getTextColor(resources));
            RippleDrawable rippleDrawable = (RippleDrawable) button.getBackground();
            float dimension = resources.getDimension(R.dimen.qs_tile_subscreen_panel_flashlight_button_radius);
            ((GradientDrawable) rippleDrawable.getDrawable(0)).setCornerRadius(dimension);
            ((GradientDrawable) rippleDrawable.getDrawable(1)).setCornerRadius(dimension);
            updateColor((RippleDrawable) button.getBackground(), resources);
            this.mFlashLightTurnOff.setBackgroundColor(getBackgroundColor(resources));
            ImageView imageView = (ImageView) activity.findViewById(R.id.back_image_view);
            imageView.setImageResource(R.drawable.subroom_close_button);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams3.width = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_image_width);
            layoutParams3.height = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_image_width);
            imageView.setLayoutParams(layoutParams3);
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mBackButton.getLayoutParams();
            layoutParams4.width = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_width);
            layoutParams4.height = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_width);
            int dimensionPixelSize6 = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_top_margin);
            int dimensionPixelSize7 = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_start_margin);
            layoutParams4.setMargins(0, dimensionPixelSize6, 0, 0);
            layoutParams4.setMarginStart(dimensionPixelSize7);
            this.mBackButton.setLayoutParams(layoutParams4);
            int dimensionPixelSize8 = resources.getDimensionPixelSize(R.dimen.subscreen_close_button_padding);
            this.mBackButton.setPadding(dimensionPixelSize8, dimensionPixelSize8, dimensionPixelSize8, dimensionPixelSize8);
            imageView.setBackgroundResource(0);
            RelativeLayout relativeLayout = this.mBackButton;
            Object obj = ContextCompat.sLock;
            relativeLayout.setBackground(activity.getDrawable(R.drawable.subscreen_ripple_drawable));
            final SubscreenToolTipWindow subscreenToolTipWindow = new SubscreenToolTipWindow(activity, R.string.subscreen_close_button);
            this.mBackButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightUtil$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    SubscreenToolTipWindow subscreenToolTipWindow2 = SubscreenToolTipWindow.this;
                    PopupWindow popupWindow = subscreenToolTipWindow2.mTipWindow;
                    if (!(popupWindow != null && popupWindow.isShowing())) {
                        subscreenToolTipWindow2.showToolTip(view);
                    }
                    return true;
                }
            });
            imageView.setColorFilter(z ? resources.getColor(R.color.subroom_back_button_color) : resources.getColor(R.color.subscreen_settings_flashlight_back_button_color));
            this.mBackButton.setContentDescription(resources.getString(R.string.subscreen_close_button));
            TextView textView3 = (TextView) this.mFlashLightTurnOn.findViewById(R.id.turn_on_help_text);
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) textView3.getLayoutParams();
            TextView textView4 = (TextView) this.mFlashLightTurnOn.findViewById(R.id.flashlight_label);
            textView4.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
            layoutParams5.height = -2;
            int dimensionPixelSize9 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnon_text_top_margin);
            float f2 = resources.getConfiguration().fontScale;
            if (f2 < 0.9f) {
                f2 = 0.9f;
            } else if (f2 > 1.3f) {
                f2 = 1.3f;
            }
            int i = (int) (dimensionPixelSize9 / f2);
            int dimensionPixelSize10 = resources.getDimensionPixelSize(R.dimen.subscreen_flashlight_turnontext_margin);
            layoutParams5.setMarginStart(dimensionPixelSize10);
            layoutParams5.setMarginEnd(dimensionPixelSize10);
            layoutParams5.setMargins(0, i, 0, 0);
            textView3.setLayoutParams(layoutParams5);
            SubscreenUtil.setLabelTextSize(R.dimen.qs_tile_subscreen_panel_turnon_text_size, textView3);
            textView3.setTextAppearance(2132018318);
            textView3.setLineSpacing(resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnon_text_line_spacing), 1.0f);
            textView3.setTextColor(getTextColor(resources));
            Button button2 = (Button) this.mFlashLightTurnOn.findViewById(R.id.turn_on_view);
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) button2.getLayoutParams();
            layoutParams6.width = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_width);
            layoutParams6.height = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_height);
            int dimensionPixelSize11 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnon_button_top_margin);
            float f3 = resources.getConfiguration().fontScale;
            if (f3 >= 0.9f) {
                f = f3 > 1.3f ? 1.3f : 0.9f;
                layoutParams6.setMargins(0, (int) (dimensionPixelSize11 / f3), 0, 0);
                button2.setLayoutParams(layoutParams6);
                SubscreenUtil.setLabelTextSize(R.dimen.qs_tile_subscreen_panel_flashlight_label_text_size, textView4);
                button2.setTextSize(0, resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_text_size));
                int dimensionPixelSize12 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_padding);
                button2.setPadding(dimensionPixelSize12, 0, dimensionPixelSize12, 0);
                button2.setTextColor(getTextColor(resources));
                this.mFlashLightTurnOn.findViewById(R.id.flashlight_label).setVisibility(0);
                RippleDrawable rippleDrawable2 = (RippleDrawable) button2.getBackground();
                float dimension2 = resources.getDimension(R.dimen.qs_tile_subscreen_panel_flashlight_button_radius);
                ((GradientDrawable) rippleDrawable2.getDrawable(0)).setCornerRadius(dimension2);
                ((GradientDrawable) rippleDrawable2.getDrawable(1)).setCornerRadius(dimension2);
                updateColor((RippleDrawable) button2.getBackground(), resources);
                button2.setTextAppearance(2132018317);
                this.mFlashLightTurnOn.setBackgroundColor(getBackgroundColor(resources));
            }
            f3 = f;
            layoutParams6.setMargins(0, (int) (dimensionPixelSize11 / f3), 0, 0);
            button2.setLayoutParams(layoutParams6);
            SubscreenUtil.setLabelTextSize(R.dimen.qs_tile_subscreen_panel_flashlight_label_text_size, textView4);
            button2.setTextSize(0, resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_text_size));
            int dimensionPixelSize122 = resources.getDimensionPixelSize(R.dimen.qs_tile_subscreen_panel_turnoff_button_padding);
            button2.setPadding(dimensionPixelSize122, 0, dimensionPixelSize122, 0);
            button2.setTextColor(getTextColor(resources));
            this.mFlashLightTurnOn.findViewById(R.id.flashlight_label).setVisibility(0);
            RippleDrawable rippleDrawable22 = (RippleDrawable) button2.getBackground();
            float dimension22 = resources.getDimension(R.dimen.qs_tile_subscreen_panel_flashlight_button_radius);
            ((GradientDrawable) rippleDrawable22.getDrawable(0)).setCornerRadius(dimension22);
            ((GradientDrawable) rippleDrawable22.getDrawable(1)).setCornerRadius(dimension22);
            updateColor((RippleDrawable) button2.getBackground(), resources);
            button2.setTextAppearance(2132018317);
            this.mFlashLightTurnOn.setBackgroundColor(getBackgroundColor(resources));
        }
    }

    public static int getBackgroundColor(Resources resources) {
        return QpRune.QUICK_PANEL_SUBSCREEN ? resources.getColor(R.color.subscreen_panel_flashlight_background) : resources.getColor(R.color.subscreen_settings_flashlight_background);
    }

    public static SubroomFlashLightUtil getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new SubroomFlashLightUtil(activity);
        }
        return mInstance;
    }

    public static int getTextColor(Resources resources) {
        return QpRune.QUICK_PANEL_SUBSCREEN ? resources.getColor(R.color.subscreen_panel_flashlight_text_color) : resources.getColor(R.color.subroom_flashlight_help_text);
    }

    public static void updateColor(RippleDrawable rippleDrawable, Resources resources) {
        rippleDrawable.setColor(new ColorStateList(new int[][]{new int[0]}, new int[]{resources.getColor(R.color.subscreen_panel_flashlight_button_turn_off_ripple)}));
        ((GradientDrawable) rippleDrawable.getDrawable(0)).setColor(resources.getColor(R.color.subscreen_panel_flashlight_button_turn_off));
    }
}
