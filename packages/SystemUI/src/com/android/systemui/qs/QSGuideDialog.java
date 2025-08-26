package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.PanelTransitionState;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QSGuideDialog extends SystemUIDialog implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean alreadyDialogShown;
    public int currentOrientation;
    public final QSGuideDialog$displayListener$1 displayListener;
    public final DisplayManager displayManager;
    public final Executor executor;
    public boolean firstExpanded;
    public final KeyguardStateController keyguardStateController;
    public final Lazy lazyUnlockedScreenOffAnimationController;
    public final SecPanelSplitHelper panelSplitHelper;
    public final QSGuideDialog$panelTransitionStateListener$1 panelTransitionStateListener;
    public final SecQSPanelResourcePicker resourcePicker;
    private final SettingsHelper settingsHelper;
    public final QSGuideDialog$shadeExpansionStateListener$1 shadeExpansionStateListener;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final QSGuideDialog$stateListener$1 stateListener;
    public int statusBarState;
    public final StatusBarStateController statusBarStateController;

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

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.QSGuideDialog$panelTransitionStateListener$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.qs.QSGuideDialog$stateListener$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.QSGuideDialog$shadeExpansionStateListener$1] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.qs.QSGuideDialog$displayListener$1] */
    public QSGuideDialog(Context context, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SecQSPanelResourcePicker secQSPanelResourcePicker, SecPanelSplitHelper secPanelSplitHelper, SettingsHelper settingsHelper, Lazy lazy, KeyguardStateController keyguardStateController, Executor executor) {
        super(context, R.style.QSGuideDialog);
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.resourcePicker = secQSPanelResourcePicker;
        this.panelSplitHelper = secPanelSplitHelper;
        this.settingsHelper = settingsHelper;
        this.lazyUnlockedScreenOffAnimationController = lazy;
        this.keyguardStateController = keyguardStateController;
        this.executor = executor;
        this.panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.QSGuideDialog$panelTransitionStateListener$1
            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                QSGuideDialog qSGuideDialog = this.this$0;
                if (qSGuideDialog.isShowing()) {
                    int i = PanelTransitionState.$r8$clinit;
                    if (panelTransitionStateChangeEvent.state == 2) {
                        qSGuideDialog.dismiss();
                    }
                }
            }
        };
        this.stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.qs.QSGuideDialog$stateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                QSGuideDialog qSGuideDialog = this.this$0;
                if (qSGuideDialog.statusBarState == i) {
                    return;
                }
                qSGuideDialog.statusBarState = i;
            }
        };
        this.shadeExpansionStateListener = new ShadeExpansionListener() { // from class: com.android.systemui.qs.QSGuideDialog$shadeExpansionStateListener$1
            @Override // com.android.systemui.shade.ShadeExpansionListener
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                float f = shadeExpansionChangeEvent.fraction;
                QSGuideDialog qSGuideDialog = this.this$0;
                if (f != 1.0f) {
                    if (f < 0.9f && qSGuideDialog.isShowing()) {
                        qSGuideDialog.dismiss();
                        return;
                    } else {
                        if (f == 0.0f) {
                            qSGuideDialog.firstExpanded = false;
                            return;
                        }
                        return;
                    }
                }
                if (qSGuideDialog.firstExpanded) {
                    return;
                }
                if (QpRune.QUICK_PANEL_GUIDE) {
                    int i = qSGuideDialog.statusBarState;
                    boolean z = i != 0;
                    Log.d("QSGuideDialog", "isNotShade : " + z + " (" + i + ")");
                    if (!z) {
                        SecPanelSplitHelper.Companion.getClass();
                        boolean z2 = SecPanelSplitHelper.isEnabled;
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("isNotSplit : ", "QSGuideDialog", !z2);
                        if (z2) {
                            boolean z3 = ((UnlockedScreenOffAnimationController) qSGuideDialog.lazyUnlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying;
                            EmergencyButtonController$$ExternalSyntheticOutline0.m("ScreenOffAnimationPlaying : ", "QSGuideDialog", z3);
                            if (!z3) {
                                boolean z4 = ((KeyguardStateControllerImpl) qSGuideDialog.keyguardStateController).mOccluded;
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("isOccluded : ", "QSGuideDialog", z4);
                                if (!z4) {
                                    boolean z5 = qSGuideDialog.alreadyDialogShown;
                                    EmergencyButtonController$$ExternalSyntheticOutline0.m("isAlreadyShown : ", "QSGuideDialog", z5);
                                    if (!z5) {
                                        qSGuideDialog.show();
                                    }
                                }
                            }
                        }
                    }
                }
                qSGuideDialog.firstExpanded = true;
            }
        };
        this.displayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.qs.QSGuideDialog$displayListener$1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
                QSGuideDialog qSGuideDialog = this.this$0;
                int i2 = QSGuideDialog.$r8$clinit;
                qSGuideDialog.getClass();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Display display = this.this$0.displayManager.getDisplay(i);
                if (display == null || i != 0 || this.this$0.statusBarState == 0) {
                    return;
                }
                int state = display.getState();
                if (state == 1 || state == 2) {
                    QSGuideDialog qSGuideDialog = this.this$0;
                    display.getState();
                    qSGuideDialog.getClass();
                    this.this$0.firstExpanded = true;
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.displayManager = (DisplayManager) context.getSystemService("display");
        this.statusBarState = 1;
        this.currentOrientation = 1;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        Log.d("QSGuideDialog", Debug.getCallers(3, " "));
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void onConfigurationChanged(Configuration configuration) {
        int i = this.currentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.currentOrientation = i2;
            this.executor.execute(new Runnable() { // from class: com.android.systemui.qs.QSGuideDialog.onConfigurationChanged.1
                @Override // java.lang.Runnable
                public final void run() {
                    QSGuideDialog qSGuideDialog = QSGuideDialog.this;
                    int i3 = QSGuideDialog.$r8$clinit;
                    qSGuideDialog.updateDialog();
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.currentOrientation = getContext().getResources().getConfiguration().orientation;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        updateDialog();
        StatusBarStateController statusBarStateController = this.statusBarStateController;
        this.statusBarState = statusBarStateController.getState();
        statusBarStateController.removeCallback(this.stateListener);
        statusBarStateController.addCallback(this.stateListener);
        ShadeExpansionStateManager shadeExpansionStateManager = this.shadeExpansionStateManager;
        shadeExpansionStateManager.removeExpansionListener(this.shadeExpansionStateListener);
        shadeExpansionStateManager.addExpansionListener(this.shadeExpansionStateListener);
        DisplayManager displayManager = this.displayManager;
        displayManager.unregisterDisplayListener(this.displayListener);
        displayManager.registerDisplayListener(this.displayListener, null);
        SecPanelSplitHelper secPanelSplitHelper = this.panelSplitHelper;
        secPanelSplitHelper.removeListener(this.panelTransitionStateListener);
        secPanelSplitHelper.addListener(this.panelTransitionStateListener);
    }

    public final void textColorChanged(TextView textView) {
        Resources resources = getContext().getResources();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        Context context = getContext();
        secQSPanelResourcePicker.getClass();
        textView.setTextColor(resources.getColor(SecQSPanelResourcePicker.isNightMode(context) ? R.color.qs_edit_content_text_color : R.color.qs_edit_content_text_color_black));
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0150  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDialog() {
        View viewInflate;
        int i;
        int i2;
        boolean z = Prefs.getBoolean(getContext(), "PanelSeparateGuideDialogShown", false);
        this.alreadyDialogShown = z;
        if (z || (viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.qs_guide_dialog, (ViewGroup) null)) == null) {
            return;
        }
        viewInflate.setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.qs_guide_dialog_vertical_margin), 0, getContext().getResources().getDimensionPixelSize(R.dimen.qs_guide_dialog_vertical_margin));
        TextView textView = (TextView) viewInflate.requireViewById(R.id.dialog_checked);
        textView.getClass();
        textColorChanged(textView);
        Resources resources = textView.getContext().getResources();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        Context context = textView.getContext();
        secQSPanelResourcePicker.getClass();
        textView.setBackground(resources.getDrawable(SecQSPanelResourcePicker.isNightMode(context) ? R.drawable.qs_guide_btn_ripple_night : R.drawable.qs_guide_btn_ripple));
        textView.setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(textView.getContext()));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QSGuideDialog$updateDialog$1$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Log.d("QSGuideDialog", "Clicked and RemoveListener");
                QSGuideDialog qSGuideDialog = this.this$0;
                int i3 = QSGuideDialog.$r8$clinit;
                Prefs.putBoolean(qSGuideDialog.getContext(), "PanelSeparateGuideDialogShown", true);
                qSGuideDialog.alreadyDialogShown = Prefs.getBoolean(qSGuideDialog.getContext(), "PanelSeparateGuideDialogShown", false);
                QSGuideDialog qSGuideDialog2 = this.this$0;
                qSGuideDialog2.statusBarStateController.removeCallback(qSGuideDialog2.stateListener);
                QSGuideDialog qSGuideDialog3 = this.this$0;
                qSGuideDialog3.shadeExpansionStateManager.removeExpansionListener(qSGuideDialog3.shadeExpansionStateListener);
                QSGuideDialog qSGuideDialog4 = this.this$0;
                qSGuideDialog4.displayManager.unregisterDisplayListener(qSGuideDialog4.displayListener);
                QSGuideDialog qSGuideDialog5 = this.this$0;
                qSGuideDialog5.panelSplitHelper.removeListener(qSGuideDialog5.panelTransitionStateListener);
                this.this$0.dismiss();
            }
        });
        ImageView imageView = (ImageView) viewInflate.requireViewById(R.id.qs_guide_image_noti);
        Resources resources2 = imageView.getResources();
        if (this.panelSplitHelper.isReversed()) {
            SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.resourcePicker;
            Context context2 = getContext();
            secQSPanelResourcePicker2.getClass();
            if (SecQSPanelResourcePicker.isNightMode(context2)) {
                i = R.drawable.dark_guided_tour_tips_left_rtl;
            } else if (!this.panelSplitHelper.isReversed()) {
                SecQSPanelResourcePicker secQSPanelResourcePicker3 = this.resourcePicker;
                Context context3 = getContext();
                secQSPanelResourcePicker3.getClass();
                i = !SecQSPanelResourcePicker.isNightMode(context3) ? R.drawable.light_guided_tour_tips_left : this.panelSplitHelper.isReversed() ? R.drawable.light_guided_tour_tips_left_rtl : R.drawable.dark_guided_tour_tips_left;
            }
        }
        imageView.setBackground(resources2.getDrawable(i));
        imageView.getLayoutParams().width = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageWidth(imageView.getContext());
        imageView.getLayoutParams().height = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageHeight(imageView.getContext());
        ImageView imageView2 = (ImageView) viewInflate.requireViewById(R.id.qs_guide_image_qs);
        Resources resources3 = imageView2.getResources();
        if (this.panelSplitHelper.isReversed()) {
            SecQSPanelResourcePicker secQSPanelResourcePicker4 = this.resourcePicker;
            Context context4 = getContext();
            secQSPanelResourcePicker4.getClass();
            if (SecQSPanelResourcePicker.isNightMode(context4)) {
                i2 = R.drawable.dark_guided_tour_tips_right_rtl;
            } else if (!this.panelSplitHelper.isReversed()) {
                SecQSPanelResourcePicker secQSPanelResourcePicker5 = this.resourcePicker;
                Context context5 = getContext();
                secQSPanelResourcePicker5.getClass();
                i2 = !SecQSPanelResourcePicker.isNightMode(context5) ? R.drawable.light_guided_tour_tips_right : this.panelSplitHelper.isReversed() ? R.drawable.light_guided_tour_tips_right_rtl : R.drawable.dark_guided_tour_tips_right;
            }
        }
        imageView2.setBackground(resources3.getDrawable(i2));
        imageView2.getLayoutParams().width = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageWidth(imageView2.getContext());
        imageView2.getLayoutParams().height = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageHeight(imageView2.getContext());
        LinearLayout linearLayout = (LinearLayout) viewInflate.requireViewById(R.id.qs_guide_noti_container);
        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).setMarginEnd(this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideContainerMargin(linearLayout.getContext()) / 2);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.requireViewById(R.id.qs_guide_qs_container);
        ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).setMarginStart(this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideContainerMargin(linearLayout2.getContext()) / 2);
        TextView textView2 = (TextView) viewInflate.requireViewById(R.id.qs_guide_text_noti);
        textView2.getClass();
        textColorChanged(textView2);
        Resources resources4 = textView2.getContext().getResources();
        boolean zIsReversed = this.panelSplitHelper.isReversed();
        int i3 = R.string.sec_notifications;
        textView2.setText(resources4.getText(zIsReversed ? R.string.sec_quick_settings : R.string.sec_notifications));
        TextView textView3 = (TextView) viewInflate.requireViewById(R.id.qs_guide_text_qs);
        textView3.getClass();
        textColorChanged(textView3);
        Resources resources5 = textView3.getContext().getResources();
        if (!this.panelSplitHelper.isReversed()) {
            i3 = R.string.sec_quick_settings;
        }
        textView3.setText(resources5.getText(i3));
        TextView textView4 = (TextView) viewInflate.requireViewById(R.id.qs_guide_description);
        textView4.getClass();
        textColorChanged(textView4);
        textView4.setText(textView4.getContext().getResources().getText(this.settingsHelper.isPanelSplitReversed() ? R.string.qs_edit_separate_description_rtl : R.string.qs_edit_separate_description));
        ((LinearLayout) viewInflate.requireViewById(R.id.qs_guide_container)).setContentDescription(textView4.getText());
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            LinearLayout linearLayout3 = (LinearLayout) viewInflate.requireViewById(R.id.qs_guide_container);
            Resources resources6 = getContext().getResources();
            SecQSPanelResourcePicker secQSPanelResourcePicker6 = this.resourcePicker;
            Context context6 = getContext();
            secQSPanelResourcePicker6.getClass();
            linearLayout3.setBackground(resources6.getDrawable(SecQSPanelResourcePicker.isNightMode(context6) ? R.drawable.qs_guide_roundborder_night : R.drawable.qs_guide_roundborder));
        } else {
            float dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.qs_guide_dialog_container_radius);
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            SecQSPanelResourcePicker secQSPanelResourcePicker7 = this.resourcePicker;
            Context context7 = getContext();
            secQSPanelResourcePicker7.getClass();
            ((LinearLayout) viewInflate.requireViewById(R.id.qs_guide_container)).semSetBlurInfo(builder.setColorCurvePreset(SecQSPanelResourcePicker.isNightMode(context7) ? 124 : 109).setBackgroundCornerRadius(dimensionPixelSize).build());
        }
        Window window = getWindow();
        if (window != null) {
            window.setLayout(this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideWidth(window.getContext()), -2);
            window.getAttributes().gravity = 17;
            window.setContentView(viewInflate);
        }
    }
}
