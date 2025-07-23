package com.android.internal.app;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.nfc.Flags;
import android.os.Bundle;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class NfcResolverActivity extends ResolverActivity {
    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        Intent intent = getIntent();
        if (!Flags.enableNfcMainline() || intent.getExtras() == null) {
            super_onCreate(bundle);
            finish();
        } else {
            super.onCreate(bundle, (Intent) intent.getParcelableExtra("android.intent.extra.INTENT", Intent.class), intent.getExtras().getCharSequence(Intent.EXTRA_TITLE, getResources().getText(R.string.chooseActivity)), null, intent.getParcelableArrayListExtra("android.nfc.extra.RESOLVE_INFOS", ResolveInfo.class), false);
        }
    }
}
