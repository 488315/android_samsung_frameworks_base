package androidx.core.app;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationCompat$MessagingStyle extends NotificationCompat$Style {
    public CharSequence mConversationTitle;
    public Boolean mIsGroupConversation;
    public Person mUser;
    public final List mMessages = new ArrayList();
    public final List mHistoricMessages = new ArrayList();

    public NotificationCompat$MessagingStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void addCompatExtras(Bundle bundle) {
        super.addCompatExtras(bundle);
        bundle.putCharSequence("android.selfDisplayName", this.mUser.mName);
        Person person = this.mUser;
        person.getClass();
        Bundle bundle2 = new Bundle();
        bundle2.putCharSequence("name", person.mName);
        IconCompat iconCompat = person.mIcon;
        bundle2.putBundle("icon", iconCompat != null ? iconCompat.toBundle() : null);
        bundle2.putString("uri", person.mUri);
        bundle2.putString("key", person.mKey);
        bundle2.putBoolean("isBot", person.mIsBot);
        bundle2.putBoolean("isImportant", person.mIsImportant);
        bundle.putBundle("android.messagingStyleUser", bundle2);
        bundle.putCharSequence("android.hiddenConversationTitle", this.mConversationTitle);
        if (this.mConversationTitle != null && this.mIsGroupConversation.booleanValue()) {
            bundle.putCharSequence("android.conversationTitle", this.mConversationTitle);
        }
        List list = this.mMessages;
        if (!((ArrayList) list).isEmpty()) {
            bundle.putParcelableArray("android.messages", Message.getBundleArrayForMessages(list));
        }
        List list2 = this.mHistoricMessages;
        if (!((ArrayList) list2).isEmpty()) {
            bundle.putParcelableArray("android.messages.historic", Message.getBundleArrayForMessages(list2));
        }
        Boolean bool = this.mIsGroupConversation;
        if (bool != null) {
            bundle.putBoolean("android.isGroupConversation", bool.booleanValue());
        }
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        boolean booleanValue;
        NotificationCompat$Builder notificationCompat$Builder = this.mBuilder;
        if (notificationCompat$Builder == null || notificationCompat$Builder.mContext.getApplicationInfo().targetSdkVersion >= 28 || this.mIsGroupConversation != null) {
            Boolean bool = this.mIsGroupConversation;
            if (bool != null) {
                booleanValue = bool.booleanValue();
            }
            booleanValue = false;
        } else {
            if (this.mConversationTitle != null) {
                booleanValue = true;
            }
            booleanValue = false;
        }
        this.mIsGroupConversation = Boolean.valueOf(booleanValue);
        Notification.MessagingStyle messagingStyle = new Notification.MessagingStyle(this.mUser.toAndroidPerson());
        Iterator it = ((ArrayList) this.mMessages).iterator();
        while (it.hasNext()) {
            messagingStyle.addMessage(((Message) it.next()).toAndroidMessage());
        }
        Iterator it2 = ((ArrayList) this.mHistoricMessages).iterator();
        while (it2.hasNext()) {
            messagingStyle.addHistoricMessage(((Message) it2.next()).toAndroidMessage());
        }
        this.mIsGroupConversation.booleanValue();
        messagingStyle.setConversationTitle(this.mConversationTitle);
        messagingStyle.setGroupConversation(this.mIsGroupConversation.booleanValue());
        messagingStyle.setBuilder(notificationCompatBuilder.mBuilder);
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
        ArrayList arrayList = (ArrayList) this.mMessages;
        arrayList.clear();
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
            arrayList.addAll(Message.getMessagesFromBundleArray(parcelableArray));
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Message {
        public String mDataMimeType;
        public Uri mDataUri;
        public final Bundle mExtras;
        public final Person mPerson;
        public final CharSequence mText;
        public final long mTimestamp;

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
                    bundle.putParcelable("sender_person", person.toAndroidPerson());
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

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00a9, code lost:
        
            r0.add(r11);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
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
                }
            }
            return arrayList;
        }

        public final Notification.MessagingStyle.Message toAndroidMessage() {
            Person person = this.mPerson;
            Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(this.mText, this.mTimestamp, person == null ? null : person.toAndroidPerson());
            String str = this.mDataMimeType;
            if (str != null) {
                message.setData(str, this.mDataUri);
            }
            return message;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        @Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Message(CharSequence charSequence, long j, CharSequence charSequence2) {
            this(charSequence, j, new Person(r0));
            Person.Builder builder = new Person.Builder();
            builder.mName = charSequence2;
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
