package android.service.controls.templates;

import android.os.Bundle;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ToggleRangeTemplate extends ControlTemplate {
    private static final String KEY_BUTTON = "key_button";
    private static final String KEY_RANGE = "key_range";
    private static final int TYPE = 6;
    private final ControlButton mControlButton;
    private final RangeTemplate mRangeTemplate;

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 6;
    }

    ToggleRangeTemplate(Bundle bundle) {
        super(bundle);
        this.mControlButton = (ControlButton) bundle.getParcelable(KEY_BUTTON, ControlButton.class);
        this.mRangeTemplate = new RangeTemplate(bundle.getBundle(KEY_RANGE));
    }

    public ToggleRangeTemplate(String str, ControlButton controlButton, RangeTemplate rangeTemplate) {
        super(str);
        Preconditions.checkNotNull(controlButton);
        Preconditions.checkNotNull(rangeTemplate);
        this.mControlButton = controlButton;
        this.mRangeTemplate = rangeTemplate;
    }

    public ToggleRangeTemplate(String str, boolean z, CharSequence charSequence, RangeTemplate rangeTemplate) {
        this(str, new ControlButton(z, charSequence), rangeTemplate);
    }

    @Override // android.service.controls.templates.ControlTemplate
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putParcelable(KEY_BUTTON, this.mControlButton);
        dataBundle.putBundle(KEY_RANGE, this.mRangeTemplate.getDataBundle());
        return dataBundle;
    }

    public RangeTemplate getRange() {
        return this.mRangeTemplate;
    }

    public boolean isChecked() {
        return this.mControlButton.isChecked();
    }

    public CharSequence getActionDescription() {
        return this.mControlButton.getActionDescription();
    }
}
