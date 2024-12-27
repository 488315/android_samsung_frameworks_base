package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarVisuals;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;

public final class SnackbarDialogImpl implements DialogInterface, SnackbarVisuals {
    public final List actions;
    public final String body;
    public final String title;
    public final String message = "";
    public final String actionLabel = "";
    public final SnackbarDuration duration = SnackbarDuration.Indefinite;

    public SnackbarDialogImpl(String str, String str2, String... strArr) {
        this.title = str;
        this.body = str2;
        this.actions = ArraysKt___ArraysKt.toList(strArr);
    }

    @Override // androidx.compose.material3.SnackbarVisuals
    public final String getActionLabel() {
        return this.actionLabel;
    }

    @Override // androidx.compose.material3.SnackbarVisuals
    public final SnackbarDuration getDuration() {
        return this.duration;
    }

    @Override // androidx.compose.material3.SnackbarVisuals
    public final String getMessage() {
        return this.message;
    }

    @Override // androidx.compose.material3.SnackbarVisuals
    public final boolean getWithDismissAction() {
        return false;
    }
}
