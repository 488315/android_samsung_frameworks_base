package com.android.internal.widget;

import android.view.View;

/* loaded from: classes6.dex */
public interface LockScreenWidgetCallback {
    boolean isVisible(View view);

    void requestHide(View view);

    void requestShow(View view);

    void userActivity(View view);
}
