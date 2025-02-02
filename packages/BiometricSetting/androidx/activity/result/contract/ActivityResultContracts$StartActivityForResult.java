package androidx.activity.result.contract;

import android.content.Intent;
import androidx.activity.result.ActivityResult;

/* compiled from: ActivityResultContracts.kt */
/* loaded from: classes.dex */
public final class ActivityResultContracts$StartActivityForResult extends ActivityResultContract<Intent, ActivityResult> {
    @Override // androidx.activity.result.contract.ActivityResultContract
    public final Object parseResult(Intent intent, int i) {
        return new ActivityResult(intent, i);
    }
}
