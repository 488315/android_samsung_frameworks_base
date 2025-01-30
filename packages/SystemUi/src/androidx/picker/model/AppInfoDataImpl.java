package androidx.picker.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppInfoDataImpl implements AppInfoData {
    public final Drawable actionIcon;
    public final AppInfo appInfo;
    public boolean dimmed;
    public final String extraLabel;
    public Drawable icon;
    public final boolean isValueInSubLabel;
    public final int itemType;
    public String label;
    public boolean selected;
    public final Drawable subIcon;
    public final String subLabel;

    public AppInfoDataImpl(AppInfo appInfo) {
        this(appInfo, 0, null, null, null, null, null, null, false, false, false, 2046, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(AppInfoDataImpl.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        AppInfoDataImpl appInfoDataImpl = (AppInfoDataImpl) obj;
        return Intrinsics.areEqual(this.appInfo, appInfoDataImpl.appInfo) && this.itemType == appInfoDataImpl.itemType && Intrinsics.areEqual(this.icon, appInfoDataImpl.icon) && Intrinsics.areEqual(this.subIcon, appInfoDataImpl.subIcon) && Intrinsics.areEqual(this.label, appInfoDataImpl.label) && Intrinsics.areEqual(this.subLabel, appInfoDataImpl.subLabel) && Intrinsics.areEqual(this.extraLabel, appInfoDataImpl.extraLabel) && Intrinsics.areEqual(this.actionIcon, appInfoDataImpl.actionIcon) && this.selected == appInfoDataImpl.selected && this.dimmed == appInfoDataImpl.dimmed && this.isValueInSubLabel == appInfoDataImpl.isValueInSubLabel;
    }

    @Override // androidx.picker.model.AppInfoData
    public final Drawable getActionIcon() {
        return this.actionIcon;
    }

    @Override // androidx.picker.model.AppData
    public final AppInfo getAppInfo() {
        return this.appInfo;
    }

    @Override // androidx.picker.model.AppInfoData
    public final boolean getDimmed() {
        return this.dimmed;
    }

    @Override // androidx.picker.model.AppInfoData
    public final String getExtraLabel() {
        return this.extraLabel;
    }

    @Override // androidx.picker.model.AppInfoData
    public final Drawable getIcon() {
        return this.icon;
    }

    @Override // androidx.picker.model.AppInfoData
    public final int getItemType() {
        return this.itemType;
    }

    @Override // androidx.picker.model.AppInfoData
    public final String getLabel() {
        return this.label;
    }

    @Override // androidx.picker.model.AppInfoData
    public final boolean getSelected() {
        return this.selected;
    }

    @Override // androidx.picker.model.AppInfoData
    public final Drawable getSubIcon() {
        return this.subIcon;
    }

    @Override // androidx.picker.model.AppInfoData
    public final String getSubLabel() {
        return this.subLabel;
    }

    public final int hashCode() {
        int hashCode = ((this.appInfo.hashCode() * 31) + this.itemType) * 31;
        Drawable drawable = this.icon;
        int hashCode2 = (hashCode + (drawable != null ? drawable.hashCode() : 0)) * 31;
        Drawable drawable2 = this.subIcon;
        int hashCode3 = (hashCode2 + (drawable2 != null ? drawable2.hashCode() : 0)) * 31;
        String str = this.label;
        int hashCode4 = (hashCode3 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.subLabel;
        int hashCode5 = (hashCode4 + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.extraLabel;
        int hashCode6 = (hashCode5 + (str3 != null ? str3.hashCode() : 0)) * 31;
        Drawable drawable3 = this.actionIcon;
        return Boolean.hashCode(this.isValueInSubLabel) + ((Boolean.hashCode(this.dimmed) + ((Boolean.hashCode(this.selected) + ((hashCode6 + (drawable3 != null ? drawable3.hashCode() : 0)) * 31)) * 31)) * 31);
    }

    @Override // androidx.picker.model.AppInfoData
    public final boolean isValueInSubLabel() {
        return this.isValueInSubLabel;
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setDimmed(boolean z) {
        this.dimmed = z;
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setLabel(String str) {
        this.label = str;
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setSelected(boolean z) {
        this.selected = z;
    }

    public AppInfoDataImpl(AppInfo appInfo, int i) {
        this(appInfo, i, null, null, null, null, null, null, false, false, false, 2044, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable) {
        this(appInfo, i, drawable, null, null, null, null, null, false, false, false, 2040, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2) {
        this(appInfo, i, drawable, drawable2, null, null, null, null, false, false, false, 2032, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str) {
        this(appInfo, i, drawable, drawable2, str, null, null, null, false, false, false, 2016, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2) {
        this(appInfo, i, drawable, drawable2, str, str2, null, null, false, false, false, 1984, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2, String str3) {
        this(appInfo, i, drawable, drawable2, str, str2, str3, null, false, false, false, 1920, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2, String str3, Drawable drawable3) {
        this(appInfo, i, drawable, drawable2, str, str2, str3, drawable3, false, false, false, 1792, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2, String str3, Drawable drawable3, boolean z) {
        this(appInfo, i, drawable, drawable2, str, str2, str3, drawable3, z, false, false, 1536, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2, String str3, Drawable drawable3, boolean z, boolean z2) {
        this(appInfo, i, drawable, drawable2, str, str2, str3, drawable3, z, z2, false, 1024, null);
    }

    public AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2, String str3, Drawable drawable3, boolean z, boolean z2, boolean z3) {
        this.appInfo = appInfo;
        this.itemType = i;
        this.icon = drawable;
        this.subIcon = drawable2;
        this.label = str;
        this.subLabel = str2;
        this.extraLabel = str3;
        this.actionIcon = drawable3;
        this.selected = z;
        this.dimmed = z2;
        this.isValueInSubLabel = z3;
    }

    public /* synthetic */ AppInfoDataImpl(AppInfo appInfo, int i, Drawable drawable, Drawable drawable2, String str, String str2, String str3, Drawable drawable3, boolean z, boolean z2, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(appInfo, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? null : drawable, (i2 & 8) != 0 ? null : drawable2, (i2 & 16) != 0 ? null : str, (i2 & 32) != 0 ? null : str2, (i2 & 64) != 0 ? null : str3, (i2 & 128) == 0 ? drawable3 : null, (i2 & 256) != 0 ? false : z, (i2 & 512) != 0 ? false : z2, (i2 & 1024) == 0 ? z3 : false);
    }
}
