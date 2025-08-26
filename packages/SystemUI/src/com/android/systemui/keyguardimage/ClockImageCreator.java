package com.android.systemui.keyguardimage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
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

/* loaded from: classes2.dex */
public class ClockImageCreator implements ImageCreator {
    public final Context mContext;
    public final CoverScreenManager mCoverScreenManager;
    public final SubScreenManager mSubScreenManager;
    public final ExternalClockProvider mClockProvider = (ExternalClockProvider) Dependency.sDependency.getDependencyInner(ExternalClockProvider.class);
    public final PluginFaceWidgetManager mPluginFaceWidget = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);

    public ClockImageCreator(Context context) {
        this.mContext = context;
        this.mSubScreenManager = LsRune.SUBSCREEN_UI ? (SubScreenManager) Dependency.sDependency.getDependencyInner(SubScreenManager.class) : null;
        this.mCoverScreenManager = LsRune.COVER_SUPPORTED ? (CoverScreenManager) Dependency.sDependency.getDependencyInner(CoverScreenManager.class) : null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e0  */
    @Override // com.android.systemui.keyguardimage.ImageCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bitmap createImage(ImageOptionCreator.ImageOption imageOption, Point point) throws Resources.NotFoundException, NumberFormatException {
        Bitmap bitmap;
        int defaultClockType;
        int i;
        View clockView;
        PluginClockProvider pluginClockProvider;
        boolean z;
        int clockScale;
        Pair<Integer, Integer> pair;
        View subScreenPreview;
        LogUtil.d("ClockImageCreator", "createImage() option: " + imageOption.toString(), new Object[0]);
        int i2 = imageOption.type;
        ExternalClockProvider externalClockProvider = this.mClockProvider;
        if (i2 == 4) {
            bitmap = null;
            if (imageOption.displayType == 33) {
                CoverScreenManager coverScreenManager = this.mCoverScreenManager;
                if (coverScreenManager == null) {
                    LogUtil.w("ClockImageCreator", "createImage return null - mCoverScreenManager is null", new Object[0]);
                    return null;
                }
                String str = imageOption.clockType;
                if (str != null) {
                    int i3 = Integer.parseInt(str);
                    PluginCover pluginCover = coverScreenManager.mCoverPlugin;
                    if (pluginCover == null) {
                        Log.w("CoverScreenManager", "getClockPreview() no plugin");
                        clockView = null;
                        if (clockView != null) {
                            LogUtil.w("ClockImageCreator", "createImage return null - getClockPreview is null", new Object[0]);
                            return null;
                        }
                        int i4 = imageOption.coverClockColor;
                        if (i4 != 0 || imageOption.coverClockColorIndex != -1) {
                            int i5 = imageOption.coverClockColorIndex;
                            int i6 = imageOption.coverClockColorType;
                            if (coverScreenManager.mCoverPlugin == null) {
                                Log.d("CoverScreenManager", "setClockPreviewColor() no plugin");
                            } else {
                                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i6, "setClockPreviewColor() paletteIndex=", ", fontColorType=", ", customColor=");
                                sbM.append(Integer.toHexString(i4));
                                Log.d("CoverScreenManager", sbM.toString());
                                coverScreenManager.mCoverPlugin.setClockColor(clockView, i5, i6, i4);
                            }
                        }
                    } else {
                        clockView = pluginCover.getCoverScreenPreview(i3);
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
                    LogUtil.w("ClockImageCreator", "createImage returns null - SubScreenManager is null", new Object[0]);
                    return null;
                }
                String str2 = imageOption.clockType;
                if (str2 != null) {
                    int i7 = Integer.parseInt(str2);
                    PluginSubScreen pluginSubScreen = subScreenManager.mSubScreenPlugin;
                    if (pluginSubScreen == null) {
                        Log.w("SubScreenManager", "getClockPreview() no plugin");
                        subScreenPreview = null;
                        if (subScreenPreview != null) {
                            LogUtil.w("ClockImageCreator", "createImage returns null - getClockPreview is null", new Object[0]);
                            return null;
                        }
                        int i8 = imageOption.coverClockColor;
                        if (i8 != 0 || imageOption.coverClockColorIndex != -1) {
                            int i9 = imageOption.coverClockColorIndex;
                            int i10 = imageOption.coverClockColorType;
                            if (subScreenManager.mSubScreenPlugin == null) {
                                Log.d("SubScreenManager", "setClockPreviewColor() no plugin");
                            } else {
                                StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(i9, i10, "setClockPreviewColor() paletteIndex=", ", fontColorType=", ", customColor=");
                                sbM2.append(Integer.toHexString(i8));
                                Log.d("SubScreenManager", sbM2.toString());
                                subScreenManager.mSubScreenPlugin.setClockColor(subScreenPreview, i9, i10, i8);
                            }
                        }
                        clockView = subScreenPreview;
                    } else {
                        subScreenPreview = pluginSubScreen.getSubScreenPreview(i7);
                        if (subScreenPreview != null) {
                        }
                    }
                } else {
                    PluginSubScreen pluginSubScreen2 = subScreenManager.mSubScreenPlugin;
                    if (pluginSubScreen2 == null) {
                        Log.w("SubScreenManager", "getClockPreview() no plugin");
                        subScreenPreview = null;
                        if (subScreenPreview != null) {
                        }
                    } else {
                        subScreenPreview = pluginSubScreen2.getSubScreenPreview();
                        if (subScreenPreview != null) {
                        }
                    }
                }
            }
            i = 2;
        } else {
            bitmap = null;
            String str3 = imageOption.clockType;
            if (str3 == null) {
                PluginClockProvider pluginClockProvider2 = externalClockProvider.mClockProvider;
                defaultClockType = pluginClockProvider2 != null ? pluginClockProvider2.getDefaultClockType() : 2;
                PluginClockProvider pluginClockProvider3 = externalClockProvider.mClockProvider;
                int consideredClockType = pluginClockProvider3 != null ? pluginClockProvider3.getConsideredClockType() : 2;
                if (consideredClockType != -1) {
                    PluginClockProvider pluginClockProvider4 = externalClockProvider.mClockProvider;
                    int clockGroup = pluginClockProvider4 != null ? pluginClockProvider4.getClockGroup(consideredClockType) : 2;
                    if (clockGroup == 1 || clockGroup == 2 || clockGroup == 4 || clockGroup == 5 || clockGroup == 7 || clockGroup == 9 || clockGroup == 10) {
                        defaultClockType = consideredClockType;
                    }
                }
            } else {
                defaultClockType = Integer.parseInt(str3);
            }
            i = defaultClockType;
            LogUtil.d("ClockImageCreator", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "createImage clockType: "), new Object[0]);
            PluginClockProvider pluginClockProvider5 = externalClockProvider.mClockProvider;
            clockView = pluginClockProvider5 != null ? pluginClockProvider5.getClockView(i) : null;
            if (clockView == null) {
                LogUtil.w("ClockImageCreator", "createImage returns null - clockView is null", new Object[0]);
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
                int i11 = imageOption.legibilityColor;
                if (i11 != -1 && (pluginClockProvider = externalClockProvider.mClockProvider) != null) {
                    pluginClockProvider.setFontColorType(clockView, i11);
                }
                int i12 = imageOption.clockColor;
                PluginClockProvider pluginClockProvider8 = externalClockProvider.mClockProvider;
                if (pluginClockProvider8 != null) {
                    pluginClockProvider8.setColorThemeAdaptiveColor(clockView, i12, false);
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
        Bitmap viewImage = ImageCreator.getViewImage(clockView, imageOption, false);
        if (viewImage == null) {
            return bitmap;
        }
        if (point == null) {
            return viewImage;
        }
        this.mContext.getResources().getValue(R.dimen.facewidget_bottom_margin_ratio, new TypedValue(), true);
        boolean z2 = imageOption.width < imageOption.height;
        PluginClockProvider pluginClockProvider10 = externalClockProvider.mClockProvider;
        int clockGravity = pluginClockProvider10 == null ? 0 : pluginClockProvider10.getClockGravity();
        if (clockGravity == -1 || clockGravity == 0) {
            z = true;
            PluginClockProvider pluginClockProvider11 = externalClockProvider.mClockProvider;
            if (pluginClockProvider11 != null ? pluginClockProvider11.isStartAlignClock(i) : false) {
                int i13 = imageOption.width;
                int i14 = imageOption.height;
                Resources resources = this.mContext.getResources();
                PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
                PluginKeyguardSidePadding pluginKeyguardSidePadding = pluginFaceWidgetManager != null ? pluginFaceWidgetManager.mPluginKeyguardSidePadding : bitmap;
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.facewidget_page_margin_side);
                if (pluginKeyguardSidePadding != 0) {
                    dimensionPixelSize = DeviceType.isTablet() ? pluginKeyguardSidePadding.getTabletClockSidePadding(i13, i14, !z2) : z2 ? pluginKeyguardSidePadding.getClockSidePadding() : pluginKeyguardSidePadding.needToSidePaddingForClock() ? pluginKeyguardSidePadding.getSidePaddingWhenIndisplayFP() : (int) (i13 * 0.162f);
                }
                int i15 = (int) (dimensionPixelSize * imageOption.scale);
                point.x = i15;
                if (imageOption.isRtl) {
                    point.x = (imageOption.width - i15) - viewImage.getWidth();
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
        if (pluginClockProvider13 != null && (pluginClockProvider13.getClockScalePivot(i) & 65536) != 65536) {
            z = false;
        }
        if (z) {
            PluginClockProvider pluginClockProvider14 = externalClockProvider.mClockProvider;
            clockScale = (int) ((((1.0f / (pluginClockProvider14 == null ? 1.0f : pluginClockProvider14.getClockScale())) - 1.0f) * height) / 2.0f);
        } else {
            clockScale = 0;
        }
        point.y = (int) (((imageOption.scale * height <= ((float) viewImage.getHeight()) ? 0.0f : height - (viewImage.getHeight() / imageOption.scale)) + (z2 ? 0.0f : SystemBarUtils.getStatusBarHeight(this.mContext)) + clockScale + (this.mPluginFaceWidget.mFaceWidgetPlugin != null ? r0.getMinTopMargin(i, false) : 0)) * imageOption.scale);
        return viewImage;
    }
}
