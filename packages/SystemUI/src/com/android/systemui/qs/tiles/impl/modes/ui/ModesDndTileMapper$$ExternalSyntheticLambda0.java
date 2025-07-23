package com.android.systemui.qs.tiles.impl.modes.ui;

import android.widget.Switch;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesDndTileModel;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ModesDndTileMapper$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ModesDndTileModel f$0;
    public final /* synthetic */ ModesDndTileMapper f$1;

    public /* synthetic */ ModesDndTileMapper$$ExternalSyntheticLambda0(ModesDndTileModel modesDndTileModel, ModesDndTileMapper modesDndTileMapper) {
        this.f$0 = modesDndTileModel;
        this.f$1 = modesDndTileMapper;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        QSTileState.Builder builder = (QSTileState.Builder) obj;
        ModesDndTileModel modesDndTileModel = this.f$0;
        boolean z = modesDndTileModel.isActivated;
        int i = z ? R.drawable.qs_dnd_icon_on : R.drawable.qs_dnd_icon_off;
        ModesDndTileMapper modesDndTileMapper = this.f$1;
        builder.icon = new Icon.Loaded(modesDndTileMapper.resources.getDrawable(i, modesDndTileMapper.theme), null, Integer.valueOf(i));
        builder.activationState = z ? QSTileState.ActivationState.ACTIVE : QSTileState.ActivationState.INACTIVE;
        String string = modesDndTileMapper.resources.getString(R.string.quick_settings_dnd_label);
        builder.label = string;
        String str = modesDndTileModel.extraStatus;
        builder.secondaryLabel = str;
        builder.contentDescription = string;
        builder.stateDescription = str;
        builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
        builder.expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(Switch.class);
        return Unit.INSTANCE;
    }
}
