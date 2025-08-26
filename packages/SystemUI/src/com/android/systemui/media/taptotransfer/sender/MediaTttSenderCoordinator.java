package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.util.Log;
import android.view.View;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.taptotransfer.common.IconInfo;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils;
import com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils$$ExternalSyntheticLambda0;
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
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class MediaTttSenderCoordinator implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ChipbarCoordinator chipbarCoordinator;
    public final CommandQueue commandQueue;
    public final Context context;
    public final int defaultTimeout;
    public final DumpManager dumpManager;
    public final MediaTttSenderLogger logger;
    public final MediaTttSenderUiEventLogger uiEventLogger;
    public final Map stateMap = new LinkedHashMap();
    public final MediaTttSenderCoordinator$commandQueueCallbacks$1 commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1
        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws Throwable {
            MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(this.this$0, i, mediaRoute2Info, iUndoMediaTransferCallback);
        }
    };
    public final MediaTttSenderCoordinator$displayListener$1 displayListener = new TemporaryViewDisplayController.Listener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$displayListener$1
        @Override // com.android.systemui.temporarydisplay.TemporaryViewDisplayController.Listener
        public final void onInfoPermanentlyRemoved(String str, String str2) {
            int i = MediaTttSenderCoordinator.$r8$clinit;
            this.this$0.removeIdFromStore(str, str2);
        }
    };

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
    public MediaTttSenderCoordinator(ChipbarCoordinator chipbarCoordinator, CommandQueue commandQueue, Context context, DumpManager dumpManager, MediaTttSenderLogger mediaTttSenderLogger, MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger) {
        this.chipbarCoordinator = chipbarCoordinator;
        this.commandQueue = commandQueue;
        this.context = context;
        this.dumpManager = dumpManager;
        this.logger = mediaTttSenderLogger;
        this.uiEventLogger = mediaTttSenderUiEventLogger;
        this.defaultTimeout = context.getResources().getInteger(R.integer.heads_up_notification_decay);
    }

    public static final void access$updateMediaTapToTransferSenderDisplay(final MediaTttSenderCoordinator mediaTttSenderCoordinator, int i, final MediaRoute2Info mediaRoute2Info, final IUndoMediaTransferCallback iUndoMediaTransferCallback) throws Throwable {
        String strName;
        InstanceId instanceIdNewInstanceId;
        boolean zIsValidNextState;
        InstanceId instanceId;
        String strName2;
        int i2;
        mediaTttSenderCoordinator.getClass();
        ChipStateSender.Companion.getClass();
        ChipbarEndItem button = null;
        try {
        } catch (NoSuchElementException e) {
            Log.e("ChipStateSender", "Could not find requested state " + i, e);
            chipStateSender = null;
        }
        for (ChipStateSender chipStateSender : ChipStateSender.values()) {
            if (chipStateSender.getStateInt() == i) {
                if (chipStateSender == null || (strName = chipStateSender.name()) == null) {
                    strName = "Invalid";
                }
                String id = mediaRoute2Info.getId();
                String clientPackageName = mediaRoute2Info.getClientPackageName();
                final MediaTttSenderLogger mediaTttSenderLogger = mediaTttSenderCoordinator.logger;
                mediaTttSenderLogger.getClass();
                MediaTttLoggerUtils.INSTANCE.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                MediaTttLoggerUtils$$ExternalSyntheticLambda0 mediaTttLoggerUtils$$ExternalSyntheticLambda0 = new MediaTttLoggerUtils$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = mediaTttSenderLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MediaTttSender", logLevel, mediaTttLoggerUtils$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = strName;
                logMessageImpl.str2 = id;
                logMessageImpl.str3 = clientPackageName;
                logBuffer.commit(logMessageObtain);
                if (chipStateSender == null) {
                    LogMessage logMessageObtain2 = logBuffer.obtain("MediaTttSender", LogLevel.ERROR, new MediaTttLoggerUtils$$ExternalSyntheticLambda0(0), null);
                    ((LogMessageImpl) logMessageObtain2).int1 = i;
                    logBuffer.commit(logMessageObtain2);
                    return;
                }
                Pair pair = (Pair) ((LinkedHashMap) mediaTttSenderCoordinator.stateMap).get(mediaRoute2Info.getId());
                ChipStateSender chipStateSender2 = pair != null ? (ChipStateSender) pair.getSecond() : null;
                Pair pair2 = (Pair) ((LinkedHashMap) mediaTttSenderCoordinator.stateMap).get(mediaRoute2Info.getId());
                ChipbarCoordinator chipbarCoordinator = mediaTttSenderCoordinator.chipbarCoordinator;
                if (pair2 == null || (instanceIdNewInstanceId = (InstanceId) pair2.getFirst()) == null) {
                    instanceIdNewInstanceId = chipbarCoordinator.tempViewUiEventLogger.instanceIdSequence.newInstanceId();
                }
                ChipStateSender.Companion companion = ChipStateSender.Companion;
                companion.getClass();
                if (chipStateSender2 == null) {
                    ((ChipStateSender.FAR_FROM_RECEIVER) ChipStateSender.FAR_FROM_RECEIVER).getClass();
                    zIsValidNextState = ChipStateSender.Companion.access$stateIsStartOfSequence(companion, chipStateSender);
                } else {
                    zIsValidNextState = chipStateSender2 == chipStateSender ? true : chipStateSender2.isValidNextState(chipStateSender);
                }
                String str = "FAR_FROM_RECEIVER";
                if (!zIsValidNextState) {
                    if (chipStateSender2 != null && (strName2 = chipStateSender2.name()) != null) {
                        str = strName2;
                    }
                    String strName3 = chipStateSender.name();
                    LogMessage logMessageObtain3 = logBuffer.obtain("MediaTttSender", LogLevel.ERROR, new MediaTttSenderLogger$$ExternalSyntheticLambda0(2), null);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                    logMessageImpl2.str1 = str;
                    logMessageImpl2.str2 = strName3;
                    logBuffer.commit(logMessageObtain3);
                    return;
                }
                mediaTttSenderCoordinator.uiEventLogger.logger.log(chipStateSender.getUiEvent(), instanceIdNewInstanceId);
                if (chipStateSender == ChipStateSender.FAR_FROM_RECEIVER) {
                    if (chipStateSender2 == null) {
                        return;
                    }
                    if (chipStateSender2.getTransferStatus() != TransferStatus.IN_PROGRESS && chipStateSender2.getTransferStatus() != TransferStatus.SUCCEEDED) {
                        mediaTttSenderCoordinator.removeIdFromStore(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
                        chipbarCoordinator.removeView(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
                        return;
                    }
                    String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("transferStatus=", chipStateSender2.getTransferStatus().name());
                    LogMessage logMessageObtain4 = logBuffer.obtain("MediaTttSender", logLevel, new MediaTttSenderLogger$$ExternalSyntheticLambda0(3), null);
                    LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain4;
                    logMessageImpl3.str1 = "FAR_FROM_RECEIVER";
                    logMessageImpl3.str2 = strM;
                    logBuffer.commit(logMessageObtain4);
                    return;
                }
                mediaTttSenderCoordinator.stateMap.put(mediaRoute2Info.getId(), new Pair(instanceIdNewInstanceId, chipStateSender));
                Map map = mediaTttSenderCoordinator.stateMap;
                LogMessage logMessageObtain5 = logBuffer.obtain("MediaTttSender", logLevel, new MediaTttSenderLogger$$ExternalSyntheticLambda0(1), null);
                ((LogMessageImpl) logMessageObtain5).str1 = map.toString();
                logBuffer.commit(logMessageObtain5);
                chipbarCoordinator.listeners.add(mediaTttSenderCoordinator.displayListener);
                Context context = mediaTttSenderCoordinator.context;
                final String clientPackageName2 = mediaRoute2Info.getClientPackageName();
                String string = StringsKt__StringsKt.isBlank(mediaRoute2Info.getName()) ? context.getString(R.string.media_ttt_default_device_type) : mediaRoute2Info.getName().toString();
                string.getClass();
                MediaTttUtils.Companion companion2 = MediaTttUtils.Companion;
                Function0 function0 = new Function0() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i3 = MediaTttSenderCoordinator.$r8$clinit;
                        String str2 = clientPackageName2;
                        if (str2 != null) {
                            MediaTttSenderLogger mediaTttSenderLogger2 = mediaTttSenderLogger;
                            mediaTttSenderLogger2.getClass();
                            MediaTttLoggerUtils.INSTANCE.getClass();
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            MediaTttLoggerUtils$$ExternalSyntheticLambda0 mediaTttLoggerUtils$$ExternalSyntheticLambda02 = new MediaTttLoggerUtils$$ExternalSyntheticLambda0(1);
                            LogBuffer logBuffer2 = mediaTttSenderLogger2.buffer;
                            LogMessage logMessageObtain6 = logBuffer2.obtain("MediaTttSender", logLevel2, mediaTttLoggerUtils$$ExternalSyntheticLambda02, null);
                            ((LogMessageImpl) logMessageObtain6).str1 = str2;
                            logBuffer2.commit(logMessageObtain6);
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
                        button = ChipbarEndItem.Loading.INSTANCE;
                    } else if (endItem instanceof SenderEndItem.Error) {
                        button = ChipbarEndItem.Error.INSTANCE;
                    } else {
                        if (!(endItem instanceof SenderEndItem.UndoButton)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (iUndoMediaTransferCallback != null) {
                            final UiEventLogger.UiEventEnum uiEventEnum = ((SenderEndItem.UndoButton) chipStateSender.getEndItem()).uiEventOnClick;
                            final int i6 = ((SenderEndItem.UndoButton) chipStateSender.getEndItem()).newState;
                            final InstanceId instanceId2 = instanceIdNewInstanceId;
                            instanceId = instanceId2;
                            button = new ChipbarEndItem.Button(new Text.Resource(R.string.media_transfer_undo), new View.OnClickListener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$getUndoButton$onClickListener$1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) throws Throwable {
                                    MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger = this.this$0.uiEventLogger;
                                    UiEventLogger.UiEventEnum uiEventEnum2 = uiEventEnum;
                                    InstanceId instanceId3 = instanceId2;
                                    mediaTttSenderUiEventLogger.getClass();
                                    if (uiEventEnum2 == MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED || uiEventEnum2 == MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED) {
                                        mediaTttSenderUiEventLogger.logger.log(uiEventEnum2, instanceId3);
                                    } else {
                                        String simpleName = Reflection.getOrCreateKotlinClass(MediaTttSenderUiEventLogger.class).getSimpleName();
                                        simpleName.getClass();
                                        Log.w(simpleName, "Must pass an undo-specific UiEvent.");
                                    }
                                    iUndoMediaTransferCallback.onUndoTriggered();
                                    MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(this.this$0, i6, mediaRoute2Info, null);
                                }
                            });
                        }
                    }
                    instanceId = instanceIdNewInstanceId;
                } else {
                    instanceId = instanceIdNewInstanceId;
                }
                chipbarCoordinator.displayView(new ChipbarInfo(tintedIcon, chipTextString, button, chipStateSender.getTransferStatus().getVibrationEffect(), true, "Media Transfer Chip View (Sender)", "MEDIA_TRANSFER_ACTIVATED_SENDER", i5, mediaRoute2Info.getId(), ViewPriority.NORMAL, instanceId));
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
        MediaTttSenderLogger$$ExternalSyntheticLambda0 mediaTttSenderLogger$$ExternalSyntheticLambda0 = new MediaTttSenderLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = mediaTttSenderLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaTttSender", logLevel, mediaTttSenderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(logMessageObtain);
        this.stateMap.remove(str);
        Map map = this.stateMap;
        LogMessage logMessageObtain2 = logBuffer.obtain("MediaTttSender", logLevel, new MediaTttSenderLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) logMessageObtain2).str1 = map.toString();
        logBuffer.commit(logMessageObtain2);
        if (this.stateMap.isEmpty()) {
            this.chipbarCoordinator.listeners.remove(this.displayListener);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
        this.dumpManager.registerNormalDumpable(this);
    }
}
