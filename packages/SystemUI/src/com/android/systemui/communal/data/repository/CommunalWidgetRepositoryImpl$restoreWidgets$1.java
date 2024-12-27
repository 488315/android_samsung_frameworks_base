package com.android.systemui.communal.data.repository;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.room.util.DBUtil;
import com.android.systemui.communal.data.backup.CommunalBackupUtils;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda2;
import com.android.systemui.communal.nano.CommunalHubState;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

final class CommunalWidgetRepositoryImpl$restoreWidgets$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Map<Integer, Integer> $oldToNewWidgetIdMap;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryImpl this$0;

    public CommunalWidgetRepositoryImpl$restoreWidgets$1(CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl, Map<Integer, Integer> map, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryImpl;
        this.$oldToNewWidgetIdMap = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryImpl$restoreWidgets$1(this.this$0, this.$oldToNewWidgetIdMap, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryImpl$restoreWidgets$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CommunalHubState.CommunalWidgetItem[] communalWidgetItemArr;
        List list;
        CommunalHubState.CommunalWidgetItem communalWidgetItem;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            CommunalBackupUtils communalBackupUtils = this.this$0.backupUtils;
            communalBackupUtils.getClass();
            FileInputStream fileInputStream = new FileInputStream(new File(communalBackupUtils.context.getFilesDir(), "communal_restore"));
            byte[] readAllBytes = fileInputStream.readAllBytes();
            fileInputStream.close();
            Intrinsics.checkNotNull(readAllBytes);
            CommunalHubState parseFrom = CommunalHubState.parseFrom(readAllBytes);
            List list2 = ArraysKt___ArraysKt.toList(this.this$0.appWidgetHost.getAppWidgetIds());
            ArrayList arrayList = new ArrayList(list2);
            CommunalHubState.CommunalWidgetItem[] communalWidgetItemArr2 = parseFrom.widgets;
            Map<Integer, Integer> map = this.$oldToNewWidgetIdMap;
            CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl = this.this$0;
            ArrayList arrayList2 = new ArrayList();
            int length = communalWidgetItemArr2.length;
            int i = 0;
            while (i < length) {
                CommunalHubState.CommunalWidgetItem communalWidgetItem2 = communalWidgetItemArr2[i];
                Integer num = map.get(new Integer(communalWidgetItem2.widgetId));
                int intValue = num != null ? num.intValue() : communalWidgetItem2.widgetId;
                if (list2.contains(new Integer(intValue))) {
                    communalWidgetItemArr = communalWidgetItemArr2;
                    list = list2;
                    arrayList.remove(new Integer(intValue));
                    communalWidgetItem = new CommunalHubState.CommunalWidgetItem();
                    communalWidgetItem.widgetId = intValue;
                    communalWidgetItem.componentName = communalWidgetItem2.componentName;
                    communalWidgetItem.rank = communalWidgetItem2.rank;
                } else {
                    Logger logger = communalWidgetRepositoryImpl.logger;
                    communalWidgetItemArr = communalWidgetItemArr2;
                    list = list2;
                    LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$restoreWidgets$1$newWidgets$1$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            LogMessage logMessage = (LogMessage) obj2;
                            return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Skipped restoring widget (old:", " new:", ") because it is not registered with host");
                        }
                    }, null);
                    obtain.setInt1(communalWidgetItem2.widgetId);
                    obtain.setInt2(intValue);
                    logger.getBuffer().commit(obtain);
                    communalWidgetItem = null;
                }
                if (communalWidgetItem != null) {
                    arrayList2.add(communalWidgetItem);
                }
                i++;
                communalWidgetItemArr2 = communalWidgetItemArr;
                list2 = list;
            }
            CommunalHubState communalHubState = new CommunalHubState();
            communalHubState.widgets = (CommunalHubState.CommunalWidgetItem[]) arrayList2.toArray(new CommunalHubState.CommunalWidgetItem[0]);
            Logger.i$default(this.this$0.logger, "Restoring communal database " + communalHubState, null, 2, null);
            CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) this.this$0.communalWidgetDao;
            communalWidgetDao_Impl.getClass();
            DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda2(communalWidgetDao_Impl, communalHubState, 1));
            CommunalBackupUtils communalBackupUtils2 = this.this$0.backupUtils;
            communalBackupUtils2.getClass();
            new File(communalBackupUtils2.context.getFilesDir(), "communal_restore").delete();
            CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl2 = this.this$0;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                int intValue2 = ((Number) it.next()).intValue();
                Logger logger2 = communalWidgetRepositoryImpl2.logger;
                LogMessage obtain2 = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.INFO, new Function1() { // from class: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$restoreWidgets$1$3$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj2).getInt1(), "Deleting widget ", " from host since it has not been restored");
                    }
                }, null);
                obtain2.setInt1(intValue2);
                logger2.getBuffer().commit(obtain2);
                communalWidgetRepositoryImpl2.appWidgetHost.deleteAppWidgetId(intValue2);
            }
            this.this$0.communalWidgetHost.refreshProviders();
            return Unit.INSTANCE;
        } catch (Exception e) {
            Logger logger3 = this.this$0.logger;
            LogMessage obtain3 = logger3.getBuffer().obtain(logger3.getTag(), LogLevel.ERROR, new Function1() { // from class: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$restoreWidgets$1.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed reading restore data from disk: ", ((LogMessage) obj2).getStr1());
                }
            }, null);
            obtain3.setStr1(e.getLocalizedMessage());
            logger3.getBuffer().commit(obtain3);
            this.this$0.abortRestoreWidgets();
            return Unit.INSTANCE;
        }
    }
}
