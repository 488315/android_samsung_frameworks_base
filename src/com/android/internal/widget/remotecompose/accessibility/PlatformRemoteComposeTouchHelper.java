package com.android.internal.widget.remotecompose.accessibility;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.IntArray;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.widget.ExploreByTouchHelper;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteContextAware;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class PlatformRemoteComposeTouchHelper extends ExploreByTouchHelper {
    private final SemanticNodeApplier<AccessibilityNodeInfo> mApplier;
    private final View mHost;
    private final RemoteComposeDocumentAccessibility mRemoteDocA11y;

    @Override // com.android.internal.widget.ExploreByTouchHelper
    protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
    }

    public PlatformRemoteComposeTouchHelper(View view, RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility, SemanticNodeApplier<AccessibilityNodeInfo> semanticNodeApplier) {
        super(view);
        this.mRemoteDocA11y = remoteComposeDocumentAccessibility;
        this.mApplier = semanticNodeApplier;
        this.mHost = view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PlatformRemoteComposeTouchHelper forRemoteComposePlayer(View view, CoreDocument coreDocument) {
        return new PlatformRemoteComposeTouchHelper(view, new CoreDocumentAccessibility(coreDocument, ((RemoteContextAware) view).getRemoteContext()), new AndroidPlatformSemanticNodeApplier(view));
    }

    @Override // com.android.internal.widget.ExploreByTouchHelper
    protected int getVirtualViewAt(float f, float f2) {
        Integer componentIdAt = this.mRemoteDocA11y.getComponentIdAt(new PointF(f, f2));
        if (componentIdAt == null) {
            return Integer.MIN_VALUE;
        }
        return componentIdAt.intValue();
    }

    @Override // com.android.internal.widget.ExploreByTouchHelper
    protected void getVisibleVirtualViews(IntArray intArray) {
        Component componentFindComponentById = this.mRemoteDocA11y.findComponentById(RemoteComposeDocumentAccessibility.RootId.intValue());
        if (componentFindComponentById == null || !this.mRemoteDocA11y.semanticModifiersForComponent(componentFindComponentById).isEmpty()) {
            intArray.add(RemoteComposeDocumentAccessibility.RootId.intValue());
        }
        Iterator<Integer> it = this.mRemoteDocA11y.semanticallyRelevantChildComponents(componentFindComponentById, false).iterator();
        while (it.hasNext()) {
            intArray.add(it.next().intValue());
        }
    }

    @Override // com.android.internal.widget.ExploreByTouchHelper
    public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfo accessibilityNodeInfo) {
        Component componentFindComponentById = this.mRemoteDocA11y.findComponentById(i);
        AccessibleComponent.Mode modeMergeMode = this.mRemoteDocA11y.mergeMode(componentFindComponentById);
        accessibilityNodeInfo.setEnabled(true);
        if (modeMergeMode == AccessibleComponent.Mode.MERGE) {
            Iterator<Integer> it = this.mRemoteDocA11y.semanticallyRelevantChildComponents(componentFindComponentById, true).iterator();
            while (it.hasNext()) {
                onPopulateNodeForVirtualView(it.next().intValue(), accessibilityNodeInfo);
            }
        }
        this.mApplier.applyComponent(this.mRemoteDocA11y, accessibilityNodeInfo, componentFindComponentById, this.mRemoteDocA11y.semanticModifiersForComponent(componentFindComponentById));
        if (modeMergeMode == AccessibleComponent.Mode.SET) {
            this.mApplier.addChildren(accessibilityNodeInfo, this.mRemoteDocA11y.semanticallyRelevantChildComponents(componentFindComponentById, false));
        }
    }

    @Override // com.android.internal.widget.ExploreByTouchHelper
    protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
        Component componentFindComponentById = this.mRemoteDocA11y.findComponentById(i);
        if (componentFindComponentById == null) {
            return false;
        }
        boolean zPerformAction = this.mRemoteDocA11y.performAction(componentFindComponentById, i2, bundle);
        if (zPerformAction) {
            this.mHost.invalidate();
            invalidateRoot();
        }
        return zPerformAction;
    }
}
