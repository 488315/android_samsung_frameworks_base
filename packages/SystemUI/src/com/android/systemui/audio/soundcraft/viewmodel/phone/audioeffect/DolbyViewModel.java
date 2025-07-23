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
import com.android.systemui.audio.soundcraft.model.common.EffectModel;
import com.android.systemui.audio.soundcraft.model.phone.Dolby;
import com.android.systemui.audio.soundcraft.model.phone.DolbyEnum;
import com.android.systemui.audio.soundcraft.utils.SoundCraftSALogging;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseSingleChoiceViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.IteratorImpl;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DolbyViewModel extends BaseSingleChoiceViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final SoundAliveManager soundAliveManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        Object obj;
        Object obj2;
        Log.d("SoundCraft.DolbyViewModel", "onItemSelected : position=" + i);
        ModelProvider modelProvider = this.modelProvider;
        EffectModel effectModel = modelProvider.effectModel;
        List list = effectModel.dolbyList;
        if (list != null) {
            List list2 = DolbyEnum.$ENTRIES;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            AbstractList abstractList = (AbstractList) list2;
            abstractList.getClass();
            AbstractList.IteratorImpl iteratorImpl = abstractList.new IteratorImpl();
            while (iteratorImpl.hasNext()) {
                arrayList.add(new Dolby(this.context.getString(((DolbyEnum) iteratorImpl.next()).getNameResId()), false));
            }
            effectModel.dolbyOldList = arrayList;
            Iterator it = list.iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    obj2 = null;
                    break;
                } else {
                    obj2 = it.next();
                    if (((Dolby) obj2).state) {
                        break;
                    }
                }
            }
            Dolby dolby = (Dolby) obj2;
            if (dolby != null) {
                if (i == 0) {
                    Iterator it2 = modelProvider.effectModel.dolbyOldList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Object next = it2.next();
                        if (Intrinsics.areEqual(((Dolby) next).name, dolby.name)) {
                            obj = next;
                            break;
                        }
                    }
                    Dolby dolby2 = (Dolby) obj;
                    if (dolby2 != null) {
                        dolby2.state = true;
                        Log.d("SoundCraft.DolbyViewModel", "previous dolby=" + dolby2.name);
                    }
                }
                dolby.state = false;
            }
            ((Dolby) list.get(i)).state = true;
            if (i != 0) {
                ((Dolby) modelProvider.effectModel.dolbyOldList.get(i)).state = true;
            }
            AppSettingModel appSettingModel = modelProvider.appSettingModel;
            if (appSettingModel.readyToUpdateRoutine) {
                String str = appSettingModel.playingAudioPackageName;
                if (str != null) {
                    RoutineManager routineManager = this.routineManager;
                    String routineId = routineManager.getRoutineId(str);
                    if (routineId != null) {
                        routineManager.updateRoutine(str, routineId, modelProvider.effectModel);
                    } else {
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
        SoundCraftSALogging.sendEventLog$default(SoundCraftSALogging.INSTANCE, SoundCraftSALogging.ScreenId.EID_PHONE_DETAIL_SETTING, SoundCraftSALogging.Event.DOLBY_ATMOS, String.valueOf(i), 8);
        notifyChange();
        dismiss();
    }
}
