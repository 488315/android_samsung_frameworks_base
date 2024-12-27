package com.android.systemui.media.mediaoutput.dagger;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public interface SavedStateHandleAssisted {
    ViewModel create(SavedStateHandle savedStateHandle);
}
