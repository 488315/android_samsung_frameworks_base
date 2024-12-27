package com.android.internal.accessibility.dialog;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;

import com.android.internal.accessibility.util.AccessibilityUtils;

import com.samsung.android.emergencymode.SemEmergencyManager;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AccessibilitySamsungShortcutChooserActivity extends Activity {
    private AccessibilityManager mAccessibilityManager;
    private AlertDialog mMenuDialog;
    private ShortcutTargetAdapter mTargetAdapter;
    private int mShortcutType = -1;
    private int mCurrentDisplayId = 0;
    private final List<AccessibilityTarget> mTargets = new ArrayList();
    private final BroadcastReceiver mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())
                            && AccessibilitySamsungShortcutChooserActivity.this.mMenuDialog
                                    != null) {
                        AccessibilitySamsungShortcutChooserActivity.this.mMenuDialog.dismiss();
                    }
                }
            };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypedArray theme = getTheme().obtainStyledAttributes(R.styleable.Theme);
        if (!theme.getBoolean(38, false)) {
            requestWindowFeature(1);
        }
        theme.recycle();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mShortcutType = extras.getInt("shortcutType");
        }
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, this.mShortcutType));
        this.mTargetAdapter = new ShortcutTargetAdapter(this.mTargets);
        this.mMenuDialog = createMenuDialog();
        this.mMenuDialog.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        AccessibilitySamsungShortcutChooserActivity.this.lambda$onCreate$0(
                                dialogInterface);
                    }
                });
        Window w = this.mMenuDialog.getWindow();
        WindowManager.LayoutParams attr = w.getAttributes();
        attr.type = 2009;
        attr.gravity = getGravity();
        w.setAttributes(attr);
        this.mMenuDialog.show();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(this.mReceiver, filter);
        if (getDisplay() != null) {
            this.mCurrentDisplayId = getDisplay().getDisplayId();
        }
        this.mAccessibilityManager =
                (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(DialogInterface dialog) {
        updateDialogListeners();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mMenuDialog != null) {
            Window w = this.mMenuDialog.getWindow();
            WindowManager.LayoutParams attr = w.getAttributes();
            attr.gravity = getGravity();
            w.setAttributes(attr);
        }
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        unregisterReceiver(this.mReceiver);
        this.mMenuDialog.dismiss();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int tempDisplayId = 0;
        if (getDisplay() != null) {
            tempDisplayId = getDisplay().getDisplayId();
        }
        if (this.mCurrentDisplayId != tempDisplayId && tempDisplayId == 2) {
            this.mCurrentDisplayId = tempDisplayId;
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetSelected(AdapterView<?> parent, View view, int position, long id) {
        AccessibilityTarget target = this.mTargets.get(position);
        String targetName = target.getId();
        String targetLabel = target.getLabel().toString();
        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
        if (AccessibilityUtils.needToShowToast(this, targetName, targetLabel)) {
            this.mMenuDialog.dismiss();
            return;
        }
        Settings.Secure.putString(
                getContentResolver(),
                Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT,
                targetName);
        if (this.mAccessibilityManager != null) {
            this.mAccessibilityManager.semPerformAccessibilityButtonClick(
                    this.mCurrentDisplayId, this.mShortcutType, targetName);
        }
        A11yLogger.insertShortcutSaLog(this, target.getShortcutType(), targetName);
        this.mMenuDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogListeners$1(View view) {
        onEditShortcutClicked();
    }

    private void updateDialogListeners() {
        this.mMenuDialog
                .getButton(-1)
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AccessibilitySamsungShortcutChooserActivity.this
                                        .lambda$updateDialogListeners$1(view);
                            }
                        });
        this.mMenuDialog
                .getListView()
                .setOnItemClickListener(
                        new AdapterView
                                .OnItemClickListener() { // from class:
                                                         // com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda3
                            @Override // android.widget.AdapterView.OnItemClickListener
                            public final void onItemClick(
                                    AdapterView adapterView, View view, int i, long j) {
                                AccessibilitySamsungShortcutChooserActivity.this.onTargetSelected(
                                        adapterView, view, i, j);
                            }
                        });
    }

    private void onEditShortcutClicked() {
        if (AccessibilityUtils.makeToastForCoverScreen(this, null)) {
            this.mMenuDialog.dismiss();
            return;
        }
        Intent intent = new Intent();
        if (this.mShortcutType == 1) {
            intent.setClassName(
                    "com.android.settings",
                    "com.android.settings.Settings$AccessibilityButtonPreferenceActivity");
        } else if (this.mShortcutType == 2) {
            intent.setClassName(
                    "com.android.settings",
                    "com.android.settings.Settings$VolumeUpAndDownPreferenceActivity");
        } else if (this.mShortcutType == 512) {
            intent.setClassName(
                    "com.android.settings",
                    "com.android.settings.Settings$SideAndVolumeUpPreferenceActivity");
        }
        intent.setFlags(268468224);
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
        }
    }

    private AlertDialog createMenuDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this)
                        .setAdapter(this.mTargetAdapter, null)
                        .setOnDismissListener(
                                new DialogInterface
                                        .OnDismissListener() { // from class:
                                                               // com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda0
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AccessibilitySamsungShortcutChooserActivity.this
                                                .lambda$createMenuDialog$2(dialogInterface);
                                    }
                                });
        boolean allowEditing =
                AccessibilityUtils.isUserSetupCompleted(this)
                        && !SemEmergencyManager.isEmergencyMode(this);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            allowEditing = false;
        }
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            allowEditing = true;
        }
        if (allowEditing) {
            String positiveButtonText =
                    getString(
                            com.android.internal.R.string.edit_accessibility_shortcut_menu_button);
            builder.setPositiveButton(positiveButtonText, (DialogInterface.OnClickListener) null);
        }
        return builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMenuDialog$2(DialogInterface dialog) {
        finish();
    }

    private int getGravity() {
        if (this.mShortcutType == 1) {
            if (getResources().getBoolean(com.android.internal.R.bool.sem_config_dialogLargeScreen)
                    || AccessibilityUtils.isDexMode(this)) {
                return 85;
            }
            return 81;
        }
        return 81;
    }
}
