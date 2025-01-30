package com.android.systemui.logcat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.app.ILogAccessDialogCallback;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LogAccessDialogActivity extends Activity implements View.OnClickListener {
    public static final int DIALOG_TIME_OUT;
    public AlertDialog mAlert;
    public String mAlertBody;
    public AlertDialog.Builder mAlertDialog;
    public SpannableString mAlertLearnMore;
    public boolean mAlertLearnMoreLink;
    public String mAlertTitle;
    public View mAlertView;
    public ILogAccessDialogCallback mCallback;
    public final HandlerC17751 mHandler = new Handler() { // from class: com.android.systemui.logcat.LogAccessDialogActivity.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AlertDialog alertDialog;
            if (message.what == 0 && (alertDialog = LogAccessDialogActivity.this.mAlert) != null) {
                alertDialog.dismiss();
                LogAccessDialogActivity logAccessDialogActivity = LogAccessDialogActivity.this;
                logAccessDialogActivity.mAlert = null;
                logAccessDialogActivity.declineLogAccess();
            }
        }
    };
    public String mPackageName;
    public int mUid;

    static {
        DIALOG_TIME_OUT = Build.IS_DEBUGGABLE ? VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS : 300000;
    }

    public final void declineLogAccess() {
        try {
            this.mCallback.declineAccessForClient(this.mUid, this.mPackageName);
        } catch (RemoteException unused) {
            finish();
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        try {
            if (view.getId() == R.id.log_access_dialog_allow_button) {
                this.mCallback.approveAccessForClient(this.mUid, this.mPackageName);
                finish();
            } else if (view.getId() == R.id.log_access_dialog_deny_button) {
                declineLogAccess();
                finish();
            }
        } catch (RemoteException unused) {
            finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0063 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            Slog.e("LogAccessDialogActivity", "Intent is null");
        } else {
            ILogAccessDialogCallback asInterface = ILogAccessDialogCallback.Stub.asInterface(intent.getExtras().getBinder("EXTRA_CALLBACK"));
            this.mCallback = asInterface;
            if (asInterface == null) {
                Slog.e("LogAccessDialogActivity", "Missing callback");
            } else {
                String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
                this.mPackageName = stringExtra;
                if (stringExtra == null || stringExtra.length() == 0) {
                    Slog.e("LogAccessDialogActivity", "Missing package name extra");
                } else {
                    if (intent.hasExtra("android.intent.extra.UID")) {
                        this.mUid = intent.getIntExtra("android.intent.extra.UID", 0);
                        z = true;
                        if (z) {
                            Slog.e("LogAccessDialogActivity", "Invalid Intent extras, finishing");
                            finish();
                            return;
                        }
                        try {
                            String str = this.mPackageName;
                            int i = this.mUid;
                            PackageManager packageManager = getPackageManager();
                            this.mAlertTitle = getString(R.string.log_access_confirmation_title, packageManager.getApplicationInfoAsUser(str, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, UserHandle.getUserId(i)).loadLabel(packageManager));
                            this.mAlertBody = getString(R.string.log_access_confirmation_body);
                            boolean z2 = getResources().getBoolean(R.bool.log_access_confirmation_learn_more_as_link);
                            this.mAlertLearnMoreLink = z2;
                            if (z2) {
                                SpannableString spannableString = new SpannableString(getString(R.string.log_access_confirmation_learn_more));
                                this.mAlertLearnMore = spannableString;
                                spannableString.setSpan(new URLSpan(getString(R.string.log_access_confirmation_learn_more_url)), 0, this.mAlertLearnMore.length(), 33);
                            } else {
                                this.mAlertLearnMore = new SpannableString(getString(R.string.log_access_confirmation_learn_more_at, new Object[]{getString(R.string.log_access_confirmation_learn_more_url)}));
                            }
                            View inflate = LayoutInflater.from(new ContextThemeWrapper(this, R.style.LogAccessDialogTheme)).inflate(R.layout.log_access_user_consent_dialog_permission, (ViewGroup) null);
                            if (inflate == null) {
                                throw new InflateException();
                            }
                            ((TextView) inflate.findViewById(R.id.log_access_dialog_title)).setText(this.mAlertTitle);
                            if (TextUtils.isEmpty(this.mAlertLearnMore)) {
                                ((TextView) inflate.findViewById(R.id.log_access_dialog_body)).setText(this.mAlertBody);
                            } else {
                                ((TextView) inflate.findViewById(R.id.log_access_dialog_body)).setText(TextUtils.concat(this.mAlertBody, "\n\n", this.mAlertLearnMore));
                                if (this.mAlertLearnMoreLink) {
                                    ((TextView) inflate.findViewById(R.id.log_access_dialog_body)).setMovementMethod(LinkMovementMethod.getInstance());
                                }
                            }
                            ((Button) inflate.findViewById(R.id.log_access_dialog_allow_button)).setOnClickListener(this);
                            ((Button) inflate.findViewById(R.id.log_access_dialog_deny_button)).setOnClickListener(this);
                            this.mAlertView = inflate;
                            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.LogAccessDialogTheme);
                            this.mAlertDialog = builder;
                            builder.setView(this.mAlertView);
                            this.mAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.systemui.logcat.LogAccessDialogActivity$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    LogAccessDialogActivity logAccessDialogActivity = LogAccessDialogActivity.this;
                                    int i2 = LogAccessDialogActivity.DIALOG_TIME_OUT;
                                    logAccessDialogActivity.declineLogAccess();
                                }
                            });
                            this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.logcat.LogAccessDialogActivity$$ExternalSyntheticLambda1
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    LogAccessDialogActivity logAccessDialogActivity = LogAccessDialogActivity.this;
                                    int i2 = LogAccessDialogActivity.DIALOG_TIME_OUT;
                                    logAccessDialogActivity.finish();
                                }
                            });
                            AlertDialog create = this.mAlertDialog.create();
                            this.mAlert = create;
                            create.getWindow().setHideOverlayWindows(true);
                            this.mAlert.show();
                            sendEmptyMessageDelayed(0, DIALOG_TIME_OUT);
                            return;
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.e("LogAccessDialogActivity", "Unable to fetch label of package " + this.mPackageName, e);
                            declineLogAccess();
                            finish();
                            return;
                        }
                    }
                    Slog.e("LogAccessDialogActivity", "Missing EXTRA_UID");
                }
            }
        }
        z = false;
        if (z) {
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        AlertDialog alertDialog;
        super.onDestroy();
        if (!isChangingConfigurations() && (alertDialog = this.mAlert) != null && alertDialog.isShowing()) {
            this.mAlert.dismiss();
        }
        this.mAlert = null;
    }
}
