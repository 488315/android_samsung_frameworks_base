package androidx.activity.result.contract;

import android.content.Intent;

/* compiled from: ActivityResultContract.kt */
/* loaded from: classes.dex */
public abstract class ActivityResultContract<I, O> {
    public abstract Object parseResult(Intent intent, int i);
}
