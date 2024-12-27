package com.android.systemui.plugins;

import android.content.Context;
import android.view.View;

public interface FragmentBase {
    Context getContext();

    View getView();
}
