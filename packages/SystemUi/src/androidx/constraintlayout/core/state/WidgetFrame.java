package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WidgetFrame {
    public final HashMap mCustom;

    public WidgetFrame() {
        this.mCustom = new HashMap();
    }

    public WidgetFrame(ConstraintWidget constraintWidget) {
        this.mCustom = new HashMap();
    }

    public WidgetFrame(WidgetFrame widgetFrame) {
        HashMap hashMap = new HashMap();
        this.mCustom = hashMap;
        widgetFrame.getClass();
        hashMap.clear();
        for (CustomVariable customVariable : widgetFrame.mCustom.values()) {
            hashMap.put(customVariable.mName, new CustomVariable(customVariable));
        }
    }
}
