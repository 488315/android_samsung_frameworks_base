package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.systemui.R;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryKairosImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryKairosImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileConnectionRepositoryKairosFactoryImpl implements MobileConnectionsRepositoryKairosImpl.ConnectionRepoFactory {
    public static final Companion Companion = new Companion(null);
    public final CarrierConfigRepository carrierConfigRepo;
    public final MobileConnectionsRepositoryKairosImpl connectionsRepo;
    public final NetworkNameModel.Default defaultNetworkName;
    public final TableLogBufferFactory logFactory;
    public final CarrierMergedConnectionRepositoryKairos.Factory mergedRepoFactory;
    public final MobileConnectionRepositoryKairosImpl.Factory mobileRepoFactory;
    public final String networkNameSeparator;
    public final TelephonyManager telephonyManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MobileConnectionRepositoryKairosFactoryImpl(Context context, MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, TableLogBufferFactory tableLogBufferFactory, CarrierConfigRepository carrierConfigRepository, TelephonyManager telephonyManager, MobileConnectionRepositoryKairosImpl.Factory factory, CarrierMergedConnectionRepositoryKairos.Factory factory2) {
        this.connectionsRepo = mobileConnectionsRepositoryKairosImpl;
        this.logFactory = tableLogBufferFactory;
        this.carrierConfigRepo = carrierConfigRepository;
        this.telephonyManager = telephonyManager;
        this.mobileRepoFactory = factory;
        this.mergedRepoFactory = factory2;
        this.networkNameSeparator = context.getString(R.string.status_bar_network_name_separator);
        this.defaultNetworkName = new NetworkNameModel.Default(context.getString(android.R.string.permlab_accessHiddenProfile));
    }
}
