package com.android.systemui.shade.ui.viewmodel;

import android.view.View;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class ShadeHeaderViewModel$createBatteryMeterViewController$1 extends FunctionReferenceImpl implements Function2 {
    public ShadeHeaderViewModel$createBatteryMeterViewController$1(Object obj) {
        super(2, obj, BatteryMeterViewController.Factory.class, "create", "create(Landroid/view/View;Lcom/android/systemui/statusbar/phone/StatusBarLocation;)Lcom/android/systemui/battery/BatteryMeterViewController;", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BatteryMeterViewController.Factory) this.receiver).create((View) obj, (StatusBarLocation) obj2);
    }
}
