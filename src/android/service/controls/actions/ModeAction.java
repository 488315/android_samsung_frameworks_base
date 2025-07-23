package android.service.controls.actions;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class ModeAction extends ControlAction {
    private static final String KEY_MODE = "key_mode";
    private static final int TYPE = 4;
    private final int mNewMode;

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 4;
    }

    public ModeAction(String str, int i, String str2) {
        super(str, str2);
        this.mNewMode = i;
    }

    public ModeAction(String str, int i) {
        this(str, i, null);
    }

    ModeAction(Bundle bundle) {
        super(bundle);
        this.mNewMode = bundle.getInt(KEY_MODE);
    }

    @Override // android.service.controls.actions.ControlAction
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putInt(KEY_MODE, this.mNewMode);
        return dataBundle;
    }

    public int getNewMode() {
        return this.mNewMode;
    }
}
