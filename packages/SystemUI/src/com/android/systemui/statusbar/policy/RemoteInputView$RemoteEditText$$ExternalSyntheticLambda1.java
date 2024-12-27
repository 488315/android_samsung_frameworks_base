package com.android.systemui.statusbar.policy;

import android.content.ClipData;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.function.Predicate;

public final /* synthetic */ class RemoteInputView$RemoteEditText$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = RemoteInputView.RemoteEditText.$r8$clinit;
        return ((ClipData.Item) obj).getUri() != null;
    }
}
