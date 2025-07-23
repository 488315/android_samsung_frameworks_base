package com.android.systemui.notetask;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NoteTaskModule_Companion_ProvideNotesTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public NoteTaskModule_Companion_ProvideNotesTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideNotesTileConfig(QsEventLogger qsEventLogger) {
        NoteTaskModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("notes"), new QSTileUIConfig.Resource(R.drawable.ic_qs_notes, R.string.quick_settings_notes_label), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNotesTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
