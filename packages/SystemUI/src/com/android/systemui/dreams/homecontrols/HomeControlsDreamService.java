package com.android.systemui.dreams.homecontrols;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.service.dreams.DreamService;
import android.window.TaskFragmentCreationParams;
import android.window.TaskFragmentOperation;
import android.window.TaskFragmentOrganizerToken;
import android.window.WindowContainerTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ServiceLifecycleDispatcher;
import com.android.systemui.dreams.homecontrols.HomeControlsDreamServiceImpl;
import com.android.systemui.dreams.homecontrols.service.TaskFragmentComponent;
import com.android.systemui.util.wakelock.WakeLock;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
public final class HomeControlsDreamService extends DreamService implements LifecycleOwner {
    public final HomeControlsDreamServiceImpl.Factory factory;
    public final ServiceLifecycleDispatcher dispatcher = new ServiceLifecycleDispatcher(this);
    public final Lazy impl$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.dreams.homecontrols.HomeControlsDreamService$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            HomeControlsDreamService homeControlsDreamService = this.f$0;
            return homeControlsDreamService.factory.create(homeControlsDreamService, homeControlsDreamService);
        }
    });

    public HomeControlsDreamService(HomeControlsDreamServiceImpl.Factory factory) {
        this.factory = factory;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.dispatcher.registry;
    }

    @Override // android.service.dreams.DreamService, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        final HomeControlsDreamServiceImpl homeControlsDreamServiceImpl = (HomeControlsDreamServiceImpl) this.impl$delegate.getValue();
        Activity activity = homeControlsDreamServiceImpl.service.getActivity();
        if (activity == null) {
            homeControlsDreamServiceImpl.service.finish();
            return;
        }
        TaskFragmentComponent taskFragmentComponentCreate = homeControlsDreamServiceImpl.taskFragmentFactory.create(activity, new Function1() { // from class: com.android.systemui.dreams.homecontrols.HomeControlsDreamServiceImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                HomeControlsDreamServiceImpl.Companion companion = HomeControlsDreamServiceImpl.Companion;
                HomeControlsDreamServiceImpl homeControlsDreamServiceImpl2 = homeControlsDreamServiceImpl;
                BuildersKt.launch$default(LifecycleKt.getCoroutineScope(homeControlsDreamServiceImpl2.$$delegate_0.getLifecycle()), null, null, new HomeControlsDreamServiceImpl$launchActivity$1(homeControlsDreamServiceImpl2, null), 3);
                return Unit.INSTANCE;
            }
        }, new HomeControlsDreamServiceImpl$onAttachedToWindow$2(homeControlsDreamServiceImpl), new HomeControlsDreamServiceImpl$$ExternalSyntheticLambda1(homeControlsDreamServiceImpl, 0));
        TaskFragmentComponent.Organizer organizer = taskFragmentComponentCreate.organizer;
        TaskFragmentOrganizerToken organizerToken = organizer.getOrganizerToken();
        Binder binder = taskFragmentComponentCreate.fragmentToken;
        IBinder activityToken = taskFragmentComponentCreate.activity.getActivityToken();
        activityToken.getClass();
        organizer.applyTransaction(new WindowContainerTransaction().createTaskFragment(new TaskFragmentCreationParams.Builder(organizerToken, binder, activityToken).setInitialRelativeBounds(new Rect()).setWindowingMode(1).build()), 6, false);
        homeControlsDreamServiceImpl.taskFragmentComponent = taskFragmentComponentCreate;
        ((WakeLock) homeControlsDreamServiceImpl.wakeLock$delegate.getValue()).acquire("HomeControlsDreamServiceImpl");
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public final void onCreate() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_CREATE);
        super.onCreate();
    }

    @Override // android.service.dreams.DreamService, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_STOP);
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
        super.onDetachedFromWindow();
        HomeControlsDreamServiceImpl homeControlsDreamServiceImpl = (HomeControlsDreamServiceImpl) this.impl$delegate.getValue();
        ((WakeLock) homeControlsDreamServiceImpl.wakeLock$delegate.getValue()).release("HomeControlsDreamServiceImpl");
        TaskFragmentComponent taskFragmentComponent = homeControlsDreamServiceImpl.taskFragmentComponent;
        if (taskFragmentComponent == null) {
            taskFragmentComponent = null;
        }
        taskFragmentComponent.organizer.applyTransaction(new WindowContainerTransaction().addTaskFragmentOperation(taskFragmentComponent.fragmentToken, new TaskFragmentOperation.Builder(1).build()), 2, false);
        taskFragmentComponent.organizer.unregisterOrganizer();
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStarted() {
        ServiceLifecycleDispatcher serviceLifecycleDispatcher = this.dispatcher;
        serviceLifecycleDispatcher.getClass();
        serviceLifecycleDispatcher.postDispatchRunnable(Lifecycle.Event.ON_START);
        super.onDreamingStarted();
    }
}
