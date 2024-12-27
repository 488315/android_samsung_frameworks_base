package com.android.systemui.mediaprojection.taskswitcher.domain.interactor;

import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class TaskSwitchInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final MediaProjectionRepository mediaProjectionRepository;
    public final ChannelFlowTransformLatest taskSwitchChanges;
    public final TasksRepository tasksRepository;

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
