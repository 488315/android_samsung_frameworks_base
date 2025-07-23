package com.android.systemui.qs.tiles.impl.custom.ui.mapper;

import android.app.IUriGrantsManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.widget.Button;
import android.widget.Switch;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.custom.domain.model.CustomTileDataModel;
import com.android.systemui.qs.tiles.impl.custom.ui.mapper.CustomTileMapper;
import java.util.Collections;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomTileMapper implements QSTileDataToStateMapper {
    public final Context context;
    public final IUriGrantsManager uriGrantsManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IconResult {
        public final boolean failedToLoad;
        public final Icon icon;

        public IconResult(Icon icon, boolean z) {
            this.icon = icon;
            this.failedToLoad = z;
        }
    }

    public CustomTileMapper(Context context, IUriGrantsManager iUriGrantsManager) {
        this.context = context;
        this.uriGrantsManager = iUriGrantsManager;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        Context context;
        final IconResult iconResult;
        Drawable drawable;
        Drawable.ConstantState constantState;
        Drawable newDrawable;
        final CustomTileDataModel customTileDataModel = (CustomTileDataModel) obj;
        boolean z = false;
        Icon.Loaded loaded = null;
        try {
            context = this.context.createContextAsUser(new UserHandle(customTileDataModel.user.getIdentifier()), 0);
        } catch (IllegalStateException unused) {
            context = null;
        }
        if (context != null) {
            android.graphics.drawable.Icon icon = customTileDataModel.tile.getIcon();
            int i = customTileDataModel.callingAppUid;
            String packageName = customTileDataModel.componentName.getPackageName();
            android.graphics.drawable.Icon icon2 = customTileDataModel.defaultTileIcon;
            if (icon != null) {
                try {
                    drawable = icon.loadDrawableCheckingUriGrant(context, this.uriGrantsManager, i, packageName);
                } catch (Exception unused2) {
                    drawable = null;
                    z = true;
                }
            } else {
                drawable = null;
            }
            if (drawable == null) {
                drawable = icon2 != null ? icon2.loadDrawable(context) : null;
            }
            if (drawable != null && (constantState = drawable.getConstantState()) != null && (newDrawable = constantState.newDrawable()) != null) {
                loaded = new Icon.Loaded(newDrawable, null, null, 4, null);
            }
            iconResult = new IconResult(loaded, z);
        } else {
            iconResult = new IconResult(null, true);
        }
        QSTileState.Companion companion = QSTileState.Companion;
        CharSequence label = customTileDataModel.tile.getLabel();
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.custom.ui.mapper.CustomTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.ActivationState activationState;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                CustomTileDataModel customTileDataModel2 = CustomTileDataModel.this;
                int state = customTileDataModel2.tile.getState();
                if (customTileDataModel2.hasPendingBind) {
                    state = 0;
                }
                CustomTileMapper.IconResult iconResult2 = iconResult;
                builder.icon = iconResult2.icon;
                if (iconResult2.failedToLoad) {
                    activationState = QSTileState.ActivationState.UNAVAILABLE;
                } else {
                    QSTileState.ActivationState.Companion.getClass();
                    activationState = state != 1 ? state != 2 ? QSTileState.ActivationState.UNAVAILABLE : QSTileState.ActivationState.ACTIVE : QSTileState.ActivationState.INACTIVE;
                }
                builder.activationState = activationState;
                CharSequence subtitle = customTileDataModel2.tile.getSubtitle();
                if (subtitle != null && subtitle.length() != 0) {
                    builder.secondaryLabel = customTileDataModel2.tile.getSubtitle();
                }
                builder.contentDescription = customTileDataModel2.tile.getContentDescription();
                builder.stateDescription = customTileDataModel2.tile.getStateDescription();
                boolean z2 = customTileDataModel2.isToggleable;
                if (!z2) {
                    builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                }
                builder.supportedActions = state == 0 ? Collections.singleton(QSTileState.UserAction.LONG_CLICK) : ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                builder.expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(z2 ? Switch.class : Button.class);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(iconResult.icon, label, function1);
    }
}
