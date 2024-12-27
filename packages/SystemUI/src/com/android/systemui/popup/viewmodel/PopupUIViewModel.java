package com.android.systemui.popup.viewmodel;

import android.content.Intent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PopupUIViewModel {
    void dismiss();

    String getAction();

    void show(Intent intent);
}
