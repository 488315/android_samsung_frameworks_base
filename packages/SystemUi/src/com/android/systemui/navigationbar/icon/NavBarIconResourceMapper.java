package com.android.systemui.navigationbar.icon;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.navigationbar.NavBarButtonDrawableProvider;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawableProvider;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarIconResourceMapper {
    public final String TAG;
    public final KeyButtonDrawableProvider buttonDrawableProvider;
    public final Context context;
    public boolean coverIcon;
    public final Map defaultIconList;
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
        this.themeIconList = CollectionsKt__CollectionsKt.listOf(iconType, iconType2, iconType3, iconType4, iconType5, iconType6);
        this.largeScreenIconList = CollectionsKt__CollectionsKt.listOf(iconType, iconType2, iconType3, iconType4, iconType5);
        this.themePostfix = "_theme";
        this.largeScreenPostfix = "_front";
        this.TAG = "NavBarIconResourceMapper";
        this.supportThemeIcon = new NavBarIconResourceMapper$supportThemeIcon$1(this);
        this.supportLargeCover = new NavBarIconResourceMapper$supportLargeCover$1(this);
    }

    public static final String getIconResource$getThemeReplacedResName(NavBarIconResourceMapper navBarIconResourceMapper, String str) {
        return StringsKt__StringsJVMKt.replace$default(str + navBarIconResourceMapper.themePostfix, "_samsung", "");
    }

    public final KeyButtonDrawable getButtonDrawable(IconType iconType) {
        IconResource iconResource = getIconResource(iconType);
        boolean z = iconResource != null && iconResource.mNeedRtlCheck;
        Context context = this.context;
        KeyButtonDrawableProvider keyButtonDrawableProvider = this.buttonDrawableProvider;
        if (z && this.isRTL) {
            ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getClass();
            return KeyButtonDrawable.create(context, iconResource.mLightDrawable, iconResource.mDarkDrawable, true);
        }
        ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getClass();
        return KeyButtonDrawable.create(context, iconResource.mLightDrawable, iconResource.mDarkDrawable, false);
    }

    public final IconTheme getDefaultIconTheme() {
        IconTheme iconTheme = new IconTheme(IconThemeType.TYPE_DEFAULT);
        for (IconType iconType : this.defaultIconList.keySet()) {
            iconTheme.addData(iconType, getIconResource(iconType));
        }
        return iconTheme;
    }

    public final GestureHintDrawable getGestureHandleDrawable(IconType iconType, int i) {
        return ((NavBarButtonDrawableProvider) this.buttonDrawableProvider).getGestureHintDrawable(this.context, getIconResource(iconType), i);
    }

    public final IconResource getIconResource(IconType iconType) {
        IconThemeBase iconThemeBase = this.preloadedIconSet;
        if (iconThemeBase != null) {
            return iconThemeBase.getData(iconType);
        }
        boolean z = false;
        this.themeIcon = ((Boolean) ((NavBarIconResourceMapper$supportThemeIcon$1) this.supportThemeIcon).invoke()).booleanValue() && ((NavBarStoreImpl) this.navBarStore).getNavStateManager(0).isNavigationBarUseThemeDefault() && this.themeIconList.contains(iconType);
        if (((Boolean) ((NavBarIconResourceMapper$supportLargeCover$1) this.supportLargeCover).invoke()).booleanValue() && this.largeScreenIconList.contains(iconType)) {
            z = true;
        }
        this.coverIcon = z;
        IconResourceInfo iconResourceInfo = (IconResourceInfo) this.defaultIconList.get(iconType);
        if (iconResourceInfo == null) {
            return null;
        }
        boolean z2 = this.themeIcon;
        String str = this.largeScreenPostfix;
        String str2 = iconResourceInfo.lightRes;
        if (z2) {
            str2 = getIconResource$getThemeReplacedResName(this, str2);
        } else if (this.coverIcon) {
            str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, str);
        }
        boolean z3 = this.themeIcon;
        String str3 = iconResourceInfo.darkRes;
        if (z3) {
            str3 = getIconResource$getThemeReplacedResName(this, str3);
        } else if (this.coverIcon) {
            str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str3, str);
        }
        boolean z4 = this.themeIcon;
        Context context = this.context;
        String packageName = z4 ? "android" : context.getPackageName();
        return new IconResource(iconResourceInfo.type, context.getDrawable(context.getResources().getIdentifier(str2, "drawable", packageName)), context.getDrawable(context.getResources().getIdentifier(str3, "drawable", packageName)), (!this.themeIcon) & iconResourceInfo.needRtl);
    }

    public final void setPreloadedIconSet(IconThemeBase iconThemeBase) {
        this.logWrapper.m98d(this.TAG, "setPreloadedIconSet() null: " + (iconThemeBase == null));
        this.preloadedIconSet = iconThemeBase;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.darkRes, AppInfo$$ExternalSyntheticOutline0.m41m(this.lightRes, this.type.hashCode() * 31, 31), 31);
            boolean z = this.needRtl;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return m41m + i;
        }

        public final String toString() {
            return "IconResourceInfo(type=" + this.type + ", lightRes=" + this.lightRes + ", darkRes=" + this.darkRes + ", needRtl=" + this.needRtl + ")";
        }

        public /* synthetic */ IconResourceInfo(IconType iconType, String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(iconType, str, str2, (i & 8) != 0 ? false : z);
        }
    }
}
