package com.android.systemui.popup.viewmodel;

import android.content.Intent;

public interface PopupUIViewModel {
    void dismiss();

    String getAction();

    void show(Intent intent);
}
