package com.android.server.am;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
