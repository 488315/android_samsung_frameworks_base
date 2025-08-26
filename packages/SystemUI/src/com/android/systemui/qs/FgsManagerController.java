package com.android.systemui.qs;

/* loaded from: classes2.dex */
public interface FgsManagerController {

    public interface OnDialogDismissedListener {
    }

    public interface OnNumberOfPackagesChangedListener {
        void onNumberOfPackagesChanged(int i);
    }

    int visibleButtonsCount();
}
