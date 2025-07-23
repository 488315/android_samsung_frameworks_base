package com.android.systemui.complication;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ComplicationHostViewController extends ViewController {
    public static final boolean DEBUG = Log.isLoggable("ComplicationHostVwCtrl", 3);
    public final ComplicationCollectionViewModel mComplicationCollectionViewModel;
    public final AnonymousClass1 mComplicationViewModelObserver;
    public final HashMap mComplications;
    public final DreamOverlayStateController mDreamOverlayStateController;
    boolean mIsAnimationEnabled;
    public final ComplicationLayoutEngine mLayoutEngine;
    public final LifecycleOwner mLifecycleOwner;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.complication.ComplicationHostViewController$1] */
    public ComplicationHostViewController(ConstraintLayout constraintLayout, ComplicationLayoutEngine complicationLayoutEngine, DreamOverlayStateController dreamOverlayStateController, LifecycleOwner lifecycleOwner, ComplicationCollectionViewModel complicationCollectionViewModel, SecureSettings secureSettings, ConfigurationInteractor configurationInteractor, CoroutineDispatcher coroutineDispatcher) {
        super(constraintLayout);
        this.mComplications = new HashMap();
        this.mComplicationViewModelObserver = new Observer() { // from class: com.android.systemui.complication.ComplicationHostViewController.1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                final int i = 1;
                final int i2 = 0;
                Collection collection = (Collection) obj;
                boolean z = ComplicationHostViewController.DEBUG;
                final ComplicationHostViewController complicationHostViewController = ComplicationHostViewController.this;
                complicationHostViewController.getClass();
                if (ComplicationHostViewController.DEBUG) {
                    Log.d("ComplicationHostVwCtrl", "updateComplications called. Callers = " + Debug.getCallers(25));
                    Log.d("ComplicationHostVwCtrl", "    mComplications = " + complicationHostViewController.mComplications.toString());
                    Log.d("ComplicationHostVwCtrl", "    complications = " + collection.toString());
                }
                final Collection collection2 = (Collection) collection.stream().map(new ComplicationHostViewController$$ExternalSyntheticLambda1()).collect(Collectors.toSet());
                ((Collection) complicationHostViewController.mComplications.keySet().stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        int i3 = i2;
                        Object obj3 = collection2;
                        switch (i3) {
                            case 0:
                                boolean z2 = ComplicationHostViewController.DEBUG;
                                return !((Collection) obj3).contains((ComplicationId) obj2);
                            default:
                                return !((ComplicationHostViewController) obj3).mComplications.containsKey(((ComplicationViewModel) obj2).mId);
                        }
                    }
                }).collect(Collectors.toSet())).forEach(new ComplicationHostViewController$$ExternalSyntheticLambda5(complicationHostViewController, i));
                ((Collection) collection.stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        int i3 = i;
                        Object obj3 = complicationHostViewController;
                        switch (i3) {
                            case 0:
                                boolean z2 = ComplicationHostViewController.DEBUG;
                                return !((Collection) obj3).contains((ComplicationId) obj2);
                            default:
                                return !((ComplicationHostViewController) obj3).mComplications.containsKey(((ComplicationViewModel) obj2).mId);
                        }
                    }
                }).collect(Collectors.toSet())).forEach(new ComplicationHostViewController$$ExternalSyntheticLambda5(complicationHostViewController, i2));
            }
        };
        this.mLayoutEngine = complicationLayoutEngine;
        this.mLifecycleOwner = lifecycleOwner;
        this.mComplicationCollectionViewModel = complicationCollectionViewModel;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mIsAnimationEnabled = secureSettings.getFloatForUser(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f, -2) != 0.0f;
        Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).maxBounds;
        Objects.requireNonNull(complicationLayoutEngine);
        JavaAdapterKt.collectFlow(constraintLayout, flow, new ComplicationHostViewController$$ExternalSyntheticLambda5(complicationLayoutEngine, 2), coroutineDispatcher);
    }

    public final View getView() {
        return this.mView;
    }

    public final List getViewsAtPosition(final int i) {
        final int i2 = 0;
        Stream flatMap = this.mLayoutEngine.mPositions.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i;
                return (((Integer) ((Map.Entry) obj).getKey()).intValue() & i3) == i3;
            }
        }).flatMap(new Function() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i2) {
                    case 0:
                        ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) ((Map.Entry) obj).getValue();
                        positionGroup.getClass();
                        ArrayList arrayList = new ArrayList();
                        Iterator it = positionGroup.mDirectionGroups.values().iterator();
                        while (it.hasNext()) {
                            arrayList.addAll(((ComplicationLayoutEngine.DirectionGroup) it.next()).mViews);
                        }
                        return arrayList.stream();
                    default:
                        return ((ComplicationLayoutEngine.ViewEntry) obj).mView;
                }
            }
        });
        final int i3 = 1;
        return (List) flatMap.map(new Function() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                switch (i3) {
                    case 0:
                        ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) ((Map.Entry) obj).getValue();
                        positionGroup.getClass();
                        ArrayList arrayList = new ArrayList();
                        Iterator it = positionGroup.mDirectionGroups.values().iterator();
                        while (it.hasNext()) {
                            arrayList.addAll(((ComplicationLayoutEngine.DirectionGroup) it.next()).mViews);
                        }
                        return arrayList.stream();
                    default:
                        return ((ComplicationLayoutEngine.ViewEntry) obj).mView;
                }
            }
        }).collect(Collectors.toList());
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mComplicationCollectionViewModel.mComplications.observe(this.mLifecycleOwner, this.mComplicationViewModelObserver);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mComplicationCollectionViewModel.mComplications.removeObserver(this.mComplicationViewModelObserver);
    }
}
