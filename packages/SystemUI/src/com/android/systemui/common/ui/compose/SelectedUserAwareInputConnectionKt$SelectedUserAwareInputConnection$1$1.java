package com.android.systemui.common.ui.compose;

import android.os.UserHandle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.compose.ui.platform.PlatformTextInputInterceptor;
import androidx.compose.ui.platform.PlatformTextInputMethodRequest;
import androidx.compose.ui.platform.PlatformTextInputSession;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
public final class SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1 implements PlatformTextInputInterceptor {
    public final /* synthetic */ int $selectedUserId;

    public SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1(int i) {
        this.$selectedUserId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CoroutineSingletons interceptStartInputMethod(final PlatformTextInputMethodRequest platformTextInputMethodRequest, PlatformTextInputSession platformTextInputSession, ContinuationImpl continuationImpl) {
        SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1 selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1;
        if (continuationImpl instanceof SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1) {
            selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1 = (SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1) continuationImpl;
            int i = selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1.label = i - Integer.MIN_VALUE;
            } else {
                selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1 = new SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1(this, continuationImpl);
            }
        }
        Object obj = selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final int i3 = this.$selectedUserId;
            PlatformTextInputMethodRequest platformTextInputMethodRequest2 = new PlatformTextInputMethodRequest() { // from class: com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$modifiedRequest$1
                @Override // androidx.compose.ui.platform.PlatformTextInputMethodRequest
                public final InputConnection createInputConnection(EditorInfo editorInfo) {
                    InputConnection inputConnectionCreateInputConnection = platformTextInputMethodRequest.createInputConnection(editorInfo);
                    editorInfo.targetInputMethodUser = UserHandle.of(i3);
                    return inputConnectionCreateInputConnection;
                }
            };
            selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1.label = 1;
            if (platformTextInputSession.startInputMethod(platformTextInputMethodRequest2, selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
