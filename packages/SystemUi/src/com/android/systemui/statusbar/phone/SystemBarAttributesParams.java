package com.android.systemui.statusbar.phone;

import android.view.InsetsFlags;
import android.view.ViewDebug;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemBarAttributesParams {
    public final int appearance;
    public final List appearanceRegions;
    public final AppearanceRegion[] appearanceRegionsArray;
    public final int behavior;
    public final int displayId;
    public final List letterboxes;
    public final LetterboxDetails[] letterboxesArray;
    public final boolean navbarColorManagedByIme;
    public final String packageName;
    public final int requestedVisibleTypes;

    public SystemBarAttributesParams(int i, int i2, List<? extends AppearanceRegion> list, boolean z, int i3, int i4, String str, List<? extends LetterboxDetails> list2) {
        this.displayId = i;
        this.appearance = i2;
        this.appearanceRegions = list;
        this.navbarColorManagedByIme = z;
        this.behavior = i3;
        this.requestedVisibleTypes = i4;
        this.packageName = str;
        this.letterboxes = list2;
        this.letterboxesArray = (LetterboxDetails[]) list2.toArray(new LetterboxDetails[0]);
        this.appearanceRegionsArray = (AppearanceRegion[]) list.toArray(new AppearanceRegion[0]);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemBarAttributesParams)) {
            return false;
        }
        SystemBarAttributesParams systemBarAttributesParams = (SystemBarAttributesParams) obj;
        return this.displayId == systemBarAttributesParams.displayId && this.appearance == systemBarAttributesParams.appearance && Intrinsics.areEqual(this.appearanceRegions, systemBarAttributesParams.appearanceRegions) && this.navbarColorManagedByIme == systemBarAttributesParams.navbarColorManagedByIme && this.behavior == systemBarAttributesParams.behavior && this.requestedVisibleTypes == systemBarAttributesParams.requestedVisibleTypes && Intrinsics.areEqual(this.packageName, systemBarAttributesParams.packageName) && Intrinsics.areEqual(this.letterboxes, systemBarAttributesParams.letterboxes);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.appearanceRegions.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.appearance, Integer.hashCode(this.displayId) * 31, 31)) * 31;
        boolean z = this.navbarColorManagedByIme;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return this.letterboxes.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.packageName, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.requestedVisibleTypes, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.behavior, (hashCode + i) * 31, 31), 31), 31);
    }

    public final String toString() {
        return StringsKt__IndentKt.trimMargin$default("SystemBarAttributesParams(\n            displayId=" + this.displayId + ",\n            appearance=" + ViewDebug.flagsToString(InsetsFlags.class, "appearance", this.appearance) + ",\n            appearanceRegions=" + this.appearanceRegions + ",\n            navbarColorManagedByIme=" + this.navbarColorManagedByIme + ",\n            behavior=" + this.behavior + ",\n            requestedVisibleTypes=" + this.requestedVisibleTypes + ",\n            packageName='" + this.packageName + "',\n            letterboxes=" + this.letterboxes + ",\n            letterboxesArray=" + Arrays.toString(this.letterboxesArray) + ",\n            appearanceRegionsArray=" + Arrays.toString(this.appearanceRegionsArray) + "\n            )");
    }
}
