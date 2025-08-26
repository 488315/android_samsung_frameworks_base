package com.android.systemui.popup.viewmodel;

import android.content.Intent;

/* loaded from: classes2.dex */
public interface PopupUIViewModel {
    void dismiss();

    String getAction();

    void show(Intent intent);
}
