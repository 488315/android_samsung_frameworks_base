package com.android.systemui.navigationbar.icon;

import android.content.Context;
import android.util.Log;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.navigationbar.NavBarButtonDrawableProvider;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawableProvider;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.samsung.systemui.splugins.navigationbar.IconResource;
import com.samsung.systemui.splugins.navigationbar.IconTheme;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.IconThemeType;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NavBarIconResourceMapper {
    public final String TAG;
    public final KeyButtonDrawableProvider buttonDrawableProvider;
    public final Context context;
    public boolean coverIcon;
    public final Map defaultIconList;
    public final Map defaultIconResIdList;
    public boolean isRTL;
    public final List largeScreenIconList;
    public final String largeScreenPostfix;
    public final LogWrapper logWrapper;
    public final NavBarStore navBarStore;
    public IconThemeBase preloadedIconSet;
    public final Function0 supportLargeCover;
    public final Function0 supportThemeIcon;
    public boolean themeIcon;
    public final List themeIconList;
    public final String themePostfix;

    public NavBarIconResourceMapper(KeyButtonDrawableProvider keyButtonDrawableProvider, NavBarStore navBarStore, Context context, LogWrapper logWrapper) {
        this.buttonDrawableProvider = keyButtonDrawableProvider;
        this.navBarStore = navBarStore;
        this.context = context;
        this.logWrapper = logWrapper;
        IconType iconType = IconType.TYPE_BACK;
        Pair pair = new Pair(iconType, new IconResourceInfo(iconType, "ic_samsung_sysbar_back", "ic_samsung_sysbar_back_dark", true));
        IconType iconType2 = IconType.TYPE_BACK_LAND;
        Pair pair2 = new Pair(iconType2, new IconResourceInfo(iconType2, "ic_samsung_sysbar_back", "ic_samsung_sysbar_back_dark", true));
        IconType iconType3 = IconType.TYPE_BACK_ALT;
        Pair pair3 = new Pair(iconType3, new IconResourceInfo(iconType3, "ic_samsung_sysbar_back_ime", "ic_samsung_sysbar_back_ime_dark", false, 8, null));
        IconType iconType4 = IconType.TYPE_BACK_ALT_LAND;
        Pair pair4 = new Pair(iconType4, new IconResourceInfo(iconType4, "ic_samsung_sysbar_back_ime", "ic_samsung_sysbar_back_ime_dark", false, 8, null));
        IconType iconType5 = IconType.TYPE_HOME;
        Pair pair5 = new Pair(iconType5, new IconResourceInfo(iconType5, "ic_samsung_sysbar_home", "ic_samsung_sysbar_home_dark", false, 8, null));
        IconType iconType6 = IconType.TYPE_RECENT;
        Pair pair6 = new Pair(iconType6, new IconResourceInfo(iconType6, "ic_samsung_sysbar_recent", "ic_samsung_sysbar_recent_dark", false, 8, null));
        IconType iconType7 = IconType.TYPE_IME;
        Pair pair7 = new Pair(iconType7, new IconResourceInfo(iconType7, "ic_samsung_sysbar_ime", "ic_samsung_sysbar_ime_dark", false, 8, null));
        IconType iconType8 = IconType.TYPE_A11Y;
        Pair pair8 = new Pair(iconType8, new IconResourceInfo(iconType8, "ic_samsung_sysbar_accessibility_button", "ic_samsung_sysbar_accessibility_button_dark", false, 8, null));
        IconType iconType9 = IconType.TYPE_GESTURE_HANDLE_HINT;
        Pair pair9 = new Pair(iconType9, new IconResourceInfo(iconType9, "ic_samsung_sysbar_gesture_handle_hint", "ic_samsung_sysbar_gesture_handle_hint_dark", false, 8, null));
        IconType iconType10 = IconType.TYPE_SECONDARY_HOME_HANDLE;
        Pair pair10 = new Pair(iconType10, new IconResourceInfo(iconType10, "ic_samsung_sysbar_gesture_handle_hint", "ic_samsung_sysbar_gesture_handle_hint_dark", false, 8, null));
        IconType iconType11 = IconType.TYPE_GESTURE_HINT;
        Pair pair11 = new Pair(iconType11, new IconResourceInfo(iconType11, "ic_samsung_sysbar_gesture_hint", "ic_samsung_sysbar_gesture_hint_dark", false, 8, null));
        IconType iconType12 = IconType.TYPE_GESTURE_HINT_VI;
        Pair pair12 = new Pair(iconType12, new IconResourceInfo(iconType12, "ic_samsung_sysbar_gesture_hint_vi", "ic_samsung_sysbar_gesture_hint_vi", false, 8, null));
        IconType iconType13 = IconType.TYPE_SHOW_PIN;
        Pair pair13 = new Pair(iconType13, new IconResourceInfo(iconType13, "ic_samsung_sysbar_navi_show", "ic_samsung_sysbar_navi_show_dark", false, 8, null));
        IconType iconType14 = IconType.TYPE_HIDE_PIN;
        this.defaultIconList = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, pair9, pair10, pair11, pair12, pair13, new Pair(iconType14, new IconResourceInfo(iconType14, "ic_samsung_sysbar_navi_hide", "ic_samsung_sysbar_navi_hide_dark", false, 8, null)));
        this.defaultIconResIdList = MapsKt__MapsKt.mapOf(new Pair(iconType, new IconResourceIdInfo(iconType, R.drawable.ic_samsung_sysbar_back, R.drawable.ic_samsung_sysbar_back_dark, true)), new Pair(iconType2, new IconResourceIdInfo(iconType2, R.drawable.ic_samsung_sysbar_back, R.drawable.ic_samsung_sysbar_back_dark, true)), new Pair(iconType3, new IconResourceIdInfo(iconType2, R.drawable.ic_samsung_sysbar_back_ime, R.drawable.ic_samsung_sysbar_back_ime_dark, true)), new Pair(iconType4, new IconResourceIdInfo(iconType2, R.drawable.ic_samsung_sysbar_back_ime, R.drawable.ic_samsung_sysbar_back_ime_dark, true)), new Pair(iconType5, new IconResourceIdInfo(iconType5, R.drawable.ic_samsung_sysbar_home, R.drawable.ic_samsung_sysbar_home_dark, false, 8, null)), new Pair(iconType6, new IconResourceIdInfo(iconType6, R.drawable.ic_samsung_sysbar_recent, R.drawable.ic_samsung_sysbar_recent_dark, false, 8, null)), new Pair(iconType7, new IconResourceIdInfo(iconType7, R.drawable.ic_samsung_sysbar_ime, R.drawable.ic_samsung_sysbar_ime_dark, false, 8, null)), new Pair(iconType8, new IconResourceIdInfo(iconType8, R.drawable.ic_samsung_sysbar_accessibility_button, R.drawable.ic_samsung_sysbar_accessibility_button_dark, false, 8, null)), new Pair(iconType9, new IconResourceIdInfo(iconType9, R.drawable.ic_samsung_sysbar_gesture_handle_hint, R.drawable.ic_samsung_sysbar_gesture_handle_hint_dark, false, 8, null)), new Pair(iconType10, new IconResourceIdInfo(iconType10, R.drawable.ic_samsung_sysbar_gesture_handle_hint, R.drawable.ic_samsung_sysbar_gesture_handle_hint_dark, false, 8, null)), new Pair(iconType11, new IconResourceIdInfo(iconType11, R.drawable.ic_samsung_sysbar_gesture_hint, R.drawable.ic_samsung_sysbar_gesture_hint_dark, false, 8, null)), new Pair(iconType12, new IconResourceIdInfo(iconType12, R.drawable.ic_samsung_sysbar_gesture_hint_vi, R.drawable.ic_samsung_sysbar_gesture_hint_vi, false, 8, null)), new Pair(iconType13, new IconResourceIdInfo(iconType13, R.drawable.ic_samsung_sysbar_navi_show, R.drawable.ic_samsung_sysbar_navi_show_dark, false, 8, null)), new Pair(iconType14, new IconResourceIdInfo(iconType14, R.drawable.ic_samsung_sysbar_navi_hide, R.drawable.ic_samsung_sysbar_navi_hide_dark, false, 8, null)));
        this.themeIconList = CollectionsKt__CollectionsKt.listOf(iconType, iconType2, iconType3, iconType4, iconType5, iconType6);
        this.largeScreenIconList = CollectionsKt__CollectionsKt.listOf(iconType, iconType2, iconType3, iconType4, iconType5);
        this.themePostfix = "_theme";
        this.largeScreenPostfix = "_front";
        this.TAG = "NavBarIconResourceMapper";
        this.supportThemeIcon = new NavBarIconResourceMapper$supportThemeIcon$1(this);
        this.supportLargeCover = new NavBarIconResourceMapper$supportLargeCover$1(this);
    }

    public final KeyButtonDrawable getButtonDrawable(IconType iconType) {
        IconResource iconResource = getIconResource(iconType);
        KeyButtonDrawableProvider keyButtonDrawableProvider = this.buttonDrawableProvider;
        if (iconResource != null && iconResource.mNeedRtlCheck && this.isRTL) {
            Context context = this.context;
            ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getClass();
            return KeyButtonDrawable.create(context, iconResource.mLightDrawable, iconResource.mDarkDrawable, true);
        }
        Context context2 = this.context;
        ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getClass();
        return KeyButtonDrawable.create(context2, iconResource.mLightDrawable, iconResource.mDarkDrawable, false);
    }

    public final IconTheme getDefaultIconTheme() {
        IconTheme iconTheme = new IconTheme(IconThemeType.TYPE_DEFAULT);
        for (IconType iconType : this.defaultIconList.keySet()) {
            iconTheme.addData(iconType, getIconResource(iconType));
        }
        return iconTheme;
    }

    public final IconResource getIconResource(IconType iconType) {
        IconThemeBase iconThemeBase = this.preloadedIconSet;
        if (iconThemeBase != null) {
            return iconThemeBase.getData(iconType);
        }
        boolean z = false;
        this.themeIcon = ((Boolean) ((NavBarIconResourceMapper$supportThemeIcon$1) this.supportThemeIcon).invoke()).booleanValue() && ((NavBarStateManagerImpl) this.navBarStore.getNavStateManager()).isNavigationBarUseThemeDefault() && this.themeIconList.contains(iconType);
        if (((Boolean) ((NavBarIconResourceMapper$supportLargeCover$1) this.supportLargeCover).invoke()).booleanValue() && this.largeScreenIconList.contains(iconType)) {
            z = true;
        }
        this.coverIcon = z;
        IconResourceInfo iconResourceInfo = (IconResourceInfo) this.defaultIconList.get(iconType);
        if (iconResourceInfo != null) {
            boolean z2 = this.themeIcon;
            String str = this.themePostfix;
            String str2 = this.largeScreenPostfix;
            String str3 = iconResourceInfo.lightRes;
            if (z2) {
                str3 = StringsKt__StringsJVMKt.replace$default(str3 + str, "_samsung", "");
            } else if (this.coverIcon) {
                str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, str2);
            }
            boolean z3 = this.themeIcon;
            String str4 = iconResourceInfo.darkRes;
            if (z3) {
                str4 = StringsKt__StringsJVMKt.replace$default(str4 + str, "_samsung", "");
            } else if (this.coverIcon) {
                str4 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str4, str2);
            }
            String packageName = this.themeIcon ? "android" : this.context.getPackageName();
            int identifier = this.context.getResources().getIdentifier(str3, BriefViewController.URI_PATH_DRAWABLE, packageName);
            int identifier2 = this.context.getResources().getIdentifier(str4, BriefViewController.URI_PATH_DRAWABLE, packageName);
            boolean z4 = (true ^ this.themeIcon) & iconResourceInfo.needRtl;
            if (identifier != 0 && identifier2 != 0) {
                return new IconResource(iconResourceInfo.type, this.context.getDrawable(identifier), this.context.getDrawable(identifier2), z4);
            }
            StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(identifier, "Unable to retrieve resource: lightResName=", str3, "(", ") lightResName=");
            m.append(str4);
            m.append("(");
            m.append(identifier2);
            m.append(")");
            Log.i(this.TAG, m.toString());
            IconResourceIdInfo iconResourceIdInfo = (IconResourceIdInfo) this.defaultIconResIdList.get(iconType);
            if (iconResourceIdInfo != null) {
                return new IconResource(iconResourceIdInfo.type, this.context.getDrawable(iconResourceIdInfo.lightResId), this.context.getDrawable(iconResourceIdInfo.darkResId), iconResourceIdInfo.needRtl);
            }
        }
        return null;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class IconResourceIdInfo {
        public final int darkResId;
        public final int lightResId;
        public final boolean needRtl;
        public final IconType type;

        public IconResourceIdInfo(IconType iconType, int i, int i2, boolean z) {
            this.type = iconType;
            this.lightResId = i;
            this.darkResId = i2;
            this.needRtl = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IconResourceIdInfo)) {
                return false;
            }
            IconResourceIdInfo iconResourceIdInfo = (IconResourceIdInfo) obj;
            return this.type == iconResourceIdInfo.type && this.lightResId == iconResourceIdInfo.lightResId && this.darkResId == iconResourceIdInfo.darkResId && this.needRtl == iconResourceIdInfo.needRtl;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.needRtl) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.darkResId, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.lightResId, this.type.hashCode() * 31, 31), 31);
        }

        public final String toString() {
            return "IconResourceIdInfo(type=" + this.type + ", lightResId=" + this.lightResId + ", darkResId=" + this.darkResId + ", needRtl=" + this.needRtl + ")";
        }

        public /* synthetic */ IconResourceIdInfo(IconType iconType, int i, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(iconType, i, i2, (i3 & 8) != 0 ? false : z);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class IconResourceInfo {
        public final String darkRes;
        public final String lightRes;
        public final boolean needRtl;
        public final IconType type;

        public IconResourceInfo(IconType iconType, String str, String str2, boolean z) {
            this.type = iconType;
            this.lightRes = str;
            this.darkRes = str2;
            this.needRtl = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof IconResourceInfo)) {
                return false;
            }
            IconResourceInfo iconResourceInfo = (IconResourceInfo) obj;
            return this.type == iconResourceInfo.type && Intrinsics.areEqual(this.lightRes, iconResourceInfo.lightRes) && Intrinsics.areEqual(this.darkRes, iconResourceInfo.darkRes) && this.needRtl == iconResourceInfo.needRtl;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.needRtl) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.type.hashCode() * 31, 31, this.lightRes), 31, this.darkRes);
        }

        public final String toString() {
            return "IconResourceInfo(type=" + this.type + ", lightRes=" + this.lightRes + ", darkRes=" + this.darkRes + ", needRtl=" + this.needRtl + ")";
        }

        public /* synthetic */ IconResourceInfo(IconType iconType, String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(iconType, str, str2, (i & 8) != 0 ? false : z);
        }
    }
}
