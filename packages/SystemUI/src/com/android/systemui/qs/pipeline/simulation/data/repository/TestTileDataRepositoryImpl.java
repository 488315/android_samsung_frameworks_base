package com.android.systemui.qs.pipeline.simulation.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.ScRune;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl.TileSimulationCommand;
import com.android.systemui.qs.pipeline.simulation.data.source.LocalTileDataSource;
import com.android.systemui.qs.pipeline.simulation.data.source.RemoteTileDataSource;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSourceFactory;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* loaded from: classes2.dex */
public final class TestTileDataRepositoryImpl implements TestTileDataRepository {
    public static final String COMMAND;
    public static final String PROPERTY_NAME;
    public static final String SIMULATION_PREF_NAME;
    public static final String TAG;
    public final SharedFlowImpl doRestore = SharedFlowKt.MutableSharedFlow$default(0, 1, BufferOverflow.DROP_OLDEST, 1);
    public final SharedPreferences prefs;
    public final TileDataSource tileDataSource;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class TileSimulationCommand implements Command {
        public TileSimulationCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            SharedPreferences.Editor editorEdit;
            SharedPreferences.Editor editorPutBoolean;
            String str = TestTileDataRepositoryImpl.TAG;
            Log.i(str, TestTileDataRepositoryImpl.COMMAND + " command was called with args: " + list);
            if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
                if (list.isEmpty()) {
                    help(printWriter);
                    return;
                }
                String str2 = (String) list.get(0);
                int iHashCode = str2.hashCode();
                TestTileDataRepositoryImpl testTileDataRepositoryImpl = TestTileDataRepositoryImpl.this;
                if (iHashCode != 97702) {
                    if (iHashCode == 3149046 && str2.equals("fota")) {
                        Log.d(str, "do fota  " + list.get(1));
                        boolean z = Boolean.parseBoolean((String) list.get(1));
                        SharedPreferences sharedPreferences = testTileDataRepositoryImpl.prefs;
                        if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null || (editorPutBoolean = editorEdit.putBoolean(TestTileDataRepositoryImpl.PROPERTY_NAME, z)) == null) {
                            return;
                        }
                        editorPutBoolean.apply();
                        return;
                    }
                } else if (str2.equals("bnr")) {
                    Log.d(str, "do BnR");
                    testTileDataRepositoryImpl.doRestore.tryEmit(Unit.INSTANCE);
                    return;
                }
                help(printWriter);
            }
        }

        public final void help(PrintWriter printWriter) {
            printWriter.println("Usage: adb shell cmd statusbar " + TestTileDataRepositoryImpl.COMMAND + " <command>");
            printWriter.println("Note: this command only simulate tile list managing");
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "Available commands:", "  fota [true|false]", "     set fota simulation mode. Need to restart SystemUI.", "  bnr ");
            printWriter.println("     Do restore logic in case of SmartSwitch.");
        }
    }

    static {
        new Companion(null);
        TAG = "TestTileDataRepo";
        COMMAND = "tilesimulation";
        SIMULATION_PREF_NAME = "simulation_pref";
        PROPERTY_NAME = "persist.systemui.test.fota";
    }

    public TestTileDataRepositoryImpl(Context context, TileDataSourceFactory tileDataSourceFactory, CommandRegistry commandRegistry) {
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
            this.prefs = context.getSharedPreferences(SIMULATION_PREF_NAME, 0);
            commandRegistry.registerCommand(COMMAND, new Function0() { // from class: com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    String str = TestTileDataRepositoryImpl.TAG;
                    return this.f$0.new TileSimulationCommand();
                }
            });
            TileDataSource.Companion.getClass();
            String str = TileDataSource.Companion.REMOTE;
            tileDataSourceFactory.getClass();
            this.tileDataSource = Intrinsics.areEqual(str, TileDataSource.Companion.LOCAL) ? new LocalTileDataSource(tileDataSourceFactory.context) : new RemoteTileDataSource(tileDataSourceFactory.context);
        }
    }

    public final boolean isFotaTest() {
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
            SharedPreferences sharedPreferences = this.prefs;
            if (sharedPreferences != null ? sharedPreferences.getBoolean(PROPERTY_NAME, false) : false) {
                return true;
            }
        }
        return false;
    }
}
