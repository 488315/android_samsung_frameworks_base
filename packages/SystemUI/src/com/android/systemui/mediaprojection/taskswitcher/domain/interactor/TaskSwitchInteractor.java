package com.android.systemui.mediaprojection.taskswitcher.domain.interactor;

import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TaskSwitchInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final MediaProjectionRepository mediaProjectionRepository;
    public final ChannelFlowTransformLatest taskSwitchChanges;
    public final TasksRepository tasksRepository;

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

    public TaskSwitchInteractor(MediaProjectionRepository mediaProjectionRepository, TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
        this.taskSwitchChanges = FlowKt.transformLatest(((MediaProjectionManagerRepository) mediaProjectionRepository).mediaProjectionState, new TaskSwitchInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
