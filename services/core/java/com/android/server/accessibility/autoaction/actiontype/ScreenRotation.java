package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.view.ContextThemeWrapper;

import com.android.internal.accessibility.util.AccessibilityUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ScreenRotation extends CornerActionType {
    public String mAccelerometerRotationUri;
    public Context mContext;
    public int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.accessibility.autoaction.actiontype.ScreenRotation$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        if (AccessibilityUtils.makeToastForCoverScreen(this.mContext, (String) null)) {
            return;
        }
        boolean z =
                this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.folder_type")
                        && this.mContext
                                .getPackageManager()
                                .hasSystemFeature("com.sec.feature.dual_lcd");
        boolean z2 = this.mContext.getResources().getConfiguration().hardKeyboardHidden == 2;
        if (!z || z2) {
            this.mAccelerometerRotationUri = "accelerometer_rotation";
        } else {
            this.mAccelerometerRotationUri = "accelerometer_rotation_second";
        }
        if (Settings.System.getIntForUser(
                        this.mContext.getContentResolver(),
                        this.mAccelerometerRotationUri,
                        0,
                        this.mUserId)
                != 1) {
            rotateScreen();
            return;
        }
        Context context = this.mContext;
        if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
            context = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault);
        }
        AlertDialog create =
                new AlertDialog.Builder(context)
                        .setTitle(17043243)
                        .setMessage(R.string.config_satellite_sim_plmn_identifier)
                        .setPositiveButton(
                                17043253,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.android.server.accessibility.autoaction.actiontype.ScreenRotation.2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        dialogInterface.dismiss();
                                        ContentResolver contentResolver =
                                                ScreenRotation.this.mContext.getContentResolver();
                                        ScreenRotation screenRotation = ScreenRotation.this;
                                        Settings.System.putIntForUser(
                                                contentResolver,
                                                screenRotation.mAccelerometerRotationUri,
                                                0,
                                                screenRotation.mUserId);
                                        ScreenRotation.this.rotateScreen();
                                    }
                                })
                        .setNegativeButton(R.string.cancel, new AnonymousClass1())
                        .setCancelable(true)
                        .create();
        create.getWindow().setType(2280);
        create.setCanceledOnTouchOutside(true);
        create.show();
    }

    public final void rotateScreen() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = 0;
        int i2 = this.mUserId;
        int intForUser = Settings.System.getIntForUser(contentResolver, "user_rotation", 0, i2);
        if (intForUser == 0) {
            i = 1;
        } else if (intForUser != 1 && intForUser != 3) {
            i = intForUser;
        }
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "user_rotation", i, i2);
    }
}
