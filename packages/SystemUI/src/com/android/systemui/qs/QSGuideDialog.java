package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
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
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            this.executor.execute(new Runnable() { // from class: com.android.systemui.qs.QSGuideDialog$onConfigurationChanged$1
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x015c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDialog() {
        /*
            Method dump skipped, instructions count: 776
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSGuideDialog.updateDialog():void");
    }
}
