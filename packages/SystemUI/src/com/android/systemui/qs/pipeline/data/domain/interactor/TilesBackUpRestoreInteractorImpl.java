package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.Context;
import android.content.res.Resources;
import android.util.IndentingPrintWriter;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.Prefs;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.TileFeatureChecker;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TilesBackUpRestoreInteractorImpl implements TilesBackUpRestoreInteractor {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final DumpManager dumpManager;
    public final QSPipelineLogger logger;
    public final CurrentTilesInteractor qqsTilesInteractor;
    public final RemovedTilesInteractor removedTilesInteractor;
    public final SecureSettings secureSettings;
    public int sepVersionForUpdate;
    public final TileFeatureChecker tileFeatureChecker;
    public final Lazy qsBackupRestoreManager$delegate = LazyKt__LazyJVMKt.lazy(new TilesBackUpRestoreInteractorImpl$$ExternalSyntheticLambda0());
    public final RestoreData restoreData = new RestoreData(0, null, null, false, null, false, 63, null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RestoreData {
        public boolean bnrQSEdited;
        public String bnrQSTiles;
        public boolean bnrQuickQSEdited;
        public String bnrQuickQSTiles;
        public String bnrRemovedTileList;
        public int sepVersion;

        public RestoreData() {
            this(0, null, null, false, null, false, 63, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RestoreData)) {
                return false;
            }
            RestoreData restoreData = (RestoreData) obj;
            return this.sepVersion == restoreData.sepVersion && Intrinsics.areEqual(this.bnrQSTiles, restoreData.bnrQSTiles) && Intrinsics.areEqual(this.bnrRemovedTileList, restoreData.bnrRemovedTileList) && this.bnrQSEdited == restoreData.bnrQSEdited && Intrinsics.areEqual(this.bnrQuickQSTiles, restoreData.bnrQuickQSTiles) && this.bnrQuickQSEdited == restoreData.bnrQuickQSEdited;
        }

        public final int hashCode() {
            int hashCode = Integer.hashCode(this.sepVersion) * 31;
            String str = this.bnrQSTiles;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.bnrRemovedTileList;
            int m = TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31, 31, this.bnrQSEdited);
            String str3 = this.bnrQuickQSTiles;
            return Boolean.hashCode(this.bnrQuickQSEdited) + ((m + (str3 != null ? str3.hashCode() : 0)) * 31);
        }

        public final String toString() {
            int i = this.sepVersion;
            String str = this.bnrQSTiles;
            String str2 = this.bnrRemovedTileList;
            boolean z = this.bnrQSEdited;
            String str3 = this.bnrQuickQSTiles;
            boolean z2 = this.bnrQuickQSEdited;
            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "RestoreData(sepVersion=", ", bnrQSTiles=", str, ", bnrRemovedTileList=");
            m.append(str2);
            m.append(", bnrQSEdited=");
            m.append(z);
            m.append(", bnrQuickQSTiles=");
            m.append(str3);
            m.append(", bnrQuickQSEdited=");
            m.append(z2);
            m.append(")");
            return m.toString();
        }

        public RestoreData(int i, String str, String str2, boolean z, String str3, boolean z2) {
            this.sepVersion = i;
            this.bnrQSTiles = str;
            this.bnrRemovedTileList = str2;
            this.bnrQSEdited = z;
            this.bnrQuickQSTiles = str3;
            this.bnrQuickQSEdited = z2;
        }

        public /* synthetic */ RestoreData(int i, String str, String str2, boolean z, String str3, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? null : str3, (i2 & 32) != 0 ? false : z2);
        }
    }

    public TilesBackUpRestoreInteractorImpl(Context context, CurrentTilesInteractor currentTilesInteractor, CurrentTilesInteractor currentTilesInteractor2, RemovedTilesInteractor removedTilesInteractor, TileFeatureChecker tileFeatureChecker, SecureSettings secureSettings, DumpManager dumpManager, QSPipelineLogger qSPipelineLogger) {
        this.context = context;
        this.currentTilesInteractor = currentTilesInteractor;
        this.qqsTilesInteractor = currentTilesInteractor2;
        this.removedTilesInteractor = removedTilesInteractor;
        this.tileFeatureChecker = tileFeatureChecker;
        this.secureSettings = secureSettings;
        this.dumpManager = dumpManager;
        this.logger = qSPipelineLogger;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("RestoreData:");
        asIndenting.increaseIndent();
        RestoreData restoreData = this.restoreData;
        asIndenting.println("sepVersion    : " + restoreData.sepVersion);
        asIndenting.decreaseIndent();
        asIndenting.increaseIndent();
        asIndenting.println("QSTiles       : " + restoreData.bnrQSTiles);
        asIndenting.decreaseIndent();
        asIndenting.increaseIndent();
        asIndenting.println("RemovedTiles  : " + restoreData.bnrRemovedTileList);
        asIndenting.decreaseIndent();
        asIndenting.increaseIndent();
        asIndenting.println("QSEdited      : " + restoreData.bnrQSEdited);
        asIndenting.decreaseIndent();
        asIndenting.increaseIndent();
        asIndenting.println("QuickQSTiles  : " + restoreData.bnrQuickQSTiles);
        asIndenting.decreaseIndent();
        asIndenting.increaseIndent();
        asIndenting.println("QuickQSEdited : " + restoreData.bnrQuickQSEdited);
        asIndenting.decreaseIndent();
    }

    public final void logTilesBackupRestored(String str, String str2) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(date);
        format.getClass();
        String str3 = str + ":" + str2;
        QSPipelineLogger qSPipelineLogger = this.logger;
        qSPipelineLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(15);
        LogBuffer logBuffer = qSPipelineLogger.restoreLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSRestoreLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = format;
        logMessageImpl.str2 = str3;
        logBuffer.commit(obtain);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void setRestoreData(String str) {
        int i;
        List split$default = StringsKt__StringsKt.split$default(str, new String[]{"::"}, 0, 6);
        Log.d("TileBackUpRestoreInteractor", "setRestoreData data=" + str);
        int size = split$default.size();
        RestoreData restoreData = this.restoreData;
        if (size <= 1) {
            String str2 = (String) split$default.get(0);
            if (str2.hashCode() == -1289769360 && str2.equals("removed_tile_list")) {
                restoreData.bnrRemovedTileList = "";
            }
            logTilesBackupRestored((String) split$default.get(0), "");
            return;
        }
        String str3 = (String) split$default.get(0);
        int hashCode = str3.hashCode();
        CurrentTilesInteractor currentTilesInteractor = this.qqsTilesInteractor;
        Companion companion = Companion;
        switch (hashCode) {
            case -1289769360:
                if (str3.equals("removed_tile_list")) {
                    i = 1;
                    restoreData.bnrRemovedTileList = (String) split$default.get(1);
                    break;
                }
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
                break;
            case -1020575241:
                if (str3.equals("sep_version")) {
                    String str4 = (String) split$default.get(1);
                    if (str4.length() > 0) {
                        restoreData.sepVersion = 0;
                        restoreData.bnrQSTiles = null;
                        restoreData.bnrRemovedTileList = null;
                        restoreData.bnrQSEdited = false;
                        restoreData.bnrQuickQSTiles = null;
                        restoreData.bnrQuickQSEdited = false;
                        Integer valueOf = Integer.valueOf(str4);
                        restoreData.sepVersion = valueOf.intValue();
                        this.sepVersionForUpdate = valueOf.intValue();
                    }
                    i = 1;
                    break;
                }
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
            case -851078257:
                if (str3.equals("tile_list")) {
                    String str5 = (String) split$default.get(1);
                    restoreData.bnrQSTiles = str5;
                    if (str5 != null) {
                        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
                        Resources resources = this.context.getResources();
                        tilesSettingConverter.getClass();
                        List tilesList = TilesSettingConverter.toTilesList(resources, str5);
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = (ArrayList) tilesList;
                        int size2 = arrayList2.size();
                        int i2 = 0;
                        while (i2 < size2) {
                            Object obj = arrayList2.get(i2);
                            i2++;
                            if (this.tileFeatureChecker.isAvailableCustomTile((TileSpec) obj)) {
                                arrayList.add(obj);
                            }
                        }
                        String str6 = restoreData.bnrRemovedTileList;
                        String str7 = str6 != null ? str6 : "";
                        Resources resources2 = this.context.getResources();
                        companion.getClass();
                        TilesSettingConverter.INSTANCE.getClass();
                        ArrayList arrayList3 = new ArrayList(TilesSettingConverter.toTilesList(resources2, str7));
                        CurrentTilesInteractor currentTilesInteractor2 = this.currentTilesInteractor;
                        List currentTilesSpecs = currentTilesInteractor2.getCurrentTilesSpecs();
                        ArrayList arrayList4 = new ArrayList();
                        ArrayList arrayList5 = (ArrayList) currentTilesSpecs;
                        int size3 = arrayList5.size();
                        int i3 = 0;
                        while (i3 < size3) {
                            Object obj2 = arrayList5.get(i3);
                            i3++;
                            TileSpec tileSpec = (TileSpec) obj2;
                            if (!arrayList.contains(tileSpec) && !arrayList3.contains(tileSpec)) {
                                arrayList4.add(obj2);
                            }
                        }
                        List plus = CollectionsKt___CollectionsKt.plus((Iterable) arrayList4, (Collection) arrayList);
                        RemovedTilesInteractorImpl removedTilesInteractorImpl = (RemovedTilesInteractorImpl) this.removedTilesInteractor;
                        Iterable iterable = (Iterable) removedTilesInteractorImpl.removedTiles.$$delegate_0.getValue();
                        ArrayList arrayList6 = new ArrayList();
                        for (Object obj3 : iterable) {
                            TileSpec tileSpec2 = (TileSpec) obj3;
                            if (!arrayList.contains(tileSpec2) && !arrayList3.contains(tileSpec2)) {
                                arrayList6.add(obj3);
                            }
                        }
                        List plus2 = CollectionsKt___CollectionsKt.plus((Iterable) arrayList6, (Collection) arrayList3);
                        Log.d("TileBackUpRestoreInteractor", "RestoreData " + restoreData);
                        Log.i("TileBackUpRestoreInteractor", "BnR from sepVersion: " + this.sepVersionForUpdate);
                        Log.i("TileBackUpRestoreInteractor", "newTiles by BnR: " + plus);
                        Log.i("TileBackUpRestoreInteractor", "newRemovedTiles by BnR: " + plus2);
                        if (this.sepVersionForUpdate < 150000) {
                            currentTilesInteractor.setTiles(plus);
                        }
                        this.sepVersionForUpdate = 0;
                        currentTilesInteractor2.setTiles(plus);
                        if (RemovedTilesInteractorImpl.DEBUG) {
                            Log.d("RemovedTilesInteractor", "setRemovedTiles: " + plus2);
                        }
                        removedTilesInteractorImpl._removedTiles.updateState(null, plus2);
                        BuildersKt.launch$default(removedTilesInteractorImpl.scope, null, null, new RemovedTilesInteractorImpl$setRemovedTiles$1(removedTilesInteractorImpl, plus2, null), 3);
                    }
                    i = 1;
                    break;
                }
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
                break;
            case 75675811:
                if (str3.equals("qqs_tile_list")) {
                    String str8 = (String) split$default.get(1);
                    restoreData.bnrQuickQSTiles = str8;
                    if (str8 != null) {
                        Resources resources3 = this.context.getResources();
                        companion.getClass();
                        TilesSettingConverter.INSTANCE.getClass();
                        currentTilesInteractor.setTiles(TilesSettingConverter.toTilesList(resources3, str8));
                    }
                    i = 1;
                    break;
                }
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
                break;
            case 432981722:
                if (str3.equals("qqs_has_edited")) {
                    String str9 = (String) split$default.get(1);
                    if (str9 != null) {
                        Boolean.parseBoolean(str9);
                    } else {
                        restoreData.bnrQuickQSEdited = false;
                        Prefs.putBoolean(this.context, "QQsHasEditedQuickTileList", false);
                    }
                    i = 1;
                    break;
                }
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
                break;
            case 1768376686:
                if (str3.equals("has_edited")) {
                    String str10 = (String) split$default.get(1);
                    if (str10 != null) {
                        Boolean.parseBoolean(str10);
                    } else {
                        restoreData.bnrQSEdited = false;
                        Prefs.putBoolean(this.context, "QsHasEditedQuickTileList", false);
                    }
                    i = 1;
                    break;
                }
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
                break;
            default:
                Log.w("TileBackUpRestoreInteractor", "setRestoreData: " + split$default.get(0) + " is unknown");
                i = 1;
                break;
        }
        logTilesBackupRestored((String) split$default.get(0), (String) split$default.get(i));
    }
}
