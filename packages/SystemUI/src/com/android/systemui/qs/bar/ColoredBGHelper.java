package com.android.systemui.qs.bar;

import android.app.OnSemColorsChangedListener;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.HexExtensionsKt;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ColoredBGHelper implements StatusBarStateController.StateListener, OnSemColorsChangedListener, ShadeExpansionListener, SettingsHelper.OnChangedCallback, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConfigurationController configurationController;
    public final Context context;
    public int curAlpha;
    public boolean panelExpanded;
    private final SettingsHelper settingsHelper;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarStateController statusBarStateController;
    public int uiMode;
    public ColoredBGHelper$updateBGColor$1 updateBGAfterPanelCollapsed;
    public final WallpaperManager wallpaperManager;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public final ArrayList barBGList = new ArrayList();
    public final ArrayList noRippleBarBGList = new ArrayList();
    public final ArrayList homeWallpaperColors = new ArrayList();
    public final ArrayList lockWallpaperColors = new ArrayList();
    public int actualAppliedColor = -1;
    public int curExtractColor = -1;
    public int homeMatchingColor = getResourceColor();
    public int lockMatchingColor = getResourceColor();
    public final Uri[] settingsValueList = {Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE), Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE)};
    public final int WALLPAPER_FIXED_ALPHA = HexExtensionsKt.hexToInt$default("3d");
    public final int THEME_FIXED_ALPHA = HexExtensionsKt.hexToInt$default(DATA.DM_FIELD_INDEX.PREF_CSCF_PORT);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ColoredBGHelper(Context context, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SettingsHelper settingsHelper, ConfigurationController configurationController) {
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.settingsHelper = settingsHelper;
        this.configurationController = configurationController;
        this.wallpaperManager = WallpaperManager.getInstance(context);
        this.uiMode = context.getResources().getConfiguration().uiMode & 48;
        Log.d("ColoredBGHelper", "init");
    }

    public static String toCompareColorString(int i, int i2) {
        return FontProvider$$ExternalSyntheticOutline0.m("0x", Integer.toHexString(i), " > 0x", Integer.toHexString(i2));
    }

    public final void addBarBackground(View view, boolean z) {
        int i = this.curExtractColor;
        if (i != -1) {
            setBackGroundDrawable(view, i);
        } else {
            setBackGroundDrawable(view, getBGColor());
        }
        if (!this.barBGList.contains(view)) {
            this.barBGList.add(view);
        }
        if (!z || this.noRippleBarBGList.contains(view)) {
            return;
        }
        this.noRippleBarBGList.add(view);
    }

    public final int extractColor(ArrayList arrayList, int i) {
        arrayList.clear();
        int[] seedColors = this.wallpaperManager.getSeedColors(i);
        if (seedColors != null) {
            for (int i2 : seedColors) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        if (arrayList.size() == 0) {
            return -1;
        }
        int intValue = ((Number) arrayList.get(0)).intValue();
        float[] fArr = new float[3];
        Color.colorToHSV(intValue, fArr);
        fArr[2] = fArr[2] * 0.5f;
        return Color.HSVToColor(fArr);
    }

    public final int getBGColor() {
        if (this.settingsHelper.isUltraPowerSavingMode()) {
            int color = this.context.getResources().getColor(R.color.qs_tile_coloring_container_bg_ultra_mode);
            Log.d("ColoredBGHelper", "getBGColor ultraPSMode");
            return color;
        }
        if (!StringsKt__StringsJVMKt.equals(Integer.toHexString(getResourceColor()), "3d000000", true)) {
            float[] fArr = new float[3];
            Color.colorToHSV(getResourceColor(), fArr);
            fArr[2] = fArr[2] * 0.5f;
            int HSVToColor = Color.HSVToColor(fArr);
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getBGColor bgColorOverlaid 0x", Integer.toHexString(HSVToColor), "ColoredBGHelper");
            return HSVToColor;
        }
        if (this.statusBarStateController.getState() != 0) {
            float[] fArr2 = new float[3];
            Color.colorToHSV(this.lockMatchingColor, fArr2);
            int resourceColor = (fArr2[1] <= 0.04f && fArr2[2] <= 0.04f) ? getResourceColor() : this.lockMatchingColor;
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getBGColor isLockScreenShowing 0x", Integer.toHexString(resourceColor), "ColoredBGHelper");
            return resourceColor;
        }
        if (this.statusBarStateController.getState() != 0) {
            return getResourceColor();
        }
        float[] fArr3 = new float[3];
        Color.colorToHSV(this.homeMatchingColor, fArr3);
        int resourceColor2 = (fArr3[1] <= 0.04f && fArr3[2] <= 0.04f) ? getResourceColor() : this.homeMatchingColor;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getBGColor isHomeWallpaperShowing 0x", Integer.toHexString(resourceColor2), "ColoredBGHelper");
        return resourceColor2;
    }

    public final int getResourceColor() {
        return this.context.getResources().getColor(R.color.qs_tile_container_bg);
    }

    public final void initialize() {
        this.statusBarStateController.addCallback(this);
        this.wallpaperManager.addOnSemColorsChangedListener(this, this.handler);
        this.shadeExpansionStateManager.addExpansionListener(this);
        SettingsHelper settingsHelper = this.settingsHelper;
        Uri[] uriArr = this.settingsValueList;
        settingsHelper.registerCallback(this, (Uri[]) Arrays.copyOf(uriArr, uriArr.length));
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE)) || Intrinsics.areEqual(uri, Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE))) {
            updateBGColor(false);
        }
    }

    public final void onColorsChanged(SemWallpaperColors semWallpaperColors, int i) {
        Log.d("ColoredBGHelper", "onColorsChanged which = " + i);
        updateBGColor(false);
    }

    public final void onDestroy() {
        this.barBGList.clear();
        this.noRippleBarBGList.clear();
        this.actualAppliedColor = -1;
        this.curExtractColor = -1;
        this.curAlpha = 0;
        this.statusBarStateController.removeCallback(this);
        this.wallpaperManager.removeOnSemColorsChangedListener(this);
        this.shadeExpansionStateManager.removeExpansionListener(this);
        this.settingsHelper.unregisterCallback(this);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        boolean z = shadeExpansionChangeEvent.expanded;
        this.panelExpanded = z;
        ColoredBGHelper$updateBGColor$1 coloredBGHelper$updateBGColor$1 = this.updateBGAfterPanelCollapsed;
        if (coloredBGHelper$updateBGColor$1 != null) {
            if (z) {
                coloredBGHelper$updateBGColor$1 = null;
            }
            if (coloredBGHelper$updateBGColor$1 != null) {
                coloredBGHelper$updateBGColor$1.run();
                this.updateBGAfterPanelCollapsed = null;
                Log.d("ColoredBGHelper", "updateBGAfterPanelCollapsed run~!");
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        this.updateBGAfterPanelCollapsed = null;
        if (i != 2) {
            updateBGColor(false);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        Log.d("ColoredBGHelper", "onThemeChanged");
        updateBGColor(false);
    }

    public final void setBackGroundDrawable(View view, int i) {
        int i2 = StringsKt__StringsJVMKt.equals(Integer.toHexString(getResourceColor()), "3d000000", true) ^ true ? this.THEME_FIXED_ALPHA : this.WALLPAPER_FIXED_ALPHA;
        if (i != this.curExtractColor || i2 != this.curAlpha) {
            int argb = Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
            int i3 = this.curAlpha;
            String compareColorString = toCompareColorString(this.curExtractColor, i);
            String compareColorString2 = toCompareColorString(this.actualAppliedColor, argb);
            String callers = Debug.getCallers(3, " ");
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i3, i2, "setBackGroundDrawable alpha = ", " > ", ", color = ");
            m.append(compareColorString);
            m.append(", actualAppliedColor = ");
            m.append(compareColorString2);
            m.append(callers);
            Log.d("ColoredBGHelper", m.toString());
            this.actualAppliedColor = argb;
            this.curExtractColor = i;
            this.curAlpha = i2;
        }
        if (view != null) {
            Drawable drawable = this.noRippleBarBGList.contains(view) ? this.context.getDrawable(R.drawable.sec_tile_layout_background) : this.context.getDrawable(R.drawable.sec_coloring_container_background);
            if (drawable != null) {
                drawable.setTint(this.actualAppliedColor);
                view.setBackground(drawable);
            }
        }
    }

    public final void updateBGColor(boolean z) {
        if (!z && this.statusBarStateController.getState() == 0 && this.panelExpanded) {
            Log.d("ColoredBGHelper", "updateBGAfterPanelCollapsed = Runnable { updateBGColor() }");
            this.updateBGAfterPanelCollapsed = new ColoredBGHelper$updateBGColor$1(this);
            return;
        }
        int extractColor = extractColor(this.homeWallpaperColors, 1);
        Integer valueOf = Integer.valueOf(extractColor);
        if (extractColor == -1) {
            valueOf = null;
        }
        if (valueOf != null) {
            this.homeMatchingColor = valueOf.intValue();
        }
        int extractColor2 = extractColor(this.lockWallpaperColors, 2);
        Integer valueOf2 = extractColor2 != -1 ? Integer.valueOf(extractColor2) : null;
        if (valueOf2 != null) {
            this.lockMatchingColor = valueOf2.intValue();
        }
        int bGColor = getBGColor();
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("updateBGColor extractColor = ", toCompareColorString(this.curExtractColor, bGColor), Debug.getCallers(3, " "), "ColoredBGHelper");
        Iterator it = this.barBGList.iterator();
        while (it.hasNext()) {
            setBackGroundDrawable((View) it.next(), bGColor);
        }
    }
}
