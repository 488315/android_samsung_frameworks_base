package com.android.wm.shell.desktopmode.persistence;

import android.content.Context;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class DesktopRepositoryInitializerImpl implements DesktopRepositoryInitializer {
    public final StateFlowImpl _isInitialized;
    public final Set addedDisplayIdsBeforeInitialized;
    public DesktopRepositoryInitializer.DeskActivationFactory deskActivationFactory;
    public DesktopRepositoryInitializer.DeskRecreationFactory deskRecreationFactory = new DefaultDeskRecreationFactory();
    public final DesktopConfig desktopConfig;
    public final StateFlowImpl isInitialized;
    public final CoroutineScope mainCoroutineScope;
    public final DesktopPersistentRepository persistentRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class DefaultDeskActivationFactory implements DesktopRepositoryInitializer.DeskActivationFactory {
        @Override // com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer.DeskActivationFactory
        public final Object activeDesk(int i, Continuation continuation) {
            return Unit.INSTANCE;
        }
    }

    public final class DefaultDeskRecreationFactory implements DesktopRepositoryInitializer.DeskRecreationFactory {
        @Override // com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer.DeskRecreationFactory
        public final Object recreateDesk(int i, int i2, int i3, Continuation continuation) {
            return new Integer(i3);
        }
    }

    /* renamed from: com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$initialize$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ DesktopUserRepositories $userRepositories;
        int I$0;
        int I$1;
        int I$2;
        int I$3;
        int I$4;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DesktopUserRepositories desktopUserRepositories, Continuation continuation) {
            super(2, continuation);
            this.$userRepositories = desktopUserRepositories;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DesktopRepositoryInitializerImpl.this.new AnonymousClass1(this.$userRepositories, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x00d4, code lost:
        
            if (r2 == r0) goto L30;
         */
        /* JADX WARN: Path cross not found for [B:97:0x02b2, B:103:0x02c1], limit reached: 172 */
        /* JADX WARN: Removed duplicated region for block: B:105:0x02f4  */
        /* JADX WARN: Removed duplicated region for block: B:106:0x02f5  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x02f9 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:110:0x0319 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:124:0x0382 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:129:0x039f A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:147:0x0412  */
        /* JADX WARN: Removed duplicated region for block: B:148:0x0415  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00f7 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x012c  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x012e A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0146 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x01a9 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x01f4 A[Catch: all -> 0x0035, LOOP:4: B:71:0x01ee->B:73:0x01f4, LOOP_END, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:77:0x0224 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:94:0x0291 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:7:0x002e, B:121:0x0364, B:122:0x037c, B:124:0x0382, B:126:0x0394, B:127:0x0398, B:129:0x039f, B:131:0x03b1, B:135:0x03b8, B:137:0x03ca, B:139:0x03df, B:141:0x03e7, B:142:0x03f3, B:144:0x03fb, B:138:0x03d4, B:75:0x021e, B:77:0x0224, B:82:0x023c, B:84:0x0245, B:88:0x025e, B:92:0x028c, B:94:0x0291, B:97:0x02b2, B:108:0x02f9, B:110:0x0319, B:112:0x0326, B:114:0x032d, B:103:0x02c1, B:83:0x0241, B:37:0x00f1, B:39:0x00f7, B:43:0x0128, B:46:0x012e, B:49:0x0140, B:51:0x0146, B:53:0x0158, B:54:0x016f, B:56:0x0175, B:58:0x0186, B:61:0x0191, B:63:0x0196, B:64:0x01a3, B:66:0x01a9, B:69:0x01ce, B:70:0x01d8, B:71:0x01ee, B:73:0x01f4, B:74:0x0207, B:12:0x0056, B:15:0x0076, B:18:0x009b, B:21:0x00aa, B:24:0x00bb, B:25:0x00c1, B:31:0x00d9, B:33:0x00dd, B:36:0x00e9, B:28:0x00ca), top: B:152:0x0009 }] */
        /* JADX WARN: Removed duplicated region for block: B:95:0x02ae  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:108:0x02f9 -> B:109:0x0316). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:117:0x034f -> B:118:0x0356). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x012c -> B:37:0x00f1). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x0207 -> B:75:0x021e). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Object userDesktopRepositoryMap;
            Iterator it;
            int i;
            DesktopRepository desktopRepository;
            Iterator it2;
            Object desktopRepositoryState;
            Object objAccess$getDesksToRestore;
            Iterator it3;
            DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl;
            Desktop desktop;
            Desktop desktop2;
            DesktopRepository desktopRepository2;
            Iterator it4;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            DesktopRepository desktopRepository3;
            Iterator it5;
            Object objRecreateDesk;
            Iterator it6;
            Desktop desktop3;
            Integer num;
            DesktopRepository desktopRepository4;
            CoroutineSingletons coroutineSingletons;
            Set set;
            Iterator it7;
            int i7;
            DesktopRepository desktopRepository5;
            Iterator it8;
            DesktopRepositoryState desktopRepositoryState2;
            Desktop desktop4;
            boolean z;
            int i8;
            int i9;
            int i10;
            Integer num2;
            Iterator it9;
            DesktopRepository desktopRepository6;
            Iterator it10;
            int i11;
            CoroutineSingletons coroutineSingletons2;
            DesktopPersistentRepository desktopPersistentRepository;
            Iterator it11;
            int size;
            int i12;
            CoroutineSingletons coroutineSingletons3 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i13 = 2;
            Integer num3 = null;
            try {
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        DesktopPersistentRepository desktopPersistentRepository2 = DesktopRepositoryInitializerImpl.this.persistentRepository;
                        this.label = 1;
                        userDesktopRepositoryMap = desktopPersistentRepository2.getUserDesktopRepositoryMap(this);
                        break;
                    case 1:
                        ResultKt.throwOnFailure(obj);
                        userDesktopRepositoryMap = obj;
                        Map map = (Map) userDesktopRepositoryMap;
                        if (map == null) {
                            Unit unit = Unit.INSTANCE;
                            DesktopRepositoryInitializerImpl.this._isInitialized.updateState(null, Boolean.TRUE);
                            return unit;
                        }
                        it = map.keySet().iterator();
                        if (it.hasNext()) {
                            int iIntValue = ((Number) it.next()).intValue();
                            DesktopRepository profile = this.$userRepositories.getProfile(iIntValue);
                            DesktopPersistentRepository desktopPersistentRepository3 = DesktopRepositoryInitializerImpl.this.persistentRepository;
                            this.L$0 = it;
                            this.L$1 = profile;
                            this.L$2 = num3;
                            this.L$3 = num3;
                            this.L$4 = num3;
                            this.L$5 = num3;
                            this.I$0 = iIntValue;
                            this.label = i13;
                            desktopRepositoryState = desktopPersistentRepository3.getDesktopRepositoryState(iIntValue, this);
                            if (desktopRepositoryState == coroutineSingletons3) {
                                return coroutineSingletons3;
                            }
                            it2 = it;
                            i = iIntValue;
                            desktopRepository = profile;
                            desktopRepositoryState2 = (DesktopRepositoryState) desktopRepositoryState;
                            if (desktopRepositoryState2 != null) {
                                it = it2;
                                if (it.hasNext()) {
                                    DesktopRepositoryInitializerImpl.this._isInitialized.updateState(null, Boolean.TRUE);
                                    return Unit.INSTANCE;
                                }
                            } else {
                                DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl2 = DesktopRepositoryInitializerImpl.this;
                                this.L$0 = it2;
                                this.L$1 = desktopRepository;
                                this.I$0 = i;
                                this.label = 3;
                                objAccess$getDesksToRestore = DesktopRepositoryInitializerImpl.access$getDesksToRestore(desktopRepositoryInitializerImpl2, desktopRepositoryState2, i, this);
                                if (objAccess$getDesksToRestore == coroutineSingletons3) {
                                    return coroutineSingletons3;
                                }
                                set = (Set) objAccess$getDesksToRestore;
                                if (!CoreRune.DW_MULTIPLE_DESKS) {
                                    List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(set, new Comparator() { // from class: com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$initialize$1$invokeSuspend$$inlined$sortedBy$1
                                        @Override // java.util.Comparator
                                        public final int compare(Object obj2, Object obj3) {
                                            return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((Desktop) obj2).getDesktopId()), Integer.valueOf(((Desktop) obj3).getDesktopId()));
                                        }
                                    });
                                    if (listSortedWith.size() > i13) {
                                        Desktop desktop5 = (Desktop) CollectionsKt___CollectionsKt.first(listSortedWith);
                                        Desktop desktop6 = (Desktop) CollectionsKt___CollectionsKt.last(listSortedWith);
                                        ArrayList arrayList = new ArrayList();
                                        for (Object obj2 : listSortedWith) {
                                            Desktop desktop7 = (Desktop) obj2;
                                            if (desktop7.getDesktopId() != desktop5.getDesktopId() && desktop7.getDesktopId() != desktop6.getDesktopId()) {
                                                arrayList.add(obj2);
                                            }
                                        }
                                        DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl3 = DesktopRepositoryInitializerImpl.this;
                                        desktopRepository2 = desktopRepository;
                                        it3 = arrayList.iterator();
                                        desktop2 = desktop5;
                                        desktop = desktop6;
                                        it4 = it2;
                                        desktopRepositoryInitializerImpl = desktopRepositoryInitializerImpl3;
                                        while (it3.hasNext()) {
                                            Desktop desktop8 = (Desktop) it3.next();
                                            DesktopPersistentRepository desktopPersistentRepository4 = desktopRepositoryInitializerImpl.persistentRepository;
                                            int desktopId = desktop8.getDesktopId();
                                            this.L$0 = it4;
                                            this.L$1 = desktopRepository2;
                                            this.L$2 = desktop2;
                                            this.L$3 = desktop;
                                            this.L$4 = desktopRepositoryInitializerImpl;
                                            this.L$5 = it3;
                                            this.I$0 = i;
                                            this.label = 4;
                                            if (desktopPersistentRepository4.removeDesktop(i, desktopId, this) == coroutineSingletons3) {
                                                return coroutineSingletons3;
                                            }
                                        }
                                        set = ArraysKt___ArraysKt.toSet(new Desktop[]{desktop2, desktop});
                                        desktopRepository = desktopRepository2;
                                        it2 = it4;
                                    }
                                }
                                DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl4 = DesktopRepositoryInitializerImpl.this;
                                Set set2 = set;
                                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
                                it7 = set2.iterator();
                                while (it7.hasNext()) {
                                    arrayList2.add(new Integer(((Desktop) it7.next()).getDesktopId()));
                                }
                                DesktopRepositoryInitializerImpl.access$logV(desktopRepositoryInitializerImpl4, "initialize() will restore desks=%s user=%d", arrayList2, new Integer(i));
                                i7 = i;
                                it = it2;
                                desktopRepository5 = desktopRepository;
                                it8 = set.iterator();
                                if (!it8.hasNext()) {
                                    Desktop desktop9 = (Desktop) it8.next();
                                    int i14 = ((DesktopConfigImpl) DesktopRepositoryInitializerImpl.this.desktopConfig).maxTaskLimit;
                                    Integer numValueOf = Integer.valueOf(i14);
                                    if (i14 <= 0) {
                                        numValueOf = num3;
                                    }
                                    int iIntValue2 = numValueOf != null ? numValueOf.intValue() : desktop9.getZOrderedTasksCount();
                                    int displayId = desktop9.getDisplayId();
                                    int desktopId2 = desktop9.getDesktopId();
                                    int i15 = DesktopRepositoryInitializerImpl.this.addedDisplayIdsBeforeInitialized.contains(Integer.valueOf(displayId)) ? displayId : 0;
                                    DesktopRepositoryInitializer.DeskRecreationFactory deskRecreationFactory = DesktopRepositoryInitializerImpl.this.deskRecreationFactory;
                                    this.L$0 = it;
                                    this.L$1 = desktopRepository5;
                                    this.L$2 = it8;
                                    this.L$3 = desktop9;
                                    this.L$4 = num3;
                                    this.L$5 = num3;
                                    this.I$0 = i7;
                                    this.I$1 = iIntValue2;
                                    this.I$2 = displayId;
                                    this.I$3 = desktopId2;
                                    this.I$4 = i15;
                                    this.label = 5;
                                    objRecreateDesk = deskRecreationFactory.recreateDesk(i7, i15, desktopId2, this);
                                    if (objRecreateDesk == coroutineSingletons3) {
                                        return coroutineSingletons3;
                                    }
                                    it5 = it;
                                    i2 = i15;
                                    it6 = it8;
                                    i3 = desktopId2;
                                    desktop3 = desktop9;
                                    i5 = iIntValue2;
                                    i6 = i7;
                                    desktopRepository3 = desktopRepository5;
                                    i4 = displayId;
                                    num = (Integer) objRecreateDesk;
                                    if (num == null) {
                                        coroutineSingletons2 = coroutineSingletons3;
                                        DesktopRepositoryInitializerImpl.access$logV(DesktopRepositoryInitializerImpl.this, "Re-created desk=%d in display=%d using new deskId=%d and displayId=%d", new Integer(i3), new Integer(i4), num, new Integer(i2));
                                    } else {
                                        coroutineSingletons2 = coroutineSingletons3;
                                    }
                                    if (num == null && num.intValue() == i3 && i2 == i4) {
                                        desktopRepository4 = desktopRepository3;
                                        coroutineSingletons = coroutineSingletons2;
                                    } else {
                                        DesktopRepositoryInitializerImpl.access$logV(DesktopRepositoryInitializerImpl.this, "Removing obsolete desk from persistence under deskId=%d", new Integer(i3));
                                        desktopPersistentRepository = DesktopRepositoryInitializerImpl.this.persistentRepository;
                                        this.L$0 = it5;
                                        this.L$1 = desktopRepository3;
                                        this.L$2 = it6;
                                        this.L$3 = desktop3;
                                        this.L$4 = num;
                                        this.I$0 = i6;
                                        this.I$1 = i5;
                                        this.I$2 = i4;
                                        this.I$3 = i3;
                                        this.I$4 = i2;
                                        this.label = 6;
                                        coroutineSingletons = coroutineSingletons2;
                                        if (desktopPersistentRepository.removeDesktop(i6, i3, this) != coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        desktopRepository4 = desktopRepository3;
                                    }
                                    i7 = i6;
                                    desktop4 = desktop3;
                                    if (num != null) {
                                        DesktopRepositoryInitializerImpl.access$logW(DesktopRepositoryInitializerImpl.this, new Integer(i3), new Integer(i4), new Integer(i2));
                                        coroutineSingletons3 = coroutineSingletons;
                                        it8 = it6;
                                        desktopRepository5 = desktopRepository4;
                                        it = it5;
                                        num3 = null;
                                        if (!it8.hasNext()) {
                                            i13 = 2;
                                            if (it.hasNext()) {
                                            }
                                        }
                                    } else {
                                        desktopRepository4.addDesk(i2, num.intValue(), desktop4.getUsed());
                                        if (i2 != 0) {
                                            z = true;
                                            if (desktop4.getUsed() == 1) {
                                                DesktopRepositoryInitializer.DeskActivationFactory deskActivationFactory = DesktopRepositoryInitializerImpl.this.deskActivationFactory;
                                                int iIntValue3 = num.intValue();
                                                this.L$0 = it5;
                                                this.L$1 = desktopRepository4;
                                                this.L$2 = it6;
                                                this.L$3 = desktop4;
                                                this.L$4 = num;
                                                this.I$0 = i7;
                                                this.I$1 = i5;
                                                this.I$2 = i4;
                                                this.label = 7;
                                                if (deskActivationFactory.activeDesk(iIntValue3, this) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                                i9 = i4;
                                                i10 = i5;
                                                num2 = num;
                                                it9 = it6;
                                                desktopRepository6 = desktopRepository4;
                                                it10 = it5;
                                                i11 = i7;
                                                Integer num4 = num2;
                                                i5 = i10;
                                                it8 = it9;
                                                num = num4;
                                                i8 = i9;
                                                i7 = i11;
                                                desktopRepository5 = desktopRepository6;
                                                it = it10;
                                            } else {
                                                i8 = i4;
                                                it8 = it6;
                                                desktopRepository5 = desktopRepository4;
                                                it = it5;
                                            }
                                        } else {
                                            z = true;
                                            i8 = i4;
                                            it8 = it6;
                                            desktopRepository5 = desktopRepository4;
                                            it = it5;
                                        }
                                        Ref$IntRef ref$IntRef = new Ref$IntRef();
                                        List listReversed = CollectionsKt___CollectionsKt.reversed(desktop4.getZOrderedTasksList());
                                        ArrayList arrayList3 = new ArrayList();
                                        it11 = listReversed.iterator();
                                        while (it11.hasNext()) {
                                            DesktopTask desktopTask = (DesktopTask) desktop4.getTasksByTaskIdMap().get((Integer) it11.next());
                                            if (desktopTask != null) {
                                                arrayList3.add(desktopTask);
                                            }
                                        }
                                        size = arrayList3.size();
                                        i12 = 0;
                                        while (i12 < size) {
                                            Object obj3 = arrayList3.get(i12);
                                            i12++;
                                            DesktopTask desktopTask2 = (DesktopTask) obj3;
                                            Iterator it12 = it;
                                            boolean z2 = desktopTask2.getDesktopTaskState() == DesktopTaskState.VISIBLE && ref$IntRef.element < i5;
                                            CoroutineSingletons coroutineSingletons4 = coroutineSingletons;
                                            desktopRepository5.addTaskToDesk(i8, num.intValue(), desktopTask2.getTaskId(), false);
                                            if (z2) {
                                                int i16 = ref$IntRef.element;
                                                ref$IntRef.element = i16 + 1;
                                                Boxing.boxInt(i16);
                                            } else {
                                                desktopRepository5.minimizeTaskInDesk(i8, num.intValue(), desktopTask2.getTaskId());
                                            }
                                            if (desktopTask2.getDesktopTaskTilingState() == DesktopTaskTilingState.LEFT) {
                                                desktopRepository5.addLeftTiledTask(desktop4.getDisplayId(), desktopTask2.getTaskId());
                                            } else if (desktopTask2.getDesktopTaskTilingState() == DesktopTaskTilingState.RIGHT) {
                                                desktopRepository5.addRightTiledTask(desktop4.getDisplayId(), desktopTask2.getTaskId());
                                            }
                                            it = it12;
                                            coroutineSingletons = coroutineSingletons4;
                                            z = true;
                                        }
                                        coroutineSingletons3 = coroutineSingletons;
                                        num3 = null;
                                        if (!it8.hasNext()) {
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                        i = this.I$0;
                        desktopRepository = (DesktopRepository) this.L$1;
                        it2 = (Iterator) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        desktopRepositoryState = obj;
                        desktopRepositoryState2 = (DesktopRepositoryState) desktopRepositoryState;
                        if (desktopRepositoryState2 != null) {
                        }
                        break;
                    case 3:
                        i = this.I$0;
                        desktopRepository = (DesktopRepository) this.L$1;
                        it2 = (Iterator) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        objAccess$getDesksToRestore = obj;
                        set = (Set) objAccess$getDesksToRestore;
                        if (!CoreRune.DW_MULTIPLE_DESKS) {
                        }
                        DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl42 = DesktopRepositoryInitializerImpl.this;
                        Set set22 = set;
                        ArrayList arrayList22 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set22, 10));
                        it7 = set22.iterator();
                        while (it7.hasNext()) {
                        }
                        DesktopRepositoryInitializerImpl.access$logV(desktopRepositoryInitializerImpl42, "initialize() will restore desks=%s user=%d", arrayList22, new Integer(i));
                        i7 = i;
                        it = it2;
                        desktopRepository5 = desktopRepository;
                        it8 = set.iterator();
                        if (!it8.hasNext()) {
                        }
                        break;
                    case 4:
                        i = this.I$0;
                        it3 = (Iterator) this.L$5;
                        desktopRepositoryInitializerImpl = (DesktopRepositoryInitializerImpl) this.L$4;
                        desktop = (Desktop) this.L$3;
                        desktop2 = (Desktop) this.L$2;
                        desktopRepository2 = (DesktopRepository) this.L$1;
                        it4 = (Iterator) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        while (it3.hasNext()) {
                        }
                        set = ArraysKt___ArraysKt.toSet(new Desktop[]{desktop2, desktop});
                        desktopRepository = desktopRepository2;
                        it2 = it4;
                        DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl422 = DesktopRepositoryInitializerImpl.this;
                        Set set222 = set;
                        ArrayList arrayList222 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set222, 10));
                        it7 = set222.iterator();
                        while (it7.hasNext()) {
                        }
                        DesktopRepositoryInitializerImpl.access$logV(desktopRepositoryInitializerImpl422, "initialize() will restore desks=%s user=%d", arrayList222, new Integer(i));
                        i7 = i;
                        it = it2;
                        desktopRepository5 = desktopRepository;
                        it8 = set.iterator();
                        if (!it8.hasNext()) {
                        }
                        break;
                    case 5:
                        i2 = this.I$4;
                        i3 = this.I$3;
                        i4 = this.I$2;
                        i5 = this.I$1;
                        i6 = this.I$0;
                        Desktop desktop10 = (Desktop) this.L$3;
                        Iterator it13 = (Iterator) this.L$2;
                        DesktopRepository desktopRepository7 = (DesktopRepository) this.L$1;
                        Iterator it14 = (Iterator) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        desktopRepository3 = desktopRepository7;
                        it5 = it14;
                        objRecreateDesk = obj;
                        it6 = it13;
                        desktop3 = desktop10;
                        num = (Integer) objRecreateDesk;
                        if (num == null) {
                        }
                        if (num == null) {
                            break;
                        }
                        DesktopRepositoryInitializerImpl.access$logV(DesktopRepositoryInitializerImpl.this, "Removing obsolete desk from persistence under deskId=%d", new Integer(i3));
                        desktopPersistentRepository = DesktopRepositoryInitializerImpl.this.persistentRepository;
                        this.L$0 = it5;
                        this.L$1 = desktopRepository3;
                        this.L$2 = it6;
                        this.L$3 = desktop3;
                        this.L$4 = num;
                        this.I$0 = i6;
                        this.I$1 = i5;
                        this.I$2 = i4;
                        this.I$3 = i3;
                        this.I$4 = i2;
                        this.label = 6;
                        coroutineSingletons = coroutineSingletons2;
                        if (desktopPersistentRepository.removeDesktop(i6, i3, this) != coroutineSingletons) {
                        }
                        break;
                    case 6:
                        i2 = this.I$4;
                        i3 = this.I$3;
                        i4 = this.I$2;
                        i5 = this.I$1;
                        i6 = this.I$0;
                        num = (Integer) this.L$4;
                        desktop3 = (Desktop) this.L$3;
                        it6 = (Iterator) this.L$2;
                        desktopRepository4 = (DesktopRepository) this.L$1;
                        it5 = (Iterator) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        coroutineSingletons = coroutineSingletons3;
                        i7 = i6;
                        desktop4 = desktop3;
                        if (num != null) {
                        }
                        break;
                    case 7:
                        i9 = this.I$2;
                        i10 = this.I$1;
                        i11 = this.I$0;
                        num2 = (Integer) this.L$4;
                        desktop4 = (Desktop) this.L$3;
                        it9 = (Iterator) this.L$2;
                        desktopRepository6 = (DesktopRepository) this.L$1;
                        it10 = (Iterator) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        z = true;
                        coroutineSingletons = coroutineSingletons3;
                        Integer num42 = num2;
                        i5 = i10;
                        it8 = it9;
                        num = num42;
                        i8 = i9;
                        i7 = i11;
                        desktopRepository5 = desktopRepository6;
                        it = it10;
                        Ref$IntRef ref$IntRef2 = new Ref$IntRef();
                        List listReversed2 = CollectionsKt___CollectionsKt.reversed(desktop4.getZOrderedTasksList());
                        ArrayList arrayList32 = new ArrayList();
                        it11 = listReversed2.iterator();
                        while (it11.hasNext()) {
                        }
                        size = arrayList32.size();
                        i12 = 0;
                        while (i12 < size) {
                        }
                        coroutineSingletons3 = coroutineSingletons;
                        num3 = null;
                        if (!it8.hasNext()) {
                        }
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } catch (Throwable th) {
                DesktopRepositoryInitializerImpl.this._isInitialized.updateState(null, Boolean.TRUE);
                throw th;
            }
        }
    }

    static {
        new Companion(null);
    }

    public DesktopRepositoryInitializerImpl(Context context, DesktopPersistentRepository desktopPersistentRepository, CoroutineScope coroutineScope, DesktopConfig desktopConfig) {
        this.persistentRepository = desktopPersistentRepository;
        this.mainCoroutineScope = coroutineScope;
        this.desktopConfig = desktopConfig;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isInitialized = stateFlowImplMutableStateFlow;
        this.isInitialized = stateFlowImplMutableStateFlow;
        this.deskActivationFactory = new DefaultDeskActivationFactory();
        this.addedDisplayIdsBeforeInitialized = new LinkedHashSet();
    }

    /* JADX WARN: Path cross not found for [B:24:0x0098, B:31:0x00aa], limit reached: 36 */
    /* JADX WARN: Path cross not found for [B:31:0x00aa, B:24:0x0098], limit reached: 36 */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x008f -> B:12:0x003c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getDesksToRestore(DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl, DesktopRepositoryState desktopRepositoryState, int i, ContinuationImpl continuationImpl) {
        DesktopRepositoryInitializerImpl$getDesksToRestore$1 desktopRepositoryInitializerImpl$getDesksToRestore$1;
        int i2;
        Collection arrayList;
        Iterator it;
        int i3;
        desktopRepositoryInitializerImpl.getClass();
        if (continuationImpl instanceof DesktopRepositoryInitializerImpl$getDesksToRestore$1) {
            desktopRepositoryInitializerImpl$getDesksToRestore$1 = (DesktopRepositoryInitializerImpl$getDesksToRestore$1) continuationImpl;
            int i4 = desktopRepositoryInitializerImpl$getDesksToRestore$1.label;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                desktopRepositoryInitializerImpl$getDesksToRestore$1.label = i4 - Integer.MIN_VALUE;
            } else {
                desktopRepositoryInitializerImpl$getDesksToRestore$1 = new DesktopRepositoryInitializerImpl$getDesksToRestore$1(desktopRepositoryInitializerImpl, continuationImpl);
            }
        }
        Object obj = desktopRepositoryInitializerImpl$getDesksToRestore$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i5 = desktopRepositoryInitializerImpl$getDesksToRestore$1.label;
        if (i5 == 0) {
            ResultKt.throwOnFailure(obj);
            i2 = !DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() ? 1 : 0;
            Set setKeySet = desktopRepositoryState.getDesktopMap().keySet();
            arrayList = new ArrayList();
            it = setKeySet.iterator();
            i3 = i;
            if (!it.hasNext()) {
            }
        } else {
            if (i5 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i6 = desktopRepositoryInitializerImpl$getDesksToRestore$1.I$1;
            i3 = desktopRepositoryInitializerImpl$getDesksToRestore$1.I$0;
            it = (Iterator) desktopRepositoryInitializerImpl$getDesksToRestore$1.L$2;
            arrayList = (Collection) desktopRepositoryInitializerImpl$getDesksToRestore$1.L$1;
            DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl2 = (DesktopRepositoryInitializerImpl) desktopRepositoryInitializerImpl$getDesksToRestore$1.L$0;
            ResultKt.throwOnFailure(obj);
            DesktopRepositoryInitializerImpl$getDesksToRestore$1 desktopRepositoryInitializerImpl$getDesksToRestore$12 = desktopRepositoryInitializerImpl$getDesksToRestore$1;
            int i7 = i6;
            desktopRepositoryInitializerImpl = desktopRepositoryInitializerImpl2;
            Collection collection = arrayList;
            DesktopRepositoryInitializerImpl$getDesksToRestore$1 desktopRepositoryInitializerImpl$getDesksToRestore$13 = desktopRepositoryInitializerImpl$getDesksToRestore$12;
            Desktop desktop = (Desktop) obj;
            if (desktop != null) {
                boolean z = desktop.getDesktopId() == desktop.getDisplayId();
                if (i7 != 0 && !z) {
                }
                if (desktop != null) {
                    collection.add(desktop);
                }
                i2 = i7;
                desktopRepositoryInitializerImpl$getDesksToRestore$1 = desktopRepositoryInitializerImpl$getDesksToRestore$13;
                arrayList = collection;
                if (!it.hasNext()) {
                    return CollectionsKt___CollectionsKt.toSet((List) arrayList);
                }
                Integer num = (Integer) it.next();
                DesktopPersistentRepository desktopPersistentRepository = desktopRepositoryInitializerImpl.persistentRepository;
                num.getClass();
                int iIntValue = num.intValue();
                desktopRepositoryInitializerImpl$getDesksToRestore$1.L$0 = desktopRepositoryInitializerImpl;
                desktopRepositoryInitializerImpl$getDesksToRestore$1.L$1 = arrayList;
                desktopRepositoryInitializerImpl$getDesksToRestore$1.L$2 = it;
                desktopRepositoryInitializerImpl$getDesksToRestore$1.I$0 = i3;
                desktopRepositoryInitializerImpl$getDesksToRestore$1.I$1 = i2;
                desktopRepositoryInitializerImpl$getDesksToRestore$1.label = 1;
                Object desktop2 = desktopPersistentRepository.readDesktop(i3, iIntValue, desktopRepositoryInitializerImpl$getDesksToRestore$1);
                if (desktop2 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                desktopRepositoryInitializerImpl$getDesksToRestore$12 = desktopRepositoryInitializerImpl$getDesksToRestore$1;
                i7 = i2;
                obj = desktop2;
                Collection collection2 = arrayList;
                DesktopRepositoryInitializerImpl$getDesksToRestore$1 desktopRepositoryInitializerImpl$getDesksToRestore$132 = desktopRepositoryInitializerImpl$getDesksToRestore$12;
                Desktop desktop3 = (Desktop) obj;
                if (desktop3 != null) {
                }
            }
            desktop3 = null;
            if (desktop3 != null) {
            }
            i2 = i7;
            desktopRepositoryInitializerImpl$getDesksToRestore$1 = desktopRepositoryInitializerImpl$getDesksToRestore$132;
            arrayList = collection2;
            if (!it.hasNext()) {
            }
        }
    }

    public static final void access$logV(DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl, String str, Object... objArr) {
        desktopRepositoryInitializerImpl.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepositoryInitializerImpl", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public static final void access$logW(DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl, Object... objArr) {
        desktopRepositoryInitializerImpl.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepositoryInitializerImpl", objArr);
        ProtoLog.w(shellProtoLogGroup, "%s: Could not re-create desk=%d from display=%d in displayId=%d", spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final void initialize(DesktopUserRepositories desktopUserRepositories) {
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            BuildersKt.launch$default(this.mainCoroutineScope, null, null, new AnonymousClass1(desktopUserRepositories, null), 3);
        } else {
            this._isInitialized.updateState(null, Boolean.TRUE);
        }
    }
}
