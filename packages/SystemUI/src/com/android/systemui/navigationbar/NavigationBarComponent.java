package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Bundle;

/* loaded from: classes2.dex */
public interface NavigationBarComponent {

    public interface Factory {
        NavigationBarComponent create(Context context, Bundle bundle);
    }
}
