package com.android.systemui.controls.p005ui;

import android.util.Log;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import com.android.systemui.controls.p005ui.fragment.NoAppFragment;
import com.android.systemui.controls.p005ui.fragment.NoFavoriteFragment;
import com.android.systemui.controls.p005ui.util.SALogger;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsUiControllerImpl$show$3 extends FunctionReferenceImpl implements Function2 {
    public CustomControlsUiControllerImpl$show$3(Object obj) {
        super(2, obj, CustomControlsUiControllerImpl.class, "showNonMainView", "showNonMainView(Ljava/util/List;Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        List list = (List) obj2;
        CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) this.receiver;
        int i = CustomControlsUiControllerImpl.$r8$clinit;
        customControlsUiControllerImpl.getClass();
        Log.d("CustomControlsUiControllerImpl", "showNonMainView");
        customControlsUiControllerImpl.serviceInfos = list;
        boolean isEmpty = list.isEmpty();
        SALogger sALogger = customControlsUiControllerImpl.saLogger;
        if (isEmpty) {
            Log.d("CustomControlsUiControllerImpl", "showNoAppView()");
            String name = NoAppFragment.class.getName();
            if (customControlsUiControllerImpl.noAppFragment == null) {
                FragmentManager fragmentManager = customControlsUiControllerImpl.fragmentManager;
                Fragment findFragmentByTag = fragmentManager != null ? fragmentManager.findFragmentByTag(name) : null;
                NoAppFragment noAppFragment = findFragmentByTag instanceof NoAppFragment ? (NoAppFragment) findFragmentByTag : null;
                if (noAppFragment == null) {
                    noAppFragment = new NoAppFragment(sALogger);
                }
                customControlsUiControllerImpl.noAppFragment = noAppFragment;
            }
            FragmentManager fragmentManager2 = customControlsUiControllerImpl.fragmentManager;
            if (fragmentManager2 != null) {
                BackStackRecord backStackRecord = new BackStackRecord(fragmentManager2);
                NoAppFragment noAppFragment2 = customControlsUiControllerImpl.noAppFragment;
                Intrinsics.checkNotNull(noAppFragment2);
                backStackRecord.replace(R.id.frame_layout, noAppFragment2, name);
                backStackRecord.commitAllowingStateLoss();
            }
        } else {
            Log.d("CustomControlsUiControllerImpl", "showNoFavoriteView()");
            String name2 = NoFavoriteFragment.class.getName();
            if (customControlsUiControllerImpl.noFavoriteFragment == null) {
                FragmentManager fragmentManager3 = customControlsUiControllerImpl.fragmentManager;
                LifecycleOwner findFragmentByTag2 = fragmentManager3 != null ? fragmentManager3.findFragmentByTag(name2) : null;
                NoFavoriteFragment noFavoriteFragment = findFragmentByTag2 instanceof NoFavoriteFragment ? (NoFavoriteFragment) findFragmentByTag2 : null;
                if (noFavoriteFragment == null) {
                    noFavoriteFragment = new NoFavoriteFragment(customControlsUiControllerImpl.controlsActivityStarter, sALogger, customControlsUiControllerImpl.badgeSubject);
                }
                customControlsUiControllerImpl.noFavoriteFragment = noFavoriteFragment;
            }
            FragmentManager fragmentManager4 = customControlsUiControllerImpl.fragmentManager;
            if (fragmentManager4 != null) {
                BackStackRecord backStackRecord2 = new BackStackRecord(fragmentManager4);
                NoFavoriteFragment noFavoriteFragment2 = customControlsUiControllerImpl.noFavoriteFragment;
                Intrinsics.checkNotNull(noFavoriteFragment2);
                backStackRecord2.replace(R.id.frame_layout, noFavoriteFragment2, name2);
                backStackRecord2.commitAllowingStateLoss();
            }
        }
        return Unit.INSTANCE;
    }
}
