package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.DeviceType;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IndicatorGardenPresenter implements Dumpable, CallbackController {
    public IndicatorGardenModel cachedGardenModel;
    public int displayDeviceType;
    public IndicatorGardenAlgorithm gardenAlgorithm;
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorGardenAlgorithmFactory indicatorGardenAlgorithmFactory;
    public final IndicatorGardenInputProperties inputProperties;
    public final List listeners;
    public final Handler mainHandler;
    public final ArrayList statusIconContainerCallbacks;

    public IndicatorGardenPresenter(DumpManager dumpManager, Context context, IndicatorGardenAlgorithmFactory indicatorGardenAlgorithmFactory, IndicatorGardenInputProperties indicatorGardenInputProperties, Handler handler, IndicatorCutoutUtil indicatorCutoutUtil) {
        this.indicatorGardenAlgorithmFactory = indicatorGardenAlgorithmFactory;
        this.inputProperties = indicatorGardenInputProperties;
        this.mainHandler = handler;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
        DeviceType.isEngOrUTBinary();
        IndicatorCutoutUtil indicatorCutoutUtil2 = indicatorGardenAlgorithmFactory.indicatorCutoutUtil;
        this.gardenAlgorithm = indicatorCutoutUtil2.cutoutType == CutoutType.CENTER_CUTOUT ? indicatorGardenAlgorithmFactory.indicatorGardenAlgorithmCenterCutout : (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && indicatorCutoutUtil2.isMainDisplay()) ? indicatorGardenAlgorithmFactory.indicatorGardenAlgorithmSidelingCenterCutout : indicatorGardenAlgorithmFactory.indicatorGardenAlgorithmNoCutout;
        this.statusIconContainerCallbacks = new ArrayList();
        this.cachedGardenModel = new IndicatorGardenModel();
        this.listeners = new ArrayList();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            this.displayDeviceType = context.getResources().getConfiguration().semDisplayDeviceType;
        }
        dumpManager.registerNormalDumpable("IndicatorGardenPresenter", this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = (PluginFaceWidgetManager$$ExternalSyntheticLambda0) obj;
        synchronized (this.listeners) {
            ((ArrayList) this.listeners).add(pluginFaceWidgetManager$$ExternalSyntheticLambda0);
        }
        IndicatorGardenModel indicatorGardenModel = this.cachedGardenModel;
        PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager$$ExternalSyntheticLambda0.f$0.mFaceWidgetPlugin;
        if (pluginKeyguardStatusView != null) {
            pluginKeyguardStatusView.onIndicatorGardenUpdated(indicatorGardenModel.paddingLeft, indicatorGardenModel.paddingRight, indicatorGardenModel.totalHeight, indicatorGardenModel.cameraTopMargin);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("IndicatorGardenPresenter");
        printWriter.println("    " + this.gardenAlgorithm.name);
        printWriter.println("");
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        indicatorGardenInputProperties.getClass();
        printWriter.println("    IndicatorGardenInputProperties");
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("        rotation(0-0,90-1,180-2,270-3)=", indicatorGardenInputProperties.rotation, printWriter);
        int i = indicatorGardenInputProperties.statusBarWidth;
        int statusBarHeight = SystemBarUtils.getStatusBarHeight(indicatorGardenInputProperties.context);
        Rect bounds = indicatorGardenInputProperties.context.getResources().getConfiguration().windowConfiguration.getBounds();
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, statusBarHeight, "        statusBarWidth=", ", statusBarHeight=", " ");
        m.append(bounds);
        printWriter.println(m.toString());
        printWriter.println(HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(indicatorGardenInputProperties.cornerPaddingC, indicatorGardenInputProperties.defaultStartPadding, "        cornerPaddingC=", " (defaultStartPadding=", ")"));
        int i2 = indicatorGardenInputProperties.cutoutSidePaddingD;
        int i3 = indicatorGardenInputProperties.cutoutInnerPaddingD;
        int i4 = indicatorGardenInputProperties.defaultCenterPadding;
        StringBuilder m2 = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i2, i3, "        cutoutSidePaddingD=", ", cutoutInnerPaddingD=", " (defaultCenterPadding=");
        m2.append(i4);
        m2.append(")");
        printWriter.println(m2.toString());
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("        cutoutTopMarginB=", indicatorGardenInputProperties.cutoutTopMarginB, printWriter);
        printWriter.println("        density=" + indicatorGardenInputProperties.density);
        printWriter.println("        displayCutout=" + indicatorGardenInputProperties.displayCutout);
        IndicatorCutoutUtil indicatorCutoutUtil = this.indicatorGardenAlgorithmFactory.indicatorCutoutUtil;
        indicatorCutoutUtil.getClass();
        printWriter.println("    IndicatorCutoutUtil");
        printWriter.println("        cutoutType=" + indicatorCutoutUtil.cutoutType + " isUDC=" + indicatorCutoutUtil.isUDCModel + " isMainDisplay=" + indicatorCutoutUtil.isMainDisplay());
        Rect displayCutoutAreaToExclude = indicatorCutoutUtil.getDisplayCutoutAreaToExclude();
        StringBuilder sb = new StringBuilder("        excludeArea=");
        sb.append(displayCutoutAreaToExclude);
        printWriter.println(sb.toString());
    }

    public final void onGardenApplyWindowInsets(IndicatorGarden indicatorGarden) {
        if (indicatorGarden.getGardenWindowInsets() != null) {
            WindowInsets gardenWindowInsets = indicatorGarden.getGardenWindowInsets();
            Intrinsics.checkNotNull(gardenWindowInsets);
            if (gardenWindowInsets.getDisplayCutout() != null) {
                WindowInsets gardenWindowInsets2 = indicatorGarden.getGardenWindowInsets();
                Intrinsics.checkNotNull(gardenWindowInsets2);
                DisplayCutout displayCutout = gardenWindowInsets2.getDisplayCutout();
                IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
                if (!Intrinsics.areEqual(displayCutout, indicatorGardenInputProperties.displayCutout)) {
                    Log.d("IndicatorGardenInputProperties", "Set cutout=" + displayCutout);
                }
                indicatorGardenInputProperties.displayCutout = displayCutout;
            }
        }
        updateGardenWithNewModel(indicatorGarden);
    }

    public final void onGardenConfigurationChanged(IndicatorGarden indicatorGarden, Configuration configuration) {
        this.inputProperties.updateWindowMetrics();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            int i = this.displayDeviceType;
            int i2 = configuration.semDisplayDeviceType;
            if (i != i2) {
                this.displayDeviceType = i2;
                IndicatorGardenAlgorithmFactory indicatorGardenAlgorithmFactory = this.indicatorGardenAlgorithmFactory;
                indicatorGardenAlgorithmFactory.indicatorCutoutUtil.loadDisplayCutout();
                IndicatorCutoutUtil indicatorCutoutUtil = indicatorGardenAlgorithmFactory.indicatorCutoutUtil;
                this.gardenAlgorithm = indicatorCutoutUtil.cutoutType == CutoutType.CENTER_CUTOUT ? indicatorGardenAlgorithmFactory.indicatorGardenAlgorithmCenterCutout : (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && indicatorCutoutUtil.isMainDisplay()) ? indicatorGardenAlgorithmFactory.indicatorGardenAlgorithmSidelingCenterCutout : indicatorGardenAlgorithmFactory.indicatorGardenAlgorithmNoCutout;
            }
        }
        updateGardenWithNewModel(indicatorGarden);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = (PluginFaceWidgetManager$$ExternalSyntheticLambda0) obj;
        synchronized (this.listeners) {
            ((ArrayList) this.listeners).remove(pluginFaceWidgetManager$$ExternalSyntheticLambda0);
        }
    }

    public final void updateGardenWithNewModel(IndicatorGarden indicatorGarden) {
        PluginKeyguardStatusView pluginKeyguardStatusView;
        IndicatorGardenAlgorithm indicatorGardenAlgorithm = this.gardenAlgorithm;
        indicatorGardenAlgorithm.initResources();
        IndicatorGardenModel indicatorGardenModel = new IndicatorGardenModel();
        indicatorGardenModel.totalHeight = SystemBarUtils.getStatusBarHeight(indicatorGardenAlgorithm.context);
        indicatorGardenModel.paddingLeft = indicatorGardenAlgorithm.calculateLeftPadding();
        indicatorGardenModel.paddingRight = indicatorGardenAlgorithm.calculateRightPadding();
        indicatorGardenModel.hasCameraTopMargin = indicatorGardenAlgorithm.hasCameraTopMargin();
        indicatorGardenModel.cameraTopMargin = indicatorGardenAlgorithm.calculateCameraTopMargin();
        indicatorGardenModel.maxWidthCenterContainer = indicatorGardenAlgorithm.calculateCenterContainerMaxWidth();
        indicatorGardenModel.maxWidthLeftContainer = indicatorGardenAlgorithm.calculateLeftContainerMaxWidth(indicatorGarden);
        indicatorGardenModel.maxWidthRightContainer = indicatorGardenAlgorithm.calculateRightContainerMaxWidth(indicatorGarden);
        if (!indicatorGardenModel.isEqual(this.cachedGardenModel)) {
            Iterator it = ((ArrayList) this.listeners).iterator();
            while (it.hasNext()) {
                PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 = (PluginFaceWidgetManager$$ExternalSyntheticLambda0) it.next();
                if (((ArrayList) this.listeners).contains(pluginFaceWidgetManager$$ExternalSyntheticLambda0) && (pluginKeyguardStatusView = pluginFaceWidgetManager$$ExternalSyntheticLambda0.f$0.mFaceWidgetPlugin) != null) {
                    pluginKeyguardStatusView.onIndicatorGardenUpdated(indicatorGardenModel.paddingLeft, indicatorGardenModel.paddingRight, indicatorGardenModel.totalHeight, indicatorGardenModel.cameraTopMargin);
                }
            }
            this.cachedGardenModel = indicatorGardenModel;
        }
        indicatorGarden.updateGarden(indicatorGardenModel, this.inputProperties);
    }
}
