package android.accounts;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.internal.R;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class CantAddAccountActivity extends Activity {
    public static final String EXTRA_ERROR_CODE = "android.accounts.extra.ERROR_CODE";

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_not_authorized);
        TextView view = (TextView) findViewById(R.id.description);
        String text = ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.CANT_ADD_ACCOUNT_MESSAGE, new Supplier() { // from class: android.accounts.CantAddAccountActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$onCreate$0;
                lambda$onCreate$0 = CantAddAccountActivity.this.lambda$onCreate$0();
                return lambda$onCreate$0;
            }
        });
        view.lambda$setTextAsync$0(text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$onCreate$0() {
        return getString(R.string.error_message_change_not_allowed);
    }

    public void onCancelButtonClicked(View view) {
        onBackPressed();
    }
}
