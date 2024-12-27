package com.android.systemui.clipboardoverlay;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.graphics.Insets;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class EditTextActivity extends Activity implements ClipboardManager.OnPrimaryClipChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mAttribution;
    public ClipboardManager mClipboardManager;
    public EditText mEditText;
    public boolean mSensitive;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.clipboard_edit_text_activity);
        findViewById(R.id.done_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.EditTextActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditTextActivity editTextActivity = EditTextActivity.this;
                int i = EditTextActivity.$r8$clinit;
                editTextActivity.getClass();
                ((InputMethodManager) editTextActivity.getSystemService(InputMethodManager.class)).hideSoftInputFromWindow(editTextActivity.mEditText.getWindowToken(), 0);
                Editable text = editTextActivity.mEditText.getText();
                text.clearSpans();
                ClipData newPlainText = ClipData.newPlainText("text", text);
                PersistableBundle persistableBundle = new PersistableBundle();
                persistableBundle.putBoolean("android.content.extra.IS_SENSITIVE", editTextActivity.mSensitive);
                newPlainText.getDescription().setExtras(persistableBundle);
                editTextActivity.mClipboardManager.setPrimaryClip(newPlainText);
                editTextActivity.finish();
            }
        });
        this.mEditText = (EditText) findViewById(R.id.edit_text);
        this.mAttribution = (TextView) findViewById(R.id.attribution);
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(ClipboardManager.class);
        Objects.requireNonNull(clipboardManager);
        this.mClipboardManager = clipboardManager;
        findViewById(R.id.editor_root).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(this) { // from class: com.android.systemui.clipboardoverlay.EditTextActivity.1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams.leftMargin = insets.left;
                marginLayoutParams.bottomMargin = insets.bottom;
                marginLayoutParams.rightMargin = insets.right;
                marginLayoutParams.topMargin = insets.top;
                view.setLayoutParams(marginLayoutParams);
                return WindowInsets.CONSUMED;
            }
        });
    }

    @Override // android.app.Activity
    public final void onPause() {
        this.mClipboardManager.removePrimaryClipChangedListener(this);
        super.onPause();
    }

    @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
    public final void onPrimaryClipChanged() {
        ((InputMethodManager) getSystemService(InputMethodManager.class)).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
        finish();
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        ClipData primaryClip = this.mClipboardManager.getPrimaryClip();
        if (primaryClip == null) {
            finish();
            return;
        }
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            this.mAttribution.setText(getResources().getString(R.string.clipboard_edit_source, packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mClipboardManager.getPrimaryClipSource(), PackageManager.ApplicationInfoFlags.of(0L)))));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("EditTextActivity", "Package not found: " + this.mClipboardManager.getPrimaryClipSource(), e);
        }
        boolean z = false;
        this.mEditText.setText(primaryClip.getItemAt(0).getText().toString());
        this.mEditText.requestFocus();
        this.mEditText.setSelection(0);
        if (primaryClip.getDescription().getExtras() != null && primaryClip.getDescription().getExtras().getBoolean("android.content.extra.IS_SENSITIVE")) {
            z = true;
        }
        this.mSensitive = z;
        this.mClipboardManager.addPrimaryClipChangedListener(this);
    }
}
