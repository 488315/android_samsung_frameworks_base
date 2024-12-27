package com.android.server.autofill;

import com.android.server.autofill.SaveEventLogger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SaveEventLogger$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SaveEventLogger$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
        this.f$0 = true;
    }

    public /* synthetic */ SaveEventLogger$$ExternalSyntheticLambda3(boolean z) {
        this.$r8$classId = 5;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        SaveEventLogger.SaveEventInternal saveEventInternal = (SaveEventLogger.SaveEventInternal) obj;
        switch (i) {
            case 0:
                saveEventInternal.mIsNewField = z;
                break;
            case 1:
                saveEventInternal.mIsFrameworkCreatedSaveInfo = z;
                break;
            case 2:
                saveEventInternal.mCancelButtonClicked = z;
                break;
            case 3:
                saveEventInternal.mSaveButtonClicked = z;
                break;
            case 4:
                saveEventInternal.mDialogDismissed = z;
                break;
            default:
                saveEventInternal.mIsSaved = z;
                break;
        }
    }
}
