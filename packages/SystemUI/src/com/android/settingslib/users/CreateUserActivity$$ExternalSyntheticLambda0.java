package com.android.settingslib.users;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class CreateUserActivity$$ExternalSyntheticLambda0 implements ActivityStarter, NewUserData {
    public final /* synthetic */ CreateUserActivity f$0;

    @Override // com.android.settingslib.users.NewUserData
    public void onSuccess(String str, Drawable drawable, String str2, Boolean bool) {
        this.f$0.setSuccessResult(str, drawable, str2, bool);
    }

    @Override // com.android.settingslib.users.ActivityStarter
    public void startActivityForResult(Intent intent) {
        int i = CreateUserActivity.$r8$clinit;
        CreateUserActivity createUserActivity = this.f$0;
        createUserActivity.startActivityForResult(intent, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
        createUserActivity.mCreateUserDialogController.mWaitingForActivityResult = true;
    }
}
