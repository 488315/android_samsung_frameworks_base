package com.android.internal.accessibility.dialog;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;

import com.android.internal.accessibility.util.AccessibilityUtils;

import java.util.ArrayList;
import java.util.List;

public class AccessibilityShortcutChooserActivity extends Activity {
    private static final String KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE =
            "accessibility_shortcut_menu_mode";
    private AlertDialog mMenuDialog;
    private Dialog mPermissionDialog;
    private ShortcutTargetAdapter mTargetAdapter;
    private final int mShortcutType = 2;
    private final List<AccessibilityTarget> mTargets = new ArrayList();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypedArray theme = getTheme().obtainStyledAttributes(R.styleable.Theme);
        if (!theme.getBoolean(38, false)) {
            requestWindowFeature(1);
        }
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, 2));
        this.mTargetAdapter = new ShortcutTargetAdapter(this.mTargets);
        this.mMenuDialog = createMenuDialog();
        this.mMenuDialog.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda5
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        AccessibilityShortcutChooserActivity.this.lambda$onCreate$0(
                                dialogInterface);
                    }
                });
        this.mMenuDialog.show();
        if (savedInstanceState != null) {
            int restoreShortcutMenuMode =
                    savedInstanceState.getInt(KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE, 0);
            if (restoreShortcutMenuMode == 1) {
                onEditButtonClicked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(DialogInterface dialog) {
        updateDialogListeners();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.mMenuDialog.setOnDismissListener(null);
        this.mMenuDialog.dismiss();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(
                KEY_ACCESSIBILITY_SHORTCUT_MENU_MODE, this.mTargetAdapter.getShortcutMenuMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetSelected(AdapterView<?> parent, View view, int position, long id) {
        AccessibilityTarget target = this.mTargets.get(position);
        if (((target instanceof AccessibilityServiceTarget)
                        || (target instanceof AccessibilityActivityTarget))
                && sendRestrictedDialogIntentIfNeeded(target)) {
            return;
        }
        target.onSelected();
        this.mMenuDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTargetChecked(AdapterView<?> parent, View view, int position, long id) {
        AccessibilityTarget target = this.mTargets.get(position);
        if (target instanceof AccessibilityServiceTarget) {
            AccessibilityServiceTarget serviceTarget = (AccessibilityServiceTarget) target;
            if (sendRestrictedDialogIntentIfNeeded(target)) {
                return;
            }
            AccessibilityManager am =
                    (AccessibilityManager) getSystemService(AccessibilityManager.class);
            if (am.isAccessibilityServiceWarningRequired(
                    serviceTarget.getAccessibilityServiceInfo())) {
                showPermissionDialogIfNeeded(
                        this, (AccessibilityServiceTarget) target, position, this.mTargetAdapter);
                return;
            }
        }
        if (target instanceof AccessibilityActivityTarget) {
            AccessibilityActivityTarget activityTarget = (AccessibilityActivityTarget) target;
            if (!activityTarget.isShortcutEnabled()
                    && sendRestrictedDialogIntentIfNeeded(activityTarget)) {
                return;
            }
        }
        target.onCheckedChanged(!target.isShortcutEnabled());
        this.mTargetAdapter.notifyDataSetChanged();
    }

    private boolean sendRestrictedDialogIntentIfNeeded(AccessibilityTarget target) {
        if (AccessibilityTargetHelper.isAccessibilityTargetAllowed(
                this, target.getComponentName().getPackageName(), target.getUid())) {
            return false;
        }
        AccessibilityTargetHelper.sendRestrictedDialogIntent(
                this, target.getComponentName().getPackageName(), target.getUid());
        return true;
    }

    private void showPermissionDialogIfNeeded(
            final Context context,
            final AccessibilityServiceTarget serviceTarget,
            final int position,
            final ShortcutTargetAdapter targetAdapter) {
        if (this.mPermissionDialog != null) {
            return;
        }
        this.mPermissionDialog =
                AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                        context,
                        serviceTarget.getAccessibilityServiceInfo(),
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda6
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AccessibilityShortcutChooserActivity.this
                                        .lambda$showPermissionDialogIfNeeded$1(
                                                serviceTarget, targetAdapter, view);
                            }
                        },
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda7
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AccessibilityShortcutChooserActivity.this
                                        .lambda$showPermissionDialogIfNeeded$2(serviceTarget, view);
                            }
                        },
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda8
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AccessibilityShortcutChooserActivity.this
                                        .lambda$showPermissionDialogIfNeeded$3(
                                                position,
                                                context,
                                                serviceTarget,
                                                targetAdapter,
                                                view);
                            }
                        });
        this.mPermissionDialog.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda9
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AccessibilityShortcutChooserActivity.this
                                .lambda$showPermissionDialogIfNeeded$4(dialogInterface);
                    }
                });
        this.mPermissionDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$1(
            AccessibilityServiceTarget serviceTarget, ShortcutTargetAdapter targetAdapter, View v) {
        serviceTarget.onCheckedChanged(true);
        targetAdapter.notifyDataSetChanged();
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$2(
            AccessibilityServiceTarget serviceTarget, View v) {
        serviceTarget.onCheckedChanged(false);
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$3(
            int position,
            Context context,
            AccessibilityServiceTarget serviceTarget,
            ShortcutTargetAdapter targetAdapter,
            View v) {
        this.mTargets.remove(position);
        context.getPackageManager()
                .getPackageInstaller()
                .uninstall(serviceTarget.getComponentName().getPackageName(), (IntentSender) null);
        targetAdapter.notifyDataSetChanged();
        this.mPermissionDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialogIfNeeded$4(DialogInterface dialog) {
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
        this.mMenuDialog
                .getButton(-1)
                .lambda$setTextAsync$0(
                        getString(
                                com.android.internal.R.string
                                        .edit_accessibility_shortcut_menu_button));
        updateDialogListeners();
    }

    private void onEditButtonClicked() {
        this.mTargets.clear();
        this.mTargets.addAll(AccessibilityTargetHelper.getInstalledTargets(this, 2));
        this.mTargetAdapter.setShortcutMenuMode(1);
        this.mTargetAdapter.notifyDataSetChanged();
        this.mMenuDialog
                .getButton(-1)
                .lambda$setTextAsync$0(
                        getString(
                                com.android.internal.R.string
                                        .done_accessibility_shortcut_menu_button));
        updateDialogListeners();
    }

    private void updateDialogListeners() {
        boolean isEditMenuMode = this.mTargetAdapter.getShortcutMenuMode() == 1;
        this.mMenuDialog.setTitle(
                getString(
                        isEditMenuMode
                                ? com.android.internal.R.string
                                        .accessibility_edit_shortcut_menu_volume_title
                                : com.android.internal.R.string
                                        .accessibility_select_shortcut_menu_title));
        this.mMenuDialog
                .getButton(-1)
                .setOnClickListener(
                        isEditMenuMode
                                ? new View
                                        .OnClickListener() { // from class:
                                                             // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AccessibilityShortcutChooserActivity.this
                                                .lambda$updateDialogListeners$5(view);
                                    }
                                }
                                : new View
                                        .OnClickListener() { // from class:
                                                             // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        AccessibilityShortcutChooserActivity.this
                                                .lambda$updateDialogListeners$6(view);
                                    }
                                });
        this.mMenuDialog
                .getListView()
                .setOnItemClickListener(
                        isEditMenuMode
                                ? new AdapterView
                                        .OnItemClickListener() { // from class:
                                                                 // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda2
                                    @Override // android.widget.AdapterView.OnItemClickListener
                                    public final void onItemClick(
                                            AdapterView adapterView, View view, int i, long j) {
                                        AccessibilityShortcutChooserActivity.this.onTargetChecked(
                                                adapterView, view, i, j);
                                    }
                                }
                                : new AdapterView
                                        .OnItemClickListener() { // from class:
                                                                 // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda3
                                    @Override // android.widget.AdapterView.OnItemClickListener
                                    public final void onItemClick(
                                            AdapterView adapterView, View view, int i, long j) {
                                        AccessibilityShortcutChooserActivity.this.onTargetSelected(
                                                adapterView, view, i, j);
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
        String dialogTitle =
                getString(com.android.internal.R.string.accessibility_select_shortcut_menu_title);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this)
                        .setTitle(dialogTitle)
                        .setAdapter(this.mTargetAdapter, null)
                        .setOnDismissListener(
                                new DialogInterface
                                        .OnDismissListener() { // from class:
                                                               // com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity$$ExternalSyntheticLambda4
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        AccessibilityShortcutChooserActivity.this
                                                .lambda$createMenuDialog$7(dialogInterface);
                                    }
                                });
        boolean allowEditing = AccessibilityUtils.isUserSetupCompleted(this);
        boolean showWhenLocked = false;
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
            allowEditing = false;
            showWhenLocked = true;
        }
        if (allowEditing) {
            String positiveButtonText =
                    getString(
                            com.android.internal.R.string.edit_accessibility_shortcut_menu_button);
            builder.setPositiveButton(positiveButtonText, (DialogInterface.OnClickListener) null);
        }
        AlertDialog dialog = builder.create();
        if (showWhenLocked) {
            dialog.getWindow().addFlags(524288);
        }
        return dialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMenuDialog$7(DialogInterface dialog) {
        finish();
    }
}
