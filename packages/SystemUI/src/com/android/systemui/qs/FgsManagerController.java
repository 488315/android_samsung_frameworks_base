package com.android.systemui.qs;

public interface FgsManagerController {

    public interface OnDialogDismissedListener {
    }

    public interface OnNumberOfPackagesChangedListener {
        void onNumberOfPackagesChanged(int i);
    }

    int visibleButtonsCount();
}
