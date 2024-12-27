package com.android.systemui.accessibility;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.util.DesktopManager;
import com.android.wm.shell.flexpanel.FlexPanelStartController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.multiwindow.MultiWindowUtils;

public final class FlexPanelInteractor {
    public final Context mContext;
    public final AnonymousClass1 mDesktopCallback;
    public final FlexPanelStartController mFlexPanelStartController;
    public boolean mIsNavBarForImmersiveSplit = false;
    public boolean mNavBarGestureEnabled;
    public final AnonymousClass5 mTaskStackChangeListener;
    public final WindowManager mWindowManager;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v8, types: [com.android.systemui.accessibility.FlexPanelInteractor$5, com.android.systemui.shared.system.TaskStackChangeListener] */
    public FlexPanelInteractor(Context context, CommandQueue commandQueue, AutoHideController autoHideController) {
        DesktopManager.Callback callback = new DesktopManager.Callback() { // from class: com.android.systemui.accessibility.FlexPanelInteractor.1
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                int enabled = semDesktopModeState.getEnabled();
                FlexPanelInteractor flexPanelInteractor = FlexPanelInteractor.this;
                if (enabled == 4 && semDesktopModeState.getState() == 50) {
                    flexPanelInteractor.mFlexPanelStartController.mDesktopMode = true;
                } else if (semDesktopModeState.getEnabled() != 2 || semDesktopModeState.getState() != 50) {
                    return;
                } else {
                    flexPanelInteractor.mFlexPanelStartController.mDesktopMode = false;
                }
                flexPanelInteractor.mFlexPanelStartController.onFlexModeChanged(true);
            }
        };
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mNavBarGestureEnabled = 2 <= Settings.Secure.getIntForUser(context.getContentResolver(), "navigation_mode", 0, -2);
        SystemUIAppComponentFactoryBase.Companion.getClass();
        this.mFlexPanelStartController = ((SplitScreenController) SystemUIAppComponentFactoryBase.systemUIInitializer.getWMComponent().getSplitScreenController().orElse(null)).mFlexController;
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("navigation_mode"), false, new ContentObserver(new Handler()) { // from class: com.android.systemui.accessibility.FlexPanelInteractor.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                FlexPanelInteractor flexPanelInteractor = FlexPanelInteractor.this;
                flexPanelInteractor.mNavBarGestureEnabled = 2 <= Settings.Secure.getIntForUser(flexPanelInteractor.mContext.getContentResolver(), "navigation_mode", 0, -2);
            }
        });
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.accessibility.FlexPanelInteractor.3
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
                if (i != 0) {
                    return;
                }
                int navigationBars = WindowInsets.Type.navigationBars() & i4;
                boolean z2 = false;
                FlexPanelInteractor flexPanelInteractor = FlexPanelInteractor.this;
                if (navigationBars != 0) {
                    boolean z3 = flexPanelInteractor.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom == 0;
                    FlexPanelStartController flexPanelStartController = flexPanelInteractor.mFlexPanelStartController;
                    if (flexPanelStartController.mIsNavBarHidden && !z3) {
                        flexPanelStartController.mIsNavBarHidden = false;
                        flexPanelStartController.onFlexModeChanged(false);
                    } else if (!flexPanelInteractor.mIsNavBarForImmersiveSplit) {
                        flexPanelStartController.mIsNavBarHidden = false;
                    }
                }
                if (str != null && str.equalsIgnoreCase("com.sec.android.app.launcher") && i2 == 0) {
                    z2 = true;
                }
                FlexPanelStartController flexPanelStartController2 = flexPanelInteractor.mFlexPanelStartController;
                if (flexPanelStartController2.mIsTaskBarAppsEnabled != z2) {
                    flexPanelStartController2.mIsTaskBarAppsEnabled = z2;
                    if (!z2) {
                        flexPanelStartController2.onFlexModeChanged(true);
                    } else {
                        flexPanelStartController2.clearMessages();
                        flexPanelInteractor.mFlexPanelStartController.clearButton();
                    }
                }
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setWindowState(int i, int i2, int i3) {
                if (i == 0 && i2 == 2) {
                    FlexPanelInteractor flexPanelInteractor = FlexPanelInteractor.this;
                    if (flexPanelInteractor.mNavBarGestureEnabled) {
                        boolean z = true;
                        boolean z2 = i3 == 2 || i3 == 1;
                        ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("setWindowState: isNavBarHidden=", ", mIsNavBarHidden=", z2), flexPanelInteractor.mFlexPanelStartController.mIsNavBarHidden, "FlexPanelInteractor");
                        if (i3 != 2 && i3 != 1) {
                            z = false;
                        }
                        flexPanelInteractor.mIsNavBarForImmersiveSplit = z;
                        FlexPanelStartController flexPanelStartController = flexPanelInteractor.mFlexPanelStartController;
                        if (flexPanelStartController.mIsNavBarHidden == z2) {
                            return;
                        }
                        flexPanelStartController.mIsNavBarHidden = z2;
                        if (z2) {
                            flexPanelStartController.clearButton();
                        } else {
                            flexPanelStartController.onFlexModeChanged(false);
                        }
                    }
                }
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void showTransient(int i, int i2, boolean z) {
                if (i == 0 && (WindowInsets.Type.navigationBars() & i2) != 0) {
                    FlexPanelInteractor flexPanelInteractor = FlexPanelInteractor.this;
                    flexPanelInteractor.mFlexPanelStartController.mIsNavBarHidden = false;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("showTransient: mIsNavBarHidden="), flexPanelInteractor.mFlexPanelStartController.mIsNavBarHidden, "FlexPanelInteractor");
                    if (flexPanelInteractor.mNavBarGestureEnabled) {
                        flexPanelInteractor.mFlexPanelStartController.onFlexModeChanged(false);
                    }
                }
            }
        });
        autoHideController.mFlexPanel = new AutoHideUiElement() { // from class: com.android.systemui.accessibility.FlexPanelInteractor.4
            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void hide() {
                FlexPanelInteractor flexPanelInteractor = FlexPanelInteractor.this;
                if (flexPanelInteractor.mNavBarGestureEnabled) {
                    flexPanelInteractor.mFlexPanelStartController.mIsNavBarHidden = true;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("hide: mIsNavBarHidden="), flexPanelInteractor.mFlexPanelStartController.mIsNavBarHidden, "FlexPanelInteractor");
                    if (!flexPanelInteractor.mIsNavBarForImmersiveSplit) {
                        flexPanelInteractor.mFlexPanelStartController.mIsNavBarHidden = false;
                    }
                    if (MultiWindowUtils.isFlexPanelEnabled(flexPanelInteractor.mContext)) {
                        flexPanelInteractor.mFlexPanelStartController.clearButton();
                    }
                }
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean isVisible() {
                return false;
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void synchronizeState() {
            }
        };
        ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).registerCallback(callback);
        AnonymousClass5 anonymousClass5 = this.mTaskStackChangeListener;
        if (anonymousClass5 != null) {
            TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(anonymousClass5);
        }
        ?? r6 = new TaskStackChangeListener() { // from class: com.android.systemui.accessibility.FlexPanelInteractor.5
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskFocusChanged() {
                FlexPanelInteractor.this.mFlexPanelStartController.onFlexModeChanged(true);
            }

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskWindowingModeChanged() {
                FlexPanelInteractor.this.mFlexPanelStartController.onFlexModeChanged(true);
            }
        };
        this.mTaskStackChangeListener = r6;
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(r6);
    }
}
