package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.HashMap;

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
        HashMap map = new HashMap();
        this.mCustom = map;
        widgetFrame.getClass();
        map.clear();
        for (CustomVariable customVariable : widgetFrame.mCustom.values()) {
            this.mCustom.put(customVariable.mName, new CustomVariable(customVariable));
        }
    }
}
