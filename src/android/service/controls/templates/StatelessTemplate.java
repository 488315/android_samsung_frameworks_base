package android.service.controls.templates;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class StatelessTemplate extends ControlTemplate {
    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 8;
    }

    StatelessTemplate(Bundle bundle) {
        super(bundle);
    }

    public StatelessTemplate(String str) {
        super(str);
    }
}
