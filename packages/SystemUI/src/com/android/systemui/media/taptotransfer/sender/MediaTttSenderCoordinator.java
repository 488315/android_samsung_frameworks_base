package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.util.Log;
import android.view.View;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.IconInfo;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils;
import com.android.systemui.media.taptotransfer.common.MediaTttUtils;
import com.android.systemui.media.taptotransfer.sender.ChipStateSender;
import com.android.systemui.media.taptotransfer.sender.SenderEndItem;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.temporarydisplay.TemporaryViewDisplayController;
import com.android.systemui.temporarydisplay.ViewPriority;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarEndItem;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaTttSenderCoordinator implements CoreStartable, Dumpable {
    public final ChipbarCoordinator chipbarCoordinator;
    public final CommandQueue commandQueue;
    public final Context context;
    public final int defaultTimeout;
    public final DumpManager dumpManager;
    public final MediaTttSenderLogger logger;
    public final MediaTttFlags mediaTttFlags;
    public final MediaTttSenderUiEventLogger uiEventLogger;
    public final Map stateMap = new LinkedHashMap();
    public final MediaTttSenderCoordinator$commandQueueCallbacks$1 commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1
        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
            MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator.this, i, mediaRoute2Info, iUndoMediaTransferCallback);
        }
    };
    public final MediaTttSenderCoordinator$displayListener$1 displayListener = new TemporaryViewDisplayController.Listener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$displayListener$1
        @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController.Listener
        public final void onInfoPermanentlyRemoved(String str, String str2) {
            MediaTttSenderCoordinator.this.removeIdFromStore(str, str2);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TimeoutLength.values().length];
            try {
                iArr[TimeoutLength.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TimeoutLength.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$displayListener$1] */
    public MediaTttSenderCoordinator(ChipbarCoordinator chipbarCoordinator, CommandQueue commandQueue, Context context, DumpManager dumpManager, MediaTttSenderLogger mediaTttSenderLogger, MediaTttFlags mediaTttFlags, MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger) {
        this.chipbarCoordinator = chipbarCoordinator;
        this.commandQueue = commandQueue;
        this.context = context;
        this.dumpManager = dumpManager;
        this.logger = mediaTttSenderLogger;
        this.mediaTttFlags = mediaTttFlags;
        this.uiEventLogger = mediaTttSenderUiEventLogger;
        this.defaultTimeout = context.getResources().getInteger(R.integer.heads_up_notification_decay);
    }

    public static final void access$updateMediaTapToTransferSenderDisplay(final MediaTttSenderCoordinator mediaTttSenderCoordinator, int i, final MediaRoute2Info mediaRoute2Info, final IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        ChipStateSender chipStateSender;
        String str;
        InstanceId newInstanceId;
        boolean isValidNextState;
        String name;
        int i2;
        mediaTttSenderCoordinator.getClass();
        ChipStateSender.Companion.getClass();
        ChipbarEndItem chipbarEndItem = null;
        try {
        } catch (NoSuchElementException e) {
            Log.e("ChipStateSender", "Could not find requested state " + i, e);
            chipStateSender = null;
        }
        for (ChipStateSender chipStateSender2 : ChipStateSender.values()) {
            if (chipStateSender2.getStateInt() == i) {
                chipStateSender = chipStateSender2;
                if (chipStateSender == null || (str = chipStateSender.name()) == null) {
                    str = "Invalid";
                }
                String id = mediaRoute2Info.getId();
                String clientPackageName = mediaRoute2Info.getClientPackageName();
                final MediaTttSenderLogger mediaTttSenderLogger = mediaTttSenderCoordinator.logger;
                mediaTttSenderLogger.getClass();
                MediaTttLoggerUtils.INSTANCE.getClass();
                LogBuffer logBuffer = mediaTttSenderLogger.buffer;
                MediaTttLoggerUtils.logStateChange(logBuffer, "MediaTttSender", str, id, clientPackageName);
                if (chipStateSender == null) {
                    MediaTttLoggerUtils.logStateChangeError(logBuffer, "MediaTttSender", i);
                    return;
                }
                Pair pair = (Pair) ((LinkedHashMap) mediaTttSenderCoordinator.stateMap).get(mediaRoute2Info.getId());
                ChipStateSender chipStateSender3 = pair != null ? (ChipStateSender) pair.getSecond() : null;
                Pair pair2 = (Pair) ((LinkedHashMap) mediaTttSenderCoordinator.stateMap).get(mediaRoute2Info.getId());
                ChipbarCoordinator chipbarCoordinator = mediaTttSenderCoordinator.chipbarCoordinator;
                if (pair2 == null || (newInstanceId = (InstanceId) pair2.getFirst()) == null) {
                    newInstanceId = chipbarCoordinator.tempViewUiEventLogger.instanceIdSequence.newInstanceId();
                }
                final InstanceId instanceId = newInstanceId;
                ChipStateSender.Companion companion = ChipStateSender.Companion;
                companion.getClass();
                if (chipStateSender3 == null) {
                    ((ChipStateSender.FAR_FROM_RECEIVER) ChipStateSender.FAR_FROM_RECEIVER).getClass();
                    isValidNextState = ChipStateSender.Companion.access$stateIsStartOfSequence(companion, chipStateSender);
                } else {
                    isValidNextState = chipStateSender3 == chipStateSender ? true : chipStateSender3.isValidNextState(chipStateSender);
                }
                String str2 = "FAR_FROM_RECEIVER";
                if (!isValidNextState) {
                    if (chipStateSender3 != null && (name = chipStateSender3.name()) != null) {
                        str2 = name;
                    }
                    String name2 = chipStateSender.name();
                    LogMessage obtain = logBuffer.obtain("MediaTttSender", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderLogger$logInvalidStateTransitionError$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return MotionLayout$$ExternalSyntheticOutline0.m("Cannot display state=", logMessage.getStr2(), " after state=", logMessage.getStr1(), "; invalid transition");
                        }
                    }, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = str2;
                    logMessageImpl.str2 = name2;
                    logBuffer.commit(obtain);
                    return;
                }
                mediaTttSenderCoordinator.uiEventLogger.logger.log(chipStateSender.getUiEvent(), instanceId);
                if (chipStateSender == ChipStateSender.FAR_FROM_RECEIVER) {
                    if (chipStateSender3 == null) {
                        return;
                    }
                    if (chipStateSender3.getTransferStatus() != TransferStatus.IN_PROGRESS && chipStateSender3.getTransferStatus() != TransferStatus.SUCCEEDED) {
                        mediaTttSenderCoordinator.removeIdFromStore(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
                        chipbarCoordinator.removeView(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
                        return;
                    }
                    String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("transferStatus=", chipStateSender3.getTransferStatus().name());
                    LogMessage obtain2 = logBuffer.obtain("MediaTttSender", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderLogger$logRemovalBypass$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return FontProvider$$ExternalSyntheticOutline0.m("Chip removal requested due to ", logMessage.getStr1(), "; however, removal was ignored because ", logMessage.getStr2());
                        }
                    }, null);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                    logMessageImpl2.str1 = "FAR_FROM_RECEIVER";
                    logMessageImpl2.str2 = m;
                    logBuffer.commit(obtain2);
                    return;
                }
                mediaTttSenderCoordinator.stateMap.put(mediaRoute2Info.getId(), new Pair(instanceId, chipStateSender));
                Map map = mediaTttSenderCoordinator.stateMap;
                LogMessage obtain3 = logBuffer.obtain("MediaTttSender", LogLevel.DEBUG, MediaTttSenderLogger$logStateMap$2.INSTANCE, null);
                ((LogMessageImpl) obtain3).str1 = map.toString();
                logBuffer.commit(obtain3);
                chipbarCoordinator.listeners.add(mediaTttSenderCoordinator.displayListener);
                Context context = mediaTttSenderCoordinator.context;
                final String clientPackageName2 = mediaRoute2Info.getClientPackageName();
                String string = StringsKt__StringsJVMKt.isBlank(mediaRoute2Info.getName()) ? context.getString(R.string.media_ttt_default_device_type) : mediaRoute2Info.getName().toString();
                Intrinsics.checkNotNull(string);
                MediaTttUtils.Companion companion2 = MediaTttUtils.Companion;
                Function0 function0 = new Function0() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$createChipbarInfo$icon$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        String str3 = clientPackageName2;
                        if (str3 != null) {
                            MediaTttSenderLogger mediaTttSenderLogger2 = mediaTttSenderLogger;
                            mediaTttSenderLogger2.getClass();
                            MediaTttLoggerUtils.INSTANCE.getClass();
                            MediaTttLoggerUtils.logPackageNotFound(mediaTttSenderLogger2.buffer, "MediaTttSender", str3);
                        }
                        return Unit.INSTANCE;
                    }
                };
                companion2.getClass();
                IconInfo iconInfoFromPackageName = MediaTttUtils.Companion.getIconInfoFromPackageName(context, clientPackageName2, false, function0);
                int i3 = WhenMappings.$EnumSwitchMapping$0[chipStateSender.getTimeoutLength().ordinal()];
                int i4 = mediaTttSenderCoordinator.defaultTimeout;
                if (i3 != 1) {
                    if (i3 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i4 *= 2;
                }
                int i5 = i4;
                TintedIcon tintedIcon = iconInfoFromPackageName.toTintedIcon();
                Text chipTextString = chipStateSender.getChipTextString(context, string);
                SenderEndItem endItem = chipStateSender.getEndItem();
                if (endItem != null) {
                    if (endItem instanceof SenderEndItem.Loading) {
                        chipbarEndItem = ChipbarEndItem.Loading.INSTANCE;
                    } else if (endItem instanceof SenderEndItem.Error) {
                        chipbarEndItem = ChipbarEndItem.Error.INSTANCE;
                    } else {
                        if (!(endItem instanceof SenderEndItem.UndoButton)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (iUndoMediaTransferCallback != null) {
                            final UiEventLogger.UiEventEnum uiEventEnum = ((SenderEndItem.UndoButton) chipStateSender.getEndItem()).uiEventOnClick;
                            final int i6 = ((SenderEndItem.UndoButton) chipStateSender.getEndItem()).newState;
                            chipbarEndItem = new ChipbarEndItem.Button(new Text.Resource(R.string.media_transfer_undo), new View.OnClickListener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$getUndoButton$onClickListener$1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger = MediaTttSenderCoordinator.this.uiEventLogger;
                                    UiEventLogger.UiEventEnum uiEventEnum2 = uiEventEnum;
                                    InstanceId instanceId2 = instanceId;
                                    mediaTttSenderUiEventLogger.getClass();
                                    if (uiEventEnum2 == MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED || uiEventEnum2 == MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED) {
                                        mediaTttSenderUiEventLogger.logger.log(uiEventEnum2, instanceId2);
                                    } else {
                                        String simpleName = Reflection.getOrCreateKotlinClass(MediaTttSenderUiEventLogger.class).getSimpleName();
                                        Intrinsics.checkNotNull(simpleName);
                                        Log.w(simpleName, "Must pass an undo-specific UiEvent.");
                                    }
                                    iUndoMediaTransferCallback.onUndoTriggered();
                                    MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator.this, i6, mediaRoute2Info, null);
                                }
                            });
                        }
                    }
                }
                chipbarCoordinator.displayView(new ChipbarInfo(tintedIcon, chipTextString, chipbarEndItem, chipStateSender.getTransferStatus().getVibrationEffect(), true, "Media Transfer Chip View (Sender)", "MEDIA_TRANSFER_ACTIVATED_SENDER", i5, mediaRoute2Info.getId(), ViewPriority.NORMAL, instanceId));
                return;
            }
        }
        throw new NoSuchElementException("Array contains no element matching the predicate.");
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Current sender states:");
        printWriter.println(this.stateMap.toString());
    }

    public final void removeIdFromStore(String str, String str2) {
        MediaTttSenderLogger mediaTttSenderLogger = this.logger;
        mediaTttSenderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaTttSenderLogger$logStateMapRemoval$2 mediaTttSenderLogger$logStateMapRemoval$2 = new Function1() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderLogger$logStateMapRemoval$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("State removal: id=", logMessage.getStr1(), " reason=", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = mediaTttSenderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTttSender", logLevel, mediaTttSenderLogger$logStateMapRemoval$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
        this.stateMap.remove(str);
        Map map = this.stateMap;
        LogMessage obtain2 = logBuffer.obtain("MediaTttSender", logLevel, MediaTttSenderLogger$logStateMap$2.INSTANCE, null);
        ((LogMessageImpl) obtain2).str1 = map.toString();
        logBuffer.commit(obtain2);
        if (this.stateMap.isEmpty()) {
            this.chipbarCoordinator.listeners.remove(this.displayListener);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        MediaTttFlags mediaTttFlags = this.mediaTttFlags;
        mediaTttFlags.getClass();
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsClassicRelease) mediaTttFlags.featureFlags).isEnabled(Flags.MEDIA_TAP_TO_TRANSFER)) {
            this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
            this.dumpManager.registerNormalDumpable(this);
        }
    }
}
