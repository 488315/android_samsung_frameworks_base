package com.android.internal.accessibility.dialog;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import com.android.internal.accessibility.util.AccessibilityUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AccessibilityShortcutChooserActivity extends Activity {
    private static final String KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE = "accessibility_shortcut_menu_mode";
    private AlertDialog mMenuDialog;
    private Dialog mPermissionDialog;
    private ShortcutTargetAdapter mTargetAdapter;
    private final int mShortcutType = 2;
    private final List<AccessibilityTarget> mTargets = new ArrayList();

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!getTheme().obtainStyledAttributes(R.styleable.Theme).getBoolean(38, false)) {
            requestWindowFeature(1);
        }
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, 2));
        this.mTargetAdapter = new ShortcutTargetAdapter(this.mTargets);
        AlertDialog alertDialogCreateMenuDialog = createMenuDialog();
        this.mMenuDialog = alertDialogCreateMenuDialog;
        alertDialogCreateMenuDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.lambda$onCreate$0(dialogInterface);
            }
        });
        this.mMenuDialog.show();
        if (bundle == null || bundle.getInt(KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE, 0) != 1) {
            return;
        }
        onEditButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(DialogInterface dialogInterface) {
        updateDialogListeners();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mMenuDialog.setOnDismissListener(null);
        this.mMenuDialog.dismiss();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE, this.mTargetAdapter.getShortcutMenuMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetSelected(AdapterView<?> adapterView, View view, int i, long j) {
        AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        if (((accessibilityTarget instanceof AccessibilityServiceTarget) || (accessibilityTarget instanceof AccessibilityActivityTarget)) && sendRestrictedDialogIntentIfNeeded(accessibilityTarget)) {
            return;
        }
        accessibilityTarget.onSelected();
        this.mMenuDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetChecked(AdapterView<?> adapterView, View view, int i, long j) {
        AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        if (accessibilityTarget instanceof AccessibilityServiceTarget) {
            AccessibilityServiceTarget accessibilityServiceTarget = (AccessibilityServiceTarget) accessibilityTarget;
            if (sendRestrictedDialogIntentIfNeeded(accessibilityTarget)) {
                return;
            }
            if (((AccessibilityManager) getSystemService(AccessibilityManager.class)).isAccessibilityServiceWarningRequired(accessibilityServiceTarget.getAccessibilityServiceInfo())) {
                showPermissionDialogIfNeeded(this, accessibilityServiceTarget, i, this.mTargetAdapter);
                return;
            }
        }
        if (accessibilityTarget instanceof AccessibilityActivityTarget) {
            AccessibilityActivityTarget accessibilityActivityTarget = (AccessibilityActivityTarget) accessibilityTarget;
            if (!accessibilityActivityTarget.isShortcutEnabled() && sendRestrictedDialogIntentIfNeeded(accessibilityActivityTarget)) {
                return;
            }
        }
        accessibilityTarget.onCheckedChanged(!accessibilityTarget.isShortcutEnabled());
        this.mTargetAdapter.notifyDataSetChanged();
    }

    private boolean sendRestrictedDialogIntentIfNeeded(AccessibilityTarget accessibilityTarget) {
        if (AccessibilityTargetHelper.isAccessibilityTargetAllowed(this, accessibilityTarget.getComponentName().getPackageName(), accessibilityTarget.getUid())) {
            return false;
        }
        AccessibilityTargetHelper.sendRestrictedDialogIntent(this, accessibilityTarget.getComponentName().getPackageName(), accessibilityTarget.getUid());
        return true;
    }

    private void showPermissionDialogIfNeeded(final Context context, final AccessibilityServiceTarget accessibilityServiceTarget, final int i, final ShortcutTargetAdapter shortcutTargetAdapter) {
        if (this.mPermissionDialog != null) {
            return;
        }
        AlertDialog alertDialogCreateAccessibilityServiceWarningDialog = AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(context, accessibilityServiceTarget.getAccessibilityServiceInfo(), new View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPermissionDialogIfNeeded$1(accessibilityServiceTarget, shortcutTargetAdapter, view);
            }
        }, new View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPermissionDialogIfNeeded$2(accessibilityServiceTarget, view);
            }
        }, new View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPermissionDialogIfNeeded$3(i, context, accessibilityServiceTarget, shortcutTargetAdapter, view);
            }
        });
        this.mPermissionDialog = alertDialogCreateAccessibilityServiceWarningDialog;
        alertDialogCreateAccessibilityServiceWarningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showPermissionDialogIfNeeded$4(dialogInterface);
            }
        });
        this.mPermissionDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$1(AccessibilityServiceTarget accessibilityServiceTarget, ShortcutTargetAdapter shortcutTargetAdapter, View view) {
        accessibilityServiceTarget.onCheckedChanged(true);
        shortcutTargetAdapter.notifyDataSetChanged();
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$2(AccessibilityServiceTarget accessibilityServiceTarget, View view) {
        accessibilityServiceTarget.onCheckedChanged(false);
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$3(int i, Context context, AccessibilityServiceTarget accessibilityServiceTarget, ShortcutTargetAdapter shortcutTargetAdapter, View view) {
        this.mTargets.remove(i);
        context.getPackageManager().getPackageInstaller().uninstall(accessibilityServiceTarget.getComponentName().getPackageName(), (IntentSender) null);
        shortcutTargetAdapter.notifyDataSetChanged();
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$4(DialogInterface dialogInterface) {
        this.mPermissionDialog = null;
    }

    private void onDoneButtonClicked() {
        this.mTargets.clear();
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, 2));
        if (this.mTargets.isEmpty()) {
            this.mMenuDialog.dismiss();
            return;
        }
        this.mTargetAdapter.setShortcutMenuMode(0);
        this.mTargetAdapter.notifyDataSetChanged();
        this.mMenuDialog.getButton(-1).lambda$setTextAsync$0(getString(com.android.internal.R.string.edit_accessibility_shortcut_menu_button));
        updateDialogListeners();
    }

    private void onEditButtonClicked() {
        this.mTargets.clear();
        this.mTargets.addAll(AccessibilityTargetHelper.getInstalledTargets(this, 2));
        this.mTargetAdapter.setShortcutMenuMode(1);
        this.mTargetAdapter.notifyDataSetChanged();
        this.mMenuDialog.getButton(-1).lambda$setTextAsync$0(getString(com.android.internal.R.string.done_accessibility_shortcut_menu_button));
        updateDialogListeners();
    }

    private void updateDialogListeners() {
        boolean z = this.mTargetAdapter.getShortcutMenuMode() == 1;
        this.mMenuDialog.setTitle(getString(z ? com.android.internal.R.string.accessibility_edit_shortcut_menu_volume_title : com.android.internal.R.string.accessibility_select_shortcut_menu_title));
        this.mMenuDialog.getButton(-1).setOnClickListener(z ? new View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateDialogListeners$5(view);
            }
        } : new View.OnClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateDialogListeners$6(view);
            }
        });
        this.mMenuDialog.getListView().setOnItemClickListener(z ? new AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda2
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.onTargetChecked(adapterView, view, i, j);
            }
        } : new AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                this.f$0.onTargetSelected(adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogListeners$5(View view) {
        onDoneButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogListeners$6(View view) {
        onEditButtonClicked();
    }

    public AlertDialog getMenuDialog() {
        return this.mMenuDialog;
    }

    public Dialog getPermissionDialog() {
        return this.mPermissionDialog;
    }

    private AlertDialog createMenuDialog() {
        AlertDialog.Builder onDismissListener = new AlertDialog.Builder(this).setTitle(getString(com.android.internal.R.string.accessibility_select_shortcut_menu_title)).setAdapter(this.mTargetAdapter, null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$createMenuDialog$7(dialogInterface);
            }
        });
        boolean zIsUserSetupCompleted = AccessibilityUtils.isUserSetupCompleted(this);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
        boolean z = false;
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            z = true;
            zIsUserSetupCompleted = false;
        }
        if (zIsUserSetupCompleted) {
            onDismissListener.setPositiveButton(getString(com.android.internal.R.string.edit_accessibility_shortcut_menu_button), (DialogInterface.OnClickListener) null);
        }
        AlertDialog alertDialogCreate = onDismissListener.create();
        if (z) {
            alertDialogCreate.getWindow().addFlags(524288);
        }
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMenuDialog$7(DialogInterface dialogInterface) {
        finish();
    }
}
