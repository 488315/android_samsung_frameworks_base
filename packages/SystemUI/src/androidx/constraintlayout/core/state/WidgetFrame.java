package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class WidgetFrame {
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
            this.mCustom.put(customVariable.mName, new CustomVariable(customVariable));
        }
    }
}
