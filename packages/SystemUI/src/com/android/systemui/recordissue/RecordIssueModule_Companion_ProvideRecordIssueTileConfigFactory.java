package com.android.systemui.recordissue;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

public final class RecordIssueModule_Companion_ProvideRecordIssueTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public RecordIssueModule_Companion_ProvideRecordIssueTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideRecordIssueTileConfig(QsEventLogger qsEventLogger) {
        RecordIssueModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("record_issue"), new QSTileUIConfig.Resource(R.drawable.qs_record_issue_icon_off, R.string.qs_record_issue_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRecordIssueTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
