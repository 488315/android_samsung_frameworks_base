package android.service.controls.templates;

import android.os.Bundle;

public final class StatelessTemplate extends ControlTemplate {
    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 8;
    }

    StatelessTemplate(Bundle b) {
        super(b);
    }

    public StatelessTemplate(String templateId) {
        super(templateId);
    }
}
