package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslWindowInsetsReflector;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.DeviceType;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IndicatorGardenPresenter implements Dumpable, CallbackController {
    public IndicatorGardenModel cachedGardenModel;
    public int displayDeviceType;
    public IndicatorGardenAlgorithm gardenAlgorithm;
    public HeadsUpAppearanceController headsUpAppearanceController;
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorGardenAlgorithmFactory indicatorGardenAlgorithmFactory;
    public final IndicatorGardenInputProperties inputProperties;
    public final List listeners;
    public final Handler mainHandler;
    public final ArrayList statusIconContainerCallbacks;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface GardenListener {
        void onGardenChanged(IndicatorGardenModel indicatorGardenModel);
    }

    public IndicatorGardenPresenter(DumpManager dumpManager, Context context, IndicatorGardenAlgorithmFactory indicatorGardenAlgorithmFactory, IndicatorGardenInputProperties indicatorGardenInputProperties, Handler handler, IndicatorCutoutUtil indicatorCutoutUtil) {
        this.indicatorGardenAlgorithmFactory = indicatorGardenAlgorithmFactory;
        this.inputProperties = indicatorGardenInputProperties;
        this.mainHandler = handler;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
        DeviceType.isEngOrUTBinary();
        this.gardenAlgorithm = indicatorGardenAlgorithmFactory.makeAlgorithm();
        this.statusIconContainerCallbacks = new ArrayList();
        this.cachedGardenModel = new IndicatorGardenModel();
        this.listeners = new ArrayList();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            this.displayDeviceType = context.getResources().getConfiguration().semDisplayDeviceType;
        }
        dumpManager.registerNormalDumpable("IndicatorGardenPresenter", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("IndicatorGardenPresenter");
        printWriter.println("    " + this.gardenAlgorithm.name);
        printWriter.println("");
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        indicatorGardenInputProperties.getClass();
        printWriter.println("    IndicatorGardenInputProperties");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("        rotation(0-0,90-1,180-2,270-3)=", indicatorGardenInputProperties.rotation, printWriter);
        int i = indicatorGardenInputProperties.statusBarWidth;
        int statusBarHeight = SystemBarUtils.getStatusBarHeight(indicatorGardenInputProperties.context);
        Rect bounds = indicatorGardenInputProperties.context.getResources().getConfiguration().windowConfiguration.getBounds();
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, statusBarHeight, "        statusBarWidth=", ", statusBarHeight=", " ");
        m.append(bounds);
        printWriter.println(m.toString());
        printWriter.println(MutableVectorKt$$ExternalSyntheticOutline0.m(indicatorGardenInputProperties.cornerPaddingC, indicatorGardenInputProperties.defaultStartPadding, "        cornerPaddingC=", " (defaultStartPadding=", ")"));
        int i2 = indicatorGardenInputProperties.cutoutSidePaddingD;
        int i3 = indicatorGardenInputProperties.cutoutInnerPaddingD;
        int i4 = indicatorGardenInputProperties.defaultCenterPadding;
        StringBuilder m2 = MutableObjectList$$ExternalSyntheticOutline0.m(i2, i3, "        cutoutSidePaddingD=", ", cutoutInnerPaddingD=", " (defaultCenterPadding=");
        m2.append(i4);
        m2.append(")");
        printWriter.println(m2.toString());
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("        cutoutTopMarginB=", indicatorGardenInputProperties.cutoutTopMarginB, printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("        cutoutBottomMarginGb=", indicatorGardenInputProperties.cutoutBottomMarginGb, printWriter);
        printWriter.println("        density=" + indicatorGardenInputProperties.density);
        printWriter.println("        displayCutout=" + indicatorGardenInputProperties.displayCutout);
        printWriter.println(this.indicatorGardenAlgorithmFactory.indicatorCutoutUtil.getLogText());
    }

    public final void onGardenApplyWindowInsets(IndicatorGarden indicatorGarden) {
        WindowInsets gardenWindowInsets = indicatorGarden.getGardenWindowInsets();
        if (gardenWindowInsets != null) {
            DisplayCutout displayCutout = gardenWindowInsets.getDisplayCutout();
            IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
            if (displayCutout != null) {
                indicatorGardenInputProperties.onGardenApplyWindowInsets(gardenWindowInsets.getDisplayCutout());
            } else {
                DisplayCutout displayCutout2 = null;
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD_HID_BUT_UDC_CUTOUT) {
                    Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslWindowInsetsReflector.mClass, "hidden_getDisplayCutoutForUdc", new Class[0]);
                    if (declaredMethod != null) {
                        Object invoke = SeslBaseReflector.invoke(gardenWindowInsets, declaredMethod, new Object[0]);
                        if (invoke instanceof DisplayCutout) {
                            displayCutout2 = (DisplayCutout) invoke;
                        }
                    }
                    indicatorGardenInputProperties.onGardenApplyWindowInsets(displayCutout2);
                } else {
                    indicatorGardenInputProperties.onGardenApplyWindowInsets(null);
                }
            }
        }
        updateGardenWithNewModel(indicatorGarden);
    }

    public final void onGardenConfigurationChanged(IndicatorGarden indicatorGarden, Configuration configuration) {
        IndicatorGardenInputProperties indicatorGardenInputProperties = this.inputProperties;
        indicatorGardenInputProperties.updateWindowMetrics();
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            int i = this.displayDeviceType;
            int i2 = configuration.semDisplayDeviceType;
            if (i != i2) {
                this.displayDeviceType = i2;
                IndicatorGardenAlgorithmFactory indicatorGardenAlgorithmFactory = this.indicatorGardenAlgorithmFactory;
                indicatorGardenAlgorithmFactory.indicatorCutoutUtil.loadDisplayCutout();
                indicatorGardenInputProperties.updateWindowMetrics();
                indicatorGardenInputProperties.updatePaddingValues();
                this.gardenAlgorithm = indicatorGardenAlgorithmFactory.makeAlgorithm();
            }
        }
        updateGardenWithNewModel(indicatorGarden);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        GardenListener gardenListener = (GardenListener) obj;
        synchronized (this.listeners) {
            ((ArrayList) this.listeners).remove(gardenListener);
        }
    }

    public final void updateGardenWithNewModel(IndicatorGarden indicatorGarden) {
        IndicatorGardenAlgorithm indicatorGardenAlgorithm = this.gardenAlgorithm;
        indicatorGardenAlgorithm.initResources();
        IndicatorGardenModel indicatorGardenModel = new IndicatorGardenModel();
        indicatorGardenModel.totalHeight = SystemBarUtils.getStatusBarHeight(indicatorGardenAlgorithm.context);
        indicatorGardenModel.paddingLeft = indicatorGardenAlgorithm.calculateLeftPadding();
        indicatorGardenModel.paddingRight = indicatorGardenAlgorithm.calculateRightPadding();
        indicatorGardenModel.hasCameraTopMargin = true;
        indicatorGardenModel.hasCameraBottomMargin = indicatorGardenAlgorithm.hasCameraBottomMargin();
        indicatorGardenModel.cameraTopMargin = indicatorGardenAlgorithm.calculateCameraTopMargin();
        indicatorGardenModel.cameraBottomMargin = indicatorGardenAlgorithm.calculateCameraBottomMargin();
        indicatorGardenModel.maxWidthCenterContainer = indicatorGardenAlgorithm.calculateCenterContainerMaxWidth();
        indicatorGardenModel.maxWidthLeftContainer = indicatorGardenAlgorithm.calculateLeftContainerMaxWidth(indicatorGarden);
        indicatorGardenModel.maxWidthRightContainer = indicatorGardenAlgorithm.calculateRightContainerMaxWidth(indicatorGarden);
        if (!indicatorGardenModel.isEqual(this.cachedGardenModel)) {
            ArrayList arrayList = (ArrayList) this.listeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                GardenListener gardenListener = (GardenListener) obj;
                if (((ArrayList) this.listeners).contains(gardenListener)) {
                    gardenListener.onGardenChanged(indicatorGardenModel);
                }
            }
            this.cachedGardenModel = indicatorGardenModel;
        }
        indicatorGarden.updateGarden(indicatorGardenModel, this.inputProperties);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(GardenListener gardenListener) {
        synchronized (this.listeners) {
            ((ArrayList) this.listeners).add(gardenListener);
        }
        gardenListener.onGardenChanged(this.cachedGardenModel);
    }
}
