package com.android.systemui.audio.soundcraft.viewmodel.buds.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SpatialAudioViewModel extends BaseSingleChoiceViewModel {
    public final Context context;
    public MenuItem currentPosition = MenuItem.OFF;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final WearableManager wearableManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MenuItem {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ MenuItem[] $VALUES;
        public static final MenuItem OFF;
        public static final MenuItem SPATIAL_AND_HEAD_TRACKING;
        public static final MenuItem SPATIAL_ONLY;
        private final int position;
        private final int titleResId;

        static {
            MenuItem menuItem = new MenuItem("OFF", 0, 0, R.string.soundcraft_spatial_audio_option_off);
            OFF = menuItem;
            MenuItem menuItem2 = new MenuItem("SPATIAL_ONLY", 1, 1, R.string.soundcraft_spatial_audio_option_360_only);
            SPATIAL_ONLY = menuItem2;
            MenuItem menuItem3 = new MenuItem("SPATIAL_AND_HEAD_TRACKING", 2, 2, R.string.soundcraft_spatial_audio_option_360_headtracking);
            SPATIAL_AND_HEAD_TRACKING = menuItem3;
            MenuItem[] menuItemArr = {menuItem, menuItem2, menuItem3};
            $VALUES = menuItemArr;
            $ENTRIES = EnumEntriesKt.enumEntries(menuItemArr);
        }

        private MenuItem(String str, int i, int i2, int i3) {
            this.position = i2;
            this.titleResId = i3;
        }

        public static MenuItem valueOf(String str) {
            return (MenuItem) Enum.valueOf(MenuItem.class, str);
        }

        public static MenuItem[] values() {
            return (MenuItem[]) $VALUES.clone();
        }

        public final int getPosition() {
            return this.position;
        }

        public final int getTitleResId() {
            return this.titleResId;
        }
    }

    static {
        new Companion(null);
    }

    public SpatialAudioViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final void dismiss() {
        this.showChooser.setValue(Boolean.FALSE);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        EffectModel effectModel = this.modelProvider.effectModel;
        Unit unit = null;
        if (effectModel.spatialAudio == null) {
            effectModel = null;
        }
        if (effectModel != null) {
            Boolean bool = effectModel.spatialAudio;
            boolean booleanValue = bool != null ? bool.booleanValue() : false;
            Boolean bool2 = effectModel.headTracking;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : false;
            EmergencyButtonController$$ExternalSyntheticOutline0.m("spatialAudio=", ", headTracking=", "SoundCraft.SpatialAudioViewModel", booleanValue, booleanValue2);
            this.currentPosition = (booleanValue && booleanValue2) ? MenuItem.SPATIAL_AND_HEAD_TRACKING : booleanValue ? MenuItem.SPATIAL_ONLY : MenuItem.OFF;
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this.currentPosition = MenuItem.OFF;
        }
        this.title.setValue(this.context.getString(R.string.soundcraft_spatial_audio_title));
        this.selectedOptionName.setValue(this.context.getString(this.currentPosition.getTitleResId()));
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final void onClick() {
        Log.d("SoundCraft.SpatialAudioViewModel", "onClick");
        List mutableList = ArraysKt___ArraysKt.toMutableList(MenuItem.values());
        if (this.modelProvider.budsModel.getHeadTracking() == null) {
            ((ArrayList) mutableList).remove(MenuItem.SPATIAL_AND_HEAD_TRACKING);
        }
        MutableLiveData mutableLiveData = this.optionNames;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mutableList, 10));
        Iterator it = mutableList.iterator();
        while (it.hasNext()) {
            arrayList.add(this.context.getString(((MenuItem) it.next()).getTitleResId()));
        }
        Log.d("SoundCraft.SpatialAudioViewModel", "options=" + arrayList);
        mutableLiveData.setValue(arrayList);
        this.showChooser.setValue(Boolean.TRUE);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final void onItemSelected(int i) {
        Unit unit;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onItemSelected : position=", "SoundCraft.SpatialAudioViewModel");
        if (i == MenuItem.OFF.getPosition()) {
            setSpatialAudio(false);
            setHeadTracking(false);
        } else if (i == MenuItem.SPATIAL_ONLY.getPosition()) {
            setSpatialAudio(true);
            setHeadTracking(false);
        } else if (i == MenuItem.SPATIAL_AND_HEAD_TRACKING.getPosition()) {
            setSpatialAudio(true);
            setHeadTracking(true);
        }
        ModelProvider modelProvider = this.modelProvider;
        AppSettingModel appSettingModel = modelProvider.appSettingModel;
        if (appSettingModel.readyToUpdateRoutine) {
            String str = appSettingModel.playingAudioPackageName;
            if (str != null) {
                RoutineManager routineManager = this.routineManager;
                String routineId = routineManager.getRoutineId(str);
                if (routineId != null) {
                    routineManager.updateRoutine(str, routineId, modelProvider.effectModel);
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    routineManager.createRoutine(modelProvider.effectModel, str);
                }
            }
        } else {
            this.wearableManager.updateBudsModel(modelProvider.budsModel);
        }
        this.selectedOptionName.setValue(this.context.getString(((MenuItem) MenuItem.$ENTRIES.get(i)).getTitleResId()));
        dismiss();
    }

    public final void setHeadTracking(boolean z) {
        ModelProvider modelProvider = this.modelProvider;
        if (modelProvider.budsModel.getHeadTracking() != null) {
            modelProvider.budsModel.setHeadTracking(Boolean.valueOf(z));
        }
        EffectModel effectModel = modelProvider.effectModel;
        if (effectModel.headTracking != null) {
            effectModel.headTracking = Boolean.valueOf(z);
        }
    }

    public final void setSpatialAudio(boolean z) {
        ModelProvider modelProvider = this.modelProvider;
        modelProvider.budsModel.setSpatialAudio(Boolean.valueOf(z));
        modelProvider.effectModel.spatialAudio = Boolean.valueOf(z);
    }
}
