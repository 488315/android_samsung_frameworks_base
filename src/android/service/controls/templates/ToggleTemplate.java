package android.service.controls.templates;

import android.os.Bundle;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ToggleTemplate extends ControlTemplate {
    private static final String KEY_BUTTON = "key_button";
    private static final int TYPE = 1;
    private final ControlButton mButton;

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 1;
    }

    public ToggleTemplate(String str, ControlButton controlButton) {
        super(str);
        Preconditions.checkNotNull(controlButton);
        this.mButton = controlButton;
    }

    ToggleTemplate(Bundle bundle) {
        super(bundle);
        this.mButton = (ControlButton) bundle.getParcelable(KEY_BUTTON, ControlButton.class);
    }

    public boolean isChecked() {
        return this.mButton.isChecked();
    }

    public CharSequence getContentDescription() {
        return this.mButton.getActionDescription();
    }

    @Override // android.service.controls.templates.ControlTemplate
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putParcelable(KEY_BUTTON, this.mButton);
        return dataBundle;
    }
}
