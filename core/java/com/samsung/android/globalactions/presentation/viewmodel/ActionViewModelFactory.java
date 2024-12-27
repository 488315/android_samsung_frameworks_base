package com.samsung.android.globalactions.presentation.viewmodel;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;

public interface ActionViewModelFactory {
    ActionViewModel createActionViewModel(SamsungGlobalActions samsungGlobalActions, String str);
}
