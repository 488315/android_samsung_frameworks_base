package com.android.server.wm;

import java.util.Collection;

/* loaded from: classes3.dex */
public interface BackgroundActivityStartCallback {
    boolean canCloseSystemDialogs(Collection collection, int i);

    boolean isActivityStartAllowed(Collection collection, int i, String str);
}
