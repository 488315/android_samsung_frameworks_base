package com.android.systemui.controls.p005ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import androidx.activity.ComponentActivity;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsAnimations;
import com.android.systemui.controls.management.ControlsAnimations$observerForAnimations$1;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsActivity extends ComponentActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public ControlsActivity$initBroadcastReceiver$1 broadcastReceiver;
    public final ControlsSettingsDialogManager controlsSettingsDialogManager;
    public final IDreamManager dreamManager;
    public final FeatureFlags featureFlags;
    public final KeyguardStateController keyguardStateController;
    public final Configuration lastConfiguration = new Configuration();
    public boolean mExitToDream;
    public ViewGroup parent;
    public final ControlsUiController uiController;

    public ControlsActivity(ControlsUiController controlsUiController, BroadcastDispatcher broadcastDispatcher, IDreamManager iDreamManager, FeatureFlags featureFlags, ControlsSettingsDialogManager controlsSettingsDialogManager, KeyguardStateController keyguardStateController) {
        this.uiController = controlsUiController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dreamManager = iDreamManager;
        this.featureFlags = featureFlags;
        this.controlsSettingsDialogManager = controlsSettingsDialogManager;
        this.keyguardStateController = keyguardStateController;
    }

    public final void finishOrReturnToDream() {
        if (this.mExitToDream) {
            try {
                this.mExitToDream = false;
                this.dreamManager.dream();
                return;
            } catch (RemoteException unused) {
            }
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        finishOrReturnToDream();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Configuration configuration2 = this.lastConfiguration;
        if ((configuration2.diff(configuration) & 3200) != 0) {
            this.uiController.getClass();
        }
        configuration2.setTo(configuration);
    }

    /* JADX WARN: Type inference failed for: r9v9, types: [com.android.systemui.controls.ui.ControlsActivity$initBroadcastReceiver$1] */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.lastConfiguration.setTo(getResources().getConfiguration());
        ReleasedFlag releasedFlag = Flags.USE_APP_PANELS;
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) this.featureFlags;
        if (featureFlagsRelease.isEnabled(releasedFlag)) {
            getWindow().addPrivateFlags(QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
        }
        setContentView(R.layout.controls_fullscreen);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.control_detail_root);
        Window window = getWindow();
        Intent intent = getIntent();
        boolean z = !featureFlagsRelease.isEnabled(releasedFlag);
        controlsAnimations.getClass();
        ((ComponentActivity) this).mLifecycleRegistry.addObserver(new ControlsAnimations$observerForAnimations$1(intent, viewGroup, z, window));
        ((ViewGroup) requireViewById(R.id.control_detail_root)).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.ui.ControlsActivity$onCreate$1$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom);
                return WindowInsets.CONSUMED;
            }
        });
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.ui.ControlsActivity$initBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent2) {
                String action = intent2.getAction();
                if (Intrinsics.areEqual(action, "android.intent.action.SCREEN_OFF") || Intrinsics.areEqual(action, "android.intent.action.DREAMING_STARTED")) {
                    ControlsActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        ControlsActivity$initBroadcastReceiver$1 controlsActivity$initBroadcastReceiver$1 = this.broadcastReceiver;
        if (controlsActivity$initBroadcastReceiver$1 == null) {
            controlsActivity$initBroadcastReceiver$1 = null;
        }
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, controlsActivity$initBroadcastReceiver$1, intentFilter, null, null, 0, null, 60);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        ControlsActivity$initBroadcastReceiver$1 controlsActivity$initBroadcastReceiver$1 = this.broadcastReceiver;
        if (controlsActivity$initBroadcastReceiver$1 == null) {
            controlsActivity$initBroadcastReceiver$1 = null;
        }
        this.broadcastDispatcher.unregisterReceiver(controlsActivity$initBroadcastReceiver$1);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mExitToDream = getIntent().getBooleanExtra("extra_exit_to_dream", false);
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.control_detail_root);
        this.parent = viewGroup;
        viewGroup.setAlpha(0.0f);
        if (!((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.USE_APP_PANELS) || this.keyguardStateController.isUnlocked()) {
            new Runnable() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsActivity.this.finishOrReturnToDream();
                }
            };
            this.uiController.getClass();
        } else {
            ((ControlsSettingsDialogManagerImpl) this.controlsSettingsDialogManager).maybeShowDialog(this, new Function0() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final ControlsActivity controlsActivity = ControlsActivity.this;
                    ControlsUiController controlsUiController = controlsActivity.uiController;
                    new Runnable() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlsActivity.this.finishOrReturnToDream();
                        }
                    };
                    controlsUiController.getClass();
                    return Unit.INSTANCE;
                }
            });
        }
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup2 = this.parent;
        if (viewGroup2 == null) {
            viewGroup2 = null;
        }
        controlsAnimations.getClass();
        ControlsAnimations.enterAnimation(viewGroup2).start();
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        this.mExitToDream = false;
        ViewGroup viewGroup = this.parent;
        if (viewGroup == null) {
            viewGroup = null;
        }
        ((CustomControlsUiControllerImpl) this.uiController).hide(viewGroup);
        ((ControlsSettingsDialogManagerImpl) this.controlsSettingsDialogManager).closeDialog();
    }
}
