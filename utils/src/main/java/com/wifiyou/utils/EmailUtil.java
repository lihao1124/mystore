package com.wifiyou.utils;

import android.content.Context;
import android.content.Intent;


/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/14/16
 */
public class EmailUtil {

    public static void launchEmail(Context activity, String[] to, String title, String content) {
        try {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL,
                    to);
            email.putExtra(Intent.EXTRA_SUBJECT, title);
            email.putExtra(Intent.EXTRA_TEXT, content);
            email.setType("message/rfc822");
            activity.startActivity(Intent.createChooser(email, "Choose an Email client :"));
        } catch (Exception e) {
            e.printStackTrace();
            MainThreadPostUtils.toast(R.string.not_found_any_email_client);
        }
    }
}
