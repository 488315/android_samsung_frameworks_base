package com.android.systemui.qs.tiles.impl.internet.ui.mapper;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.Switch;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileIconModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class InternetTileMapper implements QSTileDataToStateMapper {
    public final Context context;
    public final Handler handler;
    public final Resources resources;
    public final Resources.Theme theme;

    public InternetTileMapper(Resources resources, Resources.Theme theme, Context context, Handler handler) {
        this.resources = resources;
        this.theme = theme;
        this.context = context;
        this.handler = handler;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final InternetTileModel internetTileModel = (InternetTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.internet.ui.mapper.InternetTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                CharSequence secondaryTitle;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                InternetTileMapper internetTileMapper = this.f$0;
                builder.label = internetTileMapper.resources.getString(R.string.quick_settings_internet_label);
                builder.expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(Switch.class);
                InternetTileModel internetTileModel2 = internetTileModel;
                if (internetTileModel2.getSecondaryLabel() != null) {
                    Text.Companion companion2 = Text.Companion;
                    Text secondaryLabel = internetTileModel2.getSecondaryLabel();
                    Context context = internetTileMapper.context;
                    companion2.getClass();
                    secondaryTitle = Text.Companion.loadText(secondaryLabel, context);
                } else {
                    secondaryTitle = internetTileModel2.getSecondaryTitle();
                }
                builder.secondaryLabel = secondaryTitle;
                ContentDescription.Companion companion3 = ContentDescription.Companion;
                ContentDescription stateDescription = internetTileModel2.getStateDescription();
                Context context2 = internetTileMapper.context;
                companion3.getClass();
                builder.stateDescription = ContentDescription.Companion.loadContentDescription(stateDescription, context2);
                builder.contentDescription = ContentDescription.Companion.loadContentDescription(internetTileModel2.getContentDescription(), internetTileMapper.context);
                InternetTileIconModel icon = internetTileModel2.getIcon();
                if (icon instanceof InternetTileIconModel.ResourceId) {
                    InternetTileIconModel.ResourceId resourceId = (InternetTileIconModel.ResourceId) icon;
                    builder.icon = new Icon.Loaded(internetTileMapper.resources.getDrawable(resourceId.resId, internetTileMapper.theme), null, Integer.valueOf(resourceId.resId));
                } else if (icon instanceof InternetTileIconModel.Cellular) {
                    SignalDrawable signalDrawable = new SignalDrawable(internetTileMapper.context, internetTileMapper.handler);
                    signalDrawable.setLevel(((InternetTileIconModel.Cellular) icon).level);
                    builder.icon = new Icon.Loaded(signalDrawable, null, null, 4, null);
                } else {
                    if (!(icon instanceof InternetTileIconModel.Satellite)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    InternetTileIconModel.Satellite satellite = (InternetTileIconModel.Satellite) icon;
                    builder.icon = new Icon.Loaded(internetTileMapper.resources.getDrawable(satellite.resourceIcon.res, internetTileMapper.theme), null, Integer.valueOf(satellite.resourceIcon.res));
                }
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.activationState = internetTileModel2 instanceof InternetTileModel.Active ? QSTileState.ActivationState.ACTIVE : QSTileState.ActivationState.INACTIVE;
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
