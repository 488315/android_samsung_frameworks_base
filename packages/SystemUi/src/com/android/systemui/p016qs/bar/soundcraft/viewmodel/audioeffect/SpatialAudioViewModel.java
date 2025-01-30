package com.android.systemui.p016qs.bar.soundcraft.viewmodel.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.p016qs.bar.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.p016qs.bar.soundcraft.model.BudsInfo;
import com.android.systemui.p016qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SpatialAudioViewModel extends BaseSingleChoiceViewModel {
    public final Context context;
    public MenuItem currentPosition = MenuItem.OFF;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final WearableManager wearableManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum MenuItem {
        OFF(0, R.string.soundcraft_spatial_audio_option_off),
        SPATIAL_ONLY(1, R.string.soundcraft_spatial_audio_option_360_only),
        SPATIAL_AND_HEAD_TRACKING(2, R.string.soundcraft_spatial_audio_option_360_headtracking);

        private final int position;
        private final int titleResId;

        MenuItem(int i, int i2) {
            this.position = i;
            this.titleResId = i2;
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

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final MutableLiveData getIcon() {
        return new MutableLiveData(Integer.valueOf(R.drawable.soundcraft_ic_spatial_audio));
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        Log.d("SoundCraft.SpatialAudioViewModel", "notifyChange");
        BudsInfo budsInfo = this.modelProvider.budsInfo;
        Unit unit = null;
        if (!(budsInfo.getSpatialAudio() != null)) {
            budsInfo = null;
        }
        if (budsInfo != null) {
            Boolean spatialAudio = budsInfo.getSpatialAudio();
            boolean booleanValue = spatialAudio != null ? spatialAudio.booleanValue() : false;
            Boolean headTracking = budsInfo.getHeadTracking();
            boolean booleanValue2 = headTracking != null ? headTracking.booleanValue() : false;
            EmergencyButtonController$$ExternalSyntheticOutline0.m59m("spatialAudio=", booleanValue, ", headTracking=", booleanValue2, "SoundCraft.SpatialAudioViewModel");
            this.currentPosition = (booleanValue && booleanValue2) ? MenuItem.SPATIAL_AND_HEAD_TRACKING : booleanValue ? MenuItem.SPATIAL_ONLY : MenuItem.OFF;
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this.currentPosition = MenuItem.OFF;
        }
        MutableLiveData mutableLiveData = this.title;
        Context context = this.context;
        mutableLiveData.setValue(context.getString(R.string.soundcraft_spatial_audio_title));
        this.selectedOptionName.setValue(context.getString(this.currentPosition.getTitleResId()));
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final void onChooserDismiss() {
        this.showChooser.setValue(Boolean.FALSE);
    }

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final void onClick() {
        Log.d("SoundCraft.SpatialAudioViewModel", "onClick");
        List mutableList = ArraysKt___ArraysKt.toMutableList(MenuItem.values());
        if (this.modelProvider.budsInfo.getHeadTracking() == null) {
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

    @Override // com.android.systemui.p016qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final void onItemSelected(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onItemSelected : position=", i, "SoundCraft.SpatialAudioViewModel");
        int position = MenuItem.OFF.getPosition();
        ModelProvider modelProvider = this.modelProvider;
        if (i == position) {
            BudsInfo budsInfo = modelProvider.budsInfo;
            Boolean bool = Boolean.FALSE;
            budsInfo.setSpatialAudio(bool);
            Boolean headTracking = modelProvider.budsInfo.getHeadTracking();
            if (headTracking != null) {
                headTracking.booleanValue();
                modelProvider.budsInfo.setHeadTracking(bool);
            }
        } else if (i == MenuItem.SPATIAL_ONLY.getPosition()) {
            modelProvider.budsInfo.setSpatialAudio(Boolean.TRUE);
            Boolean headTracking2 = modelProvider.budsInfo.getHeadTracking();
            if (headTracking2 != null) {
                headTracking2.booleanValue();
                modelProvider.budsInfo.setHeadTracking(Boolean.FALSE);
            }
        } else if (i == MenuItem.SPATIAL_AND_HEAD_TRACKING.getPosition()) {
            BudsInfo budsInfo2 = modelProvider.budsInfo;
            Boolean bool2 = Boolean.TRUE;
            budsInfo2.setSpatialAudio(bool2);
            Boolean headTracking3 = modelProvider.budsInfo.getHeadTracking();
            if (headTracking3 != null) {
                headTracking3.booleanValue();
                modelProvider.budsInfo.setHeadTracking(bool2);
            }
        }
        String str = modelProvider.playingAudioPackageNameForAppSetting;
        Unit unit = null;
        if (str != null) {
            RoutineManager routineManager = this.routineManager;
            String routineId = routineManager.getRoutineId(str);
            if (routineId != null) {
                routineManager.updateRoutine(str, routineId, modelProvider.budsInfo);
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                routineManager.createRoutine(str, modelProvider.budsInfo);
            }
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this.wearableManager.updateBudsInfo(modelProvider.budsInfo);
        }
        this.selectedOptionName.setValue(this.context.getString(MenuItem.values()[i].getTitleResId()));
        onChooserDismiss();
    }
}
