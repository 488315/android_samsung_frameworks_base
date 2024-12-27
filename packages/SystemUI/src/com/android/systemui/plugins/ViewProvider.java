package com.android.systemui.plugins;

import android.view.View;

public interface ViewProvider extends Plugin {
    View getView();
}
