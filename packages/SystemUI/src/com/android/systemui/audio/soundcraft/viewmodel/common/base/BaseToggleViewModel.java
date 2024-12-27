package com.android.systemui.audio.soundcraft.viewmodel.common.base;

import androidx.lifecycle.MutableLiveData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
