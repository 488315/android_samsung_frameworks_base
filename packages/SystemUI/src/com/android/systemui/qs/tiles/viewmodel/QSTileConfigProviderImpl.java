package com.android.systemui.qs.tiles.viewmodel;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSTileConfigProviderImpl implements QSTileConfigProvider {
    public final Map configs;
    public final QsEventLogger qsEventLogger;

    public QSTileConfigProviderImpl(Map<String, QSTileConfig> map, QsEventLogger qsEventLogger) {
        this.configs = map;
        this.qsEventLogger = qsEventLogger;
        for (Map.Entry<String, QSTileConfig> entry : map.entrySet()) {
            String spec = entry.getValue().tileSpec.getSpec();
            String key = entry.getKey();
            Preconditions.checkArgument(Intrinsics.areEqual(spec, key), FontProvider$$ExternalSyntheticOutline0.m("A wrong config is injected keySpec=", key, " configSpec=", spec), new Object[0]);
        }
    }

    public final QSTileConfig getConfig(String str) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create(str);
        if (create instanceof TileSpec.PlatformTileSpec) {
            QSTileConfig qSTileConfig = (QSTileConfig) this.configs.get(str);
            if (qSTileConfig != null) {
                return qSTileConfig;
            }
            throw new IllegalArgumentException("There is no config for spec=".concat(str));
        }
        if (create instanceof TileSpec.CustomTileSpec) {
            return new QSTileConfig(create, QSTileUIConfig.Empty.INSTANCE, ((QsEventLoggerImpl) this.qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
        }
        if (create instanceof TileSpec.Invalid) {
            throw new IllegalArgumentException("TileSpec.Invalid doesn't support configs");
        }
        throw new NoWhenBranchMatchedException();
    }
}
