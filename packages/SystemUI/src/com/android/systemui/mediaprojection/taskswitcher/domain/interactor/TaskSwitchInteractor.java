package com.android.systemui.mediaprojection.taskswitcher.domain.interactor;

import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TaskSwitchInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final MediaProjectionRepository mediaProjectionRepository;
    public final ChannelFlowTransformLatest taskSwitchChanges;
    public final TasksRepository tasksRepository;

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

    public TaskSwitchInteractor(MediaProjectionRepository mediaProjectionRepository, TasksRepository tasksRepository) {
        this.mediaProjectionRepository = mediaProjectionRepository;
        this.tasksRepository = tasksRepository;
        this.taskSwitchChanges = FlowKt.transformLatest(((MediaProjectionManagerRepository) mediaProjectionRepository).mediaProjectionState, new TaskSwitchInteractor$special$$inlined$flatMapLatest$1(null, this));
    }
}
