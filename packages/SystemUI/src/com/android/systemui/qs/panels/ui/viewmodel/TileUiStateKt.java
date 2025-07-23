package com.android.systemui.qs.panels.ui.viewmodel;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Switch;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.state.ToggleableState;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.ui.viewmodel.IconProvider;
import com.android.systemui.qs.tileimpl.SubtitleArrayMapping;
import java.util.function.Supplier;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TileUiStateKt {
    public static final IconProvider toIconProvider(QSTile.State state) {
        QSTile.Icon icon = state.icon;
        if (icon != null) {
            return new IconProvider.ConstantIcon(icon);
        }
        Supplier<QSTile.Icon> supplier = state.iconSupplier;
        return supplier != null ? new IconProvider.IconSupplier(supplier) : IconProvider.Empty.INSTANCE;
    }

    public static final TileUiState toUiState(QSTile.State state, Resources resources) {
        int i;
        String str;
        String obj;
        String obj2;
        String obj3;
        if (!Intrinsics.areEqual(state.expandedAccessibilityClassName, Switch.class.getName()) || state.handlesSecondaryClick) {
            Role.Companion.getClass();
            i = 0;
        } else {
            Role.Companion.getClass();
            i = Role.Switch;
        }
        StringBuilder sb = new StringBuilder();
        Role.Companion.getClass();
        int i2 = Role.Switch;
        String str2 = "";
        if (i == i2 || state.state == 0) {
            SubtitleArrayMapping subtitleArrayMapping = SubtitleArrayMapping.INSTANCE;
            String str3 = state.spec;
            subtitleArrayMapping.getClass();
            str = resources.getStringArray(SubtitleArrayMapping.getSubtitleId(str3))[state.state];
        } else {
            str = "";
        }
        CharSequence secondaryLabel = state.getSecondaryLabel(str);
        if (!TextUtils.isEmpty(str)) {
            sb.append((CharSequence) str);
        }
        if (state.disabledByPolicy && state.state != 0) {
            sb.append(", ");
            String str4 = state.spec;
            SubtitleArrayMapping.INSTANCE.getClass();
            sb.append(resources.getStringArray(SubtitleArrayMapping.getSubtitleId(str4))[0]);
        }
        if (!TextUtils.isEmpty(state.stateDescription)) {
            CharSequence charSequence = state.stateDescription;
            charSequence.getClass();
            if (!StringsKt__StringsKt.contains(sb, charSequence, false)) {
                sb.append(", ");
                sb.append(state.stateDescription);
            }
        }
        ToggleableState toggleableState = (i == i2 || state.handlesSecondaryClick) ? state.state == 2 ? ToggleableState.On : ToggleableState.Off : null;
        CharSequence charSequence2 = state.label;
        String str5 = (charSequence2 == null || (obj3 = charSequence2.toString()) == null) ? "" : obj3;
        String str6 = (secondaryLabel == null || (obj2 = secondaryLabel.toString()) == null) ? "" : obj2;
        int i3 = state.disabledByPolicy ? 0 : state.state;
        boolean z = state.handlesLongClick;
        boolean z2 = state.handlesSecondaryClick;
        Drawable drawable = state.sideViewCustomDrawable;
        CharSequence charSequence3 = state.contentDescription;
        if (charSequence3 != null && (obj = charSequence3.toString()) != null) {
            str2 = obj;
        }
        return new TileUiState(str5, str6, i3, z, z2, drawable, new AccessibilityUiState(str2, sb.toString(), i, toggleableState, state.disabledByPolicy ? resources.getString(R.string.accessibility_tile_disabled_by_policy_action_description) : null, null));
    }
}
