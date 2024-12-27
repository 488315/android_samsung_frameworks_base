package com.android.systemui.mediaprojection.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.StatusBarManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.projection.IMediaProjection;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;

public class MediaProjectionPermissionActivity extends Activity implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mDialog;
    public final FeatureFlags mFeatureFlags;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public String mPackageName;
    public final Lazy mScreenCaptureDevicePolicyResolver;
    public final ScreenCaptureDisabledDialogDelegate mScreenCaptureDisabledDialogDelegate;
    public final StatusBarManager mStatusBarManager;
    public int mUid;
    public boolean mReviewGrantedConsentRequired = false;
    public boolean mUserSelectingTask = false;

    public MediaProjectionPermissionActivity(FeatureFlags featureFlags, Lazy lazy, StatusBarManager statusBarManager, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate) {
        this.mFeatureFlags = featureFlags;
        this.mScreenCaptureDevicePolicyResolver = lazy;
        this.mStatusBarManager = statusBarManager;
        this.mMediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.mScreenCaptureDisabledDialogDelegate = screenCaptureDisabledDialogDelegate;
    }

    @Override // android.app.Activity
    public final void finish() {
        if (this.mUserSelectingTask) {
            super.finish();
        } else {
            finish(0, null);
        }
    }

    public final void grantMediaProjectionPermission(int i) {
        AlertDialog alertDialog;
        try {
            if (i == 1) {
                try {
                    IMediaProjection createOrReuseProjection = MediaProjectionServiceHelper.createOrReuseProjection(this.mUid, this.mPackageName, this.mReviewGrantedConsentRequired);
                    Intent intent = new Intent();
                    intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", createOrReuseProjection.asBinder());
                    setResult(-1, intent);
                    finish(1, createOrReuseProjection);
                } catch (RemoteException e) {
                    Log.e("MediaProjectionPermissionActivity", "Error granting projection permission", e);
                    setResult(0);
                    finish(0, null);
                    alertDialog = this.mDialog;
                    if (alertDialog == null) {
                        return;
                    }
                }
            }
            if (((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING) && i == 0) {
                IMediaProjection createOrReuseProjection2 = MediaProjectionServiceHelper.createOrReuseProjection(this.mUid, this.mPackageName, this.mReviewGrantedConsentRequired);
                Intent intent2 = new Intent(this, (Class<?>) MediaProjectionAppSelectorActivity.class);
                intent2.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", createOrReuseProjection2.asBinder());
                intent2.putExtra("launched_from_user_handle", UserHandle.getUserHandleForUid(getLaunchedFromUid()));
                intent2.putExtra("launched_from_host_uid", getLaunchedFromUid());
                intent2.putExtra("extra_media_projection_user_consent_required", this.mReviewGrantedConsentRequired);
                intent2.setFlags(QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING);
                this.mUserSelectingTask = true;
                startActivityAsUser(intent2, UserHandle.of(0));
                this.mStatusBarManager.collapsePanels();
            }
            alertDialog = this.mDialog;
            if (alertDialog == null) {
                return;
            }
            alertDialog.dismiss();
        } catch (Throwable th) {
            AlertDialog alertDialog2 = this.mDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            throw th;
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            grantMediaProjectionPermission(1);
            return;
        }
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        setResult(0);
        finish(0, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x022c  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r19) {
        /*
            Method dump skipped, instructions count: 655
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.setOnDismissListener(null);
            this.mDialog.setOnCancelListener(null);
            this.mDialog.dismiss();
        }
    }

    public final void setUpDialog(AlertDialog alertDialog) {
        SystemUIDialog.registerDismissListener(alertDialog);
        SystemUIDialog.applyFlags(alertDialog);
        SystemUIDialog.setDialogSize(alertDialog);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                MediaProjectionPermissionActivity mediaProjectionPermissionActivity = MediaProjectionPermissionActivity.this;
                int i = MediaProjectionPermissionActivity.$r8$clinit;
                if (mediaProjectionPermissionActivity.isFinishing()) {
                    return;
                }
                mediaProjectionPermissionActivity.finish();
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                MediaProjectionPermissionActivity mediaProjectionPermissionActivity = MediaProjectionPermissionActivity.this;
                int i = MediaProjectionPermissionActivity.$r8$clinit;
                if (mediaProjectionPermissionActivity.isFinishing()) {
                    return;
                }
                mediaProjectionPermissionActivity.finish();
            }
        });
        alertDialog.create();
        alertDialog.getButton(-1).setFilterTouchesWhenObscured(true);
        alertDialog.getWindow().addSystemFlags(524288);
    }

    public final void finish(int i, IMediaProjection iMediaProjection) {
        boolean z = this.mReviewGrantedConsentRequired;
        MediaProjectionServiceHelper.Companion.getClass();
        MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(i, z, iMediaProjection);
        super.finish();
    }
}
