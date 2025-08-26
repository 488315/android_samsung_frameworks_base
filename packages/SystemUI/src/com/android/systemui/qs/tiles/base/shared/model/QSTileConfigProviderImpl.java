package com.android.systemui.qs.tiles.base.shared.model;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class QSTileConfigProviderImpl implements QSTileConfigProvider {
    public final Map configs;
    public final QsEventLogger qsEventLogger;

    public QSTileConfigProviderImpl(Map<String, QSTileConfig> map, QsEventLogger qsEventLogger) {
        this.configs = map;
        this.qsEventLogger = qsEventLogger;
        for (Map.Entry<String, QSTileConfig> entry : map.entrySet()) {
            String spec = entry.getValue().tileSpec.getSpec();
            String key = entry.getKey();
            Preconditions.checkArgument(Intrinsics.areEqual(spec, key), AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("A wrong config is injected keySpec=", key, " configSpec=", spec), new Object[0]);
        }
    }

    public final QSTileConfig getConfig(String str) {
        TileSpec.Companion.getClass();
        TileSpec tileSpecCreate = TileSpec.Companion.create(str);
        if (tileSpecCreate instanceof TileSpec.PlatformTileSpec) {
            QSTileConfig qSTileConfig = (QSTileConfig) this.configs.get(str);
            if (qSTileConfig != null) {
                return qSTileConfig;
            }
            throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("There is no config for spec=", str));
        }
        if (tileSpecCreate instanceof TileSpec.CustomTileSpec) {
            return new QSTileConfig(tileSpecCreate, QSTileUIConfig.Empty.INSTANCE, ((QsEventLoggerImpl) this.qsEventLogger).sequence.newInstanceId(), TileCategory.PROVIDED_BY_APP, null, null, false, 112, null);
        }
        if (tileSpecCreate instanceof TileSpec.Invalid) {
            throw new IllegalArgumentException("TileSpec.Invalid doesn't support configs");
        }
        throw new IllegalArgumentException("TileSpec Else doesn't support configs..");
    }
}
