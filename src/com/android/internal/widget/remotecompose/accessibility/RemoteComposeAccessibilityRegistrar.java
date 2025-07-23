package com.android.internal.widget.remotecompose.accessibility;

import android.view.View;
import com.android.internal.widget.remotecompose.core.CoreDocument;

/* loaded from: classes6.dex */
public interface RemoteComposeAccessibilityRegistrar {
    void clearAccessibilityDelegate(View view);

    void setAccessibilityDelegate(View view, CoreDocument coreDocument);
}
