package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 1
            r3 = 2
            r4 = 0
            if (r1 == 0) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L81
        L10:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L18:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.settingslib.bluetooth.CachedBluetoothDevice r11 = r10.$cachedBluetoothDevice
            java.util.List r11 = r11.getProfiles()
            java.lang.Iterable r11 = (java.lang.Iterable) r11
            com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = r10.$cachedBluetoothDevice
            java.util.Iterator r11 = r11.iterator()
        L29:
            boolean r5 = r11.hasNext()
            if (r5 == 0) goto L55
            java.lang.Object r5 = r11.next()
            r6 = r5
            com.android.settingslib.bluetooth.LocalBluetoothProfile r6 = (com.android.settingslib.bluetooth.LocalBluetoothProfile) r6
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$Companion r7 = com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.Companion
            r7.getClass()
            java.util.Set r7 = com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor.audioProfiles
            int r8 = r6.getProfileId()
            java.lang.Integer r9 = new java.lang.Integer
            r9.<init>(r8)
            boolean r7 = r7.contains(r9)
            if (r7 == 0) goto L29
            android.bluetooth.BluetoothDevice r7 = r1.mDevice
            boolean r6 = r6.isEnabled(r7)
            if (r6 == 0) goto L29
            goto L56
        L55:
            r5 = r4
        L56:
            com.android.settingslib.bluetooth.LocalBluetoothProfile r5 = (com.android.settingslib.bluetooth.LocalBluetoothProfile) r5
            if (r5 == 0) goto Lb6
            com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor r11 = r10.this$0
            com.android.settingslib.bluetooth.CachedBluetoothDevice r1 = r10.$cachedBluetoothDevice
            int r5 = r5.getProfileId()
            if (r5 == r3) goto L9c
            r6 = 21
            if (r5 == r6) goto L94
            r6 = 22
            if (r5 == r6) goto L6e
            r0 = r4
            goto La3
        L6e:
            com.android.settingslib.volume.data.repository.AudioRepository r11 = r11.audioRepository
            android.bluetooth.BluetoothDevice r1 = r1.mDevice
            java.lang.String r1 = r1.getAddress()
            r10.label = r2
            com.android.settingslib.volume.data.repository.AudioRepositoryImpl r11 = (com.android.settingslib.volume.data.repository.AudioRepositoryImpl) r11
            java.lang.Object r11 = r11.getBluetoothAudioDeviceCategory(r1, r10)
            if (r11 != r0) goto L81
            return r0
        L81:
            java.lang.Number r11 = (java.lang.Number) r11
            int r11 = r11.intValue()
            if (r11 != r3) goto L8c
            r11 = 27
            goto L8e
        L8c:
            r11 = 26
        L8e:
            java.lang.Integer r0 = new java.lang.Integer
            r0.<init>(r11)
            goto La3
        L94:
            java.lang.Integer r0 = new java.lang.Integer
            r11 = 23
            r0.<init>(r11)
            goto La3
        L9c:
            java.lang.Integer r0 = new java.lang.Integer
            r11 = 8
            r0.<init>(r11)
        La3:
            if (r0 == 0) goto Lb6
            com.android.settingslib.bluetooth.CachedBluetoothDevice r10 = r10.$cachedBluetoothDevice
            int r11 = r0.intValue()
            android.media.AudioDeviceAttributes r4 = new android.media.AudioDeviceAttributes
            android.bluetooth.BluetoothDevice r10 = r10.mDevice
            java.lang.String r10 = r10.getAddress()
            r4.<init>(r3, r11, r10)
        Lb6:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
