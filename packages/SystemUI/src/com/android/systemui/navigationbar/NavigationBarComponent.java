package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Bundle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface NavigationBarComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        NavigationBarComponent create(Context context, Bundle bundle);
    }

    NavigationBar getNavigationBar();
}
