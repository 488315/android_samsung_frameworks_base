package com.android.systemui.statusbar.policy;

import android.content.ClipData;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RemoteInputView$RemoteEditText$$ExternalSyntheticLambda2 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = RemoteInputView.RemoteEditText.$r8$clinit;
        return ((ClipData.Item) obj).getUri() != null;
    }
}
