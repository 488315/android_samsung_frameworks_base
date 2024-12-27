package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final /* synthetic */ class SecControlsProviderSelectorActivity$onCreate$9 extends FunctionReferenceImpl implements Function1 {
    public SecControlsProviderSelectorActivity$onCreate$9(Object obj) {
        super(1, obj, SecControlsProviderSelectorActivity.class, "launchFavoritingActivity", "launchFavoritingActivity(Landroid/content/ComponentName;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        final ComponentName componentName = (ComponentName) obj;
        final SecControlsProviderSelectorActivity secControlsProviderSelectorActivity = (SecControlsProviderSelectorActivity) this.receiver;
        secControlsProviderSelectorActivity.executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.SecControlsProviderSelectorActivity$launchFavoritingActivity$1
            @Override // java.lang.Runnable
            public final void run() {
                ComponentName componentName2 = componentName;
                if (componentName2 != null) {
                    SecControlsProviderSelectorActivity secControlsProviderSelectorActivity2 = secControlsProviderSelectorActivity;
                    Intent intent = new Intent(secControlsProviderSelectorActivity2.getApplicationContext(), (Class<?>) SecControlsFavoritingActivity.class);
                    intent.putExtra("extra_app_label", ((ControlsListingControllerImpl) secControlsProviderSelectorActivity2.listingController).getAppLabel(componentName2));
                    intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName2);
                    secControlsProviderSelectorActivity2.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(secControlsProviderSelectorActivity2, new Pair[0]).toBundle());
                    ((BadgeProviderImpl) secControlsProviderSelectorActivity2.badgeProvider).dismiss();
                }
            }
        });
        return Unit.INSTANCE;
    }
}
