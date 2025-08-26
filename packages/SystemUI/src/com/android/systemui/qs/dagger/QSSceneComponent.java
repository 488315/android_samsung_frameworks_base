package com.android.systemui.qs.dagger;

import android.view.View;

/* loaded from: classes2.dex */
public interface QSSceneComponent extends QSComponent {

    public interface Factory {
        QSSceneComponent create(View view);
    }
}
