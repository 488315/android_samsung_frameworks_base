package com.android.systemui.audio.soundcraft.viewmodel.common.base;

import androidx.lifecycle.MutableLiveData;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
