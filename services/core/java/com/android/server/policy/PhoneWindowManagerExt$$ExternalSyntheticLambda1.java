package com.android.server.policy;

import android.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PhoneWindowManagerExt$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((PhoneWindowManagerExt) obj)
                        .mContext.sendBroadcastAsUser(
                                new Intent("com.samsung.android.action.START_DOCK_OR_HOME"),
                                UserHandle.CURRENT,
                                "com.samsung.android.permisson.START_DOCK_OR_HOME");
                break;
            case 1:
                final PhoneWindowManagerExt phoneWindowManagerExt = (PhoneWindowManagerExt) obj;
                if (phoneWindowManagerExt.escDialog != null) {
                    Slog.d("PhoneWindowManagerExt", "escDialog is showing");
                    break;
                } else {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(
                                    phoneWindowManagerExt.mContext,
                                    (phoneWindowManagerExt
                                                                    .mContext
                                                                    .getResources()
                                                                    .getConfiguration()
                                                                    .uiMode
                                                            & 48)
                                                    == 32
                                            ? R.style.Theme.DeviceDefault.Dialog.Alert
                                            : R.style.Theme.DeviceDefault.Light.Dialog.Alert);
                    builder.setMessage(
                            phoneWindowManagerExt.mContext.getResources().getString(17043029));
                    final int i2 = 0;
                    builder.setNegativeButton(
                            R.string.cancel,
                            new DialogInterface
                                    .OnClickListener() { // from class:
                                                         // com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda18
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i3) {
                                    switch (i2) {
                                        case 0:
                                            dialogInterface.dismiss();
                                            break;
                                        default:
                                            dialogInterface.dismiss();
                                            break;
                                    }
                                }
                            });
                    final int i3 = 0;
                    builder.setOnDismissListener(
                            new DialogInterface
                                    .OnDismissListener() { // from class:
                                                           // com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda19
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    int i4 = i3;
                                    Object obj2 = phoneWindowManagerExt;
                                    switch (i4) {
                                        case 0:
                                            ((PhoneWindowManagerExt) obj2).escDialog = null;
                                            break;
                                        default:
                                            ((PhoneWindowManagerExt.HotKey) obj2).guideDialog =
                                                    null;
                                            break;
                                    }
                                }
                            });
                    builder.setPositiveButton(
                            17043005,
                            new DialogInterface
                                    .OnClickListener() { // from class:
                                                         // com.android.server.policy.PhoneWindowManagerExt.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i4) {
                                    try {
                                        Intent intent = new Intent();
                                        intent.setFlags(268468224);
                                        intent.setAction(
                                                "com.samsung.settings.CUSTOMIZE_KEY_SETTINGS");
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                ":settings:fragment_args_key", "modifier_keys_esc");
                                        intent.putExtra(":settings:show_fragment_args", bundle);
                                        PhoneWindowManagerExt.this.mContext.startActivityAsUser(
                                                intent, UserHandle.CURRENT);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    AlertDialog create = builder.create();
                    phoneWindowManagerExt.escDialog = create;
                    create.getWindow().setType(2003);
                    phoneWindowManagerExt.escDialog.getWindow().addPrivateFlags(16);
                    phoneWindowManagerExt.escDialog.show();
                    break;
                }
            case 2:
                KeyCustomizationManager keyCustomizationManager =
                        ((PhoneWindowManagerExt) obj).mKeyCustomizationPolicy;
                keyCustomizationManager.mKeyCustomizationInfoManager.init(0, false);
                for (int i4 : KeyCustomizationConstants.ALL_KEYCODE_TYPE) {
                    keyCustomizationManager.initPowerBehaviorAndSingleKeyGestureDetectorRule(i4);
                }
                keyCustomizationManager.defaultLongPressTimeout =
                        keyCustomizationManager
                                .mContext
                                .getResources()
                                .getInteger(R.integer.config_maxResolverActivityColumns);
                break;
            case 3:
                KeyboardShortcutManager keyboardShortcutManager =
                        ((PhoneWindowManagerExt) obj).mKeyboardShortcutPolicy;
                keyboardShortcutManager.getClass();
                Slog.d("KeyboardShortcutManager", "init()");
                int[] iArr = KeyboardShortcutManager.SHORT_TYPE_LIST;
                for (int i5 = 0; i5 < 5; i5++) {
                    int i6 = iArr[i5];
                    keyboardShortcutManager.mPreloadBehaviorMap.put(
                            i6, keyboardShortcutManager.getBehavior(i6));
                }
                keyboardShortcutManager.mShortcutMap.put(29, "app_shortcuts_command_a");
                keyboardShortcutManager.mShortcutMap.put(30, "app_shortcuts_command_b");
                keyboardShortcutManager.mShortcutMap.put(31, "app_shortcuts_command_c");
                keyboardShortcutManager.mShortcutMap.put(32, "app_shortcuts_command_d");
                keyboardShortcutManager.mShortcutMap.put(33, "app_shortcuts_command_e");
                keyboardShortcutManager.mShortcutMap.put(34, "app_shortcuts_command_f");
                keyboardShortcutManager.mShortcutMap.put(36, "app_shortcuts_command_h");
                keyboardShortcutManager.mShortcutMap.put(37, "app_shortcuts_command_i");
                keyboardShortcutManager.mShortcutMap.put(38, "app_shortcuts_command_j");
                keyboardShortcutManager.mShortcutMap.put(39, "app_shortcuts_command_k");
                keyboardShortcutManager.mShortcutMap.put(41, "app_shortcuts_command_m");
                keyboardShortcutManager.mShortcutMap.put(44, "app_shortcuts_command_p");
                keyboardShortcutManager.mShortcutMap.put(46, "app_shortcuts_command_r");
                keyboardShortcutManager.mShortcutMap.put(47, "app_shortcuts_command_s");
                keyboardShortcutManager.mShortcutMap.put(54, "app_shortcuts_command_z");
                break;
            case 4:
                PhoneWindowManagerExt phoneWindowManagerExt2 = (PhoneWindowManagerExt) obj;
                if (phoneWindowManagerExt2.mWakeAndUnlockTriggered) {
                    Slog.d("PhoneWindowManagerExt", "WakeAndUnlock not triggered");
                    phoneWindowManagerExt2.mWakeAndUnlockTriggered = false;
                    break;
                }
                break;
            default:
                final PhoneWindowManagerExt.HotKey hotKey = (PhoneWindowManagerExt.HotKey) obj;
                if (hotKey.guideDialog != null) {
                    Slog.d("PhoneWindowManagerExt", "showHotKeyGuideDialog is showing");
                    break;
                } else {
                    PhoneWindowManagerExt phoneWindowManagerExt3 = PhoneWindowManagerExt.this;
                    AlertDialog.Builder builder2 =
                            new AlertDialog.Builder(phoneWindowManagerExt3.mContext);
                    builder2.setTitle(R.string.minutes);
                    builder2.setMessage(
                            phoneWindowManagerExt3
                                    .mContext
                                    .getResources()
                                    .getQuantityString(R.plurals.duration_hours_relative, 3, 3));
                    final int i7 = 1;
                    builder2.setPositiveButton(
                            R.string.ok,
                            new DialogInterface
                                    .OnClickListener() { // from class:
                                                         // com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda18
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(
                                        DialogInterface dialogInterface, int i32) {
                                    switch (i7) {
                                        case 0:
                                            dialogInterface.dismiss();
                                            break;
                                        default:
                                            dialogInterface.dismiss();
                                            break;
                                    }
                                }
                            });
                    final int i8 = 1;
                    builder2.setOnDismissListener(
                            new DialogInterface
                                    .OnDismissListener() { // from class:
                                                           // com.android.server.policy.PhoneWindowManagerExt$$ExternalSyntheticLambda19
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    int i42 = i8;
                                    Object obj2 = hotKey;
                                    switch (i42) {
                                        case 0:
                                            ((PhoneWindowManagerExt) obj2).escDialog = null;
                                            break;
                                        default:
                                            ((PhoneWindowManagerExt.HotKey) obj2).guideDialog =
                                                    null;
                                            break;
                                    }
                                }
                            });
                    AlertDialog create2 = builder2.create();
                    hotKey.guideDialog = create2;
                    create2.getWindow().getAttributes().setTitle("HotKeyGuideDialog");
                    hotKey.guideDialog.getWindow().setType(2008);
                    hotKey.guideDialog.show();
                    break;
                }
        }
    }
}
