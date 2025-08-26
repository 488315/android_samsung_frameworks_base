package com.android.settingslib.inputmethod;

import android.content.DialogInterface;

/* loaded from: classes.dex */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputMethodPreference f$0;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda0(InputMethodPreference inputMethodPreference, int i) {
        this.$r8$classId = i;
        this.f$0 = inputMethodPreference;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        InputMethodPreference inputMethodPreference = this.f$0;
        switch (i2) {
            case 0:
                int i3 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setChecked(true);
                throw null;
            case 1:
                int i4 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setChecked(false);
                throw null;
            case 2:
                if (inputMethodPreference.mImi.getServiceInfo().directBootAware || inputMethodPreference.isTv$2()) {
                    inputMethodPreference.setChecked(true);
                    throw null;
                }
                inputMethodPreference.showDirectBootWarnDialog();
                return;
            default:
                int i5 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setChecked(false);
                throw null;
        }
    }
}
