package androidx.appcompat.app;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.util.Consumer;
import com.android.systemui.R;

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
