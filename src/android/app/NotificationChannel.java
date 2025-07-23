package android.app;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.vibrator.persistence.VibrationXmlParser;
import android.os.vibrator.persistence.VibrationXmlSerializer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class NotificationChannel implements Parcelable {
    public static final int ALLOW_BUBBLE_OFF = 0;
    public static final int ALLOW_BUBBLE_ON = 1;
    private static final String ATT_ALLOW_BUBBLE = "allow_bubbles";
    private static final String ATT_BLOCKABLE_SYSTEM = "blockable_system";
    private static final String ATT_CONTENT_TYPE = "content_type";
    private static final String ATT_CONVERSATION_ID = "conv_id";
    private static final String ATT_DELETED = "deleted";
    private static final String ATT_DELETED_TIME_MS = "del_time";
    private static final String ATT_DEMOTE = "dem";
    private static final String ATT_DESC = "desc";
    private static final String ATT_FG_SERVICE_SHOWN = "fgservice";
    private static final String ATT_FLAGS = "flags";
    private static final String ATT_GROUP = "group";
    private static final String ATT_ID = "id";
    private static final String ATT_IMPORTANCE = "importance";
    private static final String ATT_IMP_CONVERSATION = "imp_conv";
    private static final String ATT_LIGHTS = "lights";
    private static final String ATT_LIGHT_COLOR = "light_color";
    private static final String ATT_NAME = "name";
    private static final String ATT_ORIG_IMP = "orig_imp";
    private static final String ATT_PARENT_CHANNEL = "parent";
    private static final String ATT_PRIORITY = "priority";
    private static final String ATT_SHOW_BADGE = "show_badge";
    private static final String ATT_SOUND = "sound";
    private static final String ATT_USAGE = "usage";
    private static final String ATT_USER_LOCKED = "locked";
    private static final String ATT_VIBRATION = "vibration";
    private static final String ATT_VIBRATION_EFFECT = "vibration_effect";
    private static final String ATT_VIBRATION_ENABLED = "vibration_enabled";
    private static final String ATT_VISIBILITY = "visibility";
    public static final String CONVERSATION_CHANNEL_ID_FORMAT = "%1$s : %2$s";
    public static final int DEFAULT_ALLOW_BUBBLE = -1;
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final boolean DEFAULT_DELETED = false;
    private static final long DEFAULT_DELETION_TIME_MS = -1;
    private static final int DEFAULT_IMPORTANCE = -1000;
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;
    private static final int DEFAULT_VISIBILITY = -1000;
    private static final String DELIMITER = ",";
    public static final String EDIT_CONVERSATION = "conversation";
    public static final String EDIT_IMPORTANCE = "importance";
    public static final String EDIT_LAUNCHER = "launcher";
    public static final String EDIT_LOCKED_DEVICE = "locked";
    public static final String EDIT_SOUND = "sound";
    public static final String EDIT_VIBRATION = "vibration";
    public static final String EDIT_ZEN = "zen";
    public static final int MAX_SERIALIZED_VIBRATION_LENGTH = 32768;
    public static final int MAX_TEXT_LENGTH = 1000;
    public static final int MAX_VIBRATION_LENGTH = 500;
    public static final String PLACEHOLDER_CONVERSATION_ID = ":placeholder_id";
    private static final String TAG = "NotificationChannel";
    private static final String TAG_CHANNEL = "channel";
    public static final int USER_LOCKED_ALLOW_BUBBLE = 256;
    public static final int USER_LOCKED_APP_CHANGED = 512;
    public static final int USER_LOCKED_IMPORTANCE = 4;
    public static final int USER_LOCKED_LIGHTS = 8;
    public static final int USER_LOCKED_PRIORITY = 1;
    public static final int USER_LOCKED_SHOW_BADGE = 128;

    @SystemApi
    public static final int USER_LOCKED_SOUND = 32;
    public static final int USER_LOCKED_VIBRATION = 16;
    public static final int USER_LOCKED_VISIBILITY = 2;
    private int mAllowBubbles;
    private AudioAttributes mAudioAttributes;
    private boolean mBlockableSystem;
    private boolean mBypassDnd;
    private String mConversationId;
    private boolean mDeleted;
    private long mDeletedTime;
    private boolean mDemoted;
    private String mDesc;
    private String mGroup;
    private String mId;
    private int mImportance;
    private boolean mImportanceLockedByOEM;
    private boolean mImportanceLockedDefaultApp;
    private boolean mImportantConvo;
    private long mLastNotificationUpdateTimeMs;
    private int mLightColor;
    private boolean mLights;
    private int mLockscreenVisibility;
    private String mName;
    private int mOriginalImportance;
    private String mParentId;
    private boolean mShowBadge;
    private Uri mSound;
    private int mSoundMissingReason;
    private boolean mSoundRestored;
    private int mUserLockedFields;
    private boolean mUserVisibleTaskShown;
    private VibrationEffect mVibrationEffect;
    private boolean mVibrationEnabled;
    private long[] mVibrationPattern;
    public static final String NEWS_ID = "android.app.news";
    public static final String SOCIAL_MEDIA_ID = "android.app.social";
    public static final String PROMOTIONS_ID = "android.app.promotions";
    public static final String RECS_ID = "android.app.recs";
    public static final ArrayList<String> SYSTEM_RESERVED_IDS = new ArrayList<>(List.of(NEWS_ID, SOCIAL_MEDIA_ID, PROMOTIONS_ID, RECS_ID));
    public static final int[] LOCKABLE_FIELDS = {1, 2, 4, 8, 16, 32, 128, 256};
    public static final Parcelable.Creator<NotificationChannel> CREATOR = new Parcelable.Creator<NotificationChannel>() { // from class: android.app.NotificationChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationChannel createFromParcel(Parcel parcel) {
            return new NotificationChannel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationChannel[] newArray(int i) {
            return new NotificationChannel[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NotificationChannel(String str, CharSequence charSequence, int i) {
        this.mImportance = -1000;
        this.mOriginalImportance = -1000;
        this.mLockscreenVisibility = -1000;
        this.mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.mSoundRestored = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = -1;
        this.mParentId = null;
        this.mConversationId = null;
        this.mDemoted = false;
        this.mImportantConvo = false;
        this.mDeletedTime = -1L;
        this.mLastNotificationUpdateTimeMs = 0L;
        this.mSoundMissingReason = 0;
        this.mId = getTrimmedString(str);
        this.mName = charSequence != null ? getTrimmedString(charSequence.toString()) : null;
        this.mImportance = i;
    }

    protected NotificationChannel(Parcel parcel) {
        this.mImportance = -1000;
        this.mOriginalImportance = -1000;
        this.mLockscreenVisibility = -1000;
        this.mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
        boolean z = false;
        this.mSoundRestored = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = -1;
        this.mParentId = null;
        this.mConversationId = null;
        this.mDemoted = false;
        this.mImportantConvo = false;
        this.mDeletedTime = -1L;
        this.mLastNotificationUpdateTimeMs = 0L;
        this.mSoundMissingReason = 0;
        if (parcel.readByte() != 0) {
            this.mId = getTrimmedString(parcel.readString());
        } else {
            this.mId = null;
        }
        if (parcel.readByte() != 0) {
            this.mName = getTrimmedString(parcel.readString());
        } else {
            this.mName = null;
        }
        if (parcel.readByte() != 0) {
            this.mDesc = getTrimmedString(parcel.readString());
        } else {
            this.mDesc = null;
        }
        this.mImportance = parcel.readInt();
        this.mBypassDnd = parcel.readByte() != 0;
        this.mLockscreenVisibility = parcel.readInt();
        if (parcel.readByte() != 0) {
            Uri createFromParcel = Uri.CREATOR.createFromParcel(parcel);
            this.mSound = createFromParcel;
            this.mSound = Uri.parse(getTrimmedString(createFromParcel.toString()));
        } else {
            this.mSound = null;
        }
        this.mLights = parcel.readByte() != 0;
        this.mUserLockedFields = parcel.readInt();
        this.mUserVisibleTaskShown = parcel.readByte() != 0;
        this.mVibrationEnabled = parcel.readByte() != 0;
        this.mShowBadge = parcel.readByte() != 0;
        this.mDeleted = parcel.readByte() != 0;
        if (parcel.readByte() != 0) {
            this.mGroup = getTrimmedString(parcel.readString());
        } else {
            this.mGroup = null;
        }
        this.mAudioAttributes = parcel.readInt() > 0 ? AudioAttributes.CREATOR.createFromParcel(parcel) : null;
        this.mLightColor = parcel.readInt();
        this.mBlockableSystem = parcel.readBoolean();
        this.mImportanceLockedByOEM = parcel.readBoolean();
        this.mAllowBubbles = parcel.readInt();
        this.mOriginalImportance = parcel.readInt();
        this.mParentId = getTrimmedString(parcel.readString());
        this.mConversationId = getTrimmedString(parcel.readString());
        this.mDemoted = parcel.readBoolean();
        this.mImportantConvo = parcel.readBoolean();
        this.mDeletedTime = parcel.readLong();
        this.mImportanceLockedDefaultApp = parcel.readBoolean();
        long[] createLongArray = parcel.createLongArray();
        this.mVibrationPattern = createLongArray;
        if (createLongArray != null && createLongArray.length > 500) {
            this.mVibrationPattern = Arrays.copyOf(createLongArray, 500);
        }
        if (Flags.notifChannelEstimateEffectSize() && parcel.dataAvail() > 32768) {
            z = true;
        }
        if (Flags.notificationChannelVibrationEffectApi()) {
            this.mVibrationEffect = parcel.readInt() != 0 ? VibrationEffect.CREATOR.createFromParcel(parcel) : null;
            if (!Flags.notifChannelCropVibrationEffects() || this.mVibrationEffect == null) {
                return;
            }
            if (!Flags.notifChannelEstimateEffectSize()) {
                this.mVibrationEffect = getTrimmedVibrationEffect(this.mVibrationEffect);
            } else if (z) {
                this.mVibrationEffect = this.mVibrationEffect.cropToLengthOrNull(500);
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mId != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mName != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mName);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mDesc != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mDesc);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mImportance);
        parcel.writeByte(this.mBypassDnd ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mLockscreenVisibility);
        if (this.mSound != null) {
            parcel.writeByte((byte) 1);
            this.mSound.writeToParcel(parcel, 0);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeByte(this.mLights ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mUserLockedFields);
        parcel.writeByte(this.mUserVisibleTaskShown ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mVibrationEnabled ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mShowBadge ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mDeleted ? (byte) 1 : (byte) 0);
        if (this.mGroup != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mGroup);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mAudioAttributes != null) {
            parcel.writeInt(1);
            this.mAudioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mLightColor);
        parcel.writeBoolean(this.mBlockableSystem);
        parcel.writeBoolean(this.mImportanceLockedByOEM);
        parcel.writeInt(this.mAllowBubbles);
        parcel.writeInt(this.mOriginalImportance);
        parcel.writeString(this.mParentId);
        parcel.writeString(this.mConversationId);
        parcel.writeBoolean(this.mDemoted);
        parcel.writeBoolean(this.mImportantConvo);
        parcel.writeLong(this.mDeletedTime);
        parcel.writeBoolean(this.mImportanceLockedDefaultApp);
        parcel.writeLongArray(this.mVibrationPattern);
        if (Flags.notificationChannelVibrationEffectApi()) {
            if (this.mVibrationEffect != null) {
                parcel.writeInt(1);
                this.mVibrationEffect.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public NotificationChannel copy() {
        NotificationChannel notificationChannel = new NotificationChannel(this.mId, this.mName, this.mImportance);
        notificationChannel.setDescription(this.mDesc);
        notificationChannel.setBypassDnd(this.mBypassDnd);
        notificationChannel.setLockscreenVisibility(this.mLockscreenVisibility);
        notificationChannel.setSound(this.mSound, this.mAudioAttributes);
        notificationChannel.setLightColor(this.mLightColor);
        notificationChannel.enableLights(this.mLights);
        notificationChannel.setVibrationPattern(this.mVibrationPattern);
        if (Flags.notificationChannelVibrationEffectApi()) {
            notificationChannel.setVibrationEffect(this.mVibrationEffect);
        }
        notificationChannel.lockFields(this.mUserLockedFields);
        notificationChannel.setUserVisibleTaskShown(this.mUserVisibleTaskShown);
        notificationChannel.enableVibration(this.mVibrationEnabled);
        notificationChannel.setShowBadge(this.mShowBadge);
        notificationChannel.setDeleted(this.mDeleted);
        notificationChannel.setGroup(this.mGroup);
        notificationChannel.setBlockable(this.mBlockableSystem);
        notificationChannel.setAllowBubbles(this.mAllowBubbles);
        notificationChannel.setOriginalImportance(this.mOriginalImportance);
        notificationChannel.setConversationId(this.mParentId, this.mConversationId);
        notificationChannel.setDemoted(this.mDemoted);
        notificationChannel.setImportantConversation(this.mImportantConvo);
        notificationChannel.setDeletedTimeMs(this.mDeletedTime);
        notificationChannel.setImportanceLockedByCriticalDeviceFunction(this.mImportanceLockedDefaultApp);
        notificationChannel.setLastNotificationUpdateTimeMs(this.mLastNotificationUpdateTimeMs);
        return notificationChannel;
    }

    public void lockFields(int i) {
        this.mUserLockedFields = i | this.mUserLockedFields;
    }

    public void unlockFields(int i) {
        this.mUserLockedFields = (~i) & this.mUserLockedFields;
    }

    public void setUserVisibleTaskShown(boolean z) {
        this.mUserVisibleTaskShown = z;
    }

    public void setDeleted(boolean z) {
        this.mDeleted = z;
    }

    public void setDeletedTimeMs(long j) {
        this.mDeletedTime = j;
    }

    @SystemApi
    public void setImportantConversation(boolean z) {
        this.mImportantConvo = z;
    }

    public void setBlockable(boolean z) {
        this.mBlockableSystem = z;
    }

    public void setName(CharSequence charSequence) {
        this.mName = charSequence != null ? getTrimmedString(charSequence.toString()) : null;
    }

    public void setDescription(String str) {
        this.mDesc = getTrimmedString(str);
    }

    private String getTrimmedString(String str) {
        return (str == null || str.length() <= 1000) ? str : str.substring(0, 1000);
    }

    private VibrationEffect getTrimmedVibrationEffect(VibrationEffect vibrationEffect) {
        if (vibrationEffect == null) {
            return null;
        }
        VibrationEffect cropToLengthOrNull = vibrationEffect.cropToLengthOrNull(500);
        if (cropToLengthOrNull != null) {
            vibrationEffect = cropToLengthOrNull;
        }
        if (vibrationToString(vibrationEffect).length() > 32768) {
            return null;
        }
        return vibrationEffect;
    }

    public void setId(String str) {
        this.mId = str;
    }

    public void setGroup(String str) {
        this.mGroup = str;
    }

    public void setShowBadge(boolean z) {
        this.mShowBadge = z;
    }

    public void setSound(Uri uri, AudioAttributes audioAttributes) {
        this.mSound = uri;
        this.mAudioAttributes = audioAttributes;
    }

    public void enableLights(boolean z) {
        this.mLights = z;
    }

    public void setLightColor(int i) {
        this.mLightColor = i;
    }

    public void enableVibration(boolean z) {
        this.mVibrationEnabled = z;
    }

    public void setVibrationPattern(long[] jArr) {
        this.mVibrationEnabled = jArr != null && jArr.length > 0;
        this.mVibrationPattern = jArr;
        if (Flags.notifChannelCropVibrationEffects() && jArr != null && jArr.length > 500) {
            this.mVibrationPattern = Arrays.copyOf(jArr, 500);
        }
        if (Flags.notificationChannelVibrationEffectApi()) {
            try {
                this.mVibrationEffect = VibrationEffect.createWaveform(jArr, -1);
            } catch (IllegalArgumentException | NullPointerException unused) {
                this.mVibrationEffect = null;
            }
        }
    }

    public void setVibrationEffect(VibrationEffect vibrationEffect) {
        this.mVibrationEnabled = vibrationEffect != null;
        this.mVibrationEffect = vibrationEffect;
        if (Flags.notifChannelCropVibrationEffects() && vibrationEffect != null) {
            long[] computeCreateWaveformOffOnTimingsOrNull = vibrationEffect.computeCreateWaveformOffOnTimingsOrNull();
            if (computeCreateWaveformOffOnTimingsOrNull != null) {
                if (computeCreateWaveformOffOnTimingsOrNull.length > 500) {
                    setVibrationPattern(computeCreateWaveformOffOnTimingsOrNull);
                    return;
                } else {
                    this.mVibrationPattern = computeCreateWaveformOffOnTimingsOrNull;
                    return;
                }
            }
            this.mVibrationEffect = getTrimmedVibrationEffect(this.mVibrationEffect);
            this.mVibrationPattern = null;
            return;
        }
        VibrationEffect vibrationEffect2 = this.mVibrationEffect;
        this.mVibrationPattern = vibrationEffect2 != null ? vibrationEffect2.computeCreateWaveformOffOnTimingsOrNull() : null;
    }

    public void setImportance(int i) {
        this.mImportance = i;
    }

    public void setBypassDnd(boolean z) {
        this.mBypassDnd = z;
    }

    public void setLockscreenVisibility(int i) {
        this.mLockscreenVisibility = i;
    }

    public void setAllowBubbles(boolean z) {
        this.mAllowBubbles = z ? 1 : 0;
    }

    public void setAllowBubbles(int i) {
        this.mAllowBubbles = i;
    }

    public void setConversationId(String str, String str2) {
        this.mParentId = str;
        this.mConversationId = str2;
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDesc;
    }

    public int getImportance() {
        return this.mImportance;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public boolean isConversation() {
        return !TextUtils.isEmpty(getConversationId());
    }

    public boolean isImportantConversation() {
        return this.mImportantConvo;
    }

    public Uri getSound() {
        return this.mSound;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public boolean shouldShowLights() {
        return this.mLights;
    }

    public int getLightColor() {
        return this.mLightColor;
    }

    public boolean shouldVibrate() {
        return this.mVibrationEnabled;
    }

    public long[] getVibrationPattern() {
        return this.mVibrationPattern;
    }

    public VibrationEffect getVibrationEffect() {
        return this.mVibrationEffect;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    public boolean canShowBadge() {
        return this.mShowBadge;
    }

    public String getGroup() {
        return this.mGroup;
    }

    public boolean canBubble() {
        return this.mAllowBubbles == 1;
    }

    public int getAllowBubbles() {
        return this.mAllowBubbles;
    }

    public String getParentChannelId() {
        return this.mParentId;
    }

    public String getConversationId() {
        return this.mConversationId;
    }

    @SystemApi
    public boolean isDeleted() {
        return this.mDeleted;
    }

    public long getDeletedTimeMs() {
        return this.mDeletedTime;
    }

    @SystemApi
    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public boolean isUserVisibleTaskShown() {
        return this.mUserVisibleTaskShown;
    }

    public boolean isBlockable() {
        return this.mBlockableSystem;
    }

    public void setImportanceLockedByOEM(boolean z) {
        this.mImportanceLockedByOEM = z;
    }

    public boolean isImportanceLockedByOEM() {
        return this.mImportanceLockedByOEM;
    }

    public void setImportanceLockedByCriticalDeviceFunction(boolean z) {
        this.mImportanceLockedDefaultApp = z;
    }

    public boolean isImportanceLockedByCriticalDeviceFunction() {
        return this.mImportanceLockedDefaultApp;
    }

    public int getOriginalImportance() {
        return this.mOriginalImportance;
    }

    public void setOriginalImportance(int i) {
        this.mOriginalImportance = i;
    }

    public void setDemoted(boolean z) {
        this.mDemoted = z;
    }

    public boolean isDemoted() {
        return this.mDemoted;
    }

    public boolean hasUserSetImportance() {
        return (this.mUserLockedFields & 4) != 0;
    }

    public boolean hasUserSetSound() {
        return (this.mUserLockedFields & 32) != 0;
    }

    public long getLastNotificationUpdateTimeMs() {
        return this.mLastNotificationUpdateTimeMs;
    }

    public void setLastNotificationUpdateTimeMs(long j) {
        this.mLastNotificationUpdateTimeMs = j;
    }

    public void populateFromXmlForRestore(XmlPullParser xmlPullParser, boolean z, Context context) {
        populateFromXml(XmlUtils.makeTyped(xmlPullParser), true, z, context);
    }

    @SystemApi
    public void populateFromXml(XmlPullParser xmlPullParser) {
        populateFromXml(XmlUtils.makeTyped(xmlPullParser), false, true, null);
    }

    private void populateFromXml(TypedXmlPullParser typedXmlPullParser, boolean z, boolean z2, Context context) {
        VibrationEffect safeVibrationEffect;
        Preconditions.checkArgument((z && context == null) ? false : true, "forRestore is true but got null context");
        setDescription(typedXmlPullParser.getAttributeValue(null, ATT_DESC));
        setBypassDnd(safeInt(typedXmlPullParser, "priority", 0) != 0);
        setLockscreenVisibility(safeInt(typedXmlPullParser, "visibility", -1000));
        Uri safeUri = safeUri(typedXmlPullParser, "sound");
        AudioAttributes safeAudioAttributes = safeAudioAttributes(typedXmlPullParser);
        int usage = safeAudioAttributes.getUsage();
        if (z) {
            safeUri = restoreSoundUri(context, safeUri, z2, usage);
        }
        setSound(safeUri, safeAudioAttributes);
        enableLights(safeBool(typedXmlPullParser, "lights", false));
        setLightColor(safeInt(typedXmlPullParser, ATT_LIGHT_COLOR, 0));
        setVibrationPattern(safeLongArray(typedXmlPullParser, "vibration", null));
        if (Flags.notificationChannelVibrationEffectApi() && (safeVibrationEffect = safeVibrationEffect(typedXmlPullParser, ATT_VIBRATION_EFFECT)) != null) {
            setVibrationEffect(safeVibrationEffect);
        }
        enableVibration(safeBool(typedXmlPullParser, ATT_VIBRATION_ENABLED, false));
        setShowBadge(safeBool(typedXmlPullParser, ATT_SHOW_BADGE, false));
        setDeleted(safeBool(typedXmlPullParser, "deleted", false));
        setDeletedTimeMs(XmlUtils.readLongAttribute(typedXmlPullParser, ATT_DELETED_TIME_MS, -1L));
        setGroup(typedXmlPullParser.getAttributeValue(null, ATT_GROUP));
        lockFields(safeInt(typedXmlPullParser, "locked", 0));
        setUserVisibleTaskShown(safeBool(typedXmlPullParser, ATT_FG_SERVICE_SHOWN, false));
        setBlockable(safeBool(typedXmlPullParser, ATT_BLOCKABLE_SYSTEM, false));
        setAllowBubbles(safeInt(typedXmlPullParser, ATT_ALLOW_BUBBLE, -1));
        setOriginalImportance(safeInt(typedXmlPullParser, ATT_ORIG_IMP, -1000));
        setConversationId(typedXmlPullParser.getAttributeValue(null, "parent"), typedXmlPullParser.getAttributeValue(null, ATT_CONVERSATION_ID));
        setDemoted(safeBool(typedXmlPullParser, ATT_DEMOTE, false));
        setImportantConversation(safeBool(typedXmlPullParser, ATT_IMP_CONVERSATION, false));
    }

    public boolean isSoundRestored() {
        return this.mSoundRestored;
    }

    private Uri getCanonicalizedSoundUri(ContentResolver contentResolver, Uri uri) {
        if (!Settings.System.DEFAULT_NOTIFICATION_URI.equals(uri)) {
            if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme())) {
                try {
                    contentResolver.getResourceId(uri);
                    return uri;
                } catch (FileNotFoundException unused) {
                    return null;
                }
            }
            if (!"file".equals(uri.getScheme())) {
                return contentResolver.canonicalize(uri);
            }
        }
        return uri;
    }

    private Uri getUncanonicalizedSoundUri(ContentResolver contentResolver, Uri uri, int i) {
        if (Settings.System.DEFAULT_NOTIFICATION_URI.equals(uri) || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme()) || "file".equals(uri.getScheme())) {
            return uri;
        }
        try {
            return RingtoneManager.getRingtoneUriForRestore(contentResolver, uri.toString(), 4 != i ? 6 == i ? 1 : 2 : 4);
        } catch (Exception e) {
            Log.e(TAG, "Failed to uncanonicalized sound uri for " + uri + " " + e);
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
    }

    public Uri restoreSoundUri(Context context, Uri uri, boolean z, int i) {
        String volumeName;
        Cursor query;
        if (uri == null || Uri.EMPTY.equals(uri)) {
            return null;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri canonicalizedSoundUri = getCanonicalizedSoundUri(contentResolver, uri);
        if (canonicalizedSoundUri == null) {
            if (!this.mSoundRestored && z) {
                this.mSoundRestored = true;
                this.mSoundMissingReason = 2;
                return Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            this.mSoundRestored = false;
            return uri;
        }
        this.mSoundRestored = true;
        Uri uncanonicalize = contentResolver.uncanonicalize(canonicalizedSoundUri);
        if (uncanonicalize != null) {
            return uncanonicalize;
        }
        try {
            volumeName = MediaStore.getVolumeName(canonicalizedSoundUri);
            String queryParameter = canonicalizedSoundUri.getQueryParameter("title");
            Bundle bundle = new Bundle();
            bundle.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, "title='" + queryParameter + "'");
            query = contentResolver.query(MediaStore.Audio.Media.getContentUri(volumeName), new String[]{"_id", "is_notification"}, bundle, null);
            try {
                try {
                } finally {
                    query.close();
                }
            } finally {
            }
        } catch (IllegalArgumentException e) {
            Slog.e("NotiChannel", "This is not MediaSore uri : " + canonicalizedSoundUri, e);
        }
        if (query.moveToFirst()) {
            Uri contentUri = MediaStore.Audio.Media.getContentUri(volumeName, query.getLong(0));
            if (contentUri == null) {
                this.mSoundMissingReason = 3;
            }
            if (query != null) {
            }
            return contentUri;
        }
        query.close();
        if (query != null) {
        }
        this.mSoundMissingReason = 4;
        return Settings.System.DEFAULT_NOTIFICATION_URI;
    }

    @SystemApi
    public void writeXml(XmlSerializer xmlSerializer) throws IOException {
        writeXml(XmlUtils.makeTyped(xmlSerializer), false, null);
    }

    public void writeXmlForBackup(XmlSerializer xmlSerializer, Context context) throws IOException {
        writeXml(XmlUtils.makeTyped(xmlSerializer), true, context);
    }

    private Uri getSoundForBackup(Context context) {
        Uri sound = getSound();
        if (sound == null || Uri.EMPTY.equals(sound)) {
            return null;
        }
        try {
            Uri canonicalize = context.getContentResolver().canonicalize(sound);
            return canonicalize == null ? Settings.System.DEFAULT_NOTIFICATION_URI : canonicalize;
        } catch (SecurityException e) {
            Slog.e("NotiChannel", sound + " uri permission from App", e);
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        } catch (Exception unused) {
            Slog.e(TAG, "Cannot find file for sound " + sound + " using default");
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
    }

    private void writeXml(TypedXmlSerializer typedXmlSerializer, boolean z, Context context) throws IOException {
        Preconditions.checkArgument((z && context == null) ? false : true, "forBackup is true but got null context");
        typedXmlSerializer.startTag(null, "channel");
        typedXmlSerializer.attribute(null, "id", getId());
        if (getName() != null) {
            typedXmlSerializer.attribute(null, "name", getName().toString());
        }
        if (getDescription() != null) {
            typedXmlSerializer.attribute(null, ATT_DESC, getDescription());
        }
        if (getImportance() != -1000) {
            typedXmlSerializer.attributeInt(null, "importance", getImportance());
        }
        if (canBypassDnd()) {
            typedXmlSerializer.attributeInt(null, "priority", 2);
        }
        if (getLockscreenVisibility() != -1000) {
            typedXmlSerializer.attributeInt(null, "visibility", getLockscreenVisibility());
        }
        Uri soundForBackup = z ? getSoundForBackup(context) : getSound();
        if (soundForBackup != null) {
            typedXmlSerializer.attribute(null, "sound", soundForBackup.toString());
        }
        if (getAudioAttributes() != null) {
            typedXmlSerializer.attributeInt(null, ATT_USAGE, getAudioAttributes().getUsage());
            typedXmlSerializer.attributeInt(null, ATT_CONTENT_TYPE, getAudioAttributes().getContentType());
            typedXmlSerializer.attributeInt(null, "flags", getAudioAttributes().getFlags());
        }
        if (shouldShowLights()) {
            typedXmlSerializer.attributeBoolean(null, "lights", shouldShowLights());
        }
        if (getLightColor() != 0) {
            typedXmlSerializer.attributeInt(null, ATT_LIGHT_COLOR, getLightColor());
        }
        if (shouldVibrate()) {
            typedXmlSerializer.attributeBoolean(null, ATT_VIBRATION_ENABLED, shouldVibrate());
        }
        if (getVibrationPattern() != null) {
            typedXmlSerializer.attribute(null, "vibration", longArrayToString(getVibrationPattern()));
        }
        if (getVibrationEffect() != null && (!Flags.notifChannelCropVibrationEffects() || getVibrationPattern() == null)) {
            typedXmlSerializer.attribute(null, ATT_VIBRATION_EFFECT, vibrationToString(getVibrationEffect()));
        }
        if (getUserLockedFields() != 0) {
            typedXmlSerializer.attributeInt(null, "locked", getUserLockedFields());
        }
        if (isUserVisibleTaskShown()) {
            typedXmlSerializer.attributeBoolean(null, ATT_FG_SERVICE_SHOWN, isUserVisibleTaskShown());
        }
        if (canShowBadge()) {
            typedXmlSerializer.attributeBoolean(null, ATT_SHOW_BADGE, canShowBadge());
        }
        if (isDeleted()) {
            typedXmlSerializer.attributeBoolean(null, "deleted", isDeleted());
        }
        if (getDeletedTimeMs() >= 0) {
            typedXmlSerializer.attributeLong(null, ATT_DELETED_TIME_MS, getDeletedTimeMs());
        }
        if (getGroup() != null) {
            typedXmlSerializer.attribute(null, ATT_GROUP, getGroup());
        }
        if (isBlockable()) {
            typedXmlSerializer.attributeBoolean(null, ATT_BLOCKABLE_SYSTEM, isBlockable());
        }
        if (getAllowBubbles() != -1) {
            typedXmlSerializer.attributeInt(null, ATT_ALLOW_BUBBLE, getAllowBubbles());
        }
        if (getOriginalImportance() != -1000) {
            typedXmlSerializer.attributeInt(null, ATT_ORIG_IMP, getOriginalImportance());
        }
        if (getParentChannelId() != null) {
            typedXmlSerializer.attribute(null, "parent", getParentChannelId());
        }
        if (getConversationId() != null) {
            typedXmlSerializer.attribute(null, ATT_CONVERSATION_ID, getConversationId());
        }
        if (isDemoted()) {
            typedXmlSerializer.attributeBoolean(null, ATT_DEMOTE, isDemoted());
        }
        if (isImportantConversation()) {
            typedXmlSerializer.attributeBoolean(null, ATT_IMP_CONVERSATION, isImportantConversation());
        }
        typedXmlSerializer.endTag(null, "channel");
    }

    @SystemApi
    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put("name", getName());
        jSONObject.put(ATT_DESC, getDescription());
        if (getImportance() != -1000) {
            jSONObject.put("importance", NotificationListenerService.Ranking.importanceToString(getImportance()));
        }
        if (canBypassDnd()) {
            jSONObject.put("priority", 2);
        }
        if (getLockscreenVisibility() != -1000) {
            jSONObject.put("visibility", Notification.visibilityToString(getLockscreenVisibility()));
        }
        if (getSound() != null) {
            jSONObject.put("sound", getSound().toString());
        }
        if (getAudioAttributes() != null) {
            jSONObject.put(ATT_USAGE, Integer.toString(getAudioAttributes().getUsage()));
            jSONObject.put(ATT_CONTENT_TYPE, Integer.toString(getAudioAttributes().getContentType()));
            jSONObject.put("flags", Integer.toString(getAudioAttributes().getFlags()));
        }
        jSONObject.put("lights", Boolean.toString(shouldShowLights()));
        jSONObject.put(ATT_LIGHT_COLOR, Integer.toString(getLightColor()));
        jSONObject.put(ATT_VIBRATION_ENABLED, Boolean.toString(shouldVibrate()));
        jSONObject.put("locked", Integer.toString(getUserLockedFields()));
        jSONObject.put(ATT_FG_SERVICE_SHOWN, Boolean.toString(isUserVisibleTaskShown()));
        jSONObject.put("vibration", longArrayToString(getVibrationPattern()));
        if (getVibrationEffect() != null) {
            jSONObject.put(ATT_VIBRATION_EFFECT, vibrationToString(getVibrationEffect()));
        }
        jSONObject.put(ATT_SHOW_BADGE, Boolean.toString(canShowBadge()));
        jSONObject.put("deleted", Boolean.toString(isDeleted()));
        jSONObject.put(ATT_DELETED_TIME_MS, Long.toString(getDeletedTimeMs()));
        jSONObject.put(ATT_GROUP, getGroup());
        jSONObject.put(ATT_BLOCKABLE_SYSTEM, isBlockable());
        jSONObject.put(ATT_ALLOW_BUBBLE, getAllowBubbles());
        return jSONObject;
    }

    private static AudioAttributes safeAudioAttributes(TypedXmlPullParser typedXmlPullParser) {
        int safeInt = safeInt(typedXmlPullParser, ATT_USAGE, 5);
        int safeInt2 = safeInt(typedXmlPullParser, ATT_CONTENT_TYPE, 4);
        return new AudioAttributes.Builder().setUsage(safeInt).setContentType(safeInt2).setFlags(safeInt(typedXmlPullParser, "flags", 0)).build();
    }

    private static Uri safeUri(TypedXmlPullParser typedXmlPullParser, String str) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return null;
        }
        return Uri.parse(attributeValue);
    }

    private static String vibrationToString(VibrationEffect vibrationEffect) {
        StringWriter stringWriter = new StringWriter();
        try {
            VibrationXmlSerializer.serialize(vibrationEffect, stringWriter, 1);
        } catch (IOException e) {
            Log.e(TAG, "Unable to serialize vibration: " + vibrationEffect, e);
        }
        return stringWriter.toString();
    }

    private static VibrationEffect safeVibrationEffect(TypedXmlPullParser typedXmlPullParser, String str) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue != null) {
            try {
                return VibrationXmlParser.parseVibrationEffect(new StringReader(attributeValue), 1);
            } catch (IOException e) {
                Log.e(TAG, "Unable to read serialized vibration effect", e);
            }
        }
        return null;
    }

    private static int safeInt(TypedXmlPullParser typedXmlPullParser, String str, int i) {
        return typedXmlPullParser.getAttributeInt(null, str, i);
    }

    private static boolean safeBool(TypedXmlPullParser typedXmlPullParser, String str, boolean z) {
        return typedXmlPullParser.getAttributeBoolean(null, str, z);
    }

    private static long[] safeLongArray(TypedXmlPullParser typedXmlPullParser, String str, long[] jArr) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (TextUtils.isEmpty(attributeValue)) {
            return jArr;
        }
        String[] split = attributeValue.split(",");
        long[] jArr2 = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                jArr2[i] = Long.parseLong(split[i]);
            } catch (NumberFormatException unused) {
                jArr2[i] = 0;
            }
        }
        return jArr2;
    }

    private static String longArrayToString(long[] jArr) {
        StringBuilder sb = new StringBuilder();
        if (jArr != null && jArr.length > 0) {
            for (int i = 0; i < jArr.length - 1; i++) {
                sb.append(jArr[i]);
                sb.append(",");
            }
            sb.append(jArr[jArr.length - 1]);
        }
        return sb.toString();
    }

    public static String getChannelIdForBundleType(int i) {
        if (i == 1) {
            return PROMOTIONS_ID;
        }
        if (i == 2) {
            return SOCIAL_MEDIA_ID;
        }
        if (i == 3) {
            return NEWS_ID;
        }
        if (i != 4) {
            return null;
        }
        return RECS_ID;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NotificationChannel notificationChannel = (NotificationChannel) obj;
            if (getImportance() == notificationChannel.getImportance() && this.mBypassDnd == notificationChannel.mBypassDnd && getLockscreenVisibility() == notificationChannel.getLockscreenVisibility() && this.mLights == notificationChannel.mLights && getLightColor() == notificationChannel.getLightColor() && getUserLockedFields() == notificationChannel.getUserLockedFields() && isUserVisibleTaskShown() == notificationChannel.isUserVisibleTaskShown() && this.mVibrationEnabled == notificationChannel.mVibrationEnabled && this.mShowBadge == notificationChannel.mShowBadge && isDeleted() == notificationChannel.isDeleted() && getDeletedTimeMs() == notificationChannel.getDeletedTimeMs() && isBlockable() == notificationChannel.isBlockable() && this.mAllowBubbles == notificationChannel.mAllowBubbles && Objects.equals(getId(), notificationChannel.getId()) && Objects.equals(getName(), notificationChannel.getName()) && Objects.equals(this.mDesc, notificationChannel.mDesc) && Objects.equals(getSound(), notificationChannel.getSound()) && Arrays.equals(this.mVibrationPattern, notificationChannel.mVibrationPattern) && Objects.equals(getVibrationEffect(), notificationChannel.getVibrationEffect()) && Objects.equals(getGroup(), notificationChannel.getGroup()) && Objects.equals(getAudioAttributes(), notificationChannel.getAudioAttributes()) && isImportanceLockedByOEM() == notificationChannel.isImportanceLockedByOEM() && this.mImportanceLockedDefaultApp == notificationChannel.mImportanceLockedDefaultApp && this.mOriginalImportance == notificationChannel.mOriginalImportance && Objects.equals(getParentChannelId(), notificationChannel.getParentChannelId()) && Objects.equals(getConversationId(), notificationChannel.getConversationId()) && isDemoted() == notificationChannel.isDemoted() && isImportantConversation() == notificationChannel.isImportantConversation()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(getId(), getName(), this.mDesc, Integer.valueOf(getImportance()), Boolean.valueOf(this.mBypassDnd), Integer.valueOf(getLockscreenVisibility()), getSound(), Boolean.valueOf(this.mLights), Integer.valueOf(getLightColor()), Integer.valueOf(getUserLockedFields()), Boolean.valueOf(isUserVisibleTaskShown()), Boolean.valueOf(this.mVibrationEnabled), Boolean.valueOf(this.mShowBadge), Boolean.valueOf(isDeleted()), Long.valueOf(getDeletedTimeMs()), getGroup(), getAudioAttributes(), Boolean.valueOf(isBlockable()), Integer.valueOf(this.mAllowBubbles), Boolean.valueOf(this.mImportanceLockedByOEM), Boolean.valueOf(this.mImportanceLockedDefaultApp), Integer.valueOf(this.mOriginalImportance), getVibrationEffect(), this.mParentId, this.mConversationId, Boolean.valueOf(this.mDemoted), Boolean.valueOf(this.mImportantConvo)) * 31) + Arrays.hashCode(this.mVibrationPattern);
    }

    public void dump(PrintWriter printWriter, String str, boolean z) {
        String str2 = z ? (String) TextUtils.trimToLengthWithEllipsis(this.mName, 6) : this.mName;
        printWriter.println(str + ("NotificationChannel{mId='" + getRedatedString(this.mId) + "', mName=" + str2 + getFieldsString() + '}'));
    }

    public String toString() {
        return "NotificationChannel{mId='" + getRedatedString(this.mId) + "', mName=" + this.mName + getFieldsString() + '}';
    }

    private String getRedatedString(String str) {
        return isMatchPrivatePattern(str) ? (String) TextUtils.trimToLengthWithEllipsis(str, 6) : str;
    }

    private boolean isMatchPrivatePattern(String str) {
        if (str == null) {
            return false;
        }
        if (Patterns.PHONE.matcher(str).matches() || Patterns.WEB_URL.matcher(str).matches()) {
            return true;
        }
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '@') {
                z = true;
            }
            if (z && str.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    private String getFieldsString() {
        String redatedString = getRedatedString(this.mGroup);
        StringBuilder sb = new StringBuilder(", mDescription=");
        sb.append(!TextUtils.isEmpty(this.mDesc) ? "hasDescription " : "");
        sb.append(", mImportance=");
        sb.append(this.mImportance);
        sb.append(", mBypassDnd=");
        sb.append(this.mBypassDnd);
        sb.append(", mLockscreenVisibility=");
        sb.append(this.mLockscreenVisibility);
        sb.append(", mSound=");
        sb.append(this.mSound);
        sb.append(", mLights=");
        sb.append(this.mLights);
        sb.append(", mLightColor=");
        sb.append(this.mLightColor);
        sb.append(", mVibrationPattern=");
        sb.append(Arrays.toString(this.mVibrationPattern));
        sb.append(", mVibrationEffect=");
        VibrationEffect vibrationEffect = this.mVibrationEffect;
        sb.append(vibrationEffect == null ? PerfettoProtoLogImpl.NULL_STRING : vibrationEffect.toString());
        sb.append(", mUserLockedFields=");
        sb.append(Integer.toHexString(this.mUserLockedFields));
        sb.append(", mUserVisibleTaskShown=");
        sb.append(this.mUserVisibleTaskShown);
        sb.append(", mVibrationEnabled=");
        sb.append(this.mVibrationEnabled);
        sb.append(", mShowBadge=");
        sb.append(this.mShowBadge);
        sb.append(", mDeleted=");
        sb.append(this.mDeleted);
        sb.append(", mDeletedTimeMs=");
        sb.append(this.mDeletedTime);
        sb.append(", mGroup='");
        sb.append(redatedString);
        sb.append("', mAudioAttributes=");
        sb.append(this.mAudioAttributes);
        sb.append(", mBlockableSystem=");
        sb.append(this.mBlockableSystem);
        sb.append(", mAllowBubbles=");
        sb.append(this.mAllowBubbles);
        sb.append(", mImportanceLockedByOEM=");
        sb.append(this.mImportanceLockedByOEM);
        sb.append(", mImportanceLockedDefaultApp=");
        sb.append(this.mImportanceLockedDefaultApp);
        sb.append(", mOriginalImp=");
        sb.append(this.mOriginalImportance);
        sb.append(", mParent=");
        sb.append(this.mParentId);
        sb.append(", mConversationId=");
        sb.append(this.mConversationId);
        sb.append(", mDemoted=");
        sb.append(this.mDemoted);
        sb.append(", mImportantConvo=");
        sb.append(this.mImportantConvo);
        sb.append(", mLastNotificationUpdateTimeMs=");
        sb.append(this.mLastNotificationUpdateTimeMs);
        sb.append(", mSoundMissingReason=");
        sb.append(this.mSoundMissingReason);
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1138166333443L, this.mDesc);
        protoOutputStream.write(1120986464260L, this.mImportance);
        protoOutputStream.write(1133871366149L, this.mBypassDnd);
        protoOutputStream.write(1120986464262L, this.mLockscreenVisibility);
        Uri uri = this.mSound;
        if (uri != null) {
            protoOutputStream.write(1138166333447L, uri.toString());
        }
        protoOutputStream.write(1133871366152L, this.mLights);
        protoOutputStream.write(1120986464265L, this.mLightColor);
        long[] jArr = this.mVibrationPattern;
        if (jArr != null) {
            for (long j2 : jArr) {
                protoOutputStream.write(NotificationChannelProto.VIBRATION, j2);
            }
        }
        protoOutputStream.write(1120986464267L, this.mUserLockedFields);
        protoOutputStream.write(1133871366162L, this.mUserVisibleTaskShown);
        protoOutputStream.write(1133871366156L, this.mVibrationEnabled);
        protoOutputStream.write(1133871366157L, this.mShowBadge);
        protoOutputStream.write(1133871366158L, this.mDeleted);
        protoOutputStream.write(1138166333455L, this.mGroup);
        AudioAttributes audioAttributes = this.mAudioAttributes;
        if (audioAttributes != null) {
            audioAttributes.dumpDebug(protoOutputStream, 1146756268048L);
        }
        protoOutputStream.write(1133871366161L, this.mBlockableSystem);
        protoOutputStream.write(1133871366163L, this.mAllowBubbles);
        protoOutputStream.end(start);
    }
}
