package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ CachedBluetoothDevice $cachedBluetoothDevice;
    int label;
    final /* synthetic */ SpatialAudioComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2(CachedBluetoothDevice cachedBluetoothDevice, SpatialAudioComponentInteractor spatialAudioComponentInteractor, Continuation continuation) {
        super(2, continuation);
        this.$cachedBluetoothDevice = cachedBluetoothDevice;
        this.this$0 = spatialAudioComponentInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2(this.$cachedBluetoothDevice, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a5  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object next;
        Integer num;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            List profiles = this.$cachedBluetoothDevice.getProfiles();
            CachedBluetoothDevice cachedBluetoothDevice = this.$cachedBluetoothDevice;
            Iterator it = profiles.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) next;
                SpatialAudioComponentInteractor.Companion.getClass();
                if (SpatialAudioComponentInteractor.audioProfiles.contains(new Integer(localBluetoothProfile.getProfileId())) && localBluetoothProfile.isEnabled(cachedBluetoothDevice.mDevice)) {
                    break;
                }
            }
            LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) next;
            if (localBluetoothProfile2 != null) {
                SpatialAudioComponentInteractor spatialAudioComponentInteractor = this.this$0;
                CachedBluetoothDevice cachedBluetoothDevice2 = this.$cachedBluetoothDevice;
                int profileId = localBluetoothProfile2.getProfileId();
                if (profileId == 2) {
                    num = new Integer(8);
                } else if (profileId == 21) {
                    num = new Integer(23);
                } else if (profileId != 22) {
                    num = null;
                } else {
                    AudioRepository audioRepository = spatialAudioComponentInteractor.audioRepository;
                    String address = cachedBluetoothDevice2.mDevice.getAddress();
                    this.label = 1;
                    obj = ((AudioRepositoryImpl) audioRepository).getBluetoothAudioDeviceCategory(address, this);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                if (num != null) {
                    return new AudioDeviceAttributes(2, num.intValue(), this.$cachedBluetoothDevice.mDevice.getAddress());
                }
            }
            return null;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        num = new Integer(((Number) obj).intValue() == 2 ? 27 : 26);
        if (num != null) {
        }
        return null;
    }
}
