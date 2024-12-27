package com.android.systemui.audio.soundcraft.model;

import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.model.buds.BatteryInfo;
import com.android.systemui.audio.soundcraft.model.buds.BudsModel;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.audio.soundcraft.model.phone.PhoneEffectModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ModelProvider {
    public boolean isFromNowBar;
    public EffectOutDeviceType effectOutDeviceType = EffectOutDeviceType.PHONE;
    public AppSettingModel appSettingModel = new AppSettingModel();
    public PhoneEffectModel phoneModel = new PhoneEffectModel(0, 0, false, false, false, false, 63, null);
    public BudsModel budsModel = new BudsModel(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 131071, null);
    public EffectModel effectModel = new EffectModel(null, null, null, null, null, null, 63, null);
    public final BatteryInfo batteryInfo = new BatteryInfo(null, null, null, 7, null);
    public VolumeModel volumeModel = new VolumeModel(0, 0, 0, 0, false, false, false, 0, false, false, false, false, 4095, null);
}
