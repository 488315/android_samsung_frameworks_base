package com.android.systemui.decor;

import android.content.res.Resources;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;

public final class CutoutDecorProviderFactory extends DecorProviderFactory {
    public int cameraProtectionStrokeWidth;
    public final Display display;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public boolean isCameraProtectionEnabled;
    public boolean isCameraProtectionVisible;
    public final Resources res;
    public boolean shouldFillUDCDisplayCutout;

    public CutoutDecorProviderFactory(Resources resources, Display display) {
        this.res = resources;
        this.display = display;
        this.isCameraProtectionEnabled = resources.getBoolean(R.bool.config_enableDisplayCutoutProtection);
        this.cameraProtectionStrokeWidth = resources.getDimensionPixelSize(R.dimen.camera_protection_stroke_width);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        Display display = this.display;
        if (display != null) {
            display.getDisplayInfo(this.displayInfo);
        } else {
            Log.w("CutoutDecorProviderFactory", "display is null, can't update displayInfo");
        }
        return DisplayCutout.getFillBuiltInDisplayCutout(this.res, this.displayInfo.uniqueId) || this.isCameraProtectionVisible || this.shouldFillUDCDisplayCutout;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        if (!getHasProviders()) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            Iterator it = ((ArrayList) FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout)).iterator();
            while (it.hasNext()) {
                arrayList.add(new CutoutDecorProviderImpl(FaceScanningProviderFactoryKt.baseOnRotation0(((Number) it.next()).intValue(), this.displayInfo.rotation), this.isCameraProtectionEnabled, this.cameraProtectionStrokeWidth));
            }
        } else if (this.shouldFillUDCDisplayCutout) {
            arrayList.add(new CutoutDecorProviderImpl(1, this.isCameraProtectionEnabled, this.cameraProtectionStrokeWidth));
        }
        return arrayList;
    }
}
