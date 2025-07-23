package com.android.internal.widget.remotecompose.core.operations.layout.measure;

import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class MeasurePass {
    HashMap<Integer, ComponentMeasure> mList = new HashMap<>();

    public void clear() {
        this.mList.clear();
    }

    public void add(ComponentMeasure componentMeasure) throws Exception {
        if (componentMeasure.mId == -1) {
            throw new Exception("Component has no id!");
        }
        this.mList.put(Integer.valueOf(componentMeasure.mId), componentMeasure);
    }

    public boolean contains(int i) {
        return this.mList.containsKey(Integer.valueOf(i));
    }

    public ComponentMeasure get(Component component) {
        if (!this.mList.containsKey(Integer.valueOf(component.getComponentId()))) {
            ComponentMeasure componentMeasure = new ComponentMeasure(component.getComponentId(), component.getX(), component.getY(), component.getWidth(), component.getHeight());
            this.mList.put(Integer.valueOf(component.getComponentId()), componentMeasure);
            return componentMeasure;
        }
        return this.mList.get(Integer.valueOf(component.getComponentId()));
    }

    public ComponentMeasure get(int i) {
        if (!this.mList.containsKey(Integer.valueOf(i))) {
            ComponentMeasure componentMeasure = new ComponentMeasure(i, 0.0f, 0.0f, 0.0f, 0.0f, 0);
            this.mList.put(Integer.valueOf(i), componentMeasure);
            return componentMeasure;
        }
        return this.mList.get(Integer.valueOf(i));
    }
}
