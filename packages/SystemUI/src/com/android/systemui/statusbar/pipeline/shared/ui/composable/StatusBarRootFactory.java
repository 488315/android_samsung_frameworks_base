package com.android.systemui.statusbar.pipeline.shared.ui.composable;

import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.statusbar.data.repository.DarkIconDispatcherStore;
import com.android.systemui.statusbar.events.domain.interactor.SystemStatusEventAnimationInteractor;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.ConnectedDisplaysStatusBarNotificationIconViewStore;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerStatusBarViewBinder;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ui.DarkIconManager;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.HomeStatusBarViewBinder;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarRootFactory {
    public final DarkIconManager.Factory darkIconManagerFactory;
    public final SystemStatusEventAnimationInteractor eventAnimationInteractor;
    public final HomeStatusBarViewBinder homeStatusBarViewBinder;
    public final HomeStatusBarViewModel.HomeStatusBarViewModelFactory homeStatusBarViewModelFactory;
    public final StatusBarIconController iconController;
    public final ConnectedDisplaysStatusBarNotificationIconViewStore.Factory iconViewStoreFactory;
    public final MediaHierarchyManager mediaHierarchyManager;
    public final MediaHost mediaHost;
    public final NotificationIconContainerStatusBarViewBinder notificationIconsBinder;
    public final OngoingCallController ongoingCallController;

    public StatusBarRootFactory(HomeStatusBarViewModel.HomeStatusBarViewModelFactory homeStatusBarViewModelFactory, HomeStatusBarViewBinder homeStatusBarViewBinder, NotificationIconContainerStatusBarViewBinder notificationIconContainerStatusBarViewBinder, ConnectedDisplaysStatusBarNotificationIconViewStore.Factory factory, DarkIconManager.Factory factory2, StatusBarIconController statusBarIconController, OngoingCallController ongoingCallController, DarkIconDispatcherStore darkIconDispatcherStore, SystemStatusEventAnimationInteractor systemStatusEventAnimationInteractor, MediaHierarchyManager mediaHierarchyManager, MediaHost mediaHost) {
        this.homeStatusBarViewModelFactory = homeStatusBarViewModelFactory;
        this.homeStatusBarViewBinder = homeStatusBarViewBinder;
        this.notificationIconsBinder = notificationIconContainerStatusBarViewBinder;
        this.iconViewStoreFactory = factory;
        this.darkIconManagerFactory = factory2;
        this.iconController = statusBarIconController;
        this.ongoingCallController = ongoingCallController;
        this.eventAnimationInteractor = systemStatusEventAnimationInteractor;
        this.mediaHierarchyManager = mediaHierarchyManager;
        this.mediaHost = mediaHost;
    }
}
