package com.android.systemui.qs.bar.soundcraft.viewmodel.base;

import androidx.lifecycle.MutableLiveData;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BaseToggleViewModel extends BaseViewModel {
    public final MutableLiveData icon;
    public final MutableLiveData name = new MutableLiveData();
    public final MutableLiveData isSelected = new MutableLiveData();

    public BaseToggleViewModel() {
        new MutableLiveData();
        this.icon = new MutableLiveData();
    }

    public MutableLiveData getIcon() {
        return this.icon;
    }

    public abstract void onClick();
}
