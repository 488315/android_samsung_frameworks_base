package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface NavigationBarComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        NavigationBarComponent create(Context context, Bundle bundle);
    }

    NavigationBar getNavigationBar();
}
