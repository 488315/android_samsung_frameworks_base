package com.android.systemui.audio.soundcraft.viewmodel.common.base;

import androidx.lifecycle.MutableLiveData;
import kotlin.collections.EmptyList;

public abstract class BaseSingleChoiceViewModel extends BaseViewModel {
    public final MutableLiveData showChooser;
    public final MutableLiveData title = new MutableLiveData("");
    public final MutableLiveData optionNames = new MutableLiveData(EmptyList.INSTANCE);
    public final MutableLiveData selectedOptionName = new MutableLiveData("");

    public BaseSingleChoiceViewModel() {
        new MutableLiveData(0);
        this.showChooser = new MutableLiveData(Boolean.FALSE);
    }

    public abstract void dismiss();

    public MutableLiveData getOptionNames() {
        return this.optionNames;
    }

    public MutableLiveData getTitle() {
        return this.title;
    }

    public abstract void onClick();

    public abstract void onItemSelected(int i);
}
