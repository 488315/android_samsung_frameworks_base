package android.service.controls.actions;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class BooleanAction extends ControlAction {
    private static final String KEY_NEW_STATE = "key_new_state";
    private static final int TYPE = 1;
    private final boolean mNewState;

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 1;
    }

    public BooleanAction(String str, boolean z) {
        this(str, z, null);
    }

    public BooleanAction(String str, boolean z, String str2) {
        super(str, str2);
        this.mNewState = z;
    }

    BooleanAction(Bundle bundle) {
        super(bundle);
        this.mNewState = bundle.getBoolean(KEY_NEW_STATE);
    }

    public boolean getNewState() {
        return this.mNewState;
    }

    @Override // android.service.controls.actions.ControlAction
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean(KEY_NEW_STATE, this.mNewState);
        return dataBundle;
    }
}
