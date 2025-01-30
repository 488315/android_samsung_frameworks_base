package com.samsung.systemui.splugins;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
