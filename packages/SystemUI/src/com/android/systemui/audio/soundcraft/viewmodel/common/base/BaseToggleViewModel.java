package com.android.systemui.audio.soundcraft.viewmodel.common.base;

import androidx.lifecycle.MutableLiveData;

public abstract class BaseToggleViewModel extends BaseViewModel {
    public final MutableLiveData icon;
    public final MutableLiveData name = new MutableLiveData();
    public final MutableLiveData isSelected = new MutableLiveData();

    public BaseToggleViewModel() {
        new MutableLiveData();
        this.icon = new MutableLiveData();
    }

    public abstract void onClick();
}
