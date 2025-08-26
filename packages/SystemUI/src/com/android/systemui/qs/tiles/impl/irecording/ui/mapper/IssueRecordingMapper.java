package com.android.systemui.qs.tiles.impl.irecording.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.irecording.data.model.IssueRecordingModel;
import java.util.Collections;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class IssueRecordingMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public IssueRecordingMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        final boolean z = ((IssueRecordingModel) obj).isRecording;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.irecording.ui.mapper.IssueRecordingMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                Icon.Loaded loaded;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                boolean z2 = z;
                IssueRecordingMapper issueRecordingMapper = this;
                if (z2) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = issueRecordingMapper.resources.getString(R.string.qs_record_issue_stop);
                    loaded = new Icon.Loaded(issueRecordingMapper.resources.getDrawable(R.drawable.qs_record_issue_icon_on, issueRecordingMapper.theme), null, Integer.valueOf(R.drawable.qs_record_issue_icon_on));
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = issueRecordingMapper.resources.getString(R.string.qs_record_issue_start);
                    loaded = new Icon.Loaded(issueRecordingMapper.resources.getDrawable(R.drawable.qs_record_issue_icon_off, issueRecordingMapper.theme), null, Integer.valueOf(R.drawable.qs_record_issue_icon_off));
                }
                builder.icon = loaded;
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                builder.contentDescription = ((Object) builder.label) + ", " + ((Object) builder.secondaryLabel);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
