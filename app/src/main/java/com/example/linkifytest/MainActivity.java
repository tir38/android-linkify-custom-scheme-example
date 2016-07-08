package com.example.linkifytest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Works.
         */
        TextView textView1 = (TextView) findViewById(R.id.text_view1);
        textView1.setText("http://www.google.com");
        Linkify.addLinks(textView1, Linkify.WEB_URLS);

        /**
         * Works
         */
        TextView textView2 = (TextView) findViewById(R.id.text_view2);
        textView2.setText("https://www.google.com");
        Linkify.addLinks(textView2, Linkify.WEB_URLS);

        /**
         * Doesn't work.
         *
         * See {@link Linkify#addLinks(Spannable, int)} specifically:
         *
         if ((mask & WEB_URLS) != 0) {
         gatherLinks(links, text, Patterns.WEB_URL,
         new String[] { "http://", "https://", "rtsp://" },
         sUrlMatchFilter, null);
         }
         */
        TextView textView3 = (TextView) findViewById(R.id.text_view3);
        textView3.setText("custom://www.google.com");
        Linkify.addLinks(textView3, Linkify.WEB_URLS);


        /**
         * This works but you have do do some fancy footwork to use Linkify
         */
        String fullString = "This sentence contains a custom://www.google.com custom scheme url";
        TextView textView4 = (TextView) findViewById(R.id.text_view4);
        textView4.setText(fullString);

        Pattern urlDetect = Pattern.compile("([a-zA-Z0-9]+):\\/\\/([a-zA-Z0-9.]+)"); // this is a terrible regex, don't use it.
        Matcher matcher = urlDetect.matcher(fullString);
        String scheme = null;

        while (matcher.find()) {
            String customSchemedUrl = matcher.group(0);
            Uri uri = Uri.parse(customSchemedUrl);
            // Now you could create an intent yourself...
            // ...or if you want to rely on Linkify keep going
            scheme = uri.getScheme();
            break;
        }

        if (!TextUtils.isEmpty(scheme)) {
            Linkify.addLinks(textView4, urlDetect, scheme);
        }

        // Now if you don't have a matching intent filter, you can check the logs for:
        // com.example.linkifytest W/URLSpan: Actvity was not found for intent, Intent { act=android.intent.action.VIEW dat=custom://www.google.com (has extras) }

        // NOTE: You'll have to get fancy if your full string contains several different schemes.
    }
}
