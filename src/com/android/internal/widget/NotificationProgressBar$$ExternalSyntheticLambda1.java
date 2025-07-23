package com.android.internal.widget;

import android.app.Notification;
import java.util.function.ToIntFunction;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes6.dex */
public final /* synthetic */ class NotificationProgressBar$$ExternalSyntheticLambda1 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((Notification.ProgressStyle.Segment) obj).getLength();
    }
}
