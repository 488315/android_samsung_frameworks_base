package com.android.server.wm;

import android.view.DisplayInfo;

import com.android.server.wm.utils.DisplayInfoOverrides$DisplayInfoFieldsUpdater;

public final /* synthetic */ class DeferredDisplayUpdater$$ExternalSyntheticLambda0
        implements DisplayInfoOverrides$DisplayInfoFieldsUpdater {
    @Override // com.android.server.wm.utils.DisplayInfoOverrides$DisplayInfoFieldsUpdater
    public final void setFields(DisplayInfo displayInfo, DisplayInfo displayInfo2) {
        displayInfo.uniqueId = displayInfo2.uniqueId;
        displayInfo.address = displayInfo2.address;
        displayInfo.appWidth = displayInfo2.appWidth;
        displayInfo.appHeight = displayInfo2.appHeight;
        displayInfo.smallestNominalAppWidth = displayInfo2.smallestNominalAppWidth;
        displayInfo.smallestNominalAppHeight = displayInfo2.smallestNominalAppHeight;
        displayInfo.largestNominalAppWidth = displayInfo2.largestNominalAppWidth;
        displayInfo.largestNominalAppHeight = displayInfo2.largestNominalAppHeight;
        displayInfo.logicalWidth = displayInfo2.logicalWidth;
        displayInfo.logicalHeight = displayInfo2.logicalHeight;
        displayInfo.physicalXDpi = displayInfo2.physicalXDpi;
        displayInfo.physicalYDpi = displayInfo2.physicalYDpi;
        displayInfo.rotation = displayInfo2.rotation;
        displayInfo.displayCutout = displayInfo2.displayCutout;
        displayInfo.logicalDensityDpi = displayInfo2.logicalDensityDpi;
        displayInfo.roundedCorners = displayInfo2.roundedCorners;
        displayInfo.displayShape = displayInfo2.displayShape;
    }
}
