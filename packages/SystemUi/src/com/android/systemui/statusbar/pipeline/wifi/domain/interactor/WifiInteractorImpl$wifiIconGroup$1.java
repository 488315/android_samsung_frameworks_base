package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.statusbar.connectivity.WifiIcons;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.WifiSignalIconResource;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.WifiSignalIconResourceKt;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$wifiIconGroup$1", m277f = "WifiInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiInteractorImpl$wifiIconGroup$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ WifiSignalIconResource $wifiSignalIconResource;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiInteractorImpl$wifiIconGroup$1(WifiSignalIconResource wifiSignalIconResource, Continuation<? super WifiInteractorImpl$wifiIconGroup$1> continuation) {
        super(3, continuation);
        this.$wifiSignalIconResource = wifiSignalIconResource;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        WifiInteractorImpl$wifiIconGroup$1 wifiInteractorImpl$wifiIconGroup$1 = new WifiInteractorImpl$wifiIconGroup$1(this.$wifiSignalIconResource, (Continuation) obj3);
        wifiInteractorImpl$wifiIconGroup$1.L$0 = (WifiNetworkModel) obj;
        wifiInteractorImpl$wifiIconGroup$1.Z$0 = booleanValue;
        return wifiInteractorImpl$wifiIconGroup$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x013d, code lost:
    
        if (r0.enterpriseConfig.getPhase2Method() != 0) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0145, code lost:
    
        if (r0.enterpriseConfig.getEapMethod() != 4) goto L84;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        Object obj2;
        SignalIcon$IconGroup signalIcon$IconGroup;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$0;
        boolean z2 = this.Z$0;
        if (!(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
            return null;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("get wifiIconGroup ePDGConnected : ", z2, "WifiInteractor");
        WifiSignalIconResource wifiSignalIconResource = this.$wifiSignalIconResource;
        WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
        wifiSignalIconResource.getClass();
        SignalIcon$IconGroup signalIcon$IconGroup2 = WifiIcons.UNMERGED_WIFI;
        int i = active.wifiStandard;
        CarrierInfraMediator carrierInfraMediator = wifiSignalIconResource.carrierInfraMediator;
        if (i == 8) {
            SamsungWifiIcons.Companion.getClass();
            signalIcon$IconGroup2 = SamsungWifiIcons.WIFI_ICON_7G;
        } else {
            boolean z3 = true;
            if (i == 6) {
                int i2 = active.frequency;
                if (i2 != 5935 && (5955 > i2 || i2 >= 7116)) {
                    z3 = false;
                }
                if (z3) {
                    SamsungWifiIcons.Companion.getClass();
                    signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_6GE;
                } else {
                    SamsungWifiIcons.Companion.getClass();
                    signalIcon$IconGroup = SamsungWifiIcons.WIFI_ICON_6G;
                }
                signalIcon$IconGroup2 = signalIcon$IconGroup;
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_WIFI_CALLING_ICON, 0, new Object[0]) && z2) {
                SamsungWifiIcons.Companion.getClass();
                signalIcon$IconGroup2 = SamsungWifiIcons.WIFI_ICON_WIFI_CALLING;
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_KT, 0, new Object[0]) && active.gigaAp && (Integer.valueOf(active.receivedInetCondition).equals(1) || (Integer.valueOf(active.receivedInetCondition).equals(-1) && active.isValidated))) {
                SamsungWifiIcons.Companion.getClass();
                signalIcon$IconGroup2 = SamsungWifiIcons.WIFI_ICON_KT_GIGA;
            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LGT, 0, new Object[0])) {
                String[] strArr = WifiSignalIconResourceKt.LGT_SSIDs;
                int i3 = 0;
                while (true) {
                    if (i3 >= 4) {
                        z = false;
                        break;
                    }
                    String str = strArr[i3];
                    String str2 = active.ssid;
                    if (str2 != null && StringsKt__StringsKt.contains(str2, str, false)) {
                        z = true;
                        break;
                    }
                    i3++;
                }
                if (z) {
                    WifiManager wifiManager = wifiSignalIconResource.wifiManager;
                    List privilegedConfiguredNetworks = wifiManager != null ? wifiManager.getPrivilegedConfiguredNetworks() : null;
                    if (privilegedConfiguredNetworks != null) {
                        Iterator it = privilegedConfiguredNetworks.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                obj2 = null;
                                break;
                            }
                            obj2 = it.next();
                            if (((WifiConfiguration) obj2).networkId == active.wifiNetworkId) {
                                break;
                            }
                        }
                        WifiConfiguration wifiConfiguration = (WifiConfiguration) obj2;
                        if (wifiConfiguration != null) {
                            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("eapMethod=", wifiConfiguration.enterpriseConfig.getEapMethod(), " phase2Method=", wifiConfiguration.enterpriseConfig.getPhase2Method(), "WifiSignalIconResource");
                            if (wifiConfiguration.enterpriseConfig.getEapMethod() != 5) {
                                if (wifiConfiguration.enterpriseConfig.getEapMethod() == 0) {
                                    if (wifiConfiguration.enterpriseConfig.getPhase2Method() != 3) {
                                    }
                                }
                            }
                            if (z3 && active.isValidated) {
                                SamsungWifiIcons.Companion.getClass();
                                signalIcon$IconGroup2 = SamsungWifiIcons.WIFI_ICON_LGT;
                            }
                        }
                    }
                }
                z3 = false;
                if (z3) {
                    SamsungWifiIcons.Companion.getClass();
                    signalIcon$IconGroup2 = SamsungWifiIcons.WIFI_ICON_LGT;
                }
            }
        }
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, 0, new Object[0])) {
            signalIcon$IconGroup2.activityIcons = null;
        }
        return signalIcon$IconGroup2;
    }
}
