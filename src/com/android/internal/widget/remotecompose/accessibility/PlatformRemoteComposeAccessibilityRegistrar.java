package com.android.internal.widget.remotecompose.accessibility;

import android.view.View;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteContextAware;

/* loaded from: classes6.dex */
public class PlatformRemoteComposeAccessibilityRegistrar implements RemoteComposeAccessibilityRegistrar {
    /* JADX WARN: Multi-variable type inference failed */
    public PlatformRemoteComposeTouchHelper forRemoteComposePlayer(View view, CoreDocument coreDocument) {
        return new PlatformRemoteComposeTouchHelper(view, new CoreDocumentAccessibility(coreDocument, ((RemoteContextAware) view).getRemoteContext()), new AndroidPlatformSemanticNodeApplier(view));
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeAccessibilityRegistrar
    public void setAccessibilityDelegate(View view, CoreDocument coreDocument) {
        view.setAccessibilityDelegate(forRemoteComposePlayer(view, coreDocument));
    }

    @Override // com.android.internal.widget.remotecompose.accessibility.RemoteComposeAccessibilityRegistrar
    public void clearAccessibilityDelegate(View view) {
        view.setAccessibilityDelegate(null);
    }
}
