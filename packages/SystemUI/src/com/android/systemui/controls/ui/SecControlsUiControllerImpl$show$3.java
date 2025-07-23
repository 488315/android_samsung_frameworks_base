package com.android.systemui.controls.ui;

import android.util.Log;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.controls.ui.fragment.NoAppFragment;
import com.android.systemui.controls.ui.fragment.NoFavoriteFragment;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.util.SALogger;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecControlsUiControllerImpl$show$3 extends FunctionReferenceImpl implements Function2 {
    public SecControlsUiControllerImpl$show$3(Object obj) {
        super(2, obj, SecControlsUiControllerImpl.class, "showNonMainView", "showNonMainView(Ljava/util/List;Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        List list = (List) obj2;
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.receiver;
        int i = SecControlsUiControllerImpl.$r8$clinit;
        secControlsUiControllerImpl.getClass();
        Log.d("SecControlsUiControllerImpl", "showNonMainView");
        secControlsUiControllerImpl.serviceInfos = list;
        boolean isEmpty = list.isEmpty();
        SALogger sALogger = secControlsUiControllerImpl.saLogger;
        if (isEmpty) {
            Log.d("SecControlsUiControllerImpl", "showNoAppView");
            String name = NoAppFragment.class.getName();
            if (secControlsUiControllerImpl.noAppFragment == null) {
                FragmentManagerImpl fragmentManagerImpl = secControlsUiControllerImpl.fragmentManager;
                Fragment findFragmentByTag = fragmentManagerImpl != null ? fragmentManagerImpl.findFragmentByTag(name) : null;
                NoAppFragment noAppFragment = findFragmentByTag instanceof NoAppFragment ? (NoAppFragment) findFragmentByTag : null;
                if (noAppFragment == null) {
                    noAppFragment = new NoAppFragment(sALogger);
                }
                secControlsUiControllerImpl.noAppFragment = noAppFragment;
            }
            FragmentManagerImpl fragmentManagerImpl2 = secControlsUiControllerImpl.fragmentManager;
            if (fragmentManagerImpl2 != null) {
                BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl2);
                NoAppFragment noAppFragment2 = secControlsUiControllerImpl.noAppFragment;
                noAppFragment2.getClass();
                backStackRecord.replace(R.id.frame_layout, noAppFragment2, name);
                backStackRecord.commitInternal(true, true);
            }
        } else {
            Log.d("SecControlsUiControllerImpl", "showNoFavoriteView");
            String name2 = NoFavoriteFragment.class.getName();
            if (secControlsUiControllerImpl.noFavoriteFragment == null) {
                FragmentManagerImpl fragmentManagerImpl3 = secControlsUiControllerImpl.fragmentManager;
                LifecycleOwner findFragmentByTag2 = fragmentManagerImpl3 != null ? fragmentManagerImpl3.findFragmentByTag(name2) : null;
                NoFavoriteFragment noFavoriteFragment = findFragmentByTag2 instanceof NoFavoriteFragment ? (NoFavoriteFragment) findFragmentByTag2 : null;
                if (noFavoriteFragment == null) {
                    noFavoriteFragment = new NoFavoriteFragment((ControlsActivityStarter) secControlsUiControllerImpl.controlsActivityStarter.get(), sALogger, secControlsUiControllerImpl.badgeSubject);
                }
                secControlsUiControllerImpl.noFavoriteFragment = noFavoriteFragment;
            }
            FragmentManagerImpl fragmentManagerImpl4 = secControlsUiControllerImpl.fragmentManager;
            if (fragmentManagerImpl4 != null) {
                BackStackRecord backStackRecord2 = new BackStackRecord(fragmentManagerImpl4);
                NoFavoriteFragment noFavoriteFragment2 = secControlsUiControllerImpl.noFavoriteFragment;
                noFavoriteFragment2.getClass();
                backStackRecord2.replace(R.id.frame_layout, noFavoriteFragment2, name2);
                backStackRecord2.commitInternal(true, true);
            }
        }
        return Unit.INSTANCE;
    }
}
