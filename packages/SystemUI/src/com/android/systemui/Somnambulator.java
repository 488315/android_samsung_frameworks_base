package com.android.systemui;

import android.app.Activity;

public class Somnambulator extends Activity {
    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        finish();
    }
}
