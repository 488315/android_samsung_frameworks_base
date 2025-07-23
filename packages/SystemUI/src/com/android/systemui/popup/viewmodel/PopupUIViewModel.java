package com.android.systemui.popup.viewmodel;

import android.content.Intent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PopupUIViewModel {
    void dismiss();

    String getAction();

    void show(Intent intent);
}
