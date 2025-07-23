package android.service.controls.actions;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class FloatAction extends ControlAction {
    private static final String KEY_NEW_VALUE = "key_new_value";
    private static final int TYPE = 2;
    private final float mNewValue;

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 2;
    }

    public FloatAction(String str, float f) {
        this(str, f, null);
    }

    public FloatAction(String str, float f, String str2) {
        super(str, str2);
        this.mNewValue = f;
    }

    FloatAction(Bundle bundle) {
        super(bundle);
        this.mNewValue = bundle.getFloat(KEY_NEW_VALUE);
    }

    public float getNewValue() {
        return this.mNewValue;
    }

    @Override // android.service.controls.actions.ControlAction
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putFloat(KEY_NEW_VALUE, this.mNewValue);
        return dataBundle;
    }
}
