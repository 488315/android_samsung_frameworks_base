package android.service.controls.templates;

import android.os.Bundle;

import com.android.internal.util.Preconditions;

public final class ToggleRangeTemplate extends ControlTemplate {
    private static final String KEY_BUTTON = "key_button";
    private static final String KEY_RANGE = "key_range";
    private static final int TYPE = 6;
    private final ControlButton mControlButton;
    private final RangeTemplate mRangeTemplate;

    ToggleRangeTemplate(Bundle b) {
        super(b);
        this.mControlButton = (ControlButton) b.getParcelable(KEY_BUTTON, ControlButton.class);
        this.mRangeTemplate = new RangeTemplate(b.getBundle(KEY_RANGE));
    }

    public ToggleRangeTemplate(String templateId, ControlButton button, RangeTemplate range) {
        super(templateId);
        Preconditions.checkNotNull(button);
        Preconditions.checkNotNull(range);
        this.mControlButton = button;
        this.mRangeTemplate = range;
    }

    public ToggleRangeTemplate(
            String templateId,
            boolean checked,
            CharSequence actionDescription,
            RangeTemplate range) {
        this(templateId, new ControlButton(checked, actionDescription), range);
    }

    @Override // android.service.controls.templates.ControlTemplate
    Bundle getDataBundle() {
        Bundle b = super.getDataBundle();
        b.putParcelable(KEY_BUTTON, this.mControlButton);
        b.putBundle(KEY_RANGE, this.mRangeTemplate.getDataBundle());
        return b;
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

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 6;
    }
}
