package com.android.systemui.qs.tiles.impl.internet.domain.interactor;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileIconModel;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InternetTileDataInteractor implements QSTileDataInteractor {
    public static final Companion Companion = new Companion(null);
    public static final InternetTileModel.Inactive NOT_CONNECTED_NETWORKS_UNAVAILABLE = new InternetTileModel.Inactive(null, new Text.Resource(R.string.quick_settings_networks_unavailable), new InternetTileIconModel.ResourceId(R.drawable.ic_qs_no_internet_unavailable), null, new ContentDescription.Resource(R.string.quick_settings_networks_unavailable), 1, null);
    public final ConnectivityRepository connectivityRepository;
    public final Context context;
    public final ChannelFlowTransformLatest ethernetIconFlow;
    public final String internetLabel;
    public final ChannelFlowTransformLatest mobileDataContentName;
    public final ChannelFlowTransformLatest mobileIconFlow;
    public final ReadonlyStateFlow notConnectedFlow;
    public final ChannelFlowTransformLatest wifiIconFlow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public InternetTileDataInteractor(Context context, CoroutineScope coroutineScope, AirplaneModeRepository airplaneModeRepository, ConnectivityRepository connectivityRepository, EthernetInteractor ethernetInteractor, MobileIconsInteractor mobileIconsInteractor, WifiInteractor wifiInteractor) {
        this.context = context;
        this.connectivityRepository = connectivityRepository;
        this.internetLabel = context.getString(R.string.quick_settings_internet_label);
        WifiInteractorImpl wifiInteractorImpl = (WifiInteractorImpl) wifiInteractor;
        this.wifiIconFlow = FlowKt.transformLatest(wifiInteractorImpl.wifiNetwork, new InternetTileDataInteractor$special$$inlined$flatMapLatest$1(null, this));
        this.mobileDataContentName = FlowKt.transformLatest(mobileIconsInteractor.getActiveDataIconInteractor(), new InternetTileDataInteractor$special$$inlined$flatMapLatest$2(null, this));
        this.mobileIconFlow = FlowKt.transformLatest(mobileIconsInteractor.getActiveDataIconInteractor(), new InternetTileDataInteractor$special$$inlined$flatMapLatest$3(null, this));
        this.ethernetIconFlow = FlowKt.transformLatest(ethernetInteractor.icon, new InternetTileDataInteractor$special$$inlined$flatMapLatest$4(null, this));
        this.notConnectedFlow = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiInteractorImpl.areNetworksAvailable, ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode, new InternetTileDataInteractor$notConnectedFlow$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), NOT_CONNECTED_NETWORKS_UNAVAILABLE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.transformLatest(((ConnectivityRepositoryImpl) this.connectivityRepository).defaultConnections, new InternetTileDataInteractor$tileData$$inlined$flatMapLatest$1(null, this));
    }
}
