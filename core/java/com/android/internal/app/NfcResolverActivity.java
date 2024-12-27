package com.android.internal.app;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.nfc.Flags;
import android.os.Bundle;

import com.android.internal.R;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class NfcResolverActivity extends ResolverActivity {
    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        if (!Flags.enableNfcMainline()) {
            super_onCreate(savedInstanceState);
            finish();
            return;
        }
        Intent intent = getIntent();
        Intent target =
                (Intent) intent.getParcelableExtra("android.intent.extra.INTENT", Intent.class);
        ArrayList<ResolveInfo> rList =
                intent.getParcelableArrayListExtra(
                        "android.nfc.extra.RESOLVE_INFOS", ResolveInfo.class);
        CharSequence title =
                intent.getExtras()
                        .getCharSequence(
                                Intent.EXTRA_TITLE,
                                getResources().getText(R.string.chooseActivity));
        super.onCreate(savedInstanceState, target, title, null, rList, false);
    }
}
