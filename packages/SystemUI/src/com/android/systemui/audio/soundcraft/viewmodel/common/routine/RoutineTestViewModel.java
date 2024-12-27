package com.android.systemui.audio.soundcraft.viewmodel.common.routine;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.audio.soundcraft.interfaces.audio.AudioPlaybackManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.audio.soundcraft.model.ModelProvider;
import com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class RoutineTestViewModel extends BaseViewModel {
    public final AudioPlaybackManager audioPlaybackManager;
    public final ModelProvider modelProvider;
    public final MutableLiveData routineCount = new MutableLiveData(0);
    public final RoutineManager routineManager;

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

    public RoutineTestViewModel(Context context, RoutineManager routineManager, ModelProvider modelProvider, AudioPlaybackManager audioPlaybackManager) {
        this.routineManager = routineManager;
        this.modelProvider = modelProvider;
        this.audioPlaybackManager = audioPlaybackManager;
    }

    @Override // com.android.systemui.audio.soundcraft.viewmodel.common.base.BaseViewModel
    public final void notifyChange() {
        throw null;
    }
}
