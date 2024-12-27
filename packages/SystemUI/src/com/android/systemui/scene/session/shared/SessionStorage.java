package com.android.systemui.scene.session.shared;

import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SessionStorage {
    public final ParcelableSnapshotMutableState _storage$delegate;

    public SessionStorage() {
        SnapshotStateKt.mutableStateOf(new HashMap(), StructuralEqualityPolicy.INSTANCE);
    }
}
