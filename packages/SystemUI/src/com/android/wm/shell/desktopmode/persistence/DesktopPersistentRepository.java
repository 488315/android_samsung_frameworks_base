package com.android.wm.shell.desktopmode.persistence;

import android.content.Context;
import androidx.datastore.DataStoreFile;
import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.Serializer;
import androidx.datastore.core.UncloseableOutputStream;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository;
import com.android.wm.shell.desktopmode.persistence.DesktopTask;
import java.io.InputStream;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopPersistentRepository {
    public static final Companion Companion = new Companion(null);
    public final DataStore dataStore;
    public final FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 dataStoreFlow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class DesktopPersistentRepositoriesSerializer implements Serializer {
            public static final DesktopPersistentRepositoriesSerializer INSTANCE = new DesktopPersistentRepositoriesSerializer();
            public static final DesktopPersistentRepositories defaultValue = DesktopPersistentRepositories.getDefaultInstance();

            private DesktopPersistentRepositoriesSerializer() {
            }

            @Override // androidx.datastore.core.Serializer
            public final Object getDefaultValue() {
                return defaultValue;
            }

            @Override // androidx.datastore.core.Serializer
            public final Object readFrom(InputStream inputStream) {
                try {
                    return DesktopPersistentRepositories.parseFrom(inputStream);
                } catch (InvalidProtocolBufferException e) {
                    throw new CorruptionException("Cannot read proto.", e);
                }
            }

            @Override // androidx.datastore.core.Serializer
            public final Unit writeTo(Object obj, UncloseableOutputStream uncloseableOutputStream) {
                ((DesktopPersistentRepositories) obj).writeTo(uncloseableOutputStream);
                return Unit.INSTANCE;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static DesktopTask createDesktopTask(int i, DesktopTaskState desktopTaskState, DesktopTaskTilingState desktopTaskTilingState) {
            DesktopTask.Builder newBuilder = DesktopTask.newBuilder();
            newBuilder.copyOnWrite();
            DesktopTask.m3246$$Nest$msetTaskId((DesktopTask) newBuilder.instance, i);
            newBuilder.copyOnWrite();
            DesktopTask.m3244$$Nest$msetDesktopTaskState((DesktopTask) newBuilder.instance, desktopTaskState);
            newBuilder.copyOnWrite();
            DesktopTask.m3245$$Nest$msetDesktopTaskTilingState((DesktopTask) newBuilder.instance, desktopTaskTilingState);
            return (DesktopTask) newBuilder.build();
        }

        private Companion() {
        }
    }

    public DesktopPersistentRepository(DataStore dataStore) {
        this.dataStore = dataStore;
        this.dataStoreFlow = new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(dataStore.getData(), new DesktopPersistentRepository$dataStoreFlow$1(null));
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(8:5|6|7|8|(1:(1:11)(2:17|18))(3:19|20|(1:22))|12|13|14))|25|6|7|8|(0)(0)|12|13|14) */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005c, code lost:
    
        android.util.Log.e("DesktopPersistenceRepo", "Error in updating desktop mode related data, data is stored in a file named desktop_persistent_repositories.pb", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object addOrUpdateDesktop(int r17, int r18, android.util.ArraySet r19, android.util.ArraySet r20, java.util.ArrayList r21, java.lang.Integer r22, java.lang.Integer r23, int r24, int r25, kotlin.coroutines.jvm.internal.ContinuationImpl r26) {
        /*
            r16 = this;
            r2 = r16
            r0 = r26
            boolean r1 = r0 instanceof com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$1
            if (r1 == 0) goto L18
            r1 = r0
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$1 r1 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$1) r1
            int r3 = r1.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L18
            int r3 = r3 - r4
            r1.label = r3
        L16:
            r12 = r1
            goto L1e
        L18:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$1 r1 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$1
            r1.<init>(r2, r0)
            goto L16
        L1e:
            java.lang.Object r0 = r12.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r13 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            r14 = 1
            if (r1 == 0) goto L35
            if (r1 != r14) goto L2d
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L5b
            goto L63
        L2d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L35:
            kotlin.ResultKt.throwOnFailure(r0)
            androidx.datastore.core.DataStore r15 = r2.dataStore     // Catch: java.lang.Exception -> L5b
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$2 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$addOrUpdateDesktop$2     // Catch: java.lang.Exception -> L5b
            r11 = 0
            r1 = r17
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            r7 = r22
            r8 = r23
            r9 = r24
            r10 = r25
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Exception -> L5b
            r12.label = r14     // Catch: java.lang.Exception -> L5b
            java.lang.Object r0 = r15.updateData(r0, r12)     // Catch: java.lang.Exception -> L5b
            if (r0 != r13) goto L63
            return r13
        L5b:
            r0 = move-exception
            java.lang.String r1 = "DesktopPersistenceRepo"
            java.lang.String r2 = "Error in updating desktop mode related data, data is stored in a file named desktop_persistent_repositories.pb"
            android.util.Log.e(r1, r2, r0)
        L63:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository.addOrUpdateDesktop(int, int, android.util.ArraySet, android.util.ArraySet, java.util.ArrayList, java.lang.Integer, java.lang.Integer, int, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getDesktopRepositoryState(int r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getDesktopRepositoryState$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getDesktopRepositoryState$1 r0 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getDesktopRepositoryState$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getDesktopRepositoryState$1 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getDesktopRepositoryState$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            int r5 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Exception -> L53
            goto L41
        L29:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L31:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 r4 = r4.dataStoreFlow     // Catch: java.lang.Exception -> L53
            r0.I$0 = r5     // Catch: java.lang.Exception -> L53
            r0.label = r3     // Catch: java.lang.Exception -> L53
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r4, r0)     // Catch: java.lang.Exception -> L53
            if (r6 != r1) goto L41
            return r1
        L41:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories r6 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories) r6     // Catch: java.lang.Exception -> L53
            java.util.Map r4 = r6.getDesktopRepoByUserMap()     // Catch: java.lang.Exception -> L53
            java.lang.Integer r6 = new java.lang.Integer     // Catch: java.lang.Exception -> L53
            r6.<init>(r5)     // Catch: java.lang.Exception -> L53
            java.lang.Object r4 = r4.get(r6)     // Catch: java.lang.Exception -> L53
            com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState r4 = (com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState) r4     // Catch: java.lang.Exception -> L53
            return r4
        L53:
            r4 = move-exception
            java.lang.String r5 = "DesktopPersistenceRepo"
            java.lang.String r6 = "Unable to read from datastore"
            android.util.Log.e(r5, r6, r4)
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository.getDesktopRepositoryState(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getUserDesktopRepositoryMap(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getUserDesktopRepositoryMap$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getUserDesktopRepositoryMap$1 r0 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getUserDesktopRepositoryMap$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getUserDesktopRepositoryMap$1 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$getUserDesktopRepositoryMap$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Exception -> L44
            goto L3d
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 r4 = r4.dataStoreFlow     // Catch: java.lang.Exception -> L44
            r0.label = r3     // Catch: java.lang.Exception -> L44
            java.lang.Object r5 = kotlinx.coroutines.flow.FlowKt.first(r4, r0)     // Catch: java.lang.Exception -> L44
            if (r5 != r1) goto L3d
            return r1
        L3d:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories r5 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories) r5     // Catch: java.lang.Exception -> L44
            java.util.Map r4 = r5.getDesktopRepoByUserMap()     // Catch: java.lang.Exception -> L44
            return r4
        L44:
            r4 = move-exception
            java.lang.String r5 = "DesktopPersistenceRepo"
            java.lang.String r0 = "Unable to read from datastore"
            android.util.Log.e(r5, r0, r4)
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository.getUserDesktopRepositoryMap(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0043 A[Catch: Exception -> 0x0048, TRY_LEAVE, TryCatch #0 {Exception -> 0x0048, blocks: (B:11:0x0025, B:12:0x003f, B:14:0x0043, B:22:0x0034), top: B:7:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object readDesktop(int r5, int r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$readDesktop$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$readDesktop$1 r0 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$readDesktop$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$readDesktop$1 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$readDesktop$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            int r6 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Exception -> L48
            goto L3f
        L29:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L31:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.I$0 = r6     // Catch: java.lang.Exception -> L48
            r0.label = r3     // Catch: java.lang.Exception -> L48
            java.lang.Object r7 = r4.getDesktopRepositoryState(r5, r0)     // Catch: java.lang.Exception -> L48
            if (r7 != r1) goto L3f
            return r1
        L3f:
            com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState r7 = (com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState) r7     // Catch: java.lang.Exception -> L48
            if (r7 == 0) goto L50
            com.android.wm.shell.desktopmode.persistence.Desktop r4 = r7.getDesktopOrThrow(r6)     // Catch: java.lang.Exception -> L48
            return r4
        L48:
            r4 = move-exception
            java.lang.String r5 = "DesktopPersistenceRepo"
            java.lang.String r6 = "Unable to get desktop info from persistent repository"
            android.util.Log.e(r5, r6, r4)
        L50:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository.readDesktop(int, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|(1:(1:10)(2:16|17))(3:18|19|(1:21))|11|12|13))|24|6|7|(0)(0)|11|12|13) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0043, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0044, code lost:
    
        android.util.Log.e("DesktopPersistenceRepo", "Error in removing desktop related data, data is stored in a file named desktop_persistent_repositories.pb", r4);
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object removeDesktop(int r5, int r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$1 r0 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$1 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L43
            goto L4b
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.datastore.core.DataStore r4 = r4.dataStore     // Catch: java.lang.Throwable -> L43
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$2 r7 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeDesktop$2     // Catch: java.lang.Throwable -> L43
            r2 = 0
            r7.<init>(r5, r6, r2)     // Catch: java.lang.Throwable -> L43
            r0.label = r3     // Catch: java.lang.Throwable -> L43
            java.lang.Object r4 = r4.updateData(r7, r0)     // Catch: java.lang.Throwable -> L43
            if (r4 != r1) goto L4b
            return r1
        L43:
            r4 = move-exception
            java.lang.String r5 = "DesktopPersistenceRepo"
            java.lang.String r6 = "Error in removing desktop related data, data is stored in a file named desktop_persistent_repositories.pb"
            android.util.Log.e(r5, r6, r4)
        L4b:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository.removeDesktop(int, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|(1:(1:10)(2:16|17))(3:18|19|(1:21))|11|12|13))|24|6|7|(0)(0)|11|12|13) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0043, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0044, code lost:
    
        android.util.Log.e("DesktopPersistenceRepo", "Error in removing user related data, data is stored in a file named desktop_persistent_repositories.pb", r4);
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object removeUsers(java.util.List r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$1 r0 = (com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$1 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Exception -> L43
            goto L4b
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.datastore.core.DataStore r4 = r4.dataStore     // Catch: java.lang.Exception -> L43
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$2 r6 = new com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$removeUsers$2     // Catch: java.lang.Exception -> L43
            r2 = 0
            r6.<init>(r5, r2)     // Catch: java.lang.Exception -> L43
            r0.label = r3     // Catch: java.lang.Exception -> L43
            java.lang.Object r4 = r4.updateData(r6, r0)     // Catch: java.lang.Exception -> L43
            if (r4 != r1) goto L4b
            return r1
        L43:
            r4 = move-exception
            java.lang.String r5 = "DesktopPersistenceRepo"
            java.lang.String r6 = "Error in removing user related data, data is stored in a file named desktop_persistent_repositories.pb"
            android.util.Log.e(r5, r6, r4)
        L4b:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository.removeUsers(java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public DesktopPersistentRepository(final Context context, CoroutineScope coroutineScope) {
        this(DataStoreFactory.create$default(DataStoreFactory.INSTANCE, Companion.DesktopPersistentRepositoriesSerializer.INSTANCE, new ReplaceFileCorruptionHandler(new DesktopPersistentRepository$$ExternalSyntheticLambda0()), coroutineScope, new Function0() { // from class: com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                DesktopPersistentRepository.Companion companion = DesktopPersistentRepository.Companion;
                return DataStoreFile.dataStoreFile(context2, "desktop_persistent_repositories.pb");
            }
        }, 4));
    }
}
