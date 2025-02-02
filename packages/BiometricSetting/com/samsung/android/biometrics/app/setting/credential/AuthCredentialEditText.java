package com.samsung.android.biometrics.app.setting.credential;

import android.app.ActivityManager;
import android.content.Context;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.ImeAwareEditText;

/* loaded from: classes.dex */
public class AuthCredentialEditText extends ImeAwareEditText {
    private int mUserId;

    public AuthCredentialEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setPrivateImeOptions("inputType=option;disableToolbar=true;disableLiveMessage=true;lockScreenPasswordField=true");
    }

    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        int currentUser = ActivityManager.getCurrentUser();
        if (currentUser == 77) {
            editorInfo.targetInputMethodUser = UserHandle.semOf(currentUser);
        } else {
            editorInfo.targetInputMethodUser = UserHandle.semOf(this.mUserId);
        }
        return onCreateInputConnection;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }
}
