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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NotificationCompat$MessagingStyle extends NotificationCompat$Style {
    public CharSequence mConversationTitle;
    public Boolean mIsGroupConversation;
    public Person mUser;
    public final List mMessages = new ArrayList();
    public final List mHistoricMessages = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api26Impl {
        private Api26Impl() {
        }

        public static Notification.MessagingStyle addHistoricMessage(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
            return messagingStyle.addHistoricMessage(message);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        boolean booleanValue;
        NotificationCompat$Builder notificationCompat$Builder = this.mBuilder;
        int i = 0;
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
        Notification.MessagingStyle createMessagingStyle = Api28Impl.createMessagingStyle(this.mUser.toAndroidPerson());
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
            Notification.MessagingStyle.Message createMessage = Message.Api28Impl.createMessage(charSequence, message.mTimestamp, person != null ? person.toAndroidPerson() : null);
            String str = message.mDataMimeType;
            if (str != null) {
                Message.Api24Impl.setData(createMessage, str, message.mDataUri);
            }
            Api24Impl.addMessage(createMessagingStyle, createMessage);
        }
        ArrayList arrayList2 = (ArrayList) this.mHistoricMessages;
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            Message message2 = (Message) obj2;
            CharSequence charSequence2 = message2.mText;
            Person person2 = message2.mPerson;
            Notification.MessagingStyle.Message createMessage2 = Message.Api28Impl.createMessage(charSequence2, message2.mTimestamp, person2 == null ? null : person2.toAndroidPerson());
            String str2 = message2.mDataMimeType;
            if (str2 != null) {
                Message.Api24Impl.setData(createMessage2, str2, message2.mDataUri);
            }
            Api26Impl.addHistoricMessage(createMessagingStyle, createMessage2);
        }
        this.mIsGroupConversation.getClass();
        Api24Impl.setConversationTitle(createMessagingStyle, this.mConversationTitle);
        Api28Impl.setGroupConversation(createMessagingStyle, this.mIsGroupConversation.booleanValue());
        createMessagingStyle.setBuilder(notificationCompatBuilder.mBuilder);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Message {
        public String mDataMimeType;
        public Uri mDataUri;
        public final Bundle mExtras;
        public final Person mPerson;
        public final CharSequence mText;
        public final long mTimestamp;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00a6, code lost:
        
            r0.add(r11);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.util.List getMessagesFromBundleArray(android.os.Parcelable[] r13) {
            /*
                java.util.ArrayList r0 = new java.util.ArrayList
                int r1 = r13.length
                r0.<init>(r1)
                r1 = 0
            L7:
                int r2 = r13.length
                if (r1 >= r2) goto Lad
                r2 = r13[r1]
                boolean r3 = r2 instanceof android.os.Bundle
                if (r3 == 0) goto La9
                android.os.Bundle r2 = (android.os.Bundle) r2
                java.lang.String r3 = "extras"
                java.lang.String r4 = "uri"
                java.lang.String r5 = "type"
                java.lang.String r6 = "sender"
                java.lang.String r7 = "sender_person"
                java.lang.String r8 = "person"
                java.lang.String r9 = "time"
                java.lang.String r10 = "text"
                r11 = 0
                boolean r12 = r2.containsKey(r10)     // Catch: java.lang.ClassCastException -> La4
                if (r12 == 0) goto La4
                boolean r12 = r2.containsKey(r9)     // Catch: java.lang.ClassCastException -> La4
                if (r12 != 0) goto L35
                goto La4
            L35:
                boolean r12 = r2.containsKey(r8)     // Catch: java.lang.ClassCastException -> La4
                if (r12 == 0) goto L44
                android.os.Bundle r6 = r2.getBundle(r8)     // Catch: java.lang.ClassCastException -> La4
                androidx.core.app.Person r6 = androidx.core.app.Person.fromBundle(r6)     // Catch: java.lang.ClassCastException -> La4
                goto L6d
            L44:
                boolean r8 = r2.containsKey(r7)     // Catch: java.lang.ClassCastException -> La4
                if (r8 == 0) goto L55
                android.os.Parcelable r6 = r2.getParcelable(r7)     // Catch: java.lang.ClassCastException -> La4
                android.app.Person r6 = (android.app.Person) r6     // Catch: java.lang.ClassCastException -> La4
                androidx.core.app.Person r6 = androidx.core.app.Person.fromAndroidPerson(r6)     // Catch: java.lang.ClassCastException -> La4
                goto L6d
            L55:
                boolean r7 = r2.containsKey(r6)     // Catch: java.lang.ClassCastException -> La4
                if (r7 == 0) goto L6c
                androidx.core.app.Person$Builder r7 = new androidx.core.app.Person$Builder     // Catch: java.lang.ClassCastException -> La4
                r7.<init>()     // Catch: java.lang.ClassCastException -> La4
                java.lang.CharSequence r6 = r2.getCharSequence(r6)     // Catch: java.lang.ClassCastException -> La4
                r7.mName = r6     // Catch: java.lang.ClassCastException -> La4
                androidx.core.app.Person r6 = new androidx.core.app.Person     // Catch: java.lang.ClassCastException -> La4
                r6.<init>(r7)     // Catch: java.lang.ClassCastException -> La4
                goto L6d
            L6c:
                r6 = r11
            L6d:
                androidx.core.app.NotificationCompat$MessagingStyle$Message r7 = new androidx.core.app.NotificationCompat$MessagingStyle$Message     // Catch: java.lang.ClassCastException -> La4
                java.lang.CharSequence r8 = r2.getCharSequence(r10)     // Catch: java.lang.ClassCastException -> La4
                long r9 = r2.getLong(r9)     // Catch: java.lang.ClassCastException -> La4
                r7.<init>(r8, r9, r6)     // Catch: java.lang.ClassCastException -> La4
                boolean r6 = r2.containsKey(r5)     // Catch: java.lang.ClassCastException -> La4
                if (r6 == 0) goto L94
                boolean r6 = r2.containsKey(r4)     // Catch: java.lang.ClassCastException -> La4
                if (r6 == 0) goto L94
                java.lang.String r5 = r2.getString(r5)     // Catch: java.lang.ClassCastException -> La4
                android.os.Parcelable r4 = r2.getParcelable(r4)     // Catch: java.lang.ClassCastException -> La4
                android.net.Uri r4 = (android.net.Uri) r4     // Catch: java.lang.ClassCastException -> La4
                r7.mDataMimeType = r5     // Catch: java.lang.ClassCastException -> La4
                r7.mDataUri = r4     // Catch: java.lang.ClassCastException -> La4
            L94:
                boolean r4 = r2.containsKey(r3)     // Catch: java.lang.ClassCastException -> La4
                if (r4 == 0) goto La3
                android.os.Bundle r4 = r7.mExtras     // Catch: java.lang.ClassCastException -> La4
                android.os.Bundle r2 = r2.getBundle(r3)     // Catch: java.lang.ClassCastException -> La4
                r4.putAll(r2)     // Catch: java.lang.ClassCastException -> La4
            La3:
                r11 = r7
            La4:
                if (r11 == 0) goto La9
                r0.add(r11)
            La9:
                int r1 = r1 + 1
                goto L7
            Lad:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat$MessagingStyle.Message.getMessagesFromBundleArray(android.os.Parcelable[]):java.util.List");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        @java.lang.Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public Message(java.lang.CharSequence r2, long r3, java.lang.CharSequence r5) {
            /*
                r1 = this;
                androidx.core.app.Person$Builder r0 = new androidx.core.app.Person$Builder
                r0.<init>()
                r0.mName = r5
                androidx.core.app.Person r5 = new androidx.core.app.Person
                r5.<init>(r0)
                r1.<init>(r2, r3, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat$MessagingStyle.Message.<init>(java.lang.CharSequence, long, java.lang.CharSequence):void");
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
