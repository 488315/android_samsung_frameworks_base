package com.android.systemui.p016qs.bar.soundcraft.viewmodel.base;

import androidx.lifecycle.MutableLiveData;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BaseSingleChoiceViewModel extends BaseViewModel {
    public final MutableLiveData icon;
    public final MutableLiveData showChooser;
    public final MutableLiveData title = new MutableLiveData("");
    public final MutableLiveData optionNames = new MutableLiveData(EmptyList.INSTANCE);
    public final MutableLiveData selectedOptionName = new MutableLiveData("");

    public BaseSingleChoiceViewModel() {
        new MutableLiveData(0);
        this.showChooser = new MutableLiveData(Boolean.FALSE);
        this.icon = new MutableLiveData(-1);
    }

    public MutableLiveData getIcon() {
        return this.icon;
    }

    public MutableLiveData getOptionNames() {
        return this.optionNames;
    }

    public MutableLiveData getTitle() {
        return this.title;
    }

    public abstract void onChooserDismiss();

    public abstract void onClick();

    public abstract void onItemSelected(int i);
}
