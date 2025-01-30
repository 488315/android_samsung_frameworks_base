package com.android.systemui.keyguard.domain.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceRegistryImpl implements KeyguardQuickAffordanceRegistry {
    public final Map configByKey;
    public final Map configsByPosition;

    public KeyguardQuickAffordanceRegistryImpl(HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig) {
        Map mapOf = MapsKt__MapsKt.mapOf(new Pair(KeyguardQuickAffordancePosition.BOTTOM_START, Collections.singletonList(homeControlsKeyguardQuickAffordanceConfig)), new Pair(KeyguardQuickAffordancePosition.BOTTOM_END, CollectionsKt__CollectionsKt.listOf(quickAccessWalletKeyguardQuickAffordanceConfig, qrCodeScannerKeyguardQuickAffordanceConfig)));
        this.configsByPosition = mapOf;
        List flatten = CollectionsKt__IterablesKt.flatten(mapOf.values());
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(flatten, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity < 16 ? 16 : mapCapacity);
        for (Object obj : flatten) {
            linkedHashMap.put(((KeyguardQuickAffordanceConfig) obj).getKey(), obj);
        }
        this.configByKey = linkedHashMap;
    }
}
