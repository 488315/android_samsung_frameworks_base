package android.service.notification;

import android.app.jank.AppJankStats;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioSystem;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Contacts;
import android.util.proto.ProtoOutputStream;
import com.samsung.android.knox.analytics.database.Contract;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ZenPolicy implements Parcelable {
    public static final int CHANNEL_POLICY_NONE = 2;
    public static final int CHANNEL_POLICY_PRIORITY = 1;
    public static final int CHANNEL_POLICY_UNSET = 0;
    public static final int CONVERSATION_SENDERS_ANYONE = 1;
    public static final int CONVERSATION_SENDERS_IMPORTANT = 2;
    public static final int CONVERSATION_SENDERS_NONE = 3;
    public static final int CONVERSATION_SENDERS_UNSET = 0;
    public static final Parcelable.Creator<ZenPolicy> CREATOR = new Parcelable.Creator<ZenPolicy>() { // from class: android.service.notification.ZenPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenPolicy createFromParcel(Parcel parcel) {
            ZenPolicy zenPolicy = new ZenPolicy();
            zenPolicy.mPriorityCategories = ZenPolicy.trimList(parcel.readArrayList(Integer.class.getClassLoader(), Integer.class), 9);
            zenPolicy.mVisualEffects = ZenPolicy.trimList(parcel.readArrayList(Integer.class.getClassLoader(), Integer.class), 7);
            zenPolicy.mPriorityMessages = parcel.readInt();
            zenPolicy.mPriorityCalls = parcel.readInt();
            zenPolicy.mConversationSenders = parcel.readInt();
            zenPolicy.mAllowChannels = parcel.readInt();
            zenPolicy.mAppBypassDndFlag = parcel.readInt();
            zenPolicy.mAppsToBypassDnd = parcel.readArrayList(String.class.getClassLoader());
            zenPolicy.mExceptionContactsFlag = parcel.readInt();
            zenPolicy.mExceptionContacts = parcel.readArrayList(String.class.getClassLoader());
            zenPolicy.mIsContactsOverridden = parcel.readBoolean();
            zenPolicy.mIsAppBypassDndOverridden = parcel.readBoolean();
            return zenPolicy;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenPolicy[] newArray(int i) {
            return new ZenPolicy[i];
        }
    };
    public static final int FIELD_ALLOW_CHANNELS = 8;
    public static final int FIELD_CALLS = 2;
    public static final int FIELD_CONVERSATIONS = 4;
    public static final int FIELD_MESSAGES = 1;
    public static final int FIELD_PRIORITY_CATEGORY_ALARMS = 128;
    public static final int FIELD_PRIORITY_CATEGORY_EVENTS = 32;
    public static final int FIELD_PRIORITY_CATEGORY_MEDIA = 256;
    public static final int FIELD_PRIORITY_CATEGORY_REMINDERS = 16;
    public static final int FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS = 64;
    public static final int FIELD_PRIORITY_CATEGORY_SYSTEM = 512;
    public static final int FIELD_VISUAL_EFFECT_AMBIENT = 32768;
    public static final int FIELD_VISUAL_EFFECT_BADGE = 16384;
    public static final int FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT = 1024;
    public static final int FIELD_VISUAL_EFFECT_LIGHTS = 2048;
    public static final int FIELD_VISUAL_EFFECT_NOTIFICATION_LIST = 65536;
    public static final int FIELD_VISUAL_EFFECT_PEEK = 4096;
    public static final int FIELD_VISUAL_EFFECT_STATUS_BAR = 8192;
    public static final int NUM_PRIORITY_CATEGORIES = 9;
    public static final int NUM_VISUAL_EFFECTS = 7;
    public static final int PEOPLE_TYPE_ANYONE = 1;
    public static final int PEOPLE_TYPE_CONTACTS = 2;
    public static final int PEOPLE_TYPE_NONE = 4;
    public static final int PEOPLE_TYPE_STARRED = 3;
    public static final int PEOPLE_TYPE_UNSET = 0;
    public static final int PRIORITY_CATEGORY_ALARMS = 5;
    public static final int PRIORITY_CATEGORY_CALLS = 3;
    public static final int PRIORITY_CATEGORY_CONVERSATIONS = 8;
    public static final int PRIORITY_CATEGORY_EVENTS = 1;
    public static final int PRIORITY_CATEGORY_MEDIA = 6;
    public static final int PRIORITY_CATEGORY_MESSAGES = 2;
    public static final int PRIORITY_CATEGORY_REMINDERS = 0;
    public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 4;
    public static final int PRIORITY_CATEGORY_SYSTEM = 7;
    public static final int SELECTED_APPS_ALLOWED = 0;
    public static final int SELECTED_APPS_ALLOWED_UNSET = -1;
    public static final int SELECTED_APPS_DISALLOWED = 1;
    public static final int SELECTED_CONTACTS_ALLOWED = 0;
    public static final int SELECTED_CONTACTS_ALLOWED_UNSET = -1;
    public static final int SELECTED_CONTACTS_DISALLOWED = 1;
    public static final int STATE_ALLOW = 1;
    public static final int STATE_DISALLOW = 2;
    public static final int STATE_UNSET = 0;
    public static final int VISUAL_EFFECT_AMBIENT = 5;
    public static final int VISUAL_EFFECT_BADGE = 4;
    public static final int VISUAL_EFFECT_FULL_SCREEN_INTENT = 0;
    public static final int VISUAL_EFFECT_LIGHTS = 1;
    public static final int VISUAL_EFFECT_NOTIFICATION_LIST = 6;
    public static final int VISUAL_EFFECT_PEEK = 2;
    public static final int VISUAL_EFFECT_STATUS_BAR = 3;
    private int mAllowChannels;
    private int mAppBypassDndFlag;
    private ArrayList<String> mAppsToBypassDnd;
    private int mConversationSenders;
    private ArrayList<String> mExceptionContacts;
    private int mExceptionContactsFlag;
    private boolean mIsAppBypassDndOverridden;
    private boolean mIsContactsOverridden;
    private int mPriorityCalls;
    private List<Integer> mPriorityCategories;
    private int mPriorityMessages;
    private List<Integer> mVisualEffects;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ChannelType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConversationSenders {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PeopleType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PriorityCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VisualEffect {
    }

    public static boolean stateToBoolean(int i, boolean z) {
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            return z;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ZenPolicy() {
        this.mPriorityMessages = 0;
        this.mPriorityCalls = 0;
        this.mConversationSenders = 0;
        this.mAllowChannels = 0;
        this.mIsContactsOverridden = false;
        this.mIsAppBypassDndOverridden = false;
        this.mExceptionContactsFlag = -1;
        this.mAppBypassDndFlag = -1;
        this.mPriorityCategories = new ArrayList(Collections.nCopies(9, 0));
        this.mVisualEffects = new ArrayList(Collections.nCopies(7, 0));
        this.mAppsToBypassDnd = new ArrayList<>();
        this.mExceptionContacts = new ArrayList<>();
    }

    public ZenPolicy(List<Integer> list, List<Integer> list2, int i, int i2, int i3, int i4, int i5, ArrayList<String> arrayList, int i6, ArrayList<String> arrayList2) {
        this.mIsContactsOverridden = false;
        this.mIsAppBypassDndOverridden = false;
        this.mPriorityCategories = list;
        this.mVisualEffects = list2;
        this.mPriorityMessages = i;
        this.mPriorityCalls = i2;
        this.mConversationSenders = i3;
        this.mAllowChannels = i4;
        this.mExceptionContactsFlag = i5;
        this.mAppBypassDndFlag = i6;
        this.mAppsToBypassDnd = arrayList2 == null ? new ArrayList<>() : arrayList2;
        this.mExceptionContacts = arrayList == null ? new ArrayList<>() : arrayList;
        this.mIsContactsOverridden = !r9.isEmpty();
        this.mIsAppBypassDndOverridden = !this.mAppsToBypassDnd.isEmpty();
    }

    public static ZenPolicy getBasePolicyInterruptionFilterAlarms() {
        return new Builder().disallowAllSounds().allowAlarms(true).allowMedia(true).allowPriorityChannels(false).build();
    }

    public static ZenPolicy getBasePolicyInterruptionFilterNone() {
        return new Builder().disallowAllSounds().allowPriorityChannels(false).build();
    }

    public int getPriorityConversationSenders() {
        return this.mConversationSenders;
    }

    public int getPriorityMessageSenders() {
        return this.mPriorityMessages;
    }

    public int getPriorityCallSenders() {
        return this.mPriorityCalls;
    }

    public int getPriorityCategoryConversations() {
        return this.mPriorityCategories.get(8).intValue();
    }

    public int getPriorityCategoryReminders() {
        return this.mPriorityCategories.get(0).intValue();
    }

    public int getPriorityCategoryEvents() {
        return this.mPriorityCategories.get(1).intValue();
    }

    public int getPriorityCategoryMessages() {
        return this.mPriorityCategories.get(2).intValue();
    }

    public int getPriorityCategoryCalls() {
        return this.mPriorityCategories.get(3).intValue();
    }

    public int getPriorityCategoryRepeatCallers() {
        return this.mPriorityCategories.get(4).intValue();
    }

    public int getPriorityCategoryAlarms() {
        return this.mPriorityCategories.get(5).intValue();
    }

    public int getPriorityCategoryMedia() {
        return this.mPriorityCategories.get(6).intValue();
    }

    public int getPriorityCategorySystem() {
        return this.mPriorityCategories.get(7).intValue();
    }

    public int getVisualEffectFullScreenIntent() {
        return this.mVisualEffects.get(0).intValue();
    }

    public int getVisualEffectLights() {
        return this.mVisualEffects.get(1).intValue();
    }

    public int getVisualEffectPeek() {
        return this.mVisualEffects.get(2).intValue();
    }

    public int getVisualEffectStatusBar() {
        return this.mVisualEffects.get(3).intValue();
    }

    public int getVisualEffectBadge() {
        return this.mVisualEffects.get(4).intValue();
    }

    public int getVisualEffectAmbient() {
        return this.mVisualEffects.get(5).intValue();
    }

    public int getVisualEffectNotificationList() {
        return this.mVisualEffects.get(6).intValue();
    }

    public int getAllowedChannels() {
        return this.mAllowChannels;
    }

    public int getPriorityChannelsAllowed() {
        int i = this.mAllowChannels;
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public boolean shouldHideAllVisualEffects() {
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldShowAllVisualEffects() {
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 1) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getAppsToBypassDnd() {
        return this.mAppsToBypassDnd;
    }

    public ArrayList<String> getExceptionContacts() {
        return this.mExceptionContacts;
    }

    public boolean isContactsOverridden() {
        return this.mIsContactsOverridden;
    }

    public boolean isAppBypassDndOverridden() {
        return this.mIsAppBypassDndOverridden;
    }

    private String appsToBypassDndToString(ArrayList<String> arrayList) {
        StringBuilder sb = new StringBuilder("{");
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (i != 0) {
                    sb.append(", =");
                }
                sb.append(arrayList.get(i));
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public int getExceptionContactsFlag() {
        return this.mExceptionContactsFlag;
    }

    public int getAppBypassDndFlag() {
        return this.mAppBypassDndFlag;
    }

    public static final class Builder {
        private ZenPolicy mZenPolicy;

        public Builder() {
            this.mZenPolicy = new ZenPolicy();
        }

        public Builder(ZenPolicy zenPolicy) {
            if (zenPolicy != null) {
                this.mZenPolicy = zenPolicy.copy();
            } else {
                this.mZenPolicy = new ZenPolicy();
            }
        }

        public ZenPolicy build() {
            return new ZenPolicy(new ArrayList(this.mZenPolicy.mPriorityCategories), new ArrayList(this.mZenPolicy.mVisualEffects), this.mZenPolicy.mPriorityMessages, this.mZenPolicy.mPriorityCalls, this.mZenPolicy.mConversationSenders, this.mZenPolicy.mAllowChannels, this.mZenPolicy.mExceptionContactsFlag, new ArrayList(this.mZenPolicy.mExceptionContacts), this.mZenPolicy.mAppBypassDndFlag, new ArrayList(this.mZenPolicy.mAppsToBypassDnd));
        }

        public Builder allowAllSounds() {
            for (int i = 0; i < this.mZenPolicy.mPriorityCategories.size(); i++) {
                this.mZenPolicy.mPriorityCategories.set(i, 1);
            }
            this.mZenPolicy.mPriorityMessages = 1;
            this.mZenPolicy.mPriorityCalls = 1;
            this.mZenPolicy.mConversationSenders = 1;
            return this;
        }

        public Builder disallowAllSounds() {
            for (int i = 0; i < this.mZenPolicy.mPriorityCategories.size(); i++) {
                this.mZenPolicy.mPriorityCategories.set(i, 2);
            }
            this.mZenPolicy.mPriorityMessages = 4;
            this.mZenPolicy.mPriorityCalls = 4;
            this.mZenPolicy.mConversationSenders = 3;
            return this;
        }

        public Builder showAllVisualEffects() {
            for (int i = 0; i < this.mZenPolicy.mVisualEffects.size(); i++) {
                this.mZenPolicy.mVisualEffects.set(i, 1);
            }
            return this;
        }

        public Builder hideAllVisualEffects() {
            for (int i = 0; i < this.mZenPolicy.mVisualEffects.size(); i++) {
                this.mZenPolicy.mVisualEffects.set(i, 2);
            }
            return this;
        }

        public Builder unsetPriorityCategory(int i) {
            this.mZenPolicy.mPriorityCategories.set(i, 0);
            if (i == 2) {
                this.mZenPolicy.mPriorityMessages = 0;
                return this;
            }
            if (i == 3) {
                this.mZenPolicy.mPriorityCalls = 0;
                return this;
            }
            if (i == 8) {
                this.mZenPolicy.mConversationSenders = 0;
            }
            return this;
        }

        public Builder unsetVisualEffect(int i) {
            this.mZenPolicy.mVisualEffects.set(i, 0);
            return this;
        }

        public Builder allowReminders(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(0, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder allowEvents(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(1, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder allowConversations(int i) {
            if (i == 0) {
                return unsetPriorityCategory(8);
            }
            if (i == 3) {
                this.mZenPolicy.mPriorityCategories.set(8, 2);
            } else {
                if (i != 1 && i != 2) {
                    return this;
                }
                this.mZenPolicy.mPriorityCategories.set(8, 1);
            }
            this.mZenPolicy.mConversationSenders = i;
            return this;
        }

        public Builder allowMessages(int i) {
            if (i == 0) {
                return unsetPriorityCategory(2);
            }
            if (i == 4) {
                this.mZenPolicy.mPriorityCategories.set(2, 2);
            } else {
                if (i != 1 && i != 2 && i != 3) {
                    return this;
                }
                this.mZenPolicy.mPriorityCategories.set(2, 1);
            }
            this.mZenPolicy.mPriorityMessages = i;
            return this;
        }

        public Builder allowCalls(int i) {
            if (i == 0) {
                return unsetPriorityCategory(3);
            }
            if (i == 4) {
                this.mZenPolicy.mPriorityCategories.set(3, 2);
            } else {
                if (i != 1 && i != 2 && i != 3) {
                    return this;
                }
                this.mZenPolicy.mPriorityCategories.set(3, 1);
            }
            this.mZenPolicy.mPriorityCalls = i;
            return this;
        }

        public Builder allowRepeatCallers(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(4, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder allowAlarms(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(5, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder allowMedia(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(6, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder allowSystem(boolean z) {
            this.mZenPolicy.mPriorityCategories.set(7, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder allowCategory(int i, boolean z) {
            if (i == 0) {
                allowReminders(z);
                return this;
            }
            if (i == 1) {
                allowEvents(z);
                return this;
            }
            if (i == 4) {
                allowRepeatCallers(z);
                return this;
            }
            if (i == 5) {
                allowAlarms(z);
                return this;
            }
            if (i == 6) {
                allowMedia(z);
                return this;
            }
            if (i != 7) {
                return this;
            }
            allowSystem(z);
            return this;
        }

        public Builder allowAppsToBypassDnd(String str) {
            if (str.length() < 1) {
                return this;
            }
            for (String str2 : str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
                this.mZenPolicy.mAppsToBypassDnd.add(str2);
            }
            this.mZenPolicy.mIsAppBypassDndOverridden = true;
            return this;
        }

        public Builder allowExceptionContacts(String str) {
            if (str.length() < 1) {
                return this;
            }
            for (String str2 : str.split(",")) {
                this.mZenPolicy.mExceptionContacts.add(str2);
            }
            this.mZenPolicy.mIsContactsOverridden = true;
            return this;
        }

        public Builder setExceptionContactsFlag(int i) {
            this.mZenPolicy.mExceptionContactsFlag = i;
            return this;
        }

        public Builder setAppBypassDndFlag(int i) {
            this.mZenPolicy.mAppBypassDndFlag = i;
            return this;
        }

        public Builder showFullScreenIntent(boolean z) {
            this.mZenPolicy.mVisualEffects.set(0, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showLights(boolean z) {
            this.mZenPolicy.mVisualEffects.set(1, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showPeeking(boolean z) {
            this.mZenPolicy.mVisualEffects.set(2, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showStatusBarIcons(boolean z) {
            this.mZenPolicy.mVisualEffects.set(3, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showBadges(boolean z) {
            this.mZenPolicy.mVisualEffects.set(4, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showInAmbientDisplay(boolean z) {
            this.mZenPolicy.mVisualEffects.set(5, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showInNotificationList(boolean z) {
            this.mZenPolicy.mVisualEffects.set(6, Integer.valueOf(z ? 1 : 2));
            return this;
        }

        public Builder showVisualEffect(int i, boolean z) {
            switch (i) {
                case 0:
                    showFullScreenIntent(z);
                    break;
                case 1:
                    showLights(z);
                    break;
                case 2:
                    showPeeking(z);
                    break;
                case 3:
                    showStatusBarIcons(z);
                    break;
                case 4:
                    showBadges(z);
                    break;
                case 5:
                    showInAmbientDisplay(z);
                    break;
                case 6:
                    showInNotificationList(z);
                    break;
            }
            return this;
        }

        public Builder allowPriorityChannels(boolean z) {
            this.mZenPolicy.mAllowChannels = z ? 1 : 2;
            return this;
        }

        public Builder allowChannels(int i) {
            this.mZenPolicy.mAllowChannels = i;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.mPriorityCategories);
        parcel.writeList(this.mVisualEffects);
        parcel.writeInt(this.mPriorityMessages);
        parcel.writeInt(this.mPriorityCalls);
        parcel.writeInt(this.mConversationSenders);
        parcel.writeInt(this.mAllowChannels);
        parcel.writeInt(this.mAppBypassDndFlag);
        parcel.writeList(this.mAppsToBypassDnd);
        parcel.writeInt(this.mExceptionContactsFlag);
        parcel.writeList(this.mExceptionContacts);
        parcel.writeBoolean(this.mIsContactsOverridden);
        parcel.writeBoolean(this.mIsAppBypassDndOverridden);
    }

    public String toString() {
        return "ZenPolicy{priorityCategories=[" + priorityCategoriesToString() + "], visualEffects=[" + visualEffectsToString() + "], priorityCallsSenders=" + peopleTypeToString(this.mPriorityCalls) + ", priorityMessagesSenders=" + peopleTypeToString(this.mPriorityMessages) + ", priorityConversationSenders=" + conversationTypeToString(this.mConversationSenders) + ", allowChannels=" + channelTypeToString(this.mAllowChannels) + ", appBypassDndFlag=" + appBypassDndFlagToString(this.mAppBypassDndFlag) + ", appsToBypassDnd=" + appsToBypassDndToString(this.mAppsToBypassDnd) + ", exceptionContactsFlag=" + exceptionContactsFlagToString(this.mExceptionContactsFlag) + ", exceptionContacts=" + appsToBypassDndToString(this.mExceptionContacts) + ", isContactsOverridden=" + Boolean.toString(this.mIsContactsOverridden) + ", isAppBypassDndOverridden=" + Boolean.toString(this.mIsAppBypassDndOverridden) + '}';
    }

    public static String fieldsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("FIELD_MESSAGES");
        }
        if ((i & 2) != 0) {
            arrayList.add("FIELD_CALLS");
        }
        if ((i & 4) != 0) {
            arrayList.add("FIELD_CONVERSATIONS");
        }
        if ((i & 8) != 0) {
            arrayList.add("FIELD_ALLOW_CHANNELS");
        }
        if ((i & 16) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_REMINDERS");
        }
        if ((i & 32) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_EVENTS");
        }
        if ((i & 64) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS");
        }
        if ((i & 128) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_ALARMS");
        }
        if ((i & 256) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_MEDIA");
        }
        if ((i & 512) != 0) {
            arrayList.add("FIELD_PRIORITY_CATEGORY_SYSTEM");
        }
        if ((i & 1024) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT");
        }
        if ((i & 2048) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_LIGHTS");
        }
        if ((i & 4096) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_PEEK");
        }
        if ((i & 8192) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_STATUS_BAR");
        }
        if ((i & 16384) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_BADGE");
        }
        if ((32768 & i) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_AMBIENT");
        }
        if ((i & 65536) != 0) {
            arrayList.add("FIELD_VISUAL_EFFECT_NOTIFICATION_LIST");
        }
        return "{" + String.join(",", arrayList) + "}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList<Integer> trimList(ArrayList<Integer> arrayList, int i) {
        return (arrayList == null || arrayList.size() <= i) ? arrayList : new ArrayList<>(arrayList.subList(0, i));
    }

    private String priorityCategoriesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mPriorityCategories.size(); i++) {
            if (this.mPriorityCategories.get(i).intValue() != 0) {
                sb.append(indexToCategory(i));
                sb.append("=");
                sb.append(stateToString(this.mPriorityCategories.get(i).intValue()));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String visualEffectsToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 0) {
                sb.append(indexToVisualEffect(i));
                sb.append("=");
                sb.append(stateToString(this.mVisualEffects.get(i).intValue()));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String indexToVisualEffect(int i) {
        switch (i) {
            case 0:
                return "fullScreenIntent";
            case 1:
                return Context.LIGHTS_SERVICE;
            case 2:
                return "peek";
            case 3:
                return "statusBar";
            case 4:
                return "badge";
            case 5:
                return AudioSystem.DEVICE_IN_AMBIENT_NAME;
            case 6:
                return "notificationList";
            default:
                return null;
        }
    }

    private String indexToCategory(int i) {
        switch (i) {
            case 0:
                return "reminders";
            case 1:
                return Contract.Events.PATH;
            case 2:
                return "messages";
            case 3:
                return "calls";
            case 4:
                return "repeatCallers";
            case 5:
                return "alarms";
            case 6:
                return AppJankStats.WIDGET_CATEGORY_MEDIA;
            case 7:
                return "system";
            case 8:
                return "convs";
            default:
                return null;
        }
    }

    private String stateToString(int i) {
        if (i == 0) {
            return "unset";
        }
        if (i == 1) {
            return "allow";
        }
        if (i == 2) {
            return "disallow";
        }
        return "invalidState{" + i + "}";
    }

    public static String peopleTypeToString(int i) {
        if (i == 0) {
            return "unset";
        }
        if (i == 1) {
            return "anyone";
        }
        if (i == 2) {
            return Contacts.AUTHORITY;
        }
        if (i == 3) {
            return "starred_contacts";
        }
        if (i == 4) {
            return "none";
        }
        return "invalidPeopleType{" + i + "}";
    }

    public static String conversationTypeToString(int i) {
        if (i == 0) {
            return "unset";
        }
        if (i == 1) {
            return "anyone";
        }
        if (i == 2) {
            return "important";
        }
        if (i == 3) {
            return "none";
        }
        return "invalidConversationType{" + i + "}";
    }

    public static String channelTypeToString(int i) {
        if (i == 0) {
            return "unset";
        }
        if (i == 1) {
            return "priority";
        }
        if (i == 2) {
            return "none";
        }
        return "invalidChannelType{" + i + "}";
    }

    public static String exceptionContactsFlagToString(int i) {
        if (i == -1) {
            return "unset";
        }
        if (i == 0) {
            return "SELECTED_CONTACTS_ALLOWED";
        }
        if (i == 1) {
            return "SELECTED_CONTACTS_DISALLOWED";
        }
        return "SELECTED_CONTACTS_UNKNOWN_" + i;
    }

    public static String appBypassDndFlagToString(int i) {
        if (i == -1) {
            return "unset";
        }
        if (i == 0) {
            return "SELECTED_APPS_ALLOWED";
        }
        if (i == 1) {
            return "SELECTED_APPS_DISALLOWED";
        }
        return "SELECTED_APPS_UNKNOWN_" + i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ZenPolicy)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ZenPolicy zenPolicy = (ZenPolicy) obj;
        return Objects.equals(zenPolicy.mPriorityCategories, this.mPriorityCategories) && Objects.equals(zenPolicy.mVisualEffects, this.mVisualEffects) && zenPolicy.mPriorityCalls == this.mPriorityCalls && zenPolicy.mPriorityMessages == this.mPriorityMessages && zenPolicy.mConversationSenders == this.mConversationSenders && zenPolicy.mAllowChannels == this.mAllowChannels && zenPolicy.mAppBypassDndFlag == this.mAppBypassDndFlag && zenPolicy.mExceptionContactsFlag == this.mExceptionContactsFlag && Objects.equals(zenPolicy.mAppsToBypassDnd, this.mAppsToBypassDnd) && Objects.equals(zenPolicy.mExceptionContacts, this.mExceptionContacts) && zenPolicy.mIsContactsOverridden == this.mIsContactsOverridden && zenPolicy.mIsAppBypassDndOverridden == this.mIsAppBypassDndOverridden;
    }

    public int hashCode() {
        return Objects.hash(this.mPriorityCategories, this.mVisualEffects, Integer.valueOf(this.mPriorityCalls), Integer.valueOf(this.mPriorityMessages), Integer.valueOf(this.mConversationSenders), Integer.valueOf(this.mAllowChannels));
    }

    private int getZenPolicyPriorityCategoryState(int i) {
        switch (i) {
            case 0:
                return getPriorityCategoryReminders();
            case 1:
                return getPriorityCategoryEvents();
            case 2:
                return getPriorityCategoryMessages();
            case 3:
                return getPriorityCategoryCalls();
            case 4:
                return getPriorityCategoryRepeatCallers();
            case 5:
                return getPriorityCategoryAlarms();
            case 6:
                return getPriorityCategoryMedia();
            case 7:
                return getPriorityCategorySystem();
            case 8:
                return getPriorityCategoryConversations();
            default:
                return -1;
        }
    }

    private int getZenPolicyVisualEffectState(int i) {
        switch (i) {
            case 0:
                return getVisualEffectFullScreenIntent();
            case 1:
                return getVisualEffectLights();
            case 2:
                return getVisualEffectPeek();
            case 3:
                return getVisualEffectStatusBar();
            case 4:
                return getVisualEffectBadge();
            case 5:
                return getVisualEffectAmbient();
            case 6:
                return getVisualEffectNotificationList();
            default:
                return -1;
        }
    }

    public boolean isCategoryAllowed(int i, boolean z) {
        return stateToBoolean(getZenPolicyPriorityCategoryState(i), z);
    }

    public boolean isVisualEffectAllowed(int i, boolean z) {
        return stateToBoolean(getZenPolicyVisualEffectState(i), z);
    }

    public void apply(ZenPolicy zenPolicy) {
        int i;
        if (zenPolicy == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mPriorityCategories.size(); i2++) {
            if (this.mPriorityCategories.get(i2).intValue() != 2) {
                Integer num = zenPolicy.mPriorityCategories.get(i2);
                if (num.intValue() != 0) {
                    this.mPriorityCategories.set(i2, num);
                    if (i2 == 2) {
                        int i3 = this.mPriorityMessages;
                        int i4 = zenPolicy.mPriorityMessages;
                        if (i3 < i4) {
                            this.mPriorityMessages = i4;
                        }
                    }
                    if (i2 == 3) {
                        int i5 = this.mPriorityCalls;
                        int i6 = zenPolicy.mPriorityCalls;
                        if (i5 < i6) {
                            this.mPriorityCalls = i6;
                        }
                    }
                    if (i2 == 8) {
                        int i7 = this.mConversationSenders;
                        int i8 = zenPolicy.mConversationSenders;
                        if (i7 < i8) {
                            this.mConversationSenders = i8;
                        }
                    }
                }
            }
        }
        for (int i9 = 0; i9 < this.mVisualEffects.size(); i9++) {
            if (this.mVisualEffects.get(i9).intValue() != 2 && zenPolicy.mVisualEffects.get(i9).intValue() != 0) {
                this.mVisualEffects.set(i9, zenPolicy.mVisualEffects.get(i9));
            }
        }
        if (this.mAllowChannels != 2 && (i = zenPolicy.mAllowChannels) != 0) {
            this.mAllowChannels = i;
        }
        if (zenPolicy.getAppBypassDndFlag() != -1) {
            this.mAppBypassDndFlag = zenPolicy.getAppBypassDndFlag();
        }
        if (zenPolicy.getExceptionContactsFlag() != -1) {
            this.mExceptionContactsFlag = zenPolicy.getExceptionContactsFlag();
        }
        Iterator<String> it = zenPolicy.getExceptionContacts().iterator();
        while (it.hasNext()) {
            this.mExceptionContacts.add(it.next());
        }
        this.mIsContactsOverridden = zenPolicy.mIsContactsOverridden;
        Iterator<String> it2 = zenPolicy.getAppsToBypassDnd().iterator();
        while (it2.hasNext()) {
            this.mAppsToBypassDnd.add(it2.next());
        }
        this.mIsAppBypassDndOverridden = zenPolicy.mIsAppBypassDndOverridden;
    }

    public ZenPolicy overwrittenWith(ZenPolicy zenPolicy) {
        ZenPolicy copy = copy();
        if (zenPolicy == null) {
            return copy;
        }
        for (int i = 0; i < this.mPriorityCategories.size(); i++) {
            Integer num = zenPolicy.mPriorityCategories.get(i);
            if (num.intValue() != 0) {
                copy.mPriorityCategories.set(i, num);
                if (i == 2) {
                    copy.mPriorityMessages = zenPolicy.mPriorityMessages;
                } else if (i == 3) {
                    copy.mPriorityCalls = zenPolicy.mPriorityCalls;
                } else if (i == 8) {
                    copy.mConversationSenders = zenPolicy.mConversationSenders;
                }
            }
        }
        for (int i2 = 0; i2 < this.mVisualEffects.size(); i2++) {
            if (zenPolicy.mVisualEffects.get(i2).intValue() != 0) {
                copy.mVisualEffects.set(i2, zenPolicy.mVisualEffects.get(i2));
            }
        }
        int i3 = zenPolicy.mAllowChannels;
        if (i3 != 0) {
            copy.mAllowChannels = i3;
        }
        copy.mAppBypassDndFlag = zenPolicy.mAppBypassDndFlag;
        copy.mExceptionContactsFlag = zenPolicy.mExceptionContactsFlag;
        copy.mAppsToBypassDnd = zenPolicy.mAppsToBypassDnd == null ? new ArrayList<>() : new ArrayList<>(zenPolicy.mAppsToBypassDnd);
        copy.mExceptionContacts = zenPolicy.mExceptionContacts == null ? new ArrayList<>() : new ArrayList<>(zenPolicy.mExceptionContacts);
        copy.mIsContactsOverridden = zenPolicy.mIsContactsOverridden;
        copy.mIsAppBypassDndOverridden = zenPolicy.mIsAppBypassDndOverridden;
        return copy;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, getPriorityCategoryReminders());
        protoOutputStream.write(1159641169922L, getPriorityCategoryEvents());
        protoOutputStream.write(1159641169923L, getPriorityCategoryMessages());
        protoOutputStream.write(1159641169924L, getPriorityCategoryCalls());
        protoOutputStream.write(1159641169925L, getPriorityCategoryRepeatCallers());
        protoOutputStream.write(1159641169926L, getPriorityCategoryAlarms());
        protoOutputStream.write(1159641169927L, getPriorityCategoryMedia());
        protoOutputStream.write(1159641169928L, getPriorityCategorySystem());
        protoOutputStream.write(1159641169929L, getVisualEffectFullScreenIntent());
        protoOutputStream.write(1159641169930L, getVisualEffectLights());
        protoOutputStream.write(1159641169931L, getVisualEffectPeek());
        protoOutputStream.write(1159641169932L, getVisualEffectStatusBar());
        protoOutputStream.write(1159641169933L, getVisualEffectBadge());
        protoOutputStream.write(1159641169934L, getVisualEffectAmbient());
        protoOutputStream.write(1159641169935L, getVisualEffectNotificationList());
        protoOutputStream.write(1159641169937L, getPriorityMessageSenders());
        protoOutputStream.write(1159641169936L, getPriorityCallSenders());
        protoOutputStream.end(start);
    }

    public byte[] toProto() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(byteArrayOutputStream);
        protoOutputStream.write(1159641169921L, getPriorityCategoryCalls());
        protoOutputStream.write(1159641169922L, getPriorityCategoryRepeatCallers());
        protoOutputStream.write(1159641169923L, getPriorityCategoryMessages());
        protoOutputStream.write(1159641169924L, getPriorityCategoryConversations());
        protoOutputStream.write(1159641169925L, getPriorityCategoryReminders());
        protoOutputStream.write(1159641169926L, getPriorityCategoryEvents());
        protoOutputStream.write(1159641169927L, getPriorityCategoryAlarms());
        protoOutputStream.write(1159641169928L, getPriorityCategoryMedia());
        protoOutputStream.write(1159641169929L, getPriorityCategorySystem());
        protoOutputStream.write(1159641169930L, getVisualEffectFullScreenIntent());
        protoOutputStream.write(1159641169931L, getVisualEffectLights());
        protoOutputStream.write(1159641169932L, getVisualEffectPeek());
        protoOutputStream.write(1159641169933L, getVisualEffectStatusBar());
        protoOutputStream.write(1159641169934L, getVisualEffectBadge());
        protoOutputStream.write(1159641169935L, getVisualEffectAmbient());
        protoOutputStream.write(1159641169936L, getVisualEffectNotificationList());
        protoOutputStream.write(1159641169937L, getPriorityCallSenders());
        protoOutputStream.write(1159641169938L, getPriorityMessageSenders());
        protoOutputStream.write(1159641169939L, getPriorityConversationSenders());
        protoOutputStream.write(1159641169940L, getPriorityChannelsAllowed());
        protoOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public ZenPolicy copy() {
        Parcel obtain = Parcel.obtain();
        try {
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return CREATOR.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }
}
