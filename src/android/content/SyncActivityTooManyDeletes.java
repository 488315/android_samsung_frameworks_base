package android.content;

import android.accounts.Account;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;

/* loaded from: classes.dex */
public class SyncActivityTooManyDeletes extends Activity implements AdapterView.OnItemClickListener {
    private Account mAccount;
    private String mAuthority;
    private long mNumDeletes;
    private String mProvider;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        this.mNumDeletes = extras.getLong("numDeletes");
        this.mAccount = (Account) extras.getParcelable("account", Account.class);
        this.mAuthority = extras.getString(ContactsContract.Directory.DIRECTORY_AUTHORITY);
        this.mProvider = extras.getString(RuntimeManifestUtils.TAG_PROVIDER);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367043, 16908308, new CharSequence[]{getResources().getText(R.string.sync_really_delete), getResources().getText(R.string.sync_undo_deletes), getResources().getText(R.string.sync_do_nothing)});
        ListView listView = new ListView(this);
        listView.setAdapter((ListAdapter) arrayAdapter);
        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(this);
        TextView textView = new TextView(this);
        textView.lambda$setTextAsync$0(String.format(getResources().getText(R.string.sync_too_many_deletes_desc).toString(), Long.valueOf(this.mNumDeletes), this.mProvider, this.mAccount.name));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 0.0f);
        linearLayout.addView(textView, layoutParams);
        linearLayout.addView(listView, layoutParams);
        setContentView(linearLayout);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (i == 0) {
            startSyncReallyDelete();
        } else if (i == 1) {
            startSyncUndoDeletes();
        }
        finish();
    }

    private void startSyncReallyDelete() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_OVERRIDE_TOO_MANY_DELETIONS, true);
        bundle.putBoolean("force", true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(this.mAccount, this.mAuthority, bundle);
    }

    private void startSyncUndoDeletes() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_DISCARD_LOCAL_DELETIONS, true);
        bundle.putBoolean("force", true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(this.mAccount, this.mAuthority, bundle);
    }
}
