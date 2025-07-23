package com.android.systemui.decor;

import android.content.res.Resources;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CutoutDecorProviderFactory implements DecorProviderFactory {
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
            ArrayList arrayList2 = (ArrayList) FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout);
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                arrayList.add(new CutoutDecorProviderImpl(FaceScanningProviderFactoryKt.baseOnRotation0(((Number) obj).intValue(), this.displayInfo.rotation), this.isCameraProtectionEnabled, this.cameraProtectionStrokeWidth));
            }
        } else if (this.shouldFillUDCDisplayCutout) {
            arrayList.add(new CutoutDecorProviderImpl(1, this.isCameraProtectionEnabled, this.cameraProtectionStrokeWidth));
        }
        return arrayList;
    }
}
