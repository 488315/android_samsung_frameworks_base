package com.android.server.app;

import android.content.ComponentName;
import android.os.UserHandle;
import android.text.TextUtils;

import java.util.Objects;

public final class GameServiceConfiguration {
    public final GameServiceComponentConfiguration mGameServiceComponentConfiguration;
    public final String mPackageName;

    public final class GameServiceComponentConfiguration {
        public final ComponentName mGameServiceComponentName;
        public final ComponentName mGameSessionServiceComponentName;
        public final UserHandle mUserHandle;

        public GameServiceComponentConfiguration(
                UserHandle userHandle, ComponentName componentName, ComponentName componentName2) {
            Objects.requireNonNull(componentName);
            this.mUserHandle = userHandle;
            this.mGameServiceComponentName = componentName;
            this.mGameSessionServiceComponentName = componentName2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof GameServiceComponentConfiguration)) {
                return false;
            }
            GameServiceComponentConfiguration gameServiceComponentConfiguration =
                    (GameServiceComponentConfiguration) obj;
            return this.mUserHandle.equals(gameServiceComponentConfiguration.mUserHandle)
                    && this.mGameServiceComponentName.equals(
                            gameServiceComponentConfiguration.mGameServiceComponentName)
                    && this.mGameSessionServiceComponentName.equals(
                            gameServiceComponentConfiguration.mGameSessionServiceComponentName);
        }

        public final int hashCode() {
            return Objects.hash(
                    this.mUserHandle,
                    this.mGameServiceComponentName,
                    this.mGameSessionServiceComponentName);
        }

        public final String toString() {
            return "GameServiceComponentConfiguration{userHandle="
                    + this.mUserHandle
                    + ", gameServiceComponentName="
                    + this.mGameServiceComponentName
                    + ", gameSessionServiceComponentName="
                    + this.mGameSessionServiceComponentName
                    + "}";
        }
    }

    public GameServiceConfiguration(
            String str, GameServiceComponentConfiguration gameServiceComponentConfiguration) {
        Objects.requireNonNull(str);
        this.mPackageName = str;
        this.mGameServiceComponentConfiguration = gameServiceComponentConfiguration;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameServiceConfiguration)) {
            return false;
        }
        GameServiceConfiguration gameServiceConfiguration = (GameServiceConfiguration) obj;
        return TextUtils.equals(this.mPackageName, gameServiceConfiguration.mPackageName)
                && Objects.equals(
                        this.mGameServiceComponentConfiguration,
                        gameServiceConfiguration.mGameServiceComponentConfiguration);
    }

    public final int hashCode() {
        return Objects.hash(this.mPackageName, this.mGameServiceComponentConfiguration);
    }

    public final String toString() {
        return "GameServiceConfiguration{packageName="
                + this.mPackageName
                + ", gameServiceComponentConfiguration="
                + this.mGameServiceComponentConfiguration
                + '}';
    }
}
