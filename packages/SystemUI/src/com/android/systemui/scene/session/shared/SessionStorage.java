package com.android.systemui.scene.session.shared;

import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import java.util.HashMap;

public final class SessionStorage {
    public final ParcelableSnapshotMutableState _storage$delegate;

    public SessionStorage() {
        SnapshotStateKt.mutableStateOf(new HashMap(), StructuralEqualityPolicy.INSTANCE);
    }
}
