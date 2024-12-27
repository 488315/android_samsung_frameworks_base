package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Bundle;

public interface NavigationBarComponent {

    public interface Factory {
        NavigationBarComponent create(Context context, Bundle bundle);
    }

    NavigationBar getNavigationBar();
}
