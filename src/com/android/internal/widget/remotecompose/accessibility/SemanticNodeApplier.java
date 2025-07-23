package com.android.internal.widget.remotecompose.accessibility;

import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.semantics.AccessibilitySemantics;
import java.util.List;

/* loaded from: classes6.dex */
public interface SemanticNodeApplier<N> {
    public static final String VIRTUAL_VIEW_ID_KEY = "VirtualViewId";

    void addChildren(N n, List<Integer> list);

    void applyComponent(RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility, N n, Component component, List<AccessibilitySemantics> list);
}
