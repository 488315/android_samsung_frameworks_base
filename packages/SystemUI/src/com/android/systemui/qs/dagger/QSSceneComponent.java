package com.android.systemui.qs.dagger;

import android.view.View;

public interface QSSceneComponent extends QSComponent {

    public interface Factory {
        QSSceneComponent create(View view);
    }
}
