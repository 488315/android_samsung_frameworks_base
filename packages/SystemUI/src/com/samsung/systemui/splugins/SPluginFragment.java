package com.samsung.systemui.splugins;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SPluginFragment extends Fragment implements SPlugin {
    private Context mPluginContext;

    @Override // android.app.Fragment
    public Context getContext() {
        return this.mPluginContext;
    }

    @Override // com.samsung.systemui.splugins.SPlugin
    public void onCreate(Context context, Context context2) {
        this.mPluginContext = context2;
    }

    @Override // android.app.Fragment
    public LayoutInflater onGetLayoutInflater(Bundle bundle) {
        return super.onGetLayoutInflater(bundle).cloneInContext(getContext());
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
