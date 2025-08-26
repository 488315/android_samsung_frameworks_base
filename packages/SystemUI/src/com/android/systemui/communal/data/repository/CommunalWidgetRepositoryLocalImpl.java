package com.android.systemui.communal.data.repository;

import android.app.backup.BackupManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.communal.data.backup.CommunalBackupUtils;
import com.android.systemui.communal.data.db.CommunalWidgetDao;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda1;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda3;
import com.android.systemui.communal.data.db.DefaultWidgetPopulation;
import com.android.systemui.communal.nano.CommunalHubState;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.communal.shared.model.SpanValueKt;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class CommunalWidgetRepositoryLocalImpl implements CommunalWidgetRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommunalAppWidgetHost appWidgetHost;
    public final BackupManager backupManager;
    public final CommunalBackupUtils backupUtils;
    public final CoroutineScope bgScope;
    public final CommunalWidgetDao communalWidgetDao;
    public final CommunalWidgetHost communalWidgetHost;
    public final Flow communalWidgets;
    public final DefaultWidgetPopulation defaultWidgetPopulation;
    public final Logger logger;
    public final UserManager userManager;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 widgetEntries;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$abortRestoreWidgets$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryLocalImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Logger.i$default(CommunalWidgetRepositoryLocalImpl.this.logger, "Restore widgets aborted", null, 2, null);
            CommunalBackupUtils communalBackupUtils = CommunalWidgetRepositoryLocalImpl.this.backupUtils;
            communalBackupUtils.getClass();
            new File(communalBackupUtils.context.getFilesDir(), "communal_restore").delete();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$addWidget$1, reason: invalid class name and case insensitive filesystem */
    final class C08251 extends SuspendLambda implements Function2 {
        final /* synthetic */ WidgetConfigurator $configurator;
        final /* synthetic */ ComponentName $provider;
        final /* synthetic */ Integer $rank;
        final /* synthetic */ UserHandle $user;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08251(ComponentName componentName, UserHandle userHandle, WidgetConfigurator widgetConfigurator, Integer num, Continuation continuation) {
            super(2, continuation);
            this.$provider = componentName;
            this.$user = userHandle;
            this.$configurator = widgetConfigurator;
            this.$rank = num;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryLocalImpl.this.new C08251(this.$provider, this.$user, this.$configurator, this.$rank, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08251) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0125  */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r1v11 */
        /* JADX WARN: Type inference failed for: r1v12 */
        /* JADX WARN: Type inference failed for: r1v8 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Exception {
            boolean zBooleanValue;
            Integer num;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            Integer num2 = this.label;
            try {
            } catch (Exception e) {
                CommunalWidgetRepositoryLocalImpl.this.logger.e("Error during widget configuration, cleaning up id " + num2, e);
                if (e instanceof CancellationException) {
                    CommunalWidgetRepositoryLocalImpl.this.appWidgetHost.deleteAppWidgetId(num2.intValue());
                    throw e;
                }
                zBooleanValue = false;
                num = num2;
            }
            if (num2 == 0) {
                ResultKt.throwOnFailure(obj);
                Integer numAllocateIdAndBindWidget = CommunalWidgetRepositoryLocalImpl.this.communalWidgetHost.allocateIdAndBindWidget(this.$provider, this.$user);
                if (numAllocateIdAndBindWidget == null) {
                    Logger.e$default(CommunalWidgetRepositoryLocalImpl.this.logger, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed to allocate widget id to ", this.$provider.flattenToString()), null, 2, null);
                    return Unit.INSTANCE;
                }
                CommunalWidgetHost communalWidgetHost = CommunalWidgetRepositoryLocalImpl.this.communalWidgetHost;
                int iIntValue = numAllocateIdAndBindWidget.intValue();
                AppWidgetManager appWidgetManager = (AppWidgetManager) communalWidgetHost.appWidgetManager.orElse(null);
                AppWidgetProviderInfo appWidgetInfo = appWidgetManager != null ? appWidgetManager.getAppWidgetInfo(iIntValue) : null;
                if (this.$configurator != null && appWidgetInfo != null) {
                    CommunalWidgetHost.Companion.getClass();
                    int i = appWidgetInfo.widgetFeatures;
                    boolean z = ((i & 4) == 0 || (i & 1) == 0) ? false : true;
                    if (appWidgetInfo.configure != null && !z) {
                        Logger.i$default(CommunalWidgetRepositoryLocalImpl.this.logger, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Widget ", this.$provider.flattenToString(), " requires configuration."), null, 2, null);
                        WidgetConfigurator widgetConfigurator = this.$configurator;
                        int iIntValue2 = numAllocateIdAndBindWidget.intValue();
                        this.L$0 = numAllocateIdAndBindWidget;
                        this.label = 1;
                        obj = widgetConfigurator.configureWidget(iIntValue2, this);
                        num2 = numAllocateIdAndBindWidget;
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                }
                Logger.i$default(CommunalWidgetRepositoryLocalImpl.this.logger, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skipping configuration for ", this.$provider.flattenToString()), null, 2, null);
                zBooleanValue = true;
                num = numAllocateIdAndBindWidget;
                if (zBooleanValue) {
                    CommunalWidgetRepositoryLocalImpl.this.appWidgetHost.deleteAppWidgetId(num.intValue());
                } else {
                    CommunalWidgetDao communalWidgetDao = CommunalWidgetRepositoryLocalImpl.this.communalWidgetDao;
                    int iIntValue3 = num.intValue();
                    ComponentName componentName = this.$provider;
                    Integer num3 = this.$rank;
                    int userSerialNumber = CommunalWidgetRepositoryLocalImpl.this.userManager.getUserSerialNumber(this.$user.getIdentifier());
                    SpanValue.Fixed fixedM1077boximpl = SpanValue.Fixed.m1077boximpl(3);
                    CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
                    communalWidgetDao_Impl.getClass();
                    ((Long) DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda3(communalWidgetDao_Impl, iIntValue3, componentName, num3, userSerialNumber, fixedM1077boximpl))).getClass();
                    CommunalWidgetRepositoryLocalImpl.this.backupManager.dataChanged();
                }
                Logger.i$default(CommunalWidgetRepositoryLocalImpl.this.logger, "Added widget " + this.$provider.flattenToString() + " at position " + this.$rank + ".", null, 2, null);
                return Unit.INSTANCE;
            }
            if (num2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Integer num4 = (Integer) this.L$0;
            ResultKt.throwOnFailure(obj);
            num2 = num4;
            zBooleanValue = ((Boolean) obj).booleanValue();
            num = num2;
            if (zBooleanValue) {
            }
            Logger.i$default(CommunalWidgetRepositoryLocalImpl.this.logger, "Added widget " + this.$provider.flattenToString() + " at position " + this.$rank + ".", null, 2, null);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$deleteWidget$1, reason: invalid class name and case insensitive filesystem */
    final class C08261 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $widgetId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08261(int i, Continuation continuation) {
            super(2, continuation);
            this.$widgetId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryLocalImpl.this.new C08261(this.$widgetId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08261) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalWidgetDao communalWidgetDao = CommunalWidgetRepositoryLocalImpl.this.communalWidgetDao;
            final int i = this.$widgetId;
            final CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
            communalWidgetDao_Impl.getClass();
            if (((Boolean) DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    CommunalWidgetDao_Impl communalWidgetDao_Impl2 = communalWidgetDao_Impl;
                    communalWidgetDao_Impl2.getClass();
                    boolean z = true;
                    CommunalWidgetDao_Impl$$ExternalSyntheticLambda10 communalWidgetDao_Impl$$ExternalSyntheticLambda10 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(i, 1);
                    RoomDatabase roomDatabase = communalWidgetDao_Impl2.__db;
                    CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) DBUtil.performBlocking(roomDatabase, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda10);
                    if (communalWidgetItem == null) {
                        z = false;
                    } else {
                        final long j = communalWidgetItem.itemId;
                        DBUtil.performBlocking(roomDatabase, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda16
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj3) throws Exception {
                                long j2 = j;
                                SQLiteStatement sQLiteStatementPrepare = ((SQLiteConnection) obj3).prepare("DELETE FROM communal_item_rank_table WHERE uid = ?");
                                try {
                                    sQLiteStatementPrepare.bindLong(1, j2);
                                    sQLiteStatementPrepare.step();
                                    sQLiteStatementPrepare.close();
                                    return null;
                                } catch (Throwable th) {
                                    sQLiteStatementPrepare.close();
                                    throw th;
                                }
                            }
                        });
                        DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl2, new CommunalWidgetItem[]{communalWidgetItem}, 2));
                    }
                    return Boolean.valueOf(z);
                }
            })).booleanValue()) {
                CommunalWidgetRepositoryLocalImpl.this.appWidgetHost.deleteAppWidgetId(this.$widgetId);
                Logger.i$default(CommunalWidgetRepositoryLocalImpl.this.logger, ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(this.$widgetId, "Deleted widget with id ", "."), null, 2, null);
                CommunalWidgetRepositoryLocalImpl.this.backupManager.dataChanged();
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$resizeWidget$1, reason: invalid class name and case insensitive filesystem */
    final class C08271 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $appWidgetId;
        final /* synthetic */ SpanValue $spanValue;
        final /* synthetic */ int $spanY;
        final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08271(int i, SpanValue spanValue, Map<Integer, Integer> map, int i2, Continuation continuation) {
            super(2, continuation);
            this.$appWidgetId = i;
            this.$spanValue = spanValue;
            this.$widgetIdToRankMap = map;
            this.$spanY = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryLocalImpl.this.new C08271(this.$appWidgetId, this.$spanValue, this.$widgetIdToRankMap, this.$spanY, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08271) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalWidgetDao communalWidgetDao = CommunalWidgetRepositoryLocalImpl.this.communalWidgetDao;
            final int i = this.$appWidgetId;
            final SpanValue spanValue = this.$spanValue;
            final Map<Integer, Integer> map = this.$widgetIdToRankMap;
            final CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
            communalWidgetDao_Impl.getClass();
            DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new Function1() { // from class: com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    Map map2 = map;
                    CommunalWidgetDao_Impl communalWidgetDao_Impl2 = communalWidgetDao_Impl;
                    communalWidgetDao_Impl2.getClass();
                    CommunalWidgetDao_Impl$$ExternalSyntheticLambda10 communalWidgetDao_Impl$$ExternalSyntheticLambda10 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda10(i, 1);
                    RoomDatabase roomDatabase = communalWidgetDao_Impl2.__db;
                    CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) DBUtil.performBlocking(roomDatabase, true, false, communalWidgetDao_Impl$$ExternalSyntheticLambda10);
                    if (communalWidgetItem != null) {
                        SpanValue spanValue2 = spanValue;
                        int fixed = SpanValueKt.toFixed(spanValue2);
                        int responsive = SpanValueKt.toResponsive(spanValue2);
                        DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl2, new CommunalWidgetItem(communalWidgetItem.uid, communalWidgetItem.widgetId, communalWidgetItem.componentName, communalWidgetItem.itemId, communalWidgetItem.userSerialNumber, fixed, responsive), 3));
                    }
                    DBUtil.performBlocking(roomDatabase, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl2, map2, 1));
                    return Unit.INSTANCE;
                }
            });
            Logger logger = CommunalWidgetRepositoryLocalImpl.this.logger;
            CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 communalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 = new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(0);
            int i2 = this.$appWidgetId;
            int i3 = this.$spanY;
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, communalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0, null);
            logMessageObtain.setInt1(i2);
            logMessageObtain.setInt2(i3);
            logger.getBuffer().commit(logMessageObtain);
            CommunalWidgetRepositoryLocalImpl.this.backupManager.dataChanged();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1, reason: invalid class name and case insensitive filesystem */
    final class C08281 extends SuspendLambda implements Function2 {
        final /* synthetic */ Map<Integer, Integer> $oldToNewWidgetIdMap;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08281(Map<Integer, Integer> map, Continuation continuation) {
            super(2, continuation);
            this.$oldToNewWidgetIdMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryLocalImpl.this.new C08281(this.$oldToNewWidgetIdMap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08281) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:44:0x01f6  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x01f9 A[SYNTHETIC] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws IOException {
            CommunalHubState.CommunalWidgetItem[] communalWidgetItemArr;
            Map<Integer, Integer> map;
            UserHandle userHandle;
            CommunalHubState.CommunalWidgetItem communalWidgetItem;
            boolean z;
            String str;
            UserHandle userForAncestralSerialNumber;
            boolean z2 = true;
            String str2 = "communal_restore";
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                CommunalBackupUtils communalBackupUtils = CommunalWidgetRepositoryLocalImpl.this.backupUtils;
                communalBackupUtils.getClass();
                FileInputStream fileInputStream = new FileInputStream(new File(communalBackupUtils.context.getFilesDir(), "communal_restore"));
                byte[] allBytes = fileInputStream.readAllBytes();
                fileInputStream.close();
                allBytes.getClass();
                CommunalHubState from = CommunalHubState.parseFrom(allBytes);
                UserHandle mainUser = CommunalWidgetRepositoryLocalImpl.this.userManager.getMainUser();
                if (mainUser == null) {
                    Logger.w$default(CommunalWidgetRepositoryLocalImpl.this.logger, "Skipped restoring widgets because device does not have a main user", null, 2, null);
                    return Unit.INSTANCE;
                }
                List list = ArraysKt___ArraysKt.toList(CommunalWidgetRepositoryLocalImpl.this.appWidgetHost.getAppWidgetIds());
                ArrayList arrayList = new ArrayList(list);
                CommunalHubState.CommunalWidgetItem[] communalWidgetItemArr2 = from.widgets;
                ArrayList arrayList2 = new ArrayList(communalWidgetItemArr2.length);
                for (CommunalHubState.CommunalWidgetItem communalWidgetItem2 : communalWidgetItemArr2) {
                    arrayList2.add(new Integer(communalWidgetItem2.userSerialNumber));
                }
                List listDistinct = CollectionsKt___CollectionsKt.distinct(arrayList2);
                CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl = CommunalWidgetRepositoryLocalImpl.this;
                int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(listDistinct, 10));
                if (iMapCapacity < 16) {
                    iMapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                for (Object obj2 : listDistinct) {
                    int iIntValue = ((Number) obj2).intValue();
                    if (iIntValue == -1) {
                        z = z2;
                        str = str2;
                        userForAncestralSerialNumber = mainUser;
                    } else {
                        z = z2;
                        str = str2;
                        userForAncestralSerialNumber = communalWidgetRepositoryLocalImpl.backupManager.getUserForAncestralSerialNumber(iIntValue);
                        if (userForAncestralSerialNumber == null) {
                            userForAncestralSerialNumber = null;
                        }
                    }
                    linkedHashMap.put(obj2, userForAncestralSerialNumber);
                    z2 = z;
                    str2 = str;
                }
                boolean z3 = z2;
                String str3 = str2;
                Logger logger = CommunalWidgetRepositoryLocalImpl.this.logger;
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(2), null);
                logMessageObtain.setStr1(linkedHashMap.toString());
                logger.getBuffer().commit(logMessageObtain);
                LinkedHashSet<CommunalHubState.CommunalWidgetItem> linkedHashSet = new LinkedHashSet();
                CommunalHubState.CommunalWidgetItem[] communalWidgetItemArr3 = from.widgets;
                Map<Integer, Integer> map2 = this.$oldToNewWidgetIdMap;
                CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl2 = CommunalWidgetRepositoryLocalImpl.this;
                ArrayList arrayList3 = new ArrayList();
                int length = communalWidgetItemArr3.length;
                int i = 0;
                while (i < length) {
                    CommunalHubState.CommunalWidgetItem communalWidgetItem3 = communalWidgetItemArr3[i];
                    Integer num = (Integer) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(communalWidgetItem3.widgetId, map2);
                    int iIntValue2 = num != null ? num.intValue() : communalWidgetItem3.widgetId;
                    if (list.contains(new Integer(iIntValue2))) {
                        communalWidgetItemArr = communalWidgetItemArr3;
                        map = map2;
                        userHandle = mainUser;
                        UserHandle userHandle2 = (UserHandle) linkedHashMap.get(new Integer(communalWidgetItem3.userSerialNumber));
                        if (userHandle2 == null) {
                            Logger logger2 = communalWidgetRepositoryLocalImpl2.logger;
                            LogMessage logMessageObtain2 = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.DEBUG, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(4), null);
                            logMessageObtain2.setInt1(communalWidgetItem3.widgetId);
                            logMessageObtain2.setInt2(communalWidgetItem3.userSerialNumber);
                            logger2.getBuffer().commit(logMessageObtain2);
                        } else if (userHandle2.getIdentifier() != userHandle.getIdentifier()) {
                            Logger logger3 = communalWidgetRepositoryLocalImpl2.logger;
                            LogMessage logMessageObtain3 = logger3.getBuffer().obtain(logger3.getTag(), LogLevel.DEBUG, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(5), null);
                            logMessageObtain3.setInt1(communalWidgetItem3.widgetId);
                            logMessageObtain3.setInt2(userHandle2.getIdentifier());
                            logger3.getBuffer().commit(logMessageObtain3);
                            linkedHashSet.add(communalWidgetItem3);
                        } else {
                            arrayList.remove(new Integer(iIntValue2));
                            communalWidgetItem = new CommunalHubState.CommunalWidgetItem();
                            communalWidgetItem.widgetId = iIntValue2;
                            communalWidgetItem.componentName = communalWidgetItem3.componentName;
                            communalWidgetItem.rank = communalWidgetItem3.rank;
                            communalWidgetItem.userSerialNumber = communalWidgetRepositoryLocalImpl2.userManager.getUserSerialNumber(userHandle2.getIdentifier());
                            communalWidgetItem.spanY = communalWidgetItem3.spanY;
                            if (communalWidgetItem == null) {
                                arrayList3.add(communalWidgetItem);
                            }
                            i++;
                            communalWidgetItemArr3 = communalWidgetItemArr;
                            map2 = map;
                            mainUser = userHandle;
                        }
                    } else {
                        Logger logger4 = communalWidgetRepositoryLocalImpl2.logger;
                        communalWidgetItemArr = communalWidgetItemArr3;
                        map = map2;
                        userHandle = mainUser;
                        LogMessage logMessageObtain4 = logger4.getBuffer().obtain(logger4.getTag(), LogLevel.DEBUG, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(3), null);
                        logMessageObtain4.setInt1(communalWidgetItem3.widgetId);
                        logMessageObtain4.setInt2(iIntValue2);
                        logger4.getBuffer().commit(logMessageObtain4);
                    }
                    communalWidgetItem = null;
                    if (communalWidgetItem == null) {
                    }
                    i++;
                    communalWidgetItemArr3 = communalWidgetItemArr;
                    map2 = map;
                    mainUser = userHandle;
                }
                CommunalHubState communalHubState = new CommunalHubState();
                communalHubState.widgets = (CommunalHubState.CommunalWidgetItem[]) arrayList3.toArray(new CommunalHubState.CommunalWidgetItem[0]);
                CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl3 = CommunalWidgetRepositoryLocalImpl.this;
                communalWidgetRepositoryLocalImpl3.defaultWidgetPopulation.skipReason = DefaultWidgetPopulation.SkipReason.RESTORED_FROM_BACKUP;
                Logger.i$default(communalWidgetRepositoryLocalImpl3.logger, "Restoring communal database:\n" + communalHubState, null, 2, null);
                CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) CommunalWidgetRepositoryLocalImpl.this.communalWidgetDao;
                communalWidgetDao_Impl.getClass();
                int i2 = 0;
                DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, z3, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl, communalHubState, i2));
                CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl4 = CommunalWidgetRepositoryLocalImpl.this;
                for (CommunalHubState.CommunalWidgetItem communalWidgetItem4 : linkedHashSet) {
                    Object obj3 = linkedHashMap.get(new Integer(communalWidgetItem4.userSerialNumber));
                    obj3.getClass();
                    UserHandle userHandle3 = (UserHandle) obj3;
                    Logger logger5 = communalWidgetRepositoryLocalImpl4.logger;
                    LogMessage logMessageObtain5 = logger5.getBuffer().obtain(logger5.getTag(), LogLevel.INFO, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(6), null);
                    logMessageObtain5.setInt1(userHandle3.getIdentifier());
                    logMessageObtain5.setInt2(communalWidgetItem4.widgetId);
                    logMessageObtain5.setStr1(communalWidgetItem4.componentName);
                    logger5.getBuffer().commit(logMessageObtain5);
                    ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(communalWidgetItem4.componentName);
                    componentNameUnflattenFromString.getClass();
                    communalWidgetRepositoryLocalImpl4.addWidget(componentNameUnflattenFromString, userHandle3, new Integer(communalWidgetItem4.rank), null);
                }
                CommunalBackupUtils communalBackupUtils2 = CommunalWidgetRepositoryLocalImpl.this.backupUtils;
                communalBackupUtils2.getClass();
                new File(communalBackupUtils2.context.getFilesDir(), str3).delete();
                CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl5 = CommunalWidgetRepositoryLocalImpl.this;
                int size = arrayList.size();
                while (i2 < size) {
                    Object obj4 = arrayList.get(i2);
                    i2++;
                    int iIntValue3 = ((Number) obj4).intValue();
                    Logger logger6 = communalWidgetRepositoryLocalImpl5.logger;
                    LogMessage logMessageObtain6 = logger6.getBuffer().obtain(logger6.getTag(), LogLevel.INFO, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(7), null);
                    logMessageObtain6.setInt1(iIntValue3);
                    logger6.getBuffer().commit(logMessageObtain6);
                    communalWidgetRepositoryLocalImpl5.appWidgetHost.deleteAppWidgetId(iIntValue3);
                }
                CommunalWidgetRepositoryLocalImpl.this.communalWidgetHost.refreshProviders();
                return Unit.INSTANCE;
            } catch (Exception e) {
                Logger logger7 = CommunalWidgetRepositoryLocalImpl.this.logger;
                LogMessage logMessageObtain7 = logger7.getBuffer().obtain(logger7.getTag(), LogLevel.ERROR, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(1), null);
                logMessageObtain7.setStr1(e.getLocalizedMessage());
                logger7.getBuffer().commit(logMessageObtain7);
                CommunalWidgetRepositoryLocalImpl.this.abortRestoreWidgets();
                return Unit.INSTANCE;
            }
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$updateWidgetOrder$1, reason: invalid class name and case insensitive filesystem */
    final class C08291 extends SuspendLambda implements Function2 {
        final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08291(Map<Integer, Integer> map, Continuation continuation) {
            super(2, continuation);
            this.$widgetIdToRankMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryLocalImpl.this.new C08291(this.$widgetIdToRankMap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08291) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int i = 1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommunalWidgetDao communalWidgetDao = CommunalWidgetRepositoryLocalImpl.this.communalWidgetDao;
            Map<Integer, Integer> map = this.$widgetIdToRankMap;
            CommunalWidgetDao_Impl communalWidgetDao_Impl = (CommunalWidgetDao_Impl) communalWidgetDao;
            communalWidgetDao_Impl.getClass();
            DBUtil.performBlocking(communalWidgetDao_Impl.__db, false, true, new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(communalWidgetDao_Impl, map, i));
            Logger logger = CommunalWidgetRepositoryLocalImpl.this.logger;
            CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 communalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0 = new CommunalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0(8);
            Map<Integer, Integer> map2 = this.$widgetIdToRankMap;
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, communalWidgetRepositoryLocalImpl$resizeWidget$1$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(map2.toString());
            logger.getBuffer().commit(logMessageObtain);
            CommunalWidgetRepositoryLocalImpl.this.backupManager.dataChanged();
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public CommunalWidgetRepositoryLocalImpl(CommunalAppWidgetHost communalAppWidgetHost, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalWidgetHost communalWidgetHost, CommunalWidgetDao communalWidgetDao, LogBuffer logBuffer, BackupManager backupManager, CommunalBackupUtils communalBackupUtils, PackageChangeRepository packageChangeRepository, UserManager userManager, DefaultWidgetPopulation defaultWidgetPopulation) {
        this.appWidgetHost = communalAppWidgetHost;
        this.bgScope = coroutineScope;
        this.communalWidgetHost = communalWidgetHost;
        this.communalWidgetDao = communalWidgetDao;
        this.backupManager = backupManager;
        this.backupUtils = communalBackupUtils;
        this.userManager = userManager;
        this.defaultWidgetPopulation = defaultWidgetPopulation;
        this.logger = new Logger(logBuffer, "CommunalWidgetRepositoryLocalImpl");
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((CommunalWidgetDao_Impl) communalWidgetDao).getWidgets(), communalWidgetHost.appWidgetProviders, new CommunalWidgetRepositoryLocalImpl$widgetEntries$1(null));
        this.widgetEntries = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.communalWidgets = FlowKt.flowOn(FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new CommunalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1(null, packageChangeRepository, this)), coroutineDispatcher);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void abortRestoreWidgets() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void addWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new C08251(componentName, userHandle, widgetConfigurator, num, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void deleteWidget(int i) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new C08261(i, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final Flow getCommunalWidgets() {
        return this.communalWidgets;
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void resizeWidget(Map map, int i, int i2) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new C08271(i, SpanValue.Fixed.m1077boximpl(i2), map, i2, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void restoreWidgets(Map map) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new C08281(map, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void updateWidgetOrder(Map map) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new C08291(map, null), 7);
    }

    public final class CommunalWidgetEntry {
        public final int appWidgetId;
        public final String componentName;
        public final AppWidgetProviderInfo providerInfo;
        public final int rank;
        public final int spanY;

        public CommunalWidgetEntry(int i, String str, int i2, int i3, AppWidgetProviderInfo appWidgetProviderInfo) {
            this.appWidgetId = i;
            this.componentName = str;
            this.rank = i2;
            this.spanY = i3;
            this.providerInfo = appWidgetProviderInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CommunalWidgetEntry)) {
                return false;
            }
            CommunalWidgetEntry communalWidgetEntry = (CommunalWidgetEntry) obj;
            return this.appWidgetId == communalWidgetEntry.appWidgetId && Intrinsics.areEqual(this.componentName, communalWidgetEntry.componentName) && this.rank == communalWidgetEntry.rank && this.spanY == communalWidgetEntry.spanY && Intrinsics.areEqual(this.providerInfo, communalWidgetEntry.providerInfo);
        }

        public final int hashCode() {
            int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.spanY, ReorderTile$$ExternalSyntheticOutline0.m(this.rank, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.appWidgetId) * 31, 31, this.componentName), 31), 31);
            AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
            return iM + (appWidgetProviderInfo == null ? 0 : appWidgetProviderInfo.hashCode());
        }

        public final String toString() {
            return "CommunalWidgetEntry(appWidgetId=" + this.appWidgetId + ", componentName=" + this.componentName + ", rank=" + this.rank + ", spanY=" + this.spanY + ", providerInfo=" + this.providerInfo + ")";
        }

        public /* synthetic */ CommunalWidgetEntry(int i, String str, int i2, int i3, AppWidgetProviderInfo appWidgetProviderInfo, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, str, i2, i3, (i4 & 16) != 0 ? null : appWidgetProviderInfo);
        }
    }
}
