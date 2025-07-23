package com.android.systemui.qs.tiles.impl.notes.ui.mapper;

import android.widget.Button;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotesTileMapper$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ NotesTileMapper f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        QSTileState.Builder builder = (QSTileState.Builder) obj;
        NotesTileMapper notesTileMapper = this.f$0;
        builder.icon = new Icon.Loaded(notesTileMapper.resources.getDrawable(R.drawable.ic_qs_notes, notesTileMapper.theme), null, Integer.valueOf(R.drawable.ic_qs_notes));
        builder.contentDescription = builder.label;
        builder.activationState = QSTileState.ActivationState.INACTIVE;
        builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
        builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
        builder.expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(Button.class);
        return Unit.INSTANCE;
    }
}
