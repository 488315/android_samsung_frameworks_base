package androidx.core.app;

import android.app.Notification;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationCompat$MessagingStyle extends NotificationCompat$Style {
    public CharSequence mConversationTitle;
    public Boolean mIsGroupConversation;
    public Person mUser;
    public final List mMessages = new ArrayList();
    public final List mHistoricMessages = new ArrayList();

    public class Api24Impl {
        private Api24Impl() {
        }

        public static Notification.MessagingStyle addMessage(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
            return messagingStyle.addMessage(message);
        }

        public static Notification.MessagingStyle setConversationTitle(Notification.MessagingStyle messagingStyle, CharSequence charSequence) {
            return messagingStyle.setConversationTitle(charSequence);
        }
    }

    public class Api26Impl {
        private Api26Impl() {
        }

        public static Notification.MessagingStyle addHistoricMessage(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
            return messagingStyle.addHistoricMessage(message);
        }
    }

    public class Api28Impl {
        private Api28Impl() {
        }

        public static Notification.MessagingStyle createMessagingStyle(android.app.Person person) {
            return new Notification.MessagingStyle(person);
        }

        public static Notification.MessagingStyle setGroupConversation(Notification.MessagingStyle messagingStyle, boolean z) {
            return messagingStyle.setGroupConversation(z);
        }
    }

    public NotificationCompat$MessagingStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void addCompatExtras(Bundle bundle) {
        Bundle bundle2;
        super.addCompatExtras(bundle);
        bundle.putCharSequence("android.selfDisplayName", this.mUser.mName);
        Person person = this.mUser;
        person.getClass();
        Bundle bundle3 = new Bundle();
        bundle3.putCharSequence("name", person.mName);
        IconCompat iconCompat = person.mIcon;
        if (iconCompat != null) {
            iconCompat.getClass();
            bundle2 = new Bundle();
            switch (iconCompat.mType) {
                case -1:
                    bundle2.putParcelable("obj", (Parcelable) iconCompat.mObj1);
                    break;
                case 0:
                default:
                    throw new IllegalArgumentException("Invalid icon");
                case 1:
                case 5:
                    bundle2.putParcelable("obj", (Bitmap) iconCompat.mObj1);
                    break;
                case 2:
                case 4:
                case 6:
                    bundle2.putString("obj", (String) iconCompat.mObj1);
                    break;
                case 3:
                    bundle2.putByteArray("obj", (byte[]) iconCompat.mObj1);
                    break;
            }
            bundle2.putInt("type", iconCompat.mType);
            bundle2.putInt("int1", iconCompat.mInt1);
            bundle2.putInt("int2", iconCompat.mInt2);
            bundle2.putString("string1", iconCompat.mString1);
            ColorStateList colorStateList = iconCompat.mTintList;
            if (colorStateList != null) {
                bundle2.putParcelable("tint_list", colorStateList);
            }
            PorterDuff.Mode mode = iconCompat.mTintMode;
            if (mode != IconCompat.DEFAULT_TINT_MODE) {
                bundle2.putString("tint_mode", mode.name());
            }
        } else {
            bundle2 = null;
        }
        bundle3.putBundle("icon", bundle2);
        bundle3.putString("uri", person.mUri);
        bundle3.putString("key", person.mKey);
        bundle3.putBoolean("isBot", person.mIsBot);
        bundle3.putBoolean("isImportant", person.mIsImportant);
        bundle.putBundle("android.messagingStyleUser", bundle3);
        bundle.putCharSequence("android.hiddenConversationTitle", this.mConversationTitle);
        if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
            bundle.putCharSequence("android.conversationTitle", this.mConversationTitle);
        }
        if (!((ArrayList) this.mMessages).isEmpty()) {
            bundle.putParcelableArray("android.messages", Message.getBundleArrayForMessages(this.mMessages));
        }
        if (!((ArrayList) this.mHistoricMessages).isEmpty()) {
            bundle.putParcelableArray("android.messages.historic", Message.getBundleArrayForMessages(this.mHistoricMessages));
        }
        Boolean bool = this.mIsGroupConversation;
        if (bool != null) {
            bundle.putBoolean("android.isGroupConversation", bool.booleanValue());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
    @Override // androidx.core.app.NotificationCompat$Style
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        boolean zBooleanValue;
        NotificationCompat$Builder notificationCompat$Builder = this.mBuilder;
        int i = 0;
        if (notificationCompat$Builder == null || notificationCompat$Builder.mContext.getApplicationInfo().targetSdkVersion >= 28 || this.mIsGroupConversation != null) {
            Boolean bool = this.mIsGroupConversation;
            zBooleanValue = bool != null ? bool.booleanValue() : false;
        } else if (this.mConversationTitle != null) {
            zBooleanValue = true;
        }
        this.mIsGroupConversation = Boolean.valueOf(zBooleanValue);
        Notification.MessagingStyle messagingStyleCreateMessagingStyle = Api28Impl.createMessagingStyle(this.mUser.toAndroidPerson());
        ArrayList arrayList = (ArrayList) this.mMessages;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            Object obj = arrayList.get(i2);
            i2++;
            Message message = (Message) obj;
            CharSequence charSequence = message.mText;
            Person person = message.mPerson;
            Notification.MessagingStyle.Message messageCreateMessage = Message.Api28Impl.createMessage(charSequence, message.mTimestamp, person != null ? person.toAndroidPerson() : null);
            String str = message.mDataMimeType;
            if (str != null) {
                Message.Api24Impl.setData(messageCreateMessage, str, message.mDataUri);
            }
            Api24Impl.addMessage(messagingStyleCreateMessagingStyle, messageCreateMessage);
        }
        ArrayList arrayList2 = (ArrayList) this.mHistoricMessages;
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            Message message2 = (Message) obj2;
            CharSequence charSequence2 = message2.mText;
            Person person2 = message2.mPerson;
            Notification.MessagingStyle.Message messageCreateMessage2 = Message.Api28Impl.createMessage(charSequence2, message2.mTimestamp, person2 == null ? null : person2.toAndroidPerson());
            String str2 = message2.mDataMimeType;
            if (str2 != null) {
                Message.Api24Impl.setData(messageCreateMessage2, str2, message2.mDataUri);
            }
            Api26Impl.addHistoricMessage(messagingStyleCreateMessagingStyle, messageCreateMessage2);
        }
        this.mIsGroupConversation.getClass();
        Api24Impl.setConversationTitle(messagingStyleCreateMessagingStyle, this.mConversationTitle);
        Api28Impl.setGroupConversation(messagingStyleCreateMessagingStyle, this.mIsGroupConversation.booleanValue());
        messagingStyleCreateMessagingStyle.setBuilder(notificationCompatBuilder.mBuilder);
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void clearCompatExtraKeys(Bundle bundle) {
        super.clearCompatExtraKeys(bundle);
        bundle.remove("android.messagingStyleUser");
        bundle.remove("android.selfDisplayName");
        bundle.remove("android.conversationTitle");
        bundle.remove("android.hiddenConversationTitle");
        bundle.remove("android.messages");
        bundle.remove("android.messages.historic");
        bundle.remove("android.isGroupConversation");
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final String getClassName() {
        return "androidx.core.app.NotificationCompat$MessagingStyle";
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void restoreFromCompatExtras(Bundle bundle) {
        super.restoreFromCompatExtras(bundle);
        ((ArrayList) this.mMessages).clear();
        if (bundle.containsKey("android.messagingStyleUser")) {
            this.mUser = Person.fromBundle(bundle.getBundle("android.messagingStyleUser"));
        } else {
            Person.Builder builder = new Person.Builder();
            builder.mName = bundle.getString("android.selfDisplayName");
            this.mUser = new Person(builder);
        }
        CharSequence charSequence = bundle.getCharSequence("android.conversationTitle");
        this.mConversationTitle = charSequence;
        if (charSequence == null) {
            this.mConversationTitle = bundle.getCharSequence("android.hiddenConversationTitle");
        }
        Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
        if (parcelableArray != null) {
            ((ArrayList) this.mMessages).addAll(Message.getMessagesFromBundleArray(parcelableArray));
        }
        Parcelable[] parcelableArray2 = bundle.getParcelableArray("android.messages.historic");
        if (parcelableArray2 != null) {
            ((ArrayList) this.mHistoricMessages).addAll(Message.getMessagesFromBundleArray(parcelableArray2));
        }
        if (bundle.containsKey("android.isGroupConversation")) {
            this.mIsGroupConversation = Boolean.valueOf(bundle.getBoolean("android.isGroupConversation"));
        }
    }

    @Deprecated
    public NotificationCompat$MessagingStyle(CharSequence charSequence) {
        Person.Builder builder = new Person.Builder();
        builder.mName = charSequence;
        this.mUser = new Person(builder);
    }

    public final class Message {
        public String mDataMimeType;
        public Uri mDataUri;
        public final Bundle mExtras;
        public final Person mPerson;
        public final CharSequence mText;
        public final long mTimestamp;

        public class Api24Impl {
            private Api24Impl() {
            }

            public static Notification.MessagingStyle.Message setData(Notification.MessagingStyle.Message message, String str, Uri uri) {
                return message.setData(str, uri);
            }
        }

        public Message(CharSequence charSequence, long j, Person person) {
            this.mExtras = new Bundle();
            this.mText = charSequence;
            this.mTimestamp = j;
            this.mPerson = person;
        }

        public static Bundle[] getBundleArrayForMessages(List list) {
            Bundle[] bundleArr = new Bundle[list.size()];
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Message message = (Message) list.get(i);
                message.getClass();
                Bundle bundle = new Bundle();
                CharSequence charSequence = message.mText;
                if (charSequence != null) {
                    bundle.putCharSequence("text", charSequence);
                }
                bundle.putLong("time", message.mTimestamp);
                Person person = message.mPerson;
                if (person != null) {
                    bundle.putCharSequence("sender", person.mName);
                    bundle.putParcelable("sender_person", Api28Impl.castToParcelable(person.toAndroidPerson()));
                }
                String str = message.mDataMimeType;
                if (str != null) {
                    bundle.putString("type", str);
                }
                Uri uri = message.mDataUri;
                if (uri != null) {
                    bundle.putParcelable("uri", uri);
                }
                Bundle bundle2 = message.mExtras;
                if (bundle2 != null) {
                    bundle.putBundle("extras", bundle2);
                }
                bundleArr[i] = bundle;
            }
            return bundleArr;
        }

        public static List getMessagesFromBundleArray(Parcelable[] parcelableArr) {
            Person person;
            ArrayList arrayList = new ArrayList(parcelableArr.length);
            for (Parcelable parcelable : parcelableArr) {
                if (parcelable instanceof Bundle) {
                    Bundle bundle = (Bundle) parcelable;
                    Message message = null;
                    try {
                        if (bundle.containsKey("text") && bundle.containsKey("time")) {
                            if (bundle.containsKey("person")) {
                                person = Person.fromBundle(bundle.getBundle("person"));
                            } else if (bundle.containsKey("sender_person")) {
                                person = Person.fromAndroidPerson((android.app.Person) bundle.getParcelable("sender_person"));
                            } else if (bundle.containsKey("sender")) {
                                Person.Builder builder = new Person.Builder();
                                builder.mName = bundle.getCharSequence("sender");
                                person = new Person(builder);
                            } else {
                                person = null;
                            }
                            Message message2 = new Message(bundle.getCharSequence("text"), bundle.getLong("time"), person);
                            if (bundle.containsKey("type") && bundle.containsKey("uri")) {
                                String string = bundle.getString("type");
                                Uri uri = (Uri) bundle.getParcelable("uri");
                                message2.mDataMimeType = string;
                                message2.mDataUri = uri;
                            }
                            if (bundle.containsKey("extras")) {
                                message2.mExtras.putAll(bundle.getBundle("extras"));
                            }
                            message = message2;
                        }
                    } catch (ClassCastException unused) {
                    }
                    if (message != null) {
                        arrayList.add(message);
                    }
                }
            }
            return arrayList;
        }

        @Deprecated
        public Message(CharSequence charSequence, long j, CharSequence charSequence2) {
            Person.Builder builder = new Person.Builder();
            builder.mName = charSequence2;
            this(charSequence, j, new Person(builder));
        }

        public class Api28Impl {
            private Api28Impl() {
            }

            public static Notification.MessagingStyle.Message createMessage(CharSequence charSequence, long j, android.app.Person person) {
                return new Notification.MessagingStyle.Message(charSequence, j, person);
            }

            public static Parcelable castToParcelable(android.app.Person person) {
                return person;
            }
        }
    }

    public NotificationCompat$MessagingStyle(Person person) {
        if (!TextUtils.isEmpty(person.mName)) {
            this.mUser = person;
            return;
        }
        throw new IllegalArgumentException("User's name must not be empty.");
    }
}
