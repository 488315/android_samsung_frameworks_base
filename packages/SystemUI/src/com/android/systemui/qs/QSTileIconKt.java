package com.android.systemui.qs;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes2.dex */
public abstract class QSTileIconKt {
    public static final QSTile.Icon asQSTileIcon(Icon icon) {
        if (icon instanceof Icon.Loaded) {
            Icon.Loaded loaded = (Icon.Loaded) icon;
            Integer num = loaded.res;
            return num == null ? new QSTileImpl.DrawableIcon(loaded.drawable) : new QSTileImpl.DrawableIconWithRes(loaded.drawable, num.intValue());
        }
        if (icon instanceof Icon.Resource) {
            return QSTileImpl.ResourceIcon.get(((Icon.Resource) icon).res);
        }
        throw new NoWhenBranchMatchedException();
    }
}
