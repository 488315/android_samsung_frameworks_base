package com.android.systemui.haptics.msdl.dagger;

import android.os.Vibrator;
import android.util.Log;
import com.google.android.msdl.data.model.HapticComposition;
import com.google.android.msdl.data.model.HapticCompositionPrimitive;
import com.google.android.msdl.data.model.MSDLToken;
import com.google.android.msdl.data.repository.MSDLHapticData;
import com.google.android.msdl.data.repository.MSDLRepositoryImpl;
import com.google.android.msdl.domain.EmptyMSDLPlayer;
import com.google.android.msdl.domain.MSDLPlayer;
import com.google.android.msdl.domain.MSDLPlayerImpl;
import dagger.internal.Provider;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MSDLModule_ProvideMSDLPlayerFactory implements Provider {
    public final Provider vibratorProvider;

    public MSDLModule_ProvideMSDLPlayerFactory(Provider provider) {
        this.vibratorProvider = provider;
    }

    public static MSDLPlayer provideMSDLPlayer(Vibrator vibrator) {
        boolean z;
        MSDLModule.INSTANCE.getClass();
        MSDLPlayer.Companion companion = MSDLPlayer.Companion;
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        companion.getClass();
        if (vibrator == null) {
            Log.w("MSDLPlayer", "A null vibrator was used to create a MSDLPlayer. An empty player was created");
            return new EmptyMSDLPlayer();
        }
        MSDLRepositoryImpl mSDLRepositoryImpl = new MSDLRepositoryImpl();
        MSDLPlayerImpl.Companion.getClass();
        List list = MSDLPlayerImpl.REQUIRED_PRIMITIVES;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Object obj : list) {
            boolean[] arePrimitivesSupported = vibrator.arePrimitivesSupported(((Number) obj).intValue());
            if (arePrimitivesSupported.length == 0) {
                throw new NoSuchElementException("Array is empty.");
            }
            linkedHashMap.put(obj, Boolean.valueOf(arePrimitivesSupported[0]));
        }
        List list2 = MSDLToken.$ENTRIES;
        int mapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(mapCapacity2 >= 16 ? mapCapacity2 : 16);
        Iterator it = ((AbstractList) list2).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            MSDLHapticData mSDLHapticData = (MSDLHapticData) MSDLRepositoryImpl.HAPTIC_DATA.get(((MSDLToken) next).getHapticToken());
            HapticComposition hapticComposition = mSDLHapticData != null ? mSDLHapticData.get() : null;
            HapticComposition hapticComposition2 = hapticComposition != null ? hapticComposition : null;
            if (hapticComposition2 != null) {
                Iterator it2 = hapticComposition2.primitives.iterator();
                while (it2.hasNext()) {
                    Boolean bool = (Boolean) linkedHashMap.get(Integer.valueOf(((HapticCompositionPrimitive) it2.next()).primitiveId));
                    if (bool == null || bool.equals(Boolean.FALSE)) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            linkedHashMap2.put(next, Boolean.valueOf(z));
        }
        return new MSDLPlayerImpl(mSDLRepositoryImpl, vibrator, newSingleThreadExecutor, linkedHashMap2);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMSDLPlayer((Vibrator) this.vibratorProvider.get());
    }
}
