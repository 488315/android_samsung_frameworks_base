package com.android.server.am;

import java.util.function.Consumer;

public final /* synthetic */ class ErrorDialogController$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BaseErrorDialog baseErrorDialog = (BaseErrorDialog) obj;
        switch (this.$r8$classId) {
            case 0:
                baseErrorDialog.dismiss();
                break;
            default:
                baseErrorDialog.show();
                break;
        }
    }
}
