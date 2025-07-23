package android.app;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import java.util.Arrays;
import java.util.Map;

@SystemApi
/* loaded from: classes.dex */
public final class GameModeInfo implements Parcelable {
    public static final Parcelable.Creator<GameModeInfo> CREATOR = new Parcelable.Creator<GameModeInfo>() { // from class: android.app.GameModeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameModeInfo createFromParcel(Parcel parcel) {
            return new GameModeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GameModeInfo[] newArray(int i) {
            return new GameModeInfo[i];
        }
    };
    private final int mActiveGameMode;
    private final int[] mAvailableGameModes;
    private final Map<Integer, GameModeConfiguration> mConfigMap;
    private final boolean mIsDownscalingAllowed;
    private final boolean mIsFpsOverrideAllowed;
    private final int[] mOverriddenGameModes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public static final class Builder {
        private int mActiveGameMode;
        private boolean mIsDownscalingAllowed;
        private boolean mIsFpsOverrideAllowed;
        private int[] mAvailableGameModes = new int[0];
        private int[] mOverriddenGameModes = new int[0];
        private Map<Integer, GameModeConfiguration> mConfigMap = new ArrayMap();

        public Builder setAvailableGameModes(int[] iArr) {
            this.mAvailableGameModes = iArr;
            return this;
        }

        public Builder setOverriddenGameModes(int[] iArr) {
            this.mOverriddenGameModes = iArr;
            return this;
        }

        public Builder setActiveGameMode(int i) {
            this.mActiveGameMode = i;
            return this;
        }

        public Builder setDownscalingAllowed(boolean z) {
            this.mIsDownscalingAllowed = z;
            return this;
        }

        public Builder setFpsOverrideAllowed(boolean z) {
            this.mIsFpsOverrideAllowed = z;
            return this;
        }

        public Builder setGameModeConfiguration(int i, GameModeConfiguration gameModeConfiguration) {
            this.mConfigMap.put(Integer.valueOf(i), gameModeConfiguration);
            return this;
        }

        public GameModeInfo build() {
            return new GameModeInfo(this.mActiveGameMode, this.mAvailableGameModes, this.mOverriddenGameModes, this.mIsDownscalingAllowed, this.mIsFpsOverrideAllowed, this.mConfigMap);
        }
    }

    public GameModeInfo(int i, int[] iArr) {
        this(i, iArr, new int[0], true, true, new ArrayMap());
    }

    private GameModeInfo(int i, int[] iArr, int[] iArr2, boolean z, boolean z2, Map<Integer, GameModeConfiguration> map) {
        this.mActiveGameMode = i;
        this.mAvailableGameModes = Arrays.copyOf(iArr, iArr.length);
        this.mOverriddenGameModes = Arrays.copyOf(iArr2, iArr2.length);
        this.mIsDownscalingAllowed = z;
        this.mIsFpsOverrideAllowed = z2;
        this.mConfigMap = map;
    }

    public GameModeInfo(Parcel parcel) {
        this.mActiveGameMode = parcel.readInt();
        this.mAvailableGameModes = parcel.createIntArray();
        this.mOverriddenGameModes = parcel.createIntArray();
        this.mIsDownscalingAllowed = parcel.readBoolean();
        this.mIsFpsOverrideAllowed = parcel.readBoolean();
        ArrayMap arrayMap = new ArrayMap();
        this.mConfigMap = arrayMap;
        parcel.readMap(arrayMap, getClass().getClassLoader(), Integer.class, GameModeConfiguration.class);
    }

    public int getActiveGameMode() {
        return this.mActiveGameMode;
    }

    public int[] getAvailableGameModes() {
        int[] iArr = this.mAvailableGameModes;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public int[] getOverriddenGameModes() {
        int[] iArr = this.mOverriddenGameModes;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public GameModeConfiguration getGameModeConfiguration(int i) {
        return this.mConfigMap.get(Integer.valueOf(i));
    }

    public boolean isDownscalingAllowed() {
        return this.mIsDownscalingAllowed;
    }

    public boolean isFpsOverrideAllowed() {
        return this.mIsFpsOverrideAllowed;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mActiveGameMode);
        parcel.writeIntArray(this.mAvailableGameModes);
        parcel.writeIntArray(this.mOverriddenGameModes);
        parcel.writeBoolean(this.mIsDownscalingAllowed);
        parcel.writeBoolean(this.mIsFpsOverrideAllowed);
        parcel.writeMap(this.mConfigMap);
    }
}
