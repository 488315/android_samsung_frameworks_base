package com.android.systemui.qs.pipeline.domain.autoaddable;

import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AutoAddableSetting implements AutoAddable {
    public final AutoAddTracking.IfNotAdded autoAddTracking;
    public final CoroutineDispatcher bgDispatcher;
    public final String description;
    public final SecureSettings secureSettings;
    public final String setting;
    public final TileSpec spec;

    public AutoAddableSetting(SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher, String str, TileSpec tileSpec) {
        this.secureSettings = secureSettings;
        this.setting = str;
        this.spec = tileSpec;
        this.description = "AutoAddableSetting: " + str + ":" + tileSpec + " (" + new AutoAddTracking.IfNotAdded(tileSpec) + ")";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof AutoAddableSetting) {
            AutoAddableSetting autoAddableSetting = (AutoAddableSetting) obj;
            if (Intrinsics.areEqual(this.spec, autoAddableSetting.spec) && Intrinsics.areEqual(this.setting, autoAddableSetting.setting)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    public final int hashCode() {
        return Objects.hash(this.spec, this.setting);
    }

    public final String toString() {
        return this.description;
    }
}
