package com.android.internal.widget.remotecompose.accessibility;

import android.graphics.PointF;
import android.os.Bundle;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.semantics.AccessibilitySemantics;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import java.util.List;

/* loaded from: classes6.dex */
public interface RemoteComposeDocumentAccessibility {
    public static final int ACTION_CLICK = 16;
    public static final Integer RootId = -1;

    Component findComponentById(int i);

    Integer getComponentIdAt(PointF pointF);

    AccessibleComponent.Mode mergeMode(Component component);

    boolean performAction(Component component, int i, Bundle bundle);

    List<AccessibilitySemantics> semanticModifiersForComponent(Component component);

    List<Integer> semanticallyRelevantChildComponents(Component component, boolean z);

    String stringValue(int i);
}
