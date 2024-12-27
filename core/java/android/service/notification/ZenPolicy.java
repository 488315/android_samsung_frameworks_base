package android.service.notification;

import android.app.Flags;
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

public final class ZenPolicy implements Parcelable {
    public static final int CHANNEL_POLICY_NONE = 2;
    public static final int CHANNEL_POLICY_PRIORITY = 1;
    public static final int CHANNEL_POLICY_UNSET = 0;
    public static final int CONVERSATION_SENDERS_ANYONE = 1;
    public static final int CONVERSATION_SENDERS_IMPORTANT = 2;
    public static final int CONVERSATION_SENDERS_NONE = 3;
    public static final int CONVERSATION_SENDERS_UNSET = 0;
    public static final Parcelable.Creator<ZenPolicy> CREATOR =
            new Parcelable.Creator<
                    ZenPolicy>() { // from class: android.service.notification.ZenPolicy.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ZenPolicy createFromParcel(Parcel source) {
                    ZenPolicy policy;
                    if (Flags.modesApi()) {
                        policy =
                                new ZenPolicy(
                                        ZenPolicy.trimList(
                                                source.readArrayList(
                                                        Integer.class.getClassLoader(),
                                                        Integer.class),
                                                9),
                                        ZenPolicy.trimList(
                                                source.readArrayList(
                                                        Integer.class.getClassLoader(),
                                                        Integer.class),
                                                7),
                                        source.readInt(),
                                        source.readInt(),
                                        source.readInt(),
                                        source.readInt());
                    } else {
                        policy = new ZenPolicy();
                        policy.mPriorityCategories =
                                ZenPolicy.trimList(
                                        source.readArrayList(
                                                Integer.class.getClassLoader(), Integer.class),
                                        9);
                        policy.mVisualEffects =
                                ZenPolicy.trimList(
                                        source.readArrayList(
                                                Integer.class.getClassLoader(), Integer.class),
                                        7);
                        policy.mPriorityMessages = source.readInt();
                        policy.mPriorityCalls = source.readInt();
                        policy.mConversationSenders = source.readInt();
                    }
                    policy.appBypassDndFlag = source.readInt();
                    policy.mAppsToBypassDnd = source.readArrayList(String.class.getClassLoader());
                    policy.exceptionContactsFlag = source.readInt();
                    policy.mExceptionContacts = source.readArrayList(String.class.getClassLoader());
                    policy.mIsContactsOverridden = source.readBoolean();
                    policy.mIsAppBypassDndOverridden = source.readBoolean();
                    return policy;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ZenPolicy[] newArray(int size) {
                    return new ZenPolicy[size];
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
    private int appBypassDndFlag;
    private int exceptionContactsFlag;
    private int mAllowChannels;
    private ArrayList<String> mAppsToBypassDnd;
    private int mConversationSenders;
    private ArrayList<String> mExceptionContacts;
    private boolean mIsAppBypassDndOverridden;
    private boolean mIsContactsOverridden;
    private int mPriorityCalls;
    private List<Integer> mPriorityCategories;
    private int mPriorityMessages;
    private List<Integer> mVisualEffects;

    @Retention(RetentionPolicy.SOURCE)
    private @interface ChannelType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConversationSenders {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModifiableField {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PeopleType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PriorityCategory {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface VisualEffect {}

    public ZenPolicy() {
        this.mPriorityMessages = 0;
        this.mPriorityCalls = 0;
        this.mConversationSenders = 0;
        this.mIsContactsOverridden = false;
        this.mIsAppBypassDndOverridden = false;
        this.exceptionContactsFlag = -1;
        this.appBypassDndFlag = -1;
        this.mAllowChannels = 0;
        this.mPriorityCategories = new ArrayList(Collections.nCopies(9, 0));
        this.mVisualEffects = new ArrayList(Collections.nCopies(7, 0));
        this.mAppsToBypassDnd = new ArrayList<>();
        this.mExceptionContacts = new ArrayList<>();
    }

    public ZenPolicy(
            List<Integer> priorityCategories,
            List<Integer> visualEffects,
            int priorityMessages,
            int priorityCalls,
            int conversationSenders,
            int allowChannels) {
        this.mPriorityMessages = 0;
        this.mPriorityCalls = 0;
        this.mConversationSenders = 0;
        this.mIsContactsOverridden = false;
        this.mIsAppBypassDndOverridden = false;
        this.exceptionContactsFlag = -1;
        this.appBypassDndFlag = -1;
        this.mAllowChannels = 0;
        this.mPriorityCategories = priorityCategories;
        this.mVisualEffects = visualEffects;
        this.mPriorityMessages = priorityMessages;
        this.mPriorityCalls = priorityCalls;
        this.mConversationSenders = conversationSenders;
        this.mAllowChannels = allowChannels;
        this.mAppsToBypassDnd = new ArrayList<>();
        this.mExceptionContacts = new ArrayList<>();
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
        switch (this.mAllowChannels) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
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

    private String appsToBypassDndToString(ArrayList<String> appsToBypassDnd) {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        for (int i = 0; i < appsToBypassDnd.size(); i++) {
            if (i != 0) {
                builder.append(", =");
            }
            builder.append(appsToBypassDnd.get(i));
        }
        builder.append('}');
        return builder.toString();
    }

    public int getExceptionContactsFlag() {
        return this.exceptionContactsFlag;
    }

    public int getAppBypassDndFlag() {
        return this.appBypassDndFlag;
    }

    public static final class Builder {
        private ZenPolicy mZenPolicy;

        public Builder() {
            this.mZenPolicy = new ZenPolicy();
        }

        public Builder(ZenPolicy policy) {
            if (policy != null) {
                this.mZenPolicy = policy.copy();
            } else {
                this.mZenPolicy = new ZenPolicy();
            }
        }

        public ZenPolicy build() {
            if (Flags.modesApi()) {
                return new ZenPolicy(
                        new ArrayList(this.mZenPolicy.mPriorityCategories),
                        new ArrayList(this.mZenPolicy.mVisualEffects),
                        this.mZenPolicy.mPriorityMessages,
                        this.mZenPolicy.mPriorityCalls,
                        this.mZenPolicy.mConversationSenders,
                        this.mZenPolicy.mAllowChannels);
            }
            return this.mZenPolicy.copy();
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

        public Builder unsetPriorityCategory(int category) {
            this.mZenPolicy.mPriorityCategories.set(category, 0);
            if (category == 2) {
                this.mZenPolicy.mPriorityMessages = 0;
            } else if (category == 3) {
                this.mZenPolicy.mPriorityCalls = 0;
            } else if (category == 8) {
                this.mZenPolicy.mConversationSenders = 0;
            }
            return this;
        }

        public Builder unsetVisualEffect(int effect) {
            this.mZenPolicy.mVisualEffects.set(effect, 0);
            return this;
        }

        public Builder allowReminders(boolean allow) {
            this.mZenPolicy.mPriorityCategories.set(0, Integer.valueOf(allow ? 1 : 2));
            return this;
        }

        public Builder allowEvents(boolean allow) {
            this.mZenPolicy.mPriorityCategories.set(1, Integer.valueOf(allow ? 1 : 2));
            return this;
        }

        public Builder allowConversations(int audienceType) {
            if (audienceType == 0) {
                return unsetPriorityCategory(8);
            }
            if (audienceType == 3) {
                this.mZenPolicy.mPriorityCategories.set(8, 2);
            } else if (audienceType == 1 || audienceType == 2) {
                this.mZenPolicy.mPriorityCategories.set(8, 1);
            } else {
                return this;
            }
            this.mZenPolicy.mConversationSenders = audienceType;
            return this;
        }

        public Builder allowMessages(int audienceType) {
            if (audienceType == 0) {
                return unsetPriorityCategory(2);
            }
            if (audienceType == 4) {
                this.mZenPolicy.mPriorityCategories.set(2, 2);
            } else if (audienceType == 1 || audienceType == 2 || audienceType == 3) {
                this.mZenPolicy.mPriorityCategories.set(2, 1);
            } else {
                return this;
            }
            this.mZenPolicy.mPriorityMessages = audienceType;
            return this;
        }

        public Builder allowCalls(int audienceType) {
            if (audienceType == 0) {
                return unsetPriorityCategory(3);
            }
            if (audienceType == 4) {
                this.mZenPolicy.mPriorityCategories.set(3, 2);
            } else if (audienceType == 1 || audienceType == 2 || audienceType == 3) {
                this.mZenPolicy.mPriorityCategories.set(3, 1);
            } else {
                return this;
            }
            this.mZenPolicy.mPriorityCalls = audienceType;
            return this;
        }

        public Builder allowRepeatCallers(boolean allow) {
            this.mZenPolicy.mPriorityCategories.set(4, Integer.valueOf(allow ? 1 : 2));
            return this;
        }

        public Builder allowAlarms(boolean allow) {
            this.mZenPolicy.mPriorityCategories.set(5, Integer.valueOf(allow ? 1 : 2));
            return this;
        }

        public Builder allowMedia(boolean allow) {
            this.mZenPolicy.mPriorityCategories.set(6, Integer.valueOf(allow ? 1 : 2));
            return this;
        }

        public Builder allowSystem(boolean allow) {
            this.mZenPolicy.mPriorityCategories.set(7, Integer.valueOf(allow ? 1 : 2));
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x001c, code lost:

           return r0;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.service.notification.ZenPolicy.Builder allowCategory(int r1, boolean r2) {
            /*
                r0 = this;
                switch(r1) {
                    case 0: goto L18;
                    case 1: goto L14;
                    case 2: goto L3;
                    case 3: goto L3;
                    case 4: goto L10;
                    case 5: goto Lc;
                    case 6: goto L8;
                    case 7: goto L4;
                    default: goto L3;
                }
            L3:
                goto L1c
            L4:
                r0.allowSystem(r2)
                goto L1c
            L8:
                r0.allowMedia(r2)
                goto L1c
            Lc:
                r0.allowAlarms(r2)
                goto L1c
            L10:
                r0.allowRepeatCallers(r2)
                goto L1c
            L14:
                r0.allowEvents(r2)
                goto L1c
            L18:
                r0.allowReminders(r2)
            L1c:
                return r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.service.notification.ZenPolicy.Builder.allowCategory(int,"
                        + " boolean):android.service.notification.ZenPolicy$Builder");
        }

        public Builder allowAppsToBypassDnd(String appList) {
            if (appList.length() < 1) {
                return this;
            }
            String[] apps = appList.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            for (String str : apps) {
                this.mZenPolicy.mAppsToBypassDnd.add(str);
            }
            this.mZenPolicy.mIsAppBypassDndOverridden = true;
            return this;
        }

        public Builder allowExceptionContacts(String appList) {
            if (appList.length() < 1) {
                return this;
            }
            String[] apps = appList.split(",");
            for (String str : apps) {
                this.mZenPolicy.mExceptionContacts.add(str);
            }
            this.mZenPolicy.mIsContactsOverridden = true;
            return this;
        }

        public Builder setExceptionContactsFlag(int exceptionContactsFlag) {
            this.mZenPolicy.exceptionContactsFlag = exceptionContactsFlag;
            return this;
        }

        public Builder setAppBypassDndFlag(int appBypassDndFlag) {
            this.mZenPolicy.appBypassDndFlag = appBypassDndFlag;
            return this;
        }

        public Builder showFullScreenIntent(boolean show) {
            this.mZenPolicy.mVisualEffects.set(0, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        public Builder showLights(boolean show) {
            this.mZenPolicy.mVisualEffects.set(1, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        public Builder showPeeking(boolean show) {
            this.mZenPolicy.mVisualEffects.set(2, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        public Builder showStatusBarIcons(boolean show) {
            this.mZenPolicy.mVisualEffects.set(3, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        public Builder showBadges(boolean show) {
            this.mZenPolicy.mVisualEffects.set(4, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        public Builder showInAmbientDisplay(boolean show) {
            this.mZenPolicy.mVisualEffects.set(5, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        public Builder showInNotificationList(boolean show) {
            this.mZenPolicy.mVisualEffects.set(6, Integer.valueOf(show ? 1 : 2));
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0020, code lost:

           return r0;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.service.notification.ZenPolicy.Builder showVisualEffect(int r1, boolean r2) {
            /*
                r0 = this;
                switch(r1) {
                    case 0: goto L1c;
                    case 1: goto L18;
                    case 2: goto L14;
                    case 3: goto L10;
                    case 4: goto Lc;
                    case 5: goto L8;
                    case 6: goto L4;
                    default: goto L3;
                }
            L3:
                goto L20
            L4:
                r0.showInNotificationList(r2)
                goto L20
            L8:
                r0.showInAmbientDisplay(r2)
                goto L20
            Lc:
                r0.showBadges(r2)
                goto L20
            L10:
                r0.showStatusBarIcons(r2)
                goto L20
            L14:
                r0.showPeeking(r2)
                goto L20
            L18:
                r0.showLights(r2)
                goto L20
            L1c:
                r0.showFullScreenIntent(r2)
            L20:
                return r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.service.notification.ZenPolicy.Builder.showVisualEffect(int,"
                        + " boolean):android.service.notification.ZenPolicy$Builder");
        }

        public Builder allowPriorityChannels(boolean allow) {
            this.mZenPolicy.mAllowChannels = allow ? 1 : 2;
            return this;
        }

        public Builder allowChannels(int channelType) {
            this.mZenPolicy.mAllowChannels = channelType;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mPriorityCategories);
        dest.writeList(this.mVisualEffects);
        dest.writeInt(this.mPriorityMessages);
        dest.writeInt(this.mPriorityCalls);
        dest.writeInt(this.mConversationSenders);
        if (Flags.modesApi()) {
            dest.writeInt(this.mAllowChannels);
        }
        dest.writeInt(this.appBypassDndFlag);
        dest.writeList(this.mAppsToBypassDnd);
        dest.writeInt(this.exceptionContactsFlag);
        dest.writeList(this.mExceptionContacts);
        dest.writeBoolean(this.mIsContactsOverridden);
        dest.writeBoolean(this.mIsAppBypassDndOverridden);
    }

    public String toString() {
        StringBuilder sb =
                new StringBuilder(ZenPolicy.class.getSimpleName())
                        .append('{')
                        .append("priorityCategories=[")
                        .append(priorityCategoriesToString())
                        .append("], visualEffects=[")
                        .append(visualEffectsToString())
                        .append("], priorityCallsSenders=")
                        .append(peopleTypeToString(this.mPriorityCalls))
                        .append(", priorityMessagesSenders=")
                        .append(peopleTypeToString(this.mPriorityMessages))
                        .append(", priorityConversationSenders=")
                        .append(conversationTypeToString(this.mConversationSenders))
                        .append(", appBypassDndFlag=")
                        .append(appBypassDndFlagToString(this.appBypassDndFlag))
                        .append(", appsToBypassDnd=")
                        .append(appsToBypassDndToString(this.mAppsToBypassDnd))
                        .append(", exceptionContactsFlag=")
                        .append(exceptionContactsFlagToString(this.exceptionContactsFlag))
                        .append(", exceptionContacts=")
                        .append(appsToBypassDndToString(this.mExceptionContacts))
                        .append(", isContactsOverridden=")
                        .append(Boolean.toString(this.mIsContactsOverridden))
                        .append(", isAppBypassDndOverridden=")
                        .append(Boolean.toString(this.mIsAppBypassDndOverridden));
        if (Flags.modesApi()) {
            sb.append(", allowChannels=").append(channelTypeToString(this.mAllowChannels));
        }
        return sb.append('}').toString();
    }

    public static String fieldsToString(int bitmask) {
        ArrayList<String> modified = new ArrayList<>();
        if ((bitmask & 1) != 0) {
            modified.add("FIELD_MESSAGES");
        }
        if ((bitmask & 2) != 0) {
            modified.add("FIELD_CALLS");
        }
        if ((bitmask & 4) != 0) {
            modified.add("FIELD_CONVERSATIONS");
        }
        if ((bitmask & 8) != 0) {
            modified.add("FIELD_ALLOW_CHANNELS");
        }
        if ((bitmask & 16) != 0) {
            modified.add("FIELD_PRIORITY_CATEGORY_REMINDERS");
        }
        if ((bitmask & 32) != 0) {
            modified.add("FIELD_PRIORITY_CATEGORY_EVENTS");
        }
        if ((bitmask & 64) != 0) {
            modified.add("FIELD_PRIORITY_CATEGORY_REPEAT_CALLERS");
        }
        if ((bitmask & 128) != 0) {
            modified.add("FIELD_PRIORITY_CATEGORY_ALARMS");
        }
        if ((bitmask & 256) != 0) {
            modified.add("FIELD_PRIORITY_CATEGORY_MEDIA");
        }
        if ((bitmask & 512) != 0) {
            modified.add("FIELD_PRIORITY_CATEGORY_SYSTEM");
        }
        if ((bitmask & 1024) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_FULL_SCREEN_INTENT");
        }
        if ((bitmask & 2048) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_LIGHTS");
        }
        if ((bitmask & 4096) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_PEEK");
        }
        if ((bitmask & 8192) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_STATUS_BAR");
        }
        if ((bitmask & 16384) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_BADGE");
        }
        if ((32768 & bitmask) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_AMBIENT");
        }
        if ((65536 & bitmask) != 0) {
            modified.add("FIELD_VISUAL_EFFECT_NOTIFICATION_LIST");
        }
        return "{" + String.join(",", modified) + "}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList<Integer> trimList(ArrayList<Integer> list, int maxLength) {
        if (list == null || list.size() <= maxLength) {
            return list;
        }
        return new ArrayList<>(list.subList(0, maxLength));
    }

    private String priorityCategoriesToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.mPriorityCategories.size(); i++) {
            if (this.mPriorityCategories.get(i).intValue() != 0) {
                builder.append(indexToCategory(i))
                        .append("=")
                        .append(stateToString(this.mPriorityCategories.get(i).intValue()))
                        .append(" ");
            }
        }
        return builder.toString();
    }

    private String visualEffectsToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.mVisualEffects.size(); i++) {
            if (this.mVisualEffects.get(i).intValue() != 0) {
                builder.append(indexToVisualEffect(i))
                        .append("=")
                        .append(stateToString(this.mVisualEffects.get(i).intValue()))
                        .append(" ");
            }
        }
        return builder.toString();
    }

    private String indexToVisualEffect(int visualEffectIndex) {
        switch (visualEffectIndex) {
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

    private String indexToCategory(int categoryIndex) {
        switch (categoryIndex) {
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
                return "media";
            case 7:
                return "system";
            case 8:
                return "convs";
            default:
                return null;
        }
    }

    private String stateToString(int state) {
        switch (state) {
            case 0:
                return "unset";
            case 1:
                return "allow";
            case 2:
                return "disallow";
            default:
                return "invalidState{" + state + "}";
        }
    }

    private String peopleTypeToString(int peopleType) {
        switch (peopleType) {
            case 0:
                return "unset";
            case 1:
                return "anyone";
            case 2:
                return Contacts.AUTHORITY;
            case 3:
                return "starred_contacts";
            case 4:
                return "none";
            default:
                return "invalidPeopleType{" + peopleType + "}";
        }
    }

    public static String conversationTypeToString(int conversationType) {
        switch (conversationType) {
            case 0:
                return "unset";
            case 1:
                return "anyone";
            case 2:
                return "important";
            case 3:
                return "none";
            default:
                return "invalidConversationType{" + conversationType + "}";
        }
    }

    public static String channelTypeToString(int channelType) {
        switch (channelType) {
            case 0:
                return "unset";
            case 1:
                return "priority";
            case 2:
                return "none";
            default:
                return "invalidChannelType{" + channelType + "}";
        }
    }

    public static String exceptionContactsFlagToString(int exceptionContactsFlag) {
        switch (exceptionContactsFlag) {
            case -1:
                return "unset";
            case 0:
                return "SELECTED_CONTACTS_ALLOWED";
            case 1:
                return "SELECTED_CONTACTS_DISALLOWED";
            default:
                return "SELECTED_CONTACTS_UNKNOWN_" + exceptionContactsFlag;
        }
    }

    public static String appBypassDndFlagToString(int appBypassDndFlag) {
        switch (appBypassDndFlag) {
            case -1:
                return "unset";
            case 0:
                return "SELECTED_APPS_ALLOWED";
            case 1:
                return "SELECTED_APPS_DISALLOWED";
            default:
                return "SELECTED_APPS_UNKNOWN_" + appBypassDndFlag;
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof ZenPolicy)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        ZenPolicy other = (ZenPolicy) o;
        boolean eq =
                Objects.equals(other.mPriorityCategories, this.mPriorityCategories)
                        && Objects.equals(other.mVisualEffects, this.mVisualEffects)
                        && other.mPriorityCalls == this.mPriorityCalls
                        && other.mPriorityMessages == this.mPriorityMessages
                        && other.mConversationSenders == this.mConversationSenders
                        && other.appBypassDndFlag == this.appBypassDndFlag
                        && other.exceptionContactsFlag == this.exceptionContactsFlag
                        && Objects.equals(other.mAppsToBypassDnd, this.mAppsToBypassDnd)
                        && Objects.equals(other.mExceptionContacts, this.mExceptionContacts)
                        && other.mIsContactsOverridden == this.mIsContactsOverridden
                        && other.mIsAppBypassDndOverridden == this.mIsAppBypassDndOverridden;
        if (Flags.modesApi()) {
            return eq && other.mAllowChannels == this.mAllowChannels;
        }
        return eq;
    }

    public int hashCode() {
        if (Flags.modesApi()) {
            return Objects.hash(
                    this.mPriorityCategories,
                    this.mVisualEffects,
                    Integer.valueOf(this.mPriorityCalls),
                    Integer.valueOf(this.mPriorityMessages),
                    Integer.valueOf(this.mConversationSenders),
                    Integer.valueOf(this.mAllowChannels));
        }
        return Objects.hash(
                this.mPriorityCategories,
                this.mVisualEffects,
                Integer.valueOf(this.mPriorityCalls),
                Integer.valueOf(this.mPriorityMessages),
                Integer.valueOf(this.mConversationSenders));
    }

    private int getZenPolicyPriorityCategoryState(int category) {
        switch (category) {
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

    private int getZenPolicyVisualEffectState(int effect) {
        switch (effect) {
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

    public static boolean stateToBoolean(int state, boolean defaultVal) {
        switch (state) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                return defaultVal;
        }
    }

    public boolean isCategoryAllowed(int category, boolean defaultVal) {
        return stateToBoolean(getZenPolicyPriorityCategoryState(category), defaultVal);
    }

    public boolean isVisualEffectAllowed(int effect, boolean defaultVal) {
        return stateToBoolean(getZenPolicyVisualEffectState(effect), defaultVal);
    }

    public void apply(ZenPolicy policyToApply) {
        int newState;
        if (policyToApply == null) {
            return;
        }
        for (int category = 0; category < this.mPriorityCategories.size(); category++) {
            if (this.mPriorityCategories.get(category).intValue() != 2
                    && (newState = policyToApply.mPriorityCategories.get(category).intValue())
                            != 0) {
                this.mPriorityCategories.set(category, Integer.valueOf(newState));
                if (category == 2 && this.mPriorityMessages < policyToApply.mPriorityMessages) {
                    this.mPriorityMessages = policyToApply.mPriorityMessages;
                } else if (category == 3 && this.mPriorityCalls < policyToApply.mPriorityCalls) {
                    this.mPriorityCalls = policyToApply.mPriorityCalls;
                } else if (category == 8
                        && this.mConversationSenders < policyToApply.mConversationSenders) {
                    this.mConversationSenders = policyToApply.mConversationSenders;
                }
            }
        }
        for (int visualEffect = 0; visualEffect < this.mVisualEffects.size(); visualEffect++) {
            if (this.mVisualEffects.get(visualEffect).intValue() != 2
                    && policyToApply.mVisualEffects.get(visualEffect).intValue() != 0) {
                this.mVisualEffects.set(
                        visualEffect, policyToApply.mVisualEffects.get(visualEffect));
            }
        }
        int visualEffect2 = policyToApply.getAppBypassDndFlag();
        if (visualEffect2 != -1) {
            this.appBypassDndFlag = policyToApply.getAppBypassDndFlag();
        }
        if (policyToApply.getExceptionContactsFlag() != -1) {
            this.exceptionContactsFlag = policyToApply.getExceptionContactsFlag();
        }
        Iterator<String> it = policyToApply.getExceptionContacts().iterator();
        while (it.hasNext()) {
            String contactInfo = it.next();
            this.mExceptionContacts.add(contactInfo);
        }
        this.mIsContactsOverridden = policyToApply.mIsContactsOverridden;
        Iterator<String> it2 = policyToApply.getAppsToBypassDnd().iterator();
        while (it2.hasNext()) {
            String appName = it2.next();
            this.mAppsToBypassDnd.add(appName);
        }
        this.mIsAppBypassDndOverridden = policyToApply.mIsAppBypassDndOverridden;
        if (Flags.modesApi() && this.mAllowChannels != 2 && policyToApply.mAllowChannels != 0) {
            this.mAllowChannels = policyToApply.mAllowChannels;
        }
    }

    public ZenPolicy overwrittenWith(ZenPolicy newPolicy) {
        ZenPolicy result = copy();
        if (newPolicy == null) {
            return result;
        }
        for (int category = 0; category < this.mPriorityCategories.size(); category++) {
            int newState = newPolicy.mPriorityCategories.get(category).intValue();
            if (newState != 0) {
                result.mPriorityCategories.set(category, Integer.valueOf(newState));
                if (category == 2) {
                    result.mPriorityMessages = newPolicy.mPriorityMessages;
                } else if (category == 3) {
                    result.mPriorityCalls = newPolicy.mPriorityCalls;
                } else if (category == 8) {
                    result.mConversationSenders = newPolicy.mConversationSenders;
                }
            }
        }
        for (int visualEffect = 0; visualEffect < this.mVisualEffects.size(); visualEffect++) {
            if (newPolicy.mVisualEffects.get(visualEffect).intValue() != 0) {
                result.mVisualEffects.set(visualEffect, newPolicy.mVisualEffects.get(visualEffect));
            }
        }
        int visualEffect2 = newPolicy.mAllowChannels;
        if (visualEffect2 != 0) {
            result.mAllowChannels = newPolicy.mAllowChannels;
        }
        return result;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1159641169921L, getPriorityCategoryReminders());
        proto.write(1159641169922L, getPriorityCategoryEvents());
        proto.write(1159641169923L, getPriorityCategoryMessages());
        proto.write(1159641169924L, getPriorityCategoryCalls());
        proto.write(1159641169925L, getPriorityCategoryRepeatCallers());
        proto.write(1159641169926L, getPriorityCategoryAlarms());
        proto.write(1159641169927L, getPriorityCategoryMedia());
        proto.write(1159641169928L, getPriorityCategorySystem());
        proto.write(1159641169929L, getVisualEffectFullScreenIntent());
        proto.write(1159641169930L, getVisualEffectLights());
        proto.write(1159641169931L, getVisualEffectPeek());
        proto.write(1159641169932L, getVisualEffectStatusBar());
        proto.write(1159641169933L, getVisualEffectBadge());
        proto.write(1159641169934L, getVisualEffectAmbient());
        proto.write(1159641169935L, getVisualEffectNotificationList());
        proto.write(1159641169937L, getPriorityMessageSenders());
        proto.write(1159641169936L, getPriorityCallSenders());
        proto.end(token);
    }

    public byte[] toProto() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ProtoOutputStream proto = new ProtoOutputStream(bytes);
        proto.write(1159641169921L, getPriorityCategoryCalls());
        proto.write(1159641169922L, getPriorityCategoryRepeatCallers());
        proto.write(1159641169923L, getPriorityCategoryMessages());
        proto.write(1159641169924L, getPriorityCategoryConversations());
        proto.write(1159641169925L, getPriorityCategoryReminders());
        proto.write(1159641169926L, getPriorityCategoryEvents());
        proto.write(1159641169927L, getPriorityCategoryAlarms());
        proto.write(1159641169928L, getPriorityCategoryMedia());
        proto.write(1159641169929L, getPriorityCategorySystem());
        proto.write(1159641169930L, getVisualEffectFullScreenIntent());
        proto.write(1159641169931L, getVisualEffectLights());
        proto.write(1159641169932L, getVisualEffectPeek());
        proto.write(1159641169933L, getVisualEffectStatusBar());
        proto.write(1159641169934L, getVisualEffectBadge());
        proto.write(1159641169935L, getVisualEffectAmbient());
        proto.write(1159641169936L, getVisualEffectNotificationList());
        proto.write(1159641169937L, getPriorityCallSenders());
        proto.write(1159641169938L, getPriorityMessageSenders());
        proto.write(1159641169939L, getPriorityConversationSenders());
        if (Flags.modesApi()) {
            proto.write(1159641169940L, getPriorityChannelsAllowed());
        }
        proto.flush();
        return bytes.toByteArray();
    }

    public ZenPolicy copy() {
        Parcel parcel = Parcel.obtain();
        try {
            writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            return CREATOR.createFromParcel(parcel);
        } finally {
            parcel.recycle();
        }
    }
}
