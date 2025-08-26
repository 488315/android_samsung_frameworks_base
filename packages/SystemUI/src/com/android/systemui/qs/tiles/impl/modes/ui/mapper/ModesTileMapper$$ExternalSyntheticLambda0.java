package com.android.systemui.qs.tiles.impl.modes.ui.mapper;

import android.icu.text.MessageFormat;
import android.widget.Button;
import com.android.systemui.R;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.HashMap;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final /* synthetic */ class ModesTileMapper$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ModesTileModel f$0;
    public final /* synthetic */ ModesTileMapper f$1;

    public /* synthetic */ ModesTileMapper$$ExternalSyntheticLambda0(ModesTileModel modesTileModel, ModesTileMapper modesTileMapper) {
        this.f$0 = modesTileModel;
        this.f$1 = modesTileMapper;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        QSTileState.Builder builder = (QSTileState.Builder) obj;
        ModesTileModel modesTileModel = this.f$0;
        builder.icon = modesTileModel.icon;
        builder.activationState = modesTileModel.isActivated ? QSTileState.ActivationState.ACTIVE : QSTileState.ActivationState.INACTIVE;
        MessageFormat messageFormat = new MessageFormat(this.f$1.resources.getString(R.string.zen_mode_active_modes), Locale.getDefault());
        int size = modesTileModel.activeModes.size();
        HashMap map = new HashMap();
        map.put(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(size));
        if (size >= 1) {
            map.put("mode", modesTileModel.activeModes.get(0));
        }
        String str = messageFormat.format(map);
        builder.secondaryLabel = str;
        builder.contentDescription = ((Object) builder.label) + ". " + ((Object) str);
        builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK, QSTileState.UserAction.TOGGLE_CLICK});
        builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
        builder.expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(Button.class);
        return Unit.INSTANCE;
    }
}
