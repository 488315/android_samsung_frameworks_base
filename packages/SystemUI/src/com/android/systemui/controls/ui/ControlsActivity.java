package com.android.systemui.controls.ui;

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
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsAnimations;
import com.android.systemui.controls.management.ControlsAnimations$observerForAnimations$1;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class ControlsActivity extends ComponentActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public ControlsActivity$initBroadcastReceiver$1 broadcastReceiver;
    public final IDreamManager dreamManager;
    public final KeyguardStateController keyguardStateController;
    public final Configuration lastConfiguration = new Configuration();
    public boolean mExitToDream;
    public ViewGroup parent;
    public final ControlsUiController uiController;

    public ControlsActivity(ControlsUiController controlsUiController, BroadcastDispatcher broadcastDispatcher, IDreamManager iDreamManager, FeatureFlags featureFlags, ControlsSettingsDialogManager controlsSettingsDialogManager, KeyguardStateController keyguardStateController) {
        this.uiController = controlsUiController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dreamManager = iDreamManager;
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
        if ((this.lastConfiguration.diff(configuration) & 3200) != 0) {
            this.uiController.getClass();
        }
        this.lastConfiguration.setTo(configuration);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.lastConfiguration.setTo(getResources().getConfiguration());
        getWindow().addPrivateFlags(536870912);
        setContentView(R.layout.controls_fullscreen);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.control_detail_root);
        Window window = getWindow();
        Intent intent = getIntent();
        controlsAnimations.getClass();
        this.mLifecycleRegistry.addObserver(new ControlsAnimations$observerForAnimations$1(intent, viewGroup, false, window));
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
        IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_OFF", "android.intent.action.DREAMING_STARTED");
        ControlsActivity$initBroadcastReceiver$1 controlsActivity$initBroadcastReceiver$1 = this.broadcastReceiver;
        if (controlsActivity$initBroadcastReceiver$1 == null) {
            controlsActivity$initBroadcastReceiver$1 = null;
        }
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, controlsActivity$initBroadcastReceiver$1, m, null, null, 0, null, 60);
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
        if (!this.keyguardStateController.isUnlocked()) {
            new Function0() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$1
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
            };
            throw null;
        }
        new Runnable() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$2
            @Override // java.lang.Runnable
            public final void run() {
                ControlsActivity.this.finishOrReturnToDream();
            }
        };
        this.uiController.getClass();
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup2 = this.parent;
        ViewGroup viewGroup3 = viewGroup2 != null ? viewGroup2 : null;
        controlsAnimations.getClass();
        ControlsAnimations.enterAnimation(viewGroup3).start();
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        this.mExitToDream = false;
        ViewGroup viewGroup = this.parent;
        if (viewGroup == null) {
            viewGroup = null;
        }
        ((SecControlsUiControllerImpl) this.uiController).hide(viewGroup);
        throw null;
    }
}
