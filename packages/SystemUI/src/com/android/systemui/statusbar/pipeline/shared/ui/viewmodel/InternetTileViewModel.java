package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class InternetTileViewModel {
    public static final Companion Companion = new Companion(null);
    public static final InternetTileModel.Inactive NOT_CONNECTED_NETWORKS_UNAVAILABLE = new InternetTileModel.Inactive(null, new Text.Resource(R.string.quick_settings_networks_unavailable), Integer.valueOf(R.drawable.ic_qs_no_internet_unavailable), null, null, new ContentDescription.Resource(R.string.quick_settings_networks_unavailable), 9, null);
    public final ChannelFlowTransformLatest activeModelProvider;
    public final Context context;
    public final ChannelFlowTransformLatest ethernetIconFlow;
    public final String internetLabel;
    public final ChannelFlowTransformLatest mobileDataContentName;
    public final ChannelFlowTransformLatest mobileIconFlow;
    public final ReadonlyStateFlow notConnectedFlow;
    public final ReadonlyStateFlow tileModel;
    public final ChannelFlowTransformLatest wifiIconFlow;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public InternetTileViewModel(AirplaneModeRepository airplaneModeRepository, ConnectivityRepository connectivityRepository, EthernetInteractor ethernetInteractor, MobileIconsInteractor mobileIconsInteractor, WifiInteractor wifiInteractor, Context context, CoroutineScope coroutineScope) {
        this.context = context;
        this.internetLabel = context.getString(R.string.quick_settings_internet_label);
        WifiInteractorImpl wifiInteractorImpl = (WifiInteractorImpl) wifiInteractor;
        this.wifiIconFlow = FlowKt.transformLatest(wifiInteractorImpl.wifiNetwork, new InternetTileViewModel$special$$inlined$flatMapLatest$1(null, this));
        ReadonlyStateFlow readonlyStateFlow = ((MobileIconsInteractorImpl) mobileIconsInteractor).activeDataIconInteractor;
        this.mobileDataContentName = FlowKt.transformLatest(readonlyStateFlow, new InternetTileViewModel$special$$inlined$flatMapLatest$2(null, this));
        this.mobileIconFlow = FlowKt.transformLatest(readonlyStateFlow, new InternetTileViewModel$special$$inlined$flatMapLatest$3(null, this));
        this.ethernetIconFlow = FlowKt.transformLatest(ethernetInteractor.icon, new InternetTileViewModel$special$$inlined$flatMapLatest$4(null, this));
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiInteractorImpl.areNetworksAvailable, ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode, new InternetTileViewModel$notConnectedFlow$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), NOT_CONNECTED_NETWORKS_UNAVAILABLE);
        this.notConnectedFlow = stateIn;
        this.tileModel = FlowKt.stateIn(FlowKt.transformLatest(((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections, new InternetTileViewModel$special$$inlined$flatMapLatest$5(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateIn.$$delegate_0.getValue());
    }
}
