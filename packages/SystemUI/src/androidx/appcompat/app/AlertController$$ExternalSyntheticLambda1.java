package androidx.appcompat.app;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.util.Consumer;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AlertController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ AlertController f$0;

    public /* synthetic */ AlertController$$ExternalSyntheticLambda1(AlertController alertController) {
        this.f$0 = alertController;
    }

    @Override // androidx.core.util.Consumer
    public final void accept(Object obj) {
        LinearLayout linearLayout;
        ViewGroup viewGroup = (ViewGroup) obj;
        this.f$0.getClass();
        if (viewGroup == null || (linearLayout = (LinearLayout) viewGroup.findViewById(R.id.buttonBarLayout)) == null) {
            return;
        }
        linearLayout.post(new AlertController$$ExternalSyntheticLambda2(linearLayout));
    }
}
