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
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!Intent.ACTION_SCREEN_OFF.equals(intent.getAction()) || AccessibilitySamsungShortcutChooserActivity.this.mMenuDialog == null) {
                return;
            }
            AccessibilitySamsungShortcutChooserActivity.this.mMenuDialog.dismiss();
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(R.styleable.Theme);
        if (!typedArrayObtainStyledAttributes.getBoolean(38, false)) {
            requestWindowFeature(1);
        }
        typedArrayObtainStyledAttributes.recycle();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mShortcutType = extras.getInt("shortcutType");
        }
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, this.mShortcutType));
        this.mTargetAdapter = new ShortcutTargetAdapter(this.mTargets);
        AlertDialog alertDialogCreateMenuDialog = createMenuDialog();
        this.mMenuDialog = alertDialogCreateMenuDialog;
        alertDialogCreateMenuDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.lambda$onCreate$0(dialogInterface);
            }
        });
        Window window = this.mMenuDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.type = 2009;
        attributes.gravity = getGravity();
        window.setAttributes(attributes);
        this.mMenuDialog.show();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(this.mReceiver, intentFilter);
        if (getDisplay() != null) {
            this.mCurrentDisplayId = getDisplay().getDisplayId();
        }
        this.mAccessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(DialogInterface dialogInterface) {
        updateDialogListeners();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AlertDialog alertDialog = this.mMenuDialog;
        if (alertDialog != null) {
            Window window = alertDialog.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = getGravity();
            window.setAttributes(attributes);
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
        int displayId = getDisplay() != null ? getDisplay().getDisplayId() : 0;
        if (this.mCurrentDisplayId != displayId && displayId == 2) {
            this.mCurrentDisplayId = displayId;
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetSelected(AdapterView<?> adapterView, View view, int i, long j) {
        AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        String id = accessibilityTarget.getId();
        String string = accessibilityTarget.getLabel().toString();
        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1));
        if (AccessibilityUtils.needToShowToast(this, id, string)) {
            this.mMenuDialog.dismiss();
            return;
        }
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT, id);
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.semPerformAccessibilityButtonClick(this.mCurrentDisplayId, this.mShortcutType, id);
        }
        A11yLogger.insertShortcutSaLog(this, accessibilityTarget.getShortcutType(), id);
        this.mMenuDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogListeners$1(View view) {
        onEditShortcutClicked();
    }

    private void updateDialogListeners() {
        this.mMenuDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateDialogListeners$1(view);
            }
        });
        this.mMenuDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.onTargetSelected(adapterView, view, i, j);
            }
        });
    }

    private void onEditShortcutClicked() {
        if (AccessibilityUtils.makeToastForCoverScreen(this, null)) {
            this.mMenuDialog.dismiss();
            return;
        }
        Intent intent = new Intent();
        int i = this.mShortcutType;
        if (i == 1 || i == 32) {
            intent.setClassName("com.android.settings", "com.android.settings.Settings$AccessibilityButtonPreferenceActivity");
        } else if (i == 2) {
            intent.setClassName("com.android.settings", "com.android.settings.Settings$VolumeUpAndDownPreferenceActivity");
        } else if (i == 512) {
            intent.setClassName("com.android.settings", "com.android.settings.Settings$SideAndVolumeUpPreferenceActivity");
        }
        intent.setFlags(268468224);
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException unused) {
        }
    }

    private AlertDialog createMenuDialog() {
        AlertDialog.Builder onDismissListener = new AlertDialog.Builder(this).setAdapter(this.mTargetAdapter, null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$createMenuDialog$2(dialogInterface);
            }
        });
        boolean z = AccessibilityUtils.isUserSetupCompleted(this) && !SemEmergencyManager.isEmergencyMode(this);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (AccessibilityUtils.isFoldedLargeCoverScreen() ? true : (keyguardManager == null || !keyguardManager.isKeyguardLocked()) ? z : false) {
            onDismissListener.setPositiveButton(getString(com.android.internal.R.string.edit_accessibility_shortcut_menu_button), (DialogInterface.OnClickListener) null);
        }
        return onDismissListener.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMenuDialog$2(DialogInterface dialogInterface) {
        finish();
    }

    private int getGravity() {
        if (this.mShortcutType == 1) {
            return (getResources().getBoolean(com.android.internal.R.bool.sem_config_dialogLargeScreen) || AccessibilityUtils.isInDesktopWindowing(this)) ? 85 : 81;
        }
        return 81;
    }
}
