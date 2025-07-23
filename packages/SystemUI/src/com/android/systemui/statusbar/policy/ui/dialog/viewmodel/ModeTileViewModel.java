package com.android.systemui.statusbar.policy.ui.dialog.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ModeTileViewModel {
    public final boolean enabled;
    public final Icon icon;
    public final String id;
    public final Function0 onClick;
    public final Function0 onLongClick;
    public final String onLongClickLabel;
    public final String stateDescription;
    public final String subtext;
    public final String subtextDescription;
    public final String text;

    public ModeTileViewModel(String str, Icon icon, String str2, String str3, String str4, boolean z, String str5, Function0 function0, Function0 function02, String str6) {
        this.id = str;
        this.icon = icon;
        this.text = str2;
        this.subtext = str3;
        this.subtextDescription = str4;
        this.enabled = z;
        this.stateDescription = str5;
        this.onClick = function0;
        this.onLongClick = function02;
        this.onLongClickLabel = str6;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModeTileViewModel)) {
            return false;
        }
        ModeTileViewModel modeTileViewModel = (ModeTileViewModel) obj;
        return Intrinsics.areEqual(this.id, modeTileViewModel.id) && Intrinsics.areEqual(this.icon, modeTileViewModel.icon) && Intrinsics.areEqual(this.text, modeTileViewModel.text) && Intrinsics.areEqual(this.subtext, modeTileViewModel.subtext) && Intrinsics.areEqual(this.subtextDescription, modeTileViewModel.subtextDescription) && this.enabled == modeTileViewModel.enabled && Intrinsics.areEqual(this.stateDescription, modeTileViewModel.stateDescription) && Intrinsics.areEqual(this.onClick, modeTileViewModel.onClick) && Intrinsics.areEqual(this.onLongClick, modeTileViewModel.onLongClick) && Intrinsics.areEqual(this.onLongClickLabel, modeTileViewModel.onLongClickLabel);
    }

    public final int hashCode() {
        return this.onLongClickLabel.hashCode() + ((this.onLongClick.hashCode() + ((this.onClick.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.icon.hashCode() + (this.id.hashCode() * 31)) * 31, 31, this.text), 31, this.subtext), 31, this.subtextDescription), 31, this.enabled), 31, this.stateDescription)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ModeTileViewModel(id=");
        sb.append(this.id);
        sb.append(", icon=");
        sb.append(this.icon);
        sb.append(", text=");
        sb.append(this.text);
        sb.append(", subtext=");
        sb.append(this.subtext);
        sb.append(", subtextDescription=");
        sb.append(this.subtextDescription);
        sb.append(", enabled=");
        sb.append(this.enabled);
        sb.append(", stateDescription=");
        sb.append(this.stateDescription);
        sb.append(", onClick=");
        sb.append(this.onClick);
        sb.append(", onLongClick=");
        sb.append(this.onLongClick);
        sb.append(", onLongClickLabel=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.onLongClickLabel, ")");
    }
}
