package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.barState, AppInfo$$ExternalSyntheticOutline0.m41m(this.packageName, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.statusBarMode, (this.lightBarBounds.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.numStacks, this.logText.hashCode() * 31, 31)) * 31, 31), 31), 31);
        boolean z = this.isWhiteKeyguardStatusBar;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return m42m + i;
    }

    public final String toString() {
        String str = this.packageName;
        if (StringsKt__StringsKt.contains(str, "com.att", false)) {
            str = "";
        }
        return FragmentTransaction$$ExternalSyntheticOutline0.m38m(new StringBuilder("("), this.logText, ") ", "numStacks:" + this.numStacks + ", StatusBarMode:" + BarTransitions.modeToString(this.statusBarMode) + ", lightBarBounds:" + this.lightBarBounds + ", StatusBarState:" + StatusBarState.toString(this.barState) + ", packageName:" + str + ", isWhiteKeyguardStatusBar:" + this.isWhiteKeyguardStatusBar);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StatusBarModel(String str, int i, ArrayList arrayList, int i2, String str2, int i3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, arrayList, i2, str2, r7, r8);
        boolean z2;
        int state = (i4 & 32) != 0 ? ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).getState() : i3;
        if ((i4 & 64) != 0) {
            z2 = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("white_lockscreen_statusbar").getIntValue() == 1;
        } else {
            z2 = z;
        }
    }
}
