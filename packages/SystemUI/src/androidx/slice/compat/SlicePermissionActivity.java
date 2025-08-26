package androidx.slice.compat;

import android.content.ContentProviderClient;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.BidiFormatter;
import androidx.slice.compat.SliceProviderCompat;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class SlicePermissionActivity extends AppCompatActivity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public String mCallingPkg;
    public AlertDialog mDialog;
    public String mProviderPkg;
    public Uri mUri;

    public static CharSequence loadSafeLabel(PackageManager packageManager, ApplicationInfo applicationInfo) {
        String string = Html.fromHtml(applicationInfo.loadLabel(packageManager).toString()).toString();
        int length = string.length();
        int iCharCount = 0;
        while (iCharCount < length) {
            int iCodePointAt = string.codePointAt(iCharCount);
            int type = Character.getType(iCodePointAt);
            if (type == 13 || type == 15 || type == 14) {
                string = string.substring(0, iCharCount);
                break;
            }
            if (type == 12) {
                string = string.substring(0, iCharCount) + " " + string.substring(Character.charCount(iCodePointAt) + iCharCount);
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        String strTrim = string.trim();
        if (strTrim.isEmpty()) {
            return applicationInfo.packageName;
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(42.0f);
        return TextUtils.ellipsize(strTrim, textPaint, 500.0f, TextUtils.TruncateAt.END);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            String packageName = getPackageName();
            String str = this.mCallingPkg;
            Uri uriBuild = this.mUri.buildUpon().path("").build();
            try {
                ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = getContentResolver().acquireUnstableContentProviderClient(uriBuild);
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    throw new IllegalArgumentException("No provider found for " + uriBuild);
                }
                SliceProviderCompat.ProviderHolder providerHolder = new SliceProviderCompat.ProviderHolder(contentProviderClientAcquireUnstableContentProviderClient);
                try {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("slice_uri", uriBuild);
                    bundle.putString("provider_pkg", packageName);
                    bundle.putString("pkg", str);
                    providerHolder.mProvider.call("grant_perms", "supports_versioned_parcelable", bundle);
                    providerHolder.close();
                } finally {
                }
            } catch (RemoteException e) {
                Log.e("SliceProviderCompat", "Unable to get slice descendants", e);
            }
        }
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String string;
        String string2;
        super.onCreate(bundle);
        this.mUri = (Uri) getIntent().getParcelableExtra("slice_uri");
        this.mCallingPkg = getIntent().getStringExtra("pkg");
        this.mProviderPkg = getIntent().getStringExtra("provider_pkg");
        try {
            PackageManager packageManager = getPackageManager();
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            String string3 = loadSafeLabel(packageManager, packageManager.getApplicationInfo(this.mCallingPkg, 0)).toString();
            if (string3 == null) {
                bidiFormatter.getClass();
                string = null;
            } else {
                string = ((SpannableStringBuilder) bidiFormatter.unicodeWrap(string3, bidiFormatter.mDefaultTextDirectionHeuristicCompat)).toString();
            }
            BidiFormatter bidiFormatter2 = BidiFormatter.getInstance();
            String string4 = loadSafeLabel(packageManager, packageManager.getApplicationInfo(this.mProviderPkg, 0)).toString();
            if (string4 == null) {
                bidiFormatter2.getClass();
                string2 = null;
            } else {
                string2 = ((SpannableStringBuilder) bidiFormatter2.unicodeWrap(string4, bidiFormatter2.mDefaultTextDirectionHeuristicCompat)).toString();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = getString(R.string.abc_slice_permission_title, new Object[]{string, string2});
            alertParams.mView = null;
            alertParams.mViewLayoutResId = R.layout.abc_slice_permission_request;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(R.string.abc_slice_permission_deny);
            alertParams.mNegativeButtonListener = this;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(R.string.abc_slice_permission_allow);
            alertParams.mPositiveButtonListener = this;
            alertParams.mOnDismissListener = this;
            AlertDialog alertDialogCreate = builder.create();
            alertDialogCreate.show();
            this.mDialog = alertDialogCreate;
            ((TextView) alertDialogCreate.getWindow().getDecorView().findViewById(R.id.text1)).setText(getString(R.string.abc_slice_permission_text_1, new Object[]{string2}));
            ((TextView) this.mDialog.getWindow().getDecorView().findViewById(R.id.text2)).setText(getString(R.string.abc_slice_permission_text_2, new Object[]{string2}));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SlicePermissionActivity", "Couldn't find package", e);
            finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mDialog.cancel();
    }
}
