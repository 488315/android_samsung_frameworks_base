package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarModel {
    public final int barState;
    public final boolean isWhiteKeyguardStatusBar;
    public final ArrayList lightBarBounds;
    public final String logText;
    public final int numStacks;
    public final String packageName;
    public final int statusBarMode;

    public StatusBarModel(String str, int i, ArrayList<Rect> arrayList, int i2, String str2, int i3, boolean z) {
        this.logText = str;
        this.numStacks = i;
        this.lightBarBounds = arrayList;
        this.statusBarMode = i2;
        this.packageName = str2;
        this.barState = i3;
        this.isWhiteKeyguardStatusBar = z;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof StatusBarModel) && Intrinsics.areEqual(((StatusBarModel) obj).logText, this.logText)) {
            return true;
        }
        return super.equals(obj);
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isWhiteKeyguardStatusBar) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.barState, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.statusBarMode, (this.lightBarBounds.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.numStacks, this.logText.hashCode() * 31, 31)) * 31, 31), 31, this.packageName), 31);
    }

    public final String toString() {
        String str = this.packageName;
        if (StringsKt__StringsKt.contains(str, "com.att", false)) {
            str = "";
        }
        return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(new StringBuilder("("), this.logText, ") ", "numStacks:" + this.numStacks + ", StatusBarMode:" + BarTransitions.modeToString(this.statusBarMode) + ", lightBarBounds:" + this.lightBarBounds + ", StatusBarState:" + StatusBarState.toString(this.barState) + ", packageName:" + str + ", isWhiteKeyguardStatusBar:" + this.isWhiteKeyguardStatusBar);
    }

    public StatusBarModel(String str, int i, ArrayList arrayList, int i2, String str2, int i3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, arrayList, i2, str2, (i4 & 32) != 0 ? ((StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class)).getState() : i3, (i4 & 64) != 0 ? ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isWhiteKeyguardStatusBar() : z);
    }
}
