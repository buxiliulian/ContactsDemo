package com.bxll.contactsdemo;

import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by David Chow on 2019-12-17.
 */
public interface ContactsQuery {
    String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.SORT_KEY_PRIMARY
    };

    Uri URI = ContactsContract.Contacts.CONTENT_URI;

    String SORT_KEY = ContactsContract.Contacts.SORT_KEY_PRIMARY;
}
