package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.widget.PeopleHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class CompactMessagingLayout extends FrameLayout {
    private ViewStub mConversationFacePileViewStub;
    private int mFacePileAvatarSize;
    private int mFacePileProtectionWidth;
    private int mFacePileSize;
    private int mLayoutColor;
    private int mNotificationBackgroundColor;
    private final PeopleHelper mPeopleHelper;

    @RemotableViewMethod(asyncImpl = "setGroupFacePileAsync")
    public void setGroupFacePile(Bundle bundle) {
    }

    public CompactMessagingLayout(Context context) {
        super(context);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CompactMessagingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CompactMessagingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CompactMessagingLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPeopleHelper = new PeopleHelper();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mConversationFacePileViewStub = (ViewStub) requireViewById(R.id.conversation_face_pile);
        this.mFacePileSize = getResources().getDimensionPixelSize(R.dimen.conversation_compact_face_pile_size);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(R.dimen.conversation_compact_face_pile_avatar_size);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(R.dimen.conversation_compact_face_pile_protection_width);
    }

    @RemotableViewMethod
    public Runnable setLayoutColorAsync(int i) {
        this.mLayoutColor = i;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    public void setLayoutColor(int i) {
        this.mLayoutColor = i;
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
        this.mNotificationBackgroundColor = i;
    }

    public Runnable setGroupFacePileAsync(Bundle bundle) {
        Icon icon;
        List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_MESSAGES));
        List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES));
        Person person = (Person) bundle.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
        List<List<Notification.MessagingStyle.Message>> groupMessages = groupMessages(messagesFromBundleArray, messagesFromBundleArray2);
        PeopleHelper.NameToPrefixMap mapUniqueNamesToPrefixWithGroupList = this.mPeopleHelper.mapUniqueNamesToPrefixWithGroupList(groupMessages);
        int i = this.mLayoutColor;
        CharSequence personKey = getPersonKey(person);
        int size = groupMessages.size() - 1;
        CharSequence charSequence = null;
        Icon icon2 = null;
        while (true) {
            if (size < 0) {
                icon = null;
                break;
            }
            Notification.MessagingStyle.Message message = groupMessages.get(size).get(0);
            Person senderPerson = message.getSenderPerson() != null ? message.getSenderPerson() : person;
            CharSequence personKey2 = getPersonKey(senderPerson);
            boolean z = personKey2 != personKey;
            boolean z2 = personKey2 != charSequence;
            if ((z && z2) || (size == 0 && charSequence == null)) {
                icon = getSenderIcon(senderPerson, mapUniqueNamesToPrefixWithGroupList, i);
                if (icon2 != null) {
                    break;
                }
                icon2 = icon;
                charSequence = personKey2;
            }
            size--;
        }
        if (icon2 == null) {
            icon2 = getSenderIcon(null, null, i);
        }
        if (icon == null) {
            icon = getSenderIcon(null, null, i);
        }
        final Drawable loadDrawable = icon.loadDrawable(getContext());
        final Drawable loadDrawable2 = icon2.loadDrawable(getContext());
        return new Runnable() { // from class: com.android.internal.widget.CompactMessagingLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CompactMessagingLayout.this.lambda$setGroupFacePileAsync$0(loadDrawable, loadDrawable2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupFacePileAsync$0(Drawable drawable, Drawable drawable2) {
        View inflate = this.mConversationFacePileViewStub.inflate();
        inflate.setVisibility(0);
        ImageView imageView = (ImageView) inflate.requireViewById(R.id.conversation_face_pile_bottom_background);
        ImageView imageView2 = (ImageView) inflate.requireViewById(R.id.conversation_face_pile_top);
        ImageView imageView3 = (ImageView) inflate.requireViewById(R.id.conversation_face_pile_bottom);
        imageView2.lambda$setImageURIAsync$0(drawable);
        imageView3.lambda$setImageURIAsync$0(drawable2);
        imageView.setImageTintList(ColorStateList.valueOf(this.mNotificationBackgroundColor));
        setSize(inflate, this.mFacePileSize);
        setSize(imageView3, this.mFacePileAvatarSize);
        setSize(imageView2, this.mFacePileAvatarSize);
        setSize(imageView, this.mFacePileAvatarSize + (this.mFacePileProtectionWidth * 2));
    }

    private Icon getSenderIcon(Person person, PeopleHelper.NameToPrefixMap nameToPrefixMap, int i) {
        String str = "";
        if (person == null) {
            return this.mPeopleHelper.createAvatarSymbol("", "", i);
        }
        if (person.getIcon() != null) {
            return person.getIcon();
        }
        CharSequence name = person.getName();
        if (TextUtils.isEmpty(name)) {
            return this.mPeopleHelper.createAvatarSymbol("", "", i);
        }
        if (nameToPrefixMap != null) {
            str = nameToPrefixMap.getPrefix(name);
        }
        return this.mPeopleHelper.createAvatarSymbol(name, str, i);
    }

    private static List<List<Notification.MessagingStyle.Message>> groupMessages(List<Notification.MessagingStyle.Message> list, List<Notification.MessagingStyle.Message> list2) {
        Notification.MessagingStyle.Message message;
        if (list.isEmpty() && list2.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        int size = list2.size();
        ArrayList arrayList2 = null;
        CharSequence charSequence = null;
        for (int i = 0; i < list.size() + size; i++) {
            if (i < size) {
                message = list2.get(i);
            } else {
                message = list.get(i - size);
            }
            if (message != null) {
                CharSequence personKey = getPersonKey(message.getSenderPerson());
                if (arrayList2 == null || personKey != charSequence) {
                    arrayList2 = new ArrayList();
                    arrayList.add(arrayList2);
                    charSequence = personKey;
                }
                arrayList2.add(message);
            }
        }
        return arrayList;
    }

    private static CharSequence getPersonKey(Person person) {
        if (person == null) {
            return null;
        }
        return person.getKey() == null ? person.getName() : person.getKey();
    }

    private static void setSize(View view, int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }
}
