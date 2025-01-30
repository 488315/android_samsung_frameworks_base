package com.android.systemui.media;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.BidiFormatter;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.MediaProjectionServiceHelper;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.Utils;
import com.samsung.android.app.SemDualAppManager;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MediaProjectionPermissionActivity extends Activity implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mDialog;
    public final FeatureFlags mFeatureFlags;
    public String mPackageName;
    public boolean mReviewGrantedConsentRequired = false;
    public int mUid;

    public MediaProjectionPermissionActivity(FeatureFlags featureFlags, Lazy lazy) {
        this.mFeatureFlags = featureFlags;
    }

    @Override // android.app.Activity
    public final void finish() {
        finish(0, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0042, code lost:
    
        if (r9 != null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x005c, code lost:
    
        r9 = new android.content.Intent("com.samsung.intent.action.MEDIA_PROJECTION_PERMISSION");
        r9.putExtra("isAllowed", true);
        getApplicationContext().sendBroadcast(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
    
        r9.dismiss();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0057, code lost:
    
        if (r9 == null) goto L22;
     */
    @Override // android.content.DialogInterface.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onClick(DialogInterface dialogInterface, int i) {
        AlertDialog alertDialog;
        if (i != -1) {
            AlertDialog alertDialog2 = this.mDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            Intent intent = new Intent("com.samsung.intent.action.MEDIA_PROJECTION_PERMISSION");
            intent.putExtra("isAllowed", false);
            getApplicationContext().sendBroadcast(intent);
            setResult(0);
            finish(0, null);
            return;
        }
        try {
            try {
                int i2 = this.mUid;
                String str = this.mPackageName;
                boolean z = this.mReviewGrantedConsentRequired;
                MediaProjectionServiceHelper.Companion.getClass();
                IMediaProjectionManager iMediaProjectionManager = MediaProjectionServiceHelper.service;
                IMediaProjection projection = z ? iMediaProjectionManager.getProjection(i2, str) : null;
                if (projection == null) {
                    projection = iMediaProjectionManager.createProjection(i2, str, 0, false);
                }
                Intent intent2 = new Intent();
                intent2.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", projection.asBinder());
                setResult(-1, intent2);
                finish(1, projection);
                FeatureFlags featureFlags = this.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags.getClass();
                alertDialog = this.mDialog;
            } catch (RemoteException e) {
                Log.e("MediaProjectionPermissionActivity", "Error granting projection permission", e);
                setResult(0);
                finish(0, null);
                alertDialog = this.mDialog;
            }
        } catch (Throwable th) {
            AlertDialog alertDialog3 = this.mDialog;
            if (alertDialog3 != null) {
                alertDialog3.dismiss();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        ApplicationInfo applicationInfo;
        String string;
        String str;
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mReviewGrantedConsentRequired = intent.getBooleanExtra("extra_media_projection_user_consent_required", false);
        String callingPackage = getCallingPackage();
        this.mPackageName = callingPackage;
        if (callingPackage == null) {
            if (!intent.hasExtra("extra_media_projection_package_reusing_consent")) {
                setResult(0);
                finish(0, null);
                return;
            }
            this.mPackageName = intent.getStringExtra("extra_media_projection_package_reusing_consent");
        }
        Intent intent2 = getIntent();
        int intExtra = intent2 == null ? 0 : intent2.getIntExtra("Userid", 0);
        PackageManager packageManager = getPackageManager();
        try {
            if (SemDualAppManager.isDualAppId(intExtra)) {
                applicationInfo = packageManager.getApplicationInfoAsUser(this.mPackageName, 0, intExtra);
                this.mUid = applicationInfo.uid;
            } else {
                applicationInfo = packageManager.getApplicationInfo(this.mPackageName, 0);
                this.mUid = applicationInfo.uid;
            }
            try {
                int i = this.mUid;
                String str2 = this.mPackageName;
                MediaProjectionServiceHelper.Companion companion = MediaProjectionServiceHelper.Companion;
                companion.getClass();
                IMediaProjectionManager iMediaProjectionManager = MediaProjectionServiceHelper.service;
                if (iMediaProjectionManager.hasProjectionPermission(i, str2)) {
                    int i2 = this.mUid;
                    String str3 = this.mPackageName;
                    boolean z = this.mReviewGrantedConsentRequired;
                    companion.getClass();
                    IMediaProjection projection = z ? iMediaProjectionManager.getProjection(i2, str3) : null;
                    if (projection == null) {
                        projection = iMediaProjectionManager.createProjection(i2, str3, 0, false);
                    }
                    Intent intent3 = new Intent();
                    intent3.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", projection.asBinder());
                    setResult(-1, intent3);
                    finish(1, projection);
                    return;
                }
                FeatureFlags featureFlags = this.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags.getClass();
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(42.0f);
                if (Utils.isHeadlessRemoteDisplayProvider(packageManager, this.mPackageName)) {
                    str = getString(R.string.media_projection_sys_service_dialog_warning);
                    string = getString(R.string.media_projection_sys_service_dialog_title);
                } else {
                    String charSequence = applicationInfo.loadLabel(packageManager).toString();
                    int length = charSequence.length();
                    int i3 = 0;
                    while (i3 < length) {
                        int codePointAt = charSequence.codePointAt(i3);
                        int type = Character.getType(codePointAt);
                        if (type == 13 || type == 15 || type == 14) {
                            charSequence = charSequence.substring(0, i3) + "…";
                            break;
                        }
                        i3 += Character.charCount(codePointAt);
                    }
                    if (charSequence.isEmpty()) {
                        charSequence = this.mPackageName;
                    }
                    String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(TextUtils.ellipsize(charSequence, textPaint, 500.0f, TextUtils.TruncateAt.END).toString());
                    boolean z2 = BasicRune.MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE;
                    String string2 = z2 ? getString(R.string.media_projection_dialog_warning_chn, new Object[]{unicodeWrap}) : getString(R.string.media_projection_dialog_warning, new Object[]{unicodeWrap});
                    SpannableString spannableString = new SpannableString(string2);
                    int indexOf = string2.indexOf(unicodeWrap);
                    if (indexOf >= 0) {
                        spannableString.setSpan(new StyleSpan(1), indexOf, unicodeWrap.length() + indexOf, 0);
                    }
                    string = z2 ? getString(R.string.media_projection_dialog_title_chn, new Object[]{unicodeWrap}) : getString(R.string.media_projection_dialog_title, new Object[]{unicodeWrap});
                    str = spannableString;
                }
                FeatureFlags featureFlags2 = this.mFeatureFlags;
                Flags flags2 = Flags.INSTANCE;
                featureFlags2.getClass();
                AlertDialog create = new AlertDialog.Builder(this, 2132018527).setTitle(string).setIcon(R.drawable.ic_media_projection_permission).setMessage(str).setPositiveButton(R.string.media_projection_action_text, this).setNeutralButton(android.R.string.cancel, this).create();
                this.mDialog = create;
                SystemUIDialog.registerDismissListener(create, null);
                SystemUIDialog.applyFlags(create);
                SystemUIDialog.setDialogSize(create);
                create.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.media.MediaProjectionPermissionActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        MediaProjectionPermissionActivity mediaProjectionPermissionActivity = MediaProjectionPermissionActivity.this;
                        int i4 = MediaProjectionPermissionActivity.$r8$clinit;
                        if (mediaProjectionPermissionActivity.isFinishing()) {
                            return;
                        }
                        mediaProjectionPermissionActivity.finish();
                    }
                });
                create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.media.MediaProjectionPermissionActivity$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        MediaProjectionPermissionActivity mediaProjectionPermissionActivity = MediaProjectionPermissionActivity.this;
                        int i4 = MediaProjectionPermissionActivity.$r8$clinit;
                        if (mediaProjectionPermissionActivity.isFinishing()) {
                            return;
                        }
                        mediaProjectionPermissionActivity.finish();
                    }
                });
                create.create();
                create.getButton(-1).setFilterTouchesWhenObscured(true);
                create.getWindow().addSystemFlags(524288);
                this.mDialog.show();
            } catch (RemoteException e) {
                Log.e("MediaProjectionPermissionActivity", "Error checking projection permissions", e);
                setResult(0);
                finish(0, null);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("MediaProjectionPermissionActivity", "Unable to look up package name", e2);
            setResult(0);
            finish(0, null);
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public final void finish(int i, IMediaProjection iMediaProjection) {
        boolean z = this.mReviewGrantedConsentRequired;
        MediaProjectionServiceHelper.Companion.getClass();
        MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(i, z, iMediaProjection);
        super.finish();
    }
}
