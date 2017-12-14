/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.classic.android;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * An appender that wraps the native Android logging mechanism (<i>logcat</i>);
 * redirects all logging requests to <i>logcat</i>
 * <p>
 * <b>Note:</b><br>
 * By default, this appender pushes all messages to <i>logcat</i> regardless
 * of <i>logcat</i>'s own filter settings (i.e., everything is printed). To disable this
 * behavior and enable filter-checking, use {@link #setCheckLoggable(boolean)}.
 * See the Android Developer Guide for details on adjusting the <i>logcat</i> filter.
 * <p>
 * See http://developer.android.com/guide/developing/tools/adb.html#filteringoutput
 *
 * @author Fred Eisele
 * @author Anthony Trinh
 */
public class LogcatFormatAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    /**
     * Max tag length enforced by Android
     * http://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String, int)
     */
    private static final int MAX_TAG_LENGTH = 23;
    private PatternLayoutEncoder encoder = null;
    private PatternLayoutEncoder tagEncoder = null;
    private boolean checkLoggable = false;

    /**
     * As in most cases, the default constructor does nothing.
     */
    public LogcatFormatAppender() {
    }

    /**
     * Checks that required parameters are set, and if everything is in order,
     * activates this appender.
     */
    @Override
    public void start() {
        if ((this.encoder == null) || (this.encoder.getLayout() == null)) {
            addError("No layout set for the appender named [" + name + "].");
            return;
        }

        // tag encoder is optional but needs a layout
        if (this.tagEncoder != null) {
            final Layout<?> layout = this.tagEncoder.getLayout();

            if (layout == null) {
                addError("No tag layout set for the appender named [" + name + "].");
                return;
            }

            // prevent stack traces from showing up in the tag
            // (which could lead to very confusing error messages)
            if (layout instanceof PatternLayout) {
                String pattern = this.tagEncoder.getPattern();
                if (!pattern.contains("%nopex")) {
                    this.tagEncoder.stop();
                    this.tagEncoder.setPattern(pattern + "%nopex");
                    this.tagEncoder.start();
                }

                PatternLayout tagLayout = (PatternLayout) layout;
                tagLayout.setPostCompileProcessor(null);
            }
        }

        super.start();
    }

    /**
     * Writes an event to Android's logging mechanism (logcat)
     *
     * @param event the event to be logged
     */
    public void append(ILoggingEvent event) {

        if (!isStarted()) {
            return;
        }

        String tag = getTag(event);
//        android.util.Log.e("Zero", this.encoder.getLayout().doLayout(event));
        switch (event.getLevel().levelInt) {
            case Level.ALL_INT:
            case Level.TRACE_INT:
                if (!checkLoggable || Log.isLoggable(tag, Log.VERBOSE)) {
                    final String[] logs = logFormat(this.encoder.getLayout().doLayout(event));
                    for (int i = 0; i < logs.length; i++) {
                        Log.v(tag, logs[i]);
                    }
                }
                break;

            case Level.DEBUG_INT:
                if (!checkLoggable || Log.isLoggable(tag, Log.DEBUG)) {
                    final String[] logs = logFormat(this.encoder.getLayout().doLayout(event));
                    for (int i = 0; i < logs.length; i++) {
                        Log.d(tag, logs[i]);
                    }
                }
                break;

            case Level.INFO_INT:
                if (!checkLoggable || Log.isLoggable(tag, Log.INFO)) {
                    final String[] logs = logFormat(this.encoder.getLayout().doLayout(event));
                    for (int i = 0; i < logs.length; i++) {
                        Log.i(tag, logs[i]);
                    }
                }
                break;

            case Level.WARN_INT:
                if (!checkLoggable || Log.isLoggable(tag, Log.WARN)) {
                    final String[] logs = logFormat(this.encoder.getLayout().doLayout(event));
                    for (int i = 0; i < logs.length; i++) {
                        Log.w(tag, logs[i]);
                    }
                }
                break;

            case Level.ERROR_INT:
                if (!checkLoggable || Log.isLoggable(tag, Log.ERROR)) {
                    Log.e(tag, this.encoder.getLayout().doLayout(event));
                }
                break;

            case Level.OFF_INT:
            default:
                break;
        }
    }


    private String[] logFormat(String log) {
        if (TextUtils.isEmpty(log)) {
            return new String[]{log};
        }
        String[] tmplogs = log.split("\n");
        ArrayList<String> logList = new ArrayList<>();
        for (int i = 0; i < tmplogs.length; i++) {
            logList.addAll(getStrList(tmplogs[i], 4000));
        }
        final int Size = logList.size();
        String[] logs = new String[Size];
        for (int i = 0; i < Size; i++) {
            logs[i] = logList.get(i);
        }
        return logs;
    }

    public static List<String> getStrList(String inputString, int length) {
        if (TextUtils.isEmpty(inputString)) {
            return new ArrayList<String>();
        }
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    public static List<String> getStrList(String inputString, int length,
                                          int size) {
        List<String> list = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    public static String substring(String str, int f, int t) {
        if (f > str.length()) {
            return null;
        }
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }


    /**
     * Gets the pattern-layout encoder for this appender's <i>logcat</i> message
     *
     * @return the pattern-layout encoder
     */
    public PatternLayoutEncoder getEncoder() {
        return this.encoder;
    }

    /**
     * Sets the pattern-layout encoder for this appender's <i>logcat</i> message
     *
     * @param encoder the pattern-layout encoder
     */
    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Gets the pattern-layout encoder for this appender's <i>logcat</i> tag
     *
     * @return the pattern encoder
     */
    public PatternLayoutEncoder getTagEncoder() {
        return this.tagEncoder;
    }

    /**
     * Sets the pattern-layout encoder for this appender's <i>logcat</i> tag
     * <p>
     * The expanded text of the pattern must be less than 23 characters as
     * limited by Android. Layouts that exceed this limit are truncated,
     * and a star is appended to the tag to indicate this.
     * <p>
     * The <code>tagEncoder</code> result is limited to 22 characters plus a star to
     * indicate truncation (<code>%logger{0}</code> has no length limit in logback,
     * but <code>LogcatAppender</code> limits the length internally). For example,
     * if the <code>tagEncoder</code> evaluated to <code>foo.foo.foo.foo.foo.bar.Test</code>,
     * the tag seen in Logcat would be: <code>foo.foo.foo.foo.foo.ba*</code>.
     * Note that <code>%logger{23}</code> yields more useful results
     * (in this case: <code>f.f.foo.foo.bar.Test</code>).
     *
     * @param encoder the pattern-layout encoder; specify {@code null} to
     *                automatically use the logger's name as the tag
     */
    public void setTagEncoder(PatternLayoutEncoder encoder) {
        this.tagEncoder = encoder;
    }

    /**
     * Sets whether to ask Android before logging a message with a specific
     * tag and priority (i.e., calls {@code android.util.Log.html#isLoggable}).
     * <p>
     * See http://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String, int)
     *
     * @param enable {@code true} to enable; {@code false} to disable
     */
    public void setCheckLoggable(boolean enable) {
        this.checkLoggable = enable;
    }

    /**
     * Gets the enable status of the <code>isLoggable()</code>-check
     * that is called before logging
     * <p>
     * See http://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String, int)
     *
     * @return {@code true} if enabled; {@code false} otherwise
     */
    public boolean getCheckLoggable() {
        return this.checkLoggable;
    }

    /**
     * Gets the logcat tag string of a logging event
     *
     * @param event logging event to evaluate
     * @return the tag string, truncated if max length exceeded
     */
    protected String getTag(ILoggingEvent event) {
        // format tag based on encoder layout; truncate if max length
        // exceeded (only necessary for isLoggable(), which throws
        // IllegalArgumentException)
        if (!TextUtils.isEmpty(event.tag())) {
            return event.tag();
        }
        String tag = (this.tagEncoder != null) ? this.tagEncoder.getLayout().doLayout(event) : event.getLoggerName();
        if (checkLoggable && (tag.length() > MAX_TAG_LENGTH)) {
            tag = tag.substring(0, MAX_TAG_LENGTH - 1) + "*";
        }
        return tag;
    }
}
