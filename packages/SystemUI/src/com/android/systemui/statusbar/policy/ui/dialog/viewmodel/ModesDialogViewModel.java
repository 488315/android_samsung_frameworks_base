package com.android.systemui.statusbar.policy.ui.dialog.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.settingslib.notification.modes.ZenMode;
import com.android.settingslib.notification.modes.ZenModeDescriptions;
import com.android.systemui.R;
import com.android.systemui.qs.QSModesEvent;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogDelegate;
import com.android.systemui.statusbar.policy.ui.dialog.ModesDialogEventLogger;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ModesDialogViewModel {
    public final Context context;
    public final ModesDialogDelegate dialogDelegate;
    public final ModesDialogEventLogger dialogEventLogger;
    public final Flow tiles;
    public final FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 visibleModes;
    public final ZenModeDescriptions zenModeDescriptions;

    public ModesDialogViewModel(Context context, final ZenModeInteractor zenModeInteractor, CoroutineDispatcher coroutineDispatcher, ModesDialogDelegate modesDialogDelegate, ModesDialogEventLogger modesDialogEventLogger) {
        this.context = context;
        this.dialogDelegate = modesDialogDelegate;
        this.dialogEventLogger = modesDialogEventLogger;
        this.zenModeDescriptions = new ZenModeDescriptions(context);
        final FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 = new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(EmptyList.INSTANCE, zenModeInteractor.modes, new ModesDialogViewModel$visibleModes$1(null));
        this.visibleModes = flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
        this.tiles = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ZenModeInteractor $zenModeInteractor$inlined;
                public final /* synthetic */ ModesDialogViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
                    Object L$4;
                    Object L$5;
                    Object L$6;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, ZenModeInteractor zenModeInteractor, ModesDialogViewModel modesDialogViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$zenModeInteractor$inlined = zenModeInteractor;
                    this.this$0 = modesDialogViewModel;
                }

                /* JADX WARN: Code restructure failed: missing block: B:39:0x015d, code lost:
                
                    if (r11.emit((java.util.List) r4, r2) == r3) goto L46;
                 */
                /* JADX WARN: Removed duplicated region for block: B:19:0x00b7  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x00ca  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0107  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0115  */
                /* JADX WARN: Removed duplicated region for block: B:34:0x007c  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x0145  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x00ec  */
                /* JADX WARN: Removed duplicated region for block: B:45:0x00b9  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x005a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
                /* JADX WARN: Type inference failed for: r7v13 */
                /* JADX WARN: Type inference failed for: r7v14 */
                /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.common.shared.model.ContentDescription, java.lang.Integer] */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x009f -> B:17:0x00a2). Please report as a decompilation issue!!! */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r24, kotlin.coroutines.Continuation r25) {
                    /*
                        Method dump skipped, instructions count: 355
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, zenModeInteractor, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
    }

    public static final void access$openSettings(ModesDialogViewModel modesDialogViewModel, ZenMode zenMode) {
        ModesDialogEventLogger modesDialogEventLogger = modesDialogViewModel.dialogEventLogger;
        modesDialogEventLogger.getClass();
        modesDialogEventLogger.uiEventLogger.log(zenMode.isManualDnd() ? QSModesEvent.QS_MODES_DND_SETTINGS : QSModesEvent.QS_MODES_MODE_SETTINGS, 0, zenMode.getOwnerPackage());
        modesDialogViewModel.dialogDelegate.launchFromDialog(new Intent("android.settings.AUTOMATIC_ZEN_RULE_SETTINGS").putExtra("android.provider.extra.AUTOMATIC_ZEN_RULE_ID", zenMode.mId));
    }

    public final String getModeDescription(ZenMode zenMode, boolean z) {
        String str;
        ZenModeConfig.ScheduleInfo tryParseScheduleConditionId;
        String daysOfWeekFull;
        if (!zenMode.mRule.isEnabled()) {
            return this.context.getResources().getString(R.string.zen_mode_set_up);
        }
        if (!zenMode.mRule.isManualInvocationAllowed() && !zenMode.isActive()) {
            return this.context.getResources().getString(R.string.zen_mode_no_manual_invocation);
        }
        ZenModeDescriptions zenModeDescriptions = this.zenModeDescriptions;
        if (!z) {
            return zenModeDescriptions.getTriggerDescription(zenMode);
        }
        zenModeDescriptions.getClass();
        if (!"android".equals(zenMode.mRule.getPackageName()) || zenMode.mRule.getType() != 1 || (tryParseScheduleConditionId = ZenModeConfig.tryParseScheduleConditionId(zenMode.mRule.getConditionId())) == null || (daysOfWeekFull = SystemZenRules.getDaysOfWeekFull(zenModeDescriptions.mContext, tryParseScheduleConditionId)) == null) {
            str = null;
        } else {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(daysOfWeekFull, ", ");
            m.append(SystemZenRules.getTimeSummary(zenModeDescriptions.mContext, tryParseScheduleConditionId));
            str = m.toString();
        }
        return str == null ? zenModeDescriptions.getTriggerDescription(zenMode) : str;
    }
}
