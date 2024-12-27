package com.android.systemui.statusbar.phone.knox.ui.viewmodel;

import android.view.View;

public interface KnoxStatusBarViewControl {
    View getStatusBarView();

    void setHiddenByKnox(boolean z);
}
