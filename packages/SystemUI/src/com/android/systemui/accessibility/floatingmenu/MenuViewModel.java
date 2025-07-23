package com.android.systemui.accessibility.floatingmenu;

import android.content.ComponentName;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.systemui.accessibility.floatingmenu.MenuInfoRepository;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MenuViewModel implements MenuInfoRepository.OnContentsChanged {
    public final MutableLiveData mFadeEffectInfoData;
    public final MutableLiveData mHearingDeviceStatusData;
    public final MediatorLiveData mHearingDeviceTargetIndex;
    public final MenuInfoRepository mInfoRepository;
    public final MutableLiveData mSizeTypeData;
    public final MutableLiveData mTargetFeaturesData;

    public MenuViewModel(Context context, AccessibilityManager accessibilityManager, SecureSettings secureSettings, HearingAidDeviceManager hearingAidDeviceManager) {
        MutableLiveData mutableLiveData = new MutableLiveData(Collections.EMPTY_LIST);
        this.mTargetFeaturesData = mutableLiveData;
        this.mSizeTypeData = new MutableLiveData();
        this.mFadeEffectInfoData = new MutableLiveData();
        new MutableLiveData();
        new MutableLiveData();
        new MutableLiveData();
        new MutableLiveData();
        this.mHearingDeviceStatusData = new MutableLiveData(-1);
        this.mHearingDeviceTargetIndex = Transformations.map(mutableLiveData, new Function1() { // from class: com.android.systemui.accessibility.floatingmenu.MenuViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                List list = (List) obj;
                MenuViewModel.this.getClass();
                int size = list.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        i = -1;
                        break;
                    }
                    if (AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.equals(ComponentName.unflattenFromString(((AccessibilityTarget) list.get(i)).getId()))) {
                        break;
                    }
                    i++;
                }
                return Integer.valueOf(i);
            }
        });
        this.mInfoRepository = new MenuInfoRepository(context, accessibilityManager, this, secureSettings, hearingAidDeviceManager);
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onDevicesConnectionStatusChanged(int i) {
        this.mHearingDeviceStatusData.postValue(Integer.valueOf(i));
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onFadeEffectInfoChanged(MenuFadeEffectInfo menuFadeEffectInfo) {
        this.mFadeEffectInfoData.setValue(menuFadeEffectInfo);
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onSizeTypeChanged(int i) {
        this.mSizeTypeData.setValue(Integer.valueOf(i));
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onTargetFeaturesChanged(List list) {
        this.mTargetFeaturesData.setValue(list);
    }
}
