package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
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
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
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
import com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.util.RecoilEffectUtil;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class QSGuideDialog extends SystemUIDialog implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean alreadyDialogShown;
    public final CentralSurfacesCommandQueueCallbacks commandQueueCallbacks;
    public int currentOrientation;
    public int currentScreen;
    public final QSGuideDialog$displayListener$1 displayListener;
    public final DisplayManager displayManager;
    public final Executor executor;
    public boolean firstExpanded;
    public final Lazy lazyUnlockedScreenOffAnimationController;
    public final SecPanelSplitHelper panelSplitHelper;
    public final QSGuideDialog$panelTransitionStateListener$1 panelTransitionStateListener;
    public final SecQSPanelResourcePicker resourcePicker;
    public final QSGuideDialog$shadeExpansionStateListener$1 shadeExpansionStateListener;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final QSGuideDialog$stateListener$1 stateListener;
    public int statusBarState;
    public final StatusBarStateController statusBarStateController;

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

    public QSGuideDialog(Context context, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SecQSPanelResourcePicker secQSPanelResourcePicker, CentralSurfacesCommandQueueCallbacks centralSurfacesCommandQueueCallbacks, SecPanelSplitHelper secPanelSplitHelper, Lazy lazy, Executor executor) {
        super(context, R.style.QSGuideDialog);
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.resourcePicker = secQSPanelResourcePicker;
        this.commandQueueCallbacks = centralSurfacesCommandQueueCallbacks;
        this.panelSplitHelper = secPanelSplitHelper;
        this.lazyUnlockedScreenOffAnimationController = lazy;
        this.executor = executor;
        this.panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.QSGuideDialog$panelTransitionStateListener$1
            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                QSGuideDialog qSGuideDialog = QSGuideDialog.this;
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
                QSGuideDialog qSGuideDialog = QSGuideDialog.this;
                if (qSGuideDialog.statusBarState == i) {
                    return;
                }
                qSGuideDialog.statusBarState = i;
                if (i != 0) {
                    qSGuideDialog.dismiss();
                }
            }
        };
        this.shadeExpansionStateListener = new ShadeExpansionListener() { // from class: com.android.systemui.qs.QSGuideDialog$shadeExpansionStateListener$1
            @Override // com.android.systemui.shade.ShadeExpansionListener
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                float f = shadeExpansionChangeEvent.fraction;
                QSGuideDialog qSGuideDialog = QSGuideDialog.this;
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
                    boolean andSet = qSGuideDialog.commandQueueCallbacks.needNotToShowQSGuideDialog.getAndSet(false);
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Callbacks : ", "QSGuideDialog", andSet);
                    if (!andSet) {
                        boolean z = qSGuideDialog.statusBarState != 0;
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isShade : ", "QSGuideDialog", z);
                        if (!z) {
                            SecPanelSplitHelper.Companion.getClass();
                            boolean z2 = !SecPanelSplitHelper.isEnabled;
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isSplit : ", "QSGuideDialog", z2);
                            if (!z2) {
                                boolean isAnimationPlaying = ((UnlockedScreenOffAnimationController) qSGuideDialog.lazyUnlockedScreenOffAnimationController.get()).isAnimationPlaying();
                                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("ScreenOffAnimationPlaying : ", "QSGuideDialog", isAnimationPlaying);
                                if (!isAnimationPlaying) {
                                    boolean z3 = qSGuideDialog.alreadyDialogShown;
                                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isAlreadyShown : ", "QSGuideDialog", z3);
                                    if (!z3) {
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
                QSGuideDialog qSGuideDialog = QSGuideDialog.this;
                int i2 = QSGuideDialog.$r8$clinit;
                qSGuideDialog.getClass();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Display display = QSGuideDialog.this.displayManager.getDisplay(i);
                if (display == null || i != 0 || QSGuideDialog.this.statusBarState == 0) {
                    return;
                }
                int state = display.getState();
                if (state == 1 || state == 2) {
                    QSGuideDialog qSGuideDialog = QSGuideDialog.this;
                    display.getState();
                    qSGuideDialog.getClass();
                    QSGuideDialog.this.firstExpanded = true;
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

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void onConfigurationChanged(Configuration configuration) {
        int i = this.currentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.currentOrientation = i2;
            this.executor.execute(new Runnable() { // from class: com.android.systemui.qs.QSGuideDialog$onConfigurationChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    QSGuideDialog qSGuideDialog = QSGuideDialog.this;
                    int i3 = QSGuideDialog.$r8$clinit;
                    qSGuideDialog.updateDialog();
                }
            });
        }
        int i3 = this.currentScreen;
        int i4 = configuration.screenLayout;
        if (i3 != i4) {
            this.currentScreen = i4;
            dismiss();
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.currentOrientation = getContext().getResources().getConfiguration().orientation;
        this.currentScreen = getContext().getResources().getConfiguration().screenLayout;
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

    public final void updateDialog() {
        View inflate;
        boolean z = Prefs.getBoolean(getContext(), "PanelSeparateGuideDialogShown", false);
        this.alreadyDialogShown = z;
        if (z || (inflate = LayoutInflater.from(getContext()).inflate(R.layout.qs_guide_dialog, (ViewGroup) null)) == null) {
            return;
        }
        inflate.setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.qs_guide_dialog_vertical_margin), 0, getContext().getResources().getDimensionPixelSize(R.dimen.qs_guide_dialog_vertical_margin));
        TextView textView = (TextView) inflate.requireViewById(R.id.dialog_checked);
        textView.setStateListAnimator(RecoilEffectUtil.getRecoilSmallAnimator(textView.getContext()));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QSGuideDialog$updateDialog$1$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Log.d("QSGuideDialog", "Clicked and RemoveListener");
                QSGuideDialog qSGuideDialog = QSGuideDialog.this;
                int i = QSGuideDialog.$r8$clinit;
                Prefs.putBoolean(qSGuideDialog.getContext(), "PanelSeparateGuideDialogShown", true);
                qSGuideDialog.alreadyDialogShown = Prefs.getBoolean(qSGuideDialog.getContext(), "PanelSeparateGuideDialogShown", false);
                QSGuideDialog qSGuideDialog2 = QSGuideDialog.this;
                qSGuideDialog2.statusBarStateController.removeCallback(qSGuideDialog2.stateListener);
                QSGuideDialog qSGuideDialog3 = QSGuideDialog.this;
                qSGuideDialog3.shadeExpansionStateManager.removeExpansionListener(qSGuideDialog3.shadeExpansionStateListener);
                QSGuideDialog qSGuideDialog4 = QSGuideDialog.this;
                qSGuideDialog4.displayManager.unregisterDisplayListener(qSGuideDialog4.displayListener);
                QSGuideDialog qSGuideDialog5 = QSGuideDialog.this;
                qSGuideDialog5.panelSplitHelper.removeListener(qSGuideDialog5.panelTransitionStateListener);
                QSGuideDialog.this.dismiss();
            }
        });
        ImageView imageView = (ImageView) inflate.requireViewById(R.id.qs_guide_image_noti);
        imageView.getLayoutParams().width = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageWidth(imageView.getContext());
        imageView.getLayoutParams().height = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageHeight(imageView.getContext());
        ImageView imageView2 = (ImageView) inflate.requireViewById(R.id.qs_guide_image_qs);
        imageView2.getLayoutParams().width = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageWidth(imageView2.getContext());
        imageView2.getLayoutParams().height = this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideImageHeight(imageView2.getContext());
        LinearLayout linearLayout = (LinearLayout) inflate.requireViewById(R.id.qs_guide_noti_container);
        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).setMarginEnd(this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideContainerMargin(linearLayout.getContext()) / 2);
        LinearLayout linearLayout2 = (LinearLayout) inflate.requireViewById(R.id.qs_guide_qs_container);
        ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).setMarginStart(this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideContainerMargin(linearLayout2.getContext()) / 2);
        ((LinearLayout) inflate.requireViewById(R.id.qs_guide_container)).semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(123).setBackgroundCornerRadius(getContext().getResources().getDimensionPixelSize(R.dimen.qs_guide_dialog_container_radius)).build());
        Window window = getWindow();
        if (window != null) {
            window.setLayout(this.resourcePicker.resourcePickHelper.getTargetPicker().getQSGuideWidth(window.getContext()), -2);
            window.getAttributes().gravity = 17;
            window.setContentView(inflate);
        }
    }
}
