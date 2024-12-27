package com.android.systemui.qs.tiles.impl.internet.domain;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Switch;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InternetTileMapper implements QSTileDataToStateMapper {
    public final Context context;
    public final Resources resources;
    public final Resources.Theme theme;

    public InternetTileMapper(Resources resources, Resources.Theme theme, Context context) {
        this.resources = resources;
        this.theme = theme;
        this.context = context;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final InternetTileModel internetTileModel = (InternetTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.internet.domain.InternetTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                builder.label = InternetTileMapper.this.resources.getString(R.string.quick_settings_internet_label);
                builder.expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(Switch.class);
                if (internetTileModel.getSecondaryLabel() != null) {
                    Text.Companion companion2 = Text.Companion;
                    Text secondaryLabel = internetTileModel.getSecondaryLabel();
                    Context context = InternetTileMapper.this.context;
                    companion2.getClass();
                    builder.secondaryLabel = Text.Companion.loadText(secondaryLabel, context);
                } else {
                    builder.secondaryLabel = internetTileModel.getSecondaryTitle();
                }
                ContentDescription.Companion companion3 = ContentDescription.Companion;
                ContentDescription stateDescription = internetTileModel.getStateDescription();
                Context context2 = InternetTileMapper.this.context;
                companion3.getClass();
                builder.stateDescription = ContentDescription.Companion.loadContentDescription(stateDescription, context2);
                builder.contentDescription = ContentDescription.Companion.loadContentDescription(internetTileModel.getContentDescription(), InternetTileMapper.this.context);
                builder.iconRes = internetTileModel.getIconId();
                if (internetTileModel.getIcon() != null) {
                    final InternetTileModel internetTileModel2 = internetTileModel;
                    builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.internet.domain.InternetTileMapper$map$1.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return InternetTileModel.this.getIcon();
                        }
                    };
                } else if (internetTileModel.getIconId() != null) {
                    Resources resources2 = InternetTileMapper.this.resources;
                    Integer iconId = internetTileModel.getIconId();
                    Intrinsics.checkNotNull(iconId);
                    final Icon.Loaded loaded = new Icon.Loaded(resources2.getDrawable(iconId.intValue(), InternetTileMapper.this.theme), null);
                    builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.internet.domain.InternetTileMapper$map$1.2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return Icon.Loaded.this;
                        }
                    };
                }
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.activationState = internetTileModel instanceof InternetTileModel.Active ? QSTileState.ActivationState.ACTIVE : QSTileState.ActivationState.INACTIVE;
                builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
