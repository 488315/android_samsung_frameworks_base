package com.android.systemui.mediaprojection.permission;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.StatusBarManager;
import android.app.compat.CompatChanges;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.MediaProjectionConfig;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.BidiFormatter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.MediaProjectionUtils;
import com.android.systemui.mediaprojection.SessionCreationSource;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate;
import com.android.systemui.statusbar.phone.AlertDialogWithDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.Utils;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dagger.Lazy;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class MediaProjectionPermissionActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialogWithDelegate mDialog;
    public final KeyguardManager mKeyguardManager;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public String mPackageName;
    public final Lazy mScreenCaptureDevicePolicyResolver;
    public final ScreenCaptureDisabledDialogDelegate mScreenCaptureDisabledDialogDelegate;
    public final StatusBarManager mStatusBarManager;
    public int mUid;
    public boolean mReviewGrantedConsentRequired = false;
    public boolean mUserSelectingTask = false;

    public MediaProjectionPermissionActivity(FeatureFlags featureFlags, Lazy lazy, StatusBarManager statusBarManager, KeyguardManager keyguardManager, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate) {
        this.mScreenCaptureDevicePolicyResolver = lazy;
        this.mStatusBarManager = statusBarManager;
        this.mKeyguardManager = keyguardManager;
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

    public final void finishAsCancelled() {
        setResult(0);
        finish(0, null);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo;
        MediaProjectionPermissionActivity mediaProjectionPermissionActivityCreateDisplayContext;
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mReviewGrantedConsentRequired = intent.getBooleanExtra("extra_media_projection_user_consent_required", false);
        this.mPackageName = getLaunchedFromPackage();
        if (getCallingPackage() == null) {
            if (!intent.hasExtra("extra_media_projection_package_reusing_consent")) {
                finishAsCancelled();
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
                String str = this.mPackageName;
                MediaProjectionServiceHelper.Companion companion = MediaProjectionServiceHelper.Companion;
                companion.getClass();
                IMediaProjectionManager iMediaProjectionManager = MediaProjectionServiceHelper.service;
                boolean zHasProjectionPermission = iMediaProjectionManager.hasProjectionPermission(i, str);
                MediaProjectionMetricsLogger mediaProjectionMetricsLogger = this.mMediaProjectionMetricsLogger;
                if (zHasProjectionPermission) {
                    if (bundle == null) {
                        mediaProjectionMetricsLogger.notifyProjectionInitiated(this.mUid, SessionCreationSource.APP);
                    }
                    int i2 = this.mUid;
                    String str2 = this.mPackageName;
                    boolean z = this.mReviewGrantedConsentRequired;
                    companion.getClass();
                    IMediaProjection projection = z ? iMediaProjectionManager.getProjection(i2, str2) : null;
                    if (projection == null) {
                        projection = iMediaProjectionManager.createProjection(i2, str2, 0, false, 0);
                    }
                    ActivityOptions.LaunchCookie launchCookie = (ActivityOptions.LaunchCookie) intent.getParcelableExtra("android.media.projection.extra.EXTRA_LAUNCH_COOKIE", ActivityOptions.LaunchCookie.class);
                    if (launchCookie != null) {
                        projection.setLaunchCookie(launchCookie);
                    }
                    Intent intent3 = new Intent();
                    intent3.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", projection.asBinder());
                    setResult(-1, intent3);
                    finish(1, projection);
                    return;
                }
                if (((ScreenCaptureDevicePolicyResolver) this.mScreenCaptureDevicePolicyResolver.get()).isScreenCaptureCompletelyDisabled(UserHandle.getUserHandleForUid(getLaunchedFromUid()))) {
                    ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate = this.mScreenCaptureDisabledDialogDelegate;
                    screenCaptureDisabledDialogDelegate.getClass();
                    AlertDialog alertDialogCreate = new AlertDialog.Builder(screenCaptureDisabledDialogDelegate.context, R.style.Theme_SystemUI_Dialog).create();
                    alertDialogCreate.getClass();
                    screenCaptureDisabledDialogDelegate.initDialog(alertDialogCreate);
                    setUpDialog(alertDialogCreate);
                    alertDialogCreate.show();
                    finishAsCancelled();
                    return;
                }
                String string = applicationInfo.loadLabel(packageManager).toString();
                int length = string.length();
                int iCharCount = 0;
                while (iCharCount < length) {
                    int iCodePointAt = string.codePointAt(iCharCount);
                    int type = Character.getType(iCodePointAt);
                    if (type == 13 || type == 15 || type == 14) {
                        string = string.substring(0, iCharCount) + "…";
                        break;
                    }
                    iCharCount += Character.charCount(iCodePointAt);
                }
                if (string.isEmpty()) {
                    string = this.mPackageName;
                }
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(42.0f);
                String strUnicodeWrap = BidiFormatter.getInstance().unicodeWrap(TextUtils.ellipsize(string, textPaint, 500.0f, TextUtils.TruncateAt.END).toString());
                if (strUnicodeWrap == null || strUnicodeWrap.isEmpty()) {
                    strUnicodeWrap = this.mPackageName;
                }
                String str3 = strUnicodeWrap;
                MediaProjectionUtils mediaProjectionUtils = MediaProjectionUtils.INSTANCE;
                String str4 = this.mPackageName;
                mediaProjectionUtils.getClass();
                final boolean zIsHeadlessRemoteDisplayProvider = Utils.isHeadlessRemoteDisplayProvider(packageManager, str4);
                if (!BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                    mediaProjectionPermissionActivityCreateDisplayContext = this;
                } else {
                    Display[] displays = ((DisplayManager) getApplicationContext().getSystemService(DisplayManager.class)).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
                    mediaProjectionPermissionActivityCreateDisplayContext = displays.length > 1 ? createDisplayContext(displays[1]) : this;
                }
                boolean zIsChangeEnabled = CompatChanges.isChangeEnabled(316897322L, this.mPackageName, UserHandle.getUserHandleForUid(getLaunchedFromUid()));
                Intent intent4 = getIntent();
                MediaProjectionConfig mediaProjectionConfig = intent4 == null ? null : (MediaProjectionConfig) intent4.getParcelableExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION_CONFIG");
                Consumer consumer = new Consumer() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MediaProjectionPermissionActivity mediaProjectionPermissionActivity = this.f$0;
                        boolean z2 = zIsHeadlessRemoteDisplayProvider;
                        int i3 = MediaProjectionPermissionActivity.$r8$clinit;
                        BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder = ((BaseMediaProjectionPermissionDialogDelegate) obj).viewBinder;
                        if (baseMediaProjectionPermissionViewBinder == null) {
                            baseMediaProjectionPermissionViewBinder = null;
                        }
                        ScreenShareOption screenShareOption = baseMediaProjectionPermissionViewBinder.selectedScreenShareOption;
                        int i4 = screenShareOption.mode;
                        int i5 = screenShareOption.displayId;
                        try {
                            try {
                                int i6 = mediaProjectionPermissionActivity.mUid;
                                String str5 = mediaProjectionPermissionActivity.mPackageName;
                                boolean z3 = mediaProjectionPermissionActivity.mReviewGrantedConsentRequired;
                                MediaProjectionServiceHelper.Companion.getClass();
                                IMediaProjection projection2 = z3 ? MediaProjectionServiceHelper.service.getProjection(i6, str5) : null;
                                if (projection2 == null) {
                                    projection2 = MediaProjectionServiceHelper.service.createProjection(i6, str5, 0, false, i5);
                                }
                                if (i4 == 1) {
                                    Intent intent5 = new Intent();
                                    intent5.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", projection2.asBinder());
                                    intent5.putExtra("screen_share_type", z2 ? MediaProjectionAppSelectorActivity.ScreenShareType.SystemCast.name() : MediaProjectionAppSelectorActivity.ScreenShareType.ShareToApp.name());
                                    mediaProjectionPermissionActivity.setResult(-1, intent5);
                                    mediaProjectionPermissionActivity.finish(1, projection2);
                                }
                                if (i4 == 0) {
                                    Intent intent6 = new Intent(mediaProjectionPermissionActivity, (Class<?>) MediaProjectionAppSelectorActivity.class);
                                    intent6.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", projection2.asBinder());
                                    intent6.putExtra("screen_share_type", z2 ? MediaProjectionAppSelectorActivity.ScreenShareType.SystemCast.name() : MediaProjectionAppSelectorActivity.ScreenShareType.ShareToApp.name());
                                    intent6.putExtra("launched_from_user_handle", UserHandle.getUserHandleForUid(mediaProjectionPermissionActivity.getLaunchedFromUid()));
                                    intent6.putExtra("launched_from_host_uid", mediaProjectionPermissionActivity.getLaunchedFromUid());
                                    intent6.putExtra("extra_media_projection_user_consent_required", mediaProjectionPermissionActivity.mReviewGrantedConsentRequired);
                                    intent6.setFlags(33554432);
                                    mediaProjectionPermissionActivity.mUserSelectingTask = true;
                                    mediaProjectionPermissionActivity.startActivityAsUser(intent6, UserHandle.of(0));
                                    mediaProjectionPermissionActivity.mStatusBarManager.collapsePanels();
                                }
                                AlertDialogWithDelegate alertDialogWithDelegate = mediaProjectionPermissionActivity.mDialog;
                                if (alertDialogWithDelegate != null) {
                                    alertDialogWithDelegate.dismiss();
                                }
                            } catch (RemoteException e) {
                                Log.e("MediaProjectionPermissionActivity", "Error granting projection permission", e);
                                mediaProjectionPermissionActivity.finishAsCancelled();
                                AlertDialogWithDelegate alertDialogWithDelegate2 = mediaProjectionPermissionActivity.mDialog;
                                if (alertDialogWithDelegate2 != null) {
                                    alertDialogWithDelegate2.dismiss();
                                }
                            }
                        } finally {
                        }
                    }
                };
                Runnable runnable = new Runnable() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaProjectionPermissionActivity mediaProjectionPermissionActivity = this.f$0;
                        int i3 = MediaProjectionPermissionActivity.$r8$clinit;
                        mediaProjectionPermissionActivity.finish(0, null);
                    }
                };
                this.mDialog = new AlertDialogWithDelegate(mediaProjectionPermissionActivityCreateDisplayContext, R.style.Theme_SystemUI_Dialog, zIsHeadlessRemoteDisplayProvider ? new SystemCastPermissionDialogDelegate(mediaProjectionPermissionActivityCreateDisplayContext, mediaProjectionConfig, consumer, runnable, str3, zIsChangeEnabled, this.mUid, this.mMediaProjectionMetricsLogger) : new ShareToAppPermissionDialogDelegate(mediaProjectionPermissionActivityCreateDisplayContext, mediaProjectionConfig, consumer, runnable, str3, zIsChangeEnabled, this.mUid, this.mMediaProjectionMetricsLogger));
                if (bundle == null) {
                    mediaProjectionMetricsLogger.notifyProjectionInitiated(this.mUid, zIsHeadlessRemoteDisplayProvider ? SessionCreationSource.CAST : SessionCreationSource.APP);
                }
                setUpDialog(this.mDialog);
                if (this.mKeyguardManager.isDeviceLocked()) {
                    this.mKeyguardManager.requestDismissKeyguard(this, new KeyguardManager.KeyguardDismissCallback() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity.1
                        @Override // android.app.KeyguardManager.KeyguardDismissCallback
                        public final void onDismissCancelled() {
                            MediaProjectionPermissionActivity mediaProjectionPermissionActivity = MediaProjectionPermissionActivity.this;
                            int i3 = MediaProjectionPermissionActivity.$r8$clinit;
                            mediaProjectionPermissionActivity.finishAsCancelled();
                        }

                        @Override // android.app.KeyguardManager.KeyguardDismissCallback
                        public final void onDismissError() {
                            MediaProjectionPermissionActivity mediaProjectionPermissionActivity = MediaProjectionPermissionActivity.this;
                            int i3 = MediaProjectionPermissionActivity.$r8$clinit;
                            mediaProjectionPermissionActivity.finishAsCancelled();
                        }

                        @Override // android.app.KeyguardManager.KeyguardDismissCallback
                        public final void onDismissSucceeded() {
                            MediaProjectionPermissionActivity.this.mDialog.show();
                        }
                    });
                } else {
                    this.mDialog.show();
                }
                if (bundle == null) {
                    mediaProjectionMetricsLogger.notifyPermissionRequestDisplayed(this.mUid);
                }
            } catch (RemoteException e) {
                Log.e("MediaProjectionPermissionActivity", "Error checking projection permissions", e);
                finishAsCancelled();
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("MediaProjectionPermissionActivity", "Unable to look up package name", e2);
            finishAsCancelled();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        AlertDialogWithDelegate alertDialogWithDelegate = this.mDialog;
        if (alertDialogWithDelegate != null) {
            alertDialogWithDelegate.setOnDismissListener(null);
            this.mDialog.setOnCancelListener(null);
            this.mDialog.dismiss();
        }
    }

    public final void setUpDialog(AlertDialog alertDialog) {
        SystemUIDialog.registerDismissListener(alertDialog);
        SystemUIDialog.applyFlags(alertDialog, false);
        SystemUIDialog.setDialogSize(alertDialog);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                MediaProjectionPermissionActivity mediaProjectionPermissionActivity = this.f$0;
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
                MediaProjectionPermissionActivity mediaProjectionPermissionActivity = this.f$0;
                int i = MediaProjectionPermissionActivity.$r8$clinit;
                if (mediaProjectionPermissionActivity.isFinishing()) {
                    return;
                }
                mediaProjectionPermissionActivity.finish();
            }
        });
        alertDialog.create();
        alertDialog.getButton(-1).setFilterTouchesWhenObscured(true);
        alertDialog.getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
    }

    public final void finish(int i, IMediaProjection iMediaProjection) {
        boolean z = this.mReviewGrantedConsentRequired;
        MediaProjectionServiceHelper.Companion.getClass();
        MediaProjectionServiceHelper.Companion.setReviewedConsentIfNeeded(i, z, iMediaProjection);
        super.finish();
    }
}
