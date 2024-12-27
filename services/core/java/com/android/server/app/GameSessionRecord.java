package com.android.server.app;

import android.content.ComponentName;
import android.service.games.IGameSession;
import android.view.SurfaceControlViewHost;

import java.util.Objects;

public final class GameSessionRecord {
    public final IGameSession mIGameSession;
    public final ComponentName mRootComponentName;
    public final State mState;
    public final SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    public final int mTaskId;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State GAME_SESSION_ATTACHED;
        public static final State GAME_SESSION_ENDED_PROCESS_DEATH;
        public static final State GAME_SESSION_REQUESTED;
        public static final State NO_GAME_SESSION_REQUESTED;

        static {
            State state = new State("NO_GAME_SESSION_REQUESTED", 0);
            NO_GAME_SESSION_REQUESTED = state;
            State state2 = new State("GAME_SESSION_REQUESTED", 1);
            GAME_SESSION_REQUESTED = state2;
            State state3 = new State("GAME_SESSION_ATTACHED", 2);
            GAME_SESSION_ATTACHED = state3;
            State state4 = new State("GAME_SESSION_ENDED_PROCESS_DEATH", 3);
            GAME_SESSION_ENDED_PROCESS_DEATH = state4;
            $VALUES = new State[] {state, state2, state3, state4};
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    public GameSessionRecord(
            int i,
            State state,
            ComponentName componentName,
            IGameSession iGameSession,
            SurfaceControlViewHost.SurfacePackage surfacePackage) {
        this.mTaskId = i;
        this.mState = state;
        this.mRootComponentName = componentName;
        this.mIGameSession = iGameSession;
        this.mSurfacePackage = surfacePackage;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameSessionRecord)) {
            return false;
        }
        GameSessionRecord gameSessionRecord = (GameSessionRecord) obj;
        return this.mTaskId == gameSessionRecord.mTaskId
                && this.mState == gameSessionRecord.mState
                && this.mRootComponentName.equals(gameSessionRecord.mRootComponentName)
                && Objects.equals(this.mIGameSession, gameSessionRecord.mIGameSession)
                && Objects.equals(this.mSurfacePackage, gameSessionRecord.mSurfacePackage);
    }

    public final int hashCode() {
        Integer valueOf = Integer.valueOf(this.mTaskId);
        ComponentName componentName = this.mRootComponentName;
        IGameSession iGameSession = this.mIGameSession;
        SurfaceControlViewHost.SurfacePackage surfacePackage = this.mSurfacePackage;
        State state = this.mState;
        return Objects.hash(valueOf, state, componentName, iGameSession, state, surfacePackage);
    }

    public final String toString() {
        return "GameSessionRecord{mTaskId="
                + this.mTaskId
                + ", mState="
                + this.mState
                + ", mRootComponentName="
                + this.mRootComponentName
                + ", mIGameSession="
                + this.mIGameSession
                + ", mSurfacePackage="
                + this.mSurfacePackage
                + '}';
    }
}
