package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.CollapsiblePriorityModifierOperation;
import java.util.ArrayList;
import java.util.Comparator;

/* loaded from: classes6.dex */
public class CollapsiblePriority {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    static float getPriority(Component component, int i) {
        CollapsiblePriorityModifierOperation collapsiblePriorityModifierOperation;
        if ((component instanceof LayoutComponent) && (collapsiblePriorityModifierOperation = (CollapsiblePriorityModifierOperation) ((LayoutComponent) component).selfOrModifier(CollapsiblePriorityModifierOperation.class)) != null && collapsiblePriorityModifierOperation.getOrientation() == i) {
            return collapsiblePriorityModifierOperation.getPriority();
        }
        return Float.MAX_VALUE;
    }

    static ArrayList<Component> sortWithPriorities(ArrayList<Component> arrayList, final int i) {
        ArrayList<Component> arrayList2 = new ArrayList<>(arrayList);
        arrayList2.sort(new Comparator() { // from class: com.android.internal.widget.remotecompose.core.operations.layout.managers.CollapsiblePriority$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CollapsiblePriority.lambda$sortWithPriorities$0(i, (Component) obj, (Component) obj2);
            }
        });
        return arrayList2;
    }

    static /* synthetic */ int lambda$sortWithPriorities$0(int i, Component component, Component component2) {
        return (int) (getPriority(component2, i) - getPriority(component, i));
    }
}
