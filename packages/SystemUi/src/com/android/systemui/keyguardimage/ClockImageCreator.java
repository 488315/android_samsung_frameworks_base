package com.android.systemui.keyguardimage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardSidePadding;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClockImageCreator implements ImageCreator {
    public final Context mContext;
    public final CoverScreenManager mCoverScreenManager;
    public final SubScreenManager mSubScreenManager;
    public final ExternalClockProvider mClockProvider = (ExternalClockProvider) Dependency.get(ExternalClockProvider.class);
    public final PluginFaceWidgetManager mPluginFaceWidget = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);

    public ClockImageCreator(Context context) {
        this.mContext = context;
        this.mSubScreenManager = LsRune.SUBSCREEN_UI ? (SubScreenManager) Dependency.get(SubScreenManager.class) : null;
        this.mCoverScreenManager = LsRune.COVER_SUPPORTED ? (CoverScreenManager) Dependency.get(CoverScreenManager.class) : null;
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0073  */
    @Override // com.android.systemui.keyguardimage.ImageCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) {
        int parseInt;
        View clockView;
        PluginClockProvider pluginClockProvider;
        boolean z;
        int i;
        Pair<Integer, Integer> pair;
        View subScreenPreview;
        LogUtil.m223d("ClockImageCreator", "createImage() option: " + imageOption.toString(), new Object[0]);
        int i2 = imageOption.type;
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        if (i2 == 4) {
            if (imageOption.displayType == 33) {
                CoverScreenManager coverScreenManager = this.mCoverScreenManager;
                if (coverScreenManager == null) {
                    LogUtil.m226w("ClockImageCreator", "createImage return null - mCoverScreenManager is null", new Object[0]);
                    return null;
                }
                String str = imageOption.clockType;
                if (str != null) {
                    int parseInt2 = Integer.parseInt(str);
                    PluginCover pluginCover = coverScreenManager.mCoverPlugin;
                    if (pluginCover == null) {
                        Log.w("CoverScreenManager", "getClockPreview() no plugin");
                        clockView = null;
                        if (clockView != null) {
                            LogUtil.m226w("ClockImageCreator", "createImage return null - getClockPreview is null", new Object[0]);
                            return null;
                        }
                        int i3 = imageOption.coverClockColor;
                        if (i3 != 0 || imageOption.coverClockColorIndex != -1) {
                            int i4 = imageOption.coverClockColorIndex;
                            int i5 = imageOption.coverClockColorType;
                            if (coverScreenManager.mCoverPlugin == null) {
                                Log.d("CoverScreenManager", "setClockPreviewColor() no plugin");
                            } else {
                                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setClockPreviewColor() paletteIndex=", i4, ", fontColorType=", i5, ", customColor=");
                                m45m.append(Integer.toHexString(i3));
                                Log.d("CoverScreenManager", m45m.toString());
                                coverScreenManager.mCoverPlugin.setClockColor(clockView, i4, i5, i3);
                            }
                        }
                    } else {
                        clockView = pluginCover.getCoverScreenPreview(parseInt2);
                        if (clockView != null) {
                        }
                    }
                } else {
                    PluginCover pluginCover2 = coverScreenManager.mCoverPlugin;
                    if (pluginCover2 == null) {
                        Log.w("CoverScreenManager", "getClockPreview() no plugin");
                        clockView = null;
                        if (clockView != null) {
                        }
                    } else {
                        clockView = pluginCover2.getCoverScreenPreview();
                        if (clockView != null) {
                        }
                    }
                }
            } else {
                SubScreenManager subScreenManager = this.mSubScreenManager;
                if (subScreenManager == null) {
                    LogUtil.m226w("ClockImageCreator", "createImage returns null - SubScreenManager is null", new Object[0]);
                    return null;
                }
                String str2 = imageOption.clockType;
                if (str2 != null) {
                    int parseInt3 = Integer.parseInt(str2);
                    PluginSubScreen pluginSubScreen = subScreenManager.mSubScreenPlugin;
                    if (pluginSubScreen == null) {
                        Log.w("SubScreenManager", "getClockPreview() no plugin");
                        subScreenPreview = null;
                        clockView = subScreenPreview;
                        if (clockView != null) {
                            LogUtil.m226w("ClockImageCreator", "createImage returns null - getClockPreview is null", new Object[0]);
                            return null;
                        }
                        int i6 = imageOption.coverClockColor;
                        if (i6 != 0 || imageOption.coverClockColorIndex != -1) {
                            int i7 = imageOption.coverClockColorIndex;
                            int i8 = imageOption.coverClockColorType;
                            if (subScreenManager.mSubScreenPlugin == null) {
                                Log.d("SubScreenManager", "setClockPreviewColor() no plugin");
                            } else {
                                StringBuilder m45m2 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setClockPreviewColor() paletteIndex=", i7, ", fontColorType=", i8, ", customColor=");
                                m45m2.append(Integer.toHexString(i6));
                                Log.d("SubScreenManager", m45m2.toString());
                                subScreenManager.mSubScreenPlugin.setClockColor(clockView, i7, i8, i6);
                            }
                        }
                    } else {
                        subScreenPreview = pluginSubScreen.getSubScreenPreview(parseInt3);
                        clockView = subScreenPreview;
                        if (clockView != null) {
                        }
                    }
                } else {
                    PluginSubScreen pluginSubScreen2 = subScreenManager.mSubScreenPlugin;
                    if (pluginSubScreen2 == null) {
                        Log.w("SubScreenManager", "getClockPreview() no plugin");
                        subScreenPreview = null;
                        clockView = subScreenPreview;
                        if (clockView != null) {
                        }
                    } else {
                        subScreenPreview = pluginSubScreen2.getSubScreenPreview();
                        clockView = subScreenPreview;
                        if (clockView != null) {
                        }
                    }
                }
            }
            parseInt = 2;
        } else {
            String str3 = imageOption.clockType;
            if (str3 == null) {
                PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
                parseInt = pluginClockProvider2 != null ? pluginClockProvider2.getDefaultClockType() : 2;
                PluginClockProvider pluginClockProvider3 = externalClockProvider.mClockProvider;
                int consideredClockType = pluginClockProvider3 != null ? pluginClockProvider3.getConsideredClockType() : 2;
                if (consideredClockType != -1) {
                    PluginClockProvider pluginClockProvider4 = externalClockProvider.mClockProvider;
                    int clockGroup = pluginClockProvider4 != null ? pluginClockProvider4.getClockGroup(consideredClockType) : 2;
                    if (clockGroup == 1 || clockGroup == 2 || clockGroup == 4 || clockGroup == 5 || clockGroup == 7 || clockGroup == 9 || clockGroup == 10) {
                        parseInt = consideredClockType;
                    }
                }
            } else {
                parseInt = Integer.parseInt(str3);
            }
            LogUtil.m223d("ClockImageCreator", AbstractC0000x2c234b15.m0m("createImage clockType: ", parseInt), new Object[0]);
            PluginClockProvider pluginClockProvider5 = externalClockProvider.mClockProvider;
            clockView = pluginClockProvider5 != null ? pluginClockProvider5.getClockView(parseInt) : null;
            if (clockView == null) {
                LogUtil.m226w("ClockImageCreator", "createImage returns null - clockView is null", new Object[0]);
                return null;
            }
            if (!imageOption.useDefaultColor) {
                PluginClockProvider pluginClockProvider6 = externalClockProvider.mClockProvider;
                if (pluginClockProvider6 != null) {
                    pluginClockProvider6.setAdaptiveColors(clockView, imageOption.color);
                }
            } else if (!imageOption.useClockColor || imageOption.clockColor == 0) {
                PluginClockProvider pluginClockProvider7 = externalClockProvider.mClockProvider;
                if (pluginClockProvider7 != null) {
                    pluginClockProvider7.setPreDefineOrCustomColor(clockView);
                }
            } else {
                int i9 = imageOption.legibilityColor;
                if (i9 != -1 && (pluginClockProvider = externalClockProvider.mClockProvider) != null) {
                    pluginClockProvider.setFontColorType(clockView, i9);
                }
                int i10 = imageOption.clockColor;
                PluginClockProvider pluginClockProvider8 = externalClockProvider.mClockProvider;
                if (pluginClockProvider8 != null) {
                    pluginClockProvider8.setColorThemeAdaptiveColor(clockView, i10, false);
                }
            }
            if (imageOption.isRtl) {
                clockView.setLayoutDirection(1);
            }
            PluginClockProvider pluginClockProvider9 = externalClockProvider.mClockProvider;
            if (pluginClockProvider9 != null) {
                pluginClockProvider9.forceRefresh(clockView);
            }
        }
        Bitmap viewImage = ImageCreator.getViewImage(clockView, imageOption);
        if (viewImage == null) {
            return null;
        }
        if (point == null) {
            return viewImage;
        }
        Context context = this.mContext;
        context.getResources().getValue(R.dimen.facewidget_bottom_margin_ratio, new TypedValue(), true);
        boolean z2 = imageOption.width < imageOption.height;
        PluginClockProvider pluginClockProvider10 = externalClockProvider.mClockProvider;
        int clockGravity = pluginClockProvider10 == null ? 0 : pluginClockProvider10.getClockGravity();
        if (clockGravity == -1 || clockGravity == 0) {
            z = true;
            PluginClockProvider pluginClockProvider11 = externalClockProvider.mClockProvider;
            if (pluginClockProvider11 != null ? pluginClockProvider11.isStartAlignClock(parseInt) : false) {
                int i11 = imageOption.width;
                int i12 = imageOption.height;
                Resources resources = context.getResources();
                PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
                PluginKeyguardSidePadding pluginKeyguardSidePadding = pluginFaceWidgetManager != null ? pluginFaceWidgetManager.mPluginKeyguardSidePadding : null;
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.facewidget_page_margin_side);
                if (pluginKeyguardSidePadding != null) {
                    dimensionPixelSize = DeviceType.isTablet() ? pluginKeyguardSidePadding.getTabletClockSidePadding(i11, i12, !z2) : z2 ? pluginKeyguardSidePadding.getClockSidePadding() : pluginKeyguardSidePadding.needToSidePaddingForClock() ? pluginKeyguardSidePadding.getSidePaddingWhenIndisplayFP() : (int) (i11 * 0.162f);
                }
                int i13 = (int) (dimensionPixelSize * imageOption.scale);
                point.x = i13;
                if (imageOption.isRtl) {
                    point.x = (imageOption.width - i13) - viewImage.getWidth();
                }
            } else {
                point.x = (imageOption.width - viewImage.getWidth()) / 2;
            }
        } else {
            PluginClockProvider pluginClockProvider12 = externalClockProvider.mClockProvider;
            if (pluginClockProvider12 == null) {
                pair = new Pair<>(0, 0);
            } else {
                try {
                    pair = pluginClockProvider12.getKeyguardClockHorizontalPadding();
                } catch (Error unused) {
                    pair = new Pair<>(0, 0);
                }
            }
            z = true;
            if (clockGravity == 1) {
                point.x = (int) ((((imageOption.scale * Math.max(((Integer) pair.first).intValue(), ((Integer) pair.second).intValue())) / 2.0f) + (imageOption.width - viewImage.getWidth())) / 2.0f);
            } else if (clockGravity == 8388611) {
                point.x = (int) (((Integer) pair.first).intValue() * imageOption.scale);
            } else {
                point.x = (imageOption.width - ((int) (((Integer) pair.second).intValue() * imageOption.scale))) - viewImage.getWidth();
            }
        }
        float height = viewImage.getHeight();
        float f = imageOption.scale;
        if (0.0f < f && f < 1.0f) {
            height /= f;
        }
        PluginClockProvider pluginClockProvider13 = externalClockProvider.mClockProvider;
        if (pluginClockProvider13 != null && (pluginClockProvider13.getClockScalePivot(parseInt) & 65536) != 65536) {
            z = false;
        }
        if (z) {
            PluginClockProvider pluginClockProvider14 = externalClockProvider.mClockProvider;
            i = (int) ((((1.0f / (pluginClockProvider14 == null ? 1.0f : pluginClockProvider14.getClockScale())) - 1.0f) * height) / 2.0f);
        } else {
            i = 0;
        }
        point.y = (int) (((imageOption.scale * height <= ((float) viewImage.getHeight()) ? 0.0f : height - (viewImage.getHeight() / imageOption.scale)) + (z2 ? 0.0f : SystemBarUtils.getStatusBarHeight(context)) + i + (this.mPluginFaceWidget.mFaceWidgetPlugin != null ? r13.getMinTopMargin(parseInt, false) : 0)) * imageOption.scale);
        return viewImage;
    }
}
