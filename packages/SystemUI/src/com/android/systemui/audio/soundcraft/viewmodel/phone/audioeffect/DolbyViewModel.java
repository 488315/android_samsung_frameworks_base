package com.android.systemui.audio.soundcraft.viewmodel.phone.audioeffect;

import android.content.Context;
import android.sec.clipboard.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveEffectEnum;
import com.android.systemui.audio.soundcraft.interfaces.soundalive.SoundAliveManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.model.appsetting.AppSettingModel;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import com.android.systemui.audio.soundcraft.model.phone.DolbyEnum;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class DolbyViewModel extends BaseSingleChoiceViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final SoundAliveManager soundAliveManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DolbyViewModel(Context context, ModelProvider modelProvider, SoundAliveManager soundAliveManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.soundAliveManager = soundAliveManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final void dismiss() {
        this.showChooser.setValue(Boolean.FALSE);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final MutableLiveData getOptionNames() {
        ArrayList arrayList;
        List list = this.modelProvider.effectModel.dolbyList;
        if (list != null) {
            List list2 = list;
            arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((Dolby) it.next()).name);
            }
        } else {
            arrayList = null;
        }
        return new MutableLiveData(arrayList);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final MutableLiveData getTitle() {
        return new MutableLiveData(this.context.getString(R.string.soundcraft_dolby_title));
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        Object obj;
        List list = this.modelProvider.effectModel.dolbyList;
        if (list != null) {
            MutableLiveData optionNames = getOptionNames();
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((Dolby) it.next()).name);
            }
            optionNames.setValue(arrayList);
            MutableLiveData mutableLiveData = this.selectedOptionName;
            Iterator it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it2.next();
                    if (((Dolby) obj).state) {
                        break;
                    }
                }
            }
            Dolby dolby = (Dolby) obj;
            mutableLiveData.setValue(dolby != null ? dolby.name : null);
        }
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final void onClick() {
        Log.d("SoundCraft.DolbyViewModel", "onClick");
        this.showChooser.setValue(Boolean.TRUE);
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel
    public final void onItemSelected(int i) {
        Unit unit;
        Object obj;
        Log.d("SoundCraft.DolbyViewModel", "onItemSelected : position=" + i);
        ModelProvider modelProvider = this.modelProvider;
        List list = modelProvider.effectModel.dolbyList;
        if (list != null) {
            Iterator it = list.iterator();
            while (true) {
                unit = null;
                if (!it.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it.next();
                    if (((Dolby) obj).state) {
                        break;
                    }
                }
            }
            Dolby dolby = (Dolby) obj;
            if (dolby != null) {
                dolby.state = false;
            }
            ((Dolby) list.get(i)).state = true;
            AppSettingModel appSettingModel = modelProvider.appSettingModel;
            if (appSettingModel.readyToUpdateRoutine) {
                String str = appSettingModel.playingAudioPackageName;
                if (str != null) {
                    RoutineManager routineManager = this.routineManager;
                    String routineId = routineManager.getRoutineId(str);
                    if (routineId != null) {
                        routineManager.updateRoutine(str, routineId, modelProvider.effectModel);
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        routineManager.createRoutine(modelProvider.effectModel, str);
                    }
                }
            } else {
                int realIndex = ((DolbyEnum) DolbyEnum.$ENTRIES.get(i)).getRealIndex();
                SoundAliveManager soundAliveManager = this.soundAliveManager;
                soundAliveManager.getClass();
                soundAliveManager.setState(realIndex, SoundAliveEffectEnum.DOLBY_INDEX.getSettingName());
            }
        }
        notifyChange();
        dismiss();
    }
}
