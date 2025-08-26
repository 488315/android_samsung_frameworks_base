package com.android.systemui.recordissue;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class RecordIssueModule_Companion_ProvideRecordIssueTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public RecordIssueModule_Companion_ProvideRecordIssueTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideRecordIssueTileConfig(QsEventLogger qsEventLogger) {
        RecordIssueModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("record_issue"), new QSTileUIConfig.Resource(R.drawable.qs_record_issue_icon_off, R.string.qs_record_issue_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRecordIssueTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
