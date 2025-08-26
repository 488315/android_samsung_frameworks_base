package com.android.systemui.audio.soundcraft.viewmodel.common.base;

import androidx.lifecycle.MutableLiveData;

/* loaded from: classes.dex */
public abstract class BaseToggleViewModel extends BaseViewModel {
    public final MutableLiveData icon;
    public final MutableLiveData subText;
    public final MutableLiveData name = new MutableLiveData();
    public final MutableLiveData isSelected = new MutableLiveData();
    public final MutableLiveData isChecked = new MutableLiveData();

    public BaseToggleViewModel() {
        new MutableLiveData();
        this.icon = new MutableLiveData();
        this.subText = new MutableLiveData();
    }

    public abstract void onClick();

    public void enable(boolean z) {
    }
}
