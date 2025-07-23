package com.android.systemui.shade.ui.viewmodel;

import android.view.ViewGroup;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class ShadeHeaderViewModel$createTintedIconManager$1 extends FunctionReferenceImpl implements Function2 {
    public ShadeHeaderViewModel$createTintedIconManager$1(Object obj) {
        super(2, obj, TintedIconManager.Factory.class, "create", "create(Landroid/view/ViewGroup;Lcom/android/systemui/statusbar/phone/StatusBarLocation;)Lcom/android/systemui/statusbar/phone/ui/TintedIconManager;", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TintedIconManager.Factory) this.receiver).create((ViewGroup) obj, (StatusBarLocation) obj2);
    }
}
